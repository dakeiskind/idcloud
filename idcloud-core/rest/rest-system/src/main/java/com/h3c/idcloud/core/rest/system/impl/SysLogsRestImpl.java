/**
 *
 */
package com.h3c.idcloud.core.rest.system.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.system.SysTLogRecord;

import com.h3c.idcloud.core.rest.system.SysLogsRest;
import com.h3c.idcloud.core.service.system.api.SysTLogRecordService;

import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.BaseGridReturn;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.RequestContextUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import java.util.List;


/**
 * @author jipeigui
 */
@Component
public class SysLogsRestImpl implements SysLogsRest {

    /**
     * 系统日志系统Service
     */
    @Reference(version = "1.0.0")
    private SysTLogRecordService sysTLogRecordService;

    @Override
    public Response findLogs(@Context HttpServletRequest request) {
        Criteria criteria = new Criteria();
        WebUtil.preparePageParams(request, criteria, "ACT_END_DATE DESC");
        criteria.put("account", RequestContextUtil.getAuthUserInfo(request).getAccount());
        List<SysTLogRecord> taskLogList = this.sysTLogRecordService.selectByParams(criteria);
        int total = this.sysTLogRecordService.countByParams(criteria);
        String json = JsonUtil.toJson(new RestResult(new BaseGridReturn(total, taskLogList)));
        return Response.ok(json).build();
    }

    @Override
    public Response findLogsByParams(@Context HttpServletRequest request,SysTLogRecord sysTLogRecord) {
        Criteria criteria = new Criteria();
        WebUtil.preparePageParams(request, criteria, "ACT_END_DATE DESC");
        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        if(sysTLogRecord.getActTarget() != "" && sysTLogRecord.getActTarget() != null){
            criteria.put("actTarget",sysTLogRecord.getActTarget());
        }
        if(sysTLogRecord.getStartTime() != "" && sysTLogRecord.getStartTime() != null){
            criteria.put("actStartDate",sysTLogRecord.getStartTime());
        }
        if(sysTLogRecord.getEndTime() != "" && sysTLogRecord.getEndTime() != null){
            criteria.put("actEndDate",sysTLogRecord.getEndTime());
        }
        if(sysTLogRecord.getActResult() != "" && sysTLogRecord.getActResult() != null){
            criteria.put("actResult",sysTLogRecord.getActResult());
        }
        criteria.put("account", authUser.getAccount());
        List<SysTLogRecord> taskLogList = this.sysTLogRecordService.selectByParams(criteria);
        return Response.ok(JsonUtil.toJson(new RestResult(taskLogList))).build();
    }
}
