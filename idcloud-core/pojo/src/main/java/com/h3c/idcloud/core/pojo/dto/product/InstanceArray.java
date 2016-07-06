package com.h3c.idcloud.core.pojo.dto.product;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/19.
 */
public class InstanceArray implements Serializable {
    private static final long serialVersionUID = 1L;
    private String serviceInstanceSid;
    private String instanceName;
    private String action;
    private String rebootType;
    private String zone;

    public String getServiceInstanceSid() {
        return serviceInstanceSid;
    }

    public void setServiceInstanceSid(String serviceInstanceSid) {
        this.serviceInstanceSid = serviceInstanceSid;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRebootType() {
        return rebootType;
    }

    public void setRebootType(String rebootType) {
        this.rebootType = rebootType;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
