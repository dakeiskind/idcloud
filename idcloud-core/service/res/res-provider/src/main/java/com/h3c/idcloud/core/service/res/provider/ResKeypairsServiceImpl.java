package com.h3c.idcloud.core.service.res.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.google.common.base.Throwables;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairCreate;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairDelete;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairGet;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairListGet;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairCreateResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairGetResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairListGetResult;
import com.h3c.idcloud.core.persist.res.dao.ResKeypairsMapper;
import com.h3c.idcloud.core.persist.res.dao.ResKeypairsVeMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResKeypairs;
import com.h3c.idcloud.core.pojo.dto.res.ResKeypairsVe;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.core.service.res.api.ResKeypairsService;
import com.h3c.idcloud.core.service.res.api.base.ResBaseService;
import com.h3c.idcloud.infrastructure.common.constants.UuidConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.UuidUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import com.h3c.idcloud.infrastructure.common.util.ssh.SshKeys;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

/**
 * Res keypairs service 类.
 *
 * @author Chaohong.Mao
 */
@Service(version = "1.0.0")
@Component
public class ResKeypairsServiceImpl implements ResKeypairsService {

    private static final Logger logger = LoggerFactory.getLogger(ResKeypairsServiceImpl.class);
    @Autowired
    private ResBaseService resBaseService;

    @Autowired
    private ResKeypairsMapper resKeypairsMapper;
    @Autowired
    private ResKeypairsVeMapper resKeypairsVeMapper;

    @Override
    @Transactional
    public String createKeypairs(ResCommonInst resCommonInst) {
        ResKeypairs resKeypairs = new ResKeypairs();
        try {
            // Create keypairs
            Map<String, String> generate = SshKeys.generate(KeyPairGenerator.getInstance("RSA"),
                                                            SecureRandom.getInstance("SHA1PRNG", "SUN")
            );
            resKeypairs.setPrivateKey(generate.get("private"));
            resKeypairs.setPublicKey(generate.get("public"));
            resKeypairs.setFingerprint(SshKeys.fingerprintPublicKey(resKeypairs.getPublicKey()));
            resKeypairs.setMgtObjSid(resCommonInst.getMgtObjSid());
            resKeypairs.setOwnerId(resCommonInst.getUserAccount());
            // 画面参数
            JsonNode resSpecParam = JsonUtil.fromJson(resCommonInst.getResSpecParam());
            resKeypairs.setResKeypairsSid(UuidUtil.getShortUuid(UuidConstants.PrefixCode.KEYPAIR));
            resKeypairs.setKeypairsName(resSpecParam.get("keypairsName").getTextValue());
            resKeypairs.setDescription(resSpecParam.get("description").getTextValue());
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            logger.error("新增SSH公钥/私钥异常: {}", Throwables.getStackTraceAsString(e));
            throw new RpcException(RpcException.BIZ_EXCEPTION, "新增SSH公钥/私钥失败。");
        }
        WebUtil.prepareInsertParams(resKeypairs, resCommonInst.getUserAccount());
        this.resKeypairsMapper.insertSelective(resKeypairs);

        return resKeypairs.getPrivateKey();
    }

    /**
     * 导入秘钥对
     */
    @Override
    @Transactional
    public KeypairCreateResult importKeypairs(ResCommonInst resCommonInst) {
        KeypairCreateResult result = new KeypairCreateResult();
        KeypairCreate keypairCreate = new KeypairCreate();
        // 画面参数
        JsonNode resSpecParam = JsonUtil.fromJson(resCommonInst.getResSpecParam());
        keypairCreate.setName(resSpecParam.get("keypairsName").getTextValue());
        keypairCreate.setPublicKey(resSpecParam.get("publicKey").getTextValue());
        ResVe resVe = this.resBaseService.getVeFromZone(resCommonInst.getZoneId());
        this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, keypairCreate);

