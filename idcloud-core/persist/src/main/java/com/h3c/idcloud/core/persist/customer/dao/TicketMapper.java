package com.h3c.idcloud.core.persist.customer.dao;

import com.h3c.idcloud.core.pojo.dto.customer.Ticket;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author gujie
 */
@Repository
public interface TicketMapper {
    /**
     * 根据条件查询记录总数
     *
     * @param example 参数
     * @return 查询记录条数
     */
    int countByParams(Criteria example);

    /**
     * 根据条件删除记录
     *
     * @param example 参数
     * @return 返回值
     */
    int deleteByParams(Criteria example);

    /**
     * 根据主键删除记录
     *
     * @param ticketSid 工单Sid
     * @return 返回值
     */
    int deleteByPrimaryKey(Long ticketSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record 一条工单数据
     * @return  返回值
     */
    int insert(Ticket record);

    /**
     * 保存属性不为空的记录
     *
     * @param record 一条工单数据
     * @return 返回值
     */
    int insertSelective(Ticket record);

    /**
     * 根据条件查询记录集
     *
     * @param example 查询条件
     * @return 工单数据List
     */
    List<Ticket> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     *
     * @param ticketSid 工单Sid
     * @return 一条工单数据
     */
    Ticket selectByPrimaryKey(Long ticketSid);

    /**
     * 根据条件更新属性不为空的记录
     *
     * @param record 一条工单数据
     * @param condition 其他参数
     * @return 返回值
     */
    int updateByParamsSelective(@Param("record") Ticket record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     *
     * @param record 一条工单数据
     * @param condition 其他参数
     * @return 返回值
     */
    int updateByParams(@Param("record") Ticket record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record 一条工单数据
     * @return 返回值
     */
    int updateByPrimaryKeySelective(Ticket record);

    /**
     * 根据主键更新记录
     *
     * @param record 一条工单数据
     * @return 返回值
     */
    int updateByPrimaryKey(Ticket record);
}