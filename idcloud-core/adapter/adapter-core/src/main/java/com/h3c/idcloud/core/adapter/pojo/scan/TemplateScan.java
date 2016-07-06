package com.h3c.idcloud.core.adapter.pojo.scan;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class TemplateScan extends Base {
    private String nimIp;
    private String nimUser;
    private String nimPassword;

    public String getNimIp() {
        return nimIp;
    }

    public void setNimIp(String nimIp) {
        this.nimIp = nimIp;
    }

    public String getNimUser() {
        return nimUser;
    }

    public void setNimUser(String nimUser) {
        this.nimUser = nimUser;
    }

    public String getNimPassword() {
        return nimPassword;
    }

    public void setNimPassword(String nimPassword) {
        this.nimPassword = nimPassword;
    }
}
