package com.h3c.idcloud.core.service.res.provider;


import com.h3c.idcloud.core.adapter.core.MQException;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.pojo.vm.VmUserAdd;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmAddUserResult;
import com.h3c.idcloud.core.persist.res.dao.ResHostMapper;
import com.h3c.idcloud.core.persist.res.dao.ResOsUserMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVeMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmNetworkMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.core.pojo.dto.res.ResOsUser;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.res.ResVmNetwork;
import com.h3c.idcloud.core.service.res.api.ResOsUserService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class ResOsUserServiceImpl implements ResOsUserService {
    private static final Logger logger = LoggerFactory.getLogger(ResOsUserServiceImpl.class);
    @Autowired
    private ResOsUserMapper resOsUserMapper;
    @Autowired
    private ResVmMapper resVmMapper;
    @Autowired
    private ResVmNetworkMapper resVmNetworkMapper;
    @Autowired
    private ResVeMapper resVeMapper;
    @Autowired
    private ResHostMapper resHostMapper;

    public int insertSelective(ResOsUser record) {
        VmUserAdd vmUserAdd = new VmUserAdd();
        vmUserAdd.setProviderType(WebConstants.VirtualPlatformType.HMC);
        //得到所属的虚机或物理机的ip以及管理帐号
        if (record.getResType().contains("VM")) {
            ResVm resVm = resVmMapper.selectByPrimaryKey(record.getResSid());
            List<ResVe> resVes = resVeMapper.selectVeByResHost(resVm.getAllocateResHostSid());
            if (!CollectionUtils.isEmpty(resVes)) {
                vmUserAdd.setVirtEnvType(resVes.get(0).getVirtualPlatformType());
                vmUserAdd.setVirtEnvUuid(resVes.get(0).getResTopologySid());
            } else {
                return 0;
            }
            List<ResVmNetwork> netWorks = resVmNetworkMapper.selectByVmSid(record.getResSid());
            vmUserAdd.setServerIP(netWorks.get(0).getIpAddress());
            vmUserAdd.setServerUser(resVm.getManagementAccount());
            vmUserAdd.setServerPwd(resVm.getManagementPassword());
            vmUserAdd.setOsType(resVm.getOsType());
        } else if (record.getResType().contains("HOST")) {
            ResHost resHost = resHostMapper.selectByPrimaryKey(record.getResSid());
            List<ResVe> resVes = resVeMapper.selectVeByResHost(record.getResSid());
            if (!CollectionUtils.isEmpty(resVes)) {
                vmUserAdd.setVirtEnvType(resVes.get(0).getVirtualPlatformType());
                vmUserAdd.setVirtEnvUuid(resVes.get(0).getResTopologySid());
            } else {
                return 0;
            }
            vmUserAdd.setServerIP(resHost.getHostIp());
            vmUserAdd.setServerUser(resHost.getManagementUser());
            vmUserAdd.setServerPwd(resHost.getManagementPwd());
            vmUserAdd.setOsType(resHost.getHostEquipType());
        }
        vmUserAdd.setNonRootUser(record.getUserName());
        vmUserAdd.setNonRootPwd(record.getPassword());
        logger.info(" param=" + JsonUtil.toJson(vmUserAdd));
        Object rpc = new Object();
        try {
            rpc = MQHelper.rpc(vmUserAdd);
            VmAddUserResult userResult = (VmAddUserResult) rpc;
            if (userResult.isSuccess()) {
                return this.resOsUserMapper.insertSelective(record);
            } else {
                return 0;
            }
        } catch (MQException e) {
            e.printStackTrace();
            return 0;
        }
    }
}