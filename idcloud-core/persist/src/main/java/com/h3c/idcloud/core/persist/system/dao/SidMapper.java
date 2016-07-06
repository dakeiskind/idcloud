package com.h3c.idcloud.core.persist.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.system.Sid;
import org.springframework.stereotype.Repository;

@Repository
public interface SidMapper {
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
    int deleteByPrimaryKey(Long sid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Sid record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Sid record);

    /**
     * 根据条件查询记录集
     */
    List<Sid> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Sid selectByPrimaryKey(Long sid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Sid record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Sid record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Sid record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Sid record);
}