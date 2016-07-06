package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.product.dao.ServiceRelationMapper;
import com.h3c.idcloud.core.pojo.dto.product.ServiceRelation;
import com.h3c.idcloud.core.service.product.api.ServiceRelationService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0.0")
@Component
public class ServiceRelationServiceImpl implements ServiceRelationService {
    @Autowired
    private ServiceRelationMapper serviceRelationMapper;

    private static final Logger logger = LoggerFactory.getLogger(ServiceRelationServiceImpl.class);
    
    @Override
    public int countByParams(Criteria example) {
        int count = this.serviceRelationMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

	@Override
	public List<ServiceRelation> selectByParams(Criteria example) {
		return this.serviceRelationMapper.selectByParams(example);
	}

	@Override
	public int updateByPrimaryKey(ServiceRelation record) {
		return this.serviceRelationMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteByParams(Criteria example) {
		return this.serviceRelationMapper.deleteByParams(example);
	}

	@Override
	public int insertSelective(ServiceRelation record) {
		return this.serviceRelationMapper.insertSelective(record);
	}
    
}