package com.h3c.idcloud.core.service.product.api;

import com.h3c.idcloud.core.pojo.dto.system.Biz;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;


public interface BusinesstypeService {
    int countByParams(Criteria example);

    Biz selectByPrimaryKey(Long bizSid);

    List<Biz> selectByParams(Criteria example);
    
    List<Biz> selectFbizByParams(Criteria example);
    
    List<Biz> selectByParams2(Criteria example);

    int deleteByPrimaryKey(Long bizSid);

    int updateByPrimaryKeySelective(Biz record);

    int updateByPrimaryKey(Biz record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Biz record, Criteria example);

    int updateByParams(Biz record, Criteria example);

    int insert(Biz record);

    int insertSelective(Biz record);

	List<Biz> selectForOrgbiz(Long bizSid);

	List<Biz> selectForQuota(Long bizSid);
}