package com.h3c.idcloud.core.persist.system.dao;

import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.system.CodeProvince;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeProvinceMapper {
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
    int deleteByPrimaryKey(Long provinceSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(CodeProvince record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(CodeProvince record);

    /**
     * 根据条件查询记录集
     */
    List<CodeProvince> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    CodeProvince selectByPrimaryKey(Long provinceSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") CodeProvince record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") CodeProvince record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(CodeProvince record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(CodeProvince record);
}