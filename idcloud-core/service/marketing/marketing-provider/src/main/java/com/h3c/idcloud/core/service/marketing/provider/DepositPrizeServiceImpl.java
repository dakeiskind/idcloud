package com.h3c.idcloud.core.service.marketing.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.marketing.dao.DepositPrizeMapper;
import com.h3c.idcloud.core.pojo.dto.marketing.DepositPrize;
import com.h3c.idcloud.core.service.marketing.api.DepositPrizeService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Service(version = "1.0.0")
@Component
public class DepositPrizeServiceImpl implements DepositPrizeService {
    @Autowired
    private DepositPrizeMapper depositPrizeMapper;

    private static final Logger logger = LoggerFactory.getLogger(DepositPrizeServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.depositPrizeMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public DepositPrize selectByPrimaryKey(Long depositPrizeSid) {
        return this.depositPrizeMapper.selectByPrimaryKey(depositPrizeSid);
    }

    public List<DepositPrize> selectByParams(Criteria example) {
        return this.depositPrizeMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long depositPrizeSid) {
        return this.depositPrizeMapper.deleteByPrimaryKey(depositPrizeSid);
    }

    public int updateByPrimaryKeySelective(DepositPrize record) {
        return this.depositPrizeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(DepositPrize record) {
        return this.depositPrizeMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.depositPrizeMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(DepositPrize record, Criteria example) {
        return this.depositPrizeMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(DepositPrize record, Criteria example) {
        return this.depositPrizeMapper.updateByParams(record, example.getCondition());
    }

    public int insert(DepositPrize record) {
        return this.depositPrizeMapper.insert(record);
    }

    public int insertSelective(DepositPrize record) {
        return this.depositPrizeMapper.insertSelective(record);
    }
    
}