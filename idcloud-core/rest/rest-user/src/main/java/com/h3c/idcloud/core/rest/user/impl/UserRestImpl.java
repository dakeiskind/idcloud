package com.h3c.idcloud.core.rest.user.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.product.MgtObjRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.security.UserToken;
import com.h3c.idcloud.core.pojo.dto.system.MailTemplate;
import com.h3c.idcloud.core.pojo.dto.system.MgtObjExt;
import com.h3c.idcloud.core.pojo.dto.system.UserMgtObjKey;
import com.h3c.idcloud.core.pojo.dto.user.Module;
import com.h3c.idcloud.core.pojo.dto.user.Role;
import com.h3c.idcloud.core.pojo.dto.user.RoleModule;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.dto.user.UserRole;
import com.h3c.idcloud.core.pojo.vo.user.UserVo;
import com.h3c.idcloud.core.rest.user.UserRest;
import com.h3c.idcloud.core.service.product.api.MgtObjResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.system.api.MailNotificationsService;
import com.h3c.idcloud.core.service.system.api.MailTemplateService;
import com.h3c.idcloud.core.service.system.api.UserMgtObjService;
import com.h3c.idcloud.core.service.system.api.UserTopicService;
import com.h3c.idcloud.core.service.user.api.ModuleService;
import com.h3c.idcloud.core.service.user.api.RoleModuleService;
import com.h3c.idcloud.core.service.user.api.RoleService;
import com.h3c.idcloud.core.service.user.api.UserRoleService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.MyError;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.constants.RestConst;
import com.h3c.idcloud.infrastructure.common.exception.BizException;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.BaseGridReturn;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.InterfaceResult;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.pojo.SimpleRestResult;
import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import com.h3c.idcloud.infrastructure.common.util.FileUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.MailUtil;
import com.h3c.idcloud.infrastructure.common.util.POIUtil;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.RequestContextUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.xml.bind.v2.TODO;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.*;

/**
 * Created by qct on 2016/1/27.
 */
@Component
public class UserRestImpl implements UserRest {

    Logger log = Logger.getLogger(this.getClass());

    /**
     * 用户Service
     */
    @Reference(version = "1.0.0")
    UserService userService;

    /**
     * 用户角色关联Service
     */
    @Reference(version = "1.0.0")
    UserRoleService userRoleService;

    /**
     * 角色Service
     */
    @Reference(version = "1.0.0")
    RoleService roleService;

    /**
     * 权限Service
     */
    @Reference(version = "1.0.0")
    ModuleService moduleService;

    /**
     * 邮件模板Session
     */
    @Reference(version = "1.0.0")
    MailTemplateService mailTemplateService;

    /** 租户Service */
//    @Reference(version = "1.0.0")
//    private TenantService tenantService;

    /**
     * 邮件Service
     */
    @Reference(version = "1.0.0")
    private MailNotificationsService mailNotificationsService;

    @Reference(version = "1.0.0")
    private MgtObjResService mgtObjResService;

    @Reference(version = "1.0.0")
    private UserMgtObjService userMgtObjService;

    @Reference(version = "1.0.0")
    private UserTopicService userTopicService;

    @Reference(version = "1.0.0")
    private ServiceInstanceService instanceService;

    @Reference(version = "1.0.0")
    private RoleModuleService roleModuleService;

    @Override
    public Response getUserByIdSwq(Long id) {
        UserVo userVo = this.userService.queryUserByIdSwq(id);
        log.debug("---------------------------------getUserByIdSwq--------");
        return Response.ok(userVo).build();
    }

    @Override
    public Response getUserById(Long id) {
        User user = this.userService.selectByPrimaryKey(id, 1);
        if (user == null) {
            throw new BizException(RestConst.BizError.RES_NOT_FOUND, "没有找到用户信息");
        }
//        UserVo userVo = new UserVo();
//        BeanUtils.copyProperties(user, userVo);
        return Response.ok(user).build();
    }

    /**
     * 查询所有用户
     */
    @Override
    public Response findAllUser(User user) {

        Criteria example = new Criteria();
        if (user != null) {
            if (!StringUtil.isNullOrEmpty(user.getAccount())) {
                example.put("accountLike", user.getAccount());
            }
            if (!StringUtil.isNullOrEmpty(user.getRealName())) {
                example.put("realNameLike", user.getRealName());
            }
            if (!StringUtil.isNullOrEmpty(user.getUserType())) {
                example.put("userType", user.getUserType());
            }
            if (!StringUtil.isNullOrEmpty(user.getRoleSid())) {
                example.put("roleSid", user.getRoleSid());
            }
            if (!StringUtil.isNullOrEmpty(user.getStatus())) {
                example.put("status", user.getStatus());
            }
            if (!StringUtil.isNullOrEmpty(user.getTenantSid())) {
                example.put("tenantSid", user.getTenantSid());
            }
            if (!StringUtil.isNullOrEmpty(user.getAccountRepeat())) {
                example.put("account", user.getAccountRepeat());
            }
            if (!StringUtil.isNullOrEmpty(user.getOrgSid())) {
                example.put("orgSid", user.getOrgSid());
            }
            if (!StringUtil.isNullOrEmpty(user.getUserSid())) {
                example.put("userSid", user.getUserSid());
            }
        }
        List<User> list = this.userService.selectByParams(example);
        String json = JsonUtil.toJson(list);
        return Response.ok(new RestResult(json)).build();
    }

