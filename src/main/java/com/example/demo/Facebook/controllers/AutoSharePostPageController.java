package com.example.demo.Facebook.controllers;

import com.example.demo.Facebook.models.SharePostPageModel;
import com.example.demo.Facebook.services.SharePostService;
import com.example.demo.common.GenericResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
/*
curl --location 'localhost:8088/api/basic/share-post-page' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic dXNlcjpwYXNzd29yZA==' \
--header 'Cookie: JSESSIONID=36E0FAC6F719CF0C697DD7F33D307A92' \
--data '{
    "idPost":"1060172706116308",
    "pageName":"gp.farm47",
    "typeComp":"mac",
    "groupName":[
    "Tôi là dân Sài Gòn TP.Hồ Chí Minh"
    ]
}'
 */
@RestController
@RequestMapping("/api/basic")
public class AutoSharePostPageController {

    @Autowired
    SharePostService sharePostService;

    @PostMapping("/share-post-page")
    public GenericResponse secureEndpointBasic(@RequestBody SharePostPageModel sharePostPageModel) throws InterruptedException {
        return sharePostService.sharePostPage(sharePostPageModel);
    }

    @PostMapping("/get-all-post-id")
    public GenericResponse getAllPostId(@RequestParam("scrollNumbers") int scrollNumbers,
                                        @RequestParam("pageName") String pageName,
                                        @RequestParam ("pageId") String pageId) throws InterruptedException {
        SharePostPageModel postPageModel =new SharePostPageModel();
        postPageModel.setScrollNumbers(scrollNumbers);
        postPageModel.setPageName(pageName);
        postPageModel.setPageId(pageId);

        return sharePostService.getAllPostId(postPageModel);
    }
}
