/**
 * 
 */
package com.h3c.idcloud.core.rest.product.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.system.MgtObjExt;
import com.h3c.idcloud.core.pojo.dto.system.UserMgtObjKey;
import com.h3c.idcloud.core.rest.product.ServiceInstanceResourceRest;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.system.api.MgtObjExtService;
import com.h3c.idcloud.core.service.system.api.UserMgtObjService;
import com.h3c.idcloud.infrastructure.common.constants.RestConst;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.InterfaceResult;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.jws.WebMethod;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author
 * 
 */
@Component
public class ServiceInstanceResourceRestImpl implements ServiceInstanceResourceRest {

	/** 服务实例Service */
    @Reference(version = "1.0.0")
	ServiceInstanceService serviceInstanceService;

//	/** 订单明细Service */
//	@Autowired
//	private OrderDetailService orderDetailService;
//
//	/** 订单Service */
//	@Autowired
//	private OrderService orderService;
//
//	/** 服务实例Service */
//	@Autowired
//	private ServiceDefineService serviceDefineService;
//
//	/** 服务实例规格Service */
//	@Autowired
//	private ServiceInstanceSpecService serviceInstanceSpecService;
//
//	/** 服务规格Service */
//	@Autowired
//	private ServiceConfigService serviceConfigService;
//
//	/** 资源网路Service */
//	@Autowired
//	private ResVmNetworkService resVmNetworkService;
//
//	/** 资源网路Service */
//	@Autowired
//	private ServiceInstResService serviceInstResService;
//
//	/** 虚机资源存储实例Service */
//	@Autowired
//	private TenantQuotaService tenantQuotaService;
//
//	@Autowired
//	private OrgbizService orgbizService;
//
//	@Autowired
//	private ResBizVmService resBizVmService;
//
    @Reference(version = "1.0.0")
	private UserMgtObjService userMgtObjService;
//
//	@Autowired
//	private ServiceInstanceChangeLogService instanceChangeLogService;
//
    @Reference(version = "1.0.0")
	private MgtObjExtService mgtObjExtService;

	private static Logger logger = LoggerFactory.getLogger(ServiceInstanceResourceRestImpl.class);

	/**
	 * 取得用户下的服务实例
	 */
	@Override
	@WebMethod
	@POST
	@Path("/serviceInstancelist")
	public Response getSuccessServiceInstanceList(String params) {
//		User user = AuthUtil.getAuthUser();
//
//		// 查询退订中、已开通
//		String statusParams = "'"
//				+ WebConstants.ServiceInstanceStatus.CANCELING + "','"
//				+ WebConstants.ServiceInstanceStatus.OPENED
//				+ "'".replace(" ", "");
//		try {
//			Criteria example = new Criteria();
//			if (!StringUtil.isNullOrEmpty(params)) {
//				Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
//				map.put("ownerId", user.getAccount());
//				map.put("statusParams", statusParams);
//				example.setCondition(map);
//			} else {
//				example.put("ownerId", user.getAccount());
//				example.put("statusParams", statusParams);
//			}
//			example.setOrderByClause("A.DREDGE_DATE");
//			List<ServiceInstance> serviceInstanceList = this.serviceInstanceService.selectByParams(example);
//			String json = JsonUtil.toJson(serviceInstanceList);
//			return Response.status(Status.OK).entity(json).build();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
//					null);
//			return Response.status(Status.INTERNAL_SERVER_ERROR)
//					.entity(resultJson)
//					.build();
//		}
        String json="";
        return Response.status(Status.OK).entity(json).build();
	}

	/**
	 * 取得单个服务实例规格
	 */
	@Override
	@WebMethod
	@GET
	@Path("/spec/{serviceInstanceSid}")
	public Response getServiceInstanceSpec(
			@PathParam("serviceInstanceSid") String serviceInstanceSid) {

//		List<ServiceInstanceSpec> servcieInstanceSpecList = this.serviceInstanceSpecService.selectByInstanceSid(Long.parseLong(serviceInstanceSid));
//
//		Criteria example = new Criteria();
//		example.put("instanceSid", serviceInstanceSid);
//		List<ServiceInstRes> serviceInstResList = this.serviceInstResService.selectByParams(example);
//		ServiceInstRes serviceInstRes = serviceInstResList.get(0);
//
//		example = new Criteria();
//		example.put("resVmSid", serviceInstRes.getResId());
//		List<ResVmNetwork> resVmNetworkList = this.resVmNetworkService.selectByParams(example);
//
//		Map<String, String> map = new HashMap<String, String>();
//		if (resVmNetworkList.size() == 2) {
//			map.put("publicIp", resVmNetworkList.get(0).getIpAddress());
//			map.put("ip", resVmNetworkList.get(1).getIpAddress());
//		}
//
//		for (ServiceInstanceSpec serviceInstanceSpec : servcieInstanceSpecList) {
//			String name = serviceInstanceSpec.getName();
//			String value = serviceInstanceSpec.getValue();
//			if (WebConstants.InstanceSpecType.NEED_WAN.equals(name)) {
//				map.put("needWan", value);
//			} else if (WebConstants.InstanceSpecType.NEED_LAN.equals(name)) {
//				map.put("needLan", value);
//			} else if (WebConstants.InstanceSpecType.PERF_LEVEL.equals(name)) {
//				map.put("perfLevelName", value);
//			}
//		}
//
//		String json = JsonUtil.toJson(map);
//		logger.info(json);
		String json="";
		return Response.status(Status.OK).entity(json).build();
	}

	/**
	 * 取得单个服务实例
	 */
	@Override
	@WebMethod
	@GET
	@Path("/{serviceInstanceSid}")
	public Response getServiceInstance(
			@PathParam("serviceInstanceSid") String serviceInstanceSid) {

//		ServiceInstance serviceInstance = this.serviceInstanceService.selectByPrimaryKey(Long.parseLong(serviceInstanceSid));
//		List<ServiceInstanceSpec> servcieInstanceSpecList = this.serviceInstanceSpecService.selectByInstanceSid(serviceInstance.getInstanceSid());
//		serviceInstance.setServiceInstanceSpec(servcieInstanceSpecList);
//
//		String json = JsonUtil.toJson(serviceInstance);
//		logger.info(json);
//		return Response.status(Status.OK).entity(json).build();
		String json="";
		return Response.status(Status.OK).entity(json).build();
	}

	/**
	 * 操作服务实例
	 */
	@Override
	@WebMethod
	@POST
	@Path("/serviceInstanceOperation")
	public Response serviceInstanceOperation(String params) {
//		try {
//			boolean result = this.serviceInstanceService.serviceInstanceOperation(params);
//			if (result) {
//				String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.SUCCESS,
//						null);
//				return Response.status(Status.OK).entity(resultJson).build();
//			} else {
//				String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
//						null);
//				return Response.status(Status.INTERNAL_SERVER_ERROR)
//						.entity(resultJson)
//						.build();
//			}
//		} catch (Exception e) {
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
//					null);
//			return Response.status(Status.INTERNAL_SERVER_ERROR)
//					.entity(resultJson)
//					.build();
//		}
		return Response.status(Status.OK).entity("path change").build();
	}

