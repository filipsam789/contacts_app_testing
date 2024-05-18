package org.example.tests;

import io.appium.java_client.AppiumDriver;
import org.example.bots.ContactSearchBot;
import org.example.utils.DriverUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;

public class ContactsSearchRobotTest {
    private static ContactSearchBot contactSearchBot;
    private static AppiumDriver driver;
    private static List<String> allContacts;
    private static HashMap<String, SortedSet<String>> allContactNumbers;

    @BeforeAll
    public static void setup() {
        DriverUtils.initDriver();
        driver = DriverUtils.getDriver();
        contactSearchBot = new ContactSearchBot(driver);
        allContacts = contactSearchBot.getAllContacts();
        allContactNumbers = contactSearchBot.getAllContactNumbers();
    }

    @BeforeEach
    public void init() {
        DriverUtils.initDriver();
        driver = DriverUtils.getDriver();
        contactSearchBot = new ContactSearchBot(driver);
        System.out.println("driver setup");
    }

    @ParameterizedTest
    @ValueSource(strings = {"An", "gmail", "John"})
    public void searchContactByNameOrEmailTest(String searchQuery) {

        List<String> searchResults = contactSearchBot.searchContactByNameOrEmail(searchQuery);
        System.out.println(searchResults);

        for (String contact : allContacts) {
            if (contact.toLowerCase().contains(searchQuery.toLowerCase())) {
                Assert.assertTrue(searchResults.contains(contact));
            } else assert !searchResults.contains(searchQuery.toLowerCase());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"078", "07", "0", "+389"})
    public void searchContactByPhoneNumberTest(String phoneNumber) {

        System.out.println("all numbers");
        System.out.println(allContactNumbers);
        List<String> searchResults = contactSearchBot.searchContactByPhoneNumber(phoneNumber);
        System.out.println(searchResults);

        for (SortedSet<String> values : allContactNumbers.values()) {
            for (String contact : values) {
                if (contact.toLowerCase().startsWith(phoneNumber.toLowerCase())) {
                    Assert.assertTrue(searchResults.contains(contact));
                    break;
                } else {
                    Assert.assertFalse(searchResults.contains(phoneNumber.toLowerCase()));
                }
            }
        }
    }

    @AfterEach
    public void destroy() {
        DriverUtils.stopDriver();
    }

}
