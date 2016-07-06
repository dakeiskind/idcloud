package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.product.dao.ServiceTemplateSpecMapper;
import com.h3c.idcloud.core.pojo.dto.product.ServiceTemplateSpec;
import com.h3c.idcloud.core.service.product.api.ServiceTemplateSpecService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0.0")
@Component
public class ServiceTemplateSpecServiceImpl implements ServiceTemplateSpecService {
    @Autowired
    private ServiceTemplateSpecMapper serviceTemplateSpecMapper;

    private static final Logger logger = LoggerFactory.getLogger(ServiceTemplateSpecServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.serviceTemplateSpecMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ServiceTemplateSpec selectByPrimaryKey(Long specSid) {
        return this.serviceTemplateSpecMapper.selectByPrimaryKey(specSid);
    }

    public List<ServiceTemplateSpec> selectByParams(Criteria example) {
        return this.serviceTemplateSpecMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long specSid) {
        return this.serviceTemplateSpecMapper.deleteByPrimaryKey(specSid);
    }

    public int updateByPrimaryKeySelective(ServiceTemplateSpec record) {
        return this.serviceTemplateSpecMapper.updateByPrimaryKeySelective(record);
    }

    public int insertSelective(ServiceTemplateSpec record) {
        return this.serviceTemplateSpecMapper.insertSelective(record);
    }

	@Override
	public List<ServiceTemplateSpec> selectByTemplateSid(Long tempSid) {
		return this.serviceTemplateSpecMapper.selectByTemplateSid(tempSid);
	}

	@Override
	public int deleteByTemplateSid(Long tempSid) {
		return this.serviceTemplateSpecMapper.deleteByTemplateSid(tempSid);
	}
}