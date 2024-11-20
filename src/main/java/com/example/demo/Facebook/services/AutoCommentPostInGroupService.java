package com.example.demo.Facebook.services;

import com.example.demo.Facebook.models.AutoCommentPostGroup;
import com.example.demo.Facebook.models.AutoPostGroup;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AutoCommentPostInGroupService {
    public ResponseEntity<String> autoCommentPost(AutoCommentPostGroup autoPostGroup) throws InterruptedException {
        if(autoPostGroup.getTypeComp().toUpperCase().equals("MAC")){
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
        cookies.add(new Cookie("fr", "1K0gQHZbuaUpoSS03.AWV5LR_JDEgmfee-hSvtEMAEXEw.BnPZyS..AAA.0.0.BnPe1j.AWXlZtq1FII"));//3
        cookies.add(new Cookie("xs", "14%3APDjZjuM9Bp0zjw%3A2%3A1732111650%3A-1%3A-1"));

        // Add necessary cookies here
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }

        // Navigate to the post page after adding cookies
        driver.navigate().to("https://facebook.com/groups/"+ autoPostGroup.getGroupId());
//        WebElement postBox = driver.findElement(By.xpath("//div[@type='password']"));
//        postBox.sendKeys("27121994qweA!");
//        Thread.sleep(5000); // Wait for the next set of groups to load
        for(int i =0; i<10; i++){
            // Tạo đối tượng JavascriptExecutor
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Cuộn xuống cuối trang
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            // Chờ một lúc để kiểm tra
            Thread.sleep(2000);

            System.out.println("Đã cuộn xuống cuối trang.");
        }
        // Lấy tất cả các thẻ <a>
        // Lấy tất cả các thẻ <a>
        List<WebElement> links = driver.findElements(By.tagName("a"));
        Set<String> uniqueNumbers = new HashSet<>();

        // In ra các URL hợp lệ
        for (WebElement link : links) {
            String url = link.getAttribute("href");
            if (url != null && !url.isEmpty() && url.contains("pcb")) {
                // Define a pattern to match numbers after 'pcb.'
                Pattern pattern = Pattern.compile("pcb.(\\d+)");

                // Loop through each link and extract the number after 'pcb.'
                Matcher matcher = pattern.matcher(url);
                if (matcher.find()) {
                    uniqueNumbers.add(matcher.group(1)); // Add number to the Set
                }
            }
        }
        for (String data : uniqueNumbers) {
            driver.navigate().to("https://facebook.com/groups/"+ autoPostGroup.getGroupId()+"/posts/"+data);
            Thread.sleep(5000);
            try{
                //
                WebElement postBox = driver.findElement(By.xpath("//div[@aria-label='Answer as Ốp Lưng Điện Thoại Iphone - Samsung']"));
                postBox.click();
                postBox.sendKeys("Ib" + Keys.ENTER);
                Thread.sleep(5000); // Wait for the next set of groups to load

            }catch (Exception e){
                WebElement postBox = driver.findElement(By.xpath("//div[@aria-label='Comment as Ốp Lưng Điện Thoại Iphone - Samsung']"));
                postBox.click();
                postBox.sendKeys("Ib" + Keys.ENTER);
                Thread.sleep(5000); // Wait for the next set of groups to load
            }

        }
        return ResponseEntity.ok("dá");
    }
}