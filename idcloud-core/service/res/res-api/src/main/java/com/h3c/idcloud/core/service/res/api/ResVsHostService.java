package com.h3c.idcloud.core.service.res.api;

import com.h3c.idcloud.core.pojo.dto.res.ResVsHost;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface ResVsHostService {
    int countByParams(Criteria example);


    List<ResVsHost> selectByParams(Criteria example);

    int deleteByPrimaryKey(ResVsHost key);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(ResVsHost record, Criteria example);

    int updateByParams(ResVsHost record, Criteria example);

    int insert(ResVsHost record);

    int insertSelective(ResVsHost record);
}