package com.h3c.idcloud.core.service.ticket.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.customer.dao.TicketRecordMapper;
import com.h3c.idcloud.core.pojo.dto.customer.TicketRecord;
import com.h3c.idcloud.core.service.ticket.api.TicketRecordService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2016/2/19.
 */
@Service(version = "1.0.0")
@Component
public class TicketRecordServiceImpl implements TicketRecordService {

    @Autowired
    private TicketRecordMapper ticketRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(TicketRecordServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.ticketRecordMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public TicketRecord selectByPrimaryKey(Long ticketRecordSid) {
        return this.ticketRecordMapper.selectByPrimaryKey(ticketRecordSid);
    }

    public List<TicketRecord> selectByParams(Criteria example) {
        return this.ticketRecordMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long ticketRecordSid) {
        return this.ticketRecordMapper.deleteByPrimaryKey(ticketRecordSid);
    }

    public int updateByPrimaryKeySelective(TicketRecord record) {
        return this.ticketRecordMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(TicketRecord record) {
        return this.ticketRecordMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.ticketRecordMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(TicketRecord record, Criteria example) {
        return this.ticketRecordMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(TicketRecord record, Criteria example) {
        return this.ticketRecordMapper.updateByParams(record, example.getCondition());
    }

    public int insert(TicketRecord record) {
        return this.ticketRecordMapper.insert(record);
    }

    public int insertSelective(TicketRecord record) {
        return this.ticketRecordMapper.insertSelective(record);
    }
}
