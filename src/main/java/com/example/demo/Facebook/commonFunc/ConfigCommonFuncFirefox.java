package com.example.demo.Facebook.commonFunc;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class ConfigCommonFuncFirefox {

    public WebDriver loginByCookie(){
        String profilePath = "";
        boolean isMac = System.getProperty("os.name").toLowerCase().contains("mac");
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        if (isMac) {
            //Download geckodriver for run firefox
            System.setProperty("webdriver.gecko.driver", "/Users/giapham/Downloads/geckodriver");
            profilePath = "/Users/giapham/Library/Application Support/Firefox/Profiles/bit46kwp.default-release";
        } else if(isWindows){
            profilePath = "C:\\Users\\Admin\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\iczrdfdi.default-release";
            System.setProperty("webdriver.chrome.driver", "D:\\PROJECT\\geckodriver");
        }
        System.setProperty("java.awt.headless", "false");
        //Go to about:profiles to get path profile

        FirefoxProfile profile = new FirefoxProfile(new java.io.File(profilePath));

        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--no-remote");  // Ensure it's a fresh session

        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("dom.webnotifications.enabled", false);
        options.setProfile(profile);
        if (isMac) {
            options.setBinary("/Applications/Firefox.app/Contents/MacOS/firefox");
        } else if(isWindows){
            options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        }

        options.setBinary(firefoxBinary);

        WebDriver driver = new FirefoxDriver(options);
            driver.get("https://www.fb.com");

        return driver;
    }

    public void scrollTopToEndPage(int scrollNumbers, WebDriver driver) throws InterruptedException {
        for(int i =0; i<scrollNumbers; i++){
            // Tạo đối tượng JavascriptExecutor
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // Cuộn xuống cuối trang
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            // Chờ một lúc để kiểm tra
            Thread.sleep(2000);
        }
    }
}

