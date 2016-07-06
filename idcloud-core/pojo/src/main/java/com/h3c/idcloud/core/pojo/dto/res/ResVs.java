package com.h3c.idcloud.core.pojo.dto.res;


import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.DvPortGroupVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.DvSwitchVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.VSwitchVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Res vs 类.
 *
 * @author Chaohong.Mao
 */
public class ResVs implements Serializable {
    /**
     * 静态变量 serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 虚拟化集群ID
     */
    private String resVsSid;

    /**
     * 所属虚拟化环境ID
     */
    private String parentTopologySid;

    /**
     * 分布式交换机名称
     */
    private String resVsName;

    /**
     * 虚拟交换机类型
     */
    private String resVsType;

    /**
     * UUID
     */
    private String uuid;

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

    /**
     * vlan资源池Sid
     */
    private String resPoolSid;

    /**
     * vs是否关联Vlan
     */
    private String relation;

    /**
     * 虚拟化环境ID
     */
    private String resTopologySid;

    /**
     * 虚拟化环境管理地址
     */
    private String managementUrl;

    /**
     * 虚拟化环境管理用户帐号
     */
    private String managementAccount;

    /**
     * 虚拟化环境管理用户密码
     */
    private String managementPassword;

    /**
     * 端口组
     */
    private List<ResVsPortGroup> resVsPortGroups = new ArrayList<ResVsPortGroup>();

    /**
     * 构造 Res vs 的实例.
     */
    public ResVs() {

    }

    /**
     * MQ标准交换机对象转换平台标准交换机对象
     *
     * @param vSwitchVO the v switch vo
     */
    public ResVs(VSwitchVO vSwitchVO) {
        this.resVsName = vSwitchVO.getName();
        this.uuid = vSwitchVO.getUuid();
    }

    /**
     * MQ分布式交换机对象转换平台分布式交换机对象
     *
     * @param dvSwitchVO the dv switch vo
     */
    public ResVs(DvSwitchVO dvSwitchVO) {
        this.resVsName = dvSwitchVO.getName();
        this.uuid = dvSwitchVO.getUuid();
        if (dvSwitchVO.getDvPortGroupInfos() != null && dvSwitchVO.getDvPortGroupInfos().size() > 0) {
            for (DvPortGroupVO dvPortGroupVO : dvSwitchVO.getDvPortGroupInfos()) {
                this.resVsPortGroups.add(new ResVsPortGroup(dvPortGroupVO));
            }
        }

    }

    /**
     * 获得 res vs sid.
     *
     * @return 虚拟化集群ID res vs sid
     */
    public String getResVsSid() {
        return resVsSid;
    }

    /**
     * 设定 res vs sid.
     *
     * @param resVsSid 虚拟化集群ID
     */
    public void setResVsSid(String resVsSid) {
        this.resVsSid = resVsSid;
    }

    /**
     * 获得 parent topology sid.
     *
     * @return 所属虚拟化环境ID parent topology sid
     */
    public String getParentTopologySid() {
        return parentTopologySid;
    }

    /**
     * 设定 parent topology sid.
     *
     * @param parentTopologySid 所属虚拟化环境ID
     */
    public void setParentTopologySid(String parentTopologySid) {
        this.parentTopologySid = parentTopologySid;
    }

    /**
     * 获得 res vs name.
     *
     * @return 分布式交换机名称 res vs name
     */
    public String getResVsName() {
        return resVsName;
    }

    /**
     * 设定 res vs name.
     *
     * @param resVsName 分布式交换机名称
     */
    public void setResVsName(String resVsName) {
        this.resVsName = resVsName;
    }

    /**
     * 获得 res vs type.
     *
     * @return 虚拟交换机类型 res vs type
     */
    public String getResVsType() {
        return resVsType;
    }

    /**
     * 设定 res vs type.
     *
     * @param resVsType 虚拟交换机类型
     */
    public void setResVsType(String resVsType) {
        this.resVsType = resVsType;
    }

    /**
     * 获得 uuid.
     *
     * @return UUID uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设定 uuid.
     *
     * @param uuid UUID
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获得 description.
     *
     * @return 描述 description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设定 description.
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
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
     * 获得 res pool sid.
     *
     * @return the res pool sid
     */
    public String getResPoolSid() {
        return resPoolSid;
    }

    /**
     * 设定 res pool sid.
     *
     * @param resPoolSid the res pool sid
     */
    public void setResPoolSid(String resPoolSid) {
        this.resPoolSid = resPoolSid;
    }

    /**
     * 获得 relation.
     *
     * @return the relation
     */
    public String getRelation() {
        return relation;
    }

    /**
     * 设定 relation.
     *
     * @param relation the relation
     */
    public void setRelation(String relation) {
        this.relation = relation;
    }

    /**
     * 获得 res topology sid.
     *
     * @return the resTopologySid
     */
    public String getResTopologySid() {
        return resTopologySid;
    }

    /**
     * 设定 res topology sid.
     *
     * @param resTopologySid the resTopologySid to set
     */
    public void setResTopologySid(String resTopologySid) {
        this.resTopologySid = resTopologySid;
    }

    /**
     * 获得 management url.
     *
     * @return the managementUrl
     */
    public String getManagementUrl() {
        return managementUrl;
    }

    /**
     * 设定 management url.
     *
     * @param managementUrl the managementUrl to set
     */
    public void setManagementUrl(String managementUrl) {
        this.managementUrl = managementUrl;
    }

    /**
     * 获得 management account.
     *
     * @return the managementAccount
     */
    public String getManagementAccount() {
        return managementAccount;
    }

    /**
     * 设定 management account.
     *
     * @param managementAccount the managementAccount to set
     */
    public void setManagementAccount(String managementAccount) {
        this.managementAccount = managementAccount;
    }

    /**
     * 获得 management password.
     *
     * @return the managementPassword
     */
    public String getManagementPassword() {
        return managementPassword;
    }

    /**
     * 设定 management password.
     *
     * @param managementPassword the managementPassword to set
     */
    public void setManagementPassword(String managementPassword) {
        this.managementPassword = managementPassword;
    }

    /**
     * 获得 res vs port groups.
     *
     * @return the resVsPortGroups
     */
    public List<ResVsPortGroup> getResVsPortGroups() {
        return resVsPortGroups;
    }

    /**
     * 设定 res vs port groups.
     *
     * @param resVsPortGroups the resVsPortGroups to set
     */
    public void setResVsPortGroups(List<ResVsPortGroup> resVsPortGroups) {
        this.resVsPortGroups = resVsPortGroups;
    }
}