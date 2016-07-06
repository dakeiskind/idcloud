package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

/**
 * Res equip fw 类.
 *
 * @author Chaohong.Mao
 */
public class ResEquipFw implements Serializable {
    /**
     * 静态变量 serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    private String equipSid;

    /**
     * 所属拓扑
     */
    private String resTopologySid;

    /**
     * 所属机框
     */
    private String equipRackSid;

    /**
     * 所属机框
     */
    private String equipRoomSid;

    /**
     * 所属机框
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

    /**
     * The Res topology name ve.
     */
    private String resTopologyNameVE;

    /**
     * The Res topology name dc.
     */
    private String resTopologyNameDC;

    /**
     * The Equip rack name.
     */
    private String equipRackName;

    /**
     * The Equip room name.
     */
    private String equipRoomName;

    /**
     * The Equip cabinet name.
     */
    private String equipCabinetName;

    /**
     * The Equip category name.
     */
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
     * The Name like.
     */
    private String nameLike;

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
     * The Sta total firewall.
     */
    private Long staTotalFirewall;


    /**
     * 获得 sta total firewall.
     *
     * @return the staTotalFirewall
     */
    public Long getStaTotalFirewall() {
        return staTotalFirewall;
    }

    /**
     * 设定 sta total firewall.
     *
     * @param staTotalFirewall the staTotalFirewall to set
     */
    public void setStaTotalFirewall(Long staTotalFirewall) {
        this.staTotalFirewall = staTotalFirewall;
    }

    /**
     * 获得 equip sid.
     *
     * @return 设备ID equip sid
     */
    public String getEquipSid() {
        return equipSid;
    }

    /**
     * 设定 equip sid.
     *
     * @param equipSid 设备ID
     */
    public void setEquipSid(String equipSid) {
        this.equipSid = equipSid;
    }

    /**
     * 获得 res topology sid.
     *
     * @return 所属拓扑 res topology sid
     */
    public String getResTopologySid() {
        return resTopologySid;
    }

    /**
     * 设定 res topology sid.
     *
     * @param resTopologySid 所属拓扑
     */
    public void setResTopologySid(String resTopologySid) {
        this.resTopologySid = resTopologySid;
    }

    /**
     * 获得 equip rack sid.
     *
     * @return 所属机框 equip rack sid
     */
    public String getEquipRackSid() {
        return equipRackSid;
    }

    /**
     * 设定 equip rack sid.
     *
     * @param equipRackSid 所属机框
     */
    public void setEquipRackSid(String equipRackSid) {
        this.equipRackSid = equipRackSid;
    }

    /**
     * 获得 created by.
     *
     * @return 创建人 created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 设定 created by.
     *
     * @param createdBy 创建人
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获得 created dt.
     *
     * @return 创建时间 created dt
     */
    public Date getCreatedDt() {
        return createdDt;
    }

    /**
     * 设定 created dt.
     *
     * @param createdDt 创建时间
     */
    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    /**
     * 获得 updated by.
     *
     * @return 更新人 updated by
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * 设定 updated by.
     *
     * @param updatedBy 更新人
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * 获得 updated dt.
     *
     * @return 更新时间 updated dt
     */
    public Date getUpdatedDt() {
        return updatedDt;
    }

    /**
     * 设定 updated dt.
     *
     * @param updatedDt 更新时间
     */
    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    /**
     * 获得 version.
     *
     * @return 版本号 version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * 设定 version.
     *
     * @param version 版本号
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * 获得 res topology name ve.
     *
     * @return the resTopologyNameVE
     */
    public String getResTopologyNameVE() {
        return resTopologyNameVE;
    }

    /**
     * 设定 res topology name ve.
     *
     * @param resTopologyNameVE the resTopologyNameVE to set
     */
    public void setResTopologyNameVE(String resTopologyNameVE) {
        this.resTopologyNameVE = resTopologyNameVE;
    }

    /**
     * 获得 res topology name dc.
     *
     * @return the resTopologyNameDC
     */
    public String getResTopologyNameDC() {
        return resTopologyNameDC;
    }

    /**
     * 设定 res topology name dc.
     *
     * @param resTopologyNameDC the resTopologyNameDC to set
     */
    public void setResTopologyNameDC(String resTopologyNameDC) {
        this.resTopologyNameDC = resTopologyNameDC;
    }

    /**
     * 获得 equip rack name.
     *
     * @return the equipRackName
     */
    public String getEquipRackName() {
        return equipRackName;
    }

    /**
     * 设定 equip rack name.
     *
     * @param equipRackName the equipRackName to set
     */
    public void setEquipRackName(String equipRackName) {
        this.equipRackName = equipRackName;
    }

    /**
     * 获得 equip category name.
     *
     * @return the equipCategoryName
     */
    public String getEquipCategoryName() {
        return equipCategoryName;
    }

    /**
     * 设定 equip category name.
     *
     * @param equipCategoryName the equipCategoryName to set
     */
    public void setEquipCategoryName(String equipCategoryName) {
        this.equipCategoryName = equipCategoryName;
    }

