package com.h3c.idcloud.core.service.res.api;

import com.h3c.idcloud.core.pojo.dto.res.ResVd;
import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * 接口 Res vd service.
 *
 * @author Chaohong.Mao
 */
public interface ResVdService {

    /**
     * 创建虚拟磁盘
     *
     * @param resVdInst 磁盘对象
     * @return ResInstResult res inst result
     */
    ResInstResult createVd(ResCommonInst resVdInst);

    /**
     * 挂载虚拟磁盘
     *
     * @param resCommonInst the res common inst
     * @return res inst result
     */
    ResInstResult attachVd(ResCommonInst resCommonInst);

    /**
     * 卸载虚拟磁盘
     *
     * @param resCommonInst the res common inst
     * @return res inst result
     */
    ResInstResult detachVd(ResCommonInst resCommonInst);

    /**
     * 删除虚拟磁盘
     *
     * @param resCommonInst the res common inst
     * @return ResInstResult res inst result
     */
    ResInstResult deleteVd(ResCommonInst resCommonInst);

    /**
     * 扩大虚拟磁盘
     *
     * @param resVdSid 虚拟磁盘Sid
     * @param mgtObj   the mgt obj
     * @param size     磁盘大小
     * @return ResInstResult res inst result
     */
    ResInstResult expandVd(String resVdSid, long mgtObj, long size);

    /**
     * Select vd res sum list.
     *
     * @param example the example
     * @return the list
     */
    List<ResVd> selectVdResSum(Criteria example);
}