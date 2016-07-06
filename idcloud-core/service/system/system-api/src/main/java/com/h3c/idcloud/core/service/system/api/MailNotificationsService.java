package com.h3c.idcloud.core.service.system.api;

import com.h3c.idcloud.core.pojo.dto.customer.Ticket;
import com.h3c.idcloud.core.pojo.dto.order.OrderDetail;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.system.AlarmData;
import com.h3c.idcloud.core.pojo.dto.system.FreeRes;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.infrastructure.common.pojo.Attachment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 
 * @author 作者：zhachaoy E-mail：chao-yi.zhang@hp.com 
 * @version 创建时间：2013-11-16 下午9:16:53 
 * @see ：
 */
public interface MailNotificationsService {
	
	/**
	 * 服务开通成功通知邮件
	 * @param serviceInstanceSid
	 * @return
	 */
	public boolean launchServiceEmail(Long serviceInstanceSid);
	/**
	 * 服务开通成功通知邮件
	 * @param orderId
	 * @return
	 */
	public boolean launchServiceEmail(String orderId);
	
	/**
	 * 准备开通服务通知
	 * @param orderDetails
	 * @return
	 */
	public boolean preLaunchServiceEmail(OrderDetail orderDetails);
	
	/**
	 * 激活通知
	 * @param user
	 * @return
	 */
	public boolean activateAccountEmail(User user);
	
	/**
	 * 项目经理注册账号待审核完成通知邮件
	 * @param user
	 * @return
	 */
	public boolean registerAccountEmail(User user);

	/**
	 * 注册账号审核完成通知邮件
	 * @param user
	 * @return
	 */
	public boolean approveAccountEmail(User user);
	
	/**
	 * 意见反馈邮件
	 * @param content
	 * @return
	 */
	public boolean feedbackEmail(String adviceType, String email, String content);
	
	/**
	 * 服务退订成功通知邮件
	 * @param serviceInstanceSid
	 * @return
	 */
	public boolean unsubscribeServiceEmail(Long serviceInstanceSid, ResVm resVm);
	
	/**
	 * 用户重置密码邮件
	 * @param user
	 * @return
	 */
	public boolean changePwdEmail(User user);
	
	/**
	 * 报表发送
	 * @param templateId 报表的类型
	 * @param attachtList 附件
	 * @return
	 */
	public boolean sendSysMgtReportNoticeEmail(String templateId, List<Attachment> attachtList);
	
	/**
	 * 项目到期提醒邮件发送
	 * @param templateId 报表的类型
	 * @param mgtObjEmail 部门经理的邮箱
	 * @param mgtObjData 邮件内容
	 * @return
	 */
	public boolean sendMgtObjExpireDateNoticeEmail(String templateId, String mgtObjEmail, Map<String, String> mgtObjData);

	/**
	 * 注册完成通知
	 * @param user
	 * @return
	 */
	public boolean registerEmail(User user);
	
	/**
	 * 服务变更成功通知邮件
	 * @param serviceInstanceSid
	 */
	public boolean changeSuccessEmail(Long serviceInstanceSid);
	
	/**
	 * 待处理工单通知邮件
	 * @param ticket
	 */
	public boolean ticketNoticeEmail(Ticket ticket, Long managerKLB);
	
	/**
	 * 工单分配，相关人员通知邮件
	 * @param ticket
	 */
	public boolean ticketAllocationNoticeEmail(Ticket ticket, List<HashMap> users);
	
	/**
	 * 发送闲置资源通知邮件
	 * @param freeRes
	 * @return
	 */
	public boolean freeResNofityServiceEmail(FreeRes freeRes);
	
	/**
	 * 待审核信息通知邮件（开通服务）
	 * @param orderId 订单编号
	 * @param toAddressList 收件人邮箱
	 */
	public boolean unapproveInfoNotice(String orderId, List<String> toAddressList);
	
	/**
	 * 待审核信息通知邮件（变更服务）
	 * @param instanceSid 实例编号
	 * @param toAddressList 收件人邮箱
	 */
	public boolean changeUnapproveInfoNotice(Long instanceSid, List<String> toAddressList);
	

	/**
	 * 待审核信息通知邮件（退订服务）
	 * @param instanceSid 实例编号
	 * @param toAddressList 收件人邮箱
	 */
	public boolean unsubscribeInfoNotice(Long instanceSid, List<String> toAddressList);
	
	/**
	 * 待审核信息-拒绝通知邮件（开通服务）
	 * @param orderId 订单编号
	 * @param toAddressList 收件人邮箱
	 */
	public boolean refapproveInfoNotice(String orderId, String messages, List<String> toAddressList);
	
	/**
	 *	项目经理申请项目及配额信息
	 * 
	 */
	public boolean applyMgtObjEmail(MgtObj mgtObj);
	
	/**
	 * 项目申请审核完成通知
	 */
	public boolean approveProjectEmail(MgtObj mgtObj);
	
	
	/**
	 * 告警信息
	 */
	public boolean alarmsInfoNotice(List<AlarmData> alarmData_list);
	
}
