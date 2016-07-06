package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.product.dao.ServiceInstResMapper;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstResKey;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0.0")
@Component
public class ServiceInstResServiceImpl implements ServiceInstResService {
    @Autowired
    private ServiceInstResMapper serviceInstResMapper;

    private static final Logger logger = LoggerFactory.getLogger(ServiceInstResServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.serviceInstResMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ServiceInstRes selectByPrimaryKey(ServiceInstResKey key) {
        return this.serviceInstResMapper.selectByPrimaryKey(key);
    }

    public List<ServiceInstRes> selectByParams(Criteria example) {
        return this.serviceInstResMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(ServiceInstResKey key) {
        return this.serviceInstResMapper.deleteByPrimaryKey(key);
    }

    public int updateByPrimaryKeySelective(ServiceInstRes record) {
        return this.serviceInstResMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ServiceInstRes record) {
        return this.serviceInstResMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.serviceInstResMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(ServiceInstRes record, Criteria example) {
        return this.serviceInstResMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(ServiceInstRes record, Criteria example) {
        return this.serviceInstResMapper.updateByParams(record, example.getCondition());
    }

    public int insert(ServiceInstRes record) {
        return this.serviceInstResMapper.insert(record);
    }

    public int insertSelective(ServiceInstRes record) {
        return this.serviceInstResMapper.insertSelective(record);
    }

    public List<ServiceInstRes> selectInstanceReses(Long instanceSid) {
    	return this.serviceInstResMapper.selectInstanceReses(instanceSid);
    }
    
    public String getResSidByInstanceSid(Long instanceSid) {
		Criteria criteria = new Criteria();
		criteria.put("instanceSid", instanceSid);
		List<ServiceInstRes> serviceInstReses = serviceInstResMapper.selectByParams(criteria);
		String resSid = null;
		if(serviceInstReses.size() > 0) {
			resSid = serviceInstReses.get(0).getResId();
		}
		if(resSid == null) {
			throw new RuntimeException("The instanceSid no mapping in service_inst_res table instanceSid=" + instanceSid);
		}
		return resSid;
    }

    public Long getInstanceSidByResSid(String resSid) {
        Long instanceSid = null;
        Criteria criteria = new Criteria();
        criteria.put("resId", resSid);
        List<ServiceInstRes> serviceInstReses = serviceInstResMapper.selectByParams(criteria);
        if(serviceInstReses.size() > 0) {
            instanceSid = serviceInstReses.get(0).getInstanceSid();
        }
        if(instanceSid == null) {
            throw new RuntimeException("The resSid no mapping in service_inst_res table resSid=" + resSid);
        }
        return instanceSid;
    }
}