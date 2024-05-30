package org.example.bots;

import io.appium.java_client.AppiumDriver;
import org.example.constants.PhoneNumberValidator;
import org.example.interactions.ContactInteractions;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.*;

import static org.example.interactions.ContactInteractions.getNewSize;

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

//    public List<String> searchContactByNameOrEmail(String searchQuery) throws InterruptedException {
//        this.searchContactByQuery(searchQuery);
//
//        List<String> searchResults = new ArrayList<>();
//        Thread.sleep(1000);
//
//        List<WebElement> contactElements = driver.findElements(By.xpath("//android.widget.TextView[@content-desc]"));
//        Thread.sleep(1000);
//
//        for (WebElement contactElement : contactElements) {
//            String name = contactElement.getAttribute("content-desc");
//            searchResults.add(name);
//        }
//        return searchResults;
//    }

//    public List<String> searchContactByPhoneNumber(String phoneNumber) throws InterruptedException {
//        this.searchContactByQuery(phoneNumber);
//
//        List<String> searchResults = new ArrayList<>();
//
//        List<WebElement> contactElements = driver.findElements(By.xpath("//android.widget.ListView[@resource-id=\"android:id/list\"]/android.view.ViewGroup"));
//        Thread.sleep(1000);
//
//        for (WebElement contactElement : contactElements) {
//            String contactNumber = null;
//            try {
//                contactNumber = contactElement.findElement(By.xpath("//android.widget.TextView[@content-desc][2]")).getText();
//            } catch (Exception e) {
//                contactNumber = contactElement.findElement(By.xpath("//android.widget.TextView[@content-desc][1]")).getText();
//            }
//            searchResults.add(contactNumber.replaceAll("\\s+", ""));
//        }
//        Thread.sleep(1000);
//        return searchResults;
//    }

//    public List<String> searchContactByPhoneNumber(String phoneNumber) throws InterruptedException {
//        this.searchContactByQuery(phoneNumber);
//
//        List<String> searchResults = new ArrayList<>();
//
//        By containerLocator = By.id("com.android.contacts:id/content_frame");
//        Dimension size = driver.manage().window().getSize();
//        Dimension oldSize = size;
//
//        int startY = (int) (size.height * 0.8);
//        int endY = (int) (size.height * 0.2);
//        boolean scrolled = true;
//        int counter = 0;
//        WebElement list = driver.findElement(By.id("android:id/list"));
//
//        while (scrolled) {
//            List<WebElement> contactElements = list.findElements(By.xpath("//android.widget.ListView[@resource-id=\"android:id/list\"]/android.view.ViewGroup"));
//            int existingSize = !searchResults.isEmpty() ? searchResults.size() - 1 : 0;
//            for (int i = existingSize; i < contactElements.size(); i++) {
//                String contactNumber = null;
//                try {
//                    contactNumber = contactElements.get(i).findElement(By.xpath("//android.widget.TextView[@content-desc][2]")).getText();
//                } catch (Exception e) {
//                    contactNumber = contactElements.get(i).findElement(By.xpath("//android.widget.TextView[@content-desc][1]")).getText();
//                }
//                searchResults.add(contactNumber.replaceAll("\\s+", ""));
//            }
//
//            new Actions(driver)
//                    .moveToElement(driver.findElement(containerLocator))
//                    .clickAndHold()
//                    .moveByOffset(0, endY - startY)
//                    .release()
//                    .perform();
//
//            // Wait a bit for the content to load
//            Thread.sleep(100);
//
//            // Check if the container is still scrollable
//            Dimension newSize = ContactInteractions.getNewSize(driver, containerLocator);
//            oldSize = newSize;
//            if (counter == 1) scrolled = false;
//            if (newSize.getHeight() == oldSize.getHeight()) counter++;
//        }
//
//        return searchResults;
//    }
//
//
//    public List<String> searchContactByNameOrEmail(String searchQuery) throws InterruptedException {
//        this.searchContactByQuery(searchQuery);
//
//        List<String> searchResults = new ArrayList<>();
//        Thread.sleep(1000);
//
//        By containerLocator = By.id("com.android.contacts:id/content_frame");
//        Dimension size = driver.manage().window().getSize();
//        Dimension oldSize = size;
//
//        int startY = (int) (size.height * 0.8);
//        int endY = (int) (size.height * 0.2);
//        boolean scrolled = true;
//        int counter = 0;
//        WebElement list = driver.findElement(By.id("android:id/list"));
//
//        while (scrolled) {
//            List<WebElement> contactElements = list.findElements(By.xpath("//android.widget.TextView[@content-desc]"));
//            int existingSize = !searchResults.isEmpty() ? searchResults.size() - 1 : 0;
//            for (int i = existingSize; i < contactElements.size(); i++) {
//                String name = contactElements.get(i).getAttribute("content-desc");
//                searchResults.add(name);
//            }
//
//            new Actions(driver)
//                    .moveToElement(driver.findElement(containerLocator))
//                    .clickAndHold()
//                    .moveByOffset(0, endY - startY)
//                    .release()
//                    .perform();
//
//            // Wait a bit for the content to load
//            Thread.sleep(100);
//
//            // Check if the container is still scrollable
//            Dimension newSize = ContactInteractions.getNewSize(driver, containerLocator);
//            oldSize = newSize;
//            if (counter == 1) scrolled = false;
//            if (newSize.getHeight() == oldSize.getHeight()) counter++;
//        }
//
//        return searchResults;
//    }



