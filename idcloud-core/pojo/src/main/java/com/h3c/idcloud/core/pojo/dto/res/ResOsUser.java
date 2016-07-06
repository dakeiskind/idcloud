package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResOsUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 操作系统用户ID
     */
    private String osUserSid;

    /**
     * 资源ID
     */
    private String resSid;

    /**
     * 资源类型
     */
    private String resType;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户组
     */
    private String userGroup;

    /**
     * 权限
     */
    private String privilege;

    /**
     * 家目录路径
     */
    private String homePath;

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
     * @return 操作系统用户ID
     */
    public String getOsUserSid() {
        return osUserSid;
    }

    /**
     * @param osUserSid 操作系统用户ID
     */
    public void setOsUserSid(String osUserSid) {
        this.osUserSid = osUserSid;
    }

    /**
     * @return 资源ID
     */
    public String getResSid() {
        return resSid;
    }

    /**
     * @param resSid 资源ID
     */
    public void setResSid(String resSid) {
        this.resSid = resSid;
    }

    /**
     * @return 资源类型
     */
    public String getResType() {
        return resType;
    }

    /**
     * @param resType 资源类型
     */
    public void setResType(String resType) {
        this.resType = resType;
    }

    /**
     * @return 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return 用户组
     */
    public String getUserGroup() {
        return userGroup;
    }

    /**
     * @param userGroup 用户组
     */
    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    /**
     * @return 权限
     */
    public String getPrivilege() {
        return privilege;
    }

    /**
     * @param privilege 权限
     */
    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    /**
     * @return 家目录路径
     */
    public String getHomePath() {
        return homePath;
    }

    /**
     * @param homePath 家目录路径
     */
    public void setHomePath(String homePath) {
        this.homePath = homePath;
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
}