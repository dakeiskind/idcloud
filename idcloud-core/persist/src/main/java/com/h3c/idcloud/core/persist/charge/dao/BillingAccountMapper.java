package com.h3c.idcloud.core.persist.charge.dao;

import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.charge.BillingAccount;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingAccountMapper {
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
    int deleteByPrimaryKey(Long billSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(BillingAccount record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(BillingAccount record);

    /**
     * 根据条件查询记录集
     */
    List<BillingAccount> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    BillingAccount selectByPrimaryKey(Long billSid);
    
    /**
     * 根据主键查询记录For update
     */
    BillingAccount selectByPrimaryKeyForUpdate(Long billSid);
    

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") BillingAccount record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") BillingAccount record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(BillingAccount record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(BillingAccount record);

    /**
     * 根据userSid获取账户
     * @param userId
     * @return
     */
	BillingAccount selectByUserId(Long userId);
}
