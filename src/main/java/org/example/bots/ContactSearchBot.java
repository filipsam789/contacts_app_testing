package org.example.bots;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ContactSearchBot extends BaseBot {
    private final AppiumDriver driver;
    private final List<String> allContacts = new ArrayList<>();

    public ContactSearchBot(AppiumDriver driver) {
        this.driver = driver;
    }

    public List<String> searchContactByName(String contactName) {
        WebElement searchInput = driver.findElement(By.id("com.android.contacts:id/menu_search"));
        searchInput.click();

        WebElement input = driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"com.android.contacts:id/search_view\"]"));

        char[] chars = contactName.toCharArray();
        String[] keys = new String[chars.length];
        for (int i = 0; i < chars.length; i++) {
            keys[i] = String.valueOf(chars[i]);
        }
        input.sendKeys(keys);

        List<String> searchResults = new ArrayList<>();

        List<WebElement> contactElements = driver.findElements(By.xpath("//android.widget.TextView[@content-desc]"));
        for (WebElement contactElement : contactElements) {
            String name = contactElement.getAttribute("content-desc");
            searchResults.add(name);
        }
        return searchResults;
    }

    // Find all contacts
    public List<String> getAllContacts() {
        List<WebElement> contactElements = driver.findElements(By.id("com.android.contacts:id/cliv_name_textview"));
        for (WebElement contactElement : contactElements) {
            String contactName = contactElement.getAttribute("content-desc");
            allContacts.add(contactName);
        }
        System.out.println(allContacts);

        return allContacts;
    }
}
