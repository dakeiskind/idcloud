package com.h3c.idcloud.core.service.marketing.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.marketing.dao.AccountLevelMapper;
import com.h3c.idcloud.core.pojo.dto.marketing.AccountLevel;
import com.h3c.idcloud.core.service.marketing.api.AccountLevelService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author gujie
 */
@Service(version = "1.0.0")
@Component
public class AccountLevelServiceImpl implements AccountLevelService {

    /**
     * spring注入accountLevelMapper
     */
    @Autowired
    private AccountLevelMapper accountLevelMapper;

    /**
     * 日志Log
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountLevelServiceImpl.class);

    @Override
    @Transactional
    public int countByParams(Criteria example) {
        int count = this.accountLevelMapper.countByParams(example);
        LOGGER.debug("count: {}", count);
        return count;
    }

    @Override
    @Transactional
    public AccountLevel selectByPrimaryKey(Long levelSid) {
        return this.accountLevelMapper.selectByPrimaryKey(levelSid);
    }

    @Override
    @Transactional
    public List<AccountLevel> selectByParams(Criteria example) {
        return this.accountLevelMapper.selectByParams(example);
    }

    @Override
    @Transactional
    public int deleteByPrimaryKey(Long levelSid) {
        return this.accountLevelMapper.deleteByPrimaryKey(levelSid);
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(AccountLevel level) {
//    	WebUtil.prepareUpdateParams(level);
        return this.accountLevelMapper.updateByPrimaryKeySelective(level);
    }

    @Override
    @Transactional
    public int updateByPrimaryKey(AccountLevel record) {
        return this.accountLevelMapper.updateByPrimaryKey(record);
    }

    @Override
    @Transactional
    public int deleteByParams(Criteria example) {
        return this.accountLevelMapper.deleteByParams(example);
    }

    @Override
    @Transactional
    public int updateByParamsSelective(AccountLevel record, Criteria example) {
        return this.accountLevelMapper.updateByParamsSelective(record, example.getCondition());
    }

    @Override
    @Transactional
    public int updateByParams(AccountLevel record, Criteria example) {
        return this.accountLevelMapper.updateByParams(record, example.getCondition());
    }

    @Override
    @Transactional
    public int insert(AccountLevel record) {
        return this.accountLevelMapper.insert(record);
    }

    @Override
    @Transactional
    public int insertSelective(AccountLevel record) {
        return this.accountLevelMapper.insertSelective(record);
    }

    @Override
    @Transactional
    public boolean insertLevel(AccountLevel level) {

        boolean result = false;
        try {
            // 插入用户等级
            // TODO
            int levelResult = this.accountLevelMapper.insertSelective(level);

            if (levelResult == 1) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    @Transactional
    public boolean deleteLevel(Long levelSid) {
        boolean result = false;
        try {
            AccountLevel level = this.accountLevelMapper.selectByPrimaryKey(levelSid);
            Criteria condition = new Criteria();
            condition.put("levelSid", level.getLevelSid());
            int levelResult = this.accountLevelMapper.deleteByPrimaryKey(levelSid);
            if (levelResult == 0) {
                result = false;
            } else {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}