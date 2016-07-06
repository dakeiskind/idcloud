package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class TaskLog implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long taskLogSid;

	private String account;

	private String taskType;

	private String taskTypeName;

	private String taskTarget;

	private String taskStatus;

	private String taskStatusName;

	private Date taskStartDate;

	private Date taskEndDate;

	private String taskFailureReason;

	private String taskDetail;
	
	private String operator;

	public Long getTaskLogSid() {
		return taskLogSid;
	}

	public void setTaskLogSid(Long taskLogSid) {
		this.taskLogSid = taskLogSid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskTarget() {
		return taskTarget;
	}

	public void setTaskTarget(String taskTarget) {
		this.taskTarget = taskTarget;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Date getTaskStartDate() {
		return taskStartDate;
	}

	public void setTaskStartDate(Date taskStartDate) {
		this.taskStartDate = taskStartDate;
	}

	public Date getTaskEndDate() {
		return taskEndDate;
	}

	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}

	public String getTaskFailureReason() {
		return taskFailureReason;
	}

	public void setTaskFailureReason(String taskFailureReason) {
		this.taskFailureReason = taskFailureReason;
	}

	public String getTaskDetail() {
		return taskDetail;
	}

	public void setTaskDetail(String taskDetail) {
		this.taskDetail = taskDetail;
	}

	public String getTaskTypeName() {
		return taskTypeName;
	}

	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}

	public String getTaskStatusName() {
		return taskStatusName;
	}

	public void setTaskStatusName(String taskStatusName) {
		this.taskStatusName = taskStatusName;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}