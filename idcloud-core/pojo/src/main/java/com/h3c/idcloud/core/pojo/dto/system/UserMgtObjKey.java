package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;

public class UserMgtObjKey implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 管理对象SID
     */
    private Long mgtObjSid;

    /**
     * 用户SID
     */
    private Long userSid;
    
    private String mgtObjName;

    public String getMgtObjName() {
		return mgtObjName;
	}

	public void setMgtObjName(String mgtObjName) {
		this.mgtObjName = mgtObjName;
	}

	/**
     * @return 管理对象SID
     */
    public Long getMgtObjSid() {
        return mgtObjSid;
    }

    /**
     * @param mgtObjSid 
	 *            管理对象SID
     */
    public void setMgtObjSid(Long mgtObjSid) {
        this.mgtObjSid = mgtObjSid;
    }

    /**
     * @return 用户SID
     */
    public Long getUserSid() {
        return userSid;
    }

    /**
     * @param userSid 
	 *            用户SID
     */
    public void setUserSid(Long userSid) {
        this.userSid = userSid;
    }
}