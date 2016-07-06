package com.h3c.idcloud.core.rest.product.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.common.ServiceBaseInput;
import com.h3c.idcloud.core.pojo.dto.product.InstanceArray;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceChangeLog;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceSpec;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.rest.product.CsRest;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceChangeLogService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceSpecService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.core.service.system.api.MgtObjExtService;
import com.h3c.idcloud.core.service.system.api.UserMgtObjService;
import com.h3c.idcloud.infrastructure.common.constants.RestConst;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.InterfaceResult;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.RequestContextUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;

import org.jboss.resteasy.springmvc.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tdz on 2016/3/28.
 */
@Component
public class CsRestImpl implements CsRest {

    @Reference(version = "1.0.0")
    private UserMgtObjService userMgtObjService;
    @Reference(version = "1.0.0")
    private ServiceInstanceService serviceInstanceService;
    @Reference(version = "1.0.0")
    private MgtObjExtService mgtObjExtService;
    @Reference(version = "1.0.0")
    private ServiceInstanceSpecService serviceInstanceSpecService;
    @Reference(version = "1.0.0")
    private ServiceInstanceChangeLogService instanceChangeLogService;
    @Reference(version = "1.0.0")
    private ResVmService resVmService;
    private static Logger logger = LoggerFactory.getLogger(ServiceInstanceResourceRestImpl.class);

    /**
     * 获取云主机实例集合
     *
     * @param req
     * @return
     */
    @Override
    public Response selectServiceInstanceInfo(String params,@Context HttpServletRequest req) {

        AuthUser user = RequestContextUtil.getAuthUserInfo(req);
        Map<String, Object> pmap = JsonUtil.fromJson(params, Map.class);
        Criteria example = new Criteria();
        example.put("serviceSid",WebConstants.ServiceSid.SERVICE_VM);
        example.put("ownerId",user.getAccount());
        if(pmap.get("instanceSid")!=null&&pmap.get("instanceSid")!=""){
            example.put("instanceSid",pmap.get("instanceSid"));
        }
        // 获取用户的cs服务实例
        List<ServiceInstance> instanceList = this.serviceInstanceService.selectByParams(example);
        for(int i=0;i<instanceList.size();i++){
            ServiceInstance InstanceList = instanceList.get(i);
            InstanceList.setSidAndName(InstanceList.getInstanceId()+"<br/>"+InstanceList.getInstanceName());
        }
        // 获取用户cs数据
        Map<String,String> map = new HashMap<String,String>();
        map.put("ownerId",user.getAccount());
        List<ResVm> vmList =  resVmService.selectBaseInfoByParams(map);
        // 组合数据
        for(ServiceInstance instance:instanceList){
            for(ResVm vm:vmList){
                if(instance.getResSid().equals(vm.getResVmSid())){
                    BigDecimal memorySize = new BigDecimal(vm.getMemorySize());
                    BigDecimal val = memorySize.divide(new BigDecimal(1024),2,BigDecimal.ROUND_HALF_UP);
                    instance.setSpecValue("CPU:"+vm.getCpuCores()+"、内存:"+val.doubleValue()+"GB、操作系统:"+vm.getOsName());
                    instance.setVmIp(vm.getVmIp());
                    continue;
                }
            }
        }
        return Response.status(Response.Status.OK).entity(new RestResult(instanceList)).build();
    }

