/**
 *
 */
package com.h3c.idcloud.core.rest.marketing;


import com.h3c.idcloud.core.pojo.dto.marketing.Agreement;

import javax.jws.WebMethod;
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
 * @author ranchao
 */
@Path("/agreement")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface AgreementRest {

    /**
     * 查询所有大客户协议
     */
    @GET
    @Path("/find")
    Response find(@Context HttpServletRequest request);

    /**
     * 查询所有大客户协议
     */
    @WebMethod
    @POST
    @Path("/findAll")
    Response findAllAgreement(Agreement agreement);

    /**
     * 新增大客户协议
     */
    @WebMethod
    @POST
    @Path("/insertAgreement")
    public Response insertAgreement(Agreement agreement);

    /**
     * 更新大客户协议
     */
    @WebMethod
    @POST
    @Path("/updateAgreement")
    public Response updateAgreement(Agreement agreement);

    /**
     * 删除大客户协议
     */
    @WebMethod
    @POST
    @Path("/deleteAgreement/{agreementSid}")
    public Response deleteAgreement(@PathParam("agreementSid") Long agreementSid);

}
