package com.h3c.idcloud.core.service.order.api;

import com.h3c.idcloud.core.pojo.dto.order.OrderApvRecord;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;


public interface OrderApvRecordService {
    int countByParams(Criteria example);

    OrderApvRecord selectByPrimaryKey(Long apvRecordSid);

    List<OrderApvRecord> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long apvRecordSid);

    int updateByPrimaryKeySelective(OrderApvRecord record);

    int updateByPrimaryKey(OrderApvRecord record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(OrderApvRecord record, Criteria example);

    int updateByParams(OrderApvRecord record, Criteria example);

    int insert(OrderApvRecord record);

    int insertSelective(OrderApvRecord record);
    
    boolean insertProcessOrderApv(String orderId, Long serviceSid, String checkStatus, String Checkcomments);
}