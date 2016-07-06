package com.h3c.idcloud.core.service.system.api;


import com.h3c.idcloud.core.pojo.dto.system.SysTLogRecord;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface SysTLogRecordService {
    int countByParams(Criteria example);

    SysTLogRecord selectByPrimaryKey(Long logSid);

    List<SysTLogRecord> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long logSid);

    int updateByPrimaryKeySelective(SysTLogRecord record);

    int updateByPrimaryKey(SysTLogRecord record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(SysTLogRecord record, Criteria example);

    int updateByParams(SysTLogRecord record, Criteria example);

    int insert(SysTLogRecord record);

    int insertSelective(SysTLogRecord record);
}