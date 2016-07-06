package com.h3c.idcloud.core.service.system.api;


import com.h3c.idcloud.core.pojo.dto.system.Topic;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface TopicService {
    int countByParams(Criteria example);

    Topic selectByPrimaryKey(Long topicSid);

    List<Topic> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long topicSid);

    int updateByPrimaryKeySelective(Topic record);

    int updateByPrimaryKey(Topic record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Topic record, Criteria example);

    int updateByParams(Topic record, Criteria example);

    int insert(Topic record);

    int insertSelective(Topic record);

	List<Topic> selectTopicByUser(Criteria criteria);
}