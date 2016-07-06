package com.h3c.idcloud.core.rest.charge.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.charge.*;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceSpec;
import com.h3c.idcloud.core.pojo.dto.product.ServiceSpec;
import com.h3c.idcloud.core.pojo.dto.res.ResCdnInst;
import com.h3c.idcloud.core.pojo.dto.res.ResCdnMgtObjFlow;
import com.h3c.idcloud.core.pojo.dto.res.ResOSUsageSum;
import com.h3c.idcloud.core.pojo.vo.charge.BillingPlanSpecVo;
import com.h3c.idcloud.core.pojo.vo.charge.PricingDetailParamVo;
import com.h3c.idcloud.core.rest.charge.BillingRest;
import com.h3c.idcloud.core.service.charge.api.*;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceSpecService;
import com.h3c.idcloud.core.service.product.api.ServiceSpecService;
import com.h3c.idcloud.core.service.res.api.ResCdnInstService;
import com.h3c.idcloud.core.service.res.api.ResObjectStorageUsageService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.pojo.SimpleRestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.RequestContextUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 租户Rest接口类
 * 
 * @author 徐印
 */
@Component
public class BillingRestImpl implements BillingRest {

	/** 资费计划Service */
	@Reference(version = "1.0.0")
	private BillingPlanService billingPlanService;

	/** 资费Service */
	@Reference(version = "1.0.0")
	private BillService billService;
	
	/** 资费Service */
	@Reference(version = "1.0.0")
	private BillDetailService billDetailService;
	
	/** 服务Service */
	@Reference(version = "1.0.0")
	private ServiceSpecService serviceSpecService;
	
	/** 服务Service */
	@Reference(version = "1.0.0")
	private ServiceInstanceService serviceInstanceService;
	
	/** 服务Service */
	@Reference(version = "1.0.0")
	private BillingPricingService billingPricingService;
	
	/** 服务规格Service */
	@Reference(version = "1.0.0")
	private ServiceInstanceSpecService serviceInstanceSpecService;
	
	/** 规格定价Service */
	@Reference(version = "1.0.0")
	private BillingPricingDetailService billingPricingDetailService;
	
	/** CDN服务service */
	@Reference(version = "1.0.0")
	private ResCdnInstService resCdnInstService;
	
	/** 账户service */
	@Reference(version = "1.0.0")
	private BillingAccountService billingAccountService;
	
	/** 对象存储service */
	@Reference(version = "1.0.0")
	private ResObjectStorageUsageService resObjectStorageUsageService;
	
	/** 账户管理对象service  */
	@Reference(version = "1.0.0")
	private BillingAccountMgtObjService billingAccountMgtObjService;

	@Reference(version = "1.0.0")
	private BillingPlanSpecService billingPlanSpecService ;
	
	private static Logger logger = LoggerFactory.getLogger(BillingRestImpl.class);
	
	private double money = 0;
	private double currentMoney = 0;
	
	private List<BillDetail> billDetailList = null;
	/**
	 * 查询资费计划列表
	 */
	@Override
	@WebMethod
	@POST
	@Path("/platform/findBillingPlan")
	public Response findBillingPlan(BillingPlan billingPlan) {

		Criteria example = new Criteria();
		if (billingPlan != null) {
			if (!StringUtil.isNullOrEmpty(billingPlan.getBillingPlanName())) {
				example.put("billingPlanNameLike", billingPlan.getBillingPlanName());
			}
			if (!StringUtil.isNullOrEmpty(billingPlan.getBillingPlanType())) {
				example.put("billingPlanType", billingPlan.getBillingPlanType());
			}
			if (!StringUtil.isNullOrEmpty(billingPlan.getPlanStatus())) {
				example.put("planStatus", billingPlan.getPlanStatus());
			}
		}
		List<BillingPlan> list = this.billingPlanService.selectByParams(example);

		String json = JsonUtil.toJson(list);
		return Response.status(Status.OK).entity(new RestResult(json)).build();
	}

	@Override
	public Response getPrice(ArrayList<Map<String, Object>> models) {
        BigDecimal price = this.billingPlanService.getPriceForWebCenter(models);
		return Response.status(Status.OK)
				.entity(JsonUtil.toJson(new RestResult(price))).build();
	}

	/**
	 * 查询资费列表
	 */
	@Override
	@WebMethod
	@POST
	public Response findBilling(Bill bill) {
		Criteria example = new Criteria();
		if (!StringUtil.isNullOrEmpty(bill.getTenantId())) {
			example.put("tenantId", bill.getTenantId());
		}
		if (!StringUtil.isNullOrEmpty(bill.getStatus())) {
			example.put("status",bill.getStatus());
		}
		List<Bill> list = this.billService.selectByParams(example);
		String json = JsonUtil.toJson(list);
		return Response.status(Status.OK).entity(json).build();
	}

