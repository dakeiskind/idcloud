package com.h3c.idcloud.infrastructure.common.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户信息
 *
 * @author zharong
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户SID
     */
    private Long userSid;

    /**
     * 用户SIDs,用于用户批量操作
     */
    private String userSids;

    /**
     * 账号
     */
    private String account;

    /**
     * 账号
     */
    private String accountName;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 用户类型名称
     */
    private String userTypeName;

    /**
     * 判断用户是否重复
     */
    private String accountRepeat;

    /**
     * 密码
     */
    private String password64;

    /**
     * 密码
     */
    private String password;

    private String uuid;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 性别 0:男 1:女
     */
    private Integer sex;

    /**
     * 性别名称
     */
    private String sexName;

    /**
     * 电子邮件地址
     */
    private String email;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 所属组织ID
     */
    private Long orgSid;

    /**
     * 所属部门名称
     */
    private String depName;

    //ChengQi start
    /**
     * 一级业务（业务属性）
     */
    private Long fbizSid;

    /**
     * 一级业务（业务属性）名
     */
    private String fbizText;

    /**
     * 业务名称
     */
    private String bizText;
    //ChengQi end

    /**
     * 备注
     */
    private String remark;

    /**
     * 密码错误次数
     */
    private Integer errorCount;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;

    /**
     * 上次登录IP地址
     */
    private String lastLoginIp;

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

    private Long mgtObjSid;

    /**
     * 更新时间
     */
    private Date updatedDt;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 用户关联系统模块列表
     */
//	private List<Module> modules;

    /**
     * 用户关联角色列表
     */
//	private List<Role> roles;

    /**
     * 用户管理关系列表
     */
//	private List<UserMgtObjKey> userMgtObjs;

    /**
     * 传入该新增用户的角色
     */
    private List<String> roleArr;

    /**
     * 用户关联角色值
     */
    private String roleValue;

    /**
     * 用户状态
     */
    private String status;


    /**
     * 用户状态名称
     */
    private String statusName;

    /**
     * 租户ID
     */
    private Long tenantSid;

    /**
     * 账号
     */
    private String roleSid;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 服务限制数量
     */
    private Integer serviceLimitQuantity;

    /**
     * 申请理由
     */
    private String applyReason;

    /**
     * 全部角色
     */
    private String roleName;

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 已分配邮箱的用户
     */
    private String alloctedEmailAccount;

    /**
     * 复选框
     */
    private boolean checkbox;

    /**
     * 产品经理姓名
     */
    private String pmName;

    /**
     * 产品经理电话
     */
    private String pmTel;

    /**
     * 产品经理邮箱
     */
    private String pmEmail;

    private String orgName;

    /**
     * 项目id
     *
     * @return
     */
    private String userMgtObj;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoleValue() {
        return roleValue;
    }

    public void setRoleValue(String roleValue) {
        this.roleValue = roleValue;
    }

    /**
     * @return 用户SID
     */
    public Long getUserSid() {
        return userSid;
    }

    /**
     * @param userSid 用户SID
     */
    public void setUserSid(Long userSid) {
        this.userSid = userSid;
    }

    /**
     * @return the userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * @return the userTypeName
     */
    public String getUserTypeName() {
        return userTypeName;
    }

    /**
     * @param userTypeName the userTypeName to set
     */
    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    /**
     * @return 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account;
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
     * @return the password64
     */
    public String getPassword64() {
        return password64;
    }

    /**
     * @param password64 the password64 to set
     */
    public void setPassword64(String password64) {
        this.password64 = password64;
    }

    /**
     * @return 用户真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * @param realName 用户真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * @return 性别 0:男 1:女
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * @param sex 性别 0:男 1:女
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * @return 电子邮件地址
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email 电子邮件地址
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return 手机
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile 手机
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return 所属部门名称
     */
    public String getDepName() {
        return depName;
    }

    /**
     * @param depName 所属部门名称
     */
    public void setDepName(String depName) {
        this.depName = depName;
    }

    /**
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return 密码错误次数
     */
    public Integer getErrorCount() {
        return errorCount;
    }

    /**
     * @param errorCount 密码错误次数
     */
    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    /**
     * @return 上次登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * @param lastLoginTime 上次登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * @return 上次登录IP地址
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * @param lastLoginIp 上次登录IP地址
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
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
     * @return the modules
     */
