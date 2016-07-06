
package com.h3c.idcloud.core.rest.marketing;

import com.h3c.idcloud.core.pojo.dto.marketing.AccountLevel;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author gujie
 */
@Path("/level")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface AccountLevelRest {

	/**
	 * 查询所有用户等级
	 * @param request 参数
	 * @return 返回值
	 */
	@GET
	@Path("/find")
	Response findLevel(@Context HttpServletRequest request);

	/**
	 * 查询所有用户等级
	 * @param level 参数
	 * @return 返回值
	 */
	@WebMethod
	@POST
	@Path("/findAll")
	Response findAllLevel(AccountLevel level);
	
	/**
	 * 更新用户等级
	 * @param level 参数
	 * @return 返回值
	 */
	@WebMethod
	@POST
	@Path("/updateLevel")
	Response updateLevel(AccountLevel level);
	
	/**
	 * 删除用户等级
	 * @param level 参数
	 * @return 返回值
	 */
	@WebMethod
	@POST
	@Path("/deleteLevel")
	Response deleteLevel(AccountLevel level);

	/**
	 * 新增用户等级
	 * @param level 参数
	 * @return 返回值
	 */
	@WebMethod
	@POST
	@Path("/insertLevel")
	Response insertLevel(AccountLevel level);
	
	
	/**
	 * 获得用户的等级折扣
	 * @param request 参数
	 * @return 返回值
	 * 
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/findLevenDiscount")
	Response findLevenDiscount(@Context HttpServletRequest request);

}
