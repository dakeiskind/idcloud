package com.h3c.idcloud.core.service.product.api;


import com.h3c.idcloud.core.pojo.dto.product.FreeResCheckLog;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface FreeResCheckLogService {
    int countByParams(Criteria example);

    FreeResCheckLog selectByPrimaryKey(Long fresCheckLogSid);

    public FreeResCheckLog selectByFreeSid(Long fresSid);
    
    List<FreeResCheckLog> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long fresCheckLogSid);

    int updateByPrimaryKeySelective(FreeResCheckLog record);

    int updateByPrimaryKey(FreeResCheckLog record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(FreeResCheckLog record, Criteria example);

    int updateByParams(FreeResCheckLog record, Criteria example);

    int insert(FreeResCheckLog record);

    int insertSelective(FreeResCheckLog record);

	List<FreeResCheckLog> selectResByBizForSummary(Criteria criteria);

	FreeResCheckLog selectResByInstIdForCmId(Criteria example);
}