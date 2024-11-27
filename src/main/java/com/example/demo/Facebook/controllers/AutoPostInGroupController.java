package com.example.demo.Facebook.controllers;

import com.example.demo.Facebook.models.AutoPostGroup;
import com.example.demo.Facebook.services.AutoPostInGroupService;
import com.example.demo.common.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/*
curl --location --request POST 'localhost:8088/api/basic/auto-post?content=Content&typeComp=mac&groupId=243320756560927' \
--header 'Authorization: Basic dXNlcjpwYXNzd29yZA==' \
--header 'Cookie: JSESSIONID=36E0FAC6F719CF0C697DD7F33D307A92' \
--data ''
 */
@RestController
@RequestMapping("/api/basic")
public class AutoPostInGroupController {

    @Autowired
    AutoPostInGroupService autoPostInGroupService;

    @PostMapping("/auto-post")
    public GenericResponse autoPostGroup(@RequestBody AutoPostGroup autoPostGroup) throws InterruptedException {
        return autoPostInGroupService.autoCommentPost(autoPostGroup);
    }
}
