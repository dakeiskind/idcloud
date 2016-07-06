/**
 *
 */
package com.h3c.idcloud.core.rest.user.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.charge.ServiceBill;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.rest.user.DashboardRest;
import com.h3c.idcloud.core.service.charge.api.BillingAccountService;
import com.h3c.idcloud.core.service.order.api.OrderService;
import com.h3c.idcloud.core.service.res.api.ResStatisticsService;
import com.h3c.idcloud.core.service.ticket.api.IssueService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.RequestContextUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author jj
 */
@Component
public class DashboardRestImpl implements DashboardRest {

    @Reference(version = "1.0.0")
    private UserService userService;

    @Reference(version = "1.0.0")
    private OrderService orderService;

    @Reference(version = "1.0.0")
    private IssueService issueService;


    @Reference(version = "1.0.0")
    private BillingAccountService billingAccountService;

    @Reference(version = "1.0.0")
    ServiceInstanceService serviceInstanceService;

    @Reference(version = "1.0.0")
    ResStatisticsService resStatisticsService;

    public Response user(HttpServletRequest request) {
        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);

        // 查询用户信息
        Map map = new HashMap();
        Map<String, String> userMap = new HashMap<String, String>();
        List<User> list = userService.selectByParams(new Criteria("userSid", authUser.getUserSid()));
        if (list.size() > 0) {
            User user = list.get(0);
            userMap.put("realName", user.getRealName());
            userMap.put("mobile", user.getMobile());
            userMap.put("account", user.getAccount());
            userMap.put("email", user.getEmail());
            userMap.put("balance", user.getBalance() == null ? "0" : user.getBalance().doubleValue() + "");
        }
        map.put("user", userMap);
        //待办事项
        Map<String, String> toDoMap = new HashMap<String, String>();
        Criteria example = new Criteria();
        example.put("mgtObjSid", authUser.getMgtObjSid().toString());
        example.put("ownerId", authUser.getAccount().toString());
        example.put("status","97");
        Integer toPaidOrder = orderService.countByParams(example);

        example = new Criteria();
        List<String> createdBy = new ArrayList<String>();
        createdBy.add("admin");
        createdBy.add(authUser.getAccount());
        example.put("createdByUserAndAdmin",createdBy);
        List<String> statusToDo = new ArrayList<String>();
        statusToDo.add("01");
        statusToDo.add("02");
        example.put("issueToProcessedIssue",statusToDo);
        Integer toProcessedIssue = issueService.countByParams(example);

        toDoMap.put("toCharge", "0");
        toDoMap.put("toPaidOrder", toPaidOrder.toString());
        toDoMap.put("toProcessedIssue", toProcessedIssue.toString());
        map.put("toDo", toDoMap);

        //日总额与月总额数据
        Criteria criteria = new Criteria();
        criteria.put("userSid",authUser.getUserSid());
        criteria.put("dayTime", StringUtil.dateFormat(new Date(),StringUtil.DF_YMD));
        List<ServiceBill> dayServiceBills = billingAccountService.selectStatisticsAmountInfo(criteria);
        criteria.clear();
        criteria.put("userSid",authUser.getUserSid());
        criteria.put("monthTime", StringUtil.dateFormat(new Date(),StringUtil.DF_YM));
        List<ServiceBill> monthServiceBills = billingAccountService.selectStatisticsAmountInfo(criteria);
        map.put("dayBalance",getBalance(dayServiceBills));
        map.put("monthBalance",getBalance(monthServiceBills));


        return Response.ok(JsonUtil.toJson(new RestResult(map))).build();
    }

    @Override
    public Response userLineChart(String timeLine, @Context HttpServletRequest request) {
        //前端参数timeLine 当月消费数据，上个月消费数据
        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        Map<String,Object> lineChartData = billingAccountService.getUserCenterLineChartData(authUser.getUserSid(),timeLine);
        return Response.ok(JsonUtil.toJson(new RestResult(lineChartData))).build();
    }

    public Response console(HttpServletRequest request) {
        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        Map map = new HashMap();
        Map<String, String> vmMap = new HashMap<String, String>();
        Map<String, String> storageMap = new HashMap<String, String>();
        Map<String, String> publicIpMap = new HashMap<String, String>();

        Map<String,Object> vms = resStatisticsService.selectStatisticsVmInfo(authUser.getAccount());
        Map<String,Object> floatingIp = resStatisticsService.selectStatisticsFloatingIpInfo(authUser.getMgtObjSid());
        vmMap.put("vmNum", getCount(vms.get("TOTAL")));
        vmMap.put("stopVmNum",getCount(vms.get("POWERED_OFF")));
        vmMap.put("startVmNum",getCount(vms.get("NORMAL")));
        vmMap.put("badVmNum",getCount(vms.get("FAILURE")));
        storageMap.put("storageNum","0");
        storageMap.put("stopStorageNum","0");
        storageMap.put("startStorageNum","0");
        storageMap.put("badStorageNum","0");
        publicIpMap.put("publicNum",getCount(floatingIp.get("TOTAL")));
        publicIpMap.put("stopPublicNum","0");
        publicIpMap.put("startPublicNum",getCount(floatingIp.get("NORMAL")));
        map.put("vm",vmMap);
        map.put("storage",storageMap);
        map.put("public",publicIpMap);
        return Response.ok(JsonUtil.toJson(new RestResult(map))).build();
    }

    private String getCount(Object mapCountValue){
        return StringUtil.isNullOrEmpty(mapCountValue)?"0":mapCountValue.toString();
    }


    private Map<String,String> getBalance(List<ServiceBill> serviceBills){
        Map<String,String> balanceMap = new HashMap<>();
        BigDecimal total = BigDecimal.ZERO;
        for(ServiceBill sb:serviceBills){
            balanceMap.put(sb.getServiceCode(),sb.getAmount().toString());
            total = total.add(sb.getAmount());
        }
        balanceMap.put("total",total.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        return balanceMap;
    }
}
