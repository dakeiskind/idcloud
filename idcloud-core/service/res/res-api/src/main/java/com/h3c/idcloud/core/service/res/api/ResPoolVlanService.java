package com.h3c.idcloud.core.service.res.api;

/**
 * 接口 Vlan资源池.
 *
 * @author Chaohong.Mao
 */
public interface ResPoolVlanService {

    /**
     * 通过主键删除.
     *
     * @param resPoolSid 资源池SID
     *
     * @return 影响条数
     */
    int deleteByPrimaryKey(String resPoolSid);
}