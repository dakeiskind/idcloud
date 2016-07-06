package com.h3c.idcloud.core.rest.res.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.res.ResTopologyConfig;
import com.h3c.idcloud.core.rest.res.ResTopologyConfigRest;
import com.h3c.idcloud.core.service.res.api.ResTopologyConfigService;
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
public class ResTopologyConfigRestImpl implements ResTopologyConfigRest {

    Logger log = LoggerFactory.getLogger(this.getClass());

    /** 资源拓扑Service */
    @Reference(version = "1.0.0")
    ResTopologyConfigService resTopologyConfigService;


    /**
     * 查询拓扑结构配置
     *
     * @param config
     * @return
     */
    @Override
    public Response findTopologyConfig(ResTopologyConfig config) {
        Criteria example = new Criteria();
        if (!StringUtil.isNullOrEmpty(config.getResTopologySid())) {
            example.put("resTopologySid", config.getResTopologySid());
        }

        List<ResTopologyConfig> list = this.resTopologyConfigService.selectByParams(example);
        String json = JsonUtil.toJson(new RestResult(list));

        return Response.ok(json).build();

    }
}
