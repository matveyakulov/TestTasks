package com.matveyakulov.github.task2.parser;

import com.matveyakulov.github.task2.domain.Country;
import com.matveyakulov.github.task2.driver.DriverConfig;
import com.matveyakulov.github.task2.mapper.PhoneNumMapper;
import com.matveyakulov.github.task2.writer.FileWriter;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleThreadHtmlParser {

    private static final Logger log = Logger.getLogger(SingleThreadHtmlParser.class);

    public static Map<String, Map<String, Double>> getServicesFromUri(String uri) {

        Map<String, Map<String, Double>> resultData = new HashMap<>();
        WebDriver driver = DriverConfig.getDriver();
        try {
            log.info("Подключение к сайту " + uri);
            driver.get(uri);
            List<WebElement> countries = driver.findElements(By.className("country-name"));

            for (WebElement country : countries) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", country);
                log.info("Старт парсинга страны " + country.getText());

                List<WebElement> serviceNames = driver.findElements(By.cssSelector("div.service-block span.price-name"));
                List<WebElement> servicePrices = driver.findElements(By.cssSelector("div.service-block span.price-text"));
                Map<String, Double> services = PhoneNumMapper.getPhoneData(serviceNames, servicePrices);
                FileWriter.writeCountry(new Country(country.getText(), services));
                resultData.put(country.getText(), services);
                log.info("Парсинг страны " + country.getText() + " завершен успешно");
            }
        } finally {
            driver.quit();
        }
        FileWriter.writeAllCountry(resultData);
        return resultData;
    }


}
