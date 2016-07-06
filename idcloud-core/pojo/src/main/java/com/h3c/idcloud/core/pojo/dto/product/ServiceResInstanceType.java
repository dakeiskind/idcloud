package com.h3c.idcloud.core.pojo.dto.product;

import java.io.Serializable;

public class ServiceResInstanceType implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资源实例类型代码
     */
    private String resInstanceType;

    /**
     * 服务ID
     */
    private Long serviceSid;

    /**
     * @return 资源实例类型代码
     */
    public String getResInstanceType() {
        return resInstanceType;
    }

    /**
     * @param resInstanceType 
	 *            资源实例类型代码
     */
    public void setResInstanceType(String resInstanceType) {
        this.resInstanceType = resInstanceType;
    }

    /**
     * @return 服务ID
     */
    public Long getServiceSid() {
        return serviceSid;
    }

    /**
     * @param serviceSid 
	 *            服务ID
     */
    public void setServiceSid(Long serviceSid) {
        this.serviceSid = serviceSid;
    }
}