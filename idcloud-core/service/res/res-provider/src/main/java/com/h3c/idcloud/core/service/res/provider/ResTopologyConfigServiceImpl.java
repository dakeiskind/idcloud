package com.h3c.idcloud.core.service.res.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.res.dao.ResTopologyConfigMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResTopologyConfig;
import com.h3c.idcloud.core.service.res.api.ResTopologyConfigService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0.0")
@Component
public class ResTopologyConfigServiceImpl implements ResTopologyConfigService {
    private static final Logger logger = LoggerFactory.getLogger(ResTopologyConfigServiceImpl.class);
    @Autowired
    private ResTopologyConfigMapper resTopologyConfigMapper;

    public int countByParams(Criteria example) {
        int count = this.resTopologyConfigMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ResTopologyConfig selectByPrimaryKey(String configId) {
        return this.resTopologyConfigMapper.selectByPrimaryKey(configId);
    }

    public List<ResTopologyConfig> selectByParams(Criteria example) {
        return this.resTopologyConfigMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(String configId) {
        return this.resTopologyConfigMapper.deleteByPrimaryKey(configId);
    }

    public int updateByPrimaryKeySelective(ResTopologyConfig record) {
        return this.resTopologyConfigMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ResTopologyConfig record) {
        return this.resTopologyConfigMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.resTopologyConfigMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(ResTopologyConfig record, Criteria example) {
        return this.resTopologyConfigMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(ResTopologyConfig record, Criteria example) {
        return this.resTopologyConfigMapper.updateByParams(record, example.getCondition());
    }

    public int insert(ResTopologyConfig record) {
        return this.resTopologyConfigMapper.insert(record);
    }

    public int insertSelective(ResTopologyConfig record) {
        return this.resTopologyConfigMapper.insertSelective(record);
    }
}