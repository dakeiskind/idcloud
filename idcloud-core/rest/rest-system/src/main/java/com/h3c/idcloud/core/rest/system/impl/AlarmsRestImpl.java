package com.h3c.idcloud.core.rest.system.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.system.AlarmData;
import com.h3c.idcloud.core.pojo.dto.system.AlarmDateResponse;
import com.h3c.idcloud.core.pojo.dto.system.AlarmRule;
import com.h3c.idcloud.core.pojo.dto.system.AlarmVmDetail;
import com.h3c.idcloud.core.rest.system.AlarmsRest;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.res.api.ResVeService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.core.service.system.api.AlarmDataService;
import com.h3c.idcloud.core.service.system.api.AlarmRuleService;
import com.h3c.idcloud.core.service.system.api.MailNotificationsService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.InterfaceResult;
import com.h3c.idcloud.infrastructure.common.pojo.RESTHttpResponse;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.pojo.User;
import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import com.h3c.idcloud.infrastructure.common.util.JSONNullToBlankSerializer;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.RSETClientUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import com.hptsic.cloud.monitor.pojo.AlarmAction;
import com.hptsic.cloud.monitor.pojo.AlarmRoleUpate;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * @author gujie
 */
@Component
public class AlarmsRestImpl implements AlarmsRest {

    /**
     * 常量
     */
    private static final String MONITOR_INTERFACE_URL_KEY = "tk.interface.url";

    /**
     * 日志Log
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmsRestImpl.class);

    /**
     * 获取MONITOR_INTERFACE_URL_KEY
     */
    private static String URLPREFIX = PropertiesUtil.getProperty(MONITOR_INTERFACE_URL_KEY);

    /**
     * 告警Service
     */
    @Reference(version = "1.0.0")
    private AlarmDataService alarmDataService;

    /**
     * 告警规则Service
     */
    @Reference(version = "1.0.0")
    private AlarmRuleService alarmRuleService;

    /**
     * serviceInstanceService
     */
    @Reference(version = "1.0.0")
    private ServiceInstanceService serviceInstanceService;

    /**
     * resVmService
     */
    @Reference(version = "1.0.0")
    private ResVmService resVmService;

    /**
     * resVeService
     */
    @Reference(version = "1.0.0")
    private ResVeService resVeService;

//    /** alarmHandler */
//    @Reference(version = "1.0.0")
//    private AlarmHandler alarmHandler;

    /**
     * mailNotificationsService
     */
    @Reference(version = "1.0.0")
    private MailNotificationsService mailNotificationsService;

    @Override
    public Response findAlarm(AlarmData alarm) {
        Criteria example = new Criteria();

        if (!StringUtil.isNullOrEmpty(alarm.getAlarmLevel())) {
            example.put("alarmLevel", alarm.getAlarmLevel());
        }
        if (!StringUtil.isNullOrEmpty(alarm.getAlarmType())) {
            example.put("alarmType", alarm.getAlarmType());
        }
        if (!StringUtil.isNullOrEmpty(alarm.getStatus())) {
            example.put("status", alarm.getStatus());
        }
        if (!StringUtil.isNullOrEmpty(alarm.getAlarmTimeFromDate())) {
            example.put("alarmTimeFromDate", alarm.getAlarmTimeFromDate());
        }
        if (!StringUtil.isNullOrEmpty(alarm.getAlarmTimeToDate())) {
            example.put("alarmTimeToDate", alarm.getAlarmTimeToDate());
        }

        List<AlarmData> list = this.alarmDataService.selectByParams(example);
        return Response.ok(JsonUtil.toJson(new RestResult(list))).build();
    }

