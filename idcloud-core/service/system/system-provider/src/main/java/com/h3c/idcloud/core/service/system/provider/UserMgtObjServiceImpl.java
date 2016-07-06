package com.h3c.idcloud.core.service.system.provider;


import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.UserMgtObjMapper;
import com.h3c.idcloud.core.pojo.dto.system.UserMgtObjKey;
import com.h3c.idcloud.core.service.system.api.UserMgtObjService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class UserMgtObjServiceImpl implements UserMgtObjService {
    @Autowired
    private UserMgtObjMapper userMgtObjMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserMgtObjServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.userMgtObjMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }
//
//    public UserMgtObjKey selectByPrimaryKey(UserMgtObjKey key) {
//        return this.userMgtObjMapper.selectByPrimaryKey(key);
//    }

    public List<UserMgtObjKey> selectByParams(Criteria example) {
        return this.userMgtObjMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(UserMgtObjKey key) {
        return this.userMgtObjMapper.deleteByPrimaryKey(key);
    }
//
    public int updateByPrimaryKeySelective(UserMgtObjKey record) {
        return this.userMgtObjMapper.updateByPrimaryKeySelective(record);
    }
//
//    public int updateByPrimaryKey(UserMgtObjKey record) {
//        return this.userMgtObjMapper.updateByPrimaryKey(record);
//    }

    public int deleteByParams(Criteria example) {
        return this.userMgtObjMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(UserMgtObjKey record, Criteria example) {
        return this.userMgtObjMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(UserMgtObjKey record, Criteria example) {
        return this.userMgtObjMapper.updateByParams(record, example.getCondition());
    }

    public int insert(UserMgtObjKey record) {
        return this.userMgtObjMapper.insert(record);
    }

    public int insertSelective(UserMgtObjKey record) {
        return this.userMgtObjMapper.insertSelective(record);
    }
}