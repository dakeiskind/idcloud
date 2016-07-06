package com.h3c.idcloud.core.service.product.api;


import com.h3c.idcloud.core.pojo.dto.product.ServiceResInstanceType;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface ServiceResInstanceTypeService {
    int countByParams(Criteria example);


    List<ServiceResInstanceType> selectByParams(Criteria example);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(ServiceResInstanceType record, Criteria example);

    int updateByParams(ServiceResInstanceType record, Criteria example);

    int insert(ServiceResInstanceType record);

    int insertSelective(ServiceResInstanceType record);
}