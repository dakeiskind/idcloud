package com.h3c.idcloud.core.service.res.api;

import java.util.Map;

/**
 * 资源统计服务
 * Created by swq on 4/13/2016.
 */
public interface ResStatisticsService {
    /**
     * 统计vm状态数量信息
     * @param ownerId
     * @return {TOTAL=0, POWERED_OFF=0, FAILURE=0, NORMAL=0}
     */
    Map<String,Object> selectStatisticsVmInfo(String ownerId);

    /**
     * 统计floatingip状态数量信息
     * @param mgtObjSid
     * @return {TOTAL=14, DELETING=1, NORMAL=3, CREATING=10}
     */
    Map<String,Object> selectStatisticsFloatingIpInfo(Long mgtObjSid);
}
