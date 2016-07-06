package com.h3c.idcloud.core.service.res.provider.resultListener;


import com.h3c.idcloud.infra.log.task.TaskLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * SecurityGroupResultListener.java
 *
 * @author 徐印
 */
public class SecurityGroupResultListener {
	
	private static final Logger logger = LoggerFactory.getLogger(VmResultListener.class);
	
	@Autowired
	private TaskLoggerFactory taskLogger;
	
	/**
	 * 处理创建浮动IP结果
	 * 
	 * @param vmOperateResult
	 */
	/*public void handleMessage(SgCreateResult sgCreateResult) {
		TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
		try {
			ResInstResult result = new ResInstResult();
		
			// 虚拟机创建成功
			if (sgCreateResult.isSuccess()) {
				
				ResSecurityGroup resSecurityGroup=new ResSecurityGroup();
				resSecurityGroup.setMgtObjSid(sgCreateResult.getMgtObjSid());
				resSecurityGroup.setSecurityGroupName(sgCreateResult.getName());
				resSecurityGroup.setDescription(sgCreateResult.getDescription());
				
				TaskLog log = new TaskLog();
				log.setTaskTarget(resNetworkList.get(0).getNetworkName());
				log.setTaskType(TaskType.ATTACH_VD);
				taskLogger.success(log);
				result.setStatus(ResInstResult.SUCCESS);
				result.setData(resNetworkList.get(0));

			} else {
				logger.error("创建FloatingIp失败：" + floatingIpCreateResult.getErrMsg());
				TaskLog log = new TaskLog();
				log.setTaskTarget(resNetworkList.get(0).getNetworkName());
				log.setTaskType(TaskType.CREATE_FLOATINGIP);
				log.setTaskFailureReason("创建FloatingIp失败：" + floatingIpCreateResult.getErrMsg());
				taskLogger.fail(log);

				result.setStatus(ResInstResult.FAILURE);
				result.setMessage(floatingIpCreateResult.getErrMsg());
			}

			// 回调service方法
			floatingIpOpenHandlerImpl.operate(result);
		} catch (Exception e) {
			logger.error("创建FloatingIp失败：" + ExceptionUtils.getFullStackTrace(e));
			TaskLog log = new TaskLog();
			log.setTaskTarget(resNetworkList.get(0).getNetworkName());
			log.setTaskType(TaskType.CREATE_FLOATINGIP);
			log.setTaskFailureReason("创建FloatingIp失败：" + ExceptionUtils.getFullStackTrace(e));
			taskLogger.fail(log);
		}
	}*/
}
