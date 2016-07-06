package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.product.dao.ServiceInstanceChangeLogMapper;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceChangeLog;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceChangeLogSpec;
import com.h3c.idcloud.core.pojo.instance.ResVmInst;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceChangeLogService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service(version = "1.0.0")
@Component
public class ServiceInstanceChangeLogServiceImpl implements ServiceInstanceChangeLogService {
    @Autowired
    private ServiceInstanceChangeLogMapper serviceInstanceChangeLogMapper;

//    @Autowired
//	private ResVmService resVmService;

    @Autowired
    private ServiceInstResService serviceInstResService;

    private static final Logger logger = LoggerFactory.getLogger(ServiceInstanceChangeLogServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.serviceInstanceChangeLogMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ServiceInstanceChangeLog selectByPrimaryKey(Long changeLogSid) {
        return this.serviceInstanceChangeLogMapper.selectByPrimaryKey(changeLogSid);
    }

    public List<ServiceInstanceChangeLog> selectByParams(Criteria example) {
        return this.serviceInstanceChangeLogMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long changeLogSid) {
        return this.serviceInstanceChangeLogMapper.deleteByPrimaryKey(changeLogSid);
    }

    public int updateByPrimaryKeySelective(ServiceInstanceChangeLog record) {
        return this.serviceInstanceChangeLogMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ServiceInstanceChangeLog record) {
        return this.serviceInstanceChangeLogMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.serviceInstanceChangeLogMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(ServiceInstanceChangeLog record, Criteria example) {
        return this.serviceInstanceChangeLogMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(ServiceInstanceChangeLog record, Criteria example) {
        return this.serviceInstanceChangeLogMapper.updateByParams(record, example.getCondition());
    }

    public int insert(ServiceInstanceChangeLog record) {
        return this.serviceInstanceChangeLogMapper.insert(record);
    }

    public int insertSelective(ServiceInstanceChangeLog record) {
        return this.serviceInstanceChangeLogMapper.insertSelective(record);
    }

    public List<ServiceInstanceChangeLog> selectLastChangeLog(Long instanceSid) {
    	 return this.serviceInstanceChangeLogMapper.selectLastChangeLog(instanceSid);
    }

	public ServiceInstanceChangeLog getLastChangeLog(Long instanceSid) {
		List<ServiceInstanceChangeLog> changeLogs =  this.selectLastChangeLog(instanceSid);
		if(changeLogs.size() > 1) {
			throw new RuntimeException("The instance change log number is incorrect. changeLog.size=" + changeLogs.size());
		} else if(changeLogs.size() == 0) {
			return null;
		}
		return changeLogs.get(0);
	}

	@Override
	@Transactional
	public void submitChangeServiceInstance(ServiceInstance instance, String changeType,
			Object changeInfo) {
		Long intanceSid = instance.getInstanceSid();
		ServiceInstanceChangeLog instanceChangeLog = new ServiceInstanceChangeLog();
		instanceChangeLog.setInstanceSid(intanceSid);
		instanceChangeLog.setChangeSponsor(AuthUtil.getAuthUser().getAccount());
		instanceChangeLog.setChangeType(changeType);
		instanceChangeLog.setChangeBeginTime(new Date());
		if(changeInfo != null) {
			instanceChangeLog.setChangeSpec(JsonUtil.toJson(changeInfo));
		}
		//如果是变更和退订操作
		if(WebConstants.instanceChangeType.CHANGE.equals(changeType) || WebConstants.instanceChangeType.UNSUBSCRIBE.equals(changeType)) {
			Object curInfo = this.getServiceCurInfo(instance);
			if(curInfo != null) {
				instanceChangeLog.setChangePreSpec(JsonUtil.toJson(curInfo));
			}
		}
		instanceChangeLog.setStatus(WebConstants.instanceChangeStatus.UNCHANGE);
		WebUtil.prepareInsertParams(instanceChangeLog);
		this.insertSelective(instanceChangeLog);
	}

	@Override
	public void beginChangeServiceInstance(Long instanceSid) {
		ServiceInstanceChangeLog instanceChangeLog = this.getLastChangeLog(instanceSid);
		if(instanceChangeLog != null) {
			instanceChangeLog.setStatus(WebConstants.instanceChangeStatus.CHANGEING);
//			WebUtil.prepareUpdateParams(instanceChangeLog);
			this.updateByPrimaryKeySelective(instanceChangeLog);
		}
	}

	@Override
	@Transactional
	public <T> void updateChangeServiceInstance(Long instanceSid, String changeType,
												ServiceInstanceChangeLogSpec<?> changeInfo, TypeReference<T> type) {
		try {
			ServiceInstanceChangeLog instanceChangeLog = this.getLastChangeLog(instanceSid);
			if(instanceChangeLog == null) {
				logger.error("Can't find serivce instance change Log record.");
				return;
			}
			if(!changeType.equals(instanceChangeLog.getChangeType())) {
				logger.warn("The service instance is not completed the " + instanceChangeLog.getChangeType() + " change.");
				return;
			}
			if(WebConstants.instanceChangeStatus.CHANGED.equals(instanceChangeLog.getStatus())) {
				logger.error("The serivce instance is completed open or change. instanceSid=" + instanceSid);
				return;
			}
			ServiceInstanceChangeLogSpec<Object> orginalSpec = JsonUtil.fromJson(instanceChangeLog.getChangeSpec(), type);
			Object orginalObject = orginalSpec.getParams();
			Object changeObject = changeInfo.getParams();
			if(orginalObject != null && changeObject != null) {
				if(orginalObject instanceof ResVmInst && changeObject instanceof ResVmInst) {
					ResVmInst openOrginalParams = (ResVmInst)orginalObject;
					ResVmInst openChangeParams = (ResVmInst)changeObject;
					if(openChangeParams.getAllocateResHostIds() != null || openChangeParams.getAllocateResVcIds() != null) {
						openOrginalParams.setAllocateResHostIds(null);
						openOrginalParams.setAllocateResVcIds(null);
					}
				}
				//WebUtil.mergeObject(orginalObject, changeObject);
			} else {
				orginalSpec.setParams(changeObject);
			}
			if(orginalSpec.getVariables() != null && changeInfo.getVariables() != null)  {
				orginalSpec.getVariables().putAll(changeInfo.getVariables());
			} else {
				orginalSpec.setVariables(changeInfo.getVariables());
			}
			instanceChangeLog.setChangeSpec(JsonUtil.toJson(orginalSpec));
			//WebUtil.prepareInsertParams(instanceChangeLog);
			this.updateByPrimaryKeySelective(instanceChangeLog);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void endChangeServiceInstance(ServiceInstance instance, String changeType) {
		Long instanceSid = instance.getInstanceSid();
		ServiceInstanceChangeLog instanceChangeLog = this.getLastChangeLog(instanceSid);
		if(instanceChangeLog != null) {
			instanceChangeLog.setChangeEndTime(new Date());
			if(WebConstants.instanceChangeType.CHANGE.equals(changeType) || WebConstants.instanceChangeType.CREATE.equals(changeType)) {
				Object serviceInfo = this.getServiceCurInfo(instance);
				if(serviceInfo != null) {
					instanceChangeLog.setChangeCurSpec(JsonUtil.toJson(serviceInfo));
				}
			}
			instanceChangeLog.setStatus(WebConstants.instanceChangeStatus.CHANGED);
//			WebUtil.prepareUpdateParams(instanceChangeLog);
			this.updateByPrimaryKeySelective(instanceChangeLog);
		}
	}

	public Object getServiceCurInfo(ServiceInstance instance) {
		String resSid = this.serviceInstResService.getResSidByInstanceSid(instance.getInstanceSid());
		Object curInfo = null;
		if(WebConstants.ServiceSid.SERVICE_VM.equals(instance.getServiceSid())) {
//			curInfo = this.resVmService.getVmInfo(resSid);---------wsl
		} else if(WebConstants.ServiceSid.SERVICE_STORAGE.equals(instance.getServiceSid())) {

		} else if(WebConstants.ServiceSid.SERVICE_OBJ_STORAGE.equals(instance.getServiceSid())) {

		} else if(WebConstants.ServiceSid.FLOATING_IP.equals(instance.getServiceSid())) {
			
		}
		if(curInfo == null) {
//			throw new RuntimeException("Can't find service info. resSid=" + resSid);
			logger.warn("Can't find service info. resSid=" + resSid);
		}
		return curInfo;
	}

	@Override
	public <T> T getChangeInfo(Long instanceSid, TypeReference<T> type) {
		ServiceInstanceChangeLog changeLogs = this.getLastChangeLog(instanceSid);
		T result = null;
		try {
			result =  JsonUtil.fromJson(changeLogs.getChangeSpec(), type);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

}