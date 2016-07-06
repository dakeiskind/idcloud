package com.h3c.idcloud.core.service.charge.api;

import com.h3c.idcloud.core.pojo.dto.charge.BillDetail;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;



public interface BillDetailService {
    int countByParams(Criteria example);

    BillDetail selectByPrimaryKey(Long billDetailSid);

    List<BillDetail> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long billDetailSid);

    int updateByPrimaryKeySelective(BillDetail record);

    int updateByPrimaryKey(BillDetail record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(BillDetail record, Criteria example);

    int updateByParams(BillDetail record, Criteria example);

    int insert(BillDetail record);

    int insertSelective(BillDetail record);
}