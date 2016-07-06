package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;

public class OrgbizKey implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 业务ID
     */
    private Long bizSid;

    /**
     * 组织ID
     */
    private Long orgSid;

    /**
     * @return 业务ID
     */
    public Long getBizSid() {
        return bizSid;
    }

    /**
     * @param bizSid 
	 *            业务ID
     */
    public void setBizSid(Long bizSid) {
        this.bizSid = bizSid;
    }

    /**
     * @return 组织ID
     */
    public Long getOrgSid() {
        return orgSid;
    }

    /**
     * @param orgSid 
	 *            组织ID
     */
    public void setOrgSid(Long orgSid) {
        this.orgSid = orgSid;
    }
}