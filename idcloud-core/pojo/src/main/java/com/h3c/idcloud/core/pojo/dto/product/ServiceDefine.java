package com.h3c.idcloud.core.pojo.dto.product;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/2/19.
 */
public class ServiceDefine implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long serviceSid;

    private String serviceName;
    private String serviceCode;

    private String description;

    private String serviceType;

    private String serviceTypeName;

    private String serviceStatus;

    private String serviceStatusName;

    /**
     * 资费计划SID
     */
    private Long pricingPlanSid;

    private String serInstanceSid;

    private String contractId;

    private String ownerId;

    private String ownerName;

    private Long parentCatalogSid;

    private String parentCatalogName;

    private String tanentId;

    private String sImagePath;

    private String sImagePath1;

    private String sImagePath2;

    private String bImagePath;

    /**
     * 详细描述
     */
    private String detailDescription;

    /**
     * 产品说明
     */
    private String productDescription;

    /**
     * 产品特点
     */
    private String productFeatures;

    /**
     * 应用场景及案例
     */
    private String productCase;

    /**
     * 是否可订购
     */
    private String canOrder;

    /**
     * 是否可重复定购(0:否 1:是)
     */
    private String repeatOrder;

    /**
     * 帮助说明路径
     */
    private String helpPath;

    /**
     * 软件包目录
     */
    private String softwarePackagePath;

    /**
     * 服务到期时间
     */
    private Date expiringDate;

    /**
     * 操作处理器
     */
    private String operationHandler;

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
     * 服务配置list
     */
    private List<ServiceConfig> serviceConfig;

    /**
     * 服务规格list
     */
    private List<ServiceSpec> serviceSpec;

    /**
     * 服务适用范围
     */
    private String serviceScope;

    /**
     * 复合服务关联多个原子服务
     */
    private String relationService;

    /**
     * 服务性能指标list
     */
    private List<ServicePerf> servicePerf;

    /**
     * 服务操作项list
     */
    private List<ServiceOperation> serviceOperation;

    private int sortRank;

    public int getSortRank() {
        return sortRank;
    }

    public void setSortRank(int sortRank) {
        this.sortRank = sortRank;
    }

    public Long getServiceSid() {
        return serviceSid;
    }

    public void setServiceSid(Long serviceSid) {
        this.serviceSid = serviceSid;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    /**
     * @return 资费计划SID
     */
    public Long getPricingPlanSid() {
        return pricingPlanSid;
    }

    /**
     * @param pricingPlanSid
     *            资费计划SID
     */
    public void setPricingPlanSid(Long pricingPlanSid) {
        this.pricingPlanSid = pricingPlanSid;
    }

    public String getSerInstanceSid() {
        return serInstanceSid;
    }

    public void setSerInstanceSid(String serInstanceSid) {
        this.serInstanceSid = serInstanceSid;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getParentCatalogSid() {
        return parentCatalogSid;
    }

    public void setParentCatalogSid(Long parentCatalogSid) {
        this.parentCatalogSid = parentCatalogSid;
    }

    public String getTanentId() {
        return tanentId;
    }

    public void setTanentId(String tanentId) {
        this.tanentId = tanentId;
    }

    public String getsImagePath1() {
        return sImagePath1;
    }

    public void setsImagePath1(String sImagePath) {
        this.sImagePath1 = sImagePath;
    }

    public String getbImagePath() {
        return bImagePath;
    }

    public void setbImagePath(String bImagePath) {
        this.bImagePath = bImagePath;
    }

    /**
     * @return 是否可重复定购(0:否 1:是)
     */
    public String getRepeatOrder() {
        return repeatOrder;
    }

    /**
     * @param repeatOrder
     *            是否可重复定购(0:否 1:是)
     */
    public void setRepeatOrder(String repeatOrder) {
        this.repeatOrder = repeatOrder;
    }

    /**
     * @return 帮助说明路径
     */
    public String getHelpPath() {
        return helpPath;
    }

    /**
     * @param helpPath
     *            帮助说明路径
     */
    public void setHelpPath(String helpPath) {
        this.helpPath = helpPath;
    }

    /**
     * @return 软件包目录
     */
    public String getSoftwarePackagePath() {
        return softwarePackagePath;
    }

    /**
     * @param softwarePackagePath
     *            软件包目录
     */
    public void setSoftwarePackagePath(String softwarePackagePath) {
        this.softwarePackagePath = softwarePackagePath;
    }

    /**
     * @return 服务到期时间
     */
    public Date getExpiringDate() {
        return expiringDate;
    }

    /**
     * @param expiringDate
     *            服务到期时间
     */
    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }

    /**
     * @return 操作处理器
     */
    public String getOperationHandler() {
        return operationHandler;
    }

    /**
     * @param operationHandler
     *            操作处理器
     */
    public void setOperationHandler(String operationHandler) {
        this.operationHandler = operationHandler;
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

    public String getParentCatalogName() {
        return parentCatalogName;
    }

    public void setParentCatalogName(String parentCatalogName) {
        this.parentCatalogName = parentCatalogName;
    }

    public String getServiceStatusName() {
        return serviceStatusName;
    }

    public void setServiceStatusName(String serviceStatusName) {
        this.serviceStatusName = serviceStatusName;
    }

    public List<ServiceConfig> getServiceConfig() {
        return serviceConfig;
    }

    public void setServiceConfig(List<ServiceConfig> serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    public List<ServiceSpec> getServiceSpec() {
        return serviceSpec;
    }

    public void setServiceSpec(List<ServiceSpec> serviceSpec) {
        this.serviceSpec = serviceSpec;
    }

    public List<ServicePerf> getServicePerf() {
        return servicePerf;
    }

    public void setServicePerf(List<ServicePerf> servicePerf) {
        this.servicePerf = servicePerf;
    }

    public List<ServiceOperation> getServiceOperation() {
        return serviceOperation;
    }

    public void setServiceOperation(List<ServiceOperation> serviceOperation) {
        this.serviceOperation = serviceOperation;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    /**
     * @return the productDescription
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * @param productDescription the productDescription to set
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    /**
     * @return the productFeatures
     */
    public String getProductFeatures() {
        return productFeatures;
    }

    /**
     * @param productFeatures the productFeatures to set
     */
    public void setProductFeatures(String productFeatures) {
        this.productFeatures = productFeatures;
    }

    public String getsImagePath2() {
        return sImagePath2;
    }

    public void setsImagePath2(String sImagePath2) {
        this.sImagePath2 = sImagePath2;
    }

    /**
     * @return the detailDescription
     */
    public String getDetailDescription() {
        return detailDescription;
    }

    /**
     * @param detailDescription the detailDescription to set
     */
    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    /**
     * @return the canOrder
     */
    public String getCanOrder() {
        return canOrder;
    }

    /**
     * @param canOrder the canOrder to set
     */
    public void setCanOrder(String canOrder) {
        this.canOrder = canOrder;
    }

    /**
     * @return the productCase
     */
    public String getProductCase() {
        return productCase;
    }

    /**
     * @param productCase the productCase to set
     */
    public void setProductCase(String productCase) {
        this.productCase = productCase;
    }

    public String getServiceScope() {
        return serviceScope;
    }

    public void setServiceScope(String serviceScope) {
        this.serviceScope = serviceScope;
    }

    public String getsImagePath() {
        return sImagePath;
    }

    public void setsImagePath(String sImagePath) {
        this.sImagePath = sImagePath;
    }

    public String getRelationService() {
        return relationService;
    }

    public void setRelationService(String relationService) {
        this.relationService = relationService;
    }



}
