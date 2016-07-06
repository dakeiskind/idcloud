package com.h3c.idcloud.core.service.system.api;

import com.h3c.idcloud.core.pojo.dto.system.CodeArea;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface CodeAreaService {
    int countByParams(Criteria example);

    CodeArea selectByPrimaryKey(Long areaSid);

    List<CodeArea> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long areaSid);

    int updateByPrimaryKeySelective(CodeArea record);

    int updateByPrimaryKey(CodeArea record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(CodeArea record, Criteria example);

    int updateByParams(CodeArea record, Criteria example);

    int insert(CodeArea record);

    int insertSelective(CodeArea record);
}