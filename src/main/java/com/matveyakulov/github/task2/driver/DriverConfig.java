package com.matveyakulov.github.task2.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class DriverConfig {

    public static WebDriver getDriver(){

        String path = new File("src/main/resources/utils/chromedriver.exe").getAbsolutePath(); // chromedriver version 99
        System.setProperty("webdriver.chrome.driver", path);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }
}
