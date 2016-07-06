package com.h3c.idcloud.core.service.res.provider;


import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.pojo.network.SgRuleCreate;
import com.h3c.idcloud.core.adapter.pojo.network.SgRuleDelete;
import com.h3c.idcloud.core.adapter.pojo.network.SgRuleListQuery;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgRuleCreateResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgRuleDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgRuleListQueryResult;
import com.h3c.idcloud.core.persist.res.dao.ResVeMapper;
import com.h3c.idcloud.core.persist.system.dao.MgtObjMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.service.res.api.ResSecurityGroupRuleService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResSecurityGroupRuleServiceImpl implements ResSecurityGroupRuleService {
    private static final Logger logger = LoggerFactory.getLogger(ResSecurityGroupRuleServiceImpl.class);

    @Autowired
    private MgtObjMapper mgtObjMapper;

    @Autowired
    private ResVeMapper resVeMapper;

    @Override
    public SgRuleListQueryResult selectByParams(String id, Long mgtObjSid) {
        MgtObj mgtObj = this.mgtObjMapper.selectByPrimaryKey(mgtObjSid);
        SgRuleListQueryResult result = new SgRuleListQueryResult();
        try {
            String resVeSid = PropertiesUtil.getProperty("kvm.resve");
            ResVe resVe = this.resVeMapper.selectByPrimaryKey(resVeSid);
            SgRuleListQuery listQuery = new SgRuleListQuery();
            listQuery.setProviderType(resVe.getVirtualPlatformType());
            listQuery.setAuthUrl(resVe.getManagementUrl());
            listQuery.setAuthUser(resVe.getManagementAccount());
            listQuery.setAuthPass(resVe.getManagementPassword());
            listQuery.setVirtEnvType(resVe.getVirtualPlatformType());
            listQuery.setVirtEnvUuid(resVe.getResTopologySid());
            listQuery.setTenantId(mgtObj.getUuid());
            listQuery.setTenantName(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));
            listQuery.setSgId(id);

            logger.info("输入MQ参数：" + JsonUtil.toJson(listQuery));
            Object rules = MQHelper.rpc(listQuery);
            result = (SgRuleListQueryResult) rules;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updateByPrimaryKeySelective(Criteria example) {
        return 0;
    }

    @Override
    public SgRuleCreateResult insertSelective(SgRuleCreate ruleCreate, MgtObj mgtObj) {
        SgRuleCreateResult result = new SgRuleCreateResult();
        try {
            String resVeSid = PropertiesUtil.getProperty("kvm.resve");
            ResVe resVe = this.resVeMapper.selectByPrimaryKey(resVeSid);
            ruleCreate.setProviderType(resVe.getVirtualPlatformType());
            ruleCreate.setAuthUrl(resVe.getManagementUrl());
            ruleCreate.setAuthUser(resVe.getManagementAccount());
            ruleCreate.setAuthPass(resVe.getManagementPassword());
            ruleCreate.setVirtEnvType(resVe.getVirtualPlatformType());
            ruleCreate.setVirtEnvUuid(resVe.getResTopologySid());
            ruleCreate.setTenantId(mgtObj.getUuid());
            ruleCreate.setTenantName(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));
            logger.info("输入MQ参数：" + JsonUtil.toJson(ruleCreate));
            Object rules = MQHelper.rpc(ruleCreate);
            result = (SgRuleCreateResult) rules;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public SgRuleDeleteResult deleteByPrimaryKey(SgRuleDelete ruleDelete, MgtObj mgtObj) {
        SgRuleDeleteResult result = new SgRuleDeleteResult();
        try {
            String resVeSid = PropertiesUtil.getProperty("kvm.resve");
            ResVe resVe = this.resVeMapper.selectByPrimaryKey(resVeSid);
            ruleDelete.setProviderType(resVe.getVirtualPlatformType());
            ruleDelete.setAuthUrl(resVe.getManagementUrl());
            ruleDelete.setAuthUser(resVe.getManagementAccount());
            ruleDelete.setAuthPass(resVe.getManagementPassword());
            ruleDelete.setVirtEnvType(resVe.getVirtualPlatformType());
            ruleDelete.setVirtEnvUuid(resVe.getResTopologySid());
            ruleDelete.setTenantId(mgtObj.getUuid());
            ruleDelete.setTenantName(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));
            logger.info("输入MQ参数：" + JsonUtil.toJson(ruleDelete));
            Object rules = MQHelper.rpc(ruleDelete);
            result = (SgRuleDeleteResult) rules;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}