package com.h3c.idcloud.core.persist.order.dao;

import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.vo.order.UserOrderDetailVo;
import org.apache.ibatis.annotations.Param;

import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.order.OrderDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailMapper {
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
    int deleteByPrimaryKey(Long detailSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(OrderDetail record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(OrderDetail record);

    /**
     * 根据条件查询记录集
     */
    List<OrderDetail> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    OrderDetail selectByPrimaryKey(Long detailSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") OrderDetail record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") OrderDetail record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(OrderDetail record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(OrderDetail record);


    /**
     * 用户中心订单明细
     */
    List<UserOrderDetailVo> selectOrderDetailsByOrderId(String orderId);
}