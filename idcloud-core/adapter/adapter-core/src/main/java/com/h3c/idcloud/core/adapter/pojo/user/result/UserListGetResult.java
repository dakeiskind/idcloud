package com.h3c.idcloud.core.adapter.pojo.user.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.user.User;

import java.util.List;

public class UserListGetResult extends BaseResult {

    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


}
