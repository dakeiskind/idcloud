package com.h3c.idcloud.core.persist.product.dao;

import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceSpec;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ServiceInstanceSpecMapper {
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
    int deleteByPrimaryKey(Long specSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(ServiceInstanceSpec record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ServiceInstanceSpec record);
    
    /**
     * 根据条件查询记录集
     */
    List<ServiceInstanceSpec> selectByParams(Criteria example);
    
    /**
     * 根据条件查询记录集
     */
    List<ServiceInstanceSpec> selectByParamsNewVersion(Criteria example);
    
    /**
     * 根据条件查询记录集
     */
    List<ServiceInstanceSpec> selectByParamsLatest(Criteria example);
              
    /**
     * 根据条件查询记录集
     */
    List<ServiceInstanceSpec> selectByInstanceSid(Long instanceSid);

    /**
     * 根据主键查询记录
     */
    ServiceInstanceSpec selectByPrimaryKey(Long specSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") ServiceInstanceSpec record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") ServiceInstanceSpec record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ServiceInstanceSpec record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ServiceInstanceSpec record);
    
    /**
     * 根据instanceSid查询出实例规格信息
     * 
     * @param instanceSid 
     * @return
     */
    List<ServiceInstanceSpec> selectByInstanceSpecBySid(Long instanceSid);
    
    
    /**
     * 根据instanceSid查询出当前实例规格的最大版本号
     * 
     * @param instanceSid
     * @return
     */
    Long selectByInstanceSpecMaxVersion(Long instanceSid);
    
    /**
     * 根据版本查询出当前实例的规格
     * 
     * @param instanceSid
     * @param version
     * @return
     */
    List<ServiceInstanceSpec> selectByInstanceSpecByVersion(@Param("instanceSid") Long instanceSid, @Param("status") String status, @Param("version") Long version);

	Long selectValidInstanceSpecByVersion(@Param("instanceSid") Long instanceSid);

	Long selectValidSpecByInstanceAndVersion(Criteria criteria);

	Long selectVmDiskTotalSize(Long instanceSid);
	
	int updateByName(@Param("instanceSid") Long instanceSid, @Param("name") String name, @Param("value") String value);
}