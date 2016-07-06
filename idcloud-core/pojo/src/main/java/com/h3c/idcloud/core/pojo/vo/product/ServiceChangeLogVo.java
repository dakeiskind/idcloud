package com.h3c.idcloud.core.pojo.vo.product;

import java.io.Serializable;
import java.util.Date;

public class ServiceChangeLogVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long cmSid;

    /**
     * 服务实例SID
     */
    private Long instanceSid;

    /**
     * 实例规格SID
     */
    private Long specVersion;

    private String cmSponsor;

    /**
     * 0 用户发起;1 闲置资源回收;2:IDC导入;3：遗留同步
     */
    private Long cmType;

    private String cmComments;

    private Date cmBeginTime;

    private Date cmEndTime;

    private Date sugTime;
    
    private String changeContent;

    /**
     * 0 待变更 1 变更ok 2 变更失败
     */
    private Long status;

    public Long getCmSid() {
        return cmSid;
    }

    public void setCmSid(Long cmSid) {
        this.cmSid = cmSid;
    }

    /**
     * @return 服务实例SID
     */
    public Long getInstanceSid() {
        return instanceSid;
    }

    /**
     * @param instanceSid 
	 *            服务实例SID
     */
    public void setInstanceSid(Long instanceSid) {
        this.instanceSid = instanceSid;
    }

   

    public String getCmSponsor() {
        return cmSponsor;
    }

    public void setCmSponsor(String cmSponsor) {
        this.cmSponsor = cmSponsor;
    }

    /**
     * @return 0 用户发起;1 闲置资源回收;2:IDC导入;3：遗留同步
     */
    public Long getCmType() {
        return cmType;
    }

    /**
     * @param cmType 
	 *            0 用户发起;1 闲置资源回收;2:IDC导入;3：遗留同步
     */
    public void setCmType(Long cmType) {
        this.cmType = cmType;
    }

    public String getCmComments() {
        return cmComments;
    }

    public void setCmComments(String cmComments) {
        this.cmComments = cmComments;
    }

    public Date getCmBeginTime() {
        return cmBeginTime;
    }

    public void setCmBeginTime(Date cmBeginTime) {
        this.cmBeginTime = cmBeginTime;
    }

    public Date getCmEndTime() {
        return cmEndTime;
    }

    public void setCmEndTime(Date cmEndTime) {
        this.cmEndTime = cmEndTime;
    }

    public Date getSugTime() {
        return sugTime;
    }

    public void setSugTime(Date sugTime) {
        this.sugTime = sugTime;
    }

    /**
     * @return 0 待变更 1 变更ok 2 变更失败
     */
    public Long getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            0 待变更 1 变更ok 2 变更失败
     */
    public void setStatus(Long status) {
        this.status = status;
    }

	public Long getSpecVersion() {
		return specVersion;
	}

	public void setSpecVersion(Long specVersion) {
		this.specVersion = specVersion;
	}

	public String getChangeContent() {
		return changeContent;
	}

	public void setChangeContent(String changeContent) {
		this.changeContent = changeContent;
	}
}