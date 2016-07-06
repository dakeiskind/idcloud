package com.h3c.idcloud.core.persist.system.dao;

import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.system.Biz;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BizMapper {
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
    int deleteByPrimaryKey(Long bizSid);

    /**
     * 保存记录,不管记录里面的属性是否为
     */
    int insert(Biz record);

    /**
     * 保存属不为空的记
     */
    int insertSelective(Biz record);

    /**
     * 根据条件查询记录
     */
    List<Biz> selectByParams(Criteria example);

    /**
     * 根据条件查询记录
     */
    List<Biz> selectByParams2(Criteria example);
    
    /**
     * 根据主键查询记录
     */
    Biz selectByPrimaryKey(Long bizSid);

    /**
     * 根据条件更新属不为空的记
     */
    int updateByParamsSelective(@Param("record") Biz record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Biz record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属不为空的记
     */
    int updateByPrimaryKeySelective(Biz record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Biz record);

    /**
     * 查询级业务下的业
     */
	List<Biz> selectSbizByParams(Criteria example);
	
	/**
	 * 查询级业
	 */
	List<Biz> selectFbizByParams(Criteria example);

	List<Biz> selectForOrgbiz(Long bizSid);

	List<Biz> selectForQuota(Long bizSid);
}