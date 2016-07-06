package com.h3c.idcloud.core.service.res.api;

import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.pojo.instance.ResObjStoInst;

/**
 * 接口 对象存储实例.
 *
 * @author Chaohong.Mao
 */
public interface ResObjStorageInstService {

    /**
     * 申请对象存储
     *
     * @param resObjStoInst 对象存储实例
     *
     * @return res inst result
     */
    ResInstResult createObjStorage(ResObjStoInst resObjStoInst);

    /**
     * 删除对象存储
     *
     * @param resObjStoInstSid 对象存储实例 sid
     *
     * @return res inst result
     */
    ResInstResult deleteObjStorage(String resObjStoInstSid);
}