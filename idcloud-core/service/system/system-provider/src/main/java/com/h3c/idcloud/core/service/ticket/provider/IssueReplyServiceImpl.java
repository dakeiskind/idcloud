package com.h3c.idcloud.core.service.ticket.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.customer.dao.IssueReplyMapper;
import com.h3c.idcloud.core.pojo.dto.customer.IssueReply;
import com.h3c.idcloud.core.service.ticket.api.IssueReplyService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0.0")
@Component
public class IssueReplyServiceImpl implements IssueReplyService {
    
    @Autowired
    private IssueReplyMapper issueReplyMapper;

    private static final Logger logger = LoggerFactory.getLogger(IssueReplyServiceImpl.class);

    @Override
    public int countByParams(Criteria example) {
        int count = this.issueReplyMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    @Override
    public IssueReply selectByPrimaryKey(Long issueReplySid) {
        return this.issueReplyMapper.selectByPrimaryKey(issueReplySid);
    }

    @Override
    public List<IssueReply> selectByParams(Criteria example) {
        return this.issueReplyMapper.selectByParams(example);
    }

    @Override
    public int deleteByPrimaryKey(Long issueReplySid) {
        return this.issueReplyMapper.deleteByPrimaryKey(issueReplySid);
    }

    @Override
    public int updateByPrimaryKeySelective(IssueReply record) {
        return this.issueReplyMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(IssueReply record) {
        return this.issueReplyMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteByParams(Criteria example) {
        return this.issueReplyMapper.deleteByParams(example);
    }

    @Override
    public int updateByParamsSelective(IssueReply record, Criteria example) {
        return this.issueReplyMapper.updateByParamsSelective(record, example.getCondition());
    }

    @Override
    public int updateByParams(IssueReply record, Criteria example) {
        return this.issueReplyMapper.updateByParams(record, example.getCondition());
    }

    @Override
    public int insert(IssueReply record) {
        return this.issueReplyMapper.insert(record);
    }

    @Override
    public int insertSelective(IssueReply record) {
        return this.issueReplyMapper.insertSelective(record);
    }
}