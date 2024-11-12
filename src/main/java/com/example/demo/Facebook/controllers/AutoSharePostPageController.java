package com.example.demo.Facebook.controllers;

import com.example.demo.Facebook.models.SharePostPageModel;
import com.example.demo.Facebook.services.SharePostService;
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

@RestController
@RequestMapping("/api/basic")
public class AutoSharePostPageController {

    @Autowired
    SharePostService sharePostService;

    @PostMapping("/share-post-page")
    public ResponseEntity<String> secureEndpointBasic(@RequestBody SharePostPageModel sharePostPageModel) {
        return sharePostService.sharePostPage(sharePostPageModel);
    }
}
