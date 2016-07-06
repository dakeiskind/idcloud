package com.h3c.idcloud.core.service.system.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.SiteMsgMapper;
import com.h3c.idcloud.core.pojo.dto.system.SiteMsg;
import com.h3c.idcloud.core.service.system.api.SiteMsgService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "1.0.0")
public class SiteMsgServiceImpl implements SiteMsgService {
    @Autowired
    private SiteMsgMapper siteMsgMapper;

    private static final Logger logger = LoggerFactory.getLogger(SiteMsgServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.siteMsgMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public SiteMsg selectByPrimaryKey(Long siteMsgSid) {
        return this.siteMsgMapper.selectByPrimaryKey(siteMsgSid);
    }

    public List<SiteMsg> selectByParams(Criteria example) {
        return this.siteMsgMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long siteMsgSid) {
        return this.siteMsgMapper.deleteByPrimaryKey(siteMsgSid);
    }

    public int updateByPrimaryKeySelective(SiteMsg record) {
        return this.siteMsgMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(SiteMsg record) {
        return this.siteMsgMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.siteMsgMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(SiteMsg record, Criteria example) {
        return this.siteMsgMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(SiteMsg record, Criteria example) {
        return this.siteMsgMapper.updateByParams(record, example.getCondition());
    }

    public int insert(SiteMsg record) {
        return this.siteMsgMapper.insert(record);
    }

    public int insertSelective(SiteMsg record) {
        return this.siteMsgMapper.insertSelective(record);
    }
}