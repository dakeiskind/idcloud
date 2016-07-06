package com.h3c.idcloud.core.service.system.provider;


import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.SysTLogRecordMapper;
import com.h3c.idcloud.core.pojo.dto.system.SysTLogRecord;
import com.h3c.idcloud.core.service.system.api.SysTLogRecordService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class SysTLogRecordServiceImpl implements SysTLogRecordService {

    @Autowired
    private SysTLogRecordMapper sysTLogRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(SysTLogRecordServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.sysTLogRecordMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public SysTLogRecord selectByPrimaryKey(Long logSid) {
        return this.sysTLogRecordMapper.selectByPrimaryKey(logSid);
    }

    public List<SysTLogRecord> selectByParams(Criteria example) {
        return this.sysTLogRecordMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long logSid) {
        return this.sysTLogRecordMapper.deleteByPrimaryKey(logSid);
    }

    public int updateByPrimaryKeySelective(SysTLogRecord record) {
        return this.sysTLogRecordMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(SysTLogRecord record) {
        return this.sysTLogRecordMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.sysTLogRecordMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(SysTLogRecord record, Criteria example) {
        return this.sysTLogRecordMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(SysTLogRecord record, Criteria example) {
        return this.sysTLogRecordMapper.updateByParams(record, example.getCondition());
    }

    public int insert(SysTLogRecord record) {
        return this.sysTLogRecordMapper.insert(record);
    }

    public int insertSelective(SysTLogRecord record) {
        return this.sysTLogRecordMapper.insertSelective(record);
    }
}