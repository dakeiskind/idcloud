package com.h3c.idcloud.core.persist.charge.dao;

import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.charge.BillingRecord;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRecordMapper {
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
    int insert(BillingRecord record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(BillingRecord record);

    /**
     * 根据条件查询记录集
     */
    List<BillingRecord> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    BillingRecord selectByPrimaryKey(Long billSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") BillingRecord record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") BillingRecord record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(BillingRecord record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(BillingRecord record);

}
