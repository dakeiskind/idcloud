package com.h3c.idcloud.core.persist.system.dao;

import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.system.AlarmData;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author gujie
 */
@Repository
public interface AlarmDataMapper {
    /**
     * 根据条件查询记录总数
     * @param example 参数
     * @return 返回值
     */
    int countByParams(Criteria example);

    /**
     * 根据条件删除记录
     * @param example 参数
     * @return 返回值
     */
    int deleteByParams(Criteria example);

    /**
     * 根据主键删除记录
     * @param alarmDataSid 告警信息Sid
     * @return 返回值
     */
    int deleteByPrimaryKey(Long alarmDataSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     * @param record 告警信息数据
     * @return 返回值
     */
    int insert(AlarmData record);

    /**
     * 保存属性不为空的记录
     * @param record 告警信息数据
     * @return 返回值
     */
    int insertSelective(AlarmData record);

    /**
     * 根据参数查询数据
     * @param example 参数
     * @return 返回值
     */
    List<AlarmData> countAlarmByLevel(Criteria example);

    /**
     * 根据条件查询记录集
     * @param example 参数
     * @return 返回值
     */
    List<AlarmData> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     * @param alarmDataSid 告警信息Sid
     * @return 返回值
     */
    AlarmData selectByPrimaryKey(Long alarmDataSid);

    /**
     * 根据条件更新属性不为空的记录
     * @param record 告警信息数据
     * @param condition 参数
     * @return 返回值
     */
    int updateByParamsSelective(@Param("record") AlarmData record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     * @param record 告警信息数据
     * @param condition 参数
     * @return 返回值
     */
    int updateByParams(@Param("record") AlarmData record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     * @param record 告警信息数据
     * @return 返回值
     */
    int updateByPrimaryKeySelective(AlarmData record);

    /**
     * 根据主键更新记录
     * @param record 告警信息数据
     * @return 返回值
     */
    int updateByPrimaryKey(AlarmData record);


}