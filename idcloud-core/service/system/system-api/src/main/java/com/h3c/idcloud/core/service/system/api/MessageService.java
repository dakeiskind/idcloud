package com.h3c.idcloud.core.service.system.api;

import com.h3c.idcloud.core.pojo.dto.marketing.Coupon;


import com.h3c.idcloud.core.pojo.dto.system.Message;
import com.h3c.idcloud.core.pojo.dto.user.SystemMessage;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import javassist.NotFoundException;

import java.util.List;

/**
 * 站内信息服务接口
 */
public interface MessageService {

    /**
     * 根据条件查询记录总数
     * @param example
     * @return 记录总数
     */
    int countByParams(Criteria example);

    /**
     * 根据主键查询记录总数
     * @param msgSid
     * @return
     * @throws NotFoundException
     */
    Message selectByPrimaryKey(Long msgSid) throws NotFoundException;

    /**
     * 根据条件查询记录集
     * @param example
     * @return
     */
    List<Message> selectByParams(Criteria example);

    /**
     * 根据主键删除记录
     * @param msgSid
     * @return
     */
    int deleteByPrimaryKey(Long msgSid);

    /**
     *  根据主键更新属性不为空的记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Message record);

    /**
     * 根据主键更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKey(Message record);

    /**
     * 根据条件删除记录
     * @param example
     * @return
     */
    int deleteByParams(Criteria example);

    /**
     * 根据条件更新属性不为空的记录
     * @param record
     * @param example
     * @return
     */
    int updateByParamsSelective(Message record, Criteria example);

    /**
     * 根据条件更新记录
     * @param record
     * @param example
     * @return
     */
    int updateByParams(Message record, Criteria example);

    /**
     * 保存记录,不管记录里面的属性是否为空
     * @param record
     * @return
     */
    int insert(Message record);

    /**
     * 保存属性不为空的记录
     * @param record
     * @return
     */
    int insertSelective(Message record);

    /**
     * 根据主键批量删除
     * @param msgSids
     * @return
     */
    int deleteBatchByPks(List<String> msgSids);

    /**
     * 根据主键批量更新
     * @param messages
     * @return
     */
    int updateBatchByPks(List<Message> messages);

    /**
     * 批量插入消息
     * @param messages
     * @return
     */
    int insertBatch(List<Message> messages);

    /**
     * 插入系统消息
     * @param systemMessage
     * @return
     */
    int insertSystemMessages(SystemMessage systemMessage);

    /**
     * 插入优惠券消息
     * @param coupon
     * @return 插入信息的条数
     */
    int insertCouponMessages(Coupon coupon);
}