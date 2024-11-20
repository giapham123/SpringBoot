package com.example.demo.Facebook.controllers;

import com.example.demo.Facebook.models.AutoCommentPostGroup;
import com.example.demo.Facebook.models.SharePostPageModel;
import com.example.demo.Facebook.services.AutoCommentPostInGroupService;
import com.example.demo.Facebook.services.SharePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/basic")
public class AutoCommentPostInGroupController {

    @Autowired
    AutoCommentPostInGroupService autoCommentPostInGroupService;

    @PostMapping("/auto-comment-post")
    public ResponseEntity<String> autoCommentPost(@RequestParam ("content") String content,
                                                  @RequestParam ("groupId") String groupId,
                                                  @RequestParam ("typeComp") String typeComp) throws InterruptedException {
        AutoCommentPostGroup autoCommentPostGroup = new AutoCommentPostGroup();
        autoCommentPostGroup.setContent(content);
        autoCommentPostGroup.setGroupId(groupId);
        autoCommentPostGroup.setTypeComp(typeComp);
        return autoCommentPostInGroupService.autoCommentPost(autoCommentPostGroup);
    }
}