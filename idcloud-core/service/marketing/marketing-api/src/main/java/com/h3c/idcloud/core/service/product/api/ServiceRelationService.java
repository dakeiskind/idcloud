package com.h3c.idcloud.core.service.product.api;


import com.h3c.idcloud.core.pojo.dto.product.ServiceRelation;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface ServiceRelationService {
    int countByParams(Criteria example);

    List<ServiceRelation> selectByParams(Criteria example);

    int updateByPrimaryKey(ServiceRelation record);

    int deleteByParams(Criteria example);

    int insertSelective(ServiceRelation record);
}