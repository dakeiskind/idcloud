package com.h3c.idcloud.core.service.product.api;


import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceSpec;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface ServiceInstanceSpecService {
    int countByParams(Criteria example);

    ServiceInstanceSpec selectByPrimaryKey(Long specSid);

    List<ServiceInstanceSpec> selectByParams(Criteria example);

    List<ServiceInstanceSpec> selectByParamsNewVersion(Criteria example);
 
    List<ServiceInstanceSpec> selectByParamsLatest(Criteria example);
    
    int deleteByPrimaryKey(Long specSid);

    int updateByPrimaryKeySelective(ServiceInstanceSpec record);

    int insertSelective(ServiceInstanceSpec record);
    
    int deleteByParams(Criteria example);
    /**
     * 根据条件查询记录集
     */
    List<ServiceInstanceSpec> selectByInstanceSid(Long instanceSid);

    List<ServiceInstanceSpec> selectByInstanceSpecBySid(Long instanceSid);
    
    Long selectByInstanceSpecMaxVersion(Long instanceSid);

    List<ServiceInstanceSpec> selectByInstanceSpecByVersion(Long instanceSid, String status, Long version);
    
    void modifyInstanceSpecToValid(Long instanceSid);
    
    void modifyInstanceSpecToChanging(Long instanceSid);
    
    void modifyInstanceSpecToInvalid(Long instanceSid);

	List<ServiceInstanceSpec> selectByInstanceSpecForDataDiskBySid(Long instanceSid);

	Long selectValidInstanceSpecByVersion(Long processObjectId);
	
	boolean checkInstanceValid(Long instanceSid);

	Long selectValidSpecByInstanceAndVersion(Criteria criteria);
	
	Long selectVmDiskTotalSize(Long instanceSid);
	
	int updateByName(Long instanceSid, String name, String value);
}