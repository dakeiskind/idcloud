package com.h3c.idcloud.core.persist.product.dao;

import com.h3c.idcloud.core.pojo.dto.product.ServiceResInstanceType;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ServiceResInstanceTypeMapper {
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
    int deleteByPrimaryKey(ServiceResInstanceType key);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(ServiceResInstanceType record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ServiceResInstanceType record);

    /**
     * 根据条件查询记录集
     */
    List<ServiceResInstanceType> selectByParams(Criteria example);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") ServiceResInstanceType record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") ServiceResInstanceType record, @Param("condition") Map<String, Object> condition);
}