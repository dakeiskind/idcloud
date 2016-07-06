package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;

public class ResPoolVlanVs implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * VLAN池ID
     */
    private String resPoolSid;

    /**
     * 虚拟交换机ID
     */
    private String resVsSid;
    
    /**
     * 是否关联
     */
    private String relation;
    
    /**
     * 分布式交换机名称
     */
    private String resVsName;
    

    /**
     * @return VLAN池ID
     */
    public String getResPoolSid() {
        return resPoolSid;
    }

    /**
     * @param resPoolSid 
	 *            VLAN池ID
     */
    public void setResPoolSid(String resPoolSid) {
        this.resPoolSid = resPoolSid;
    }

    /**
     * @return 虚拟交换机ID
     */
    public String getResVsSid() {
        return resVsSid;
    }

    /**
     * @param resVsSid 
	 *            虚拟交换机ID
     */
    public void setResVsSid(String resVsSid) {
        this.resVsSid = resVsSid;
    }

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getResVsName() {
		return resVsName;
	}

	public void setResVsName(String resVsName) {
		this.resVsName = resVsName;
	}

}