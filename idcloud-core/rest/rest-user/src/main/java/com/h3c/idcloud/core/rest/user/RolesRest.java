/**
 *
 */
package com.h3c.idcloud.core.rest.user;

import java.util.List;

import javax.jws.WebMethod;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.h3c.idcloud.core.pojo.dto.user.Role;
import com.h3c.idcloud.core.pojo.dto.user.RoleModule;

import org.springframework.context.annotation.Scope;


/**
 * @author zharong
 */
@Path("/roles")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Scope("singleton")
public interface RolesRest {

    /**
     * 查询角色列表
     */
    @WebMethod
    @POST
    public Response findRole(Role role);

    /**
     * 查询角色列表
     */
    @WebMethod
    @POST
    @Path("/findRoleByName")
    public Response findRoleByName(Role role);

    /**
     * 新增角色
     */
    @WebMethod
    @POST
    @Path("/create")
    public Response createRole(Role role);

    /**
     * 更新角色
     */
    @WebMethod
    @POST
    @Path("/update")
    public Response updateRole(Role role);

    /**
     * 删除角色
     */
    @WebMethod
    @GET
    @Path("/delete/{roleSid}")
    public Response deleteRole(@PathParam("roleSid") Long roleSid);

    /**
     * 查询角色列表
     */
    @WebMethod
    @POST
    @Path("/add")
    public Response insertRoleModules(List<RoleModule> list);

    /**
     * 查询所有的权限
     */
    @WebMethod
    @GET
    @Path("/modules")
    public Response findAllModules(@QueryParam("roleSid") long roleSid,
                                   @QueryParam("moduleCategory") int moduleCategory);

}
