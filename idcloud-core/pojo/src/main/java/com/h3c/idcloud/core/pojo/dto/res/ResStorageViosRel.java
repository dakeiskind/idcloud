package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;

public class ResStorageViosRel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 存储资源ID
     */
    private String resStorageSid;

    private String resViosSid;

    /**
     * @return 存储资源ID
     */
    public String getResStorageSid() {
        return resStorageSid;
    }

    /**
     * @param resStorageSid 存储资源ID
     */
    public void setResStorageSid(String resStorageSid) {
        this.resStorageSid = resStorageSid;
    }

    public String getResViosSid() {
        return resViosSid;
    }

    public void setResViosSid(String resViosSid) {
        this.resViosSid = resViosSid;
    }
}