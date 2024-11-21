package com.example.demo.Facebook.services;

import com.example.demo.Facebook.models.GetUidUserInGroupModel;
import com.example.demo.common.GenericResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GetAllGroupNameInPageService {
    @Autowired
    com.example.demo.Facebook.commonFunc.configCommonFunc configCommonFunc;

    public GenericResponse getAllGroupNameInPage(GetUidUserInGroupModel getUidUserInGroupModel) throws InterruptedException {
        GenericResponse rs = new GenericResponse();
        WebDriver driver = configCommonFunc.loginByCookie(getUidUserInGroupModel.getTypeComp());
        driver.navigate().to("https://web.facebook.com/groups/joins/?nav_source=tab");
        configCommonFunc.scrollTopToEndPage(getUidUserInGroupModel.getScrollNumbers(),driver);
        try {
            List<WebElement> links = driver.findElements(By.xpath("//a[starts-with(@href, 'https://web.facebook.com/groups/')]"));
            Set<String> uniqueNumbers = new HashSet<>();

            // In nội dung văn bản của từng thẻ <a>
            for (WebElement link : links) {
                String text = link.getText(); // Lấy nội dung văn bản
                uniqueNumbers.add(text.split("\n")[0]);
                System.out.println("Text: " + text);
            }
            System.out.println("Total groups in page: " + uniqueNumbers.size());

            //Set data for return
            Map resultData = new HashMap();
            resultData.put("totalUser", uniqueNumbers.size());
            resultData.put("dataUid", uniqueNumbers);
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
}
