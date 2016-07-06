package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.res.ResOsSoftware;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.service.product.api.ResourceRequestHanlder;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 软件自动安装处理实现类
 * 
 * @author ChengQi
 *
 */
//@Service("softwareAutoInstallHandlerImpl")
@Service(version = "1.0.0",group = "softwareAutoInstallHandlerImpl")
@Component("softwareAutoInstallHandlerImpl")
public class SoftwareAutoInstallHandlerImpl implements ResourceRequestHanlder {

	private static final Logger logger = LoggerFactory.getLogger(SoftwareAutoInstallHandlerImpl.class);

	@Autowired
	private ServiceInstanceService instanceService;

	@Autowired
	private ServiceInstResService instResService;

	/*@Autowired
	private TicketMgt ticketMgt;*/
	

	@Override
	public void operate(ResInstResult result) {
		try {
			ResVm resVm = (ResVm)result.getData();
			if(resVm == null ||  resVm.getSoftwares() == null || resVm.getSoftwares().size() == 0) {
				logger.error("softwareAutoInstall callback data vm info is null or softwares is null");
				return;
			}
			Long instanceSid = this.instResService.getInstanceSidByResSid(resVm.getResVmSid());
			
			ServiceInstance vmServiceInstance  = this.instanceService.selectByPrimaryKey(instanceSid);
			String orderId = vmServiceInstance.getOrderId();
			Long mgtObjSid = vmServiceInstance.getMgtObjSid();
			
			List<ResOsSoftware> softwares = resVm.getSoftwares();
			List<ResOsSoftware> exceptionInstallSoftwares = new ArrayList<ResOsSoftware>();
//			for(ResOsSoftware software : softwares) {      ---------wsl
//				Long softwareInstanceSidSid = this.instResService.getInstanceSidByResSid(software.getResSortwareSid());
//				if(WebConstants.OsSoftwareStatus.INSTALLED.equals(software.getStatus())) {
//					ServiceInstance softwareInstance = this.instanceService.selectByPrimaryKey(softwareInstanceSidSid);
//					softwareInstance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
//					this.instanceService.updateByPrimaryKeySelective(softwareInstance);
//				} else if(WebConstants.OsSoftwareStatus.EXCEPTION.equals(software.getStatus())) {
//					exceptionInstallSoftwares.add(software);
//				}
//			}
			
			if(exceptionInstallSoftwares.size() > 0) {
				//生成云主机软件安装工单
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("orderId", orderId);
				params.put("mgtObjSid", mgtObjSid);
				params.put("instanceSid", instanceSid);
				params.put("processTarget", JsonUtil.toJson(exceptionInstallSoftwares));
				//flag= 1-无自动部署脚本，需手动安装 flag= 2-部署异常，需要手动安装
				params.put("flag", "2");
//				ticketMgt.createTicket(WebConstants.ticketType.SOFTWARE_INSTALL_TICKET, params);
			}

			//检查云主机下的所有子服务实例是否开通完成,如果全部完成则更新云主机服务实例状态为已开通, 并发送邮件通知
			instanceService.modifyVmServiceInstanceStatus(vmServiceInstance);
	
			//检查当前云主机所属订单下的所有云主机服务实例是否全部开通完成，如果全部完成则更新订单的状态为已开通
			instanceService.modifyOrderStatusOfVmServiceInstance(orderId);

		} catch(Exception e) {
			logger.error("operate failure.", e);
		}
	}
	/**
	 * 调用申请资源接口
	 *
	 * @param processObjectId 申请资源对象ids
	 * @return TODO
	 */
	public boolean invoke(Long processObjectId) {
		return false;
	}

	/**
	 * 调用申请资源接口
	 * @param params 动态参数
	 * @return
	 */
	public boolean invoke(Map<String, Object> params) {
		return false;
	}

	/**
	 * 调用申请资源接口
	 */
	public boolean invoke(List<Long> instanceSids) {
		return false;
	}

	/**
	 * 完成操作数据处理
	 */
	public void handlerData(ServiceInstance instance, String resVmSid) {}
}
