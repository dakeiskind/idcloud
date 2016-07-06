package com.h3c.idcloud.core.adapter.pojo.network.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.network.SecurityGroupRule;

import java.util.List;

public class SgRuleListQueryResult extends BaseResult {
    private List<SecurityGroupRule> securityGroups;

    public List<SecurityGroupRule> getSecurityGroups() {
        return securityGroups;
    }

    public void setSecurityGroups(List<SecurityGroupRule> securityGroups) {
        this.securityGroups = securityGroups;
    }

}
