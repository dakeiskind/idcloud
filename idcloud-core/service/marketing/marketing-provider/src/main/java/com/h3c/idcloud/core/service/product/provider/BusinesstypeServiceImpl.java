package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.BizMapper;
import com.h3c.idcloud.core.pojo.dto.system.Biz;
import com.h3c.idcloud.core.service.product.api.BusinesstypeService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Service(version = "1.0.0")
@Component
public class BusinesstypeServiceImpl implements BusinesstypeService {
    @Autowired
    private BizMapper businesstypeMapper;

    private static final Logger logger = LoggerFactory.getLogger(BusinesstypeServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.businesstypeMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Biz selectByPrimaryKey(Long bizSid) {
        return this.businesstypeMapper.selectByPrimaryKey(bizSid);
    }
    
    public List<Biz> selectByParams(Criteria example) {
        return this.businesstypeMapper.selectByParams(example);
    }
    
    public List<Biz> selectFbizByParams(Criteria example) {
        return this.businesstypeMapper.selectFbizByParams(example);
    }
    
    public List<Biz> selectByParams2(Criteria example) {
        return this.businesstypeMapper.selectByParams2(example);
    }

    public int deleteByPrimaryKey(Long bizSid) {
        return this.businesstypeMapper.deleteByPrimaryKey(bizSid);
    }

    public int updateByPrimaryKeySelective(Biz record) {
        return this.businesstypeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Biz record) {
        return this.businesstypeMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.businesstypeMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Biz record, Criteria example) {
        return this.businesstypeMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Biz record, Criteria example) {
        return this.businesstypeMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Biz record) {
        return this.businesstypeMapper.insert(record);
    }

    public int insertSelective(Biz record) {
        return this.businesstypeMapper.insertSelective(record);
    }

	@Override
	public List<Biz> selectForOrgbiz(Long bizSid) {
		return this.businesstypeMapper.selectForOrgbiz(bizSid);
	}

	@Override
	public List<Biz> selectForQuota(Long bizSid) {
		return this.businesstypeMapper.selectForQuota(bizSid);
	}
}