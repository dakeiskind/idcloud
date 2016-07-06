package com.h3c.idcloud.core.service.system.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.TaskLogMapper;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.core.service.system.api.TaskLogService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class TaskLogServiceImpl implements TaskLogService {
	@Autowired
	private TaskLogMapper taskLogMapper;

	private static final Logger logger = LoggerFactory
			.getLogger(TaskLogServiceImpl.class);

	public int countByParams(Criteria example) {
		int count = this.taskLogMapper.countByParams(example);
		logger.debug("count: {}", count);
		return count;
	}

	public TaskLog selectByPrimaryKey(Long taskLogSid) {
		return this.taskLogMapper.selectByPrimaryKey(taskLogSid);
	}

	public List<TaskLog> selectByParams(Criteria example) {
		return this.taskLogMapper.selectByParams(example);
	}

	public int deleteByPrimaryKey(Long taskLogSid) {
		return this.taskLogMapper.deleteByPrimaryKey(taskLogSid);
	}

	public int updateByPrimaryKeySelective(TaskLog record) {
		return this.taskLogMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(TaskLog record) {
		return this.taskLogMapper.updateByPrimaryKey(record);
	}

	public int deleteByParams(Criteria example) {
		return this.taskLogMapper.deleteByParams(example);
	}

	public int updateByParamsSelective(TaskLog record, Criteria example) {
		return this.taskLogMapper.updateByParamsSelective(record,
				example.getCondition());
	}

	public int updateByParams(TaskLog record, Criteria example) {
		return this.taskLogMapper
				.updateByParams(record, example.getCondition());
	}

	public int insert(TaskLog record) {
		return this.taskLogMapper.insert(record);
	}

	public int insertSelective(TaskLog record) {
		return this.taskLogMapper.insertSelective(record);
	}

	@Override
	public int update(TaskLog record) {
		if (record.getTaskLogSid() != null)
			return this.taskLogMapper.updateByPrimaryKeySelective(record);
		else if (record.getTaskTarget() != null && record.getTaskType() != null) {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("taskTarget", record.getTaskTarget());
			condition.put("taskType", record.getTaskType());

			return this.taskLogMapper
					.updateByParamsSelective(record, condition);
		}

		return 0;
	}

	@Override
	public int logInsertSelective(TaskLog record) {
		return this.insertSelective(record);
	}

	@Override
	public int logUpdate(TaskLog record) {
		return this.taskLogMapper.logUpdateSelective(record);
	}
}