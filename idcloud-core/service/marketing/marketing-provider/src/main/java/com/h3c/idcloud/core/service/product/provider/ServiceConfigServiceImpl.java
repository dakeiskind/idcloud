package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.product.dao.ServiceConfigMapper;
import com.h3c.idcloud.core.pojo.dto.product.ServiceConfig;
import com.h3c.idcloud.core.service.product.api.ServiceConfigService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0.0")
@Component
public class ServiceConfigServiceImpl implements ServiceConfigService {
	@Autowired
	private ServiceConfigMapper serviceConfigMapper;

	private static final Logger logger = LoggerFactory
			.getLogger(ServiceConfigServiceImpl.class);

	public int countByParams(Criteria example) {
		int count = this.serviceConfigMapper.countByParams(example);
		logger.debug("count: {}", count);
		return count;
	}

	public ServiceConfig selectByPrimaryKey(Long configSid) {
		return this.serviceConfigMapper.selectByPrimaryKey(configSid);
	}

	public List<ServiceConfig> selectByParams(Criteria example) {
		return this.serviceConfigMapper.selectByParams(example);
	}

	public int deleteByPrimaryKey(Long configSid) {
		return this.serviceConfigMapper.deleteByPrimaryKey(configSid);
	}

	public int updateByPrimaryKeySelective(ServiceConfig record) {
		return this.serviceConfigMapper.updateByPrimaryKeySelective(record);
	}

	public int insertSelective(ServiceConfig record) {
		return this.serviceConfigMapper.insertSelective(record);
	}

	@Override
	public List<ServiceConfig> selectByServiceSid(Long serviceSid) {
		return this.serviceConfigMapper.selectByServiceSid(serviceSid);
	}

	public String selectActivitiFlowByServiceSid(Long serviceSid,
			String activitiName) {
		String activitiFlow = "";
		// 取得服务配置信息
		List<ServiceConfig> configs = serviceConfigMapper
				.selectByServiceSid(serviceSid);

		for (ServiceConfig serviceConfig : configs) {
			if (serviceConfig.getName().equals(activitiName)) {
				activitiFlow = serviceConfig.getValue();
			}
		}
		return activitiFlow;
	}
}