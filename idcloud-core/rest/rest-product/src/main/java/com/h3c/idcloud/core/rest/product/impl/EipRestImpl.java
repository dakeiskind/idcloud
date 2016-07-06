package com.h3c.idcloud.core.rest.product.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.res.ResVmNetwork;
import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.rest.product.EipRest;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.res.api.ResFloatingIpService;
import com.h3c.idcloud.core.service.res.api.ResVmNetworkService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.InterfaceResult;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.RequestContextUtil;
import com.h3c.idcloud.infrastructure.common.util.SpringContextHolder;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Created by Administrator on 2016/4/6.
 */
@Component
public class EipRestImpl implements EipRest {
    @Reference(version = "1.0.0")
    ServiceInstanceService serviceInstanceService;
	@Reference(version = "1.0.0")
	ResFloatingIpService resFloatingIpService;
    @Reference(version = "1.0.0")
    ResVmNetworkService resVmNetworkService;
    @Override
    public Response selectFloatingIpInfo(@Context HttpServletRequest request) {
        // 得到当前用户
        AuthUser userInfo = RequestContextUtil.getAuthUserInfo(request);
		Criteria example = new Criteria();
        example.put("ownerId", userInfo.getAccount());
		example.put("mgtObjSid", userInfo.getMgtObjSid());
		example.put("serviceSid", WebConstants.ServiceSid.FLOATING_IP);
		List<ServiceInstance> list = this.serviceInstanceService.selectFloatingIpInfo(example);
		// 返回结果
		return Response.status(Response.Status.OK).entity(new RestResult(list)).build();

	}

	@Override
	public Response attachFloatingIp(String params,@Context HttpServletRequest request) {
		AuthUser userInfo = RequestContextUtil.getAuthUserInfo(request);
		String resultJson = "";
		try {
			ResCommonInst baseInput = new ResCommonInst();
			baseInput.setMgtObjSid(userInfo.getMgtObjSid());
			baseInput.setUserAccount(userInfo.getAccount());
			baseInput.setUserPass(userInfo.getPassword());
			baseInput.setResSpecParam(params);
//			ResFloatingIpService resFloatingIpService = SpringContextHolder.getBean("resFloatingIpService");
			ResInstResult resInstResult = resFloatingIpService.attachFloatingIp(baseInput);
			if(resInstResult.getStatus()){
				resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
						.getMessage(WebConstants.MsgCd.INFO_RELATION_SUCCESS), null));
			}else{
				resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, "关联失败！", null));
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,"关联失败！", null));
		}
		return Response.status(Response.Status.OK).entity(resultJson).build();
	}

	@Override
	public Response dettachFloatingIp(String params, @Context HttpServletRequest request) {
		AuthUser userInfo = RequestContextUtil.getAuthUserInfo(request);
		String resultJson = "";
		try {
			ResCommonInst baseInput = new ResCommonInst();
			baseInput.setMgtObjSid(userInfo.getMgtObjSid());
			baseInput.setUserAccount(userInfo.getAccount());
			baseInput.setUserPass(userInfo.getPassword());
			baseInput.setResSpecParam(params);
			ResInstResult resInstResult = resFloatingIpService.detachFloatingIp(baseInput);
			System.out.println(JsonUtil.toJson("+++++++++++++++++++++我在这儿"+resInstResult.getStatus()));
			if(resInstResult.getStatus()){
				resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
						.getMessage(WebConstants.MsgCd.INFO_RELATION_SUCCESS), null));
			}else{
				resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, "解绑失败！", null));
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,"解绑失败！", null));
		}
		return Response.status(Response.Status.OK).entity(resultJson).build();

	}

    @Override
    public Response releaseEipInstance(
            @PathParam("serviceInstanceSid") String serviceInstanceSid, @Context HttpServletRequest request) {
        AuthUser user = RequestContextUtil.getAuthUserInfo(request);
        boolean releaseResult = this.serviceInstanceService.releaseServiceInstance(Long.parseLong(serviceInstanceSid),
                                                                                   WebConstants.ResourceType.RES_FLOATING_IP,user);
        return Response.ok(new RestResult(releaseResult)).build();
    }

    @Override
    public Response getInstanceForFloatingip(String params, @Context HttpServletRequest request) {
        AuthUser userInfo = RequestContextUtil.getAuthUserInfo(request);
        // 查询退订中、已开通
        String statusParams = "'" + WebConstants.ServiceInstanceStatus.CANCELING + "','"
                              + WebConstants.ServiceInstanceStatus.CHANGEING + "','" + WebConstants.ServiceInstanceStatus.OPENED
                              + "'".replace(" ", "");
        try {
            Criteria example = new Criteria();
            if (!StringUtil.isNullOrEmpty(params)) {
                Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
                example.setCondition(map);
            }
            example.put("mgtObjSid", userInfo.getMgtObjSid());
			example.put("resVmInsName","01");
//            example.put("statusParams", statusParams);
            example.setOrderByClause("A.DREDGE_DATE desc , H.VM_NAME");
            List<ServiceInstance> serviceInstanceList = this.serviceInstanceService.selectByParams(example);

//            List<ServiceInstance> instanceResult = new ArrayList<ServiceInstance>();
//
//            if (!CollectionUtils.isEmpty(serviceInstanceList)) {
//                for (ServiceInstance serviceInstance : serviceInstanceList) {
//                    if (serviceInstance != null && !StringUtil.isNullOrEmpty(serviceInstance.getResSid())) {
//                        example = new Criteria();
//                        example.put("resVmSid", serviceInstance.getResSid());
////                        example.put("networkType", WebConstants.ResNetworkType.USER_DEFINED);
//
//                        List<ResVmNetwork> networks = this.resVmNetworkService.selectNetsByParams(example);
//                        if(!CollectionUtils.isEmpty(networks)){
//                            instanceResult.add(serviceInstance);
//                        }
//                    }
//                }
//            }

            return Response.status(Response.Status.OK).entity(new RestResult(serviceInstanceList)).build();
        } catch (Exception e) {
            e.printStackTrace();
            String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
        }
    }
}
