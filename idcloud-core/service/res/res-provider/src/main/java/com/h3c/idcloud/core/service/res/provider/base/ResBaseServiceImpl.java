package com.h3c.idcloud.core.service.res.provider.base;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.persist.res.dao.ResTopologyConfigMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVeMapper;
import com.h3c.idcloud.core.pojo.common.ServiceBaseInput;
import com.h3c.idcloud.core.pojo.dto.res.ResTopologyConfig;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.service.res.api.base.ResBaseService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * com.h3c.idcloud.core.service.res.provider.base
 *
 * @author Chaohong.Mao
 */
@Component
@Service(version = "1.0.0")
public class ResBaseServiceImpl implements ResBaseService {

    @Autowired
    private ResTopologyConfigMapper resTopologyConfigMapper;
    @Autowired
    private ResVeMapper resVeMapper;

    @Override
    public String getRegionFromZone(String zoneId) {
        String region = null;
        Criteria criteria = new Criteria();
        criteria.put("resTopologySid", zoneId);
        criteria.put("configKey", WebConstants.ResConfig.REGION_NAME);
        List<ResTopologyConfig> configList = this.resTopologyConfigMapper.selectByParams(criteria);
        if (configList != null && configList.size() > 0) {
            region = configList.get(0).getConfigValue();
        }

        return region;
    }

    @Override
    public ResVe getVeFromZone(String zoneId) {
        ResVe resVe = null;

        Criteria criteria = new Criteria();
        criteria.put("resTopologySid", zoneId);
        criteria.put("configKey", WebConstants.ResConfig.RES_ENV_ID);
        List<ResTopologyConfig> configList = this.resTopologyConfigMapper.selectByParams(criteria);

        if (configList != null && configList.size() > 0) {
            String resEnvId = configList.get(0).getConfigValue();
            // 查找VE的设定属性
            resVe = this.resVeMapper.selectByPrimaryKey(resEnvId);
        }

        return resVe;
    }

    @Override
    public void setAdapterBaseInfo(ResVe resVe, Base base) {
        setAdapterBaseInfo(resVe, null, base);
    }

    @Override
    public void setAdapterBaseInfo(ResVe resVe, ServiceBaseInput baseInput, Base base) {
        // 管理基本信息
        if (resVe == null) {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "无法取得环境信息。");
        }
        base.setAuthUrl(resVe.getAdminManagementUrl());
        base.setAuthUser(resVe.getManagementAccount());
        base.setAuthTenant(resVe.getManagementTenant());
        base.setAuthPass(resVe.getManagementPassword());

        base.setProviderUrl(resVe.getManagementUrl());
        base.setProviderType(resVe.getVirtualPlatformType());

        base.setVirtEnvType(resVe.getVirtualPlatformType());
        base.setVirtEnvUuid(resVe.getResTopologySid());
        // 租户基本信息
        if (baseInput != null) {
            base.setTenantUserName(baseInput.getUserAccount());
            // 使用平台的tenant id作为底层的tenant name
            base.setTenantName(baseInput.getMgtObjSid().toString());
//            base.setTenantUserPass(resBaseInst.getUserPass());
            // TODO
            base.setTenantUserPass("123456");
            base.setRegion(this.getRegionFromZone(baseInput.getZoneId()));
        }
    }
}
