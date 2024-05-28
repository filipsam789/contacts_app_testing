package org.example.bots;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactsSortingBot extends BaseBot{
    private final AppiumDriver driver;
    public ContactsSortingBot(AppiumDriver driver) {
        this.driver = driver;
    }
    public List<String> getStringContactsWithScrolling() {
        By containerLocator = By.id("com.android.contacts:id/content_frame");
        Set<String> listWithoutFavoritesAndNumbers = new HashSet<>();
        Set<String>  listOfFavoriteNumbers =  new HashSet<>();
        Set<String>   listOfFavoritesWithoutNumbers =  new HashSet<>();
        Set<String>   listWithoutFavoritesJustNumbers = new HashSet<>();
        // Get device screen dimensions
        Dimension size = driver.manage().window().getSize();
        Dimension oldSize = size;
        // Initialize start and end coordinates for the swipe action
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);
        boolean scrolled = true;
        int counter = 0;
        boolean favoritesFinished = false;
        WebElement list = driver.findElement(By.id("android:id/list"));
        while (scrolled) {
            List<WebElement> listOfCurrentContacts = list.findElements(By.className("android.view.ViewGroup"));
            List<WebElement> tmpListWithoutFavorites
                    = new ArrayList<>(List.copyOf(listOfCurrentContacts));
            Set<String> tmpListOfFavoriteNumbers = new HashSet<>();
            Set<String> tmpListOfFavoritesWithoutNumbers =  new HashSet<>();
            for (WebElement contact : listOfCurrentContacts){
                if(contact.findElements(By.className("android.widget.TextView")).size()>1)
                {
                    favoritesFinished = true;
                }
                if (favoritesFinished) break;
                if(contact.findElement(By.id("com.android.contacts:id/cliv_name_textview"))
                        .getAttribute("text").charAt(0) >= '0'
                        && contact.findElement(By.id("com.android.contacts:id/cliv_name_textview"))
                        .getAttribute("text").charAt(0) <= '9')
                    tmpListOfFavoriteNumbers.add(contact.findElement(By.id("com.android.contacts:id/cliv_name_textview"))
                            .getAttribute("text"));
                else tmpListOfFavoritesWithoutNumbers.add(contact.findElement(By.id("com.android.contacts:id/cliv_name_textview"))
                        .getAttribute("text"));
                tmpListWithoutFavorites.remove(contact);
            }
            Set<String> tmpListWithoutFavoritesAndNumbers = new HashSet<>();
            Set<String> tmpListWithoutFavoritesJustNumbers = new HashSet<>();
            for (WebElement contact : tmpListWithoutFavorites){
                if(contact.findElement(By.id("com.android.contacts:id/cliv_name_textview"))
                        .getAttribute("text").charAt(0) >= '0'
                        && contact.findElement(By.id("com.android.contacts:id/cliv_name_textview"))
                        .getAttribute("text").charAt(0) <= '9')
                    tmpListWithoutFavoritesJustNumbers.add(contact.findElement(By.id("com.android.contacts:id/cliv_name_textview"))
                            .getAttribute("text"));
                else tmpListWithoutFavoritesAndNumbers.add(contact.findElement(By.id("com.android.contacts:id/cliv_name_textview"))
                        .getAttribute("text"));
            }
            listWithoutFavoritesAndNumbers.addAll(tmpListWithoutFavoritesAndNumbers);
            listOfFavoriteNumbers.addAll(tmpListOfFavoriteNumbers);
            listOfFavoritesWithoutNumbers.addAll(tmpListOfFavoritesWithoutNumbers);
            listWithoutFavoritesJustNumbers.addAll(tmpListWithoutFavoritesJustNumbers);
            // Perform swipe action from startY to endY
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
            Dimension newSize = getNewSize(driver, containerLocator);
            oldSize = newSize;
            if(counter==1)scrolled=false;
            if(newSize.getHeight()==oldSize.getHeight())counter++;
        }
        List<String> realListOfFavoritesWithoutNumbers=listOfFavoritesWithoutNumbers.stream().sorted().toList();
        List<String> realListOfFavoriteNumbers=listOfFavoriteNumbers.stream().sorted().toList();
        List<String> realListWithoutFavoritesAndNumbers=listWithoutFavoritesAndNumbers.stream().sorted().toList();
        List<String> realListWithoutFavoritesJustNumbers=listWithoutFavoritesJustNumbers.stream().sorted().toList();
        List<String> sortedList = new ArrayList<>();
        sortedList.addAll(realListOfFavoritesWithoutNumbers);
        sortedList.addAll(realListOfFavoriteNumbers);
        sortedList.addAll(realListWithoutFavoritesAndNumbers);
        sortedList.addAll(realListWithoutFavoritesJustNumbers);
        return sortedList;
    }

    public Dimension getNewSize(AppiumDriver driver, By containerLocator) {
        WebElement container = driver.findElement(containerLocator);
        return container.getSize();
    }
}
