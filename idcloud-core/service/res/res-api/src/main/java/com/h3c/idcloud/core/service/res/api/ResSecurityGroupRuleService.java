package com.h3c.idcloud.core.service.res.api;

import com.h3c.idcloud.core.adapter.pojo.network.SgRuleCreate;
import com.h3c.idcloud.core.adapter.pojo.network.SgRuleDelete;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgRuleCreateResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgRuleDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgRuleListQueryResult;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

/**
 * 安全组策略 接口.
 *
 * @author Chaohong.Mao
 */
public interface ResSecurityGroupRuleService {

    /**
     * 查询安全组规则列表
     *
     * @param id        安全组ID
     * @param mgtObjSid 管理对象SID
     *
     * @return 安全组策略List
     */
    SgRuleListQueryResult selectByParams(String id, Long mgtObjSid);

    /**
     * 更新安全组规则
     *
     * @param example 参数
     *
     * @return int
     */
    int updateByPrimaryKeySelective(Criteria example);

    /**
     * 添加安全组规则
     *
     * @param ruleCreate 安全组策略创建对象
     * @param mgtObj     管理对象
     *
     * @return sg rule create result
     */
    SgRuleCreateResult insertSelective(SgRuleCreate ruleCreate, MgtObj mgtObj);

    /**
     * 删除安全组规则
     *
     * @param ruleDelete 删除策略对象
     * @param mgtObj     管理对象
     *
     * @return 删除结果
     */
    SgRuleDeleteResult deleteByPrimaryKey(SgRuleDelete ruleDelete, MgtObj mgtObj);

}