    /**
     * 导出用户
     */
    @Override
    public Response exportUser(@PathParam("params") String params,
                               @Context HttpServletResponse servletResponse) {
        Criteria example = new Criteria();
        Map<String, Object> conditionMap = new HashMap<String, Object>();

        conditionMap = JsonUtil.fromJson(params, Map.class);

        if (!StringUtil.isNullOrEmpty(conditionMap.get("accountLike"))) {
            example.put("accountLike", conditionMap.get("accountLike").toString());
        }
        if (!StringUtil.isNullOrEmpty(conditionMap.get("realNameLike"))) {
            example.put("realNameLike", conditionMap.get("realNameLike").toString());
        }
        if (!StringUtil.isNullOrEmpty(conditionMap.get("userType"))) {
            example.put("userType", conditionMap.get("userType").toString());
        }
        if (!StringUtil.isNullOrEmpty(conditionMap.get("roleSid"))) {
            example.put("roleSid", conditionMap.get("roleSid").toString());
        }
        if (!StringUtil.isNullOrEmpty(conditionMap.get("status"))) {
            example.put("status", conditionMap.get("status").toString());
        }
        if (!StringUtil.isNullOrEmpty(conditionMap.get("bizTextLike"))) {
            example.put("bizTextLike", conditionMap.get("bizTextLike").toString());
        }
        example.setOrderByClause("C.CODE_DISPLAY  DESC, A.REAL_NAME");
        List<User> list = this.userService.selectByParams(example);

        POIUtil excelUtil = new POIUtil();
        //1.显示的表头文本
        String[]
                headerTitle =
                new String[]{"用户账号", "用户姓名", "用户类型", "所属角色", "所属项目", "电话", "邮箱", "状态"};
//	    String[] headerTitle = new String[] { "用户账号", "用户姓名", "用户类型", "所属角色", "所属项目", "电话","邮箱","经理姓名","经理电话","状态"};
//	    String[] headerTitle = new String[] { "用户账号", "用户姓名", "用户类型", "所属角色", "所属项目","所属部门", "电话","邮箱","经理姓名","经理电话","状态"};
        //2.表头对应的字段
        String[] headerField = new String[]{"account", "realName", "userTypeName", "roleName",
                                            "projectName", "mobile", "email", "statusName"};
//	    String[] headerField = new String[] {"account", "realName", "userTypeName", "roleName",
//	    		"bizText","orgName", "mobile", "email", "pmName", "pmTel", "statusName"};
        //3.设置表头每列的宽度
        short[]
                headerWidth =
                new short[]{15 * 256, 15 * 256, 10 * 256, 15 * 256, 35 * 256, 35 * 256, 35 * 256,
                            12 * 256};

        //4.设置显示的具体数据列表
        List<Map> dataList = new ArrayList<Map>();
        Map<String, String> dataMap = null;
        User user = null;
        for (int i = 0; i < list.size(); i++) {
            user = list.get(i);
            dataMap = new HashMap<String, String>();
            dataMap.put("account", user.getAccount());
            dataMap.put("realName", user.getRealName());
            dataMap.put("userTypeName", user.getUserTypeName());
            dataMap.put("roleName", user.getRoleName());
            dataMap.put("projectName", user.getProjectName());
//					dataMap.put("bizText",user.getBizText());
//					dataMap.put("orgName",user.getOrgName());
            dataMap.put("mobile", user.getMobile());
            dataMap.put("email", user.getEmail());
//					dataMap.put("pmName", user.getPmName());
//					dataMap.put("pmTel", user.getPmTel());
            dataMap.put("statusName", user.getStatusName());
            dataList.add(dataMap);
        }

        //5.sheet名称，可以多个sheet
        String[] sheetName = new String[]{"用户列表"};
        //6.输出的文件名称
        String date = StringUtil.dateFormat(new Date(), StringUtil.DF_YMD);
        String systemName = PropertiesUtil.getProperty("system.name");
        String fileName = "" + systemName + "-用户列表-" + date + ".xls";
        //7.调用通用方法，生成excel文件
        Integer[] type = new Integer[]{};
        excelUtil
                .doFromDataToExecl(servletResponse, headerTitle, headerField, headerWidth, dataList,
                                   sheetName, fileName, type);

        return null;

    }

    /**
     * 查询所有用户
     */
    @Override
    public Response findUserByBizId(String params) {
        String json = "";

        Criteria criteria = new Criteria();
        Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
        if (!map.isEmpty()) {
            criteria.setCondition(map);
        }
        List<User> list = this.userService.selectByParams(criteria);
        json = JsonUtil.toJson(list);
            /*if(list.isEmpty()) {
                json = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.ERROR_BIZ_USER_CHECK), null));
            }
            else {

            }*/
        return Response.ok().build();
    }

    /**
     * 分页查询用户信息
     */
    @Override
    public Response findByPage(@Context HttpServletRequest request) {

        // 参数设置
        Criteria param = new Criteria();
        WebUtil.preparePageParams(request, param,
                                  "CASE A.`STATUS` WHEN 2 THEN 0 ELSE 1 END,A.ACCOUNT");

        // 查询数据
        List<User> list = this.userService.selectByParams(param);
        int total = this.userService.countByParams(param);

        String json = JsonUtil.toJson(new BaseGridReturn(total, list));

        return Response.ok(json).build();
    }

    @Override
    public Response verifyUser(User user, @Context HttpServletRequest servletRequest,
                               @Context HttpServletResponse servletResponse) {
//        Response response = Response.status(500)
//                .entity(JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, "服务器异常，请联系管理员。", null))).build();
//        ;

        HttpSession session = servletRequest.getSession();
        String platform = servletRequest.getHeader("PLATFORM");
        // 获取用户名密码
        String account = user.getAccount().trim();
        String password = user.getPassword();
        // 用户名密码为空返回提示
        if (StringUtil.isNullOrEmpty(account) || StringUtil.isNullOrEmpty(password)) {
            throw new BizException(RestConst.BizError.UNKNOWN_ERROR, "请输入用户名和密码。");
        }
        Criteria critera = new Criteria();
        critera.put("account", account);
        critera.put("password", WebUtil.encrypt(password, account));
        critera.put("userType", user.getUserType());
        // 根据用户名密码查询用户信息
        List<User> users = userService.selectByParams(critera);
        if (users.size() > 0) {
            User currentUser = users.get(0);
            // 判断当前用户是否合法
            if (WebConstants.USER_TYPE.BACKGROUND.equals(currentUser.getUserType())) {
                if (WebConstants.UserStatus.AVAILABILITY.equals(currentUser.getStatus())) {
                    // 保存Session和Cookie
                    String json = "";
                    if (WebConstants.PlatformName.PORTAL.equals(platform)) {
                        json =
                                createSession(session, servletResponse,
                                              WebConstants.CURRENT_PORTAL_USER, currentUser);
                    } else {
                        json =
                                createSession(session, servletResponse,
                                              WebConstants.CURRENT_PLATFORM_USER, currentUser);
                    }
                    return Response.ok(json).build();
                } else {
                    throw new BizException(RestConst.BizError.RES_NOT_FOUND, "用户被禁用。");
                }
            } else if (WebConstants.USER_TYPE.FOREGROUND.equals(currentUser.getUserType())
                       && WebConstants.PlatformName.PORTAL.equals(platform)) {
                // 查询消费门户用户所属租户信息
//                Tenant tenant = this.tenantService.selectByPrimaryKey(currentUser.getTenantSid());
                if (WebConstants.UserStatus.AVAILABILITY.equals(currentUser.getStatus())) {
//					&& WebConstants.TenantStatus.NORMAL.equals(tenant.getStatus())
                    // 保存Session和Cookie
                    String
                            json =
                            createSession(session, servletResponse,
                                          WebConstants.CURRENT_PORTAL_USER, currentUser);
                    return Response.ok(json).build();
                } else if (WebConstants.UserStatus.NOTAPPROVE.equals(currentUser.getStatus())) {
//					String json = "该用户正在审核中！";
//					response = Response.status(Status.OK).entity(json).build();
                    throw new BizException(RestConst.BizError.UNKNOWN_ERROR, "该用户正在审核中！");
                } else {
                    throw new BizException(RestConst.BizError.UNKNOWN_ERROR, "用户名或密码错误。");
                }
            } else {
                throw new BizException(RestConst.BizError.UNKNOWN_ERROR, "用户名或密码错误。");
            }
        } else {
            throw new BizException(RestConst.BizError.UNKNOWN_ERROR, "用户名或密码错误。");
        }

//        return Response.ok().build();
    }

