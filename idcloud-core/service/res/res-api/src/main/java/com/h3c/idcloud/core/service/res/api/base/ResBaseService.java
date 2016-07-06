package com.h3c.idcloud.core.service.res.api.base;

import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.pojo.common.ServiceBaseInput;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;

/**
 * 接口 Tenant service.
 *
 * @author Chaohong.Mao
 */
public interface ResBaseService {

    /**
     * 通过区域取得Region
     *
     * @param zoneId the zone id
     * @return region from zone
     */
    String getRegionFromZone(String zoneId);

    /**
     * 通过区域取得VE
     *
     * @param zoneId 区域SId
     * @return ve ve from zone
     */
    ResVe getVeFromZone(String zoneId);

    /**
     * 设定MQ消息的基本信息
     *
     * @param resVe 环境VE
     * @param base  MQ消息Base
     */
    void setAdapterBaseInfo(ResVe resVe, Base base);

    /**
     * 设定MQ消息的基本信息
     *
     * @param resVe     环境VE
     * @param baseInput 服务基础类
     * @param base      MQ消息Base
     */
    void setAdapterBaseInfo(ResVe resVe, ServiceBaseInput baseInput, Base base);
}
