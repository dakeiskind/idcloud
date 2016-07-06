package com.h3c.idcloud.core.service.order.api;

import com.h3c.idcloud.core.pojo.dto.order.OrderDetail;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;


public interface OrderDetailService {
    int countByParams(Criteria example);

    OrderDetail selectByPrimaryKey(Long detailSid);

    List<OrderDetail> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long detailSid);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(OrderDetail record, Criteria example);

    int updateByParams(OrderDetail record, Criteria example);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);
}