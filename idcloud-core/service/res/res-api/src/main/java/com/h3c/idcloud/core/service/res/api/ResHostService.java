package com.h3c.idcloud.core.service.res.api;


import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * 接口 主机资源 service.
 *
 * @author Chaohong.Mao
 */
public interface ResHostService {
    int countByBizParams(Criteria example);
    /**
     * 统计数据条数
     *
     * @param example 查询参数
     *
     * @return int 条数
     */
    int countByParams(Criteria example);

    /**
     * 查询主机列表
     *
     * @param example 查询参数
     *
     * @return list 主机列表
     */
    List<ResHost> selectByParams(Criteria example);


    /**
     * 根据sid查询资主机信息
     *
     * @param resHostSid 主机Sid
     *
     * @return 主机信息
     */
    ResHost selectByPrimaryKey(String resHostSid);

    /**
     * 查询vCenter主机监控信息
     *
     * @param ipAddr IP地址
     * @param ve     VE信息
     *
     * @return 主机监控信息
     *
     * @throws Exception the exception
     */
    String getHostMonitorInformation(String ipAddr, ResVe ve) throws Exception;

    /**
     * 同步单一主机
     *
     * @param resHostSid 主机Sid
     *
     * @return 同步结果
     *
     * @throws Exception the exception
     */
    ResInstResult getAllByHost(String resHostSid) throws Exception;

    /***
     * 同步单一主机 供同步集群调用
     *
     * @param resHostSid 主机Sid
     * @param hostName   the host name
     * @param resVe      资源环境信息
     *
     * @return boolean 同步结果
     *
     * @throws Exception the exception
     */
    boolean asyncHost(String resHostSid, String hostName, ResVe resVe) throws Exception;

    /**
     * 主机操作.
     *
     * @param operation 操作
     * @param hostSid   主机Sid
     * @param ve        资源环境信息
     *
     * @return the boolean
     *
     * @throws Exception the exception
     */
    boolean hostOperation(String operation, String hostSid, ResVe ve) throws Exception;

    /***
     * 关闭主机下虚拟机进入维护模式
     *
     * @param resHostSid the 主机资源 sid
     *
     * @return boolean boolean
     *
     * @throws Exception the exception
     */
    boolean stopAllVmOfHost(String resHostSid) throws Exception;


    /**
     * 删除主机
     *
     * @param resHostSid the 主机资源 sid
     *
     * @return the int
     */
    int deleteHostWithObjRes(String resHostSid);

    /**
     * 获取物理主机(包含主机)信息
     *
     * @param resHostSid 物理主机sid
     *
     * @return 物理主机对象 host info
     */
    ResHost getHostInfo(String resHostSid);

    /**
     * 统计出拓扑结构下主机相关信息
     *
     * @param criteria the criteria
     *
     * @return 主机统计 主机资源
     */
    ResHost statisticalTopologyOfHost(Criteria criteria);

    /**
     * 统计业务下的主机信息
     *
     * @param example the example
     *
     * @return the 主机资源
     */
    ResHost statisticalBizOfHost(Criteria example);

    /**
     * 统计拓扑结构下的主机资源分配信息
     *
     * @param resTopologySid 资源拓扑Sid
     *
     * @return 主机资源
     */
    ResHost statisticalTopologyOfHostAllotInfo(String resTopologySid);

    /**
     * 统计资源分区结构下的主机资源信息
     *
     * @param resTopologySid 资源拓扑Sid
     *
     * @return 主机资源
     */
    ResHost statisticalHostPoolOfRz(String resTopologySid);

    /**
     * 统计资源分区结构下的主机资源分配信息
     *
     * @param resTopologySid 资源拓扑Sid
     *
     * @return 主机资源
     */
    ResHost statisticalRzOfHostAllotInfo(String resTopologySid);

    /**
     * 统计资源池下的主机信息
     *
     * @param resPoolSid 资源池Sid
     *
     * @return 主机资源
     */
    ResHost statisticalComputePoolOfHost(String resPoolSid);
    /**
     * 根据条件查询记录集
     */
    List<ResHost> selectByBizParams(Criteria example);
}