package com.h3c.idcloud.core.pojo.dto.product;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ServiceTemplate implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 模块SID
     */
    private Long templateSid;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板描述
     */
    private String description;

    /**
     * 服务SID
     */
    private Long serviceSid;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 到期时间
     */
    private Date expiringDate;

    /**
     * 图片路径
     */
    private String imagePath;

    /**
     * 资费计划SID
     */
    private Long pricingSid;

    /**
     * 模板状态
     */
    private String templateStatus;
    
    /**
     * 模板状态名称
     */
    private String templateStatusName;

    /**
     * 资费计划SID
     */
    private Long billingPlanSid;
    
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
     * 模板规格集合
     */
    private List<ServiceTemplateSpec> specifications;
    
    /**
     * 服务规格集合
     */
    private List<ServiceSpec> serviceSpecs;
    
    /**
     * @return 模块SID
     */
    public Long getTemplateSid() {
        return templateSid;
    }

    /**
     * @param templateSid 
	 *            模块SID
     */
    public void setTemplateSid(Long templateSid) {
        this.templateSid = templateSid;
    }

    /**
     * @return 模板名称
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * @param templateName 
	 *            模板名称
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * @return 模板描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 
	 *            模板描述
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return 到期时间
     */
    public Date getExpiringDate() {
        return expiringDate;
    }

    /**
     * @param expiringDate 
	 *            到期时间
     */
    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }

    /**
     * @return 图片路径
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath 
	 *            图片路径
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * @return 资费计划SID
     */
    public Long getPricingSid() {
        return pricingSid;
    }

    /**
     * @param pricingSid 
	 *            资费计划SID
     */
    public void setPricingSid(Long pricingSid) {
        this.pricingSid = pricingSid;
    }

    /**
     * @return 模板状态
     */
    public String getTemplateStatus() {
        return templateStatus;
    }

    /**
     * @param templateStatus 
	 *            模板状态
     */
    public void setTemplateStatus(String templateStatus) {
        this.templateStatus = templateStatus;
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

	public String getTemplateStatusName() {
		return templateStatusName;
	}

	public void setTemplateStatusName(String templateStatusName) {
		this.templateStatusName = templateStatusName;
	}

	/**
	 * @return the specifications
	 */
	public List<ServiceTemplateSpec> getSpecifications() {
		return specifications;
	}

	/**
	 * @param specifications the specifications to set
	 */
	public void setSpecifications(List<ServiceTemplateSpec> specifications) {
		this.specifications = specifications;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Long getBillingPlanSid() {
		return billingPlanSid;
	}

	public void setBillingPlanSid(Long billingPlanSid) {
		this.billingPlanSid = billingPlanSid;
	}

	public List<ServiceSpec> getServiceSpecs() {
		return serviceSpecs;
	}

	public void setServiceSpecs(List<ServiceSpec> serviceSpecs) {
		this.serviceSpecs = serviceSpecs;
	}

}