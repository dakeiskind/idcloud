package com.h3c.idcloud.core.service.marketing.api;



import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.marketing.GiftCard;
import com.h3c.idcloud.core.pojo.dto.marketing.GiftCardBatch;
import com.h3c.idcloud.core.pojo.dto.user.User;

import java.util.List;

public interface GiftCardService {
    int countByParams(Criteria example);

    GiftCard selectByPrimaryKey(Long cardSid);

    List<GiftCard> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long cardSid);

    int updateByPrimaryKeySelective(GiftCard record);

    int updateByPrimaryKey(GiftCard record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(GiftCard record, Criteria example);

    int updateByParams(GiftCard record, Criteria example);

    int insert(GiftCard record);

    int insertSelective(GiftCard record);
    
    int updateByCardSids(Criteria example);

    /**
     * 作废礼品卡
     * @param giftCardSid
     * @return
     */
    public int executeInvalidGiftCard(Long giftCardSid, User user);

    /**
     * 激活多张礼品卡
     * @param giftCardSids
     * @return
     */
    public int executeActivateGiftCards(List<Long> giftCardSids, User user);

    /**
     * 激活整个批次的礼品卡
     * @param giftCardBatchSid
     * @param user
     * @return
     */
    public int executeActivateGiftCardForBatch(Long giftCardBatchSid, User user);

     /**
     * 根据条件查询记录总数
     */
    public int findByParamsCnt(Criteria example);

     /**
     * 根据条件查询记录集
     */
    public List<GiftCard> findByParams(Criteria example);

    /**
     * 根据sid李彪查询记录集
     */
    List<GiftCard> findListBySids(List<Long> sids);

    /**
     * 整批生成礼品卡
     * 生成优惠券代码
     * @param giftCardBatch
     * @return
     */
    public int executeGenGiftCardsForBatch(GiftCardBatch giftCardBatch, User user);

    /**
     * 判断礼品卡能否充值
     * @param cardNo
     * @param cardPassword
     * @return true if can charge
     */
    public boolean canChangeable(String cardNo, String cardPassword);

     /**
     * 将礼品卡更新为充值状态
     * @param cardNo
     * @param cardPassword
     * @param userAccount
     * @return
     */
    public boolean executeCharge(String cardNo, String cardPassword, String userAccount, User user);

    List<GiftCard>  findAvailableGiftCards();
}