package com.h3c.idcloud.core.service.res.api;


import com.h3c.idcloud.core.pojo.dto.res.ResTopologyConfig;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface ResTopologyConfigService {
    int countByParams(Criteria example);

    ResTopologyConfig selectByPrimaryKey(String configId);

    List<ResTopologyConfig> selectByParams(Criteria example);

    int deleteByPrimaryKey(String configId);

    int updateByPrimaryKeySelective(ResTopologyConfig record);

    int updateByPrimaryKey(ResTopologyConfig record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(ResTopologyConfig record, Criteria example);

    int updateByParams(ResTopologyConfig record, Criteria example);

    int insert(ResTopologyConfig record);

    int insertSelective(ResTopologyConfig record);
}