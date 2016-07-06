package com.h3c.idcloud.core.service.system.api;


import com.h3c.idcloud.core.pojo.dto.system.MgtObjExt;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface MgtObjExtService {
    int countByParams(Criteria example);

    MgtObjExt selectByPrimaryKey(Long extendSid);

    List<MgtObjExt> selectByParams(Criteria example);
    List<MgtObjExt> selectByMgtObjSid(Long mgtObjSid);
    int deleteByPrimaryKey(Long extendSid);

    int updateByPrimaryKeySelective(MgtObjExt record);

    int updateByPrimaryKey(MgtObjExt record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(MgtObjExt record, Criteria example);

    int updateByParams(MgtObjExt record, Criteria example);

    int insert(MgtObjExt record);

    int insertSelective(MgtObjExt record);
}