package com.h3c.idcloud.infra.task.base;

import com.h3c.idcloud.core.pojo.dto.task.Task;
import com.h3c.idcloud.infra.task.constants.TaskConstants;
import org.codehaus.jackson.map.ObjectMapper;

import java.lang.reflect.ParameterizedType;
import java.util.Date;

/**
 * 任务基类
 *
 * @param <T> the type parameter
 */
public abstract class BaseTask<T> implements TaskAction<T> {
    protected Task task;
    protected Class<T> entityClass;

    /**
     * 构造 Quartz base task 的实例.
     */
    @SuppressWarnings("unchecked")
    public BaseTask() {
        entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 传入参数，开启定时或周期任务
     *
     * @param t 任务参数
     * @return string
     */
    public String start(T t) {
        this.task = new Task();
        this.task.setCreatedDt(new Date());

        this.before(t);

        this.task.setTaskStatus(TaskConstants.TaskStatus.WAITING);
        this.task.setTaskClassPath(this.getClass().getName());

        try {
            if (t != null) {
                ObjectMapper o = new ObjectMapper();
                this.task.setTaskArg(o.writeValueAsString(t));
            }
            //存储task信息
            this.storeTask();

            startTask();

        } catch (Exception e) {
            doTaskError(e, TaskConstants.TaskStatus.ERROR, this.task.getTaskId());
        }

        this.after(t);
        return this.task.getTaskId();
    }

    /**
     * 创建新的定时或周期任务
     *
     * @throws Exception the exception
     */
    protected abstract void startTask() throws Exception;

    /**
     * 取消定时任务
     *
     * @param taskId the task id
     */
    protected abstract void cancelTask(String taskId);

    /**
     * 任务开始前的操作
     *
     * @param t 任务参数
     */
    public abstract void before(T t);

    /**
     * 任务开始后的操作
     *
     * @param t 任务参数
     */
    public abstract void after(T t);

    /**
     * 处理任务错误信息
     *
     * @param e          异常
     * @param taskStatus 错误后设定状态
     * @param taskId     任务ID
     */
    protected abstract void doTaskError(Exception e, String taskStatus, String taskId);

    /**
     * 存储task信息
     */
    protected abstract void storeTask();

    /**
     * 重新运行任务
     *
     * @see com.h3c.idcloud.infra.task.base.BaseTask#restart
     *
     * @param taskId 任务ID
     */
    @Deprecated
    public abstract void resume(String taskId);

    /**
     * 重新加载任务
     *
     * @param taskId 任务ID
     */
    public abstract void restart(String taskId);

}
