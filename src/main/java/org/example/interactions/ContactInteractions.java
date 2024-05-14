package org.example.interactions;

import io.appium.java_client.AppiumDriver;
import org.example.bots.BaseBot;
import org.example.bots.ContactSearchBot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ContactInteractions {
    private final AppiumDriver driver;
    private final BaseBot baseBot;

    public ContactInteractions(AppiumDriver driver, BaseBot baseBot) {
        this.driver = driver;
        this.baseBot = baseBot;
    }
    public void setDisplayByLastName() throws InterruptedException {
        WebElement menu = driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Open navigation drawer\"]"));
        menu.click();
        WebElement settings = driver.findElement(By.id("com.android.contacts:id/nav_settings"));
        settings.click();
        WebElement nameFormat = driver.findElement(By.xpath("//(android.widget.ListView[@resource-id=\"android:id/list\"])[1]/android.widget.LinearLayout[6]/android.widget.RelativeLayout"));
        nameFormat.click();
        WebElement lastName = driver.findElement(By.xpath("//android.widget.CheckedTextView[@resource-id=\"android:id/text1\" and @text=\"Last name first\"]"));
        lastName.click();
        WebElement backButton = driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"));
        backButton.click();
        Thread.sleep(2000);
    }
    public void setSortByLastName() throws InterruptedException {
        Thread.sleep(2000);
        WebElement menu = driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Open navigation drawer\"]"));
        menu.click();
        WebElement settings = driver.findElement(By.id("com.android.contacts:id/nav_settings"));
        settings.click();
        WebElement sortBy = driver.findElement(By.xpath("(//android.widget.ListView[@resource-id=\"android:id/list\"])[1]/android.widget.LinearLayout[5]/android.widget.RelativeLayout"));
        sortBy.click();
        WebElement lastName = driver.findElement(By.xpath("//android.widget.CheckedTextView[@resource-id=\"android:id/text1\" and @text=\"Last name\"]"));
        lastName.click();
        WebElement backButton = driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"));
        backButton.click();
        Thread.sleep(2000);
    }
}
