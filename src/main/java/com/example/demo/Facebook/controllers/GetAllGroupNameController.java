package com.example.demo.Facebook.controllers;

import com.example.demo.Facebook.models.GetUidUserInGroupModel;
import com.example.demo.Facebook.services.GetAllGroupNameInPageService;
import com.example.demo.common.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
curl --location --request POST 'localhost:8088/api/basic/get-all-group-name?typeComp=mac&groupId=656961338072129&scrollNumbers=2' \
        --header 'Authorization: Basic dXNlcjpwYXNzd29yZA==' \
        --header 'Cookie: JSESSIONID=36E0FAC6F719CF0C697DD7F33D307A92'

 */

@RestController
@RequestMapping("/api/basic")
public class GetAllGroupNameController {

    @Autowired
    GetAllGroupNameInPageService getAllGroupNameInPageService;

    @PostMapping("/get-all-group-name")
    public GenericResponse autoCommentPost(@RequestParam("scrollNumbers") int scrollNumbers,
                                           @RequestParam("pageId") String pageId) throws InterruptedException {
        GetUidUserInGroupModel getUidUserInGroupModel= new GetUidUserInGroupModel();
        getUidUserInGroupModel.setScrollNumbers(scrollNumbers);
        getUidUserInGroupModel.setPageId(pageId);

        return getAllGroupNameInPageService.getAllGroupNameInPage(getUidUserInGroupModel);
    }
}