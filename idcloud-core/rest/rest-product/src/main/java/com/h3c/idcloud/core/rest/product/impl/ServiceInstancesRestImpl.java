/**
 *
 */
package com.h3c.idcloud.core.rest.product.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.product.ServiceChangeLog;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceChangeLog;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceChangeLogSpec;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceSpec;
import com.h3c.idcloud.core.pojo.dto.product.ServiceSpecChange;
import com.h3c.idcloud.core.pojo.instance.ResVmInst;
import com.h3c.idcloud.core.rest.product.ServiceInstancesRest;
import com.h3c.idcloud.core.service.product.api.ServiceChangeLogService;
import com.h3c.idcloud.core.service.product.api.ServiceConfigService;
import com.h3c.idcloud.core.service.product.api.ServiceDefineService;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceChangeLogService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceSpecService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * @author 徐印
 */
@Component
public class ServiceInstancesRestImpl implements ServiceInstancesRest {

    private static final Logger logger = LoggerFactory.getLogger(ServiceInstancesRestImpl.class);

    /**
     * 服务实例Service
     */
    @Reference(version = "1.0.0")
    private ServiceInstanceService serviceInstanceService;

    /**
     * 服务实例Service
     */
    @Reference(version = "1.0.0")
    private ServiceDefineService serviceDefineService;

    /**
     * 服务实例规格Service
     */
    @Reference(version = "1.0.0")
    private ServiceInstanceSpecService serviceInstanceSpecService;

    /**
     * 服务规格Service
     */
    @Reference(version = "1.0.0")
    private ServiceConfigService serviceConfigService;
    /**
     * 产品实例ServicesTest
     */
//      ---------wsl
//	@Autowired
//	private BssOrderService productService;

    // /** 资源实例Service */
    // @Autowired
    // private ResourceInstanceService resourceInstanceService;
    //
    // /** 虚机资源实例Service */
    // @Autowired
    // private ResourceInstanceVmService resourceInstanceVmService;
    //
    // /** 虚机资源存储实例Service */
    // @Autowired
    // private ResourceInstanceStorageService resourceInstanceStorageService;

//	@Autowired     --------wsl
//	private ResVmNetworkService resVmNetworkService;

    @Reference(version = "1.0.0")
    private ServiceInstResService serviceInstResService;

    @Reference(version = "1.0.0")
    private ServiceChangeLogService changeLogService;

//	@Autowired          --------wsl
//	private BusinesstypeService businesstypeService;

//	@Autowired        ----------wsl
//	private ResVmService resVmService;

    @Reference(version = "1.0.0")
    private ServiceInstanceChangeLogService instanceChangeLogService;

    @Reference(version = "1.0.0")
    private UserService userService;

