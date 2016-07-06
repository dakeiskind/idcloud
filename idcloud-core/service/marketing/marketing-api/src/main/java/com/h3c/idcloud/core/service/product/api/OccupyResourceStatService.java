package com.h3c.idcloud.core.service.product.api;

import com.h3c.idcloud.core.pojo.dto.product.OccupyResourceStat;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface OccupyResourceStatService {
    int countByParams(Criteria example);

    OccupyResourceStat selectByPrimaryKey(Long occupyResourceSid);

    List<OccupyResourceStat> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long occupyResourceSid);

    int updateByPrimaryKeySelective(OccupyResourceStat record);

    int updateByPrimaryKey(OccupyResourceStat record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(OccupyResourceStat record, Criteria example);

    int updateByParams(OccupyResourceStat record, Criteria example);

    int insert(OccupyResourceStat record);

    int insertSelective(OccupyResourceStat record);

	List<OccupyResourceStat> selectByParamsSum(Criteria example);
	
	List<OccupyResourceStat> selectByParamsSumByDate(Criteria example);

	List<OccupyResourceStat> selectUsageByParamsSum(Criteria example);
	
	List<OccupyResourceStat> selectUsageByParamsForRank(Criteria example);
	
	void insertOccupyResStat();
}