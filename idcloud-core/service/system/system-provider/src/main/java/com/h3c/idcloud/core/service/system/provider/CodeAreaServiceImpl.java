package com.h3c.idcloud.core.service.system.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.CodeAreaMapper;
import com.h3c.idcloud.core.pojo.dto.system.CodeArea;
import com.h3c.idcloud.core.service.system.api.CodeAreaService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service(version = "1.0.0")
@Component
public class CodeAreaServiceImpl implements CodeAreaService {
    @Autowired
    private CodeAreaMapper codeAreaMapper;

    private static final Logger logger = LoggerFactory.getLogger(CodeAreaServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.codeAreaMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public CodeArea selectByPrimaryKey(Long areaSid) {
        return this.codeAreaMapper.selectByPrimaryKey(areaSid);
    }

    public List<CodeArea> selectByParams(Criteria example) {
        return this.codeAreaMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long areaSid) {
        return this.codeAreaMapper.deleteByPrimaryKey(areaSid);
    }

    public int updateByPrimaryKeySelective(CodeArea record) {
        return this.codeAreaMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(CodeArea record) {
        return this.codeAreaMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.codeAreaMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(CodeArea record, Criteria example) {
        return this.codeAreaMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(CodeArea record, Criteria example) {
        return this.codeAreaMapper.updateByParams(record, example.getCondition());
    }

    public int insert(CodeArea record) {
        return this.codeAreaMapper.insert(record);
    }

    public int insertSelective(CodeArea record) {
        return this.codeAreaMapper.insertSelective(record);
    }
}