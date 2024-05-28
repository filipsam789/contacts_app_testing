
package org.example.tests;

import io.appium.java_client.AppiumDriver;
import org.example.bots.ContactCreateBot;
import org.example.bots.ContactDeleteBot;
import org.example.interactions.ContactInteractions;
import org.example.utils.DriverUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testng.Assert;


import java.util.List;
import java.util.Random;
import java.util.Scanner;

@ExtendWith(AppiumReporter.class)
public class ContactsCreateRobotTest {

    private static ContactCreateBot contactCreateBot;
    private static ContactInteractions contactInteractions;
//    private static AppiumDriver driver;
//
//    @BeforeEach
//    public void init() {
//        DriverUtils.initDriver();
//        driver = DriverUtils.getDriver();
//        contactCreateBot = new ContactCreateBot(driver);
//        contactInteractions = new ContactInteractions(driver);
//        System.out.println("driver setup");
//    }

    //TEST 1: Giving First Name, Phone Number and Street Address parameters for the creation of a new Contact. This contact shall be referenced by its First Name.
    @Test
    public void createContactWithFirstNameAndPhoneNumber(AppiumDriver driver) throws InterruptedException {
        contactCreateBot = new ContactCreateBot(driver);
        contactInteractions = new ContactInteractions(driver);
        String contactName = "Gjorgji";
        String contactSurname ="";
        String contactPhoneNumber= "075744000";
        String contactEmail= "";
        String contactAddress = "Ulica Belasica - 4";
        String contactNameSaved = contactCreateBot.createContact(contactName,contactSurname,contactPhoneNumber,contactEmail,contactAddress);
        Assert.assertEquals(contactName+ " saved", contactNameSaved);

    }

    //TEST 2: Giving First Name, Last Name, Phone Number and Street Address parameters for the creation of a new Contact. This contact shall be referenced by its First Name combined with its Last Name.
    @Test
    public void createContactWithFirstNameLastNameAndPhoneNumber(AppiumDriver driver) throws InterruptedException {
        contactCreateBot = new ContactCreateBot(driver);
        contactInteractions = new ContactInteractions(driver);
        String contactName = "Gjorgji";
        String contactSurname = "Koceski";
        String contactPhoneNumber= "075744000";
        String contactEmail= "";
        String contactAddress = "Ulica Belasica - 4";
        String contactNameSaved = contactCreateBot.createContact(contactName,contactSurname,contactPhoneNumber,contactEmail,contactAddress);
        Assert.assertEquals(contactName+" "+contactSurname+ " saved", contactNameSaved);

    }

    //TEST 3: Giving Phone Number as a parameter for the creation of a new Contact. This contact shall be referenced by its Phone Number.
    @Test
    public void createContactWithPhoneNumber(AppiumDriver driver) throws InterruptedException {
        contactCreateBot = new ContactCreateBot(driver);
        contactInteractions = new ContactInteractions(driver);
        String contactName = "";
        String contactSurname = "";
        String contactPhoneNumber= "075744000";
        String contactEmail= "";
        String contactAddress = "Ulica Belasica - 4";
        String contactNameSaved = contactCreateBot.createContact(contactName,contactSurname,contactPhoneNumber,contactEmail,contactAddress);
        Assert.assertEquals(contactPhoneNumber+ " saved", contactNameSaved);

    }

    //TEST 4: Giving Email and Street Address parameters for the creation of a new Contact. This contact shall be referenced by its Email Address.
    @Test
    public void createContactWithEmailAddress(AppiumDriver driver) throws InterruptedException {
        contactCreateBot = new ContactCreateBot(driver);
        contactInteractions = new ContactInteractions(driver);
        String contactName = "";
        String contactSurname = "";
        String contactPhoneNumber= "";
        String contactEmail = "koceskigj@yahoo.com";
        String contactAddress = "Ulica Belasica - 4";
        String contactNameSaved = contactCreateBot.createContact(contactName,contactSurname,contactPhoneNumber,contactEmail,contactAddress);
        Assert.assertEquals(contactEmail+ " saved", contactNameSaved);

    }

    //TEST 5: Giving Street Address parameter for the creation of a new Contact. This contact shall be referenced simply as Contact, and shall be presented as (No name) in the list of contacts.
    @Test
    public void createContactWithStreetAddress(AppiumDriver driver) throws InterruptedException {
        contactCreateBot = new ContactCreateBot(driver);
        contactInteractions = new ContactInteractions(driver);
        String contactName = "";
        String contactSurname = "";
        String contactPhoneNumber= "";
        String contactEmail = "";
        String contactAddress = "Planet Earth - 1605";
        String contactNameSaved = contactCreateBot.createContact(contactName,contactSurname,contactPhoneNumber,contactEmail,contactAddress);
        Assert.assertEquals("Contact saved", contactNameSaved);

    }


//    @AfterEach
//    public void destroy() {
//        DriverUtils.stopDriver();
//    }
}
