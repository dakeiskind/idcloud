package com.h3c.idcloud.core.pojo.dto.order;

import java.io.Serializable;
import java.util.Date;

public class OrderApvRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 审核记录SID
     */
    private Long apvRecordSid;

    /**
     * 审核定单编码
     */
    private String orderId;

    /**
     * 服务SID
     */
    private Long serviceSid;

    /**
     * 订单明细SID
     */
    private Long detailSid;

    /**
     * 审核者ID
     */
    private String approvorId;

    /**
     * 审核状态
     */
    private String approveStatus;

    /**
     * 审核意见
     */
    private String approveOpinion;

    /**
     * 审核时间
     */
    private Date approveDate;

    /**
     * @return 审核记录SID
     */
    public Long getApvRecordSid() {
        return apvRecordSid;
    }

    /**
     * @param apvRecordSid 
	 *            审核记录SID
     */
    public void setApvRecordSid(Long apvRecordSid) {
        this.apvRecordSid = apvRecordSid;
    }

    /**
     * @return 审核定单编码
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId 
	 *            审核定单编码
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
     * @return 订单明细SID
     */
    public Long getDetailSid() {
        return detailSid;
    }

    /**
     * @param detailSid 
	 *            订单明细SID
     */
    public void setDetailSid(Long detailSid) {
        this.detailSid = detailSid;
    }

    /**
     * @return 审核者ID
     */
    public String getApprovorId() {
        return approvorId;
    }

    /**
     * @param approvorId 
	 *            审核者ID
     */
    public void setApprovorId(String approvorId) {
        this.approvorId = approvorId;
    }

    /**
     * @return 审核状态
     */
    public String getApproveStatus() {
        return approveStatus;
    }

    /**
     * @param approveStatus 
	 *            审核状态
     */
    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    /**
     * @return 审核意见
     */
    public String getApproveOpinion() {
        return approveOpinion;
    }

    /**
     * @param approveOpinion 
	 *            审核意见
     */
    public void setApproveOpinion(String approveOpinion) {
        this.approveOpinion = approveOpinion;
    }

    /**
     * @return 审核时间
     */
    public Date getApproveDate() {
        return approveDate;
    }

    /**
     * @param approveDate 
	 *            审核时间
     */
    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }
}