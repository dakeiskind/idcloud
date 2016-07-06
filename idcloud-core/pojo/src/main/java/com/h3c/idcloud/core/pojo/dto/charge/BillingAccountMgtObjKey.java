package com.h3c.idcloud.core.pojo.dto.charge;

import java.io.Serializable;

public class BillingAccountMgtObjKey implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账户SID
     */
    private Long accountSid;

    /**
     * 管理对象SID
     */
    private Long mgtObjSid;

    /**
     * @return 账户SID
     */
    public Long getAccountSid() {
        return accountSid;
    }

    /**
     * @param accountSid 
	 *            账户SID
     */
    public void setAccountSid(Long accountSid) {
        this.accountSid = accountSid;
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
}