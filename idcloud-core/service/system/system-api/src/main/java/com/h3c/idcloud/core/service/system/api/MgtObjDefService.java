package com.h3c.idcloud.core.service.system.api;


import com.h3c.idcloud.core.pojo.dto.system.MgtObjDef;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface MgtObjDefService {
    int countByParams(Criteria example);

    MgtObjDef selectByPrimaryKey(Long defSid);

    List<MgtObjDef> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long defSid);

    int updateByPrimaryKeySelective(MgtObjDef record);

    int updateByPrimaryKey(MgtObjDef record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(MgtObjDef record, Criteria example);

    int updateByParams(MgtObjDef record, Criteria example);

    int insert(MgtObjDef record);

    int insertSelective(MgtObjDef record);
}