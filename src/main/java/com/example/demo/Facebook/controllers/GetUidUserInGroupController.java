package com.example.demo.Facebook.controllers;

import com.example.demo.Facebook.models.AutoPostGroup;
import com.example.demo.Facebook.models.GetUidUserInGroupModel;
import com.example.demo.Facebook.services.AutoPostInGroupService;
import com.example.demo.Facebook.services.GetUidUserInGroupService;
import com.example.demo.common.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/basic")
public class GetUidUserInGroupController {

    @Autowired
    GetUidUserInGroupService getUidUserInGroupService;

    @PostMapping("/get-uid-user")
    public GenericResponse autoCommentPost( @RequestParam ("scrollNumbers") int scrollNumbers,
                                                  @RequestParam ("groupId") String groupId,
                                                  @RequestParam ("typeComp") String typeComp) throws InterruptedException {
        GetUidUserInGroupModel getUidUserInGroupModel= new GetUidUserInGroupModel();
        getUidUserInGroupModel.setGroupId(groupId);
        getUidUserInGroupModel.setTypeComp(typeComp);
        getUidUserInGroupModel.setScrollNumbers(scrollNumbers);

        return getUidUserInGroupService.getUidUser(getUidUserInGroupModel);
    }
}