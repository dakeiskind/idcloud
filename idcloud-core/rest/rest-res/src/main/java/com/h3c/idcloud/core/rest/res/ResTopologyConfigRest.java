package com.h3c.idcloud.core.rest.res;


import com.h3c.idcloud.core.pojo.dto.res.ResTopologyConfig;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 资源环境接口
 *
 * Created by jpg on 2016/3/2.
 */
@Path("/topologyConfigs")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface ResTopologyConfigRest {


    /**
     * 查询拓扑结构配置
     *
     * @param config
     * @return
     */
    @POST
    Response findTopologyConfig(ResTopologyConfig config);

}
