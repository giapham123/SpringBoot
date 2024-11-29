package com.example.demo.Facebook.services;


import com.example.demo.Facebook.commonFunc.ConfigCommonFuncFirefox;
import com.example.demo.Facebook.models.AddFriendViaUIdModel;
import com.example.demo.Facebook.models.AutoPostGroup;
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
public class AddFriendViaUIdService {

    @Autowired
    ConfigCommonFuncFirefox configCommonFuncFirefox;

    public GenericResponse autoAddFriend(AddFriendViaUIdModel addFriendViaUIdModel) throws InterruptedException {
        GenericResponse rs = new GenericResponse();
        WebDriver driver = configCommonFuncFirefox.loginByCookie(addFriendViaUIdModel.getPageId());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        List<String> listUIdSuccessAdd = new ArrayList<>();
        List<String> listUIdFailAdd = new ArrayList<>();
        for(int i = 0;i<addFriendViaUIdModel.getuId().size(); i++) {
            driver.get("https://facebook.com/"+ addFriendViaUIdModel.getuId().get(i));
            try {
                WebElement clickButtonAddFriend = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'xh8yej3')]//div[@aria-label='Add friend']")));
                clickButtonAddFriend.click();
                listUIdSuccessAdd.add(addFriendViaUIdModel.getuId().get(i));
                Thread.sleep(3000);
            } catch (Exception e) {
                listUIdFailAdd.add(addFriendViaUIdModel.getuId().get(i));
            }finally {
                continue;
            }
        }
        driver.quit();
        Map rsData = new HashMap();
        rsData.put("listUIdSuccessAdd",listUIdSuccessAdd);
        rsData.put("listUIdFailAdd",listUIdFailAdd);
        rs.setData(rsData);
        rs.setMessage("Add Friends Method.");
        return rs;
    }
}
