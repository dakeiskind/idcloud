/**
 * 
 */
package com.h3c.idcloud.core.rest.user;

import org.springframework.context.annotation.Scope;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 *
 * @author jj
 */
@Path("/dashboard")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface DashboardRest {
	
	/**
	 * 用户中心
	 * @return
	 */
	@GET
	@Path("/user")
	Response user(@Context HttpServletRequest request);

	/**
	 * 用户中心消费趋势
	 * @return
	 */
	@GET
	@Path("/user/line/balance/{timeLine}")
	Response userLineChart(@PathParam("timeLine") String timeLine, @Context HttpServletRequest request);
	
	/**
	 * 控制中心
	 * @apiNote
	 * @return
	 */
	@GET
	@Path("/console")
	Response console(@Context HttpServletRequest request);
}
