package com.h3c.idcloud.core.rest.order.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.common.ResDataUtils;
import com.h3c.idcloud.core.pojo.dto.order.Order;
import com.h3c.idcloud.core.pojo.dto.order.OrderDetail;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceSpec;
import com.h3c.idcloud.core.pojo.dto.system.ApproveRecord;
import com.h3c.idcloud.core.pojo.dto.system.Attachments;
import com.h3c.idcloud.core.pojo.dto.system.UserMgtObjKey;
import com.h3c.idcloud.core.pojo.dto.user.Role;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.vo.order.UserOrderVo;
import com.h3c.idcloud.core.rest.order.OrderRest;
import com.h3c.idcloud.core.service.order.api.OrderDetailService;
import com.h3c.idcloud.core.service.order.api.OrderService;
import com.h3c.idcloud.core.service.product.api.BusinesstypeService;
import com.h3c.idcloud.core.service.product.api.MgtObjResService;
import com.h3c.idcloud.core.service.product.api.ServiceDefineService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceChangeLogService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceSpecService;
import com.h3c.idcloud.core.service.res.api.ResImageService;
import com.h3c.idcloud.core.service.res.api.ResTopologyService;
import com.h3c.idcloud.core.service.res.api.ResVeService;
import com.h3c.idcloud.core.service.system.api.*;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.MyError;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.exception.ServiceException;
import com.h3c.idcloud.infrastructure.common.pojo.*;
import com.h3c.idcloud.infrastructure.common.util.*;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 订单Rest接口实现类
 *
 * @author
 */
@Component
public class OrderRestImpl implements OrderRest {

    Logger log = Logger.getLogger(this.getClass());

    /**
     * 订单Service
     */
    @Reference(version = "1.0.0")
    private OrderService orderService;

    /**
     * 用户Service
     */
    @Reference(version = "1.0.0")
    private UserService userService;

    /**
     * 审核记录Service
     */
    @Reference(version = "1.0.0")
    private ApproveRecordService approveRecordService;

    /** 任务Service */
//	@Autowired
//	private TaskService taskService;

    /**
     * 订单详细Service
     */
    @Reference(version = "1.0.0")
    private OrderDetailService orderDetailService;
//
//	/** 订单服务Service */
//	@Autowired
//	OrderService orderSerivce;

    /**
     * 服务定义Service
     */
    @Reference(version = "1.0.0")
    ServiceDefineService serviceDefineService;

    /**
     * 服务实例Service
     */
    @Reference(version = "1.0.0")
    private ServiceInstanceService serviceInstanceService;

    @Reference(version = "1.0.0")
    private ServiceInstanceChangeLogService changeLogService;

    /**
     * 日志Service
     */
    @Reference(version = "1.0.0")
    SysTLogRecordService sysTLogRecordService;

    /**
     * 服务实例规格Service
     */
    @Reference(version = "1.0.0")
    private ServiceInstanceSpecService instanceSpecService;

    /**
     * 资源拓扑Service
     */
    @Reference(version = "1.0.0")
    private ResTopologyService resTopologyService;

    /**
     * 镜像Service
     */
    @Reference(version = "1.0.0")
    private ResImageService resImageService;

    /**
     * 业务资源关联Service
     */
    @Reference(version = "1.0.0")
    private MgtObjResService bizResService;

    /**
     * 业务Service
     */
    @Reference(version = "1.0.0")
    private BusinesstypeService bizSerivce;

    @Reference(version = "1.0.0")
    private AttachmentService attachmentService;

    @Reference(version = "1.0.0")
    private MgtObjService mgtObjService;

    @Reference(version = "1.0.0")
    private UserMgtObjService userMgtObjService;

    @Reference(version = "1.0.0")
    private ResVeService resVeService;

