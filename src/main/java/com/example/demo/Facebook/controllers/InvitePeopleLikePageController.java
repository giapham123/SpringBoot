package com.example.demo.Facebook.controllers;

import com.example.demo.Facebook.models.InvitePeopleLikePageModel;
import com.example.demo.Facebook.services.InvitePeopleLikePageService;
import com.example.demo.common.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/basic")
public class InvitePeopleLikePageController {

    @Autowired
    InvitePeopleLikePageService invitePeopleLikePageService;

    @PostMapping("/auto-invite-people-like")
    public GenericResponse autoCommentPost(@RequestParam ("groupName") String groupname) throws InterruptedException {
        InvitePeopleLikePageModel model = new InvitePeopleLikePageModel();
        model.setGroupName(groupname);
        return invitePeopleLikePageService.autoInvitePeopleLikePage(model);
    }
}
