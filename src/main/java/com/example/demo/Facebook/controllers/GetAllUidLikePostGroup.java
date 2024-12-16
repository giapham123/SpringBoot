package com.example.demo.Facebook.controllers;

import com.example.demo.Facebook.models.GetAllUidLikePostModel;
import com.example.demo.Facebook.services.GetAllUidLikePostGroupService;
import com.example.demo.Facebook.services.GetUidUserInGroupService;
import com.example.demo.common.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/basic")
public class GetAllUidLikePostGroup {

    @Autowired
    GetAllUidLikePostGroupService getAllUidLikePostGroupService;

    @PostMapping("/get-all-uid-like-post")
    public GenericResponse getAllUidLikePost() throws InterruptedException {
        GetAllUidLikePostModel model = new GetAllUidLikePostModel();
        return getAllUidLikePostGroupService.getAllUidLikePost(model);
    }
}
