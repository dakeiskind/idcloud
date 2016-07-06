/**
 *
 */
package com.h3c.idcloud.core.rest.system.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idclod.core.service.activiti.api.ActivitiService;
import com.h3c.idcloud.core.pojo.common.ResDataUtils;
import com.h3c.idcloud.core.pojo.dto.product.*;
import com.h3c.idcloud.core.pojo.dto.res.*;
import com.h3c.idcloud.core.pojo.dto.system.ApproveRecord;
import com.h3c.idcloud.core.pojo.dto.system.Biz;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.pojo.instance.ResNetworkInst;
import com.h3c.idcloud.core.pojo.instance.ResVdInst;
import com.h3c.idcloud.core.pojo.instance.ResVmInst;
import com.h3c.idcloud.core.rest.system.ApproveRecordRest;
import com.h3c.idcloud.core.service.order.api.OrderDetailService;
import com.h3c.idcloud.core.service.product.api.BusinesstypeService;
import com.h3c.idcloud.core.service.product.api.FreeResCheckLogService;
import com.h3c.idcloud.core.service.product.api.FreeResService;
import com.h3c.idcloud.core.service.product.api.ResourceRequestHanlder;
import com.h3c.idcloud.core.service.product.api.ServiceChangeLogService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceChangeLogService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceSpecService;
import com.h3c.idcloud.core.service.res.api.ResNetworkService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.core.service.res.api.ResVsService;
import com.h3c.idcloud.core.service.system.api.ApproveRecordService;
import com.h3c.idcloud.core.service.system.api.MgtObjService;
import com.h3c.idcloud.core.service.system.api.SidService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.InterfaceResult;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.FileUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author 徐印
 */
@Component
public class ApproveRecordRestImpl implements ApproveRecordRest {

    private static final Logger logger = LoggerFactory.getLogger(ApproveRecordRestImpl.class);

    /**
     * 审核记录Service
     */
    @Reference(version = "1.0.0")
    private ApproveRecordService approveRecordService;

    /**
     * 订单详细Service
     */
    @Reference(version = "1.0.0")
    private OrderDetailService orderDetailService;

    /**
     * 闲置资源管理Service
     */
    @Reference(version = "1.0.0")
    private FreeResService freeresService;

    @Reference(version = "1.0.0")
    private FreeResCheckLogService checkLogService;

    @Reference(version = "1.0.0")
    private ServiceInstanceService serviceInstanceService;

    @Reference(version = "1.0.0")
    private ServiceInstanceSpecService serviceInstanceSpecService;

    @Reference(version = "1.0.0")
    private ServiceChangeLogService changeLogService;

    @Reference(version = "1.0.0")
    private BusinesstypeService bizService;

    @Reference(version = "1.0.0")
    private ActivitiService activitiService;

    @Reference(version = "1.0.0", group = "vmChangeHandlerImpl")
    private ResourceRequestHanlder vmChangeHandlerImpl;

    @Reference(version = "1.0.0")
    private SidService sidService;

//	@Autowired
//	private TicketMapper ticketMapper;

    @Reference(version = "1.0.0")
    private ResNetworkService networkService;

    @Reference(version = "1.0.0")
    private ResVmService resVmService;

    @Reference(version = "1.0.0")
    private ServiceChangeLogService serviceChangeLogService;

//	@Reference(version = "1.0.0")
//	private TicketRecordService recordService;

//	@Reference(version = "1.0.0")
//	private ResHostItemService resHostItemService;

    @Reference(version = "1.0.0")
    private ResVsService resVsService;

    @Reference(version = "1.0.0")
    private ServiceInstanceChangeLogService instanceChangeLogService;

    @Reference(version = "1.0.0")
    private MgtObjService mgtObjService;

//	@Reference(version = "1.0.0")
//	private ResCpuPoolService cpuPoolService;

    @Reference(version = "1.0.0")
    private ResNetworkService resNetworkService;

    @Reference(version = "1.0.0")
    private UserService userService;

    /**
     * 获取不同类型订单
     */
    @Override
    @WebMethod
    @Produces({MediaType.APPLICATION_FORM_URLENCODED})
    @GET
    @Path("/getApproveByType")
    public Response selectApproveRecord(@Context HttpServletRequest request) {
        String json = "";
        try {
            json = this.approveRecordService.selectApproveRecord(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 获取不同类型服务实例
     */
    @Override
    @WebMethod
    @POST
    @Path("/getApproveByTypeInst")
    public Response selectApproveRecordInst(String params) {
        Map<String, Object> map;
        String json = "";
        map = JsonUtil.fromJson(params, Map.class);
        String aproveType = String.valueOf(map.get("approveType"));
        map.remove("approveType");
        if (StringUtil.isNullOrEmpty(String.valueOf(map.get("processInstanceId")))) {
            map.remove("processInstanceId");
        }
        if (StringUtil.isNullOrEmpty(String.valueOf(map.get("approvorIdLike")))) {
            map.remove("approvorIdLike");
        }
        if (StringUtil.isNullOrEmpty(String.valueOf(map.get("processType")))) {
            map.remove("processType");
        }
        if (StringUtil.isNullOrEmpty(String.valueOf(map.get("approveFromTime")))) {
            map.remove("approveFromTime");
        }
        if (StringUtil.isNullOrEmpty(String.valueOf(map.get("approveToTime")))) {
            map.remove("approveToTime");
        }
        Criteria criteria = new Criteria();
        //criteria.setCondition(map);
        List<ApproveRecord>
                list =
                this.approveRecordService.selectApproveRecordInst(criteria, aproveType);
        json = JsonUtil.toJson(list);
        //json = "[{\"processInstanceId\": \"1111\"}, {\"processInstanceId\": \"2222\"}]";
        // 返回前台
        return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 获取审核流程
     */
    @Override
    @WebMethod
    @GET
    @Path("/platform/getApproveFlow/{processInstanceId}")
    public void getApproveFlow(@Context HttpServletRequest request,
                               @Context HttpServletResponse response,
                               @PathParam("processInstanceId") String processInstanceId) {

        InputStream resourceAsStream = this.activitiService.loadResource(processInstanceId);

        FileUtil.ImageOutput(response, resourceAsStream);

//		OutputStream op;
//		try {
//			op = response.getOutputStream();
//			byte[] buf=new byte[1024];
//			int len= is.read(buf,0,1024);
//			while(len!=-1){
//			op.write(buf, 0, len);
//			len=is.read(buf,0,1024);
//			}
//			op.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
    }


    /**
     * 获取审核记录
     */
    @Override
    @WebMethod
    @GET
    @Path("/platform/getApproveHistoryRecords/{processInstanceId}")
    public Response getApproveHistoryRecords(
            @PathParam("processInstanceId") String processInstanceId) {
        String json = "";
        try {
            List<ApproveRecord>
                    approveRecordList =
                    this.approveRecordService.findApproveHistoryRecords(processInstanceId);
//			Map<String, Object> activityImageInfo = this.activitiService.traceProcess(processInstanceId);
            Map<String, Object>
                    activityImageInfo =
                    this.activitiService.getActivityImageInfo(processInstanceId);
            Map<String, Object> resultMap = new HashMap<String, Object>();
//			InputStream resourceAsStream = this.activitiService.loadResource(processInstanceId);
//			if(activityImageInfo != null) {
//				activityImageInfo.putAll(FileUtil.getImageSizeByBufferedImage(resourceAsStream));
//			}
            resultMap.put("approveRecord", approveRecordList);
            resultMap.put("activityImageInfo", activityImageInfo);
            json = JsonUtil.toJson(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回前台
        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    @WebMethod
    @GET
    @Path("/platform/getApproveDetailsRecords")
    public Response findApproveDetailsRecords(@QueryParam("orderId") String orderId) {

        return null;
    }

    /**
     * 管理员审核
     */
    @Override
    @WebMethod
    @POST
    @Path("/platform/createAdminApprove")
    public Response createAdminApprove(String params) {

        String returnJson;
        boolean result = false;

        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);

            String checkStatus = StringUtil.nullToEmpty(map.get("approveStatus"));
            String checkcomments = StringUtil.nullToEmpty(map.get("approveNote"));
            String processInstanceId = StringUtil.nullToEmpty(map.get("processInstanceId"));
            String approvorAction = StringUtil.nullToEmpty(map.get("approvorAction"));
            String processType = StringUtil.nullToEmpty(map.get("processType"));
            boolean isFinal = this.activitiService.isFinal(processInstanceId);
            ResInstResult resInstResult = null;

            if (isFinal && WebConstants.ApproveStatus.AGREE.equals(checkStatus)) {
//				resInstResult = new ResInstResult();
//				resInstResult.setStatus(ResInstResult.SUCCESS);
                if (WebConstants.ProcessType.VM_OPEN.equals(processType)
                    || WebConstants.ProcessType.INSTANCE_ADJUST.equals(processType)) {
                    resInstResult = approveRecordService.vmResCheck(map);
                    if (ResInstResult.FAILURE == resInstResult.getStatus()) {
                        String message = (String) resInstResult.getMessage();
                        if (StringUtil.isNullOrEmpty(message)) {
                            message = WebUtil.getMessage(WebConstants.MsgCd.ERROR_VM_RES_CHECK);
                        }
                        returnJson =
                                JsonUtil.toJson(
                                        new RestResult(RestResult.Status.FAILURE, message, null));
                        return Response.status(Status.OK).entity(returnJson).build();
                    }
                }
            }

            result =
                    approveRecordService
                            .createAdminApprove(map, checkStatus, checkcomments, processInstanceId,
                                                approvorAction, processType);

            //调用虚拟机开通资源层接口
//			if(checkStatus.equals(WebConstants.ApproveStatus.AGREE) && result && isFinal) {
//				approveRecordService.executeRequestResource(map, processType);
//			}
            //ChengQi end

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
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            //资源变更检查失败
            String resultJson = "";
            if (e.getMessage().startsWith("reconfigVmCheck")) {
                returnJson =
                        JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,
                                                       WebUtil.getMessage(
                                                               WebConstants.MsgCd.ERROR_VM_RES_CHECK),
                                                       null));
            } else {
                resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            }
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
        }
    }


    /**
     * 变更终审前的资源检查
     */
    @Override
    @WebMethod
    @POST
    @Path("/platform/vmResCheck")
    public Response vmResCheck(String params) {
        String returnJson = "";
        ResInstResult resInstResult = null;
        boolean isFinal = false;

        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            String processInstanceId = StringUtil.nullToEmpty(map.get("processInstanceId"));
            //工单重新执行
            if (StringUtil.isNullOrEmpty(processInstanceId) || "0".equals(processInstanceId)) {
                String ticketType = (String) map.get("ticketType");
                if (WebConstants.ticketType.VM_AUTO_OPEN_FAILURE_TICKET.equals(ticketType)) {
                    map.put("processType", WebConstants.ProcessType.VM_OPEN);
                } else if (WebConstants.ticketType.VM_AUTO_CHANGE_FAILURE_TICKET
                        .equals(ticketType)) {
                    map.put("processType", WebConstants.ProcessType.INSTANCE_ADJUST);
                }
                isFinal = true;
            } else {
                //将原来区分租户管理员审批和运营管理员审批的方式改为统一的审批方式
                isFinal = this.activitiService.isFinal(processInstanceId);
            }

            //调用虚拟机变更检查资源层接口
            if (isFinal) {
                resInstResult = approveRecordService.vmResCheck(map);
                if (ResInstResult.SUCCESS == resInstResult.getStatus()) {
                    returnJson =
                            JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS,
                                                           WebUtil.getMessage(
                                                                   WebConstants.MsgCd.INFO_VM_RES_CHECK),
                                                           null));
                } else {
                    String message = (String) resInstResult.getMessage();
                    if (StringUtil.isNullOrEmpty(message)) {
                        message = "资源检查失败";
                    }
                    returnJson =
                            JsonUtil.toJson(
                                    new RestResult(RestResult.Status.FAILURE, message, null));
                }
            }
            return Response.status(Status.OK).entity(returnJson).build();
        } catch (Exception e) {
            e.printStackTrace();
            String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
        }
    }

