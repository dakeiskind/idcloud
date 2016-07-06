package com.h3c.idcloud.core.persist.charge.dao;

import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.charge.BillingPricing;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingPricingMapper {
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
    int deleteByPrimaryKey(Long billingPricingSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(BillingPricing record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(BillingPricing record);

    /**
     * 根据条件查询记录集
     */
    List<BillingPricing> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    BillingPricing selectByPrimaryKey(Long billingPricingSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") BillingPricing record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") BillingPricing record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(BillingPricing record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(BillingPricing record);
}