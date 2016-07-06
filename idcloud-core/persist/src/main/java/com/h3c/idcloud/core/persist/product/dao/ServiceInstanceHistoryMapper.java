package com.h3c.idcloud.core.persist.product.dao;

import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceHistory;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;

public interface ServiceInstanceHistoryMapper {
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
    int insert(ServiceInstanceHistory record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ServiceInstanceHistory record);

    /**
     * 根据条件查询记录集
     */
    List<ServiceInstanceHistory> selectByParamsWithBLOBs(Criteria example);

    /**
     * 根据条件查询记录集
     */
    List<ServiceInstanceHistory> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    ServiceInstanceHistory selectByPrimaryKey(Long instanceSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") ServiceInstanceHistory record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParamsWithBLOBs(@Param("record") ServiceInstanceHistory record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") ServiceInstanceHistory record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ServiceInstanceHistory record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKeyWithBLOBs(ServiceInstanceHistory record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ServiceInstanceHistory record);
}