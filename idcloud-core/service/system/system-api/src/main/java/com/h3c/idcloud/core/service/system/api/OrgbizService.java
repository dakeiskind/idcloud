package com.h3c.idcloud.core.service.system.api;

import com.h3c.idcloud.core.pojo.dto.system.Orgbiz;
import com.h3c.idcloud.core.pojo.dto.system.OrgbizKey;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;



public interface OrgbizService {
    int countByParams(Criteria example);

    List<Orgbiz> selectByParams(Criteria example);

    int deleteByPrimaryKey(OrgbizKey key);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Orgbiz record, Criteria example);

    int updateByParams(Orgbiz record, Criteria example);

    int insert(Orgbiz record);

    int insertSelective(Orgbiz record);
    
    List<Orgbiz> selectBizTreeByParams(Criteria example);
    
    List<Orgbiz> selectSecondBizesByParams(Criteria example);

	List<Orgbiz> findBizByOrg(Criteria example);

	List<Orgbiz> findParentBizByOrg(Criteria example);
}