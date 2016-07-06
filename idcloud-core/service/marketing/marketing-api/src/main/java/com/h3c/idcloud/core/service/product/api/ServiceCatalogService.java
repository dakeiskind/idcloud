package com.h3c.idcloud.core.service.product.api;


import com.h3c.idcloud.core.pojo.dto.product.ServiceCatalog;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * 服务目录Service
 * 
 * @author 张荣
 */
public interface ServiceCatalogService {
    int countByParams(Criteria example);

    ServiceCatalog selectByPrimaryKey(Long catalogSid);

    List<ServiceCatalog> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long catalogSid);

    int updateByPrimaryKeySelective(ServiceCatalog record);

    int updateByPrimaryKey(ServiceCatalog record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(ServiceCatalog record, Criteria example);

    int updateByParams(ServiceCatalog record, Criteria example);

    int insert(ServiceCatalog record);

    int insertSelective(ServiceCatalog record);
}