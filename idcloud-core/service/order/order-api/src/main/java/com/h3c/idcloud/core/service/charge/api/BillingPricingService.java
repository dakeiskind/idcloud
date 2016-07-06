package com.h3c.idcloud.core.service.charge.api;

import com.h3c.idcloud.core.pojo.dto.charge.BillingPricing;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;



public interface BillingPricingService {
    int countByParams(Criteria example);

    BillingPricing selectByPrimaryKey(Long billingPricingSid);

    List<BillingPricing> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long billingPricingSid);

    int updateByPrimaryKeySelective(BillingPricing record);

    int updateByPrimaryKey(BillingPricing record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(BillingPricing record, Criteria example);

    int updateByParams(BillingPricing record, Criteria example);

    int insert(BillingPricing record);

    int insertSelective(BillingPricing record);

    /**
     * 新增资费组合
     * @param record
     * @return
     */
    int addBillingPrice(BillingPricing record);
}