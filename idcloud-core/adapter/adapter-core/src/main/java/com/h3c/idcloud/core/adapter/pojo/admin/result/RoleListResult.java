package com.h3c.idcloud.core.adapter.pojo.admin.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

import java.util.ArrayList;
import java.util.List;

public class RoleListResult extends BaseResult {

    private List<RoleResult> roles = new ArrayList<RoleResult>();

    public List<RoleResult> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleResult> roles) {
        this.roles = roles;
    }

}
