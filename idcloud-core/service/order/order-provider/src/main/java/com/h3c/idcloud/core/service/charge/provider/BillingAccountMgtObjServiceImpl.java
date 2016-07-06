package com.h3c.idcloud.core.service.charge.provider;


import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.charge.dao.BillingAccountMgtObjMapper;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccountMgtObj;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccountMgtObjKey;
import com.h3c.idcloud.core.service.charge.api.BillingAccountMgtObjService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class BillingAccountMgtObjServiceImpl implements BillingAccountMgtObjService {
    @Autowired
    private BillingAccountMgtObjMapper billingAccountMgtObjMapper;

    private static final Logger logger = LoggerFactory.getLogger(BillingAccountMgtObjServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.billingAccountMgtObjMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public BillingAccountMgtObj selectByPrimaryKey(BillingAccountMgtObjKey key) {
        return this.billingAccountMgtObjMapper.selectByPrimaryKey(key);
    }

    public List<BillingAccountMgtObj> selectByParams(Criteria example) {
        return this.billingAccountMgtObjMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(BillingAccountMgtObjKey key) {
        return this.billingAccountMgtObjMapper.deleteByPrimaryKey(key);
    }

    public int updateByPrimaryKeySelective(BillingAccountMgtObj record) {
        return this.billingAccountMgtObjMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(BillingAccountMgtObj record) {
        return this.billingAccountMgtObjMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.billingAccountMgtObjMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(BillingAccountMgtObj record, Criteria example) {
        return this.billingAccountMgtObjMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(BillingAccountMgtObj record, Criteria example) {
        return this.billingAccountMgtObjMapper.updateByParams(record, example.getCondition());
    }

    public int insert(BillingAccountMgtObj record) {
        return this.billingAccountMgtObjMapper.insert(record);
    }

    public int insertSelective(BillingAccountMgtObj record) {
        return this.billingAccountMgtObjMapper.insertSelective(record);
    }
}