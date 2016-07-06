package com.h3c.idcloud.core.pojo.vo.product;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ServiceCatalogVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 目录SID
     */
    private Long catalogSid;

    /**
     * 父目录名称
     */
    private String catalogParentName;
    
    /**
     * 目录名称
     */
    private String catalogName;

    /**
     * 描述
     */
    private String description;

    /**
     * 目录分类
     */
    private String catelogGroup;

    /**
     * 父级目录编号
     */
    private Long parentCatalogSid;

    /**
     * 图例
     */
    private String imagePath;
    
    /**
     * 前面展示图片
     */
    private String bgImagePath;

    /**
     * 所需租户
     */
    private String tanentId;
    
    /**
     * 状态
     */
    private String status;
    
    /**
     * 状态名称
     */
    private String statusName;

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
     * 值
     */
    private Long value;

    /**
     * 服务集合
     */
    private List<ServiceDefineVo> serviceDefineVoList;
    /**
     * @return 目录SID
     */
    public Long getCatalogSid() {
        return catalogSid;
    }

    /**
     * @param catalogSid 
	 *            目录SID
     */
    public void setCatalogSid(Long catalogSid) {
        this.catalogSid = catalogSid;
    }

    /**
     * @return 目录名称
     */
    public String getCatalogName() {
        return catalogName;
    }

    /**
     * @param catalogName 
	 *            目录名称
     */
    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
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
     * @return 目录分类
     */
    public String getCatelogGroup() {
        return catelogGroup;
    }

    /**
     * @param catelogGroup 
	 *            目录分类
     */
    public void setCatelogGroup(String catelogGroup) {
        this.catelogGroup = catelogGroup;
    }

    /**
     * @return 父级目录编号
     */
    public Long getParentCatalogSid() {
        return parentCatalogSid;
    }

    /**
     * @param parentCatalogSid 
	 *            父级目录编号
     */
    public void setParentCatalogSid(Long parentCatalogSid) {
        this.parentCatalogSid = parentCatalogSid;
    }

    /**
     * @return 图例
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath 
	 *            图例
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * @return 所需租户
     */
    public String getTanentId() {
        return tanentId;
    }

    /**
     * @param tanentId 
	 *            所需租户
     */
    public void setTanentId(String tanentId) {
        this.tanentId = tanentId;
    }

    /**
     * @return 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态
     */
    public void setStatus(String status) {
        this.status = status;
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

	public String getCatalogParentName() {
		return catalogParentName;
	}

	public void setCatalogParentName(String catalogParentName) {
		this.catalogParentName = catalogParentName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the serviceDefineList
	 */
	public List<ServiceDefineVo> getServiceDefineVoList() {
		return serviceDefineVoList;
	}

	/**
	 * @param serviceDefineVoList the serviceDefineList to set
	 */
	public void setServiceDefineVoList(List<ServiceDefineVo> serviceDefineVoList) {
		this.serviceDefineVoList = serviceDefineVoList;
	}

	public String getBgImagePath() {
		return bgImagePath;
	}

	public void setBgImagePath(String bgImagePath) {
		this.bgImagePath = bgImagePath;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}	
	
}