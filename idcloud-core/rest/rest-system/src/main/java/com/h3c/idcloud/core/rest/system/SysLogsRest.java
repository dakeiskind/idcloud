/**
 *
 */
package com.h3c.idcloud.core.rest.system;


import com.h3c.idcloud.core.pojo.dto.system.SysTLogRecord;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * @author jipeigui
 */
@Path("/logs")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface SysLogsRest {

    /**
     * 查询日志计数
     */
    @GET
    @Path("/findLogs")
    public Response findLogs(@Context HttpServletRequest request);

    /**
     * 查询日志列表
     */
    @WebMethod
    @POST
    @Path("/findLogsByParams")
    public Response findLogsByParams(@Context HttpServletRequest request,SysTLogRecord sysTLogRecord);

}
