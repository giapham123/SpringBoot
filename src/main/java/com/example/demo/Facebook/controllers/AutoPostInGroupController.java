package com.example.demo.Facebook.controllers;

import com.example.demo.Facebook.models.AutoPostGroup;
import com.example.demo.Facebook.services.AutoPostInGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/basic")
public class AutoPostInGroupController {

    @Autowired
    AutoPostInGroupService autoPostInGroupService;

    @PostMapping("/auto-comment-post")
    public ResponseEntity<String> autoCommentPost(@RequestParam ("content") String content,
                                                  @RequestParam ("groupId") String groupId,
                                                  @RequestParam ("typeComp") String typeComp) throws InterruptedException {
        AutoPostGroup autoPostGroup = new AutoPostGroup();
        autoPostGroup.setContent(content);
        autoPostGroup.setGroupId(groupId);
        autoPostGroup.setTypeComp(typeComp);
        return autoPostInGroupService.autoCommentPost(autoPostGroup);
    }
}