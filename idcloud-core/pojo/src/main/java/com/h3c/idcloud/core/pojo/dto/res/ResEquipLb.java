package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResEquipLb implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    private String equipLoadBalanceSid;

    /**
     * 所属拓扑
     */
    private String resTopologySid;

    /**
     * 所属机架
     */
    private String equipRackSid;

    /**
     * 所属机架
     */
    private String equipRoomSid;

    /**
     * 所属机架
     */
    private String equipCabinetSid;

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

    private String resTopologyNameVE;

    private String resTopologyNameDC;

    private String equipRackName;

    private String equipCabinetName;

    private String equipRoomName;

    private String equipCategoryName;

    /**
     * 设备分类
     */
    private String equipCategory;

    /**
     * 设备类型
     */
    private String equipType;

    /**
     * 位置编号
     */
    private String locationNumber;

    /**
     * 名称
     */
    private String name;

    /**
     * 序列号
     */
    private String serialNumber;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 型号
     */
    private String model;

    /**
     * 远程管理IP1
     */
    private String remoteMgtIp1;

    /**
     * 远程管理IP2
     */
    private String remoteMgtIp2;

    /**
     * 远程管理用户
     */
    private String remoteMgtUser;

    /**
     * 远程管理密码
     */
    private String remoteMgtPwd;

    /**
     * 关联IP地址
     */
    private String relevanceIp;

    private Long staTotalLoadBalance;


    /**
     * @return 设备ID
     */
    public String getEquipLoadBalanceSid() {
        return equipLoadBalanceSid;
    }

    /**
     * @param equipLoadBalanceSid 设备ID
     */
    public void setEquipLoadBalanceSid(String equipLoadBalanceSid) {
        this.equipLoadBalanceSid = equipLoadBalanceSid;
    }

    /**
     * @return the staTotalLoadBalance
     */
    public Long getStaTotalLoadBalance() {
        return staTotalLoadBalance;
    }

    /**
     * @param staTotalLoadBalance the staTotalLoadBalance to set
     */
    public void setStaTotalLoadBalance(Long staTotalLoadBalance) {
        this.staTotalLoadBalance = staTotalLoadBalance;
    }

    /**
     * @return 所属拓扑
     */
    public String getResTopologySid() {
        return resTopologySid;
    }

    /**
     * @param resTopologySid 所属拓扑
     */
    public void setResTopologySid(String resTopologySid) {
        this.resTopologySid = resTopologySid;
    }

    /**
     * @return 所属机架
     */
    public String getEquipRackSid() {
        return equipRackSid;
    }

    /**
     * @param equipRackSid 所属机架
     */
    public void setEquipRackSid(String equipRackSid) {
        this.equipRackSid = equipRackSid;
    }

    /**
     * @return 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy 创建人
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
     * @param createdDt 创建时间
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
     * @param updatedBy 更新人
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
     * @param updatedDt 更新时间
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
     * @param version 版本号
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * @return the resTopologyNameVE
     */
    public String getResTopologyNameVE() {
        return resTopologyNameVE;
    }

    /**
     * @param resTopologyNameVE the resTopologyNameVE to set
     */
    public void setResTopologyNameVE(String resTopologyNameVE) {
        this.resTopologyNameVE = resTopologyNameVE;
    }

    /**
     * @return the resTopologyNameDC
     */
    public String getResTopologyNameDC() {
        return resTopologyNameDC;
    }

    /**
     * @param resTopologyNameDC the resTopologyNameDC to set
     */
    public void setResTopologyNameDC(String resTopologyNameDC) {
        this.resTopologyNameDC = resTopologyNameDC;
    }

    /**
     * @return the equipRackName
     */
    public String getEquipRackName() {
        return equipRackName;
    }

    /**
     * @param equipRackName the equipRackName to set
     */
    public void setEquipRackName(String equipRackName) {
        this.equipRackName = equipRackName;
    }

    /**
     * @return the equipCategoryName
     */
    public String getEquipCategoryName() {
        return equipCategoryName;
    }

    /**
     * @param equipCategoryName the equipCategoryName to set
     */
    public void setEquipCategoryName(String equipCategoryName) {
        this.equipCategoryName = equipCategoryName;
    }

    /**
     * @return the equipCategory
     */
    public String getEquipCategory() {
        return equipCategory;
    }

    /**
     * @param equipCategory the equipCategory to set
     */
    public void setEquipCategory(String equipCategory) {
        this.equipCategory = equipCategory;
    }

    /**
     * @return the equipType
     */
    public String getEquipType() {
        return equipType;
    }

    /**
     * @param equipType the equipType to set
     */
    public void setEquipType(String equipType) {
        this.equipType = equipType;
    }

    /**
     * @return the locationNumber
     */
    public String getLocationNumber() {
        return locationNumber;
    }

    /**
     * @param locationNumber the locationNumber to set
     */
    public void setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the serialNumber
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * @param serialNumber the serialNumber to set
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the remoteMgtIp1
     */
    public String getRemoteMgtIp1() {
        return remoteMgtIp1;
    }

    /**
     * @param remoteMgtIp1 the remoteMgtIp1 to set
     */
    public void setRemoteMgtIp1(String remoteMgtIp1) {
        this.remoteMgtIp1 = remoteMgtIp1;
    }

    /**
     * @return the remoteMgtIp2
     */
    public String getRemoteMgtIp2() {
        return remoteMgtIp2;
    }

    /**
     * @param remoteMgtIp2 the remoteMgtIp2 to set
     */
    public void setRemoteMgtIp2(String remoteMgtIp2) {
        this.remoteMgtIp2 = remoteMgtIp2;
    }

    /**
     * @return the remoteMgtUser
     */
    public String getRemoteMgtUser() {
        return remoteMgtUser;
    }

    /**
     * @param remoteMgtUser the remoteMgtUser to set
     */
    public void setRemoteMgtUser(String remoteMgtUser) {
        this.remoteMgtUser = remoteMgtUser;
    }

    /**
     * @return the remoteMgtPwd
     */
    public String getRemoteMgtPwd() {
        return remoteMgtPwd;
    }

    /**
     * @param remoteMgtPwd the remoteMgtPwd to set
     */
    public void setRemoteMgtPwd(String remoteMgtPwd) {
        this.remoteMgtPwd = remoteMgtPwd;
    }

    /**
     * @return the relevanceIp
     */
    public String getRelevanceIp() {
        return relevanceIp;
    }

    /**
     * @param relevanceIp the relevanceIp to set
     */
    public void setRelevanceIp(String relevanceIp) {
        this.relevanceIp = relevanceIp;
    }

    /**
     * @return the equipRoomSid
     */
    public String getEquipRoomSid() {
        return equipRoomSid;
    }

    /**
     * @param equipRoomSid the equipRoomSid to set
     */
    public void setEquipRoomSid(String equipRoomSid) {
        this.equipRoomSid = equipRoomSid;
    }

    /**
     * @return the equipCabinetSid
     */
    public String getEquipCabinetSid() {
        return equipCabinetSid;
    }

    /**
     * @param equipCabinetSid the equipCabinetSid to set
     */
    public void setEquipCabinetSid(String equipCabinetSid) {
        this.equipCabinetSid = equipCabinetSid;
    }

    /**
     * @return the equipCabinetName
     */
    public String getEquipCabinetName() {
        return equipCabinetName;
    }

    /**
     * @param equipCabinetName the equipCabinetName to set
     */
    public void setEquipCabinetName(String equipCabinetName) {
        this.equipCabinetName = equipCabinetName;
    }

    /**
     * @return the equipRoomName
     */
    public String getEquipRoomName() {
        return equipRoomName;
    }

    /**
     * @param equipRoomName the equipRoomName to set
     */
    public void setEquipRoomName(String equipRoomName) {
        this.equipRoomName = equipRoomName;
    }
}