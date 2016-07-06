package com.h3c.idcloud.infra.task.base;

/**
 * 任务异常类.
 *
 * @author Chaohong.Mao
 */
public class TaskException extends RuntimeException {
    /**
     * 构造 Task exception 的实例.
     *
     * @param message the message
     */
    public TaskException(String message) {
        super(message);
    }

    /**
     * 构造 Task exception 的实例.
     *
     * @param message the message
     * @param ex      the ex
     */
    public TaskException(String message, Throwable ex) {
        super(message, ex);
    }
}
