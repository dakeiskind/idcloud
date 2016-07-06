package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.product.dao.ServiceOperationMapper;
import com.h3c.idcloud.core.pojo.dto.product.ServiceOperation;
import com.h3c.idcloud.core.service.product.api.ServiceOperationService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0.0")
@Component
public class ServiceOperationServiceImpl implements ServiceOperationService {
    @Autowired
    private ServiceOperationMapper serviceOperationMapper;

    private static final Logger logger = LoggerFactory.getLogger(ServiceOperationServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.serviceOperationMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ServiceOperation selectByPrimaryKey(Long operationSid) {
        return this.serviceOperationMapper.selectByPrimaryKey(operationSid);
    }

    public List<ServiceOperation> selectByParams(Criteria example) {
        return this.serviceOperationMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long operationSid) {
        return this.serviceOperationMapper.deleteByPrimaryKey(operationSid);
    }

    public int updateByPrimaryKeySelective(ServiceOperation record) {
        return this.serviceOperationMapper.updateByPrimaryKeySelective(record);
    }

    public int insertSelective(ServiceOperation record) {
        return this.serviceOperationMapper.insertSelective(record);
    }

	@Override
	public List<ServiceOperation> selectByServiceSid(Long serviceSid) {
		return this.serviceOperationMapper.selectByServiceSid(serviceSid);
	}
}