package com.h3c.idcloud.core.service.res.api;

import com.h3c.idcloud.core.pojo.dto.res.ResVlan;

/**
 * 接口 Res vlan service.
 */
public interface ResVlanService {

    /**
     * Insert selective int.
     *
     * @param record the record
     *
     * @return the int
     */
    int insertSelective(ResVlan record);

    /**
     * 统计网络资源池下的vlan信息
     *
     * @param resTopologySid the res topology sid
     *
     * @return the res vlan
     */
    ResVlan statisticsVlanInPn(String resTopologySid);
}