package com.example.demo.Facebook.models;

import java.util.ArrayList;
import java.util.List;

public class SharePostPageModel {
    private String idPost;
    private String pageName;
    private int scrollNumbers;
    private String typeComp;
    private List<String> groupName =new ArrayList<>();

    public int getScrollNumbers() {
        return scrollNumbers;
    }

    public void setScrollNumbers(int scrollNumbers) {
        this.scrollNumbers = scrollNumbers;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getTypeComp() {
        return typeComp;
    }

    public void setTypeComp(String typeComp) {
        this.typeComp = typeComp;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public List<String> getGroupName() {
        return groupName;
    }

    public void setGroupName(List<String> groupName) {
        this.groupName = groupName;
    }
}
