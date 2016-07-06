package com.h3c.idcloud.core.service.res.api;


import com.h3c.idcloud.core.pojo.dto.res.ResImage;
import com.h3c.idcloud.core.pojo.dto.res.ResImageSoftWare;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * 接口 Res image service.
 *
 * @author Chaohong.Mao
 */
public interface ResImageService {

    /**
     * 查询镜像
     *
     * @param example
     * @return
     */
    List<ResImage> selectByParams(Criteria example);

    /**
     * 根据虚拟化环境扫描vcenter 获取下面的镜像模板
     *
     * @param resve the resve
     *
     * @return all by image
     */
    ResInstResult getAllByImage(ResVe resve);

    /**
     * 根据拓扑类型，查询镜像
     *
     * @param topoloyType 拓扑类型
     * @param topoloyId   拓扑Id数组
     * @param imageType   the image type
     *
     * @return res images by topology
     */
    List<ResImage> getResImagesByTopology(String topoloyType, String[] topoloyId, String imageType);

    /**
     * 获取资源环境下，最优模板
     * 传资源环境，和所选操作系统
     * {"resVeSid": "36fc99b4-3e82-11e4-a27c-0050568b6446",
     * "osType": "administrator",
     * "osVersion": "!QAZ2wsx",
     * "softwareType": "S01,S02",
     * "softwareVersion": "S010101,S020101"}
     *
     * @param resImageSoftWare the res image soft ware
     *
     * @return the image by soft ware
     */
    //String getImageBySoftWare(String imageJsonData);
    ResImageSoftWare getImageBySoftWare(ResImageSoftWare resImageSoftWare);

    /**
     * test测试接口
     *
     * @param resve the resve
     *
     * @return boolean
     *
     * @throws Exception the exception
     */
    boolean testImage(ResVe resve) throws Exception;




}