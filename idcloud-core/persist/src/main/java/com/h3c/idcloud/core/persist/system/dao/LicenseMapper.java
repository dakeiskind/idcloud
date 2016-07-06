package com.h3c.idcloud.core.persist.system.dao;


import com.h3c.idcloud.core.pojo.dto.system.License;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LicenseMapper {
	
	/**
	 * 查询license序列号
	 */
	
	License selectByParam();
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
    int deleteByPrimaryKey(String licenseSerialno);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(License record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(License record);

    /**
     * 根据条件查询记录集
     */
    List<License> selectByParams(Criteria example);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") License record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") License record, @Param("condition") Map<String, Object> condition);

	/**
	 * @param licenseSerialno
	 * @return
	 */
	License selectByPrimaryKey(String licenseSerialno);

	/**
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(License record);

	/**
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(License record);
}