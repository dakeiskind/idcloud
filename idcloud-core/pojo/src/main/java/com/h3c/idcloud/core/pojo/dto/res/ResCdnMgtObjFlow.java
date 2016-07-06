/*
 * ts-resource
 * 概要：ResCdnMgtObjFlow.java
 *
 * 创建人：yxu
 * 创建日：2015-4-20
 * 更新履历
 * 2015-4-20  yxu  创建
 *
 * @(#)ResCdnMgtObjFlow.java
 *
 * Copyright (c) 2014 Hewlett Packard Corporation, All rights reserved.
 */
package com.h3c.idcloud.core.pojo.dto.res;

import java.util.List;

/**
 * ResCdnMgtObjFlow.java
 *
 * @author yxu
 */
public class ResCdnMgtObjFlow {

    /**
     * 管理对象Sid
     */
    private String mgtObjSid;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 总流量
     */
    private String totalFlow;

    /**
     * 租户下域名信息
     */
    private List<ResCdnMgtObjDomainFlow> domainFlows;

    /**
     * @return the mgtObjSid
     */
    public String getMgtObjSid() {
        return mgtObjSid;
    }

    /**
     * @param mgtObjSid the mgtObjSid to set
     */
    public void setMgtObjSid(String mgtObjSid) {
        this.mgtObjSid = mgtObjSid;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the totalFlow
     */
    public String getTotalFlow() {
        return totalFlow;
    }

    /**
     * @param totalFlow the totalFlow to set
     */
    public void setTotalFlow(String totalFlow) {
        this.totalFlow = totalFlow;
    }

    /**
     * @return the domainFlows
     */
    public List<ResCdnMgtObjDomainFlow> getDomainFlows() {
        return domainFlows;
    }

    /**
     * @param domainFlows the domainFlows to set
     */
    public void setDomainFlows(List<ResCdnMgtObjDomainFlow> domainFlows) {
        this.domainFlows = domainFlows;
    }

}
