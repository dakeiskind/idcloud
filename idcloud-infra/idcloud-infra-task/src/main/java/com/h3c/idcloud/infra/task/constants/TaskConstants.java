package com.h3c.idcloud.infra.task.constants;

/**
 * 资源系统常量
 */
public interface TaskConstants {

	String TASK_PARAM_KEY = "data";

	/** 任务类型代码 */
	interface TaskType {
		/** 固定延迟周期 */
		String FIXED_DELAY = "01";

		/** 固定周期 */
		String FIXED_RATE = "02";

		/** cron */
		String CRON = "03";

		/** 一次性 */
		String ONE_OFF = "04";
	}

	/** 任务状态代码 */
	interface TaskStatus {
		/** 等待 */
		String WAITING = "01";

		/** 正在运行 */
		String RUNNING = "02";

		/** 取消 */
		String CANCEL = "03";

		/** 完成 */
		String DONE = "04";

		/** 错误 */
		String ERROR = "09";
	}
}
