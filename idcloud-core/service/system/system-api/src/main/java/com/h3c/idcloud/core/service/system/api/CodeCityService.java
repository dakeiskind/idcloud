package com.h3c.idcloud.core.service.system.api;

import com.h3c.idcloud.core.pojo.dto.system.CodeCity;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface CodeCityService {
    int countByParams(Criteria example);

    CodeCity selectByPrimaryKey(Long citySid);

    List<CodeCity> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long citySid);

    int updateByPrimaryKeySelective(CodeCity record);

    int updateByPrimaryKey(CodeCity record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(CodeCity record, Criteria example);

    int updateByParams(CodeCity record, Criteria example);

    int insert(CodeCity record);

    int insertSelective(CodeCity record);
}