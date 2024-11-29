package com.example.demo.Facebook.services;

import com.example.demo.Facebook.commonFunc.ConfigCommonFuncFirefox;
import com.example.demo.Facebook.models.AutoPostGroup;
import com.example.demo.Facebook.models.InvitePeopleLikePageModel;
import com.example.demo.common.GenericResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvitePeopleLikePageService {

    @Autowired
    ConfigCommonFuncFirefox configCommonFuncFirefox;

    public GenericResponse autoInvitePeopleLikePage(InvitePeopleLikePageModel invitePeopleLikePageModel) throws InterruptedException {
        GenericResponse rs = new GenericResponse();
        WebDriver driver = configCommonFuncFirefox.loginByCookie(invitePeopleLikePageModel.getPageId());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("https://facebook.com/"+ invitePeopleLikePageModel.getGroupName());
        try {
            WebElement clickOptions = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'x78zum5')]//div[@aria-label='See Options']")));
            clickOptions.click();
            WebElement clickInviteFriends = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'Invite friends')]")));
            clickInviteFriends.click();
            WebElement clickSelectAll = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@aria-label, 'Select All')]")));
            clickSelectAll.click();
        } catch (Exception e) {
            System.out.println(e);
            rs.setMessage("Invite Failed, Please Check Element.");
        }
        rs.setMessage("Invite Completed.");
        return rs;
    }
}
