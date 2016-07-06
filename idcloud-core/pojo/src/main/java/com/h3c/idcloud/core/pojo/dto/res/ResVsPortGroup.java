package com.h3c.idcloud.core.pojo.dto.res;


import com.google.common.base.Strings;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.DvPortGroupVO;

import java.io.Serializable;
import java.util.Date;

/**
 * Res vs port group 类.
 *
 * @author Chaohong.Mao
 */
public class ResVsPortGroup implements Serializable {
    /**
     * 静态变量 serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Id.
     */
    private String id;

    /**
     * The Res vs sid.
     */
    private String resVsSid;

    /**
     * The Name.
     */
    private String name;

    /**
     * The Vlan id.
     */
    private String vlanId;

    /**
     * The Total ports.
     */
    private Long totalPorts;

    /**
     * The Available ports.
     */
    private Long availablePorts;

    /**
     * The Uuid.
     */
    private String uuid;

    /**
     * The Created by.
     */
    private String createdBy;

    /**
     * The Created dt.
     */
    private Date createdDt;

    /**
     * The Updated by.
     */
    private String updatedBy;

    /**
     * The Updated dt.
     */
    private Date updatedDt;

    /**
     * The Version.
     */
    private Long version;

    /**
     * 构造 Res vs port group 的实例.
     */
    public ResVsPortGroup() {

    }

    /**
     * 构造 Res vs port group 的实例.
     *
     * @param dvPortGroupVO the dv port group vo
     */
    public ResVsPortGroup(DvPortGroupVO dvPortGroupVO) {
        this.name = dvPortGroupVO.getName();
        this.uuid = dvPortGroupVO.getUuid();
        this.vlanId = dvPortGroupVO.getVlanId();
        this.totalPorts = Long.parseLong(Strings.nullToEmpty(dvPortGroupVO.getNumPorts()));
    }

    /**
     * 获得 id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * 设定 id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获得 res vs sid.
     *
     * @return the res vs sid
     */
    public String getResVsSid() {
        return resVsSid;
    }

    /**
     * 设定 res vs sid.
     *
     * @param resVsSid the res vs sid
     */
    public void setResVsSid(String resVsSid) {
        this.resVsSid = resVsSid;
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
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获得 vlan id.
     *
     * @return the vlan id
     */
    public String getVlanId() {
        return vlanId;
    }

    /**
     * 设定 vlan id.
     *
     * @param vlanId the vlan id
     */
    public void setVlanId(String vlanId) {
        this.vlanId = vlanId;
    }

    /**
     * 获得 total ports.
     *
     * @return the total ports
     */
    public Long getTotalPorts() {
        return totalPorts;
    }

    /**
     * 设定 total ports.
     *
     * @param totalPorts the total ports
     */
    public void setTotalPorts(Long totalPorts) {
        this.totalPorts = totalPorts;
    }

    /**
     * 获得 available ports.
     *
     * @return the available ports
     */
    public Long getAvailablePorts() {
        return availablePorts;
    }

    /**
     * 设定 available ports.
     *
     * @param availablePorts the available ports
     */
    public void setAvailablePorts(Long availablePorts) {
        this.availablePorts = availablePorts;
    }

    /**
     * 获得 uuid.
     *
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设定 uuid.
     *
     * @param uuid the uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获得 created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 设定 created by.
     *
     * @param createdBy the created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获得 created dt.
     *
     * @return the created dt
     */
    public Date getCreatedDt() {
        return createdDt;
    }

    /**
     * 设定 created dt.
     *
     * @param createdDt the created dt
     */
    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    /**
     * 获得 updated by.
     *
     * @return the updated by
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * 设定 updated by.
     *
     * @param updatedBy the updated by
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * 获得 updated dt.
     *
     * @return the updated dt
     */
    public Date getUpdatedDt() {
        return updatedDt;
    }

    /**
     * 设定 updated dt.
     *
     * @param updatedDt the updated dt
     */
    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    /**
     * 获得 version.
     *
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * 设定 version.
     *
     * @param version the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }
}