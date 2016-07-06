package com.h3c.idcloud.core.persist.charge.dao;


import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.charge.ServiceBill;
import com.h3c.idcloud.core.pojo.vo.charge.ServiceBillVo;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceBillMapper {
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
    int deleteByPrimaryKey(Long serviceBillSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(ServiceBill record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ServiceBill record);

    /**
     * 根据条件查询记录集
     */
    List<ServiceBill> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    ServiceBill selectByPrimaryKey(Long serviceBillSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") ServiceBill record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") ServiceBill record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ServiceBill record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ServiceBill record);

    /**
     * 查询出所有用户的账单信息
     * @param criteria
     * @return
     */
    List<ServiceBillVo> selectUserServiceBillVos(Criteria criteria);

    /**
     * 查询日月统计信息
     * @param criteria
     * @return
     */
    List<ServiceBill> selectStatisticsAmountInfo(Criteria criteria);

    /**
     * 查询余额月份的每日花费信息
     * @param criteria
     * @return
     */
    List<ServiceBill> selectStatisticsByMonthTime(Criteria criteria);
}