package com.h3c.idcloud.core.service.charge.api;

import com.h3c.idcloud.core.pojo.dto.charge.BillingType;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;



public interface BillingTypeService {
    int countByParams(Criteria example);

    BillingType selectByPrimaryKey(String billingType);

    List<BillingType> selectByParams(Criteria example);

    int deleteByPrimaryKey(String billingType);

    int updateByPrimaryKeySelective(BillingType record);

    int updateByPrimaryKey(BillingType record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(BillingType record, Criteria example);

    int updateByParams(BillingType record, Criteria example);

    int insert(BillingType record);

    int insertSelective(BillingType record);
}