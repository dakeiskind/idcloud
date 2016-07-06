package com.h3c.idcloud.core.service.system.api;

import com.h3c.idcloud.core.pojo.dto.system.Code;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;


public interface CodeService {
    int countByParams(Criteria example);

    Code selectByPrimaryKey(Long codeSid);

    List<Code> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long codeSid);

    int updateByPrimaryKeySelective(Code record);

    int updateByPrimaryKey(Code record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Code record, Criteria example);

    int updateByParams(Code record, Criteria example);

    int insert(Code record);

    int insertSelective(Code record);

    List<Code> findImageSoftWare(Criteria example);

    String getSoftwareName(String softwareVersion);

    Code getCodeByValue(String value, String codeCategory);

    List<Code> findParentCodeByCodeVaule(Criteria example);

    List<Code> getParentCodeByCodeVaule(Criteria example);
}