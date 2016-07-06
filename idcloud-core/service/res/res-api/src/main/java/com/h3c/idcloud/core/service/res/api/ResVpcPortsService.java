package com.h3c.idcloud.core.service.res.api;


import com.h3c.idcloud.core.pojo.dto.res.ResVpcPorts;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * 接口 路由 service.
 *
 * @author jpg
 */
public interface ResVpcPortsService {

    /**
     * 查询端口列表
     *
     * @param criteria
     *
     * @return
     */
    List<ResVpcPorts> selectByParams(Criteria criteria);

    /**
     * 更新端口信息
     *
     * @param port
     * @return
     */
    int updateByPrimaryKeySelective(ResVpcPorts port);
}