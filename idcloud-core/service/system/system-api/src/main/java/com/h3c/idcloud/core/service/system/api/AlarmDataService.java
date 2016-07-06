package com.h3c.idcloud.core.service.system.api;

import com.h3c.idcloud.core.pojo.dto.system.AlarmData;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * @author gujie
 */
public interface AlarmDataService {

    /**
     *
     * @param example 参数
     * @return 返回值
     */
    int countByParams(Criteria example);

    /**
     *
     * @param alarmDataSid 告警信息Sid
     * @return 返回值
     */
    AlarmData selectByPrimaryKey(Long alarmDataSid);

    /**
     *
     * @param example 参数
     * @return 返回值
     */
    List<AlarmData> selectByParams(Criteria example);

    /**
     *
     * @param alarmDataSid 告警信息Sid
     * @return 返回值
     */
    int deleteByPrimaryKey(Long alarmDataSid);

    /**
     *
     * @param record 告警数据
     * @return 返回值
     */
    int updateByPrimaryKeySelective(AlarmData record);

    /**
     *
     * @param record 告警数据
     * @return 返回值
     */
    int updateByPrimaryKey(AlarmData record);

    /**
     *
     * @param example 参数
     * @return 返回值
     */
    int deleteByParams(Criteria example);

    /**
     *
     * @param record 告警数据
     * @param example 其他参数
     * @return 返回值
     */
    int updateByParamsSelective(AlarmData record, Criteria example);

    /**
     *
     * @param record 告警数据
     * @param example 其他参数
     * @return 返回值
     */
    int updateByParams(AlarmData record, Criteria example);

    /**
     *
     * @param record 告警数据
     * @return 返回值
     */
    int insert(AlarmData record);

    /**
     *
     * @param record 告警数据
     * @return 返回值
     */
    int insertSelective(AlarmData record);

//    /**
//     *
//     * @param example
//     */
//    void syncAlarmInfo(Criteria example);

    /**
     *
     * @param example 参数
     * @return 返回值
     */
    List<AlarmData> countAlarmByLevel(Criteria example);
    
}