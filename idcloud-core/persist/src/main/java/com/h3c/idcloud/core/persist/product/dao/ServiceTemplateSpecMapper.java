package com.h3c.idcloud.core.persist.product.dao;

import com.h3c.idcloud.core.pojo.dto.product.ServiceTemplateSpec;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ServiceTemplateSpecMapper {
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
     * 根据主键删除记录
     */
    int deleteByTemplateSid(Long tempSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(ServiceTemplateSpec record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ServiceTemplateSpec record);
    
    /**
     * 根据条件查询记录集
     */
    List<ServiceTemplateSpec> selectByParams(Criteria example);
    
    /**
     * 根据条件查询记录集
     */
    List<ServiceTemplateSpec> selectByTemplateSid(Long tempSid);

    /**
     * 根据主键查询记录
     */
    ServiceTemplateSpec selectByPrimaryKey(Long specSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") ServiceTemplateSpec record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") ServiceTemplateSpec record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ServiceTemplateSpec record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ServiceTemplateSpec record);
}