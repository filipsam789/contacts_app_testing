package org.example.tests;

import io.appium.java_client.AppiumDriver;
import org.example.bots.ContactSearchBot;
import org.example.interactions.ContactInteractions;
import org.example.utils.DriverUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;

@ExtendWith(AppiumReporter.class)
public class ContactsSearchRobotTest {
    private static ContactSearchBot contactSearchBot;
    private static ContactInteractions contactInteractions;
    private static AppiumDriver driver;
    private static List<String> allContacts;
    private static HashMap<String, SortedSet<String>> allContactNumbers;

    @BeforeAll
    public static void setup() throws InterruptedException {
        DriverUtils.initDriver();
        driver = DriverUtils.getDriver();
        contactSearchBot = new ContactSearchBot(driver);
        contactInteractions = new ContactInteractions(driver);
        allContacts = contactInteractions.getAllContacts();
//        allContactNumbers = contactSearchBot.getAllContactNumbers();
        allContactNumbers = new HashMap<>();
    }

    @BeforeEach
    public void init() {
        DriverUtils.initDriver();
        driver = DriverUtils.getDriver();
        contactSearchBot = new ContactSearchBot(driver);
        contactInteractions = new ContactInteractions(driver);
        System.out.println("driver setup");
    }

    @ParameterizedTest
    @ValueSource(strings = {"An", "gmail", "yahoo", "Test"})
    public void searchContactByNameOrEmailTest(String searchQuery) throws InterruptedException {
        if (allContacts.isEmpty())
            return;

        List<String> searchResults = contactSearchBot.searchContactByNameOrEmail(searchQuery);
        System.out.println(searchResults);

        for (String contact : allContacts) {
            if (contact.toLowerCase().contains(searchQuery.toLowerCase()) && contact.toLowerCase().startsWith(searchQuery.toLowerCase())) {
                Assert.assertTrue(searchResults.contains(contact));
            } else assert !searchResults.contains(searchQuery.toLowerCase());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "078", "07", "+389"})
    public void searchContactByPhoneNumberTest(String phoneNumber) throws InterruptedException {
        if (allContactNumbers.isEmpty())
            allContactNumbers = contactSearchBot.getAllContactNumbers();

        if (allContacts.isEmpty())
            return;

        System.out.println("all numbers");
        System.out.println(allContactNumbers);
        List<String> searchResults = contactSearchBot.searchContactByPhoneNumber(phoneNumber);
        System.out.println("search results");
        System.out.println(searchResults);

        for (SortedSet<String> values : allContactNumbers.values()) {
            for (String contact : values) {
                if (contact.toLowerCase().startsWith(phoneNumber.toLowerCase())) {
                    Assert.assertTrue(searchResults.contains(contact));
                    break;
                } else {
                    Assert.assertFalse(searchResults.contains(contact.toLowerCase()));
                }
            }
        }

    }

    @AfterEach
    public void destroy() {
        DriverUtils.stopDriver();
    }

}
