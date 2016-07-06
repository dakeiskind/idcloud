package com.h3c.idcloud.core.pojo.vo.customer;

import java.util.Date;

/**
 * Created by Administrator on 2016/2/18.
 */
public class TicketVo {

    private Long ticketSid;
    private String ticketNo;
    private String title;
    private String serviceName;
    private String content;
    private String questionType;
    private String status;
    private Date updatedDt;
    private Date createdDt;

    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
