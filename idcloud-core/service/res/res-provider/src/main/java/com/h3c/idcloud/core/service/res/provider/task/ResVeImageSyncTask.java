package com.h3c.idcloud.core.service.res.provider.task;

import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.service.res.api.ResImageService;
import com.h3c.idcloud.infra.task.base.OneTimeTask;
import com.h3c.idcloud.infra.task.util.QuartzUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 虚拟化环境虚拟机模板的同步任务，立刻执行并且只执行一次
 * 
 */
@Scope("prototype")
@Service
public class ResVeImageSyncTask extends OneTimeTask<ResVe> {
	@Autowired
	private ResImageService resImageService;

	public void action(ResVe t) throws Exception {
		resImageService.getAllByImage(t);
	}

	public void before(ResVe t) {
		super.doOneTimeLog("虚拟机模板同步");
	}
	
	public void after(ResVe t) {
		// no-op
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ResVe t = QuartzUtil.getParams(context);
		String taskId = context.getJobDetail().getKey().getName();
		super.doRun(t);
		super.cancelTask(taskId);
	}


}
