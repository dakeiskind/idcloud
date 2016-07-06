package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

/**
 * 对象存储使用量信息
 *
 * @author xubei
 */
public class ResObjectStorageUsage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 租户id
     */
    private Long account;

    /**
     * 创建日期
     */
    private Date createDt;

    /**
     * 统计日期
     */
    private Date countDt;

    /**
     * container总数
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getCountDt() {
        return countDt;
    }

    public void setCountDt(Date countDt) {
        this.countDt = countDt;
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