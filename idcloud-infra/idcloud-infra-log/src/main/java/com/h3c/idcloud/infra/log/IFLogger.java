package com.h3c.idcloud.infra.log;

import org.slf4j.Logger;

public class IFLogger {
	private Logger logger;
	final static String FQCN = IFLogger.class.getName();

	public IFLogger() {
		logger = org.slf4j.LoggerFactory.getLogger("if");
	}

	public void info(String log) {
		logger.info(log);
	}

	public void debug(String log) {
		logger.debug(log);
	}

	public void error(String log) {
		logger.error(log);
	}
}
