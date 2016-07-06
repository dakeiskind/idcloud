package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.product.dao.ServicePerfMapper;
import com.h3c.idcloud.core.pojo.dto.product.ServicePerf;
import com.h3c.idcloud.core.service.product.api.ServicePerfService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0.0")
@Component
public class ServicePerfServiceImpl implements ServicePerfService {
    @Autowired
    private ServicePerfMapper servicePerfMapper;

    private static final Logger logger = LoggerFactory.getLogger(ServicePerfServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.servicePerfMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ServicePerf selectByPrimaryKey(Long perfSid) {
        return this.servicePerfMapper.selectByPrimaryKey(perfSid);
    }

    public List<ServicePerf> selectByParams(Criteria example) {
        return this.servicePerfMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long perfSid) {
        return this.servicePerfMapper.deleteByPrimaryKey(perfSid);
    }

    public int updateByPrimaryKeySelective(ServicePerf record) {
        return this.servicePerfMapper.updateByPrimaryKeySelective(record);
    }

    public int insertSelective(ServicePerf record) {
        return this.servicePerfMapper.insertSelective(record);
    }

	@Override
	public List<ServicePerf> selectByServiceSid(Long serviceSid) {
		return this.servicePerfMapper.selectByServiceSid(serviceSid);
	}
}