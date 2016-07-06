/**
 *
 */
package com.h3c.idcloud.core.rest.product.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.product.ServiceCatalog;
import com.h3c.idcloud.core.pojo.dto.product.ServiceConfig;
import com.h3c.idcloud.core.pojo.dto.product.ServiceDefine;
import com.h3c.idcloud.core.pojo.dto.product.ServiceOperation;
import com.h3c.idcloud.core.pojo.dto.product.ServicePerf;
import com.h3c.idcloud.core.pojo.dto.product.ServiceSpec;
import com.h3c.idcloud.core.pojo.dto.product.ServiceTemplate;
import com.h3c.idcloud.core.pojo.dto.product.ServiceTemplateSpec;
import com.h3c.idcloud.core.pojo.vo.common.CommonServiceCode;
import com.h3c.idcloud.core.pojo.vo.product.ServiceCatalogVo;
import com.h3c.idcloud.core.rest.product.ServicesRest;
import com.h3c.idcloud.core.service.product.api.ServiceCatalogService;
import com.h3c.idcloud.core.service.product.api.ServiceConfigService;
import com.h3c.idcloud.core.service.product.api.ServiceDefineService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.product.api.ServiceOperationService;
import com.h3c.idcloud.core.service.product.api.ServicePerfService;
import com.h3c.idcloud.core.service.product.api.ServiceSpecService;
import com.h3c.idcloud.core.service.product.api.ServiceTemplateService;
import com.h3c.idcloud.core.service.product.api.ServiceTemplateSpecService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.InterfaceResult;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.RequestContextUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * @author zharong
 */
@Component
public class ServicesRestImpl implements ServicesRest {

    /**
     * 服务定义Service
     */
    @Reference(version = "1.0.0")
    ServiceDefineService serviceDefineService;

    /**
     * 服务实例定义Service
     */
    @Reference(version = "1.0.0")
    ServiceInstanceService serviceInstanceService;

    /**
     * 服务配置Service
     */
    @Reference(version = "1.0.0")
    ServiceConfigService serviceConfig;

    /**
     * 服务规格Service
     */
    @Reference(version = "1.0.0")
    ServiceSpecService serviceSpec;

    /**
     * 服务性能Service
     */
    @Reference(version = "1.0.0")
    ServicePerfService servicePerf;

    /**
     * 服务操作Service
     */
    @Reference(version = "1.0.0")
    ServiceOperationService serviceOperation;

    /**
     * 服务模板Service
     */
    @Reference(version = "1.0.0")
    ServiceTemplateService serviceTemplateService;

    /**
     * 服务模板规格Service
     */
    @Reference(version = "1.0.0")
    ServiceTemplateSpecService serviceTemplateSpecService;

    /**
     * 服务类型Service
     */
    @Reference(version = "1.0.0")
    ServiceCatalogService serviceCatalogService;

