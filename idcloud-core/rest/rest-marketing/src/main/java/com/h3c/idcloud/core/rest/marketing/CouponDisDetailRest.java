package com.h3c.idcloud.core.rest.marketing;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Administrator on 2016/4/14.
 */

@Path("/couponDis")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface CouponDisDetailRest {

    /**
     * 绑定优惠券
     * @param data
     * @return
     */
    @POST
    @Path("/bindingBydistributionDetailSid")
    Response  bindingBydistributionDetailSid(Map<String, Object> data);

}
