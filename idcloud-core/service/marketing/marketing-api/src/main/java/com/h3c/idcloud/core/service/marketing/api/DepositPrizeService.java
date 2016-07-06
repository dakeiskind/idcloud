package com.h3c.idcloud.core.service.marketing.api;


import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.marketing.DepositPrize;

import java.util.List;

public interface DepositPrizeService {
    int countByParams(Criteria example);

    DepositPrize selectByPrimaryKey(Long depositPrizeSid);

    List<DepositPrize> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long depositPrizeSid);

    int updateByPrimaryKeySelective(DepositPrize record);

    int updateByPrimaryKey(DepositPrize record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(DepositPrize record, Criteria example);

    int updateByParams(DepositPrize record, Criteria example);

    int insert(DepositPrize record);

    int insertSelective(DepositPrize record);
}