//    // Find all contact numbers
//    public HashMap<String, SortedSet<String>> getAllContactNumbers() throws InterruptedException {
//        List<WebElement> contactElements = driver.findElements(By.xpath("//android.widget.ListView[@resource-id=\"android:id/list\"]/android.view.ViewGroup"));
//        for (WebElement contactElement : contactElements) {
//            contactElement.click();
//            Thread.sleep(1000);
//            List<WebElement> numberElements = driver.findElements(By.id("com.android.contacts:id/header"));
//            for (WebElement number : numberElements) {
//                String contactNumber = number.getAttribute("text");
//                if (!PhoneNumberValidator.isValidPhoneNumber(contactNumber))
//                    continue;
//                String contactName = driver.findElement(By.id("com.android.contacts:id/large_title")).getText();
//                if (allContactNumbers.containsKey(contactName)) {
//                    allContactNumbers.get(contactName).add(contactNumber.replaceAll("\\s+", ""));
//                } else {
//                    allContactNumbers.computeIfAbsent(contactName, v -> new TreeSet<>()).add(contactNumber.replaceAll("\\s+", ""));
//                }
//            }
//            driver.navigate().back();
//        }
//
//        return allContactNumbers;
//    }

//    public HashMap<String, SortedSet<String>> getAllContactNumbers() throws InterruptedException {
//        HashMap<String, SortedSet<String>> allContactNumbers = new HashMap<>();
//
//        By containerLocator = By.id("com.android.contacts:id/content_frame");
//        Dimension size = driver.manage().window().getSize();
//        Dimension oldSize = size;
//
//        int startY = (int) (size.height * 0.8);
//        int endY = (int) (size.height * 0.2);
//        boolean scrolled = true;
//        int counter = 0;
//        WebElement list = driver.findElement(By.id("android:id/list"));
//
//        while (scrolled) {
//            List<WebElement> contactElements = list.findElements(By.xpath("//android.widget.ListView[@resource-id=\"android:id/list\"]/android.view.ViewGroup"));
//            for (WebElement contactElement : contactElements) {
//                contactElement.click();
//                Thread.sleep(1000);
//                List<WebElement> numberElements = driver.findElements(By.id("com.android.contacts:id/header"));
//                for (WebElement number : numberElements) {
//                    String contactNumber = number.getAttribute("text");
//                    if (!PhoneNumberValidator.isValidPhoneNumber(contactNumber))
//                        continue;
//                    String contactName = driver.findElement(By.id("com.android.contacts:id/large_title")).getText();
//                    allContactNumbers.computeIfAbsent(contactName, v -> new TreeSet<>()).add(contactNumber.replaceAll("\\s+", ""));
//                }
//                driver.navigate().back();
//                Thread.sleep(1000);
//            }
//
//            new Actions(driver)
//                    .moveToElement(driver.findElement(containerLocator))
//                    .clickAndHold()
//                    .moveByOffset(0, endY - startY)
//                    .release()
//                    .perform();
//
//            // Wait a bit for the content to load
//            Thread.sleep(100);
//
//            // Check if the container is still scrollable
//            Dimension newSize = ContactInteractions.getNewSize(driver, containerLocator);
//            oldSize = newSize;
//            if (counter == 1) scrolled = false;
//            if (newSize.getHeight() == oldSize.getHeight()) counter++;
//        }
//
//        return allContactNumbers;
//    }



    public List<String> searchContactByPhoneNumber(String phoneNumber) throws InterruptedException {
        this.searchContactByQuery(phoneNumber);

        List<String> searchResults = new ArrayList<>();
        By containerLocator = By.id("com.android.contacts:id/content_frame");

        performScrolling(containerLocator, () -> {
            List<WebElement> contactElements = driver.findElements(By.xpath("//android.widget.ListView[@resource-id=\"android:id/list\"]/android.view.ViewGroup"));
            for (WebElement contactElement : contactElements) {
                String contactNumber = null;
                try {
                    contactNumber = contactElement.findElement(By.xpath("//android.widget.TextView[@content-desc][2]")).getText();
                } catch (Exception e) {
                    contactNumber = contactElement.findElement(By.xpath("//android.widget.TextView[@content-desc][1]")).getText();
                }
                searchResults.add(contactNumber.replaceAll("\\s+", ""));
            }
        });

        return searchResults;
    }

    public List<String> searchContactByNameOrEmail(String searchQuery) throws InterruptedException {
        this.searchContactByQuery(searchQuery);

        List<String> searchResults = new ArrayList<>();
        By containerLocator = By.id("com.android.contacts:id/content_frame");

        performScrolling(containerLocator, () -> {
            List<WebElement> contactElements = driver.findElements(By.xpath("//android.widget.TextView[@content-desc]"));
            for (WebElement contactElement : contactElements) {
                String name = contactElement.getAttribute("content-desc");
                searchResults.add(name);
            }
        });

        return searchResults;
    }

    public HashMap<String, SortedSet<String>> getAllContactNumbers() throws InterruptedException {
        HashMap<String, SortedSet<String>> allContactNumbers = new HashMap<>();
        By containerLocator = By.id("com.android.contacts:id/content_frame");

        performScrolling(containerLocator, () -> {
            List<WebElement> contactElements = driver.findElements(By.xpath("//android.widget.ListView[@resource-id=\"android:id/list\"]/android.view.ViewGroup"));
            for (WebElement contactElement : contactElements) {
                contactElement.click();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                List<WebElement> numberElements = driver.findElements(By.id("com.android.contacts:id/header"));
                for (WebElement number : numberElements) {
                    String contactNumber = number.getAttribute("text");
                    if (!PhoneNumberValidator.isValidPhoneNumber(contactNumber))
                        continue;
                    String contactName = driver.findElement(By.id("com.android.contacts:id/large_title")).getText();
                    allContactNumbers.computeIfAbsent(contactName, v -> new TreeSet<>()).add(contactNumber.replaceAll("\\s+", ""));
                }
                driver.navigate().back();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return allContactNumbers;
    }

    private void performScrolling(By containerLocator, Runnable action) throws InterruptedException {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);
        boolean scrolled = true;
        int counter = 0;

        while (scrolled) {
            action.run();

            new Actions(driver)
                    .moveToElement(driver.findElement(containerLocator))
                    .clickAndHold()
                    .moveByOffset(0, endY - startY)
                    .release()
                    .perform();

            // Wait a bit for the content to load
            Thread.sleep(100);

            // Check if the container is still scrollable
            Dimension newSize = getNewSize(driver, containerLocator);
            if (++counter == 2 || newSize.getHeight() == size.getHeight()) {
                scrolled = false;
            }
        }
    }
}


