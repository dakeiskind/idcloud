package com.h3c.idcloud.core.service.system.provider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.res.dao.ResVmMapper;
import com.h3c.idcloud.core.persist.user.dao.UserMapper;
import com.h3c.idcloud.core.pojo.common.ResDataUtils;
import com.h3c.idcloud.core.pojo.dto.customer.Ticket;
import com.h3c.idcloud.core.pojo.dto.order.Order;
import com.h3c.idcloud.core.pojo.dto.order.OrderDetail;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.system.*;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.dto.user.UserRole;
import com.h3c.idcloud.core.service.order.api.OrderService;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.core.service.system.api.*;
import com.h3c.idcloud.core.service.ticket.api.TicketService;
import com.h3c.idcloud.core.service.user.api.UserRoleService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Attachment;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.FileUtil;
import com.h3c.idcloud.infrastructure.common.util.MailUtil;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import sun.net.util.IPAddressUtil;

/**
 * @author 作者：zhachaoy E-mail：chao-yi.zhang@hp.com
 * @version 创建时间：2013-11-16 下午9:23:58
 * @see ：
 */
@Service(version = "1.0.0")
@Component
public class MailNotificationsServiceImpl implements MailNotificationsService {

	private static Logger logger = LoggerFactory.getLogger(MailNotificationsServiceImpl.class);
	@Autowired
	private MailTemplateService mailTemplateService;

	@Reference(version = "1.0.0")
	private UserService userService;

	@Reference(version = "1.0.0")
	private ServiceInstanceService serviceInstanceService;
	
	/** 资源申请接口Service */
	@Autowired
	private ResVmMapper resVmMapper;
	@Reference(version = "1.0.0")
	private ResVmService resVmService;
	@Reference(version = "1.0.0")
	private OrderService orderService;
	@Autowired
	private UserMgtObjService userMgtObjService;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private MgtObjService mgtObjService;
	@Autowired
	private CodeService codeService;
	@Reference(version = "1.0.0")
	private UserRoleService userRoleService;
	@Reference(version = "1.0.0")
	private TicketService ticketService;
	
	
	/** 服务实例资源映射Service */
	@Reference(version = "1.0.0")
	private ServiceInstResService serviceInstResService;

    private MailInfo getMailInfo(String resVmSid, ServiceInstance serviceInstance){
    	MailInfo mailInfo = new MailInfo();
    	ResVm resVm = this.resVmService.getVmInfo(resVmSid);
    	if (resVm != null) {
        	mailInfo.setCpuCores(resVm.getCpuCores().toString());
        	mailInfo.setIntranetIp(ResDataUtils.getIntranetIp(resVm.getResIpList()));
        	mailInfo.setManagementAccount(resVm.getManagementAccount());
        	mailInfo.setManagementPassword(resVm.getManagementPassword());
        	mailInfo.setMemorySize(resVm.getMemorySize().toString());
        	mailInfo.setVmName(resVm.getVmName());
        	mailInfo.setDataDisk(String.valueOf(resVm.getAllVdSize()));
        	mailInfo.setOsName(resVm.getOsVersion());
        	mailInfo.setVmIp(ResDataUtils.getInternetIp(resVm.getResIpList()));
    	} else {
    		mailInfo.setVmName(serviceInstance.getInstanceName());
    	}

    	mailInfo.setServiceName(serviceInstance.getServiceName());
    	mailInfo.setUserName(serviceInstance.getOwnerName());
    	mailInfo.setUserEmail(serviceInstance.getOwnerEmail());
    	mailInfo.setOrderId(serviceInstance.getOrderId());
    	if(mailInfo.getIntranetIp()==null){
    		for(int i=0;i<resVm.getResIpList().size();i++){
    			if(internalIp(resVm.getResIpList().get(i).getIpAddress())){
    				mailInfo.setIntranetIp(resVm.getResIpList().get(i).getIpAddress());
    			}
    		}
    	}
    	if(mailInfo.getVmIp()==null){
    		for(int i=0;i<resVm.getResIpList().size();i++){
    			if(internalIp(resVm.getResIpList().get(i).getIpAddress())==false){
    				mailInfo.setVmIp(resVm.getResIpList().get(i).getIpAddress());
    			}
    		}
    	}
    	return mailInfo;
    }
    
    private MailInfo getMailInfoByUnsubscribe(String resVmSid, ServiceInstance serviceInstance){
    	MailInfo mailInfo = new MailInfo();
    	ResVm resVm = this.resVmService.getVmInfo(resVmSid);
    	if (resVm != null) {
        	mailInfo.setCpuCores(resVm.getCpuCores().toString());
        	mailInfo.setIntranetIp(ResDataUtils.getIntranetIp(resVm.getResIpList()));
        	mailInfo.setManagementAccount(resVm.getManagementAccount());
        	mailInfo.setManagementPassword(resVm.getManagementPassword());
        	mailInfo.setMemorySize(resVm.getMemorySize().toString());
        	mailInfo.setVmName(resVm.getVmName());
        	mailInfo.setDataDisk(String.valueOf(resVm.getAllVdSize()));
        	mailInfo.setOsName(resVm.getOsVersion());
        	mailInfo.setVmIp(ResDataUtils.getInternetIp(resVm.getResIpList()));
    	} else {
    		mailInfo.setVmName(serviceInstance.getInstanceName());
    	}

    	mailInfo.setServiceName(serviceInstance.getServiceName());
    	mailInfo.setUserName(serviceInstance.getOwnerName());
    	mailInfo.setUserEmail(serviceInstance.getOwnerEmail());
    	mailInfo.setOrderId(serviceInstance.getOrderId());
    	return mailInfo;
    }
    
