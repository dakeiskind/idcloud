package com.h3c.idcloud.core.persist.order.dao;

import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.vo.order.UserOrderVo;
import org.apache.ibatis.annotations.Param;

import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.order.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {
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
    int deleteByPrimaryKey(Long orderSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Order record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Order record);

    /**
     * 根据条件查询记录集
     */
    List<Order> selectByParams(Criteria param);

    /**
     * 根据orderId查询订单信息
     * @param orderId
     * @return
     */
    Order selectOrderByOrderId(String orderId);

    /**
     * 根据主键查询记录
     */
    Order selectByPrimaryKey(Long orderSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Order record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Order record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Order record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Order record);
    
    List<Order> selectByOrderStatusInMonth(Criteria example);
    List<Order> selectByOrderStatusInAll(Criteria example);

    /**
     * 用户中心订单列表
     * @param example
     * @return
     */
    List<UserOrderVo> selectPersonalOrderList(Criteria example);
}