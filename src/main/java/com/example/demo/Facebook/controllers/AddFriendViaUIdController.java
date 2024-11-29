package com.example.demo.Facebook.controllers;

import com.example.demo.Facebook.models.AddFriendViaUIdModel;
import com.example.demo.Facebook.models.AutoCommentPostGroup;
import com.example.demo.Facebook.services.AddFriendViaUIdService;
import com.example.demo.common.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/basic")
public class AddFriendViaUIdController {

    @Autowired
    AddFriendViaUIdService addFriendViaUIdService;

    @PostMapping("/auto-add-friend")
    public GenericResponse autoAddFriend(@RequestBody AddFriendViaUIdModel addFriendViaUIdModel) throws InterruptedException {
        return addFriendViaUIdService.autoAddFriend(addFriendViaUIdModel);
    }
}
