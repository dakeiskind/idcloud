package com.h3c.idcloud.core.persist.system.dao;

import com.h3c.idcloud.core.pojo.dto.system.Attachments;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentMapper {
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
    int deleteByPrimaryKey(String attachmentSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Attachments record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Attachments record);

    /**
     * 根据条件查询记录集
     */
    List<Attachments> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Attachments selectByPrimaryKey(String attachmentSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Attachments record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Attachments record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Attachments record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Attachments record);
}