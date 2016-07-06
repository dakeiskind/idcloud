package com.h3c.idcloud.core.service.res.api;

import com.h3c.idcloud.core.pojo.dto.res.ResVpc;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * 接口 Res ve service.
 */
public interface ResVpcService {
    /**
     * 查询vpc列表
     *
     * @param criteria
     *
     * @return
     */
    List<ResVpc> selectByParams(Criteria criteria);

    /**
     * 更新vpc信息
     *
     * @param vpc
     * @return
     */
    int updateByPrimaryKeySelective(ResVpc vpc);


}