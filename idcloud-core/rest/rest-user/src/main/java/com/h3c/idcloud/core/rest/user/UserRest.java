package com.h3c.idcloud.core.rest.user;


import com.h3c.idcloud.core.pojo.dto.user.User;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.List;

/**
 * 用户管理接口
 *
 * Created by qct on 2016/1/27.
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface UserRest {
//    int a=0;

    /**
     * 通过用户的id获取用户信息
     *
     * @param id 用户id
     * @return Response
     */
    @GET
    @Path("/swq/{id}")
    Response getUserByIdSwq(@PathParam("id") Long id);

    /**
     * 通过用户的id获取用户信息
     *
     * @param id 用户id
     * @return Response
     */
    @GET
    @Path("/{id}")
    Response getUserById(@PathParam("id") Long id);

    /**
     * 查询所有用户
     */

    @POST
    @Path("/findAll")
    Response findAllUser(User user);

    /**
     * 分页查询用户信息
     */

    @GET
    @Path("/findByPage")
    Response findByPage(@Context HttpServletRequest request);

    /**
     * 查询所有租户
     */

    @POST
    @Path("/platform/findAllTenant")
    Response findAllTenant();

    /**
     * 导出用户
     */

    @GET
    @Path("/exportUser/{params}")
    @Produces("application/vnd.ms-excel")
    Response exportUser(@PathParam("params") String params,
                        @Context HttpServletResponse servletResponse);

    /**
     * 用户登录
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/verify")
    Response verifyUser(User user, @Context HttpServletRequest servletRequest,
                        @Context HttpServletResponse servletResponse);


    /**
     * @param user username,password,captcha
     */
    @POST
    @Path("/login")
    Response loginUser(User user);

    @WebMethod
    @POST
    @Path("/logout")
    Response logoutUser(HttpServletRequest servletRequest, HttpServletResponse servletResponse);


    @GET
    @Path("/previlege/{userSid}")
    Response getUserPrevilege(@PathParam("userSid") Long userSid);


    @GET
    @Path("/current/{userSid}")
    Response current(@PathParam("userSid") Long userSid);

    /**
     * 新增前台用户
     */

    @POST
    @Path("/insertUser")
    Response insertUser(User user, @Context HttpServletRequest servletRequest)throws IOException;

    /**
     * 新增后台用户
     */

    @POST
    @Path("/platform/insertPlatformUser")
    Response insertPlatformUser(User user);

    /**
     * 更新后台用户
     */

    @POST
    @Path("/platform/updatePlatformUser")
    Response updatePlatformUser(User user);

    /**
     * 更新用户
     */

    @POST
    @Path("/updateUser")
    Response updateUser(User user);

    /**
     * 账号管理--更新用户基本信息
     */

    @POST
    @Path("/updateUserMess")
    Response updateUserMess(User user);

    /**
     * 删除用户
     */

    @GET
    @Path("/deleteUser")
    Response deleteUser(@QueryParam("userSids") String userSids);

    /**
     * 验证用户名是否重复
     */

    @GET
    @Path("/validateUserAccount")
    Response validateUserAccount(@QueryParam("account") String account);

    /**
     * 验证邮箱是否重复
     */

    @GET
    @Path("/validateUserEmail")
    Response validateUserEmail(@QueryParam("email") String email);

    /**
     * 验证邮箱是否重复
     */

    @GET
    @Path("/validateUserMobile")
    Response validateUserMobile(@QueryParam("mobile") String mobile);


    /**
     * 验证相关数据在用户表是否已经被使用
     * @param validateType account mobile  email 可接受类型
     * @param validateData 实际值
     * @return
     */
    @GET
    @Path("/validateExits")
    Response validateUserExits(@QueryParam("isjQuery") boolean isjQuery,
                               @QueryParam("validateType") String validateType, @QueryParam("validateData") String validateData);

    /**
     * 验证密码是否统一
     */

    @GET
    @Path("/validateUserPassword/{password}/{userSid}")
    Response validateUserPassword(@PathParam("password") String password,
                                  @PathParam("userSid") String userSid);

    /**
     * 验证企业简写是否重复
     */

    @GET
    @Path("/validateTenantShortName")
    Response validateTenantShortName(@QueryParam("tenantShortName") String tenantShortName);

    /**
     * 查询该用户所属租户
     */

    @POST
    @Path("/findUsers")
    Response findUsers(String params);

    /**
     * 启用、禁用用户
     */

    @GET
    @Path("/operationUser")
    Response operationUser(@QueryParam("userSids") String userSids,
                           @QueryParam("action") String action);

    /**
     * 用户修改密码
     */

    @POST
    @Path("/modifyPassword")
    Response modifyPassword(String params);

    /**
     * 找回密码
     *
     * @param account 用户账号
     * @return 随机六位数密码
     */

    @GET
    @Path("/findLostPwd")
    Response findLostPwd(@QueryParam("account") String account);

    /**
     * 注册用户
     */

    @POST
    @Path("/registerUser")
    Response registerUser(String params);

    /**
     * 密码找回时，验证用户是否存在
     */

    @GET
    @Path("/findLostPwd/userAtFindLostPwd")
    Response userAtFindLostPwd(@QueryParam("phone") String phone,
                                   @QueryParam("email") String email);

    /**
     * 密码找回时，修改密码
     */

    @POST
    @Path("/findLostPwd/modifyPwdAtFindLostPwd")
    Response modifyPwdAtFindLostPwd(String params);

    /**
     * 验证用户是否存在并修改用户状态为正常
     */

    @GET
    @Path("/activate")
    Response testUser(@QueryParam("id") long id, @QueryParam("securityKey") String securityKey);

    /**
     * 密码找回时，验证用户是否存在
     */

    @GET
    @Path("/testUserAtEmail")
    Response testUserAtEmail(@QueryParam("userSid") long userSid,
                             @QueryParam("passWord") String password);

    /**
     * 查询所有角色
     */

    @POST
    @Path("/findAllRole")
    Response findAllRole();

    /**
     * 根据用户查询菜单
     */
    @GET
    @Path("/platform/findMoudules/{userSid}")
    Response findModules(@PathParam("userSid") Long userSid);

