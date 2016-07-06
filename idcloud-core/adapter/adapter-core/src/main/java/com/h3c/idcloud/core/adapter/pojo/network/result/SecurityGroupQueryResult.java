package com.h3c.idcloud.core.adapter.pojo.network.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

import java.util.List;

public class SecurityGroupQueryResult extends BaseResult implements java.io.Serializable {
    private List<com.h3c.idcloud.core.adapter.pojo.network.SecurityGroup> securityGroups;

    public List<com.h3c.idcloud.core.adapter.pojo.network.SecurityGroup> getSecurityGroups() {
        return securityGroups;
    }

    public void setSecurityGroups(
            List<com.h3c.idcloud.core.adapter.pojo.network.SecurityGroup> securityGroups) {
        this.securityGroups = securityGroups;
    }
}
