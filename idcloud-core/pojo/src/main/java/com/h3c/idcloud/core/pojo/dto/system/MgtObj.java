package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class MgtObj implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 管理对象ID
     */
    private Long mgtObjSid;
    
    /**
     * 管理对象对应的账户SID
     */
    private Long accountSid;

    /**
     * 管理对象名称
     */
    private String name;
    
    /**
     * 账户余额
     */
    private double balance;
    
    private String status;
    
    /**
     * 账户状态
     */
    private String statusName;

    /**
     * 上级管理对象ID
     */
    private Long parentId;

    /**
     * 管理对象对象描述
     */
    private String description;

    /**
     * 管理对象级别
     */
    private Long level;

    /**
     * 管理对象对象描述
     */
    private String uuid;
    
    /**
     * 结算截止时间
     */
    private String billEndTime;
    
    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdDt;

    /**
     * 修改人
     */
    private String updatedBy;

    /**
     * 修改时间
     */
    private Date updatedDt;

    /**
     * 版本号
     */
    private Long version;
    
    /** 其他功能属性 start */
    
    /** 父管理对象名称 */
    private String parentName;
    
    /** 管理对象扩展属性 */
    private List<MgtObjExt> mgtObjExts;
    
    /** 获取计算资源数据模式(0 显示信息和选择信息  1 显示信息  2 选择信息 )*/
    private String mode;

    /** 其他功能属性end  */
    
    /** 扩展属性的json  **/
    private String extJson;
    
    /**配额信息**/
    private List<Quota> mgtQuotas;
    
    /**对应的项目经理的id**/
    private String userSid;
    
    /**对应的经理名称**/
    private String managerName;
    
    /**是否在提醒时间内         1：在；-1：已过期；0：不在**/
    private Integer inNoticeTime;
    
    private String mgtObjIcon;
    
    private String projectStartTime;
    
    private String projectEndTime;

    public String getProjectStartTime() {
		return projectStartTime;
	}

	public void setProjectStartTime(String projectStartTime) {
		this.projectStartTime = projectStartTime;
	}

	public String getProjectEndTime() {
		return projectEndTime;
	}

	public void setProjectEndTime(String projectEndTime) {
		this.projectEndTime = projectEndTime;
	}

	public String getMgtObjIcon() {
		return mgtObjIcon;
	}

	public void setMgtObjIcon(String mgtObjIcon) {
		this.mgtObjIcon = mgtObjIcon;
	}

	public Integer getInNoticeTime() {
		return inNoticeTime;
	}

	public void setInNoticeTime(Integer inNoticeTime) {
		this.inNoticeTime = inNoticeTime;
	}

	public String getExtJson() {
		return extJson;
	}

	public void setExtJson(String extJson) {
		this.extJson = extJson;
	}

	/**
     * @return 管理对象ID
     */
    public Long getMgtObjSid() {
        return mgtObjSid;
    }

    /**
     * @param mgtObjSid 
	 *            管理对象ID
     */
    public void setMgtObjSid(Long mgtObjSid) {
        this.mgtObjSid = mgtObjSid;
    }

    /**
     * @return 管理对象名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            管理对象名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 上级管理对象ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId 
	 *            上级管理对象ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * @return 管理对象对象描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 
	 *            管理对象对象描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return 管理对象级别
     */
    public Long getLevel() {
        return level;
    }

    /**
     * @param level 
	 *            管理对象级别
     */
    public void setLevel(Long level) {
        this.level = level;
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
     * @return 修改人
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy 
	 *            修改人
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return 修改时间
     */
    public Date getUpdatedDt() {
        return updatedDt;
    }

    /**
     * @param updatedDt 
	 *            修改时间
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

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<MgtObjExt> getMgtObjExts() {
		return mgtObjExts;
	}

	public void setMgtObjExts(List<MgtObjExt> mgtObjExts) {
		this.mgtObjExts = mgtObjExts;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getBillEndTime() {
		return billEndTime;
	}

	public void setBillEndTime(String billEndTime) {
		this.billEndTime = billEndTime;
	}

	public List<Quota> getMgtQuotas() {
		return mgtQuotas;
	}

	public void setMgtQuotas(List<Quota> mgtQuotas) {
		this.mgtQuotas = mgtQuotas;
	}

	public String getUserSid() {
		return userSid;
	}

	public void setUserSid(String userSid) {
		this.userSid = userSid;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public Long getAccountSid() {
		return accountSid;
	}

	public void setAccountSid(Long accountSid) {
		this.accountSid = accountSid;
	}

	
}