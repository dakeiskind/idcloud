package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResEquipRoom implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 机房ID
     */
    private String equipRoomSid;

    /**
     * 所属拓扑
     */
    private String resTopologySid;
    private String parentTopologySid;
    private String resTopologyNameDC;
    private String resTopologyNameVE;
    private String resTopologyType;

    /**
     * 名称
     */
    private String name;
    private String nameLike;

    /**
     * 位置
     */
    private String location;

    /**
     * 描述
     */
    private String description;

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

    private int staTotalServerRoom;

    /**
     * @return the staTotalServerRoom
     */
    public int getStaTotalServerRoom() {
        return staTotalServerRoom;
    }

    /**
     * @param staTotalServerRoom the staTotalServerRoom to set
     */
    public void setStaTotalServerRoom(int staTotalServerRoom) {
        this.staTotalServerRoom = staTotalServerRoom;
    }

    /**
     * @return 机房ID
     */
    public String getEquipRoomSid() {
        return equipRoomSid;
    }

    /**
     * @param equipRoomSid 机房ID
     */
    public void setEquipRoomSid(String equipRoomSid) {
        this.equipRoomSid = equipRoomSid;
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
     * @return 位置
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location 位置
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return the parentTopologySid
     */
    public String getParentTopologySid() {
        return parentTopologySid;
    }

    /**
     * @param parentTopologySid the parentTopologySid to set
     */
    public void setParentTopologySid(String parentTopologySid) {
        this.parentTopologySid = parentTopologySid;
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
     * @return the resTopologyType
     */
    public String getResTopologyType() {
        return resTopologyType;
    }

    /**
     * @param resTopologyType the resTopologyType to set
     */
    public void setResTopologyType(String resTopologyType) {
        this.resTopologyType = resTopologyType;
    }

    /**
     * @return the nameLike
     */
    public String getNameLike() {
        return nameLike;
    }

    /**
     * @param nameLike the nameLike to set
     */
    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }
}