package com.h3c.idcloud.core.pojo.dto.order;

import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * 服务代码
	 */
	private String serviceCode;

	/**
	 * 关联服务实例sid
	 */
	private Long instanceSid;

    /**
     * 定单明细SID
     */
    private Long detailSid;

    /**
     * 定单ID
     */
    private String orderId;

    /**
     * 服务ID
     */
    private Long serviceSid;
    
    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 模块ID
     */
    private Long templateSid;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 计费类型
     */
    private String billingType;

	/**
	 * 明细金额
	 */
	private BigDecimal amount;
    
    /**
     * 计费类型名称
     */
    private String billingTypeName;
    /**
     * 计费类型Ym名称
     */
    private String billingTypeYmName;
    /**
     * 计费时长
     */
    private Long buyLength;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 预计时间
     */
    private Date expectedTime;

    /**
     * 到期时间
     */
    private Date expiringDate;

    /**
     * 状态
     */
    private String status;
    
    /**
     * 状态名称
     */
    private String statusName;

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
     * 配置规格描述
     */
    private String specificationDesc;
    
    /**
     * 购买时长
     */
    private String purchaseLongTime;
    
    /**
     * 服务实例集合
     */
    private List<ServiceInstance> serviceInstances;
    
    /**
     * 服务实例列表集合
     */
    private List<HashMap<String,Object>> serviceInstanceMaps;
    
    /**
     * 服务实例规格集合
     */
    private HashMap<String,Object> specificationMaps;
    
    /**
     * 所有者名称
     */
    private String ownerName;
       
    /**
     * 租户名称
     */
    private String tenantName;
    
    /**
     * 实例创建开始时间
     */
    private Date serviceDredgeDate;

    /**
     * 实例创建开始时间
     */
    private Date serviceExpiringDate;
    
    /**
	 * 商务合同号
	 */
	private String contractId;


	/**
	 * 项目立项号
	 */
	private String projectId;	

	/**
	 * 合同文件
	 */
	private String contractFile;	

	/**
	 * 防火墙端口
	 */
	private String fwPort;

	/**
	 * 虚拟机服务实例Sid
	 */
	private Long vmServiceInstanceSid;
	
	/**
	 * 所属项目
	 */
	private String mgtObjSid;
	
	/**
	 * 记录订单内容，用于修改
	 */
	private String specification;
	
	/**
	 * 对应的服务实例的name
	 */
	private String instanceName;
	
    public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	/**
     * @return 定单明细SID
     */
    public Long getDetailSid() {
        return detailSid;
    }

    /**
     * @param detailSid 
	 *            定单明细SID
     */
    public void setDetailSid(Long detailSid) {
        this.detailSid = detailSid;
    }

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	/**
     * @return 定单ID
     */
    public String getOrderId() {
        return orderId;
    }

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
     * @param orderId 
	 *            定单ID
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return 服务ID
     */
    public Long getServiceSid() {
        return serviceSid;
    }

    /**
     * @param serviceSid 
	 *            服务ID
     */
    public void setServiceSid(Long serviceSid) {
        this.serviceSid = serviceSid;
    }

    /**
     * @return 模块ID
     */
    public Long getTemplateSid() {
        return templateSid;
    }

    /**
     * @param templateSid 
	 *            模块ID
     */
    public void setTemplateSid(Long templateSid) {
        this.templateSid = templateSid;
    }

    /**
     * @return 购买数量
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity 
	 *            购买数量
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return 计费类型
     */
    public String getBillingType() {
        return billingType;
    }

    /**
     * @param billingType 
	 *            计费类型
     */
    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }
    
    public String getBillingTypeName() {
		return billingTypeName;
	}

	public void setBillingTypeName(String billingTypeName) {
		this.billingTypeName = billingTypeName;
	}

	/**
	 * @return the 计费时长
	 */
	public Long getBuyLength() {
		return buyLength;
	}

	/**
	 * @param buyLength the buyLength to set
	 */
	public void setBuyLength(Long buyLength) {
		this.buyLength = buyLength;
	}

	/**
     * @return 流程实例ID
     */
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * @param processInstanceId 
	 *            流程实例ID
     */
    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    /**
     * @return 预计时间
     */
    public Date getExpectedTime() {
        return expectedTime;
    }

    /**
     * @param expectedTime 
	 *            预计时间
     */
    public void setExpectedTime(Date expectedTime) {
        this.expectedTime = expectedTime;
    }

    /**
     * @return 到期时间
     */
    public Date getExpiringDate() {
        return expiringDate;
    }

    /**
     * @param expiringDate 
	 *            到期时间
     */
    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }

    /**
     * @return 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy 
	 *            创建人
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
     * @param createdDt 
	 *            创建时间
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
     * @param updatedBy 
	 *            更新人
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
     * @param updatedDt 
	 *            更新时间
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
     * @param version 
	 *            版本号
     */
    public void setVersion(Long version) {
        this.version = version;
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
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the serviceInstances
	 */
	public List<ServiceInstance> getServiceInstances() {
		return serviceInstances;
	}

	/**
	 * @param serviceInstances the serviceInstances to set
	 */
	public void setServiceInstances(List<ServiceInstance> serviceInstances) {
		this.serviceInstances = serviceInstances;
	}

	/**
	 * @return the serviceInstanceMaps
	 */
	public List<HashMap<String, Object>> getServiceInstanceMaps() {
		return serviceInstanceMaps;
	}

	/**
	 * @param serviceInstanceMaps the serviceInstanceMaps to set
	 */
	public void setServiceInstanceMaps(List<HashMap<String, Object>> serviceInstanceMaps) {
		this.serviceInstanceMaps = serviceInstanceMaps;
	}

	/**
	 * @return the specificationDesc
	 */
	public String getSpecificationDesc() {
		return specificationDesc;
	}

	/**
	 * @param specificationDesc the specificationDesc to set
	 */
	public void setSpecificationDesc(String specificationDesc) {
		this.specificationDesc = specificationDesc;
	}

	/**
	 * @return the specificationMaps
	 */
	public HashMap<String, Object> getSpecificationMaps() {
		return specificationMaps;
	}

	/**
	 * @param specificationMaps the specificationMaps to set
	 */
	public void setSpecificationMaps(HashMap<String, Object> specificationMaps) {
		this.specificationMaps = specificationMaps;
	}

	public String getPurchaseLongTime() {
		return purchaseLongTime;
	}

	public void setPurchaseLongTime(String purchaseLongTime) {
		this.purchaseLongTime = purchaseLongTime;
	}

	public String getBillingTypeYmName() {
		return billingTypeYmName;
	}

	public void setBillingTypeYmName(String billingTypeYmName) {
		this.billingTypeYmName = billingTypeYmName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public Date getServiceDredgeDate() {
		return serviceDredgeDate;
	}

	public void setServiceDredgeDate(Date serviceDredgeDate) {
		this.serviceDredgeDate = serviceDredgeDate;
	}

	public Date getServiceExpiringDate() {
		return serviceExpiringDate;
	}

	public void setServiceExpiringDate(Date serviceExpiringDate) {
		this.serviceExpiringDate = serviceExpiringDate;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getProjectId() {
		return projectId;
	}

	public Long getInstanceSid() {
		return instanceSid;
	}

	public void setInstanceSid(Long instanceSid) {
		this.instanceSid = instanceSid;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getContractFile() {
		return contractFile;
	}

	public void setContractFile(String contractFile) {
		this.contractFile = contractFile;
	}

	public String getFwPort() {
		return fwPort;
	}

	public void setFwPort(String fwPort) {
		this.fwPort = fwPort;
	}

	public Long getVmServiceInstanceSid() {
		return vmServiceInstanceSid;
	}

	public void setVmServiceInstanceSid(Long vmServiceInstanceSid) {
		this.vmServiceInstanceSid = vmServiceInstanceSid;
	}

	public String getMgtObjSid() {
		return mgtObjSid;
	}

	public void setMgtObjSid(String mgtObjSid) {
		this.mgtObjSid = mgtObjSid;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String orderDetailSpec) {
		this.specification = orderDetailSpec;
	}

}