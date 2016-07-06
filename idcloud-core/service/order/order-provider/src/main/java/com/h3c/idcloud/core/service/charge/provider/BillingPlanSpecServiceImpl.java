package com.h3c.idcloud.core.service.charge.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.charge.dao.BillingPlanSpecMapper;
import com.h3c.idcloud.core.pojo.dto.charge.BillingPlanSpec;
import com.h3c.idcloud.core.service.charge.api.BillingPlanSpecService;
import java.util.List;

import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service(version="1.0.0")
@Component
public class BillingPlanSpecServiceImpl implements BillingPlanSpecService {
    @Autowired
    private BillingPlanSpecMapper billingPlanSpecMapper;

    private static final Logger logger = LoggerFactory.getLogger(BillingPlanSpecServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.billingPlanSpecMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public BillingPlanSpec selectByPrimaryKey(Long billingPlanSpecSid) {
        return this.billingPlanSpecMapper.selectByPrimaryKey(billingPlanSpecSid);
    }

    public List<BillingPlanSpec> selectByParams(Criteria example) {
        return this.billingPlanSpecMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long billingPlanSpecSid) {
        return this.billingPlanSpecMapper.deleteByPrimaryKey(billingPlanSpecSid);
    }

    public int updateByPrimaryKeySelective(BillingPlanSpec record) {
        return this.billingPlanSpecMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(BillingPlanSpec record) {
        return this.billingPlanSpecMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.billingPlanSpecMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(BillingPlanSpec record, Criteria example) {
        return this.billingPlanSpecMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(BillingPlanSpec record, Criteria example) {
        return this.billingPlanSpecMapper.updateByParams(record, example.getCondition());
    }

    public int insert(BillingPlanSpec record) {
        return this.billingPlanSpecMapper.insert(record);
    }

    public int insertSelective(BillingPlanSpec record) {
        return this.billingPlanSpecMapper.insertSelective(record);
    }
}