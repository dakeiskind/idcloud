package com.h3c.idcloud.core.persist.customer.dao;

import com.h3c.idcloud.core.pojo.dto.customer.TicketRecord;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/19.
 */
@Repository
public interface TicketRecordMapper {

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
    int deleteByPrimaryKey(Long ticketRecordSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(TicketRecord record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(TicketRecord record);

    /**
     * 根据条件查询记录集
     */
    List<TicketRecord> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    TicketRecord selectByPrimaryKey(Long ticketRecordSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") TicketRecord record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") TicketRecord record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(TicketRecord record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(TicketRecord record);
}
