package com.h3c.idcloud.core.service.system.provider;


import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.MgtObjExtMapper;
import com.h3c.idcloud.core.pojo.dto.system.MgtObjExt;
import com.h3c.idcloud.core.service.system.api.MgtObjExtService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class MgtObjExtServiceImpl implements MgtObjExtService {
    @Autowired
    private MgtObjExtMapper mgtObjExtMapper;

    private static final Logger logger = LoggerFactory.getLogger(MgtObjExtServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.mgtObjExtMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public MgtObjExt selectByPrimaryKey(Long extendSid) {
        return this.mgtObjExtMapper.selectByPrimaryKey(extendSid);
    }
//    public List<MgtObjExt> selectByMgtObjSid(Long mgtObjSid){
//        return this.mgtObjExtMapper.selectByMgtObjSid(mgtObjSid);
//    }
    public List<MgtObjExt> selectByMgtObjSid(Long mgtObjSid) {
        Criteria criteria = new Criteria();
        criteria.put("mgtObjSid", mgtObjSid);
        return this.mgtObjExtMapper.selectByParams(criteria);
    }
    public List<MgtObjExt> selectByParams(Criteria example) {
        return this.mgtObjExtMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long extendSid) {
        return this.mgtObjExtMapper.deleteByPrimaryKey(extendSid);
    }

    public int updateByPrimaryKeySelective(MgtObjExt record) {
        return this.mgtObjExtMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(MgtObjExt record) {
        return this.mgtObjExtMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.mgtObjExtMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(MgtObjExt record, Criteria example) {
        return this.mgtObjExtMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(MgtObjExt record, Criteria example) {
        return this.mgtObjExtMapper.updateByParams(record, example.getCondition());
    }

    public int insert(MgtObjExt record) {
        return this.mgtObjExtMapper.insert(record);
    }

    public int insertSelective(MgtObjExt record) {
        return this.mgtObjExtMapper.insertSelective(record);
    }
}