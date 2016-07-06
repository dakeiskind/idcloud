package com.h3c.idcloud.core.service.marketing.provider;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.marketing.dao.GiftCardBatchMapper;
import com.h3c.idcloud.core.pojo.dto.marketing.GiftCardBatch;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.service.marketing.api.GiftCardBatchService;
import com.h3c.idcloud.core.service.marketing.api.GiftCardService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service(version = "1.0.0")
@Component
public class GiftCardBatchServiceImpl implements GiftCardBatchService {

    @Autowired
    private GiftCardBatchMapper giftCardBatchMapper;
    @Reference(version = "1.0.0")
    private GiftCardService giftCardService;

    private static final Logger logger = LoggerFactory.getLogger(GiftCardBatchServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.giftCardBatchMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public GiftCardBatch selectByPrimaryKey(Long batchSid) {
        return this.giftCardBatchMapper.selectByPrimaryKey(batchSid);
    }

    public List<GiftCardBatch> selectByParams(Criteria example) {
        return this.giftCardBatchMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long batchSid) {
        return this.giftCardBatchMapper.deleteByPrimaryKey(batchSid);
    }

    public int updateByPrimaryKeySelective(GiftCardBatch record) {
        return this.giftCardBatchMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(GiftCardBatch record) {
        return this.giftCardBatchMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.giftCardBatchMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(GiftCardBatch record, Criteria example) {
        return this.giftCardBatchMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(GiftCardBatch record, Criteria example) {
        return this.giftCardBatchMapper.updateByParams(record, example.getCondition());
    }

    public int insert(GiftCardBatch record) {
        return this.giftCardBatchMapper.insert(record);
    }

    public int insertSelective(GiftCardBatch record) {
        return this.giftCardBatchMapper.insertSelective(record);
    }

    private void validate(GiftCardBatch record, boolean isAdd) {
//    	if(record == null)
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_PARAMETER_WRONG);
//        //礼品卡名称不能为空
//        if(record.getCardName() == null || "".equals(record.getCardName().trim()))
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_GIFT_CARD_NAME_EMPTY);
//
//        //面额大于0
//        if(record.getFaceValue() == null || record.getFaceValue().doubleValue()<=0)
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_GIFT_CARD_FACE_VALUE_LE_ZERO);
//
//        //发行数量大于0
//        if(record.getQuantity() == null || record.getQuantity().intValue()<=0)
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_GIFT_CARD_QUANTITY_LE_ZERO);
//
//        //有效期
//         //有效期开始不能为空 并且必须大于当天
//        Date t_ = null;
//        if(record.getValidStart() == null)
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_DATE_IS_NULL);
//
//        else{
//        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
//        	DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        	try {
//				record.setValidStart(df1.parse(df.format(record.getValidStart())));
//				t_ = df1.parse(df.format(new Date()));
//			} catch (ParseException e) {
//				throw new ApplicationException(e.getMessage());
//			}
//        }
//
//
//
//        if(record.getValidStart().getTime()<t_.getTime() && isAdd)
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_START_DAY_LT_TODAY);
//
//        //有效期截至不能为空 并且必须大于开始时间
//        if(record.getValidTo() == null)
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_DATE_IS_NULL);
//        else{
//        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
//        	DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        	try {
//				record.setValidTo(df1.parse(df.format(record.getValidTo())));
//			} catch (ParseException e) {
//				throw new ApplicationException(e.getMessage());
//			}
//        }
//
//        if(record.getValidTo().getTime()<=record.getValidStart().getTime())
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_END_DAY_LT_START_DAY);
    }

    /**
     * 创建礼品卡批次
     */
    public int createGiftCardBatch(GiftCardBatch record, User user) {
        String batchNo = null;

        validate(record, true);

        //生成批次号yyyymmddhh24mmssms
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        batchNo = df.format(now);

        WebUtil.prepareInsertParams(record, user.getAccount());
        //创建
        /**
         * 状态
         0 - 未生成
         1 - 已生成
         2 - 已作废
         */
        record.setStatus(WebConstants.GiftCardBatchStatus.GIFT_CARD_NOT_GENERATED);//未生成
        record.setBatchNo(batchNo);
        return this.giftCardBatchMapper.insertSelective(record);
    }

    /**
     * 整批生成礼品卡 生成优惠券代码
     */
    public int executeGenGiftCardsForBatch(Long giftCardBatchSid, User user) {
        GiftCardBatch batch = this.giftCardBatchMapper.selectByPrimaryKey(giftCardBatchSid);
        int cnt =this.giftCardService.executeGenGiftCardsForBatch(batch, user);

        //更新批次状态
        GiftCardBatch newBatch = new GiftCardBatch();
        newBatch.setBatchSid(batch.getBatchSid());
        newBatch.setStatus(WebConstants.GiftCardBatchStatus.GIFT_CARD_GENERATED);
        WebUtil.prepareUpdateParams(newBatch,user.getAccount());

        this.giftCardBatchMapper.updateByPrimaryKeySelective(newBatch);

        return cnt;
    }

    /**
     * 整批激活礼品卡 生成优惠券代码
     */
    public int executeActivateGiftCardForBatch(Long giftCardBatchSid, User user) {
        return giftCardService.executeActivateGiftCardForBatch(giftCardBatchSid, user);
    }

    /**
     * 作废礼品卡批次
     */
    public int executeInvalidGiftCardBatch(Long giftCardBatchSid, User user) {
        //获取礼品卡批次
        GiftCardBatch giftCardBatch = this.giftCardBatchMapper.selectByPrimaryKey(giftCardBatchSid);
//        if(giftCardBatch == null)
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_PARAMETER_WRONG);
//
//        //只有状态为 0-未生成的 才能作废
//        if(giftCardBatch.getStatus() !=  null &&  giftCardBatch.getStatus() != WebConstants.GiftCardBatchStatus.GIFT_CARD_NOT_GENERATED.intValue())
//             throw new ApplicationException(WebConstants.MsgCd.ERROR_GIFT_CARD_BATCH_CANNOT_INVALID);
//
//         //更新为作废
//        /**
//         * 状态
//            0 - 未生成
//            1 - 已生成
//            2 - 已作废
//         */
//        giftCardBatch.setStatus(WebConstants.GiftCardBatchStatus.BATCH_INVALID);//已作废
//        WebUtil.prepareUpdateParams(giftCardBatch,user.getAccount());

        return this.updateByPrimaryKeySelective(giftCardBatch);
    }

    /**
     * 更新礼品卡批次
     */
    public int updateGiftCardBatch(GiftCardBatch record, User user) {
        //只有状态为 0-未生成的 才能修改
//        if(record.getStatus() !=  null &&  record.getStatus() != WebConstants.GiftCardBatchStatus.GIFT_CARD_NOT_GENERATED.intValue())
//             throw new ApplicationException(WebConstants.MsgCd.ERROR_GIFT_CARD_BATCH_CANNOT_UPDATE);
//
//        validate(record,false);
//
//         //创建
//        WebUtil.prepareUpdateParams(record,user.getAccount());
        record.setBatchNo(null);//不能更新批次号
        return this.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据条件查询记录总数
     */
    public int findByParamsCnt(Criteria example) {
        return giftCardBatchMapper.findByParamsCnt(example);
    }

    /**
     * 根据条件查询记录集
     */
    public List<GiftCardBatch> findByParams(Criteria example) {
        return giftCardBatchMapper.findByParams(example);
    }

    public List<GiftCardBatch> findAvailableBatchs() {
        return giftCardBatchMapper.getAvailableBatchs();
    }
}