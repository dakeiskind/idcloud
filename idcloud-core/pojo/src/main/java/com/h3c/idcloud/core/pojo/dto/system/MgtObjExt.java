package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;

public class MgtObjExt implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 扩展属性ID
     */
    private Long extendSid;

    /**
     * 管理对象ID
     */
    private Long mgtObjSid;

    /**
     * 属性KEY
     */
    private String attrKey;

    /**
     * 属性值
     */
    private String attrValue;
    
    private String attrName;
    
    private String fileName;

    public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	/**
     * @return 扩展属性ID
     */
    public Long getExtendSid() {
        return extendSid;
    }

    /**
     * @param extendSid 
	 *            扩展属性ID
     */
    public void setExtendSid(Long extendSid) {
        this.extendSid = extendSid;
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
     * @return 属性KEY
     */
    public String getAttrKey() {
        return attrKey;
    }

    /**
     * @param attrKey 
	 *            属性KEY
     */
    public void setAttrKey(String attrKey) {
        this.attrKey = attrKey;
    }

    /**
     * @return 属性值
     */
    public String getAttrValue() {
        return attrValue;
    }

    /**
     * @param attrValue 
	 *            属性值
     */
    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }
}