package com.h3c.idcloud.core.service.system.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.CodeCityMapper;
import com.h3c.idcloud.core.pojo.dto.system.CodeCity;
import com.h3c.idcloud.core.service.system.api.CodeCityService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service(version = "1.0.0")
@Component
public class CodeCityServiceImpl implements CodeCityService {
    @Autowired
    private CodeCityMapper codeCityMapper;

    private static final Logger logger = LoggerFactory.getLogger(CodeCityServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.codeCityMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public CodeCity selectByPrimaryKey(Long citySid) {
        return this.codeCityMapper.selectByPrimaryKey(citySid);
    }

    public List<CodeCity> selectByParams(Criteria example) {
        return this.codeCityMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long citySid) {
        return this.codeCityMapper.deleteByPrimaryKey(citySid);
    }

    public int updateByPrimaryKeySelective(CodeCity record) {
        return this.codeCityMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(CodeCity record) {
        return this.codeCityMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.codeCityMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(CodeCity record, Criteria example) {
        return this.codeCityMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(CodeCity record, Criteria example) {
        return this.codeCityMapper.updateByParams(record, example.getCondition());
    }

    public int insert(CodeCity record) {
        return this.codeCityMapper.insert(record);
    }

    public int insertSelective(CodeCity record) {
        return this.codeCityMapper.insertSelective(record);
    }
}