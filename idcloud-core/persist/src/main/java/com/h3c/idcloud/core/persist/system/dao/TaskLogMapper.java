package com.h3c.idcloud.core.persist.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskLogMapper {
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
    int deleteByPrimaryKey(Long taskLogSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(TaskLog record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(TaskLog record);

    /**
     * 根据条件查询记录集
     */
    List<TaskLog> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    TaskLog selectByPrimaryKey(Long taskLogSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") TaskLog record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") TaskLog record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(TaskLog record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(TaskLog record);
    
    /**
     * 根据taskLogSid或taskType和taskTarget更新记录，并且taskDetail是累加，以'\n'结尾
     */
    int logUpdateSelective(@Param("record") TaskLog record);
}