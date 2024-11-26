package com.example.demo.Facebook.commonFunc;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConfigCommonFuncForRobotChooseFile {

    public WebDriver loginByCookie(String compType){
        //Set up for run real chrome and using cookie session in chrome
        if (compType.toUpperCase().equals("MAC")) {
            System.setProperty("webdriver.chrome.driver", "/Users/giapham/Documents/chromedriver-mac-x64/chromedriver");
        } else {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Downloads\\chromedriver-win64\\chromedriver.exe");
        }
        String userProfilePath = "C:\\Users\\admin\\AppData\\Local\\Google\\Chrome\\User Data";  // Windows path
        System.setProperty("java.awt.headless","false"); //Set up to run ROBOT CHOOSE FILE
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
        options.addArguments("user-data-dir=" + userProfilePath);
        options.addArguments("profile-directory=Default");
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.facebook.com");
        return driver;
        //End set up
    }

    public void scrollTopToEndPage(int scrollNumbers, WebDriver driver) throws InterruptedException {
        for(int i =0; i<scrollNumbers; i++){
            // Tạo đối tượng JavascriptExecutor
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // Cuộn xuống cuối trang
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            // Chờ một lúc để kiểm tra
            Thread.sleep(2000);
//            System.out.println("Đã cuộn xuống cuối trang.");
        }
    }
}
