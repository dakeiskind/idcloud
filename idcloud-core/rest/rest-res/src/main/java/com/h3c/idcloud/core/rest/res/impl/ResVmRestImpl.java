package com.h3c.idcloud.core.rest.res.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.rest.res.ResVmRest;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.infrastructure.common.pojo.BaseGridReturn;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by jpg on 2016/3/2.
 */
@Component
public class ResVmRestImpl implements ResVmRest {

    Logger log = LoggerFactory.getLogger(this.getClass());

    /** 资源拓扑Service */
    @Reference(version = "1.0.0")
    ResVmService resVmService;

    @Override
    public Response findVmByPaging(@Context HttpServletRequest request) {
        Criteria param = new Criteria();
        WebUtil.preparePageParams(request, param, "VM_NAME");

        if (!StringUtil.isNullOrEmpty(param.get("resTopologySid"))) {
            if ("HOST".equals(param.get("resTopologyType"))) {
                param.put("allocateResHostSid", param.get("resTopologySid"));
                param.put("resTopologySid", null);
            } else if ("PHOST".equals(param.get("resTopologyType"))) {
                param.put("allocateResHostSid", param.get("resTopologySid"));
                param.put("resTopologySid", null);
            }else{
                param.put("resTopologySid", param.get("resTopologySid"));
            }
        }

        // 鏌ヨ鏁版嵁
        List<ResVm> list = this.resVmService.selectByParams(param);
        int total = this.resVmService.countByParams(param);

        String json = JsonUtil.toJson(new RestResult(new BaseGridReturn(total, list)));

        return Response.ok(json).build();

    }

    /**
     * 查询主机下的虚拟机
     *
     * @param allocateResHostSid
     * @return
     */
    @Override
    public Response findOriginalVM(String allocateResHostSid) {
        ResVm list = this.resVmService.selectByResHostSid(allocateResHostSid);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();
    }

    /**
     * 统计vm在拓扑结构下的信息
     *
     * @param resTopologySid
     * @return
     */
    @Override
    public Response statisticalTopologyOfVm(String resTopologySid) {
        ResVm list = this.resVmService.statisticalTopologyOfVm(resTopologySid);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();

    }

    /**
     * 统计资源池下的虚拟机信息
     *
     * @param resPoolSid
     * @return
     */
    @Override
    public Response statisticalComputePoolOfVm(String resPoolSid) {
        ResVm list = this.resVmService.statisticalComputePoolOfVm(resPoolSid);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();
    }
}
