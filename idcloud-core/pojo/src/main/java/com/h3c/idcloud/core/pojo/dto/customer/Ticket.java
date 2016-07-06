package com.h3c.idcloud.core.pojo.dto.customer;

import java.io.Serializable;
import java.util.Date;

public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工单SID
     */
    private Long ticketSid;

    /**
     * 工单号
     */
    private String ticketNo;

    private String ticketNoLike;

    /**
     * 所属租户
     */
    private Long tenantSid;


    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 服务SID
     */
    private Long serviceSid;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 问题分类
     */
    private String questionType;


    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    /**
     * 处理分类
     */
    private String processType;

    /**
     * 处理对象id
     */
    private Long processObjectId;

    /**
     * 问题分类
     */
    private String questionTypeName;

    /**
     * 紧急程度
     */
    private String questionLevel;

    /**
     * 紧急程度
     */
    private String questionLevelName;

    /**
     * 工单主题
     */
    private String title;
    private String titleLike;

    /**
     * 工单内容
     */
    private String content;

    /**
     * 工单状态
     */
    private String status;

    /**
     * 工单状态名称
     */
    private String statusName;

    /**
     * 工单提交人
     */
    private String ticketUser;

    /**
     * 分配处理工单人
     */
    private String allocationTicketUser;

    /**
     * 能否自动处理标记
     */
    private Integer autoHandlerFlag;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdDt;

    private String createdStartDt;

    private String createdEndDt;

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
     * 分配权限
     */
    private boolean allocateAccess;

    /**
     * 处理权限
     */
    private boolean processAccess;

    /**
     * 处理人
     */
    private String operator;

    /**
     * 处理时间
     */
    private String operateTime;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 处理目标
     */
    private String processTarget;

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
     * @return 工单号
     */
    public String getTicketNo() {
        return ticketNo;
    }

    /**
     * @param ticketNo
     *            工单号
     */
    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    /**
     * @return 所属租户
     */
    public Long getTenantSid() {
        return tenantSid;
    }

    /**
     * @param tenantSid
     *            所属租户
     */
    public void setTenantSid(Long tenantSid) {
        this.tenantSid = tenantSid;
    }

    /**
     * @return 服务SID
     */
    public Long getServiceSid() {
        return serviceSid;
    }

    /**
     * @param serviceSid
     *            服务SID
     */
    public void setServiceSid(Long serviceSid) {
        this.serviceSid = serviceSid;
    }

    /**
     * @return 问题分类
     */
    public String getQuestionType() {
        return questionType;
    }

    /**
     * @param questionType
     *            问题分类
     */
    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    /**
     * @return 紧急程度
     */
    public String getQuestionLevel() {
        return questionLevel;
    }

    /**
     * @param questionLevel
     *            紧急程度
     */
    public void setQuestionLevel(String questionLevel) {
        this.questionLevel = questionLevel;
    }

    /**
     * @return 工单主题
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            工单主题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return 工单内容
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     *            工单内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return 工单状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            工单状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return 工单提交人
     */
    public String getTicketUser() {
        return ticketUser;
    }

    /**
     * @param ticketUser
     *            工单提交人
     */
    public void setTicketUser(String ticketUser) {
        this.ticketUser = ticketUser;
    }

    /**
     * @return 分配处理工单人
     */
    public String getAllocationTicketUser() {
        return allocationTicketUser;
    }

    /**
     * @param allocationTicketUser
     *            分配处理工单人
     */
    public void setAllocationTicketUser(String allocationTicketUser) {
        this.allocationTicketUser = allocationTicketUser;
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
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return the questionTypeName
     */
    public String getQuestionTypeName() {
        return questionTypeName;
    }

    /**
     * @param questionTypeName the questionTypeName to set
     */
    public void setQuestionTypeName(String questionTypeName) {
        this.questionTypeName = questionTypeName;
    }

    /**
     * @return the questionLevelName
     */
    public String getQuestionLevelName() {
        return questionLevelName;
    }

    /**
     * @param questionLevelName the questionLevelName to set
     */
    public void setQuestionLevelName(String questionLevelName) {
        this.questionLevelName = questionLevelName;
    }

    /**
     * @return the statusName
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * @param statusName the statusName to set
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * @return the tenantName
     */
    public String getTenantName() {
        return tenantName;
    }

    /**
     * @param tenantName the tenantName to set
     */
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public boolean isAllocateAccess() {
        return allocateAccess;
    }

    public void setAllocateAccess(boolean allocateAccess) {
        this.allocateAccess = allocateAccess;
    }

    public boolean isProcessAccess() {
        return processAccess;
    }

    public void setProcessAccess(boolean processAccess) {
        this.processAccess = processAccess;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getTitleLike() {
        return titleLike;
    }

    public void setTitleLike(String titleLike) {
        this.titleLike = titleLike;
    }

    public Long getProcessObjectId() {
        return processObjectId;
    }

    public void setProcessObjectId(Long processObjectId) {
        this.processObjectId = processObjectId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getAutoHandlerFlag() {
        return autoHandlerFlag;
    }

    public void setAutoHandlerFlag(Integer autoHandlerFlag) {
        this.autoHandlerFlag = autoHandlerFlag;
    }

    public String getProcessTarget() {
        return processTarget;
    }

    public void setProcessTarget(String processTarget) {
        this.processTarget = processTarget;
    }

    public String getCreatedStartDt() {
        return createdStartDt;
    }

    public void setCreatedStartDt(String createdStartDt) {
        this.createdStartDt = createdStartDt;
    }

    public String getCreatedEndDt() {
        return createdEndDt;
    }

    public void setCreatedEndDt(String createdEndDt) {
        this.createdEndDt = createdEndDt;
    }

    public String getTicketNoLike() {
        return ticketNoLike;
    }

    public void setTicketNoLike(String ticketNoLike) {
        this.ticketNoLike = ticketNoLike;
    }
}