package com.h3c.idcloud.core.rest.customer;

import com.h3c.idcloud.core.pojo.dto.customer.IssueReply;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by gujie on 2016/3/24.
 */

@Path("/issueReply")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface IssueReplyRest {

    /**
     * 查询问题回复记录
     *
     * @return 返回值
     */
    @WebMethod
    @GET
    @Path("/findReply/{issueSid}")
    Response findReply(@PathParam("issueSid") String issueSid);

    /**
     * 回复问题
     *
     * @param issueReply 回复问题数据
     * @return 返回值
     */
    @WebMethod
    @POST
    @Path("/reply")
    Response reply(@Context HttpServletRequest request, IssueReply issueReply);
}
