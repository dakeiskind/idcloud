package com.h3c.idcloud.core.service.system.provider;


import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.AttachmentMapper;
import com.h3c.idcloud.core.pojo.dto.system.Attachments;
import com.h3c.idcloud.core.service.system.api.AttachmentService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    private AttachmentMapper attachmentMapper;

    private static final Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.attachmentMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Attachments selectByPrimaryKey(String attachmentSid) {
        return this.attachmentMapper.selectByPrimaryKey(attachmentSid);
    }

    public List<Attachments> selectByParams(Criteria example) {
        return this.attachmentMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(String attachmentSid) {
        return this.attachmentMapper.deleteByPrimaryKey(attachmentSid);
    }

    public int updateByPrimaryKeySelective(Attachments record) {
        return this.attachmentMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Attachments record) {
        return this.attachmentMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.attachmentMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Attachments record, Criteria example) {
        return this.attachmentMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Attachments record, Criteria example) {
        return this.attachmentMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Attachments record) {
        return this.attachmentMapper.insert(record);
    }

    public int insertSelective(Attachments record) {
        return this.attachmentMapper.insertSelective(record);
    }
}