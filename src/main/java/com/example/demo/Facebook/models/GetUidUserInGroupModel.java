package com.example.demo.Facebook.models;

public class GetUidUserInGroupModel {
    private String typeComp;
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

    public String getTypeComp() {
        return typeComp;
    }

    public void setTypeComp(String typeComp) {
        this.typeComp = typeComp;
    }
}
