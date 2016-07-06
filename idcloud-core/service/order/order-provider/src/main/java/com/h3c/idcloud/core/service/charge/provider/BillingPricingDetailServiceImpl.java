package com.h3c.idcloud.core.service.charge.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.charge.dao.BillingPricingDetailMapper;
import com.h3c.idcloud.core.pojo.dto.charge.BillingPricingDetail;
import com.h3c.idcloud.core.service.charge.api.BillingPricingDetailService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class BillingPricingDetailServiceImpl implements BillingPricingDetailService {
    @Autowired
    private BillingPricingDetailMapper billingPricingDetailMapper;

    private static final Logger logger = LoggerFactory.getLogger(BillingPricingDetailServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.billingPricingDetailMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public BillingPricingDetail selectByPrimaryKey(Long pricingDetailSid) {
        return this.billingPricingDetailMapper.selectByPrimaryKey(pricingDetailSid);
    }

    public List<BillingPricingDetail> selectByParams(Criteria example) {
        return this.billingPricingDetailMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long pricingDetailSid) {
        return this.billingPricingDetailMapper.deleteByPrimaryKey(pricingDetailSid);
    }

    public int updateByPrimaryKeySelective(BillingPricingDetail record) {
        return this.billingPricingDetailMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(BillingPricingDetail record) {
        return this.billingPricingDetailMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.billingPricingDetailMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(BillingPricingDetail record, Criteria example) {
        return this.billingPricingDetailMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(BillingPricingDetail record, Criteria example) {
        return this.billingPricingDetailMapper.updateByParams(record, example.getCondition());
    }

    public int insert(BillingPricingDetail record) {
        return this.billingPricingDetailMapper.insert(record);
    }

    public int insertSelective(BillingPricingDetail record) {
        return this.billingPricingDetailMapper.insertSelective(record);
    }
}