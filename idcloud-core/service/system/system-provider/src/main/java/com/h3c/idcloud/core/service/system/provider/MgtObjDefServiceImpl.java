package com.h3c.idcloud.core.service.system.provider;


import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.MgtObjDefMapper;
import com.h3c.idcloud.core.pojo.dto.system.MgtObjDef;
import com.h3c.idcloud.core.service.system.api.MgtObjDefService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class MgtObjDefServiceImpl implements MgtObjDefService {
    @Autowired
    private MgtObjDefMapper mgtObjDefMapper;

    private static final Logger logger = LoggerFactory.getLogger(MgtObjDefServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.mgtObjDefMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public MgtObjDef selectByPrimaryKey(Long defSid) {
        return this.mgtObjDefMapper.selectByPrimaryKey(defSid);
    }

    public List<MgtObjDef> selectByParams(Criteria example) {
        return this.mgtObjDefMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long defSid) {
        return this.mgtObjDefMapper.deleteByPrimaryKey(defSid);
    }

    public int updateByPrimaryKeySelective(MgtObjDef record) {
        return this.mgtObjDefMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(MgtObjDef record) {
        return this.mgtObjDefMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.mgtObjDefMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(MgtObjDef record, Criteria example) {
        return this.mgtObjDefMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(MgtObjDef record, Criteria example) {
        return this.mgtObjDefMapper.updateByParams(record, example.getCondition());
    }

    public int insert(MgtObjDef record) {
        return this.mgtObjDefMapper.insert(record);
    }

    public int insertSelective(MgtObjDef record) {
        return this.mgtObjDefMapper.insertSelective(record);
    }
}