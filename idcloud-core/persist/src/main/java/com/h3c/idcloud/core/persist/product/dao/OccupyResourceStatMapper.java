package com.h3c.idcloud.core.persist.product.dao;

import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.product.OccupyResourceStat;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;

public interface OccupyResourceStatMapper {
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
    int deleteByPrimaryKey(Long occupyResourceSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(OccupyResourceStat record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(OccupyResourceStat record);

    /**
     * 根据条件查询记录集
     */
    List<OccupyResourceStat> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    OccupyResourceStat selectByPrimaryKey(Long occupyResourceSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") OccupyResourceStat record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") OccupyResourceStat record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(OccupyResourceStat record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(OccupyResourceStat record);

	List<OccupyResourceStat> selectByParamsSum(Criteria example);
	
	List<OccupyResourceStat> selectByParamsSumByDate(Criteria example);

	List<OccupyResourceStat> selectUsageByParamsSum(Criteria example);
	
	List<OccupyResourceStat> selectUsageByParamsForRank(Criteria example);
	
}