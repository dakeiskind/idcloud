package com.h3c.idcloud.core.service.res.api;


import com.h3c.idcloud.core.pojo.dto.res.ResCdnMgtObjFlow;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;

/**
 * 接口 Res cdn inst service.
 *
 * @author Chaohong.Mao
 */
public interface ResCdnInstService {

    /**
     * 启用CDN
     *
     * @param resCdnSid the res cdn sid
     * @param mgtObjSid the mgt obj sid
     *
     * @return res inst result
     */
    ResInstResult startCDN(String resCdnSid, long mgtObjSid);

    /**
     * 停用CDN
     *
     * @param resCdnInstSid the res cdn inst sid
     *
     * @return res inst result
     */
    ResInstResult stopCDN(String resCdnInstSid);

    /**
     * 获取租户CDN流量信息
     *
     * @param resCdnInstSid the res cdn inst sid
     * @param startTime     the start time
     * @param stopTime      the stop time
     *
     * @return cdn flow
     */
    ResCdnMgtObjFlow getCDNFlow(String resCdnInstSid, String startTime, String stopTime);
}