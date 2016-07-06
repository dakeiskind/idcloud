package com.h3c.idcloud.core.adapter.pojo.user.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class UserDeleteResult extends BaseResult {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
