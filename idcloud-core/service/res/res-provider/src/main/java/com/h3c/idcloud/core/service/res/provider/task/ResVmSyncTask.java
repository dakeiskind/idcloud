package com.h3c.idcloud.core.service.res.provider.task;


import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.infra.task.base.OneTimeTask;
import com.h3c.idcloud.infra.task.util.QuartzUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * ResVmSyncTask.java
 *
 */
@Scope("prototype")
@Service
public class ResVmSyncTask extends OneTimeTask<String> {

	@Autowired
	private ResVmService resVmService;
	
	public void action(String t) throws Exception {
		resVmService.synaVmInfo(t);
	}

	public void before(String t) {
		super.doOneTimeLog("虚拟机信息同步");
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
