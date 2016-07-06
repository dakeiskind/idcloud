package com.h3c.idcloud.core.service.marketing.api;


import com.h3c.idcloud.core.pojo.dto.marketing.Coupon;
import com.h3c.idcloud.core.pojo.dto.marketing.CouponDisDetail;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface CouponDisDetailService {
    int countByParams(Criteria example);

    CouponDisDetail selectByPrimaryKey(Long distributionDetailSid);

    List<CouponDisDetail> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long distributionDetailSid);

    int updateByPrimaryKeySelective(CouponDisDetail record);

    int updateByPrimaryKey(CouponDisDetail record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(CouponDisDetail record, Criteria example);

    int updateByParams(CouponDisDetail record, Criteria example);

    int insert(CouponDisDetail record);

    int insertSelective(CouponDisDetail record);
     public int   executeGenCouponDisDetails(Coupon coupon);
}