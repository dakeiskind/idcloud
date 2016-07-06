/*
 * ts-resource
 * 概要：ResCdnMgtObjDomainFlow.java
 *
 * 创建人：yxu
 * 创建日：2015-4-20
 * 更新履历
 * 2015-4-20  yxu  创建
 *
 * @(#)ResCdnMgtObjDomainFlow.java
 *
 * Copyright (c) 2014 Hewlett Packard Corporation, All rights reserved.
 */
package com.h3c.idcloud.core.pojo.dto.res;

/**
 * ResCdnMgtObjDomainFlow.java
 *
 * @author yxu
 */
public class ResCdnMgtObjDomainFlow {

    /**
     * 域名
     */
    private String domain;

    /**
     * 域名流量
     */
    private String domainFlow;

    /**
     * 获得 domain.
     *
     * @return the domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * 设定 domain.
     *
     * @param domain the domain to set
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * 获得 domain flow.
     *
     * @return the domainFlow
     */
    public String getDomainFlow() {
        return domainFlow;
    }

    /**
     * 设定 domain flow.
     *
     * @param domainFlow the domainFlow to set
     */
    public void setDomainFlow(String domainFlow) {
        this.domainFlow = domainFlow;
    }

}
