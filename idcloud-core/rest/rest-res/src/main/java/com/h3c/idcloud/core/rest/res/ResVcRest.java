package com.h3c.idcloud.core.rest.res;


import com.h3c.idcloud.core.pojo.dto.res.ResVc;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 资源集群接口
 *
 * Created by jpg on 2016/3/2.
 */
@Path("/vcs")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface ResVcRest {

    /**
     * 查询vc列表
     *
     * @return
     */
    @POST
    Response selectVcByParams(ResVc resvc);

    /**
     * 根据sid查询vc信息
     *
     * @param resTopologySid
     * @return
     */
    @GET
    @Path("/{resTopologySid}")
    public Response findResourceVcBySid(@PathParam("resTopologySid") String resTopologySid);



}
