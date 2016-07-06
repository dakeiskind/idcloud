package com.h3c.idcloud.core.service.system.api;

import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;



public interface TaskLogService {
	int countByParams(Criteria example);

	TaskLog selectByPrimaryKey(Long taskLogSid);

	List<TaskLog> selectByParams(Criteria example);

	int deleteByPrimaryKey(Long taskLogSid);

	int updateByPrimaryKeySelective(TaskLog record);

	int updateByPrimaryKey(TaskLog record);

	int deleteByParams(Criteria example);

	int updateByParamsSelective(TaskLog record, Criteria example);

	int updateByParams(TaskLog record, Criteria example);

	/**
	 * 更新任务日志，会以taskLogSid或者taskType和taskTarget为主键更新
	 * 
	 * @param record
	 * @return
	 */
	int update(TaskLog record);

	int insert(TaskLog record);

	int insertSelective(TaskLog record);

	int logInsertSelective(TaskLog record);

	int logUpdate(TaskLog record);
}