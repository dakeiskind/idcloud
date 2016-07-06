package com.h3c.idcloud.core.persist.product.dao;

import com.h3c.idcloud.core.pojo.dto.product.ServiceChangeLog;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ServiceChangeLogMapper {
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
    int deleteByPrimaryKey(Long cmSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(ServiceChangeLog record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ServiceChangeLog record);

    /**
     * 根据条件查询记录集
     */
    List<ServiceChangeLog> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    ServiceChangeLog selectByPrimaryKey(Long cmSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") ServiceChangeLog record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") ServiceChangeLog record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ServiceChangeLog record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ServiceChangeLog record);

    /**
     * 用来查询没有结束时间的记录
     */
	List<ServiceChangeLog> selectByParams2(Criteria example);
}