package com.h3c.idcloud.core.rest.product;

import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.rest.product.impl.CbsRestImpl;
import org.springframework.context.annotation.Scope;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 云硬盘相关Rest接口
 * Created by tdz on 2016/3/28.
 */
@Path("/cbs")
@Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
@Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
@Scope("singleton")
public interface CbsRest {
    /**
     * 获取块存储服务实例
     */
    @POST
    @Path("/findCbs")
    Response findCbsInfo(@Context HttpServletRequest req);

    /**
     * 挂载或卸载块存储
     */
    @POST
    @Path("/operation")
    Response operation(CbsRestImpl.ParamOperateObs param, @Context HttpServletRequest request);
}
