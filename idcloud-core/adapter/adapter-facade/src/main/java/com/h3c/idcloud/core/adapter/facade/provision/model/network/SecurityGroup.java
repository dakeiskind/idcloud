package com.h3c.idcloud.core.adapter.facade.provision.model.network;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class SecurityGroup extends CommonAdapterResult {

    @JsonProperty("tenant_id")
    private String tenantId;

    private String id;

    private String name;

    private String description;

    @JsonProperty("security_group_rules")
    private List<SecurityGroupRule> securityGroupRules;


    public List<SecurityGroupRule> getSecurityGroupRules() {
        return securityGroupRules;
    }

    public void setSecurityGroupRules(List<SecurityGroupRule> securityGroupRules) {
        this.securityGroupRules = securityGroupRules;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }


}
