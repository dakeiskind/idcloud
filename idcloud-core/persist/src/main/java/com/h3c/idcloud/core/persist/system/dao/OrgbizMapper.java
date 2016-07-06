package com.h3c.idcloud.core.persist.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.system.Orgbiz;
import com.h3c.idcloud.core.pojo.dto.system.OrgbizKey;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgbizMapper {
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
    int deleteByPrimaryKey(OrgbizKey key);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(OrgbizKey record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(OrgbizKey record);

    /**
     * 根据条件查询记录集
     */
    List<Orgbiz> selectByParams(Criteria example);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") OrgbizKey record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") OrgbizKey record, @Param("condition") Map<String, Object> condition);

    List<Orgbiz> selectBizTreeByParams(Criteria example);

    List<Orgbiz> selectSecondBizesByParams(Criteria example);

	List<Orgbiz> findBizByOrg(Criteria example);

	List<Orgbiz> findParentBizByOrg(Criteria example);
}