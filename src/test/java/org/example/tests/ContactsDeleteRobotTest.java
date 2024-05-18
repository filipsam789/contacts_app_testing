package org.example.tests;

import io.appium.java_client.AppiumDriver;
import org.example.bots.ContactDeleteBot;
import org.example.interactions.ContactInteractions;
import org.example.utils.DriverUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.Assert;


import java.util.List;
import java.util.Random;

public class ContactsDeleteRobotTest {
    private static ContactDeleteBot contactDeleteBot;
    private static ContactInteractions contactInteractions;
    private static AppiumDriver driver;
    private static List<String> allContacts;

    @BeforeAll
    public static void setup() {
        DriverUtils.initDriver();
        driver = DriverUtils.getDriver();
        contactDeleteBot = new ContactDeleteBot(driver);
        contactInteractions = new ContactInteractions(driver, contactDeleteBot);
        allContacts = contactInteractions.getAllContacts();
    }

    @BeforeEach
    public void init() {
        DriverUtils.initDriver();
        driver = DriverUtils.getDriver();
        contactDeleteBot = new ContactDeleteBot(driver);
        contactInteractions = new ContactInteractions(driver, contactDeleteBot);
        System.out.println("driver setup");
    }

    @Test
    public void deleteRandomContact() throws InterruptedException {
        if (allContacts.isEmpty()) {
            System.out.println("No contacts available to delete.");
            return;
        }

        Random random = new Random();
        int allContactsSize = allContacts.size();
        int randomIndex = random.nextInt(allContactsSize);
        String contactName = allContacts.get(randomIndex);
        System.out.println(randomIndex + " " + contactName);
        String contactNameDeleted = contactDeleteBot.deleteContact(randomIndex + 1);
        Assert.assertEquals(contactName + " deleted", contactNameDeleted);
        Thread.sleep(1000);
        allContacts = contactInteractions.getAllContacts();
        Assert.assertEquals(allContactsSize - 1, allContacts.size());
    }

    @Test
    public void deleteFirstContact() throws InterruptedException {
        if (allContacts.isEmpty()) {
            System.out.println("No contacts available to delete.");
            return;
        }

        int allContactsSize = allContacts.size();
        int index = 0;
        String contactName = allContacts.get(index);
        System.out.println(index + " " + contactName);
        String contactNameDeleted = contactDeleteBot.deleteContact(index + 1);
        Assert.assertEquals(contactName + " deleted", contactNameDeleted);
        Thread.sleep(1000);
        allContacts = contactInteractions.getAllContacts();
        Assert.assertEquals(allContactsSize - 1, allContacts.size());
    }

    @AfterEach
    public void destroy() {
        DriverUtils.stopDriver();
    }
}