	/**
	 * 退订服务
	 */
	@Override
	@WebMethod
	@GET
	@Path("/release/{serviceInstanceSid}")
	public Response releaseServiceInstance(
			@PathParam("serviceInstanceSid") String serviceInstanceSid) {
//		String resultJson = null;
//		try {
//			//检查是否有调整规格申请正在执行中
//			List<ServiceInstanceChangeLog> changeLogs = this.instanceChangeLogService.selectLastChangeLog(Long.parseLong(serviceInstanceSid));
//			if(changeLogs.size() != 0){
//				String changeType = changeLogs.get(0).getChangeType();
//				if(WebConstants.instanceChangeType.UNSUBSCRIBE.equals(changeType)) {
//					resultJson = JsonUtil.toJson(new RestResult(RestResult.FAILURE, "该服务已提交退订申请，请勿重复申请！", null));
//				} else if (WebConstants.instanceChangeType.CHANGE.equals(changeType)) {
//					resultJson = JsonUtil.toJson(new RestResult(RestResult.FAILURE, "该服务已提交调整规格申请，请等待调整规格完成后，再提交退订！", null));
//				}
//				return Response.status(Status.OK).entity(resultJson).build();
//			}
//			this.serviceInstanceService.createServiceInstanceCancel(serviceInstanceSid);
//			resultJson = JsonUtil.toJson(new RestResult(RestResult.SUCCESS, "服务退订提交成功！", null));
//			return Response.status(Status.OK).entity(resultJson).build();
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			resultJson = JsonUtil.toJson(new RestResult(RestResult.FAILURE, "服务退订提交失败！", null));
//			return Response.status(Status.OK).entity(resultJson).build();
//		}
		String json="";
		return Response.status(Status.OK).entity(json).build();
	}

	/**
	 * 查询所有服务实例
	 */
	@Override
	@WebMethod
	@POST
	@Path("/serviceAllInstancelist")
	public Response getAllServiceInstanceList(String params) {
//		User user = AuthUtil.getAuthUser();
//
//		try {
//			Criteria example = new Criteria();
//			if (!StringUtil.isNullOrEmpty(params)) {
//				Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
//				map.put("ownerId", user.getAccount());
//				example.setCondition(map);
//			} else {
//				example.put("ownerId", user.getAccount());
//			}
//			List<ServiceInstance> serviceInstanceList = this.serviceInstanceService.selectByParams(example);
//			String json = JsonUtil.toJson(serviceInstanceList);
//			return Response.status(Status.OK).entity(json).build();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
//					null);
//			return Response.status(Status.INTERNAL_SERVER_ERROR)
//					.entity(resultJson)
//					.build();
//		}
		String json="";
		return Response.status(Status.OK).entity(json).build();
	}

	/**
	 * 获取订单详情的服务实例
	 */
	@Override
	@WebMethod
	@GET
	@Path("/serviceInstanceByDetailSid/{detailSid}")
	public Response serviceInstanceByDetailSid(
			@PathParam("detailSid") String detailSid) {
//		Criteria example = new Criteria();
//		example.put("detailSid", Long.parseLong(detailSid));
//		example.put("serviceSid", 100);
//		List<ServiceInstance> serviceInstanceList = this.serviceInstanceService.selectByParams(example);
//		String json = JsonUtil.toJson(serviceInstanceList);
//		// 返回前台
//		return Response.status(Status.OK).entity(json).build();
		String json="";
		return Response.status(Status.OK).entity(json).build();
	}

	/**
	 * 获取订单详情的服务实例
	 */
	@Override
	@WebMethod
	@POST
	@Path("/serviceInstanceByParam")
	public Response serviceInstanceByParam(String params) {
//		try {
//			Criteria example = new Criteria();
//			if (!StringUtil.isNullOrEmpty(params)) {
//				Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
//				example.setCondition(map);
//			}
//			List<ServiceInstance> serviceInstanceList = this.serviceInstanceService.selectByParams(example);
//			String json = JsonUtil.toJson(serviceInstanceList);
//			// 返回前台
//			return Response.status(Status.OK).entity(json).build();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
//					null);
//			return Response.status(Status.INTERNAL_SERVER_ERROR)
//					.entity(resultJson)
//					.build();
//		}
		String json="";
		return Response.status(Status.OK).entity(json).build();
	}

	@Override
	@WebMethod
	@POST
	@Path("/findVolume")
	public Response selectVolumeStorageInfo() {
		// 得到当前用户
//		User user = AuthUtil.getAuthUser();
//		// 传入用户account，查询volume
//		List<Role> roles = user.getRoles();
//		boolean roleFlag = false;
//		String mgtObjRole = PropertiesUtil.getProperty("mgtobj.manager.sid");
//		if(!CollectionUtils.isEmpty(roles)){
//			for (Role role : roles) {
//				if(role.getRoleSid().toString().equals(mgtObjRole)){
//					roleFlag = true;
//					break;
//				}
//			}
//		}
//		// 查询退订中、已开通
//		String statusParams = "'"
//				+ WebConstants.ServiceInstanceStatus.CANCELING + "','"
//				+ WebConstants.ServiceInstanceStatus.CHANGEING + "','"
//				+ WebConstants.ServiceInstanceStatus.OPENED
//				+ "'".replace(" ", "");
//		Criteria example = new Criteria();
//		if(!roleFlag){
//			example.put("ownerId", user.getAccount());
//		}
////		example.put("mgtObjSid", user.getMgtObjSid());
//		example.put("statusParams", statusParams);
//		example.put("serviceSid", WebConstants.ServiceSid.SERVICE_STORAGE);
////		example.put("storagePurpose", WebConstants.StoragePurpose.DATA_DISK);
//		List<ServiceInstance> list = this.serviceInstanceService.selectVolumeStorageInfo(example);
//		// 返回结果
//		String json = JsonUtil.toJson(list);
//		return Response.status(Status.OK).entity(json).build();
		String json="";
		return Response.status(Status.OK).entity(json).build();

	}
	
	@Override
	@WebMethod
	@POST
	@Path("/findFloatingIps")
	public Response selectFloatingIpInfo() {
		// 得到当前用户
//		User user = AuthUtil.getAuthUser();
//		// 传入用户account，查询volume
//		List<Role> roles = user.getRoles();
//		boolean roleFlag = false;
//		String mgtObjRole = PropertiesUtil.getProperty("mgtobj.manager.sid");
//		if(!CollectionUtils.isEmpty(roles)){
//			for (Role role : roles) {
//				if(role.getRoleSid().toString().equals(mgtObjRole)){
//					roleFlag = true;
//					break;
//				}
//			}
//		}
//		// 查询退订中、已开通
//		String statusParams = "'"
//				+ WebConstants.ServiceInstanceStatus.CANCELING + "','"
//				+ WebConstants.ServiceInstanceStatus.CHANGEING + "','"
//				+ WebConstants.ServiceInstanceStatus.OPENED
//				+ "'".replace(" ", "");
//		Criteria example = new Criteria();
//		if(!roleFlag){
//			example.put("ownerId", user.getAccount());
//		}
////		example.put("mgtObjSid", user.getMgtObjSid());
//		example.put("statusParams", statusParams);
//		example.put("serviceSid", WebConstants.ServiceSid.FLOATING_IP);
//		List<ServiceInstance> list = this.serviceInstanceService.selectFloatingIpInfo(example);
//		// 返回结果
//		String json = JsonUtil.toJson(list);
//		return Response.status(Status.OK).entity(json).build();
		String json="";
		return Response.status(Status.OK).entity(json).build();
	}