	/**
	 * 新增资费计划
	 */
	@Override
	@POST
	@Path("/insertBillingPlan")
	public Response insertBillingPlan(BillingPlan billingPlan,@Context HttpServletRequest servletRequest) {
		AuthUser authUserInfo = RequestContextUtil.getAuthUserInfo(servletRequest);
		this.billingPlanService.addBillingPlan(billingPlan,authUserInfo);
		return Response.status(Status.OK).entity(new RestResult(RestResult.Status.SUCCESS, WebUtil
				.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS), null)).build();
	}

	/**
	 * 更新资费计划
	 */
	@Override
	@WebMethod
	@POST
	@Path("/updateBillingPlan")
	public Response updateBillingPlan(BillingPlan billingPlan) {
		String returnJson = "";

		WebUtil.prepareInsertParams(billingPlan);
		int result = this.billingPlanService.updateByPrimaryKeySelective(billingPlan);
		if (1 == result) {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
					.getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS), null));
		} else {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE), null));
		}

		return Response.status(Status.OK).entity(returnJson).build();
	}

	/**
	 * 删除资费计划
	 */
	@Override
	@WebMethod
	@GET
	@Path("/deleteBillingPlan/{billingPlanSid}")
	public Response deleteBillingPlan(@PathParam("billingPlanSid") Long billingPlanSid) {
		String returnJson;
		// 先删除资费定价及其详细
		Criteria example = new Criteria();
		example.put("billingPlanSid", billingPlanSid);
		List<BillingPricing> list = this.billingPricingService.selectByParams(example);
		if(list != null && list.size() > 0){
			for(BillingPricing billPricing : list){
				example = new Criteria();
				example.put("billingPricingSid", billPricing.getBillingPricingSid());
				this.billingPricingDetailService.deleteByParams(example);
				this.billingPricingService.deleteByPrimaryKey(billPricing.getBillingPricingSid());
			}
		}
		// 删除资费计划
		int result = this.billingPlanService.deleteByPrimaryKey(billingPlanSid);
		if (1 == result) {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
					.getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS)));
		}  else {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage(WebConstants.MsgCd.ERROR_DELETE_FAILURE)));
		}

		return Response.status(Status.OK).entity(returnJson).build();
	}
	
	/**
	 * 获得服务规格集合 
	 * 
	 * @param serviceSid
	 * @param billingPlanSid
	 * @param type
	 * @return
	 */
	@Override
	@WebMethod
	@GET
	@Path("/getServiceSpec/{serviceSid}/{billingPricingSid}/{type}")
	public Response getServiceSpecList(
			@PathParam("serviceSid") Long serviceSid,
			@PathParam("billingPlanSid") Long billingPlanSid,
			@PathParam("type") String type) {
		
		// 添加服务规格信息
		Criteria criteria = new Criteria();
		criteria.put("serviceSid", serviceSid);
		List<ServiceSpec> tempspec = this.serviceSpecService.selectByParams(criteria);
		List<ServiceSpec> spec = new ArrayList<ServiceSpec>();
		
		Criteria example = new Criteria();
		example.put("billingPlanSid", billingPlanSid);
//		example.put("billingType", type);
		List<BillingPricing> list = this.billingPricingService.selectByParams(example);
		
		if(list != null && list.size() > 0){
			// 获取当前资费计划下的所有已经定价的规格项sid
			String[] str = getBillingPricingServicePesc(list);
			for (ServiceSpec serviceSpec : tempspec) {
				boolean isok = true;
				for(int i=0;i<str.length;i++){
					// 判断是否有服务的规格项被定价了
					if(serviceSpec.getSpecSid() == Long.parseLong(str[i])){
						isok = false;
						break;
					}
				}
				if(isok){
					spec.add(serviceSpec);
				}
			}
		}else{
			spec = tempspec;
		}
		
		String json = JsonUtil.toJson(spec);
		return Response.status(Status.OK).entity(json).build();
	}

	@Override
	public Response getbillingPlanServiceSpec(Long serviceSid,Long planSid) {
		List<BillingPlanSpecVo> billingPlanSpecVos = this.billingPlanService.selectBillingPlanSpecVos(serviceSid, planSid);
		return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(billingPlanSpecVos))).build();
	}

	@Override
	public Response getbillingPlanServiceSpec(Long serviceSid) {
		List<BillingPlanSpecVo> billingPlanSpecVos = this.billingPlanService.selectBillingPlanSpecVos(serviceSid);
		return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(billingPlanSpecVos))).build();
	}

	@Override
	public Response getBilledPlanServiceSpec(Long planSid) {
		Criteria criteria = new Criteria();
		criteria.put("billingPlanSid",planSid);
		criteria.put("isBill",1);
		List<BillingPlanSpec> billingPlanSpecs = this.billingPlanSpecService.selectByParams(criteria);
		return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(billingPlanSpecs))).build();
	}

	/**
	 * 新增资费定价
	 * 
	 * @param billingPricing
	 * @return
	 */
	@Override
	@WebMethod
	@POST
	@Path("/addBillingPrice")
	public Response saveMgtBillingPrice(BillingPricing billingPricing) {
		int result = 0;
		Criteria criteria = new Criteria();
		criteria.put("billingPlanSid", billingPricing.getBillingPlanSid());
		criteria.put("billingType", billingPricing.getBillingType());
		criteria.put("status", billingPricing.getStatus());

		List<BillingPricing> billingPricings = this.billingPricingService.selectByParams(criteria);
		
		if (billingPricings != null && billingPricings.size() > 0) {
			//设置更新主键
			billingPricing.setBillingPricingSid(billingPricings.get(0).getBillingPricingSid());
			//更新时添加用户以及当前时间信息
//			WebUtil.prepareUpdateParams(billingPricing);
			result = billingPricingService.updateByPrimaryKeySelective(billingPricing);
		} else {
			//新增时添加用户以及当前时间信息
//			WebUtil.prepareInsertParams(billingPricing);
			result = billingPricingService.insertSelective(billingPricing);
		}
		
		String json = JsonUtil.toJson(billingPricing);
		return Response.status(Status.OK).entity(json).build();
		
	}
	
	/**
	 * 保存规格
	 */
	@Override
	@WebMethod
	@POST
	@Path("/saveMgtBillingPrice")
	public Response saveMgtBillingPrice(List<BillingPricingDetail> list) {
		int result = 0;
		// 删除全部原有的
		Criteria example = new Criteria();
		example.put("billingPricingSid1", list.get(0).getBillingPricingSid());
		billingPricingDetailService.deleteByParams(example);
		
		if(StringUtil.isNullOrEmpty(list.get(0).getSpecSid())){
			String json = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil.getMessage("添加成功！")));
			return Response.status(Status.OK).entity(json).build();
		}else{
			for(int i=0;i<list.size();i++){
				//新增时添加用户以及当前时间信息
				WebUtil.prepareInsertParams(list.get(i));
				result += billingPricingDetailService.insertSelective(list.get(i));
			}

			if(list.size() == result){
				String json = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil.getMessage("添加成功！")));
				return Response.status(Status.OK).entity(json).build();
			}else{
				String json = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage("添加失败！")));
				return Response.status(Status.OK).entity(json).build();
			}
		}
	}
	
	/**
	 * 查询资费计划的定价明细
	 */
	@Override
	@WebMethod
	@GET
	@Path("/getBillingBytype/{type}/{billingPlanSid}")
	public Response saveMgtBillingPrice(@PathParam("type") String type,@PathParam("billingPlanSid") Long billingPlanSid) {
		Criteria criteria = new Criteria();
		criteria.put("billingPlanSid", billingPlanSid);
		criteria.put("billingType", type);
		List<BillingPricing> list = this.billingPricingService.selectByParams(criteria);
		return Response.status(Status.OK).entity(new RestResult(list)).build();
	}
	
	/**
	 * 结算
	 */
	@Override
	@WebMethod
	@GET
	@Path("/billingbalance/{billingType}/{confirmTime}/{tenantId}")
	public Response billingBalance(
			@PathParam("billingType") String billingType,
			@PathParam("confirmTime") String confirmTime,
			@PathParam("tenantId") Long tenantId) {
		
		HashMap<String , String> resultMap = new  HashMap<String , String>();
		// 清空数据
		money = 0L;
		billDetailList = new ArrayList<BillDetail>();
		String returnJson = "";
		
		// 判断租户是否已经存在未付款的账单
//		Criteria billCriteria= new Criteria();
//		billCriteria.put("tenantId", tenantId);
//		billCriteria.put("status", WebConstants.BILL_STATUS.UNPAYED);
//		List<Bill> billList = this.billService.selectByParams(billCriteria);
//		if(billList.size() > 0){
//			// 该租户存在了账单
//			returnJson = JsonUtil.toJson(new RestResult(RestResult.SUCCESS, WebUtil
//					.getMessage("已存在未付款账单，请作废之后再结算！")));
//			return Response.status(Status.OK).entity(returnJson).build();
//		}
		
		// 1.获取规定时间
		Date date = StringUtil.strToDate(confirmTime, StringUtil.DF_YM, Locale.US);
		
		Date confirmToDate = new Date();
		confirmToDate = addDateNum(date, "", 1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(confirmToDate)+" 23:59:59";
		
		// 2.查询出该租户下在结算时间以前的全部ServiceInstance
		Criteria criteria= new Criteria();
	    criteria.put("confirmToDate", str);
	    criteria.put("billingEndTime", str);
		criteria.put("isBillingStatus", "yes");
		criteria.put("tanentId", tenantId);
		//单个服务实例测试
//    	criteria.put("instanceSid", 6089);
		List<ServiceInstance> serviceInstances = serviceInstanceService.selectServiceInstanceForNeedBalance(criteria);
		
		// 3.计算该租户使用的费用(按月)
	    // 3.1 不满一月的服务，按照：使用天数/30*单价
		if(0 == serviceInstances.size()){
			// 提示该租户在这个时段里面没有需要结算的服务实例
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage("没有需要结算的服务或都已结算过！")));
		}else{
			
			Date toDate = null;
			try {
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				toDate = sdf2.parse(str);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			resultMap = getServiceInstanceMoney(serviceInstances,toDate,tenantId);
			if("1".equals(resultMap.get("result"))){
				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
						.getMessage("结算成功！")));
			}else{
				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
						.getMessage(resultMap.get("message"))));
			}
		}
		return Response.status(Status.OK).entity(returnJson).build();
	}
	
	// 结算服务
	private HashMap<String , String> getServiceInstanceMoney(List<ServiceInstance> serviceInstances,Date confirmToDate,Long tenantId) {
		
		HashMap<String , String> resultMap = new  HashMap<String , String>();
		String serviceName = null;
		boolean isFirstflag = true;
		
		for (ServiceInstance serviceInstance : serviceInstances) {
			if(107 == serviceInstance.getServiceSid()){
				// CDN服务只能保留一个
				if(isFirstflag){
					SimpleDateFormat cdnSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Calendar calendar = Calendar.getInstance(); 
					calendar.setTime(confirmToDate); 
					int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
					int month = calendar.get(Calendar.MONTH) + 1;
					int year = calendar.get(Calendar.YEAR);
					String startStr = year +"-"+(month>9?month:"0"+month)+"-01 00:00:00";
					String endStr = year +"-"+(month>9?month:"0"+month)+"-"+maxDay+" 23:59:59";
					try {
						serviceName = serviceInstance.getInstanceName();
						serviceInstance.setInstanceName("CDN服务");
						serviceInstance.setDredgeDate(cdnSdf.parse(startStr));
						serviceInstance.setDestroyDate(cdnSdf.parse(endStr));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					isFirstflag = false;
				}else{
					// 设置最后一次结算时间
					SimpleDateFormat cdnSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Calendar calendar = Calendar.getInstance(); 
					calendar.setTime(confirmToDate); 
					int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
					int month = calendar.get(Calendar.MONTH) + 1;
					int year = calendar.get(Calendar.YEAR);
					String endStr = year +"-"+(month>9?month:"0"+month)+"-"+maxDay+" 23:59:59";
					try {
						serviceInstance.setBillingEndTime(cdnSdf.parse(endStr));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					this.serviceInstanceService.updateByPrimaryKeySelective(serviceInstance);
					continue;
				}
			}
			
			// 服务实例是：已退订、退订中
			if(WebConstants.ServiceInstanceStatus.CANCELING.equals(serviceInstance.getStatus()) 
			   || WebConstants.ServiceInstanceStatus.CANCELED.equals(serviceInstance.getStatus())){
				// 当服务实例为已退订时,还没有结算过
				if(StringUtil.isNullOrEmpty(serviceInstance.getBillingEndTime())){
					if(StringUtil.isNullOrEmpty(serviceInstance.getDredgeDate()) 
						|| StringUtil.isNullOrEmpty(serviceInstance.getDestroyDate())){
						resultMap.put("result", "0");
						resultMap.put("message", "结算失败");
						logger.info("当服务实例"+serviceInstance.getInstanceSid()+"状态为已退订或者退订中时，退订时间为空，不能正常结算！");
					}else{
						// 退订时间是否大于结算截止时间
						long distanceDays = getDaysFromTwoDate(confirmToDate,serviceInstance.getDestroyDate());
						if(distanceDays > 0){
							long distance = getDaysFromTwoDate(serviceInstance.getDredgeDate(),confirmToDate);
							if(distance <= 0){
								resultMap.put("message", "没有需要结算的服务或都已结算过！");
								continue;
							}else{
								List<BillTime> timeList = getTwoTimesxDisplayMonthly(serviceInstance.getDredgeDate(),confirmToDate);
								serviceInstance.setBillingEndTime(confirmToDate);
								resultMap = calculatedMonthly(timeList,serviceInstance);
							}
						}else{
							long distance = getDaysFromTwoDate(serviceInstance.getDredgeDate(),serviceInstance.getDestroyDate());
							if(distance < 0){
								resultMap.put("message", "没有需要结算的服务或都已结算过！");
								continue;
							}else{
								List<BillTime> timeList = getTwoTimesxDisplayMonthly(serviceInstance.getDredgeDate(),serviceInstance.getDestroyDate());
								serviceInstance.setBillingEndTime(serviceInstance.getDestroyDate());
								resultMap = calculatedMonthly(timeList,serviceInstance);
							}
						}
					}
				}else{
					if(StringUtil.isNullOrEmpty(serviceInstance.getDestroyDate())){
						resultMap.put("result", "0");
						resultMap.put("message", "结算失败");
						logger.info("当服务实例状态为已开通或者变更中时，退订时间为空，不能正常结算！");
					}else{
						
						long distance = getDaysFromTwoDate(serviceInstance.getBillingEndTime(),serviceInstance.getDestroyDate());
						if(distance <= 0){
							resultMap.put("message", "没有需要结算的服务或都已结算过！");
							continue;
						}else{
							List<BillTime> timeList = getTwoTimesxDisplayMonthly(serviceInstance.getBillingEndTime(),serviceInstance.getDestroyDate());
							serviceInstance.setBillingEndTime(serviceInstance.getDestroyDate());
							resultMap = calculatedMonthly(timeList,serviceInstance);
						}
					}
				}
			// 服务实例是：已开通、变更中	
			}else if(WebConstants.ServiceInstanceStatus.OPENED.equals(serviceInstance.getStatus())
					|| WebConstants.ServiceInstanceStatus.CHANGEING.equals(serviceInstance.getStatus())){
				// 当服务实例为已开通时
				if(StringUtil.isNullOrEmpty(serviceInstance.getBillingEndTime())){
					// 该服务实例没有结算过时，按照开通时间开始计算
					if(StringUtil.isNullOrEmpty(serviceInstance.getDredgeDate())){
						resultMap.put("result", "0");
						resultMap.put("message", "结算失败");
						logger.info("当服务实例状态为已退订或者退订中时，开通时间为空，不能正常结算！");
					}else{
						
						long distance = getDaysFromTwoDate(serviceInstance.getDredgeDate(),confirmToDate);
						if(distance < 0){
							resultMap.put("message", "没有需要结算的服务或都已结算过！");
							continue;
						}else{
							List<BillTime> timeList = getTwoTimesxDisplayMonthly(serviceInstance.getDredgeDate(),confirmToDate);
							serviceInstance.setBillingEndTime(confirmToDate);
							resultMap = calculatedMonthly(timeList,serviceInstance);
						}
					}
				}else{
					
					long distance = getDaysFromTwoDate(serviceInstance.getBillingEndTime(),confirmToDate);
					if(distance <= 0){
						resultMap.put("message", "没有需要结算的服务或都已结算过！");
						continue;
					}else{
						
						List<BillTime> timeList = getTwoTimesxDisplayMonthly(serviceInstance.getDredgeDate(),confirmToDate);
						serviceInstance.setBillingEndTime(confirmToDate);
						resultMap = calculatedMonthly(timeList,serviceInstance);
					}
				}
			}else{
				// 中断本次循环
				continue;
			}
			// 判断每次计算是否正确
			if("1".equals(resultMap.get("result"))){
				// 如果是CDN服务
				if(107 == serviceInstance.getServiceSid()){
					// 还原instanceName
					serviceInstance.setInstanceName(serviceName);
				}
				// 更新服务实例最后结算时间
				this.serviceInstanceService.updateByPrimaryKeySelective(serviceInstance);
			}else{
				// 发生错误了
				break;
			}
		}
		if("1".equals(resultMap.get("result"))){
			// 根据账单明细生成账单表
			Set<String> set = new TreeSet<String>();
			for(int i=0;i<billDetailList.size();i++){
				set.add(billDetailList.get(i).getBillTimeName());
			}
			Iterator<String> it = set.iterator();
			
			while (it.hasNext()) {
				  double locMoney = 0;
				  String str = it.next();
				  Bill bill = new Bill();
				  WebUtil.prepareInsertParams(bill);
				  bill.setBillName(str);
				  bill.setBillCreateTime(new Date());
				  bill.setBillPayoffTime(confirmToDate);
				  bill.setTenantId(tenantId.toString());
				  bill.setMoney(new BigDecimal(0));
				  bill.setStatus(WebConstants.BILL_STATUS.UNPAYED);
				  this.billService.insertSelective(bill);
				  // 插入账单详细
				  for(int i=0;i<billDetailList.size();i++){
					  if(str.equals(billDetailList.get(i).getBillTimeName())){
						  	BillDetail billDetail = billDetailList.get(i);
							billDetail.setBillSid(bill.getBillSid());
							locMoney += billDetail.getMoney().doubleValue();
							this.billDetailService.insertSelective(billDetail);
					  }
				  }
				  
				  // 更新月账单的金额
				  bill.setMoney(new BigDecimal(locMoney));
				  this.billService.updateByPrimaryKeySelective(bill);
			}  
			
		}
		return resultMap;
	}
	
	// 计算费用
	public HashMap<String , String> calculatedMonthly(List<BillTime> timeList,ServiceInstance serviceInstance){

		HashMap<String , String> map = new HashMap<String , String>(); 
		// 获得将时间段划分成一个月的集合
		for(BillTime time:timeList){
			map = computeServiceBillingPricing(serviceInstance,time);
		}

		return map;
	}
	
	// 计算服务资费金额
	public HashMap<String , String> computeServiceBillingPricing(ServiceInstance serviceInstance,BillTime time){
		
		HashMap<String , String> map = new HashMap<String , String>(); 
		BillDetail billDetail = new BillDetail();
		
		double localMoney = 0;
		
		// 1、获取服务规格资费定价
		BillingPlan billingPlans = billingPlanService.selectBillingByServiceSidAndStatus(serviceInstance.getServiceSid());
		if(StringUtil.isNullOrEmpty(billingPlans)){
			map.put("result", "0");
			map.put("message", "无法结算，"+serviceInstance.getServiceName()+"没有资费计划！");
			return map;
		}
		
		// 2、获得服务实例的规格及定价
		Criteria example = new Criteria();
		example.put("billingPlanSid", billingPlans.getBillingPlanSid());
		List<BillingPricing> billingPricingList = this.billingPricingService.selectByParams(example);
		if(billingPricingList == null || billingPricingList.size() == 0){
			map.put("result", "0");
			map.put("message", "无法结算，该"+billingPlans.getBillingPlanName()+"资费计划不存在资费定价！");
			return map;
		}else{
			// 获取规格组合
			for(BillingPricing price : billingPricingList){
				// 判断收费类型
				if(WebConstants.BillingChargeType.FIX_CHARGE.equals(price.getChargeType())){
					// 固定收费
					String configDetailsName = getServiceInstancePricingSpec(serviceInstance,price.getBillingConfigName(),time.getBillStartTime(),time.getBillEndTime());
					example = new Criteria(); 
					example.put("configDetailName", configDetailsName);
					List<BillingPricingDetail> list = this.billingPricingDetailService.selectByParams(example);
					if(list != null && list.size() == 1){
						// 判断该资费定价是按月收费还是按天收费
						if(WebConstants.BillingType.MONTH.equals(price.getBillingType())){
							// 按月
							billDetail.setBillingType(WebConstants.BILLING_TYPE.MONTH);
							billDetail.setUsageVolume(configDetailsName);
							// 不满一月是否按1月计算
							if(WebConstants.IS_BILLING_MONTH.YES == list.get(0).getIsMonth()){
								// 不足按一月计算
								// 单价（元/月）
								BigDecimal fixedPricing = new BigDecimal(list.get(0).getUnitPrice());

								money +=  fixedPricing.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								localMoney += fixedPricing.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
							}else{
								
								// 不足按天算
								BigDecimal Month = new BigDecimal(30);
								BigDecimal useDays = new BigDecimal(time.getTimeDaysCount());
								// 单价（元/月）
								BigDecimal fixedPricing = new BigDecimal(list.get(0).getUnitPrice());
								BigDecimal allMoney = fixedPricing.multiply(useDays);
								// 单价*时间 = 总价
								BigDecimal total = allMoney.divide(Month,2, BigDecimal.ROUND_HALF_UP);
								
								money +=  total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								localMoney += total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
							}

						}if(WebConstants.BillingType.DAY.equals(price.getBillingType())){
							// 按天
							billDetail.setBillingType(WebConstants.BILLING_TYPE.DAY);
							billDetail.setUsageVolume(configDetailsName);
							
							// 单价(元/天)
							BigDecimal fixedPricing = new BigDecimal(list.get(0).getUnitPrice());
							// 总价
							BigDecimal total = fixedPricing.multiply(new BigDecimal(time.getTimeDaysCount()));
							
							money +=  total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
							localMoney += total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
						}
					}else{
						logger.info("结算失败，服务实例ID："+serviceInstance.getInstanceSid()+"可能不存在定价明细或者没有生成相应的服务实例规格数据。");
						map.put("result", "0");
						map.put("message", "请检查是否存在定价明细！");
						return map;
					}
					
				}else{
					// 增量收费
					example = new Criteria(); 
					example.put("billingPricingSid", price.getBillingPricingSid());
					List<BillingPricingDetail> list = this.billingPricingDetailService.selectByParams(example);

					if(list != null && list.size() == 1){
						// 获取单价数量
//						String str = list.get(0).getConfigDetailName();
						String str = null;
						String[] st = str.substring(1, str.length()-1).split(":");
						int uAmount = Integer.parseInt(st[1]);
						Map<String,String> specMap = getServiceInstancePricingCount(serviceInstance,st[0],time.getBillStartTime(),time.getBillEndTime());
						// 服务使用数量
						
						double tAmount = Double.parseDouble(specMap.get("value"));
						
						// 单价 
						BigDecimal unitPrice = new BigDecimal(list.get(0).getUnitPrice());
						// 单价数量
						BigDecimal unitAmount = new BigDecimal(uAmount);
						// 总数量
						BigDecimal totalAmount = new BigDecimal(tAmount);
						// 数量 
						BigDecimal amount = totalAmount.divide(unitAmount);
						// 服务规格的价钱
						BigDecimal unitMoney = unitPrice.multiply(amount); 
						
						// 判断该资费定价是按月收费还是按天收费
						if(WebConstants.BillingType.MONTH.equals(price.getBillingType())){
							// 按月
							billDetail.setBillingType(WebConstants.BILLING_TYPE.MONTH);
							
							if(StringUtil.isNullOrEmpty(specMap.get("unit"))){
								billDetail.setUsageVolume(tAmount+"");
							}else{
								billDetail.setUsageVolume(tAmount+specMap.get("unit"));
							}
							// 当服务实例为CDN、对象存储时，按月时，不计算时间，因为值是取得一段时间内的
							if(WebConstants.ServiceSid.SERVICE_OBJ_STORAGE.equals(serviceInstance.getServiceSid())){
								// 总价
								BigDecimal total = unitMoney;
								money +=  total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								localMoney += total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
							}else if(WebConstants.ServiceSid.SERVICE_CDN.equals(serviceInstance.getServiceSid())){
								// 总价
								BigDecimal total = unitMoney;
								money +=  total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								localMoney += total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
							}else{
								
								if(WebConstants.IS_BILLING_MONTH.YES == list.get(0).getIsMonth()){
									// 时间
//									BigDecimal totalDays = new BigDecimal(time.getTimeDaysCount());
									// 总价
									BigDecimal total = unitMoney;
									money +=  total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
									localMoney += total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								}else{
									BigDecimal totalDays = new BigDecimal(time.getTimeDaysCount());
									// 总价
									BigDecimal unitTime = totalDays.divide(new BigDecimal(30),5,BigDecimal.ROUND_HALF_UP);
									BigDecimal total = unitMoney.multiply(unitTime);
									money +=  total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
									localMoney += total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								}
								
							}
						
						}if(WebConstants.BillingType.DAY.equals(price.getBillingType())){
							// 按天
							billDetail.setBillingType(WebConstants.BILLING_TYPE.DAY);
							if(StringUtil.isNullOrEmpty(specMap.get("unit"))){
								billDetail.setUsageVolume(tAmount+"");
							}else{
								billDetail.setUsageVolume(tAmount+specMap.get("unit"));
							}
							// 当CDN、对象存储的时候，由于取出的数据是很多天的数量，所以就不用再乘以天数
							if(WebConstants.ServiceSid.SERVICE_OBJ_STORAGE.equals(serviceInstance.getServiceSid())){
								// 总价
								BigDecimal total = unitMoney.multiply(new BigDecimal(1));
								
								money +=  total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								localMoney += total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
							}else if(WebConstants.ServiceSid.SERVICE_CDN.equals(serviceInstance.getServiceSid())){
								// 总价
								BigDecimal total = unitMoney.multiply(new BigDecimal(1));
								money +=  total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								localMoney += total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
							}else{
								// 总价
								BigDecimal total = unitMoney.multiply(new BigDecimal(time.getTimeDaysCount()));
								money +=  total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								localMoney += total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
							}
						}
						
					}else{
						map.put("result", "0");
						map.put("message", "还不存在"+price.getBillingConfigName()+"配置详情的定价");
						return map;
					}
				}
			}
		}
		// 插入服务账单每月详情
		WebUtil.prepareInsertParams(billDetail);
		billDetail.setServiceSid(serviceInstance.getServiceSid().toString());
		billDetail.setServiceInstanceSid(serviceInstance.getInstanceSid().toString());
		
		//　当前月 billTimeName,比如：2015年3月
		billDetail.setBillTimeName(time.getBillTimeName());
		// 当前月天数
		billDetail.setLongUse((float) time.getTimeDaysCount());
		// 开始时间
		billDetail.setBillingStartTime(time.getBillStartTime());
		// 结束时间
		billDetail.setBillingEndTime(time.getBillEndTime());
		// 当前月该服务使用的费用
		billDetail.setMoney(new BigDecimal(localMoney));
		
		billDetail.setStatus(WebConstants.BILL_STATUS.UNPAYED);
		billDetailList.add(billDetail);
		
		map.put("result", "1");
		map.put("message", "成功");
		return map;
	}
	
	// 固定收费，获取服务实例定价规格
	public String getServiceInstancePricingSpec(ServiceInstance instance,String billingConfigName,String startTime,String endTime){
		String json = "{";
		String[] str = billingConfigName.substring(1, billingConfigName.length()-1).split(",");
		// 固定收费中，不会存在对象存储和CDN服务
		for(int i=0;i<str.length;i++){
			List<ServiceInstanceSpec> list = this.serviceInstanceSpecService.selectByInstanceSid(instance.getInstanceSid());
			for(ServiceInstanceSpec spec : list){
				if(str[i].equals(spec.getName())){
					if(i == str.length -1){
						json += spec.getName()+":"+spec.getValue()+"}";
					}else{
						json += spec.getName()+":"+spec.getValue()+",";
					}
				}
			}
		}
		
		return json;
	}
	
	// 增量计费，获取服务的使用数量
	public Map<String,String> getServiceInstancePricingCount(ServiceInstance instance,String name,String startTime,String endTime){
		Double count = 0d;
		
		Map<String,String> specMap = new HashMap<String,String>();
		
		if(WebConstants.ServiceSid.SERVICE_OBJ_STORAGE.equals(instance.getServiceSid())){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date start = null;
			Date end = null;
			try {
				start = df.parse(startTime+" 00:00:00");
				end = df.parse(endTime + " 23:59:59");
			} catch (ParseException e) {
				e.printStackTrace();
			}

			ResOSUsageSum osUsage = this.resObjectStorageUsageService.getUsageSum(instance.getMgtObjSid(), start, end);
			if(!StringUtil.isNullOrEmpty(osUsage)){
				Criteria criteria = new Criteria();
				criteria.put("serviceSid", instance.getServiceSid());
				criteria.put("name", name);
				List<ServiceSpec> list = this.serviceSpecService.selectByParams(criteria);
				// 单位转换
//				count = unitConversion(osUsage.getData(),osUsage.getUnit(),list.get(0).getUnit());
				specMap.put("unit", list.get(0).getUnit());
				if(count == 0){
					logger.debug("该对象存储服务实例SID："+instance.getInstanceSid()+" 的总流量为0");
				}
			}
		// 当服务实例为CDN时
		}else if(WebConstants.ServiceSid.SERVICE_CDN.equals(instance.getServiceSid())){
			Long mgtObjSid = instance.getMgtObjSid();
			Criteria example = new Criteria();
			example.put("mgtObjSid", mgtObjSid);
//			example.put("status","normal");
//			List<ResCdnInst> cdnList = resCdnInstService.selectByParams(example);
			List<ResCdnInst> cdnList = null;
			if(cdnList != null && cdnList.size() > 0){
				Criteria criteria = new Criteria();
				criteria.put("serviceSid", instance.getServiceSid());
				criteria.put("name", name);
				List<ServiceSpec> list = this.serviceSpecService.selectByParams(criteria);

				// 单位转换
				ResCdnMgtObjFlow cdn = resCdnInstService.getCDNFlow(cdnList.get(0).getCdnInstSid(), startTime, endTime);

				count = unitConversion(Long.parseLong(cdn.getTotalFlow()),"MB",list.get(0).getUnit());

				specMap.put("unit", "MB");
				if(0L == count){
					logger.debug("该CDN服务实例SID："+instance.getInstanceSid()+" 的总流量为0");
				}
			}else{
				logger.debug("该CDN服务实例SID："+instance.getInstanceSid()+" 的总流量为0");
			}

		}else{
			Criteria criteria = new Criteria();
			criteria.put("instanceSid", instance.getInstanceSid());
			criteria.put("name", name);
			List<ServiceInstanceSpec> list = this.serviceInstanceSpecService.selectByParams(criteria);
			if(list != null && list.size() == 1){
				count = Double.parseDouble(list.get(0).getValue());
				specMap.put("unit", list.get(0).getUnit());
			}
		}
		specMap.put("value", count.toString());
		return specMap;
	}
	
	//添加年月
	public Date addDateNum(Date date, String type, int num) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar cd = Calendar.getInstance();
		Date confirmDate = new Date();

		try {
			 cd.setTime(sdf.parse(StringUtil.dateFormat(date)));
			 cd.add(Calendar.DAY_OF_MONTH, -1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if (type.equals("Month")) {
			cd.add(Calendar.MONTH, num);
			confirmDate = cd.getTime();
		} else if (type.equals("Year")) {
			cd.add(Calendar.YEAR, num);
			confirmDate = cd.getTime();
		} else {
			confirmDate = cd.getTime();
		}
		return confirmDate;
	}
	
	// 单位转换方法,TB、GB、MB、KB、B
	public double unitConversion(Long value,String unit,String targetUnit){
		
		BigDecimal val = new BigDecimal(value);
		BigDecimal bd = new BigDecimal(1024);
		// 小转大
		if(unit.equals("B") && targetUnit.equals("KB")){
			BigDecimal bigDec = val.divide(bd,2, BigDecimal.ROUND_HALF_UP);
			return bigDec.doubleValue();
		}else if(unit.equals("B") && targetUnit.equals("MB")){
			BigDecimal mul = bd.multiply(bd);
			BigDecimal bigDec = val.divide(mul,2, BigDecimal.ROUND_HALF_UP);
			return bigDec.doubleValue();
		}else if(unit.equals("B") && targetUnit.equals("GB")){
			BigDecimal mul = bd.multiply(bd).multiply(bd);
			BigDecimal bigDec = val.divide(mul,2, BigDecimal.ROUND_HALF_UP);
			return bigDec.doubleValue();
		}else if(unit.equals("B") && targetUnit.equals("TB")){
			BigDecimal mul = bd.multiply(bd).multiply(bd).multiply(bd);
			BigDecimal bigDec = val.divide(mul,2, BigDecimal.ROUND_HALF_UP);
			return bigDec.doubleValue();
		}else if(unit.equals("KB") && targetUnit.equals("MB")){
			BigDecimal bigDec = val.divide(bd,2, BigDecimal.ROUND_HALF_UP);
			return bigDec.doubleValue();
		}else if(unit.equals("KB") && targetUnit.equals("GB")){
			BigDecimal mul = bd.multiply(bd);
			BigDecimal bigDec = val.divide(mul,2, BigDecimal.ROUND_HALF_UP);
			return bigDec.doubleValue();
		}else if(unit.equals("KB") && targetUnit.equals("TB")){
			BigDecimal mul = bd.multiply(bd).multiply(bd);
			BigDecimal bigDec = val.divide(mul,2, BigDecimal.ROUND_HALF_UP);
			return bigDec.doubleValue();
		}else if(unit.equals("MB") && targetUnit.equals("GB")){
			BigDecimal bigDec = val.divide(bd,2, BigDecimal.ROUND_HALF_UP);
			return bigDec.doubleValue();
		}else if(unit.equals("MB") && targetUnit.equals("TB")){
			BigDecimal mul = bd.multiply(bd);
			BigDecimal bigDec = val.divide(mul,2, BigDecimal.ROUND_HALF_UP);
			return bigDec.doubleValue();
		}else if(unit.equals("GB") && targetUnit.equals("TB")){
			BigDecimal bigDec = val.divide(bd,2, BigDecimal.ROUND_HALF_UP);
			return bigDec.doubleValue();
			// 大转小
		}else if(unit.equals("TB") && targetUnit.equals("GB")){
			return value*1024;
		}else if(unit.equals("TB") && targetUnit.equals("MB")){
			return value*1024*1024;
		}else if(unit.equals("TB") && targetUnit.equals("KB")){
			return value*1024*1024*1024;
		}else if(unit.equals("TB") && targetUnit.equals("B")){
			return value*1024*1024*1024*1024;
		}else if(unit.equals("GB") && targetUnit.equals("MB")){
			return value*1024;
		}else if(unit.equals("GB") && targetUnit.equals("KB")){
			return value*1024*1024;
		}else if(unit.equals("GB") && targetUnit.equals("B")){
			return value*1024*1024*1024;
		}else if(unit.equals("MB") && targetUnit.equals("KB")){
			return value*1024;
		}else if(unit.equals("MB") && targetUnit.equals("B")){
			return value*1024*1024;
		}else if(unit.equals("KB") && targetUnit.equals("B")){
			return value*1024;
		}else{
			return value;
		}
		
	}
	
	// 计算两个时间之间相差的天数
	public long getDaysFromTwoDate(Date fromDate, Date toDate) {
			
		Long time =toDate.getTime()-fromDate.getTime();
		
//		long days = time/1000/60/60/24;

		return time;
	}
	
	// 计算2个时间之差，并且以每月的形式展示出来，不足一个月的，用天数展示出来
	public List<BillTime> getTwoTimesxDisplayMonthly(Date fromDate,Date toDate){
		Calendar fromDate1 = Calendar.getInstance();
		fromDate1.setTime(fromDate);
		
		Calendar toDate1 = Calendar.getInstance();
		toDate1.setTime(toDate);
		
		// 组装参数
		List<BillTime> list = new ArrayList<BillTime>();
		
		// 判断起始时间是否小于结束时间
		if(fromDate1.after(toDate1)){
			return list;
		}
		
		int startYear = fromDate1.get(Calendar.YEAR);
		int endYear = toDate1.get(Calendar.YEAR);
		int startMonth = fromDate1.get(Calendar.MONTH)+1;
		int endMonth = toDate1.get(Calendar.MONTH)+1;
		int maxStartDay = fromDate1.getActualMaximum(Calendar.DAY_OF_MONTH);
		int maxEndDay = toDate1.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		int startDay = fromDate1.get(Calendar.DAY_OF_MONTH);
		int endDay = toDate1.get(Calendar.DAY_OF_MONTH);
		
		// 计算不满1月时间的长度
		if(startYear == endYear && startMonth == endMonth){
			// 计算不满1月时间的长度
			BillTime demostart = new BillTime();
			if(startMonth == 2){
				if((endDay-startDay) >=27){
					demostart.setTimeDaysCount(30);
					demostart.setBillStartTime(startYear+"-"+startMonth+"-"+startDay);
					demostart.setBillEndTime(startYear+"-"+startMonth+"-"+endDay);
					
					demostart.setBillTimeName(startYear+"年"+startMonth+"月");
				}else{
					demostart.setTimeDaysCount(endDay-startDay+1);
					demostart.setBillStartTime(startYear+"-"+startMonth+"-"+startDay);
					demostart.setBillEndTime(startYear+"-"+startMonth+"-"+endDay);
					demostart.setBillTimeName(startYear+"年"+startMonth+"月");
				}
			}else{
				if((endDay-startDay) >=30){
					demostart.setTimeDaysCount(30);
					demostart.setBillStartTime(startYear+"-"+startMonth+"-"+startDay);
					demostart.setBillEndTime(startYear+"-"+startMonth+"-"+endDay);
					demostart.setBillTimeName(startYear+"年"+startMonth+"月");
				}else{
					demostart.setTimeDaysCount(endDay-startDay+1);
					demostart.setBillStartTime(startYear+"-"+startMonth+"-"+startDay);
					demostart.setBillEndTime(startYear+"-"+startMonth+"-"+endDay);
					demostart.setBillTimeName(startYear+"年"+startMonth+"月");
				}
			}
			list.add(demostart);
		}else{
			// 计算不满1月时间的长度
			BillTime demostart = new BillTime();
			if(startMonth == 2){
				if((maxStartDay-startDay) >=27){
					demostart.setTimeDaysCount(30);
					demostart.setBillStartTime(startYear+"-"+startMonth+"-"+startDay);
					demostart.setBillEndTime(startYear+"-"+startMonth+"-"+maxStartDay);
					demostart.setBillTimeName(startYear+"年"+startMonth+"月");
				}else{
					demostart.setTimeDaysCount(maxStartDay-startDay+1);
					demostart.setBillStartTime(startYear+"-"+startMonth+"-"+startDay);
					demostart.setBillEndTime(startYear+"-"+startMonth+"-"+maxStartDay);
					demostart.setBillTimeName(startYear+"年"+startMonth+"月");
				}
			}else{
				if((maxStartDay-startDay) >=30){
					demostart.setTimeDaysCount(30);
					demostart.setBillStartTime(startYear+"-"+startMonth+"-"+startDay);
					demostart.setBillEndTime(startYear+"-"+startMonth+"-"+maxStartDay);
					demostart.setBillTimeName(startYear+"年"+startMonth+"月");
				}else{
					demostart.setTimeDaysCount(maxStartDay-startDay+1);
					demostart.setBillStartTime(startYear+"-"+startMonth+"-"+startDay);
					demostart.setBillEndTime(startYear+"-"+startMonth+"-"+maxStartDay);
					demostart.setBillTimeName(startYear+"年"+startMonth+"月");
				}
			}
			list.add(demostart);
			// 计算满月的时间段
			for(int i=startYear;i<=endYear;i++){
				
				for(int j=1;j<13;j++){
					BillTime time = new BillTime();
					
					if(startMonth+j > 12){
						startMonth = 0;
						break;
					}
					if(endYear == i){
						if(startMonth+j > (endMonth-1))
						break;
					}
					time.setTimeDaysCount(30);
					// 计算一个月中最大的那天
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					int maxMonth = 30;
					try {
						Date timeMaxMonth = df.parse(startYear+"-"+(startMonth+j)+"-01");
						Calendar timeMaxCalendar = Calendar.getInstance();
						timeMaxCalendar.setTime(timeMaxMonth);
						maxMonth = timeMaxCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					time.setBillStartTime(startYear+"-"+(startMonth+j)+"-01");
					time.setBillEndTime(startYear+"-"+(startMonth+j)+"-"+maxMonth);
					time.setBillTimeName(i+"年"+(startMonth+j)+"月");
					list.add(time);
				}
			}
			// 计算不满1月时间的长度
			BillTime demoend = new BillTime();
			if(endMonth == 2){
				if((maxEndDay-endDay) <=1){
					demoend.setTimeDaysCount(30);
					demoend.setBillStartTime(startYear+"-"+endMonth+"-01");
					demoend.setBillEndTime(startYear+"-"+endMonth+"-"+endDay);
					demoend.setBillTimeName(endYear+"年"+endMonth+"月");
				}else{
					demoend.setTimeDaysCount(endDay);
					demoend.setBillStartTime(startYear+"-"+endMonth+"-01");
					demoend.setBillEndTime(startYear+"-"+endMonth+"-"+endDay);
					demoend.setBillTimeName(endYear+"年"+endMonth+"月");
				}
			}else{
				if((maxEndDay-endDay) <= 1){
					demoend.setTimeDaysCount(30);
					demoend.setBillStartTime(startYear+"-"+endMonth+"-01");
					demoend.setBillEndTime(startYear+"-"+endMonth+"-"+endDay);
					demoend.setBillTimeName(endYear+"年"+endMonth+"月");
				}else{
					demoend.setTimeDaysCount(endDay);
					demoend.setBillStartTime(startYear+"-"+endMonth+"-01");
					demoend.setBillEndTime(startYear+"-"+endMonth+"-"+endDay);
					demoend.setBillTimeName(endYear+"年"+endMonth+"月");
				}
			}
			list.add(demoend);
		}
		
		return list;
	}
	
	/**
	 * 查询账单明细
	 */
	@Override
	@WebMethod
	@GET
	@Path("/findBillDetails/{billSid}/{billName}")
	public Response findBillDetails(@PathParam("billSid") Long billSid,@PathParam("billName") String billName) {
		
		Criteria criteria= new Criteria();
		criteria.put("billSid", billSid);
		criteria.put("billName", billName);
		List<BillDetail> list = this.billDetailService.selectByParams(criteria);
		
		String json = JsonUtil.toJson(list);
		return Response.status(Status.OK).entity(json).build();
	}
	
	/**
	 * 缴费
	 */
	@Override
	@WebMethod
	@POST
	@Path("/updateBill")
	public Response updateBill(Bill bill) {
		String returnJson;

		WebUtil.prepareInsertParams(bill);
		bill.setStatus(WebConstants.BILL_STATUS.PAYED);
		int result = this.billService.updateByPrimaryKeySelective(bill);
		if (1 == result) {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
					.getMessage("缴费成功！"), null));
		} else {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage("缴费失败！"), null));
		}

		return Response.status(Status.OK).entity(returnJson).build();
	}
	
	/**
	 * 新增资费定价
	 * 
	 * @param billingPricing
	 * @return
	 */
	@Override
	@WebMethod
	@POST
	@Path("/create/BillingPrice")
	public Response saveCreateBillingPrice(BillingPricing billingPricing,@Context HttpServletRequest servletRequest) {
		AuthUser authUserInfo = RequestContextUtil.getAuthUserInfo(servletRequest);

		String returnJson;
		WebUtil.prepareInsertParams(billingPricing,authUserInfo);
		int result = this.billingPricingService.addBillingPrice(billingPricing);
		if (1 == result) {
			returnJson = JsonUtil.toJson(new RestResult(billingPricing));
		} else {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage("添加失败!"), null));
		}
		return Response.status(Status.OK).entity(returnJson).build();
	}
	
	public String[] getBillingPricingServicePesc(List<BillingPricing> list){
		Set<String> set = new TreeSet<String>();
		String[] st = null;
//		for(BillingPricing spcing : list){
//			String[] str = spcing.getBillingSpecSid().split(",");
//			for (int i = 0; i < str.length; i++) {
//				set.add(str[i]);
//			}
//		}
//
//		st = (String[]) set.toArray(new String[0]);
		
		return st;
	}
	
	/**
	 * 查询所有定价详细集合
	 * 
	 * @param pricingDetail
	 * @return
	 */
	@Override
	@WebMethod
	@POST
	@Path("/billingPricingDetail/findAll")
	public Response findAllBillingPricingDetail(
			BillingPricingDetail pricingDetail) {
		
		Criteria example = new Criteria();
		
		List<BillingPricingDetail> list = this.billingPricingDetailService.selectByParams(example);

		return Response.status(Status.OK).entity(new RestResult(list)).build();
	}
	
	
	/**
	 * 新增资费定价明细details
	 * 
	 * @param billingPricingDetail
	 * @return
	 */
	@Override
	@WebMethod
	@POST
	@Path("/create/BillingPriceDetails")
	public Response saveCreateBillingPriceDetails(BillingPricingDetail billingPricingDetail) {
		
		String returnJson;
		WebUtil.prepareInsertParams(billingPricingDetail);
		int result = this.billingPricingDetailService.insertSelective(billingPricingDetail);
		if (1 == result) {
			returnJson = JsonUtil.toJson(billingPricingDetail);
		} else {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage("定价失败!"), null));
		}

		return Response.status(Status.OK).entity(returnJson).build();
	}
	
	/**
	 * 更新资费定价
	 * 
	 * @param billingPricing
	 * @return
	 */
	@Override
	@WebMethod
	@POST
	@Path("/update/BillingPrice")
	public Response updateCreateBillingPrice(BillingPricing billingPricing) {
		String returnJson;
		WebUtil.prepareUpdateParams(billingPricing);
		int result = this.billingPricingService.updateByPrimaryKeySelective(billingPricing);
		if (1 == result) {
			returnJson = JsonUtil.toJson(billingPricing);
		} else {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage("操作失败!"), null));
		}

		return Response.status(Status.OK).entity(returnJson).build();
	}
	
	/**
	 * 删除资费定价明细details
	 * 
	 * @param pricingDetailSid
	 * @return
	 */
	@Override
	@WebMethod
	@GET
	@Path("/delete/BillingPriceDetails/{pricingDetailSid}")
	public Response deleteCreateBillingPriceDetails(
			@PathParam("pricingDetailSid") Long pricingDetailSid) {
		
		String returnJson;
		int result = this.billingPricingDetailService.deleteByPrimaryKey(pricingDetailSid);
		if (1 == result) {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
					.getMessage("删除成功！"), null));
		} else {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage("删除失败!"), null));
		}

		return Response.status(Status.OK).entity(returnJson).build();
	}
	

	/**
	 * 删除资费定价明细
	 * 
	 * @param billingPricingSid
	 * @return
	 */
	@Override
	@WebMethod
	@GET
	@Path("/delete/BillingPricing/{billingPricingSid}")
	public Response deleteBillingPricing(
			@PathParam("billingPricingSid") Long billingPricingSid) {
		
		String returnJson;
		// 删除资费定价明细details
		Criteria example = new Criteria();
		example.put("billingPricingSid", billingPricingSid);
		this.billingPricingDetailService.deleteByParams(example);
		// 删除资费定价
		int result = this.billingPricingService.deleteByPrimaryKey(billingPricingSid);
		if (1 == result) {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
					.getMessage("删除成功！"), null));
		} else {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage("删除失败!"), null));
		}

		return Response.status(Status.OK).entity(returnJson).build();
	}
	
	/**
	 * 判断是否有相同的定价明细
	 * 
	 * @param billingPricingSid
	 * @return
	 */
	@Override
	@WebMethod
	@GET
	@Path("/checkSamePricingConfig/BillingPriceDetails/{billingPricingSid}/{billingConfiguration}")
	public Response checkSamePricingConfig(
			@PathParam("billingPricingSid") Long billingPricingSid,
			@PathParam("billingConfiguration") String billingConfiguration) {
		
		Criteria example = new Criteria();
		example.put("billingPricingSid", billingPricingSid);
		example.put("configDetailName", billingConfiguration);
		List<BillingPricingDetail> list = this.billingPricingDetailService.selectByParams(example);
		Map<String,String> map = new HashMap<String,String>();
		if(list != null && list.size() > 0){
			map.put("result", "false");
		}else{
			map.put("result", "true");
		}
		
		return Response.status(Status.OK).entity(JsonUtil.toJson(map)).build();
	}

	
	/**
	 * 获取配置项规格信息
	 * 
	 * @param specSids
	 * @return
	 */
	@Override
	@WebMethod
	@GET
	@Path("/getServiceSpecDetailsInfo/{specSids}")
	public Response getServiceSpecDetailsInfo(
			@PathParam("specSids") String specSids) {
		String[] str = specSids.split(",");
		List<ServiceSpec> list = new ArrayList<ServiceSpec>();
		for(int i=0;i<str.length;i++){
			ServiceSpec spec = this.serviceSpecService.selectByPrimaryKey(Long.parseLong(str[i]));
			list.add(spec);
		}
		return Response.status(Status.OK).entity(JsonUtil.toJson(list)).build();
	}

	@Override
	public Response createPriceDetails(List<PricingDetailParamVo> pricingDetailParamVos,@Context HttpServletRequest servletRequest) {
		if(!StringUtil.isNullOrEmpty(pricingDetailParamVos) && pricingDetailParamVos.size()>0){
			AuthUser authUser =RequestContextUtil.getAuthUserInfo(servletRequest);
			BillingPricingDetail billingPricingDetail = new BillingPricingDetail();

			billingPricingDetail.setBillingPricingSid(pricingDetailParamVos.get(0).getBillingPricingSid());
			billingPricingDetail.setSpecSid(pricingDetailParamVos.get(0).getSpecSid());
			billingPricingDetail.setDiscount(pricingDetailParamVos.get(0).getDiscount());
			billingPricingDetail.setStatus(WebConstants.BILL_PLAN_STATUS.ABLE);
			billingPricingDetail.setUnitPrice(0F);
			billingPricingDetail.setName(pricingDetailParamVos.get(0).getName());
			billingPricingDetail.setChargeUnit(WebConstants.CHARGE_UNIT_ZH.RMB);
			List<Map<String,String>> values = new ArrayList<>();
			pricingDetailParamVos.forEach(pdpv ->{
				Map<String,String> specModules = new LinkedHashMap<String, String>();
				specModules.put("moduleName",pdpv.getModuleName());
				specModules.put("moduleCode",pdpv.getModuleCode());
				specModules.put("moduleValue",pdpv.getModuleValue());
				specModules.put("billingChargeType",pdpv.getBillingChargeType());
				if(pdpv.getBillingChargeType().equals(WebConstants.BillingChargeType.INCREMENT_CHARGE))
					specModules.put("initPrice",pdpv.getInitPrice());
				specModules.put("unitPrice",StringUtil.isNullOrEmpty(pdpv.getUnitPrice())?"0":pdpv.getUnitPrice());
				values.add(specModules);
			});
			billingPricingDetail.setValue(StringUtil.formatJson(JsonUtil.toJson(values)));
			WebUtil.prepareInsertParams(billingPricingDetail,authUser);
			this.billingPricingDetailService.insertSelective(billingPricingDetail);
		}
		return Response.status(Status.OK).entity(new RestResult(true)).build();
	}

	/**
	 * 租户充值/扣款
	 * 
	 * @param billSid
	 * @param mgtObjSid
	 * @param amount
	 * @param flag
	 * @return
	 */
	@Override
	@WebMethod
	@GET
	@Path("/prepaidDebit/{billSid}/{mgtObjSid}/{amount}/{flag}")
	public Response mgtObjPrepaidDebit(
			@PathParam("billSid") Long billSid,
			@PathParam("mgtObjSid") Long mgtObjSid,
			@PathParam("amount") String amount,
			@PathParam("flag") boolean flag) {
		
		String returnJson;
		
		Criteria example = new Criteria();
		example.put("mgtObjSid", mgtObjSid);
		List<BillingAccountMgtObj> billList = this.billingAccountMgtObjService.selectByParams(example);
		// 充值/扣款金额
		double amountDouble = Double.parseDouble(amount);
		BigDecimal amountDec = new BigDecimal(amountDouble);
			
		boolean result = false;
		try {
			if(billList != null && billList.size() > 0){
				result = this.billingAccountService.updateAccount(billList.get(0).getAccountSid(), amountDec, flag);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result) {
			// 修改成功，更新用户账单表
			Bill billAccount = this.billService.selectByPrimaryKey(billSid);
			billAccount.setPaymentAmount(Double.parseDouble(amount));
			billAccount.setStatus(WebConstants.BILL_STATUS.PAYED);
			billAccount.setPaymentTime(new Date());
			this.billService.updateByPrimaryKeySelective(billAccount);
			
			if(flag){
				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
						.getMessage("扣款成功！"), null));
			}else{
				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
						.getMessage("充值成功！"), null));
			}
		} else {
			if(flag){
				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
						.getMessage("扣款失败！"), null));
			}else{
				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
						.getMessage("充值失败！"), null));
			}
		}

		return Response.status(Status.OK).entity(returnJson).build();
	}

	/**
	 * 资费结算当前月
	 * 
	 * @param tenantId
	 * @return
	 */
	@Override
	@WebMethod
	@GET
	@Path("/current/billingbalance/{tenantId}")
	public Response billingBalanceCurrentMonth(@PathParam("tenantId") Long tenantId) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Bill> billList = new ArrayList<Bill>();
		List<BillDetail> billDetailsList = new ArrayList<BillDetail>();
		currentMoney = 0;
		// 获取当前月时间
		Calendar ca = Calendar.getInstance();      
		int year = ca.get(Calendar.YEAR);//获取年份      
		int month=ca.get(Calendar.MONTH)+1;//获取月份      
		int day=ca.get(Calendar.DATE);//获取日
		String startTime = year+"-"+(month > 9?month:"0"+month)+"-01 00:00:00";
		String toTime = year+"-"+(month > 9?month:"0"+month)+"-"+(day > 9?day:"0"+day)+" 23:59:59";
		
		// 根据时间，查询租户下需要结算的服务实例
		Criteria example = new Criteria();
		example.put("tanentId", tenantId);
		example.put("isBillingStatus", "yes");
		example.put("billingEndTime", startTime);
		example.put("confirmToDate", toTime);