    /**
     * 取得单个服务实例
     */
    @Override
    public Response getServiceInstance(String serviceInstanceSid) {
        ServiceInstance serviceInstance = this.serviceInstanceService.selectByPrimaryKey(Long
                                                                                                 .parseLong(
                                                                                                         serviceInstanceSid));
        List<ServiceInstanceSpec> servcieInstanceSpecList = this.serviceInstanceSpecService
                .selectByInstanceSid(serviceInstance.getInstanceSid());

        serviceInstance.setServiceInstanceSpec(servcieInstanceSpecList);

        String json = JsonUtil.toJson(serviceInstance);
        return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 根据条件获取serviceInstance列表服务
     */
    @Override
    public Response searchServiceInstance(String params) {
        try {
            Criteria example = new Criteria();
            if (!StringUtil.isNullOrEmpty(params)) {
                Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
                if (!StringUtil.isNullOrEmpty(map.get("orderIdLike"))) {
                    example.put("orderIdLike", map.get("orderIdLike"));
                }

                if (!StringUtil.isNullOrEmpty(map.get("instanceSid"))) {
                    example.put("instanceSid", map.get("instanceSid"));
                }

                if (!StringUtil.isNullOrEmpty(map.get("parentInstanceSid"))) {
                    example.put("parentInstanceSid", map.get("parentInstanceSid"));
                }

                if (!StringUtil.isNullOrEmpty(map.get("serviceSid"))) {
                    example.put("serviceSid", map.get("serviceSid"));
                }

                if (!StringUtil.isNullOrEmpty(map.get("dredgeFromDate"))) {
                    example.put("dredgeFromDate", map.get("dredgeFromDate"));
                }

                if (!StringUtil.isNullOrEmpty(map.get("dredgeToDate"))) {
                    example.put("dredgeToDate", map.get("dredgeToDate"));
                }

                if (!StringUtil.isNullOrEmpty(map.get("instanceNameLike"))) {
                    example.put("instanceNameLike", map.get("instanceNameLike"));
                }

                if (!StringUtil.isNullOrEmpty(map.get("instanceName"))) {
                    example.put("instanceName", map.get("instanceName"));
                }

                if (!StringUtil.isNullOrEmpty(map.get("statusParams"))) {
                    example.put("statusParams", map.get("statusParams"));
                }
                if (!StringUtil.isNullOrEmpty(map.get("status"))) {
                    example.put("status", map.get("status"));
                }
                if (!StringUtil.isNullOrEmpty(map.get("expiringFromDate"))) {
                    example.put("expiringFromDate", map.get("expiringFromDate"));
                }
                if (!StringUtil.isNullOrEmpty(map.get("expiringToDate"))) {
                    example.put("expiringToDate", map.get("expiringToDate"));
                }

                example.setOrderByClause("A.INSTANCE_SID ASC");
            }
            List<ServiceInstance> list = serviceInstanceService.selectByParams(example);
            String json = JsonUtil.toJson(list);
            return Response.status(Status.OK).entity(json).build();
        } catch (Exception e) {
            e.printStackTrace();
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            String resultJson = "";
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
        }

    }

    /**
     * 根据条件获取serviceInstance列表服务
     */
    @Override
    public Response searchServiceInstanceByName(String params) {
        try {
            Criteria example = new Criteria();
            if (!StringUtil.isNullOrEmpty(params)) {
                Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
                if (!StringUtil.isNullOrEmpty(map.get("orderIdLike"))) {
                    example.put("orderIdLike", map.get("orderIdLike"));
                }

                if (!StringUtil.isNullOrEmpty(map.get("instanceSid"))) {
                    example.put("instanceSid", map.get("instanceSid"));
                }

                if (!StringUtil.isNullOrEmpty(map.get("parentInstanceSid"))) {
                    example.put("parentInstanceSid", map.get("parentInstanceSid"));
                }

                if (!StringUtil.isNullOrEmpty(map.get("serviceSid"))) {
                    example.put("serviceSid", map.get("serviceSid"));
                }

                if (!StringUtil.isNullOrEmpty(map.get("dredgeFromDate"))) {
                    example.put("dredgeFromDate", map.get("dredgeFromDate"));
                }

                if (!StringUtil.isNullOrEmpty(map.get("dredgeToDate"))) {
                    example.put("dredgeToDate", map.get("dredgeToDate"));
                }

                if (!StringUtil.isNullOrEmpty(map.get("instanceNameLike"))) {
                    example.put("instanceNameLike", map.get("instanceNameLike"));
                }

                if (!StringUtil.isNullOrEmpty(map.get("instanceName"))) {
                    example.put("instanceName", map.get("instanceName"));
                }

                if (!StringUtil.isNullOrEmpty(map.get("status"))) {
                    example.put("status", map.get("status"));
                }
                if (!StringUtil.isNullOrEmpty(map.get("expiringFromDate"))) {
                    example.put("expiringFromDate", map.get("expiringFromDate"));
                }
                if (!StringUtil.isNullOrEmpty(map.get("expiringToDate"))) {
                    example.put("expiringToDate", map.get("expiringToDate"));
                }
            }
            List<ServiceInstance>
                    list =
                    serviceInstanceService.selectByParamsOrderByPurpose(example);
            for (ServiceInstance instance : list) {
                if (WebConstants.ServiceSid.SERVICE_STORAGE.equals(instance.getServiceSid())) {
                    String
                            vdName =
                            this.serviceInstanceService
                                    .selectVdNameByInstanceSid(instance.getInstanceSid());
                    if (vdName != null) {
                        instance.setVdName(vdName);
                    } else {
                        instance.setVdName("");
                    }
                }
            }
            String json = JsonUtil.toJson(list);
            return Response.status(Status.OK).entity(json).build();
        } catch (Exception e) {
            e.printStackTrace();
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null); -----wsl
            String resultJson = ""; //wsl
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
        }

    }

    /**
     * 根据条件获取后台serviceInstance列表 配置信息
     */
    @Override
    public Response searchServiceInstanceDetailConfig(String params) {
        try {
            long instanceSid = 0L;
            if (!StringUtil.isNullOrEmpty(params)) {
                Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
                instanceSid = Long.parseLong(StringUtil.nullToEmpty(map.get("instanceSid")));
            }
            List<ServiceInstanceSpec> instanceSpecServices = serviceInstanceSpecService
                    .selectByInstanceSid(instanceSid);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            for (ServiceInstanceSpec serviceInstanceSpec : instanceSpecServices) {
                if ("CPU".equals(serviceInstanceSpec.getName())) {
                    resultMap.put("cpuCores", serviceInstanceSpec.getValue() + "核");
                } else if ("MEMORY".equals(serviceInstanceSpec.getName())) {
                    resultMap.put("memorySize", serviceInstanceSpec.getValue() + "G");
                } else if ("OS".equals(serviceInstanceSpec.getName())) {
//					String osTypeName = WebUtil.findDisplayByParams(serviceInstanceSpec.getValue(), ------wsl
//							WebConstants.CodeCategroy.OS_TYPE);
//					resultMap.put("osTypeName", osTypeName);
                }
            }
            String json = JsonUtil.toJson(resultMap);
            return Response.status(Status.OK).entity(json).build();
        } catch (Exception e) {
            e.printStackTrace();
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            String resultJson = "";//wsl
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
        }
    }

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
    // long serviceSid = 0L;
    //
    // if (!StringUtil.isNullOrEmpty(params)) {
    // Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
    // instanceSid =
    // Long.parseLong(StringUtil.nullToEmpty(map.get("instanceSid")));
    // serviceSid =
    // Long.parseLong(StringUtil.nullToEmpty(map.get("serviceSid")));
    // }
    //
    // Criteria criteria = new Criteria();
    //
    // if (serviceSid == 105L) {
    // criteria.put("instanceSid", instanceSid);
    // resourceInstanceStorageList =
    // resourceInstanceStorageService.selectByParams(criteria);
    // } else {
    // criteria.put("instanceSid", instanceSid);
    // List<ResourceInstanceVm> resourceInstanceVmList =
    // resourceInstanceVmService.selectByParams(criteria);
    //
    // if (null != resourceInstanceVmList
    // && resourceInstanceVmList.size() > 0) {
    // criteria = new Criteria();
    // criteria.put("resInstanceHostSid",
    // resourceInstanceVmList.get(0).getResInstanceSid());
    // resourceInstanceStorageList =
    // resourceInstanceStorageService.selectByParams(criteria);
    // }
    // }
    //
    // String json = JsonUtil.toJson(resourceInstanceStorageList);
    // return Response.status(Status.OK).entity(json).build();
    // } catch (Exception e) {
    // e.printStackTrace();
    // String resultJson =
    // InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
    // null);
    // return Response.status(Status.INTERNAL_SERVER_ERROR)
    // .entity(resultJson)
    // .build();
    // }
    // }

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
    // InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
    // null);
    // return Response.status(Status.INTERNAL_SERVER_ERROR)
    // .entity(resultJson)
    // .build();
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
    // long serviceSid = 0L;
    //
    // if (!StringUtil.isNullOrEmpty(params)) {
    // Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
    // instanceSid =
    // Long.parseLong(StringUtil.nullToEmpty(map.get("instanceSid")));
    // serviceSid =
    // Long.parseLong(StringUtil.nullToEmpty(map.get("serviceSid")));
    // }
    //
    // Criteria criteria = new Criteria();
    //
    // if (serviceSid == 105L) {
    // criteria.put("instanceSid", instanceSid);
    // resourceInstanceStorageList =
    // resourceInstanceStorageService.selectByParams(criteria);
    // } else {
    // criteria.put("instanceSid", instanceSid);
    // List<ResourceInstanceVm> resourceInstanceVmList =
    // resourceInstanceVmService.selectByParams(criteria);
    //
    // if (null != resourceInstanceVmList
    // && resourceInstanceVmList.size() > 0) {
    // criteria = new Criteria();
    // criteria.put("resInstanceHostSid",
    // resourceInstanceVmList.get(0).getResInstanceSid());
    // resourceInstanceStorageList =
    // resourceInstanceStorageService.selectByParams(criteria);
    // }
    // }
    //
    // String json = JsonUtil.toJson(resourceInstanceStorageList);
    // return Response.status(Status.OK).entity(json).build();
    // } catch (Exception e) {
    // e.printStackTrace();
    // String resultJson =
    // InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
    // null);
    // return Response.status(Status.INTERNAL_SERVER_ERROR)
    // .entity(resultJson)
    // .build();
    // }
    // }

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
    // InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
    // null);
    // return Response.status(Status.INTERNAL_SERVER_ERROR)
    // .entity(resultJson)
    // .build();
    // }
    // }

