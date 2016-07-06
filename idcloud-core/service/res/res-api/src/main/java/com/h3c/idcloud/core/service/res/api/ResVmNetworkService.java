package com.h3c.idcloud.core.service.res.api;


import com.h3c.idcloud.core.pojo.dto.res.ResVmNetwork;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface ResVmNetworkService {
    int countByParams(Criteria example);

    ResVmNetwork selectByPrimaryKey(String resVmSid);

    List<ResVmNetwork> selectByParams(Criteria example);

    int deleteByPrimaryKey(String resVmSid);

    int updateByPrimaryKeySelective(ResVmNetwork record);

    int updateByPrimaryKey(ResVmNetwork record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(ResVmNetwork record, Criteria example);

    int updateByParams(ResVmNetwork record, Criteria example);

    int insert(ResVmNetwork record);

    int insertSelective(ResVmNetwork record);

	/**
	 * @param example
	 * @return
	 */
	List<ResVmNetwork> selectNetsByParams(Criteria example);
}