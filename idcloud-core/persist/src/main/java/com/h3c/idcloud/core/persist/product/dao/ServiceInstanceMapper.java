package com.h3c.idcloud.core.persist.product.dao;

import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.product.ServiceDefine;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ServiceInstanceMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByParams(Criteria example);

    /**
     * 根据条件删除记录
     */
    int deleteByParams(Criteria example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long instanceSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(ServiceInstance record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ServiceInstance record);

    /**
     * 根据条件查询记录集
     */
    List<ServiceInstance> selectByParams(Criteria example);

    /**
     * 根据条件查询虚机记录集
     */
    List<ServiceInstance> selectInstVmByParams(Criteria example);

    /**
     * 根据条件查询虚机记录数
     */
    List<ServiceInstance> countInstVmByParams(Criteria example);

    /**
     * 根据条件查询物理机记录集
     */
    List<ServiceInstance> selectInstHostByParams(Criteria example);

    /**
     * 根据条件查询物理机记录数
     */
    List<ServiceInstance> countInstHostByParams(Criteria example);

    /**
     * 根据条件查询服务总数与有效服务
     */
    List<ServiceInstance> countServiceByParams(Criteria example);

    /**
     * 查询出企业下订购的各项服务个数
     */
    List<ServiceInstance> selectServiceCountByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    ServiceInstance selectByPrimaryKey(Long instanceSid);

    /**
     * 查询管理对象下的服务实例
     */
    List<ServiceInstance> selectServiceInstanceByMgtObjSid(Long mgtObjSid);

    /**
     * 根据主键查询记录
     */
    ServiceInstance selectByPrimaryKeyInstance(Long instanceSid);

    /**
     * 查询企业申请的对象存储
     */
    ServiceInstance selectObjStorageByMgtobjSid(Criteria example);


    /**
     * 查询块存储信息
     */
    List<ServiceInstance> selectVolumeStorageInfo(Criteria example);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") ServiceInstance record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") ServiceInstance record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ServiceInstance record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ServiceInstance record);

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
     * 根据条件查询租户服务总数与租户服务数
     */
    List<ServiceInstance> countTenantServiceVmByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与租户服务数
     */
    List<ServiceInstance> countTenantServiceStByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与租户服务数
     */
    List<ServiceInstance> countTenantServiceVmCpuByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与租户服务数
     */
    List<ServiceInstance> countTenantServiceVmMemoryByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与租户服务数
     */
    List<ServiceInstance> countTenantServiceStDiskByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与租户服务数
     */
    List<ServiceInstance> countTenantServiceVmQuotaByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与租户服务数
     */
    List<ServiceInstance> countTenantServiceStQuotaByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与租户服务数
     */
    List<ServiceInstance> countTenantServiceVmCpuQuotaByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与租户服务数
     */
    List<ServiceInstance> countTenantServiceVmMemoryQuotaByParams(Criteria example);

    /**
     * 根据条件查询租户服务总数与租户服务数
     */
    List<ServiceInstance> countTenantServiceStDiskQuotaByParams(Criteria example);

    /**
     * 查询需要结算的服务实例
     */
    List<ServiceInstance> selectServiceInstanceForNeedBalance(Criteria example);

    /**
     * 查询需要审批的服务实例
     */
    List<ServiceInstance> selectServiceInstanceForApprove(Criteria example);

    /**
     * 统计所有的已使用配额
     * @param example
     * @return
     */
    List<Map<String, Object>> countAllUsageQuotaByParams(Criteria example);

    /**
     * 获取当前订单所需配额
     * @param example
     * @return
     */
    List<Map<String, Object>> getOrderQuotaByParams(Criteria example);

    /**
     * 检查订单下的所有虚拟机是否开通完成
     * @param criteria
     * @return
     */
    Boolean checkOrderSuccess(Criteria criteria);

    /**
     * 检查云主机（虚拟机）服务实例下的子服务实例（存储服务实例，软件服务实例）是否都完成
     * @param  criteria instanceSid参数
     * @return true/false
     */
    Boolean checkVmServiceInstanceSuccess(Criteria criteria);

    /**
     * 得到和业务相关的资源使用情况
     * @param example
     * @return
     */
    List<ServiceInstance> selectByParamsForBar(Criteria example);

    /**
     * 检查订单下的所有虚拟机是否退订完成
     * @param orderId
     * @return
     */
    Boolean checkUnsubscribeVmSuccess(String orderId);

    /**
     * 按照磁盘用途、名称排序的查询
     * @param criteria
     * @return
     */
    List<ServiceInstance> selectByParamsOrderByPurpose(Criteria criteria);

    /**
     * 统计用户已订购的服务类型及个数
     * @param example
     * @return
     */
    List<ServiceInstance> selectCountByParams(Criteria example);

    /**
     * 根据磁盘实例的sid查询出磁盘的vd名称
     * @param instanceSid
     */
    String selectVdNameByInstanceSid(Long instanceSid);

    /**
     * 得到浮动IP列表
     * @param example
     * @return
     */
    List<ServiceInstance> selectFloatingIpInfo(Criteria example);

    Integer countMgtObjInstVdByParams(Criteria example);

    Long selectVmHostNameSeq();

    /**
     * 根据服务实例ID查询服务实例信息
     * @param instanceId
     * @return
     */
    ServiceInstance selectByInstanceId(String instanceId);

}