    /**
     * 调整租期
     */
    @Override
    public Response changeExpiringDate(String params) {
        Map<String, Object> map;

        map = JsonUtil.fromJson(params, Map.class);
        long serviceInstanceSid = Long.parseLong(map.get("serviceInstanceSid").toString());
        int duration = Integer.parseInt(map.get("duration").toString());
        ServiceInstance si = this.serviceInstanceService.selectByPrimaryKey(serviceInstanceSid);
        Date oldExpiringDate = si.getExpiringDate();
        Calendar c = Calendar.getInstance();
        c.setTime(oldExpiringDate);
        int oldMonth = c.get(Calendar.MONTH);
        c.set(Calendar.MONTH, oldMonth + duration);
        si.setExpiringDate(c.getTime());
        int result = this.serviceInstanceService.updateByPrimaryKeySelective(si);
//			if (result > 0) { --wsl
//				String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.SUCCESS, "租期调整成功！");
//				return Response.status(Status.OK).entity(resultJson).build();
//			} else {
//				String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, "租期调整失败！");
//				return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
//			}
        return null;
    }

    /**
     * 获取最新版本号
     */
    @Override
    public Response getNewVersion(String params) {

        long instanceSid = 0L;
        String name = "";
        if (!StringUtil.isNullOrEmpty(params)) {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            instanceSid = Long.parseLong(StringUtil.nullToEmpty(map.get("instanceSid")));
            name = StringUtil.nullToEmpty(map.get("name"));
        }
        Criteria criteria = new Criteria();
        criteria.put("instanceSid", instanceSid);
        criteria.put("name", name);
        // criteria.put("instanceSid", 2595L);
        // criteria.put("name", "CPU");
        List<ServiceInstanceSpec>
                speclist =
                serviceInstanceSpecService.selectByParamsNewVersion(criteria);
        String json = JsonUtil.toJson(speclist);

        return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 写入最新版本记录
     */
    @Override
    public Response setNewVersion(String params) {
        String resultJson = null;
        try {
            List<Map<String, Object>> instanceList = JsonUtil.fromJson(params,
                                                                       new TypeReference<List<Map<String, Object>>>() {
                                                                       });
            boolean flag = true;
            for (Map<String, Object> map : instanceList) {
                if (map.get("instanceSid") != null) {
                    Integer instanceSid = (Integer) map.get("instanceSid");
                    Criteria example = new Criteria();
                    example.put("instanceSid", instanceSid);
                    List<ServiceChangeLog> changeLogs = changeLogService.selectByParams2(example);
                    if (changeLogs != null && changeLogs.size() != 0) {
                        // 说明该实例正在进行变更或者回收等流程，需要等流程走完
//						resultJson = JsonUtil.toJson(new RestResult(RestResult.SUCCESS, "该云主机已提交变更申请，请勿重复申请！", null));
//						return Response.status(Status.OK).entity(resultJson).build();
                        return null;//--------wsl
                    }
                }
            }
            serviceInstanceService.modifyVm(instanceList);
//			resultJson = JsonUtil.toJson(new RestResult(RestResult.SUCCESS, "变更请求提交成功!", null)); -----wsl
            return Response.status(Status.OK).entity(resultJson).build();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
//			resultJson = JsonUtil.toJson(new RestResult(RestResult.SUCCESS, "变更申请提交失败！", null));  -----wsl
            return Response.status(Status.OK).entity(resultJson).build();
        }
    }

    /**
     * 服务调整规格申请(云主机调整规格申请，块存储调整规格申请)
     */
    @Override
    public Response adjustSpec(String params, @PathParam("serviceSid") Long serviceSid,
                               @PathParam("instanceSid") Long instanceSid) {
        String resultJson = null;
        try {
            TypeReference<?> vmType = null;
            ServiceSpecChange<?> serviceInstanceChange = null;

            // 检查是否有调整规格申请正在执行中
            ServiceInstance
                    serviceInstance =
                    this.serviceInstanceService.selectByPrimaryKey(instanceSid);
            List<ServiceInstanceChangeLog>
                    changeLogs =
                    this.instanceChangeLogService.selectLastChangeLog(instanceSid);
            if (changeLogs.size() != 0) {
//				resultJson = JsonUtil.toJson(new RestResult(RestResult.SUCCESS, "该服务已提交调整规格申请，请勿重复申请！", null));
                return Response.status(Status.OK).entity(resultJson).build();
            }

//			if (WebConstants.ServiceSid.SERVICE_VM.equals(serviceSid)) {
//				vmType = new TypeReference<ServiceSpecChange<ResVmInst>>() {
//				};
//				ServiceSpecChange<ResVmInst> vmChange = JsonUtil.fromJson(params, vmType);
//				String resSid = this.serviceInstResService.getResSidByInstanceSid(instanceSid);
//				ResVmInst resVmInst = vmChange.getChangeData();
//				resVmInst.setResVmInstId(resSid);
//				resVmInst.setMgtObjSid(serviceInstance.getMgtObjSid());
//				serviceInstanceChange = vmChange;
//			} else if (WebConstants.ServiceSid.SERVICE_STORAGE.equals(serviceSid)) {
//				vmType = new TypeReference<ServiceSpecChange<VdExpandRequest>>() {
//				};
//				ServiceSpecChange<VdExpandRequest> vdChange = JsonUtil.fromJson(params, vmType);
//				String resSid = this.serviceInstResService.getResSidByInstanceSid(instanceSid);
//				VdExpandRequest vdExpandRequest = vdChange.getChangeData();
//				vdExpandRequest.setResVdSid(resSid);
//				vdExpandRequest.setMgtObjSid(serviceInstance.getMgtObjSid().toString());
//				serviceInstanceChange = vdChange;
//			}
//
//			this.serviceInstanceService.adjustServiceSpec(serviceInstance, serviceInstanceChange);
//			resultJson = JsonUtil.toJson(new RestResult(RestResult.SUCCESS, "服务调整规格提交成功！", null));
            return Response.status(Status.OK).entity(resultJson).build();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
//			resultJson = JsonUtil.toJson(new RestResult(RestResult.FAILURE, "服务调整规格提交失败！", null));
            return Response.status(Status.OK).entity(resultJson).build();
        }
    }

    /**
     * 根据条件获取记录
     */
    @Override
    public Response getLatest(String params) {
        try {
            long instanceSid = 0L;
            String name = "";
            String status = "";
            if (!StringUtil.isNullOrEmpty(params)) {
                Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
                instanceSid = Long.parseLong(StringUtil.nullToEmpty(map.get("instanceSid")));
                name = StringUtil.nullToEmpty(map.get("name"));
                status = StringUtil.nullToEmpty(map.get("status"));
            }

            Criteria criteria = new Criteria();
            criteria.put("instanceSid", instanceSid);
            criteria.put("status", status);

            // List<ServiceInstanceSpec> temp =
            // serviceInstanceSpecService.selectByParamsNewVersion(criteria);

            // if (temp != null && temp.size() > 0 && temp.get(0) != null
            // && temp.get(0).getVersion() != null) {
            // criteria.put("version", temp.get(0).getVersion() - 1);
            // } else {
            // criteria.put("version", null);
            // }
            Long
                    validVersion =
                    serviceInstanceSpecService.selectValidInstanceSpecByVersion(instanceSid);
            criteria.put("version", validVersion);
            List<ServiceInstanceSpec>
                    speclist =
                    serviceInstanceSpecService.selectByParamsLatest(criteria);
            List<ServiceInstanceSpec> specs = new ArrayList<ServiceInstanceSpec>();
            if (speclist != null && speclist.size() != 0) {
                if (!name.equals("STORAGE")) {
                    for (ServiceInstanceSpec sis : speclist) {
                        if (sis.getName().equals(name)) {
                            specs.add(sis);
                            break;
                        }
                    }
                } else {
                    ServiceInstanceSpec instanceSpec = new ServiceInstanceSpec();
                    Long storage = 0L;
                    for (ServiceInstanceSpec sis : speclist) {
                        if (sis.getName().equals("SYSTEM_DISK") || sis.getName()
                                .equals("DATA_DISK")) {
                            storage = storage + Long.parseLong(sis.getValue());
                            instanceSpec = sis;
                        }
                    }
                    instanceSpec.setValue(storage.toString());
                    specs.add(instanceSpec);
                }
            }
            String json = JsonUtil.toJson(specs);
            return Response.status(Status.OK).entity(json).build();
        } catch (Exception e) {
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            String resultJson = "";
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
        }
    }

    @Override
    public Response queryInstanceSpec(String params) {

        long instanceSid = 0L;
        String name = "";
        String status = "";
        String version = "";
        if (!StringUtil.isNullOrEmpty(params)) {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            instanceSid = Long.parseLong(StringUtil.nullToEmpty(map.get("instanceSid")));
            name = StringUtil.nullToEmpty(map.get("name"));
            status = StringUtil.nullToEmpty(map.get("status"));
            version = StringUtil.nullToEmpty(map.get("version"));

        }

        Criteria criteria = new Criteria();
        criteria.put("instanceSid", instanceSid);
        if (!StringUtil.isNullOrEmpty(name)) {
            criteria.put("name", name);
        }
        if (!StringUtil.isNullOrEmpty(status)) {
            criteria.put("status", status);
        }

        if (!StringUtil.isNullOrEmpty(version)) {
            criteria.put("version", version);
        }
        List<ServiceInstanceSpec>
                speclist =
                serviceInstanceSpecService.selectByParams(criteria);

        String json = JsonUtil.toJson(speclist);

        return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 查询出前台用户的ServiceInstance列表服务
     */
    @Override
    public Response getServiceInstanceList(ServiceInstance service) {

        // 查询退订中，已开通的服务实例
        String statusParams = "'" + WebConstants.ServiceInstanceStatus.CANCELING + "','"
                              + WebConstants.ServiceInstanceStatus.CHANGEING + "','"
                              + WebConstants.ServiceInstanceStatus.OPENED
                              + "'".replace(" ", "");

//		User user = AuthUtil.getAuthUser(); --------wsl
        Criteria criteria = new Criteria();
//		criteria.put("ownerId", user.getAccount());  -------------wsl
        criteria.put("statusParams", statusParams);
        criteria.put("serviceSid", service.getServiceSid());
        criteria.setOrderByClause("A.DREDGE_DATE desc , H.VM_NAME");
        List<ServiceInstance> list = this.serviceInstanceService.selectByParams(criteria);
        String json = JsonUtil.toJson(list);

        return Response.status(Status.OK).entity(json).build();
    }

    @WebMethod
    public Response getServiceInstRes(String params) {

        long instanceSid = 0L;
        if (!StringUtil.isNullOrEmpty(params)) {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            instanceSid = Long.parseLong(StringUtil.nullToEmpty(map.get("instanceSid")));
        }
        Criteria criteria = new Criteria();
        criteria.put("instanceSid", instanceSid);

        List<ServiceInstRes> speclist = serviceInstResService.selectByParams(criteria);
        String json = JsonUtil.toJson(speclist);

        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    public Response getLatestSpecs(String params) {
        try {
            long instanceSid = 0L;
            String name = "";
            if (!StringUtil.isNullOrEmpty(params)) {
                Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
                instanceSid = Long.parseLong(StringUtil.nullToEmpty(map.get("instanceSid")));
                name = StringUtil.nullToEmpty(map.get("name"));
            }
            List<ServiceInstanceSpec> speclist = new ArrayList<ServiceInstanceSpec>();
            // 获取实例规格的最大版本号
            Long
                    diskMaxVersion =
                    serviceInstanceSpecService.selectByInstanceSpecMaxVersion(instanceSid);
            // 获取当前版本中未生效Vm规格
            List<ServiceInstanceSpec> newDiskInstaceSpec = serviceInstanceSpecService
                    .selectByInstanceSpecByVersion(instanceSid, null, diskMaxVersion);
            // 判断最大版本的规格是否是有效的
            String purpose = "";

            for (ServiceInstanceSpec si : newDiskInstaceSpec) {
                if (si.getName().equals(name)) {
//					String value = ResDataUtils.getSpecValueFromSpecs("STORAGE_PURPOSE", newDiskInstaceSpec);  --wsl
                    String value = "";
                    if (value != null) {
                        if (value.equals(WebConstants.StoragePurpose.SYSTEM_DISK)) {
                            purpose = "系统盘";
                        } else if (value.equals(WebConstants.StoragePurpose.DATA_DISK)) {
                            purpose = "数据盘";
                        } else if (value.equals(WebConstants.StoragePurpose.SYSTEM_DATA_DISK)) {
                            purpose = "系统数据盘";
                        }
                    }
                    // 如果是有效的
                    if (!StringUtil.isNullOrEmpty(si.getStatus())
                        && si.getStatus().equals(WebConstants.SpecStatus.valid)) {
                        // 判断新值是不是0，是0表示已删除，不再页面上显示
                        if (si.getValue() != null && !si.getValue().equals("0")) {
                            si.setValueText(purpose);
                            speclist.add(si);
                        }
                    }
                    // 如果是无效，则去取最大的有效版本
                    else {
                        // 最大的有效规格版本号
                        Long
                                validDiskSpec =
                                serviceInstanceSpecService
                                        .selectValidInstanceSpecByVersion(instanceSid);
                        // 如果最大有效版本是null，说明是新加的磁盘，所以没有以往的版本
                        if (validDiskSpec != null) {
                            // 获取上一个版本有效的Vm规格
                            List<ServiceInstanceSpec>
                                    oldDiskInstaceSpec =
                                    serviceInstanceSpecService
                                            .selectByInstanceSpecByVersion(instanceSid, null,
                                                                           validDiskSpec);
                            for (ServiceInstanceSpec service : oldDiskInstaceSpec) {
                                if (service.getName().equals(name)) {
                                    // 判断新值是不是0，是0表示已删除，不再页面上显示
                                    if (service.getValue() != null) {
                                        service.setValueText(purpose);
                                        speclist.add(service);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            String json = JsonUtil.toJson(speclist);
            return Response.status(Status.OK).entity(json).build();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            String resultJson = "";
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
        }
    }

    @Override
    public Response getNetWorkInfo(String params) {
        try {
            long instanceSid = 0L;
            if (!StringUtil.isNullOrEmpty(params)) {
                Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
                instanceSid = Long.parseLong(StringUtil.nullToEmpty(map.get("instanceSid")));
            }
            Map<String, Object> result = new HashMap<String, Object>();
            ServiceInstance
                    serviceInstance =
                    this.serviceInstanceService.selectByPrimaryKey(instanceSid);
//			if (serviceInstance != null) {      ----wsl
//				Long bizSid = serviceInstance.getMgtObjSid();
//				Biz biz = businesstypeService.selectByPrimaryKey(bizSid);
//				Biz parentBiz = businesstypeService.selectByPrimaryKey(biz.getParentBizSid());
//				if (parentBiz.getName().equals("内容引入") || parentBiz.getName().equals("政企")) {
//					result.put("type", "out");
//				} else {
//					result.put("type", "all");
//				}
//				List<ServiceInstRes> serviceInsRes = serviceInstResService.selectInstanceReses(instanceSid);
//				if (serviceInsRes != null && serviceInsRes.size() != 0 && serviceInsRes.get(0) != null) {
//					for (ServiceInstRes instRes : serviceInsRes) {
//						if (WebConstants.ResourceType.RES_VM.equals(instRes.getResType())) {
//							String resId = instRes.getResId();
//							ResVm vmInfo = resVmService.getVmInfo(resId);
//							if (vmInfo != null) {
//								List<ResIp> outVlan = new ArrayList<ResIp>();
//								List<ResIp> innerVlan = new ArrayList<ResIp>();
//								List<ResIp> ips = vmInfo.getResIpList();
//								if (!CollectionUtils.isEmpty(ips)) {
//									for (ResIp ip : ips) {
//										if (ip != null && !StringUtil.isNullOrEmpty(ip.getNetworkType())) {
//											if (ip.getNetworkType().equals(WebConstants.ResNetworkType.PUBLIC)) {
//												outVlan.add(ip);
//											} else if (ip.getNetworkType().equals(WebConstants.ResNetworkType.PRIVATE)) {
//												innerVlan.add(ip);
//											}
//										}
//									}
//								}
//								result.put("outVlan", outVlan);
//								result.put("innerVlan", innerVlan);
//								result.put("vm", vmInfo);
//							}
//						}
//					}
//				}
//			}
            String json = JsonUtil.toJson(result);
            return Response.status(Status.OK).entity(json).build();
        } catch (Exception e) {
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            String resultJson = "";
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
        }
    }

    @Override
    public Response getInstanceCount(String params) {
        // 查询退订中，已开通的服务实例
        String statusParams = "'" + WebConstants.ServiceInstanceStatus.CANCELING + "','"
                              + WebConstants.ServiceInstanceStatus.CHANGEING + "','"
                              + WebConstants.ServiceInstanceStatus.OPENED
                              + "'".replace(" ", "");

//		User user = AuthUtil.getAuthUser();---------wsl
        Criteria criteria = new Criteria();
//		criteria.put("ownerId", user.getAccount()); ---------wsl
        criteria.put("statusParams", statusParams);
        List<ServiceInstance> list = serviceInstanceService.selectCountByParams(criteria);
        String json = JsonUtil.toJson(list);
        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    public Response modifyInstanceSpec(String params) {
        return null;
//		try {       ---------wsl
//			Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
//			this.serviceInstanceService.modifyInstanceSpec(map);
//			return Response.status(Status.OK)
//					.entity(JsonUtil.toJson(new RestResult(RestResult.SUCCESS, "保存订单实例规格项成功!"))).build();
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			String resultJson = JsonUtil.toJson(new RestResult(RestResult.FAILURE, "保存订单实例规格项失败!"));
//			return Response.status(Status.OK).entity(resultJson).build();
//		}
    }

    @Override
    public Response getInstanceCountByMgtObj(ServiceInstance instance) {
        String statusParams = "'" + WebConstants.ServiceInstanceStatus.CANCELING + "','"
                              + WebConstants.ServiceInstanceStatus.CHANGEING + "','"
                              + WebConstants.ServiceInstanceStatus.CANCELED
                              + "','" + WebConstants.ServiceInstanceStatus.OPENED + "'"
                                      .replace(" ", "");
        Criteria example = new Criteria();
        if (!StringUtil.isNullOrEmpty(instance.getMgtObjNameLike())) {
            example.put("mgtObjNameLike", instance.getMgtObjNameLike());
        }
        if (!StringUtil.isNullOrEmpty(instance.getMgtObjSid())) {
            example.put("mgtObjSid", instance.getMgtObjSid());
        }
        if (!StringUtil.isNullOrEmpty(instance.getOwnerId())) {
            example.put("ownerId", instance.getOwnerId());
        }
        example.put("statusParams", statusParams);
        example.put("isMgtBilling", "yes");
        List<ServiceInstance>
                list =
                this.serviceInstanceService.selectServiceCountByParams(example);
        String json = JsonUtil.toJson(list);
        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    public Response getServerCountByMgtObj(ServiceInstance instance) {
        String statusParams = "'" + WebConstants.ServiceInstanceStatus.CANCELING + "','"
                              + WebConstants.ServiceInstanceStatus.CHANGEING + "','"
                              + WebConstants.ServiceInstanceStatus.OPENED
                              + "'".replace(" ", "");
        Criteria example = new Criteria();
        if (!StringUtil.isNullOrEmpty(instance.getMgtObjNameLike())) {
            example.put("mgtObjNameLike", instance.getMgtObjNameLike());
        }
        if (!StringUtil.isNullOrEmpty(instance.getMgtObjSid())) {
            example.put("mgtObjSid", instance.getMgtObjSid());
        }
        if (!StringUtil.isNullOrEmpty(instance.getOwnerId())) {
            example.put("ownerId", instance.getOwnerId());
        }
//		if (!StringUtil.isNullOrEmpty(instance.getUserSid())) { -----wsl
//			User user = userService.selectByPrimaryKey(Long.parseLong(instance.getUserSid()), 0);
//			example.put("mgtObjSid", user.getMgtObjSid());
//		}
        example.put("statusParams", statusParams);
        example.put("isMgtBilling", "yes");
        List<ServiceInstance>
                list =
                this.serviceInstanceService.selectServiceCountByParams(example);
        String json = JsonUtil.toJson(list);
        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    @WebMethod
    @GET
    @Path("/mgtObj/find/{mgtObjSid}")
    public Response findMgtObjBySid(@PathParam("mgtObjSid") Long mgtObjSid) {
        List<ServiceInstance>
                list =
                this.serviceInstanceService.selectServiceInstanceByMgtObjSid(mgtObjSid);
        String json = JsonUtil.toJson(list);
        return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 判断是否存在对象存储
     */
    @Override
    @WebMethod
    @GET
    @Path("/judgement/exist/service/{serviceSid}")
    public Response judgementExistObjStorage(@PathParam("serviceSid") Long serviceSid) {
        return null;
//		User user = AuthUtil.getAuthUser();
//		String statusParams = "'" + WebConstants.ServiceInstanceStatus.CANCELING + "','"
//				+ WebConstants.ServiceInstanceStatus.CHANGEING + "','" + WebConstants.ServiceInstanceStatus.OPENED
//				+ "'".replace(" ", "");
//
//		Criteria criteria = new Criteria();
//		criteria.put("mgtObjSid", user.getMgtObjSid());
//		criteria.put("serviceSid", serviceSid);
//		criteria.put("statusParams", statusParams);
//		Map<String, String> map = new HashMap<String, String>();
//		List<ServiceInstance> list = this.serviceInstanceService.selectByParams(criteria);
//		if (0 < list.size()) {
//			// ＝1，用户所属企业申请了对象存储;≠1,用户申请了多个或者未申请对象存储
//			map.put("result", "true");
//		} else {
//			map.put("result", "false");
//		}
//		String json = JsonUtil.toJson(map);
//		return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 判断实例名称重复
     */
    @Override
    @WebMethod
    @POST
    @Path("/serviceInstance/checkInstanceName")
    public Response checkInstanceName(String params) {
        return null;
//
//		try {
//			Criteria example = new Criteria();
//			if (!StringUtil.isNullOrEmpty(params)) {
//				Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
//				if (!StringUtil.isNullOrEmpty(map.get("orderIdLike"))) {
//					example.put("orderIdLike", map.get("orderIdLike"));
//				}
//
//				if (!StringUtil.isNullOrEmpty(map.get("serviceSid"))) {
//					example.put("serviceSid", map.get("serviceSid"));
//				}
//
//				if (!StringUtil.isNullOrEmpty(map.get("dredgeFromDate"))) {
//					example.put("dredgeFromDate", map.get("dredgeFromDate"));
//				}
//
//				if (!StringUtil.isNullOrEmpty(map.get("dredgeToDate"))) {
//					example.put("dredgeToDate", map.get("dredgeToDate"));
//				}
//
//				if (!StringUtil.isNullOrEmpty(map.get("instanceNameLike"))) {
//					example.put("instanceNameLike1", map.get("instanceNameLike"));
//				}
//
//				if (!StringUtil.isNullOrEmpty(map.get("status"))) {
//					example.put("status", map.get("status"));
//				}
//				if (!StringUtil.isNullOrEmpty(map.get("expiringFromDate"))) {
//					example.put("expiringFromDate", map.get("expiringFromDate"));
//				}
//				if (!StringUtil.isNullOrEmpty(map.get("expiringToDate"))) {
//					example.put("expiringToDate", map.get("expiringToDate"));
//				}
//			}
//			int count = serviceInstanceService.countByParams(example);
//			String json = JsonUtil.toJson(count);
//			return Response.status(Status.OK).entity(json).build();
//		} catch (Exception e) {
//			e.printStackTrace();
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
//			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
//		}
    }

    /**
     * 查询企业下的对象存储
     */
    @Override
    @WebMethod
    @GET
    @Path("/find/objStorage")
    public Response findObjStorageByMgtObjSid() {
        return null;
//		User user = AuthUtil.getAuthUser();
//		String statusParams = "'" + WebConstants.ServiceInstanceStatus.CANCELING + "','"
//				+ WebConstants.ServiceInstanceStatus.CHANGEING + "','" + WebConstants.ServiceInstanceStatus.OPENED
//				+ "'".replace(" ", "");
//		Criteria criteria = new Criteria();
//		criteria.put("mgtObjSid", user.getMgtObjSid());
//		criteria.put("serviceSid", 104L);
//		criteria.put("statusParams", statusParams);
//		ServiceInstance list = this.serviceInstanceService.selectObjStorageByMgtobjSid(criteria);
//		String json = JsonUtil.toJson(list);
//		return Response.status(Status.OK).entity(json).build();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.hptsic.cloud.rest.service.ServiceInstancesRest#getBssProductList()
     */
    @Override
    @WebMethod
    @GET
    @Path("/getBssProductList/{serviceName}/code/{code}")
    public Response getBssProductList(@PathParam("serviceName") String serviceName,
                                      @PathParam("code") String code) {
        // TODO Auto-generated method stub
//		String json = productService.getProductListRe();
//		return Response.status(Status.OK).entity(json).build();
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.hptsic.cloud.rest.service.ServiceInstancesRest#getBssProductPrice
     * (java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    @WebMethod
    @POST
    @Path("/getBssProductPrice")
    public Response getBssProductPrice(String params) {
        return null;
//		// TODO Auto-generated method stub
//		String json = productService.getProductPrice(params);
//		Map<String, Object> map;
//		try {
//			map = JsonUtil.fromJson(params, Map.class);
//
//			List<ProductResourceInfoVO> list = new ArrayList<ProductResourceInfoVO>();
//			ProductVO proVo = new ProductVO();
//			if (!StringUtil.isNullOrEmpty(map.get("id"))) {
//				String obj = String.valueOf(map.get("id"));
//				Long id = Long.valueOf(obj);
//				proVo.setId(id);
//			}
//			if (!StringUtil.isNullOrEmpty(map.get("code"))) {
//				proVo.setCode((String) map.get("code"));
//			}
//			if (!StringUtil.isNullOrEmpty(map.get("productTypeCode"))) {
//				proVo.setProductTypeCode((String) map.get("code"));
//			}
//			if (!StringUtil.isNullOrEmpty(map.get("productResourceInfos"))) {
//				List<Map> childlist = (List<Map>) map.get("productResourceInfos");
//				if (childlist != null && childlist.size() > 0) {
//					for (Map cmap : childlist) {
//						UnitResourceVO unitVo = new UnitResourceVO();
//						ProductResourceInfoVO proReVo = new ProductResourceInfoVO();
//						unitVo.setUnitResourceCode((String) cmap.get("unitResourceCode"));
//						proReVo.setResourcevalue((String) cmap.get("resourcevalue"));
//						proReVo.setUnitResource(unitVo);
//						list.add(proReVo);
//					}
//				}
//			}
//			proVo.setProductResourceInfos(list);
//			json = productService.getProductPrice(params);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return Response.status(Status.OK).entity(json).build();
    }

    @WebMethod
    @GET
    @Path("/getContractList/{name}")
    public Response getContractList(@PathParam("name") String name) {
//		String json = productService.getContractList(name);      ----wsl
//		return Response.status(Status.OK).entity(json).build();
        return null;//wsl
    }

    @Override
    @WebMethod
    @POST
    @Path("/getInstanceForFloatingip")
    public Response getInstanceForFloatingip(String params) {
//		User user = AuthUtil.getAuthUser();    //wsl
//		List<Role> roles = user.getRoles();
//		boolean roleFlag = false;
//		String mgtObjRole = PropertiesUtil.getProperty("mgtobj.manager.sid");
//		if (!CollectionUtils.isEmpty(roles)) {
//			for (Role role : roles) {
//				if (role.getRoleSid().toString().equals(mgtObjRole)) {
//					roleFlag = true;
//					break;
//				}
//			}
//		}
        // 查询退订中、已开通
        String statusParams = "'" + WebConstants.ServiceInstanceStatus.CANCELING + "','"
                              + WebConstants.ServiceInstanceStatus.CHANGEING + "','"
                              + WebConstants.ServiceInstanceStatus.OPENED
                              + "'".replace(" ", "");
        try {
            Criteria example = new Criteria();
            if (!StringUtil.isNullOrEmpty(params)) {
                Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
                example.setCondition(map);
            }
//			if (!roleFlag) {     //wsl
//				example.put("ownerId", user.getAccount());
//			}
//			example.put("mgtObjSid", user.getMgtObjSid());
            example.put("statusParams", statusParams);
            example.setOrderByClause("A.DREDGE_DATE desc , H.VM_NAME");
            List<ServiceInstance>
                    serviceInstanceList =
                    this.serviceInstanceService.selectByParams(example);

            List<ServiceInstance> instanceResult = new ArrayList<ServiceInstance>();

            if (!CollectionUtils.isEmpty(serviceInstanceList)) {
                for (ServiceInstance serviceInstance : serviceInstanceList) {
                    if (serviceInstance != null && !StringUtil
                            .isNullOrEmpty(serviceInstance.getResSid())) {
                        example = new Criteria();
                        example.put("resVmSid", serviceInstance.getResSid());
//						example.put("networkType", WebConstants.ResNetworkType.USER_DEFINED);------wsl

//						List<ResVmNetwork> networks = this.resVmNetworkService.selectNetsByParams(example);
//						if(!CollectionUtils.isEmpty(networks)){
//							instanceResult.add(serviceInstance);
//						} //wsl
                    }
                }
            }

            String json = JsonUtil.toJson(instanceResult);
            return Response.status(Status.OK).entity(json).build();
        } catch (Exception e) {
            e.printStackTrace();
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
//			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
            return null;//wsl
        }
    }

    @Override
    public Response getLastChangeLog(Long serviceInstanceSid) {
        ServiceInstanceChangeLogSpec<ResVmInst>
                openSpec =
                instanceChangeLogService.getChangeInfo(serviceInstanceSid,
                                                       new TypeReference<ServiceInstanceChangeLogSpec<ResVmInst>>() {
                                                       });
        String json = JsonUtil.toJson(openSpec);
        return Response.status(Status.OK).entity(json).build();
    }


}
