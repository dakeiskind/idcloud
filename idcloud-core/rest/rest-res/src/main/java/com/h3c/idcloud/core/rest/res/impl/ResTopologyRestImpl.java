package com.h3c.idcloud.core.rest.res.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.core.pojo.dto.res.ResVc;
import com.h3c.idcloud.core.rest.res.ResTopologyRest;
import com.h3c.idcloud.core.service.res.api.ResTopologyService;
import com.h3c.idcloud.core.service.res.api.ResVcService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by jpg on 2016/3/2.
 */
@Component
public class ResTopologyRestImpl implements ResTopologyRest {

    Logger log = LoggerFactory.getLogger(this.getClass());

    /** 资源拓扑Service */
    @Reference(version = "1.0.0")
    ResTopologyService resTopologyService;

    @Reference(version = "1.0.0")
    ResVcService resVcService;

    /**
     * 查询资源拓扑图列表
     *
     * @param topology@return
     */
    @Override
    public Response findTopology(ResTopology topology) {
        Criteria example = new Criteria();

        if (!StringUtil.isNullOrEmpty(topology.getResTopologyType())) {
            example.put("resTopologyType", topology.getResTopologyType());
        }

        if (!StringUtil.isNullOrEmpty(topology.getTopologyTypes())) {
            example.put("resTopologyTypes", topology.getTopologyTypes());
        }

        if (!StringUtil.isNullOrEmpty(topology.getParentTopologySid())) {
            example.put("parentTopologySid", topology.getParentTopologySid());
        }

        if (!StringUtil.isNullOrEmpty(topology.getResTopologySid())) {
            example.put("parentTopologySid", topology.getResTopologySid());
        }

        if (!StringUtil.isNullOrEmpty(topology.getResTopologyName())) {
            example.put("resTopologyName", topology.getResTopologyName());
        }

        if (!StringUtil.isNullOrEmpty(topology.getResTopologyNameLike())) {
            example.put("resTopologyNameLike", topology.getResTopologyNameLike());
        }

        if (!StringUtil.isNullOrEmpty(topology.getResTabsType())) {
            example.put("resTabsType", topology.getResTabsType());
        }
        // 查询拓扑结构子集
        if (!StringUtil.isNullOrEmpty(topology.getFindChildBySid())) {
            example.put("findChildBySid", topology.getFindChildBySid());
        }

        List<ResTopology> list = this.resTopologyService.selectByParams(example);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();
    }

    /**
     * 根据拓扑SID查询topology信息
     *
     * @param resTopologySid
     * @return
     */
    @Override
    public Response findResourceTopologyById(String resTopologySid) {
        ResTopology topology = this.resTopologyService.selectByPrimaryKey(resTopologySid);
        String json = JsonUtil.toJson(new RestResult(topology));
        return Response.ok(json).build();
    }

    /**
     * 查询出资源环境主机视图Tree列表
     *
     * @return
     */
    @Override
    public Response findResVirtualTopology() {
        Criteria example = new Criteria();
        List<ResTopology> list = this.resTopologyService.selectVirtualTopologyTree(example);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();
    }

    /**
     * 查询出资源池视图Tree列表
     *
     * @return
     */
    @Override
    public Response findResPoolTopology() {
        Criteria example = new Criteria();
        List<ResTopology> list = this.resTopologyService.selectPoolTopologyTree(example);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();
    }

    /**
     * 查询出资源存储视图Tree列表
     *
     * @return
     */
    @Override
    public Response findResStorageVirtualTopology() {
        Criteria example = new Criteria();
        List<ResTopology> list = this.resTopologyService.selectVirtualStorageTopologyTree(example);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.ok(json).build();
    }

    /**
     * 新增资源拓扑结构
     *
     * @param topology
     * @return
     */
    @Override
    public Response createTopology(ResTopology topology) {
        String returnJson;

        WebUtil.prepareInsertParams(topology);
        int result = this.resTopologyService.insertSelective(topology);
        if ("VC".equals(topology.getResTopologyType())) {
            ResVc resvc = new ResVc();
            resvc.setResTopologySid(topology.getResTopologySid());
            this.resVcService.insertSelective(resvc);
        }
        if (1 == result) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS,WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS)));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
        }

        return Response.ok(returnJson).build();
    }

    /**
     * 新增资源分区
     *
     * @param topology
     * @return
     */
    @Override
    public Response CreateTopologyRz(ResTopology topology) {
        String returnJson;
        WebUtil.prepareInsertParams(topology);
        String result = this.resTopologyService.insertRzSelective(topology);
        if ("01".equals(result)) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS,WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS)));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
        }

        return Response.ok(returnJson).build();

    }

    /**
     * 删除资源拓扑分区
     *
     * @param resTopologySid
     * @return
     */
    @Override
    public Response deleteTopologyRz(String resTopologySid) {
        String returnJson;

        int result = this.resTopologyService.deleteRzByPrimaryKey(resTopologySid);
        if (1 == result) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS,WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS)));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_DELETE_FAILURE)));
        }
        return Response.ok(returnJson).build();

    }

    /**
     * 检查拓扑结构名称是否重复
     *
     * @param topology
     * @return
     */
    @Override
    public Response checkTopologyIsNotRepeat(ResTopology topology) {
        Criteria example = new Criteria();
        if (!StringUtil.isNullOrEmpty(topology.getResTopologyName())) {
            example.put("resTopologyName", topology.getResTopologyName());
        }
        if (!StringUtil.isNullOrEmpty(topology.getResTopologyType())) {
            example.put("resTopologyType", topology.getResTopologyType());
        }
        List<ResTopology> list = this.resTopologyService.selectByParams(example);
        String json = JsonUtil.toJson(new RestResult(list));

        return Response.ok(json).build();

    }

}