    //判断ip地址是否是内网和外网
    public static boolean internalIp(String ip) {
	    byte[] addr = IPAddressUtil.textToNumericFormatV4(ip);
	    return internalIp(addr);
	}
	 public static boolean internalIp(byte[] addr) {
		 final byte b0 = addr[0];
	     final byte b1 = addr[1];
	     //10.x.x.x/8
	     final byte SECTION_1 = 0x0A;
	     //172.16.x.x/12
	     final byte SECTION_2 = (byte) 0xAC;
	     final byte SECTION_3 = (byte) 0x10;
	     final byte SECTION_4 = (byte) 0x1F;
	     //192.168.x.x/16
	     final byte SECTION_5 = (byte) 0xC0;
	     final byte SECTION_6 = (byte) 0xA8;
	     switch (b0) {
	         case SECTION_1:
	             return true;
	         case SECTION_2:
	             if (b1 >= SECTION_3 && b1 <= SECTION_4) {
	                 return true;
	             }
	         case SECTION_5:
	            switch (b1) {
	                case SECTION_6:
	                    return true;
	            }
	         default:
	             return false;
	    }
	} 
    
	
	@Override
	public boolean launchServiceEmail(String orderId) {
		// TODO Auto-generated method stub
		boolean result = false;
		// 邮件地址
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = new ArrayList<String>();
		// 密送地址
		List<String> bccAddressList = null;
		List<MailInfo> mailInfolist = new ArrayList<MailInfo>();
		Criteria criteria1 = new Criteria();
		criteria1.put("orderId", orderId);
		List<ServiceInstance> serviceInstance_list = serviceInstanceService.selectByParams(criteria1);
		for(int i=0;i<serviceInstance_list.size();i++){
			//ServiceInstance serviceInstanceVM = serviceInstanceService.selectByPrimaryKey(serviceInstance_list.get(i).getInstanceSid());
			Criteria criteria = new Criteria();
			criteria.put("instanceSid", serviceInstance_list.get(i).getInstanceSid());
			criteria.put("resType", WebConstants.ResourceType.RES_VM);
			List<ServiceInstRes> serviceInstResList = this.serviceInstResService.selectByParams(criteria);
			if(serviceInstResList.size()>0){
				ServiceInstance serviceInstanceVM = serviceInstanceService.selectByPrimaryKey(serviceInstResList.get(0).getInstanceSid());
				if (serviceInstResList.isEmpty()) {
					result = false;
					return result;
				}
				ServiceInstRes serviceInstRes = serviceInstResList.get(0);
				criteria = new Criteria();
				criteria.put("name", WebConstants.ServiceConfigEmail.SEND_TO_OWNER);
				MailInfo mailInfo = getMailInfo(serviceInstRes.getResId(), serviceInstanceVM);
				mailInfolist.add(mailInfo);
			}
		}
		// 获取发送到租户用户邮件模板
//		Criteria criteria = new Criteria();
//		criteria.put("instanceSid", serviceInstanceSid);
//		
//		ServiceInstance serviceInstance = serviceInstanceService.selectByPrimaryKey(serviceInstanceSid);
//		
//		List<ServiceInstRes> serviceInstResList = this.serviceInstResService.selectByParams(criteria);
//		if (serviceInstResList.isEmpty()) {
//			result = false;
//			return result;
//		}
//		ServiceInstRes serviceInstRes = serviceInstResList.get(0);
//		
//		criteria = new Criteria();
//		criteria.put("name", WebConstants.ServiceConfigEmail.SEND_TO_OWNER);
//		
//		MailInfo mailInfo = getMailInfo(serviceInstRes.getResId(), serviceInstance);
//		
//		if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_VM)) {
//			// 获取邮件模板信息
//			//mailInfo = mailTemplateService.selectVmMailInfo(criteria);
//		} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_STORAGE)) {
//			// 获取邮件模板信息
//			mailInfo = mailTemplateService.selectStorageMailInfo(criteria);
//		} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_EX)) {
//			// 获取邮件模板信息
//			mailInfo = mailTemplateService.selectExMailInfo(criteria);
//		} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_SP)) {
//			// 获取邮件模板信息
//			mailInfo = mailTemplateService.selectSpMailInfo(criteria);
//		} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_DEPLOYMENT)) {
//			// 获取邮件模板信息
//			mailInfo = mailTemplateService.selectDeploymentMailInfo(criteria);
//		}
		
		//判断是否发送邮件给所有者
		String ownerBool = PropertiesUtil.getProperty("mail.service.notice.owner.send");
		//判断是否发送邮件给管理员
		String adminBool = PropertiesUtil.getProperty("mail.service.notice.admin.send");
		String adminAddress = "";
		if("true".equals(adminBool)){
			adminAddress = PropertiesUtil.getProperty("mail.notice.admin.address");
			String []admin = adminAddress.split(";");
			for(int i=0;i<admin.length;i++){
				toAddressList.add(admin[i]);
			}
		}
		if("true".equals(ownerBool) && "true".equals(adminBool)){
			toAddressList.add(mailInfolist.get(0).getUserEmail());
		}else if("true".equals(ownerBool) && !"true".equals(adminBool)){
			toAddressList.add(mailInfolist.get(0).getUserEmail());
		}else if(!"true".equals(ownerBool) && !"true".equals(adminBool)){
			return false;
		}

		if (mailInfolist.size()>0 && !StringUtil.isNullOrEmpty(mailInfolist.get(0).getUserEmail())) {
			// 租户用户邮件地址
//			toAddressList.add(mailInfo.getUserEmail());
			
			//发送通知邮件给管理员
//			String adminAdress = PropertiesUtil.getProperty("mail.notice.admin.address");
//			String[] str = adminAdress.split(";");
//			for(int i = 0; i < str.length; i++){
//				ccAddressList.add(str[i]);
//			}
			

			// 读取路径下邮件模板
			//StringBuffer mailContent = FileUtil.readFileByClasspath(mailInfo.getMailContentFilePath());
			//StringBuffer mailContent = FileUtil.readFileByClasspath("/mailtemplate/serviceOpenSuccessNotice.html");
			//String promptInfo = "※Linux操作系统SSH端口号为10022；Widnows操作系统远程桌面连接端口号为10033。";
			// 替换邮件内容开始
//			criteria = new Criteria();
//			criteria.put("mailContentFilePath", "/mailtemplate/serviceOpenSuccessNotice.html");
//			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			Criteria criteria = new Criteria();
			criteria.put("mailTemplateId", WebConstants.MailTemplateId.SERVICE_OPEN_SUCCESS_EMAIL);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			String mailContent = mailTemplageList.get(0).getMailContent();
			StringBuffer newMailContent = new StringBuffer(mailContent);
			StringUtil.strBufReplace("${owner}", mailInfolist.get(0).getUserName(), newMailContent);//替换邮件 用户
			StringUtil.strBufReplace("${service}", mailInfolist.get(0).getServiceName(), newMailContent);//替换邮件服务信息
			StringUtil.strBufReplace("${orderId}", mailInfolist.get(0).getOrderId(), newMailContent);//替换订单编码信息
			String systemName = PropertiesUtil.getProperty("system.name");
			String companyName = PropertiesUtil.getProperty("company.name");
			StringBuilder sb = new StringBuilder();// 初始化字符串拼接
			//判断云主机服务
//			for(int i=0;i<serviceInstance_list.size();i++){
//				if (serviceInstance_list.get(i).getServiceSid().equals(WebConstants.ServiceSid.SERVICE_VM)) {
//					for(int j=0;j<mailInfolist.size();j++){
//						sb.append("云主机名：").append(mailInfolist.get(j).getVmName()).append("<br>")
////						.append("管理账号/密码：").append( mailInfo.getManagementAccount()).append("/").append(mailInfo.getManagementPassword()).append("<br>")
//						.append("云主机公网IP：").append(mailInfolist.get(j).getVmIp()!=null?mailInfolist.get(j).getVmIp():"无").append("<br>")
//						.append("云主机内网IP：").append(mailInfolist.get(j).getIntranetIp()!=null?mailInfolist.get(j).getIntranetIp():"无");
//					}
//				}
//			}
			for(int j=0;j<mailInfolist.size();j++){
//				for(int i=0;i<serviceInstance_list.size();i++){
//					if (serviceInstance_list.get(i).getServiceSid().equals(WebConstants.ServiceSid.SERVICE_VM)) {
						sb.append("云主机名：").append(mailInfolist.get(j).getVmName()).append("<br>")
//						.append("管理账号/密码：").append( mailInfo.getManagementAccount()).append("/").append(mailInfo.getManagementPassword()).append("<br>")
						.append("云主机公网IP：").append(mailInfolist.get(j).getVmIp()!=null?mailInfolist.get(j).getVmIp():"无").append("<br>")
						.append("云主机内网IP：").append(mailInfolist.get(j).getIntranetIp()!=null?mailInfolist.get(j).getIntranetIp():"无").append("<br>").append("<br>");
//					}
//				}
			}
			
			if (serviceInstance_list.get(0).getServiceSid().equals(WebConstants.ServiceSid.SERVICE_VM)) {
				StringUtil.strBufReplace("${serviceSpecDesc}", sb.toString(), newMailContent);
				StringUtil.strBufReplace("${systemName}", systemName, newMailContent);
				StringUtil.strBufReplace("${companyName}", companyName, newMailContent);
			}
			
//			if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_VM)) {
//				// 获取开通云主机服务邮件模板信息
//				sb.append("云主机名：").append(mailInfo.getVmName()).append("<br>")
////				.append("管理账号/密码：").append( mailInfo.getManagementAccount()).append("/").append(mailInfo.getManagementPassword()).append("<br>")
//				.append("云主机公网IP：").append(mailInfo.getVmIp()!=null?mailInfo.getVmIp():"无").append("<br>")
//				.append("云主机内网IP：").append(mailInfo.getIntranetIp()!=null?mailInfo.getIntranetIp():"无");
//				
//				StringUtil.strBufReplace("${serviceSpecDesc}", sb.toString(), newMailContent);
//				StringUtil.strBufReplace("${systemName}", systemName, newMailContent);
//				StringUtil.strBufReplace("${companyName}", companyName, newMailContent);
//				//StringUtil.strBufReplace("${promptInfo}", promptInfo, mailContent);
//				
//			} 
//			else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_STORAGE)) {
//				StringUtil.strBufReplace("${vmName}", mailInfo.getVmName(), newMailContent);
//				StringUtil.strBufReplace("${allocateDiskSize}", mailInfo.getAllocateDiskSize() + " GB", newMailContent);
//				StringUtil.strBufReplace("${volumeTypeName}", mailInfo.getVolumeTypeName(), newMailContent);
//				StringUtil.strBufReplace("${dredgeDate}", StringUtil.dateFormat(mailInfo.getDredgeDate(), StringUtil.DF_YMD_24), newMailContent);
//				StringUtil.strBufReplace("${expiringDate}", StringUtil.dateFormat(mailInfo.getExpiringDate(), StringUtil.DF_YMD_24), newMailContent);
//			} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_EX)) {
//				StringUtil.strBufReplace("${vmName}", mailInfo.getVmName(), newMailContent);
//				StringUtil.strBufReplace("${allocateDomain}", mailInfo.getAllocateDomain(), newMailContent);
//				StringUtil.strBufReplace("${userAmount}", mailInfo.getUserAmount() + " 个", newMailContent);
//				StringUtil.strBufReplace("${singleMailboxCapacity}", mailInfo.getSingleMailboxCapacity() + " MB", newMailContent);
//				StringUtil.strBufReplace("${dredgeDate}", StringUtil.dateFormat(mailInfo.getDredgeDate(), StringUtil.DF_YMD_24), newMailContent);
//				StringUtil.strBufReplace("${expiringDate}", StringUtil.dateFormat(mailInfo.getExpiringDate(), StringUtil.DF_YMD_24), newMailContent);
//			} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_SP)) {
//				StringUtil.strBufReplace("${vmName}", mailInfo.getVmName(), newMailContent);
//				StringUtil.strBufReplace("${allocateStorageCapacity}", mailInfo.getAllocateStorageCapacity() + " MB", newMailContent);
//				StringUtil.strBufReplace("${allocateSharepointAddress}", mailInfo.getAllocateSharepointAddress(), newMailContent);
//				StringUtil.strBufReplace("${dredgeDate}", StringUtil.dateFormat(mailInfo.getDredgeDate(), StringUtil.DF_YMD_24), newMailContent);
//				StringUtil.strBufReplace("${expiringDate}", StringUtil.dateFormat(mailInfo.getExpiringDate(), StringUtil.DF_YMD_24), newMailContent);
//			} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_DEPLOYMENT)) {
//			}
			
			// 邮件发送
			logger.info("发送服务开通邮件.");
			result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0).getMailSubject(),
					newMailContent.toString(), null);
			logger.info("服务开通邮件Message sent successfully..."+result);
		}	
		return result;
	}
	@Override
	public boolean launchServiceEmail(Long serviceInstanceSid) {
		// TODO Auto-generated method stub
		boolean result = false;
		// 邮件地址
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = new ArrayList<String>();
		// 密送地址
		List<String> bccAddressList = null;
		
		// 获取发送到租户用户邮件模板
		Criteria criteria = new Criteria();
		criteria.put("instanceSid", serviceInstanceSid);
		
		ServiceInstance serviceInstance = serviceInstanceService.selectByPrimaryKey(serviceInstanceSid);
		
		List<ServiceInstRes> serviceInstResList = this.serviceInstResService.selectByParams(criteria);
		if (serviceInstResList.isEmpty()) {
			result = false;
			return result;
		}
		ServiceInstRes serviceInstRes = serviceInstResList.get(0);
		
		criteria = new Criteria();
		criteria.put("name", WebConstants.ServiceConfigEmail.SEND_TO_OWNER);
		
		MailInfo mailInfo = getMailInfo(serviceInstRes.getResId(), serviceInstance);
		
		if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_VM)) {
			// 获取邮件模板信息
			//mailInfo = mailTemplateService.selectVmMailInfo(criteria);
		} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_STORAGE)) {
			// 获取邮件模板信息
			mailInfo = mailTemplateService.selectStorageMailInfo(criteria);
		} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_EX)) {
			// 获取邮件模板信息
			mailInfo = mailTemplateService.selectExMailInfo(criteria);
		} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_SP)) {
			// 获取邮件模板信息
			mailInfo = mailTemplateService.selectSpMailInfo(criteria);
		} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_DEPLOYMENT)) {
			// 获取邮件模板信息
			mailInfo = mailTemplateService.selectDeploymentMailInfo(criteria);
		}
		
		//判断是否发送邮件给所有者
		String ownerBool = PropertiesUtil.getProperty("mail.service.notice.owner.send");
		//判断是否发送邮件给管理员
		String adminBool = PropertiesUtil.getProperty("mail.service.notice.admin.send");
		String adminAddress = "";
		if("true".equals(adminBool)){
			adminAddress = PropertiesUtil.getProperty("mail.notice.admin.address");
			String []admin = adminAddress.split(";");
			for(int i=0;i<admin.length;i++){
				toAddressList.add(admin[i]);
			}
		}
		if("true".equals(ownerBool) && "true".equals(adminBool)){
			toAddressList.add(mailInfo.getUserEmail());
		}else if("true".equals(ownerBool) && !"true".equals(adminBool)){
			toAddressList.add(mailInfo.getUserEmail());
		}else if(!"true".equals(ownerBool) && !"true".equals(adminBool)){
			return false;
		}
		
		if (mailInfo != null && !StringUtil.isNullOrEmpty(mailInfo.getUserEmail())) {
			// 租户用户邮件地址
//			toAddressList.add(mailInfo.getUserEmail());
			
			//发送通知邮件给管理员
//			String adminAdress = PropertiesUtil.getProperty("mail.notice.admin.address");
//			String[] str = adminAdress.split(";");
//			for(int i = 0; i < str.length; i++){
//				ccAddressList.add(str[i]);
//			}
			
			
			// 读取路径下邮件模板
			//StringBuffer mailContent = FileUtil.readFileByClasspath(mailInfo.getMailContentFilePath());
			//StringBuffer mailContent = FileUtil.readFileByClasspath("/mailtemplate/serviceOpenSuccessNotice.html");
			//String promptInfo = "※Linux操作系统SSH端口号为10022；Widnows操作系统远程桌面连接端口号为10033。";
			// 替换邮件内容开始
//			criteria = new Criteria();
//			criteria.put("mailContentFilePath", "/mailtemplate/serviceOpenSuccessNotice.html");
//			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			criteria = new Criteria();
			criteria.put("mailTemplateId", WebConstants.MailTemplateId.SERVICE_OPEN_SUCCESS_EMAIL);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			String mailContent = mailTemplageList.get(0).getMailContent();
			StringBuffer newMailContent = new StringBuffer(mailContent);
			StringUtil.strBufReplace("${owner}", mailInfo.getUserName(), newMailContent);//替换邮件 用户
			StringUtil.strBufReplace("${service}", mailInfo.getServiceName(), newMailContent);//替换邮件服务信息
			StringUtil.strBufReplace("${orderId}", mailInfo.getOrderId(), newMailContent);//替换订单编码信息
			String systemName = PropertiesUtil.getProperty("system.name");
			String companyName = PropertiesUtil.getProperty("company.name");
			StringBuilder sb = new StringBuilder();// 初始化字符串拼接
			//判断云主机服务
			if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_VM)) {
				// 获取开通云主机服务邮件模板信息
				sb.append("云主机名：").append(mailInfo.getVmName()).append("<br>")
//				.append("管理账号/密码：").append( mailInfo.getManagementAccount()).append("/").append(mailInfo.getManagementPassword()).append("<br>")
				.append("云主机公网IP：").append(mailInfo.getVmIp()!=null?mailInfo.getVmIp():"无").append("<br>")
				.append("云主机内网IP：").append(mailInfo.getIntranetIp()!=null?mailInfo.getIntranetIp():"无");
				
				StringUtil.strBufReplace("${serviceSpecDesc}", sb.toString(), newMailContent);
				StringUtil.strBufReplace("${systemName}", systemName, newMailContent);
				StringUtil.strBufReplace("${companyName}", companyName, newMailContent);
				//StringUtil.strBufReplace("${promptInfo}", promptInfo, mailContent);
				
			} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_STORAGE)) {
				StringUtil.strBufReplace("${vmName}", mailInfo.getVmName(), newMailContent);
				StringUtil.strBufReplace("${allocateDiskSize}", mailInfo.getAllocateDiskSize() + " GB", newMailContent);
				StringUtil.strBufReplace("${volumeTypeName}", mailInfo.getVolumeTypeName(), newMailContent);
				StringUtil.strBufReplace("${dredgeDate}", StringUtil.dateFormat(mailInfo.getDredgeDate(), StringUtil.DF_YMD_24), newMailContent);
				StringUtil.strBufReplace("${expiringDate}", StringUtil.dateFormat(mailInfo.getExpiringDate(), StringUtil.DF_YMD_24), newMailContent);
			} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_EX)) {
				StringUtil.strBufReplace("${vmName}", mailInfo.getVmName(), newMailContent);
				StringUtil.strBufReplace("${allocateDomain}", mailInfo.getAllocateDomain(), newMailContent);
				StringUtil.strBufReplace("${userAmount}", mailInfo.getUserAmount() + " 个", newMailContent);
				StringUtil.strBufReplace("${singleMailboxCapacity}", mailInfo.getSingleMailboxCapacity() + " MB", newMailContent);
				StringUtil.strBufReplace("${dredgeDate}", StringUtil.dateFormat(mailInfo.getDredgeDate(), StringUtil.DF_YMD_24), newMailContent);
				StringUtil.strBufReplace("${expiringDate}", StringUtil.dateFormat(mailInfo.getExpiringDate(), StringUtil.DF_YMD_24), newMailContent);
			} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_SP)) {
				StringUtil.strBufReplace("${vmName}", mailInfo.getVmName(), newMailContent);
				StringUtil.strBufReplace("${allocateStorageCapacity}", mailInfo.getAllocateStorageCapacity() + " MB", newMailContent);
				StringUtil.strBufReplace("${allocateSharepointAddress}", mailInfo.getAllocateSharepointAddress(), newMailContent);
				StringUtil.strBufReplace("${dredgeDate}", StringUtil.dateFormat(mailInfo.getDredgeDate(), StringUtil.DF_YMD_24), newMailContent);
				StringUtil.strBufReplace("${expiringDate}", StringUtil.dateFormat(mailInfo.getExpiringDate(), StringUtil.DF_YMD_24), newMailContent);
			} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_DEPLOYMENT)) {
			}
			
			// 邮件发送
			logger.info("发送服务开通邮件.");
			result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0).getMailSubject(),
					newMailContent.toString(), null);
			logger.info("服务开通邮件Message sent successfully..."+result);
		}	
		return result;
	}

	@Override
	public boolean activateAccountEmail(User user) {
		// TODO Auto-generated method stub
		boolean result = false;

		String accountActiveUrl = PropertiesUtil.getProperty("server.account.active.url");
		// 邮件地址
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = null;
		// 密送地址
		List<String> bccAddressList = null;

		if (user != null && !StringUtil.isNullOrEmpty(user.getEmail())) {
			// 租户用户邮件地址
			toAddressList.add(user.getEmail());
			// 读取路径下邮件模板
			Criteria criteria = new Criteria();
			criteria.put("mailTemplateId", WebConstants.MailTemplateId.EMAIL_TO_ACTIVATE);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			if (mailTemplageList.size() > 0) {
				StringBuffer mailContent = FileUtil.readFileByClasspath(mailTemplageList.get(0)
						.getMailContentFilePath());
				// 替换邮件内容
				StringUtil.strBufReplace("${owner}", user.getAccount(), mailContent);

				StringUtil.strBufReplace("${url}", accountActiveUrl + "?userSid=" + user.getUserSid() + "&securityKey="
						+ user.getPassword(), mailContent);
				// 邮件发送
				logger.info("账户激活邮件发送.");
				result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0)
						.getMailSubject(), mailContent.toString(), null);
				logger.info("账户激活邮件发送Message sent successfully..."+result);
			}
		}
		return result;
	}
	
	@Override
	public boolean registerEmail(User user) {
		// TODO Auto-generated method stub
		boolean result = false;

		// 邮件地址
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = null;
		// 密送地址
		List<String> bccAddressList = null;

		if (user != null && !StringUtil.isNullOrEmpty(user.getEmail())) {
			// 租户用户邮件地址
			toAddressList.add(user.getEmail());
			// 读取路径下邮件模板
			Criteria criteria = new Criteria();
			criteria.put("mailTemplateId", WebConstants.MailTemplateId.EMAIL_TO_REGISTER);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			if (mailTemplageList.size() > 0) {
				StringBuffer mailContent = FileUtil.readFileByClasspath(mailTemplageList.get(0)
						.getMailContentFilePath());
				// 替换邮件内容
				StringUtil.strBufReplace("${owner}", user.getAccount(), mailContent);

				// 邮件发送
				logger.info("注册完成通知发送.");
				result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0)
						.getMailSubject(), mailContent.toString(), null);
				logger.info("注册完成通知发送Message sent successfully..."+result);
			}
		}
		return result;
	}

	@Override
	public boolean feedbackEmail(String adviceType, String email, String content) {
		// TODO Auto-generated method stub
		boolean result = false;
		String mailFeedback = PropertiesUtil.getProperty("mail.feedback.address");
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = null;
		// 密送地址
		List<String> bccAddressList = null;
		String[] strArray = mailFeedback.split(";");
		for (int i = 0; i < strArray.length; i++) {
			toAddressList.add(strArray[i]);
		}
		if (toAddressList.size() > 0) {
			Criteria criteria = new Criteria();
			criteria.put("mailTemplateId", WebConstants.MailTemplateId.EMAIL_TO_FEEDBACK);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			if (mailTemplageList.size() > 0) {
				String systemName = PropertiesUtil.getProperty("system.name");
				String companyName = PropertiesUtil.getProperty("company.name");
				StringBuffer mailContent = FileUtil.readFileByClasspath(mailTemplageList.get(0)
						.getMailContentFilePath());
				StringUtil.strBufReplace("${adviceType}", adviceType, mailContent);
				StringUtil.strBufReplace("${email}", email, mailContent);
				StringUtil.strBufReplace("${content}", content, mailContent);
				StringUtil.strBufReplace("${systemName}", systemName, mailContent);
				StringUtil.strBufReplace("${companyName}", companyName, mailContent);
				logger.info("注册账号审核完成通知邮件发送.");
				result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0)
						.getMailSubject(), mailContent.toString(), null);
				if (result) {
					logger.info("有一条新的意见反馈发送到管理员邮箱！");
				} else {
					logger.error("有一条新的意见反馈发送错误！");
				}
			} else {
				logger.error("意见反馈模板未指定！");
			}
		} else {
			logger.error("意见反馈邮箱未指定！");
		}

		return result;
	}
	
	@Override
	public boolean unsubscribeServiceEmail(Long serviceInstanceSid,ResVm resVm) {
		// TODO Auto-generated method stub
		boolean result = false;
		// 邮件地址
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = new ArrayList<String>();
		// 密送地址
		List<String> bccAddressList = null;

		// 获取发送到租户用户邮件模板
		Criteria criteria = new Criteria();
		criteria.put("instanceSid", serviceInstanceSid);
		ServiceInstance serviceInstance = serviceInstanceService.selectByPrimaryKey(serviceInstanceSid);
		List<ServiceInstRes> serviceInstResList = this.serviceInstResService.selectByParams(criteria);
		if (serviceInstResList.isEmpty()) {
			result = false;
			return result;
		}
		ServiceInstRes serviceInstRes = serviceInstResList.get(0);
		criteria = new Criteria();
		criteria.put("name", WebConstants.ServiceConfigEmail.UNSUBSCRIBE_VM_SEND_TO_OWNER);
		MailInfo mailInfo = getMailInfoByUnsubscribe(serviceInstRes.getResId(), serviceInstance);
		
//		if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_VM)) {
//			// 获取邮件模板信息
//		//	mailInfo = mailTemplateService.selectVmMailInfo(criteria);
//		} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_STORAGE)) {
//			// 获取邮件模板信息
//			mailInfo = mailTemplateService.selectStorageMailInfo(criteria);
//		} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_EX)) {
//			// 获取邮件模板信息
//			mailInfo = mailTemplateService.selectExMailInfo(criteria);
//		} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_SP)) {
//			// 获取邮件模板信息
//			mailInfo = mailTemplateService.selectSpMailInfo(criteria);
//		} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_DEPLOYMENT)) {
//			// 获取邮件模板信息
//			mailInfo = mailTemplateService.selectDeploymentMailInfo(criteria);
//		}
		
		//判断是否发送邮件给所有者
		String ownerBool = PropertiesUtil.getProperty("mail.service.notice.owner.send");
		//判断是否发送邮件给管理员
		String adminBool = PropertiesUtil.getProperty("mail.service.notice.admin.send");
		String adminAddress = "";
		if("true".equals(adminBool)){
			adminAddress = PropertiesUtil.getProperty("mail.notice.admin.address");
			String []admin = adminAddress.split(";");
			for(int i=0;i<admin.length;i++){
				toAddressList.add(admin[i]);
			}
		}
		if("true".equals(ownerBool) && "true".equals(adminBool)){
			toAddressList.add(mailInfo.getUserEmail());
		}else if("true".equals(ownerBool) && !"true".equals(adminBool)){
			toAddressList.add(mailInfo.getUserEmail());
		}else if(!"true".equals(ownerBool) && !"true".equals(adminBool)){
			return false;
		}
		if (mailInfo != null && !StringUtil.isNullOrEmpty(mailInfo.getUserEmail())) {
			// 租户用户邮件地址
//			toAddressList.add(mailInfo.getUserEmail());
//			//cc租户用户邮件地址
//			String ccaddresss = PropertiesUtil.getProperty("service.order.ccaddress");
//			String[] str = ccaddresss.split(";");
//			for(int i = 0; i < str.length; i++){
//				ccAddressList.add(str[i]);
//			}
			// 读取路径下邮件模板serviceCancelSuccessNotice.html
			//StringBuffer mailContent = FileUtil.readFileByClasspath("/mailtemplate/serviceCancelSuccessNotice.html");
			//String promptInfo = "※Linux操作系统SSH端口号为10022；Widnows操作系统远程桌面连接端口号为10033。";
			// 替换邮件内容开始
//			criteria = new Criteria();
//			criteria.put("mailContentFilePath", "/mailtemplate/serviceCancelSuccessNotice.html");
//			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			criteria = new Criteria();
			criteria.put("mailTemplateId", WebConstants.MailTemplateId.SERVICE_CENCAL_SUCCESS_EMAIL);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			String mailContent = mailTemplageList.get(0).getMailContent();
			StringBuffer newMailContent = new StringBuffer(mailContent);
			// 替换邮件内容
			StringUtil.strBufReplace("${owner}", mailInfo.getUserName(), newMailContent);
			StringUtil.strBufReplace("${service}", mailInfo.getServiceName(), newMailContent);
			StringUtil.strBufReplace("${orderId}", mailInfo.getOrderId(), newMailContent);
			String systemName = PropertiesUtil.getProperty("system.name");
			String companyName = PropertiesUtil.getProperty("company.name");
			StringBuilder sb = new StringBuilder();// 初始化字符串拼接
			if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_VM)) {
				// 获取开通云主机服务邮件模板信息
				sb.append("云主机名：").append(resVm.getVmName()).append("<br>")
//				.append("管理账号/密码：").append(resVm.getManagementAccount()).append("/").append(resVm.getManagementPassword()).append("<br>")
				.append("云主机公网IP：").append(ResDataUtils.getInternetIp(resVm.getResIpList())!=null?ResDataUtils.getInternetIp(resVm.getResIpList()):"无").append("<br>")
				.append("云主机内网IP：").append(ResDataUtils.getIntranetIp(resVm.getResIpList())!=null?ResDataUtils.getIntranetIp(resVm.getResIpList()):"无").append("<br>");
				
				StringUtil.strBufReplace("${serviceSpecDesc}", sb.toString(), newMailContent);
				StringUtil.strBufReplace("${systemName}", systemName, newMailContent);
				StringUtil.strBufReplace("${companyName}", companyName, newMailContent);
				//StringUtil.strBufReplace("${promptInfo}", promptInfo, mailContent);
			} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_STORAGE)) {
				StringUtil.strBufReplace("${vmName}", mailInfo.getVmName(), newMailContent);
				StringUtil.strBufReplace("${allocateDiskSize}", mailInfo.getAllocateDiskSize() + " GB", newMailContent);
				StringUtil.strBufReplace("${volumeTypeName}", mailInfo.getVolumeTypeName(), newMailContent);
				StringUtil.strBufReplace("${dredgeDate}", StringUtil.dateFormat(mailInfo.getDredgeDate(), StringUtil.DF_YMD_24), newMailContent);
				StringUtil.strBufReplace("${expiringDate}", StringUtil.dateFormat(mailInfo.getExpiringDate(), StringUtil.DF_YMD_24), newMailContent);
			} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_EX)) {
				StringUtil.strBufReplace("${vmName}", mailInfo.getVmName(), newMailContent);
				StringUtil.strBufReplace("${allocateDomain}", mailInfo.getAllocateDomain(), newMailContent);
				StringUtil.strBufReplace("${userAmount}", mailInfo.getUserAmount() + " 个", newMailContent);
				StringUtil.strBufReplace("${singleMailboxCapacity}", mailInfo.getSingleMailboxCapacity() + " MB", newMailContent);
				StringUtil.strBufReplace("${dredgeDate}", StringUtil.dateFormat(mailInfo.getDredgeDate(), StringUtil.DF_YMD_24), newMailContent);
				StringUtil.strBufReplace("${expiringDate}", StringUtil.dateFormat(mailInfo.getExpiringDate(), StringUtil.DF_YMD_24), newMailContent);
			} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_SP)) {
				StringUtil.strBufReplace("${vmName}", mailInfo.getVmName(), newMailContent);
				StringUtil.strBufReplace("${allocateStorageCapacity}", mailInfo.getAllocateStorageCapacity() + " MB", newMailContent);
				StringUtil.strBufReplace("${allocateSharepointAddress}", mailInfo.getAllocateSharepointAddress(), newMailContent);
				StringUtil.strBufReplace("${dredgeDate}", StringUtil.dateFormat(mailInfo.getDredgeDate(), StringUtil.DF_YMD_24), newMailContent);
				StringUtil.strBufReplace("${expiringDate}", StringUtil.dateFormat(mailInfo.getExpiringDate(), StringUtil.DF_YMD_24), newMailContent);
			} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_DEPLOYMENT)) {
			}
			
			// 邮件发送
			logger.info("服务退订成功通知邮件发送.");
			result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0).getMailSubject(),
					newMailContent.toString(), null);
			logger.info("服务退订成功通知邮件发送Message sent successfully..."+result);
		}
		return result;
	}
	
	@Override
	public boolean changePwdEmail(User user) {
		// TODO Auto-generated method stub
		boolean result = false;

		String accountActiveUrl = PropertiesUtil.getProperty("mail.account.change.pwd.url");
		// 邮件地址
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = null;
		// 密送地址
		List<String> bccAddressList = null;

		if (user != null && !StringUtil.isNullOrEmpty(user.getEmail())) {
			// 租户用户邮件地址
			toAddressList.add(user.getEmail());
			// 读取路径下邮件模板
			Criteria criteria = new Criteria();
			criteria.put("mailTemplateId", WebConstants.MailTemplateId.EMAIL_TO_CHANGEPWD);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			String systemName = PropertiesUtil.getProperty("system.name");
			String companyName = PropertiesUtil.getProperty("company.name");
			if (mailTemplageList.size() > 0) {
				//StringBuffer mailContent = FileUtil.readFileByClasspath(mailTemplageList.get(0).getMailContent());
				String mailContent = mailTemplageList.get(0).getMailContent();
				StringBuffer newMailContent = new StringBuffer(mailContent);
				// 替换邮件内容
				StringUtil.strBufReplace("${owner}", user.getAccount(), newMailContent);
				StringUtil.strBufReplace("${systemName}", systemName, newMailContent);
				StringUtil.strBufReplace("${companyName}", companyName, newMailContent);

				StringUtil.strBufReplace("${url}", accountActiveUrl + "?userSid=" + user.getUserSid() + "&securityKey="
						+ user.getPassword(), newMailContent);
				// 邮件发送
				logger.info("服务退订成功通知邮件发送.");
				result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0)
						.getMailSubject(), newMailContent.toString(), null);
				logger.info("服务退订成功通知邮件发送Message sent successfully..."+result);
			}
		}
		return result;
	}

	@Override
	public boolean preLaunchServiceEmail(OrderDetail orderDetails) {
		boolean result = false;

		// 邮件地址
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = null;
		// 密送地址
		List<String> bccAddressList = null;

		if (!StringUtil.isNullOrEmpty(orderDetails)) {
			// 获取邮件地址
			
			String emailAddress = PropertiesUtil.getProperty("service.order.ccaddress");
			String[] addr = emailAddress.split(";");
			for(int i=0;i<addr.length;i++){
				toAddressList.add(addr[i]);
			}
			
			// 读取路径下邮件模板
			Criteria criteria = new Criteria();
			criteria.put("mailTemplateId", WebConstants.MailTemplateId.PRE_LAUNCH_SERVICE);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			if (mailTemplageList.size() > 0) {
				StringBuffer mailContent = FileUtil.readFileByClasspath(mailTemplageList.get(0)
						.getMailContentFilePath());
				StringUtil.strBufReplace("${orderId}", orderDetails.getOrderId(), mailContent);
				// 邮件发送
				result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0)
						.getMailSubject(), mailContent.toString(), null);
			}
		}
		return result;
	}
	
	@Override
	public boolean sendSysMgtReportNoticeEmail(String templateId , List<Attachment> attachtList) {
		boolean result = false;
		// 邮件地址
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = null;
		// 密送地址
		List<String> bccAddressList = null;
		// 获取邮件地址
		String emailAddress = PropertiesUtil.getProperty("mail.notice.admin.address");
		String systemName = PropertiesUtil.getProperty("system.name");
		String companyName = PropertiesUtil.getProperty("company.name");
		String[] addr = emailAddress.split(";");
		for(int i=0;i<addr.length;i++){
			toAddressList.add(addr[i]);
		}
		
		// 读取路径下邮件模板
		Criteria criteria = new Criteria();
		criteria.put("mailTemplateId", templateId);
		List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
		if (mailTemplageList.size() > 0) {
			StringBuffer mailContent = FileUtil.readFileByClasspath(mailTemplageList.get(0)
					.getMailContentFilePath());
			StringUtil.strBufReplace("${reportName}", mailTemplageList.get(0).getMailTemplateName(), mailContent);
			StringUtil.strBufReplace("${systemName}", systemName, mailContent);
			StringUtil.strBufReplace("${companyName}", companyName, mailContent);
			// 邮件发送
			result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0).getMailSubject() , mailContent.toString(), attachtList);
		}
		return result;
	}
	
	@Override
	public boolean sendMgtObjExpireDateNoticeEmail(String templateId,String mgtObjEmail,Map<String,String> mgtObjData) {
		boolean result = false;
		// 邮件地址
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = null;
		// 密送地址
		List<String> bccAddressList = null;
		String noticeFalg = PropertiesUtil.getProperty("mail.notice.mgtobjexpiredate.send");
		if("false".equals(noticeFalg)){
			return false;
		}
		// 获取邮件地址
		String emailAddress = PropertiesUtil.getProperty("mail.notice.admin.address");
		String systemName = PropertiesUtil.getProperty("system.name");
		String companyName = PropertiesUtil.getProperty("company.name");
		String[] addr = emailAddress.split(";");
		for(int i=0;i<addr.length;i++){
			toAddressList.add(addr[i]);
		}
		if(!StringUtil.isNullOrEmpty(mgtObjEmail)){
			toAddressList.add(mgtObjEmail);
		}
		
		// 读取路径下邮件模板
		Criteria criteria = new Criteria();
		criteria.put("mailTemplateId", templateId);
		List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
		if (mailTemplageList.size() > 0) {
//			StringBuffer mailContent = FileUtil.readFileByClasspath(mailTemplageList.get(0)
//					.getMailContentFilePath());
			String mailContent = mailTemplageList.get(0).getMailContent();
			StringBuffer newMailContent = new StringBuffer(mailContent);
			StringUtil.strBufReplace("${reportName}", mailTemplageList.get(0).getMailTemplateName(), newMailContent);
			StringUtil.strBufReplace("${systemName}", systemName, newMailContent);
			StringUtil.strBufReplace("${companyName}", companyName, newMailContent);
			StringUtil.strBufReplace("${mgtObjName}", mgtObjData.get("mgtObjName"), newMailContent);
			StringUtil.strBufReplace("${mgtObjEndTime}", mgtObjData.get("mgtObjEndTime"), newMailContent);
			StringUtil.strBufReplace("${mgtObjResName}", mgtObjData.get("mgtObjResName"), newMailContent);
			// 邮件发送
			result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0).getMailSubject() , newMailContent.toString(), null);
		}
		return result;
	}

	@Override
	public boolean approveAccountEmail(User user) {

		boolean result = false;

		// 邮件地址
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = null;
		// 密送地址
		List<String> bccAddressList = null;

		if (user != null && !StringUtil.isNullOrEmpty(user.getEmail())) {
			// 租户用户邮件地址
			toAddressList.add(user.getEmail());
			// 读取路径下邮件模板
			Criteria criteria = new Criteria();
			criteria.put("mailTemplateId", WebConstants.MailTemplateId.EMAIL_TO_APPROVE);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			
			if (mailTemplageList.size() > 0) {
				String systemName = PropertiesUtil.getProperty("system.name");
				String companyName = PropertiesUtil.getProperty("company.name");
				//StringBuffer mailContent = FileUtil.readFileByClasspath(mailTemplageList.get(0).getMailContentFilePath());
				String mailContent = mailTemplageList.get(0).getMailContent();
				StringBuffer newMailContent = new StringBuffer(mailContent);
				// 替换邮件内容
				StringUtil.strBufReplace("${owner}", user.getAccount(), newMailContent);
				StringUtil.strBufReplace("${systemName}", systemName, newMailContent);
				StringUtil.strBufReplace("${companyName}", companyName, newMailContent);
				// 邮件发送
				logger.info("注册账号审核完成通知邮件发送.");
				result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0)
						.getMailSubject(), newMailContent.toString(), null);
				logger.info("注册账号审核完成通知邮件发送Message sent successfully..."+result);
			}
		}
		return result;
	
	}
	
	@Override
	public boolean freeResNofityServiceEmail(FreeRes freeRes) {
		// TODO Auto-generated method stub
		boolean result = false;
		// 邮件地址
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = new ArrayList<String>();
		// 密送地址
		List<String> bccAddressList = null;
		
		// 获取发送到租户用户邮件模板
		Criteria criteria2 = new Criteria();
		criteria2.put("fresSid", freeRes.getFresSid());
		MailInfo mailInfo = mailTemplateService.selectFreeResMailInfo(criteria2);
		if (mailInfo != null && !StringUtil.isNullOrEmpty(mailInfo.getUserEmail())) {
			// 租户用户邮件地址
			toAddressList.add(mailInfo.getUserEmail());
			
			// 读取路径下邮件模板
			Criteria criteria = new Criteria();
			criteria.put("mailTemplateId", WebConstants.MailTemplateId.FREE_RES_RECOVER_NOTIFY);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			if (mailTemplageList.size() > 0) {
				StringBuffer mailContent = FileUtil.readFileByClasspath(mailTemplageList.get(0)
						.getMailContentFilePath());
				
				StringUtil.strBufReplace("${owner}", mailInfo.getUserName(), mailContent);
				StringUtil.strBufReplace("${vmName}", mailInfo.getVmName(), mailContent);
				StringUtil.strBufReplace("${fbizName}", mailInfo.getTenantName(), mailContent);
				StringUtil.strBufReplace("${sbizName}", mailInfo.getServiceName(), mailContent);
				
				StringUtil.strBufReplace("${cpuCores}", freeRes.getCpuCores() + "", mailContent);
				StringUtil.strBufReplace("${cpuUsage}", freeRes.getCpuUsage() + "", mailContent);
				StringUtil.strBufReplace("${memory}", freeRes.getMemory() + "", mailContent);
				StringUtil.strBufReplace("${memoryUsage}", freeRes.getMemUsage() + "", mailContent);
				StringUtil.strBufReplace("${disk}", freeRes.getStorage() + "", mailContent);
				StringUtil.strBufReplace("${diskUsage}", freeRes.getStUsage() + "", mailContent);
				StringUtil.strBufReplace("${privateNetOld}", freeRes.getPrivageIp(), mailContent);
				StringUtil.strBufReplace("${pubNetOld}", freeRes.getPubIp(), mailContent);
				StringUtil.strBufReplace("${content}", freeRes.getContent(), mailContent);
				
				// 邮件发送
				result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0)
						.getMailSubject(), mailContent.toString(), null);
			}
		}
		
		return result;
	}
	
	/**
	 *
	 * 云服务变更通知
	 */
	@Override
	public boolean changeSuccessEmail(Long serviceInstanceSid) {
		boolean result = false;
		// 邮件地址
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = new ArrayList<String>();
		// 密送地址
		List<String> bccAddressList = null;
		// 获取发送到租户用户邮件模板
		Criteria criteria = new Criteria();
		criteria.put("instanceSid", serviceInstanceSid);
		ServiceInstance serviceInstance = serviceInstanceService.selectByPrimaryKey(serviceInstanceSid);
		List<ServiceInstRes> serviceInstResList = this.serviceInstResService.selectByParams(criteria);
		if (serviceInstResList.isEmpty()) {
			result = false;
			return result;
		}
		ServiceInstRes serviceInstRes = serviceInstResList.get(0);
		criteria = new Criteria();
		criteria.put("name", WebConstants.ServiceConfigEmail.SEND_TO_OWNER);
		MailInfo mailInfo = getMailInfo(serviceInstRes.getResId(), serviceInstance);
		//判断是否发送邮件给所有者
		String ownerBool = PropertiesUtil.getProperty("mail.service.notice.owner.send");
		//判断是否发送邮件给管理员
		String adminBool = PropertiesUtil.getProperty("mail.service.notice.admin.send");
		String adminAddress = "";
		if("true".equals(adminBool)){
			adminAddress = PropertiesUtil.getProperty("mail.notice.admin.address");
			String []admin = adminAddress.split(";");
			for(int i=0;i<admin.length;i++){
				toAddressList.add(admin[i]);
			}
		}
		if("true".equals(ownerBool) && "true".equals(adminBool)){
			toAddressList.add(mailInfo.getUserEmail());
			//toAddressList.add(adminAddress);
		}else if("true".equals(ownerBool) && !"true".equals(adminBool)){
			toAddressList.add(mailInfo.getUserEmail());
		}else if(!"true".equals(ownerBool) && !"true".equals(adminBool)){
			return false;
		}
		if (mailInfo != null && !StringUtil.isNullOrEmpty(mailInfo.getUserEmail())) {
			// 读取路径下邮件模板
			//StringBuffer mailContent = FileUtil.readFileByClasspath("/mailtemplate/serviceChangeSuccessNotice.html");
			// 替换邮件内容开始
			//String promptInfo = "※Linux操作系统SSH端口号为10022；Widnows操作系统远程桌面连接端口号为10033。";
//			criteria = new Criteria();
//			criteria.put("mailContentFilePath", "/mailtemplate/serviceChangeSuccessNotice.html");
//			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			criteria = new Criteria();
			criteria.put("mailTemplateId", WebConstants.MailTemplateId.SERVICE_CHANGE_SUCCESS_EMAIL);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			String mailContent = mailTemplageList.get(0).getMailContent();
			StringBuffer newMailContent = new StringBuffer(mailContent);
			StringUtil.strBufReplace("${owner}", mailInfo.getUserName(), newMailContent);//替换邮件 用户
			StringUtil.strBufReplace("${service}", mailInfo.getServiceName(), newMailContent);//替换邮件服务信息
			StringUtil.strBufReplace("${orderId}", mailInfo.getOrderId(), newMailContent);//替换订单编码信息
			String systemName = PropertiesUtil.getProperty("system.name");
			String companyName = PropertiesUtil.getProperty("company.name");
			StringBuilder sb = new StringBuilder();// 初始化字符串拼接
			if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_VM)) {
				// 获取开通云主机服务邮件模板信息
				sb.append("云主机名：").append(mailInfo.getVmName()).append("<br>")
//				.append("管理账号/密码：").append( mailInfo.getManagementAccount()).append("/").append(mailInfo.getManagementPassword()).append("<br>")
				.append("云主机公网IP：").append(mailInfo.getVmIp()!=null?mailInfo.getVmIp():"无").append("<br>")
				.append("云主机内网IP：").append(mailInfo.getIntranetIp()!=null?mailInfo.getIntranetIp():"无");
				
				StringUtil.strBufReplace("${serviceSpecDesc}", sb.toString(), newMailContent);
				//StringUtil.strBufReplace("${promptInfo}", promptInfo, mailContent);
				StringUtil.strBufReplace("${systemName}", systemName, newMailContent);
				StringUtil.strBufReplace("${companyName}", companyName, newMailContent);
			}
			// 邮件发送
			logger.info("发送服务变更成功通知邮件.");
				result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0).getMailSubject(),
						newMailContent.toString(), null);
		    logger.info("发送服务变更成功通知邮件Message sent successfully..."+result);
		}
		return result;
	}

	@Override
	public boolean ticketNoticeEmail(Ticket ticket, Long managerKLB) {
		boolean result = false;
		// 邮件地址
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = null;
		// 密送地址
		List<String> bccAddressList = null;
		Criteria criteria1 = new Criteria();
		//判断是否发送邮件给管理员
		if(null!=ticket){
			Criteria criteria2 = new Criteria();
			criteria2.put("roleSid", managerKLB);
			List<UserRole> listUserSid = this.userRoleService.selectByParams(criteria2);
			String adminBool = PropertiesUtil.getProperty("mail.ticket.notice.admin.send");
			if("true".equals(adminBool)){
				if(listUserSid.size() > 0){
					for(int i=0;i<listUserSid.size();i++){
						User user = this.userMapper.selectByPrimaryKey(listUserSid.get(i).getUserSid());
						toAddressList.add(user.getEmail());
					}
				}else{
					String adminAddress = PropertiesUtil.getProperty("mail.notice.admin.address");
					String []admin = adminAddress.split(";");
					for(int i=0;i<admin.length;i++){
						toAddressList.add(admin[i]);
					}
				}
			}else{
				return false;
			}
//			if("true".equals(adminBool)&&"true".equals(ownerBool)){
//				String[] str = ticket.getAllocationTicketUser().split(",");
//				for(int i=0;i<str.length;i++){
//					criteria1.put("account", str[i]);
//					List<User> addressList = this.userService.selectByParams(criteria1);
//					toAddressList.add(addressList.get(0).getEmail());
//				}
//			}else if(!"true".equals(adminBool)&&"true".equals(ownerBool)){
//				String[] str = ticket.getAllocationTicketUser().split(",");
//				for(int i=0;i<str.length;i++){
//					criteria1.put("account", str[i]);
//					List<User> addressList = this.userService.selectByParams(criteria1);
//					toAddressList.add(addressList.get(0).getEmail());
//				}
//			}else if(!"true".equals(adminBool)&&!"true".equals(ownerBool)){
//				return false;
//			}
			//获取模板
			//StringBuffer mailContent = FileUtil.readFileByClasspath("/mailtemplate/sysNeedDealTicketNotice.html");
//			criteria1 = new Criteria();
//			criteria1.put("mailContentFilePath", "/mailtemplate/sysNeedDealTicketNotice.html");
//			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria1);
			Criteria criteria = new Criteria();
			criteria.put("mailTemplateId", WebConstants.MailTemplateId.TICKET_NOTICE_ADMIN_SEND);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			String mailContent = mailTemplageList.get(0).getMailContent();
			StringBuffer newMailContent = new StringBuffer(mailContent);
			criteria = new Criteria();
			criteria.put("ticketNo", ticket.getTicketNo());
			List<Ticket> ticket2 = this.ticketService.selectByParams(criteria);
			if (mailTemplageList.size() > 0) {
				String companyName = PropertiesUtil.getProperty("company.name");
				StringBuilder sb = new StringBuilder();
				sb.append("工单号：").append(ticket2.get(0).getTicketNo()).append("<br>")
				.append("工单主题：").append(ticket2.get(0).getTitle()).append("<br>")
				.append("工单级别：").append(ticket2.get(0).getQuestionLevelName()).append("<br>")
				.append("工单类型：").append(ticket2.get(0).getQuestionTypeName()).append("<br>")
				.append("工单内容：").append(ticket2.get(0).getContent());
				
				StringUtil.strBufReplace("${ticketInfo}", sb.toString(), newMailContent);
				StringUtil.strBufReplace("${companyName}", companyName, newMailContent);
				logger.info("发送待处理工单通知邮件.");
				result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0)
						.getMailSubject(), newMailContent.toString(), null);
				logger.info("发送待处理工单通知邮件Message sent successfully..."+result);
			}
		}
		return result;
	}
	
	/**
	 * 工单分配，相关人员通知邮件
	 * @param ticket
	 */
	@Override
	public boolean ticketAllocationNoticeEmail(Ticket ticket ,List<HashMap> users) {
		boolean result = false;
		// 邮件地址
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = null;
		// 密送地址
		List<String> bccAddressList = null;
		String isTrue = PropertiesUtil.getProperty("mail.ticket.notice.admin.send");
		String email = "";
		if("false".equals(isTrue)){
			return result;
		}else{
			//获取邮件地址
			for (int i = 0; i < users.size(); i++) {
				email = StringUtil.nullToEmpty(users.get(i).get("email"));
			}
			toAddressList.add(email);
			Criteria criteria = new Criteria();
			criteria.put("mailTemplateId", WebConstants.MailTemplateId.TICKET_ALLOCATION_NOTICE_EMAIL);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			String mailContent = mailTemplageList.get(0).getMailContent();
			StringBuffer newMailContent = new StringBuffer(mailContent);
			if (mailTemplageList.size() > 0) {
				String companyName = PropertiesUtil.getProperty("company.name");
				StringBuilder sb = new StringBuilder();
				sb.append("工单号：").append(ticket.getTicketNo()).append("<br>")
				.append("工单主题：").append(ticket.getTitle()).append("<br>")
				.append("工单级别：").append(ticket.getQuestionLevelName()).append("<br>")
				.append("工单类型：").append(ticket.getQuestionTypeName()).append("<br>")
				.append("工单内容：").append(ticket.getContent());
				StringUtil.strBufReplace("${ticketInfo}", sb.toString(), newMailContent);
				StringUtil.strBufReplace("${companyName}", companyName, newMailContent);
				logger.info("发送工单分配，相关人员通知邮件.");
				result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0)
						.getMailSubject(), newMailContent.toString(), null);
				logger.info("发送工单分配，相关人员通知邮件Message sent successfully..."+result);
			}
		}
	
		
		return result;
	}
	/**
	 * 待审核信息通知邮件
	 * orderId 订单编号
	 * toAddressList 收件人地址
	 * 
	 */
	@Override
	public boolean unapproveInfoNotice(String orderId,List<String> toAddressList) {
		boolean result = false;
		// 邮件地址
		List<String> toAddressLists = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = null;
		// 密送地址
		List<String> bccAddressList = null;
		String approve = PropertiesUtil.getProperty("mail.approve.notice.send");
		
		if("false".equals(approve)){
			return result;
		}else if("true".equals(approve)){
			Criteria criteria = new Criteria();
			criteria.put("mailTemplateId", WebConstants.MailTemplateId.ORDER_APPROVE_NOTIFY);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			if (mailTemplageList.size() > 0) {
			//	StringBuffer mailContent = FileUtil.readFileByClasspath(mailTemplageList.get(0).getMailContentFilePath());
				String mailContent = mailTemplageList.get(0).getMailContent();
				StringBuffer newMailContent = new StringBuffer(mailContent);
				String systemName = PropertiesUtil.getProperty("system.name");
				String companyName = PropertiesUtil.getProperty("company.name");
				StringBuilder sb = new StringBuilder();
				criteria = new Criteria();
				criteria.put("orderId", orderId);
				Order order = orderService.selectByParams(criteria).get(0);
				sb.append("订单号：").append(orderId).append("<br>").append("所有者：").append(order.getOwnerId());
				// 替换邮件内容
				StringUtil.strBufReplace("${unapproveInfo}", sb.toString(), newMailContent);
				StringUtil.strBufReplace("${systemName}", systemName, newMailContent);
				StringUtil.strBufReplace("${companyName}", companyName, newMailContent);
				//判断收件人邮箱是否为空
				for(int i=0;i<toAddressList.size();i++){
					if(!"".equals(toAddressList.get(i))){
						toAddressLists.add(toAddressList.get(i));
					}
				}
				logger.info("发送待审核信息通知邮件（开通服务）.");
				result = MailUtil.sendMail(toAddressLists, ccAddressList, bccAddressList, mailTemplageList.get(0)
						.getMailSubject(), newMailContent.toString(), null);
				logger.info("发送待审核信息通知邮件（开通服务）Message sent successfully..."+result);
				}
			}
		return result;
	}

	@Override
	public boolean changeUnapproveInfoNotice(Long instanceSid,List<String> toAddressList) {
		
		boolean result = false;
		// 邮件地址
		//List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = null;
		// 密送地址
		List<String> bccAddressList = null;
		String approve = PropertiesUtil.getProperty("mail.approve.notice.send");
		
		if("false".equals(approve)){
			return result;
		}else if("true".equals(approve)){
			Criteria criteria = new Criteria();
			criteria.put("mailTemplateId", WebConstants.MailTemplateId.ORDER_APPROVE_NOTIFY);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			if (mailTemplageList.size() > 0) {
//				StringBuffer mailContent = FileUtil.readFileByClasspath(mailTemplageList.get(0).getMailContentFilePath());
//				String systemName = PropertiesUtil.getProperty("system.name");
//				String companyName = PropertiesUtil.getProperty("company.name");
				String mailContent = mailTemplageList.get(0).getMailContent();
				StringBuffer newMailContent = new StringBuffer(mailContent);
				String systemName = PropertiesUtil.getProperty("system.name");
				String companyName = PropertiesUtil.getProperty("company.name");
				StringBuilder sb = new StringBuilder();
				ServiceInstance serviceInstance = serviceInstanceService.selectByPrimaryKey(instanceSid);
//				Order order = orderService.selectByParams(criteria).get(0);
				sb.append("实例号：").append(serviceInstance.getInstanceName()).append("<br>").append("所有者：").append(serviceInstance.getOwnerName());
				// 替换邮件内容
				StringUtil.strBufReplace("${unapproveInfo}", sb.toString(), newMailContent);
				StringUtil.strBufReplace("${systemName}", systemName, newMailContent);
				StringUtil.strBufReplace("${companyName}", companyName, newMailContent);
				logger.info("发送待审核信息通知邮件（变更服务）.");
				result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0)
						.getMailSubject(), newMailContent.toString(), null);
				logger.info("发送待审核信息通知邮件（变更服务）Message sent successfully..."+result);
				}
			}
		return result;
	}
	
	@Override
	public boolean unsubscribeInfoNotice(Long instanceSid,List<String> toAddressList) {
		boolean result = false;
		// 邮件地址
		//List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = null;
		// 密送地址
		List<String> bccAddressList = null;
		String approve = PropertiesUtil.getProperty("mail.approve.notice.send");
		
		if("false".equals(approve)){
			return result;
		}else if("true".equals(approve)){
			Criteria criteria = new Criteria();
			criteria.put("mailTemplateId", WebConstants.MailTemplateId.ORDER_APPROVE_NOTIFY);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			if (mailTemplageList.size() > 0) {
				String mailContent = mailTemplageList.get(0).getMailContent();
				StringBuffer newMailContent = new StringBuffer(mailContent);
				String systemName = PropertiesUtil.getProperty("system.name");
				String companyName = PropertiesUtil.getProperty("company.name");
				StringBuilder sb = new StringBuilder();
				ServiceInstance serviceInstance = serviceInstanceService.selectByPrimaryKey(instanceSid);
				criteria = new Criteria();
				criteria.put("instanceSid", instanceSid);
				List<ServiceInstRes> serviceInstResService = this.serviceInstResService.selectByParams(criteria);
				ResVm resVm = this.resVmMapper.selectByPrimaryKey(serviceInstResService.get(0).getResId());
				sb.append("云主机名称：").append(resVm.getVmName()).append("<br>").append("所有者：").append(serviceInstance.getOwnerName());
				// 替换邮件内容
				StringUtil.strBufReplace("${unapproveInfo}", sb.toString(), newMailContent);
				StringUtil.strBufReplace("${systemName}", systemName, newMailContent);
				StringUtil.strBufReplace("${companyName}", companyName, newMailContent);
				logger.info("发送待审核信息通知邮件（退订服务）.");
				result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0)
						.getMailSubject(), newMailContent.toString(), null);
				logger.info("发送待审核信息通知邮件（退订服务）Message sent successfully..."+result);
				}
			}
		return result;
	}
	

	@Override
	public boolean refapproveInfoNotice(String orderId, String messages, List<String> toAddressList) {
		boolean result = false;
		
		List<String> ccAddressList = null;
		// 密送地址
		List<String> bccAddressList = null;
		String approve = PropertiesUtil.getProperty("mail.approve.notice.send");
		if("false".equals(approve)){
			return result;
		}else if("true".equals(approve)){
			Criteria criteria = new Criteria();
			criteria.put("mailTemplateId", WebConstants.MailTemplateId.ORDER_REFUSE_NOTIFY);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			if (mailTemplageList.size() > 0) {
				String mailContent = mailTemplageList.get(0).getMailContent();
				StringBuffer newMailContent = new StringBuffer(mailContent);
				String systemName = PropertiesUtil.getProperty("system.name");
				String companyName = PropertiesUtil.getProperty("company.name");
				
//				StringBuilder sb = new StringBuilder();
//				criteria = new Criteria();
//				criteria.put("orderId", orderId);
//				Order order = orderService.selectByParams(criteria).get(0);
//				
//				sb.append("订单号：").append(orderId).append("<br>").append("所有者：").append(order.getOwnerId());
				// 替换邮件内容
				StringUtil.strBufReplace("${orderId}", orderId, newMailContent);
				StringUtil.strBufReplace("${unapproveInfo}", messages, newMailContent);
				StringUtil.strBufReplace("${systemName}", systemName, newMailContent);
				StringUtil.strBufReplace("${companyName}", companyName, newMailContent);
				//判断收件人邮箱是否为空
				logger.info("发送待审核信息-拒绝通知邮件.");
				result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0)
						.getMailSubject(), newMailContent.toString(), null);
				logger.info("发送待审核信息-拒绝通知邮件 Message sent successfully..."+result);
			}
		}
		return result;
	}

	@Override
	public boolean registerAccountEmail(User user) {

		boolean result = false;

		// 邮件地址
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = null;
		// 密送地址
		List<String> bccAddressList = null;

		String accountActiveUrl = PropertiesUtil.getProperty("server.account.active.url");
		String approve = PropertiesUtil.getProperty("mail.user.notice.admin.send");
		if("false".equals(approve)){
			return false;
		}else if("true".equals(approve)){
			/*String adminEmail = PropertiesUtil.getProperty("mail.notice.admin.address");
			String[] adminEmails = adminEmail.split(";");
			for(int i=0;i<adminEmails.length;i++){
				toAddressList.add(adminEmails[i]);
			}*/
			if (user != null && !StringUtil.isNullOrEmpty(user.getEmail())) {
				toAddressList.add(user.getEmail());
				// 租户用户邮件地址
//				toAddressList.add(adminEmail);
				// 读取路径下邮件模板
				Criteria criteria = new Criteria();
				criteria.put("mailTemplateId", WebConstants.MailTemplateId.EMAIL_TO_ACTIVATE);
				List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
				
				if (mailTemplageList.size() > 0) {
					String systemName = PropertiesUtil.getProperty("system.name");
					String companyName = PropertiesUtil.getProperty("company.name");
					//StringBuffer mailContent = FileUtil.readFileByClasspath(mailTemplageList.get(0).getMailContentFilePath());
					
					String mailContent = mailTemplageList.get(0).getMailContent();
					StringBuffer newMailContent = new StringBuffer(mailContent);
					// 替换邮件内容
					StringUtil.strBufReplace("${owner}", user.getAccount(), newMailContent);
					StringUtil.strBufReplace("${url}", accountActiveUrl + "?id=" + user.getUserSid() + "&securityKey="
							+ user.getPassword(), newMailContent);
					/*StringUtil.strBufReplace("${account}", user.getAccount(), newMailContent);
					StringUtil.strBufReplace("${systemName}", systemName, newMailContent);
					StringUtil.strBufReplace("${companyName}", companyName, newMailContent);*/
					// 邮件发送
					logger.info("项目经理注册账号待审核完成通知邮件发送.");
					result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0)
							.getMailSubject(), newMailContent.toString(), null);
					logger.info("项目经理注册账号待审核完成通知邮件发送.Message sent successfully..."+result);
				}
			}
		}
		return result;
	}

	@Override
	public boolean applyMgtObjEmail(MgtObj mgtObj) {
		boolean result = false;

		// 邮件地址
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = null;
		// 密送地址
		List<String> bccAddressList = null;
		
		String approve = PropertiesUtil.getProperty("mail.project.notice.admin.send");
		
		if("false".equals(approve)){
			return result;
		}else{
			//获取运维管理员email地址
			String adminEmail = PropertiesUtil.getProperty("mail.notice.admin.address");
			String[] adminEmails = adminEmail.split(";");
			for(int i=0;i<adminEmails.length;i++){
				toAddressList.add(adminEmails[i]);
			}
			//获取邮件模板信息
			Criteria criteria = new Criteria();
			criteria.put("mailTemplateId", WebConstants.MailTemplateId.MGTOBJ_APPLY_ADMIN_EMAIL);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
			if(mailTemplageList.size() > 0){
				//toAddressList.add(adminEmail);
				//获取模板变量基本信息
				//String systemName = PropertiesUtil.getProperty("system.name");
				String companyName = PropertiesUtil.getProperty("company.name");
				String mailContent = mailTemplageList.get(0).getMailContent();
				StringBuffer newMailContent = new StringBuffer(mailContent);
				//获取项目经理信息
				Criteria example = new Criteria();
				example.put("mgtObjSid", mgtObj.getMgtObjSid());
				List<UserMgtObjKey> list = this.userMgtObjService.selectByParams(example);
				User user = new User();
				if(!CollectionUtils.isEmpty(list)){
					user = this.userMapper.selectByPrimaryKey(list.get(0).getUserSid());
				}
				//获取配额信息
				List<Quota> mgtQuotas = mgtObj.getMgtQuotas();
				StringBuilder sb = new StringBuilder();
				StringBuilder sb1 = new StringBuilder();
				StringBuilder sb2 = new StringBuilder();
				StringBuilder sb3 = new StringBuilder();
				StringBuilder sb4 = new StringBuilder();
				if(null != mgtQuotas){
					sb.append("云主机类型:").append(" Linux,Windows").append("</br>");
					sb2.append("云主机类型:").append(" AIX").append("</br>");
					for(int i=0;i<mgtQuotas.size();i++){
						if("Linux,Windows".equals(mgtQuotas.get(i).getQuotaType())){
							sb1.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+mgtQuotas.get(i).getQuotaName()+":").append(" "+mgtQuotas.get(i).getQuotaValue()).append("</br>");
						}
						if("AIX".equals(mgtQuotas.get(i).getQuotaType())){
							sb3.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+mgtQuotas.get(i).getQuotaName()+":").append(" "+mgtQuotas.get(i).getQuotaValue()).append("</br>");
						}
					}
					sb4 = sb.append(sb1).append(sb2).append(sb3);
				}
				StringUtil.strBufReplace("${account}", user.getAccount(), newMailContent); 
				StringUtil.strBufReplace("${name}", mgtObj.getName(), newMailContent); 
				StringUtil.strBufReplace("${projectDesc}", sb4.toString(), newMailContent);
				StringUtil.strBufReplace("${companyName}", companyName, newMailContent);
				result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0)
						.getMailSubject(), newMailContent.toString(), null);
			}
		}
		return result;
	}
	
	/**
	 * 项目申请审核完成邮件
	 */
	@Override
	public boolean approveProjectEmail(MgtObj mgtObj) {
		boolean result = false;

		// 邮件地址
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = null;
		// 密送地址
		List<String> bccAddressList = null;
		
		String approve = PropertiesUtil.getProperty("mail.project.notice.user.send");
		if("false".equals(approve)){
			return result;
		}else{
			//获取项目经理邮件地址
			Criteria example = new Criteria();
			example.put("mgtObjSid", mgtObj.getMgtObjSid());
			List<UserMgtObjKey> list = this.userMgtObjService.selectByParams(example);
			User user = this.userMapper.selectByPrimaryKey(list.get(0).getUserSid());
			MgtObj mgtObjs = this.mgtObjService.selectByPrimaryKey(mgtObj.getMgtObjSid());
			//获取邮件内容模板
			//获取邮件模板信息
			example = new Criteria();
			example.put("mailTemplateId", WebConstants.MailTemplateId.APPROVE_PROJECT_EMAIL);
			List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(example);
			String status = "";
			if(mailTemplageList.size() > 0){
				toAddressList.add(user.getEmail());
				//获取模板变量基本信息
				String systemName = PropertiesUtil.getProperty("system.name");
				String companyName = PropertiesUtil.getProperty("company.name");
				String mailContent = mailTemplageList.get(0).getMailContent();
				StringBuffer newMailContent = new StringBuffer(mailContent);
				
				if("02".equals(mgtObj.getStatus()))
					status = "已通过";
				else{
					status = "被拒绝";
				}
				StringUtil.strBufReplace("${account}", user.getAccount(), newMailContent); 
				StringUtil.strBufReplace("${name}", mgtObjs.getName(), newMailContent); 
				StringUtil.strBufReplace("${status}", status, newMailContent);
				StringUtil.strBufReplace("${companyName}", companyName, newMailContent);
				logger.info("发送项目申请审核完成通知.");
				result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0)
						.getMailSubject(), newMailContent.toString(), null);
				logger.info("发送项目申请审核完成通知.Message sent successfully..."+result);
			}
		}
		return result;
	}
	
	/**
	 * 告警信息
	 */
	@Override
	public boolean alarmsInfoNotice(List<AlarmData> alarmData_list) {
		boolean result = false;
		// 邮件地址
		List<String> toAddressList = new ArrayList<String>();
		// 抄送地址
		List<String> ccAddressList = null;
		// 密送地址
		List<String> bccAddressList = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String approve = PropertiesUtil.getProperty("mail.alarm.notice.admin.send");
		if("false".equals(approve)){
			return false;
		}else if("true".equals(approve)){
			String adminEmail = PropertiesUtil.getProperty("mail.notice.admin.address");
			String[] adminEmails = adminEmail.split(";");
			for(int i=0;i<adminEmails.length;i++){
				toAddressList.add(adminEmails[i]);
			}
			if (alarmData_list.size() >0) {
				// 租户用户邮件地址
//				toAddressList.add(adminEmail);
				// 读取路径下邮件模板
				Criteria criteria = new Criteria();
				criteria.put("mailTemplateId", WebConstants.MailTemplateId.ALARM_INFO_EMAIL);
				List<MailTemplate> mailTemplageList = mailTemplateService.selectByParams(criteria);
				
				if (mailTemplageList.size() > 0) {
					String systemName = PropertiesUtil.getProperty("system.name");
					String companyName = PropertiesUtil.getProperty("company.name");
					//StringBuffer mailContent = FileUtil.readFileByClasspath(mailTemplageList.get(0).getMailContentFilePath());
					
					String mailContent = mailTemplageList.get(0).getMailContent();
					StringBuffer newMailContent = new StringBuffer(mailContent);
					StringBuilder sb = new StringBuilder();
					String alarmLevel = "";
					String alarmType = "";
					for (int i=0;i<alarmData_list.size();i++) {
						criteria = new Criteria();
						criteria.put("enabled", WebConstants.CodeEnable.ABLE);
						criteria.put("codeCategory", "ALARM_LEVEL");
						criteria.put("codeValue", alarmData_list.get(i).getAlarmLevel());
						List<Code> levels = this.codeService.selectByParams(criteria);
						if(levels.size()>0){
							alarmLevel = levels.get(0).getCodeDisplay();
						}
						criteria = new Criteria();
						criteria.put("enabled", WebConstants.CodeEnable.ABLE);
						criteria.put("codeCategory", "ALARM_TYPE");
						criteria.put("codeValue", alarmData_list.get(i).getAlarmType());
						List<Code> types = this.codeService.selectByParams(criteria);
						if(types.size()>0){
							alarmType = types.get(0).getCodeDisplay();
						}
						criteria = new Criteria();
						criteria.put("monitorNodeId", alarmData_list.get(i).getAlarmTarget());
						List<ResVm> vmAlarmlist = this.resVmMapper.selectByParams(criteria);
						if(vmAlarmlist.size()>0){
							sb.append("<tr>").append("<td align='right' width='20%'><strong>").append(alarmLevel).append("</strong></td>")
							.append("<td align='right' width='20%'><strong>").append(alarmType).append("</strong></td>")
							.append("<td align='right' width='20%'><strong>").append(vmAlarmlist.get(0).getVmName()).append("</strong></td>")
							.append("<td align='right' width='25%'><strong>").append(formatter.format(alarmData_list.get(i).getAlarmTime())).append("</strong></td>")
							.append("<td align='right' width='30%'><strong>").append(alarmData_list.get(i).getContent()).append("</strong></td>").append("</tr>");
						}
						
					}
					// 替换邮件内容
					StringUtil.strBufReplace("${alarnInfo}", sb.toString(), newMailContent); 
					StringUtil.strBufReplace("${systemName}", systemName, newMailContent);
					StringUtil.strBufReplace("${companyName}", companyName, newMailContent);
					// 邮件发送
					logger.info("告警信息通知邮件发送.");
					result = MailUtil.sendMail(toAddressList, ccAddressList, bccAddressList, mailTemplageList.get(0)
							.getMailSubject(), newMailContent.toString(), null);
					logger.info("告警信息通知邮件发送.Message sent successfully..."+result);
				}
			}
		}
		return result;
	}


}


