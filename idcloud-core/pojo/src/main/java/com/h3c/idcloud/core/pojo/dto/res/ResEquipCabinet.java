package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

/**
 * Res equip cabinet 类.
 *
 * @author Chaohong.Mao
 */
public class ResEquipCabinet implements Serializable {
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
     * 位置
     */
    private String location;

    /**
     * 所属机房
     */
    private String equipRoomSid;

    /**
     * The Equip server room name.
     */
    private String equipServerRoomName;

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
     * The Parent topology sid.
     */
    private String parentTopologySid;
    /**
     * The Res topology name dc.
     */
    private String resTopologyNameDC;
    /**
     * The Res topology name ve.
     */
    private String resTopologyNameVE;
    /**
     * The Sta total cabinet.
     */
    private Long staTotalCabinet;

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
     * 获得 location.
     *
     * @return 位置 location
     */
    public String getLocation() {
        return location;
    }

    /**
     * 设定 location.
     *
     * @param location 位置
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 获得 equip room sid.
     *
     * @return 所属机房 equip room sid
     */
    public String getEquipRoomSid() {
        return equipRoomSid;
    }

    /**
     * 设定 equip room sid.
     *
     * @param equipRoomSid 所属机房
     */
    public void setEquipRoomSid(String equipRoomSid) {
        this.equipRoomSid = equipRoomSid;
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
     * 获得 equip server room name.
     *
     * @return the equipServerRoomName
     */
    public String getEquipServerRoomName() {
        return equipServerRoomName;
    }

    /**
     * 设定 equip server room name.
     *
     * @param equipServerRoomName the equipServerRoomName to set
     */
    public void setEquipServerRoomName(String equipServerRoomName) {
        this.equipServerRoomName = equipServerRoomName;
    }

    /**
     * 获得 parent topology sid.
     *
     * @return the parentTopologySid
     */
    public String getParentTopologySid() {
        return parentTopologySid;
    }

    /**
     * 设定 parent topology sid.
     *
     * @param parentTopologySid the parentTopologySid to set
     */
    public void setParentTopologySid(String parentTopologySid) {
        this.parentTopologySid = parentTopologySid;
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
     * 获得 sta total cabinet.
     *
     * @return the staTotalCabinet
     */
    public Long getStaTotalCabinet() {
        return staTotalCabinet;
    }

    /**
     * 设定 sta total cabinet.
     *
     * @param staTotalCabinet the staTotalCabinet to set
     */
    public void setStaTotalCabinet(Long staTotalCabinet) {
        this.staTotalCabinet = staTotalCabinet;
    }
}