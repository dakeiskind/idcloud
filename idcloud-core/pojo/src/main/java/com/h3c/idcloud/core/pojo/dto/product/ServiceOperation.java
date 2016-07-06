package com.h3c.idcloud.core.pojo.dto.product;

import java.io.Serializable;
import java.util.Date;

public class ServiceOperation implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 操作SID
     */
    private Long operationSid;

    /**
     * 服务SID
     */
    private Long serviceSid;

    /**
     * 操作名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 类型
     */
    private String type;

    /**
     * WS定义
     */
    private String wsDefinition;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdDt;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private Date updatedDt;

    /**
     * 版本号
     */
    private Long version;
    
    /**
     * uid
     */
    private String uid;

    /**
     * @return 操作SID
     */
    public Long getOperationSid() {
        return operationSid;
    }

    /**
     * @param operationSid 
	 *            操作SID
     */
    public void setOperationSid(Long operationSid) {
        this.operationSid = operationSid;
    }

    /**
     * @return 服务SID
     */
    public Long getServiceSid() {
        return serviceSid;
    }

    /**
     * @param serviceSid 
	 *            服务SID
     */
    public void setServiceSid(Long serviceSid) {
        this.serviceSid = serviceSid;
    }

    /**
     * @return 操作名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            操作名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 
	 *            描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return 类型
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return WS定义
     */
    public String getWsDefinition() {
        return wsDefinition;
    }

    /**
     * @param wsDefinition 
	 *            WS定义
     */
    public void setWsDefinition(String wsDefinition) {
        this.wsDefinition = wsDefinition;
    }

    /**
     * @return 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy 
	 *            创建人
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return 创建时间
     */
    public Date getCreatedDt() {
        return createdDt;
    }

    /**
     * @param createdDt 
	 *            创建时间
     */
    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    /**
     * @return 更新人
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy 
	 *            更新人
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return 更新时间
     */
    public Date getUpdatedDt() {
        return updatedDt;
    }

    /**
     * @param updatedDt 
	 *            更新时间
     */
    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    /**
     * @return 版本号
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @param version 
	 *            版本号
     */
    public void setVersion(Long version) {
        this.version = version;
    }

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
    
    
}