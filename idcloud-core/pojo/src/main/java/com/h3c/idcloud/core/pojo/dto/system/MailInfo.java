package com.h3c.idcloud.core.pojo.dto.system;

import java.util.Date;

/**
 * @author zhachaoy
 *
 */
public class MailInfo {

	/**
	 * 用户姓名
	 */
	private String userName;
	
	/**
	 * 用户邮箱
	 */
	private String userEmail;
	
	/**
	 * 租户姓名
	 */
	private String tenantName;
	
	/**
	 * 租户邮箱
	 */
	private String tenantEmail;
	
	/**
	 * 邮件主题
	 */
	private String mailSubject;
	
	/**
	 * 邮件内容模板路径
	 */
	private String mailContentFilePath;
	
	/**
	 * 云主机名称
	 */
	private String vmName;
	
	/**
	 * 操作系统名称
	 */
	private String osName;
	
	/**
	 * 云主机外网ip
	 */
	private String vmIp;
	
	/**
	 * 云主机内网ip
	 */
	private String intranetIp;
	
	/**
	 * 磁盘大小
	 */
	private String dataDisk;
	
	/**
	 * CPU核数
	 */
	private String cpuCores;
	
	/**
	 * 内存大小
	 */
	private String memorySize;
	
	/**
	 * 主机管理账号
	 */
	private String managementAccount;
	
	/**
	 * 主机管理密码
	 */
	private String managementPassword;
	
	/**
	 * 服务名称
	 */
	private String serviceName;
	
	/**
	 * 订单编号
	 */
	private String orderId;
	
	public String allocateDiskSize;
	
    /**
     * 到期时间
     */
    private Date expiringDate;
    
    /**
     * 开通时间
     */
    private Date dredgeDate;
    
    public String volumeTypeName;
    /**
     * Ex分配域名
     */
    private String allocateDomain;
    /**
     * Ex用户数
     */
    private Long userAmount;
    /**
     * Ex单个邮箱容量
     */
    private Long singleMailboxCapacity;
    /**
     * Sp分配存储容量
     */
    private Long allocateStorageCapacity;
    /**
     * Sp分配地址
     */
    private String allocateSharepointAddress;
	
	/**
     * @return 用户姓名
     */
	public String getUserName() {
		return userName;
	}
	
    /**
     * @param userName 
	 *         用户姓名
     */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
     * @return 用户邮箱
     */
	public String getUserEmail() {
		return userEmail;
	}
	
    /**
     * @param userEmail 
	 *         用户邮箱
     */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	/**
     * @return 租户姓名
     */
	public String getTenantName() {
		return tenantName;
	}
	
	/**
     * @param tenantName 
	 *         租户姓名
     */
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	
	/**
     * @return 租户邮箱
     */
	public String getTenantEmail() {
		return tenantEmail;
	}
	
	/**
     * @param tenantEmail 
	 *         租户邮箱
     */
	public void setTenantEmail(String tenantEmail) {
		this.tenantEmail = tenantEmail;
	}
	
	/**
     * @return 邮件主题
     */
	public String getMailSubject() {
		return mailSubject;
	}
	
	/**
     * @param mailSubject 
	 *         邮件主题
     */
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	
	/**
     * @return 邮件内容模板路径
     */
	public String getMailContentFilePath() {
		return mailContentFilePath;
	}
	
	/**
     * @param mailContentFilePath 
	 *         邮件内容模板路径
     */
	public void setMailContentFilePath(String mailContentFilePath) {
		this.mailContentFilePath = mailContentFilePath;
	}
	
	/**
     * @return 云主机名称
     */
	public String getVmName() {
		return vmName;
	}
	
