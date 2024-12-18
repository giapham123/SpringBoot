package com.example.demo.Facebook.commonFunc;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class SwitchPage {

    public boolean switchPage(WebDriver driver, String pageName) {
        try{
            WebElement chooseProfile = driver.findElement(By.xpath("//div[@aria-label='Your profile']"));
            chooseProfile.click();
            Thread.sleep(2000);
            WebElement ClickSeeAllPage = driver.findElement(By.xpath("//div[@aria-label='See all profiles']"));
            ClickSeeAllPage.click();
            Thread.sleep(3000);
            WebElement spanElement = driver.findElement(By.xpath("//span[text()='"+pageName+"']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", spanElement);
            Thread.sleep(10000);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
