package com.h3c.idcloud.core.service.res.provider.task;

import com.h3c.idcloud.core.service.res.api.ResVcService;
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
public class ResVcSyncTask extends OneTimeTask<String> {
	@Autowired
	private ResVcService resVcService;

	public void action(String t) throws Exception {
		resVcService.getAllByVc(t);
	}

	public void before(String t) {
		super.doOneTimeLog("同步集群");
	}


	@Override
	public void after(String t) {

	}


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String t = QuartzUtil.getParams(context);
		String taskId = context.getJobDetail().getKey().getName();
		super.doRun(t);
		super.cancelTask(taskId);
	}
}
