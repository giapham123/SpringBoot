package com.example.demo.Facebook.controllers;

import com.example.demo.Facebook.models.AutoCommentPostGroup;
import com.example.demo.Facebook.models.AutoPostGroup;
import com.example.demo.Facebook.services.AutoCommentPostInGroupService;
import com.example.demo.Facebook.services.AutoPostInGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/basic")
public class AutoCommentPostInGroupController {

    @Autowired
    AutoCommentPostInGroupService autoPostInGroupService;

    @PostMapping("/auto-comment-post")
    public ResponseEntity<String> autoCommentPost(@RequestParam ("content") String content,
                                                  @RequestParam ("groupId") String groupId,
                                                  @RequestParam ("typeComp") String typeComp) throws InterruptedException {
        AutoCommentPostGroup autoPostGroup = new AutoCommentPostGroup();
        autoPostGroup.setContent(content);
        autoPostGroup.setGroupId(groupId);
        autoPostGroup.setTypeComp(typeComp);
        return autoPostInGroupService.autoCommentPost(autoPostGroup);
    }
}