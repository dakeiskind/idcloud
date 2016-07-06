package com.h3c.idcloud.core.service.system.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.AlarmRuleMapper;
import com.h3c.idcloud.core.pojo.dto.system.AlarmRule;
import com.h3c.idcloud.core.service.system.api.AlarmRuleService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gujie
 */
@Service(version = "1.0.0")
@Component
public class AlarmRuleServiceImpl implements AlarmRuleService {

    /** 注入Mapper */
    @Autowired
    private AlarmRuleMapper alarmRuleMapper;

    /** 日志log */
    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmRuleServiceImpl.class);

    @Override
    public int countByParams(Criteria example) {
        int count = this.alarmRuleMapper.countByParams(example);
        LOGGER.debug("count: {}", count);
        return count;
    }

    @Override
    public AlarmRule selectByPrimaryKey(Long alarmRuleSid) {
        return this.alarmRuleMapper.selectByPrimaryKey(alarmRuleSid);
    }

    @Override
    public List<AlarmRule> selectByParams(Criteria example) {
        return this.alarmRuleMapper.selectByParams(example);
    }

    @Override
    public int deleteByPrimaryKey(Long alarmRuleSid) {
        return this.alarmRuleMapper.deleteByPrimaryKey(alarmRuleSid);
    }

    @Override
    public int updateByPrimaryKeySelective(AlarmRule record) {
        return this.alarmRuleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AlarmRule record) {
        return this.alarmRuleMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteByParams(Criteria example) {
        return this.alarmRuleMapper.deleteByParams(example);
    }

    @Override
    public int updateByParamsSelective(AlarmRule record, Criteria example) {
        return this.alarmRuleMapper.updateByParamsSelective(record, example.getCondition());
    }

    @Override
    public int updateByParams(AlarmRule record, Criteria example) {
        return this.alarmRuleMapper.updateByParams(record, example.getCondition());
    }

    @Override
    public int insert(AlarmRule record) {
        return this.alarmRuleMapper.insert(record);
    }

    @Override
    public int insertSelective(AlarmRule record) {
        return this.alarmRuleMapper.insertSelective(record);
    }
}
