package com.h3c.idcloud.core.service.marketing.api;


import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.marketing.Coupon;

import java.util.List;

public interface CouponService {
    int countByParams(Criteria example);

    Coupon selectByPrimaryKey(Long couponSid);

    List<Coupon> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long couponSid);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Coupon record, Criteria example);

    int updateByParams(Coupon record, Criteria example);

    int insert(Coupon record);

    int insertSelective(Coupon record);

     /**
     * 创建优惠券
     * 生成优惠券代码
     * @param record
     * @return
     */
    public int createCoupon(Coupon record);
    public int updateCoupon(Coupon record);
     /**
     * 删除优惠券，已经分发成功的不允许删除
     * @param couponSid
     * @return
     */
    public int deleteCoupon(Long couponSid);
    /**
     * 通过调用响应接口将优惠券分发出去
     * @param couponSid
     */
    public int executeDistributeCoupon(Long couponSid, String channel);

     /**
     * 根据条件查询记录总数
     */
    int findByParamsCnt(Criteria example);

    /**
     * 根据条件查询记录集
     */
    List<Coupon> findByParams(Criteria example);
    public int  executeGenCoupons(Long couponSid);

    List<Coupon> findAvailableCoupons();
}