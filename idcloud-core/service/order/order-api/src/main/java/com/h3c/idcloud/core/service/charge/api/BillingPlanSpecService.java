package com.h3c.idcloud.core.service.charge.api;

import com.h3c.idcloud.core.pojo.dto.charge.BillingPlanSpec;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface BillingPlanSpecService {
    int countByParams(Criteria example);

    BillingPlanSpec selectByPrimaryKey(Long billingPlanSpecSid);

    List<BillingPlanSpec> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long billingPlanSpecSid);

    int updateByPrimaryKeySelective(BillingPlanSpec record);

    int updateByPrimaryKey(BillingPlanSpec record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(BillingPlanSpec record, Criteria example);

    int updateByParams(BillingPlanSpec record, Criteria example);

    int insert(BillingPlanSpec record);

    int insertSelective(BillingPlanSpec record);
}