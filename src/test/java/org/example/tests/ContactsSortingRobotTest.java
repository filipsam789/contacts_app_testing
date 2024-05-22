package org.example.tests;

import io.appium.java_client.AppiumDriver;
import org.example.bots.ContactsSortingBot;
import org.example.interactions.ContactInteractions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testng.Assert;

import java.util.List;

@ExtendWith(AppiumReporter.class)
public class ContactsSortingRobotTest {

    //This method and class serve the purpose for finding out whether contacts are correctly sorted by their last name.
    //We first need to get the contacts with their last names, but there isn't a clear separation for the first and last names.
    //That is because a contact could have two(or more) first names or two(or more) last names and there would be no indication where
    //the first name ends and the last name starts. That's why we change the setting which displays the contacts by last name first, and
    //which also separates the last name from the first using a comma. We use the extracted contacts from before and after to compare
    //them, i.e. to check if they are ordered correctly by their last name.
    //This contacts app puts the favorite contacts on the same page as the regular ones and the favorites appear between the regular ones too,
    //i.e. they are duplicated. There isn't an element which separates the two subsections, forcing us to find a tricky solution in order to
    //separate them. Another problem was that the app sorts the contacts that start with a number at the top, while java puts them at the end.
    //We need to force the threads to sleep in order not to potentially get the StaleElementReferenceException.
    //Appium inspector was used for finding the ids, classes or xpaths of the elements on screen.
    @Test
    public void executeSortingTest(AppiumDriver driver) throws InterruptedException {
        ContactsSortingBot contactsSortingBot = new ContactsSortingBot(driver);
        ContactInteractions contactInteractions = new ContactInteractions(driver);
        contactInteractions.setDisplayByLastName();
        List<String> sortedList1 = contactsSortingBot.getStringContactsWithScrolling();
        contactInteractions.setSortByLastName();
        List<String> sortedList2 = contactsSortingBot.getStringContactsWithScrolling();
        Assert.assertEquals(sortedList1, sortedList2);
    }

}
