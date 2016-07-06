package com.h3c.idcloud.core.persist.system.dao;

import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.system.Sysconfig;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysconfigMapper {
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
    int deleteByPrimaryKey(Long configSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Sysconfig record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Sysconfig record);

    /**
     * 根据条件查询记录集
     */
    List<Sysconfig> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Sysconfig selectByPrimaryKey(Long configSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Sysconfig record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Sysconfig record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Sysconfig record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Sysconfig record);

	List<Sysconfig> selectConfigTypeByParams(Criteria criteria);
}