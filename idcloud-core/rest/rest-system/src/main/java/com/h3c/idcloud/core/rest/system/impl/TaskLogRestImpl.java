package com.h3c.idcloud.core.rest.system.impl;


import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Context;

import javax.ws.rs.core.Response;


import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.core.rest.system.TaskLogRest;
import com.h3c.idcloud.core.service.system.api.TaskLogService;
import com.h3c.idcloud.infrastructure.common.pojo.BaseGridReturn;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;



import org.springframework.stereotype.Component;

@Component
public class TaskLogRestImpl implements TaskLogRest {

    /**
     * 任务管理service
     */
    @Reference(version = "1.0.0")
    private TaskLogService taskLogService;

    /**
     * 查询TASK_LOG列表
     */
    public Response findTaskLog(@Context HttpServletRequest request) {

        Criteria criteria = new Criteria();
        WebUtil.preparePageParams(request, criteria, "TASK_LOG_SID DESC");
        List<TaskLog> taskLogList = this.taskLogService.selectByParams(criteria);
        int total = this.taskLogService.countByParams(criteria);
        String json = JsonUtil.toJson(new BaseGridReturn(total, taskLogList));
        return Response.ok(json).build();
    }

    /**
     * 查询日志
     */
    @Override
    public Response findAll(Map map) {
        return null;
    }


}
