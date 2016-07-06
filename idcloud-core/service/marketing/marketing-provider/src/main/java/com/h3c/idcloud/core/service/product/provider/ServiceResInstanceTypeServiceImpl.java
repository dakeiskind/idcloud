package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.product.dao.ServiceResInstanceTypeMapper;
import com.h3c.idcloud.core.pojo.dto.product.ServiceResInstanceType;
import com.h3c.idcloud.core.service.product.api.ServiceResInstanceTypeService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0.0")
@Component
public class ServiceResInstanceTypeServiceImpl implements ServiceResInstanceTypeService {
    @Autowired
    private ServiceResInstanceTypeMapper serviceResInstanceTypeMapper;

    private static final Logger logger = LoggerFactory.getLogger(ServiceResInstanceTypeServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.serviceResInstanceTypeMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }


    public List<ServiceResInstanceType> selectByParams(Criteria example) {
        return this.serviceResInstanceTypeMapper.selectByParams(example);
    }


    public int deleteByParams(Criteria example) {
        return this.serviceResInstanceTypeMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(ServiceResInstanceType record, Criteria example) {
        return this.serviceResInstanceTypeMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(ServiceResInstanceType record, Criteria example) {
        return this.serviceResInstanceTypeMapper.updateByParams(record, example.getCondition());
    }

    public int insert(ServiceResInstanceType record) {
        return this.serviceResInstanceTypeMapper.insert(record);
    }

    public int insertSelective(ServiceResInstanceType record) {
        return this.serviceResInstanceTypeMapper.insertSelective(record);
    }
}