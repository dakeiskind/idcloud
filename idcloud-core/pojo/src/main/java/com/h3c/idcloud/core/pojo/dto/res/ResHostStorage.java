package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;

public class ResHostStorage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主机资源ID
     */
    private String resHostSid;

    /**
     * 块存储资源ID
     */
    private String resStorageSid;

    /**
     * @return 主机资源ID
     */
    public String getResHostSid() {
        return resHostSid;
    }

    /**
     * @param resHostSid 主机资源ID
     */
    public void setResHostSid(String resHostSid) {
        this.resHostSid = resHostSid;
    }

    /**
     * @return 块存储资源ID
     */
    public String getResStorageSid() {
        return resStorageSid;
    }

    /**
     * @param resStorageSid 块存储资源ID
     */
    public void setResStorageSid(String resStorageSid) {
        this.resStorageSid = resStorageSid;
    }
}