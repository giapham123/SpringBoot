package com.example.demo.controllers;

import org.openqa.selenium.Cookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/basic")
public class BasicAuthController {

    @GetMapping("/test")
    public ResponseEntity<String> secureEndpointBasic() throws InterruptedException {
        // Set up the ChromeDriver path

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Downloads\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.facebook.com");

        // Example: Add your cookies here
        List<Cookie> cookies = new ArrayList<>();
        cookies.add(new Cookie("b_user", "61560391100126"));
        cookies.add(new Cookie("c_user", "100007769235838"));
        cookies.add(new Cookie("datr", "U75EZr-96XGfZQxL1SfKqCYg"));
        cookies.add(new Cookie("dpr", "1.25"));
        cookies.add(new Cookie("fr", "1ZiBxR0G02hFmGwdf.AWWXEfEt9j2Pec-MCrwA0d25360.BnMEjU..AAA.0.0.BnMEmL.AWXmimiqqrc"));
        cookies.add(new Cookie("m_ls", "%7B%2261554650825233%22%3A%7B%22c%22%3A%7B%221%22%3A%22HCwAABbkAhaC_v2pBhMFFqKIxvH5_hsA%22%2C%222%22%3A%22GRwVQBxMAAAWARao5dztDBYAFqjl3O0MABYoAA%22%2C%2295%22%3A%22HCwAABZkFsauoNwGEwUWoojG8fn-GwA%22%7D%2C%22d%22%3A%2263b4a96e-827f-4fac-a36e-8e648a8d3e59%22%2C%22s%22%3A%220%22%2C%22u%22%3A%227csmoe%22%7D%7D"));
        cookies.add(new Cookie("presence", "C%7B%22t3%22%3A%5B%5D%2C%22utc3%22%3A1731217806024%2C%22v%22%3A1%7D"));
        cookies.add(new Cookie("ps_l", "1"));
        cookies.add(new Cookie("ps_n", "1"));
        cookies.add(new Cookie("sb", "JmIpZG_pK3duwRSUYwZA-Mwa"));
        cookies.add(new Cookie("wd", "711x983"));
        cookies.add(new Cookie("xs", "33%3A7BAMKsuAgazkTA%3A2%3A1717076724%3A-1%3A6191%3ACrRmDO5Ou4vwHA%3AAcXLouWkk9yXcHAI5FBEpGv2lE4XxNdm1WaT3-nlFT6e"));


        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }

        // Navigate to Facebook again after adding cookies
        driver.navigate().to("https://www.facebook.com");
        driver.wait(5000);
        return ResponseEntity.ok("Basic Auth API accessed!");    }
}
