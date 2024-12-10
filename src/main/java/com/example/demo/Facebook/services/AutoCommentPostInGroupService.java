package com.example.demo.Facebook.services;

import com.example.demo.Facebook.commonFunc.ConfigCommonFunc;
import com.example.demo.Facebook.commonFunc.ConfigCommonFuncFirefox;
import com.example.demo.Facebook.models.AutoCommentPostGroup;
import com.example.demo.Facebook.models.AutoPostGroup;
import com.example.demo.common.GenericResponse;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ConfigCommonFuncFirefox configCommonFunc;

    public GenericResponse autoCommentPost(AutoCommentPostGroup autoPostGroup) throws InterruptedException {
        GenericResponse rs = new GenericResponse();
        WebDriver driver = configCommonFunc.loginByCookie(autoPostGroup.getPageId());
        // Navigate to the post page after adding cookies
        driver.navigate().to("https://facebook.com/groups/"+ autoPostGroup.getGroupId());
        for(int i =0; i<1; i++){
            // Tạo đối tượng JavascriptExecutor
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Cuộn xuống cuối trang
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            // Chờ một lúc để kiểm tra
            Thread.sleep(2000);
        }
        // Lấy tất cả các thẻ <a>
        List<WebElement> links = driver.findElements(By.tagName("a"));
        Set<String> uniqueNumbers = new HashSet<>();
        Set<String> listLinkComment = new HashSet<>();

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
            listLinkComment.add("https://facebook.com/groups/"+ autoPostGroup.getGroupId()+"/posts/"+data);
            Thread.sleep(2000);
            try{
                //
//                WebElement postBox = driver.findElement(By.xpath("//div[@aria-label='Comment as Ốp Lưng Điện Thoại Iphone - Samsung']"));
                WebElement postBox = driver.findElement(By.xpath("//div[contains(@aria-label, 'Comment as')]"));
                postBox.click();
                String content = autoPostGroup.getContent();
                for (char c : content.toCharArray()) {
                    postBox.sendKeys(String.valueOf(c));
                    Thread.sleep(100); // Small delay between keystrokes
                }
                postBox.sendKeys(Keys.ENTER);
                Thread.sleep(5000); // Small delay between keystrokes
//                postBox.sendKeys(autoPostGroup.getContent() );
//                Thread.sleep(3000); // Wait for the next set of groups to load
            }catch (Exception e){
                try {
//                    WebElement postBox = driver.findElement(By.xpath("//div[@aria-label='Comment as Ốp Lưng Điện Thoại Iphone - Samsung']"));
                    WebElement postBox = driver.findElement(By.xpath("//div[contains(@aria-label, 'Answer as')]"));
                    postBox.click();
                    String content = autoPostGroup.getContent();
                    for (char c : content.toCharArray()) {
                        postBox.sendKeys(String.valueOf(c));
                        Thread.sleep(100); // Small delay between keystrokes
                    }
                    postBox.sendKeys(Keys.ENTER);
                    Thread.sleep(5000); // Small delay between keystrokes
//                    postBox.sendKeys(autoPostGroup.getContent() );
//                    Thread.sleep(3000); // Wait for the next set of groups to load
                }catch (Exception ex){
                    System.out.println(ex);
                }
            }

        }
        rs.setData(listLinkComment);
        rs.setMessage("Comment for posts in group success. Total posts for comment:" + uniqueNumbers.size());
        return rs;
    }
}