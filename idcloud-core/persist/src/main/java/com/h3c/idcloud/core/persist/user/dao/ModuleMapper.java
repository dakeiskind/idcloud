package com.h3c.idcloud.core.persist.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.user.Module;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleMapper {
	
	/**
	 * 根据条件查询记录�?
	 * 
	 * @param condition
	 * @return
	 */
	List<Module> selectUserModules(Criteria condition);
	
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
    int deleteByPrimaryKey(String moduleSid);

    /**
     * 保存记录,不管记录里面的属性是否为�?
     */
    int insert(Module record);

    /**
     * 保存属�?�不为空的记�?
     */
    int insertSelective(Module record);

    /**
     * 根据条件查询记录�?
     */
    List<Module> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Module selectByPrimaryKey(String moduleSid);

    /**
     * 根据条件更新属�?�不为空的记�?
     */
    int updateByParamsSelective(@Param("record") Module record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Module record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属�?�不为空的记�?
     */
    int updateByPrimaryKeySelective(Module record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Module record);
}