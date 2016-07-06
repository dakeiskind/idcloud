package com.h3c.idcloud.core.service.res.api;


import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairCreateResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairGetResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairListGetResult;
import com.h3c.idcloud.core.pojo.dto.res.ResKeypairs;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * 接口 Res keypairs service.
 *
 * @author Chaohong.Mao
 */
public interface ResKeypairsService {

    /**
     * 创建秘钥对
     *
     * @param resCommonInst the res common inst
     * @return the keypair create result
     */
    String createKeypairs(ResCommonInst resCommonInst);

    /**
     * 导入秘钥对
     *
     * @param keypairs   the keypairs
     * @param publicKeys the public keys
     * @param mgtObj     the mgt obj
     * @return the keypair create result
     */
    KeypairCreateResult importKeypairs(ResCommonInst resCommonInst);

    /**
     * 删除秘钥对
     *
     * @param keypairs the keypairs
     * @param mgtObj   the mgt obj
     * @return the keypair delete result
     */
    KeypairDeleteResult deleteKeypairs(ResCommonInst resCommonInst);

    /**
     * 查看秘钥对详情
     *
     * @param keypairs the keypairs
     * @param mgtObj   the mgt obj
     * @return the keypair get result
     */
    KeypairGetResult findKeypairsByName(ResCommonInst resCommonInst);

    /**
     * 导出所有秘钥对
     *
     * @param mgtObj the mgt obj
     * @return the keypair list get result
     */
    KeypairListGetResult exportAllKeypairs(ResCommonInst resCommonInst);

    /**
     * Select by params list.
     *
     * @param example the example
     * @return the list
     */
    List<ResKeypairs> selectByParams(Criteria example);

    /**
     * Select by primary key res keypairs.
     *
     * @param resKeypairsSid the res keypairs sid
     * @return the res keypairs
     */
    ResKeypairs selectByPrimaryKey(String resKeypairsSid);

    /**
     * Delete by primary key int.
     *
     * @param resKeypairsSid the res keypairs sid
     * @return the int
     */
    int deleteByPrimaryKey(String resKeypairsSid);

    /**
     * Modify by record int.
     *
     * @param record the res keypairs record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResKeypairs record);

    /**
     * insert a keypair
     *
     * @param record the res kepairs record
     * @return the int
     */
    int insertSelective(ResKeypairs record);
}