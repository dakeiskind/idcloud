package com.h3c.idcloud.core.service.system.api;


import com.h3c.idcloud.core.pojo.dto.system.CodeProvince;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface CodeProvinceService {
    int countByParams(Criteria example);

    CodeProvince selectByPrimaryKey(Long provinceSid);

    List<CodeProvince> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long provinceSid);

    int updateByPrimaryKeySelective(CodeProvince record);

    int updateByPrimaryKey(CodeProvince record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(CodeProvince record, Criteria example);

    int updateByParams(CodeProvince record, Criteria example);

    int insert(CodeProvince record);

    int insertSelective(CodeProvince record);
}