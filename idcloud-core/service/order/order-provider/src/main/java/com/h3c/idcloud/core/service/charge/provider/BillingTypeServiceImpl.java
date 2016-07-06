package com.h3c.idcloud.core.service.charge.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.charge.dao.BillingTypeMapper;
import com.h3c.idcloud.core.pojo.dto.charge.BillingType;
import com.h3c.idcloud.core.service.charge.api.BillingTypeService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class BillingTypeServiceImpl implements BillingTypeService {
    @Autowired
    private BillingTypeMapper billingTypeMapper;

    private static final Logger logger = LoggerFactory.getLogger(BillingTypeServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.billingTypeMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public BillingType selectByPrimaryKey(String billingType) {
        return this.billingTypeMapper.selectByPrimaryKey(billingType);
    }

    public List<BillingType> selectByParams(Criteria example) {
        return this.billingTypeMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(String billingType) {
        return this.billingTypeMapper.deleteByPrimaryKey(billingType);
    }

    public int updateByPrimaryKeySelective(BillingType record) {
        return this.billingTypeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(BillingType record) {
        return this.billingTypeMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.billingTypeMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(BillingType record, Criteria example) {
        return this.billingTypeMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(BillingType record, Criteria example) {
        return this.billingTypeMapper.updateByParams(record, example.getCondition());
    }

    public int insert(BillingType record) {
        return this.billingTypeMapper.insert(record);
    }

    public int insertSelective(BillingType record) {
        return this.billingTypeMapper.insertSelective(record);
    }
}