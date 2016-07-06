package com.h3c.idcloud.core.persist.system.dao;

import com.h3c.idcloud.core.pojo.dto.system.AlarmRule;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author gujie
 */
public interface AlarmRuleMapper {

    /**
     * 根据条件查询记录总数
     * @param example 参数
     * @return 返回数据
     */
    int countByParams(Criteria example);

    /**
     * 根据条件删除记录
     * @param example 参数
     * @return 返回数据
     */
    int deleteByParams(Criteria example);

    /**
     * 根据主键删除记录
     * @param alarmRuleSid 告警规则SId
     * @return 返回数据
     */
    int deleteByPrimaryKey(Long alarmRuleSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     * @param record 告警规则数据
     * @return 返回数据
     */
    int insert(AlarmRule record);

    /**
     * 保存属性不为空的记录
     * @param  record 告警规则数据
     * @return 返回数据
     */
    int insertSelective(AlarmRule record);

    /**
     * 根据条件查询记录集
     * @param example 参数
     * @return 返回数据
     */
    List<AlarmRule> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     * @param alarmRuleSid 告警规则SId
     * @return 返回数据
     */
    AlarmRule selectByPrimaryKey(Long alarmRuleSid);

    /**
     * 根据条件更新属性不为空的记录
     * @param record 告警规则数据
     * @param condition 其他参数
     * @return 返回数据
     */
    int updateByParamsSelective(@Param("record") AlarmRule record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     * @param  record 告警规则数据
     * @param  condition 其他参数
     * @return 返回数据
     */
    int updateByParams(@Param("record") AlarmRule record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     * @param record 告警规则数据
     * @return 返回数据
     */
    int updateByPrimaryKeySelective(AlarmRule record);

    /**
     * 根据主键更新记录
     * @param record 告警规则数据
     * @return 返回数据
     */
    int updateByPrimaryKey(AlarmRule record);
}