    @Override
    public Response loginUser(User user) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        // 查询用户
        UserToken token = userService.login(user);
        if (StringUtil.isNullOrEmpty(token)) {
            resultMap.put("resultStatus", "false");
            resultMap.put("data", "用户名或密码错误");
            return Response.ok(new RestResult(RestResult.Status.FAILURE
                    ,null,resultMap)).build();
        } else if (token.getAccessToken().equals("02") || token.getAccessToken().equals("03")
                   || token.getAccessToken().equals("04")) {
            resultMap.put("resultStatus", "false");
            resultMap.put("data", "用户名或密码错误");
            return Response.ok(new RestResult(RestResult.Status.FAILURE
                    ,null,resultMap)).build();
        }
        resultMap.put("resultStatus", "true");
        resultMap.put("data", token);
        return Response.ok(new RestResult(resultMap)).build();
    }

    @Override
    public Response logoutUser(@Context HttpServletRequest servletRequest,
                               @Context HttpServletResponse servletResponse) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            HttpSession session = servletRequest.getSession(false);
            if (session != null && session.getId() != null && !"".equals(session.getId())) {
                Enumeration e = session.getAttributeNames();
                while (e.hasMoreElements()) {
                    String sessionName = (String) e.nextElement();
                    log.debug("存在的session有：" + sessionName);
                    session.removeAttribute(sessionName);
                }
                session.invalidate();
                resultMap = new HashMap<String, Object>();
            }
            resultMap.put("resultStatus", "true");
        } catch (Exception e) {
            log.error("用户logout出错!", e);
            resultMap.put("resultStatus", "false");
        }
        return Response.ok(resultMap).build();
    }

    @Override
    public Response getUserPrevilege(@PathParam("userSid") Long userSid) {
        List<Module>
                modules =
                moduleService.selectByUserSid(userSid, WebConstants.ModuleCategory.PROTAL);
        ;
        String
                json =
                JsonUtil.toJson(
                        new RestResult(RestConst.HttpConst.OK, RestResult.Status.SUCCESS, null,
                                       modules));
        return Response.ok(json).build();
    }

    @Override
    public Response current(@PathParam("userSid") Long userSid) {
        User user = userService.selectByPrimaryKey(userSid, 1);
        System.err.println(user.getPassword());
        System.err.println(WebUtil.decryptBase64(user.getPassword()));
        String json = JsonUtil.toJson(new RestResult(user));

        return Response.ok(JsonUtil.toJson(json)).build();
    }

    /**
     * 新增用户
     */
    @Override
    public Response insertUser(User user, @Context HttpServletRequest servletRequest)
            throws IOException {
        AuthUser authUser = RequestContextUtil.getAuthUserInfo(servletRequest);
        String json = "";
//        user.setUserType(authUser.getUserType());
//        user.setStatus(authUser.getStatus());
        user.setMgtObjSid(authUser.getMgtObjSid());
        user.setCreatedBy(authUser.getAccount());
        user.setCreatedDt(new Date());
        user.setUpdatedBy(authUser.getAccount());
        user.setUpdatedDt(new Date());
        boolean result = this.userService.insertUser(user);
//        this.userService.

//        UserRole userRole = new UserRole();
//        userRole.setUserSid(user.getUserSid());
//        userRole.setRoleSid(WebConstants.RoleSid.T_USER);
//        this.userRoleService.insertSelective(userRole);
        if (result) {
//            json = InterfaceResult.setResult(WebConstants.ResultStatus.SUCCESS, null);
//            return Response.ok(json).build();
            json = JsonUtil.toJson(
                    new RestResult(RestResult.Status.SUCCESS,
                                   WebUtil.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS)));
        } else {
//            json = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
//            return Response.ok(json).build();
            json = JsonUtil.toJson(
                    new RestResult(RestResult.Status.FAILURE,
                                   WebUtil.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
        }
        return Response.ok(json).build();

    }

    /**
     * 更新用户
     */
    @Override
    public Response updateUser(User user) {
        String returnJson = "";
        WebUtil.prepareUpdateParams(user);
        int result = this.userService.updateByPrimaryKeySelective2(user);
        if (1 == result) {
            if (user.getUserMgtObj() == null) {
                returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                        .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS), null));
            } else {
                UserMgtObjKey userMgt = new UserMgtObjKey();
                userMgt.setMgtObjSid(
                        user.getUserMgtObj() == null ? null : Long.valueOf(user.getUserMgtObj()));
                userMgt.setUserSid(user.getUserSid());
                int result1 = this.userMgtObjService.updateByPrimaryKeySelective(userMgt);
                if (result1 == 1) {
                    returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                            .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS), null));
                } else {
                    returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                            .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE), null));
                }
            }
        }
        return Response.ok(returnJson).build();
    }

    /**
     * 更新用户
     */
    @Override
    public Response updateUserMess(User user) {
        String returnJson = "";
        WebUtil.prepareUpdateParams(user);
        int result = this.userService.updateByPrimaryKeySelective2(user);
        if (1 == result) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS), null));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE), null));
        }
        return Response.ok(returnJson).build();
    }


    @Override
    public Response deleteUser(@QueryParam("userSids") String userSids) {
        String json = "";
        boolean result = false;
        System.out.println(Long.parseLong(userSids));
//        String[] sids = userSids.split(",");
//        if (sids != null && sids.length > 0) {
//            for (int i = 0; i < sids.length; i++) {
//                result = this.userService.deleteUser(Long.parseLong(sids[i]));
//            }
//        }
        result = this.userService.deleteUser(Long.parseLong(userSids));
        if (result) {
            json = JsonUtil.toJson(
                    new RestResult(RestResult.Status.SUCCESS,
                                   WebUtil.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS)));
        } else {
            json = JsonUtil.toJson(
                    new RestResult(RestResult.Status.FAILURE,
                                   WebUtil.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
        }
        return Response.ok(json).build();
    }

    /**
     * 验证用户名是否重复
     */
    @Override
    public Response validateUserAccount(@QueryParam("account") String account) {

        Criteria criteria = new Criteria();
        criteria.put("account", account);
        List<User> userList = this.userService.selectByParams(criteria);
        if(StringUtil.isNullOrEmpty(userList) || userList.size() == 0)
            return Response.ok(true).build();
        return Response.ok(false).build();

    }

    /**
     * 验证用户名是否重复
     */
    @Override
    public Response validateUserEmail(@QueryParam("email") String email) {
        Criteria criteria = new Criteria();
        criteria.put("email", email);
        List<User> userList = this.userService.selectByParams(criteria);
        if(StringUtil.isNullOrEmpty(userList) || userList.size() == 0)
            return Response.ok(true).build();
        return Response.ok(false).build();
    }

    /**
     * 验证手机是否重复
     */
    @Override
    public Response validateUserMobile(@QueryParam("mobile") String mobile) {
        Criteria criteria = new Criteria();
        criteria.put("mobile", mobile);
        List<User> userList = this.userService.selectByParams(criteria);
        if(StringUtil.isNullOrEmpty(userList) || userList.size() == 0)
            return Response.ok(true).build();
        return Response.ok(false).build();
    }

    @Override
    public Response validateUserExits(boolean isjQuery,String validateType, String validateData) {
        Criteria criteria = new Criteria();
        criteria.put(validateType, validateData);
        List<User> userList = this.userService.selectByParams(criteria);
        if(StringUtil.isNullOrEmpty(userList) || userList.size() == 0)
            return Response.ok(isjQuery?true:new RestResult(true)).build();
        return Response.ok(isjQuery?false:new RestResult(false)).build();
    }

    /**
     * 验证密码是否统一
     */
    @Override
    public Response validateUserPassword(@PathParam("password") String password,
                                         @PathParam("userSid") String userSid) {
        String json = "";
        Criteria criteria = new Criteria();
        criteria.put("userSid", userSid);
        List<User> userlist = this.userService.selectByParams(criteria);
        String oldpassword = userlist.get(0).getPassword();
        String oldpassword64 = userlist.get(0).getPassword64();
        String checkpasswd64 = WebUtil.encryptBase64(password);
        String checkpasswd = WebUtil.encrypt(password, userlist.get(0).getAccount());
        if (oldpassword.equals(checkpasswd) && oldpassword64.equals(checkpasswd64)) {
            json = InterfaceResult.setResult(WebConstants.ResultStatus.SUCCESS, null);
            return Response.ok(json).build();
        } else {
            json = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.ok(json).build();
        }
    }

    /**
     * 验证企业简写是否重复
     */
    @Override
    public Response validateTenantShortName(@QueryParam("tenantShortName") String tenantShortName) {
        String json = "";
        Criteria criteria = new Criteria();
        criteria.put("tenantShortName", tenantShortName);
        /*List<Tenant> userList = this.tenantService.selectByParams(criteria);
        if (userList.size() > 0) {
            json = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.ok(json).build();
        } else {
            json = InterfaceResult.setResult(WebConstants.ResultStatus.SUCCESS, null);
            return Response.ok(json).build();
        }*/
        return Response.ok(json).build();
    }

    /**
     * 查询该用户所属租户
     */
    @Override
    public Response findUsers(String params) {
        String json = "";

        // TODO
        User user = new User();
        Criteria criteria = new Criteria();
        Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
        if (map.isEmpty()) {
            criteria.put("userByMgtobjSid", user.getUserSid());
//            criteria.put("userByMgtobjSid", user.getUserSid());
        } else {
            criteria.setCondition(map);
            criteria.put("userByMgtobjSid", user.getUserSid());
        }
//        List<User> list = this.userService.selectTUserByTenantSid(criteria);
        List<User> list = this.userService.selectUserByProjectSid(criteria);
        json = JsonUtil.toJson(list);
        return Response.ok(json).build();
    }

    public Map<String, String> checkMgtRes(Long mgtObjSid) {
        Map<String, String> result = new HashMap<String, String>();
        String returnJson = "";
        Criteria example = new Criteria();
        example.put("bizSid", mgtObjSid);
        List<MgtObjRes> mgtObjRes = mgtObjResService.selectMgtRes(example);
        if (!CollectionUtils.isEmpty(mgtObjRes)) {
            Map<String, String> map = new HashMap<String, String>();
            map.put(WebConstants.ResourceType.RES_HOST, "计算资源");
            map.put(WebConstants.ResourceType.RES_STORAGE, "存储资源");
            map.put(WebConstants.ResourceType.RES_NETWORK, "网络资源");
            for (MgtObjRes mgtRes : mgtObjRes) {
                if (WebConstants.ResourceType.RES_HOST.equals(mgtRes.getResSetType())) {
                    map.remove(WebConstants.ResourceType.RES_HOST);
                } else if (WebConstants.ResourceType.RES_STORAGE.equals(mgtRes.getResSetType())) {
                    map.remove(WebConstants.ResourceType.RES_STORAGE);
                } else if (WebConstants.ResourceType.RES_NETWORK.equals(mgtRes.getResSetType())) {
                    map.remove(WebConstants.ResourceType.RES_NETWORK);
                }
            }
            if (map.size() > 0) {
                String value = "";
                for (String key : map.keySet()) {
                    value = value + map.get(key) + "、";
                }
                if (value.length() > 0) {
                    value = value.substring(0, value.length() - 1);
                }
                returnJson =
                        JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,
                                                       "所属租户尚未关联" + value + "。"));
                result.put("status", "failure");
                result.put("msg", returnJson);
            } else {
                returnJson = "";
                result.put("status", "success");
                result.put("msg", returnJson);
            }
        } else {
            log.error("租户未关联资源不允许审核");
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.WARNING_MGTOBJRES_FAILURE)));
            result.put("status", "failure");
            result.put("msg", returnJson);
        }
        return result;
    }

    /**
     * 启用、禁用用户
     */
    @Override
    public Response operationUser(@QueryParam("userSids") String userSids,
                                  @QueryParam("action") String action) {
        String returnJson = "";

        String[] sids = userSids.split(",");

        if (sids != null && sids.length > 0) {
            for (int i = 0; i < sids.length; i++) {
                User user = new User();
                user.setUserSid(Long.parseLong(sids[i]));
                WebUtil.prepareUpdateParams(user);
                // 设置用户状态为1，有效
                // 判断用户审核时，是否需要发送邮件
                String isSend = PropertiesUtil.getProperty("mail.account.active.send");
                //审核需要生成用户，启用禁用不需要
                int result = 0;
                if ("3".equals(action)) {
                    User user2 = userService.selectByPrimaryKey(Long.parseLong(sids[i]), 0);
//					Map<String, String> checkMgtRes = checkMgtRes(user2.getMgtObjSid());
//					if(RestResult.Status.SUCCESS.equals(checkMgtRes.get("status"))){
                    user.setStatus(WebConstants.UserStatus.AVAILABILITY);
                    result = this.userService.updateByPrimaryKeySelective2(user);
                    if ("true".equals(isSend)) {
                        // 需要发送
                        if (1 == result) {
                            // 审核成功，并发送邮件
                            user = this.userService.selectByPrimaryKey(Long.parseLong(sids[i]), 0);
//                            boolean resultMail = mailNotificationsService.approveAccountEmail(user);
                            boolean resultMail = true;
                            if (resultMail) {
                                returnJson =
                                        JsonUtil.toJson(
                                                new RestResult(RestResult.Status.SUCCESS, WebUtil
                                                        .getMessage(
                                                                WebConstants.MsgCd.INFO_APPROVE_SUCCESS)));
                            } else {
                                log.error("邮件发送失败");
                                returnJson =
                                        JsonUtil.toJson(
                                                new RestResult(RestResult.Status.SUCCESS, WebUtil
                                                        .getMessage(
                                                                WebConstants.MsgCd.ERROR_SENDMAIL_FAILURE)));
                            }
                        } else if (-1 == result) {
                            log.error("租户没有关联openstack环境");
                            returnJson =
                                    JsonUtil.toJson(
                                            new RestResult(RestResult.Status.FAILURE, WebUtil
                                                    .getMessage(
                                                            WebConstants.MsgCd.WARNING_MGTOBJRES_FAILURE)));
                        } else {
                            // 审核失败
                            log.error("发送邮件，审核用户失败");
                            returnJson =
                                    JsonUtil.toJson(
                                            new RestResult(RestResult.Status.SUCCESS, WebUtil
                                                    .getMessage(
                                                            WebConstants.MsgCd.ERROR_APPROVE_FAILURE)));
                        }
                    } else {
                        // 不需要发送
                        if (1 == result) {
                            returnJson =
                                    JsonUtil.toJson(
                                            new RestResult(RestResult.Status.SUCCESS, WebUtil
                                                    .getMessage(
                                                            WebConstants.MsgCd.INFO_APPROVE_SUCCESS)));
                        } else if (-1 == result) {
                            log.error("租户没有关联openstack环境");
                            returnJson =
                                    JsonUtil.toJson(
                                            new RestResult(RestResult.Status.FAILURE, WebUtil
                                                    .getMessage(
                                                            WebConstants.MsgCd.WARNING_MGTOBJRES_FAILURE)));
                        } else {
                            log.error("不发送邮件，审核用户失败");
                            MyError error = new MyError();
                            error.error = "审核用户失败";
                            error.errorCode = "320";
                            returnJson =
                                    JsonUtil.toJson(
                                            new RestResult(RestResult.Status.SUCCESS, WebUtil
                                                    .getMessage(
                                                            WebConstants.MsgCd.ERROR_APPROVE_FAILURE)));
                        }
                    }
//					}else if(RestResult.Status.FAILURE.equals(checkMgtRes.get("status"))){
//						returnJson = checkMgtRes.get("msg");
//						return Response.status(Status.OK).entity(returnJson).build();
//					}
                } else if ("0".equals(action)) {
                    user.setStatus(WebConstants.UserStatus.FORBIDDEN);
                    result = this.userService.updateByPrimaryKeySelective2(user);
                    if (1 == result) {
                        returnJson =
                                JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                                        .getMessage(WebConstants.MsgCd.INFO_OPERATE_SUCCESS)));
                    } else {
                        returnJson =
                                JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                                        .getMessage(WebConstants.MsgCd.ERROR_OPERATE_FAILURE)));
                    }
                } else {
                    user.setStatus(WebConstants.UserStatus.AVAILABILITY);
                    result = this.userService.updateByPrimaryKeySelective2(user);
                    if (1 == result) {
                        returnJson =
                                JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                                        .getMessage(WebConstants.MsgCd.INFO_OPERATE_SUCCESS)));
                    } else {
                        returnJson =
                                JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                                        .getMessage(WebConstants.MsgCd.ERROR_OPERATE_FAILURE)));
                    }
                }
            }
        }

        return Response.ok(returnJson).build();
    }

    /**
     * 用户修改密码
     */
    @Override
    public Response modifyPassword(String params) {
        String json = "";
        Map<String, Object> map;
        MyError error = new MyError();
        error.error = "修改密码失败 ";
        error.errorCode = "320";

        int result = 0;
        try {
            map = JsonUtil.fromJson(params, Map.class);
            String userSids = map.get("userSids").toString();
            String[] sids = userSids.split(",");

            if (sids != null && sids.length > 0) {
                for (int i = 0; i < sids.length; i++) {
                    User
                            currentUser =
                            this.userService.selectByPrimaryKey(Long.parseLong(sids[i]), null);
                    String newPwd = String.valueOf(map.get("newPassword"));
                    currentUser.setPassword(WebUtil.encrypt(newPwd, currentUser.getAccount()));
                    currentUser.setPassword64(WebUtil.encryptBase64(newPwd));
                    WebUtil.prepareUpdateParams(currentUser);
                    if (StringUtil.isNullOrEmpty(currentUser.getUuid())) {
                        result += this.userService.updateByPrimaryKeySelective2(currentUser);
                    } else {
                        result += this.userService.updatePasswordByPrimaryKey(currentUser);
                    }
                }
            }

            if (sids.length == result) {
                // 修改密码后同步ou账户密码
                // Criteria example = new Criteria();
                // example.put("allocateUserAccount", currentUser.getAccount());
                // int count = resourceInstanceExchangeAcService.countByParams(example);
                // if (count > 0) {
                // resourceInstanceExchangeService
                // .resetPassword(currentUser.getAccount(), currentUser.getPassword64());
                // }
                return Response.ok(result).build();
            } else {
                return Response.serverError().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

    }

    /**
     * 找回密码
     */
    @Override
    public Response findLostPwd(@QueryParam("account") String account) {
        // TODO Auto-generated method stub
        String json = "";
        Criteria criteria = new Criteria();
        criteria.put("account", account);
        List<User> userList = this.userService.selectByParams(criteria);
        User user = userList.get(0);
        String randomPwd = "";
        if (userList.size() > 0) {
            randomPwd = WebUtil.randomPwd(10);
            user.setPassword(WebUtil.encrypt(randomPwd, user.getAccount()));
            user.setPassword64(WebUtil.encryptBase64(randomPwd));
            this.userService.updateByPrimaryKeySelective2(user);
            MailTemplate mailTemplate = this.mailTemplateService.selectByPrimaryKey(1003L);

            // 读取路径下邮件模板
            StringBuffer
                    mailContent =
                    FileUtil.readFileByClasspath(mailTemplate.getMailContentFilePath());

            // 替换邮件内容
            StringUtil.strBufReplace("${owner}", user.getRealName(), mailContent);
            StringUtil.strBufReplace("${newRandomPassword}", randomPwd, mailContent);
            List<String> emails = new ArrayList<String>();
            emails.add(user.getEmail());

            // 邮件发送
            boolean result = MailUtil.sendMail(emails, null, null, mailTemplate.getMailSubject(),
                                               mailContent.toString(), null);
            if (result) {
                json = InterfaceResult.setResult(WebConstants.ResultStatus.SUCCESS, null);
                return Response.ok(json).build();
            } else {
                json = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
                return Response.ok(json).build();
            }
        } else {
            json =
                    InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
                                              JsonUtil.toJson(MyError.USER_NO_EXIST));
            return Response.ok(json).build();
        }

    }

    /**
     * 注册用户
     */
    @Override
    public Response registerUser(String params) {
        String json = "";
        User user;
        int result = 0;

        Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
        String userJson = JsonUtil.toJson(map.get("user"));
        user = JsonUtil.fromJson(userJson, User.class);
        result = this.userService.addRegisterUser(user);
        if (result == 0) {
//                boolean isSend = this.mailNotificationsService.registerEmail(user);
            boolean isSend = true;
            if (isSend) {
                json = InterfaceResult.setResult(WebConstants.ResultStatus.SUCCESS, null);
                return Response.ok(json).build();
            } else {
                log.error("注册完成通知邮件发送失败");
//					json = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
//					return Response.status(Status.INTERNAL_SERVER_ERROR).entity(json).build();
                json = InterfaceResult.setResult(WebConstants.ResultStatus.SUCCESS, null);
                return Response.ok(json).build();
            }
        } else if (result == 1) {
            json = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.ok(json).build();
        } else {
            json = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.ok(json).build();
        }
    }

    /**
     * 找回密码时，验证用户
     */
    @Override
    public Response userAtFindLostPwd(@QueryParam("phone") String phone,
                                          @QueryParam("email") String email) {

        Criteria criteria = new Criteria();
//        criteria.put("mobile", phone);
        criteria.put("email", email);
        List<User> list = this.userService.selectByParams(criteria);
        if (!list.isEmpty()) {
            // TODO 发送邮件
            boolean result = mailNotificationsService.changePwdEmail(list.get(0));
            if (result) {
                return Response.ok(new RestResult(true)).build();
            } else {
                log.error("邮件发送失败");
                return Response.ok(new RestResult(
                        RestResult.Status.FAILURE, "email sending failed","email sending failed")).build();
            }

        } else {
            log.info("用户不存在");
            MyError error = new MyError();
            error.error = "用户不存在";
            error.errorCode = "320";
            return Response.ok(new RestResult(
                    RestResult.Status.FAILURE,"the user does not exist","the user does not exist")).build();
        }
    }

    /**
     * 验证用户是否存在,并修改用户状态为正常
     */
    @Override
    public Response testUser(@QueryParam("id") long id,
                             @QueryParam("securityKey") String securityKey) {

        Criteria criteria = new Criteria();
        criteria.put("userSid", id);
        criteria.put("password", securityKey);
        List<User> listUser = this.userService.selectByParams(criteria);
        if (listUser.size() > 0) {
            User user = listUser.get(0);
            if (WebConstants.UserStatus.NOTACTIVATE.equals(user.getStatus())) {
                if (securityKey.equals(user.getPassword())) {
                    user.setStatus(WebConstants.UserStatus.AVAILABILITY);
                    int updateResult = this.userService.updateByPrimaryKeySelective2(user);
                    if (updateResult == 1) {
                        return Response.ok(updateResult).build();
                    } else {
                        return Response.status(320).entity(null).build();
                    }
                } else {
                    return Response.status(320).entity(null).build();
                }
            } else {
                return Response.status(320).entity(null).build();
            }
        } else {
            return Response.status(320).entity(null).build();
        }
    }

    /**
     * 点击邮件链接，验证用户是否存在
     */
    @Override
    public Response testUserAtEmail(@QueryParam("userSid") long userSid,
                                    @QueryParam("passWord") String password) {

        Criteria criteria = new Criteria();
        criteria.put("userSid", userSid);
        criteria.put("password", password);
        List<User> listUser = this.userService.selectByParams(criteria);
        if (listUser.size() > 0) {
            return Response.ok(JsonUtil.toJson(listUser.get(0))).build();
        } else {
            log.info("用户不存在");
            MyError error = new MyError();
            error.error = "用户不存在";
            error.errorCode = "320";
            return Response.status(320).entity(error).build();
        }
    }

    /**
     * 密码找回时，修改密码
     */
    @Override
    public Response modifyPwdAtFindLostPwd(String params) {
        String returnJson = "";
        try {
            Map<String, String> map = JsonUtil.fromJson(params, Map.class);

            Criteria criteria = new Criteria();
            criteria.put("userSid",Long.parseLong(map.get("userSid").toString()));
            criteria.put("password",map.get("oldPassword"));

            List<User>  userList= userService.selectByParams(criteria);
            if(userList.size()>0){
                User user = userList.get(0);

                User modifyUser = new User();
                modifyUser.setUserSid(user.getUserSid());
                modifyUser.setOldPassword(map.get("oldPassword"));
                modifyUser.setPassword(WebUtil.encrypt(map.get("newPassword"),user.getAccount()));
                modifyUser.setPassword64(WebUtil.encryptBase64(map.get("newPassword")));
                int result = this.userService.updateByPrimaryKeySelective2(modifyUser);
                if (result == 1) {
                    // 修改密码后同步ou账户密码
                    // Criteria example = new Criteria();
                    // example.put("allocateUserAccount", user.getAccount());
                    // int count = resourceInstanceExchangeAcService.countByParams(example);
                    // if (count > 0) {
                    // resourceInstanceExchangeService.resetPassword(user.getAccount(), user.getPassword64());
                    // }
                    returnJson = JsonUtil.toJson(
                            new RestResult(RestResult.Status.SUCCESS,WebUtil.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS), user.getAccount()));
                } else {
                    returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,"error.update.failure",02));
                }
            }else {
                returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,"the url error",03));
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,"the server error",04));
        }

        return Response.ok(returnJson).build();
    }

    /**
     * 查询所有角色
     */
    @Override
    public Response findAllRole() {
        Criteria example = new Criteria();
        example.put("status", "0");
        List<Role> list = this.roleService.selectByParams(example);
        String json = JsonUtil.toJson(list);
        return Response.ok(json).build();
    }

    /**
     * 新增后台用户
     */
    @Override
    public Response insertPlatformUser(User user) {
        int result = 0;
        String returnJson;
        List<String> userRoles = user.getRoleArr();
        Criteria criteria = new Criteria();
        for (int i = 0; i < userRoles.size(); i++) {
            criteria.put("roleSid", userRoles.get(i));
            List<RoleModule> list = this.roleModuleService.selectByParams(criteria);
            if (list.size() == 0) {
                Role role = this.roleService.selectByPrimaryKey(Long.valueOf(userRoles.get(i)));
                returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                        .getMessage("角色" + role.getRoleName() + "未被分配，请重新选择！"), "unSelected"));
                return Response.ok(returnJson).build();
            }
        }
        WebUtil.prepareInsertParams(user);
        result = this.userService.insertPlatformUser(user);
        if (result == 1) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS), null));
        } else if (result == -1) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.WARNING_MGTOBJRES_FAILURE), null));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE), null));
        }

        return Response.ok(returnJson).build();