    @Override
    public Response addAlarm(AlarmData alarm) {
        String returnJson;
        WebUtil.prepareInsertParams(alarm);
        if (!StringUtil.isNullOrEmpty(alarm.getAlarmTimeToDate())) {
            alarm.setAlarmTime(
                    StringUtil.strToDate(alarm.getAlarmTimeToDate(), StringUtil.DF_YMD_24));
        }
        int result = this.alarmDataService.insertSelective(alarm);
        if (1 == result) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS)));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
        }

        return Response.ok(returnJson).build();
    }

    @Override
    public Response updateAlarm(AlarmData alarm) {
        String returnJson = null;
        WebUtil.prepareUpdateParams(alarm);
        alarm.setConfirmTime(new Date());
        // 调用Monitor接口，同步确认告警 TODO
//        AlarmAction alarmAction = new AlarmAction();
//        alarmAction.setProviderType("opennms");
//        alarmAction.setId(alarm.getAlarmId());
//          try {
//            boolean ackAlarm = alarmHandler.ackAlarm(alarmAction);
//            if(ackAlarm){
//                int result = this.alarmDataService.updateByPrimaryKeySelective(alarm);
//                if (1 == result) {
//                    returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
//                            .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS)));
//                } else {
//                    returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
//                            .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
//                }
//            }else{
//                returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
//                        .getMessage("确认告警信息失败！")));
//            }
//        } catch (CommonAdapterException e) {
//            e.printStackTrace();
//        } catch (AdapterUnavailableException e) {
//            e.printStackTrace();
//        }

        int result = this.alarmDataService.updateByPrimaryKeySelective(alarm);
        if (1 == result) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS)));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
        }

        return Response.ok(returnJson).build();
    }

    @Override
    public Response setStatusAlarm(AlarmData alarm) {
        String returnJson = null;
        WebUtil.prepareUpdateParams(alarm);
        int result = this.alarmDataService.updateByPrimaryKeySelective(alarm);

        if (1 == result) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS)));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
        }

        return Response.ok(returnJson).build();
    }

    @Override
    public Response syncAlarmTotal(AlarmData alarm) {

        String resultJson = "";
        Map<String, String> map = new HashMap<String, String>();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        // 查询出数据库中所有的
//        Criteria example = new Criteria();
//        List<AlarmData> alaList = this.alarmDataService.selectByParams(example);
//
//        try {
//
//            // 来自监控平台的告警信息
//            EventListGet eventListGet = new EventListGet();
////    		eventListGet.setEventidFrom("15305");
////    		eventListGet.setEventidTill("15305");
//            try {
//                eventListGet.setTimeFrom(getBeforeSevenDayTime());
//            } catch (ParseException e1) {
//                e1.printStackTrace();
//            }
//            eventListGet.setTimeTill(sdf.format(new Date()));
//            eventListGet.setRetrieveAll(true);
//            eventListGet.setProviderType("opennms");
//
//            List<Alarm> eventList = alarmHandler.getAlarmList(eventListGet);
//            List<AlarmData> alarmData_list = new ArrayList<AlarmData>();
//
//            if(eventList != null && eventList.size() > 0){
//                if(alaList !=null && alaList.size() > 0){
//                    for (Alarm ala : eventList) {
//                        // 是否新增
//                        boolean isok = true;
//                        for(AlarmData alarmdata:alaList){
//                            // 判断是否和以前的告警相同
//                            if(ala.getId().equals(alarmdata.getAlarmId())){
//                                isok = false;
//                                // 编辑
//                                try {
//                                    alarmdata.setAlarmTime(sdf.parse(ala.getTime()));
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//                                alarmdata.setAlarmLevel(ala.getPriority());
//                                alarmdata.setAlarmType(ala.getType());
//                                alarmdata.setTitle(ala.getTitle());
//                                alarmdata.setContent(ala.getContent());
//                                alarmdata.setAlarmTarget(ala.getNodeId());
//                                WebUtil.prepareUpdateParams(alarmdata);
//                                this.alarmDataService.updateByPrimaryKeySelective(alarmdata);
//                                alarmData_list.add(alarmdata);
//                            }
//                        }
//
//                        if(isok){
//                            // 新增
//                            AlarmData alaData = new AlarmData();
//                            alaData.setAlarmId(ala.getId());
//                            try {
//                                alaData.setAlarmTime(sdf.parse(ala.getTime()));
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//                            alaData.setAlarmLevel(ala.getPriority());
//                            alaData.setAlarmType(ala.getType());
//                            alaData.setTitle(ala.getTitle());
//                            alaData.setAlarmSource("监控平台");
//                            alaData.setContent(ala.getContent());
//                            alaData.setAlarmTarget(ala.getNodeId());
//                            alaData.setStatus(WebConstants.AlarmStatus.UNTREATED);
//                            WebUtil.prepareInsertParams(alaData);
//
//                            this.alarmDataService.insertSelective(alaData);
//                            alarmData_list.add(alaData);
//                        }
//                    }
//                }else{
//                    // 数据库中告警信息为空，全部插入数据库
//                    for (Alarm ala : eventList) {
//                        AlarmData alaData = new AlarmData();
//                        alaData.setAlarmId(ala.getId());
//                        try {
//                            alaData.setAlarmTime(sdf.parse(ala.getTime()));
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        alaData.setAlarmLevel(ala.getPriority());
//                        alaData.setAlarmType(ala.getType());
//                        alaData.setTitle(ala.getTitle());
//                        alaData.setAlarmSource("监控平台");
//                        alaData.setContent(ala.getContent());
//                        alaData.setAlarmTarget(ala.getNodeId());
//                        alaData.setStatus(WebConstants.AlarmStatus.UNTREATED);
//                        WebUtil.prepareInsertParams(alaData);
//
//                        this.alarmDataService.insertSelective(alaData);
//                        alarmData_list.add(alaData);
//
//                    }
//                }
//                this.mailNotificationsService.alarmsInfoNotice(alarmData_list);
//            }else{
//                logger.info("监控平台没有同步告警信息！");
//            }
//        } catch (CommonAdapterException e) {
//            e.printStackTrace();
//        } catch (AdapterUnavailableException e) {
//            e.printStackTrace();
//        }

        map.put("result", "success");

        return Response.ok(JsonUtil.toJson(new RestResult(map))).build();
    }

    @Override
    public Response findAlarmRule(AlarmRule rule) {
        Criteria example = new Criteria();

        if (!StringUtil.isNullOrEmpty(rule.getAlarmLevel())) {
            example.put("alarmLevel", rule.getAlarmLevel());
        }
        if (!StringUtil.isNullOrEmpty(rule.getAlarmType())) {
            example.put("alarmType", rule.getAlarmType());
        }
        if (!StringUtil.isNullOrEmpty(rule.getCheckOptr())) {
            example.put("checkOptr", rule.getCheckOptr());
        }
        if (!StringUtil.isNullOrEmpty(rule.getAlarmKpi())) {
            example.put("alarmKpi", rule.getAlarmKpi());
        }

        List<AlarmRule> list = this.alarmRuleService.selectByParams(example);
        return Response.ok(JsonUtil.toJson(new RestResult(list))).build();
    }

    @Override
    public Response addAlarmRule(AlarmRule rule) {
        String returnJson;
        WebUtil.prepareInsertParams(rule);
        rule.setCheckOptr("1");
        rule.setPlatformType("zabbix");
        rule.setInstanceId("0");
        int result = this.alarmRuleService.insertSelective(rule);
        if (1 == result) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS)));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
        }

        return Response.ok(returnJson).build();
    }

    @Override
    public Response deleteAlarmRule(@PathParam("alarmRuleSid") Long alarmRuleSid) {
        String returnJson;
        int result = this.alarmRuleService.deleteByPrimaryKey(alarmRuleSid);
        if (1 == result) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS)));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_DELETE_FAILURE)));
        }

        return Response.ok(returnJson).build();
    }

    @Override
    public Response updateAlarmRule(AlarmRule rule) {
        String returnJson = null;
        WebUtil.prepareUpdateParams(rule);
        // 调用Monitor工程接口，同步编辑告警规则
//        AlarmRoleUpate triggerInfoUpdate = new AlarmRoleUpate();
//        triggerInfoUpdate.setProviderType("opennms");
//        triggerInfoUpdate.setName(rule.getTitle());
//        triggerInfoUpdate.setId(rule.getAlarmRuleSid().toString());
//        triggerInfoUpdate.setDescription(rule.getContent());
//        triggerInfoUpdate.setType(rule.getAlarmType());
//        triggerInfoUpdate.setSubType(rule.getAlarmKpi());
//        triggerInfoUpdate.setValue(rule.getAlarmThreshold());
//        triggerInfoUpdate.setOperator(Integer.parseInt(rule.getOperator()));
//        triggerInfoUpdate.setPriority(rule.getAlarmLevel());
//        triggerInfoUpdate.setCount(rule.getAccumulateCount().toString());
//        try {
//
//            boolean bool = alarmHandler.updateAlarmRoleInfo(triggerInfoUpdate);
//            if(bool){
//                int result = this.alarmRuleService.updateByPrimaryKeySelective(rule);
//                if (1 == result) {
//                    returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
//                            .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS)));
//                } else {
//                    returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
//                            .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
//                }
//            }else{
//                returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
//                        .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
//            }
//        } catch (CommonAdapterException e) {
//            e.printStackTrace();
//            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
//                    .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
//            return Response.status(Status.OK).entity(returnJson).build();
//
//        } catch (AdapterUnavailableException e) {
//            e.printStackTrace();
//            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
//                    .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
//            return Response.status(Status.OK).entity(returnJson).build();
//
//        }

        int result = this.alarmRuleService.updateByPrimaryKeySelective(rule);

        if (1 == result) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS)));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
        }

        return Response.ok(returnJson).build();
    }

    @Override
    public Response findVmAlarmDetail(String params) {
        User user = AuthUtil.getAuthUser();

        // 查询退订中、已开通
        String statusParams = "'"
                + WebConstants.ServiceInstanceStatus.CANCELING + "','"
                + WebConstants.ServiceInstanceStatus.CHANGEING + "','"
                + WebConstants.ServiceInstanceStatus.OPENED
                + "'".replace(" ", "");
        try {
            Criteria example = new Criteria();
            if (!StringUtil.isNullOrEmpty(params)) {
                Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
                map.put("ownerId", user.getAccount());
                map.put("statusParams", statusParams);
                example.setCondition(map);
            } else {
                example.put("ownerId", user.getAccount());
                example.put("statusParams", statusParams);
            }
            example.setOrderByClause("A.DREDGE_DATE desc , H.VM_NAME");
            List<ServiceInstance>
                    serviceInstanceList =
                    this.serviceInstanceService.selectByParams(example);
            //得到监控的告警信息
            List<AlarmVmDetail> result = new ArrayList<AlarmVmDetail>();
            if (!CollectionUtils.isEmpty(serviceInstanceList)) {
                String ids = "";
                for (ServiceInstance serviceInstance : serviceInstanceList) {
                    String resId = serviceInstance.getResSid();
                    ResVm resVm = resVmService.selectByPrimaryKey(resId);
                    if (resVm != null && !StringUtil.isNullOrEmpty(resVm.getMonitorNodeId())) {
                        ids = ids + resVm.getMonitorNodeId() + ",";
                    }
                }
                if (ids.length() > 0) {
                    ids = ids.substring(0, ids.length() - 1);
                }
                String nodeids = "{nodeids:[" + ids + "]}";
                List<AlarmVmDetail> vmAlarmDetail = getVmAlarmDetail(nodeids);
                if (!CollectionUtils.isEmpty(vmAlarmDetail)) {
                    for (AlarmVmDetail alarm : vmAlarmDetail) {
                        for (ServiceInstance serviceInstance : serviceInstanceList) {
                            if (alarm.getAlarmTarget().equals(serviceInstance.getMonitorNodeId())) {
                                alarm.setInstanceName(serviceInstance.getInstanceName());
                                alarm.setVmName(serviceInstance.getVmName());
                                alarm.setResInsVmStatusName(
                                        serviceInstance.getResInsVmStatusName());
                                result.add(alarm);
                                break;
                            }
                        }
                    }
                }
            }
            String json = JsonUtil.toJson(result);
            return Response.status(Status.OK).entity(json).build();
        } catch (Exception e) {
            e.printStackTrace();
            String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
                    null);
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(resultJson)
                    .build();
        }
    }

    @Override
    public Response findVmAlarmTotal(String params) {
        User user = AuthUtil.getAuthUser();

        // 查询退订中、已开通
        String statusParams = "'"
                + WebConstants.ServiceInstanceStatus.CANCELING + "','"
                + WebConstants.ServiceInstanceStatus.CHANGEING + "','"
                + WebConstants.ServiceInstanceStatus.OPENED
                + "'".replace(" ", "");
        try {
            Criteria example = new Criteria();
            if (!StringUtil.isNullOrEmpty(params)) {
                Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
                map.put("ownerId", user.getAccount());
                map.put("statusParams", statusParams);
                example.setCondition(map);
            } else {
                example.put("ownerId", user.getAccount());
                example.put("statusParams", statusParams);
            }
            example.setOrderByClause("A.DREDGE_DATE desc , H.VM_NAME");
            List<ServiceInstance>
                    serviceInstanceList =
                    this.serviceInstanceService.selectByParams(example);
            //得到监控的告警信息
            List<AlarmDateResponse> result = new ArrayList<AlarmDateResponse>();
            String nodeIds = "";
            if (!CollectionUtils.isEmpty(serviceInstanceList)) {
                for (ServiceInstance serviceInstance : serviceInstanceList) {
                    String resId = serviceInstance.getResSid();
                    ResVm resVm = resVmService.selectByPrimaryKey(resId);
                    if (resVm != null && !StringUtil.isNullOrEmpty(resVm.getMonitorNodeId())) {
                        nodeIds = nodeIds + resVm.getMonitorNodeId() + ",";
                    }
                }
            }
            if (nodeIds.length() > 0) {
                nodeIds = nodeIds.substring(0, nodeIds.length() - 1);
                String ids = "{nodeids:[" + nodeIds + "]}";
                result = getVmAlarmTotal(ids);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            if (!CollectionUtils.isEmpty(result)) {
                String columnData = "";
                String pieData = "";
                List<String> alarmname = new ArrayList<String>();
                for (AlarmDateResponse alarm : result) {
                    alarmname.add(alarm.getAlarmLevelName());
                    columnData = columnData + "{value:" + alarm.getAlarmTotal() + "},";
                    pieData =
                            pieData + "{category:'" + alarm.getAlarmLevelName() + "',value:" + alarm
                                    .getAlarmTotal() + "},";
                }
                map.put("value", JsonUtil.toJson("[" + columnData + "]"));
                map.put("data", JsonUtil.toJson("[" + pieData + "]"));
                map.put("name", alarmname);
            }
            String json = JsonUtil.toJson(map);
            return Response.status(Status.OK).entity(json).build();
        } catch (Exception e) {
            e.printStackTrace();
            String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
                    null);
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(resultJson)
                    .build();
        }
    }

    /**
     * 获取前7天的时间
     *
     * @return 返回前7天的时间
     * @throws ParseException 可能抛出的异常
     */
    public String getBeforeSevenDayTime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -7);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }

    /**
     * 获取虚拟机的告警统计信息
     *
     * @param nodeIds 参数
     * @return 返回值
     */
    public static List<AlarmDateResponse> getVmAlarmTotal(String nodeIds) {
        return post(URLPREFIX + "/alarm/vmAlarmTotal", nodeIds,
                new TypeReference<List<AlarmDateResponse>>() {

                });
    }

    /**
     * 获取虚拟机告警信息的详细信息
     *
     * @param nodeIds 参数
     * @return 返回值
     */
    public static List<AlarmVmDetail> getVmAlarmDetail(String nodeIds) {
        return post(URLPREFIX + "/alarm/vmAlarmDetail", nodeIds,
                new TypeReference<List<AlarmVmDetail>>() {

                });
    }

    /**
     * @param url           路径
     * @param request       request请求
     * @param typeReference 类型
     * @param <T>           泛型
     * @return 返回值
     */
    public static <T> T post(String url, Object request, TypeReference<T> typeReference) {
        return handler("post", url, request, typeReference);
    }

    /**
     * @param method        参数方法
     * @param url           路径
     * @param request       request请求
     * @param typeReference 类型
     * @param <T>           泛型
     * @return 返回值
     */
    public static <T> T handler(String method, String url, Object request,
                                TypeReference<T> typeReference) {
        T response = null;
        try {
            RESTHttpResponse result = null;
            LOGGER.info("call external interface, url=" + url);

            String requestJson = customToJson(request);
            LOGGER.info("requestBody=" + requestJson);
            result = RSETClientUtil.post(url, requestJson);

//            if("post".equals(method)) {
//                //将所有的null转化为""
//                String requestJson =  customToJson(request);
//                LOGGER.info("requestBody=" + requestJson);
//                result = RSETClientUtil.post(url, requestJson);
//            } else {
//                result = RSETClientUtil.get(url);
//            }

            if (result != null && RESTHttpResponse.SUCCESS.equals(result.getStatus())) {
                LOGGER.debug("responseBody=" + result.getContent());
                response = JsonUtil.fromJson(result.getContent(), typeReference);
            } else {
                LOGGER.error("call external interface failure, url=" + url);
            }
        } catch (Exception e) {
            LOGGER.error("call external interface exception, url=" + url, e);
        }
        return response;
    }

    /**
     * @param src 参数
     * @param <T> 泛型
     * @return 返回值
     * @throws IOException 可能抛出IO异常
     */
    public static <T> String customToJson(T src) throws IOException {
        ObjectMapper mapper = JsonUtil.generateMapper(Inclusion.ALWAYS);
        mapper.getSerializerProvider().setNullValueSerializer(new JSONNullToBlankSerializer());
        return JsonUtil.toJson(src, mapper);
    }
}
