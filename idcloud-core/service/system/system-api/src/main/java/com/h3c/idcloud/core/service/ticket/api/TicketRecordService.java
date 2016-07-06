package com.h3c.idcloud.core.service.ticket.api;

import com.h3c.idcloud.core.pojo.dto.customer.TicketRecord;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * Created by Administrator on 2016/2/19.
 */
public interface TicketRecordService {

    int countByParams(Criteria example);

    TicketRecord selectByPrimaryKey(Long ticketRecordSid);

    List<TicketRecord> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long ticketRecordSid);

    int updateByPrimaryKeySelective(TicketRecord record);

    int updateByPrimaryKey(TicketRecord record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(TicketRecord record, Criteria example);

    int updateByParams(TicketRecord record, Criteria example);

    int insert(TicketRecord record);

    int insertSelective(TicketRecord record);
}
