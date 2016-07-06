package com.h3c.idcloud.core.pojo.dto.product;

import java.io.Serializable;

public class MgtObjResKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long bizSid;

    private String resSetSid;

    public Long getBizSid() {
        return bizSid;
    }

    public void setBizSid(Long bizSid) {
        this.bizSid = bizSid;
    }

    public String getResSetSid() {
        return resSetSid;
    }

    public void setResSid(String resSetSid) {
        this.resSetSid = resSetSid;
    }

}