//		example.put("instanceSid", 6089);
		List<ServiceInstance> instanceList = this.serviceInstanceService.selectByParams(example);
		
		Bill bill = new Bill();
		WebUtil.prepareInsertParams(bill);
		bill.setBillName(year+"年"+month+"月");
		bill.setBillCreateTime(new Date());
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date Payoff = null;
		try {
		Payoff = sdf.parse(toTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(instanceList != null && instanceList.size() > 0){
			//根据服务实例计算当月每个服务实例的价钱
			billDetailsList = CalculationCurrentMonthBillingTime(instanceList,startTime,toTime);
			
		}else{
			billDetailsList = null;
		}
		bill.setBillPayoffTime(Payoff);
		bill.setTenantId(tenantId.toString());
		BigDecimal bigMoney = new BigDecimal(currentMoney);
		BigDecimal allMoney = bigMoney.divide(new BigDecimal(1),2, BigDecimal.ROUND_HALF_UP);
		
		bill.setMoney(allMoney);
		bill.setStatus(WebConstants.BILL_STATUS.UNPAYED);
		billList.add(bill);
		
		map.put("billList", billList);
		map.put("billDetailList", billDetailsList);
		
		return Response.status(Status.OK).entity(JsonUtil.toJson(map)).build();
	}
	
	// 计算当前月服务实例的结算时间
	public List<BillDetail> CalculationCurrentMonthBillingTime(List<ServiceInstance> serviceInstances,String startTime,String toTime){
		
		// 变量
		List<BillDetail> billDetailsList = new ArrayList<BillDetail>();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = null;
		Date toDate = null;
		try {
			startDate = df.parse(startTime);
			toDate = df.parse(toTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		boolean isFirstflag = true;
		for (ServiceInstance serviceInstance : serviceInstances) {
			if(107 == serviceInstance.getServiceSid()){
				// CDN服务只能保留一个
				if(isFirstflag){
					serviceInstance.setInstanceName("CDN服务");
					serviceInstance.setStatus("99");
					SimpleDateFormat cdnSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						serviceInstance.setDredgeDate(cdnSdf.parse(startTime));
						serviceInstance.setDestroyDate(cdnSdf.parse(toTime));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
				}else{
					continue;
				}
			}
			// 服务实例是：已退订、退订中
			if(WebConstants.ServiceInstanceStatus.CANCELING.equals(serviceInstance.getStatus()) 
			   || WebConstants.ServiceInstanceStatus.CANCELED.equals(serviceInstance.getStatus())){
				// 当服务实例为已退订时,还没有结算过
				if(StringUtil.isNullOrEmpty(serviceInstance.getBillingEndTime())){
										
					// 加上判断开始时间是否在统计范围内
					long startDredgeCount = getDaysFromTwoDate(startDate,serviceInstance.getDredgeDate());
					// 判断当前服务退订时间，是否在统计时间内
					long startCount = getDaysFromTwoDate(startDate,serviceInstance.getDestroyDate());
					long endCount = getDaysFromTwoDate(serviceInstance.getDestroyDate(),toDate);
					if(startCount < 0){
						continue;
					}
					
					// 服务退订时间和服务开通时间都在统计时间内
					if(endCount >= 0 && startCount >= 0 && startDredgeCount >= 0){
						List<BillTime> timeList = getTwoTimesxDisplayMonthly(serviceInstance.getDredgeDate(),serviceInstance.getDestroyDate());
						for(BillTime time:timeList){
							BillDetail bd = CalculationCurrentMonthBillingMoney(serviceInstance,time);
							if(!StringUtil.isNullOrEmpty(bd)){
								if(107 == serviceInstance.getServiceSid()){
									isFirstflag = false;
								}
								billDetailsList.add(bd);
							}
						}
					}
					
					// 服务退订时间在统计时间内
					if(endCount >= 0 && startCount >= 0 && startDredgeCount < 0){
						List<BillTime> timeList = getTwoTimesxDisplayMonthly(startDate,serviceInstance.getDestroyDate());
						for(BillTime time:timeList){
							BillDetail bd = CalculationCurrentMonthBillingMoney(serviceInstance,time);
							if(!StringUtil.isNullOrEmpty(bd)){
								if(107 == serviceInstance.getServiceSid()){
									isFirstflag = false;
								}
								billDetailsList.add(bd);
							}
						}
					}
					
					// 服务退订时间大于统计结束时间,服务开通时间 > 开始统计时间
					if(endCount < 0 && startDredgeCount >= 0){
						List<BillTime> timeList = getTwoTimesxDisplayMonthly(serviceInstance.getDredgeDate(),toDate);
						for(BillTime time:timeList){
							BillDetail bd = CalculationCurrentMonthBillingMoney(serviceInstance,time);
							if(!StringUtil.isNullOrEmpty(bd)){
								if(107 == serviceInstance.getServiceSid()){
									isFirstflag = false;
								}
								billDetailsList.add(bd);
							}
						}
					}
					// 服务退订大于统计时间
					if(endCount < 0 && startDredgeCount < 0){
						List<BillTime> timeList = getTwoTimesxDisplayMonthly(startDate,toDate);
						for(BillTime time:timeList){
							BillDetail bd = CalculationCurrentMonthBillingMoney(serviceInstance,time);
							if(!StringUtil.isNullOrEmpty(bd)){
								if(107 == serviceInstance.getServiceSid()){
									isFirstflag = false;
								}
								billDetailsList.add(bd);
							}
						}
					}
				}else{
					long billingCount = getDaysFromTwoDate(serviceInstance.getBillingEndTime(),serviceInstance.getDestroyDate());
					// 判断当前服务退订时间，是否在统计时间内
					long startCount = getDaysFromTwoDate(startDate,serviceInstance.getDestroyDate());
					long endCount = getDaysFromTwoDate(serviceInstance.getDestroyDate(),toDate);
					
					// 已经被结算了
					if(billingCount <= 0){
						continue;
					}
					
					// 退订时间大于最后一次结算时间，且小于截止时间
					if(startCount >= 0 && endCount >= 0){
						List<BillTime> timeList = getTwoTimesxDisplayMonthly(startDate,serviceInstance.getDestroyDate());
						for(BillTime time:timeList){
							BillDetail bd = CalculationCurrentMonthBillingMoney(serviceInstance,time);
							if(!StringUtil.isNullOrEmpty(bd)){
								if(107 == serviceInstance.getServiceSid()){
									isFirstflag = false;
								}
								billDetailsList.add(bd);
							}
						}
					}
					
					// 退订时间大于截止时间
					if(startCount > 0 && endCount < 0){
						List<BillTime> timeList = getTwoTimesxDisplayMonthly(startDate,toDate);
						for(BillTime time:timeList){
							BillDetail bd = CalculationCurrentMonthBillingMoney(serviceInstance,time);
							if(!StringUtil.isNullOrEmpty(bd)){
								if(107 == serviceInstance.getServiceSid()){
									isFirstflag = false;
								}
								billDetailsList.add(bd);
							}
						}
					}
				}
			// 服务实例是：已开通、变更中	
			}else if(WebConstants.ServiceInstanceStatus.OPENED.equals(serviceInstance.getStatus())
					|| WebConstants.ServiceInstanceStatus.CHANGEING.equals(serviceInstance.getStatus())){
				
				long startCount = getDaysFromTwoDate(startDate,serviceInstance.getDredgeDate());
				long endCount = getDaysFromTwoDate(serviceInstance.getDredgeDate(),toDate);
				// 时间段右边
				if(endCount < 0){
					continue;
				}
				// 时间段左边
				if(startCount < 0){
					List<BillTime> timeList = getTwoTimesxDisplayMonthly(startDate,toDate);
					for(BillTime time:timeList){
						BillDetail bd = CalculationCurrentMonthBillingMoney(serviceInstance,time);
						if(!StringUtil.isNullOrEmpty(bd)){
							if(107 == serviceInstance.getServiceSid()){
								isFirstflag = false;
							}
							billDetailsList.add(bd);
						}
					}
				}
				// 在时间段中间
				if(endCount >=0 && startCount >= 0){
					List<BillTime> timeList = getTwoTimesxDisplayMonthly(serviceInstance.getDredgeDate(),toDate);
					for(BillTime time:timeList){
						BillDetail bd = CalculationCurrentMonthBillingMoney(serviceInstance,time);
						if(!StringUtil.isNullOrEmpty(bd)){
							if(107 == serviceInstance.getServiceSid()){
								isFirstflag = false;
							}
							billDetailsList.add(bd);
						}
					}
				}
				
			}else{
				// 中断本次循环
				continue;
			}
		}
		
		return billDetailsList;
	}
	
	// 计算当前月用的钱
	public BillDetail CalculationCurrentMonthBillingMoney(ServiceInstance serviceInstance,BillTime time){
		BillDetail  billDetail = new BillDetail();
		double localMoney = 0;
		
		// 1、获取服务规格资费定价
		BillingPlan billingPlans = billingPlanService.selectBillingByServiceSidAndStatus(serviceInstance.getServiceSid());
		if(StringUtil.isNullOrEmpty(billingPlans)){
			return null;
		}
		
		// 2、获得服务实例的规格及定价
		Criteria example = new Criteria();
		example.put("billingPlanSid", billingPlans.getBillingPlanSid());
		List<BillingPricing> billingPricingList = this.billingPricingService.selectByParams(example);
		if(billingPricingList == null || billingPricingList.size() == 0){
			return null;
		}else{
			// 获取规格组合
			for(BillingPricing price : billingPricingList){
				// 判断收费类型
				if(WebConstants.BillingChargeType.FIX_CHARGE.equals(price.getChargeType())){
					// 固定收费
					String configDetailsName = getServiceInstancePricingSpec(serviceInstance,price.getBillingConfigName(),time.getBillStartTime(),time.getBillEndTime());
					example = new Criteria(); 
					example.put("configDetailName", configDetailsName);
					List<BillingPricingDetail> list = this.billingPricingDetailService.selectByParams(example);
					if(list != null && list.size() == 1){
						// 判断该资费定价是按月收费还是按天收费
						if(WebConstants.BillingType.MONTH.equals(price.getBillingType())){
							// 按月
							billDetail.setBillingType(WebConstants.BILLING_TYPE.MONTH);
							billDetail.setBillingTypeName("按月");
							billDetail.setUsageVolume(configDetailsName);
							// 不满一月是否按1月计算
							if(WebConstants.IS_BILLING_MONTH.YES == list.get(0).getIsMonth()){
								// 不足按一月计算
								// 单价（元/月）
								BigDecimal fixedPricing = new BigDecimal(list.get(0).getUnitPrice());
								currentMoney += fixedPricing.doubleValue();
								localMoney += fixedPricing.doubleValue();
							}else{
								// 不足按天算
								BigDecimal Month = new BigDecimal(30);
								BigDecimal useDays = new BigDecimal(time.getTimeDaysCount());
								// 单价（元/月）
								BigDecimal fixedPricing = new BigDecimal(list.get(0).getUnitPrice());
								BigDecimal allMoney = fixedPricing.multiply(useDays);
								// 单价*时间 = 总价
								BigDecimal total = allMoney.divide(Month,2, BigDecimal.ROUND_HALF_UP);
								
								currentMoney += total.doubleValue();
								localMoney += total.doubleValue();
							}

						}if(WebConstants.BillingType.DAY.equals(price.getBillingType())){
							// 按天
							billDetail.setBillingType(WebConstants.BILLING_TYPE.DAY);
							billDetail.setBillingTypeName("按天");
							billDetail.setUsageVolume(configDetailsName);
							
							// 单价(元/天)
							BigDecimal fixedPricing = new BigDecimal(list.get(0).getUnitPrice());
							// 总价
							BigDecimal total = fixedPricing.multiply(new BigDecimal(time.getTimeDaysCount()));
							
							currentMoney += total.doubleValue();
							localMoney += total.doubleValue();
						}
					}else{
						logger.info("结算失败，服务实例ID："+serviceInstance.getInstanceSid()+"可能不存在定价明细或者没有生成相应的服务实例规格数据。");					
						return null;
					}
					
				}else{
					// 增量收费
					example = new Criteria(); 
					example.put("billingPricingSid", price.getBillingPricingSid());
					List<BillingPricingDetail> list = this.billingPricingDetailService.selectByParams(example);

					if(list != null && list.size() == 1){
						// 获取单价数量
//						String str = list.get(0).getConfigDetailName();
						String str = null;
						String[] st = str.substring(1, str.length()-1).split(":");
						int uAmount = Integer.parseInt(st[1]);
						Map<String,String> specMap = getServiceInstancePricingCount(serviceInstance,st[0],time.getBillStartTime(),time.getBillEndTime());
						// 服务使用数量
						double tAmount = Double.parseDouble(specMap.get("value"));
						
						// 单价 
						BigDecimal unitPrice = new BigDecimal(list.get(0).getUnitPrice());
						// 单价数量
						BigDecimal unitAmount = new BigDecimal(uAmount);
						// 总数量
						BigDecimal totalAmount = new BigDecimal(tAmount);
						// 数量 
						BigDecimal amount = totalAmount.divide(unitAmount);
						// 单价
						BigDecimal unitMoney = unitPrice.multiply(amount);
						
						// 判断该资费定价是按月收费还是按天收费
						if(WebConstants.BillingType.MONTH.equals(price.getBillingType())){
							// 按月
							billDetail.setBillingType(WebConstants.BILLING_TYPE.MONTH);
							billDetail.setBillingTypeName("按月");
							
							if(StringUtil.isNullOrEmpty(specMap.get("unit"))){
								billDetail.setUsageVolume(tAmount+"");
							}else{
								billDetail.setUsageVolume(tAmount+specMap.get("unit"));
							}
							// 当服务实例为CDN、对象存储时，按月时，不计算时间，因为值是取得一段时间内的
							if(WebConstants.ServiceSid.SERVICE_OBJ_STORAGE.equals(serviceInstance.getServiceSid())){
								// 总价
								BigDecimal total = unitMoney;
								currentMoney +=  total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								localMoney += total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
							}else if(WebConstants.ServiceSid.SERVICE_CDN.equals(serviceInstance.getServiceSid())){
								// 总价
								BigDecimal total = unitMoney;
								currentMoney +=  total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								localMoney += total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
							}else{
								if(WebConstants.IS_BILLING_MONTH.YES == list.get(0).getIsMonth()){
									// 时间
//									BigDecimal totalDays = new BigDecimal(time.getTimeDaysCount());
									// 总价
									BigDecimal total = unitMoney;
									currentMoney +=  total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
									localMoney += total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								}else{
									BigDecimal totalDays = new BigDecimal(time.getTimeDaysCount());
									// 总价
									BigDecimal unitTime = totalDays.divide(new BigDecimal(30),5,BigDecimal.ROUND_HALF_UP);
									BigDecimal total = unitMoney.multiply(unitTime);
									currentMoney +=  total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
									localMoney += total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								}
								
							}
						
						}if(WebConstants.BillingType.DAY.equals(price.getBillingType())){
							// 按天
							billDetail.setBillingType(WebConstants.BILLING_TYPE.DAY);
							billDetail.setBillingTypeName("按天");
							
							if(StringUtil.isNullOrEmpty(specMap.get("unit"))){
								billDetail.setUsageVolume(tAmount+"");
							}else{
								billDetail.setUsageVolume(tAmount+specMap.get("unit"));
							}
							
							// 当CDN、对象存储的时候，由于取出的数据是很多天的数量，所以就不用再乘以天数
							if(WebConstants.ServiceSid.SERVICE_OBJ_STORAGE.equals(serviceInstance.getServiceSid())){
								// 总价
								BigDecimal total = unitMoney.multiply(new BigDecimal(1));
								currentMoney +=  total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								localMoney += total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
							}else if(WebConstants.ServiceSid.SERVICE_CDN.equals(serviceInstance.getServiceSid())){
								// 总价
								BigDecimal total = unitMoney.multiply(new BigDecimal(1));
								currentMoney +=  total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								localMoney += total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
							}else{
								// 总价
								BigDecimal total = unitMoney.multiply(new BigDecimal(time.getTimeDaysCount()));
								currentMoney +=  total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								localMoney += total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
							}
						}
						
					}else{
						return null;
					}
				}
			}
		}
		// 插入服务账单每月详情
		WebUtil.prepareInsertParams(billDetail);
		billDetail.setServiceSid(serviceInstance.getServiceSid().toString());
		billDetail.setServiceInstanceSid(serviceInstance.getInstanceSid().toString());
		
		billDetail.setServiceInstanceName(serviceInstance.getInstanceName());
		billDetail.setServiceName(serviceInstance.getServiceName());
		billDetail.setOwnerId(serviceInstance.getOwnerId());
		//　当前月 billTimeName,比如：2015年3月
		billDetail.setBillTimeName(time.getBillTimeName());
		// 当前月天数
		billDetail.setLongUse((float) time.getTimeDaysCount());
		// 开始时间
		billDetail.setBillingStartTime(time.getBillStartTime());
		// 结束时间
		billDetail.setBillingEndTime(time.getBillEndTime());
		// 当前月该服务使用的费用
		billDetail.setMoney(new BigDecimal(localMoney));
		
		billDetail.setStatus(WebConstants.BILL_STATUS.UNPAYED);
		
		return billDetail;
		
	}

	/**
	 * 根据服务类型和flover查询价格
	 * 
	 * @param serviceSid
	 * @param configName
	 * @param flover
	 * @return
	 */
	@Override
	@WebMethod
	@GET
	@Path("/selectPricing/{serviceSid}/{configName}/{flover}")
	public Response selectPricingByServiceSidAndFlover(
			@PathParam("serviceSid") Long serviceSid,
			@PathParam("configName") String configName,
			@PathParam("flover") String flover) {
		
		Map<String,String> map = new HashMap<String,String>();
		
		// 查询服务的资费计划
		BillingPlan billingPlans = billingPlanService.selectBillingByServiceSidAndStatus(serviceSid);
		if(StringUtil.isNullOrEmpty(billingPlans)){
			map.put("result", "failure");
			logger.debug("无法结算，云主机没有资费计划！");
			return Response.status(Status.OK).entity(JsonUtil.toJson(map)).build();
		}
		
		// 获取资费计划明细
		Criteria example = new Criteria();
		example.put("billingPlanSid", billingPlans.getBillingPlanSid());
		example.put("billingConfigName", configName);
		List<BillingPricing> billingPricingList = this.billingPricingService.selectByParams(example);
		if(billingPricingList == null || billingPricingList.size() == 0){
			map.put("result", "failure");
			logger.debug("无法结算，该"+billingPlans.getBillingPlanName()+"资费计划不存在定价配置项！");
			return Response.status(Status.OK).entity(JsonUtil.toJson(map)).build();
		}
		
		// 查询该配置项下的所有定价
		example = new Criteria(); 
		example.put("billingPricingSid", billingPricingList.get(0).getBillingPricingSid());
		List<BillingPricingDetail> list = this.billingPricingDetailService.selectByParams(example);
		if(list == null || list.size() == 0){
			map.put("result", "failure");
			logger.debug("无法结算，该"+billingPricingList.get(0).getBillingConfigName()+"配置项不存在详细定价！");
			return Response.status(Status.OK).entity(JsonUtil.toJson(map)).build();
		}
		
		if(100 == serviceSid){
			for(BillingPricingDetail detial : list){
//				if(detial.getConfigDetailName().equals(flover)){
//					map.put("result", "success");
//					map.put("billingType", billingPricingList.get(0).getBillingType());
//					map.put("unitPrice", detial.getUnitPrice().toString());
//					return Response.status(Status.OK).entity(map).build();
//				}
			}
			
			map.put("result", "failure");
			logger.debug("该配置项"+flover+"没有匹配的定价");
			return Response.status(Status.OK).entity(JsonUtil.toJson(map)).build();
		}else{
			Criteria criteria = new Criteria();
			criteria.put("serviceSid", serviceSid);
			criteria.put("name", flover);
			List<ServiceSpec> sList = this.serviceSpecService.selectByParams(criteria);
			
//			String str = list.get(0).getConfigDetailName();
			String str = null;
			String[] st = str.substring(1, str.length()-1).split(":");
			
			map.put("result", "success");
			map.put("billingType", billingPricingList.get(0).getBillingType());
			map.put("unit", sList.get(0).getUnit());
			map.put("unitPrice", list.get(0).getUnitPrice().toString());
			map.put("unitCount", st[1]);
			return Response.status(Status.OK).entity(JsonUtil.toJson(map)).build();
		}
	}
	
}
