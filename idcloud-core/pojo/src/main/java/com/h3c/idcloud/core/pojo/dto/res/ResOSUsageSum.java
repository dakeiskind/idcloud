package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;

/**
 * 对象存储使用量统计
 *
 * @author xubei
 */
public class ResOSUsageSum implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 租户id
     */
    private Long account;

    /**
     * container统计
     */
    private Long containerCount;

    /**
     * 对象总数
     */
    private Long objectCount;

    /**
     * 对象存储的字节数
     */
    private Long bytes;

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public Long getContainerCount() {
        return containerCount;
    }

    public void setContainerCount(Long containerCount) {
        this.containerCount = containerCount;
    }

    public Long getObjectCount() {
        return objectCount;
    }

    public void setObjectCount(Long objectCount) {
        this.objectCount = objectCount;
    }

    public Long getBytes() {
        return bytes;
    }

    public void setBytes(Long bytes) {
        this.bytes = bytes;
    }

}