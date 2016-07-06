package com.h3c.idcloud.infra.task.base;

import com.google.common.base.Strings;
import com.h3c.idcloud.core.persist.task.dao.TaskMapper;
import com.h3c.idcloud.core.pojo.dto.task.Task;
import com.h3c.idcloud.infra.task.QuartzManager;
import com.h3c.idcloud.infra.task.constants.TaskConstants;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.Date;


/**
 * 定时任务基类.
 *
 * @param <T> the type parameter
 * @author Chaohong.Mao *
 */
public abstract class QuartzTimedTask<T> extends BaseTask<T> {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private QuartzManager quartzManager;

    /**
     * 构造 Quartz timed task 的实例.
     */
    @SuppressWarnings("unchecked")
    public QuartzTimedTask() {
        entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 执行任务
     *
     * @param t      任务参数
     * @param taskId 任务ID
     */
    protected void doRun(T t, String taskId) {

        this.beforeRun(taskId);
        try {
            this.action(t);
        } catch (Exception e) {
            this.doTaskError(e, TaskConstants.TaskStatus.ERROR, taskId);
        }
        this.afterRun(taskId);
    }

    /**
     * 任务执行预处理.
     *
     * @param taskId 任务ID
     */
    private void beforeRun(String taskId) {
        Task task = new Task();
        task.setTaskId(taskId);
        task.setTaskStartDate(new Date());
        task.setTaskFailureReason("");
        task.setTaskStatus(TaskConstants.TaskStatus.RUNNING);

        this.taskMapper.updateByPrimaryKeySelective(task);
    }
    /**
     * 创建新的定时或周期任务
     *
     * @throws Exception the exception
     */
    protected void startTask() throws Exception {
        String json = this.task.getTaskArg();
        if (json != null) {
            ObjectMapper o = new ObjectMapper();
            final T t = o.readValue(json, entityClass);
            this.quartzManager.addJob(
                    this.task.getTaskId(), this.getClass(), this.task.getUpdateCycle(), t);
        } else {

            this.quartzManager.addJob(
                    this.task.getTaskId(), this.getClass(), this.task.getUpdateCycle(), null);
        }
    }


    /**
     * 任务执行后处理.
     *
     * @param taskId 任务ID
     */
    private void afterRun(String taskId) {
        Task task = new Task();
        task.setTaskId(taskId);
        task.setTaskEndDate(new Date());
        task.setTaskStatus(TaskConstants.TaskStatus.WAITING);

        this.taskMapper.updateByPrimaryKeySelective(task);
    }

    @Override
    protected void doTaskError(Exception e, String taskStatus, String taskId) {
        String errorMessage = "执行任务错误";
        if (e != null && Strings.isNullOrEmpty(e.getMessage())) {
            errorMessage = e.getMessage();
            if (errorMessage.length() > 500)
                errorMessage = errorMessage.substring(0, 500);
        }

        Task task = new Task();
        task.setTaskId(taskId);
        task.setTaskFailureReason(errorMessage);
        task.setTaskStatus(taskStatus);

        this.taskMapper.updateByPrimaryKeySelective(task);
    }

    @Override
    protected void storeTask() {
        String taskId = this.task.getTaskId();
        if (!Strings.isNullOrEmpty(taskId)) {
            taskMapper.updateByPrimaryKeySelective(this.task);
        } else {
            taskMapper.insertSelective(this.task);
        }
    }

    /**
     * 手动运行一次任务
     *
     * @param taskId the task id
     */
    public void sync(String taskId) {
        this.resumeTask(taskId);
    }

    @Override
    public void resume(String taskId) {
        this.resumeTask(taskId);
    }

    /**
     * Resume task.
     *
     * @param taskId 任务ID
     */
    private void resumeTask(String taskId) {
        if (taskId != null) {
            Task task = this.taskMapper.selectByPrimaryKey(taskId);
            if (task != null) {
                this.task = task;
                String json = this.task.getTaskArg();
                if (json != null) {
                    ObjectMapper o = new ObjectMapper();
                    try {
                        final T t = o.readValue(json, entityClass);

                        this.beforeRun(task.getTaskId());
                        try {
                            this.action(t);
                        } catch (Exception e) {
                            this.doTaskError(e, TaskConstants.TaskStatus.ERROR, taskId);
                        }
                        this.afterRun(task.getTaskId());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 更新一个任务 根据任务名
     *
     * @param t           任务参数
     * @param taskId      任务Id
     * @param updateCycle 任务循环周期(小时)
     * @return 任务Id string
     */
    public String updateTask(T t, String taskId, String updateCycle) {
        if (taskId != null) {
            Task task = this.taskMapper.selectByPrimaryKey(taskId);
            if (task != null) {
                this.task = task;
                if (!this.task.getUpdateCycle().equals(updateCycle)) {
                    this.task.setUpdatedDt(new Date());
                    this.quartzManager.modifyJobTime(this.task.getTaskId(), updateCycle, t);
                }
            }
        }
        return this.task.getTaskId();
    }


    /**
     * 取消任务
     *
     */
    public void cancelTask(String taskId) {
        this.task = this.taskMapper.selectByPrimaryKey(taskId);
        if (this.task != null) {
            this.quartzManager.removeJob(taskId);
            this.taskMapper.deleteByPrimaryKey(taskId);
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
                this.quartzManager.resumeJob(taskId, this.getClass(), task.getUpdateCycle(), t);
            } catch (Exception e) {
                throw new TaskException("重新开始任务失败.");
            }
        }
    }
}
