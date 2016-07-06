package com.h3c.idcloud.core.service.system.api;


import com.h3c.idcloud.core.pojo.dto.system.Sysconfig;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface SysconfigService {
    int countByParams(Criteria example);

    Sysconfig selectByPrimaryKey(Long configSid);

    List<Sysconfig> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long configSid);

    int updateByPrimaryKeySelective(Sysconfig record);

    int updateByPrimaryKey(Sysconfig record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Sysconfig record, Criteria example);

    int updateByParams(Sysconfig record, Criteria example);

    int insert(Sysconfig record);

    int insertSelective(Sysconfig record);

    public List<Sysconfig> selectConfigTypeByParams(Criteria criteria);

    /**
     * 此方法不会进行空结果验证，调用者需要自己判断
     * @param configKey
     * @return
     */
    public String getValueByConfigKey(String configKey);
}