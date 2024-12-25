package com.example.demo.Facebook.controllers;

import com.example.demo.Facebook.models.GetAllUidLikePostModel;
import com.example.demo.Facebook.services.GetAllUidLikePostGroupService;
import com.example.demo.Facebook.services.GetUidUserInGroupService;
import com.example.demo.common.GenericResponse;
import io.swagger.v3.oas.annotations.Parameter;
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
    public GenericResponse getAllUidLikePost(
            @Parameter(example = "2447675352168096")
            @RequestParam ("groupId") String groupId) throws InterruptedException {
        GetAllUidLikePostModel model = new GetAllUidLikePostModel();
        model.setGroupId(groupId);
        return getAllUidLikePostGroupService.getAllUidLikePost(model);
    }


    @PostMapping("/get-all-uid-comment-post")
    public GenericResponse getAllUidCommentInPost(@RequestParam ("groupId") String groupId) throws InterruptedException {
        GetAllUidLikePostModel model = new GetAllUidLikePostModel();
        model.setGroupId(groupId);
        return getAllUidLikePostGroupService.getAllUidCommentInPost(model);
    }
}
