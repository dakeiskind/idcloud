package com.h3c.idcloud.core.service.ticket.api;

import com.h3c.idcloud.core.pojo.dto.customer.Issue;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * 客户工单管理Service接口
 * @author gujie
 */
public interface IssueService {

    /**
     *
     * @param example 参数
     * @return 返回值
     */
    int countByParams(Criteria example);

    /**
     *
     * @param issueSid 参数
     * @return 返回值
     */
    Issue selectByPrimaryKey(Long issueSid);

    /**
     *
     * @param example 参数
     * @return 返回值
     */
    List<Issue> selectByParams(Criteria example);

    /**
     *
     * @param issueSid 参数
     * @return 返回值
     */
    int deleteByPrimaryKey(Long issueSid);

    /**
     *
     * @param record 参数
     * @return 返回值
     */
    int updateByPrimaryKeySelective(Issue record);

    /**
     *
     * @param record 参数
     * @return 返回值
     */
    int updateByPrimaryKey(Issue record);

    /**
     *
     * @param example 参数
     * @return 返回值
     */
    int deleteByParams(Criteria example);

    /**
     *
     * @param record 参数
     * @param example 参数
     * @return 返回值
     */
    int updateByParamsSelective(Issue record, Criteria example);

    /**
     *
     * @param record 参数
     * @param example 参数
     * @return 返回值
     */
    int updateByParams(Issue record, Criteria example);

    /**
     *
     * @param record 参数
     * @return 返回值
     */
    int insert(Issue record);

    /**
     *
     * @param record 参数
     * @return 返回值
     */
    int insertSelective(Issue record);

}
