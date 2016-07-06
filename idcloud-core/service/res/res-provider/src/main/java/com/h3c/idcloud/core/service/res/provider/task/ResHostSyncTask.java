package com.h3c.idcloud.core.service.res.provider.task;

import com.h3c.idcloud.core.service.res.api.ResHostService;
import com.h3c.idcloud.infra.task.base.OneTimeTask;
import com.h3c.idcloud.infra.task.util.QuartzUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 虚拟化环境同步任务
 * 
 */
@Scope("prototype")
@Service
public class ResHostSyncTask extends OneTimeTask<String> {
	@Autowired
	private ResHostService resHostService;

	public void action(String t) throws Exception {
		resHostService.getAllByHost(t);
	}

	public void before(String t) {
		super.doOneTimeLog("主机信息同步");
	}

	public void after(String t) {
		// no-op
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String t = QuartzUtil.getParams(context);
		String taskId = context.getJobDetail().getKey().getName();
		super.doRun(t);
		super.cancelTask(taskId);
	}
}
