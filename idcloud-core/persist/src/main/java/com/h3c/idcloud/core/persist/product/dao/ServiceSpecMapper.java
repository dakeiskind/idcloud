package com.h3c.idcloud.core.persist.product.dao;

import com.h3c.idcloud.core.pojo.dto.product.ServiceSpec;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ServiceSpecMapper {
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
    int insert(ServiceSpec record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ServiceSpec record);
    
    /**
     * 根据条件查询记录集
     */
    List<ServiceSpec> selectByParams(Criteria example);
    
    /**
     * 根据条件查询记录集
     */
    List<ServiceSpec> selectTemplateSpec(Criteria example);
   
    /**
     * 根据服务sid记录集
     */
    List<ServiceSpec> selectByServiceSid(Long serviceSid);
    
    /**
     * 根据主键查询记录
     */
    ServiceSpec selectByPrimaryKey(Long specSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") ServiceSpec record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") ServiceSpec record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ServiceSpec record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ServiceSpec record);
}