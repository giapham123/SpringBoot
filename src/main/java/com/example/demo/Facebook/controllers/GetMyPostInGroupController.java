package com.example.demo.Facebook.controllers;

import com.example.demo.Facebook.models.GetMyPostGroupModel;
import com.example.demo.Facebook.models.GetUidUserInGroupModel;
import com.example.demo.Facebook.services.GetMyPostInGroupService;
import com.example.demo.common.GenericResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/*
This controller will get all post published in group and invite people reactions to follow page
 */
@RestController
@RequestMapping("/api/basic")
public class GetMyPostInGroupController {

    @Autowired
    GetMyPostInGroupService getMyPostInGroupService;

    @PostMapping("/get-my-post-invite-group")
    public GenericResponse getMyPostInGroupAndInvite(
            @Parameter(example = "3010847542478743")
            @RequestParam ("groupId") String groupId,
            @Parameter(example = "100063707646753")
            @RequestParam ("pageId") String pageId) throws InterruptedException {
        GetMyPostGroupModel getMyPostGroupModel= new GetMyPostGroupModel();
        getMyPostGroupModel.setGroupId(groupId);
        getMyPostGroupModel.setPageId(pageId);
        return getMyPostInGroupService.getMyPostInGroupAndInvite(getMyPostGroupModel);
    }
}
