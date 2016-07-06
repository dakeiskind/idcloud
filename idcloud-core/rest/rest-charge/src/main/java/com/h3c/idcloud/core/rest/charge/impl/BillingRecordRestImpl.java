/**
 * 
 */
package com.h3c.idcloud.core.rest.charge.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.charge.BillingRecord;
import com.h3c.idcloud.core.rest.charge.BillingRecordRest;
import com.h3c.idcloud.core.service.charge.api.BillingAccountCdrService;
import com.h3c.idcloud.core.service.charge.api.BillingAccountService;
import com.h3c.idcloud.core.service.charge.api.BillingRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author chxiaoqi
 *
 */
@Component
public class BillingRecordRestImpl implements BillingRecordRest {

	@Reference(version = "1.0.0")
	private BillingRecordService billingRecordService;

	@Reference(version = "1.0.0")
	private BillingAccountCdrService billingAccountCdrService;

	@Reference(version = "1.0.0")
	private BillingAccountService baService;
	
	private static final Logger logger = LoggerFactory.getLogger(BillingRecordRestImpl.class);

	
	/* (non-Javadoc)
	 * @see com.hptsic.cloud.rest.billing.BillingRecordRest#insertBillingRecord(com.hptsic.cloud.billing.pojo.BillingRecord)
	 */
	@Override
	@WebMethod
	@POST
	@Path("/")
	public Response insertBillingRecord(BillingRecord record) {
//		String returnJson = "";
//		WebUtil.prepareInsertParams(record);
//		int i = this.billingRecordService.insert(record);
//		if(i >0){
//			returnJson = JsonUtil.toJson(new RestResult(RestResult.SUCCESS, "插入成功", null));
//		}else{
//			returnJson = JsonUtil.toJson(new RestResult(RestResult.FAILURE, "插入失败", null));
//		}
//		return Response.status(Status.OK).entity(returnJson).build();
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hptsic.cloud.rest.billing.BillingRecordRest#updateBillingRecord(com.hptsic.cloud.billing.pojo.BillingRecord)
	 */
	@Override
	@WebMethod
	@PUT
	@Path("/")
	public Response updateBillingRecord(BillingRecord record) {
//		String returnJson = "";
//		WebUtil.prepareUpdateParams(record);
//		boolean flag = this.billingRecordService.updateBillingRecordAndAccount(record);
//		if(flag){
//			returnJson = JsonUtil.toJson(new RestResult(RestResult.SUCCESS, "充值成功", null));
//		}else{
//			returnJson = JsonUtil.toJson(new RestResult(RestResult.FAILURE, "充值失败", null));
//		}
//		return Response.status(Status.OK).entity(returnJson).build();
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hptsic.cloud.rest.billing.BillingRecordRest#getPayStatus(java.lang.String, java.lang.String)
	 */
	@Override
	@GET
	@Path("/payStatus/{provider}/{requestId}")
	public Response getPayStatus(@PathParam("provider") String provider,
			@PathParam("requestId") String requestId) {
//		if(StringUtil.isNullOrEmpty(provider)||StringUtil.isNullOrEmpty(requestId)){
//			return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.FAILURE, "Invalid Parameter", null))).build();
//		}
//		Criteria example = new Criteria().put("provider", provider).put("requestId", requestId);
//		List<BillingRecord> billingRecords = this.billingRecordService.selectByParams(example);
//		if(billingRecords == null) return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.FAILURE, "Record not found!", null))).build();
//		if(billingRecords.size() == 0) return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.FAILURE, "Record not found!", null))).build();
//		if(billingRecords.size() > 1) return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.FAILURE, "Multiple records found!", null))).build();
//		if(billingRecords.size() > 1) return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.FAILURE, "Multiple records found!", null))).build();
//		BillingRecord br = billingRecords.get(0);
//		return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.SUCCESS, "", br))).build();
		return null;
	}

	@Override
	@WebMethod
	@POST
	@Path("/billingRecordList")
	public Response getBillingRecordList(String params) throws IOException{
//		MyError error = new MyError();
//		Criteria example = new Criteria();
//
//		Map<String, Object> conditionMap = new HashMap<String, Object>();
//
//		// 取得当前登录用户
//		User user = AuthUtil.getAuthUser();
//		BillingAccount ba = this.baService.selectByUserId(user.getUserSid());
//		if (!StringUtil.isNullOrEmpty(params)) {
//			// 将条件转换为Map
//			conditionMap = JsonUtil.fromJson(params, Map.class);
//			conditionMap.put("accountSid", ba.getAccountSid());
//			example.setCondition(conditionMap);
//		} else {
//			example.put("accountSid", ba.getAccountSid());
//		}
//
//		example.setOrderByClause("CREATED_DT");
//		// 查询符合条件的数据
//		List<BillingRecord> orderList = billingRecordService.selectByParams(example);
//		String json = JsonUtil.toJson(orderList);
//		return Response.status(Status.OK).entity(json).build();
		return null;
	}

	@Override
	@WebMethod
	@POST
	@Path("/billingAcountCrdList")
	public Response getBillingAcountCrdList(String params) throws IOException{
//		MyError error = new MyError();
//		Criteria example = new Criteria();
//
//		Map<String, Object> conditionMap = new HashMap<String, Object>();
//
//		// 取得当前登录用户
//		User user = AuthUtil.getAuthUser();
//		BillingAccount ba = this.baService.selectByUserId(user.getUserSid());
//		if (!StringUtil.isNullOrEmpty(params)) {
//			// 将条件转换为Map
//			conditionMap = JsonUtil.fromJson(params, Map.class);
//			conditionMap.put("billingAccountSid", ba.getAccountSid());
//			example.setCondition(conditionMap);
//		} else {
//			example.put("accountSid", ba.getAccountSid());
//		}
//
//		example.setOrderByClause("CREATED_DT DESC");
//		// 查询符合条件的数据
//		List<BillingAccountCdr> orderList = billingAccountCdrService.selectByParams(example);
//		String json = JsonUtil.toJson(orderList);
//		return Response.status(Status.OK).entity(json).build();
		return null;
	}

	/**
	 * 后台管理平台查询交易详情
	 * 
	 * @param params
	 * @return
	 * @throws IOException
	 */
	@Override
	@WebMethod
	@POST
	@Path("/platform/billingRecordList")
	public Response getBillingPlatformRecordList(String params)
			throws IOException {
//		Criteria example = new Criteria();
//		String json = "";
//		Map<String, Object> conditionMap = new HashMap<String, Object>();
//
//		// 取得当前登录用户
//		if (StringUtil.isNullOrEmpty(params)) {
//			return Response.status(Status.OK).entity(json).build();
//		}else{
//			// 将条件转换为Map
//			conditionMap = JsonUtil.fromJson(params, Map.class);
//			example.setCondition(conditionMap);
//		}
//		example.setOrderByClause("CREATED_DT");
//		// 查询符合条件的数据
//		List<BillingRecord> orderList = billingRecordService.selectByParams(example);
//		json = JsonUtil.toJson(orderList);
//		return Response.status(Status.OK).entity(json).build();
		return null;
	}

}
