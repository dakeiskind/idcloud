package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

/**
 * Res equip rack 类.
 *
 * @author Chaohong.Mao
 */
public class ResEquipRack implements Serializable {
    /**
     * 静态变量 serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    private String equipSid;

    /**
     * 名称
     */
    private String name;
    /**
     * The Name like.
     */
    private String nameLike;
    /**
     * 所属拓扑
     */
    private String resTopologySid;

    /**
     * 管理地址
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
     * The Equip cabinet name.
     */
    private String equipCabinetName;

    /**
     * The Roomname.
     */
    private String roomname;

    /**
     * The Sta total rack.
     */
    private String staTotalRack;

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
     * 获得 equip cabinet sid.
     *
     * @return 管理地址 equip cabinet sid
     */
    public String getEquipCabinetSid() {
        return equipCabinetSid;
    }

    /**
     * 设定 equip cabinet sid.
     *
     * @param equipCabinetSid 管理地址
     */
    public void setEquipCabinetSid(String equipCabinetSid) {
        this.equipCabinetSid = equipCabinetSid;
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
     * 获得 roomname.
     *
     * @return the roomname
     */
    public String getRoomname() {
        return roomname;
    }

    /**
     * 设定 roomname.
     *
     * @param roomname the roomname to set
     */
    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    /**
     * 获得 sta total rack.
     *
     * @return the staTotalRack
     */
    public String getStaTotalRack() {
        return staTotalRack;
    }

    /**
     * 设定 sta total rack.
     *
     * @param staTotalRack the staTotalRack to set
     */
    public void setStaTotalRack(String staTotalRack) {
        this.staTotalRack = staTotalRack;
    }
}