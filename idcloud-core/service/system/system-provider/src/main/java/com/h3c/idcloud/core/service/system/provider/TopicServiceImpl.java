package com.h3c.idcloud.core.service.system.provider;


import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.TopicMapper;
import com.h3c.idcloud.core.pojo.dto.system.Topic;
import com.h3c.idcloud.core.service.system.api.TopicService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicMapper topicMapper;

    private static final Logger logger = LoggerFactory.getLogger(TopicServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.topicMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Topic selectByPrimaryKey(Long topicSid) {
        return this.topicMapper.selectByPrimaryKey(topicSid);
    }

    public List<Topic> selectByParams(Criteria example) {
        return this.topicMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long topicSid) {
        return this.topicMapper.deleteByPrimaryKey(topicSid);
    }

    public int updateByPrimaryKeySelective(Topic record) {
        return this.topicMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Topic record) {
        return this.topicMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.topicMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Topic record, Criteria example) {
        return this.topicMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Topic record, Criteria example) {
        return this.topicMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Topic record) {
        return this.topicMapper.insert(record);
    }

    public int insertSelective(Topic record) {
        return this.topicMapper.insertSelective(record);
    }

	@Override
	public List<Topic> selectTopicByUser(Criteria example) {
		return this.topicMapper.selectTopicByUser(example);
	}
}