    /**
     * 获得 equip category.
     *
     * @return the equipCategory
     */
    public String getEquipCategory() {
        return equipCategory;
    }

    /**
     * 设定 equip category.
     *
     * @param equipCategory the equipCategory to set
     */
    public void setEquipCategory(String equipCategory) {
        this.equipCategory = equipCategory;
    }

    /**
     * 获得 equip type.
     *
     * @return the equipType
     */
    public String getEquipType() {
        return equipType;
    }

    /**
     * 设定 equip type.
     *
     * @param equipType the equipType to set
     */
    public void setEquipType(String equipType) {
        this.equipType = equipType;
    }

    /**
     * 获得 location number.
     *
     * @return the locationNumber
     */
    public String getLocationNumber() {
        return locationNumber;
    }

    /**
     * 设定 location number.
     *
     * @param locationNumber the locationNumber to set
     */
    public void setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
    }

    /**
     * 获得 name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * 设定 name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获得 serial number.
     *
     * @return the serialNumber
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * 设定 serial number.
     *
     * @param serialNumber the serialNumber to set
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * 获得 brand.
     *
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 设定 brand.
     *
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * 获得 model.
     *
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * 设定 model.
     *
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 获得 remote mgt ip 1.
     *
     * @return the remoteMgtIp1
     */
    public String getRemoteMgtIp1() {
        return remoteMgtIp1;
    }

    /**
     * 设定 remote mgt ip 1.
     *
     * @param remoteMgtIp1 the remoteMgtIp1 to set
     */
    public void setRemoteMgtIp1(String remoteMgtIp1) {
        this.remoteMgtIp1 = remoteMgtIp1;
    }

    /**
     * 获得 remote mgt ip 2.
     *
     * @return the remoteMgtIp2
     */
    public String getRemoteMgtIp2() {
        return remoteMgtIp2;
    }

    /**
     * 设定 remote mgt ip 2.
     *
     * @param remoteMgtIp2 the remoteMgtIp2 to set
     */
    public void setRemoteMgtIp2(String remoteMgtIp2) {
        this.remoteMgtIp2 = remoteMgtIp2;
    }

    /**
     * 获得 remote mgt user.
     *
     * @return the remoteMgtUser
     */
    public String getRemoteMgtUser() {
        return remoteMgtUser;
    }

    /**
     * 设定 remote mgt user.
     *
     * @param remoteMgtUser the remoteMgtUser to set
     */
    public void setRemoteMgtUser(String remoteMgtUser) {
        this.remoteMgtUser = remoteMgtUser;
    }

    /**
     * 获得 remote mgt pwd.
     *
     * @return the remoteMgtPwd
     */
    public String getRemoteMgtPwd() {
        return remoteMgtPwd;
    }

    /**
     * 设定 remote mgt pwd.
     *
     * @param remoteMgtPwd the remoteMgtPwd to set
     */
    public void setRemoteMgtPwd(String remoteMgtPwd) {
        this.remoteMgtPwd = remoteMgtPwd;
    }

    /**
     * 获得 relevance ip.
     *
     * @return the relevanceIp
     */
    public String getRelevanceIp() {
        return relevanceIp;
    }

    /**
     * 设定 relevance ip.
     *
     * @param relevanceIp the relevanceIp to set
     */
    public void setRelevanceIp(String relevanceIp) {
        this.relevanceIp = relevanceIp;
    }

    /**
     * 获得 name like.
     *
     * @return the nameLike
     */
    public String getNameLike() {
        return nameLike;
    }

    /**
     * 设定 name like.
     *
     * @param nameLike the nameLike to set
     */
    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    /**
     * 获得 equip room sid.
     *
     * @return the equipRoomSid
     */
    public String getEquipRoomSid() {
        return equipRoomSid;
    }

    /**
     * 设定 equip room sid.
     *
     * @param equipRoomSid the equipRoomSid to set
     */
    public void setEquipRoomSid(String equipRoomSid) {
        this.equipRoomSid = equipRoomSid;
    }

    /**
     * 获得 equip cabinet sid.
     *
     * @return the equipCabinetSid
     */
    public String getEquipCabinetSid() {
        return equipCabinetSid;
    }

    /**
     * 设定 equip cabinet sid.
     *
     * @param equipCabinetSid the equipCabinetSid to set
     */
    public void setEquipCabinetSid(String equipCabinetSid) {
        this.equipCabinetSid = equipCabinetSid;
    }

    /**
     * 获得 equip room name.
     *
     * @return the equipRoomName
     */
    public String getEquipRoomName() {
        return equipRoomName;
    }

    /**
     * 设定 equip room name.
     *
     * @param equipRoomName the equipRoomName to set
     */
    public void setEquipRoomName(String equipRoomName) {
        this.equipRoomName = equipRoomName;
    }

    /**
     * 获得 equip cabinet name.
     *
     * @return the equipCabinetName
     */
    public String getEquipCabinetName() {
        return equipCabinetName;
    }

    /**
     * 设定 equip cabinet name.
     *
     * @param equipCabinetName the equipCabinetName to set
     */
    public void setEquipCabinetName(String equipCabinetName) {
        this.equipCabinetName = equipCabinetName;
    }
}