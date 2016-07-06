package com.h3c.idcloud.core.service.res.api;

import com.h3c.idcloud.core.adapter.core.MQException;
import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.core.pojo.dto.res.ResNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.core.pojo.dto.res.ResVc;
import com.h3c.idcloud.core.pojo.dto.res.ResVd;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.res.ResVs;
import com.h3c.idcloud.core.pojo.dto.res.ResVsPortGroup;
import com.h3c.idcloud.core.pojo.instance.LicenseResult;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.io.IOException;
import java.util.List;

/**
 * 接口 Res ve service.
 */
public interface ResVeService {
    /**
     * 查询Ve列表
     *
     * @param criteria
     *
     * @return
     */
    List<ResVe> selectByParams(Criteria criteria);

    /**
     * 根据Sid查询资源环境信息
     *
     * @param resTopologySid
     *
     * @return
     */
    ResVe selectByPrimaryKey(String resTopologySid);

    /**
     * Delete by primary key int.
     *
     * @param resTopologySid the res topology sid
     *
     * @return the int
     */
    int deleteByPrimaryKey(String resTopologySid);

    /**
     * Update by primary key selective int.
     *
     * @param record the record
     *
     * @return the int
     */
    int updateByPrimaryKeySelective(ResVe record);


    /**
     * Insert selective int.
     *
     * @param record the record
     *
     * @return the int
     */
    int insertSelective(ResVe record);

    /**
     * 根据虚拟化环境扫描vcenter 获取下面的集群、主机、以及虚拟机
     *
     * @param resve the resve
     *
     * @return boolean
     *
     * @throws Exception the exception
     */
    boolean findAllByVe(ResVe resve) throws Exception;

    /**
     * 根据虚拟化环境扫描虚拟交换机，端口组，更新交换机与主机关系
     *
     * @param ResVe the res ve
     *
     * @return net work by ve
     *
     * @throws Exception the exception
     */
    public boolean getNetWorkByVe(ResVe ResVe) throws Exception;

    /**
     * 测试虚拟化环境
     *
     * @param resve the resve
     *
     * @return boolean
     *
     * @throws Exception the exception
     */
    boolean testVeConnection(ResVe resve) throws Exception;

    /**
     * 更新主机与交换机关系
     *
     * @param resVsList the res vs list
     * @param resVe     the res ve
     *
     * @throws Exception the exception
     */
    void updateHostResVsRelationship(List<ResVs> resVsList, ResVe resVe) throws Exception;

    /**
     * 更新分布式端口组
     *
     * @param disPortGroupList the dis port group list
     * @param resVs            the res vs
     */
    void insertDisPortGroup(List<ResVsPortGroup> disPortGroupList, ResVs resVs);

    /**
     * 调用南向接口获取网络MAP
     *
     * @param resVe the res ve
     *
     * @return all net
     *
     * @throws Exception the exception
     */
    public ResNetwork getAllNet(ResVe resVe) throws Exception;


    /**
     * 调用接口查询集群下主机
     *
     * @param resVc the res vc
     * @param resVe the res ve
     *
     * @return host by vc
     *
     * @throws IOException the io exception
     */
    public List<ResHost> getHostByVc(ResVc resVc, ResVe resVe) throws Exception;

    /**
     * 调用接口根据主机查询虚拟机
     *
     * @param resHost   the res host
     * @param resVe     the res ve
     * @param resVmList the res vm list
     *
     * @return vms by host
     *
     * @throws Exception the exception
     */
    public List<ResVm> getVmsByHost(ResHost resHost, ResVe resVe, List<ResVm> resVmList) throws Exception;

    /**
     * 根据虚拟机查询磁盘，并插入数据库
     *
     * @param resVm     the res vm
     * @param resVe     the res ve
     * @param resVdList the res vd list
     *
     * @return vds by vm
     *
     * @throws IOException the io exception
     */
    public List<ResVd> getVdsByVm(ResVm resVm, ResVe resVe, List<ResVd> resVdList) throws IOException;

    /**
     * 获取虚拟化环境下所有主机
     *
     * @param resve the resve
     *
     * @return all host by ve
     *
     * @throws Exception the exception
     */
    public List<ResHost> getAllHostByVe(ResVe resve) throws Exception;

    /**
     * 调用接口查询虚拟化环境下的集群，并插入数据库
     *
     * @param resve the resve
     *
     * @return vc by ve
     *
     * @throws IOException the io exception
     * @throws MQException the mq exception
     */
    public List<ResTopology> getVcByVe(ResVe resve) throws IOException, MQException;

    /**
     * 检查数据库虚拟机，与扫描过来的虚拟机是否一样
     *
     * @param resVmDb     the res vm db
     * @param resVmReturn the res vm return
     *
     * @return boolean
     */
    public boolean isResVmSame(ResVm resVmDb, ResVm resVmReturn);

    /**
     * 检查License
     *
     * @return license result
     */
    public LicenseResult checkLincense();

    /**
     * 得到租户所对应的虚拟化环境
     *
     * @param mgtObjSid   the mgt obj sid
     * @param virtualType the virtual type
     *
     * @return list
     */
    List<ResVe> selectMgtObjVe(Long mgtObjSid, String virtualType);

    /**
     * 调用MQ接口注册虚拟化环境队列
     *
     * @param resVe the res ve
     *
     * @return boolean
     */
    boolean registerMqEnv(ResVe resVe);

    /**
     * 调用MQ接口删除虚拟化环境队列
     *
     * @param resVe the res ve
     *
     * @return boolean
     */
    boolean unRegisterrMqEnv(ResVe resVe);

    /**
     * 扫描power环境
     *
     * @param resve the resve
     */
    void findAllByPowerVe(ResVe resve);

}