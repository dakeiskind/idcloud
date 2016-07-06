package com.h3c.idcloud.core.service.res.api;


import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;

/**
 * 接口 Res floating ip service.
 *
 * @author Chaohong.Mao
 */
public interface ResFloatingIpService {

    /**
     * 申请浮动IP
     *
     * @param resCommonInst the res common inst
     * @return res inst result
     */
    ResInstResult applyFloatingIP(ResCommonInst resCommonInst);

    /**
     * 挂载FloatingIP
     *
     * @param resCommonInst the res common inst
     * @return the res inst result
     */
    ResInstResult attachFloatingIp(ResCommonInst resCommonInst);

    /**
     * 卸载FloatingIP
     *
     * @param resCommonInst the res common inst
     * @return the res inst result
     */
    ResInstResult detachFloatingIp(ResCommonInst resCommonInst);

    /**
     * 释放浮动ip
     *
     * @param resCommonInst the res common inst
     * @return the res inst result
     */
    ResInstResult deleteFloatingIP(ResCommonInst resCommonInst);
}