package org.example.bots;

import io.appium.java_client.AppiumDriver;
import org.example.constants.PhoneNumberValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;

public class ContactSearchBot extends BaseBot {
    private final AppiumDriver driver;
    private final HashMap<String, SortedSet<String>> allContactNumbers = new HashMap<>();

    public ContactSearchBot(AppiumDriver driver) {
        this.driver = driver;
    }

    public void searchContactByQuery(String searchQuery) {
        WebElement searchInput = driver.findElement(By.id("com.android.contacts:id/menu_search"));
        searchInput.click();

        WebElement input = driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"com.android.contacts:id/search_view\"]"));

        char[] chars = searchQuery.toCharArray();
        String[] keys = new String[chars.length];
        for (int i = 0; i < chars.length; i++) {
            keys[i] = String.valueOf(chars[i]);
        }
        input.sendKeys(keys);
    }

    public List<String> searchContactByNameOrEmail(String searchQuery) throws InterruptedException {
        this.searchContactByQuery(searchQuery);

        List<String> searchResults = new ArrayList<>();

        List<WebElement> contactElements = driver.findElements(By.xpath("//android.widget.TextView[@content-desc]"));
        Thread.sleep(1000);

        for (WebElement contactElement : contactElements) {
            String name = contactElement.getAttribute("content-desc");
            searchResults.add(name);
        }
        return searchResults;
    }

    public List<String> searchContactByPhoneNumber(String phoneNumber) throws InterruptedException {
        this.searchContactByQuery(phoneNumber);

        List<String> searchResults = new ArrayList<>();

        List<WebElement> contactElements = driver.findElements(By.xpath("//android.widget.ListView[@resource-id=\"android:id/list\"]/android.view.ViewGroup"));
        Thread.sleep(1000);

        for (WebElement contactElement : contactElements) {
            String contactNumber = contactElement.findElement(By.xpath("//android.widget.TextView[@content-desc][2]")).getText();
            searchResults.add(contactNumber.replaceAll("\\s+", ""));
        }

        return searchResults;
    }


    // Find all contact numbers
    public HashMap<String, SortedSet<String>> getAllContactNumbers() {
        List<WebElement> contactElements = driver.findElements(By.xpath("//android.widget.ListView[@resource-id=\"android:id/list\"]/android.view.ViewGroup"));
        for (WebElement contactElement : contactElements) {
            contactElement.click();
            List<WebElement> numberElements = driver.findElements(By.id("com.android.contacts:id/header"));
            for (WebElement number : numberElements) {
                String contactNumber = number.getAttribute("text");
                if (!PhoneNumberValidator.isValidPhoneNumber(contactNumber))
                    continue;
                String contactName = driver.findElement(By.id("com.android.contacts:id/large_title")).getText();
                if (allContactNumbers.containsKey(contactName)) {
                    allContactNumbers.get(contactName).add(contactNumber.replaceAll("\\s+", ""));
                } else {
                    allContactNumbers.computeIfAbsent(contactName, v -> new TreeSet<>()).add(contactNumber.replaceAll("\\s+", ""));
                }
            }
            driver.navigate().back();
        }

        return allContactNumbers;
    }

}
