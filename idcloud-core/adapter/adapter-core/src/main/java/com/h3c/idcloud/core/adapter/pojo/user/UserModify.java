package com.h3c.idcloud.core.adapter.pojo.user;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class UserModify extends Base {

    private String userId;

    private String newPasswd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewPasswd() {
        return newPasswd;
    }

    public void setNewPasswd(String newPasswd) {
        this.newPasswd = newPasswd;
    }

}