	/**
     * @param vmName 
	 *         云主机名称
     */
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}
	
	/**
     * @return 操作系统名称
     */
	public String getOsName() {
		return osName;
	}
	
	/**
     * @param osName 
	 *         操作系统名称
     */
	public void setOsName(String osName) {
		this.osName = osName;
	}
	
	/**
     * @return 云主机外网ip
     */
	public String getVmIp() {
		return vmIp;
	}
	
	/**
     * @param vmIp 
	 *         云主机外网ip
     */
	public void setVmIp(String vmIp) {
		this.vmIp = vmIp;
	}
	
	/**
     * @return 云主机内网ip
     */
	public String getIntranetIp() {
		return intranetIp;
	}
	
	/**
     * @param intranetIp 
	 *         云主机内网ip
     */
	public void setIntranetIp(String intranetIp) {
		this.intranetIp = intranetIp;
	}
	
	/**
     * @return 磁盘大小
     */
	public String getDataDisk() {
		return dataDisk;
	}
	
	/**
     * @param dataDisk 
	 *         磁盘大小
     */
	public void setDataDisk(String dataDisk) {
		this.dataDisk = dataDisk;
	}
	
	/**
     * @return CPU核数
     */
	public String getCpuCores() {
		return cpuCores;
	}
	
	/**
     * @param cpuCores 
	 *         CPU核数
     */
	public void setCpuCores(String cpuCores) {
		this.cpuCores = cpuCores;
	}
	
	/**
     * @return 内存大小
     */
	public String getMemorySize() {
		return memorySize;
	}
	
	/**
     * @param memorySize 
	 *         内存大小
     */
	public void setMemorySize(String memorySize) {
		this.memorySize = memorySize;
	}
	
	/**
     * @return 主机管理账号
     */
	public String getManagementAccount() {
		return managementAccount;
	}
	
	/**
     * @param managementAccount 
	 *         主机管理账号
     */
	public void setManagementAccount(String managementAccount) {
		this.managementAccount = managementAccount;
	}
	
	/**
     * @return 主机管理密码
     */
	public String getManagementPassword() {
		return managementPassword;
	}
	
	/**
     * @param managementPassword 
	 *         主机管理密码
     */
	public void setManagementPassword(String managementPassword) {
		this.managementPassword = managementPassword;
	}
	
	/**
     * @return 服务名称
     */
	public String getServiceName() {
		return serviceName;
	}
	
	/**
     * @param serviceName 
	 *         服务名称
     */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	/**
     * @return 订单编号
     */
	public String getOrderId() {
		return orderId;
	}
	
	/**
     * @param orderId 
	 *         订单编号
     */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAllocateDiskSize() {
		return allocateDiskSize;
	}

	public void setAllocateDiskSize(String allocateDiskSize) {
		this.allocateDiskSize = allocateDiskSize;
	}

	public Date getExpiringDate() {
		return expiringDate;
	}

	public void setExpiringDate(Date expiringDate) {
		this.expiringDate = expiringDate;
	}

	public Date getDredgeDate() {
		return dredgeDate;
	}

	public void setDredgeDate(Date dredgeDate) {
		this.dredgeDate = dredgeDate;
	}

	public String getVolumeTypeName() {
		return volumeTypeName;
	}

	public void setVolumeTypeName(String volumeTypeName) {
		this.volumeTypeName = volumeTypeName;
	}

	public String getAllocateDomain() {
		return allocateDomain;
	}

	public void setAllocateDomain(String allocateDomain) {
		this.allocateDomain = allocateDomain;
	}

	public Long getUserAmount() {
		return userAmount;
	}

	public void setUserAmount(Long userAmount) {
		this.userAmount = userAmount;
	}

	public Long getSingleMailboxCapacity() {
		return singleMailboxCapacity;
	}

	public void setSingleMailboxCapacity(Long singleMailboxCapacity) {
		this.singleMailboxCapacity = singleMailboxCapacity;
	}

	public Long getAllocateStorageCapacity() {
		return allocateStorageCapacity;
	}

	public void setAllocateStorageCapacity(Long allocateStorageCapacity) {
		this.allocateStorageCapacity = allocateStorageCapacity;
	}

	public String getAllocateSharepointAddress() {
		return allocateSharepointAddress;
	}

	public void setAllocateSharepointAddress(String allocateSharepointAddress) {
		this.allocateSharepointAddress = allocateSharepointAddress;
	}
	
	
}
