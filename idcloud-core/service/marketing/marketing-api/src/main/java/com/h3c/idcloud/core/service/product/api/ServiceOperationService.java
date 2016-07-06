package com.h3c.idcloud.core.service.product.api;


import com.h3c.idcloud.core.pojo.dto.product.ServiceOperation;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface ServiceOperationService {
    int countByParams(Criteria example);

    ServiceOperation selectByPrimaryKey(Long operationSid);

    List<ServiceOperation> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long operationSid);

    int updateByPrimaryKeySelective(ServiceOperation record);

    int insertSelective(ServiceOperation record);
    
    /**
     * 根据条件查询记录集
     */
    List<ServiceOperation> selectByServiceSid(Long serviceSid);
}