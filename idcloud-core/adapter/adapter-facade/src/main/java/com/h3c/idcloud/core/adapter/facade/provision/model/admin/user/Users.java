package com.h3c.idcloud.core.adapter.facade.provision.model.admin.user;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.user.result.UserCreateResult;
import com.h3c.idcloud.core.adapter.pojo.user.result.UserDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.user.result.UserInfoGetResult;
import com.h3c.idcloud.core.adapter.pojo.user.result.UserListGetResult;

public class Users extends CommonAdapterResult {

    private UserCreateResult userCreateResult = new UserCreateResult();

    private UserDeleteResult userDeleteResult = new UserDeleteResult();

    private UserInfoGetResult userInfoGetResult = new UserInfoGetResult();

    private UserListGetResult userListGetResult = new UserListGetResult();

    public UserCreateResult getUserCreateResult() {
        return userCreateResult;
    }

    public void setUserCreateResult(UserCreateResult userCreateResult) {
        this.userCreateResult = userCreateResult;
    }

    public UserDeleteResult getUserDeleteResult() {
        return userDeleteResult;
    }

    public void setUserDeleteResult(UserDeleteResult userDeleteResult) {
        this.userDeleteResult = userDeleteResult;
    }

    public UserInfoGetResult getUserInfoGetResult() {
        return userInfoGetResult;
    }

    public void setUserInfoGetResult(UserInfoGetResult userInfoGetResult) {
        this.userInfoGetResult = userInfoGetResult;
    }

    public UserListGetResult getUserListGetResult() {
        return userListGetResult;
    }

    public void setUserListGetResult(UserListGetResult userListGetResult) {
        this.userListGetResult = userListGetResult;
    }


}
