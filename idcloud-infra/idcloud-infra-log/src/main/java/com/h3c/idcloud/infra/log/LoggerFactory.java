package com.h3c.idcloud.infra.log;

public interface LoggerFactory<T> {
	T getLogger(LoggerTypeEnum loggerType);
}
