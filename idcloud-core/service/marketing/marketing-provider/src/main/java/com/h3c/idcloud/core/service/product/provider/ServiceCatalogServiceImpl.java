package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.product.dao.ServiceCatalogMapper;
import com.h3c.idcloud.core.pojo.dto.product.ServiceCatalog;
import com.h3c.idcloud.core.service.product.api.ServiceCatalogService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0.0")
@Component
public class ServiceCatalogServiceImpl implements ServiceCatalogService {
    @Autowired
    private ServiceCatalogMapper serviceCatalogMapper;

    private static final Logger logger = LoggerFactory.getLogger(ServiceCatalogServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.serviceCatalogMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ServiceCatalog selectByPrimaryKey(Long catalogSid) {
        return this.serviceCatalogMapper.selectByPrimaryKey(catalogSid);
    }

    public List<ServiceCatalog> selectByParams(Criteria example) {
        return this.serviceCatalogMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long catalogSid) {
        return this.serviceCatalogMapper.deleteByPrimaryKey(catalogSid);
    }

    public int updateByPrimaryKeySelective(ServiceCatalog record) {
        return this.serviceCatalogMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ServiceCatalog record) {
        return this.serviceCatalogMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.serviceCatalogMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(ServiceCatalog record, Criteria example) {
        return this.serviceCatalogMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(ServiceCatalog record, Criteria example) {
        return this.serviceCatalogMapper.updateByParams(record, example.getCondition());
    }

    public int insert(ServiceCatalog record) {
        return this.serviceCatalogMapper.insert(record);
    }

    public int insertSelective(ServiceCatalog record) {
        return this.serviceCatalogMapper.insertSelective(record);
    }
}