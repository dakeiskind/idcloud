package com.h3c.idcloud.core.rest.system;

import com.h3c.idcloud.core.pojo.dto.system.AlarmData;
import com.h3c.idcloud.core.pojo.dto.system.AlarmRule;

import javax.jws.WebMethod;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * @author gujie
 */
@Path("/alarms")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface AlarmsRest {


    /**
     * 查询告警列表
     * @param alarm 根据参数查询列表数据
     * @return 返回值
     */
    @WebMethod
    @POST
    Response findAlarm(AlarmData alarm);

    /**
     * 增加一条告警规则
     * @param alarm 告警规则数据
     * @return 返回值
     */
    @WebMethod
    @POST
    @Path("/create")
    Response addAlarm(AlarmData alarm);

    /**
     * 更新租户 --确定告警信息
     * @param alarm 更新数据
     * @return 返回值
     */
    @WebMethod
    @POST
    @Path("/update")
    Response updateAlarm(AlarmData alarm);

    /**
     * 消除告警信息
     * @param alarm 数据参数
     * @return 返回值
     */
    @WebMethod
    @POST
    @Path("/setStatus")
    Response setStatusAlarm(AlarmData alarm);

    /**
     * 同步告警信息
     * @param alarm 参数
     * @return 返回值
     */
    @WebMethod
    @POST
    @Path("/sync")
    Response syncAlarmTotal(AlarmData alarm);

    /**
     * 查询告警规则列表
     * @param rule 参数
     * @return 返回值
     */
    @WebMethod
    @POST
    @Path("/rule")
    Response findAlarmRule(AlarmRule rule);

    /**
     * 新增告警规则
     * @param rule 参数
     * @return 返回值
     */
    @WebMethod
    @POST
    @Path("/rule/create")
    Response addAlarmRule(AlarmRule rule);

    /**
     * 删除告警规则
     * @param alarmRuleSid 告警规则Sid
     * @return 返回值
     */
    @WebMethod
    @GET
    @Path("/rule/delete/{alarmRuleSid}")
    Response deleteAlarmRule(@PathParam("alarmRuleSid") Long alarmRuleSid);

    /**
     * 更新告警 --确定告警规则
     * @param rule 参数
     * @return 返回值
     */
    @WebMethod
    @POST
    @Path("/rule/update")
    Response updateAlarmRule(AlarmRule rule);

    /**
     * 得到用户下的所有的云主机的告警信息
     * @param param 参数
     * @return 返回值
     */
    @WebMethod
    @POST
    @Path("/findVmAlarmDetail")
    Response findVmAlarmDetail(String param);

    /**
     * 得到用户下的所有的云主机的告警信息
     * @param param 参数
     * @return 返回值
     */
    @WebMethod
    @POST
    @Path("/findVmAlarmTotal")
    Response findVmAlarmTotal(String param);
}
