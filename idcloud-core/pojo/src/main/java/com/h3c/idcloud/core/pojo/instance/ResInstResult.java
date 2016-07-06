package com.h3c.idcloud.core.pojo.instance;

import java.io.Serializable;

/**
 * 资源接口返回对象
 *
 * @author 刘洋
 */
public class ResInstResult implements Serializable {

    /**
     * 成功
     */
    public static final boolean SUCCESS = true;

    /**
     * 失败
     */
    public static final boolean FAILURE = false;

    /**
     * 返回状态
     */
    private boolean status;

    /**
     * 是否可以重发
     */
    private boolean isReSend;

    /**
     * 返回消息
     */
    private Object message;

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 构造方法
     */
    public ResInstResult() {
    }

    /**
     * 构造方法(状态)
     *
     * @param status 返回状态
     */
    public ResInstResult(boolean status) {
        this.status = status;
        this.message = "";
        this.data = "";
        isReSend = true;
    }

    /**
     * 构造方法(状态、消息)
     *
     * @param status  返回状态
     * @param message 返回消息
     */
    public ResInstResult(boolean status, Object message) {
        this.status = status;
        this.message = message;
        this.data = "";
        isReSend = true;
    }

    /**
     * 构造方法(状态、消息、数据)
     *
     * @param status  返回状态
     * @param message 返回消息
     * @param data    the data
     */
    public ResInstResult(boolean status, Object message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造方法(状态、消息、数据)
     *
     * @param status   返回状态
     * @param message  返回消息
     * @param isReSend the is re send
     * @param data     the data
     */
    public ResInstResult(boolean status, Object message, boolean isReSend, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.isReSend = isReSend;
    }

    /**
     * 获得 status.
     *
     * @return 返回状态 status
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * 设定 status.
     *
     * @param status 返回状态
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * 获得 message.
     *
     * @return 返回消息 message
     */
    public Object getMessage() {
        return message;
    }

    /**
     * 设定 message.
     *
     * @param message 返回消息
     */
    public void setMessage(Object message) {
        this.message = message;
    }

    /**
     * 获得 data.
     *
     * @return 返回数据 data
     */
    public Object getData() {
        return data;
    }

    /**
     * 设定 data.
     *
     * @param data 返回数据
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * Is re send boolean.
     *
     * @return the isReSend
     */
    public boolean isReSend() {
        return isReSend;
    }

    /**
     * 设定 re send.
     *
     * @param isReSend the isReSend to set
     */
    public void setReSend(boolean isReSend) {
        this.isReSend = isReSend;
    }

}
