package com.h3c.idcloud.core.service.system.api;


import com.h3c.idcloud.core.pojo.dto.system.LogRecord;

import java.util.List;

public interface LogRecordService {

    LogRecord selectByPrimaryKey(Long logSid);

    int deleteByPrimaryKey(Long logSid);

    int updateByPrimaryKeySelective(LogRecord record);

    int updateByPrimaryKey(LogRecord record);


    int insert(LogRecord record);

    int insertSelective(LogRecord record);
}