package com.h3c.idcloud.core.rest.res;


import com.h3c.idcloud.core.pojo.dto.res.ResVe;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 资源环境接口
 *
 * Created by jpg on 2016/3/2.
 */
@Path("/ves")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface ResVeRest {

    /**
     * 查询ve列表
     *
     * @return
     */
    @POST
    Response selectVeByParams(ResVe resve);

    /**
     * 根据veSid查询资源环境信息
     *
     * @param resTopologySid
     * @return
     */
    @GET
    @Path("/{resTopologySid}")
    Response findResourceVeBySid(@PathParam("resTopologySid") String resTopologySid);

}
