package com.h3c.idcloud.core.service.marketing.provider;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.marketing.dao.CouponMapper;
import com.h3c.idcloud.core.pojo.dto.marketing.Coupon;
import com.h3c.idcloud.core.service.marketing.api.CouponDisDetailService;
import com.h3c.idcloud.core.service.marketing.api.CouponService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;


@Service(version = "1.0.0")
@Component
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Reference(version = "1.0.0")
    private CouponDisDetailService couponDisDetailService;

//     @Autowired
//    private MailNotificationsService mailNotificationsService;
//
//    @Autowired
//    private MessageService messageService;

    private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.couponMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Coupon selectByPrimaryKey(Long couponSid) {
        return this.couponMapper.selectByPrimaryKey(couponSid);
    }

    public List<Coupon> selectByParams(Criteria example) {
        return this.couponMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long couponSid) {
        return this.couponMapper.deleteByPrimaryKey(couponSid);
    }

    public int updateByPrimaryKeySelective(Coupon record) {
        return this.couponMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Coupon record) {
        return this.couponMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.couponMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Coupon record, Criteria example) {
        return this.couponMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Coupon record, Criteria example) {
        return this.couponMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Coupon record) {
        return this.couponMapper.insert(record);
    }

    public int insertSelective(Coupon record) {
        return this.couponMapper.insertSelective(record);
    }

    Random random = new Random(System.currentTimeMillis());

    public String generateCouponCode(String codeTable, int couponLen) {
        if (couponLen <= 0 || codeTable == null || codeTable.length() == 0) {
            return null;
        }

        String ret = "";
        for (int i = 0; i < couponLen; i++) {
            ret += codeTable.charAt(random.nextInt(codeTable.length()));
        }

        return ret;
    }

    private void validate(Coupon record, boolean isAdd) {
//        //验证
//        //有效期开始不能为空 并且必须大于当天
//    	Date t_ = null;
//        if(record.getValidStartDt() == null)
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_DATE_IS_NULL);
//        else{
//        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
//        	DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        	try {
//				record.setValidStartDt(df1.parse(df.format(record.getValidStartDt())));
//				t_ = df1.parse(df.format(new Date()));
//			} catch (ParseException e) {
//				throw new ApplicationException(e.getMessage());
//			}
//        }
//
//        if(record.getValidStartDt().getTime()<t_.getTime() && isAdd)
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_START_DAY_LT_TODAY);
//
//        //有效期截至不能为空 并且必须大于开始时间
//        if(record.getValidToDt() == null)
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_DATE_IS_NULL);
//        else{
//        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
//        	DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        	try {
//				record.setValidToDt(df1.parse(df.format(record.getValidToDt())));
//			} catch (ParseException e) {
//				throw new ApplicationException(e.getMessage());
//			}
//        }
//
//        if(record.getValidToDt().getTime()<=record.getValidStartDt().getTime())
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_END_DAY_LT_START_DAY);
//
//        //折扣率必须大于0，小于1
//        if(record.getDiscountRate()== null || record.getDiscountRate()<=0 || record.getDiscountRate()>1)
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_COUPON_DISCOUNT_ERROR);

    }

    /**
     * 创建优惠券 生成优惠券代码
     */
    public int createCoupon(Coupon record) {
        String couponCode = null;
        validate(record, true);
        //检查coupon 是不是已经存在了,只到找到一个不存在的为止，但不超过100次
        boolean couponExisted = true;
        for (int i = 0; i < 100; i++) {
            //generate couponCode
            couponCode =
                    generateCouponCode("ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789",
                                       6);

//            Criteria criteria = new Criteria("couponCode", couponCode);
//            List list = this.couponMapper.selectByParams(criteria);
//            if(list.size() == 0)
//            {
//                couponExisted = false;
//                break;
//            }
        }

//        if(couponCode == null || couponExisted)
//            throw new ApplicationException(WebConstants.MsgCd.ERROR_COUPON_GENERATE_ERROR);

        record.setCouponCode(couponCode);
        record.setDistributeStatus((short) 0);

//        WebUtil.prepareInsertParams(record);

        return this.couponMapper.insertSelective(record);

    }


    public int updateCoupon(Coupon record) {
        //优惠券码不允许更新
        record.setCouponCode(null);
//        WebUtil.prepareUpdateParams(record);
        validate(record, false);
        return this.couponMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 删除优惠券，已经分发成功的不允许删除
     */
    public int deleteCoupon(Long couponSid) {
        Coupon coupon = couponMapper.selectByPrimaryKey(couponSid);
        if (coupon == null) {
            return 0;
        }

        //已分发的不允许删除
        if (coupon.getDistributeStatus() != null
            && coupon.getDistributeStatus().shortValue() == 1) {
            return -2;
        }
        return this.couponMapper.deleteByPrimaryKey(couponSid);
    }

    /**
     * 通过调用响应接口将优惠券分发出去
     */
    public int executeDistributeCoupon(Long couponSid, String channel) {
        Coupon couponNew = new Coupon();
        //获取优惠券信息
        Coupon coupon = this.couponMapper.selectByPrimaryKey(couponSid);
        if (coupon == null) {
            return -1;
        }

        String channels = channel;
        if (channels == null || "".equals(channels)) {
            channels = coupon.getDistributeChannel();
        }
        if (channels == null || "".equals(channels)) {
            return -1;
        }
//        try
//        {
//            //发分发邮件
//            boolean isSuccessSendMail = false;
//            if(channels.contains(WebConstants.CouponDistributeChannel.EMAIL))
//            {
//                isSuccessSendMail = mailNotificationsService.sendCouponEmail(coupon);
//            }
//
//             //发站内信
//            boolean isSuccessSendSiteMsg = false;
//            if(channels.contains(WebConstants.CouponDistributeChannel.SITEMAIL))
//            {
//                //只要没有异常就视为成功
//                int ret = messageService.insertCouponMessages(coupon);
//                isSuccessSendSiteMsg = ret>0;
//            }

//            if(isSuccessSendMail || isSuccessSendSiteMsg){
//                couponNew.setDistributeStatus((short)1);//有一种方式分发成功 就算成功
//            }
//            else
//                couponNew.setDistributeStatus((short)2);//发送失败
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//            couponNew.setDistributeStatus((short)2);//发送失败
//            return -1;
//        }

        //更新记录分发状态
        couponNew.setCouponSid(couponSid);
        couponNew.setDistributeDt(new Date());
//        WebUtil.prepareUpdateParams(couponNew);
        this.couponMapper.updateByPrimaryKeySelective(couponNew);
        return 1;
    }

    /**
     * 根据条件查询记录总数
     */
    public int findByParamsCnt(Criteria example) {
        return couponMapper.findByParamsCnt(example);
    }

    /**
     * 根据条件查询记录集
     */
    public List<Coupon> findByParams(Criteria example) {
        return couponMapper.findByParams(example);
    }

    @Override
    public int executeGenCoupons(Long couponSid) {
        Coupon  coupon = this.couponMapper.selectByPrimaryKey(couponSid);
        int cnt = this.couponDisDetailService.executeGenCouponDisDetails(coupon);

        //更新状态
        Coupon newCoupon = new Coupon();
        newCoupon.setCouponSid(coupon.getCouponSid());
        newCoupon.setDistributeStatus((short) 1);
        WebUtil.prepareUpdateParams(newCoupon);
        this.couponMapper.updateByPrimaryKeySelective(newCoupon);
        return cnt;
    }

    @Override
    public List<Coupon> findAvailableCoupons() {
        return this.couponMapper.getAvailableCoupon();
    }

}