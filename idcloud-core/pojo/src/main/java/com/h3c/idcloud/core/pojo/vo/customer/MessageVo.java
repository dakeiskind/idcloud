package com.h3c.idcloud.core.pojo.vo.customer;

import java.util.Date;

/**
 *
 * Created by wuchong on 2016/2/18.
 */
public class MessageVo {

    /**
     * 消息SID
     */
    private Long msgSid;

    /**
     * 消息标题
     */
    private String msgTitle;

    /**
     * 消息类型
     */

    private String msgType;

    /**
     * 创建时间
     */
    private Date sendDate;

    /**
     * 是否标为已读
     */
    private String readFlag;

    /**
     * 消息内容
     */
    private String msgContent;



    public Long getMsgSid() {
        return msgSid;
    }

    public void setMsgSid(Long msgSid) {
        this.msgSid = msgSid;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }





}
