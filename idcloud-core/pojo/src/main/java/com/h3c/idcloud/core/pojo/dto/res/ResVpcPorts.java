package com.h3c.idcloud.core.pojo.dto.res;

import com.h3c.idcloud.core.adapter.pojo.network.Port;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;

import java.io.Serializable;
import java.util.Date;

public class ResVpcPorts implements Serializable {
    private static final long serialVersionUID = 1L;
    public ResVpcPorts() {

    }

    public ResVpcPorts(Port port) {
        this.deviceId = port.getDeviceId();
        this.deviceOwner = port.getDeviceOwner();
        this.portName = port.getName();
        this.portId = port.getId();
        this.status = WebConstants.NETWORK_STATUS.CREATE_SUCCESS;
    }

    /**
     * 资源ID
     */
    private String resPortSid;

    /**
     * VPC网络ID
     */
    private String vpcId;

    /**
     * 子网ID
     */
    private String subnetId;

    /**
     * 设备所属
     */
    private String deviceOwner;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 端口名称
     */
    private String portName;

    /**
     * 端口ID
     */
    private String portId;

    /**
     * 状态
     */
    private String status;

    /**
     * 描述
     */
    private String description;

    /**
     * 管理对象ID
     */
    private Long mgtObjSid;

    /**
     * 所有者ID
     */
    private String ownerId;

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
     * 固定IP地址
     */
    private String ipAddress;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * @return 资源ID
     */
    public String getResPortSid() {
        return resPortSid;
    }

    /**
     * @param resPortSid 
	 *            资源ID
     */
    public void setResPortSid(String resPortSid) {
        this.resPortSid = resPortSid;
    }

    /**
     * @return VPC网络ID
     */
    public String getVpcId() {
        return vpcId;
    }

    /**
     * @param vpcId 
	 *            VPC网络ID
     */
    public void setVpcId(String vpcId) {
        this.vpcId = vpcId;
    }

    /**
     * @return 设备所属
     */
    public String getDeviceOwner() {
        return deviceOwner;
    }

    /**
     * @param deviceOwner 
	 *            设备所属
     */
    public void setDeviceOwner(String deviceOwner) {
        this.deviceOwner = deviceOwner;
    }

    /**
     * @return 设备ID
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId 
	 *            设备ID
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return 端口名称
     */
    public String getPortName() {
        return portName;
    }

    /**
     * @param portName 
	 *            端口名称
     */
    public void setPortName(String portName) {
        this.portName = portName;
    }

    /**
     * @return 端口ID
     */
    public String getPortId() {
        return portId;
    }

    /**
     * @param portId 
	 *            端口ID
     */
    public void setPortId(String portId) {
        this.portId = portId;
    }

    /**
     * @return 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 
	 *            描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return 管理对象ID
     */
    public Long getMgtObjSid() {
        return mgtObjSid;
    }

    /**
     * @param mgtObjSid 
	 *            管理对象ID
     */
    public void setMgtObjSid(Long mgtObjSid) {
        this.mgtObjSid = mgtObjSid;
    }

    /**
     * @return 所有者ID
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId 
	 *            所有者ID
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSubnetId() {
        return subnetId;
    }

    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }
}