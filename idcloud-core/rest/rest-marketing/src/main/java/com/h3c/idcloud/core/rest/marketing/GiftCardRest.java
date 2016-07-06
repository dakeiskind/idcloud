package com.h3c.idcloud.core.rest.marketing;

import com.h3c.idcloud.core.pojo.dto.marketing.GiftCard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Title:
 * Company:   HP
 * Copyright: Copyright (c) 2012
 * Description:
 * <Class Description >
 *
 * @author andre created at 14:18 2014/8/7
 */

@Path("/giftCards")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface GiftCardRest {
    /**
     * 查询礼品卡
     * @param request
     * @return return response with List<GiftCard>
     */
	@GET
    @Path("getGiftCards")
    Response getGiftCards(@Context HttpServletRequest request);
    
    @POST
	@Path("/findGiftCards")
    Response findGiftCards(String param);

    /**
     * 导出指定的礼品卡
     * @param request
     * @param response
     * @param giftCardSids 礼品卡sid，多个以","分隔
     * @return return response with excel File
     */
    @GET
    @Path("/exportSpecified")
    @Produces(MediaType.APPLICATION_JSON)
    Response exportSpecifiedGiftCards(@Context HttpServletRequest request, @Context HttpServletResponse response, @QueryParam("giftCardSids") String giftCardSids);

    /**
     * 导出查村到的所有的礼品卡
     * @param request
     * @param response
     * @return return response with excel File
     */
    @GET
    @Path("/exportQueried")
    @Produces(MediaType.APPLICATION_JSON)
    Response exportQueriedGiftCards(@Context HttpServletRequest request, @Context HttpServletResponse response);

    /**
     *批量激活一批指定的礼品卡
     * @param giftCardsSids 礼品卡sid串，以","隔开
     * @return Response with true/false
     */
    @PUT
    @Path("/activate")
    Response activateGiftCards(@QueryParam("giftCardSids") String giftCardsSids);

	@POST
	@Path("/bindingByCardSids")
    Response bindingByCardSids(Map<String, Object> data);
     /**
     *作废指定的礼品卡
     * @param giftCardSid 礼品卡sid
     * @return Response with true/false
     */
    @PUT
    @Path("/{giftCardSid}/invalid")
    Response invalidGiftCard(@PathParam("giftCardSid") Long giftCardSid);

    /**
     * 获取单个礼品卡
     * @param giftCardSid
     * @return return response with entity of GiftCard
     */
    @GET
    @Path("/{giftCardSid}")
    Response getGiftCard(@PathParam("giftCardSid") Long giftCardSid);
    
    /**
     * 充值卡充值
     * @param card
     * @return return response with entity of GiftCard
     */
    @POST
    @Path("/depositByCard")
    Response depositByCard(GiftCard card);
    
	@GET
	@Path("/getValidCardsByBatchSid")
    Response getValidCardsByBatchSid(@Context HttpServletRequest request, @Context HttpServletResponse response);

    @GET
    @Path("/displayGiftCards")
    public Response displayGiftCards(@QueryParam("validStart") String validStart,
                                          @QueryParam("validTo") String validTo,
                                          @QueryParam("status") String status,
                                          @Context HttpServletRequest request,
                                          @Context HttpServletResponse response);

    /**
     * 获取被用户绑定的礼品卡
     * @param request
     * @param response
     * @return
     */
    @GET
    @Path("/getGiftBinding")
    public Response getGiftBinding(@Context HttpServletRequest request, @Context
    HttpServletResponse response);

    @GET
    @Path("/getAvailableBatchs")
    Response getAvailableBatchs(@Context HttpServletRequest request, @Context HttpServletResponse response);
}
