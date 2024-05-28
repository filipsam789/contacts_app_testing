package org.example.utils;


import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

import static org.example.constants.AppConstants.*;

public class DriverUtils {
    private static AppiumDriver driver;

    public static DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", PLATFORM_NAME);
        desiredCapabilities.setCapability("deviceName", DEVICE_NAME);
        desiredCapabilities.setCapability("appPackage", APP_PACKAGE);
        desiredCapabilities.setCapability("appActivity", APP_ACTIVITY);
        desiredCapabilities.setCapability("automationName", AUTOMATION_NAME);
        return desiredCapabilities;
    }

    public static void initDriver() {
        try {
            driver = new AppiumDriver(new URL("http://localhost:4723"), getDesiredCapabilities());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static AppiumDriver getDriver() {
        return driver;
    }

    public static void stopDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}

