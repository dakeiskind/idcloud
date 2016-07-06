package com.h3c.idcloud.core.persist.product.dao;

import com.h3c.idcloud.core.pojo.dto.product.ServiceTemplate;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ServiceTemplateMapper {
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
    int deleteByPrimaryKey(Long templateSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(ServiceTemplate record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ServiceTemplate record);
    
    /**
     * 根据条件查询记录集
     */
    List<ServiceTemplate> selectByParams(Criteria example);
    
    /**
     * 根据serviceSid查询记录集
     */
    List<ServiceTemplate> selectByServiceSid(Long serviceSid);


    /**
     * 根据主键查询记录
     */
    ServiceTemplate selectByPrimaryKey(Long templateSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") ServiceTemplate record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") ServiceTemplate record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ServiceTemplate record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ServiceTemplate record);
}