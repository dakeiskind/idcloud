package com.h3c.idcloud.core.pojo.dto.customer;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/2/19.
 */
public class TicketRecord implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 工单操作SID
     */
    private Long ticketRecordSid;

    /**
     * 工单SID
     */
    private Long ticketSid;

    /**
     * 工单号
     */
    private String ticketNo;

    /**
     * 处理人
     */
    private String operator;

    /**
     * 处理操作
     */
    private String operate;

    /**
     * 处理操作名称
     */
    private String operateName;

    /**
     * 处理意见
     */
    private String operateContent;

    /**
     * 处理时间
     */
    private Date operateTime;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
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
     * 版本号
     */
    private Long version;

    /**
     * @return 工单操作SID
     */
    public Long getTicketRecordSid() {
        return ticketRecordSid;
    }

    /**
     * @param ticketRecordSid
     *            工单操作SID
     */
    public void setTicketRecordSid(Long ticketRecordSid) {
        this.ticketRecordSid = ticketRecordSid;
    }

    /**
     * @return 工单SID
     */
    public Long getTicketSid() {
        return ticketSid;
    }

    /**
     * @param ticketSid
     *            工单SID
     */
    public void setTicketSid(Long ticketSid) {
        this.ticketSid = ticketSid;
    }

    /**
     * @return 处理人
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator
     *            处理人
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * @return 处理操作
     */
    public String getOperate() {
        return operate;
    }

    /**
     * @param operate
     *            处理操作
     */
    public void setOperate(String operate) {
        this.operate = operate;
    }

    /**
     * @return 处理意见
     */
    public String getOperateContent() {
        return operateContent;
    }

    /**
     * @param operateContent
     *            处理意见
     */
    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent;
    }

    /**
     * @return 处理时间
     */
    public Date getOperateTime() {
        return operateTime;
    }

    /**
     * @param operateTime
     *            处理时间
     */
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    /**
     * @return 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     *            创建人
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return 创建时间
     */
    public Date getCreatedDt() {
        return createdDt;
    }

    /**
     * @param createdDt
     *            创建时间
     */
    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    /**
     * @return 更新人
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy
     *            更新人
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return 更新时间
     */
    public Date getUpdatedDt() {
        return updatedDt;
    }

    /**
     * @param updatedDt
     *            更新时间
     */
    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    /**
     * @return 版本号
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @param version
     *            版本号
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * @return the operateName
     */
    public String getOperateName() {
        return operateName;
    }

    /**
     * @param operateName the operateName to set
     */
    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    /**
     * @return the ticketNo
     */
    public String getTicketNo() {
        return ticketNo;
    }

    /**
     * @param ticketNo the ticketNo to set
     */
    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }
}
