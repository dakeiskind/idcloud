package com.h3c.idcloud.core.pojo.dto.task;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 任务id
	 */
	private String taskId;

	/**
	 * 任务名称
	 */
	private String taskName;

	/**
	 * 任务类型，值见com.hptsic.cloud.res.common.constant.TaskType
	 */
	private String taskType;

	/**
	 * 任务状态，值见com.hptsic.cloud.res.common.constant.TaskStatus
	 */
	private String taskStatus;

	/**
	 * 任务开始时间
	 */
	private Date taskStartDate;

	/**
	 * 任务结束时间
	 */
	private Date taskEndDate;

	/**
	 * 任务执行类的全地址，包括包名
	 */
	private String taskClassPath;

	/**
	 * 任务参数，json格式
	 */
	private String taskArg;

	/**
	 * 运行周期，单位为小时
	 */
	private String updateCycle;

	/**
	 * 任务失败原因
	 */
	private String taskFailureReason;

	/**
	 * 任务详情
	 */
	private String taskDetail;

	/**
	 * 任务创建人
	 */
	private String createdBy;

	/**
	 * 任务创建时间
	 */
	private Date createdDt;

	/**
	 * 更新人
	 */
	private String updatedBy;

	/**
	 * 更新时间
	 */
	private Date updatedDt;

	/**
	 * 版本
	 */
	private Long version;

	/**
	 * 任务开始调度时间
	 */
	private Date schedule_time;
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
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

	public String getTaskClassPath() {
		return taskClassPath;
	}

	public void setTaskClassPath(String taskClassPath) {
		this.taskClassPath = taskClassPath;
	}

	public String getTaskArg() {
		return taskArg;
	}

	public void setTaskArg(String taskArg) {
		this.taskArg = taskArg;
	}

	public String getUpdateCycle() {
		return updateCycle;
	}

	public void setUpdateCycle(String updateCycle) {
		this.updateCycle = updateCycle;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getSchedule_time() {
		return schedule_time;
	}

	public void setSchedule_time(Date schedule_time) {
		this.schedule_time = schedule_time;
	}

}