package com.h3c.idcloud.core.service.product.api;

import com.h3c.idcloud.core.pojo.dto.order.OrderDetail;
import com.h3c.idcloud.core.pojo.dto.product.ServiceDefine;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceChangeLogSpec;
import com.h3c.idcloud.core.pojo.dto.product.ServiceSpecChange;
import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.core.pojo.dto.res.ResStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResVd;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.instance.ResVdInst;
import com.h3c.idcloud.core.pojo.instance.ResVmInst;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.activiti.engine.impl.util.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ServiceInstanceService {
    int countByParams(Criteria example);

    ServiceInstance selectByPrimaryKey(Long instanceSid);

    ServiceInstance selectByPrimaryKeyInstance(Long instanceSid);

    List<ServiceInstance> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long instanceSid);

    int updateByPrimaryKeySelective(ServiceInstance record);

    int insertSelective(ServiceInstance record);

    boolean createServiceInstance(OrderDetail orderDetail, String ownerId) throws Exception;

    boolean updateServiceInstance(OrderDetail orderDetail) throws Exception;

    Long createHostAndStorageServiceInstance(OrderDetail orderDetail, HashMap<String, Object> hostDiskMap);

    /**
     * 服务退订
     *
     * @param serviceInstaceSid
     * @return
     */
    boolean releaseService(Long serviceInstaceSid, String serviceType);

    /**
     * 服务退订
     *
     * @param serviceInstaceSid
     * @return
     */
    boolean releaseService(Long serviceInstaceSid);

    /**
     * 操作服务实例
     *
     * @param params
     * @return
     */
      boolean serviceInstanceOperation(Map<String, Object> params, AuthUser authUser) throws IOException, JSONException;
    /**
     * 退订服务实例
     *
     * @param serviceInstanceSid
     * @return
     */
    void createServiceInstanceCancel(String serviceInstanceSid);

    //    /**
//     * 修改服务实例及其下的资源实例名称
//     *
//     * @param instanceSid
//     * @param newName
//     * @return
//     * @throws Exception
//     */
//    boolean modifyResInsAndSerInsName(long instanceSid, String newName ,String resDescription) throws ApplicationException, Exception;
    boolean modifyResInsAndSerInsName(long instanceSid, String newName ,String resDescription) throws Exception;


    /**
     * 根据条件查询服务总数与有效服务
     *
     * @param example
     * @return
     */
    List<ServiceInstance> countServiceByParams(Criteria example);

    /**
     * 查询块存储信息
     */
    List<ServiceInstance> selectVolumeStorageInfo(Criteria example);

    /**
     * 查询目标主机
     * @param example
     * @return
     */
    List<ServiceDefine> selectedTarfgetHost(Criteria example);

    /**
     * 查询sharepoint的详细信息
     */
    ServiceInstance selectedSPAddressByServiceSid(Criteria example);

    /**
     * 根据条件查询租户服务总数与用户服务数
     *
     * @param example
     * @return
     */
    List<ServiceInstance> countTenantServiceVmByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与用户服务数
     *
     * @param example
     * @return
     */
    List<ServiceInstance> countTenantServiceStByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与用户服务数
     *
     * @param example
     * @return
     */
    List<ServiceInstance> countTenantServiceVmCpuByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与用户服务数
     *
     * @param example
     * @return
     */
    List<ServiceInstance> countTenantServiceVmMemoryByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与用户服务数
     *
     * @param example
     * @return
     */
    List<ServiceInstance> countTenantServiceStDiskByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与用户服务数
     *
     * @param example
     * @return
     */
    List<ServiceInstance> countTenantServiceVmQuotaByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与用户服务数
     *
     * @param example
     * @return
     */
    List<ServiceInstance> countTenantServiceStQuotaByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与用户服务数
     *
     * @param example
     * @return
     */
    List<ServiceInstance> countTenantServiceVmCpuQuotaByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与用户服务数
     *
     * @param example
     * @return
     */
    List<ServiceInstance> countTenantServiceVmMemoryQuotaByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与用户服务数
     *
     * @param example
     * @return
     */
    List<ServiceInstance> countTenantServiceStDiskQuotaByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与用户服务数
     *
     * @param params
     * @return
     */
    Map<String, Object> getTenantUserQuotaListByParams(String params, User user);

    /**
     * 根据条件查询租户服务总数与用户服务数
     *
     * @param list
     * @return
     */
    List<ServiceInstance> removeDuplicateData(List<ServiceInstance> list);

    /**
     * 查询需要结算的服务实例
     */
    List<ServiceInstance> selectServiceInstanceForNeedBalance(Criteria example);

    /**
     * 查询需要审批的服务实例
     */
    List<ServiceInstance> selectServiceInstanceForApprove(Criteria example);

//    public Map<String, Object> checkAllQuota(String orderId) throws ServiceException;
    public Map<String, Object> checkAllQuota(String orderId);

