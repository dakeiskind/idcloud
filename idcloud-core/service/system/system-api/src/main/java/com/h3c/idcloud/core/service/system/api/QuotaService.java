package com.h3c.idcloud.core.service.system.api;

import com.h3c.idcloud.core.pojo.dto.system.Quota;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;
import java.util.Map;



public interface QuotaService {
    int countByParams(Criteria example);

    Quota selectByPrimaryKey(Long quotaSid);

    List<Quota> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long quotaSid);

    int updateByPrimaryKeySelective(Quota record);

    int updateByPrimaryKey(Quota record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Quota record, Criteria example);

    int updateByParams(Quota record, Criteria example);

    int insert(Quota record);

    int insertSelective(Quota record);

    Long selectByQuotaUsage(Criteria example);
    
    List<Quota> selectParentBizQuota(Criteria example);

    List<Quota> selectChildrenBizQuotas(Criteria example);
    
    List<Quota> selectDeptUsageQuotas(Long orgSid);
    
    Map<String, Object> selectMgtObjQuotasByOsTypes(Long mgtObjSid, String osTypes);

	List<Quota> countByQuotaKey(Criteria example);
}