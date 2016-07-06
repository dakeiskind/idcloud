package com.h3c.idcloud.core.service.ticket.api;

import com.h3c.idcloud.core.pojo.dto.customer.IssueReply;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface IssueReplyService {
    int countByParams(Criteria example);

    IssueReply selectByPrimaryKey(Long issueReplySid);

    List<IssueReply> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long issueReplySid);

    int updateByPrimaryKeySelective(IssueReply record);

    int updateByPrimaryKey(IssueReply record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(IssueReply record, Criteria example);

    int updateByParams(IssueReply record, Criteria example);

    int insert(IssueReply record);

    int insertSelective(IssueReply record);
}