package com.example.demo.Facebook.controllers;

import com.example.demo.Facebook.models.GetUidUserInGroupModel;
import com.example.demo.Facebook.services.GetAllGroupNameInPageService;
import com.example.demo.common.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/basic")
public class GetAllGroupNameController {

    @Autowired
    GetAllGroupNameInPageService getAllGroupNameInPageService;

    @PostMapping("/get-all-group-name")
    public GenericResponse autoCommentPost(@RequestParam("scrollNumbers") int scrollNumbers,
                                           @RequestParam ("typeComp") String typeComp) throws InterruptedException {
        GetUidUserInGroupModel getUidUserInGroupModel= new GetUidUserInGroupModel();
        getUidUserInGroupModel.setTypeComp(typeComp);
        getUidUserInGroupModel.setScrollNumbers(scrollNumbers);

        return getAllGroupNameInPageService.getAllGroupNameInPage(getUidUserInGroupModel);
    }
}