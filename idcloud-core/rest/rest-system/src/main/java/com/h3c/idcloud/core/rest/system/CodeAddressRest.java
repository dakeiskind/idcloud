package com.h3c.idcloud.core.rest.system;

import javax.jws.WebMethod;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by gujie on 2016/3/31.
 */
@Path("/getAddress")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface CodeAddressRest {

    /**
     * 查询省份
     * @return 返回值
     */
    @WebMethod
    @GET
    @Path("/getProvince")
    Response getProvince();

    /**
     * 查询城市
     * @param provinceId 省份Id
     * @return 返回值
     */
    @WebMethod
    @GET
    @Path("/getCityByProvince/{provinceId}")
    Response getCityByProvince(@PathParam("provinceId") Long provinceId);

    /**
     * 查询区
     * @param cityId 市Id
     * @return 返回值
     */
    @WebMethod
    @GET
    @Path("/getAreaByCity/{cityId}")
    Response getAreaByCity(@PathParam("cityId") Long cityId);
}
