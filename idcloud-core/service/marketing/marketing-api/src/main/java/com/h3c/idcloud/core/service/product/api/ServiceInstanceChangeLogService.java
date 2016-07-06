package com.h3c.idcloud.core.service.product.api;


import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceChangeLog;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceChangeLogSpec;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.codehaus.jackson.type.TypeReference;

import java.util.List;

public interface ServiceInstanceChangeLogService {
    int countByParams(Criteria example);

    ServiceInstanceChangeLog selectByPrimaryKey(Long changeLogSid);

    List<ServiceInstanceChangeLog> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long changeLogSid);

    int updateByPrimaryKeySelective(ServiceInstanceChangeLog record);

    int updateByPrimaryKey(ServiceInstanceChangeLog record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(ServiceInstanceChangeLog record, Criteria example);

    int updateByParams(ServiceInstanceChangeLog record, Criteria example);

    int insert(ServiceInstanceChangeLog record);

    int insertSelective(ServiceInstanceChangeLog record);

    void submitChangeServiceInstance(ServiceInstance instance, String changeType, Object changeInfo);

    void beginChangeServiceInstance(Long instanceSid);

    <T> void updateChangeServiceInstance(Long instanceSid, String changeType, ServiceInstanceChangeLogSpec<?> changeInfo, TypeReference<T> type);

    void endChangeServiceInstance(ServiceInstance instance, String changeType);

    <T> T getChangeInfo(Long instanceSid, TypeReference<T> type);

    ServiceInstanceChangeLog getLastChangeLog(Long instanceSid);

    List<ServiceInstanceChangeLog> selectLastChangeLog(Long instanceSid);
}