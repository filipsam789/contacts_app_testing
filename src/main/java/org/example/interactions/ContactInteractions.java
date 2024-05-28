package org.example.interactions;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;

public class ContactInteractions {
    private final AppiumDriver driver;

    public ContactInteractions(AppiumDriver driver) {
        this.driver = driver;
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

    // Find all contacts no duplicates
    public List<String> getAllContactsNoDuplicates() {
        Set<String> allContactsNoDuplicates = new TreeSet<>();

        List<WebElement> listOfContacts = driver.findElements(By.id("android:id/list"));
        if (listOfContacts.isEmpty()) {
            return Collections.emptyList();
        }
        List<WebElement> contactElements = driver.findElements(By.id("com.android.contacts:id/cliv_name_textview"));
        for (WebElement contactElement : contactElements) {
            String contactName = contactElement.getAttribute("content-desc");
            allContactsNoDuplicates.add(contactName);
        }

        return new ArrayList<>(allContactsNoDuplicates);
    }

    //Find all contacts
    public List<String> getAllContacts() {
        List<String> allContacts = new ArrayList<>();

        List<WebElement> listOfContacts = driver.findElements(By.id("android:id/list"));
        if (listOfContacts.isEmpty()) {
            return Collections.emptyList();
        }
        List<WebElement> contactElements = driver.findElements(By.id("com.android.contacts:id/cliv_name_textview"));
        for (WebElement contactElement : contactElements) {
            String contactName = contactElement.getAttribute("content-desc");
            allContacts.add(contactName);
        }
        return new ArrayList<>(allContacts);
    }
}