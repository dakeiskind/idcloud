package com.h3c.idcloud.core.persist.product.dao;

import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceChangeLog;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ServiceInstanceChangeLogMapper {
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
    int deleteByPrimaryKey(Long changeLogSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(ServiceInstanceChangeLog record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ServiceInstanceChangeLog record);

    /**
     * 根据条件查询记录集
     */
    List<ServiceInstanceChangeLog> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    ServiceInstanceChangeLog selectByPrimaryKey(Long changeLogSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") ServiceInstanceChangeLog record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") ServiceInstanceChangeLog record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ServiceInstanceChangeLog record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ServiceInstanceChangeLog record);

    /**
     * 查询最新未完成的变更记录
     */
    List<ServiceInstanceChangeLog> selectLastChangeLog(Long instanceSid);
}