package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.product.dao.ServiceConfigMapper;
import com.h3c.idcloud.core.persist.product.dao.ServiceDefineMapper;
import com.h3c.idcloud.core.persist.product.dao.ServiceOperationMapper;
import com.h3c.idcloud.core.persist.product.dao.ServicePerfMapper;
import com.h3c.idcloud.core.persist.product.dao.ServiceSpecMapper;
import com.h3c.idcloud.core.pojo.dto.product.ServiceConfig;
import com.h3c.idcloud.core.pojo.dto.product.ServiceDefine;
import com.h3c.idcloud.core.pojo.dto.product.ServiceOperation;
import com.h3c.idcloud.core.pojo.dto.product.ServicePerf;
import com.h3c.idcloud.core.pojo.dto.product.ServiceRelation;
import com.h3c.idcloud.core.pojo.dto.product.ServiceSpec;
import com.h3c.idcloud.core.pojo.vo.common.CommonServiceCode;
import com.h3c.idcloud.core.service.product.api.ServiceDefineService;
import com.h3c.idcloud.core.service.product.api.ServiceRelationService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2016/2/19.
 */
@Service(version = "1.0.0")
@Component
public class ServiceDefineServiceImpl implements ServiceDefineService {
    @Autowired
    private ServiceDefineMapper serviceDefineMapper;
    @Autowired
    private ServiceConfigMapper serviceConfigMapper;
    @Autowired
    private ServiceSpecMapper serviceSpecMapper;
    @Autowired
    private ServicePerfMapper servicePerfMapper;
    @Autowired
    private ServiceOperationMapper serviceOperationMapper;
    @Autowired
    private ServiceRelationService serviceRelationService;

    private static final Logger logger = LoggerFactory.getLogger(ServiceDefineServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.serviceDefineMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ServiceDefine selectByPrimaryKey(Long serviceSid) {
        return this.serviceDefineMapper.selectByPrimaryKey(serviceSid);
    }

    public List<ServiceDefine> selectByParams(Criteria example) {
        return this.serviceDefineMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long serviceSid) {
        return this.serviceDefineMapper.deleteByPrimaryKey(serviceSid);
    }

    public int updateByPrimaryKeySelective(ServiceDefine record) {
        return this.serviceDefineMapper.updateByPrimaryKeySelective(record);
    }

    public int insertSelective(ServiceDefine record) {
        return this.serviceDefineMapper.insertSelective(record);
    }

//    @Override
    public List<ServiceDefine> selectServiceByCatalogSidList(Criteria example){
        return this.serviceDefineMapper.selectServiceByCatalogSidList(example);
    }

//    @Override
    public boolean insertPlatformService(ServiceDefine service) {
        boolean result = false;
        try {

            // 插入服务数据库
            int insertCount = this.serviceDefineMapper.insertSelective(service);

            // 插入服务配置项
            for (ServiceConfig serviceConfig : service.getServiceConfig()) {
                WebUtil.prepareInsertParams(serviceConfig);
                serviceConfig.setServiceSid(service.getServiceSid());
                serviceConfigMapper.insertSelective(serviceConfig);
            }

            // 插入服务规格项
            for (ServiceSpec serviceSpec : service.getServiceSpec()) {
                WebUtil.prepareInsertParams(serviceSpec);
                serviceSpec.setServiceSid(service.getServiceSid());
                serviceSpecMapper.insertSelective(serviceSpec);
            }

            // 插入服务性能指标
            for (ServicePerf servicePerf : service.getServicePerf()) {
                WebUtil.prepareInsertParams(servicePerf);
                servicePerf.setServiceSid(service.getServiceSid());
                servicePerfMapper.insertSelective(servicePerf);
            }

            // 插入服务操作项
            for (ServiceOperation serviceOperation : service.getServiceOperation()) {
                WebUtil.prepareInsertParams(serviceOperation);
                serviceOperation.setServiceSid(service.getServiceSid());
                serviceOperationMapper.insertSelective(serviceOperation);
            }

            // 如果该服务是复合服务，插入复合服务与原子服务关联表
            if("02".equals(service.getServiceType())){
                String[] str = service.getRelationService().split(",");
                if(str != null && str.length > 0){
                    for(int i=0;i<str.length;i++){
                        ServiceRelation sr = new ServiceRelation();
                        sr.setCompoundServiceSid(service.getServiceSid());
                        sr.setAtomServiceSid(Long.parseLong(str[i]));
                        this.serviceRelationService.insertSelective(sr);
                    }
                }
            }

            if (insertCount == 1) {
                result = true;

            } else {
                result = false;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = false;
        }
        return result;

    }

//    @Override
    public boolean updatePlatformService(ServiceDefine service) {
        boolean result = false;
        try {
            // 补全服务信息
            WebUtil.prepareUpdateParams(service);

            // 插入数据库
            int insertCount = this.serviceDefineMapper.updateByPrimaryKeySelective(service);

            for (ServiceConfig serviceConfig : service.getServiceConfig()) {

                WebUtil.prepareUpdateParams(serviceConfig);
                //serviceConfig.setServiceSid(service.getServiceSid());
                serviceConfigMapper.updateByPrimaryKeySelective(serviceConfig);
            }

            for (ServiceSpec serviceSpec : service.getServiceSpec()) {

                WebUtil.prepareUpdateParams(serviceSpec);
//				serviceSpec.setServiceSid(service.getServiceSid());
                serviceSpecMapper.updateByPrimaryKeySelective(serviceSpec);
            }

            for (ServicePerf servicePerf : service.getServicePerf()) {

                WebUtil.prepareUpdateParams(servicePerf);
//				servicePerf.setServiceSid(service.getServiceSid());

                servicePerfMapper.updateByPrimaryKeySelective(servicePerf);
            }

            for (ServiceOperation serviceOperation : service.getServiceOperation()) {

                WebUtil.prepareUpdateParams(serviceOperation);
//				serviceOperation.setServiceSid(service.getServiceSid());

                serviceOperationMapper.updateByPrimaryKeySelective(serviceOperation);
            }

            if (insertCount == 1) {
                result = true;

            } else {
                result = false;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = false;
        }
        return result;

    }

    @Override
    public List<CommonServiceCode> findCommonServiceCodes(Criteria example) {
        return this.serviceDefineMapper.findCommonServiceCodes(example);
    }
}
