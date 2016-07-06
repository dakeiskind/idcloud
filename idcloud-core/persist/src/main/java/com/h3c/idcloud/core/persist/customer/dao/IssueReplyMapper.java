package com.h3c.idcloud.core.persist.customer.dao;

import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.customer.IssueReply;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueReplyMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByParams(Criteria example);

    /**
     * 根据条件删除记录
     */
    int deleteByParams(Criteria example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long issueReplySid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(IssueReply record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(IssueReply record);

    /**
     * 根据条件查询记录集
     */
    List<IssueReply> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    IssueReply selectByPrimaryKey(Long issueReplySid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") IssueReply record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") IssueReply record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(IssueReply record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(IssueReply record);
}