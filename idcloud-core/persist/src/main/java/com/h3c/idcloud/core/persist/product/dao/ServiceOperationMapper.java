package com.h3c.idcloud.core.persist.product.dao;

import com.h3c.idcloud.core.pojo.dto.product.ServiceOperation;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ServiceOperationMapper {
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
    int deleteByPrimaryKey(Long operationSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(ServiceOperation record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ServiceOperation record);
    
    /**
     * 根据条件查询记录集
     */
    List<ServiceOperation> selectByParams(Criteria example);
    
    /**
     * 根据条件查询记录集
     */
    List<ServiceOperation> selectByServiceSid(Long serviceSid);

    /**
     * 根据主键查询记录
     */
    ServiceOperation selectByPrimaryKey(Long operationSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") ServiceOperation record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") ServiceOperation record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ServiceOperation record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ServiceOperation record);
}