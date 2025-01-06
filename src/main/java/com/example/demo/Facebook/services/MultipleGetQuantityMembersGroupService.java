package com.example.demo.Facebook.services;

import com.example.demo.Facebook.commonFunc.ConfigCommonFuncFirefox;
import com.example.demo.common.GenericResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class MultipleGetQuantityMembersGroupService {
    @Autowired
    ConfigCommonFuncFirefox configCommonFuncFirefox;

    @Async("taskExecutor")
    public CompletableFuture<String> processTaskMultipleGetNumMembers(String groupId, String pageId) throws InterruptedException {
        WebDriver driver = configCommonFuncFirefox.loginByCookie(pageId);
        String dataRemove = groupId;
        String[] splitText = groupId.split("-------------------");
        try {

            driver.get("https://web.facebook.com/groups/" + splitText[1]);
            Thread.sleep(10000);
            WebElement element = driver.findElement(By.xpath("//a[contains(@href, '/groups/"+splitText[1]+"/members/')]"));
            Thread.sleep(1000);
            if(element.getText().isEmpty()){
                element = driver.findElement(By.xpath("//a[contains(@href, 'groups') and contains(@href, 'members')]"));
                Thread.sleep(1000);
            }
            System.out.println(element.getText());
            dataRemove += "-------------------" + element.getText();
        } catch (InterruptedException e) {
            System.out.println("Cant find elements" + "https://web.facebook.com/groups/" + splitText[1]);
        }
        driver.quit();
        return CompletableFuture.completedFuture(dataRemove  +groupId);

    }
}
