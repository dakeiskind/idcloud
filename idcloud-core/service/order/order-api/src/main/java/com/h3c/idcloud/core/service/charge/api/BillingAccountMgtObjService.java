package com.h3c.idcloud.core.service.charge.api;


import com.h3c.idcloud.core.pojo.dto.charge.BillingAccountMgtObj;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccountMgtObjKey;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface BillingAccountMgtObjService {
    int countByParams(Criteria example);

    BillingAccountMgtObj selectByPrimaryKey(BillingAccountMgtObjKey key);

    List<BillingAccountMgtObj> selectByParams(Criteria example);

    int deleteByPrimaryKey(BillingAccountMgtObjKey key);

    int updateByPrimaryKeySelective(BillingAccountMgtObj record);

    int updateByPrimaryKey(BillingAccountMgtObj record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(BillingAccountMgtObj record, Criteria example);

    int updateByParams(BillingAccountMgtObj record, Criteria example);

    int insert(BillingAccountMgtObj record);

    int insertSelective(BillingAccountMgtObj record);
}