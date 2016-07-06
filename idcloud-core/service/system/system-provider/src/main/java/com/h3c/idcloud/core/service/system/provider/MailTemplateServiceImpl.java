package com.h3c.idcloud.core.service.system.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.MailTemplateMapper;
import com.h3c.idcloud.core.pojo.dto.system.MailInfo;
import com.h3c.idcloud.core.pojo.dto.system.MailTemplate;
import com.h3c.idcloud.core.service.system.api.MailTemplateService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class MailTemplateServiceImpl implements MailTemplateService {
    @Autowired
    private MailTemplateMapper mailTemplateMapper;

    private static final Logger logger = LoggerFactory.getLogger(MailTemplateServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.mailTemplateMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public MailTemplate selectByPrimaryKey(Long mailTemplateSid) {
        return this.mailTemplateMapper.selectByPrimaryKey(mailTemplateSid);
    }
    
    public MailTemplate selectById(String mailTemplateId) {
        return this.mailTemplateMapper.selectById(mailTemplateId);
    }

    public List<MailTemplate> selectByParams(Criteria example) {
        return this.mailTemplateMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long mailTemplateSid) {
        return this.mailTemplateMapper.deleteByPrimaryKey(mailTemplateSid);
    }

    public int updateByPrimaryKeySelective(MailTemplate record) {
        return this.mailTemplateMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(MailTemplate record) {
        return this.mailTemplateMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.mailTemplateMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(MailTemplate record, Criteria example) {
        return this.mailTemplateMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(MailTemplate record, Criteria example) {
        return this.mailTemplateMapper.updateByParams(record, example.getCondition());
    }

    public int insert(MailTemplate record) {
        return this.mailTemplateMapper.insert(record);
    }

    public int insertSelective(MailTemplate record) {
        return this.mailTemplateMapper.insertSelective(record);
    }
    
    public MailInfo selectVmMailInfo(Criteria example) {
        return this.mailTemplateMapper.selectVmMailInfo(example);
    }
    
    public MailInfo selectStorageMailInfo(Criteria example) {
        return this.mailTemplateMapper.selectStorageMailInfo(example);
    }
    
    public MailInfo selectExMailInfo(Criteria example) {
        return this.mailTemplateMapper.selectExMailInfo(example);
    }
    
    public MailInfo selectSpMailInfo(Criteria example) {
        return this.mailTemplateMapper.selectSpMailInfo(example);
    }
    
    public MailInfo selectDeploymentMailInfo(Criteria example) {
        return this.mailTemplateMapper.selectDeploymentMailInfo(example);
    }
    
    public MailInfo selectFreeResMailInfo(Criteria example){
    	return this.mailTemplateMapper.selectFreeResMailInfo(example);
    }
}