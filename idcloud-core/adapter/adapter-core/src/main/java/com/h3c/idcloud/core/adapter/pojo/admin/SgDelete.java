package com.h3c.idcloud.core.adapter.pojo.admin;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class SgDelete extends Base {

    private String resId;
    private String securityGroupId;

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getSecurityGroupId() {
        return securityGroupId;
    }

    public void setSecurityGroupId(String securityGroupId) {
        this.securityGroupId = securityGroupId;
    }


}