    /**
     * 取得所有服务
     */
    @Override
    @WebMethod
    @POST
    @Path("/servicelist")
    public Response getServiceList() {
        Criteria example = new Criteria();
        example.put("serviceStatus", WebConstants.ServiceStatus.RELEASED);
        example.setOrderByClause("A.SERVICE_SID");
        List<ServiceDefine> list = serviceDefineService.selectServiceByCatalogSidList(example);
        HashMap<String, Object> serviceListMap = new HashMap<String, Object>();
        HashMap<Long, Object> serviceMap = new HashMap<Long, Object>();
        List<Map<Long, Object>> serviceList = new ArrayList<Map<Long, Object>>();
        for (ServiceDefine service : list) {
            Map<String, Object> serviceDetailMap = new HashMap<String, Object>();
            serviceDetailMap.put("serviceSid", service.getServiceSid());
            serviceDetailMap.put("serviceName", service.getServiceName());
            serviceDetailMap.put("serviceType", service.getServiceType());
            serviceDetailMap.put("serviceStatus", service.getServiceStatus());
            serviceDetailMap.put("pricingPlanSid", service.getPricingPlanSid());
            serviceDetailMap.put("contractId", service.getContractId());
            serviceDetailMap.put("ownerId", service.getOwnerId());
            serviceDetailMap.put("ownerName", service.getOwnerName());
            serviceDetailMap.put("parentCatalogSid", service.getParentCatalogSid());
            serviceDetailMap.put("parentCatalogName", service.getParentCatalogName());
            serviceDetailMap.put("tanentId", service.getTanentId());
            serviceDetailMap.put("sImagePath1", service.getsImagePath1());
            serviceDetailMap.put("sImagePath2", service.getsImagePath2());
            serviceDetailMap.put("bImagePath", service.getbImagePath());
            serviceDetailMap.put("repeatOrder", service.getRepeatOrder());
            serviceDetailMap.put("helpPath", service.getHelpPath());
            serviceDetailMap.put("softwarePackagePath", service.getSoftwarePackagePath());
            serviceDetailMap.put("expiringDate", service.getExpiringDate());
            serviceDetailMap.put("operationHandler", service.getOperationHandler());

            // 添加服务配置信息
            List<ServiceConfig> config = serviceConfig.selectByServiceSid(service.getServiceSid());
            serviceDetailMap.put("serviceConfig", config);

            // 添加服务规格信息
            List<ServiceSpec> spec = serviceSpec.selectByServiceSid(service.getServiceSid());
            serviceDetailMap.put("serviceSpec", spec);

            // 添加服务性能指标信息
            List<ServicePerf> perf = servicePerf.selectByServiceSid(service.getServiceSid());
            serviceDetailMap.put("servicePerf", perf);

            // 添加服务操作项信息
            List<ServiceOperation> operation = serviceOperation.selectByServiceSid(service.getServiceSid());
            serviceDetailMap.put("serviceOperation", operation);
            serviceMap.put(service.getServiceSid(), serviceDetailMap);
            serviceList.add(serviceMap);
        }
        serviceListMap.put("servicesList", serviceList);
        String json = null;
        json = JsonUtil.toJson(serviceListMap);
        return Response.ok(json).build();
//		return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 取得单个服务以及其详细信息
     */
    @Override
    @WebMethod
    @GET
    @Path("/serviceDetail/{serviceSid}")
    public Response getServiceDetail(@PathParam("serviceSid") String serviceSid) {
        ServiceDefine service = serviceDefineService.selectByPrimaryKey(Long.parseLong(serviceSid));
        Map<String, Object> serviceDetailMap = new HashMap<String, Object>();
        serviceDetailMap.put("serviceSid", service.getServiceSid());
        serviceDetailMap.put("serviceName", service.getServiceName());
        serviceDetailMap.put("serviceType", service.getServiceType());
        serviceDetailMap.put("serviceStatus", service.getServiceStatus());
        serviceDetailMap.put("pricingPlanSid", service.getPricingPlanSid());
        serviceDetailMap.put("contractId", service.getContractId());
        serviceDetailMap.put("ownerId", service.getOwnerId());
        serviceDetailMap.put("ownerName", service.getOwnerName());
        serviceDetailMap.put("parentCatalogSid", service.getParentCatalogSid());
        serviceDetailMap.put("parentCatalogName", service.getParentCatalogName());
        serviceDetailMap.put("tanentId", service.getTanentId());
        serviceDetailMap.put("sImagePath1", service.getsImagePath1());
        serviceDetailMap.put("sImagePath2", service.getsImagePath2());
        serviceDetailMap.put("bImagePath", service.getbImagePath());
        serviceDetailMap.put("repeatOrder", service.getRepeatOrder());
        serviceDetailMap.put("helpPath", service.getHelpPath());
        serviceDetailMap.put("softwarePackagePath", service.getSoftwarePackagePath());
        serviceDetailMap.put("expiringDate", service.getExpiringDate());
        serviceDetailMap.put("operationHandler", service.getOperationHandler());

        // 添加服务配置信息
        List<ServiceConfig> config = serviceConfig.selectByServiceSid(service.getServiceSid());
        serviceDetailMap.put("serviceConfig", config);

        // 添加服务规格信息
        List<ServiceSpec> spec = serviceSpec.selectByServiceSid(service.getServiceSid());
        serviceDetailMap.put("serviceSpec", spec);

        // 添加服务性能指标信息
        List<ServicePerf> perf = servicePerf.selectByServiceSid(service.getServiceSid());
        serviceDetailMap.put("servicePerf", perf);

        // 添加服务操作项信息
        List<ServiceOperation> operation = serviceOperation.selectByServiceSid(service.getServiceSid());
        serviceDetailMap.put("serviceOperation", operation);
        String json = null;
        json = JsonUtil.toJson(serviceDetailMap);
        return Response.ok(json).build();
//		return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 获取一个服务下服务模板的详细信息
     */
    @Override
    @WebMethod
    @GET
    @Path("/templatelist/{serviceSid}")
    public Response getServiceTemplate(@PathParam("serviceSid") String serviceSid) {
        Criteria criteria = new Criteria();
        criteria.put("serviceSid", serviceSid);
        criteria.put("templateStatus", WebConstants.ServiceTemplateStatus.RELEASED);
        List<ServiceTemplate> list = serviceTemplateService.selectByParams(criteria);

        for (ServiceTemplate st : list) {
            List<ServiceTemplateSpec> speList = serviceTemplateSpecService.selectByTemplateSid(st.getTemplateSid());
            st.setSpecifications(speList);
        }

        String json = JsonUtil.toJson(list);
        return Response.ok(json).build();
//		return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 取得所有服务和服务类型组成Tree
     */
    @Override
    @WebMethod
    @POST
    @Path("/serviceTree")
    public Response getServiceAndCatalogList() {
        Criteria example = new Criteria();
        example.put("serviceStatus", WebConstants.ServiceStatus.RELEASED);
        List<ServiceDefine> servicelist = serviceDefineService.selectServiceByCatalogSidList(example);
        Criteria criteria = new Criteria();
//		criteria.put("status", WebConstants.SERVICE_STATUS.RELEASED);
        List<ServiceCatalog> serviceCatalogList = serviceCatalogService.selectByParams(criteria);
        for (ServiceCatalog sc : serviceCatalogList) {
            List<ServiceDefine> serviceList = new ArrayList<ServiceDefine>();
            for (ServiceDefine service : servicelist) {
                if (sc.getCatalogSid().equals(service.getParentCatalogSid())) {
                    serviceList.add(service);
                }
            }
            sc.setServiceDefineList(serviceList);
        }
        String json = null;
        json = JsonUtil.toJson(new RestResult(serviceCatalogList));
        return Response.ok(json).build();
//		return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 获取单一服务
     */
    @Override
    @WebMethod
    @GET
    @Path("/{serviceSid}")
    public Response getService(@PathParam("serviceSid") String serviceSid) {
        ServiceDefine service = serviceDefineService.selectByPrimaryKey(Long.parseLong(serviceSid));
        String json = JsonUtil.toJson(service);
        return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 获取单一服务操作
     */
    @Override
    @WebMethod
    @GET
    @Path("/getServiceOperation")
    public Response getServiceOperation(@QueryParam("serviceSid") String serviceSid) {
        Criteria criteria = new Criteria();
        criteria.put("serviceSid", serviceSid);
        List<ServiceOperation> list = this.serviceOperation.selectByParams(criteria);
        String json = JsonUtil.toJson(list);
        return Response.ok(json).build();
//		return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 根据角色获取该角色可订购的service服务
     */
    @Override
    @WebMethod
    @POST
    @Path("/findAll")
    public Response findAllServiceByRole(String params) {
        try {
            Criteria example = new Criteria();
            if (!StringUtil.isNullOrEmpty(params)) {
                Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
                example.setCondition(map);
            }
            List<ServiceDefine> list = serviceDefineService.selectByParams(example);
            String json = JsonUtil.toJson(list);
            return Response.ok(json).build();
//			return Response.status(Status.OK).entity(json).build();
        } catch (Exception e) {
            e.printStackTrace();
            String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.ok(resultJson).build();
//			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
        }
    }

    @Override
    public Response findServiceDefines(String params) {
        try {
            Criteria example = new Criteria();
            if (!StringUtil.isNullOrEmpty(params)) {
                Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
                example.setCondition(map);
            }
            List<CommonServiceCode> list = this.serviceDefineService.findCommonServiceCodes(example);
            String json = JsonUtil.toJson(list);
            return Response.ok(new RestResult(json)).build();
        } catch (Exception e) {
            e.printStackTrace();
            String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.ok(resultJson).build();
        }
    }

    /**
     * 查询所有服务
     */
    @Override
    public Response findAllService() {
        Criteria example = new Criteria();
        List<ServiceDefine> list = this.serviceDefineService.selectByParams(example);
        Map<String, Object> data = new HashMap<String, Object>();
        String json = JsonUtil.toJson(list);
//		String json = JsonUtil.toJson(list);
        return Response.ok(json).build();
//		return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 根据条件获取initServiceMenuMgtTree列表服务
     */
    @Override
    public Response initServiceMenuMgtTree() {
        try {
            Criteria example = new Criteria();
            List<ServiceCatalog> list = serviceCatalogService.selectByParams(example);
            Map<String, Object> paraMap = new HashMap<String, Object>();
            Map<String, Object> itemMap = new HashMap<String, Object>();
            List<Map<String, Object>> itemMaps = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> paraMaps = new ArrayList<Map<String, Object>>();

            for (ServiceCatalog serviceCatalog : list) {
                if (serviceCatalog.getParentCatalogSid() == null) {
                    paraMap.put("id", serviceCatalog.getCatalogSid());
                    paraMap.put("catalogParentName", serviceCatalog.getCatalogParentName());
                    paraMap.put("catalogName", serviceCatalog.getCatalogName());
                    paraMap.put("description", serviceCatalog.getDescription());
                    paraMap.put("imagePath", serviceCatalog.getImagePath());
                    paraMap.put("statusName", serviceCatalog.getStatusName());
                    paraMap.put("label", serviceCatalog.getCatalogName());
                    paraMap.put("expanded", true);
                } else {
                    itemMap = new HashMap<String, Object>();
                    itemMap.put("id", serviceCatalog.getCatalogSid());
                    itemMap.put("catalogParentName", serviceCatalog.getCatalogParentName());
                    itemMap.put("catalogName", serviceCatalog.getCatalogName());
                    itemMap.put("description", serviceCatalog.getDescription());
                    itemMap.put("imagePath", serviceCatalog.getImagePath());
                    itemMap.put("statusName", serviceCatalog.getStatusName());
                    itemMap.put("label", serviceCatalog.getCatalogName());
                    itemMaps.add(itemMap);
                }
            }
            paraMap.put("items", itemMaps);
            paraMaps.add(paraMap);
            String json = JsonUtil.toJson(paraMaps);
            return Response.ok(json).build();
//			return Response.status(Status.OK).entity(json).build();
        } catch (Exception e) {
            e.printStackTrace();
            String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.ok(resultJson).build();
//			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
        }
    }

    /**
     * 查询目录列表
     */
    @Override
    public Response findServiceCatalog(ServiceCatalogVo serviceCatalog) {

        Criteria example = new Criteria();
//		if (tenant != null) {
//			if (!StringUtil.isNullOrEmpty(tenant.getTenantName())) {
//				example.put("tenantNameLike", tenant.getTenantName());
//			}
//
//			if (!StringUtil.isNullOrEmpty(tenant.getTenantType())) {
//				example.put("tenantType", tenant.getTenantType());
//			}
//
//			if (!StringUtil.isNullOrEmpty(tenant.getBusinessType())) {
//				example.put("businessType", tenant.getBusinessType());
//			}
//
//			if (!StringUtil.isNullOrEmpty(tenant.getStatus())) {
//				example.put("status", tenant.getStatus());
//			}
//		}
        List<ServiceCatalog> list = this.serviceCatalogService.selectByParams(example);

        for (ServiceCatalog catalog : list) {
            catalog.setValue(catalog.getCatalogSid());
        }
        return Response.ok(new RestResult(list)).build();
//		return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 查询单一服务目录
     */
    @Override
    public Response findServiceCatalogBySid(String params) {
        try {
            long catalogSid = 0L;
            if (!StringUtil.isNullOrEmpty(params)) {
                Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
                if (map != null) {
                    catalogSid = Long.parseLong(StringUtil.nullToEmpty(map.get("catalogSid")));
                }
            }
            ServiceCatalog serviceCatalog = serviceCatalogService.selectByPrimaryKey(catalogSid);
            return Response.ok(new RestResult(serviceCatalog)).build();
        } catch (Exception e) {
            e.printStackTrace();
            String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.ok(new RestResult(resultJson)).build();
        }
    }

    /**
     * 查询服务定义列表
     */
    @Override
    public Response findServiceDefineByParams(String params) {
        try {
            Criteria example = new Criteria();
            if (!StringUtil.isNullOrEmpty(params)) {
                Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
                example.setCondition(map);
            }
            List<ServiceDefine> list = serviceDefineService.selectServiceByCatalogSidList(example);
            return Response.ok(new RestResult(list)).build();
        } catch (Exception e) {
            e.printStackTrace();
            String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.ok(new RestResult(resultJson)).build();
        }
    }

    /**
     * 查询单个服务定义
     */
    @Override
    public Response findServiceDefineBySid(String params) {
        try {
            long serviceSid = 0L;
            if (!StringUtil.isNullOrEmpty(params)) {
                Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
                serviceSid = Long.parseLong(StringUtil.nullToEmpty(map.get("serviceSid")));
            }

            ServiceDefine service = serviceDefineService.selectByPrimaryKey(serviceSid);

            // 添加服务配置信息
            List<ServiceConfig> config = serviceConfig.selectByServiceSid(serviceSid);
            service.setServiceConfig(config);

            // 添加服务规格信息
            List<ServiceSpec> spec = serviceSpec.selectByServiceSid(serviceSid);
            service.setServiceSpec(spec);

            // 添加服务性能指标信息
            List<ServicePerf> perf = servicePerf.selectByServiceSid(serviceSid);
            service.setServicePerf(perf);

            // 添加服务操作项信息
            List<ServiceOperation> operation = serviceOperation.selectByServiceSid(serviceSid);
            service.setServiceOperation(operation);

            return Response.status(Status.OK).entity(new RestResult(service)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok(new RestResult(RestResult.Status.FAILURE)).build();
        }
    }

    /**
     * 查询服务模板列表
     */
    @Override
    public Response findServiceTempByServiceSid(String params) {
        try {
            long serviceSid = 0L;
            if (!StringUtil.isNullOrEmpty(params)) {
                Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
                serviceSid = Long.parseLong(StringUtil.nullToEmpty(map.get("serviceSid")));
            }
            List<ServiceTemplate> templates = serviceTemplateService.selectByServiceSid(serviceSid);
            return Response.ok(new RestResult(templates)).build();
        } catch (Exception e) {
            e.printStackTrace();
            String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.ok(new RestResult(RestResult.Status.FAILURE)).build();
        }
    }

    /**
     * 查询服务模板列表
     */
    @Override
    public Response findServiceTempSpecByParams(String params) {
        try {
            Criteria tempSpec = new Criteria();
            Criteria example2 = new Criteria();
            List<ServiceSpec> specs = new ArrayList<ServiceSpec>();
            List<ServiceSpec> specs1 = new ArrayList<ServiceSpec>();

            if (!StringUtil.isNullOrEmpty(params)) {
                Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
                if (StringUtil.isNullOrEmpty(map.get("templateSid"))) {
                    //新增
                    specs = serviceSpec.selectByServiceSid(Long.parseLong(StringUtil.nullToEmpty(map.get("serviceSid"))));
                } else {
                    //编辑
                    specs = serviceSpec.selectByServiceSid(Long.parseLong(StringUtil.nullToEmpty(map.get("serviceSid"))));

                    //查出服务规格中存在服务模板规格中不存在的数据，这种数据可以选择打钩保存，即其他数据要打上勾
                    example2.put("serviceSid", map.get("serviceSid"));
                    example2.put("templateSid", map.get("templateSid"));
                    specs1 = serviceSpec.selectTemplateSpec(example2);

                    //找出没有被删选到模板中的
                    for (ServiceSpec serviceSpec : specs) {
                        //假设每条数据当一家选择到模板中去的数据打上钩
                        serviceSpec.setCheckbox(true);
                        for (ServiceSpec serviceSpec1 : specs1) {
                            if (serviceSpec1.getSpecSid().equals(serviceSpec.getSpecSid())) {
                                //找出没有选择到模板中去的数据去调勾
                                serviceSpec.setCheckbox(false);
                            }
                        }
                    }

                    //找出被删选到模板中的value值
                    for (ServiceSpec serviceSpec : specs) {
                        if (serviceSpec.getCheckbox() == true) {
                            tempSpec.put("templateSid", map.get("templateSid"));
                            List<ServiceTemplateSpec> serviceTemplateSpecsList = serviceTemplateSpecService.selectByParams(tempSpec);
                            for (ServiceTemplateSpec serviceTemplateSpec : serviceTemplateSpecsList) {
                                if (serviceTemplateSpec.getName().equals(serviceSpec.getName())) {
                                    serviceSpec.setValue(serviceTemplateSpec.getValue());
                                }
                            }

                        }

                    }
                }
            }

            return Response.ok(new RestResult(specs)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok(new RestResult(RestResult.Status.FAILURE)).build();
        }
    }

    /**
     * 新增服务模板
     */
    @Override
    public Response insertServiceTemplate(ServiceTemplate template) {
        String json = "";
        try {

            boolean result = this.serviceTemplateService.insertPlatformServiceTemp(template);
            if (result) {
                json = InterfaceResult.setResult(WebConstants.ResultStatus.SUCCESS, null);
                return Response.ok(json).build();
            } else {
                json = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
                return Response.ok(json).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
//			 String resultJson = "";
            return Response.ok(resultJson).build();
//			 return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
        }

    }

    /**
     * 删除服务模板
     */
    @Override
    public Response deleteTemplate(@QueryParam("templateSid") Long templateSid) {
        String json = "";
        // 删除模板
        int result = serviceTemplateService.deleteByPrimaryKey(templateSid);
        if (result == 1) {
            // 删除规格
            serviceTemplateSpecService.deleteByTemplateSid(templateSid);
            json = InterfaceResult.setResult(WebConstants.ResultStatus.SUCCESS, null);
            return Response.ok(json).build();
//			return Response.status(Status.OK).entity(json).build();
        } else {
            json = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.ok(json).build();
//			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(json).build();
        }
    }

    /**
     * 更新服务模板
     */
    @Override
    public Response updateServiceTemplate(ServiceTemplate template) {
        String json = "";
        try {
            boolean result = this.serviceTemplateService.updatePlatformServiceTemp(template);
            if (result) {
                json = InterfaceResult.setResult(WebConstants.ResultStatus.SUCCESS, null);
                return Response.ok(json).build();
//				return Response.status(Status.OK).entity(json).build();
            } else {
                json = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
                return Response.ok(json).build();
//				return Response.status(Status.INTERNAL_SERVER_ERROR).entity(json).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.ok(resultJson).build();
//			 return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
        }

    }

    /**
     * 新增服务
     */
    @Override
    public Response insertService(ServiceDefine service,HttpServletRequest servletRequest) {
        String json = "";
        try {
            AuthUser authUser = RequestContextUtil.getAuthUserInfo(servletRequest);
            WebUtil.prepareInsertParams(service,authUser);
            boolean result = this.serviceDefineService.insertPlatformService(service);
            if (result) {
                return Response.ok(new RestResult(true)).build();
            } else {
                return Response.ok(new RestResult(RestResult.Status.FAILURE)).build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok(new RestResult(RestResult.Status.FAILURE)).build();
        }

    }

    /**
     * 更新服务
     */
    @Override
    public Response updateService(ServiceDefine service) {
        String json = "";
        try {

            boolean result = this.serviceDefineService.updatePlatformService(service);
            if (result) {
//				json = JsonUtil.toJson(new RestResult(RestResult.SUCCESS, WebUtil
//						.getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS), null));   -------wsl
                return Response.status(Status.OK).entity(json).build();
            } else {
//				json = JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil     ------wsl
//						.getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE), null));
                return Response.status(Status.OK).entity(json).build();
            }

        } catch (Exception e) {
            e.printStackTrace();
//			String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            String resultJson = "";
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
        }

    }

    /**
     * 操作服务
     */
    @Override
    public Response operationService(@QueryParam("serviceSid") Long serviceSid, @QueryParam("action") String action) {
        String returnJson = "";

        // 操作服务
        ServiceDefine service = new ServiceDefine();
        service.setServiceSid(serviceSid);
        service.setServiceStatus(action);
//		WebUtil.prepareUpdateParams(service);        ----------wsl
        int result = this.serviceDefineService.updateByPrimaryKeySelective(service);
        if (result == 1) {
//			returnJson = InterfaceResult.setResult(WebConstants.ResultStatus.SUCCESS, null);--------wsl
            return Response.status(Status.OK).entity(returnJson).build();
        } else {
//			returnJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);--------wsl
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(returnJson).build();
        }

    }

    /**
     * 后台新增服务目录
     */
    @Override
    public Response addServiceCatalog(ServiceCatalog serviceCatalog) {
        String returnJson;

        WebUtil.prepareInsertParams(serviceCatalog);
        int result = this.serviceCatalogService.insertSelective(serviceCatalog);
        if (1 == result) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS), null));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE), null));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    /**
     * 后台更新服务目录
     */
    @Override
    public Response updateServiceCatalog(ServiceCatalog serviceCatalog) {
        String returnJson;

        WebUtil.prepareInsertParams(serviceCatalog);
        int result = this.serviceCatalogService.updateByPrimaryKeySelective(serviceCatalog);
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
     * 删除服务目录
     */
    @Override
    public Response deleteServiceCatalog(@PathParam("catalogSid") Long catalogSid) {
        String returnJson;

        int result = this.serviceCatalogService.deleteByPrimaryKey(catalogSid);
        if (1 == result) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS)));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_DELETE_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    /**
     * 删除租户
     */
    @Override
    public Response deleteCatalog(@PathParam("catalogSid") Long catalogSid) {
        String returnJson;

        int result = this.serviceCatalogService.deleteByPrimaryKey(catalogSid);
        // sunyu update for #131
        if (1 == result) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS)));
        } else if (2 == result) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(
                    WebConstants.MsgCd.ERROR_DATA_RELATION, new Object[]{"该服务目录下下还存在目录"})));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_DELETE_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }
}
