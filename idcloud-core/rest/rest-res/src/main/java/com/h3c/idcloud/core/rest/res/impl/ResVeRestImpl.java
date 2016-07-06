package com.h3c.idcloud.core.rest.res.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.rest.res.ResVeRest;
import com.h3c.idcloud.core.service.res.api.ResVeService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by jpg on 2016/3/2.
 */
@Component
public class ResVeRestImpl implements ResVeRest {

    Logger log = LoggerFactory.getLogger(this.getClass());

    /** 资源拓扑Service */
    @Reference(version = "1.0.0")
    ResVeService resVeService;

    /**
     * 查询ve列表
     *
     * @param resve
     * @return
     */
    @Override
    public Response selectVeByParams(ResVe resve) {
        Criteria example = new Criteria();

        if (!StringUtil.isNullOrEmpty(resve.getResTopologyNameLike())) {
            example.put("resTopologyNameLike", resve.getResTopologyNameLike());
        }

        if (!StringUtil.isNullOrEmpty(resve.getVirtualPlatformType())) {
            example.put("virtualPlatformType", resve.getVirtualPlatformType());
        }

        if (!StringUtil.isNullOrEmpty(resve.getManagementAccountLike())) {
            example.put("managementAccountLike", resve.getManagementAccountLike());
        }

        if (!StringUtil.isNullOrEmpty(resve.getConnectStatus())) {
            example.put("connectStatus", resve.getConnectStatus());
        }
        if (!StringUtil.isNullOrEmpty(resve.getResTopologySid())) {
            example.put("parentTopologySid", resve.getResTopologySid());
        }
        List<ResVe> list = this.resVeService.selectByParams(example);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();

    }

    /**
     * 根据veSid查询资源环境信息
     *
     * @param resTopologySid
     * @return
     */
    @Override
    public Response findResourceVeBySid(String resTopologySid) {

        ResVe topology = this.resVeService.selectByPrimaryKey(resTopologySid);
        String json = JsonUtil.toJson(new RestResult(topology));
        return Response.ok(json).build();

    }
}
