package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.product.dao.ServiceSpecMapper;
import com.h3c.idcloud.core.pojo.dto.product.ServiceSpec;
import com.h3c.idcloud.core.service.product.api.ServiceSpecService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0.0")
@Component
public class ServiceSpecServiceImpl implements ServiceSpecService {
    @Autowired
    private ServiceSpecMapper serviceSpecMapper;

    private static final Logger logger = LoggerFactory.getLogger(ServiceSpecServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.serviceSpecMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ServiceSpec selectByPrimaryKey(Long specSid) {
        return this.serviceSpecMapper.selectByPrimaryKey(specSid);
    }

    public List<ServiceSpec> selectByParams(Criteria example) {
        return this.serviceSpecMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long specSid) {
        return this.serviceSpecMapper.deleteByPrimaryKey(specSid);
    }

    public int updateByPrimaryKeySelective(ServiceSpec record) {
        return this.serviceSpecMapper.updateByPrimaryKeySelective(record);
    }

    public int insertSelective(ServiceSpec record) {
        return this.serviceSpecMapper.insertSelective(record);
    }

	@Override
	public List<ServiceSpec> selectByServiceSid(Long serviceSid) {
		
		return this.serviceSpecMapper.selectByServiceSid(serviceSid);
	}

	@Override
	public List<ServiceSpec> selectTemplateSpec(Criteria example) {
		return this.serviceSpecMapper.selectTemplateSpec(example);
	}
}