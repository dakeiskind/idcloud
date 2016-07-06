package com.h3c.idcloud.core.service.system.api;

import com.h3c.idcloud.core.pojo.dto.system.Org;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;



public interface OrgService {
    int countByParams(Criteria example);

    Org selectByPrimaryKey(Long orgSid);

    List<Org> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long orgSid);

    int updateByPrimaryKeySelective(Org record);

    int updateByPrimaryKey(Org record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Org record, Criteria example);

    int updateByParams(Org record, Criteria example);

    int insert(Org record);

    int insertSelective(Org record);

	List<Org> selectByParamsWithoutRoot(Criteria example);
    
	// List<ResourceTopology> selectOrgTopologyByParams(Criteria example);
	// List<ResourceTopology> selectOrgPoolTopologyByParams(Criteria example);

}