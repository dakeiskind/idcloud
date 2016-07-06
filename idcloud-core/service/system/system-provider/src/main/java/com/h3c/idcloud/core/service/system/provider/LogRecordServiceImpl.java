package com.h3c.idcloud.core.service.system.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.LogRecordMapper;
import com.h3c.idcloud.core.pojo.dto.system.LogRecord;

import java.util.List;

import com.h3c.idcloud.core.service.system.api.LogRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class LogRecordServiceImpl implements LogRecordService {
    @Autowired
    private LogRecordMapper logRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(LogRecordServiceImpl.class);


    public LogRecord selectByPrimaryKey(Long logSid) {
        return this.logRecordMapper.selectByPrimaryKey(logSid);
    }

    public int deleteByPrimaryKey(Long logSid) {
        return this.logRecordMapper.deleteByPrimaryKey(logSid);
    }

    public int updateByPrimaryKeySelective(LogRecord record) {
        return this.logRecordMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(LogRecord record) {
        return this.logRecordMapper.updateByPrimaryKey(record);
    }



    public int insert(LogRecord record) {
        return this.logRecordMapper.insert(record);
    }

    public int insertSelective(LogRecord record) {
        return this.logRecordMapper.insertSelective(record);
    }
}