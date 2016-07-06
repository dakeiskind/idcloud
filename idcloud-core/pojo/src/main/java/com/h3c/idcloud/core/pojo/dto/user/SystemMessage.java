/**
 *
 */
package com.h3c.idcloud.core.pojo.dto.user;

/**
 * @author qct
 * @since 2014-9-10
 */
public class SystemMessage {
    private String fromSystem;
    private Long userSid;
    private String msgId;
    private String corelationId;
    private Object[] params;

    private Long[] levelSid;
    private String userGroup;

    private String messageContentJson;

    public String getMessageContentJson() {
        return messageContentJson;
    }

    public void setMessageContentJson(String messageContentJson) {
        this.messageContentJson = messageContentJson;
    }

    public String getFromSystem() {
        return fromSystem;
    }

    public void setFromSystem(String fromSystem) {
        this.fromSystem = fromSystem;
    }

    //发送渠道 0 站内信 1邮件 2短信  多个如：new int[]{0,1,2}
    private int[] sendType;

    public int[] getSendType() {
        return sendType;
    }

    public void setSendType(int[] sendType) {
        this.sendType = sendType;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public Long[] getLevelSid() {
        return levelSid;
    }

    public void setLevelSid(Long[] levelSid) {
        this.levelSid = levelSid;
    }

    /**
     * @return the userSid
     */
    public Long getUserSid() {
        return userSid;
    }

    /**
     * @param userSid the userSid to set
     */
    public void setUserSid(Long userSid) {
        this.userSid = userSid;
    }

    /**
     * @return the msgId
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * @param msgId the msgId to set
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * @return the params
     */
    public Object[] getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(Object[] params) {
        this.params = params;
    }

    public String getCorelationId() {
        return corelationId;
    }

    public void setCorelationId(String corelationId) {
        this.corelationId = corelationId;
    }


}
