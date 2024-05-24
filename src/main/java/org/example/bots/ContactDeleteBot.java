package org.example.bots;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ContactDeleteBot extends BaseBot {
    private final AppiumDriver driver;

    public ContactDeleteBot(AppiumDriver driver) {
        this.driver = driver;
    }

    public String deleteContact(int index) throws InterruptedException {
        System.out.println("index " + index);
        WebElement contact = driver.findElement(By.xpath("//android.widget.ListView[@resource-id=\"android:id/list\"]/android.view.ViewGroup[" + index + "]"));
        contact.click();
        Thread.sleep(1000);
        WebElement moreOptions = driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"More options\"]"));
        moreOptions.click();
        Thread.sleep(1000);
        WebElement delete = driver.findElement(By.xpath("//android.widget.ListView/android.widget.LinearLayout[2]"));
        delete.click();
        Thread.sleep(1000);
        WebElement confirm = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]"));
        confirm.click();
        WebElement deletedName = driver.findElement(By.xpath("//android.widget.Toast"));
        return deletedName.getAttribute("text");
    }

}