        logger.info("输入MQ参数：" + JsonUtil.toJson(keypairCreate));
        try {
            Object msgId = MQHelper.rpc(keypairCreate);
            result = (KeypairCreateResult) msgId;
        } catch (Exception e) {
            logger.error("导入SSH公钥/私钥异常: {}", Throwables.getStackTraceAsString(e));
            throw new RpcException(RpcException.BIZ_EXCEPTION, "导入SSH公钥/私钥失败。");
        }
        return result;
    }

    /**
     * 删除秘钥对
     */
    @Override
    @Transactional
    public KeypairDeleteResult deleteKeypairs(ResCommonInst resCommonInst) {
        KeypairDeleteResult result = null;

        KeypairDelete keypairDelete = new KeypairDelete();
        // 画面参数
        JsonNode resSpecParam = JsonUtil.fromJson(resCommonInst.getResSpecParam());
        ResKeypairs resKeypairs = this.resKeypairsMapper.selectByPrimaryKey(resSpecParam.get("keypairsSid").getTextValue());
        keypairDelete.setKeyName(resKeypairs.getKeypairsName());
        List<ResKeypairsVe> keypairsVes =
                this.resKeypairsVeMapper.selectByParams(new Criteria("resKeypairsSid",
                                                                     resKeypairs.getResKeypairsSid()));
        for (ResKeypairsVe keypairsVe : keypairsVes) {
            ResVe resVe = this.resBaseService.getVeFromZone(keypairsVe.getResTopologySid());
            this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, keypairDelete);

            logger.info("输入MQ参数：" + JsonUtil.toJson(keypairDelete));
            try {
                Object msgId = MQHelper.rpc(keypairDelete);
                // TODO result
                result = (KeypairDeleteResult) msgId;
            } catch (Exception e) {
                logger.error("导入SSH公钥/私钥异常: {}", Throwables.getStackTraceAsString(e));
                throw new RpcException(RpcException.BIZ_EXCEPTION, "导入SSH公钥/私钥异常。");
            }
        }
        return result;
    }

    /**
     * 查看秘钥对详情 TODO 环境
     */
    @Override
    public KeypairGetResult findKeypairsByName(ResCommonInst resCommonInst) {
        KeypairGetResult keypairGetResult = new KeypairGetResult();
        KeypairGet keypairGet = new KeypairGet();
        // 画面参数
        JsonNode resSpecParam = JsonUtil.fromJson(resCommonInst.getResSpecParam());
        keypairGet.setKeyName(resSpecParam.get("keypairsName").getTextValue());
        ResVe resVe = this.resBaseService.getVeFromZone(resCommonInst.getZoneId());
        this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, keypairGet);
        logger.info("输入MQ参数：" + JsonUtil.toJson(keypairGet));
        try {
            Object msgId = MQHelper.rpc(keypairGet);
            keypairGetResult = (KeypairGetResult) msgId;
        } catch (Exception e) {
            logger.error("查看SSH公钥/私钥异常: {}", Throwables.getStackTraceAsString(e));
            throw new RpcException(RpcException.BIZ_EXCEPTION, "查看SSH公钥/私钥异常。");
        }

        return keypairGetResult;
    }


    /**
     * 导出所有秘钥对
     */
    @Override
    public KeypairListGetResult exportAllKeypairs(ResCommonInst resCommonInst) {
        KeypairListGetResult KeypairListGetResult = new KeypairListGetResult();
        KeypairListGet keypairListGet = new KeypairListGet();
        ResVe resVe = this.resBaseService.getVeFromZone(resCommonInst.getZoneId());
        this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, keypairListGet);
        logger.info("输入MQ参数：" + JsonUtil.toJson(keypairListGet));
        try {
            Object msgId = MQHelper.rpc(keypairListGet);
            KeypairListGetResult = (KeypairListGetResult) msgId;
        } catch (Exception e) {
            logger.error("查看SSH公钥/私钥异常: {}", Throwables.getStackTraceAsString(e));
            throw new RpcException(RpcException.BIZ_EXCEPTION, "查看SSH公钥/私钥异常。");
        }
        return KeypairListGetResult;
    }

    @Override
    public List<ResKeypairs> selectByParams(Criteria example) {
        return this.resKeypairsMapper.selectByParams(example);
    }

    @Override
    public ResKeypairs selectByPrimaryKey(String resKeypairsSid) {
        return this.resKeypairsMapper.selectByPrimaryKey(resKeypairsSid);
    }

    @Override
    public int deleteByPrimaryKey(String resKeypairsSid) {
        return this.resKeypairsMapper.deleteByPrimaryKey(resKeypairsSid);
    }

    @Override
    public int updateByPrimaryKeySelective(ResKeypairs record) {
        return this.resKeypairsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insertSelective(ResKeypairs record) {
        return this.resKeypairsMapper.insertSelective(record);
    }
}