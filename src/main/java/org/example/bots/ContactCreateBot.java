package org.example.bots;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.appium.java_client.AppiumBy.*;

public class ContactCreateBot extends BaseBot{
    private final AppiumDriver driver;

    public ContactCreateBot(AppiumDriver driver) {
        this.driver = driver;
    }

    public String createContact(String first_name, String last_name, String phone_number, String email_address, String street_address) throws InterruptedException {
        WebElement contact = driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Create new contact\"]"));
        contact.click();
        Thread.sleep(1000);

        WebElement googleAccount = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"com.android.contacts:id/left_button\"]"));
        googleAccount.click();
        Thread.sleep(1000);

        WebElement firstName = driver.findElement(By.xpath("//android.widget.EditText[@text=\"First name\"]"));
        firstName.sendKeys(first_name);
        Thread.sleep(1000);

        WebElement lastName = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Last name\"]"));
        lastName.sendKeys(last_name);
        Thread.sleep(1000);

        WebElement phone = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Phone\"]"));
        phone.sendKeys(phone_number);
        Thread.sleep(1000);

        WebElement email = driver.findElement(new ByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Email\").instance(0))"));
        email.sendKeys(email_address);
        Thread.sleep(1000);

        WebElement more_fields = driver.findElement(new ByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"More fields\").instance(0))"));
        more_fields.click();
        Thread.sleep(1000);

        WebElement address = driver.findElement(new ByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Address\").instance(0))"));
        address.sendKeys(street_address);
        Thread.sleep(1000);

        WebElement save_button = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"com.android.contacts:id/editor_menu_save_button\"]"));
        save_button.click();
        Thread.sleep(1000);

        if(!last_name.equals("")) last_name = " " + last_name;
        if(first_name.equals("")) {
            first_name = phone_number;
            if(phone_number.equals("")){
                first_name = email_address;
                if(email_address.equals("")){
                    first_name = "Contact";
                }
            }
        }

        WebElement createdName = driver.findElement(By.xpath("//android.widget.Toast[@text=\""+first_name+last_name+" saved\"]"));
        return createdName.getAttribute("text");
    }
}
