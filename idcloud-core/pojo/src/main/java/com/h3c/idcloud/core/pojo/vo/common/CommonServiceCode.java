package com.h3c.idcloud.core.pojo.vo.common;

import java.io.Serializable;

/**
 * Created by gaox on 2/29/2016.
 */
public class CommonServiceCode implements Serializable {

    private String serviceName;

    private Long serviceSid;

    public Long getServiceSid() {
        return serviceSid;
    }

    public void setServiceSid(Long serviceSid) {
        this.serviceSid = serviceSid;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
