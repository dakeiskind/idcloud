package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceChangeLog;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.system.SysTLogRecord;
import com.h3c.idcloud.core.pojo.dto.system.UserMgtObjKey;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.service.product.api.ResourceRequestHanlder;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceChangeLogService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.system.api.MgtObjService;
import com.h3c.idcloud.core.service.system.api.UserMgtObjService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 其他变更申请回调处理
 * 
 * @author LiufuJun
 *
 */
//@Service("otherChangeHandlerImpl")
@Service(version = "1.0.0",group = "otherChangeHandlerImpl")
@Component("otherChangeHandlerImpl")
public class OtherChangeHandlerImpl implements ResourceRequestHanlder {

	private static final Logger logger = LoggerFactory
			.getLogger(OtherChangeHandlerImpl.class);

	/** 资源申请接口Service */
	@Reference(version = "1.0.0")
	private MgtObjService mgtObjService;

	@Reference(version = "1.0.0")
	private UserMgtObjService userMgtObjService;

	@Autowired
	private ServiceInstanceService instanceService;

//	@Autowired
//	private SysLoggerFactory sysLogger;

	@Autowired
	private ServiceInstanceChangeLogService instanceChangeLogService;

	/*@Autowired
	private TicketMgt ticketMgt;*/

	@Override
	public boolean invoke(Long processObjectId) {
		//系统日志
		SysTLogRecord record = new SysTLogRecord();
//		record.setAccount(AuthUtil.getAuthUser().getAccount());
		record.setActLevel("01");		
		record.setActTarget("其他类型变更");
		record.setActName("其他类型变更");
		record.setActStartDate(new Date());
		record.setActEndDate(new Date());
		Boolean result = false;
		try {
			List<ServiceInstanceChangeLog> changeLogs = this.instanceChangeLogService.selectLastChangeLog(processObjectId);
			ServiceInstanceChangeLog changeLog = changeLogs.get(0);
			if(WebConstants.instanceChangeType.CHANGE_MANAGER.equals(changeLog.getChangeType())) {
				try {
					MgtObj mgtObj = mgtObjService.selectByPrimaryKey(processObjectId);
					mgtObj.setStatus(WebConstants.MGT_OBJ_STATUS.NORMAL);
					mgtObjService.updateByPrimaryKeySelective(mgtObj);
					
					//获取变更内容
					String changeSpec = changeLog.getChangeSpec();
					Map<String,String> specMap = JsonUtil.fromJson(changeSpec, Map.class);
					
					//删除原来的项目经理关系
					Criteria example = new Criteria();
					example.put("mgtObjSid", processObjectId);
					example.put("userSid", specMap.get("oldUserSid"));
					userMgtObjService.deleteByParams(example);
					//添加新的项目经理关系
					UserMgtObjKey userMgtObj = new UserMgtObjKey();
					userMgtObj.setMgtObjSid(processObjectId);
					userMgtObj.setUserSid(Long.parseLong(specMap.get("newUserSid")));
					userMgtObjService.insertSelective(userMgtObj);
					
					//更新changeLog的状态和 结束时间 完成本次变更
					changeLog.setChangeEndTime( new Date());
					changeLog.setStatus(WebConstants.instanceChangeStatus.CHANGED);
					
					instanceChangeLogService.updateByPrimaryKeySelective(changeLog);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(WebConstants.instanceChangeType.CHANGE_MGT_OBJ.equals(changeLog.getChangeType())) {
				try {
					ServiceInstance instance = instanceService.selectByPrimaryKey(processObjectId);
					//获取变更内容
					String changeSpec = changeLog.getChangeSpec();
					Map<String,String> specMap = JsonUtil.fromJson(changeSpec, Map.class);
					
					instance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
					instance.setMgtObjSid(Long.parseLong(specMap.get("newMgtObjSid")));
					instanceService.updateByPrimaryKeySelective(instance);
					
					//更新changeLog的状态和 结束时间 完成本次变更
					changeLog.setChangeEndTime( new Date());
					changeLog.setStatus(WebConstants.instanceChangeStatus.CHANGED);
					
					instanceChangeLogService.updateByPrimaryKeySelective(changeLog);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			record.setActResult("02");
			result = true;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			record.setActResult("01");
			result = false;
		} finally {
			record.setActDetail("项目管理:项目经理变更");
//			SysLogger log = sysLogger.getLogger(LoggerTypeEnum.DB);
//			log.debug(record);
		}
		return result;
	}

	@Override
	public boolean invoke(Map<String, Object> params) {
		return false;
	}

	@Override
	public boolean invoke(List<Long> instanceSids) {
		return false;
	}

	@Override
	public void operate(ResInstResult result) {

	}

	@Override
	public void handlerData(ServiceInstance instance, String resVmSid) {

	}
}
