package com.example.demo.Facebook.models;

import java.util.List;

public class AddFriendViaUIdModel extends CommonModel{
    List<String> uId;

    public List<String> getuId() {
        return uId;
    }

    public void setuId(List<String> uId) {
        this.uId = uId;
    }
}