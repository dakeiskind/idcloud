package com.h3c.idcloud.infra.log.task;

import com.h3c.idcloud.infrastructure.common.util.SpringContextHolder;
import com.h3c.idcloud.infra.log.LoggerFactory;
import com.h3c.idcloud.infra.log.LoggerTypeEnum;
import org.springframework.stereotype.Service;

@Service
public class TaskLoggerFactory implements LoggerFactory<TaskLogger> {
	public TaskLogger getLogger(LoggerTypeEnum loggerType) {

		TaskLogger logger = (TaskLogger) SpringContextHolder
				.getApplicationContext().getBean("taskLogger");
		if (logger != null)
			logger.init(loggerType);

		return logger;
	}
}
