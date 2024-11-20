package com.example.demo.Facebook.services;

import com.example.demo.Facebook.models.AutoCommentPostGroup;
import com.example.demo.Facebook.models.SharePostPageModel;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class AutoCommentPostInGroupService {
    public ResponseEntity<String> autoCommentPost(AutoCommentPostGroup autoCommentPostGroup) throws InterruptedException {
        if(autoCommentPostGroup.getTypeComp().toUpperCase().equals("MAC")){
            System.setProperty("webdriver.chrome.driver", "/Users/giapham/Documents/chromedriver-mac-x64/chromedriver");
        }else{
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Downloads\\chromedriver-win64\\chromedriver.exe");
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.setExperimentalOption("detach", false);
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Add cookies for login
        driver.get("https://www.facebook.com");
        List<Cookie> cookies = new ArrayList<>();

        cookies.add(new Cookie("c_user", "61568239606429"));//1
        cookies.add(new Cookie("datr", "mwg0Z9THOO1-yWEpQbBW07Sx"));//2
        cookies.add(new Cookie("i_user", "100066835222220"));
        cookies.add(new Cookie("locale", "en_US"));
        cookies.add(new Cookie("ps_l", "1"));
        cookies.add(new Cookie("ps_n", "1"));
        cookies.add(new Cookie("sb", "mwg0Z16z_I75ZUIAXFwsTihu"));//4
        cookies.add(new Cookie("wd", "872x75"));
        cookies.add(new Cookie("fr", "11iZMNBDaCFFhGkbk.AWVzh9MCkJ2tlqbhvEhgFumKjE0.BnPWQZ..AAA.0.0.BnPZG3.AWXdi5ZHMLk"));//3
        cookies.add(new Cookie("xs", "11%3AWe5fJeHEAhBxyg%3A2%3A1731463337%3A-1%3A-1%3A%3AAcXGLjzU8I-4z_bOtDFNlTvRBaSJadBy_8UAZCUabTk"));

        // Add necessary cookies here
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }

        // Navigate to the post page after adding cookies
        driver.navigate().to("https://facebook.com/groups/"+autoCommentPostGroup.getGroupId());

        WebElement clickShare = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'Write something...')]")));
        clickShare.click();
        Thread.sleep(2000); // Đợi hộp mở ra
        try {
            //không thể import image vì phải luu image trước nên khong the sd hàm duưới
//            WebElement clickShare1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label='Photo/video']")));
//            clickShare1.click();
//            Thread.sleep(5000); // Chờ ảnh tải lên
//            try {
//                WebElement uploadInput = driver.findElement(By.xpath("//input[@type='file']"));
//                uploadInput.sendKeys("/Users/giapham/Downloads/bbb.jpeg");
//                Thread.sleep(5000); // Chờ ảnh tải lên
//            }catch (Exception e){
//                WebElement clickShare1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label='Photo/video']")));
//                clickShare1.click();
//                Thread.sleep(5000); // Chờ ảnh tải lên
//
//                WebElement uploadInput = driver.findElement(By.xpath("//input[@type='file']"));
//                uploadInput.sendKeys("/Users/giapham/Downloads/bbb.jpeg");
//                Thread.sleep(5000); // Chờ ảnh tải lên
//                System.out.println("cccccc"+e);
//            }
            //Input Content
            WebElement postBox = driver.findElement(By.xpath("//div[@aria-label='Create a public post…']"));
            postBox.sendKeys(autoCommentPostGroup.getContent());
            Thread.sleep(5000); // Wait for the next set of groups to load

            //Click Post Button
//            WebElement clickPost = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label='Post']")));
//            clickPost.click();
//            Thread.sleep(5000); // Chờ ảnh tải lên
        } catch(Exception v){
            Thread.sleep(5000); // Wait for the next set of groups to load
            System.out.println(v);
        } finally {
            // Đóng trình duyệt
//            driver.quit();
        }
        return ResponseEntity.ok("Post to group success.");
    }
}