//	public List<Module> getModules() {
//
//		return modules;
//	}

    /**
     * @param modules
     *            the modules to set
     */
//	public void setModules(List<Module> modules) {
//
//		this.modules = modules;
//	}

    /**
     * @return the roles
     */
//	public List<Role> getRoles() {
//		return roles;
//	}

    /**
     * @param roles
     *            the roles to set
     */
//	public void setRoles(List<Role> roles) {
//		this.roles = roles;
//	}

    /**
     * @return the sexName
     */
    public String getSexName() {
        return sexName;
    }

    /**
     * @param sexName the sexName to set
     */
    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    /**
     * @return the tenantSid
     */
    public Long getTenantSid() {
        return tenantSid;
    }

    /**
     * @param tenantSid the tenantSid to set
     */
    public void setTenantSid(Long tenantSid) {
        this.tenantSid = tenantSid;
    }

    /**
     * @return the tenantName
     */
    public String getTenantName() {
        return tenantName;
    }

    /**
     * @param tenantName the tenantName to set
     */
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    /**
     * @return the statusName
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * @param statusName the statusName to set
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * @return the serviceLimitQuantity
     */
    public Integer getServiceLimitQuantity() {
        return serviceLimitQuantity;
    }

    /**
     * @param serviceLimitQuantity the serviceLimitQuantity to set
     */
    public void setServiceLimitQuantity(Integer serviceLimitQuantity) {
        this.serviceLimitQuantity = serviceLimitQuantity;
    }

    /**
     * @return the applyReason
     */
    public String getApplyReason() {
        return applyReason;
    }

    /**
     * @param applyReason the applyReason to set
     */
    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * @return the oldPassword
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * @param oldPassword the oldPassword to set
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getAlloctedEmailAccount() {
        return alloctedEmailAccount;
    }

    public void setAlloctedEmailAccount(String alloctedEmailAccount) {
        this.alloctedEmailAccount = alloctedEmailAccount;
    }

    /**
     * @return the checkbox
     */
    public boolean isCheckbox() {
        return checkbox;
    }

    /**
     * @param checkbox the checkbox to set
     */
    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public List<String> getRoleArr() {
        return roleArr;
    }

    public void setRoleArr(List<String> roleArr) {
        this.roleArr = roleArr;
    }

    public String getRoleSid() {
        return roleSid;
    }

    public void setRoleSid(String roleSid) {
        this.roleSid = roleSid;
    }

    public Long getOrgSid() {
        return orgSid;
    }

    public void setOrgSid(Long orgSid) {
        this.orgSid = orgSid;
    }

    public String getAccountRepeat() {
        return accountRepeat;
    }

    public void setAccountRepeat(String accountRepeat) {
        this.accountRepeat = accountRepeat;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public String getPmTel() {
        return pmTel;
    }

    public void setPmTel(String pmTel) {
        this.pmTel = pmTel;
    }

    public String getPmEmail() {
        return pmEmail;
    }

    public void setPmEmail(String pmEmail) {
        this.pmEmail = pmEmail;
    }

    public String getBizText() {
        return bizText;
    }

    public void setBizText(String bizText) {
        this.bizText = bizText;
    }

    public Long getFbizSid() {
        return fbizSid;
    }

    public void setFbizSid(Long fbizSid) {
        this.fbizSid = fbizSid;
    }

    public String getFbizText() {
        return fbizText;
    }

    public void setFbizText(String fbizText) {
        this.fbizText = fbizText;
    }

    public String getUserSids() {
        return userSids;
    }

    public void setUserSids(String userSids) {
        this.userSids = userSids;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the userMgtObj
     */
    public String getUserMgtObj() {
        return userMgtObj;
    }

    /**
     * @param userMgtObj the userMgtObj to set
     */
    public void setUserMgtObj(String userMgtObj) {
        this.userMgtObj = userMgtObj;
    }

//	public List<UserMgtObjKey> getUserMgtObjs() {
//		return userMgtObjs;
//	}

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName the accountName to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

//	public void setUserMgtObjs(List<UserMgtObjKey> userMgtObjs) {
//		this.userMgtObjs = userMgtObjs;
//	}

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return the mgtObjSid
     */
    public Long getMgtObjSid() {
        return mgtObjSid;
    }

    /**
     * @param mgtObjSid the mgtObjSid to set
     */
    public void setMgtObjSid(Long mgtObjSid) {
        this.mgtObjSid = mgtObjSid;
    }

}