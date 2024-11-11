package com.example.demo.controllers;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.interactions.Actions;
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
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.chrome.ChromeOptions;

@RestController
@RequestMapping("/api/basic1")
public class BasicAuthController_bk {

    @GetMapping("/test")
    public ResponseEntity<String> secureEndpointBasic() {
        System.setProperty("webdriver.chrome.driver", "/Users/giapham/Documents/chromedriver-mac-x64/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.setExperimentalOption("detach", false);

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Add cookies for login
        driver.get("https://www.facebook.com");
        List<Cookie> cookies = new ArrayList<>();
        cookies.add(new Cookie("b_user", "61560391100126"));
        cookies.add(new Cookie("c_user", "100007769235838"));
        cookies.add(new Cookie("datr", "J5snZ6ojUq88q-5OSI6_yXnF"));
        cookies.add(new Cookie("fr", "1zAFnXnH1dkIlMrru.AWWn3GpKjlG6aru68YVEFyi3qSY.BnMWNI..AAA.0.0.BnMWgi.AWXked_QbgA"));
        cookies.add(new Cookie("i_user", "61551932516721"));
        cookies.add(new Cookie("locale", "en_US"));
        cookies.add(new Cookie("ps_l", "1"));
        cookies.add(new Cookie("ps_n", "1"));
        cookies.add(new Cookie("sb", "J5snZzIet1Ek6EH1ijk2lqLi"));
        cookies.add(new Cookie("wd", "342x754"));
        cookies.add(new Cookie("xs", "48%3AqX5zpi8eTXCxug%3A2%3A1731291093%3A-1%3A6191"));

        // Add necessary cookies here
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }

        // Navigate to the post page after adding cookies
        driver.navigate().to("https://www.facebook.com/tuvanphapluat.hp/posts/122205601838064417");
        List<String> groupName = new ArrayList<>();
        groupName.add("LUẬT SƯ VIỆT NAM");
        groupName.add("Luật sư tư vấn miễn phí");
        groupName.add("Luật sư Tư vấn Pháp luật 247");
        groupName.add("Học Luật Đừng Học Đại");
        groupName.add("Cùng Hiểu Luật");
        groupName.add("Việc làm Nghề Luật - Tư vấn Pháp Luật - HLU");
        groupName.add("Hội những người thích Học Luật ☑\uFE0F");
        groupName.add("NGHỀ LUẬT VÀ VIỆC LÀM");
        groupName.add("CỘNG ĐỒNG SINH VIÊN LUẬT");
        groupName.add("SINH VIÊN NGÀNH LUẬT");
        groupName.add("Học Luật");
        groupName.add("Diễn đàn luật sư LOF");
        groupName.add("CHUYỆN NGHỀ LUẬT SƯ");
        groupName.add("Luật sư tư vấn Pháp luật miễn phí");
        groupName.add("TÔI HỌC LUẬT - ULAW");
        try {
            for (String group : groupName) {
                System.out.println("Group Name: " + group);

                WebElement clickShare = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[3]/div/div/div[2]/span/span")));
                clickShare.click();

                WebElement clickGroup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[4]/div/div/div/div/div/i")));
                clickGroup.click();

                WebElement inputSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/div/div/div/div/div/div/label/input")));
                inputSearch.click();
                WebElement inputSearchValue = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/div/div/div/div/div/div/label/input")));
                String targetGroup = group;  // Replace with your target group name
                inputSearchValue.sendKeys(targetGroup);
                Thread.sleep(5000); // Wait for the next set of groups to load

                WebElement clickSelectGroup = wait.until(
                        ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(.,'" + group + "')]"))
                );
                clickSelectGroup.click();
                WebElement clickPost = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[3]/div[3]/div/div/div")));
                clickPost.click();

                // Pause for a few seconds to ensure the post completes
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return ResponseEntity.ok("Basic Auth API accessed!");
    }
}