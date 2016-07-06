package com.h3c.idcloud.core.persist.marketing.dao;


import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.marketing.GiftCardBatch;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.marketing.GiftCard;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCardMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByParams(Criteria example);
    
    /**
     * 查询某批次下未激活的数量
     * @param batchSid
     * @return
     */
    int countForBatchInactive(Long batchSid);

    /**
     * 根据条件删除记录
     */
    int deleteByParams(Criteria example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long cardSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(GiftCard record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(GiftCard record);

    /**
     * 根据条件查询记录集
     */
    List<GiftCard> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    GiftCard selectByPrimaryKey(Long cardSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") GiftCard record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") GiftCard record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(GiftCard record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(GiftCard record);

     /**
     * 根据sid李彪查询记录集
     */
    List<GiftCard> findListBySids(Criteria example);

    int insertAll(List<GiftCard> records);
    int updateByCardSids(Criteria example);

    List<GiftCard>  getAvailableGiftCards();

}