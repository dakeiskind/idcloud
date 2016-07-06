package com.h3c.idcloud.core.service.marketing.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.marketing.dao.GiftCardMapper;
import com.h3c.idcloud.core.pojo.dto.marketing.GiftCard;
import com.h3c.idcloud.core.pojo.dto.marketing.GiftCardBatch;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.service.marketing.api.GiftCardService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.omg.CORBA.portable.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Service(version = "1.0.0")
@Component
public class GiftCardServiceImpl implements GiftCardService {

    final private static int BATCH_SIZE = 100;

    @Autowired
    private GiftCardMapper giftCardMapper;

    private static final Logger logger = LoggerFactory.getLogger(GiftCardServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.giftCardMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public GiftCard selectByPrimaryKey(Long cardSid) {
        return this.giftCardMapper.selectByPrimaryKey(cardSid);
    }

    public List<GiftCard> selectByParams(Criteria example) {
        return this.giftCardMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long cardSid) {
        return this.giftCardMapper.deleteByPrimaryKey(cardSid);
    }

    public int updateByPrimaryKeySelective(GiftCard record) {
        return this.giftCardMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(GiftCard record) {
        return this.giftCardMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.giftCardMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(GiftCard record, Criteria example) {
        return this.giftCardMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(GiftCard record, Criteria example) {
        return this.giftCardMapper.updateByParams(record, example.getCondition());
    }

    public int insert(GiftCard record) {
        return this.giftCardMapper.insert(record);
    }

    public int insertSelective(GiftCard record) {
        return this.giftCardMapper.insertSelective(record);
    }

    public int updateByCardSids(Criteria example) {
        return this.giftCardMapper.updateByCardSids(example);
    }

    /**
     * 作废礼品卡
     */
    public int executeInvalidGiftCard(Long giftCardSid, User user) {
        //获取礼品卡批次
        GiftCard giftCard = this.giftCardMapper.selectByPrimaryKey(giftCardSid);
//        if(giftCard == null)
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_PARAMETER_WRONG);
//
//        //只有状态为 0-未激活 1 - 已激活 的 才能作废
//        if(giftCard.getStatus() !=  null
//                &&  giftCard.getStatus() != WebConstants.GiftCardStatus.NOT_ACTIVATED.intValue()
//                && giftCard.getStatus() != WebConstants.GiftCardStatus.ACTIVATED.intValue())
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_GIFT_CARD_CANNOT_INVALID);

        //更新为作废
        /**
         *  0  未激活   刚创建成功
         1  已激活   创建后需要激活后才能使用
         2  已充值
         3  已作废
         */
//        giftCard.setStatus(WebConstants.GiftCardStatus.INVALID);//已作废
//        WebUtil.prepareUpdateParams(giftCard, user.getAccount());

        return this.updateByPrimaryKeySelective(giftCard);
    }

    /**
     * 激活礼品卡
     *
     * @param giftCardSids List
     * @return return on of updated
     */
    public int executeActivateGiftCards(List<Long> giftCardSids, User user) {
        //更新条件
        Map criteria = new HashMap();
        criteria.put("cardSids", giftCardSids);
//        criteria.put("status",""+WebConstants.GiftCardStatus.NOT_ACTIVATED);//只有状态为0=未激活的才能激活
        criteria.put("notExpire", "true");//只有状态为0=未激活的才能激活

        //需要更新的数据
        GiftCard giftCard = new GiftCard();
        /**
         * 礼品卡状态
         *  0  未激活   刚创建成功
         1  已激活   创建后需要激活后才能使用
         2  已充值
         3  已作废
         */
//        giftCard.setStatus(WebConstants.GiftCardStatus.ACTIVATED); //设置为激活状态
        giftCard.setActivatedTime(new Date());
        giftCard.setActivatedUserSid(user.getUserSid());
//        WebUtil.prepareUpdateParams(giftCard,user.getAccount());

        return giftCardMapper.updateByParamsSelective(giftCard, criteria);
    }

    /**
     * 激活整个批次的礼品卡
     */
    public int executeActivateGiftCardForBatch(Long giftCardBatchSid, User user) {
        //更新条件
        Map criteria = new HashMap();
        criteria.put("batchSid", giftCardBatchSid);
//        criteria.put("status",""+WebConstants.GiftCardStatus.NOT_ACTIVATED);//只有状态为0=未激活的才能激活
        criteria.put("notExpire", "true");//只有状态为0=未激活的才能激活

        //需要更新的数据
        GiftCard giftCard = new GiftCard();
        /**
         * 礼品卡状态
         *  0  未激活   刚创建成功
         1  已激活   创建后需要激活后才能使用
         2  已充值
         3  已作废
         */
//        giftCard.setStatus(WebConstants.GiftCardStatus.ACTIVATED); //设置为激活状态
        giftCard.setActivatedTime(new Date());
        giftCard.setActivatedUserSid(user.getUserSid());
//        WebUtil.prepareUpdateParams(giftCard,user.getAccount());

        return giftCardMapper.updateByParamsSelective(giftCard, criteria);
    }

    /**
     * 根据条件查询记录总数
     */
    public int findByParamsCnt(Criteria example) {

        return giftCardMapper.countByParams(example);
    }

    /**
     * 根据条件查询记录集
     */
    public List<GiftCard> findByParams(Criteria example) {
        return giftCardMapper.selectByParams(example);
    }

    /**
     * 根据sid李彪查询记录集
     */
    public List<GiftCard> findListBySids(List<Long> sids) {
        Criteria criteria = new Criteria();
        criteria.put("sids", sids);
        return giftCardMapper.findListBySids(criteria);
    }


    /**
     * 整批生成礼品卡
     */
    public int executeGenGiftCardsForBatch(GiftCardBatch batch, User user) {
        int cardQuantity = batch.getQuantity();
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();

        //
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 50, 5000, TimeUnit.MILLISECONDS, workQueue);

        //小于1批
        if (cardQuantity < BATCH_SIZE) {
            GenGiftCardThread cmd = new GenGiftCardThread(batch, cardQuantity, user, giftCardMapper);
            poolExecutor.execute(cmd);
            return cardQuantity;
        }

        //整批数
        int batchNum = cardQuantity / BATCH_SIZE;
        for (int i = 0; i < batchNum; i++) {
            //礼品卡生成线程
            GenGiftCardThread cmd = new GenGiftCardThread(batch, BATCH_SIZE, user, giftCardMapper);
            poolExecutor.execute(cmd);
        }

        //零头
        int remainSize = cardQuantity - BATCH_SIZE * batchNum;
        if (remainSize > 0) {
            //礼品卡生成线程
            GenGiftCardThread cmd = new GenGiftCardThread(batch, remainSize, user, giftCardMapper);
            poolExecutor.execute(cmd);
        }
        poolExecutor.shutdown();
        return cardQuantity;
    }

    private class GenGiftCardThread extends Thread {

        GiftCardBatch batch;
        int size;
        User user;
        GiftCardMapper giftCardMapper;

        GenGiftCardThread(GiftCardBatch batch, int size, User user, GiftCardMapper giftCardMapper) {
            this.batch = batch;
            this.size = size;
            this.user = user;
            this.giftCardMapper = giftCardMapper;
        }

        public void run() {
            List<GiftCard> giftCardList = new ArrayList<GiftCard>();
            for (int i = 0; i < size; i++) {
                GiftCard card = new GiftCard();
                card.setCardName(batch.getCardName());
                card.setBatchSid(batch.getBatchSid());
                card.setBatchNo(batch.getBatchNo());
                card.setStatus(WebConstants.GiftCardStatus.ACTIVATED);
                card.setValidStart(batch.getValidStart());
                card.setValidTo(batch.getValidTo());
                card.setFaceValue(batch.getFaceValue());

                //生成卡密 6位数字
                String cardNo = generateCardNo();
                String password = generateCardPassword();
                card.setCardNo(cardNo);
                card.setCardPassword(password);
                WebUtil.prepareInsertParams(card, user.getAccount());
                giftCardList.add(card);
            }

            int ret = this.giftCardMapper.insertAll(giftCardList);
        }
    }

    public String generateCardPassword() {
        String codeTable = "0123456789";
        int len = 6;
        return generateCode(codeTable, len);
    }

    /**
     * 本方法有较小的概率出现重复 xxxx-xxxx-xxxx-xxxx 随机4位数字-随机4位数字-随机4位数字-时间最后4位
     */
    public String generateCardNo() {
        String ret = "";
        String codeTable = "0123456789";
        for (int i = 0; i < 3; i++) {
            ret += generateCode(codeTable, 4) + "-";
        }
        //截取时间的最后3位
        String time = "" + System.nanoTime();
        String timeStr = time.substring(time.length() - 4);
        ret += timeStr;

        return ret;
    }

    Random random = new Random(System.currentTimeMillis());

    public String generateCode(String codeTable, int len) {
        if (len <= 0 || codeTable == null || codeTable.length() == 0) {
            return null;
        }

        String ret = "";
        for (int i = 0; i < len; i++) {
            ret += codeTable.charAt(random.nextInt(codeTable.length()));
        }

        return ret;
    }

    /**
     * 判断礼品卡能否充值
     *
     * @return true if can charge
     */
    public boolean canChangeable(String cardNo, String cardPassword) {
        //根据卡号和密码获取礼品卡
        Criteria criteria = new Criteria();
        criteria.put("cardNo", cardNo);
        criteria.put("cardPassword", cardPassword);

        List<GiftCard> cards = this.giftCardMapper.selectByParams(criteria);

//        if(cards == null || cards.size() == 0)
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_GIFT_CARD_INVALID_PIN);
//
//        //卡号不唯一，系统错误
//        if(cards.size() != 1)
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_GIFT_CARD_DATA_DUP);
//
//         GiftCard  giftCard = cards.get(0);
//
//        //礼品卡未激活
//        if(giftCard.getStatus() == WebConstants.GiftCardStatus.NOT_ACTIVATED.intValue())
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_GIFT_CARD_NOT_ACTIVATE);
//
//        //礼品卡已被充值
//        if(giftCard.getStatus() == WebConstants.GiftCardStatus.CHARGED.intValue())
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_GIFT_CARD_ALREADY_CHARGED);
//
//        //礼品卡其他非激活状态
//        if(giftCard.getStatus() != WebConstants.GiftCardStatus.ACTIVATED.intValue())
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_GIFT_CARD_INVALID);

        return true;
    }

    /**
     * 将礼品卡更新为充值状态
     */
    public boolean executeCharge(String cardNo, String cardPassword, String userAccount,
                                 User user) {
        if (canChangeable(cardNo, cardPassword)) {
            //更新礼品卡充值状态
            GiftCard record = new GiftCard();
//                     record.setStatus(WebConstants.GiftCardStatus.CHARGED);
            record.setChargeAccountSid(userAccount);
            record.setChargeOperUserSid(user.getUserSid());
            record.setChargeTime(new Date());
//            WebUtil.prepareUpdateParams(record,user.getAccount());

            Map where = new HashMap();
            where.put("cardNo", cardNo);
            where.put("cardPassword", cardPassword);
//                     where.put("status",WebConstants.GiftCardStatus.ACTIVATED);

            this.giftCardMapper.updateByParamsSelective(record, where);

            return true;
        }
        return false;
    }

    @Override
    public List<GiftCard> findAvailableGiftCards() {
        return giftCardMapper.getAvailableGiftCards();
    }
}