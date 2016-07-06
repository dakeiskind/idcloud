package com.h3c.idcloud.core.service.res.api;


import com.h3c.idcloud.core.pojo.dto.res.ResVpcRouter;
import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * 接口 路由 service.
 *
 * @author jpg
 */
public interface ResVpcRouterService {

    /**
     * 查询路由器列表
     *
     * @param criteria the criteria
     * @return list
     */
    List<ResVpcRouter> selectByParams(Criteria criteria);

    /**
     * 根据路由器id，查询路由器信息
     *
     * @param resRouterSid
     * @return list
     */
    ResVpcRouter selectByPrimaryKey(String resRouterSid);

    /**
     * 更新路由器信息
     *
     * @param router the router
     * @return int
     */
    int updateByPrimaryKeySelective(ResVpcRouter router);


    /**
     * 创建路由器.
     *
     * @param resCommonInst the res common inst
     * @return the int
     */
    ResVpcRouter createVpcRouter(ResCommonInst resCommonInst);

    /**
     * 删除路由器.
     *
     * @param resCommonInst the res common inst
     * @return the int
     */
    int removeVpcRouter(ResCommonInst resCommonInst);

    /**
     * 网络关联路由器.
     *
     * @param resCommonInst the res common inst
     * @return the int
     */
    int attachSubNetWithRouter(ResCommonInst resCommonInst);

    /**
     * 网络解绑路由器.
     *
     * @param resCommonInst the res common inst
     * @return the int
     */
    int dettachSubNetWithRouter(ResCommonInst resCommonInst);

    /**
     * 路由器关联外部网络.
     *
     * @param resCommonInst the res common inst
     * @return the int
     */
    int attachExtNetWithRouter(ResCommonInst resCommonInst);
}