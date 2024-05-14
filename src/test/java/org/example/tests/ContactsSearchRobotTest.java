package org.example.tests;

import io.appium.java_client.AppiumDriver;
import org.example.bots.ContactSearchBot;
import org.example.utils.DriverUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testng.Assert;

import java.util.List;

public class ContactsSearchRobotTest {
    private ContactSearchBot contactSearchBot;
    private AppiumDriver driver;

    @BeforeEach
    public void init() {
        DriverUtils.initDriver();
        driver = DriverUtils.getDriver();
        contactSearchBot = new ContactSearchBot(driver);
        System.out.println("driver setup");
    }

    @ParameterizedTest
    @ValueSource(strings = {"An", "John", "Mary"})
    public void searchContactByNameTest(String searchQuery) {
        List<String> allContacts = contactSearchBot.getAllContacts();

        List<String> searchResults = contactSearchBot.searchContactByName(searchQuery);
        System.out.println(searchResults);

        for (String contact : allContacts) {
            if (contact.toLowerCase().contains(searchQuery.toLowerCase())) {
                Assert.assertTrue(searchResults.contains(contact), "Contact '" + contact + "' not found in search results.");
            }
        }
    }

    @AfterEach
    public void destroy() {
        DriverUtils.stopDriver();
    }

}
