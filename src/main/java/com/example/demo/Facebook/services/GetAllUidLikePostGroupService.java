package com.example.demo.Facebook.services;

import com.example.demo.Facebook.commonFunc.ConfigCommonFunc;
import com.example.demo.Facebook.commonFunc.ConfigCommonFuncFirefox;
import com.example.demo.Facebook.models.AddFriendViaUIdModel;
import com.example.demo.Facebook.models.GetAllUidLikePostModel;
import com.example.demo.common.GenericResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GetAllUidLikePostGroupService {
    @Autowired
    ConfigCommonFunc configCommonFunc;

    @Autowired
    ConfigCommonFuncFirefox configCommonFuncFirefox;

    public GenericResponse getAllUidLikePost(GetAllUidLikePostModel getAllUidLikePostModel) throws InterruptedException {
        GenericResponse rs = new GenericResponse();
        WebDriver driver = configCommonFuncFirefox.loginByCookie("111111");
        driver.navigate().to("https://facebook.com/groups/1908251769308065");
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
                    break;
                }
            }
        }
        Set<String> uniqueNumbersUid = new HashSet<>();
        for (String data : uniqueNumbers) {
            driver.navigate().to("https://www.facebook.com/groups/535148127127030/posts/1585342455440920");
            listLinkComment.add("https://facebook.com/groups/" + getAllUidLikePostModel.getGroupId() + "/posts/" + data);
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement clickReaction = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@aria-label='See who reacted to this']")));
                clickReaction.click();
                WebElement clickReaction1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@aria-label, 'Show') and contains(@aria-label, 'reacted with All')]")));
                clickReaction1.click();
                Thread.sleep(5000);
            }catch (Exception e){
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement clickReaction = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@aria-label='See who reacted to this']")));
                clickReaction.click();
                WebElement clickReaction1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@aria-label, 'Show') and contains(@aria-label, 'reacted with All')]")));
                clickReaction1.click();
                Thread.sleep(5000);
            }

            //Scroll Dialog Reaction
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                // Locate the dialog using its attributes
                WebElement dialog = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("div[role='dialog']"))); // Adjust selector if needed

                // Wait for the content to be fully loaded inside the dialog
                wait.until(ExpectedConditions.visibilityOf(dialog));

                // Locate the scrollable child element (if dialog itself is not scrollable)
                JavascriptExecutor js = (JavascriptExecutor) driver;
                WebElement scrollableElement = (WebElement) js.executeScript(
                        "let dialog = arguments[0];" +
                                "let children = dialog.querySelectorAll('*');" +
                                "for (let el of children) {" +
                                "  let style = getComputedStyle(el);" +
                                "  if ((style.overflowY === 'auto' || style.overflowY === 'scroll') && el.scrollHeight > el.clientHeight) {" +
                                "    return el;" +
                                "  }" +
                                "}" +
                                "return dialog;", dialog);

                // Scroll the detected element
                for(int i =0;i<3; i++){
                    scrollElement(js, scrollableElement);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            //End Scroll Dialog Reaction


            WebElement dialog1 = driver.findElement(By.xpath("//div[@role='dialog']"));
            List<WebElement> linksUid = dialog1.findElements(By.tagName("a"));
            // In ra các URL hợp lệ
            for (WebElement link : linksUid) {
                String url = link.getAttribute("href");
                if (url != null && !url.isEmpty() && url.contains("groups/535148127127030/user")) {
                    Pattern pattern = Pattern.compile("/user/(\\d+)");
                    Matcher matcher = pattern.matcher(url);
                    if (matcher.find()) {
                        uniqueNumbersUid.add(matcher.group(1)); // Add number to the Set
                    }
                }
            }
        }
        driver.quit();
        rs.setData(uniqueNumbersUid);
        return rs;
    }


    public GenericResponse getAllUidCommentInPost(GetAllUidLikePostModel getAllUidLikePostModel) throws InterruptedException {
        GenericResponse rs = new GenericResponse();
        WebDriver driver = configCommonFuncFirefox.loginByCookie(getAllUidLikePostModel.getPageId());
        driver.navigate().to("https://facebook.com/groups/"+ getAllUidLikePostModel.getGroupId());
        for(int i =0; i<3; i++){
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
        Set<String> uniqueNumbersUid = new HashSet<>();

        for (String data : uniqueNumbers) {
            driver.navigate().to("https://facebook.com/groups/" + getAllUidLikePostModel.getGroupId() + "/posts/" + data);
            listLinkComment.add("https://facebook.com/groups/" + getAllUidLikePostModel.getGroupId() + "/posts/" + data);
            Thread.sleep(2000);

            List<WebElement> linksForGetUidCmt = driver.findElements(By.xpath(
                    "//div[contains(@class, 'html-div') and contains(@class, 'xdj266r') and contains(@class, 'x11i5rnm') and contains(@class, 'xat24cr') and contains(@class, 'x1mh8g0r') and contains(@class, 'xexx8yu') and contains(@class, 'x18d9i69') and contains(@class, 'x1swvt13') and contains(@class, 'x1pi30zi') and contains(@class, 'x1n2onr6')]//a"        ));
            // In ra các URL hợp lệ
            for (WebElement link : linksForGetUidCmt) {
                String url = link.getAttribute("href");
                if (url != null && !url.isEmpty() && url.contains("groups/"+getAllUidLikePostModel.getGroupId()+"/user")) {
                    Pattern pattern = Pattern.compile("/user/(\\d+)");
                    Matcher matcher = pattern.matcher(url);
                    if (matcher.find()) {
                        uniqueNumbersUid.add(matcher.group(1)); // Add number to the Set
                    }
                }
            }
        }

        driver.quit();
        Map data = new HashMap();
        data.put("Uid",uniqueNumbersUid);
        data.put("Post",listLinkComment);
        rs.setData(data);
        return rs;
    }

    private static void scrollElement(JavascriptExecutor js, WebElement element) {
        try {
            // Scroll down step by step with smooth scrolling
            long scrollHeight = (Long) js.executeScript("return arguments[0].scrollHeight;", element);
            long clientHeight = (Long) js.executeScript("return arguments[0].clientHeight;", element);
            long scrollPosition = 0;

            while (scrollPosition < scrollHeight - clientHeight) {
                js.executeScript("arguments[0].scrollTop += 100;", element); // Adjust increment as needed
                scrollPosition = (Long) js.executeScript("return arguments[0].scrollTop;", element);
                System.out.println("Scrolled to position: " + scrollPosition);
                Thread.sleep(200); // Add delay for smooth scrolling
            }

            // Ensure it reaches the bottom
            js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight;", element);
            System.out.println("Scroll completed.");
        } catch (Exception e) {
            System.err.println("Error during scrolling: " + e.getMessage());
        }
    }
}
