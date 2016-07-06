package com.h3c.idcloud.infra.task.base;

import com.h3c.idcloud.core.persist.task.dao.TaskMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.task.Task;
import com.h3c.idcloud.infra.task.QuartzManager;
import com.h3c.idcloud.infra.task.constants.TaskConstants;
import com.h3c.idcloud.infrastructure.common.pojo.User;
import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 一次性任务类.
 *
 * @param <T> the type parameter
 * @author Chaohong.Mao *
 */
public abstract class OneTimeTask<T> extends BaseTask<T> {
    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private QuartzManager quartzManager;

    @Override
    protected void doTaskError(Exception e, String taskStatus, String taskId) {
        String errorMessage = "执行任务错误";
        if (e != null && e.getMessage() != null && e.getMessage() != "") {
            errorMessage = e.getMessage();
            if (errorMessage.length() > 500)
                errorMessage = errorMessage.substring(0, 500);
        }

        Task task = new Task();
        task.setTaskId(this.task.getTaskId());
        task.setTaskFailureReason(errorMessage);
        task.setTaskStatus(taskStatus);

        this.taskMapper.updateByPrimaryKeySelective(task);
    }

    /**
     * Do run.
     *
     * @param t the t
     */
    protected void doRun(T t) {
        Task task = new Task();
        task.setTaskId(this.task.getTaskId());
        task.setTaskStartDate(new Date());
        task.setTaskFailureReason("");
        task.setTaskStatus(TaskConstants.TaskStatus.RUNNING);

        this.taskMapper.updateByPrimaryKeySelective(task);

        try {
            this.action(t);
            taskMapper.deleteByPrimaryKey(this.task.getTaskId());
        } catch (Exception e) {
            this.doTaskError(e, TaskConstants.TaskStatus.ERROR, this.task.getTaskId());
        }
    }

    protected void storeTask() {
        String taskId = this.task.getTaskId();
        if (taskId != null && taskId != "")
            this.taskMapper.updateByPrimaryKeySelective(this.task);
        else
            this.taskMapper.insertSelective(this.task);
    }

    public void resume(String taskId) {
        if (taskId != null) {
            Task task = this.taskMapper.selectByPrimaryKey(taskId);
            if (task != null) {
                try {
                    startTask();
                } catch (Exception e) {
                    doTaskError(e, TaskConstants.TaskStatus.ERROR, this.task.getTaskId());
                }
            }
        }
    }

    /**
     * 取消任务
     */
    public void cancelTask() {
        this.quartzManager.removeJob(this.task.getTaskId());
        this.taskMapper.deleteByPrimaryKey(this.task.getTaskId());
    }

    /**
     * 取消任务
     *
     */
    public void cancelTask(String taskId) {
        this.task = this.taskMapper.selectByPrimaryKey(taskId);
        if (this.task != null)
            this.cancelTask();
    }

    /**
     * After.
     *
     * @param t the t
     */
    public void after(ResVe t) {
        // no-op
    }

    /**
     * Do one time log.
     *
     * @param msg the msg
     */
    protected void doOneTimeLog(String msg) {
        task.setTaskName(msg);
        task.setTaskDetail(msg);
        task.setTaskType(TaskConstants.TaskType.ONE_OFF);
        User user = AuthUtil.getAuthUser();
        task.setCreatedBy(user.getAccount());
    }

    protected void startTask() throws Exception {
        String json = this.task.getTaskArg();
        if (json != null) {
            ObjectMapper o = new ObjectMapper();
            final T t = o.readValue(json, entityClass);
            quartzManager.addOneTimeJob(this.task.getTaskId(), this.getClass(), t);
        } else {
            quartzManager.addOneTimeJob(this.task.getTaskId(), this.getClass(), null);
        }
    }

    public void restart(String taskId) {
        Task task = this.taskMapper.selectByPrimaryKey(taskId);
        if (task != null) {
            this.task = task;
            try {
                String json = this.task.getTaskArg();
                T t = null;
                if (json != null) {
                    ObjectMapper o = new ObjectMapper();
                    t = o.readValue(json, entityClass);
                }
                this.quartzManager.resumeOneTimeJob(taskId, this.getClass(), t);
            } catch (Exception e) {
                throw new TaskException("重新开始任务失败.");
            }
        }
    }
}
