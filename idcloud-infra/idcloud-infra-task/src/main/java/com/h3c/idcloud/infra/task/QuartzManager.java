package com.h3c.idcloud.infra.task;

import com.h3c.idcloud.infra.task.base.TaskException;
import com.h3c.idcloud.infra.task.constants.TaskConstants;
import com.h3c.idcloud.infra.task.util.QuartzUtil;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

/**
 * 定时任务管理类
 *
 * @author Administrator
 */
public class QuartzManager {

    /**
     * Job默认组
     */
    private static final String DEFAULT_JOB_GROUP = "DEFAULT_JOB_GROUP";
    /**
     * Trigger默认组
     */
    private static final String DEFAULT_TRIGGER_GROUP = "DEFAULT_TRIGGER_GROUP";
    /**
     * Quartz 调度器
     */
    private Scheduler scheduler;

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 添加一个定时任务.<br/>
     * 触发器名默认为任务名.<br/>
     * 分组名为默认分组.
     *
     * @param jobName 任务名
     * @param clazz   任务
     * @param time    时间设置
     * @param params  job任务需要的参数
     */
    public void addJob(String jobName, Class<? extends Job> clazz, String time, Object params) {
        addJob(jobName, DEFAULT_JOB_GROUP, jobName, DEFAULT_TRIGGER_GROUP, clazz, time, params);
    }

    /**
     * 添加一个定时任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param clazz            任务类
     * @param time             触发时间
     * @param params           任务参数
     */
    public void addJob(String jobName, String jobGroupName,
                       String triggerName, String triggerGroupName, Class<? extends Job> clazz,
                       String time, Object params) {
        try {
            // 任务名，任务组，任务执行类、
            JobDetail jobDetail = JobBuilder.newJob(clazz)
                    .withIdentity(jobName, jobGroupName).build();
            // 向任务类传递需要的外部参数
            if (params != null) {
                jobDetail.getJobDataMap().put(TaskConstants.TASK_PARAM_KEY, params);
            }
            // 触发器(触发器名，触发器组)
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerName, triggerGroupName)
                    .withSchedule(
                            SimpleScheduleBuilder.repeatMinutelyForever(QuartzUtil.getMinuteFromHour(time))
                    ).build();
            // 设置触发时间
            scheduler.scheduleJob(jobDetail, trigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            throw new TaskException("添加定时任务出错.", e);
        }
    }

    /**
     * 添加一次性任务.
     *
     * @param jobName 任务名
     * @param clazz   任务类
     * @param params  任务参数
     */
    public void addOneTimeJob(String jobName, Class<? extends Job> clazz, Object params) {
        try {
            // 任务名，任务组，任务执行类、
            JobDetail jobDetail = JobBuilder.newJob(clazz)
                    .withIdentity(jobName, DEFAULT_JOB_GROUP).build();
            // 向任务类传递需要的外部参数
            if (params != null) {
                jobDetail.getJobDataMap().put(TaskConstants.TASK_PARAM_KEY, params);
            }
            // 触发器(触发器名，触发器组)
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName, DEFAULT_TRIGGER_GROUP)
                    .startNow().build();
            // 设置触发时间
            scheduler.scheduleJob(jobDetail, trigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            throw new TaskException("添加一次性任务出错.", e);
        }
    }

    /**
     * Modify job time.
     *
     * @param jobName 任务名称
     * @param time    更新任务触发时间
     */
    public void modifyJobTime(String jobName, String time) {
        modifyJobTime(jobName, time, null);
    }

    /**
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param jobName 任务名称
     * @param time    更新任务触发时间
     * @param params  更新任务参数
     */
    public void modifyJobTime(String jobName, String time, Object params) {
        // 默认的trigger名为jobName
        modifyJobTime(jobName, DEFAULT_JOB_GROUP, jobName, DEFAULT_TRIGGER_GROUP, time, params);
    }

    /**
     * 修改一个任务的触发时间
     *
     * @param jobName          任务名称
     * @param JobGroupName     任务分组名
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器分组名
     * @param time             更新任务触发时间
     * @param params           更新任务参数
     */
    public void modifyJobTime(String jobName, String JobGroupName, String triggerName, String triggerGroupName, String time, Object params) {
        try {
            Trigger oldTrigger = scheduler.getTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
            JobDetail oldJob = scheduler.getJobDetail(JobKey.jobKey(jobName, JobGroupName));
            if (oldTrigger == null || oldJob == null) {
                throw new TaskException("调整任务出错.");
            }
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.repeatMinutelyForever(
                    QuartzUtil.getMinuteFromHour(time)
            );
            TriggerBuilder tb = oldTrigger.getTriggerBuilder();
            Trigger newTrigger = tb.withSchedule(simpleScheduleBuilder).build();
            if (params != null) {
                oldJob.getJobDataMap().put(TaskConstants.TASK_PARAM_KEY, params);
                scheduler.addJob(oldJob, true);
            }
            scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
        } catch (Exception e) {
            throw new TaskException("调整任务出错.", e);
        }
    }

    /**
     * 移除一个任务(使用默认的任务组名)
     *
     * @param jobName 任务名
     */
    public void removeJob(String jobName) {
        removeJob(jobName, DEFAULT_JOB_GROUP);
    }

    /**
     * 移除一个任务以及其触发器
     *
     * @param jobName      任务名
     * @param jobGroupName 任务分组名
     */
    public void removeJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            // 停止任务
            scheduler.pauseJob(jobKey);
            //删除任务 以及对应的trigger
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            throw new TaskException("移除任务出错.", e);
        }
    }

    /**
     * 启动所有定时任务
     */
    public void startJobs() {
        try {
            scheduler.start();
        } catch (Exception e) {
            throw new TaskException("启动所有定时任务出错.", e);
        }
    }

    /**
     * 关闭所有定时任务
     */
    public void shutdownJobs() {
        shutdownJobs(true);
    }

    /**
     * 关闭所有定时任务
     *
     * @param waitForJobsToComplete 等待所以任务完成
     */
    public void shutdownJobs(boolean waitForJobsToComplete) {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown(waitForJobsToComplete);
            }
        } catch (Exception e) {
            throw new TaskException("关闭所有定时任务出错.", e);
        }
    }

    /**
     * 恢复任务.
     *
     * @param jobName the job name
     * @param clazz   the clazz
     * @param time    the time
     * @param params  the params
     */
    public void resumeJob(String jobName, Class<? extends Job> clazz, String time, Object params) {
        try {
            if (scheduler.isStarted()) {
                scheduler.start();
            }
            if (scheduler.checkExists(JobKey.jobKey(jobName, DEFAULT_JOB_GROUP))) {
                scheduler.resumeJob(JobKey.jobKey(jobName, DEFAULT_JOB_GROUP));
            } else {
                this.addJob(jobName, clazz, time, params);
            }
        } catch (Exception e) {
            throw new TaskException("恢复任务出错.", e);
        }
    }

    /**
     * 恢复一次性任务.
     *
     * @param jobName 任务名称
     * @param clazz   任务执行类
     * @param params  任务参数
     */
    public void resumeOneTimeJob(String jobName, Class<? extends Job> clazz, Object params) {
        try {
            if (scheduler.isStarted()) {
                scheduler.start();
            }
            if (scheduler.checkExists(JobKey.jobKey(jobName, DEFAULT_JOB_GROUP))) {
                scheduler.resumeJob(JobKey.jobKey(jobName, DEFAULT_JOB_GROUP));
            } else {
                this.addOneTimeJob(jobName, clazz, params);
            }
        } catch (Exception e) {
            throw new TaskException("恢复任务出错.", e);
        }
    }
}
