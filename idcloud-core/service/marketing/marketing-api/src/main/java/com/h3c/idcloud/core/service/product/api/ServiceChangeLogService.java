package com.h3c.idcloud.core.service.product.api;

import com.h3c.idcloud.core.pojo.dto.product.ServiceChangeLog;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface ServiceChangeLogService {
    int countByParams(Criteria example);

    ServiceChangeLog selectByPrimaryKey(Long cmSid);

    List<ServiceChangeLog> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long cmSid);

    int updateByPrimaryKeySelective(ServiceChangeLog record);

    int updateByPrimaryKey(ServiceChangeLog record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(ServiceChangeLog record, Criteria example);

    int updateByParams(ServiceChangeLog record, Criteria example);

    int insert(ServiceChangeLog record);

    int insertSelective(ServiceChangeLog record);

	List<ServiceChangeLog> selectByParams2(Criteria example);
}