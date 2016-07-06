package com.h3c.idcloud.core.adapter.pojo.vm.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.network.SecurityGroup;

import java.util.List;

public class VmQuerySgsResult extends BaseResult {
    private List<SecurityGroup> securityGroups;

    public List<SecurityGroup> getSecurityGroups() {
        return securityGroups;
    }

    public void setSecurityGroups(List<SecurityGroup> securityGroups) {
        this.securityGroups = securityGroups;
    }

}
