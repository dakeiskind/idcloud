package com.h3c.idcloud.core.service.product.api;


import com.h3c.idcloud.core.pojo.dto.product.ServiceInstRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstResKey;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface ServiceInstResService {
    int countByParams(Criteria example);

    ServiceInstRes selectByPrimaryKey(ServiceInstResKey key);

    List<ServiceInstRes> selectByParams(Criteria example);

    int deleteByPrimaryKey(ServiceInstResKey key);

    int updateByPrimaryKeySelective(ServiceInstRes record);

    int updateByPrimaryKey(ServiceInstRes record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(ServiceInstRes record, Criteria example);

    int updateByParams(ServiceInstRes record, Criteria example);

    int insert(ServiceInstRes record);

    int insertSelective(ServiceInstRes record);

    List<ServiceInstRes> selectInstanceReses(Long instanceSid);

    String getResSidByInstanceSid(Long instanceSid);

    Long getInstanceSidByResSid(String resSid);
}