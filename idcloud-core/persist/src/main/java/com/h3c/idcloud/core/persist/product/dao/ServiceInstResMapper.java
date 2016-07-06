package com.h3c.idcloud.core.persist.product.dao;

import com.h3c.idcloud.core.pojo.dto.product.ServiceInstRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstResKey;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ServiceInstResMapper {
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
    int deleteByPrimaryKey(ServiceInstResKey key);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(ServiceInstRes record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ServiceInstRes record);

    /**
     * 根据条件查询记录集
     */
    List<ServiceInstRes> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    ServiceInstRes selectByPrimaryKey(ServiceInstResKey key);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") ServiceInstRes record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") ServiceInstRes record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ServiceInstRes record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ServiceInstRes record);

    /**
     * 查询服务实例的所有关联信息
     */
    List<ServiceInstRes> selectInstanceReses(Long instanceSid);
}