//    public Map<String, Object> checkAllQuota(Long bizSid, List<Map<String, Object>> curOrderQuotaList) throws ServiceException;
    public Map<String, Object> checkAllQuota(Long bizSid, List<Map<String, Object>> curOrderQuotaList);

    /**
     * 检查订单下的所是服务实例是否开通完成
     * @param orderId
     * @return
     */
    Boolean checkOrderSuccess(String orderId, Long serviceSid);


    /**
     * 检查云主机（虚拟机）服务实例下的所有子服务实例是否开通完成
     *
     * @param instanceSid
     * @return
     */
    Boolean checkVmServiceInstanceSuccess(Long instanceSid);

    /**
     * 变更虚拟机
     * @param instanceList
     */
    void modifyVm(List<Map<String, Object>> instanceList);

    List<ServiceInstance> selectByParamsForBar(Criteria example);

    void insertApprove(Long instanceSid);

    void modifyAllInstanceStatus(ServiceInstance instance);

    /**
     * 检查订单下的所有虚拟机是否退订完成
     * @param orderId
     * @return
     */
    Boolean checkUnsubscribeVmSuccess(String orderId);

    List<ServiceInstance> selectByParamsOrderByPurpose(Criteria criteria);

    List<ServiceInstance> selectCountByParams(Criteria example);

    void modifyInstanceSpec(Map map);

    String selectVdNameByInstanceSid(Long instanceSid);

    /**
     * 查询出企业下订购的各项服务个数
     */
    List<ServiceInstance> selectServiceCountByParams(Criteria example);

    /**
     * 查询管理对象下的服务实例
     */
    List<ServiceInstance> selectServiceInstanceByMgtObjSid(Long mgtObjSid);

    /**
     * 查询企业申请的对象存储
     */
    ServiceInstance selectObjStorageByMgtobjSid(Criteria example);

    /**
     * 得到浮动Ip列表
     * @param example
     * @return
     */
    List<ServiceInstance> selectFloatingIpInfo(Criteria example);

    /**
     * 测试退订CDN
     */
    void cancalCDN();

    /**
     * 调整服务规格
     * @param instance
     */
    void adjustServiceSpec(ServiceInstance instance, ServiceSpecChange<?> serviceInstanceChange, Long originalChangeLogSid);

    /**
     * 组装单个服务实例开通参数
     * @param serviceInstance
     * @param map
     * @return
     */
    ServiceInstanceChangeLogSpec<ResVmInst> assembleServiceInstanceOpenParams(ServiceInstance serviceInstance, Map<String, Object> map);

    /**
     * 添加vd的服务实例和规格
     * @param vd
     * @param serviceInstance
     * @return
     */
    Long insertVdInstanceAndSpec(ResVdInst vd, ServiceInstance serviceInstance);


    /**
     * 更新云主机（虚拟机）所属磁盘服务实例的状态
     * @param instanceSid 云主机服务实例的sid
     * @param status 状态
     */
    void modifyDiskServiceInstancesOfVmStatus(Long instanceSid, String status);


    /**
     * 更新云主机(虚拟机) 所属磁盘服务实例和软件服务实例状态
     * @param instanceSid 云主机服务实例sid
     * @param status 状态
     */
    void modifyAllChildServiceInstancesOfVmStatus(Long instanceSid, String status);

    /**
     * 更新云主机(虚拟机) 所属订单的状态
     * @param orderId
     */
    void modifyOrderStatusOfVmServiceInstance(String orderId);

    /**
     * 更新云主机（虚拟机）服务实例的状态
     * @param vmServiceInstance 云主机服务实例
     */
    void modifyVmServiceInstanceStatus(ServiceInstance vmServiceInstance);

    boolean insertResHostMgtObjRel(ResHost resHost,List<ResStorage> sysSt,List<ResStorage> dataSt,String mgtObjSid, String account);

    /**
     * 查询关联的虚机的集合
     */
    List<ServiceInstance> selectInstVmByParams(Criteria example);

    /**
     * 查询关联的物理机的集合
     */
    List<ServiceInstance> selectInstHostByParams(Criteria example);

    List<ServiceInstance> countInstVmByParams(Criteria example);

    List<ServiceInstance> countInstHostByParams(Criteria example);

    Integer countMgtObjInstVdByParams(Criteria param);

    Integer getMaxVolumeIndex(List<ResVd> resVdList);

    int deleteByParams(Criteria example);

    int updateInstanceBelongMgtObj(ServiceInstance instance, String mgtObjSid);

    /**
     * 释放服务实例
     * @param serviceInstanceSid
     * @param serviceType WebConstants.ResourceType.RES_VM
     * @return
     */
    boolean releaseServiceInstance(Long serviceInstanceSid,String serviceType,AuthUser authUser);

    /**
     * 资源创建失败回调处理
     * @param resId  资源ID
     * @param instanceID  服务实例ID
     * @param resType 资源类型 WebConstants.ResourceType.*
     * @param errorMsg 错误消息
     */
    void resInvokeErrorCallback(String resId,String instanceID,String resType,String errorMsg);

    /**
     * 服务开通处理服务
     * @param resMap 调用资源层返回的data map
     * @param serviceInstance  资源服务实例
     * @param user  用户信息
     * @param isRetry 是否重试
     */
    void openServiceSuccessHandle(Map<String,String> resMap,ServiceInstance serviceInstance,User user,boolean isRetry);
}