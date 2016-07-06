/**
 *
 */
package com.h3c.idcloud.core.rest.system.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.product.MgtObjRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.res.*;
import com.h3c.idcloud.core.pojo.dto.system.*;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.dto.user.UserRole;
import com.h3c.idcloud.core.rest.system.MgtObjRest;
import com.h3c.idcloud.core.service.product.api.BusinesstypeService;
import com.h3c.idcloud.core.service.product.api.MgtObjResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.res.api.*;
import com.h3c.idcloud.core.service.system.api.*;
import com.h3c.idcloud.core.service.user.api.UserRoleService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.exception.ServiceException;
import com.h3c.idcloud.infrastructure.common.pojo.Attachment;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.*;
import com.sun.org.apache.bcel.internal.generic.NEW;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 管理对象Restful接口实现类
 *
 * @author ChengQi
 */
@Component
public class MgtObjRestImpl implements MgtObjRest {

    Logger log = Logger.getLogger(this.getClass());

    /**
     * 管理对象Service
     */
    @Reference(version = "1.0.0")
    private MgtObjService mgtObjService;

    /**
     * 管理对象定义Service
     */
    @Reference(version = "1.0.0")
    private MgtObjDefService mgtObjDefService;

    /**
     * 管理对象扩展Service
     */
    @Reference(version = "1.0.0")
    private MgtObjExtService mgtObjExtService;

    /**
     * 业务类型Service
     */
    @Reference(version = "1.0.0")
    private BusinesstypeService businesstypeService;

    /**
     * 资源拓扑Service
     */
    @Reference(version = "1.0.0")
    private ResTopologyService resTopologyService;

    /**
     * 业务资源关系Service
     */
    @Reference(version = "1.0.0")
    private MgtObjResService mgtObjResService;

    /**
     * 业务组织关系Service
     */
    @Reference(version = "1.0.0")
    private OrgbizService orgBizService;

    /**
     * 配额管理Service *
     */
    @Reference(version = "1.0.0")
    private QuotaService quotaService;
    @Reference(version = "1.0.0")
    private ServiceInstanceService serviceInstanceService;
    @Reference(version = "1.0.0")
    private ServiceInstResService serviceInstResService;

    @Reference(version = "1.0.0")
    private UserService userService;

	/*@Reference(version = "1.0.0")
    private ResBizVmService resBizVmService;*/

    @Reference(version = "1.0.0")
    private ResVdService resVdService;

    @Reference(version = "1.0.0")
    private UserMgtObjService userMgtObjService;

    @Reference(version = "1.0.0")
    private UserRoleService userRoleService;

    @Reference(version = "1.0.0")
    private ResImageService resImageService;

    @Reference(version = "1.0.0")
    private MailNotificationsService mailNotificationsService;

    @Reference(version = "1.0.0")
    private ResHostService resHostService;

    @Reference(version = "1.0.0")
    private AttachmentService attachmentService;

    @Reference(version = "1.0.0")
    private ResVmService resVmService;

	/*@Reference(version = "1.0.0")
    private ResOsSoftwareService osSoftwareService;*/

    @Reference(version = "1.0.0")
    private ResOsUserService resOsUserService;

    @Reference(version = "1.0.0")
    private ResStorageService resStorageService;

    @Reference(version = "1.0.0")
    private ResVeService resVeService;

	/*@Reference(version = "1.0.0")
    private ResIpService resIpMapper;

	@Reference(version = "1.0.0")
	private ResVfcMapper resVfcMapper;*/

    /**
     * 查询所有管理对象
     */
    @Override
    @WebMethod
    @POST
    public Response findAllMgtObj(MgtObj obj) {
        Criteria criteria = new Criteria();
        criteria.setOrderByClause("NAME");
        List<MgtObj> mgtObjList = this.mgtObjService.selectByParams(criteria);

        return Response.status(Status.OK).entity(JsonUtil.toJson(mgtObjList))
                .build();
    }

    /**
     * 查询所有管理对象
     */
    @Override
    @WebMethod
    @POST
    @Path("/findAllMgtObj")
    public Response findAllMgtObjForTree(MgtObj obj) {
        Criteria criteria = new Criteria();
        criteria.setOrderByClause("A.MGT_OBJ_SID DESC");
        List<MgtObj> mgtObjList = this.mgtObjService
                .selectMgtObjTreeByParams2(criteria);

        return Response.status(Status.OK).entity(JsonUtil.toJson(mgtObjList))
                .build();
    }

    /**
     * 查询所有管理对象
     */
    @WebMethod
    @POST
    @Path("/find")
    @Override
    public Response findMgtObj(Criteria criteria) {
        criteria.setOrderByClause("NAME");
        List<MgtObj> mgtObjList = this.mgtObjService.selectByParams(criteria);
        for (MgtObj mgtObj : mgtObjList) {
            criteria = new Criteria();
            criteria.put("mgtObjSid", mgtObj.getMgtObjSid());
            List<MgtObjExt> mgtObjExts = this.mgtObjExtService.selectByParams(criteria);
            mgtObj.setMgtObjExts(mgtObjExts);
        }
//		new RestResult(mgtObjList);
        return Response.ok(new RestResult(mgtObjList)).build();
    }

    /**
     * 查询所有管理对象
     */
    @WebMethod
    @POST
    @Path("/checkMgtObjExt")
    @Override
    public List<MgtObjExt> checkMgtObjExt(Criteria criteria) {
        List<MgtObjExt> exts = mgtObjExtService.selectByParams(criteria);
        return exts;
    }

	/**
	 * 查询所有管理对象
	 */
	@WebMethod
	@POST
	@Path("/findMgtObjTreeData")
	@Override
	public Response findMgtObjTreeData(Criteria criteria) {
		criteria.setOrderByClause("A.MGT_OBJ_SID DESC");
		List<MgtObj> mgtObjList = this.mgtObjService
				.selectMgtObjTreeByParams(criteria);
		for (MgtObj mgtObj : mgtObjList) {
			criteria = new Criteria();
			criteria.put("mgtObjSid", mgtObj.getMgtObjSid());
			List<MgtObjExt> mgtObjExts = this.mgtObjExtService
					.selectByParams(criteria);
			mgtObj.setMgtObjExts(mgtObjExts);
		}
        return Response.ok(new RestResult(mgtObjList)).build();
	}

