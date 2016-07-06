package com.h3c.idcloud.core.service.system.api;

import com.h3c.idcloud.core.pojo.dto.system.MailInfo;
import com.h3c.idcloud.core.pojo.dto.system.MailTemplate;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;


public interface MailTemplateService {
    int countByParams(Criteria example);

    MailTemplate selectByPrimaryKey(Long mailTemplateSid);
    
    MailTemplate selectById(String mailTemplateId);

    List<MailTemplate> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long mailTemplateSid);

    int updateByPrimaryKeySelective(MailTemplate record);

    int updateByPrimaryKey(MailTemplate record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(MailTemplate record, Criteria example);

    int updateByParams(MailTemplate record, Criteria example);

    int insert(MailTemplate record);

    int insertSelective(MailTemplate record);
    
//    MailInfo selectVmMailInfo(Criteria example);
    
    MailInfo selectStorageMailInfo(Criteria example);
    
    MailInfo selectExMailInfo(Criteria example);
    
    MailInfo selectSpMailInfo(Criteria example);
    
    MailInfo selectDeploymentMailInfo(Criteria example);
    
    MailInfo selectFreeResMailInfo(Criteria example);
}