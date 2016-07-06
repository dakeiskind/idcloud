package com.h3c.idcloud.core.pojo.vo.customer;

import com.h3c.idcloud.core.pojo.dto.user.SystemMessage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 此为发送消息公共vo，根据不同的消息类型使用的字段可能不同
 * @author shiw
 *
 */
public class MsgVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String templateId;
	private String msgContent;
	private String fromUser = "Admin";
	private String msgTitle = "消息提示";
	private String mailTemplateId;
	//针对充值
	private BigDecimal amountDeposited;
	private Long depositeSid;
	
	//针对替换类模板消息内容存储
	private Map<String,String> messageContent;
	//消息代码
	private String messageCode;
	
	private SystemMessage systemMessage;
	
	
	
	public SystemMessage getSystemMessage() {
		return systemMessage;
	}
	public void setSystemMessage(SystemMessage systemMessage) {
		this.systemMessage = systemMessage;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getMailTemplateId() {
		return mailTemplateId;
	}
	public void setMailTemplateId(String mailTemplateId) {
		this.mailTemplateId = mailTemplateId;
	}
	public BigDecimal getAmountDeposited() {
		return amountDeposited;
	}
	public void setAmountDeposited(BigDecimal amountDeposited) {
		this.amountDeposited = amountDeposited;
	}
	public Long getDepositeSid() {
		return depositeSid;
	}
	public void setDepositeSid(Long depositeSid) {
		this.depositeSid = depositeSid;
	}
	public Map<String, String> getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(Map<String, String> messageContent) {
		this.messageContent = messageContent;
	}
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

}