//		String returnJson="";
//		int result = 0;
//		String mgtObjSid = user.getUserMgtObj();
//		Criteria criteria = new Criteria();
//		criteria.put("mgtObjSid", mgtObjSid);
//		List<UserMgtObjKey> list = this.userMgtObjService.selectByParams(criteria);
//		if(list.size() == 0){
//			WebUtil.prepareInsertParams(user);
//			result = this.userService.insertPlatformUser(user);
//			if (result==1) {
//				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
//						.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS), null));
//			} else if (result==-1) {
//				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
//						.getMessage(WebConstants.MsgCd.WARNING_MGTOBJRES_FAILURE), null));
//			} else {
//				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
//						.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE), null));
//			}
//		}else{
//			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
//					.getMessage("该项目已被关联，请重新选择！"), null));
//		}
//		return Response.status(Status.OK).entity(returnJson).build();
    }

    /**
     * 查询所有租户
     */
    @Override
    public Response findAllTenant() {
        Criteria example = new Criteria();
//        List<Tenant> list = this.tenantService.selectByParams(example);
//        String json = JsonUtil.toJson(list);
        return Response.ok().build();
    }

    /**
     * 更新后台用户
     */
    @Override
    public Response updatePlatformUser(User user) {
        String returnJson = "";
        List<String> userRoles = user.getRoleArr();
        Criteria criteria = new Criteria();
        if (null != userRoles) {
            for (int i = 0; i < userRoles.size(); i++) {
                criteria.put("roleSid", userRoles.get(i));
                List<RoleModule> list = this.roleModuleService.selectByParams(criteria);
                if (list.size() == 0) {
                    Role role = this.roleService.selectByPrimaryKey(Long.valueOf(userRoles.get(i)));
                    returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                            .getMessage("角色 " + role.getRoleName() + " 未被分配，请重新选择！"),
                                                                "unSelected"));
                    return Response.ok(returnJson).build();
                }
            }
        }
        WebUtil.prepareUpdateParams(user);
        boolean result = this.userService.updatePlatformUser(user);
        if (result) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS), null));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE), null));
        }
        return Response.ok(returnJson).build();
    }

    /**
     * 查询后台用户权限
     */
    @Override
    public Response findModules(Long userSid) {
        List<Module> modules = null;
        try {
            modules = moduleService.selectByUserSid(userSid, WebConstants.ModuleCategory.DASHBOARD);
            if (modules == null) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = JsonUtil.toJson(new RestResult(modules));
        return Response.ok(json).build();
    }


    @Override
    public Response findTenantUsers(String params, @Context HttpServletRequest servletRequest)
            throws IOException {
        //获取当前登录用户
        AuthUser authUserInfo = RequestContextUtil.getAuthUserInfo(servletRequest);
        Criteria criteria = new Criteria();
        Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
        if (!map.isEmpty()) {
            criteria.put("mgtObjSid", authUserInfo.getMgtObjSid());
        } else {
            criteria.setCondition(map);
            criteria.put("mgtObjSid", authUserInfo.getMgtObjSid());
        }
        //查询符合条件的数据
        System.err.println(map.size());
        System.err.println(map.isEmpty());
        System.err.println(!map.isEmpty());
        System.err.println(JsonUtil.toJson(criteria));
        List<User> list = this.userService.selectTUserByTenantSid(criteria);
        System.err.println(list.size());
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.status(Response.Status.OK).entity(json).build();
    }

    /**
     * 创建Session
     */
    @SuppressWarnings("unused")
    private String createSession(HttpSession session, HttpServletResponse servletResponse,
                                 String userAttributeName,
                                 User currentUser) {
        // 放入角色信息
        List<Role> roles = this.roleService.selectRoleByUserSid(currentUser.getUserSid());
        currentUser.setRoles(roles);
        //放入管理对象信息
        Criteria example = new Criteria();
        example.put("userSid", currentUser.getUserSid());
        List<UserMgtObjKey> userMgtObjs = userMgtObjService.selectByParams(example);
        currentUser.setUserMgtObjs(userMgtObjs);
        // 创建SSO信息
//        SSOUser ssoUser = new SSOUser(currentUser);
        // 设置User的Session信息
        session.setAttribute(userAttributeName, currentUser);
        // 设置SSO的Session信息
//        session.setAttribute(WebConstants.SSO_PORTAL_USER, ssoUser);
        // 清空密码
        currentUser.setPassword("");
        currentUser.setPassword64("");
        // 创建用户LocalThread
        /*userSession = new UserSession();
//		userSession.id = session.getId();
//		userSession.ssoToken = ssoUser.getSsoToken();
//		userSession.creationTime = session.getCreationTime();
//		userSession.user = currentUser;
        userSession.setCreationTime(session.getCreationTime());
        userSession.setId(session.getId());
        userSession.setSsoToken(ssoUser.getSsoToken());
        userSession.setUser(currentUser);
        session.setAttribute(WebConstants.CURRENT_USER, currentUser);
        //去掉UserMananger类，后临时兼容session, 后面需要优化后去掉， chengqi
        session.setAttribute("SSO_TEMP_SESSION", userSession);
        // 返回SSOToken的Cookie
        Cookie cookies = new Cookie("SSO_TOKEN", ssoUser.getSsoToken());
        cookies.setPath("/");
        servletResponse.addCookie(cookies);
        Cookie sessioncookies = new Cookie("WS-JSESSIONID", session.getId());
        sessioncookies.setPath("/");
        servletResponse.addCookie(sessioncookies);
        String json = JsonUtil.toJson(userSession);
        return json;*/
        return "";
    }

    @Override
    public Response findRoles(String params) {
        String returnJson;
        try {
            Map<String, Object>
                    map =
                    JsonUtil.fromJson(params, new TypeReference<Map<String, Object>>() {
                    });
            Criteria criteria = new Criteria();
            criteria.put("roleType", map.get("roleType"));
            List<Role> roles = roleService.selectByParams(criteria);
            // 角色信息构建
            returnJson = JsonUtil.toJson(roles);
        } catch (Exception e) {
            log.error("findRoles failure.", e);
            // 失败信息返回
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.ok(returnJson).build();
    }

    @Override
    public Response findBizType(String params) {
        String returnJson;
        User user = null;
        try {
            Map<String, String>
                    map =
                    JsonUtil.fromJson(params, new TypeReference<Map<String, String>>() {
                    });
            List<User> userList = this.userService.selectBizByAccount(map.get("account"));
            if (userList.size() > 0) {
                user = userList.get(0);
            } else {
                user = new User();
            }
            // 角色信息构建
            returnJson = JsonUtil.toJson(user);
        } catch (Exception e) {
            log.error("findBizType failure.", e);
            // 失败信息返回
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.ok(returnJson).build();
    }

//	@Override
//	@WebMethod
//	@POST
//	@Path("/registerTenants")
//	public Response registerTenants(String params) {
//		String returnJson;
//		try {
//			boolean f = userService.insertTenantUser(params);
//			if(f){
//				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
//						.getMessage(WebConstants.MsgCd.INFO_REGISTER_SUCCESS), null));
//			}else{
//				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
//						.getMessage(WebConstants.MsgCd.ERROR_REGISTER_FAILURE)));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
//					.getMessage(WebConstants.MsgCd.ERROR_REGISTER_FAILURE)));
//		}
//		return Response.status(Status.OK).entity(returnJson).build();
//	}

//	@Override
//	@WebMethod
//	@POST
//	@Path("/form")
//	@Consumes("multipart/form-data")
//	public Response findAr(List<Attachment> attachments,@Context HttpServletRequest request) {
//
//		 String url = "D:\\";
//
//		 try {
//			List<String> sid =  FileUtil.uploadAttachmentFiles(attachments);
//			for(int i=0;i<sid.size();i++){
//				System.out.println(sid.get(i));
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		 return Response.ok().entity("ok").build();
//	}


    public Response registerTenants(String params) {
        String returnJson = "";
        User user;
        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            String userJson = JsonUtil.toJson(map.get("user"));
            user = JsonUtil.fromJson(userJson, User.class);
            boolean result = this.userService.insertRegisterUser(user);
            if (result) {
                returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                        .getMessage(WebConstants.MsgCd.INFO_REGISTER_SUCCESS), null));
            } else {
                returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                        .getMessage(WebConstants.MsgCd.ERROR_REGISTER_FAILURE)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_REGISTER_FAILURE)));
        }
        return Response.ok(returnJson).build();
    }

    @Override
    public Response findUsersByMgtObjSid(@PathParam("mgtObjSid") Long mgtObjSid) {
        String json = "";
        List<User> list = new ArrayList<User>();
        Criteria criteria = new Criteria();
        criteria.put("mgtObjSid", mgtObjSid);
        List<User> mgtobjList = this.userService.findUsersByMgtObjSid(criteria);
        if (null != mgtobjList && mgtobjList.size() > 0) {
            criteria = new Criteria();
            criteria.put("mgtObjSid", mgtObjSid);
            criteria.put("userByMgtobjSid", mgtobjList.get(0).getUserSid());
            list = this.userService.selectUserByProjectSid(criteria);
            json = JsonUtil.toJson(list);
        }
        return Response.ok(json).build();
    }

    @Override
    public Response findManagerByMgtObjSid(@PathParam("mgtObjSid") Long mgtObjSid) {
        List<User> list = new ArrayList<User>();
        Criteria criteria = new Criteria();
        criteria.put("mgtObjSid", mgtObjSid);
        List<User> mgtobjList = this.userService.findUsersByMgtObjSid(criteria);
        if (CollectionUtils.isEmpty(mgtobjList)) {
            return Response.ok(new RestResult(null)).build();
        } else {
            return Response.ok(new RestResult(mgtobjList.get(0))).build();
        }
    }

    @Override
    public Response findUsersByParams(String params) {
        List<User> list = new ArrayList<User>();
        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            String mgtObjSid = StringUtil.nullToEmpty(map.get("userMgtObj"));
            String accountLike = StringUtil.nullToEmpty(map.get("accountLike"));
            String realNameLike = StringUtil.nullToEmpty(map.get("realNameLike"));
            String status = StringUtil.nullToEmpty(map.get("status"));
            Criteria criteria = new Criteria();
            criteria.put("mgtObjSid", mgtObjSid);
            List<User> mgtobjList = this.userService.findUsersByMgtObjSid(criteria);
            if (null != mgtobjList && mgtobjList.size() > 0) {
                criteria = new Criteria();
                criteria.put("mgtObjSid", mgtObjSid);
                criteria.put("accountPriject", accountLike);
                criteria.put("realNameProject", realNameLike);
                criteria.put("statusProject", status);
                criteria.put("userSid", mgtobjList.get(0).getUserSid());
                list = this.userService.selectUserByProjectSid(criteria);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(new RestResult(list)).build();
    }

    @Override
    public List findAllUsersByMgtObj(@PathParam("mgtObjSid") Long mgtObjSid) {
        Criteria criteria = new Criteria();
        criteria.put("mgtObjSid", mgtObjSid);
        List<User> mgtobjList = this.userService.findAllUsersByMgtObj(criteria);
        return mgtobjList;
    }

    @Override
    public Response registerProtalUser(String params) {
        String returnJson;
        try {
            boolean f = userService.insertTenantUser(params);
            if (f) {
                returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                        .getMessage(WebConstants.MsgCd.INFO_REGISTER_SUCCESS), null));
            } else {
                returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                        .getMessage(WebConstants.MsgCd.ERROR_REGISTER_FAILURE)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_REGISTER_FAILURE)));
        }
        return Response.status(Response.Status.OK).entity(returnJson).build();
    }

    @Override
    public Response updateProtalUser(String params) {
        String returnJson = "";
        Map<String, Object> map = JsonUtil.fromJson(params, Map.class);

        Map<String, String> userMap = (Map<String, String>) map.get("user");
        User user = new User();
        user.setUserSid(Long.parseLong(userMap.get("userSid")));
//        user.setMgtObjSid(Long.parseLong(userMap.get("userSid")));
        user.setRealName(userMap.get("realName").toString());

        Map<String, Object> tenantMap = (Map<String, Object>) map.get("tenant");
        List<MgtObjExt> mgtObjExts = new ArrayList<>();
        Iterator i=tenantMap.entrySet().iterator();
        while (i.hasNext()){
            MgtObjExt mgtObjExt = new MgtObjExt();
            mgtObjExt.setMgtObjSid(Long.parseLong(userMap.get("mgtObjSid")));
            Map.Entry e = (Map.Entry)i.next();
            if(e.getValue() != null && e.getValue() != ""){
                if(e.getKey().toString() .equals("tenantType")){
                    mgtObjExt.setAttrKey("tenantType");
                }
                if(e.getKey().toString() .equals("tenantName")){
                    mgtObjExt.setAttrKey("tenantName");
                }
                if(e.getKey().toString() .equals("Industry")){
                    mgtObjExt.setAttrKey("tenantIndustry");
                }
                if(e.getKey().toString() .equals("selProvince")){
                    mgtObjExt.setAttrKey("tenantProvince");
                }
                if(e.getKey().toString() .equals("selCity")){
                    mgtObjExt.setAttrKey("tenantCity");
                }
                if(e.getKey().toString() .equals("selArea")){
                    mgtObjExt.setAttrKey("tenantArea");
                }
                if(e.getKey().toString() .equals("addressmsg")){
                    mgtObjExt.setAttrKey("tenantAddress");
                }
                mgtObjExt.setAttrValue(e.getValue().toString());
                mgtObjExts.add(mgtObjExt);
            }
        }
        int result = this.userService.updateByPrimaryKeySelective3(user,mgtObjExts);
        if (result == 2) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS)));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
        }

        return Response.ok(returnJson).build();
    }

    @Override
    public Response getUserInfo(@PathParam("userSid") Long userSid) {
        User user = userService.selectByPrimaryKey2(userSid);
        String json = JsonUtil.toJson(user);
        return Response.ok(JsonUtil.toJson(json)).build();
    }

    @Override
    public Response updateUserInfo(String params, @Context HttpServletRequest servletRequest) {
        System.err.println(params);
        String returnJson = null;
        User user = new User();
        AuthUser authUserInfo = RequestContextUtil.getAuthUserInfo(servletRequest);
        Map<String, String> map = JsonUtil.fromJson(params, Map.class);
        user.setUserSid(authUserInfo.getUserSid());
        if (!StringUtil.isNullOrEmpty(map.get("email"))) {
            user.setEmail(map.get("email"));
        }
        if (!StringUtil.isNullOrEmpty(map.get("password"))) {
            user.setPassword64(WebUtil.encryptBase64(map.get("password")));
            user.setPassword(WebUtil.encrypt(map.get("password"), authUserInfo.getAccount()));
        }
        if (!StringUtil.isNullOrEmpty(map.get("mobile"))) {
            user.setMobile(map.get("mobile"));
        }
        int result = this.userService.updatePasswordByPrimaryKey(user);
        if (result == 1) {
            returnJson = JsonUtil.toJson(
                    new RestResult(RestResult.Status.SUCCESS,
                                   WebUtil.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS),
                                   null));

        } else {
            returnJson = JsonUtil.toJson(
                    new RestResult(RestResult.Status.FAILURE,
                                   WebUtil.getMessage(WebConstants.MsgCd.ERROR_IN_FAILURE),
                                   null));
        }
        return Response.status(Response.Status.OK).entity(returnJson).build();
    }


}
