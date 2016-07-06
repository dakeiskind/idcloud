package com.h3c.idcloud.core.service.product.api;


import com.h3c.idcloud.core.pojo.dto.product.ServicePerf;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface ServicePerfService {
    int countByParams(Criteria example);

    ServicePerf selectByPrimaryKey(Long perfSid);

    List<ServicePerf> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long perfSid);

    int updateByPrimaryKeySelective(ServicePerf record);

    int insertSelective(ServicePerf record);
    
    /**
     * 根据服务sid查询记录集
     */
    List<ServicePerf> selectByServiceSid(Long serviceSid);
}