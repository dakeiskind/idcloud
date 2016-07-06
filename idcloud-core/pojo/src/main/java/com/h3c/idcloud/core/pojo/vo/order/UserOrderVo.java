package com.h3c.idcloud.core.pojo.vo.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户中心用户订单vo
 * Created by swq on 3/2/2016.
 */
public class UserOrderVo implements Serializable {

    /**
     * 定单编号
     */
    private String orderId;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 定单名称
     */
    private String orderName;


    /**
     * 订单总金额
     */
    private BigDecimal amount;

    /**
     * 状态名称
     */
    private String statusName;


    /**
     * 创建时间
     */
    private Date createdDt;


    /**
     * 开通时间
     */
    private Date dredgeDate;


    /**
     * 订单支付时间
     */
    private Date timePurchase;

    /**
     * 订单类型名称
     */
    private String orderTypeName;

    /**
     * 订单详情
     */
    List<UserOrderDetailVo> orderDetails = new ArrayList<UserOrderDetailVo>();

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public Date getDredgeDate() {
        return dredgeDate;
    }

    public void setDredgeDate(Date dredgeDate) {
        this.dredgeDate = dredgeDate;
    }

    public Date getTimePurchase() {
        return timePurchase;
    }

    public void setTimePurchase(Date timePurchase) {
        this.timePurchase = timePurchase;
    }

    public List<UserOrderDetailVo> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<UserOrderDetailVo> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }
}
