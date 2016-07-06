package com.h3c.idcloud.infra.task.base;

import org.quartz.Job;

/**
 * 接口 Task 基础类.
 *
 * @param <T> the type parameter
 */
public interface TaskAction<T> extends Job {
	/**
	 * Task执行逻辑.
	 *
	 * @param t 参数
	 * @throws Exception the exception
	 */
	void action(T t) throws Exception;
}