//    /**
//     * 查询该用户所属租户
//     */
//
//    @POST
//    @Path("/findTenantUsers")
//    Response findTenantUsers(String params);

    /**
     * 查询用户中心该用户下的子用户
     */
    @WebMethod
    @POST
    @Path("findTenantUsers")
    Response findTenantUsers(String params, @Context HttpServletRequest servletRequest) throws
                                                                                        IOException;


    /**
     * 根据角色类型(01 前台角色 02 后台角色)，查询角色列表
     */

    @POST
    @Path("/platform/roles/")
    Response findRoles(String params);

    /**
     * 根据帐号，查询业务属性和业务名称
     */

    @POST
    @Path("/findBizType")
    Response findBizType(String account);

    /**
     * 根据二级业务id，查询是否存在用户
     */

    @POST
    @Path("/findUserByBizId")
    Response findUserByBizId(String params);

    /**
     * 注册租户
     */

    @POST
    @Path("/registerTenants")
    Response registerTenants(String params);


    @GET
    @Path("/findUsersByMgtObjSid/{mgtObjSid}")
    Response findUsersByMgtObjSid(@PathParam("mgtObjSid") Long mgtObjSid);


    @GET
    @Path("/findManagerByMgtObjSid/{mgtObjSid}")
    Response findManagerByMgtObjSid(@PathParam("mgtObjSid") Long mgtObjSid);


    @POST
    @Path("/findUsersByParams")
    Response findUsersByParams(String params);


    @GET
    @Path("/findAllUsersByMgtObj/{mgtObjSid}")
    List findAllUsersByMgtObj(@PathParam("mgtObjSid") Long mgtObjSid);

    /**
     * 注册前台账户
     */
    @WebMethod
    @POST
    @Path("/registerProtalUser")
    Response registerProtalUser(String params);

    /**
     * 更新前台账户
     */
    @WebMethod
    @POST
    @Path("/updateProtalUser")
    Response updateProtalUser(String params);

    /**
     * 用户仪表盘相关信息
     */
    @WebMethod
    @GET
    @Path("/getUserInfo/{userSid}")
    Response getUserInfo(@PathParam("userSid") Long userSid);

    /**
     *更新前台的邮箱或者电话
     */
    @WebMethod
    @POST
    @Path("/updateUserInfo")
    Response updateUserInfo(String params, @Context HttpServletRequest servletRequest );

}
