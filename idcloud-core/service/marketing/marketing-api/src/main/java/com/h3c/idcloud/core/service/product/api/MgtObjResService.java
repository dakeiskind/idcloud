package com.h3c.idcloud.core.service.product.api;

import com.h3c.idcloud.core.pojo.dto.product.MgtObjRes;
import com.h3c.idcloud.core.pojo.dto.product.MgtObjResKey;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface MgtObjResService {
    int countByParams(Criteria example);

    MgtObjRes selectByPrimaryKey(MgtObjResKey key);

    List<MgtObjRes> selectByParams(Criteria example);

    List<MgtObjRes> selectBizReses(Criteria example);

    int deleteByPrimaryKey(MgtObjResKey key);

    int updateByPrimaryKeySelective(MgtObjRes record);

    int updateByPrimaryKey(MgtObjRes record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(MgtObjRes record, Criteria example);

    int updateByParams(MgtObjRes record, Criteria example);

    int insert(MgtObjRes record);

    int insertSelective(MgtObjRes record);

    List<MgtObjRes> selectByParams2(Criteria example);

    /**
     * 判断租户关联了哪些资源以及资源环境
     * @param example
     * @return
     */
    List<MgtObjRes> selectMgtRes(Criteria example);

    /**
     * 查询需要回收的项目资源
     * @param example
     */
    void selectMgtObjResForRecovery(Criteria example);

}