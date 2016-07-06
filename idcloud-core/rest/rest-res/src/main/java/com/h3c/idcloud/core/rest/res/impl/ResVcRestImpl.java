package com.h3c.idcloud.core.rest.res.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.res.ResVc;
import com.h3c.idcloud.core.rest.res.ResVcRest;
import com.h3c.idcloud.core.service.res.api.ResVcService;
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
public class ResVcRestImpl implements ResVcRest {

    Logger log = LoggerFactory.getLogger(this.getClass());

    /** 资源拓扑Service */
    @Reference(version = "1.0.0")
    ResVcService resVcService;

    /**
     * 查询vc列表
     *
     * @param resvc
     * @return
     */
    @Override
    public Response selectVcByParams(ResVc resvc) {
        Criteria example = new Criteria();
        if (!StringUtil.isNullOrEmpty(resvc.getResTopologyNameLike())) {
            example.put("resTopologyNameLike", resvc.getResTopologyNameLike());
        }
        if (!StringUtil.isNullOrEmpty(resvc.getResTopologyName())) {
            example.put("clusterName", resvc.getResTopologyName());
        }
        if (!StringUtil.isNullOrEmpty(resvc.getParentTopologySid())) {
            // 鏉′欢鏌ヨ鏃?
            example.put("parentTopologySid", resvc.getParentTopologySid());
        }

        if (!StringUtil.isNullOrEmpty(resvc.getResVcTopologyType())) {
            example.put("resVcTopologyType", resvc.getResVcTopologyType());
        }

        if (!(StringUtil.isNullOrEmpty(resvc.getResPoolSid()))) {
            example.put("resPoolSid", resvc.getResPoolSid());
        }
        if (!StringUtil.isNullOrEmpty(resvc.getNotExistResPoolSid())) {
            example.put("notExistResPoolSid", resvc.getNotExistResPoolSid());
        }
        example.setOrderByClause("CLUSTER_NAME");
        List<ResVc> list = this.resVcService.selectByParams(example);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();

    }

    /**
     * 根据sid查询vc信息
     *
     * @param resTopologySid
     * @return
     */
    @Override
    public Response findResourceVcBySid(String resTopologySid) {
        ResVc res = this.resVcService.selectByPrimaryKey(resTopologySid);
        String json = JsonUtil.toJson(new RestResult(res));
        return Response.ok(json).build();
    }
}
