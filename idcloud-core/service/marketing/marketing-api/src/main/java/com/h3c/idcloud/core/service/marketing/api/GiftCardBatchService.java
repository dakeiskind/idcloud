package com.h3c.idcloud.core.service.marketing.api;



import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.marketing.GiftCardBatch;
import com.h3c.idcloud.core.pojo.dto.user.User;

import java.util.List;

public interface GiftCardBatchService {
    int countByParams(Criteria example);

    GiftCardBatch selectByPrimaryKey(Long batchSid);

    List<GiftCardBatch> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long batchSid);

    int updateByPrimaryKeySelective(GiftCardBatch record);

    int updateByPrimaryKey(GiftCardBatch record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(GiftCardBatch record, Criteria example);

    int updateByParams(GiftCardBatch record, Criteria example);

    int insert(GiftCardBatch record);

    int insertSelective(GiftCardBatch record);

    /**
     * 创建礼品卡批次
     * 生成优惠券代码
     * @param record
     * @return
     */
    public int createGiftCardBatch(GiftCardBatch record, User user);

    /**
     * 整批生成礼品卡
     * 生成优惠券代码
     * @param giftCardBatchSid
     * @return
     */
    public int executeGenGiftCardsForBatch(Long giftCardBatchSid, User user);

     /**
     * 整批激活礼品卡
     * 生成优惠券代码
     * @param giftCardBatchSid
     * @return
     */
    public int executeActivateGiftCardForBatch(Long giftCardBatchSid, User user);

     /**
     * 作废礼品卡批次
     * @param giftCardBatchSid
     * @return
     */
    public int executeInvalidGiftCardBatch(Long giftCardBatchSid, User user);

     /**
     * 更新礼品卡批次
     * @param record
     * @return
     */
    public int updateGiftCardBatch(GiftCardBatch record, User user);

    /**
     * 根据条件查询记录总数
     */
    int findByParamsCnt(Criteria example);

    /**
     * 根据条件查询记录集
     */
    List<GiftCardBatch> findByParams(Criteria example);
    
    List<GiftCardBatch>  findAvailableBatchs();
}