    /**
     * 查询所有管理对象
     */
    @WebMethod
    @POST
    @Path("/findByParams")
    @Override
    public List<Map<String, String>> findMgtObjByParams(Criteria criteria) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        try {
            List<UserMgtObjKey> userMgts = userMgtObjService
                    .selectByParams(criteria);
            if (!CollectionUtils.isEmpty(userMgts)) {
                for (UserMgtObjKey userMgtObj : userMgts) {
                    Criteria params = new Criteria();
                    params.put("mgtObjSid", userMgtObj.getMgtObjSid());
                    List<MgtObjExt> mgtObjExts = this.mgtObjExtService
                            .selectByParams(params);

                    criteria.put("mgtObjSid", userMgtObj.getMgtObjSid());
                    String statusParams = "'"
                                          + WebConstants.MGT_OBJ_STATUS.NORMAL + "','"
                                          + WebConstants.MGT_OBJ_STATUS.SETTING
                                          + "'".replace(" ", "");
//					criteria.put("status", WebConstants.MGT_OBJ_STATUS.NORMAL);
                    criteria.put("statusParams", statusParams);
                    List<MgtObj> mgtObjs = this.mgtObjService
                            .selectByParams(criteria);
                    if (!CollectionUtils.isEmpty(mgtObjs)) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("statusName", mgtObjs.get(0).getStatusName());
                        map.put("status", mgtObjs.get(0).getStatus());
                        map.put("mgtObjSid", userMgtObj.getMgtObjSid() + "");
                        String presetTime = PropertiesUtil
                                .getProperty("mgtobj.expiredate.notice");
                        int time = Integer.parseInt(presetTime) * 7;
                        for (MgtObjExt mgtObjExt : mgtObjExts) {
                            map.put(mgtObjExt.getAttrKey(),
                                    mgtObjExt.getAttrValue());
                            if ("projectEndDate".equals(mgtObjExt.getAttrKey())) {
                                String mgtObjEndDate = mgtObjExt.getAttrValue();
                                Date endTime = df.parse(mgtObjEndDate
                                                        + " 23:59:59");
                                Date now = new Date();
                                long buffer = endTime.getTime() - now.getTime();
                                long days = buffer / (1000 * 60 * 60 * 24);
                                // 如果在提醒范围内
                                if (time >= days && 0 <= days) {
                                    map.put("inNoticeTime", "1");
                                } else if (days < 0) {
                                    map.put("inNoticeTime", "-1");
                                } else {
                                    map.put("inNoticeTime", "0");
                                }
                            }
                        }
                        resultList.add(map);
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultList;
    }

	/**
	 * 查询可修改的父管理对象
	 */
	@WebMethod
	@POST
	@Path("/find/parent")
	@Override
	public Response findParentMgtObj(Criteria criteria) {
		criteria.setOrderByClause("NAME");
		List<MgtObj> mgtObjList = this.mgtObjService.findParentMgtObj(criteria);
        return Response.status(Status.OK).entity(new RestResult(mgtObjList)).build();
	}

	/**
	 * 查询管理对象信息
	 */
	@WebMethod
	@POST
	@Path("/load/{mgtObjSid}")
	@Override
	public Response loadMgtObj(@PathParam("mgtObjSid") Long mgtObjSid) {
		MgtObj mgtObj = this.mgtObjService.selectByPrimaryKey(mgtObjSid);
		Criteria criteria = new Criteria();
		criteria.put("mgtObjSid", mgtObj.getMgtObjSid());
		List<MgtObjExt> mgtObjExts = this.mgtObjExtService
				.selectByParams(criteria);
		mgtObj.setMgtObjExts(mgtObjExts);
		String json=JsonUtil.toJson(new RestResult(mgtObj));
		return Response.status(Status.OK).entity(json).build();
	}

	/**
	 * 查询所有管理对象定义
	 */
	@WebMethod
	@POST
	@Path("/load/def")
	@Override
	public Response loadMgtObjDef(Criteria criteria) {
        List<MgtObjDef> mgtObjList = this.mgtObjDefService.selectByParams(criteria);
        return Response.status(Status.OK).entity(new RestResult(mgtObjList)).build();
//		return this.mgtObjDefService.selectByParams(criteria);
	}

    /**
     * 新增管理对象
     */
    @Override
    public Response addMgtObj(MgtObj mgtObj) {
        String returnJson;
        try {
            if (mgtObj.getParentId() == null) {
                mgtObj.setParentId(-999L);
            }
            WebUtil.prepareInsertParams(mgtObj);
            mgtObj.setLevel(1L);
            int result = this.mgtObjService.insertSelective(mgtObj);
            if (1 == result) {
                Long mgtObjSid = mgtObj.getMgtObjSid();
                List<MgtObjExt> mgtObjExts = mgtObj.getMgtObjExts();
                for (MgtObjExt mgtObjExt : mgtObjExts) {
                    mgtObjExt.setMgtObjSid(mgtObjSid);
                    this.mgtObjExtService.insertSelective(mgtObjExt);
                }
                // 把这个关联到用户
                if (!StringUtil.isNullOrEmpty(mgtObj.getUserSid())) {
                    UserMgtObjKey userMgt = new UserMgtObjKey();
                    userMgt.setMgtObjSid(mgtObjSid);
                    userMgt.setUserSid(Long.parseLong(mgtObj.getUserSid()));
                    this.userMgtObjService.insertSelective(userMgt);
                }

                // 保存相关配额信息
                List<Quota> mgtQuotas = mgtObj.getMgtQuotas();
                if (!CollectionUtils.isEmpty(mgtQuotas)) {
                    for (Quota quota : mgtQuotas) {
                        quota.setQuotaObjSid(mgtObjSid);
                        WebUtil.prepareInsertParams(quota);
                        quotaService.insertSelective(quota);
                    }
                }

                // 创建成功信息构建
                returnJson = JsonUtil
                        .toJson(new RestResult(
                                RestResult.Status.SUCCESS,
                                WebUtil.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS)));
                boolean flag = this.mailNotificationsService
                        .applyMgtObjEmail(mgtObj);

            } else {
                returnJson = JsonUtil
                        .toJson(new RestResult(
                                RestResult.Status.SUCCESS,
                                WebUtil.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
            }

        } catch (Exception e) {
            log.error("addMgtObj failure.", e);
            // 创建失败信息构建
            returnJson = JsonUtil
                    .toJson(new RestResult(
                            RestResult.Status.FAILURE,
                            WebUtil.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    /**
     * 新增管理对象
     */
    @Override
    public Response addMgtObjGroup(MgtObj mgtObj) {
        String returnJson;
        try {
            WebUtil.prepareInsertParams(mgtObj);
            mgtObj.setStatus(WebConstants.MGT_OBJ_STATUS.NORMAL);
            mgtObj.setLevel(0L);

            Criteria example = new Criteria();
            example.put("level", -1L);
            List<MgtObj> parentMgtObjs = mgtObjService.selectByParams(example);
            if (!CollectionUtils.isEmpty(parentMgtObjs)) {
                mgtObj.setParentId(parentMgtObjs.get(0).getMgtObjSid());
            }
            int result = this.mgtObjService.insertSelective(mgtObj);
            if (1 == result) {
                returnJson = JsonUtil
                        .toJson(new RestResult(
                                RestResult.Status.SUCCESS,
                                WebUtil.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS)));
            } else {
                returnJson = JsonUtil
                        .toJson(new RestResult(
                                RestResult.Status.SUCCESS,
                                WebUtil.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
            }
        } catch (Exception e) {
            log.error("addMgtObj failure.", e);
            // 创建失败信息构建
            returnJson = JsonUtil
                    .toJson(new RestResult(
                            RestResult.Status.FAILURE,
                            WebUtil.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    /**
     * 更新管理对象
     */
    @WebMethod
    @POST
    @Path("/update")
    @Override
    public Response updateMgtObj(MgtObj mgtObj) {
        String returnJson;
        try {

            this.mgtObjService.updateByPrimaryKeySelective(mgtObj);
            List<MgtObjExt> mgtObjExts = mgtObj.getMgtObjExts();
            if (!CollectionUtils.isEmpty(mgtObjExts)) {
                for (MgtObjExt mgtObjExt : mgtObjExts) {
                    if (mgtObjExt.getExtendSid() != null) {
                        this.mgtObjExtService.updateByPrimaryKey(mgtObjExt);
                    } else {
                        this.mgtObjExtService.insertSelective(mgtObjExt);
                    }
                }
            }
            if (!StringUtil.isNullOrEmpty(mgtObj.getUserSid())) {
                // 删除老的关系
                Criteria example = new Criteria();
                example.put("mgtObjSid", mgtObj.getMgtObjSid());
                this.userMgtObjService.deleteByParams(example);
                // 添加新的关系
                UserMgtObjKey userMgtObj = new UserMgtObjKey();
                userMgtObj.setMgtObjSid(mgtObj.getMgtObjSid());
                userMgtObj.setUserSid(Long.parseLong(mgtObj.getUserSid()));
                this.userMgtObjService.insertSelective(userMgtObj);
            }
            // 更新成功信息构建
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                            .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS)));
        } catch (Exception e) {
            log.error("updateMgtObj failure.", e);
            // 更新失败信息构建
            returnJson = JsonUtil
                    .toJson(new RestResult(
                            RestResult.Status.FAILURE,
                            WebUtil.getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    /**
     * 更新管理对象
     */
    @WebMethod
    @POST
    @Path("/approveMgtObj")
    @Override
    public Response approveMgtObj(MgtObj mgtObj) {
        String returnJson;
        try {
            MgtObj oldMgtObj = this.mgtObjService.selectByPrimaryKey(mgtObj
                                                                             .getMgtObjSid());
            int result = this.mgtObjService.updateByPrimaryKeySelective(mgtObj);
            if (result == 1) {
                // 发送邮件
                this.mailNotificationsService.approveProjectEmail(mgtObj);
                // 更新成功信息构建
                returnJson = JsonUtil
                        .toJson(new RestResult(
                                RestResult.Status.SUCCESS,
                                WebUtil.getMessage(WebConstants.MsgCd.INFO_APPROVE_SUCCESS)));
            } else {
                // 更新失败信息构建
                returnJson = JsonUtil
                        .toJson(new RestResult(
                                RestResult.Status.FAILURE,
                                WebUtil.getMessage(WebConstants.MsgCd.ERROR_APPROVE_FAILURE)));
            }
        } catch (Exception e) {
            log.error("approveMgtObj failure.", e);
            // 更新失败信息构建
            returnJson = JsonUtil
                    .toJson(new RestResult(
                            RestResult.Status.FAILURE,
                            WebUtil.getMessage(WebConstants.MsgCd.ERROR_APPROVE_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

	@Override
	public Response deleteMgtObj(Long mgtObjSid, boolean delBaseRes) {
		String returnJson = null;
		try {
			Criteria criteria = new Criteria();
			criteria.put("parentId", mgtObjSid);
			List<MgtObj> childList = this.mgtObjService
					.selectByParams(criteria);
			if (!childList.isEmpty()) {
				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,
						"此分类下存在项目，请先删除所有项目后再进行操作！"));
				return Response.status(Status.OK).entity(returnJson).build();
			}
			criteria = new Criteria();
			criteria.put("mgtObjSid", mgtObjSid);
			// List<User> userList = this.userService.selectByParams(criteria);
			List<UserMgtObjKey> userMgtRels = userMgtObjService
					.selectByParams(criteria);
			if (!userMgtRels.isEmpty()) {
				boolean flag = true;
				for (UserMgtObjKey um : userMgtRels) {
					Criteria condition = new Criteria();
					condition.put("userSid", um.getUserSid());
					condition.put("roleSid",
							WebConstants.RoleSid.MGT_OBJ_MANAGER);
					List<UserRole> userRoles = userRoleService
							.selectByParams(condition);
					if (CollectionUtils.isEmpty(userRoles)) {
						flag = false;
					}
				}
				if (!flag) {
					returnJson = JsonUtil.toJson(new RestResult(
							RestResult.Status.FAILURE, "此项目下存在成员，请先删除所有成员后再进行操作！"));
					return Response.status(Status.OK).entity(returnJson)
							.build();
				}
			}
			int result = 0;
			// 删除物理资源
			// if(delBaseRes){
			// result =
			// this.mgtObjService.deleteTenantResByPrimaryKey(mgtObjSid);
			// }else{
			// result = this.mgtObjService.deleteByPrimaryKey(mgtObjSid);
			result = this.mgtObjService.deleteLocalDataByPrimaryKey(mgtObjSid);
			// 删除项目和项目经理的关系
			Criteria example = new Criteria();
			example.put("mgtObjSid", mgtObjSid);
			userMgtObjService.deleteByParams(example);
			// }
			if (1 == result) {
				// 删除MgtObj之后，再删除其配置表信息
				criteria = new Criteria();
				criteria.put("mgtObjSid", mgtObjSid);
				this.mgtObjExtService.deleteByParams(criteria);
				// 删除成功信息构建
				returnJson = JsonUtil
						.toJson(new RestResult(
								RestResult.Status.SUCCESS,
								WebUtil.getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS)));
			} else {
				// 删除成功信息构建
				returnJson = JsonUtil
						.toJson(new RestResult(
								RestResult.Status.SUCCESS,
								WebUtil.getMessage(WebConstants.MsgCd.ERROR_DELETE_FAILURE)));
			}

		} catch (Exception e) {
			log.error("deleteMgtObj failure.", e);
			// 删除失败信息构建
			returnJson = JsonUtil
					.toJson(new RestResult(
							RestResult.Status.FAILURE,
							WebUtil.getMessage(WebConstants.MsgCd.ERROR_DELETE_FAILURE)));
		}
		return Response.status(Status.OK).entity(returnJson).build();
	}

    @Override
    public Response getComputeResourceTree(MgtObj mgtObj) {
        String returnJson;
        try {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            // 查询父业务所属的资源分区
            List<MgtObjRes> bizResList = new ArrayList<MgtObjRes>();
            if (null != mgtObj.getParentId()) {
                Criteria criteria = new Criteria();
                criteria.put("bizSid", mgtObj.getParentId());
                criteria.put("resSetType", WebConstants.RES_TOPOLOGY_TYPE.RZ);
                bizResList = mgtObjResService.selectByParams(criteria);
                // 检查父业务是否关联资源分区
                if (bizResList.size() == 0) {
                    // returnJson = JsonUtil.toJson(new
                    // RestResult(RestResult.Status.FAILURE, "请先将项目分类关联到资源分区！"));
                    resultMap.put("treeData", new ArrayList());
                    returnJson = JsonUtil.toJson(resultMap);
                    return Response.status(Status.OK).entity(returnJson)
                            .build();
                }

            }
            String mode = mgtObj.getMode();
            Long mgtObjSid = mgtObj.getMgtObjSid();
            if ("1".equals(mode) || "0".equals(mode)) {// 组装资源分区参数
                if (null != mgtObj.getParentId()) {
                    List<String> resourceZones = new ArrayList<String>();
                    for (MgtObjRes bizRes : bizResList) {
                        resourceZones.add(bizRes.getResSetSid());
                    }
                    Criteria resZoneParam = new Criteria();
                    resZoneParam.put("resTopologyType", WebConstants.RES_TOPOLOGY_TYPE.PC);
                    resZoneParam.put("resTopologySidList", resourceZones);
                    List<ResTopology> resTopologies = mgtObjService
                            .selectResZoneTopologyByParams(resZoneParam);
                    resultMap.put("treeData", resTopologies);
                } else {
                    // 得到所有顶级资源R
                    Criteria params = new Criteria();
                    params.put("resTopologyType",
                               WebConstants.RES_TOPOLOGY_TYPE.REGION);
					/*List<ResTopology> regions = resTopologyService
							.selectByParams(params);
					if (!CollectionUtils.isEmpty(regions)) {
						List<String> rtIds = new ArrayList<String>();
						for (ResTopology rt : regions) {
							rtIds.add(rt.getResTopologySid());
						}
						if (!CollectionUtils.isEmpty(rtIds)) {
							// 查询父业务的资源分区下所属的网络资源
							Criteria resZoneParam = new Criteria();
							resZoneParam.put("resTopologyType",
									WebConstants.RES_TOPOLOGY_TYPE.PC);
							resZoneParam.put("resTopologySidList", rtIds);
							List<ResTopology> resTopologies = mgtObjService
									.selectResZoneTopologyByParams(resZoneParam);
							resultMap.put("treeData", resTopologies);
						}
					}*/
                }
            }
            if ("2".equals(mode) || "0".equals(mode)) {
                // 返回关联信息
                if (mgtObjSid != null) {
                    Criteria bizResParams = new Criteria();
                    bizResParams.put("bizSid", mgtObjSid);
                    bizResParams.put("resSetType_vc", WebConstants.RES_TOPOLOGY_TYPE.VC);
                    bizResParams.put("resSetType_host", "RES-HOST");
                    List<MgtObjRes> bizReses = mgtObjResService
                            .selectByParams(bizResParams);
                    resultMap.put("checkedData", bizReses);
                }
            }
            returnJson = JsonUtil.toJson(resultMap);
        } catch (Exception e) {
            log.error("getComputeResourceTree failure.", e);
            // 获取失败信息构建
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                            .getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    @Override
    public Response getNetworkResourceTree(MgtObj mgtObj) {
        String returnJson;
        try {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            // 查询父业务所属的资源分区
            List<MgtObjRes> bizResList = new ArrayList<MgtObjRes>();
            if (null != mgtObj.getParentId()) {
                Criteria criteria = new Criteria();
                criteria.put("bizSid", mgtObj.getParentId());
                criteria.put("resSetType", WebConstants.RES_TOPOLOGY_TYPE.RZ);
                bizResList = mgtObjResService.selectByParams(criteria);
                // 检查父业务是否关联资源分区
                if (bizResList.size() == 0) {
                    returnJson = JsonUtil.toJson(new RestResult(
                            RestResult.Status.FAILURE, "请先将业务属性关联到资源分区！"));
                    return Response.status(Status.OK).entity(returnJson)
                            .build();
                }

            }
            String mode = mgtObj.getMode();
            Long mgtObjSid = mgtObj.getMgtObjSid();
            if ("1".equals(mode) || "0".equals(mode)) {
                if (null != mgtObj.getParentId()) {
                    // 组装资源分区参数
                    List<String> resourceZones = new ArrayList<String>();
                    for (MgtObjRes bizRes : bizResList) {
                        resourceZones.add(bizRes.getResSetSid());
                    }
                    // 查询父业务的资源分区下所属的网络资源
                    Criteria resZoneParam = new Criteria();
                    resZoneParam.put("resZoneSids", resourceZones);
                    resZoneParam.put("resTopologyType", WebConstants.RES_TOPOLOGY_TYPE.PN);
                    List<ResTopology> resTopologies = mgtObjService
                            .selectResZoneTopologyByParams(resZoneParam);
                    resultMap.put("treeData", resTopologies);
                } else {
                    Criteria params = new Criteria();
                    params.put("resTopologyType",
                               WebConstants.RES_TOPOLOGY_TYPE.REGION);
					/*List<ResTopology> regions = resTopologyService
							.selectByParams(params);
					if (!CollectionUtils.isEmpty(regions)) {
						List<String> rtIds = new ArrayList<String>();
						for (ResTopology rt : regions) {
							rtIds.add(rt.getResTopologySid());
						}
						if (!CollectionUtils.isEmpty(rtIds)) {
							// 查询父业务的资源分区下所属的网络资源
							Criteria resZoneParam = new Criteria();
							resZoneParam.put("resTopologyType",
									WebConstants.RES_TOPOLOGY_TYPE.PN);
							resZoneParam.put("resTopologySidList", rtIds);
							List<ResTopology> resTopologies = mgtObjService
									.selectResZoneTopologyByParams(resZoneParam);
							resultMap.put("treeData", resTopologies);
						}
					}*/
                }
            }

            if ("2".equals(mode) || "0".equals(mode)) {
                // 返回关联信息
                if (mgtObjSid != null) {
                    Criteria bizResParams = new Criteria();
                    bizResParams.put("bizSid", mgtObjSid);
                    bizResParams.put("resSetType", "RES-NETWORK");
                    List<MgtObjRes> bizReses = mgtObjResService
                            .selectByParams(bizResParams);
                    resultMap.put("checkedData", bizReses);
                }
            }
            returnJson = JsonUtil.toJson(resultMap);
        } catch (Exception e) {
            log.error("getNetworkResourceTree failure.", e);
            // 获取失败信息构建
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                            .getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    @Override
    public Response getStroageResourceTree(MgtObj mgtObj) {
        String returnJson;
        try {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            // 查询父业务所属的资源分区
            List<MgtObjRes> bizResList = new ArrayList<MgtObjRes>();
            if (null != mgtObj.getParentId()) {
                Criteria criteria = new Criteria();
                criteria.put("bizSid", mgtObj.getParentId());
                criteria.put("resSetType", WebConstants.RES_TOPOLOGY_TYPE.RZ);
                bizResList = mgtObjResService.selectByParams(criteria);
                // 检查父业务是否关联资源分区
                if (bizResList.size() == 0) {
                    returnJson = JsonUtil.toJson(new RestResult(
                            RestResult.Status.FAILURE, "请先将业务属性关联到资源分区！"));
                    return Response.status(Status.OK).entity(returnJson)
                            .build();
                }

            }
            String mode = mgtObj.getMode();
            Long mgtObjSid = mgtObj.getMgtObjSid();
            if ("1".equals(mode) || "0".equals(mode)) {
                Criteria resZoneParam = new Criteria();
                resZoneParam.put("mgtObjSid", mgtObj.getMgtObjSid());
                resZoneParam.put("resTopologyType", WebConstants.RES_TOPOLOGY_TYPE.PS);
                resZoneParam.put("resTopologySid", "10");
                List<ResTopology> resTopologies = mgtObjService
                        .selectResZoneTopologyByParams(resZoneParam);
                resultMap.put("treeData", resTopologies);
            }

            if ("2".equals(mode) || "0".equals(mode)) {
                // 返回关联信息
                if (mgtObjSid != null) {
                    Criteria bizResParams = new Criteria();
                    bizResParams.put("bizSid", mgtObjSid);
                    bizResParams.put("resSetType", "RES-STORAGE");
                    List<MgtObjRes> bizReses = mgtObjResService
                            .selectByParams(bizResParams);
                    resultMap.put("checkedData", bizReses);
                }
            }
            returnJson = JsonUtil.toJson(resultMap);
        } catch (Exception e) {
            log.error("getNetworkResourceTree failure.", e);
            // 获取失败信息构建
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                            .getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    @Override
    public Response createRelateResource(BizResForm bizResForm) {
        String returnJson;
        try {
            // 判断是否已关联了这个虚拟化环境的资源
            Criteria example = new Criteria();
            if (bizResForm.getBizReses() != null) {
                for (MgtObjRes res : bizResForm.getBizReses()) {
                    if ("add".equals(res.getOperate())
                        && !"RES-VE".equals(res.getResSetType())) {
                        if (!StringUtil.isNullOrEmpty(res.getResVeSid())) {
                            example = new Criteria();
                            example.put("bizSid", res.getBizSid());
                            example.put("resSetSid", res.getResVeSid());
                            List<MgtObjRes> mgtRes = mgtObjResService
                                    .selectByParams(example);
                            if (CollectionUtils.isEmpty(mgtRes)) {
                                MgtObjRes mgtObjRes = new MgtObjRes();
                                mgtObjRes.setBizSid(res.getBizSid());
                                mgtObjRes.setResSetType("RES-VE");
                                mgtObjRes.setResSid(res.getResVeSid());
                                mgtObjRes.setResCategory("-1");
                                mgtObjResService.insertSelective(mgtObjRes);
                            }
                        }
                        mgtObjResService.insertSelective(res);
                    } else if ("delete".equals(res.getOperate())
                               && !"RES-VE".equals(res.getResSetType())) {
                        example = new Criteria();
                        example.put("bizSid", res.getBizSid());
                        example.put("resSetSid", res.getResSetSid());
                        mgtObjResService.deleteByParams(example);
                    }
                }
            }
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                            .getMessage(WebConstants.MsgCd.INFO_RELATE_SUCCESS)));
        } catch (Exception e) {
            log.error("relateResource failure", e);
            returnJson = JsonUtil
                    .toJson(new RestResult(
                            RestResult.Status.FAILURE,
                            WebUtil.getMessage(WebConstants.MsgCd.ERROR_RELATE_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    @Override
    public Response createRelateTreeResource(BizResForm bizResForm) {
        String returnJson;
        try {
            // 删除原关联关系
            Criteria params = new Criteria();
            params.put("bizSid", bizResForm.getBizSid());
            params.put("resSetTypes", "'RES-VE','"
                                      + WebConstants.ResourceType.RES_HOST + "','"
                                      + WebConstants.ResourceType.RES_STORAGE + "','"
                                      + WebConstants.ResourceType.RES_NETWORK + "'");
            mgtObjResService.deleteByParams(params);
            // 判断是否已关联了这个虚拟化环境的资源
            Criteria example = new Criteria();
            if (bizResForm.getBizReses() != null) {
                for (MgtObjRes res : bizResForm.getBizReses()) {
                    if ("RES-HOST".equals(res.getResSetType())) {
                        // 得到该主机所在的VE
						/*ResHost host = resHostService.selectByPrimaryKey(res
								.getResSetSid());
						ResTopology topology = resTopologyService
								.selectByPrimaryKey(host.getParentTopologySid());
						if (topology != null) {
							String veSid = "";
							if ("VE".equals(topology.getResTopologyType())) {
								veSid = topology.getResTopologySid();
							} else {
								ResTopology parentTopology = resTopologyService
										.selectByPrimaryKey(topology
												.getParentTopologySid());
								if (parentTopology != null
										&& "VE".equals(parentTopology
												.getResTopologyType())) {
									veSid = parentTopology.getResTopologySid();
								}
							}
							if (!StringUtil.isNullOrEmpty(veSid)) {
								example.put("bizSid", bizResForm.getBizSid());
								example.put("resSetSid", veSid);
								List<MgtObjRes> relations = mgtObjResService
										.selectByParams(example);
								if (CollectionUtils.isEmpty(relations)) {
									MgtObjRes mgtObjRes = new MgtObjRes();
									mgtObjRes.setBizSid(res.getBizSid());
									mgtObjRes.setResSetType("RES-VE");
									mgtObjRes.setResCategory("-1");
									mgtObjRes.setResSid(veSid);
									mgtObjResService.insertSelective(mgtObjRes);
								}
							}
						}*/
                    }
                    // 判断该主机是否已关联
                    example = new Criteria();
                    example.put("bizSid", bizResForm.getBizSid());
                    example.put("resSetSid", res.getResSetSid());
                    List<MgtObjRes> relations = mgtObjResService
                            .selectByParams(example);
                    if (CollectionUtils.isEmpty(relations)) {
                        MgtObjRes mgtObjRes = new MgtObjRes();
                        mgtObjRes.setBizSid(res.getBizSid());
                        mgtObjRes.setResSetType(res.getResSetType());
                        mgtObjRes.setResCategory(res.getResCategory());
                        mgtObjRes.setResSid(res.getResSetSid());
                        mgtObjResService.insertSelective(mgtObjRes);
                    }
                }
            }
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                            .getMessage(WebConstants.MsgCd.INFO_RELATE_SUCCESS)));
        } catch (Exception e) {
            log.error("relateResource failure", e);
            returnJson = JsonUtil
                    .toJson(new RestResult(
                            RestResult.Status.FAILURE,
                            WebUtil.getMessage(WebConstants.MsgCd.ERROR_RELATE_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    /**
     * 查询一级业务类型信息
     */
    @Override
    public Response findFbizTypeInfo(Biz biz) {
        Criteria example = new Criteria();
        List<Biz> list = this.businesstypeService.selectFbizByParams(example);
        String json = JsonUtil.toJson(list);
        return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 查询二级业务类型信息
     */
    @Override
    public Response findSbizTypeInfo(Biz biz) {
        Criteria example = new Criteria();
        Long parentBizSid = biz.getParentBizSid();
        List<Biz> list = new ArrayList<Biz>();
        if (parentBizSid != null) {
            example.put("parentBizSid", parentBizSid);
            list = this.businesstypeService.selectByParams2(example);
        }
        String json = JsonUtil.toJson(list);
        return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 根据名称查询业务
     */
    @Override
    public Response findBizTypeInfo(Biz biz) {
        Criteria criteria = new Criteria();
        criteria.put("name", biz.getName());
        List<Biz> list = this.businesstypeService.selectByParams2(criteria);
        String json = JsonUtil.toJson(list);
        return Response.status(Status.OK).entity(json).build();
    }

    /**
     * 按条件查询所有二级业务类型
     */
    @Override
    @WebMethod
    @GET
    @Path("/findSubBizType/{bizSid}")
    public Response findSubBizTypeInfo(@PathParam("bizSid") String bizSid) {
        Criteria example = new Criteria();
        example.put("parentBizSid", bizSid);
        List<Biz> list = this.businesstypeService.selectByParams2(example);
        String json = JsonUtil.toJson(list);
        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    public Response findBizType(Long bizSid) {
        String returnJson;
        try {
            Criteria criteria = new Criteria();
            criteria.put("parentBizSids", bizSid);
            List<Biz> businesstype = this.businesstypeService
                    .selectByParams(criteria);
            // Biz businesstype =
            // this.businesstypeService.selectByPrimaryKey(bizSid);
            returnJson = JsonUtil.toJson(businesstype);
        } catch (Exception e) {
            log.error("findBizType failure.", e);
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                            .getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    @Override
    public Response getResourceZoneTree(MgtObj mgtObj) {
        String returnJson;
        try {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            String mode = mgtObj.getMode();
            Long bizSid = mgtObj.getMgtObjSid();
            if ("1".equals(mode) || "0".equals(mode)) {
                List<ResTopology> resTopologies = resTopologyService
                        .getAllResZoneTopology();
                List<ResTopologyNode> resTopologyNodes = new ArrayList<ResTopologyNode>();
                for (ResTopology resTopology : resTopologies) {
                    ResTopologyNode topologyNode = new ResTopologyNode();
                    topologyNode.setResTopologySid(resTopology
                                                           .getResTopologySid());
                    topologyNode.setParentTopologySid(resTopology
                                                              .getParentTopologySid());
                    topologyNode.setResTopologyName(resTopology
                                                            .getResTopologyName());
                    topologyNode.setResTopologyType(resTopology
                                                            .getResTopologyType());
                    resTopologyNodes.add(topologyNode);
                }
                resultMap.put("treeData", resTopologyNodes);
            }

            if ("2".equals(mode) || "0".equals(mode)) {
                if (bizSid != null) {
                    Criteria criteria = new Criteria();
                    criteria.put("bizSid", bizSid);
                    criteria.put("resSetType", WebConstants.RES_TOPOLOGY_TYPE.RZ);
                    List<MgtObjRes> mgtObjResList = mgtObjResService
                            .selectByParams(criteria);
                    resultMap.put("checkedData", mgtObjResList);
                }
            }
            returnJson = JsonUtil.toJson(resultMap);
        } catch (Exception e) {
            log.error("getResourceZoneTree failure.", e);
            // 获取失败信息构建
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                            .getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    @Override
    public Response getRemainQuota(Biz biz) {
        String returnJson;
        try {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("remainQuota", getRemainQuotaValue(biz));
            returnJson = JsonUtil.toJson(resultMap);
        } catch (Exception e) {
            log.error("getRemainQuota failure", e);
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                            .getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    private Long getRemainQuotaValue(Biz biz) throws ServiceException {
        Criteria queryParentBizQuotaParams = new Criteria();
        queryParentBizQuotaParams.put("quotaObjSid", biz.getParentBizSid());
        queryParentBizQuotaParams.put("quotaObj",
                                      WebConstants.QuotaObjectType.BIZ);
        queryParentBizQuotaParams.put("quotaKey", biz.getQuotaKey());
        List<Quota> quotaList = quotaService
                .selectByParams(queryParentBizQuotaParams);
        if (quotaList.size() != 1) {
            throw new ServiceException("except 1 query but actual "
                                       + quotaList.size() + " query");
        }
        Quota parentQuota = quotaList.get(0);
        Long usage = quotaService.selectByQuotaUsage(queryParentBizQuotaParams);
        return Long.parseLong(parentQuota.getQuotaValue())
               - (usage == null ? 0 : usage);
    }

    private void setRemainQuotaValue(Quota quota, Biz biz)
            throws ServiceException {
        Criteria queryParentBizQuotaParams = new Criteria();
        if (biz.getLevel() == 1) {
            queryParentBizQuotaParams.put("quotaObjSid", biz.getBizSid());
        } else if (biz.getLevel() == 2) {
            queryParentBizQuotaParams.put("quotaObjSid", biz.getParentBizSid());
        }
        queryParentBizQuotaParams.put("quotaObj",
                                      WebConstants.QuotaObjectType.BIZ);
        queryParentBizQuotaParams.put("quotaKey", biz.getQuotaKey());
        List<Quota> quotaList = quotaService
                .selectByParams(queryParentBizQuotaParams);
        if (quotaList.size() != 1) {
            throw new ServiceException("except 1 query but actual "
                                       + quotaList.size() + " query");
        }
        Quota parentQuota = quotaList.get(0);
        Long usage = quotaService.selectByQuotaUsage(queryParentBizQuotaParams);
        usage = usage == null ? 0 : usage;
        if (biz.getLevel() == 1) {
            quota.setUsagedQuota(usage);
            quota.setRemainQuota(Long.parseLong(parentQuota.getQuotaValue())
                                 - usage);
        } else if (biz.getLevel() == 2) {
            quota.setRemainQuota(Long.parseLong(parentQuota.getQuotaValue())
                                 - usage);
        }
    }

    @Override
    public Response updateMgtQuota(MgtObj mgtObj) {
        String returnJson;
        List<Quota> quotas = mgtObj.getMgtQuotas();
        if (!CollectionUtils.isEmpty(quotas)) {
            for (Quota quota : quotas) {
                // 先查询出来这条配额信息
                Criteria params = new Criteria();
                params.put("quotaKey", quota.getQuotaKey());
                params.put("quotaObjSid", mgtObj.getMgtObjSid());
                params.put("quotaType", quota.getQuotaType());
                List<Quota> oldQuotas = quotaService.selectByParams(params);
                if (!CollectionUtils.isEmpty(oldQuotas)) {
                    Quota oldQuota = oldQuotas.get(0);
                    // quota.setQuotaSid(oldQuota.getQuotaSid());
                    oldQuota.setQuotaValue(quota.getQuotaValue());
                    // 再更新
                    quotaService.updateByPrimaryKeySelective(oldQuota);
                } else {
                    quota.setQuotaObjSid(mgtObj.getMgtObjSid());
                    WebUtil.prepareInsertParams(quota);
                    quotaService.insertSelective(quota);
                }
            }
        }
        returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS,
                                                    "配额调整成功！"));
        return Response.status(Status.OK).entity(returnJson).build();
    }

    @Override
    public Response getQuotasInfo(String params) {
        String returnJson;
        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            // 判断父业务的配额是否设置
            Criteria queryBizQuotaParams = new Criteria();
            queryBizQuotaParams.put("quotaObjSid", map.get("mgtObjSid"));
            List<Quota> quotaList = quotaService
                    .selectByParams(queryBizQuotaParams);
            Map<String, Map> resultMap = new HashMap<String, Map>();
            Map<String, String> x86SumMap = new HashMap<String, String>();
            Map<String, String> aixSumMap = new HashMap<String, String>();
            for (Quota quota : quotaList) {
                if (WebConstants.QUOTA_TYPE.X86.equals(quota.getQuotaType())) {
                    x86SumMap.put(quota.getQuotaKey(), quota.getQuotaValue());
                } else if (WebConstants.QUOTA_TYPE.AIX.equals(quota
                                                                      .getQuotaType())) {
                    aixSumMap.put(quota.getQuotaKey(), quota.getQuotaValue());
                }
            }
            resultMap.put("x86", x86SumMap);
            resultMap.put("aix", aixSumMap);
            // 得到已使用的配额

            returnJson = JsonUtil.toJson(resultMap);
        } catch (Exception e) {
            log.error("getRemainQuota failure", e);
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                            .getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    @Override
    public Response getQuotas(Biz biz) {
        String returnJson;
        try {
            // 判断父业务的配额是否设置
            if (biz.getLevel() != null && biz.getLevel().equals(2L)
                && biz.getParentBizSid() != null) {
                Criteria queryParentBizQuotaParams = new Criteria();
                queryParentBizQuotaParams.put("quotaObjSid",
                                              biz.getParentBizSid());
                queryParentBizQuotaParams.put("quotaObj",
                                              WebConstants.QuotaObjectType.BIZ);
                queryParentBizQuotaParams.put("quotaKey", "cores");
                List<Quota> quotaList = quotaService
                        .selectByParams(queryParentBizQuotaParams);
                Boolean cpuQuotas = true;
                Boolean memQuotas = true;
                Boolean idoQuotas = true;
                Boolean idiQuotas = true;
                String resturnStr = "";
                if (quotaList.size() == 0) {
                    cpuQuotas = false;
                    resturnStr += "虚拟机核数,";
                }
                queryParentBizQuotaParams.put("quotaKey", "ram");
                quotaList = quotaService
                        .selectByParams(queryParentBizQuotaParams);
                if (quotaList.size() == 0) {
                    memQuotas = false;
                    resturnStr += "虚拟机内存(GB),";
                }
                queryParentBizQuotaParams.put("quotaKey", "vLanIdoLimit");
                quotaList = quotaService
                        .selectByParams(queryParentBizQuotaParams);
                if (quotaList.size() == 0) {
                    idoQuotas = false;
                    resturnStr += "业务外网IP数,";
                }
                queryParentBizQuotaParams.put("quotaKey", "vLanIdiLimit");
                quotaList = quotaService
                        .selectByParams(queryParentBizQuotaParams);
                if (quotaList.size() == 0) {
                    idiQuotas = false;
                    resturnStr += "业务内网IP数,";
                }
                if (!cpuQuotas || !memQuotas || !idoQuotas || !idiQuotas) {
                    returnJson = JsonUtil.toJson(new RestResult(
                            RestResult.Status.FAILURE, "请先设定业务属性的"
                                                       + resturnStr.substring(0,
                                                                              resturnStr.length()
                                                                              - 1) + "配额！"));
                    return Response.status(Status.OK).entity(returnJson)
                            .build();
                }
            }

            Criteria queryBizQuotaParams = new Criteria();
            queryBizQuotaParams.put("quotaObjSid", biz.getBizSid());
            queryBizQuotaParams.put("quotaObj",
                                    WebConstants.QuotaObjectType.BIZ);
            List<Quota> quotaList = quotaService
                    .selectByParams(queryBizQuotaParams);
            for (Quota quota : quotaList) {
                quota.setQuotaOldValue(quota.getQuotaValue());
                biz.setQuotaKey(quota.getQuotaKey());
                try {
                    setRemainQuotaValue(quota, biz);
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            }
            returnJson = JsonUtil.toJson(quotaList);
        } catch (Exception e) {
            log.error("getRemainQuota failure", e);
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                            .getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    @Override
    public Response createQuotas(List<Quota> quotas) {
        String returnJson;
        try {
            for (Quota quota : quotas) {
                if ("add".equals(quota.getOper())) {
                    quotaService.insertSelective(quota);
                } else if ("update".equals(quota.getOper())) {
                    quotaService.updateByPrimaryKeySelective(quota);
                } else if ("delete".equals(quota.getOper())) {
                    // 如果子业务存在同样配额,则删除连带删除子业务配额
                    Criteria criteria = new Criteria();
                    criteria.put("bizSid", quota.getQuotaObjSid());
                    criteria.put("quotaObj", 0);
                    List<Quota> childQuotaList = this.quotaService
                            .selectChildrenBizQuotas(criteria);
                    for (Quota childQuota : childQuotaList) {
                        quotaService.deleteByPrimaryKey(childQuota
                                                                .getQuotaSid());
                    }
                    quotaService.deleteByPrimaryKey(quota.getQuotaSid());
                } else {
                    throw new ServiceException("unknow oper type: "
                                               + quota.getOper());
                }
            }
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                            .getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS)));
        } catch (Exception e) {
            log.error("createQuotas failure", e);
            returnJson = JsonUtil
                    .toJson(new RestResult(
                            RestResult.Status.FAILURE,
                            WebUtil.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    @Override
    public Response findBizResForBar(Criteria criteria) {
        Map<String, List> map = new HashMap<String, List>();
        List<String> bizNames = new ArrayList<String>();
        List<Biz> bizs = businesstypeService.selectFbizByParams(criteria);
        for (Biz biz : bizs) {
            int cpuCores = 0;
            double memory = 0;
            long storage = 0;
            Criteria example = new Criteria();
            example.put("parentBizSid", biz.getBizSid());
			/*List<ResBizVm> list = resBizVmService.selectByParams2(example);

			for (ResBizVm si : list) {
				if (si != null) {
					cpuCores = cpuCores
							+ (si.getCpuCores() == null ? 0 : si.getCpuCores());
					memory = (double) (memory + (si.getMemorySize() == null ? 0.0
							: si.getMemorySize()));
					Criteria example2 = new Criteria();
					example2.put("resVmSid", si.getResBizVmSid());
					List<ResVd> selectByParams = resVdMapper
							.selectByParams(example2);
					for (ResVd resVd : selectByParams) {
						storage = storage
								+ ((resVd.getAllocateDiskSize() == null ? 0
										: resVd.getAllocateDiskSize()));
					}
				}
			}*/
            List reList = new ArrayList();
            reList.add(cpuCores);
            reList.add(BigDecimal
                               .valueOf(memory)
                               .divide(BigDecimal.valueOf(1024), 3,
                                       BigDecimal.ROUND_HALF_UP).doubleValue());
            reList.add(BigDecimal
                               .valueOf(storage)
                               .divide(BigDecimal.valueOf(1024), 3,
                                       BigDecimal.ROUND_HALF_UP).doubleValue());
            map.put(biz.getName(), reList);
            bizNames.add(biz.getName());
        }
        Map<String, List> listMap = new HashMap<String, List>();
        for (int i = 0; i < 3; i++) {
            List list = new ArrayList();
            for (String bizname : bizNames) {
                list.add(map.get(bizname).get(i));
            }
            if (i == 0) {
                listMap.put("cpu", list);
            } else if (i == 1) {
                listMap.put("mem", list);
            } else {
                listMap.put("storage", list);
            }
        }
        listMap.put("bizList", bizNames);
        String json = "";
        json = JsonUtil.toJson(listMap);

        return Response.status(Status.OK).entity(json).build();
    }

    public List<Biz> getResult(List<Biz> bizs) {
        List<Biz> bizList = new ArrayList<Biz>();
        for (Biz biz : bizs) {
            Criteria example = new Criteria();
            Long bizSid = biz.getBizSid();
            if (bizSid != null) {
                example.put("parentBizSid", bizSid);
                List<Biz> list = this.businesstypeService
                        .selectByParams2(example);
                if (list != null && list.size() != 0) {
                    bizList.addAll(getResult(list));
                }
            }
        }
        return bizList;
    }

    @Override
    @WebMethod
    @POST
    @Path("/bizQuotas")
    public Response getBizQuotas(Biz biz) {
        String returnJson;
        Map<String, String> map = new HashMap<String, String>();
        try {
            Criteria queryBizQuotaParams = new Criteria();
            queryBizQuotaParams.put("quotaObjSid", biz.getParentBizSid());
            queryBizQuotaParams.put("quotaObj",
                                    WebConstants.QuotaObjectType.BIZ);
            List<Quota> quotaList = quotaService
                    .selectByParams(queryBizQuotaParams);
            for (Quota quota : quotaList) {
                map.put(quota.getQuotaKey(),
                        (quota.getQuotaValue() == null ? "0" : quota
                                .getQuotaValue()));
            }
            returnJson = JsonUtil.toJson(map);
        } catch (Exception e) {
            log.error("getBizQuota failure", e);
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                            .getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    @Override
    @WebMethod
    @POST
    @Path("/findByFbizForSum")
    public Response findByFbizForSum(Criteria criteria) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        List<Biz> bizs = businesstypeService.selectFbizByParams(criteria);
        int sum = 0;
        for (Biz biz : bizs) {
            Criteria example = new Criteria();
            Long bizSid = biz.getBizSid();
            List<Biz> bizList = new ArrayList<Biz>();
            if (bizSid != null) {
                example.put("parentBizSid", bizSid);
                List<Biz> list = this.businesstypeService
                        .selectByParams2(example);
                bizList.addAll(list);
                if (list != null && list.size() != 0) {
                    bizList.addAll(getResult(list));
                }
            }
            map.put(biz.getName(), bizList.size());
            sum = sum + bizList.size();
        }
        StringBuffer json = new StringBuffer();
        for (int i = 0; i < bizs.size(); i++) {
            Integer integer = map.get(bizs.get(i).getName());
            if (i == bizs.size() - 1) {
                json.append("['" + bizs.get(i).getName() + "'," + integer + "]");
            } else {
                json.append("['" + bizs.get(i).getName() + "'," + integer
                            + "],");
            }
        }
        json.insert(0, '[');
        json.append(']');
        Map<String, String> strMap = new HashMap<String, String>();
        strMap.put("sumNum", sum + "");
        strMap.put("data", json.toString());
        String result = "";
        result = JsonUtil.toJson(strMap);
        return Response.status(Status.OK).entity(result).build();
    }

    /**
     * 根据查询条件查询业务类型
     */
    @Override
    @WebMethod
    @POST
    @Path("/findAll")
    public Response findAllBiz(Biz biz) {
        String returnJson;

        Criteria criteria = new Criteria();
        criteria.put("bizNameLike", biz.getBizNameLike());
        criteria.put("parentBizSids", biz.getParentBizSids());
        criteria.put("parentBizNameLike", biz.getParentBizNameLike());
        List<Biz> businesstype = this.businesstypeService
                .selectByParams(criteria);
        returnJson = JsonUtil.toJson(businesstype);

        return Response.status(Status.OK).entity(returnJson).build();
    }

    /**
     * 根据业务类型sid，查询业务类型信息
     */
    @Override
    @WebMethod
    @GET
    @Path("/findByPrimarySid/{bizSid}")
    public Response findByPrimarySid(@PathParam("bizSid") Long bizSid) {
        String returnJson;
        try {
            Biz businesstype = this.businesstypeService
                    .selectByPrimaryKey(bizSid);
            returnJson = JsonUtil.toJson(businesstype);
        } catch (Exception e) {
            log.error("findBizType failure.", e);
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                            .getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    @Override
    @WebMethod
    @POST
    @Path("/getMgtObjDefs")
    public Response getMgtObjDefs(String params) {
        String returnJson;
        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            Criteria example = new Criteria();
            example.put("mgtBizMode", map.get("mgtMode"));
            example.setOrderByClause("SORT_RANK");
            List<MgtObjDef> defs = mgtObjDefService.selectByParams(example);
            returnJson = JsonUtil.toJson(defs);
        } catch (Exception e) {
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                            .getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    @Override
    @WebMethod
    @POST
    @Path("/getMgtObjResList")
    public Response getMgtObjResList(String params) {
        String returnJson;
        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            Criteria example = new Criteria();
            example.put("bizSid", map.get("mgtObjSid"));
            List<MgtObjRes> mgtObjRes = mgtObjResService
                    .selectByParams(example);
            returnJson = JsonUtil.toJson(mgtObjRes);
        } catch (Exception e) {
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                            .getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    /**
     * 查询账户
     */
    @Override
    @WebMethod
    @POST
    @Path("/findBillAccount")
    public Response findBillAccount(MgtObj obj) {
        Criteria criteria = new Criteria();
        criteria.setOrderByClause("NAME");
        List<MgtObj> mgtObjList = this.mgtObjService
                .selectByBillingAccount(criteria);

        return Response.status(Status.OK).entity(JsonUtil.toJson(mgtObjList))
                .build();
    }

    /**
     * 查询账户
     */
    @Override
    @WebMethod
    @POST
    @Path("/findBillAccountByUser")
    public Response findBillAccountByUser(MgtObj obj) {
        Criteria criteria = new Criteria();
        criteria.put("userSid", obj.getAccountSid());
        criteria.setOrderByClause("NAME");
        List<MgtObj> mgtObjList = this.mgtObjService
                .selectByBillingAccount(criteria);

        return Response.status(Status.OK).entity(JsonUtil.toJson(mgtObjList))
                .build();
    }

    /**
     * 获取管理对象（项目）当前配额以及使用情况
     */
    @Override
    public Response getMgtObjQuotasDetail(Long mgtObjSid) {
        String returnJson = "";
		/*try {
			List<Map<String, Object>> quotas = new ArrayList<Map<String, Object>>();
			Map<String, Object> x86Quotas = this.quotaService
					.selectMgtObjQuotasByOsTypes(mgtObjSid,
							WebConstants.QUOTA_TYPE.X86);
			Map<String, Object> powerQuotas = this.quotaService
					.selectMgtObjQuotasByOsTypes(mgtObjSid,
							WebConstants.QUOTA_TYPE.AIX);
			// 云主机数量配额情况
			Map<String, Object> vmMap = new HashMap<String, Object>();
			vmMap.put("quota", "instances");
			vmMap.put("x86Quota", x86Quotas.get("vmQuota"));
			vmMap.put("x86Used", x86Quotas.get("vmUsed"));
			vmMap.put("x86Unused", ResDataUtils
					.transferNegativeToZero((Long) x86Quotas.get("vmUnused")));
			vmMap.put("powerQuota", powerQuotas.get("vmQuota"));
			vmMap.put("powerUsed", powerQuotas.get("vmUsed"));
			vmMap.put("powerUnused", ResDataUtils
					.transferNegativeToZero((Long) powerQuotas.get("vmUnused")));
			quotas.add(vmMap);
			// CPU配额情况
			Map<String, Object> cpuMap = new HashMap<String, Object>();
			cpuMap.put("quota", "cpu");
			cpuMap.put("x86Quota", x86Quotas.get("cpuQuota"));
			cpuMap.put("x86Used", x86Quotas.get("cpuUsed"));
			cpuMap.put("x86Unused", ResDataUtils
					.transferNegativeToZero((Long) x86Quotas.get("cpuUnused")));
			cpuMap.put("powerQuota", powerQuotas.get("cpuQuota"));
			cpuMap.put("powerUsed", powerQuotas.get("cpuUsed"));
			cpuMap.put("powerUnused",
					ResDataUtils.transferNegativeToZero((Long) powerQuotas
							.get("cpuUnused")));
			quotas.add(cpuMap);
			// 内存配额情况
			Map<String, Object> memoryMap = new HashMap<String, Object>();
			memoryMap.put("quota", "memory");
			memoryMap.put("x86Quota", x86Quotas.get("memoryQuota"));
			memoryMap.put("x86Used", x86Quotas.get("memoryUsed"));
			memoryMap.put("x86Unused", ResDataUtils
					.transferNegativeToZero((Long) x86Quotas
							.get("memoryUnused")));
			memoryMap.put("powerQuota", powerQuotas.get("memoryQuota"));
			memoryMap.put("powerUsed", powerQuotas.get("memoryUsed"));
			memoryMap.put("powerUnused", ResDataUtils
					.transferNegativeToZero((Long) powerQuotas
							.get("memoryUnused")));
			quotas.add(memoryMap);
			// 外置存储配额情况
			Map<String, Object> stMap = new HashMap<String, Object>();
			stMap.put("quota", "storage");
			stMap.put("x86Quota", x86Quotas.get("storageQuota"));
			stMap.put("x86Used", x86Quotas.get("storageUsed"));
			stMap.put("x86Unused", ResDataUtils
					.transferNegativeToZero((Long) x86Quotas
							.get("storageUnused")));
			stMap.put("powerQuota", powerQuotas.get("storageQuota"));
			stMap.put("powerUsed", powerQuotas.get("storageUsed"));
			stMap.put("powerUnused", ResDataUtils
					.transferNegativeToZero((Long) powerQuotas
							.get("storageUnused")));
			quotas.add(stMap);
			// 将null值转化为""
			returnJson = HttpUtils.customToJson(quotas);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			returnJson = JsonUtil
					.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
							.getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
		}*/
        return Response.status(Status.OK).entity(returnJson).build();
    }

    /**
     * 检查订单申请资源是否超过配额
     */
    @Override
    public Response checkOrderQutoa(List<Map<String, Object>> orderRequestList) {
        String returnJson;
        try {
            for (Map<String, Object> orderRequest : orderRequestList) {
                String quotaType = orderRequest.get("quotaType").toString();
                String cpu = orderRequest.get("cpu").toString();
                String memory = orderRequest.get("memory").toString();
                String vmCount = orderRequest.get("vmCount").toString();
                String storage = orderRequest.get("storage").toString();
                String mgtObjSid = orderRequest.get("mgtObjSid").toString();

                Map<String, Object> quotas = null;
                if (WebConstants.QUOTA_TYPE.X86.equals(quotaType)) {
                    quotas = this.quotaService.selectMgtObjQuotasByOsTypes(
                            Long.parseLong(mgtObjSid),
                            WebConstants.QUOTA_TYPE.X86);
                } else if (WebConstants.OS_TYPE.AIX.equals(quotaType)) {
                    quotas = this.quotaService.selectMgtObjQuotasByOsTypes(
                            Long.parseLong(mgtObjSid),
                            WebConstants.QUOTA_TYPE.AIX);
                }
                if (quotas.get("vmQuota") == null) {
                    returnJson = JsonUtil.toJson(new RestResult(
                            RestResult.Status.FAILURE, "项目配额(" + quotaType
                                                       + ")[虚拟机个数]尚未设置， 请设置后再进行云主机申请。"));
                    return Response.status(Status.OK).entity(returnJson)
                            .build();
                }
                if (quotas.get("cpuQuota") == null) {
                    returnJson = JsonUtil.toJson(new RestResult(
                            RestResult.Status.FAILURE, "项目配额(" + quotaType
                                                       + ")[CPU核数]尚未设置， 请设置后再进行云主机申请。"));
                    return Response.status(Status.OK).entity(returnJson)
                            .build();
                }
                if (quotas.get("memoryQuota") == null) {
                    returnJson = JsonUtil.toJson(new RestResult(
                            RestResult.Status.FAILURE, "项目配额(" + quotaType
                                                       + ")[内存]尚未设置， 请设置后再进行云主机申请。"));
                    return Response.status(Status.OK).entity(returnJson)
                            .build();
                }
                if (quotas.get("storageQuota") == null) {
                    returnJson = JsonUtil.toJson(new RestResult(
                            RestResult.Status.FAILURE, "项目配额(" + quotaType
                                                       + ")[外置存储容量]尚未设置， 请设置后再进行云主机申请。"));
                    return Response.status(Status.OK).entity(returnJson)
                            .build();
                }

                // 检查云主机数配额
                Long vmUnused = (Long) quotas.get("vmUnused");
                if (vmUnused - Long.parseLong(vmCount) < 0) {
                    returnJson = JsonUtil.toJson(new RestResult(
                            RestResult.Status.FAILURE, "云主机申请数量超出项目配额(" + quotaType
                                                       + "), 请修改后重新申请。"));
                    return Response.status(Status.OK).entity(returnJson)
                            .build();
                }
                // 检查Cpu配额
                Long cpuUnused = (Long) quotas.get("cpuUnused");
                if (cpuUnused - Long.parseLong(cpu) < 0) {
                    returnJson = JsonUtil.toJson(new RestResult(
                            RestResult.Status.FAILURE, "云主机申请总CPU核数超出项目配额("
                                                       + quotaType + "), 请修改后重新申请。"));
                    return Response.status(Status.OK).entity(returnJson)
                            .build();
                }
                // 检查Memory配额
                Long memoryUnused = (Long) quotas.get("memoryUnused");
                if (memoryUnused - Long.parseLong(memory) < 0) {
                    returnJson = JsonUtil.toJson(new RestResult(
                            RestResult.Status.FAILURE, "云主机申请总内存超出项目配额(" + quotaType
                                                       + "), 请修改后重新申请。"));
                    return Response.status(Status.OK).entity(returnJson)
                            .build();
                }
                // 检查外置存储配额
                Long storageUnused = (Long) quotas.get("storageUnused");
                if (storageUnused - Long.parseLong(storage) < 0) {
                    returnJson = JsonUtil.toJson(new RestResult(
                            RestResult.Status.FAILURE, "云主机申请总存储超出项目配额(" + quotaType
                                                       + "), 请修改后重新申请。"));
                    return Response.status(Status.OK).entity(returnJson)
                            .build();
                }
            }
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.SUCCESS, ""));
            return Response.status(Status.OK).entity(returnJson).build();
        } catch (Exception e) {
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                            .getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    @Override
    @WebMethod
    @POST
    @Path("/findAllProject")
    public List findAllProject(Criteria criteria) {
        // List<Map<String,String>> resultList = new
        // ArrayList<Map<String,String>>();
        // List<UserMgtObjKey> userMgts =
        // userMgtObjService.selectByParams(criteria);
        // if(!CollectionUtils.isEmpty(userMgts)){
        // for(UserMgtObjKey userMgtObj : userMgts) {
        // Criteria params = new Criteria();
        // params.put("mgtObjSid", userMgtObj.getMgtObjSid());
        // List<MgtObjExt> mgtObjExts =
        // this.mgtObjExtService.selectByParams(params);
        // criteria.put("mgtObjSid", userMgtObj.getMgtObjSid());
        // List<MgtObj> mgtObjs = this.mgtObjService.selectByParams(criteria);
        // if(!CollectionUtils.isEmpty(mgtObjs)){
        // Map<String,String> map = new HashMap<String, String>();
        // map.put("statusName", mgtObjs.get(0).getStatusName());
        // map.put("status", mgtObjs.get(0).getStatus());
        // map.put("mgtObjSid", userMgtObj.getMgtObjSid()+"");
        // for (MgtObjExt mgtObjExt : mgtObjExts) {
        // map.put(mgtObjExt.getAttrKey(), mgtObjExt.getAttrValue());
        // }
        // resultList.add(map);
        // }
        // }
        // }
        List<MgtObj> projects = this.mgtObjService.selectAllProject(criteria);
        return projects;
    }

    @Override
    @WebMethod
    @POST
    @Path("/findMgtObjComTree")
    public Response findMgtObjComTree(String params) {
        String returnJson;
        List<ResTopology> mgtObjRes = new ArrayList<ResTopology>();
        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            Criteria example = new Criteria();
            // example.put("osVersion", map.get("osVersion"));
            if (WebUtil.isNotBlank(map.get("mgtObjSid"))) {
                example.put("mgtObjSid", map.get("mgtObjSid"));
            }
            if (WebUtil.isNotBlank(map.get("resPoolType"))) {
                example.put("resPoolType", map.get("resPoolType"));
            }
            if (WebUtil.isNotBlank(map.get("partitionType"))) {
                example.put("partitionType", map.get("partitionType"));
            }
            mgtObjRes = mgtObjService.selectMgtObjComByParams(example);
            // 如果没有关联的资源，就查询父级
            if (CollectionUtils.isEmpty(mgtObjRes)) {
                MgtObj mgtObj = mgtObjService.selectByPrimaryKey(Long
                                                                         .parseLong(
                                                                                 map.get("mgtObjSid")
                                                                                         .toString()));
                List<MgtObjRes> bizResList = new ArrayList<MgtObjRes>();
                if (null != mgtObj.getParentId()) {
                    Criteria criteria = new Criteria();
                    criteria.put("bizSid", mgtObj.getParentId());
                    criteria.put("resSetType", WebConstants.RES_TOPOLOGY_TYPE.RZ);
                    bizResList = mgtObjResService.selectByParams(criteria);
                    // 检查父业务是否关联资源分区
                    if (!CollectionUtils.isEmpty(bizResList)) {
                        // 得到所有顶级资源R
                        List<String> resourceZones = new ArrayList<String>();
                        for (MgtObjRes bizRes : bizResList) {
                            resourceZones.add(bizRes.getResSetSid());
                        }
                        Criteria resZoneParam = new Criteria();
                        if (WebUtil.isNotBlank(map.get("resPoolType"))) {
                            resZoneParam.put("resPoolType",
                                             map.get("resPoolType"));
                        }
                        resZoneParam.put("resTopologySidList", resourceZones);
                        mgtObjRes = mgtObjService
                                .selectMgtObjResZoneTopologyByParams(resZoneParam);
                    }
                }
            }
            returnJson = JsonUtil.toJson(mgtObjRes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            returnJson = JsonUtil
                    .toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                            .getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE)));
        }
        return Response.status(Status.OK).entity(returnJson).build();
    }

    @Override
    @WebMethod
    @POST
    @Path("/uploadMgtObjFile")
    public Response importFile(List<Attachment> attachments,
                               @Context HttpServletRequest request) {
        String json = "";
		/*try {
			List<String> sidList = FileUtil
					.uploadOnlyAttachmentFile(attachments);
			json = JsonUtil.toJson(sidList);
		} catch (Exception e) {
			log.error("fileupload error.", e);
		}*/
        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    @WebMethod
    @GET
    @Path("/exportMgtObjRes")
    @Produces("application/vnd.ms-excel")
    public Response exportMgtObjRes(@Context HttpServletResponse servletResponse) {
        // 得到要导出的数据集
        List<Map<String, Object>> excelData = new ArrayList<Map<String, Object>>();

        Criteria example = new Criteria();
        example.put("level", 1);
        example.put("status", "02");
        List<MgtObj> mgtObjs = mgtObjService.selectBaseFileByParams(example);
        if (!CollectionUtils.isEmpty(mgtObjs)) {
            for (MgtObj mgtObj : mgtObjs) {
                Map<String, Object> map = new HashMap<String, Object>();

                example = new Criteria();
                example.put("mgtObjSid", mgtObj.getMgtObjSid());
                List<MgtObjExt> mgtObjExts = this.mgtObjExtService
                        .selectByParams(example);
                mgtObj.setMgtObjExts(mgtObjExts);
                // 项目管理员信息
                example = new Criteria();
                example.put("mgtObjSid", mgtObj.getMgtObjSid());
                List<User> managers = this.userService
                        .findUsersByMgtObjSid(example);
                // if(CollectionUtils.isEmpty(managers)){
                // continue;
                // }
                map.put("manager", CollectionUtils.isEmpty(managers) ? ""
                                                                     : managers.get(0));
                // 项目信息
                map.put("mgtObj", mgtObj);
                // 项目配额
                example = new Criteria();
                example.put("quotaObjSid", mgtObj.getMgtObjSid());
                List<Quota> mgtObjQuotas = quotaService
                        .countByQuotaKey(example);
                map.put("quota", mgtObjQuotas);
                // 主机信息

                // 查询退订中、已开通
                String statusParams = "'"
                                      + WebConstants.ServiceInstanceStatus.CANCELING + "','"
                                      + WebConstants.ServiceInstanceStatus.CHANGEING + "','"
                                      + WebConstants.ServiceInstanceStatus.REFUSED + "','"
                                      + WebConstants.ServiceInstanceStatus.OPENED
                                      + "'".replace(" ", "");
                // 虚机
                example = new Criteria();
                example.put("mgtObjSid", mgtObj.getMgtObjSid());
                example.put("instStatusParams", statusParams);
				/*List<ResVm> vms = resVmService.selectByParams(example);
				if (!CollectionUtils.isEmpty(vms)) {
					for (ResVm vm : vms) {
						// List<ResVe> resVes =
						// resVeService.selectVeByResHost(vm.getAllocateResHostSid());
						// if(!CollectionUtils.isEmpty(resVes)){
						// vm.setVirtualPlatformType(resVes.get(0).getVirtualPlatformType());
						if (StringUtil.isNullOrEmpty(vm.getParType())) {
							vm.setVirtualPlatformType("X86");
						} else if (WebConstants.PowerPartitionType.MPAR.equals(vm.getParType().toString())) {
							vm.setVirtualPlatformType("MPAR");
						} else if (WebConstants.PowerPartitionType.LPAR.equals(vm.getParType().toString())) {
							vm.setVirtualPlatformType("LPAR");
						} else {
							vm.setVirtualPlatformType("X86");
						}
						// }
						// 外置存储
						example = new Criteria();
						example.put("resVmSid", vm.getResVmSid());
						*//*List<ResVd> vds = resVdService.selectByParams(example);
						List<ResVd> dataDisks = new ArrayList<ResVd>();
						if (!CollectionUtils.isEmpty(vds)) {
							for (ResVd vd : vds) {
								if (WebConstants.StoragePurpose.SYSTEM_DISK
										.equals(vd.getStoragePurpose())) {
									vm.setSysDiskSize(vd.getAllocateDiskSize());
								} else if (WebConstants.StoragePurpose.DATA_DISK
										.equals(vd.getStoragePurpose())) {
									Criteria param = new Criteria();
									param.put("resVdSid", vd.getResVdSid());
									List<ResVfc> vfcs = resVfcMapper
											.selectByParams(param);
									if (!CollectionUtils.isEmpty(vfcs)) {
										vd.setResVfc(vfcs.get(0));
									}
									dataDisks.add(vd);
								}
							}
						}
						vm.setResVdList(dataDisks);*//*
						List<ResIp> resIpList = resIpMapper
								.selectResIpByVM(example);
						vm.setResIpList(resIpList);
						// 软件信息
						example = new Criteria();
						example.put("resSid", vm.getResVmSid());
						*//*List<ResOsSoftware> softwares = osSoftwareService
								.selectByParams(example);
						vm.setSoftwares(softwares);*//*

						example = new Criteria();
						example.put("resHostSid", vm.getAllocateResHostSid());
						*//*List<ResHost> hosts = resHostService
								.selectByParams(example);
						if (!CollectionUtils.isEmpty(hosts)
								&& hosts.get(0) != null) {
							vm.setResHost(hosts.get(0));
						}*//*

						// 用户
						example = new Criteria();
						example.put("resSid", vm.getResVmSid());
						List<ResOsUser> osUsers = resOsUserService
								.selectByParams(example);
						vm.setResOsUsers(osUsers);

					}
				}
				map.put("vmList", vms);*/
                // 物理机
                example = new Criteria();
                example.put("mgtObjSid", mgtObj.getMgtObjSid());
				/*List<ResHost> hosts = resHostService.selectByParams(example);
				if (!CollectionUtils.isEmpty(hosts)) {
					for (ResHost host : hosts) {
						example = new Criteria();
						example.put("resHostSid", host.getResHostSid());
						List<ResStorage> storages = resStorageService
								.selectStorageByHostSid(example);
						List<ResStorage> dataDisks = new ArrayList<ResStorage>();
						if (!CollectionUtils.isEmpty(storages)) {
							for (ResStorage st : storages) {
								if (WebConstants.StoragePurpose.SYSTEM_DISK
										.equals(st.getStoragePurpose())) {
									host.setSysDiskSize(st.getTotalCapacity());
								} else if (WebConstants.StoragePurpose.DATA_DISK
										.equals(st.getStoragePurpose())) {
									Criteria param = new Criteria();
									param.put("resVdSid", st.getResStorageSid());
									List<ResVfc> vfcs = resVfcMapper
											.selectByParams(param);
									if (!CollectionUtils.isEmpty(vfcs)) {
										st.setResVfc(vfcs.get(0));
									}
									dataDisks.add(st);
								}
							}
						}
						host.setResStorages(dataDisks);
						// 软件信息
						example = new Criteria();
						example.put("resSid", host.getResHostSid());
						*//*List<ResOsSoftware> softwares = osSoftwareService
								.selectByParams(example);
						host.setSoftwares(softwares);*//*

						// 用户
						example = new Criteria();
						example.put("resSid", host.getResHostSid());
						List<ResOsUser> osUsers = resOsUserService
								.selectByParams(example);
						host.setResOsUsers(osUsers);
					}
				}
				map.put("hostList", hosts);
*/
                excelData.add(map);
            }
        }

        String tempPath = PropertiesUtil.getProperty("upload.tmpfile.path");
        File fileDir = new File(tempPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String date = StringUtil.dateFormat(new Date(), StringUtil.DF_YMD);
        String systemName = PropertiesUtil.getProperty("system.name");
        String fileName = "" + systemName + "-项目资源统计表-" + date + ".xls";

        String path = tempPath + File.separator + fileName;
		/*ExcelUtil eu = new ExcelUtil();
		try {
			HSSFWorkbook workbook = eu.exportMgtObjResExcel(excelData);
			// 新建一输出文件流
			FileOutputStream fOut = new FileOutputStream(path);
			// 把相应的Excel 工作簿存盘
			workbook.write(fOut);
			fOut.flush();
			// 操作结束，关闭文件
			fOut.close();

			System.out.println("--------------  文件已生成  -------------");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
        POIUtil poiUtil = new POIUtil();
        poiUtil.fileDownLoad(servletResponse, path, fileName);
        poiUtil.deleteFile(path);
        return null;
    }

    @Override
    @WebMethod
    @POST
    @Path("/changeMgtObjManager")
    public Response changeMgtObjManager(String params) {
        String resultJson = "";

        Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
        if (!StringUtil.isNullOrEmpty(map.get("mgtObjSid"))) {
            Criteria example = new Criteria();
            example.put("mgtObjSid", map.get("mgtObjSid"));
            List<User> oldManager = userService.findUsersByMgtObjSid(example);

            int result = mgtObjService.changeManager(map.get("mgtObjSid").toString(),
                                                     map.get("userSid").toString(),
                                                     oldManager.get(0).getUserSid().toString());

            if (result == 1) {
                resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS,
                                                            "项目经理变更提交成功！", null));

            }
        } else {
            resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, "项目经理变更提交失败！",
                                                        null));
        }

        return Response.status(Status.OK).entity(resultJson).build();
    }

    /**
     * 根据服务实例id找到和该实例所属项目拥有相同资源的项目
     */
    @Override
    @WebMethod
    @POST
    @Path("/findMgtObjByInstance")
    public Response findMgtObjByInstance(String params) {
        String resultJson = "";

        Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
        if (!StringUtil.isNullOrEmpty(map.get("instanceSid"))) {
            ServiceInstance
                    instance =
                    serviceInstanceService.selectByPrimaryKey(
                            Long.parseLong(map.get("instanceSid").toString()));

            Criteria example = new Criteria();
            example.put("bizSid", instance.getMgtObjSid());
            List<MgtObjRes> mgtObjRes = mgtObjResService.selectByParams(example);

            Criteria condition = new Criteria();
            condition.put("level", 1);
            List<MgtObj> mgtObjs = mgtObjService.selectBaseFileByParams(condition);
            resultJson = JsonUtil.toJson(mgtObjs);
        }

        return Response.status(Status.OK).entity(resultJson).build();
    }

}
