package org.example.interactions;

import io.appium.java_client.AppiumDriver;
import org.example.bots.BaseBot;
import org.example.bots.ContactSearchBot;
import org.example.utils.DriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;

public class ContactInteractions {
    private final BaseBot baseBot;
    private final AppiumDriver driver;

    public ContactInteractions(AppiumDriver driver, BaseBot baseBot) {
        this.driver = driver;
        this.baseBot = baseBot;
    }


    // Find all contacts
    public List<String> getAllContacts() {
        Set<String> allContactsNoDuplicates = new HashSet<>();

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


}