    /**
     * 审核
     */
    @Override
    @WebMethod
    @POST
    @Path("/platform/createApprove")
    public Response createApprove(String params) {
        String returnJson;
        boolean result = false;
        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            String checkStatus = StringUtil.nullToEmpty(map.get("approveStatus"));
            String checkcomments = StringUtil.nullToEmpty(map.get("approveNote"));
            String processInstanceId = StringUtil.nullToEmpty(map.get("processInstanceId"));
            String approvorAction = StringUtil.nullToEmpty(map.get("approvorAction"));
            String processType = StringUtil.nullToEmpty(map.get("processType"));

            //将原来区分租户管理员审批和运营管理员审批的方式改为统一的审批方式
            result =
                    approveRecordService
                            .createApprove(checkStatus, checkcomments, processInstanceId,
                                           approvorAction, processType);
            if (result == true) {
                returnJson =
                        JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS,
                                                       WebUtil.getMessage(
                                                               WebConstants.MsgCd.INFO_APPROVE_SUCCESS),
                                                       null));
            } else {
                returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,
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
    }

    /**
     * 初始化审核
     */
    @Override
    @WebMethod
    @POST
    @Path("/platform/initApprove")
    public Response initApprove(String params) {
        String returnJson;
        boolean result = false;
        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            String instanceSid = StringUtil.nullToEmpty(map.get("instanceSid"));

//			result = approveRecordService.initApprove(instanceSid);
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
    }

    @Override
    public Response getApproveFreeInstance(String instanceId) {
        String resultJson = "";
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Criteria criteria = new Criteria();
        criteria.put("instanceSid", instanceId);

        List<FreeRes> freeReses = this.freeresService.selectByFreeInstance(criteria);
        if (!freeReses.isEmpty()) {
            //设置闲置资源信息
            FreeRes freeres = freeReses.get(0);
            String resFreeType = freeres.getResFreeType();
            String netFreeType = freeres.getNetFreeType();
            resultMap.put("freeResData", freeres);
            //设置闲置资源配额信息freeres.getCpuFerquency()
            List<Map<String, Object>> specList = new ArrayList<Map<String, Object>>();
            //设置CPU行信息
            Map<String, Object> cpuRow = new HashMap<String, Object>();
            cpuRow.put("specKey", "cpu");
            cpuRow.put("specName", "CPU");
            cpuRow.put("specText", StringUtil.nullToEmpty(freeres.getCpuCores()) + "核/" + StringUtil
                    .nullToEmpty(freeres.getCpuFerquency()) + "GHZ");
            cpuRow.put("specValue", StringUtil.nullToEmpty(freeres.getCpuCores()));
//			cpuRow.put("specRate", NumberFormatUtils.formatToPercent(freeres.getCpuUsage()));
            cpuRow.put("specChangeValue", null);
            cpuRow.put("isFree", resFreeType.substring(0, 1).equals("1"));
            specList.add(cpuRow);
            //设置内存行信息
            Map<String, Object> memoryRow = new HashMap<String, Object>();
            memoryRow.put("specKey", "memory");
            memoryRow.put("specName", "内存");
            memoryRow.put("specValue", StringUtil.nullToEmpty(freeres.getMemory()));
            memoryRow.put("specText", StringUtil.nullToEmpty(freeres.getMemory()) + "GB");
//			memoryRow.put("specRate", NumberFormatUtils.formatToPercent(freeres.getMemUsage()));
            memoryRow.put("specChangeValue", null);
            memoryRow.put("isFree", resFreeType.substring(1, 2).equals("1"));
            specList.add(memoryRow);
            //设置存储行信息
            Map<String, Object> storageRow = new HashMap<String, Object>();
            storageRow.put("specKey", "storage");
            storageRow.put("specName", "存储");
            storageRow.put("specValue", StringUtil.nullToEmpty(freeres.getStorage()));
            storageRow.put("specText", StringUtil.nullToEmpty(freeres.getStorage()) + "GB");
//			storageRow.put("specRate", NumberFormatUtils.formatToPercent(freeres.getStUsage()));
            storageRow.put("specChangeValue", null);
            storageRow.put("isFree", resFreeType.substring(2, 3).equals("1"));
            specList.add(storageRow);
            //设置内网IP信息
            Map<String, Object> diRow = new HashMap<String, Object>();
            diRow.put("specKey", "privateIp");
            diRow.put("specName", "内网IP");
            diRow.put("specValue", freeres.getPrivageIp());
            diRow.put("specText", freeres.getPrivageIp());
            diRow.put("specRate", "");
            diRow.put("specChangeValue", null);
            diRow.put("isFree", netFreeType.substring(0, 1).equals("1"));
            specList.add(diRow);
            //设置外网IP信息
            Map<String, Object> doRow = new HashMap<String, Object>();
            doRow.put("specKey", "pubIp");
            doRow.put("specName", "外网IP");
            doRow.put("specValue", freeres.getPubIp());
            doRow.put("specText", freeres.getPubIp());
            doRow.put("specRate", "");
            doRow.put("specChangeValue", null);
            doRow.put("isFree", netFreeType.substring(1, 2).equals("1"));
            specList.add(doRow);
            resultMap.put("freeResSpec", specList);
        }

        resultJson = JsonUtil.toJson(resultMap);
        return Response.status(Status.OK).entity(resultJson).build();
    }

    @Override
    public Response getApproveAdjustInstance(String approveObjectId, String processInstanceId) {
        String resultJson = "";
        Long instanceSid = Long.parseLong(approveObjectId);
        Map<String, Object> result = new HashMap<String, Object>();
        List<ServiceInstanceSpec> vmChangeSpecs = new ArrayList<ServiceInstanceSpec>();
        List<ServiceInstanceSpec> vmDiskChangeSpecs = new ArrayList<ServiceInstanceSpec>();
        //获取实例规格的最大版本号
        Long maxVersion = serviceInstanceSpecService.selectByInstanceSpecMaxVersion(instanceSid);
        Long
                validInstaceSpec =
                serviceInstanceSpecService.selectValidInstanceSpecByVersion(instanceSid);
        //获取上一个版本有效的Vm规格
        List<ServiceInstanceSpec>
                oldInstaceSpec =
                serviceInstanceSpecService
                        .selectByInstanceSpecByVersion(instanceSid, WebConstants.SpecStatus.valid,
                                                       validInstaceSpec);
        //获取当前版本中未生效Vm规格
        List<ServiceInstanceSpec>
                newInstaceSpec =
                serviceInstanceSpecService
                        .selectByInstanceSpecByVersion(instanceSid, WebConstants.SpecStatus.invalid,
                                                       maxVersion);

        //设置CPU变更的规格值
        setChangeSpecList("CPU", "CPU", oldInstaceSpec, newInstaceSpec, vmChangeSpecs);
        //设置MEMORY变更的规格值
        setChangeSpecList("MEMORY", "内存", oldInstaceSpec, newInstaceSpec, vmChangeSpecs);

        //查询虚拟机实例的磁盘实例
        Criteria criteria = new Criteria();
        criteria.put("parentInstanceSid", instanceSid);
        criteria.setOrderByClause("A.INSTANCE_NAME");
        List<ServiceInstance>
                diskInstances =
                serviceInstanceService.selectByParamsOrderByPurpose(criteria);
        for (ServiceInstance diskInstance : diskInstances) {
            //获取实例规格的最大版本号
//			Long maxDiskVersion = serviceInstanceSpecService.selectByInstanceSpecMaxVersion(diskInstance.getInstanceSid());
//			Long validDiskInstaceSpec = serviceInstanceSpecService.selectValidInstanceSpecByVersion(diskInstance.getInstanceSid());
            //获取上一个版本有效的Disk规格
            List<ServiceInstanceSpec>
                    oldDiskInstaceSpec =
                    serviceInstanceSpecService
                            .selectByInstanceSpecByVersion(diskInstance.getInstanceSid(),
                                                           WebConstants.SpecStatus.valid,
                                                           validInstaceSpec);
            //获取当前版本中未生效Disk规格
            List<ServiceInstanceSpec> newDiskInstaceSpec = new ArrayList<ServiceInstanceSpec>();
            if (oldDiskInstaceSpec == null || oldDiskInstaceSpec.size() == 0) {
                newDiskInstaceSpec =
                        serviceInstanceSpecService
                                .selectByInstanceSpecByVersion(diskInstance.getInstanceSid(), null,
                                                               maxVersion);
                if (newDiskInstaceSpec.size() != 0) {
                    ServiceInstanceSpec spec = new ServiceInstanceSpec();
                    for (ServiceInstanceSpec instanceSpec : newDiskInstaceSpec) {
                        if ("DISK_SIZE".equals(instanceSpec.getName())) {
                            spec.setName(diskInstance.getInstanceName());
                            spec.setDescription(diskInstance.getInstanceName());
                            String oldValue = "";
                            String newValue = instanceSpec.getValue();
                            spec.setNewValue(newValue);
                            spec.setValue(oldValue);
                            spec.setOperate("创建");
                        }
                        if ("STORAGE_PURPOSE".equals(instanceSpec.getName())) {
                            if (instanceSpec.getValue()
                                    .equals(WebConstants.StoragePurpose.SYSTEM_DISK)) {
                                spec.setValueText("系统盘");
                            } else if (instanceSpec.getValue()
                                    .equals(WebConstants.StoragePurpose.DATA_DISK)) {
                                spec.setValueText("外置存储盘");
                            } else if (instanceSpec.getValue()
                                    .equals(WebConstants.StoragePurpose.SYSTEM_DATA_DISK)) {
                                spec.setValueText("系统外置存储盘");
                            }
                        }
                    }
                    vmDiskChangeSpecs.add(spec);
                }
            } else {
                newDiskInstaceSpec =
                        serviceInstanceSpecService
                                .selectByInstanceSpecByVersion(diskInstance.getInstanceSid(), null,
                                                               maxVersion);
                if (newDiskInstaceSpec.size() != 0) {
                    ServiceInstanceSpec spec = new ServiceInstanceSpec();
                    for (ServiceInstanceSpec instanceSpec : oldDiskInstaceSpec) {
                        if ("DISK_SIZE".equals(instanceSpec.getName())) {
                            String oldValue = instanceSpec.getValue();
                            //判断这个规格对应的instance是不是已删除的
                            if (!StringUtil.isNullOrEmpty(oldValue) && !oldValue.equals("0")) {
                                spec.setName(diskInstance.getInstanceName());
                                spec.setDescription(diskInstance.getInstanceName());
                                String
                                        newValue =
                                        ResDataUtils.getSpecValueFromSpecs(instanceSpec.getName(),
                                                                           newDiskInstaceSpec);
                                spec.setValue(oldValue);
                                if (newValue.equals("0")) {
                                    spec.setNewValue("");
                                    spec.setOperate("删除");
                                } else if (newValue.equals(oldValue)) {
                                    spec.setNewValue("");
                                    spec.setOperate("");
                                } else {
                                    spec.setNewValue(newValue);
                                    spec.setOperate("变更");
                                }
                                spec.setValue(oldValue);
                            }
                        }
                        if ("STORAGE_PURPOSE".equals(instanceSpec.getName())) {
                            if (instanceSpec.getValue()
                                    .equals(WebConstants.StoragePurpose.SYSTEM_DISK)) {
                                spec.setValueText("系统盘");
                            } else if (instanceSpec.getValue()
                                    .equals(WebConstants.StoragePurpose.DATA_DISK)) {
                                spec.setValueText("外置存储盘");
                            } else if (instanceSpec.getValue()
                                    .equals(WebConstants.StoragePurpose.SYSTEM_DATA_DISK)) {
                                spec.setValueText("系统外置存储盘");
                            }
                        }
                    }
                    if (!StringUtil.isNullOrEmpty(spec.getValue())) {
                        vmDiskChangeSpecs.add(spec);
                    }
                }
            }
        }
//		//设置LAN变更的规格值
//		setChangeSpecList("NEED_LAN", "是否需要内网", oldInstaceSpec, newInstaceSpec, vmChangeSpecs);
//		//设置WAN变更的规格值
//		setChangeSpecList("NEED_WAN", "是否需要外网", oldInstaceSpec, newInstaceSpec, vmChangeSpecs);

        //新的网络变更，读取的是service_change_log
        List<Map> nets = new ArrayList<Map>();
//		List<ResNetworkInst> nets = new ArrayList<ResNetworkInst>();
        criteria = new Criteria();
        criteria.put("instanceSid", instanceSid);
        criteria.put("stauts", WebConstants.ServiceChangeStatus.NOT_CHANGE);
        List<ServiceChangeLog> serviceChangeLogs = changeLogService.selectByParams2(criteria);
        if (!CollectionUtils.isEmpty(serviceChangeLogs) && serviceChangeLogs.get(0) != null) {
            String content = serviceChangeLogs.get(0).getChangeContent();

            ResVmInst resVmInst = JsonUtil.fromJson(content, ResVmInst.class);
            String vmSId = resVmInst.getResVmInstId();
            ResVm vmInfo = resVmService.getVmInfo(vmSId);
            List<ResIp> resIpList = vmInfo.getResIpList();
            List<ResNetworkInst> vmNets = resVmInst.getNets();
            if (!CollectionUtils.isEmpty(vmNets)) {
                for (ResNetworkInst net : vmNets) {
                    if (net != null) {
                        Map<String, String> netsMap = new HashMap<String, String>();
                        netsMap.put("resNetworkType", net.getResNetworkType());
                        if (WebConstants.ResNetworkType.PRIVATE.equals(net.getResNetworkType())) {
                            netsMap.put("resNetworkTypeName", "内网");
                        } else if (WebConstants.ResNetworkType.PUBLIC
                                .equals(net.getResNetworkType())) {
                            netsMap.put("resNetworkTypeName", "外网");
                        }
                        netsMap.put("resNetworkId", net.getResNetworkId());
                        ResNetwork
                                netWork =
                                networkService.selectByPrimaryKey(net.getResNetworkId());
                        netsMap.put("resNetworkName",
                                    (netWork == null ? "" : netWork.getNetworkName()));
                        netsMap.put("ipAddress", net.getIpAddress());
                        netsMap.put("ipAddressWithStatus", net.getIpAddress() + "(已使用)");
                        //判断原有的ip是否有操作
                        if (!CollectionUtils.isEmpty(resIpList)) {
                            for (ResIp ip : resIpList) {
                                if (ip != null && ip.getIpAddress().equals(net.getIpAddress())) {
                                    resIpList.remove(ip);
                                    break;
                                }
                            }
                        }

                        netsMap.put("operate", net.getOperate());
                        if (WebConstants.NetOperate.ADD.equals(net.getOperate())) {
                            netsMap.put("operateText", "添加");
                        } else if (WebConstants.NetOperate.DELLETE.equals(net.getOperate())) {
                            netsMap.put("operateText", "删除");
                        } else if (WebConstants.NetOperate.UNCHANGE.equals(net.getOperate())) {
                            netsMap.put("operateText", "不变");
                        }
                        nets.add(netsMap);
                    }
                }
            }
            //判断原有的ip是否为空，如果不为空，则里面的ip都是没操作的
            if (!CollectionUtils.isEmpty(resIpList)) {
                for (ResIp ip : resIpList) {
                    if (ip != null) {
                        Map<String, String> netsMap = new HashMap<String, String>();
                        netsMap.put("resNetworkType", ip.getNetworkType());
                        if (WebConstants.ResNetworkType.PRIVATE.equals(ip.getNetworkType())) {
                            netsMap.put("resNetworkTypeName", "内网");
                        } else if (WebConstants.ResNetworkType.PUBLIC.equals(ip.getNetworkType())) {
                            netsMap.put("resNetworkTypeName", "外网");
                        }
                        netsMap.put("resNetworkId", ip.getResNetworkSid());
                        ResNetwork
                                netWork =
                                networkService.selectByPrimaryKey(ip.getResNetworkSid());
                        netsMap.put("resNetworkName",
                                    (netWork == null ? "" : netWork.getNetworkName()));
                        netsMap.put("ipAddress", ip.getIpAddress());
                        netsMap.put("ipAddressWithStatus", ip.getIpAddress() + "(已使用)");

                        netsMap.put("operate", "");
                        netsMap.put("operateText", "不变");
                        nets.add(netsMap);
                    }
                }
            }
        }

        ServiceInstance instance = this.serviceInstanceService.selectByPrimaryKey(instanceSid);
        //获取所属二级业务
        Biz childbiz = bizService.selectByPrimaryKey(instance.getMgtObjSid());
        if (childbiz == null) {
            throw new RuntimeException(
                    "child biz is can't find. bizSid=" + instance.getMgtObjSid());
        }
        //获取所属一级业务
        Biz parentBiz = bizService.selectByPrimaryKey(childbiz.getParentBizSid());
        if (parentBiz == null) {
            throw new RuntimeException(
                    "parent biz is can't find. bizSid=" + childbiz.getParentBizSid());
        }
        result.put("fbizText", parentBiz.getName());
        result.put("sbizText", childbiz.getName());
        result.put("instanceName", instance.getInstanceName());
        result.put("ownerId", instance.getOwnerId());
        result.put("specs", vmChangeSpecs);
        result.put("diskSpecs", vmDiskChangeSpecs);
        result.put("netSpecs", nets);
        //加入是否为到达最终审判的判断
        if (StringUtil.isNullOrEmpty(processInstanceId) || "0".equals(processInstanceId)) {
            result.put("isFinal", true);
        } else {
            Boolean isFinal = this.activitiService.isFinal(processInstanceId);
            if (isFinal != null) {
                result.put("isFinal", isFinal);
            }
        }

        resultJson = JsonUtil.toJson(result);
        return Response.status(Status.OK).entity(resultJson).build();
    }

    @Override
    public Response getApproveAdjustInstanceDetail(String approveObjectId,
                                                   String processInstanceId) {
        String resultJson = "";
        try {
            Long instanceSid = Long.parseLong(approveObjectId);
            Map<String, Object> result = new HashMap<String, Object>();
            List<ServiceInstanceSpec> vmChangeSpecs = new ArrayList<ServiceInstanceSpec>();
            List<ServiceInstanceSpec> vmDiskChangeSpecs = new ArrayList<ServiceInstanceSpec>();

            //得到change_log表中的数据
            List<ServiceInstanceChangeLog>
                    changeLogs =
                    this.instanceChangeLogService.selectLastChangeLog(instanceSid);
            if (CollectionUtils.isEmpty(changeLogs) || changeLogs.size() > 1) {
                //			resultJson = JsonUtil.toJson("获取变更信息失败！");
                throw new RuntimeException(
                        "The instance change log number is incorrect. instanceSid=" + instanceSid
                        + " changeLog.size=" + changeLogs.size());
            } else {
                ServiceInstance
                        instance =
                        this.serviceInstanceService.selectByPrimaryKey(instanceSid);
                MgtObj mgtObj = mgtObjService.selectByPrimaryKey(instance.getMgtObjSid());
                result.put("mgtObjSid", mgtObj.getMgtObjSid());
                result.put("mgtObjName", mgtObj.getName());
                result.put("instanceName", instance.getInstanceName());
                result.put("ownerId", instance.getOwnerId());
                ServiceInstanceChangeLog changeLog = changeLogs.get(0);
                if (WebConstants.instanceChangeType.CHANGE.equals(changeLog.getChangeType())) {
                    //虚机原配置
                    ResVm preSpec = JsonUtil.fromJson(changeLog.getChangePreSpec(), ResVm.class);
                    //虚机变更信息
                    ServiceInstanceChangeLogSpec<ResVmInst>
                            changeSpec =
                            JsonUtil.fromJson(changeLog.getChangeSpec(),
                                              new TypeReference<ServiceInstanceChangeLogSpec<ResVmInst>>() {
                                              });
                    ResVmInst vmSpec = changeSpec.getParams();

                    ServiceInstanceSpec cpuSpec = new ServiceInstanceSpec();
                    cpuSpec.setName("CPU");
                    if (preSpec.getParType() == null) {
                        cpuSpec.setDescription("CPU");
                    } else if (Integer.parseInt(WebConstants.PowerPartitionType.MPAR) == preSpec
                            .getParType()) {
                        //micro Part
                        cpuSpec.setDescription("虚拟CPU");
                    } else if (Integer.parseInt(WebConstants.PowerPartitionType.LPAR) == preSpec
                            .getParType()) {
                        //Logic Part
                        cpuSpec.setDescription("物理CPU");
                    }
                    cpuSpec.setValue(preSpec.getCpuCores() + "");
                    cpuSpec.setNewValue(vmSpec.getCpu() + "");
                    vmChangeSpecs.add(cpuSpec);

                    if (preSpec.getParType() != null) {
                        ServiceInstanceSpec cpuMinSpec = new ServiceInstanceSpec();
                        cpuMinSpec.setName("CPU_MIN");
                        if (Integer.parseInt(WebConstants.PowerPartitionType.MPAR) == preSpec
                                .getParType()) {
                            //micro Part
                            cpuMinSpec.setDescription("虚拟最小CPU");
                        } else if (Integer.parseInt(WebConstants.PowerPartitionType.LPAR) == preSpec
                                .getParType()) {
                            //Logic Part
                            cpuMinSpec.setDescription("物理最小CPU");
                        }
                        cpuMinSpec.setValue(preSpec.getCpuCoresMin() + "");
                        cpuMinSpec.setNewValue(vmSpec.getCpuMin() + "");
                        vmChangeSpecs.add(cpuMinSpec);

                        ServiceInstanceSpec cpuMaxSpec = new ServiceInstanceSpec();
                        cpuMaxSpec.setName("CPU_MAX");
                        if (Integer.parseInt(WebConstants.PowerPartitionType.MPAR) == preSpec
                                .getParType()) {
                            //micro Part
                            cpuMaxSpec.setDescription("虚拟最大CPU");
                        } else if (Integer.parseInt(WebConstants.PowerPartitionType.LPAR) == preSpec
                                .getParType()) {
                            //Logic Part
                            cpuMaxSpec.setDescription("物理最大CPU");
                        }
                        cpuMaxSpec.setValue(preSpec.getCpuCoresMax() + "");
                        cpuMaxSpec.setNewValue(vmSpec.getCpuMax() + "");
                        vmChangeSpecs.add(cpuMaxSpec);
                    }

                    if (preSpec.getParType() != null
                        && Integer.parseInt(WebConstants.PowerPartitionType.MPAR) == preSpec
                            .getParType()) {
                        ServiceInstanceSpec powerCpuUsedUnitSpec = new ServiceInstanceSpec();
                        powerCpuUsedUnitSpec.setName("POWER_CPU_USED_UNIT");
                        powerCpuUsedUnitSpec.setDescription("物理CPU");
                        powerCpuUsedUnitSpec.setValue(preSpec.getPowerCpuUsedUnits() + "");
                        powerCpuUsedUnitSpec.setNewValue(vmSpec.getPowerCpuUsedUnits() + "");
                        vmChangeSpecs.add(powerCpuUsedUnitSpec);

                        ServiceInstanceSpec powerCpuMinUnitSpec = new ServiceInstanceSpec();
                        powerCpuMinUnitSpec.setName("POWER_CPU_MIN_UNIT");
                        powerCpuMinUnitSpec.setDescription("物理最小CPU");
                        powerCpuMinUnitSpec.setValue(preSpec.getPowerCpuMinUnits() + "");
                        powerCpuMinUnitSpec.setNewValue(vmSpec.getPowerCpuMinUnits() + "");
                        vmChangeSpecs.add(powerCpuMinUnitSpec);

                        ServiceInstanceSpec powerCpuMaxUnitSpec = new ServiceInstanceSpec();
                        powerCpuMaxUnitSpec.setName("POWER_CPU_MAX_UNIT");
                        powerCpuMaxUnitSpec.setDescription("物理最大CPU");
                        powerCpuMaxUnitSpec.setValue(preSpec.getPowerCpuMaxUnits() + "");
                        powerCpuMaxUnitSpec.setNewValue(vmSpec.getPowerCpuMaxUnits() + "");
                        vmChangeSpecs.add(powerCpuMaxUnitSpec);
                    }

                    ServiceInstanceSpec memSpec = new ServiceInstanceSpec();
                    memSpec.setName("MEMORY");
                    if (preSpec.getParType() == null) {
                        memSpec.setDescription("内存");
                    } else if (Integer.parseInt(WebConstants.PowerPartitionType.MPAR) == preSpec
                            .getParType()) {
                        //micro Part
                        memSpec.setDescription("虚拟内存");
                    } else if (Integer.parseInt(WebConstants.PowerPartitionType.LPAR) == preSpec
                            .getParType()) {
                        //Logic Part
                        memSpec.setDescription("物理内存");
                    }
                    memSpec.setValue(BigDecimal.valueOf(preSpec.getMemorySize())
                                             .divide(BigDecimal.valueOf(1024), 0,
                                                     BigDecimal.ROUND_HALF_UP) + "");
                    memSpec.setNewValue(BigDecimal.valueOf(vmSpec.getMemory())
                                                .divide(BigDecimal.valueOf(1024), 0,
                                                        BigDecimal.ROUND_HALF_UP) + "");
                    vmChangeSpecs.add(memSpec);

                    if (preSpec.getParType() != null) {
                        ServiceInstanceSpec memMinSpec = new ServiceInstanceSpec();
                        memMinSpec.setName("MEMORY_MIN");
                        if (Integer.parseInt(WebConstants.PowerPartitionType.MPAR) == preSpec
                                .getParType()) {
                            //micro Part
                            memMinSpec.setDescription("虚拟最小内存");
                        } else if (Integer.parseInt(WebConstants.PowerPartitionType.LPAR) == preSpec
                                .getParType()) {
                            //Logic Part
                            memMinSpec.setDescription("物理最小内存");
                        }
                        memMinSpec.setValue(BigDecimal.valueOf(preSpec.getMemoryMin())
                                                    .divide(BigDecimal.valueOf(1024), 0,
                                                            BigDecimal.ROUND_HALF_UP) + "");
                        memMinSpec.setNewValue(BigDecimal.valueOf(vmSpec.getMemoryMin())
                                                       .divide(BigDecimal.valueOf(1024), 0,
                                                               BigDecimal.ROUND_HALF_UP) + "");
                        vmChangeSpecs.add(memMinSpec);

                        ServiceInstanceSpec memMaxSpec = new ServiceInstanceSpec();
                        memMaxSpec.setName("MEMORY_MAX");
                        if (Integer.parseInt(WebConstants.PowerPartitionType.MPAR) == preSpec
                                .getParType()) {
                            //micro Part
                            memMaxSpec.setDescription("虚拟最大内存");
                        } else if (Integer.parseInt(WebConstants.PowerPartitionType.LPAR) == preSpec
                                .getParType()) {
                            //Logic Part
                            memMaxSpec.setDescription("物理最大内存");
                        }
                        memMaxSpec.setValue(BigDecimal.valueOf(preSpec.getMemoryMax())
                                                    .divide(BigDecimal.valueOf(1024), 0,
                                                            BigDecimal.ROUND_HALF_UP) + "");
                        memMaxSpec.setNewValue(BigDecimal.valueOf(vmSpec.getMemoryMax())
                                                       .divide(BigDecimal.valueOf(1024), 0,
                                                               BigDecimal.ROUND_HALF_UP) + "");
                        vmChangeSpecs.add(memMaxSpec);
                    }

                    //原来的vd列表
                    List<ResVd> resVdList = preSpec.getResVdList();
                    //变更中的vd列表
                    List<ResVdInst> dataDisks = vmSpec.getDataDisks();
                    if (!CollectionUtils.isEmpty(dataDisks)) {
                        for (ResVdInst resVdInst : dataDisks) {
                            ServiceInstanceSpec diskSpec = new ServiceInstanceSpec();
                            if (WebConstants.StoragePurpose.SYSTEM_DISK
                                    .equals(resVdInst.getStoragePurpose())) {
                                diskSpec.setValueText("系统盘");
                            } else if (WebConstants.StoragePurpose.DATA_DISK
                                    .equals(resVdInst.getStoragePurpose())) {
                                diskSpec.setValueText("外置存储盘");
                            } else if (WebConstants.StoragePurpose.SYSTEM_DATA_DISK
                                    .equals(resVdInst.getStoragePurpose())) {
                                diskSpec.setValueText("系统外置存储盘");
                            }
                            if (WebConstants.VdOperate.ADD.equals(resVdInst.getOperate())) {
                                diskSpec.setOperate("创建");
                            } else if ("update".equals(resVdInst.getOperate())) {
                                diskSpec.setOperate("修改");
                            } else if (WebConstants.VdOperate.DELLETE
                                    .equals(resVdInst.getOperate())) {
                                diskSpec.setOperate("删除");
                            } else if (WebConstants.VdOperate.EXPAND
                                    .equals(resVdInst.getOperate())) {
                                diskSpec.setOperate("扩容");
                            }
                            diskSpec.setName(resVdInst.getResVdInstName());
                            diskSpec.setNewValue(resVdInst.getDiskSize() + "");
                            diskSpec.setValue("");
                            if (!StringUtil.isNullOrEmpty(resVdInst.getResVdInstId())) {
                                if (!CollectionUtils.isEmpty(resVdList)) {
                                    for (ResVd resVd : resVdList) {
                                        if (resVdInst.getResVdInstId()
                                                .equals(resVd.getResVdSid())) {
                                            diskSpec.setValue(resVd.getAllocateDiskSize() + "");
                                            break;
                                        }
                                    }
                                }
                            }
                            vmDiskChangeSpecs.add(diskSpec);
                        }
                    }

                    result.put("specs", vmChangeSpecs);
                    result.put("diskSpecs", vmDiskChangeSpecs);
                }
            }

            //加入是否为到达最终审判的判断
            if (StringUtil.isNullOrEmpty(processInstanceId) || "0".equals(processInstanceId)) {
                result.put("isFinal", true);
            } else {
                Boolean isFinal = this.activitiService.isFinal(processInstanceId);
                if (isFinal != null) {
                    result.put("isFinal", isFinal);
                }
            }
            resultJson = JsonUtil.toJson(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultJson =
                    JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(
                            WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(resultJson).build();
    }

    private void setChangeSpecList(String nameKey, String nameText, String oldValue,
                                   String newValue, List<ServiceInstanceSpec> specs) {
        ServiceInstanceSpec spec = new ServiceInstanceSpec();
        spec.setName(nameKey);
        spec.setDescription(nameText);

        if (newValue != null) {
            if (nameKey.equals("NEED_LAN") || nameKey.equals("NEED_WAN")) {
                if (!newValue.equals(oldValue)) {
                    if ("1".equals(newValue)) {
                        spec.setNewValue("需要");
                    } else {
                        spec.setNewValue("不需要");
                    }
                }
            } else {
                if (!newValue.equals(oldValue)) {
                    spec.setNewValue(newValue);
                }
            }
        }
        if (nameKey.equals("NEED_LAN") || nameKey.equals("NEED_WAN")) {
            if ("1".equals(oldValue)) {
                spec.setValue("需要");
            } else {
                spec.setValue("不需要");
            }
        } else {
            spec.setValue(oldValue);
        }
        specs.add(spec);
    }

    private void setChangeSpecList(String nameKey, String nameText,
                                   List<ServiceInstanceSpec> oldSpecs,
                                   List<ServiceInstanceSpec> newSpecs,
                                   List<ServiceInstanceSpec> specs) {
        String oldValue = ResDataUtils.getSpecValueFromSpecs(nameKey, oldSpecs);
        String newValue = ResDataUtils.getSpecValueFromSpecs(nameKey, newSpecs);
        setChangeSpecList(nameKey, nameText, oldValue, newValue, specs);
    }

    @Override
    public Response getIdleAdjustInstance(String instanceSid) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Long instSid = Long.parseLong(instanceSid);
        try {
            ServiceInstance
                    serviceInstance =
                    serviceInstanceService.selectByPrimaryKeyInstance(instSid);
            //得到闲置资源的原值
            Criteria example = new Criteria();
            example.put("instanceSid", instSid);
            example.put("status", 3L);
            List<FreeRes> fres = freeresService.selectByFreeInstance(example);
            //得到变更后的值
            Criteria example2 = new Criteria();
            example.put("fresSid", fres.get(0).getFresSid());
            List<FreeResCheckLog> frc = checkLogService.selectByParams(example2);
            String resultJson = "";
            resultMap.put("instanceName", serviceInstance.getInstanceName());
            //resultMap.put("isFree", "0");
            resultMap.put("isFree", String.valueOf(serviceInstance.getIsFree()));
            resultMap.put("oldCpu", fres.get(0).getCpuCores());
            resultMap.put("newCpu", frc.get(0).getCpuCores());
            resultMap.put("oldMem", fres.get(0).getMemory());
            resultMap.put("newMem", frc.get(0).getMemory());
            resultMap.put("oldStorage", fres.get(0).getStorage());
            resultMap.put("newStorage", frc.get(0).getStorage());
            resultMap.put("oldPubIp", fres.get(0).getPubIp());
            if (frc.get(0).getPubIp() != null && frc.get(0).getPubIp() != "") {
                resultMap.put("newPubIp", "保留");
            } else {
                resultMap.put("newPubIp", "回收");
            }
            resultMap.put("oldPriIp", serviceInstance.getInstanceName());
            if (frc.get(0).getPrivageIp() != null && frc.get(0).getPrivageIp() != "") {
                resultMap.put("newPriIp", "保留");
            } else {
                resultMap.put("newPriIp", "回收");
            }
            resultMap.put("status", "success");
            resultJson = JsonUtil.toJson(resultMap);
            return Response.status(Status.OK).entity(resultJson).build();
        } catch (Exception e) {
            String resultJson = "";
            resultMap.put("status", "failure");
            resultJson = JsonUtil.toJson(resultMap);
            return Response.status(Status.OK).entity(resultJson).build();
        }
    }

    @Override
    @WebMethod
    @GET
    @Path("/portal/approve/recovery/{instanceSid}")
    public Response insertRecovery(@PathParam("instanceSid") String instanceSid) {
        String returnJson;
        //判断是否有回收的操作，一次只能有一个
        Criteria example = new Criteria();
        example.put("instanceSid", Long.parseLong(instanceSid));
        List<ServiceChangeLog> changeLogs = changeLogService.selectByParams2(example);
        if (changeLogs != null && changeLogs.size() != 0) {
            String msg = "";
            if (changeLogs.get(0).getCmType() == 0L) {
                msg = "正在进行云主机变更";
            } else if (changeLogs.get(0).getCmType() == 1L) {
                msg = "正在进行云主机回收";
            } else {
                msg = "正在进行其他操作";
            }
            //存在正在回收的记录，则该操作要停止
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_RECOVER_BUSY, new Object[]{msg})));
            return Response.status(Status.OK).entity(returnJson).build();
        } else {
            try {
                serviceInstanceService.insertApprove(Long.parseLong(instanceSid));
                returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, "回收成功！"));
                return Response.status(Status.OK).entity(returnJson).build();
            } catch (Exception e) {
                returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, "回收失败！"));
                return Response.status(Status.OK).entity(returnJson).build();
            }
        }
    }

    private Boolean checkAllDiskExpend(List<Boolean> diskExpend) {
        Boolean diskExpendFlag = false;
        for (Boolean diskFlag : diskExpend) {
            if (diskFlag != null) {
                diskExpendFlag = diskExpendFlag || diskFlag;
            }
        }
        return diskExpendFlag;
    }

    @Override
    @WebMethod
    @GET
    @Path("/platform/approved/adjustinstance/{approveObjectId}/{processInstanceId}")
    public Response getApprovedAdjustInstance(@PathParam("approveObjectId") String approveObjectId,
                                              @PathParam("processInstanceId") String processInstanceId) {
        String resultJson = "";
        Long instanceSid = Long.parseLong(approveObjectId);
        Long processSid = Long.parseLong(processInstanceId);
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //得到该审核记录对应的版本
            Long changeLogSid = null;
            Criteria example = new Criteria();
            example.put("processInstanceId", processSid);
            example.put("processObjectId", instanceSid);
            List<ApproveRecord> approveds = approveRecordService.selectByParams(example);
            List<ServiceInstanceSpec> vmChangeSpecs = new ArrayList<ServiceInstanceSpec>();
            List<ServiceInstanceSpec> vmDiskChangeSpecs = new ArrayList<ServiceInstanceSpec>();
            if (approveds != null && approveds.size() != 0) {
                changeLogSid = approveds.get(0).getProcessTargetId();

                ServiceInstanceChangeLog
                        changeLog =
                        instanceChangeLogService.selectByPrimaryKey(changeLogSid);
                ServiceInstance
                        instance =
                        this.serviceInstanceService.selectByPrimaryKey(instanceSid);
                MgtObj mgtObj = mgtObjService.selectByPrimaryKey(instance.getMgtObjSid());
                //虚机原配置
                ResVm preSpec = JsonUtil.fromJson(changeLog.getChangePreSpec(), ResVm.class);

                if (WebConstants.instanceChangeType.CHANGE.equals(changeLog.getChangeType())) {
                    //虚机变更信息
                    ServiceInstanceChangeLogSpec<ResVmInst>
                            changeSpec =
                            JsonUtil.fromJson(changeLog.getChangeSpec(),
                                              new TypeReference<ServiceInstanceChangeLogSpec<ResVmInst>>() {
                                              });
                    ResVmInst vmSpec = changeSpec.getParams();

                    ServiceInstanceSpec cpuSpec = new ServiceInstanceSpec();
                    cpuSpec.setName("CPU");
                    cpuSpec.setDescription("CPU");
                    cpuSpec.setValue(preSpec.getCpuCores() + "");
                    cpuSpec.setNewValue(vmSpec.getCpu() + "");

                    ServiceInstanceSpec memSpec = new ServiceInstanceSpec();
                    memSpec.setName("MEMORY");
                    memSpec.setDescription("内存");
                    memSpec.setValue(BigDecimal.valueOf(preSpec.getMemorySize())
                                             .divide(BigDecimal.valueOf(1024), 0,
                                                     BigDecimal.ROUND_HALF_UP) + "");
                    memSpec.setNewValue(BigDecimal.valueOf(vmSpec.getMemory())
                                                .divide(BigDecimal.valueOf(1024), 0,
                                                        BigDecimal.ROUND_HALF_UP) + "");

                    vmChangeSpecs.add(cpuSpec);
                    vmChangeSpecs.add(memSpec);

                    //原来的vd列表
                    List<ResVd> resVdList = preSpec.getResVdList();
                    //变更中的vd列表
                    List<ResVdInst> dataDisks = vmSpec.getDataDisks();
                    if (!CollectionUtils.isEmpty(dataDisks)) {
                        for (ResVdInst resVdInst : dataDisks) {
                            ServiceInstanceSpec diskSpec = new ServiceInstanceSpec();
                            if (WebConstants.StoragePurpose.SYSTEM_DISK
                                    .equals(resVdInst.getStoragePurpose())) {
                                diskSpec.setValueText("系统盘");
                            } else if (WebConstants.StoragePurpose.DATA_DISK
                                    .equals(resVdInst.getStoragePurpose())) {
                                diskSpec.setValueText("外置存储盘");
                            } else if (WebConstants.StoragePurpose.SYSTEM_DATA_DISK
                                    .equals(resVdInst.getStoragePurpose())) {
                                diskSpec.setValueText("系统外置存储盘");
                            }
                            if (WebConstants.VdOperate.ADD.equals(resVdInst.getOperate())) {
                                diskSpec.setOperate("创建");
                            } else if ("update".equals(resVdInst.getOperate())) {
                                diskSpec.setOperate("修改");
                            } else if (WebConstants.VdOperate.DELLETE
                                    .equals(resVdInst.getOperate())) {
                                diskSpec.setOperate("删除");
                            } else if (WebConstants.VdOperate.EXPAND
                                    .equals(resVdInst.getOperate())) {
                                diskSpec.setOperate("扩容");
                            }
                            diskSpec.setName(resVdInst.getResVdInstName());
                            diskSpec.setNewValue(resVdInst.getDiskSize() + "");
                            diskSpec.setValue("");
                            if (!StringUtil.isNullOrEmpty(resVdInst.getResVdInstId())) {
                                if (!CollectionUtils.isEmpty(resVdList)) {
                                    for (ResVd resVd : resVdList) {
                                        if (resVdInst.getResVdInstId()
                                                .equals(resVd.getResVdSid())) {
                                            diskSpec.setValue(resVd.getAllocateDiskSize() + "");
                                            break;
                                        }
                                    }
                                }
                            }
                            vmDiskChangeSpecs.add(diskSpec);
                        }
                    }
                } else if (WebConstants.instanceChangeType.UNSUBSCRIBE
                        .equals(changeLog.getChangeType())) {
                    ServiceInstanceSpec cpuSpec = new ServiceInstanceSpec();
                    cpuSpec.setName("CPU");
                    cpuSpec.setDescription("CPU");
                    cpuSpec.setValue(preSpec.getCpuCores() + "");

                    ServiceInstanceSpec memSpec = new ServiceInstanceSpec();
                    memSpec.setName("MEMORY");
                    memSpec.setDescription("内存");
                    memSpec.setValue(BigDecimal.valueOf(preSpec.getMemorySize())
                                             .divide(BigDecimal.valueOf(1024), 0,
                                                     BigDecimal.ROUND_HALF_UP) + "");

                    vmChangeSpecs.add(cpuSpec);
                    vmChangeSpecs.add(memSpec);

                    List<ResVd> resVdList = preSpec.getResVdList();
                    if (!CollectionUtils.isEmpty(resVdList)) {
                        for (ResVd resVdInst : resVdList) {
                            ServiceInstanceSpec diskSpec = new ServiceInstanceSpec();
                            if (WebConstants.StoragePurpose.SYSTEM_DISK
                                    .equals(resVdInst.getStoragePurpose())) {
                                diskSpec.setValueText("系统盘");
                            } else if (WebConstants.StoragePurpose.DATA_DISK
                                    .equals(resVdInst.getStoragePurpose())) {
                                diskSpec.setValueText("外置存储盘");
                            } else if (WebConstants.StoragePurpose.SYSTEM_DATA_DISK
                                    .equals(resVdInst.getStoragePurpose())) {
                                diskSpec.setValueText("系统外置存储盘");
                            }
                            diskSpec.setOperate("删除");
                            diskSpec.setName(resVdInst.getVdName());
                            diskSpec.setNewValue("");
                            diskSpec.setValue(resVdInst.getAllocateDiskSize() + "");
                            vmDiskChangeSpecs.add(diskSpec);
                        }
                    }
                }

                result.put("mgtObjSid", mgtObj.getMgtObjSid());
                result.put("mgtObjName", mgtObj.getName());
                result.put("instanceName", instance.getInstanceName());
                result.put("ownerId", instance.getOwnerId());
                result.put("specs", vmChangeSpecs);
                result.put("diskSpecs", vmDiskChangeSpecs);
            } else {
                throw new RuntimeException("该审核记录不存在，请刷新重试！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultJson = JsonUtil.toJson(result);
        return Response.status(Status.OK).entity(resultJson).build();
    }

    @Override
    @WebMethod
    @GET
    @Path("/platform/approved/adjustOther/{approveObjectId}/{processInstanceId}")
    public Response getApprovedAdjustOther(@PathParam("approveObjectId") String approveObjectId,
                                           @PathParam("processInstanceId") String processInstanceId) {
        String resultJson = "";
        Long objectSid = Long.parseLong(approveObjectId);
        Long processSid = Long.parseLong(processInstanceId);
        try {
            Criteria example = new Criteria();
            example.put("processInstanceId", processSid);
            example.put("processObjectId", objectSid);
            List<ApproveRecord> approveds = approveRecordService.selectByParams(example);

            Map<String, Object> result = new HashMap<String, Object>();
            //得到change_log表中的数据
            if (approveds != null && approveds.size() != 0) {
                Long changeLogSid = approveds.get(0).getProcessTargetId();
                ServiceInstanceChangeLog
                        changeLog =
                        instanceChangeLogService.selectByPrimaryKey(changeLogSid);
                if (WebConstants.instanceChangeType.CHANGE_MANAGER
                        .equals(changeLog.getChangeType())) {
                    MgtObj mgtObj = mgtObjService.selectByPrimaryKey(objectSid);
                    result.put("mgtObjSid", mgtObj.getMgtObjSid());
                    result.put("mgtObjName", mgtObj.getName());
                    result.put("ownerId", changeLog.getCreatedBy());

                    //获取变更内容
                    String changeSpec = changeLog.getChangeSpec();
                    Map<String, String> specMap = JsonUtil.fromJson(changeSpec, Map.class);

                    User
                            oldUser =
                            userService
                                    .selectByPrimaryKey(Long.parseLong(specMap.get("oldUserSid")),
                                                        0);
                    User
                            newUser =
                            userService
                                    .selectByPrimaryKey(Long.parseLong(specMap.get("newUserSid")),
                                                        0);

                    List<Map<String, String>> changeData = new ArrayList<Map<String, String>>();
                    Map<String, String> oldManagerInfo = new HashMap<String, String>();
                    oldManagerInfo.put("specName", "原项目经理");
                    oldManagerInfo.put("userName", oldUser.getAccount());
                    Map<String, String> newManagerInfo = new HashMap<String, String>();
                    newManagerInfo.put("specName", "新项目经理");
                    newManagerInfo.put("userName", newUser.getAccount());

                    changeData.add(oldManagerInfo);
                    changeData.add(newManagerInfo);

                    result.put("changeData", changeData);
                } else if (WebConstants.instanceChangeType.CHANGE_MGT_OBJ
                        .equals(changeLog.getChangeType())) {
                    //获取变更内容
                    String changeSpec = changeLog.getChangeSpec();
                    Map<String, String> specMap = JsonUtil.fromJson(changeSpec, Map.class);

                    MgtObj
                            oldMgtObj =
                            mgtObjService.selectByPrimaryKey(
                                    Long.parseLong(specMap.get("oldMgtObjSid")));
                    MgtObj
                            newMgtObj =
                            mgtObjService.selectByPrimaryKey(
                                    Long.parseLong(specMap.get("newMgtObjSid")));

                    List<Map<String, String>> changeData = new ArrayList<Map<String, String>>();
                    Map<String, String> oldManagerInfo = new HashMap<String, String>();
                    oldManagerInfo.put("specName", "原所属项目");
                    oldManagerInfo.put("userName", oldMgtObj.getName());
                    Map<String, String> newManagerInfo = new HashMap<String, String>();
                    newManagerInfo.put("specName", "新所属项目");
                    newManagerInfo.put("userName", newMgtObj.getName());

                    changeData.add(oldManagerInfo);
                    changeData.add(newManagerInfo);

                    result.put("changeData", changeData);
                }
                resultJson = JsonUtil.toJson(result);
            } else {
                throw new RuntimeException("该审核记录不存在，请刷新重试！");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultJson =
                    JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(
                            WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(resultJson).build();
    }

    /**
     * 获取终审Hba信息
     */
    @Override
    @WebMethod
    @POST
    @Path("/hba")
    public Response getHbaList(String params) {
//		String returnJson = "" ;
//		try {
//			Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
//			String hostSid = (String)map.get("hostSid");
//			String itemType = (String)map.get("itemType");
//			Criteria criteria = new Criteria();
//			criteria.put("resHostSid", hostSid);
//			criteria.put("hostItemTypeCode", itemType);
//			criteria.put("resAllocStatus", WebConstants.HostItemAllocStatus.FREE);
//			List<ResHostItem> hbaList = new ArrayList<ResHostItem>();
//			if(WebConstants.HostItemTypeCode.ETHERNET.equals(itemType)) {
//				hbaList = this.resHostItemService.selectByParams(criteria);
//				for(ResHostItem hostItem : hbaList) {
//					hostItem.setItemLocation(hostItem.getItemLocation() +  (hostItem.getMacAddress() != null ?  "(" + hostItem.getMacAddress() + ")" : ""));
//				}
//			} else if(WebConstants.HostItemTypeCode.LOCAL_DISK.equals(itemType)){
//				hbaList = this.resHostItemService.selectNoDiskRootHba(hostSid);
//			} else {
//				hbaList = this.resHostItemService.selectByParams(criteria);
//			}
//			returnJson = JsonUtil.toJson(hbaList);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
//		}
//		return Response.status(Status.OK).entity(returnJson).build();
        return null;
    }

    /**
     * 获取终审交换机信息
     */
    @Override
    @WebMethod
    @POST
    @Path("/switch")
    public Response getSwithList(String params) {
//		String returnJson = "" ;
//		try {
//			Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
//			String hostSid = (String)map.get("hostSid");
//			List<ResVs> switchList = this.resVsService.selectByHostSid(hostSid);
//			returnJson = JsonUtil.toJson(switchList);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			returnJson = JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
//		}
//		return Response.status(Status.OK).entity(returnJson).build();
        return null;
    }

    /**
     * 获取终审CPU资源池信息
     */
    @Override
    @WebMethod
    @POST
    @Path("/cpuPool")
    public Response getCpuPoolList(String params) {
//		String returnJson = "" ;
//		try {
//			Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
//			String hostSid = (String)map.get("hostSid");
//			Criteria criteria = new Criteria();
//			criteria.put("resHostSid", hostSid);
//			List<ResCpuPool> cpuPoolList = this.cpuPoolService.selectByParams(criteria);
//			returnJson = JsonUtil.toJson(cpuPoolList);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			returnJson = JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
//		}
//		return Response.status(Status.OK).entity(returnJson).build();
        return null;
    }

    /**
     * 获取内网网络
     */
    @Override
    @WebMethod
    @POST
    @Path("/lans")
    public Response getLanList(String params) {
        String returnJson = "";
        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            String hostSid = (String) map.get("hostSid");
//			List<ResNetwork> nets = this.resNetworkService.selectNetworksByHostSid(hostSid);
            //TODO
            List<ResNetwork> nets = new ArrayList<>();
            returnJson = JsonUtil.toJson(nets);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            returnJson =
                    JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(
                            WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    /**
     * 检查主机名是否重复
     */
    @Override
    @WebMethod
    @POST
    @Path("/checkVmName")
    public Response checkVmName(String params) {
        String returnJson = "";
        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            String vmName = (String) map.get("vmName");
            String oldVmName = (String) map.get("oldVmName");
            Criteria criteria = new Criteria();
            criteria.put("vmName", vmName);
            //TODO
//			List<ResVm> resVmList = this.resVmService.selectByParams(criteria);
            List<ResVm> resVmList = new ArrayList<>();
            criteria = new Criteria();
            if (StringUtils.isNotBlank(vmName)) {
                criteria.put("resInstName", vmName);
            }
            if (StringUtils.isNotBlank(oldVmName)) {
                criteria.put("oldResInstName", oldVmName);
            }
            List<ServiceInstance>
                    instanceList =
                    this.serviceInstanceService.selectByParams(criteria);

            if (resVmList.size() > 0 || instanceList.size() > 0) {
                returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, ""));
            } else {
                returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, ""));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            returnJson =
                    JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(
                            WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    @Override
    @WebMethod
    @GET
    @Path("/adjustOtherDetail/{approveObjectId}/{processInstanceId}")
    public Response getApproveAdjustOtherDetail(
            @PathParam("approveObjectId") String approveObjectId,
            @PathParam("processInstanceId") String processInstanceId) {

        String resultJson = "";
        try {
            Long objectSid = Long.parseLong(approveObjectId);
            Map<String, Object> result = new HashMap<String, Object>();
            //得到change_log表中的数据
            List<ServiceInstanceChangeLog>
                    changeLogs =
                    this.instanceChangeLogService.selectLastChangeLog(objectSid);
            if (CollectionUtils.isEmpty(changeLogs) || changeLogs.size() > 1) {
                throw new RuntimeException(
                        "The instance change log number is incorrect. objectSid=" + objectSid
                        + " changeLog.size=" + changeLogs.size());
            } else {
                ServiceInstanceChangeLog changeLog = changeLogs.get(0);
                if (WebConstants.instanceChangeType.CHANGE_MANAGER
                        .equals(changeLog.getChangeType())) {
                    MgtObj mgtObj = mgtObjService.selectByPrimaryKey(objectSid);
                    result.put("mgtObjSid", mgtObj.getMgtObjSid());
                    result.put("mgtObjName", mgtObj.getName());
                    result.put("ownerId", changeLog.getCreatedBy());

                    //获取变更内容
                    String changeSpec = changeLog.getChangeSpec();
                    Map<String, String> specMap = JsonUtil.fromJson(changeSpec, Map.class);

                    User
                            oldUser =
                            userService
                                    .selectByPrimaryKey(Long.parseLong(specMap.get("oldUserSid")),
                                                        0);
                    User
                            newUser =
                            userService
                                    .selectByPrimaryKey(Long.parseLong(specMap.get("newUserSid")),
                                                        0);

                    List<Map<String, String>> changeData = new ArrayList<Map<String, String>>();
                    Map<String, String> oldManagerInfo = new HashMap<String, String>();
                    oldManagerInfo.put("specName", "原项目经理");
                    oldManagerInfo.put("userName", oldUser.getAccount());
                    Map<String, String> newManagerInfo = new HashMap<String, String>();
                    newManagerInfo.put("specName", "新项目经理");
                    newManagerInfo.put("userName", newUser.getAccount());

                    changeData.add(oldManagerInfo);
                    changeData.add(newManagerInfo);

                    result.put("changeData", changeData);
                } else if (WebConstants.instanceChangeType.CHANGE_MGT_OBJ
                        .equals(changeLog.getChangeType())) {
                    ServiceInstance instance = serviceInstanceService.selectByPrimaryKey(objectSid);
                    //获取变更内容
                    String changeSpec = changeLog.getChangeSpec();
                    Map<String, String> specMap = JsonUtil.fromJson(changeSpec, Map.class);

                    MgtObj
                            oldMgtObj =
                            mgtObjService.selectByPrimaryKey(
                                    Long.parseLong(specMap.get("oldMgtObjSid")));
                    MgtObj
                            newMgtObj =
                            mgtObjService.selectByPrimaryKey(
                                    Long.parseLong(specMap.get("newMgtObjSid")));

                    List<Map<String, String>> changeData = new ArrayList<Map<String, String>>();
                    Map<String, String> oldManagerInfo = new HashMap<String, String>();
                    oldManagerInfo.put("specName", "原所属项目");
                    oldManagerInfo.put("userName", oldMgtObj.getName());
                    Map<String, String> newManagerInfo = new HashMap<String, String>();
                    newManagerInfo.put("specName", "新所属项目");
                    newManagerInfo.put("userName", newMgtObj.getName());

                    changeData.add(oldManagerInfo);
                    changeData.add(newManagerInfo);

                    result.put("changeData", changeData);
                }
            }

            //加入是否为到达最终审判的判断
            if (StringUtil.isNullOrEmpty(processInstanceId) || "0".equals(processInstanceId)) {
                result.put("isFinal", true);
            } else {
                Boolean isFinal = this.activitiService.isFinal(processInstanceId);
                if (isFinal != null) {
                    result.put("isFinal", isFinal);
                }
            }
            resultJson = JsonUtil.toJson(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultJson =
                    JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(
                            WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(resultJson).build();
    }

}
