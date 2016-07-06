package com.h3c.idcloud.core.service.marketing.provider;

import com.h3c.idcloud.core.persist.marketing.dao.CouponDisDetailMapper;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.pojo.dto.marketing.Coupon;
import com.h3c.idcloud.core.pojo.dto.marketing.CouponDisDetail;
import com.h3c.idcloud.core.service.marketing.api.CouponDisDetailService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

@Service(version = "1.0.0")
@Component
public class CouponDisDetailServiceImpl implements CouponDisDetailService {

    @Autowired
    private CouponDisDetailMapper couponDisDetailMapper;

    private static final Logger logger = LoggerFactory.getLogger(CouponDisDetailServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.couponDisDetailMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public CouponDisDetail selectByPrimaryKey(Long distributionDetailSid) {
        return this.couponDisDetailMapper.selectByPrimaryKey(distributionDetailSid);
    }

    public List<CouponDisDetail> selectByParams(Criteria example) {
        return this.couponDisDetailMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long distributionDetailSid) {
        return this.couponDisDetailMapper.deleteByPrimaryKey(distributionDetailSid);
    }

    public int updateByPrimaryKeySelective(CouponDisDetail record) {
        return this.couponDisDetailMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(CouponDisDetail record) {
        return this.couponDisDetailMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.couponDisDetailMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(CouponDisDetail record, Criteria example) {
        return this.couponDisDetailMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(CouponDisDetail record, Criteria example) {
        return this.couponDisDetailMapper.updateByParams(record, example.getCondition());
    }

    public int insert(CouponDisDetail record) {
        return this.couponDisDetailMapper.insert(record);
    }

    public int insertSelective(CouponDisDetail record) {
        return this.couponDisDetailMapper.insertSelective(record);
    }

    @Override
    public int executeGenCouponDisDetails(Coupon coupon) {
        CouponDisDetail couponDisDetail = new CouponDisDetail();
        couponDisDetail.setCouponSid(coupon.getCouponSid());
        couponDisDetail.setUsedStatus(0);
        WebUtil.prepareInsertParams(couponDisDetail);
        int ret = this.couponDisDetailMapper.insertSelective(couponDisDetail);
        return ret;
    }
}
