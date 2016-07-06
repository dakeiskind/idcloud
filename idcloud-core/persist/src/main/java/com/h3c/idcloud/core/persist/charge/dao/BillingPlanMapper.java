package com.h3c.idcloud.core.persist.charge.dao;

import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.charge.BillingPlan;
import com.h3c.idcloud.core.pojo.vo.charge.BillingPlanSpecVo;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingPlanMapper {
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
    int deleteByPrimaryKey(Long billingPlanSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(BillingPlan record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(BillingPlan record);

    /**
     * 根据条件查询记录集
     */
    List<BillingPlan> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    BillingPlan selectByPrimaryKey(Long billingPlanSid);
    
    /**
     * 查询出资费计划
     */
    BillingPlan selectBillingByServiceSidAndStatus(Long serviceSid);
    
    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") BillingPlan record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") BillingPlan record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(BillingPlan record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(BillingPlan record);

    /**
     * 查询与资费计划关联的服务规格配置数据
     * @param serviceSid
     * @param billingPlanSid
     * @return
     */
    List<BillingPlanSpecVo> selectBillingPlanSpecVos(@Param("serviceSid")Long serviceSid,@Param("billingPlanSid")Long billingPlanSid);

    /**
     * 查询与资费计划关联的服务规格配置数据
     * @param serviceSid
     * @return
     */
    List<BillingPlanSpecVo> selectAllBillingPlanSpecVos(@Param("serviceSid")Long serviceSid);
}