package org.example.interactions;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;


import java.util.*;

public class ContactInteractions {
    private final AppiumDriver driver;

    public ContactInteractions(AppiumDriver driver) {
        this.driver = driver;
    }

    public static Dimension getNewSize(AppiumDriver driver, By containerLocator) {
        WebElement container = driver.findElement(containerLocator);
        return container.getSize();
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

//    //Find all contacts
//    public List<String> getAllContacts() {
//        List<String> allContacts = new ArrayList<>();
//
//        List<WebElement> listOfContacts = driver.findElements(By.id("android:id/list"));
//        if (listOfContacts.isEmpty()) {
//            return Collections.emptyList();
//        }
//        List<WebElement> contactElements = driver.findElements(By.id("com.android.contacts:id/cliv_name_textview"));
//        for (WebElement contactElement : contactElements) {
//            String contactName = contactElement.getAttribute("content-desc");
//            allContacts.add(contactName);
//        }
//        return new ArrayList<>(allContacts);
//    }

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

    public List<String> getAllContacts() {
        By containerLocator = By.id("com.android.contacts:id/content_frame");
        Set<String> contactNames = new LinkedHashSet<>();

        // Get device screen dimensions
        Dimension size = driver.manage().window().getSize();
        Dimension oldSize = size;

        // Initialize start and end coordinates for the swipe action
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);
        boolean scrolled = true;
        int counter = 0;
        WebElement list = driver.findElement(By.id("android:id/list"));

        while (scrolled) {
            List<WebElement> tempElements = list.findElements(By.className("android.view.ViewGroup"));
            for (WebElement element : tempElements) {
                String contactName = element.findElement(By.id("com.android.contacts:id/cliv_name_textview")).getAttribute("text");
                contactNames.add(contactName);
            }

            new Actions(driver)
                    .moveToElement(driver.findElement(containerLocator))
                    .clickAndHold()
                    .moveByOffset(0, endY - startY)
                    .release()
                    .perform();

            // Wait a bit for the content to load
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Check if the container is still scrollable
            Dimension newSize = ContactInteractions.getNewSize(driver, containerLocator);
            oldSize = newSize;
            if (counter == 1) scrolled = false;
            if (newSize.getHeight() == oldSize.getHeight()) counter++;
        }

        return contactNames.stream().toList();
    }


}