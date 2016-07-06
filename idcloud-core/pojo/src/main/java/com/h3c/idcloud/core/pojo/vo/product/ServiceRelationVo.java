package com.h3c.idcloud.core.pojo.vo.product;

import java.io.Serializable;

public class ServiceRelationVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 原子服务ID
     */
    private Long atomServiceSid;

    /**
     * 复合服务ID
     */
    private Long compoundServiceSid;

    /**
     * @return 原子服务ID
     */
    public Long getAtomServiceSid() {
        return atomServiceSid;
    }

    /**
     * @param atomServiceSid 
	 *            原子服务ID
     */
    public void setAtomServiceSid(Long atomServiceSid) {
        this.atomServiceSid = atomServiceSid;
    }

    /**
     * @return 复合服务ID
     */
    public Long getCompoundServiceSid() {
        return compoundServiceSid;
    }

    /**
     * @param compoundServiceSid 
	 *            复合服务ID
     */
    public void setCompoundServiceSid(Long compoundServiceSid) {
        this.compoundServiceSid = compoundServiceSid;
    }
}