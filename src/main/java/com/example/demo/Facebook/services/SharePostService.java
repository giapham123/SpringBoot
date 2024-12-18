package com.example.demo.Facebook.services;

import com.example.demo.Facebook.commonFunc.ConfigCommonFunc;
import com.example.demo.Facebook.commonFunc.ConfigCommonFuncFirefox;
import com.example.demo.Facebook.models.GetUidUserInGroupModel;
import com.example.demo.Facebook.models.SharePostPageModel;
import com.example.demo.common.GenericResponse;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SharePostService {

    @Autowired
    ConfigCommonFuncFirefox configCommonFunc;

    public GenericResponse getAllPostId(SharePostPageModel sharePostPageModel) throws InterruptedException {
        GenericResponse rs = new GenericResponse();
        WebDriver driver = configCommonFunc.loginByCookie(sharePostPageModel.getPageId());
        driver.navigate().to("https://web.facebook.com/"+sharePostPageModel.getPageName());
        configCommonFunc.scrollTopToEndPage(sharePostPageModel.getScrollNumbers(),driver);
        try {
            List<WebElement> links = driver.findElements(By.xpath("//a[@aria-label='Boost post']"));
            Set<String> uniqueNumbers = new HashSet<>();

            // In ra các URL hợp lệ
            for (WebElement link : links) {
                String url = link.getAttribute("href");
                // Define the regex pattern
                String regex = "target_id=(\\d+)&__cft__";
                // Compile the pattern
                Pattern pattern = Pattern.compile(regex);
                // Match the pattern in the input string
                Matcher matcher = pattern.matcher(url);
                if (matcher.find()) {
                    // Extract the number (group 1)
                    String targetId = matcher.group(1);
                    uniqueNumbers.add(targetId);
                    System.out.println("Extracted target_id: " + targetId);
                    break;
                } else {
                    System.out.println("No match found.");
                }
            }
            System.out.println("Total groups in page: " + uniqueNumbers.size());

            //Set data for return
            Map resultData = new HashMap();
            resultData.put("quantityPostId", uniqueNumbers.size());
            resultData.put("dataPostId", uniqueNumbers);
            rs.setData(resultData);
            rs.setMessage("Get All Group In Page Success");
            driver.quit();
            return rs;
        }catch (Exception e){
            rs.setData(null);
            rs.setMessage("Get All Group In Page Failed");
            driver.quit();
            System.out.println(e);
            return rs;
        }
    }

    public GenericResponse sharePostPage(SharePostPageModel sharePostPageModel) throws InterruptedException {
        GenericResponse rs = new GenericResponse();
        WebDriver driver = configCommonFunc.loginByCookie(sharePostPageModel.getPageId());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        List<String> listGroupShareSuccess = new ArrayList<>();
        List<String> listGroupShareFail= new ArrayList<>();

        try {
            for (String group : sharePostPageModel.getGroupName()) {
                driver.navigate().to("https://www.facebook.com/"+sharePostPageModel.getPageName()+"/posts/"+sharePostPageModel.getIdPost());
                System.out.println("Group Name: " + group);
                //Click share at post
                try{
                    // Chờ popup hiển thị
                    WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='dialog']")));
                    // Click nút Share trong popup
                    WebElement popupShareButton = popup.findElement(By.cssSelector("span[data-ad-rendering-role='share_button']"));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", popupShareButton);
                }catch (Exception e){
                    //Edit for click Share button on post
                    //WebElement clickShare = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[3]/div/div/div[2]/span/span")));
                    WebElement dialogPost = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='dialog']")));
                    WebElement clickShare = dialogPost.findElement(By.xpath("//div[@aria-label='Send this to friends or post it on your profile.']//span[@data-ad-rendering-role='share_button']"));
                    clickShare.click();
                    //End edit
                }

                //Click group at popup share
                WebElement clickGroup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[4]/div/div/div/div/div/i")));
                clickGroup.click();
                //Input search for find group
                WebElement inputSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/div/div/div/div/div/div/label/input")));
                inputSearch.click();
                WebElement inputSearchValue = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/div/div/div/div/div/div/label/input")));
                String targetGroup = group;  // Replace with your target group name
                inputSearchValue.sendKeys(targetGroup);
                Thread.sleep(2000); // Wait for the next set of groups to load
                //Select group for share
                try {
                    WebElement clickSelectGroup = wait.until(
                            ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(.,'" + group + "')]"))
                    );
                    clickSelectGroup.click();
                }
                catch(Exception e){
                    try {
                        WebElement clickSelectGroup = wait.until(
                                ExpectedConditions.presenceOfElementLocated(By.xpath("//div[2]/div/div/i"))
                        );
                        clickSelectGroup.click();
                        listGroupShareFail.add(group);
                    }catch (Exception c){
                        listGroupShareFail.add(group);
                        continue;
                    }
                }
                //Click post
                WebElement clickPost = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label='Post']")));
                clickPost.click();
                listGroupShareSuccess.add(group);
                // Pause for a few seconds to ensure the post completes
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        Map rsData = new HashMap();
        rsData.put("listSuccess",listGroupShareSuccess);
        rsData.put("listFail",listGroupShareFail);
        rs.setData(rsData);
        rs.setMessage("Share post to group.");
        driver.quit();
        return rs;
    }
}