	@Override
	@WebMethod
	@GET
	@Path("/findTargetHost")
	public Response selectTargethost(@QueryParam("OStype") String OStype) {
		// 得到当前用户
//		User user = AuthUtil.getAuthUser();
//		// 传入user的账号和服务实例，查询目标主机
//		Criteria example = new Criteria();
//		example.put("ownerIdHost", user.getAccount());
//		example.put("OStype", OStype);
//		List<ServiceDefine> list = this.serviceInstanceService.selectedTarfgetHost(example);
//		// 返回结果,并将结果转换为json字符串
//		String json = JsonUtil.toJson(list);
//		return Response.status(Status.OK).entity(json).build();
		String json="";
		return Response.status(Status.OK).entity(json).build();
	}

	/**
	 * 获取服务实例，并取得实例的实时状态
	 */
	@Override
	@WebMethod
	@POST
	@Path("/serviceInstancelistAndCurrentState")
	public Response getServiceInstanceList(String params) {
//		User user = AuthUtil.getAuthUser();
//		List<Role> roles = user.getRoles();
		boolean roleFlag = false;
//		String mgtObjRole = PropertiesUtil.getProperty("mgtobj.manager.sid");
//		if(!CollectionUtils.isEmpty(roles)){
//			for (Role role : roles) {
//				if(role.getRoleSid().toString().equals(mgtObjRole)){
//					roleFlag = true;
//					break;
//				}
//			}
//		}
		// 查询退订中、已开通
		String statusParams = "'"
				+ WebConstants.ServiceInstanceStatus.CANCELING + "','"
				+ WebConstants.ServiceInstanceStatus.CHANGEING + "','"
				+ WebConstants.ServiceInstanceStatus.REFUSED + "','"
				+ WebConstants.ServiceInstanceStatus.OPENED
				+ "'".replace(" ", "");
		try {
			List<ServiceInstance> serviceInstanceList = new ArrayList<ServiceInstance>();
			Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
			Criteria example = new Criteria();
			Criteria example2 = new Criteria();
			if (!StringUtil.isNullOrEmpty(params)) {
				example.setCondition(map);
				example2.setCondition(map);
			}
			if(!roleFlag){
				example.put("ownerId", map.get("account"));
				example2.put("ownerId", map.get("account"));
			}
			//查询用户管理的项目
			Criteria criteria = new Criteria();
			criteria.put("userSid", map.get("userSid"));
			List<UserMgtObjKey> userMgts = userMgtObjService.selectByParams(criteria);
			if(!CollectionUtils.isEmpty(userMgts)){
				List<Long> mgtObjSids = new ArrayList<Long>();
				for(UserMgtObjKey mgtObjKey : userMgts) {
					mgtObjSids.add(mgtObjKey.getMgtObjSid());
				}
				example.put("mgtObjSids", mgtObjSids);
				example2.put("mgtObjSids", mgtObjSids);
			}else{
				example.put("ownerId", map.get("account"));
				example2.put("ownerId", map.get("account"));
			}
//			example.put("statusParams", statusParams);
			example.setOrderByClause("A.DREDGE_DATE desc , H.VM_NAME");
			System.out.println("Vm参数："+JsonUtil.toJson(example));
			List<ServiceInstance> instanceVmList = this.serviceInstanceService.selectInstVmByParams(example);
			if(map.get("serviceSid")=="100") {
				example2.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
			}else if(map.get("serviceSid")=="105"){
				example2.put("serviceSid", WebConstants.ServiceSid.SERVICE_STORAGE);
			}
//			example2.put("statusParams", statusParams);
//			example2.put("serviceSid", WebConstants.ServiceSid.SERVICE_PM);
			example2.setOrderByClause("A.DREDGE_DATE desc , H.HOST_NAME");
			System.out.println("Host参数："+JsonUtil.toJson(example2));
			List<ServiceInstance> instancceHostList = this.serviceInstanceService.selectInstHostByParams(example2);
			serviceInstanceList.addAll(instanceVmList);
			serviceInstanceList.addAll(instancceHostList);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(!CollectionUtils.isEmpty(serviceInstanceList)){
				for (ServiceInstance serviceInstance : serviceInstanceList) {
					//得到提前多久提醒
//					String presetTime = PropertiesUtil.getProperty("mgtobj.expiredate.notice");
//					int time = Integer.parseInt(presetTime)*7;
					Criteria condition = new Criteria();
					condition.put("mgtObjSid", serviceInstance.getMgtObjSid());
					condition.put("attrKey", "projectEndDate");
					List<MgtObjExt> exts = mgtObjExtService.selectByParams(condition);
					if(!CollectionUtils.isEmpty(exts)){
						String mgtObjEndDate = exts.get(0).getAttrValue();
						Date endTime = df.parse(mgtObjEndDate+" 23:59:59");
						Date now = new Date();
						long buffer = endTime.getTime() - now.getTime();
						long days = buffer/(1000*60*60*24);
						//如果在提醒范围内
//						if(time>=days&&0<=days){
//							serviceInstance.setInNoticeTime(1);
//						}else if(days<0){
//							serviceInstance.setInNoticeTime(-1);
//						}else{
//							serviceInstance.setInNoticeTime(0);
//						}
					}
				}
			}
			
			// //实时调南向接口查询主机信息
			// for(ServiceInstance si:serviceInstanceList){
			// ResourceInstance ri=new ResourceInstance();
			// ri.setResInstanceSid(si.getResInstanceSid());
			// ri.setResPoolSid(si.getResPoolSid());
			// ri.setAllocateInstanceId(si.getAllocateInstanceId());
			// String resInsVmStatus= ri.getStatus();
			// //String
			// resInsVmStatus=this.resourceInstanceVmService.getRealTimeStatus(ri);
			// if(WebConstants.CheckInstanceStatus.POWERED_ON.equals(resInsVmStatus)){
			// si.setResInsVmStatusName("正常");
			// si.setResInsVmStatus(WebConstants.ResHostInstanceStatus.NORMAL);
			// }else
			// if(WebConstants.CheckInstanceStatus.POWERED_OFF.equals(resInsVmStatus)){
			// si.setResInsVmStatusName("已关机");
			// si.setResInsVmStatus(WebConstants.ResHostInstanceStatus.SHUTDOWN);
			// }else
			// if(WebConstants.CheckInstanceStatus.SUSPENDED.equals(resInsVmStatus)){
			// si.setResInsVmStatusName("已挂起");
			// si.setResInsVmStatus(WebConstants.ResHostInstanceStatus.PAUSE);
			// }
			// }
			String json = JsonUtil.toJson(new RestResult(RestConst.HttpConst.OK, RestResult.Status.SUCCESS,null,serviceInstanceList));
			System.out.println(json);
			return Response.ok(json).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
					null);
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(resultJson)
					.build();
		}
	}

	/**
	 * 更新服务实例
	 */
	@Override
	@WebMethod
	@POST
	@Path("/updateServiceInstance")
	public Response updateServiceInstance(String params) {
//		try {
//			Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
//			String description = StringUtil.nullToEmpty(map.get("description"));
//			long instanceId = Long.parseLong(StringUtil.nullToEmpty(map.get("instanceSid")));
//			ServiceInstance si = this.serviceInstanceService.selectByPrimaryKey(instanceId);
//			si.setDescription(description);
//			int result = this.serviceInstanceService.updateByPrimaryKeySelective(si);
//			if (result > 0) {
//				return Response.status(Status.OK).entity(si).build();
//			} else {
//				String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
//						null);
//				return Response.status(Status.INTERNAL_SERVER_ERROR)
//						.entity(resultJson)
//						.build();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
//					null);
//			return Response.status(Status.INTERNAL_SERVER_ERROR)
//					.entity(resultJson)
//					.build();
//		}
        String json="";
        return Response.status(Status.OK).entity(json).build();
	}

	/**
	 * 取得租户下的所有购买的服务
	 */
	@Override
	@WebMethod
	@POST
	@Path("/getTenantOrderServicelist")
	public Response getTenantOrderServicelist(String params) {
//		User user = AuthUtil.getAuthUser();
//
//		// 查询已开通
//		try {
//			Criteria example = new Criteria();
//			if (!StringUtil.isNullOrEmpty(params) && !params.equals("null")) {
//				Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
//				map.put("status", WebConstants.OrderStatusCd.OPENED);
//				map.put("tenantId", user.getTenantSid());
//				example.setCondition(map);
//			} else {
//				example.put("tenantId", user.getTenantSid());
//				example.put("status", WebConstants.OrderStatusCd.OPENED);
//			}
//			example.setOrderByClause("A.CREATED_DT");
//			List<OrderDetail> orderDetailList = this.orderDetailService.selectByParams(example);
//			String json = JsonUtil.toJson(orderDetailList);
//			return Response.status(Status.OK).entity(json).build();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
//					null);
//			return Response.status(Status.INTERNAL_SERVER_ERROR)
//					.entity(resultJson)
//					.build();
//		}
        String json="";
        return Response.status(Status.OK).entity(json).build();

	}

	/**
	 * 取得租户下的所有购买的服务实例
	 */
	@Override
	@WebMethod
	@POST
	@Path("/getTenantOrderServiceInstancelist")
	public Response getTenantOrderServiceInstancelist(String params) {

//		User user = AuthUtil.getAuthUser();
//
//		// 查询已开通
//		try {
//			List<ServiceInstance> resultServiceInstanceList = new ArrayList<ServiceInstance>();
//			Map<String, Object> resultMap = new HashMap<String, Object>();
//
//			Criteria example = new Criteria();
//			if (!StringUtil.isNullOrEmpty(params) && !params.equals("null")) {
//				Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
//
//				if (StringUtil.isNullOrEmpty(map.get("ownerId"))) {
//					map.put("tanentId", user.getTenantSid());
//				}
//				map.put("status", WebConstants.ServiceInstanceStatus.OPENED);
//				example.setCondition(map);
//			} else {
//				// 初始化查询出所有租户下已开通的服务
//				example.put("tanentId", user.getTenantSid());
//				example.put("status", WebConstants.ServiceInstanceStatus.OPENED);
//			}
//
//			example.setOrderByClause("A.OWNER_ID");
//			List<ServiceInstance> serviceInstanceVmList = this.serviceInstanceService.selectByParams(example);
//			for (ServiceInstance serviceInstance : serviceInstanceVmList) {
//				// 块存储服务要单独筛选
//				if (serviceInstance.getServiceSid()
//						.equals(WebConstants.ServiceSid.SERVICE_STORAGE)) {
//					// 块存储 选出数据盘,去掉系统盘
//					example = new Criteria();
//					example.put("volumeStorageOrderTenantId",
//							user.getTenantSid());
//					example.put("status",
//							WebConstants.ServiceInstanceStatus.OPENED);
//					example.put("storagePurpose",
//							WebConstants.StoragePurpose.DATA_DISK);
//					List<ServiceInstance> serviceInstanceStList = this.serviceInstanceService.selectVolumeStorageInfo(example);
//					for (ServiceInstance serviceInstanceSt : serviceInstanceStList) {
//						if (serviceInstance.getInstanceSid()
//								.equals(serviceInstanceSt.getInstanceSid())) {
//							resultServiceInstanceList.add(serviceInstance);
//						}
//					}
//				} else {
//					resultServiceInstanceList.add(serviceInstance);
//				}
//			}
//
//			resultMap.put("serviceInstanceList", resultServiceInstanceList);
//			// 统计云主机数
//			example = new Criteria();
//			example.put("tanentId", user.getTenantSid());
//			example.put("status", WebConstants.ServiceInstanceStatus.OPENED);
//			example.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
//			List<ServiceInstance> userServiceVmList = this.serviceInstanceService.countTenantServiceVmByParams(example);
//			if (userServiceVmList == null || userServiceVmList.size() == 0) {
//				ServiceInstance blankServiceInstance = new ServiceInstance();
//				blankServiceInstance.setOwnerName("无");
//				blankServiceInstance.setUserServiceNum(0);
//				userServiceVmList.add(blankServiceInstance);
//			}
//			resultMap.put("userServiceVmList", userServiceVmList);
//
//			// 统计云主机cpu数
//			example = new Criteria();
//			example.put("tanentId", user.getTenantSid());
//			example.put("status", WebConstants.ServiceInstanceStatus.OPENED);
//			example.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
//			List<ServiceInstance> userServiceVmCpuList = this.serviceInstanceService.countTenantServiceVmCpuByParams(example);
//			if (userServiceVmCpuList == null
//					|| userServiceVmCpuList.size() == 0) {
//				ServiceInstance blankServiceInstance = new ServiceInstance();
//				blankServiceInstance.setOwnerName("无");
//				blankServiceInstance.setUserServiceNum(0);
//				userServiceVmCpuList.add(blankServiceInstance);
//			}
//			resultMap.put("userServiceVmCpuList", userServiceVmCpuList);
//
//			// 统计云主机内存数
//			example = new Criteria();
//			example.put("tanentId", user.getTenantSid());
//			example.put("status", WebConstants.ServiceInstanceStatus.OPENED);
//			example.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
//			List<ServiceInstance> userServiceVmMemoryList = this.serviceInstanceService.countTenantServiceVmMemoryByParams(example);
//			if (userServiceVmMemoryList == null
//					|| userServiceVmMemoryList.size() == 0) {
//				ServiceInstance blankServiceInstance = new ServiceInstance();
//				blankServiceInstance.setOwnerName("无");
//				blankServiceInstance.setUserServiceNum(0);
//				userServiceVmMemoryList.add(blankServiceInstance);
//			}
//			resultMap.put("userServiceVmMemoryList", userServiceVmMemoryList);
//
//			// 统计块存储数
//			example = new Criteria();
//			example.put("volumeStorageOrderTenantId", user.getTenantSid());
//			example.put("status", WebConstants.ServiceInstanceStatus.OPENED);
//			example.put("storagePurpose", WebConstants.StoragePurpose.DATA_DISK);
//			List<ServiceInstance> userServiceStList = this.serviceInstanceService.countTenantServiceStByParams(example);
//			if (userServiceStList == null || userServiceStList.size() == 0) {
//				ServiceInstance blankServiceInstance = new ServiceInstance();
//				blankServiceInstance.setOwnerName("无");
//				blankServiceInstance.setUserServiceNum(0);
//				userServiceStList.add(blankServiceInstance);
//			}
//			resultMap.put("userServiceStList", userServiceStList);
//
//			// 统计块存储数磁盘大小
//			example = new Criteria();
//			example.put("volumeStorageOrderTenantId", user.getTenantSid());
//			example.put("status", WebConstants.ServiceInstanceStatus.OPENED);
//			example.put("storagePurpose", WebConstants.StoragePurpose.DATA_DISK);
//			List<ServiceInstance> userServiceStDiskList = this.serviceInstanceService.countTenantServiceStDiskByParams(example);
//			if (userServiceStDiskList == null
//					|| userServiceStDiskList.size() == 0) {
//				ServiceInstance blankServiceInstance = new ServiceInstance();
//				blankServiceInstance.setOwnerName("无");
//				blankServiceInstance.setUserServiceNum(0);
//				userServiceStDiskList.add(blankServiceInstance);
//			}
//			resultMap.put("userServiceStDiskList", userServiceStDiskList);
//			String json = JsonUtil.toJson(resultMap);
//			return Response.status(Status.OK).entity(json).build();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
//					null);
//			return Response.status(Status.INTERNAL_SERVER_ERROR)
//					.entity(resultJson)
//					.build();
//		}
        String json="";
        return Response.status(Status.OK).entity(json).build();
	}

	/**
	 * 取得租户下的所有购买的服务实例配额
	 */
	@Override
	@WebMethod
	@POST
	@Path("/getTenantOrderServiceInstanceQuotalist")
	public Response getTenantOrderServiceInstanceQuotalist(String params) {
//		User user = AuthUtil.getAuthUser();
//
//		// 查询已开通
//		try {
//			List<ServiceInstance> resultServiceInstanceQuotaLastList = new ArrayList<ServiceInstance>();
//			List<ServiceInstance> resultServiceInstanceQuotaList = new ArrayList<ServiceInstance>();
//
//			Map<String, Object> resultMap = new HashMap<String, Object>();
//
//			Criteria example = new Criteria();
//
//			resultMap = serviceInstanceService.getTenantUserQuotaListByParams(params,
//					user);
//
//			for (ServiceInstance serviceInstance : (List<ServiceInstance>) resultMap.get("serviceInstanceList")) {
//				ServiceInstance instance = new ServiceInstance();
//				String ownerId = serviceInstance.getOwnerId();
//				instance.setOwnerId(ownerId);
//				instance.setTenantName(serviceInstance.getTenantName());
//				instance.setOwnerName(serviceInstance.getOwnerName());
//
//				for (ServiceInstance serviceInstanceVm : (List<ServiceInstance>) resultMap.get("userServiceVmList")) {
//					if (ownerId.equals(serviceInstanceVm.getOwnerId())) {
//						instance.setUserServiceVmNum(serviceInstanceVm.getUserServiceNum());
//					}
//				}
//
//				for (ServiceInstance serviceInstanceSt : (List<ServiceInstance>) resultMap.get("userServiceStList")) {
//					if (ownerId.equals(serviceInstanceSt.getOwnerId())) {
//						instance.setUserServiceStNum(serviceInstanceSt.getUserServiceNum());
//					}
//				}
//
//				for (ServiceInstance serviceInstanceCpu : (List<ServiceInstance>) resultMap.get("userServiceVmCpuList")) {
//					if (ownerId.equals(serviceInstanceCpu.getOwnerId())) {
//						instance.setUserServiceCpuNum(serviceInstanceCpu.getUserServiceNum());
//					}
//				}
//
//				for (ServiceInstance serviceInstanceMemory : (List<ServiceInstance>) resultMap.get("userServiceVmMemoryList")) {
//					if (ownerId.equals(serviceInstanceMemory.getOwnerId())) {
//						instance.setUserServiceMemoryNum(serviceInstanceMemory.getUserServiceNum());
//					}
//				}
//				for (ServiceInstance serviceInstanceDisk : (List<ServiceInstance>) resultMap.get("userServiceStDiskList")) {
//					if (ownerId.equals(serviceInstanceDisk.getOwnerId())) {
//						instance.setUserServiceStdiskNum(serviceInstanceDisk.getUserServiceNum());
//					}
//				}
//
//				resultServiceInstanceQuotaList.add(instance);
//			}
//
//			// 取出租户下所有的配额
//			resultServiceInstanceQuotaLastList = serviceInstanceService.removeDuplicateData(resultServiceInstanceQuotaList);
//			Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
//
//			// 从列表中查询
//			if (!StringUtil.isNullOrEmpty(params) && !params.equals("null")) {
//
//				if (!StringUtil.isNullOrEmpty(map.get("ownerId"))
//						&& map.get("ownerId").equals("null")) {
//					for (ServiceInstance serviceInstance : resultServiceInstanceQuotaList) {
//						if (!serviceInstance.getOwnerId()
//								.equals(map.get("ownerId"))) {
//							resultServiceInstanceQuotaList.remove(serviceInstance);
//						}
//					}
//				}
//			} else {
//				resultServiceInstanceQuotaLastList = serviceInstanceService.removeDuplicateData(resultServiceInstanceQuotaList);
//			}
//
//			resultMap.put("serviceInstanceList",
//					resultServiceInstanceQuotaLastList);
//
//			// 统计云主机数配额
//			example = new Criteria();
//			example.put("tanentId", user.getTenantSid());
//			example.put("status", WebConstants.ServiceInstanceStatus.OPENED);
//			example.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
//			List<ServiceInstance> userServiceVmList = this.serviceInstanceService.countTenantServiceVmQuotaByParams(example);
//			List<HashMap<String, String>> vmList = new ArrayList<HashMap<String, String>>();
//			HashMap<String, String> vmMap = new HashMap<String, String>();
//			if (userServiceVmList == null || userServiceVmList.size() == 0) {
//				vmMap.put("source", "已使用：" + "0个");
//				vmMap.put("percentage", "0");
//				vmList.add(vmMap);
//				vmMap = new HashMap<String, String>();
//				example = new Criteria();
//				example.put("tenantSid", user.getTenantSid());
//				example.put("quotaKey", "instances");
//				List<TenantQuota> tenantQuotas = tenantQuotaService.selectByParams(example);
//				if (tenantQuotas != null && tenantQuotas.size() > 0) {
//					vmMap.put("source",
//							"未使用："
//									+ StringUtil.nullToEmpty(tenantQuotas.get(0)
//											.getQuotaValue()) + "个");
//					vmMap.put("percentage",
//							StringUtil.nullToEmpty(tenantQuotas.get(0)
//									.getQuotaValue()));
//					vmList.add(vmMap);
//				}
//			} else {
//				vmMap.put("source",
//						"已使用："
//								+ StringUtil.nullToEmpty(userServiceVmList.get(0)
//										.getUserServiceNum()) + "个");
//				vmMap.put("percentage",
//						StringUtil.nullToEmpty(userServiceVmList.get(0)
//								.getUserServiceNum()));
//				vmList.add(vmMap);
//				vmMap = new HashMap<String, String>();
//				vmMap.put("source",
//						"未使用："
//								+ StringUtil.nullToEmpty(userServiceVmList.get(0)
//										.getRestQuotaServiceNum()) + "个");
//				vmMap.put("percentage",
//						StringUtil.nullToEmpty(userServiceVmList.get(0)
//								.getRestQuotaServiceNum()));
//				vmList.add(vmMap);
//
//			}
//			resultMap.put("userServiceVmList", vmList);
//
//			// 统计云主机cpu数配额
//			example = new Criteria();
//			example.put("tanentId", user.getTenantSid());
//			example.put("status", WebConstants.ServiceInstanceStatus.OPENED);
//			example.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
//			List<ServiceInstance> userServiceVmCpuList = this.serviceInstanceService.countTenantServiceVmCpuQuotaByParams(example);
//			List<HashMap<String, String>> cpuList = new ArrayList<HashMap<String, String>>();
//			HashMap<String, String> cpuMap = new HashMap<String, String>();
//			if (userServiceVmCpuList == null
//					|| userServiceVmCpuList.size() == 0) {
//				cpuMap.put("source", "已使用：" + "0核");
//				cpuMap.put("percentage", "0");
//				cpuList.add(cpuMap);
//				cpuMap = new HashMap<String, String>();
//				example = new Criteria();
//				example.put("tenantSid", user.getTenantSid());
//				example.put("quotaKey", "cores");
//				List<TenantQuota> tenantQuotas = tenantQuotaService.selectByParams(example);
//				if (tenantQuotas != null && tenantQuotas.size() > 0) {
//					cpuMap.put("source",
//							"未使用："
//									+ StringUtil.nullToEmpty(tenantQuotas.get(0)
//											.getQuotaValue()) + "核");
//					cpuMap.put("percentage",
//							StringUtil.nullToEmpty(tenantQuotas.get(0)
//									.getQuotaValue()));
//					cpuList.add(cpuMap);
//				}
//			} else {
//				cpuMap.put("source",
//						"已使用："
//								+ StringUtil.nullToEmpty(userServiceVmCpuList.get(0)
//										.getUserServiceNum()) + "核");
//				cpuMap.put("percentage",
//						StringUtil.nullToEmpty(userServiceVmCpuList.get(0)
//								.getUserServiceNum()));
//				cpuList.add(cpuMap);
//				cpuMap = new HashMap<String, String>();
//				cpuMap.put("source",
//						"未使用："
//								+ StringUtil.nullToEmpty(userServiceVmCpuList.get(0)
//										.getRestQuotaServiceNum()) + "核");
//				cpuMap.put("percentage",
//						StringUtil.nullToEmpty(userServiceVmCpuList.get(0)
//								.getRestQuotaServiceNum()));
//				cpuList.add(cpuMap);
//			}
//			resultMap.put("userServiceVmCpuList", cpuList);
//
//			// 统计云主机内存数配额
//			example = new Criteria();
//			example.put("tanentId", user.getTenantSid());
//			example.put("status", WebConstants.ServiceInstanceStatus.OPENED);
//			example.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
//			List<ServiceInstance> userServiceVmMemoryList = this.serviceInstanceService.countTenantServiceVmMemoryQuotaByParams(example);
//			List<HashMap<String, String>> memoryList = new ArrayList<HashMap<String, String>>();
//			HashMap<String, String> memoryMap = new HashMap<String, String>();
//			if (userServiceVmMemoryList == null
//					|| userServiceVmMemoryList.size() == 0) {
//				memoryMap.put("source", "已使用：" + "0MB");
//				memoryMap.put("percentage", "0");
//				memoryList.add(memoryMap);
//				memoryMap = new HashMap<String, String>();
//				example = new Criteria();
//				example.put("tenantSid", user.getTenantSid());
//				example.put("quotaKey", "ram");
//				List<TenantQuota> tenantQuotas = tenantQuotaService.selectByParams(example);
//				if (tenantQuotas != null && tenantQuotas.size() > 0) {
//					memoryMap.put("source",
//							"未使用："
//									+ StringUtil.nullToEmpty(tenantQuotas.get(0)
//											.getQuotaValue()) + "MB");
//					memoryMap.put("percentage",
//							StringUtil.nullToEmpty(tenantQuotas.get(0)
//									.getQuotaValue()));
//					memoryList.add(memoryMap);
//				}
//			} else {
//				memoryMap.put("source",
//						"已使用："
//								+ StringUtil.nullToEmpty(userServiceVmMemoryList.get(0)
//										.getUserServiceNum()) + "MB");
//				memoryMap.put("percentage",
//						StringUtil.nullToEmpty(userServiceVmMemoryList.get(0)
//								.getUserServiceNum()));
//				memoryList.add(memoryMap);
//				memoryMap = new HashMap<String, String>();
//				memoryMap.put("source",
//						"未使用："
//								+ StringUtil.nullToEmpty(userServiceVmMemoryList.get(0)
//										.getRestQuotaServiceNum()) + "MB");
//				memoryMap.put("percentage",
//						StringUtil.nullToEmpty(userServiceVmMemoryList.get(0)
//								.getRestQuotaServiceNum()));
//				memoryList.add(memoryMap);
//			}
//			resultMap.put("userServiceVmMemoryList", memoryList);
//
//			// 统计块存储数配额
//			example = new Criteria();
//			example.put("volumeStorageOrderTenantId", user.getTenantSid());
//			example.put("status", WebConstants.ServiceInstanceStatus.OPENED);
//			example.put("storagePurpose", WebConstants.StoragePurpose.DATA_DISK);
//			List<ServiceInstance> userServiceStList = this.serviceInstanceService.countTenantServiceStQuotaByParams(example);
//			List<HashMap<String, String>> stList = new ArrayList<HashMap<String, String>>();
//			HashMap<String, String> stMap = new HashMap<String, String>();
//			if (userServiceStList == null || userServiceStList.size() == 0) {
//				stMap.put("source", "已使用：" + "0个");
//				stMap.put("percentage", "0");
//				stList.add(stMap);
//				stMap = new HashMap<String, String>();
//				example = new Criteria();
//				example.put("tenantSid", user.getTenantSid());
//				example.put("quotaKey", "volumes");
//				List<TenantQuota> tenantQuotas = tenantQuotaService.selectByParams(example);
//				if (tenantQuotas != null && tenantQuotas.size() > 0) {
//					stMap.put("source",
//							"未使用："
//									+ StringUtil.nullToEmpty(tenantQuotas.get(0)
//											.getQuotaValue()) + "个");
//					stMap.put("percentage",
//							StringUtil.nullToEmpty(tenantQuotas.get(0)
//									.getQuotaValue()));
//					stList.add(stMap);
//				}
//			} else {
//				stMap.put("source",
//						"已使用："
//								+ StringUtil.nullToEmpty(userServiceStList.get(0)
//										.getUserServiceNum()) + "个");
//				stMap.put("percentage",
//						StringUtil.nullToEmpty(userServiceStList.get(0)
//								.getUserServiceNum()));
//				stList.add(stMap);
//				stMap = new HashMap<String, String>();
//				stMap.put("source",
//						"未使用："
//								+ StringUtil.nullToEmpty(userServiceStList.get(0)
//										.getRestQuotaServiceNum()) + "个");
//				stMap.put("percentage",
//						StringUtil.nullToEmpty(userServiceStList.get(0)
//								.getRestQuotaServiceNum()));
//				stList.add(stMap);
//			}
//			resultMap.put("userServiceStList", stList);
//
//			// 统计块存储数磁盘大小配额
//			example = new Criteria();
//			example.put("volumeStorageOrderTenantId", user.getTenantSid());
//			example.put("status", WebConstants.ServiceInstanceStatus.OPENED);
//			example.put("storagePurpose", WebConstants.StoragePurpose.DATA_DISK);
//			List<ServiceInstance> userServiceStDiskList = this.serviceInstanceService.countTenantServiceStDiskQuotaByParams(example);
//			List<HashMap<String, String>> rtDiskList = new ArrayList<HashMap<String, String>>();
//			HashMap<String, String> rtDiskMap = new HashMap<String, String>();
//			if (userServiceStDiskList == null
//					|| userServiceStDiskList.size() == 0) {
//				rtDiskMap.put("source", "已使用：" + "0GB");
//				rtDiskMap.put("percentage", "0");
//				rtDiskList.add(rtDiskMap);
//				rtDiskMap = new HashMap<String, String>();
//				example = new Criteria();
//				example.put("tenantSid", user.getTenantSid());
//				example.put("quotaKey", "gigabytes");
//				List<TenantQuota> tenantQuotas = tenantQuotaService.selectByParams(example);
//				if (tenantQuotas != null && tenantQuotas.size() > 0) {
//					rtDiskMap.put("source",
//							"未使用："
//									+ StringUtil.nullToEmpty(tenantQuotas.get(0)
//											.getQuotaValue()) + "GB");
//					rtDiskMap.put("percentage",
//							StringUtil.nullToEmpty(tenantQuotas.get(0)
//									.getQuotaValue()));
//					rtDiskList.add(rtDiskMap);
//				}
//			} else {
//				rtDiskMap.put("source",
//						"已使用："
//								+ StringUtil.nullToEmpty(userServiceStDiskList.get(0)
//										.getUserServiceNum()) + "GB");
//				rtDiskMap.put("percentage",
//						StringUtil.nullToEmpty(userServiceStDiskList.get(0)
//								.getUserServiceNum()));
//				rtDiskList.add(rtDiskMap);
//				rtDiskMap = new HashMap<String, String>();
//				rtDiskMap.put("source",
//						"未使用："
//								+ StringUtil.nullToEmpty(userServiceStDiskList.get(0)
//										.getRestQuotaServiceNum()) + "GB");
//				rtDiskMap.put("percentage",
//						StringUtil.nullToEmpty(userServiceStDiskList.get(0)
//								.getRestQuotaServiceNum()));
//				rtDiskList.add(rtDiskMap);
//			}
//			resultMap.put("userServiceStDiskList", rtDiskList);
//			String json = JsonUtil.toJson(resultMap);
//			return Response.status(Status.OK).entity(json).build();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
//					null);
//			return Response.status(Status.INTERNAL_SERVER_ERROR)
//					.entity(resultJson)
//					.build();
//		}
        String json="";
        return Response.status(Status.OK).entity(json).build();
	}

	/**
	 * 判断是否可重复订购
	 */
	@Override
	@WebMethod
	@GET
	@Path("/checkAllServiceShow")
	public Response checkAllServiceShow() {
//		User user = AuthUtil.getAuthUser();
//		boolean isTenantMgt = false;
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		if (user != null) {
//
//			for (Role role : user.getRoles()) {
//				if (role.getRoleSid().equals(WebConstants.RoleSid.T_MANAGER)) {
//					isTenantMgt = true;
//				}
//			}
//			resultMap.put("isTenantMgt", isTenantMgt);
//
//			String json = JsonUtil.toJson(resultMap);
//			return Response.status(Status.OK).entity(json).build();
//
//		} else {
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
//					null);
//			return Response.status(Status.INTERNAL_SERVER_ERROR)
//					.entity(resultJson)
//					.build();
//		}
        String json="";
        return Response.status(Status.OK).entity(json).build();
	}

	@Override
	@WebMethod
	@POST
	@Path("/getOrgServiceInstance")
	public Response getOrgServiceInstance(String params) {
//		User user = AuthUtil.getAuthUser();
//		try {
//			//得到部门关联的二级业务
//			List<Orgbiz> orgBizs = new ArrayList<Orgbiz>();
//			Criteria example = new Criteria();
//			Long orgId = user.getOrgSid();
//			if(orgId!=null){
//				example.put("orgSid", orgId);
//				example.put("level", 2);
//				orgBizs = orgbizService.selectSecondBizesByParams(example);
//			}
//			String bizSids = "";
//			if(!CollectionUtils.isEmpty(orgBizs)){
//				for (Orgbiz orgbiz : orgBizs) {
//					bizSids = bizSids + orgbiz.getBizSid() +",";
//				}
//			}
//			if(bizSids.length()>0){
//				bizSids = bizSids.substring(0, bizSids.length()-1);
//			}
//			example = new Criteria();
//			example.put("bizSids", bizSids);
//			if (!StringUtil.isNullOrEmpty(params) && !params.equals("null")) {
//				Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
//				if (!StringUtil.isNullOrEmpty(map.get("vmNameLike"))) {
//					example.put("vmNameLike", map.get("vmNameLike"));
//				}
//				if (!StringUtil.isNullOrEmpty(map.get("bizNameLike"))) {
//					example.put("bizNameLike", map.get("bizNameLike"));
//				}
//				if (!StringUtil.isNullOrEmpty(map.get("vmStatus"))) {
//					example.put("status", map.get("vmStatus"));
//				}
//				if (!StringUtil.isNullOrEmpty(map.get("instanceStatus"))) {
//					example.put("instanceStatus", map.get("instanceStatus"));
//				}
//			}
//			example.setOrderByClause("A.DREDGE_DATE DESC ,F.INSTANCE_NAME");
//			List<ResBizVm> list = this.resBizVmService.selectByParamsForPortal(example);
//			String json = JsonUtil.toJson(list);
//			return Response.status(Status.OK).entity(json).build();
//		} catch (Exception e) {
//			e.printStackTrace();
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
//					null);
//			return Response.status(Status.INTERNAL_SERVER_ERROR)
//					.entity(resultJson)
//					.build();
//		}
        String json="";
        return Response.status(Status.OK).entity(json).build();
	}

	// /**
	// * 根据条件获取serviceInstance列表服务
	// */
	// @Override
	// @WebMethod
	// @POST
	// @Path("/platform/searchServiceInstance")
	// public Response searchServiceInstance(String params) {
	//
	// try {
	// Criteria example = new Criteria();
	// if (!StringUtil.isNullOrEmpty(params)) {
	// Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
	// if(!StringUtil.isNullOrEmpty(map.get("orderIdLike"))){
	// example.put("orderIdLike", map.get("orderIdLike"));
	// }
	//
	// if(!StringUtil.isNullOrEmpty(map.get("serviceSid"))){
	// example.put("serviceSid", map.get("serviceSid"));
	// }
	//
	// if(!StringUtil.isNullOrEmpty(map.get("dredgeFromDate"))){
	// example.put("dredgeFromDate", map.get("dredgeFromDate"));
	// }
	//
	// if(!StringUtil.isNullOrEmpty(map.get("dredgeToDate"))){
	// example.put("dredgeToDate", map.get("dredgeToDate"));
	// }
	//
	// if(!StringUtil.isNullOrEmpty(map.get("instanceNameLike"))){
	// example.put("instanceNameLike", map.get("instanceNameLike"));
	// }
	//
	// if(!StringUtil.isNullOrEmpty(map.get("status"))){
	// example.put("status", map.get("status"));
	// }
	// if(!StringUtil.isNullOrEmpty(map.get("expiringFromDate"))){
	// example.put("expiringFromDate", map.get("expiringFromDate"));
	// }
	// if(!StringUtil.isNullOrEmpty(map.get("expiringToDate"))){
	// example.put("expiringToDate", map.get("expiringToDate"));
	// }
	// }
	// List<ServiceInstance> list =
	// serviceInstanceService.selectByParams(example);
	// String json = JsonUtil.toJson(list);
	// return Response.status(Status.OK).entity(json).build();
	// } catch (Exception e) {
	// e.printStackTrace();
	// String resultJson =
	// InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
	// return
	// Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
	// }
	//
	// }
	//
	// /**
	// * 根据条件获取后台serviceInstance列表 配置信息
	// */
	// @Override
	// @WebMethod
	// @POST
	// @Path("/platform/searchServiceInstanceDetailConfig")
	// public Response searchServiceInstanceDetailConfig(String params) {
	// try {
	// long instanceSid = 0L;
	// if (!StringUtil.isNullOrEmpty(params)) {
	// Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
	// instanceSid =
	// Long.parseLong(StringUtil.nullToEmpty(map.get("instanceSid")));
	// }
	// List<ServiceInstanceSpec> instanceSpecServices =
	// serviceInstanceSpecService.selectByInstanceSid(instanceSid);
	// Map<String, Object> resultMap = new HashMap<String, Object>();
	// for (ServiceInstanceSpec serviceInstanceSpec : instanceSpecServices) {
	// if ("CPU".equals(serviceInstanceSpec.getName())) {
	// resultMap.put("cpuCores",serviceInstanceSpec.getValue() + "核");
	// } else if ("MEMORY".equals(serviceInstanceSpec.getName())) {
	// resultMap.put("memorySize",serviceInstanceSpec.getValue() + "G");
	// } else if ("OS".equals(serviceInstanceSpec.getName())) {
	// String osTypeName =
	// WebUtil.findDisplayByParams(serviceInstanceSpec.getValue(),
	// WebConstants.CodeCategroy.OS_TYPE);
	// resultMap.put("osTypeName", osTypeName);
	// }
	// }
	// String json = JsonUtil.toJson(resultMap);
	// return Response.status(Status.OK).entity(json).build();
	// } catch (Exception e) {
	// e.printStackTrace();
	// String resultJson =
	// InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
	// return
	// Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
	// }
	// }
	//
	// /**
	// * 根据条件获取后台serviceInstance列表 存储信息
	// */
	// @Override
	// @WebMethod
	// @POST
	// @Path("/platform/searchServiceInstanceDetailStorage")
	// public Response searchServiceInstanceDetailStorage(String params) {
	// try {
	//
	// List<ResourceInstanceStorage> resourceInstanceStorageList = new
	// ArrayList<ResourceInstanceStorage>();
	// long instanceSid = 0L;
	// if (!StringUtil.isNullOrEmpty(params)) {
	// Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
	// instanceSid =
	// Long.parseLong(StringUtil.nullToEmpty(map.get("instanceSid")));
	// }
	//
	// Criteria criteria = new Criteria();
	// criteria.put("instanceSid", instanceSid);
	// List<ResourceInstanceVm> resourceInstanceVmList =
	// resourceInstanceVmService.selectByParams(criteria);
	//
	// if (null != resourceInstanceVmList && resourceInstanceVmList.size() > 0)
	// {
	// criteria = new Criteria();
	// criteria.put("resInstanceHostSid",
	// resourceInstanceVmList.get(0).getResInstanceSid());
	// resourceInstanceStorageList =
	// resourceInstanceStorageService.selectByParams(criteria);
	// }
	//
	// String json = JsonUtil.toJson(resourceInstanceStorageList);
	// return Response.status(Status.OK).entity(json).build();
	// } catch (Exception e) {
	// e.printStackTrace();
	// String resultJson =
	// InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
	// return
	// Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
	// }
	// }
	//
	// /**
	// * 根据条件获取后台serviceInstance列表 资源信息
	// */
	// @Override
	// @WebMethod
	// @POST
	// @Path("/platform/searchServiceInstanceDetailResource")
	// public Response searchServiceInstanceDetailResource(String params) {
	// try {
	//
	// long instanceSid = 0L;
	// if (!StringUtil.isNullOrEmpty(params)) {
	// Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
	// instanceSid =
	// Long.parseLong(StringUtil.nullToEmpty(map.get("instanceSid")));
	// }
	//
	// Criteria criteria = new Criteria();
	// criteria.put("serviceInstanceSid", instanceSid);
	// List<ResourceInstance> reslist =
	// resourceInstanceService.selectByParams(criteria);
	//
	// String json = JsonUtil.toJson(reslist);
	// return Response.status(Status.OK).entity(json).build();
	// } catch (Exception e) {
	// e.printStackTrace();
	// String resultJson =
	// InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
	// return
	// Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
	// }
	// }
}
