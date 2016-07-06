package com.h3c.idcloud.core.rest.customer;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.h3c.idcloud.core.pojo.dto.customer.Issue;
/**
 * 客户工单管理接口
 * @author gujie
 */
@Path("/issue")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface IssueRest {

    /**
     * 列出所有问题
     * @return 返回值
     */
    @WebMethod
    @GET
    @Path("/findAll")
    Response findAll();

    /**
     * 根据条件查询问题
     * @param issue 根据条件查询数据
     * @return 返回值
     */
    @WebMethod
    @POST
    @Path("/find")
    Response find(@Context HttpServletRequest request, Issue issue);

    /**
     * 创建新问题
     *
     * @param  issue 新建问题
     * @return 返回值
     */
    @WebMethod
    @POST
    @Path("/create")
    Response create(@Context HttpServletRequest request, Issue issue);

    /**
     * 更新问题
     *
     * @param issue 更新数据
     * @return 返回值
     */
    @WebMethod
    @POST
    @Path("/update")
    Response update(@Context HttpServletRequest request,Issue issue);

}
