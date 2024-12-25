package com.example.demo.Facebook.services;

import com.example.demo.Facebook.commonFunc.ConfigCommonFuncFirefox;
import com.example.demo.Facebook.models.GetMyPostGroupModel;
import com.example.demo.Facebook.models.GetUidUserInGroupModel;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GetMyPostInGroupService {

    @Autowired
    ConfigCommonFuncFirefox configCommonFuncFirefox;

    public GenericResponse getMyPostInGroupAndInvite(GetMyPostGroupModel getMyPostGroupModel) throws InterruptedException {
        try{
            GenericResponse rs = new GenericResponse();
            WebDriver driver = configCommonFuncFirefox.loginByCookie(getMyPostGroupModel.getPageId());
            String[] splitGroupId = getMyPostGroupModel.getGroupId().trim().split(",");
            Set<String> myPostIds = new HashSet<>();
            Set<String> listReactionsPages= new HashSet<>();
            for(int j =0; j < splitGroupId.length; j++) {
                driver.navigate().to("https://facebook.com/groups/" + splitGroupId[j] + "/my_posted_content");
                for (int i = 0; i < 5; i++) {
                    // Tạo đối tượng JavascriptExecutor
                    JavascriptExecutor js = (JavascriptExecutor) driver;

                    // Cuộn xuống cuối trang
                    js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

                    // Chờ một lúc để kiểm tra
                    Thread.sleep(2000);
                }
                // Lấy tất cả các thẻ <a>
                List<WebElement> links = driver.findElements(By.tagName("a"));
                // In ra các URL hợp lệ
                for (WebElement link : links) {
                    String url = link.getAttribute("href");
                    if (url != null && !url.isEmpty() && url.contains("pcb")) {
                        // Define a pattern to match numbers after 'pcb.'
                        Pattern pattern = Pattern.compile("pcb.(\\d+)");
                        // Loop through each link and extract the number after 'pcb.'
                        Matcher matcher = pattern.matcher(url);
                        if (matcher.find()) {
                            myPostIds.add(matcher.group(1)); // Add number to the Set
                            break;
                        }
                    }
                }
                for (String data : myPostIds) {
//                    driver.navigate().to("https://www.facebook.com/groups/" + splitGroupId[j] + "/posts/" + data);
                    driver.navigate().to("https://web.facebook.com/groups/3010847542478743/posts/3695813393982151");
                    listReactionsPages.add("https://facebook.com/groups/" + splitGroupId[j] + "/posts/" + data);
                    Thread.sleep(2000);
                    try {
                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                        WebElement clickReaction = driver.findElement(By.xpath("//span[@aria-label='See who reacted to this']"));
                        clickReaction.click();
//                        WebElement clickReaction1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@aria-label, 'Show') and contains(@aria-label, 'reacted with All')]")));
//                        clickReaction1.click();
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        System.out.println(e);
                    }



//                    WebElement dialog1 = driver.findElement(By.xpath("//div[@role='dialog']"));
//                    List<WebElement> linksUidUser = dialog1.findElements(By.tagName("a"));
//                    // In ra các URL hợp lệ
//                    List<String> listUidUser = new ArrayList<>();
//
//                    for (WebElement link : linksUidUser) {
//                        String url = link.getAttribute("href");
//                        if (url != null && !url.isEmpty() && url.contains("groups/" + splitGroupId[j] + "/user")) {
//                            Pattern pattern = Pattern.compile("/user/(\\d+)");
//                            Matcher matcher = pattern.matcher(url);
//                            if (matcher.find()) {
//                                listUidUser.add(matcher.group(1));
//                            }
//                        }
//                    }

                    for(int i = 1; i <=6; i++){
                        try {
                            WebElement clickInvite = driver.findElement(By.xpath("(//div[@aria-label='Invite' and @role='button'])[" + i + "]"));
                            clickInvite.click();
                            Thread.sleep(2000);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }
            }
            driver.quit();
            rs.setData(myPostIds);
            return rs;
        }catch (Exception e){
            return null;
        }
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
