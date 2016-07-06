package com.h3c.idcloud.core.service.system.provider;


import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.UserTopicMapper;
import com.h3c.idcloud.core.pojo.dto.system.UserTopic;
import com.h3c.idcloud.core.service.system.api.UserTopicService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class UserTopicServiceImpl implements UserTopicService {
    @Autowired
    private UserTopicMapper usertopicMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserTopicServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.usertopicMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public UserTopic selectByPrimaryKey(UserTopic key) {
        return this.usertopicMapper.selectByPrimaryKey(key);
    }

    public List<UserTopic> selectByParams(Criteria example) {
        return this.usertopicMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(UserTopic key) {
        return this.usertopicMapper.deleteByPrimaryKey(key);
    }

    public int updateByPrimaryKeySelective(UserTopic record) {
        return this.usertopicMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(UserTopic record) {
        return this.usertopicMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.usertopicMapper.deleteByParams(example);
    }

    public int insert(UserTopic record) {
        return this.usertopicMapper.insert(record);
    }

    public int insertSelective(UserTopic record) {
        return this.usertopicMapper.insertSelective(record);
    }
}