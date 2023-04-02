package com.github.Chestaci.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

/**
 * Утилитный класс для настроек WebDriver
 */
public class WebDriverUtils {

    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        return options;
    }

    public static void setUpDriver(WebDriver driver) {
        long delaySeconds = 60;
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(delaySeconds));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(delaySeconds));
    }


}
