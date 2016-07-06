package com.h3c.idcloud.core.rest.res.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.core.rest.res.ResHostRest;
import com.h3c.idcloud.core.service.res.api.ResHostService;
import com.h3c.idcloud.core.service.res.api.ResTopologyService;
import com.h3c.idcloud.infrastructure.common.pojo.BaseGridReturn;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by jpg on 2016/3/2.
 */
@Component
public class ResHostRestImpl implements ResHostRest {

    Logger log = LoggerFactory.getLogger(this.getClass());

    /** 资源拓扑Service */
    @Reference(version = "1.0.0")
    ResHostService resHostService;

    /** 资源拓扑Service */
    @Reference(version = "1.0.0")
    ResTopologyService resTopologyService;

    /**
     * 查询主机列表
     *
     * @param host
     * @return
     */
    @Override
    public Response findHost(ResHost host) {
        Criteria example = new Criteria();
        if (host != null) {
            if (!StringUtil.isNullOrEmpty(host.getHostName())) {
                example.put("hostName", host.getHostName());
            }
            if (!StringUtil.isNullOrEmpty(host.getHostNameLike())) {
                example.put("hostNameLike", host.getHostNameLike());
            }
            if (!StringUtil.isNullOrEmpty(host.getResHostSid())) {
                example.put("resHostSid", host.getResHostSid());
            }
            if (!StringUtil.isNullOrEmpty(host.getHostOsType())) {
                example.put("hostOsType", host.getHostOsType());
            }
            if (!StringUtil.isNullOrEmpty(host.getStatus())) {
                example.put("status", host.getStatus());
            }
            if (!StringUtil.isNullOrEmpty(host.getHostIp())) {
                example.put("hostIp", host.getHostIp());
            }
            if (!StringUtil.isNullOrEmpty(host.getResTopologySid())) {
                example.put("resTopologySid", host.getResTopologySid());
            }
            if (!StringUtil.isNullOrEmpty(host.getResHostTopologyType())) {
                example.put("resHostTopologyType", host.getResHostTopologyType());
            }
            if (!StringUtil.isNullOrEmpty(host.getIsViosFlag())) {
                example.put("isViosFlag", host.getIsViosFlag());
            }

            if (!StringUtil.isNullOrEmpty(host.getIsNullResPoolSid())) {
                example.put("isNullResPoolSid", host.getIsNullResPoolSid());
            }
            if (!StringUtil.isNullOrEmpty(host.getResPoolSid())) {
                example.put("resPoolSid", host.getResPoolSid());
            }
        }

        List<ResHost> list = this.resHostService.selectByParams(example);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();

    }

    /**
     * 查询主机列表，并采用服务器分页方式
     *
     * @param request
     * @return
     */
    @Override
    public Response findHostByPaging(@Context HttpServletRequest request) {
        Criteria param = new Criteria();
        WebUtil.preparePageParams(request, param, "RES_HOST_SID");

        param.setOrderByClause("A.HOST_NAME ASC");

        List<ResHost> list = this.resHostService.selectByParams(param);
        int total = this.resHostService.countByParams(param);

        String json = JsonUtil.toJson(new RestResult(new BaseGridReturn(total, list)));

        return Response.ok(json).build();
    }

    /**
     * 根据sid查询主机信息
     *
     * @param resHostSid
     * @return
     */
    @Override
    public Response findResHostBySid(String resHostSid) {
        ResHost list = this.resHostService.selectByPrimaryKey(resHostSid);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();
    }

    /**
     * 查询可以添加到集群的主机列表
     *
     * @param resTopologySid
     * @return
     */
    @Override
    public Response findAddHostToPool(String resTopologySid) {
        ResTopology resTopology = this.resTopologyService.selectByPrimaryKey(resTopologySid);
        Criteria criteria = new Criteria();
        criteria.put("parentTopologySid", resTopology.getParentTopologySid());
        List<ResHost> hostList = this.resHostService.selectByParams(criteria);
        String json = JsonUtil.toJson(new RestResult(hostList));
        return Response.ok(json).build();
    }

    /**
     * 统计出拓扑结构下主机相关信息
     * @param host
     * @return
     */
    @Override
    public Response statisticalTopologyOfHost(ResHost host) {
        Criteria example = new Criteria();
        if (host != null) {
            if (!StringUtil.isNullOrEmpty(host.getResTopologySid())) {
                example.put("resTopologySid", host.getResTopologySid());
            }
            if (!StringUtil.isNullOrEmpty(host.getResPoolSid())) {
                example.put("resPoolSid", host.getResPoolSid());
            }
        }

        ResHost resHost = this.resHostService.statisticalTopologyOfHost(example);
        String json = JsonUtil.toJson(new RestResult(resHost));
        return Response.ok(json).build();

    }

    /**
     * 统计业务下的主机信息
     */
    @Override
    public Response statisticalBizOfHost(
            @PathParam("resBizSid") Long resBizSid) {
        Criteria example = new Criteria();
        example.put("resBizSid", resBizSid);
        ResHost list = this.resHostService.statisticalBizOfHost(example);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.status(Response.Status.OK).entity(json).build();
    }

    /**
     * 统计拓扑结构下的主机资源分配信息
     *
     * @param resTopologySid
     * @return
     */
    @Override
    public Response statisticalTopologyOfHostAllot(String resTopologySid) {
        ResHost list = this.resHostService.statisticalTopologyOfHostAllotInfo(resTopologySid);
        String json = JsonUtil.toJson(new RestResult(list));

        return Response.ok(json).build();
    }

    /**
     * 统计资源分区下的主机信息
     *
     * @param resTopologySid
     * @return
     */
    @Override
    public Response statisticalRzOfHostInfo(String resTopologySid) {
        ResHost list = this.resHostService.statisticalHostPoolOfRz(resTopologySid);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();

    }

    /**
     * 统计资源分区下的主机资源分配信息
     *
     * @param resTopologySid
     * @return
     */
    @Override
    public Response statisticalRzOfHostAllotInfo(String resTopologySid) {
        ResHost list = this.resHostService.statisticalRzOfHostAllotInfo(resTopologySid);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();
    }

    /**
     * 统计资源池下的主机信息
     *
     * @param resPoolSid
     * @return
     */
    @Override
    public Response statisticalComputePoolOfHost(String resPoolSid) {
        ResHost list = this.resHostService.statisticalComputePoolOfHost(resPoolSid);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();
    }
    /**
     * 查询业务视图下的主机--分页
     */
    @Override
    public Response findBizHostByPaging(@Context HttpServletRequest request) {
        // 参数设置
        Criteria param = new Criteria();
        WebUtil.preparePageParams(request, param, "RES_HOST_SID");

        param.setOrderByClause("A.HOST_NAME ASC");
        // 查询数据
        List<ResHost> list = this.resHostService.selectByBizParams(param);
        int total = this.resHostService.countByBizParams(param);

        String json = JsonUtil.toJson(new RestResult(new BaseGridReturn(total, list)));

        return Response.status(Response.Status.OK).entity(json).build();
    }
}
