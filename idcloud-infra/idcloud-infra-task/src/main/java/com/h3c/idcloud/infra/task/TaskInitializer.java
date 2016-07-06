package com.h3c.idcloud.infra.task;

import com.h3c.idcloud.core.persist.task.dao.TaskMapper;
import com.h3c.idcloud.core.pojo.dto.task.Task;
import com.h3c.idcloud.infra.task.base.BaseTask;
import com.h3c.idcloud.infrastructure.common.util.SpringContextHolder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.h3c.idcloud.infra.task.constants.TaskConstants.TaskStatus;

/**
 * <p>Title: Task initializer</p>
 * <p>Description: </p>
 *
 * @author Chaohong.Mao
 */
public class TaskInitializer {

    private final static Logger log = LoggerFactory.getLogger(TaskInitializer.class);

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private QuartzManager quartzManager;

    /**
     * 停止
     */
    public void destory() {
        quartzManager.shutdownJobs();
    }

    /**
     * 初始化
     */
    public void init() {
        List<Task> tasks = taskMapper.selectTaskStatusByOr(TaskStatus.RUNNING, TaskStatus.WAITING);

        if (tasks != null) {
            for (final Task task : tasks) {
                try {
                    Class clazz = Class.forName(task.getTaskClassPath());
                    BaseTask base = (BaseTask) SpringContextHolder.getApplicationContext().getBean(clazz);

                    base.restart(task.getTaskId());
                } catch (ClassNotFoundException e) {
                    log.error(String.format("初始化任务失败，任务信息%s，失败原因：无法找到相关任务类。",
                                            ReflectionToStringBuilder.toString(task)
                    ));
                } catch (Exception e) {
                    log.error(String.format("初始化任务失败，任务信息%s，失败原因：%s。", ReflectionToStringBuilder.toString(task), e));
                }
            }
        }
    }
}
