package com.h3c.idcloud.core.persist.marketing.dao;


import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.marketing.GiftCardBatch;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCardBatchMapper {
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
    int deleteByPrimaryKey(Long batchSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(GiftCardBatch record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(GiftCardBatch record);

    /**
     * 根据条件查询记录集
     */
    List<GiftCardBatch> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    GiftCardBatch selectByPrimaryKey(Long batchSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") GiftCardBatch record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") GiftCardBatch record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(GiftCardBatch record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(GiftCardBatch record);

    /**
     * 根据条件查询记录总数
     */
    int findByParamsCnt(Criteria example);

    /**
     * 根据条件查询记录集
     */
    List<GiftCardBatch> findByParams(Criteria example);
    
    List<GiftCardBatch>  getAvailableBatchs();


}