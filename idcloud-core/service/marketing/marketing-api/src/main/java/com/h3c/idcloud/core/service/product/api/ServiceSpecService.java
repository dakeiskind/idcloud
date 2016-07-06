package com.h3c.idcloud.core.service.product.api;


import com.h3c.idcloud.core.pojo.dto.product.ServiceSpec;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface ServiceSpecService {
    int countByParams(Criteria example);

    ServiceSpec selectByPrimaryKey(Long specSid);

    List<ServiceSpec> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long specSid);

    int updateByPrimaryKeySelective(ServiceSpec record);

    int insertSelective(ServiceSpec record);
    
    /**
     * 根据服务sid记录集
     */
    List<ServiceSpec> selectByServiceSid(Long serviceSid);
    
    /**
     * 根据条件查询记录集
     */
    List<ServiceSpec> selectTemplateSpec(Criteria example);
}