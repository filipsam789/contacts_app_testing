package org.example.interactions;

import io.appium.java_client.AppiumDriver;
import org.example.bots.ContactSearchBot;

public class ContactInteractions {
    private final AppiumDriver driver;
    private final ContactSearchBot contactSearchBot;

    public ContactInteractions(AppiumDriver driver, ContactSearchBot contactSearchBot) {
        this.driver = driver;
        this.contactSearchBot = contactSearchBot;
    }

    public void searchContactByName(String query) {
        contactSearchBot.searchContactByName(query);
    }

}