package com.h3c.idcloud.core.service.res.provider.task;


import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.service.res.api.ResVeService;
import com.h3c.idcloud.infra.task.base.QuartzTimedTask;
import com.h3c.idcloud.infra.task.constants.TaskConstants;
import com.h3c.idcloud.infra.task.util.QuartzUtil;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.User;
import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 虚拟化环境同步任务
 *
 * @author Chaohong.Mao
 */
@Scope("prototype")
@Service
public class ResVeSyncTask extends QuartzTimedTask<ResVe> {
    @Autowired
    private ResVeService resVeService;

    /**
     * 任务前置执行方法
     */
    public void before(ResVe t) {
        task.setTaskName("虚拟化平台同步");
        task.setTaskDetail("虚拟化平台同步开始");
        task.setUpdateCycle(t.getUpdateCycle());
        task.setTaskType(TaskConstants.TaskType.FIXED_RATE);
        User user = AuthUtil.getAuthUser();
        task.setCreatedBy(user.getAccount());
    }

    /**
     * 任务执行方法
     */
    public void action(ResVe t) throws Exception {
        if (WebConstants.VirtualPlatformType.HMC.equals(t.getVirtualPlatformType())) {
            resVeService.findAllByPowerVe(t);
        } else {
            resVeService.findAllByVe(t);
        }
    }


    /**
     * 任务后置执行方法
     */
    public void after(ResVe t) {
        // no-op
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ResVe t = QuartzUtil.getParams(context);
        String taskId = context.getJobDetail().getKey().getName();
        super.doRun(t, taskId);
    }
}
