package com.h3c.idcloud.core.service.system.api;


import com.h3c.idcloud.core.pojo.dto.system.UserTopic;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface UserTopicService {
    int countByParams(Criteria example);

    UserTopic selectByPrimaryKey(UserTopic key);

    List<UserTopic> selectByParams(Criteria example);

    int deleteByPrimaryKey(UserTopic key);

    int updateByPrimaryKeySelective(UserTopic record);

    int updateByPrimaryKey(UserTopic record);

    int deleteByParams(Criteria example);

    int insert(UserTopic record);

    int insertSelective(UserTopic record);
}