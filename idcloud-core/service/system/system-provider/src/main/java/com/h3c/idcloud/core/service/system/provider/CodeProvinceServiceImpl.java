package com.h3c.idcloud.core.service.system.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.CodeProvinceMapper;
import com.h3c.idcloud.core.pojo.dto.system.CodeProvince;
import com.h3c.idcloud.core.service.system.api.CodeProvinceService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class CodeProvinceServiceImpl implements CodeProvinceService {
    @Autowired
    private CodeProvinceMapper codeProvinceMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeProvinceServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.codeProvinceMapper.countByParams(example);
        LOGGER.debug("count: {}", count);
        return count;
    }

    public CodeProvince selectByPrimaryKey(Long provinceSid) {
        return this.codeProvinceMapper.selectByPrimaryKey(provinceSid);
    }

    public List<CodeProvince> selectByParams(Criteria example) {
        return this.codeProvinceMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long provinceSid) {
        return this.codeProvinceMapper.deleteByPrimaryKey(provinceSid);
    }

    public int updateByPrimaryKeySelective(CodeProvince record) {
        return this.codeProvinceMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(CodeProvince record) {
        return this.codeProvinceMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.codeProvinceMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(CodeProvince record, Criteria example) {
        return this.codeProvinceMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(CodeProvince record, Criteria example) {
        return this.codeProvinceMapper.updateByParams(record, example.getCondition());
    }

    public int insert(CodeProvince record) {
        return this.codeProvinceMapper.insert(record);
    }

    public int insertSelective(CodeProvince record) {
        return this.codeProvinceMapper.insertSelective(record);
    }
}