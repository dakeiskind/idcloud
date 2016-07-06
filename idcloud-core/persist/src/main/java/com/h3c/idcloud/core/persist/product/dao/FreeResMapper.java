package com.h3c.idcloud.core.persist.product.dao;
import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.product.FreeRes;
import com.h3c.idcloud.core.pojo.dto.product.FreeResDisk;
import com.h3c.idcloud.core.pojo.dto.product.FreeResTotal;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;


public interface FreeResMapper {
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
    int deleteByPrimaryKey(Long fresSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(FreeRes record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(FreeRes record);

    /**
     * 根据条件查询记录集
     */
    List<FreeRes> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    FreeRes selectByPrimaryKey(Long fresSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") FreeRes record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") FreeRes record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(FreeRes record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(FreeRes record);
    
    /**
     * 获取闲置资源信息
     */
    List<FreeRes> selectByFreeInstance(Criteria example);
    
    /**
     * 根据条件查询记录集,用于报表
     */
    List<FreeRes> selectByParamsForFresReport(Criteria example);
    
    /**
     * 根据条件查询记录集,用于报表
     */
    List<FreeResTotal> selectByParamsForRecoveryReport(Criteria example);

	List<FreeRes> selectByParamsForRecovery(Criteria criteria);
	
	/**
	 * 根据条件，取得空闲资源规格里的磁盘信息
	 * @param criteria
	 * @return
	 */
	List<FreeResDisk> selectByParamsForFreeDisk(Criteria criteria);
	
	/**
	 * 通过服务实例id，取得闲置资源列表
	 * @param criteria
	 * @return
	 */
	List<FreeRes> selectByInstSid(Criteria criteria);

	/**
	 * 根据业务条件查询统计
	 * @param example
	 * @return
	 */
	List<FreeRes> selectByParamsForSum(Criteria example);
	
}