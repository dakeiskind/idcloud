package com.h3c.idcloud.core.rest.res.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.res.ResStorage;
import com.h3c.idcloud.core.rest.res.ResStorageRest;
import com.h3c.idcloud.core.service.res.api.ResHostService;
import com.h3c.idcloud.core.service.res.api.ResStorageService;
import com.h3c.idcloud.infrastructure.common.pojo.BaseGridReturn;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
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
public class ResStorageRestImpl implements ResStorageRest {

    Logger log = LoggerFactory.getLogger(this.getClass());

    /** 资源拓扑Service */
    @Reference(version = "1.0.0")
    ResStorageService resStorageService;
    @Reference(version = "1.0.0")
    ResHostService resHostService;

    /**
     * 查询主机列表，并采用服务器分页方式
     *
     * @param request
     * @return
     */
    @Override
    public Response findStorageByPage(@Context HttpServletRequest request) {
        Criteria example = new Criteria();

        WebUtil.preparePageParams(request, example, "A.STORAGE_NAME");

        List<ResStorage> list = this.resStorageService.selectByParams(example);
        int total = this.resStorageService.countByParams(example);

        String json = JsonUtil.toJson(new RestResult(new BaseGridReturn(total, list)));
        return Response.ok(json).build();

    }

    /**
     * 统计拓扑结构下的存储信息
     *
     * @param resTopologySid
     * @return
     */
    @Override
    public Response statisticalStoragePoolOfStorage(String resTopologySid) {

        ResStorage list = this.resStorageService.statisticalTopologyOfStorage(resTopologySid);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();
    }

    /**
     * 统计拓扑结构下的存储分配信息
     *
     * @param resPoolSid
     * @return
     */
    @Override
    public Response statisticalStoragePoolOfStorageAllotInfo(String resPoolSid) {
        ResStorage list = this.resStorageService.statisticalTopologyOfStorageAllotInfo(resPoolSid);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();
    }

    /**
     * 统计拓扑结构下的存储信息
     *
     * @param resTopologySid
     * @return
     */
    @Override
    public Response statisticalStorageVolumeOfStorage(String resTopologySid) {
        ResStorage list = this.resStorageService.statisticalTopologyOfStorageVolume(resTopologySid);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();
    }

    /**
     * 统计资源集群下的存储容量信息
     *
     * @param resTopologySid
     * @return
     */
    @Override
    public Response statisticalClusterOfStorageVolume(String resTopologySid) {
        ResStorage list = this.resStorageService.statisticalClusterOfStorageVolume(resTopologySid);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();
    }

    /**
     * 统计资源集群下的存储分配信息
     *
     * @param resTopologySid
     * @return
     */
    @Override
    public Response statisticalClusterOfStorageAllotInfo(String resTopologySid) {
        ResStorage list = this.resStorageService.statisticalClusterOfStorageAllotInfo(resTopologySid);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();
    }

    /**
     * 统计主机下的存储信息
     *
     * @param resTopologySid
     * @return
     */
    @Override
    public Response statisticalHostOfStorageVolume(String resTopologySid) {
        ResStorage list = this.resStorageService.statisticalHostOfStorageVolume(resTopologySid);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();
    }

    /**
     * 统计主机下的存储分配信息
     *
     * @param resTopologySid
     * @return
     */
    @Override
    public Response statisticalHostOfStorageAllotInfo(String resTopologySid) {
        ResStorage list = this.resStorageService.statisticalHostOfStorageAllotInfo(resTopologySid);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();

    }

    /**
     * 统计资源分区下存储分配信息
     *
     * @param resPoolSid
     * @return
     */
    @Override
    public Response statisticalRzfStorageAllotInfo(String resPoolSid) {
        ResStorage list = this.resStorageService.statisticalRzOfStorageAllotInfo(resPoolSid);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();
    }

    /**
     * 统计资源分区下存储容量使用信息
     *
     * @param resPoolSid
     * @return
     */
    @Override
    public Response statisticalVolumeRzOfStorage(String resPoolSid) {
        ResStorage list = this.resStorageService.statisticalVolumeRzOfStorage(resPoolSid);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();
    }
    /**
     * 统计业务下的存储信息
     */
    @Override
    public Response statisticalBizOfStorage(
            @PathParam("resBizSid") Long resBizSid) {
        Criteria example = new Criteria();
        example.put("resBizSid", resBizSid);
        ResStorage list = this.resStorageService.statisticalBizOfStorage(example);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.status(Response.Status.OK).entity(json).build();
    }

    /**
     * 查询业务员块存储列表---分页
     */
    @Override
    public Response findBizStorageByPage(@Context HttpServletRequest request) {
        Criteria example = new Criteria();
        WebUtil.preparePageParams(request, example, "A.STORAGE_NAME");
        example.setOrderByClause("A.STORAGE_NAME");
        List<ResStorage> list = this.resStorageService.selectByBizParams(example);
        int total = this.resStorageService.countByBizParams(example);
        String json = JsonUtil.toJson(new RestResult(new BaseGridReturn(total, list)));
        return Response.status(Response.Status.OK).entity(json).build();
    }

}
