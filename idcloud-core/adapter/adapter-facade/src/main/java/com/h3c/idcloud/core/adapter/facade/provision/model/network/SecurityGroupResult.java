package com.h3c.idcloud.core.adapter.facade.provision.model.network;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import org.codehaus.jackson.annotate.JsonProperty;

public class SecurityGroupResult extends CommonAdapterResult {

    @JsonProperty("security_group")
    private SecurityGroup securityGroup = new SecurityGroup();

    public SecurityGroup getSecurityGroup() {
        return securityGroup;
    }

    public void setSecurityGroup(SecurityGroup securityGroup) {
        this.securityGroup = securityGroup;
    }

}
