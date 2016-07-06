package com.h3c.idcloud.core.service.product.api;


import com.h3c.idcloud.core.pojo.dto.product.ServiceConfig;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface ServiceConfigService {
    int countByParams(Criteria example);

    ServiceConfig selectByPrimaryKey(Long configSid);

    List<ServiceConfig> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long configSid);

    int updateByPrimaryKeySelective(ServiceConfig record);

    int insertSelective(ServiceConfig record);
    
    /**
     * 根据条件查询记录集
     */
    List<ServiceConfig> selectByServiceSid(Long serviceSid);
    
    String selectActivitiFlowByServiceSid(Long serviceSid, String ActivitiName);
}