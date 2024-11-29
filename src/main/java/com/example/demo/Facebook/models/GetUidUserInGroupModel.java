package com.example.demo.Facebook.models;

public class GetUidUserInGroupModel extends CommonModel{
    private String groupId;
    private int scrollNumbers;

    public int getScrollNumbers() {
        return scrollNumbers;
    }

    public void setScrollNumbers(int scrollNumbers) {
        this.scrollNumbers = scrollNumbers;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
