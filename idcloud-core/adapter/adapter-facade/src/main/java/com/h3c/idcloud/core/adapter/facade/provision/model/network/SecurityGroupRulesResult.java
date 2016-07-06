package com.h3c.idcloud.core.adapter.facade.provision.model.network;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import org.codehaus.jackson.annotate.JsonProperty;

public class SecurityGroupRulesResult extends CommonAdapterResult {

    @JsonProperty("security_group_rule")
    private SecurityGroupRule securityGroupRules;

    public SecurityGroupRule getSecurityGroupRules() {
        return securityGroupRules;
    }

    public void setSecurityGroupRules(SecurityGroupRule securityGroupRules) {
        this.securityGroupRules = securityGroupRules;
    }

}
