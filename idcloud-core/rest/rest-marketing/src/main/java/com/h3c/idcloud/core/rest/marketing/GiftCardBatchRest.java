package com.h3c.idcloud.core.rest.marketing;



import com.h3c.idcloud.core.pojo.dto.marketing.GiftCardBatch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Title:
 * Company:   HP
 * Copyright: Copyright (c) 2012
 * Description:
 * <Class Description >
 *
 * @author andre created at 14:18 2014/8/7
 */

@Path("/giftCardBatchs")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface GiftCardBatchRest {
    /**
     * 查询礼品卡批次
     * @param request
     * @return return response with List<GiftCardBatch>
     */
    @GET
    @Path("/getGiftCardBatchs")
    Response getGiftCardBatchs(@Context HttpServletRequest request);

    /**
     * 创建礼品卡批次
     * @param giftCardBatch
     * @return return response with giftCardBatchSid
     */
    @POST
    @Path("/addGiftCardBatch")
    Response addGiftCardBatch(GiftCardBatch giftCardBatch);

    /**
     *更新礼品卡批次
     * @param giftCardBatch
     * @return Response with true/false
     */
    @PUT
    Response updateGiftCardBatch(GiftCardBatch giftCardBatch);

    /**
     * 整批生成礼品卡
     * @param giftCardBatchSid
     * @return return response with true/false
     */
    @Path("/{giftCardBatchSid}/genGiftCard")
    @POST
    Response genGiftCard(@PathParam("giftCardBatchSid") Long giftCardBatchSid);

     /**
     *激活整批礼品卡
     * @param giftCardBatchSid
     * @return true/false
     */
    @PUT
    @Path("/{giftCardBatchSid}/activateGiftCard")
    Response activateGiftCardBatch(@PathParam("giftCardBatchSid") Long giftCardBatchSid);

     /**
     *作废礼品卡批次
     * @param giftCardBatchSid
     * @return Response with success/failure
     */
    @DELETE
    @Path("/{giftCardBatchSid}/invalid")
    Response invalidGiftCardBatch(@PathParam("giftCardBatchSid") Long giftCardBatchSid);

    /**
     * 获取单个礼品卡批次
     * @param giftCardBatchSid
     * @return return response with entity of GiftCardBatch
     */
    @GET
    @Path("/{giftCardBatchSid}")
    Response getGiftCardBatch(@PathParam("giftCardBatchSid") Long giftCardBatchSid);
    
    @GET
    @Path("/getAvailableBatchs")
    Response getAvailableBatchs(@Context HttpServletRequest request, @Context HttpServletResponse response);


}
