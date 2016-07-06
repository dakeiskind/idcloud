package com.h3c.idcloud.core.service.charge.api;

import com.h3c.idcloud.core.pojo.dto.charge.BillingPricingDetail;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;



public interface BillingPricingDetailService {
    int countByParams(Criteria example);

    BillingPricingDetail selectByPrimaryKey(Long pricingDetailSid);

    List<BillingPricingDetail> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long pricingDetailSid);

    int updateByPrimaryKeySelective(BillingPricingDetail record);

    int updateByPrimaryKey(BillingPricingDetail record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(BillingPricingDetail record, Criteria example);

    int updateByParams(BillingPricingDetail record, Criteria example);

    int insert(BillingPricingDetail record);

    int insertSelective(BillingPricingDetail record);
}