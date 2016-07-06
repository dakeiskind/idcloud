package com.h3c.idcloud.core.service.charge.api;

import com.h3c.idcloud.core.pojo.dto.charge.Bill;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;


public interface BillService {
    int countByParams(Criteria example);

    Bill selectByPrimaryKey(Long billSid);

    List<Bill> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long billSid);

    int updateByPrimaryKeySelective(Bill record);

    int updateByPrimaryKey(Bill record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Bill record, Criteria example);

    int updateByParams(Bill record, Criteria example);

    int insert(Bill record);

    int insertSelective(Bill record);
}