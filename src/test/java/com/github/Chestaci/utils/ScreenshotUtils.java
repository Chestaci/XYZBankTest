package com.github.Chestaci.utils;

import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScreenshotUtils {
    @Attachment
    private static byte[] getBytes(String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }

    private static String captureScreen(WebDriver driver) {
        String path;
        try {
            WebDriver webDriver = new Augmenter().augment(driver);
            File source = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
            path = "./target/screenshots/" + source.getName();
            FileUtils.copyFile(source, new File(path));
        }
        catch(IOException e) {
            path = "Failed to capture screenshot: " + e.getMessage();
        }
        return path;
    }

    public static byte[] getScreenshot(WebDriver driver){
        try {
            return getBytes(captureScreen(driver));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
