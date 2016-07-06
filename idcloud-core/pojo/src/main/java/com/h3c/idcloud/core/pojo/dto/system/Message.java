package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private String corelationId;
    private Boolean isMaxSendDate; 
    
    public Boolean getIsMaxSendDate() {
		return isMaxSendDate;
	}

	public void setIsMaxSendDate(Boolean isMaxSendDate) {
		this.isMaxSendDate = isMaxSendDate;
	}

	public String getCorelationId() {
		return corelationId;
	}

	public void setCorelationId(String corelationId) {
		this.corelationId = corelationId;
	}
	
	private String templateId;
	

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

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
    
    private String msgTypeName;

    /**
     * 发件人
     */
    private String fromUser;

    /**
     * 收件人
     */
    private String toUser;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 发件时间
     */
    private Date sendDate;
    
    /**
     * 查询条件：开始时间
     */
    private String sendDateStart;
    
    /**
     * 查询条件：结束时间
     */
    private String sendDateEnd;
    
    /**
     * 是否已读 
     * 01：是，02：否
     */
    private String readFlag;
    
    private String readFlagName;
    
    //收件箱中是否可见。0：不可见；1：可见
    private Integer inboxInUse;
    //发件箱中是否可见。0：不可见；1：可见
    private Integer outboxInUse;
    
	/**
	 * @return the inboxInUse
	 */
	public Integer getInboxInUse() {
		return inboxInUse;
	}

	/**
	 * @param inboxInUse the inboxInUse to set
	 */
	public void setInboxInUse(Integer inboxInUse) {
		this.inboxInUse = inboxInUse;
	}

	/**
	 * @return the outboxInUse
	 */
	public Integer getOutboxInUse() {
		return outboxInUse;
	}

	/**
	 * @param outboxInUse the outboxInUse to set
	 */
	public void setOutboxInUse(Integer outboxInUse) {
		this.outboxInUse = outboxInUse;
	}

	/**
     * @return 消息SID
     */
    public Long getMsgSid() {
        return msgSid;
    }

    /**
     * @param msgSid 
	 *            消息SID
     */
    public void setMsgSid(Long msgSid) {
        this.msgSid = msgSid;
    }

    /**
     * @return 消息标题
     */
    public String getMsgTitle() {
        return msgTitle;
    }

    /**
     * @param msgTitle 
	 *            消息标题
     */
    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    /**
     * @return 消息类型
     */
    public String getMsgType() {
        return msgType;
    }

    /**
     * @param msgType 
	 *            消息类型
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    /**
     * @return 发件人
     */
    public String getFromUser() {
        return fromUser;
    }

    /**
     * @param fromUser 
	 *            发件人
     */
    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    /**
     * @return 收件人
     */
    public String getToUser() {
        return toUser;
    }

    /**
     * @param toUser 
	 *            收件人
     */
    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    /**
     * @return 消息内容
     */
    public String getMsgContent() {
        return msgContent;
    }

    /**
     * @param msgContent 
	 *            消息内容
     */
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    /**
     * @return 发件时间
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * @param sendDate 
	 *            发件时间
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

	/**
	 * @return the sendDateStart
	 */
	public String getSendDateStart() {
		return sendDateStart;
	}

	/**
	 * @param sendDateStart the sendDateStart to set
	 */
	public void setSendDateStart(String sendDateStart) {
		this.sendDateStart = sendDateStart;
	}

	/**
	 * @return the sendDateEnd
	 */
	public String getSendDateEnd() {
		return sendDateEnd;
	}

	/**
	 * @param sendDateEnd the sendDateEnd to set
	 */
	public void setSendDateEnd(String sendDateEnd) {
		this.sendDateEnd = sendDateEnd;
	}

	/**
	 * @return the msgTypeName
	 */
	public String getMsgTypeName() {
		return msgTypeName;
	}

	/**
	 * @param msgTypeName the msgTypeName to set
	 */
	public void setMsgTypeName(String msgTypeName) {
		this.msgTypeName = msgTypeName;
	}

	/**
	 * @return the readFlag
	 */
	public String getReadFlag() {
		return readFlag;
	}

	/**
	 * @param readFlag the readFlag to set
	 */
	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	/**
	 * @return the readFlagName
	 */
	public String getReadFlagName() {
		return readFlagName;
	}

	/**
	 * @param readFlagName the readFlagName to set
	 */
	public void setReadFlagName(String readFlagName) {
		this.readFlagName = readFlagName;
	}
}