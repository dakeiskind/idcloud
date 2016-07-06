package com.h3c.idcloud.core.service.res.api;

/**
 * 接口 VLan交换机资源.
 *
 * @author Chaohong.Mao
 */
public interface ResPoolVlanVsService {

    /**
     * 保存分布式交换机与VLan资源池的关系
     *
     * @param resPoolSid 资源池SID
     * @param vsSids     交换机SIDs
     *
     * @return the int
     */
    int insertOrUpdateRelationShipDvsWithVlanPool(String resPoolSid, String vsSids);
}