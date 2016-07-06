/**
 *
 */
package com.h3c.idcloud.core.rest.marketing;


import com.h3c.idcloud.core.pojo.dto.marketing.DepositPrize;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author shiw
 */
@Path("/depositPrize")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface BillingDepositPrizeRest {

    /**
     * 查询充值奖励优惠
     *@param   request
     * @return
     */
    @GET
    @Path("/findAll")
     Response findAll(@Context HttpServletRequest  request);

    /**
     * 创建充值奖励优惠
     *
     * @return return response with depositPrizeSid
     */
    @POST
    @Path("/addDepositPrize")
     Response addDepositPrize(DepositPrize depositPrize);

    /**
     * 删除充值奖励优惠
     */
    @POST
    @Path("/delete/{depositPrizeSid}")
     Response removeDepositPrize(@PathParam("depositPrizeSid") Long depositPrizeSid);

    /**
     * 更新充值奖励优惠
     *
     * @return Response with true/false
     */
    @POST
    @Path("/updateDepositPrize")
     Response updateDepositPrize(DepositPrize depositPrize);


}
