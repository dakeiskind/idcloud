package com.h3c.idcloud.core.persist.charge.dao;


import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.charge.BillingPlanSpec;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;

public interface BillingPlanSpecMapper {
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
    int deleteByPrimaryKey(Long billingPlanSpecSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(BillingPlanSpec record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(BillingPlanSpec record);

    /**
     * 根据条件查询记录集
     */
    List<BillingPlanSpec> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    BillingPlanSpec selectByPrimaryKey(Long billingPlanSpecSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") BillingPlanSpec record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") BillingPlanSpec record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(BillingPlanSpec record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(BillingPlanSpec record);

    /**
     * 根据计费计划与配置项名称得到所有计费与未计费的规格配置项sid
     * @param example
     * @return
     */
    List<BillingPlanSpec> selectDiffBilledList(Criteria example);
}