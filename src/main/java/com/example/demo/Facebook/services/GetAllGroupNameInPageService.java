package com.example.demo.Facebook.services;

import com.example.demo.Facebook.commonFunc.ConfigCommonFunc;
import com.example.demo.Facebook.commonFunc.ConfigCommonFuncFirefox;
import com.example.demo.Facebook.models.GetUidUserInGroupModel;
import com.example.demo.common.GenericResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GetAllGroupNameInPageService {
    @Autowired
    ConfigCommonFunc configCommonFunc;

    @Autowired
    ConfigCommonFuncFirefox configCommonFuncFirefox;

    public GenericResponse getAllGroupNameInPage(GetUidUserInGroupModel getUidUserInGroupModel) throws InterruptedException {
        GenericResponse rs = new GenericResponse();
        WebDriver driver = configCommonFuncFirefox.loginByCookie(getUidUserInGroupModel.getPageId());
        driver.navigate().to("https://web.facebook.com/groups/joins/?nav_source=tab");
        configCommonFunc.scrollTopToEndPage(getUidUserInGroupModel.getScrollNumbers(),driver);
        try {

            List<WebElement> links = driver.findElements(By.xpath("//a[contains(@href, 'facebook.com/groups/')]"));
            Set<String> uniqueNumbers = new HashSet<>();
            Set<String> uniqueNumbersNumMembers = new HashSet<>();
            Set<String> uniqueGroupNameNoId = new HashSet<>();
            String groupId = "";

            // In nội dung văn bản của từng thẻ <a>
            for (WebElement link : links) {
                String href = link.getAttribute("href");
                String text = link.getText(); // Lấy nội dung văn bản
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(href);
                String groupid = "";
                while (matcher.find()) {
                    groupid = matcher.group();
                }
                if(!text.split("\n")[0].isEmpty() && !text.split("\n")[0].equals("View group")){
                    uniqueNumbers.add(text.split("\n")[0] + ":" +groupid);
                    uniqueGroupNameNoId.add(text.split("\n")[0]);
                    System.out.println("Text: " + href);
                    groupId += groupid +",";
                }

            }
            for (String data : uniqueNumbers) {
                String dataRemove = data;
                String[] splitText = data.split(":");
                if(!splitText[0].isEmpty()){
                    try {
                        driver.get("https://web.facebook.com/groups/" + splitText[1]);
                        WebElement element = driver.findElement(By.xpath("//a[contains(@href, '/groups/"+splitText[1]+"/members/')]"));
                        Thread.sleep(2000);
                        dataRemove += ":" + element.getText();
                        uniqueNumbersNumMembers.add(dataRemove);
                        System.out.println("Get " + splitText[0]);
                    } catch (Exception e) {
//                        System.out.println(e);
                    }
                }
            }

            driver.quit();
            System.out.println("Total groups in page: " + uniqueNumbersNumMembers.size());

            //Set data for return
            Map resultData = new HashMap();
            resultData.put("totalGroupName", uniqueNumbersNumMembers.size());
            resultData.put("totalGroupNameAndId", uniqueNumbersNumMembers);
            resultData.put("totalGroupName", uniqueGroupNameNoId);
            resultData.put("groupId",groupId);
            rs.setData(resultData);
            rs.setMessage("Get All Group In Page Success");
            return rs;
        }catch (Exception e){
            rs.setData(null);
            rs.setMessage("Get All Group In Page Failed");
            driver.quit();
            System.out.println(e);
            return rs;
        }
    }
}
