package com.h3c.idcloud.core.service.res.api;

import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.core.pojo.dto.res.ResVc;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * 接口 Res vc service.
 */
public interface ResVcService {

    /**
     * 查询Vc列表
     *
     * @param example
     *
     * @return
     */
    List<ResVc> selectByParams(Criteria example);

    /**
     * 根据sid查询资源集群信息
     *
     * @param resVcSid
     *
     * @return
     */
    ResVc selectByPrimaryKey(String resVcSid);

    /**
     * 同步单一集群
     *
     * @param resVcSid the res vc sid
     *
     * @return all by vc
     */
    boolean getAllByVc(String resVcSid);

    /**
     * 同步单一集群
     *
     * @param resVcSid  the res vc sid
     * @param resVcInfo the res vc info
     * @param resVe     the res ve
     *
     * @return the boolean
     */
    boolean getAllByVc(String resVcSid, ResVc resVcInfo, ResVe resVe);

    /**
     *  插入数据
     *
     * @param record
     * @return
     */
    int insertSelective(ResVc record);
}