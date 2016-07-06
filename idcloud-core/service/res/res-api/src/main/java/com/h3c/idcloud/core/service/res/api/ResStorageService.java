package com.h3c.idcloud.core.service.res.api;

import com.h3c.idcloud.core.adapter.pojo.datastore.result.DataStoreCreateResult;
import com.h3c.idcloud.core.adapter.pojo.datastore.result.DataStoreDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.datastore.result.DataStoreExtendResult;
import com.h3c.idcloud.core.pojo.dto.res.ResStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * 资源存储接口.
 *
 * @author Chaohong.Mao
 */
public interface ResStorageService {

    /**
     * 统计数据条数
     *
     * @param example 参数
     *
     * @return int
     */
    int countByParams(Criteria example);

    /**
     * 查询存储列表
     *
     * @param example 参数
     *
     * @return list
     */
    List<ResStorage> selectByParams(Criteria example);

    /**
     * 删除（关联存储环境）
     *
     * @param resVe      资源环境
     * @param resStorage 存储资源
     *
     * @return 删除结果
     */
    DataStoreDeleteResult deleteStorage(ResVe resVe, ResStorage resStorage);

    /**
     * 扩展存储.
     *
     * @param resVe      资源环境
     * @param resStorage 存储资源
     *
     * @return 扩展结果
     */
    DataStoreExtendResult extendStorage(ResVe resVe, ResStorage resStorage);

    /**
     * 创建集群存储
     *
     * @param resVe      资源环境
     * @param resStorage 存储资源
     *
     * @return 创建结果
     */
    DataStoreCreateResult createHostStorage(ResVe resVe, ResStorage resStorage);

    /**
     * 统计拓扑结构下的存储信息
     *
     * @param resTopologySid 拓扑资源Sid
     *
     * @return 存储
     */
    ResStorage statisticalTopologyOfStorage(String resTopologySid);

    /**
     * 统计拓扑结构下的存储分配信息
     *
     * @param resTopologySid 拓扑资源Sid
     *
     * @return 存储
     */
    ResStorage statisticalTopologyOfStorageAllotInfo(String resTopologySid);

    /**
     * 统计拓扑结构下的存储容量信息
     *
     * @param resTopologySid 拓扑资源Sid
     *
     * @return 存储
     */
    ResStorage statisticalTopologyOfStorageVolume(String resTopologySid);

    /**
     * 统计资源集群下的存储容量信息
     *
     * @param resTopologySid 拓扑资源Sid
     *
     * @return 存储
     */
    ResStorage statisticalClusterOfStorageVolume(String resTopologySid);

    /**
     * 统计资源集群下的存储分配信息
     *
     * @param resTopologySid 拓扑资源Sid
     *
     * @return 存储
     */
    ResStorage statisticalClusterOfStorageAllotInfo(String resTopologySid);

    /**
     * 统计主机下的存储信息
     *
     * @param resTopologySid 拓扑资源Sid
     *
     * @return 存储
     */
    ResStorage statisticalHostOfStorageVolume(String resTopologySid);

    /**
     * 统计主机下的存储分配信息
     *
     * @param resTopologySid 拓扑资源Sid
     *
     * @return 存储
     */
    ResStorage statisticalHostOfStorageAllotInfo(String resTopologySid);

    /**
     * 统计资源分区下存储分配信息
     *
     * @param resTopologySid 拓扑资源Sid
     *
     * @return 存储
     */
    ResStorage statisticalRzOfStorageAllotInfo(String resTopologySid);

    /**
     * 统计资源分区下存储容量使用信息
     *
     * @param resTopologySid 拓扑资源Sid
     *
     * @return 存储
     */
    ResStorage statisticalVolumeRzOfStorage(String resTopologySid);
    /**
     * 统计业务下的存储信息
     */
    ResStorage statisticalBizOfStorage(Criteria example);

    /**
     * 根据条件查询记录集
     */
    List<ResStorage> selectByBizParams(Criteria example);

    /**
     * 根据条件查询记录总数
     */
    int countByBizParams(Criteria example);
}