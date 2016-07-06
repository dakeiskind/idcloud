package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ResEquipInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    private String equipSid;

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

    /**
     * 维保厂商
     */
    private String maintCompany;

    /**
     * 维保电话
     */
    private String maintTel;

    /**
     * 购置日期
     */
    private Date purchaseDate;

    /**
     * 保修期限
     */
    private BigDecimal warrantyPeriod;

    /**
     * 设备提供商
     */
    private String equipSupplier;

    /**
     * 规格
     */
    private String spec;

    /**
     * 更新周期
     */
    private String description;

    private Date startEndDate;

    private Integer equipSum;

    private String equipCategoryName;

    private String roomName;

    private String cabinetName;

    private String rackName;

    public String getEquipCategoryName() {
        return equipCategoryName;
    }

    public void setEquipCategoryName(String equipCategoryName) {
        this.equipCategoryName = equipCategoryName;
    }

    public Integer getEquipSum() {
        return equipSum;
    }

    public void setEquipSum(Integer equipSum) {
        this.equipSum = equipSum;
    }

    /**
     * @return the spec
     */
    public String getSpec() {
        return spec;
    }

    /**
     * @param spec the spec to set
     */
    public void setSpec(String spec) {
        this.spec = spec;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return 设备ID
     */
    public String getEquipSid() {
        return equipSid;
    }

    /**
     * @param equipSid 设备ID
     */
    public void setEquipSid(String equipSid) {
        this.equipSid = equipSid;
    }

    /**
     * @return 设备分类
     */
    public String getEquipCategory() {
        return equipCategory;
    }

    /**
     * @param equipCategory 设备分类
     */
    public void setEquipCategory(String equipCategory) {
        this.equipCategory = equipCategory;
    }

    /**
     * @return 设备类型
     */
    public String getEquipType() {
        return equipType;
    }

    /**
     * @param equipType 设备类型
     */
    public void setEquipType(String equipType) {
        this.equipType = equipType;
    }

    /**
     * @return 位置编号
     */
    public String getLocationNumber() {
        return locationNumber;
    }

    /**
     * @param locationNumber 位置编号
     */
    public void setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
    }

    /**
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 序列号
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * @param serialNumber 序列号
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * @return 品牌
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand 品牌
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return 型号
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model 型号
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return 远程管理IP1
     */
    public String getRemoteMgtIp1() {
        return remoteMgtIp1;
    }

    /**
     * @param remoteMgtIp1 远程管理IP1
     */
    public void setRemoteMgtIp1(String remoteMgtIp1) {
        this.remoteMgtIp1 = remoteMgtIp1;
    }

    /**
     * @return 远程管理IP2
     */
    public String getRemoteMgtIp2() {
        return remoteMgtIp2;
    }

    /**
     * @param remoteMgtIp2 远程管理IP2
     */
    public void setRemoteMgtIp2(String remoteMgtIp2) {
        this.remoteMgtIp2 = remoteMgtIp2;
    }

    /**
     * @return 远程管理用户
     */
    public String getRemoteMgtUser() {
        return remoteMgtUser;
    }

    /**
     * @param remoteMgtUser 远程管理用户
     */
    public void setRemoteMgtUser(String remoteMgtUser) {
        this.remoteMgtUser = remoteMgtUser;
    }

    /**
     * @return 远程管理密码
     */
    public String getRemoteMgtPwd() {
        return remoteMgtPwd;
    }

    /**
     * @param remoteMgtPwd 远程管理密码
     */
    public void setRemoteMgtPwd(String remoteMgtPwd) {
        this.remoteMgtPwd = remoteMgtPwd;
    }

    /**
     * @return 维保厂商
     */
    public String getMaintCompany() {
        return maintCompany;
    }

    /**
     * @param maintCompany 维保厂商
     */
    public void setMaintCompany(String maintCompany) {
        this.maintCompany = maintCompany;
    }

    /**
     * @return 维保电话
     */
    public String getMaintTel() {
        return maintTel;
    }

    /**
     * @param maintTel 维保电话
     */
    public void setMaintTel(String maintTel) {
        this.maintTel = maintTel;
    }

    /**
     * @return 购置日期
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * @param purchaseDate 购置日期
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * @return 保修期限
     */
    public BigDecimal getWarrantyPeriod() {
        return warrantyPeriod;
    }

    /**
     * @param warrantyPeriod 保修期限
     */
    public void setWarrantyPeriod(BigDecimal warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    /**
     * @return 设备提供商
     */
    public String getEquipSupplier() {
        return equipSupplier;
    }

    /**
     * @param equipSupplier 设备提供商
     */
    public void setEquipSupplier(String equipSupplier) {
        this.equipSupplier = equipSupplier;
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
     * @return the startEndDate
     */
    public Date getStartEndDate() {
        return startEndDate;
    }

    /**
     * @param startEndDate the startEndDate to set
     */
    public void setStartEndDate(Date startEndDate) {
        this.startEndDate = startEndDate;
    }

    /**
     * @return the roomName
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * @param roomName the roomName to set
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * @return the cabinetName
     */
    public String getCabinetName() {
        return cabinetName;
    }

    /**
     * @param cabinetName the cabinetName to set
     */
    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
    }

    /**
     * @return the rackName
     */
    public String getRackName() {
        return rackName;
    }

    /**
     * @param rackName the rackName to set
     */
    public void setRackName(String rackName) {
        this.rackName = rackName;
    }
}