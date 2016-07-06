package com.h3c.idcloud.core.rest.system;

import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.h3c.idcloud.core.pojo.dto.system.Biz;
import com.h3c.idcloud.core.pojo.dto.system.BizResForm;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.system.MgtObjExt;
import com.h3c.idcloud.core.pojo.dto.system.Quota;
import com.h3c.idcloud.infrastructure.common.pojo.Attachment;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.springframework.web.bind.annotation.RequestBody;

/**
 * 管理对象Restful接口
 *
 * @author ChengQi
 */
@Path("/mgtObj")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface MgtObjRest {

    /**
     * 查询所有管理对象
     */
    @WebMethod
    @POST
    Response findAllMgtObj(MgtObj obj);

    /**
     * 查询所有管理对象
     */
    @WebMethod
    @POST
    @Path("/findAllMgtObj")
    Response findAllMgtObjForTree(MgtObj obj);

    /**
     * 查询所有管理对象
     */
    @WebMethod
    @POST
    @Path("/find")
    Response findMgtObj(Criteria criteria);

    /**
     * 判断英文以及编号等属性是否重复
     */
    @WebMethod
    @POST
    @Path("/checkMgtObjExt")
    List<MgtObjExt> checkMgtObjExt(Criteria criteria);

    /**
     * 查询所有管理对象
     */
    @WebMethod
    @POST
    @Path("/findMgtObjTreeData")
    Response findMgtObjTreeData(Criteria criteria);

    /**
     * 查询所有管理对象
     */
    @WebMethod
    @POST
    @Path("/findByParams")
    List<Map<String, String>> findMgtObjByParams(Criteria criteria);

    /**
     * 查询可以修改的父管理对象
     */
    @WebMethod
    @POST
    @Path("/find/parent")
    Response findParentMgtObj(Criteria criteria);

    /**
     * 查询账户信息
     */
    @WebMethod
    @POST
    @Path("/findBillAccount")
    Response findBillAccount(MgtObj obj);

    /**
     * 查询用户下的账户信息
     */
    @WebMethod
    @POST
    @Path("/findBillAccountByUser")
    Response findBillAccountByUser(MgtObj obj);

    /**
     * 查询管理对象信息
     */
    @WebMethod
    @POST
    @Path("/load/{mgtObjSid}")
    Response loadMgtObj(@PathParam("mgtObjSid") Long mgtObjSid);

    /**
     * 查询管理对象定义
     */
    @WebMethod
    @POST
    @Path("/load/def")
    Response loadMgtObjDef(Criteria criteria);

    /**
     * 新增管理对象
     */
    @WebMethod
    @POST
    @Path("/create")
    Response addMgtObj(MgtObj mgtObj);

    /**
     * 新增管理对象
     */
    @WebMethod
    @POST
    @Path("/createMgtGroup")
    Response addMgtObjGroup(MgtObj mgtObj);

    /**
     * 更新管理对象
     */
    @WebMethod
    @POST
    @Path("/update")
    Response updateMgtObj(MgtObj mgtObj);

    /**
     * 更新管理对象
     */
    @WebMethod
    @POST
    @Path("/approveMgtObj")
    Response approveMgtObj(MgtObj mgtObj);

    /**
     * 删除管理对象
     */
    @WebMethod
    @GET
    @Path("/delete/{mgtObjSid}/{delBaseRes}")
    Response deleteMgtObj(@PathParam("mgtObjSid") Long mgtObjSid,
                                 @PathParam("delBaseRes") boolean delBaseRes);

    /**
     * 查询计算资源Tree信息
     */
    @WebMethod
    @POST
    @Path("/compute")
    Response getComputeResourceTree(MgtObj mgtObj);

    /**
     * 查询网络资源Tree信息
     */
    @WebMethod
    @POST
    @Path("/network")
    Response getNetworkResourceTree(MgtObj mgtObj);

    /**
     * 查询存储资源Tree信息
     */
    @WebMethod
    @POST
    @Path("/storage")
    Response getStroageResourceTree(MgtObj mgtObj);

    /**
     * 业务类型关联资源
     */
    @WebMethod
    @POST
    @Path("/relate")
    Response createRelateResource(@RequestBody BizResForm bizResForm);

    /**
     * 业务类型树形关联资源
     */
    @WebMethod
    @POST
    @Path("/relateTreeRes")
    Response createRelateTreeResource(@RequestBody BizResForm bizResForm);

    /**
     * 得到业务资源使用情况
     */
    @WebMethod
    @POST
    @Path("/findBizResForBar")
    Response findBizResForBar(Criteria criteria);

    /**
     * 查询一级业务
     */
    @WebMethod
    @POST
    @Path("/findFbizType")
    Response findFbizTypeInfo(Biz biz);

    /**
     * 查询二级业务
     */
    @WebMethod
    @POST
    @Path("/findSbizType")
    Response findSbizTypeInfo(Biz biz);


    /**
     * 根据名称查询业务
     */
    @WebMethod
    @POST
    @Path("/findBizType")
    Response findBizTypeInfo(Biz biz);

    /**
     * 根据查询条件查询业务类型
     */
    @WebMethod
    @POST
    @Path("/findAll")
    Response findAllBiz(Biz biz);

    /**
     * 根据业务类型sid，查询业务类型信息
     */
    @WebMethod
    @GET
    @Path("/{bizSid}")
    Response findBizType(@PathParam("bizSid") Long bizSid);

    /**
     * 根据业务类型sid，查询业务类型信息
     */
    @WebMethod
    @GET
    @Path("/findByPrimarySid/{bizSid}")
    Response findByPrimarySid(@PathParam("bizSid") Long bizSid);


    /**
     * 查询资源分区Tree信息
     */
    @WebMethod
    @POST
    @Path("/rz")
    Response getResourceZoneTree(MgtObj mgtObj);


    /**
     * 获取项目的配额总量和已使用
     */
    @WebMethod
    @POST
    @Path("/updateMgtQuota")
    Response updateMgtQuota(MgtObj mgtObj);

    /**
     * 获取项目的配额总量和已使用
     */
    @WebMethod
    @POST
    @Path("/getQuotasInfo")
    Response getQuotasInfo(String params);

    /**
     * 获取业务类型的剩余配额
     */
    @WebMethod
    @POST
    @Path("/remain/quota")
    Response getRemainQuota(Biz biz);

    /**
     * 获取业务类型的配额列表
     */
    @WebMethod
    @POST
    @Path("/quotas")
    Response getQuotas(Biz biz);

    /**
     * 获取业务类型的配额列表，用于前台订单提交的判断
     */
    @WebMethod
    @POST
    @Path("/bizQuotas")
    Response getBizQuotas(Biz biz);

    /**
     * 创建配额配置
     */
    @WebMethod
    @POST
    @Path("/quotas/create")
    Response createQuotas(List<Quota> quotas);

    /**
     * 按条件查询所有二级业务类型
     */
    @WebMethod
    @GET
    @Path("/findSubBizType/{bizSid}")
    Response findSubBizTypeInfo(@PathParam("bizSid") String bizSid);

    /**
     * 按一级业务进行业务的统计
     */
    @WebMethod
    @POST
    @Path("/findByFbizForSum")
    Response findByFbizForSum(Criteria criteria);

    /**
     * 得到管理对象的所有字段
     */
    @WebMethod
    @POST
    @Path("/getMgtObjDefs")
    Response getMgtObjDefs(String params);

    /**
     * 得到管理对象的资源
     */
    @WebMethod
    @POST
    @Path("/getMgtObjResList")
    Response getMgtObjResList(String params);

    @WebMethod
    @GET
    @Path("/quotas/{mgtObjSid}")
    Response getMgtObjQuotasDetail(@PathParam("mgtObjSid") Long mgtObjSid);

    @WebMethod
    @POST
    @Path("/quotas/check")
    Response checkOrderQutoa(List<Map<String, Object>> orderRequestList);

    @WebMethod
    @POST
    @Path("/findAllProject")
    List<Map<String, String>> findAllProject(Criteria criteria);

    @WebMethod
    @POST
    @Path("/findMgtObjComTree")
    Response findMgtObjComTree(String params);

    @WebMethod
    @POST
    @Path("/uploadMgtObjFile")
    Response importFile(List<Attachment> attachments, @Context HttpServletRequest request);

    @WebMethod
    @GET
    @Path("/exportMgtObjRes")
    @Produces("application/vnd.ms-excel")
    Response exportMgtObjRes(@Context HttpServletResponse servletResponse);

    @WebMethod
    @POST
    @Path("/changeMgtObjManager")
    Response changeMgtObjManager(String params);

    @WebMethod
    @POST
    @Path("/findMgtObjByInstance")
    Response findMgtObjByInstance(String params);

}
