package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.product.dao.ServiceChangeLogMapper;
import com.h3c.idcloud.core.pojo.dto.product.ServiceChangeLog;
import com.h3c.idcloud.core.service.product.api.ServiceChangeLogService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0.0")
@Component
public class ServiceChangeLogServiceImpl implements ServiceChangeLogService {
    @Autowired
    private ServiceChangeLogMapper serviceChangeLogMapper;

    private static final Logger logger = LoggerFactory.getLogger(ServiceChangeLogServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.serviceChangeLogMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ServiceChangeLog selectByPrimaryKey(Long cmSid) {
        return this.serviceChangeLogMapper.selectByPrimaryKey(cmSid);
    }

    public List<ServiceChangeLog> selectByParams(Criteria example) {
        return this.serviceChangeLogMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long cmSid) {
        return this.serviceChangeLogMapper.deleteByPrimaryKey(cmSid);
    }

    public int updateByPrimaryKeySelective(ServiceChangeLog record) {
        return this.serviceChangeLogMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ServiceChangeLog record) {
        return this.serviceChangeLogMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.serviceChangeLogMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(ServiceChangeLog record, Criteria example) {
        return this.serviceChangeLogMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(ServiceChangeLog record, Criteria example) {
        return this.serviceChangeLogMapper.updateByParams(record, example.getCondition());
    }

    public int insert(ServiceChangeLog record) {
        return this.serviceChangeLogMapper.insert(record);
    }

    public int insertSelective(ServiceChangeLog record) {
        return this.serviceChangeLogMapper.insertSelective(record);
    }

	@Override
	public List<ServiceChangeLog> selectByParams2(Criteria example) {
		return this.serviceChangeLogMapper.selectByParams2(example);
	}
}