package com.h3c.idcloud.infra.log.task;

import com.h3c.idcloud.core.persist.system.dao.TaskLogMapper;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infra.log.LoggerTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 任务日志
 *
 * @author xubei
 */

@Scope("prototype")
@Service
public class TaskLogger {
    private LoggerTypeEnum type;
    private Logger logger;

    @Autowired
    private TaskLogMapper taskLogMapper;

    public void init(LoggerTypeEnum type) {
        this.type = type;
        if (type != LoggerTypeEnum.DB)
            logger = LoggerFactory.getLogger("task");
    }

    public Long start(String account, String taskType, String taskTarget) {
        TaskLog log = new TaskLog();
        log.setAccount(account);
        log.setTaskType(taskType);
        log.setTaskTarget(taskTarget);
        log = start(log);

        return log.getTaskLogSid();
    }

    /**
     * 开始记录任务日志
     *
     * @param log
     * @throws Exception
     */
    public TaskLog start(TaskLog log) {
        if (log != null) {
            TaskLog strict = new TaskLog();
            strict.setAccount(log.getAccount());
            strict.setTaskType(log.getTaskType());
            strict.setTaskTarget(log.getTaskTarget());
            strict.setTaskStatus(WebConstants.TaskStatus.RUNNING);
            strict.setTaskStartDate(new Date());

            switch (this.type) {
                case DB:
                    this.taskLogMapper.insertSelective(strict);
                    break;
                case LOG_FILE:
                    logger.info(log.toString());
                    break;
                default:
                    this.taskLogMapper.insertSelective(strict);
                    logger.info(log.toString());
                    break;
            }

            return strict;
        }

        return null;
    }

    public TaskLog startLogTest(TaskLog log) {
        return this.start(log);
    }

    /**
     * 更新任务信息
     *
     * @param log
     */
    public TaskLog update(TaskLog log) {
        if (log != null) {
            TaskLog strict = validPrimaryKey(log);
            strict.setTaskDetail(log.getTaskDetail());
            this.updateNoCheck(strict);

            return strict;
        }

        return null;
    }

    public void update(Long logId, String taskDetail) {
        TaskLog log = new TaskLog();
        log.setTaskLogSid(logId);
        log.setTaskDetail(taskDetail);
        this.update(log);
    }

    public void update(String taskType, String taskTarget, String taskDetail) {
        TaskLog log = new TaskLog();
        log.setTaskType(taskType);
        log.setTaskTarget(taskTarget);
        log.setTaskDetail(taskDetail);
        this.update(log);
    }

    private TaskLog validPrimaryKey(TaskLog log) {
        TaskLog strict = new TaskLog();
        if (log.getTaskLogSid() != null)
            strict.setTaskLogSid(log.getTaskLogSid());
        else if (log.getTaskTarget() != null && log.getTaskType() != null) {
            strict.setTaskTarget(log.getTaskTarget());
            strict.setTaskType(log.getTaskType());
        }

        return strict;
    }

    private TaskLog updateNoCheck(TaskLog log) {
        switch (this.type) {
            case DB:
                this.taskLogMapper.logUpdateSelective(log);
                break;
            case LOG_FILE:
                logger.info(log.toString());
                break;
            default:
                this.taskLogMapper.logUpdateSelective(log);
                logger.info(log.toString());
                break;
        }

        return log;
    }

    /**
     * 记录任务失败日志
     *
     * @param log
     */
    public TaskLog fail(TaskLog log) {
        if (log != null) {
            TaskLog strict = validPrimaryKey(log);
            strict.setTaskStatus(WebConstants.TaskStatus.FAIL);
            strict.setTaskEndDate(new Date());
            strict.setTaskFailureReason(log.getTaskFailureReason());

            return this.updateNoCheck(strict);
        }

        return null;
    }

    public void fail(Long logId, String failureReason) {
        TaskLog log = new TaskLog();
        log.setTaskLogSid(logId);
        log.setTaskFailureReason(failureReason);
        this.fail(log);
    }

    public void fail(String taskType, String taskTarget, String failureReason) {
        TaskLog log = new TaskLog();
        log.setTaskType(taskType);
        log.setTaskTarget(taskTarget);
        log.setTaskFailureReason(failureReason);
        this.fail(log);
    }

    /**
     * 记录任务成功日志
     *
     * @param log
     */
    public TaskLog success(TaskLog log) {
        if (log != null) {
            TaskLog strict = validPrimaryKey(log);
            strict.setTaskStatus(WebConstants.TaskStatus.SUCCESS);
            strict.setTaskEndDate(new Date());

            return this.updateNoCheck(strict);
        }

        return null;
    }

    public void success(Long logId) {
        TaskLog log = new TaskLog();
        log.setTaskLogSid(logId);
        this.success(log);
    }

    public void success(String taskType, String taskTarget) {
        TaskLog log = new TaskLog();
        log.setTaskType(taskType);
        log.setTaskTarget(taskTarget);
        this.success(log);
    }
}
