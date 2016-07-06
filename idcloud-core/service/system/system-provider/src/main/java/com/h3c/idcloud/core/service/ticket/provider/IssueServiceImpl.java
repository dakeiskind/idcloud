package com.h3c.idcloud.core.service.ticket.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.customer.dao.IssueMapper;
import com.h3c.idcloud.core.pojo.dto.customer.Issue;
import com.h3c.idcloud.core.service.ticket.api.IssueService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 客户工单管理Service接口Impl
 *
 * @author gujie
 */
@Service(version = "1.0.0")
@Component
public class IssueServiceImpl implements IssueService {

    /**注入issueMapper*/
    @Autowired
    private IssueMapper issueMapper;

    /**日志log*/
    private static final Logger LOGGER = LoggerFactory.getLogger(IssueServiceImpl.class);

    @Override
    public int countByParams(Criteria example) {
        int count = this.issueMapper.countByParams(example);
        LOGGER.debug("count: {}", count);
        return count;
    }

    @Override
    public Issue selectByPrimaryKey(Long ticketSid) {
        return this.issueMapper.selectByPrimaryKey(ticketSid);
    }

    @Override
    public List<Issue> selectByParams(Criteria example) {
        return this.issueMapper.selectByParams(example);
    }

    @Override
    public int deleteByPrimaryKey(Long ticketSid) {
        return this.issueMapper.deleteByPrimaryKey(ticketSid);
    }

    @Override
    public int deleteByParams(Criteria example) {
        return this.issueMapper.deleteByParams(example);
    }

    @Override
    public int updateByPrimaryKeySelective(Issue record) {
        return this.issueMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Issue record) {
        return this.issueMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByParamsSelective(Issue record, Criteria example) {
        return this.issueMapper.updateByParamsSelective(record, example.getCondition());
    }

    @Override
    public int updateByParams(Issue record, Criteria example) {
        return this.issueMapper.updateByParams(record, example.getCondition());
    }

    @Override
    public int insert(Issue record) {
        return this.issueMapper.insert(record);
    }

    @Override
    public int insertSelective(Issue record) {
        return this.issueMapper.insertSelective(record);
    }
}
