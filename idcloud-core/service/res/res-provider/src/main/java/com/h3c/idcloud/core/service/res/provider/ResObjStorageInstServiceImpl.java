package com.h3c.idcloud.core.service.res.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.res.dao.ResObjStorageInstMapper;
import com.h3c.idcloud.core.persist.res.dao.ResObjStorageMapper;
import com.h3c.idcloud.core.persist.system.dao.MgtObjMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResObjStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResObjStorageInst;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.pojo.instance.ResObjStoInst;
import com.h3c.idcloud.core.service.res.api.ResObjStorageInstService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service(version = "1.0.0")
@Component
public class ResObjStorageInstServiceImpl implements ResObjStorageInstService {
    private static final Logger logger = LoggerFactory.getLogger(ResObjStorageInstServiceImpl.class);
    @Autowired
    private ResObjStorageInstMapper resObjStorageInstMapper;
    @Autowired
    private ResObjStorageMapper resObjStorageMapper;
    @Autowired
    private MgtObjMapper mgtObjMapper;

    /**
     * 申请对象存储
     */
    @Override
    public ResInstResult createObjStorage(ResObjStoInst resObjStoInst) {
        ResInstResult result = null;
        try {
            ResObjStorage resObjStorage = this.resObjStorageMapper.selectByPrimaryKey(PropertiesUtil.getProperty("kvm.obj.storage"));
            MgtObj mgtObj = this.mgtObjMapper.selectByPrimaryKey(resObjStoInst.getMgtObjSid());
            ResObjStorageInst resObjStorageInst = new ResObjStorageInst();
            resObjStorageInst.setResOsSid(resObjStorage.getResOsSid());
            resObjStorageInst.setVisitAddress(resObjStorage.getOsVisitAddress());
            resObjStorageInst.setVisitKey(StringUtil.nullToEmpty(resObjStoInst.getMgtObjSid()));
            resObjStorageInst.setVisitUser(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));
            resObjStorageInst.setStatus(WebConstants.ResObjStorageInstStatus.NORMAL);
            WebUtil.prepareInsertParams(resObjStorageInst);
            int result1 = this.resObjStorageInstMapper.insertSelective(resObjStorageInst);
            if (result1 > 0) {
                logger.info("创建对象存储成功");
                result = new ResInstResult(ResInstResult.SUCCESS, "", resObjStorageInst);
            } else {
                logger.error("创建对象存储失败");
                result = new ResInstResult(ResInstResult.FAILURE, "", resObjStorageInst);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("创建对象存储失败");
            result = new ResInstResult(ResInstResult.FAILURE, "");
        }
        return result;
    }

    /**
     * 删除对象存储
     */
    @Override
    public ResInstResult deleteObjStorage(String resObjStorageInstSid) {
        ResInstResult result = null;
        int result1 = this.resObjStorageInstMapper.deleteByPrimaryKey(resObjStorageInstSid);
        if (result1 > 0) {
            logger.info("删除对象存储成功");
            result = new ResInstResult(ResInstResult.SUCCESS, "", resObjStorageInstSid);
        } else {
            logger.error("创建对象存储失败");
            result = new ResInstResult(ResInstResult.FAILURE, "", resObjStorageInstSid);
        }
        return result;
    }
}