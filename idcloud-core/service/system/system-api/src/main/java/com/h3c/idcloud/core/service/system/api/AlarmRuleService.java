package com.h3c.idcloud.core.service.system.api;

import com.h3c.idcloud.core.pojo.dto.system.AlarmRule;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * @author gujie
 */
public interface AlarmRuleService {

    /**
     *
     * @param example 参数
     * @return 返回值
     */
    int countByParams(Criteria example);

    /**
     *
     * @param alarmRuleSid 告警规则Sid
     * @return 返回值
     */
    AlarmRule selectByPrimaryKey(Long alarmRuleSid);

    /**
     *
     * @param example 参数
     * @return 返回值
     */
    List<AlarmRule> selectByParams(Criteria example);

    /**
     *
     * @param alarmRuleSid 告警规则Sid
     * @return 返回值
     */
    int deleteByPrimaryKey(Long alarmRuleSid);

    /**
     *
     * @param record 告警规则数据
     * @return 返回值
     */
    int updateByPrimaryKeySelective(AlarmRule record);

    /**
     *
     * @param record 告警规则数据
     * @return 返回值
     */
    int updateByPrimaryKey(AlarmRule record);

    /**
     *
     * @param example 参数
     * @return 返回值
     */
    int deleteByParams(Criteria example);

    /**
     *
     * @param record 告警规则数据
     * @param example 其他参数
     * @return 返回值
     */
    int updateByParamsSelective(AlarmRule record, Criteria example);

    /**
     *
     * @param record 告警规则数据
     * @param example 其他参数
     * @return 返回值
     */
    int updateByParams(AlarmRule record, Criteria example);

    /**
     *
     * @param record 告警规则数据
     * @return 返回值
     */
    int insert(AlarmRule record);

    /**
     *
     * @param record 告警规则数据
     * @return 返回值
     */
    int insertSelective(AlarmRule record);
}
