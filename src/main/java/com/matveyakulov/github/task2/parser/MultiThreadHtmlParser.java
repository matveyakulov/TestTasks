package com.matveyakulov.github.task2.parser;

import com.matveyakulov.github.task2.callable.CountryHandler;
import com.matveyakulov.github.task2.domain.Country;
import com.matveyakulov.github.task2.driver.DriverConfig;
import com.matveyakulov.github.task2.mapper.PhoneNumMapper;
import com.matveyakulov.github.task2.writer.FileWriter;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreadHtmlParser {

    private static final Logger log = Logger.getLogger(MultiThreadHtmlParser.class);

    public static Map<String, Map<String, Double>> getServicesFromUri(String uri) {

        Map<String, Map<String, Double>> resultData = new HashMap<>();
        WebDriver driver = DriverConfig.getDriver();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        List<Future<Country>> futures = new ArrayList<>();
        try {
            log.info("Подключение к сайту " + uri);
            driver.get(uri);
            List<WebElement> countries = driver.findElements(By.className("country-name"));
            int countCountry = countries.size();
            for (int i = 0; i < countCountry; i++) {
                String countryName = countries.get(i).getText();
                futures.add(executorService.submit(new CountryHandler(countryName, uri, i)));
            }
            executorService.shutdown();
            for(Future<Country> future: futures){
                try {
                    Country country = future.get();
                    FileWriter.writeCountry(country);
                    resultData.put(country.getCountryName(), country.getServices());
                    log.info("Парсинг страны " + country.getCountryName() + " завершен успешно");
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            driver.quit();
        }
        FileWriter.writeAllCountry(resultData);
        return resultData;
    }

    public static Map<String, Double> getServicesFromCountry(String uri, int i) {

        Map<String, Double> data;
        WebDriver driver = DriverConfig.getDriver();
        try {
            driver.get(uri);
            List<WebElement> countries = driver.findElements(By.className("country-name"));
            log.info("Старт парсинга страны " + countries.get(i).getText());

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", countries.get(i));

            List<WebElement> serviceNames = driver.findElements(By.cssSelector("div.service-block span.price-name"));
            List<WebElement> servicePrices = driver.findElements(By.cssSelector("div.service-block span.price-text"));
            data = PhoneNumMapper.getPhoneData(serviceNames, servicePrices);

        } finally {
            driver.quit();
        }
        return data;
    }
}
