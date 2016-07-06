package com.h3c.idcloud.core.adapter.pojo.network.result;

import java.util.List;

public class SecurityGroup {

    private String tenantId;

    private String id;

    private String name;

    private String description;

    private List<SecurityGroupRules> securityGroupRules;


    public List<SecurityGroupRules> getSecurityGroupRules() {
        return securityGroupRules;
    }

    public void setSecurityGroupRules(List<SecurityGroupRules> securityGroupRules) {
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