    @Override
    public Response getServiceInstanceList(@Context HttpServletRequest request) {
        AuthUser user = RequestContextUtil.getAuthUserInfo(request);
        // 查询退订中、已开通
//        String statusParams = "'"
//              + WebConstants.ServiceInstanceStatus.CANCELING + "','"
//              + WebConstants.ServiceInstanceStatus.CHANGEING + "','"
//              + WebConstants.ServiceInstanceStatus.REFUSED + "','"
//              + WebConstants.ServiceInstanceStatus.OPENED
//              + "'".replace(" ", "");
        try {
            List<ServiceInstance> serviceInstanceList = new ArrayList<ServiceInstance>();
//            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            Criteria example = new Criteria();
            Criteria example2 = new Criteria();
            example.put("mgtObjSid", user.getMgtObjSid());
            example2.put("mgtObjSid", user.getMgtObjSid());
            example.put("ownerId", user.getAccount());
            example2.put("ownerId", user.getAccount());
            example.setOrderByClause("A.DREDGE_DATE desc , H.VM_NAME");
            System.out.println("Vm参数：" + JsonUtil.toJson(example));
            List<ServiceInstance>
                    instanceVmList =
                    this.serviceInstanceService.selectInstVmByParams(example);
//            if (map.get("serviceSid") == "100") {
                example2.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
//            }
//            else if (map.get("serviceSid") == "105") {
//                example2.put("serviceSid", WebConstants.ServiceSid.SERVICE_STORAGE);
//            }
            example2.setOrderByClause("A.DREDGE_DATE desc , H.HOST_NAME");
            System.out.println("Host参数：" + JsonUtil.toJson(example2));
            List<ServiceInstance>
                    instancceHostList =
                    this.serviceInstanceService.selectInstHostByParams(example2);
            serviceInstanceList.addAll(instanceVmList);
            serviceInstanceList.addAll(instancceHostList);
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            if (!CollectionUtils.isEmpty(serviceInstanceList)) {
//                for (ServiceInstance serviceInstance : serviceInstanceList) {
//                    //得到提前多久提醒
////					String presetTime = PropertiesUtil.getProperty("mgtobj.expiredate.notice");
////					int time = Integer.parseInt(presetTime)*7;
//                    Criteria condition = new Criteria();
//                    condition.put("mgtObjSid", serviceInstance.getMgtObjSid());
//                    condition.put("attrKey", "projectEndDate");
//                    List<MgtObjExt> exts = mgtObjExtService.selectByParams(condition);
//                    if (!CollectionUtils.isEmpty(exts)) {
//                        String mgtObjEndDate = exts.get(0).getAttrValue();
//                        Date endTime = df.parse(mgtObjEndDate + " 23:59:59");
//                        Date now = new Date();
//                        long buffer = endTime.getTime() - now.getTime();
//                        long days = buffer / (1000 * 60 * 60 * 24);
//                        //如果在提醒范围内
////						if(time>=days&&0<=days){
////							serviceInstance.setInNoticeTime(1);
////						}else if(days<0){
////							serviceInstance.setInNoticeTime(-1);
////						}else{
////							serviceInstance.setInNoticeTime(0);
////						}
//                    }
//                }
//            }

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
            String
                    json =
                    JsonUtil.toJson(
                            new RestResult(RestConst.HttpConst.OK, RestResult.Status.SUCCESS, null,
                                           serviceInstanceList));
            System.out.println(json);
            return Response.ok(json).build();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
                                                          null);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(resultJson)
                    .build();
        }
    }

    @Override
    public Response getServiceInstance(@PathParam("serviceInstanceSid") String
                                               serviceInstanceSid) {

        ServiceInstance serviceInstance =
                this.serviceInstanceService.selectByPrimaryKey(Long.parseLong(serviceInstanceSid));
        List<ServiceInstanceSpec> servcieInstanceSpecList =
                this.serviceInstanceSpecService.selectByInstanceSid(serviceInstance.getInstanceSid());
        serviceInstance.setServiceInstanceSpec(servcieInstanceSpecList);

        String json = JsonUtil.toJson(new RestResult(serviceInstance));
//		logger.info(json);
        return Response.status(Response.Status.OK).entity(json).build();
    }

    @Override
    public Response serviceInstanceOperation(InstanceArray[] params, HttpServletRequest request) {
        AuthUser userInfo = RequestContextUtil.getAuthUserInfo(request);
        String resultJson="";
        for (int i=0;i<params.length;i++) {
            try {
                System.out.print("++++++++++++++"+JsonUtil.toJson(params[i]));
                Map<String, Object> map =  JsonUtil.fromJson(JsonUtil.toJson(params[i]),Map.class);
                boolean result = this.serviceInstanceService.serviceInstanceOperation(map, userInfo);
                if (result) {
                    resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.SUCCESS, null);
                } else {
                    resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
                }
            } catch (Exception e) {
                 resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            }
        }
        return Response.status(Response.Status.OK).entity(resultJson).build();
    }

    @Override
    public Response releaseCsInstance(
            ServiceInstance[] params, @Context HttpServletRequest request) {
        AuthUser user = RequestContextUtil.getAuthUserInfo(request);
        boolean releaseResult=false;
        for (int i=0;i<params.length;i++) {
            Map<String, Object> map =  JsonUtil.fromJson(JsonUtil.toJson(params[i]),Map.class);
            releaseResult = this.serviceInstanceService.releaseServiceInstance(Long.parseLong(map.get("instanceSid").toString()),
                                                                       WebConstants.ResourceType.RES_VM, user);
        }
        return Response.ok(new RestResult(releaseResult)).build();
    }

    @Override
    public Response getWebConsoleUrl(String resVmSid, @Context HttpServletRequest request) {
        AuthUser user = RequestContextUtil.getAuthUserInfo(request);
        ServiceBaseInput baseInput = new ServiceBaseInput();
        baseInput.setMgtObjSid(user.getMgtObjSid());
        baseInput.setUserAccount(user.getAccount());
        baseInput.setUserPass(user.getPassword());
        String webConsole = this.resVmService.getWebConsole(resVmSid, baseInput);

        RestResult restResult = new RestResult(webConsole);
        return Response.ok(restResult).build();

    }

    @Override
    public Response getEipForHost(String params, @Context HttpServletRequest request) {
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
            example.put("resVmInsName","02");
//            example.put("statusParams", statusParams);
            example.setOrderByClause("A.DREDGE_DATE desc");
            List<ServiceInstance> serviceInstanceList = this.serviceInstanceService.selectFloatingIpInfo(example);
            return Response.status(Response.Status.OK).entity(new RestResult(serviceInstanceList)).build();
        } catch (Exception e) {
            e.printStackTrace();
            String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
        }
    }

}
