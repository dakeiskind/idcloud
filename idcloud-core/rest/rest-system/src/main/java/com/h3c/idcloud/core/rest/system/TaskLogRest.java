package com.h3c.idcloud.core.rest.system;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import java.util.Map;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface TaskLogRest {

    /**
     * 查询TASK_LOG列表
     */
    @GET
    @Path("/findTaskLog")
    Response findTaskLog(@Context HttpServletRequest request);

    /**
     * 查询日志
     */
    @WebMethod
    @POST
    @Path("/findAll")
    Response findAll(Map map);


}
