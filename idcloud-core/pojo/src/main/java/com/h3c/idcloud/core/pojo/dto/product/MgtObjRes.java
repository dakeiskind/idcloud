package com.h3c.idcloud.core.pojo.dto.product;

import java.io.Serializable;

public class MgtObjRes extends MgtObjResKey implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 集群、主机、存储、交换机、路由器、Vlan
     */
    private String resSetType;

    /**
     * 0计算资源、1网络资源、2存储资源
     */
    private String resCategory;
    
    private String bizText;
    
    private String operate;

	private String resVeSid;
	
    private int count;
    
    public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	/**
     * @return 集群、主机、存储、交换机、路由器、Vlan
     */
    public String getResSetType() {
        return resSetType;
    }

    /**
     * @param resSetType
	 *            集群、主机、存储、交换机、路由器、Vlan
     */
    public void setResSetType(String resSetType) {
        this.resSetType = resSetType;
    }

    /**
     * @return 0计算资源、1网络资源、2存储资源
     */
    public String getResCategory() {
        return this.resCategory;
    }

    /**
     * @param resCategory 
	 *            0计算资源、1网络资源、2存储资源
     */
    public void setResCategory(String resCategory) {
        this.resCategory = resCategory;
    }

	public String getBizText() {
		return bizText;
	}

	public void setBizText(String bizText) {
		this.bizText = bizText;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getResVeSid() {
		return resVeSid;
	}

	public void setResVeSid(String resVeSid) {
		this.resVeSid = resVeSid;
	}
	
}