package com.h3c.idcloud.core.pojo.vo.customer;

import java.util.Date;

public class TicketListVo {
    private Long ticketSid;
    private String ticketNo;
    private String title;
    private String questionType;
    private String questionLevel;
    private String questionLevelName;
    private String content;
    private String questionTypeName;
    private String serviceName;
    private String ticketUser;
    private String allocationTicketUser;
    private String status;
    private String statusName;
    private Date updatedDt;
    private Date createdDt;
    private boolean allocateAccess;
    private boolean processAccess;
    private Long processObjectId;

    public Long getProcessObjectId() {
        return processObjectId;
    }

    public void setProcessObjectId(Long processObjectId) {
        this.processObjectId = processObjectId;
    }

    public String getQuestionLevelName() {
        return questionLevelName;
    }

    public void setQuestionLevelName(String questionLevelName) {
        this.questionLevelName = questionLevelName;
    }

    public String getQuestionLevel() {
        return questionLevel;
    }

    public void setQuestionLevel(String questionLevel) {
        this.questionLevel = questionLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isProcessAccess() {
        return processAccess;
    }

    public void setProcessAccess(boolean processAccess) {
        this.processAccess = processAccess;
    }

    public boolean isAllocateAccess() {
        return allocateAccess;
    }

    public void setAllocateAccess(boolean allocateAccess) {
        this.allocateAccess = allocateAccess;
    }

    public String getQuestionTypeName() {
        return questionTypeName;
    }

    public void setQuestionTypeName(String questionTypeName) {
        this.questionTypeName = questionTypeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getAllocationTicketUser() {
        return allocationTicketUser;
    }

    public void setAllocationTicketUser(String allocationTicketUser) {
        this.allocationTicketUser = allocationTicketUser;
    }

    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    public String getTicketUser() {
        return ticketUser;
    }

    public void setTicketUser(String ticketUser) {
        this.ticketUser = ticketUser;
    }

    public Long getTicketSid() {
        return ticketSid;
    }

    public void setTicketSid(Long ticketSid) {
        this.ticketSid = ticketSid;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }
}
