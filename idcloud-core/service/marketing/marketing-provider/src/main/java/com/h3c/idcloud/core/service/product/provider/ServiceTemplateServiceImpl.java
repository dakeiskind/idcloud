package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.product.dao.ServiceTemplateMapper;
import com.h3c.idcloud.core.persist.product.dao.ServiceTemplateSpecMapper;
import com.h3c.idcloud.core.pojo.dto.product.ServiceSpec;
import com.h3c.idcloud.core.pojo.dto.product.ServiceTemplate;
import com.h3c.idcloud.core.pojo.dto.product.ServiceTemplateSpec;
import com.h3c.idcloud.core.service.product.api.ServiceTemplateService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0.0")
@Component
public class ServiceTemplateServiceImpl implements ServiceTemplateService {
    @Autowired
    private ServiceTemplateMapper serviceTemplateMapper;
    @Autowired
    private ServiceTemplateSpecMapper serviceTemplateSpecMapper;

    private static final Logger logger = LoggerFactory.getLogger(ServiceTemplateServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.serviceTemplateMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ServiceTemplate selectByPrimaryKey(Long templateSid) {
        return this.serviceTemplateMapper.selectByPrimaryKey(templateSid);
    }

    public List<ServiceTemplate> selectByParams(Criteria example) {
        return this.serviceTemplateMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long templateSid) {
        return this.serviceTemplateMapper.deleteByPrimaryKey(templateSid);
    }

    public int updateByPrimaryKeySelective(ServiceTemplate record) {
        return this.serviceTemplateMapper.updateByPrimaryKeySelective(record);
    }

    public int insertSelective(ServiceTemplate record) {
        return this.serviceTemplateMapper.insertSelective(record);
    }

	@Override
	public List<ServiceTemplate> selectByServiceSid(Long serviceSid) {
		return this.serviceTemplateMapper.selectByServiceSid(serviceSid);
	}
	
	@Override
	public boolean insertPlatformServiceTemp(ServiceTemplate template) {
		boolean result = false;
		try {
			// 补全用户信息
			WebUtil.prepareInsertParams(template);
			
			// 插入数据库
			int insertCount = this.serviceTemplateMapper.insertSelective(template);
			
			for (ServiceSpec serviceSpec : template.getServiceSpecs()) {
				ServiceTemplateSpec tempSpec = new ServiceTemplateSpec();
				
				WebUtil.prepareInsertParams(tempSpec);

				tempSpec.setTemplateSid(template.getTemplateSid());
				tempSpec.setName(serviceSpec.getName());
				tempSpec.setDescription(serviceSpec.getDescription());
				tempSpec.setValue(serviceSpec.getValue());
				tempSpec.setUnit(serviceSpec.getUnit());
				tempSpec.setValueDomain(serviceSpec.getValueDomain());
				serviceTemplateSpecMapper.insertSelective(tempSpec);
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
	public boolean updatePlatformServiceTemp(ServiceTemplate template) {
		boolean result = false;
		try {
			// 补全用户信息
//			WebUtil.prepareUpdateParams(template);--------------wsl
			
			// 插入数据库
			int updateCount = this.serviceTemplateMapper.updateByPrimaryKeySelective(template);
			
			//删除模板，更新新模板
			Criteria criteria = new Criteria();
			criteria.put("templateSid", template.getTemplateSid());
			this.serviceTemplateSpecMapper.deleteByTemplateSid(template.getTemplateSid());
			
			for (ServiceSpec serviceSpec : template.getServiceSpecs()) {
				ServiceTemplateSpec tempSpec = new ServiceTemplateSpec();
//				WebUtil.prepareInsertParams(tempSpec);-----------------wsl
				tempSpec.setTemplateSid(template.getTemplateSid());
				tempSpec.setName(serviceSpec.getName());
				tempSpec.setDescription(serviceSpec.getDescription());
				tempSpec.setValue(serviceSpec.getValue());
				tempSpec.setUnit(serviceSpec.getUnit());
				tempSpec.setValueDomain(serviceSpec.getValueDomain());

				serviceTemplateSpecMapper.insertSelective(tempSpec);
			}
			
			if (updateCount == 1) {
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
	
}