package com.h3c.idcloud.core.persist.charge.dao;

import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.charge.BillingAccountMgtObj;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccountMgtObjKey;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingAccountMgtObjMapper {
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
    int deleteByPrimaryKey(BillingAccountMgtObjKey key);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(BillingAccountMgtObj record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(BillingAccountMgtObj record);

    /**
     * 根据条件查询记录集
     */
    List<BillingAccountMgtObj> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    BillingAccountMgtObj selectByPrimaryKey(BillingAccountMgtObjKey key);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") BillingAccountMgtObj record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") BillingAccountMgtObj record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(BillingAccountMgtObj record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(BillingAccountMgtObj record);
}