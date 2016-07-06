package com.h3c.idcloud.core.service.system.api;

import com.h3c.idcloud.core.pojo.dto.system.SiteMsg;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface SiteMsgService {
    int countByParams(Criteria example);

    SiteMsg selectByPrimaryKey(Long siteMsgSid);

    List<SiteMsg> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long siteMsgSid);

    int updateByPrimaryKeySelective(SiteMsg record);

    int updateByPrimaryKey(SiteMsg record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(SiteMsg record, Criteria example);

    int updateByParams(SiteMsg record, Criteria example);

    int insert(SiteMsg record);

    int insertSelective(SiteMsg record);
}