    /**
     * 根据条件查询订单
     */
    @Override
    @WebMethod
    @POST
    public Response findAllOrderList(Order order) {

        // 查询条件设置
        Criteria param = new Criteria();
        if (!StringUtil.isNullOrEmpty(order.getOrderId())) {
            param.put("orderIdLike", order.getOrderId());
        }
        if (!StringUtil.isNullOrEmpty(order.getTanentId())) {
            param.put("tenantId", order.getTanentId());
        }
        if (!StringUtil.isNullOrEmpty(order.getStatus())) {
            param.put("status", order.getStatus());
        }
        param.setOrderByClause("CREATED_DT");

        // 查询符合条件的数据
        List<Order> orderList = orderService.selectByParams(param);
        String json = JsonUtil.toJson(orderList);

        return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 根据条件查询订单admin 使用查询所有
     */
    @Override
    @POST
    @Path("/orderList")
    public Response getOrderList(String params, @Context HttpServletRequest servletRequest) {

        Criteria example = new Criteria();
        Map<String, Object> conditionMap = new HashMap<String, Object>();

        if (!StringUtil.isNullOrEmpty(params)) {
            // 将条件转换为Map
            conditionMap = JsonUtil.fromJson(params, Map.class);
            example.setCondition(conditionMap);
        }
        example.setOrderByClause("CREATED_DT");
        // 查询符合条件的数据
        List<Order> orderList = orderService.selectByParams(example);
        String json = JsonUtil.toJson(orderList);
        return Response.status(Status.OK).entity(new RestResult(json)).build();
    }

    @Override
    public Response displayPersonalOrderList(String params,
                                             @Context HttpServletRequest servletRequest)
            throws IOException {
        Criteria example = new Criteria();
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        if (!StringUtil.isNullOrEmpty(params)) {
            // 将条件转换为Map
            conditionMap = JsonUtil.fromJson(params, Map.class);
        }
        Map<String, Object> forConditionMap = new HashMap<String, Object>();
        conditionMap.forEach((key,value) ->{
            if(!StringUtil.isNullOrEmpty(value))
                forConditionMap.put(key,value);
        });
        example.setCondition(forConditionMap);

        // TODO  --start

        // TODO  --end
        // 取得当前登录用户
        AuthUser authUserInfo = RequestContextUtil.getAuthUserInfo(servletRequest);

        // TODO  --start
		/*
        Criteria param = new Criteria();
		param.put("userSid", authUserInfo.getUserSid());

		List<UserMgtObjKey> userMgts = userMgtObjService.selectByParams(param);
		if(!CollectionUtils.isEmpty(userMgts)){
			List<Role> roles = authUserInfo.getRoles();
			boolean roleFlag = false;
			String mgtObjRole = PropertiesUtil.getProperty("mgtobj.manager.sid");
			if(!CollectionUtils.isEmpty(roles)){
				for (Role role : roles) {
					if(role.getRoleSid().toString().equals(mgtObjRole)){
						roleFlag = true;
						break;
					}
				}
			}
			if(!roleFlag){
				conditionMap.put("ownerId", user.getAccount());
			}else{
				List<Long> mgtObjSids = new ArrayList<Long>();
				for(UserMgtObjKey mgtObjKey : userMgts) {
					mgtObjSids.add(mgtObjKey.getMgtObjSid());
				}
				conditionMap.put("mgtObjSids", mgtObjSids);
			}
		}else if(WebConstants.USER_TYPE.FOREGROUND.equals(user.getUserType())){
			conditionMap.put("ownerId", user.getAccount());
		}
		*/

        // TODO  --end

        example.put("mgtObjSid", authUserInfo.getMgtObjSid());
        example.put("ownerId", authUserInfo.getAccount());
        example.setOrderByClause("A.CREATED_DT");
        // 查询符合条件的数据
        List<UserOrderVo> orderList = orderService.selectPersonalOrderList(example);
        String json = JsonUtil.toJson(new RestResult(orderList));
        return Response.status(Response.Status.OK).entity(json).build();

    }

    /**
     * 审批订单
     */
    @Override
    @WebMethod
    @POST
    @Path("/approveOrder")
    public Response tenantAdminApprove(String params) throws IOException {
        //ChengQi start
//		String returnJson = "";
//		Map<String, Object> map;
//		map = JsonUtil.fromJson(params, Map.class);
//		String checkStatus = String.valueOf(map.get("checkStatus"));
//		String checkcomments = String.valueOf(map.get("checkcomments"));
//		String processInstanceId = String.valueOf(map.get("processInstanceId"));
//		String approvorAction = String.valueOf(map.get("approvorAction"));
//		String processType = String.valueOf(map.get("processType"));
//		boolean result = this.approveRecordService.tenantAdminApprove(
//				checkStatus, checkcomments, processInstanceId, approvorAction,
//				processType);
//		if (result) {
//			returnJson = JsonUtil
//					.toJson(new RestResult(
//							RestResult.SUCCESS,
//							WebUtil.getMessage(WebConstants.MsgCd.INFO_APPROVE_SUCCESS),
//							null));
//		} else {
//			returnJson = JsonUtil
//					.toJson(new RestResult(
//							RestResult.FAILURE,
//							WebUtil.getMessage(WebConstants.MsgCd.ERROR_APPROVE_FAILURE),
//							null));
//		}
//		return Response.status(Status.OK).entity(returnJson).build();
        String returnJson;
        boolean result = false;

        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);

            String checkStatus = StringUtil.nullToEmpty(map.get("checkStatus"));
            String checkcomments = StringUtil.nullToEmpty(map.get("checkcomments"));
            String processInstanceId = StringUtil.nullToEmpty(map.get("processInstanceId"));
            String approvorAction = StringUtil.nullToEmpty(map.get("approvorAction"));
            String processType = StringUtil.nullToEmpty(map.get("processType"));
            result =
                    approveRecordService
                            .createAdminApprove(map, checkStatus, checkcomments, processInstanceId,
                                                approvorAction, processType);

            if (result == true) {
                returnJson =
                        JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS,
                                                       WebUtil.getMessage(
                                                               WebConstants.MsgCd.INFO_APPROVE_SUCCESS),
                                                       null));
            } else {
                returnJson =
                        JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,
                                                       WebUtil.getMessage(
                                                               WebConstants.MsgCd.ERROR_APPROVE_FAILURE),
                                                       null));
            }

            return Response.status(Status.OK).entity(returnJson).build();
        } catch (Exception e) {
            e.printStackTrace();
            String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
        }
        //ChengQi end;
    }

    @Override
    public Response saveServiceOrder(
            @RequestBody ArrayList<Map<String, Object>> models,
            @Context HttpServletRequest servletRequest) {

        AuthUser authUserInfo = RequestContextUtil.getAuthUserInfo(servletRequest);
        String orderId = orderService.createServiceOrder(models, authUserInfo);
        return Response.status(Status.OK)
                .entity(JsonUtil.toJson(new RestResult(orderId))).build();
    }

    @Override
    public Response payServiceOrder(String orderId,@Context HttpServletRequest servletRequest) {
        AuthUser authUserInfo = RequestContextUtil.getAuthUserInfo(servletRequest);
        orderService.payServiceOrder(orderId,authUserInfo);
        return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(true)))
                .build();
    }

    @Override
    public Response cancelServiceOrder(String orderId,@Context HttpServletRequest servletRequest) {
        AuthUser authUserInfo = RequestContextUtil.getAuthUserInfo(servletRequest);
        orderService.cancelServiceOrder(orderId,authUserInfo);
        return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(true)))
                .build();
    }

    /**
     * 删除订单
     */
    @Override
    @GET
    @Path("/deleteOrder/{orderId}")
    public Response deleteOrder(@PathParam("orderId") String orderId) {
        String json = "";
        if (!StringUtil.isNullOrEmpty(orderId)) {
            //找到订单
            Order order = orderService.selectByPrimaryKey(Long.parseLong(orderId));
            if (order != null) {
                //找到订单详情
                Criteria example = new Criteria();
                example.put("orderId", order.getOrderId());
                orderDetailService.deleteByParams(example);
                //找到服务实例
                List<ServiceInstance> instances = serviceInstanceService.selectByParams(example);
                if (!CollectionUtils.isEmpty(instances)) {
                    for (ServiceInstance si : instances) {
                        //找到服务实例相关记录
                        Criteria param = new Criteria();
                        param.put("instanceSid", si.getInstanceSid());
                        instanceSpecService.deleteByParams(param);
                        changeLogService.deleteByParams(param);
                    }
                }
                serviceInstanceService.deleteByParams(example);
                orderService.deleteByPrimaryKey(Long.parseLong(orderId));
            }
//			json = JsonUtil.toJson(new RestResult(RestResult.SUCCESS,
//					WebUtil.getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS)));
        } else {
//			json = JsonUtil.toJson(new RestResult(RestResult.SUCCESS,
//					WebUtil.getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS)));
        }
        return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 获取订单详情
     */
    @Override
    @WebMethod
    @GET
    @Path("/orderDetail")
    public Response getOrderDetail(@QueryParam("orderId") String orderId) {
        Criteria example = new Criteria();

        // 获取当前用户
//		User user = AuthUtil.getAuthUser();
//		example.put("ownerId", user.getAccount());
        if (StringUtil.isNullOrEmpty(orderId)) {
            List<OrderDetail> orderDetailList = this.orderDetailService
                    .selectByParams(example);
            String json = JsonUtil.toJson(orderDetailList);
            return Response.status(Status.OK).entity(json).build();
        } else {
            example.put("orderId", orderId);
            List<OrderDetail> orderDetailList = this.orderDetailService
                    .selectByParams(example);
            String json = JsonUtil.toJson(orderDetailList);
            return Response.status(Status.OK).entity(json).build();
        }

    }

    /**
     * 查询单一订单以及详情
     */
    @Override
    @WebMethod
    @GET
    @Path("/getOrder")
    public Response getOrder(@QueryParam("orderId") String orderId) {
        Criteria example = new Criteria();
        example.put("orderId", orderId);
        List<Order> orderList = this.orderService.selectByParams(example);
        Order order = orderList.get(0);
        List<OrderDetail> orderDetailList = this.orderDetailService
                .selectByParams(example);
        order.setOrderDetail(orderDetailList);

        Criteria criteria = new Criteria();
        criteria.put("orderId", order.getOrderId());
        criteria.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
        List<ServiceInstance> vmServiceInstances = serviceInstanceService.selectByParams(criteria);

        for (ServiceInstance vmInstance : vmServiceInstances) {
            List<ServiceInstanceSpec>
                    serviceInstanceSpecs =
                    this.instanceSpecService.selectByInstanceSpecBySid(vmInstance.getInstanceSid());
            ServiceInstanceSpec
                    spec =
                    ResDataUtils.getSpecObjectFromSpecs(WebConstants.InstanceSpecType.OS,
                                                        serviceInstanceSpecs);
            if (spec != null) {
                vmInstance.setOsVersionName(spec.getValueText());
            }
        }
        order.setVmServiceInstances(vmServiceInstances);
        return Response.status(Status.OK).entity(new RestResult(order)).build();
    }

    /**
     * 取消订单
     */
    @Override
    @WebMethod
    @GET
    @Path("/cancelOrder")
    public Response cancelOrder(@QueryParam("orderSid") String orderSid) {
        String json = "";
        boolean result = this.orderService
                .cancelOrder(Long.parseLong(orderSid));
        if (result) {
            json = InterfaceResult.setResult(WebConstants.ResultStatus.SUCCESS,
                                             null);
            return Response.status(Status.OK).entity(json).build();
        } else {
            String returnJson = JsonUtil.toJson(new RestResult(
                    RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_CANCEL_ORDER),
                    null));
            return Response.status(Status.OK).entity(returnJson).build();
        }
    }

    /**
     * 判断租户是否已订购Exchange和Sharepoint服务
     */
    @Override
    @WebMethod
    @GET
    @Path("/checkExSpOrder")
    public Response checkTenantExAndSpOrder(@QueryParam("serviceSid") long serviceSid) {
//		User user = AuthUtil.getAuthUser();
//		if (user != null) {
//			Criteria criteria = new Criteria();
//			criteria.put("serviceSid", serviceSid);
//			criteria.put("ownerId", user.getAccount());
//			criteria.put("statusParams", "03");
//			List<ServiceInstance> siList = this.serviceInstanceService.selectByParams(criteria);
//			if (siList.size() > 0) {
//				String returnJson = JsonUtil
//						.toJson(new RestResult(
//								RestResult.FAILURE,
//								WebUtil.getMessage(WebConstants.MsgCd.WARNING_SERVICE_REPEAT),
//								null));
//				return Response.status(Status.OK).entity(returnJson)
//						.build();
//			} else {
//				return Response.status(Status.OK).entity(new RestResult())
//						.build();
//			}
//
//		}else{
//			String returnJson = JsonUtil
//					.toJson(new RestResult(
//							RestResult.FAILURE,
//							WebUtil.getMessage(WebConstants.MsgCd.WARNING_SERVICE_REPEAT),
//							null));
//			return Response.status(Status.OK).entity(returnJson).build();
//		}
        return null;
    }

    /**
     * 判断是否可重复订购
     */
    @Override
    @WebMethod
    @GET
    @Path("/checkRepeatOrder")
    public Response checkRepeatOrder(@QueryParam("serviceSid") long serviceSid) {
//		User user = AuthUtil.getAuthUser();
//		if (user != null) {
//			ServiceDefine service = this.serviceDefineService
//					.selectByPrimaryKey(serviceSid);
//			if (WebConstants.RepeatOrder.YES.equals(service.getRepeatOrder())) {
//				return Response.status(Status.OK).entity(new RestResult(RestResult.SUCCESS))
//						.build();
//			} else {
//
//
//				String statusNotParams = "'"
//						+ WebConstants.ServiceInstanceStatus.CANCELED + "','"
//						+ WebConstants.ServiceInstanceStatus.EXCEPTION+"','"
//						+WebConstants.ServiceInstanceStatus.INVALID
//						+ "'".replace(" ", "");
//				Criteria criteria = new Criteria();
//				criteria.put("serviceSid", serviceSid);
//				criteria.put("ownerId", user.getAccount());
//				criteria.put("statusNotParams", statusNotParams);
//				List<ServiceInstance> siList = this.serviceInstanceService
//						.selectByParams(criteria);
//				if (siList.size() > 0) {
//					String returnJson = JsonUtil
//							.toJson(new RestResult(
//									RestResult.FAILURE,
//									WebUtil.getMessage(WebConstants.MsgCd.WARNING_SERVICE_REPEAT),
//									null));
//					return Response.status(Status.OK).entity(returnJson)
//							.build();
//				} else {
//					return Response.status(Status.OK).entity(new RestResult())
//							.build();
//				}
//			}
//		} else {
//			String returnJson = JsonUtil
//					.toJson(new RestResult(
//							RestResult.FAILURE,
//							WebUtil.getMessage(WebConstants.MsgCd.WARNING_SERVICE_REPEAT),
//							null));
//			return Response.status(Status.OK).entity(returnJson).build();
//		}
        return null;

    }


    /**
     * 查询单一订单以及详情
     */
    @Override
    @WebMethod
    @GET
    @Path("/getApproveOrder")
    public Response getApproveOrder(@QueryParam("orderId") String orderId,
                                    @QueryParam("processInstanceId") String processInstanceId) {
        Criteria example = new Criteria();
        example.put("orderId", orderId);
        List<Order> orderList = this.orderService.selectByParams(example);
        String portalHost = PropertiesUtil.getProperty("portal.inner.url");
        Order order = orderList.get(0);
        List<OrderDetail> orderDetailList = this.orderDetailService.selectByParams(example);
        List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
        for (OrderDetail orderDetail : orderDetailList) {
            example = new Criteria();
            example.put("processInstanceId", processInstanceId);
            List<ApproveRecord> approveRecords = approveRecordService.selectByParams(example);
            if (approveRecords != null && approveRecords.size() > 0) {
                if (approveRecords.get(0).getProcessType().equals(WebConstants.ProcessType.VM_OPEN)
                    || approveRecords.get(0).getProcessType()
                            .equals(WebConstants.ProcessType.VM_CANCEL)) {
                    if (orderDetail.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_VM)) {
                        orderDetails.add(orderDetail);
                    }
                } else if (approveRecords.get(0).getProcessType()
                                   .equals(WebConstants.ProcessType.STORAGE_OPEN)
                           || approveRecords.get(0).getProcessType()
                                   .equals(WebConstants.ProcessType.STORAGE_CANCEL)) {
                    if (orderDetail.getServiceSid()
                            .equals(WebConstants.ServiceSid.SERVICE_STORAGE)) {
                        orderDetails.add(orderDetail);
                    }
                } else if (approveRecords.get(0).getProcessType()
                                   .equals(WebConstants.ProcessType.DEPLOYMENT_OPEN)
                           || approveRecords.get(0).getProcessType()
                                   .equals(WebConstants.ProcessType.DEPLOYMENT_CANCEL)) {
                    if (orderDetail.getServiceSid()
                            .equals(WebConstants.ServiceSid.SERVICE_DEPLOYMENT)) {
                        orderDetails.add(orderDetail);
                    }
                } else if (approveRecords.get(0).getProcessType()
                                   .equals(WebConstants.ProcessType.EXCHANGE_OPEN)
                           || approveRecords.get(0).getProcessType()
                                   .equals(WebConstants.ProcessType.EXCHANGE_CANCEL)) {
                    if (orderDetail.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_EX)) {
                        orderDetails.add(orderDetail);
                    }
                } else if (approveRecords.get(0).getProcessType()
                                   .equals(WebConstants.ProcessType.SHAREPOINT_OPEN)
                           || approveRecords.get(0).getProcessType()
                                   .equals(WebConstants.ProcessType.SHAREPOINT_CANCEL)) {
                    if (orderDetail.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_SP)) {
                        orderDetails.add(orderDetail);
                    }
                }
            }
        }
        Criteria criteria = new Criteria();
        criteria.put("orderId", order.getOrderId());
        criteria.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
        List<ServiceInstance> vmServiceInstances = serviceInstanceService.selectByParams(criteria);

        for (ServiceInstance vmInstance : vmServiceInstances) {
            List<ServiceInstanceSpec>
                    serviceInstanceSpecs =
                    this.instanceSpecService.selectByInstanceSpecBySid(vmInstance.getInstanceSid());
            ServiceInstanceSpec
                    spec =
                    ResDataUtils.getSpecObjectFromSpecs(WebConstants.InstanceSpecType.OS,
                                                        serviceInstanceSpecs);
            if (spec != null) {
                vmInstance.setOsVersionName(spec.getValueText());
            }
        }

        order.setVmServiceInstances(vmServiceInstances);
        order.setOrderDetail(orderDetails);
        order.setPortlaHost(portalHost);
        //ChengQi start
        //加入是否为到达最终审判的判断
        ActivitiWorkflowUtil activitiWorkflowUtil = new ActivitiWorkflowUtil();
        Boolean isFinal = activitiWorkflowUtil.isFinal(processInstanceId);
        if (isFinal != null) {
            order.setIsFinal(isFinal);
        }
        Criteria criteria1 = new Criteria();
        criteria1.put("attachmentSid", order.getContractFile().trim());
        Attachments
                attachments =
                this.attachmentService.selectByPrimaryKey(order.getContractFile());
        if (attachments != null) {
            order.setContractFile(order.getContractFile() + "/" + attachments.getOriginalName());
        }
        Attachments projectFile = this.attachmentService.selectByPrimaryKey(order.getProjectFile());
        if (projectFile != null) {
            order.setProjectFile(order.getProjectFile() + "/" + projectFile.getOriginalName());
        }
        //ChengQi end
        String json = JsonUtil.toJson(order);
        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    public Response modifyOrderDetail(OrderDetail orderDetail) {
        String returnJson;
        try {
            int updateNum = orderDetailService.updateByPrimaryKeySelective(orderDetail);
            if (updateNum == 0) {
                throw new ServiceException(
                        "Order detail record is not updated. updateNum=" + updateNum);
            }
            returnJson =
                    JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil.getMessage(
                            WebConstants.MsgCd.INFO_UPDATE_SUCCESS), null));
            return Response.status(Status.OK).entity(returnJson).build();
        } catch (Exception e) {
            log.error("更新订单详情失败", e);
            returnJson =
                    JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(
                            WebConstants.MsgCd.ERROR_UPDATE_FAILURE), null));
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(returnJson).build();
        }
    }

    public Response getServiceInstanceSpecs(Long instanceSid) {
        String returnJson;
        try {
            //查询服务实例信息
            List<ServiceInstanceSpec>
                    serviceInstanceSpecs =
                    this.instanceSpecService.selectByInstanceSpecBySid(instanceSid);
            returnJson = JsonUtil.toJson(serviceInstanceSpecs);
            return Response.status(Status.OK).entity(returnJson).build();
        } catch (Exception e) {
            log.error("getServiceInstanceSpecs failure.", e);
            returnJson =
                    JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(
                            WebConstants.MsgCd.ERROR_QUERY_FAILURE), null));
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(returnJson).build();
        }
    }

    public Response getServiceInstanceSpecsForDataDisk(Long instanceSid) {
        String returnJson;
        try {
            //查询服务实例信息
            List<ServiceInstanceSpec>
                    serviceInstanceSpecs =
                    this.instanceSpecService.selectByInstanceSpecForDataDiskBySid(instanceSid);
            returnJson = JsonUtil.toJson(serviceInstanceSpecs);
            return Response.status(Status.OK).entity(returnJson).build();
        } catch (Exception e) {
            log.error("getServiceInstanceSpecs failure.", e);
            returnJson =
                    JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(
                            WebConstants.MsgCd.ERROR_QUERY_FAILURE), null));
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(returnJson).build();
        }
    }

    public Response checkAllQuota(String orderId) {
        String returnJson;
        try {
//			Map<String, Object> resultMap = this.serviceInstanceService.checkAllQuota(orderId);
            //测试用
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("checkResult", true);
            returnJson = JsonUtil.toJson(resultMap);
            return Response.status(Status.OK).entity(returnJson).build();
        } catch (Exception e) {
            log.error("checkAllQuota failure.", e);
            returnJson =
                    JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(
                            WebConstants.MsgCd.ERROR_QUERY_FAILURE), null));
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(returnJson).build();
        }
    }

    public Response checkBizQuota(Long mgtObjSid, List<Map<String, Object>> curOrderQuotaList) {
        String returnJson;
        try {
            Map<String, Object>
                    resultMap =
                    this.serviceInstanceService.checkAllQuota(mgtObjSid, curOrderQuotaList);
            returnJson = JsonUtil.toJson(resultMap);
            return Response.status(Status.OK).entity(returnJson).build();
        } catch (Exception e) {
            log.error("checkBizQuota failure.", e);
            returnJson =
                    JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(
                            WebConstants.MsgCd.ERROR_QUERY_FAILURE), null));
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(returnJson).build();
        }
    }

    @Override
    public Response findImages(String params) {
        String json = "";
//		try {
//			List<ResImage> resImageList = new ArrayList<ResImage>();
//
////			User user = AuthUtil.getAuthUser();
//			User user = new User();
//			Criteria criteria = new Criteria();
//			Criteria param = new Criteria();
//			param.put("userSid", user.getUserSid());
//			List<UserMgtObjKey> userMgts = userMgtObjService.selectByParams(param);
//			if(!CollectionUtils.isEmpty(userMgts)){
//				List<Long> mgtObjSids = new ArrayList<Long>();
//				for(UserMgtObjKey mgtObjKey : userMgts) {
//					mgtObjSids.add(mgtObjKey.getMgtObjSid());
//				}
//				criteria.put("bizSidList", mgtObjSids);
//			}
//
//			criteria.put("resSetType", WebConstants.ResourceType.RES_HOST);
//			List<MgtObjRes> mgtObjReses = this.bizResService.selectByParams(criteria);
//			List<String> hostSids = new ArrayList<String>();
//			for(MgtObjRes mgtObjRes : mgtObjReses) {
//				hostSids.add(mgtObjRes.getResSetSid());
//			}
//
//			List<ResTopology> resVeList =  null;
//
//			if(hostSids.size() > 0) {
//				criteria = new Criteria();
//				criteria.put("hosts", hostSids);
//				resVeList = this.mgtObjService.selectVeListByHost(criteria);
//			}
//
//			if(resVeList!=null&&resVeList.size()>0){
//				//查询用户私有镜像
//				for(ResTopology resVe:resVeList){
//					Criteria criteria2=new Criteria();
//					criteria2.put("resVeSid", resVe.getResTopologySid());
////					criteria2.put("createdBy", user.getAccount());
//					criteria2.put("imageType", null);
//					criteria2.put("status", WebConstants.ImageStatus.AVAILABILITY);
//					criteria2.setOrderByClause("A.IMAGE_NAME");
//					List<ResImage> resImageByVe=this.resImageService.selectOSVersionByParams(criteria2);
//					resImageList.addAll(resImageByVe);
//				}
//			}
//			json = JsonUtil.toJson(resImageList);
//			return Response.status(Status.OK).entity(json).build();
//		} catch(Exception e) {
//			log.error("findImages failure.", e);
//			json = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, "镜像获取失败", null));
//			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(json).build();
//		}
        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    public Response findImageOSVer(String params) {
        String json = "";
//		try {
////			User user = AuthUtil.getAuthUser();
//			User user = new User();
//
//			Criteria criteria = new Criteria();
//			Criteria param = new Criteria();
//			param.put("userSid", user.getUserSid());
//			List<UserMgtObjKey> userMgts = userMgtObjService.selectByParams(param);
//			if(!CollectionUtils.isEmpty(userMgts)){
//				List<Long> mgtObjSids = new ArrayList<Long>();
//				for(UserMgtObjKey mgtObjKey : userMgts) {
//					mgtObjSids.add(mgtObjKey.getMgtObjSid());
//				}
//				criteria.put("bizSidList", mgtObjSids);
//			}
//
//			criteria.put("resSetType", "RES-VE");
//			List<MgtObjRes> mgtObjReses = this.bizResService.selectByParams(criteria);
//
//			List<ResImage> images = new ArrayList<ResImage>();
//			if(!CollectionUtils.isEmpty(mgtObjReses)){
//				for(MgtObjRes mgtObjRes : mgtObjReses) {
//					Criteria criteria2=new Criteria();
//					criteria2.put("resTopologyNameVE",mgtObjRes.getResSetSid());
//					criteria2.put("imageType", null);
//					criteria2.put("status", WebConstants.ImageStatus.AVAILABILITY);
//					criteria2.setOrderByClause("A.IMAGE_NAME");
//					List<ResImage> resImageByVe=this.resImageMapper.selectOSVersionByParams(criteria2);
//					if(!CollectionUtils.isEmpty(resImageByVe)){
//						for (ResImage resImage : resImageByVe) {
//							boolean flag = true;
//							if(!StringUtil.isNullOrEmpty(resImage.getOsVersion())){
//								for (ResImage ri : images) {
//									if(resImage.getOsVersion().equals(ri.getOsVersion())){
//										flag = false;
//										break;
//									}
//								}
//								if(flag){
//									images.add(resImage);
//								}
//							}
//						}
//					}
//				}
//			}else{
//				//项目没有关联资源，读取父级所关联的资源
//				if(!CollectionUtils.isEmpty(userMgts)){
//					List<Long> mgtObjSids = new ArrayList<Long>();
//					for(UserMgtObjKey mgtObjKey : userMgts) {
//						MgtObj mgtObj = mgtObjService.selectByPrimaryKey(mgtObjKey.getMgtObjSid());
//						mgtObjSids.add(mgtObj.getParentId());
//					}
//					criteria = new Criteria();
//					criteria.put("bizSidList", mgtObjSids);
//					criteria.put("resSetType", "RZ");
//					List<MgtObjRes> mgtObjRzs = this.bizResService.selectByParams(criteria);
//					if(!CollectionUtils.isEmpty(mgtObjRzs)){
//						List<String> rzSids = new ArrayList<String>();
//						for (MgtObjRes mgtObjRes : mgtObjRzs) {
//							rzSids.add(mgtObjRes.getResSetSid());
//						}
//						criteria = new Criteria();
//						criteria.put("mgtObjRzSidList", rzSids);
//						List<ResVe> ves = resVeService.selectByParams(criteria);
//						if(!CollectionUtils.isEmpty(ves)){
//							for (ResVe resVe : ves) {
//								Criteria criteria2=new Criteria();
//								criteria2.put("resTopologyNameVE",resVe.getResTopologySid());
//								criteria2.put("imageType", null);
//								criteria2.put("status", WebConstants.ImageStatus.AVAILABILITY);
//								criteria2.setOrderByClause("A.IMAGE_NAME");
//								List<ResImage> resImageByVe=this.resImageMapper.selectOSVersionByParams(criteria2);
//								if(!CollectionUtils.isEmpty(resImageByVe)){
//									for (ResImage resImage : resImageByVe) {
//										boolean flag = true;
//										if(!StringUtil.isNullOrEmpty(resImage.getOsVersion())){
//											for (ResImage ri : images) {
//												if(resImage.getOsVersion().equals(ri.getOsVersion())){
//													flag = false;
//													break;
//												}
//											}
//											if(flag){
//												images.add(resImage);
//											}
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//			json = JsonUtil.toJson(images);
//			return Response.status(Status.OK).entity(json).build();
//		} catch(Exception e) {
//			log.error("findImages failure.", e);
//			json = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, "镜像获取失败", null));
//			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(json).build();
//		}
        return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 自服务首页--我的订单详情
     */
    @Override
    @WebMethod
    @GET
    @Path("/getMyOrderServiceInfo")
    public Response getMyOrderServiceInfo() {
//		User user = AuthUtil.getAuthUser();
        User user = null;
        String applicationInMonth = "";
        String openedInMonth = "";
        String refusedInMonth = "";
        String applicationInAll = "";
        String openedInAll = "";
        String refusedInAll = "";
        if (user != null) {
            Criteria criteria = new Criteria();
            criteria.put("ownerId", user.getAccount());
            List<Order> orderStatusInMonth = this.orderService.selectByOrderStatusInMonth(criteria);
            List<Order> orderStatusInAll = this.orderService.selectByOrderStatusInAll(criteria);
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            List<String>
                    statusName =
                    new ArrayList<String>(Arrays.asList("审核中订单", "已开通订单", "已拒绝订单"));
            List<String>
                    statusColor =
                    new ArrayList<String>(Arrays.asList("#FF8800", "#00AA00", "#CC0000 "));
            for (Order os : orderStatusInMonth) {
                applicationInMonth = os.getStatusapplication();
                openedInMonth = os.getStatusopened();
                refusedInMonth = os.getStatusrefused();
            }
            for (Order os : orderStatusInAll) {
                applicationInAll = os.getStatusapplication();
                openedInAll = os.getStatusopened();
                refusedInAll = os.getStatusrefused();
            }
            List<String>
                    dataArrApplication =
                    new ArrayList<String>(Arrays.asList(applicationInMonth, applicationInAll));
            List<String>
                    dataArrOpened =
                    new ArrayList<String>(Arrays.asList(openedInMonth, openedInAll));
            List<String>
                    dataArrRefused =
                    new ArrayList<String>(Arrays.asList(refusedInMonth, refusedInAll));
            List<List<String>>
                    dataArrList =
                    new ArrayList<List<String>>(
                            Arrays.asList(dataArrApplication, dataArrOpened, dataArrRefused));
            for (int i = 0; i < statusName.size(); i++) {
                Map<String, Object> serMap = new HashMap<String, Object>();
                serMap.put("data", dataArrList.get(i));
                serMap.put("name", statusName.get(i));
                serMap.put("color", statusColor.get(i));
                resultList.add(serMap);
            }
            String json = JsonUtil.toJson(resultList);
            return Response.status(Status.OK).entity(json).build();
        } else {
            log.error("当前用户为空");
            MyError error = new MyError();
            error.error = "Internal Server Error";
            error.errorCode = "500";
            return Response.status(320).entity(error).build();
        }
    }

    @Override
    @WebMethod
    @POST
    @Path("/fileUpload")
    public Response addTemp(List<Attachment> attachments, @Context HttpServletRequest request) {
        String json = "";
//		try{
//			List<String> sid = FileUtil.uploadAttachmentFiles(attachments);
//			json = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, "", sid));
//		} catch(Exception e){
//			log.error("fileupload error.", e);
//			json = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE), null));
//		}
        return Response.status(Status.OK).entity(json).build();

    }

    @Override
    @POST
    @Produces("application/json")
    @Path("/saveReSubmitOrder")
    public Response saveReSubmitOrder(
            @RequestBody ArrayList<Map<String, Object>> models,
            @Context HttpServletRequest servletRequest,
            @Context HttpServletResponse servletResponse) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        Date actStartDate = new Date();
        // 传入参数，返回订单号
        try {
            resultMap = orderService.updateServiceOrder(models);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultMap.put("status", "failure");
            resultMap.put("message", e.getMessage());
            return Response.status(Status.OK).entity(resultMap).build();
        }

        if (!resultMap.get("checkQuotaMsg").equals(StringUtil.EMPTY) && resultMap.get("orderId")
                .equals(StringUtil.EMPTY)) {
//			LoggerUtil.WriteLog(WebConstants.SYS_LOG_LEVEL.INFO, "订单管理","服务订购申请", actStartDate, StringUtil.EMPTY,
//					WebConstants.SYS_LOG_RESULT.FAIL, new Date(), StringUtil.nullToEmpty(resultMap.get("checkQuotaMsg")));
        } else {
//			LoggerUtil.WriteLog(WebConstants.SYS_LOG_LEVEL.INFO, "订单管理","服务订购申请", actStartDate, StringUtil.EMPTY,
//					WebConstants.SYS_LOG_RESULT.SUCCESS, new Date(), StringUtil.EMPTY);
        }
        return Response.status(Status.OK).entity(resultMap).build();
    }
}
