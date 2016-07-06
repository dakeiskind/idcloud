package com.h3c.idcloud.core.rest.product.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.databind.JsonNode;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.rest.product.CbsRest;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.res.api.ResVdService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.RequestContextUtil;

import com.sun.javafx.binding.StringFormatter;
import org.apache.commons.lang.BooleanUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Created by Administrator on 2016/4/20.
 */
@Component
public class CbsRestImpl implements CbsRest {

    @Reference(version = "1.0.0")
    private ServiceInstanceService serviceInstanceService;
    @Reference(version = "1.0.0")
    private ResVdService resVdService;
    @Reference(version = "1.0.0")
    private ServiceInstResService serviceInstResService;

    @Override
    public Response findCbsInfo(@Context HttpServletRequest req) {
        // 得到当前用户
        AuthUser user = RequestContextUtil.getAuthUserInfo(req);
        Criteria example = new Criteria();
        example.put("ownerId", user.getAccount());
        example.put("mgtObjSid", user.getMgtObjSid());
        example.put("serviceSid", WebConstants.ServiceSid.SERVICE_STORAGE);
        example.put("storagePurpose", WebConstants.StoragePurpose.DATA_DISK);
        List<ServiceInstance> list = this.serviceInstanceService.selectVolumeStorageInfo(example);
//		// 返回结果
        return Response.status(Response.Status.OK).entity(new RestResult(list)).build();

    }

    @Override
    public Response operation(ParamOperateObs param, @Context HttpServletRequest req) {
        ResInstResult result = new ResInstResult(Boolean.FALSE, "just initialized");
        try {
            String action = param.getActionName();
            for (ServiceInstance svc : param.getActionCbs()) {
                ResCommonInst inst = new ResCommonInst();
                // TODO: assemble inst
                // spec
                Map<String, Object> map = JsonUtil.fromJson(svc.getSpecification(), new TypeReference<Map<String, Object>>() {
                });
//                ServiceInstance vdSvc = serviceInstanceService.selectByPrimaryKey(svc.getInstanceSid());
//                if (null == vdSvc) {
//                    throw new Exception(String.format("virtual disk service instance is not existed(%s:%s)", svc.getInstanceSid(), svc.getInstanceId()));
//                }
                String resId = serviceInstResService.getResSidByInstanceSid(svc.getInstanceSid());
                if (null == resId || "".equals(resId)) {
                    throw new Exception(String.format("no resource found by service: " + svc.getInstanceSid()));
                }

                // resVmSid - vm sid
//                map.put("resVmSid", svc.getResSid());
                map.put("resVmSid", svc.getResInstanceHostSid());
                // resVdSid - vd sid
//                map.put("resVdSid", svc.getInstanceSid() + "");
                map.put("resVdSid", resId);
                inst.setResSpecParam(JsonUtil.toJson(map));
                // zone id
                inst.setZoneId(svc.getZoneSid());
                // user account
                inst.setUserAccount(svc.getOwnerId());
                // getMgtObjSid
                inst.setMgtObjSid(svc.getMgtObjSid());

                if ("attach".equalsIgnoreCase(action)) {
                    result = resVdService.attachVd(inst);
                } else if ("detach".equalsIgnoreCase(action)) {
                    result = resVdService.detachVd(inst);
                } else if ("release".equalsIgnoreCase(action)) {
                    result = resVdService.deleteVd(inst);
                } else {
                    // action type not defined
                    throw new Exception("action type is not defined: " + action);
                }
            }
        } catch (Exception e) {
            result.setStatus(false);
            result.setMessage(e.getMessage());
        }

        return Response.status(Response.Status.OK).entity(result).build();
    }

    public static class ParamOperateObs implements Serializable {
        private String actionName;
        private List<ServiceInstance> actionCbs;

        public String getActionName() {
            return actionName;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }

        public List<ServiceInstance> getActionCbs() {
            return actionCbs;
        }

        public void setActionCbs(List<ServiceInstance> actionCbs) {
            this.actionCbs = actionCbs;
        }
    }
}
