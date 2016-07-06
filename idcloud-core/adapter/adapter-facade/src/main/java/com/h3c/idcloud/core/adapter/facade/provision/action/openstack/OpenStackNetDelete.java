package com.h3c.idcloud.core.adapter.facade.provision.action.openstack;


import com.google.common.collect.Maps;

import com.h3c.idcloud.core.adapter.facade.common.NeutronApiFactory;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.ActionKvm;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.network.NetDelete;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.function.Predicate;


/**
 * Created by qct on 2016/4/14.
 *
 * @author qct
 */
@Service
public class OpenStackNetDelete extends ActionKvm {
    @Override
    protected CommonAdapterResult execute(Base base) throws CommonAdapterException, AdapterUnavailableException {
        if (NetDelete.class.cast(base).getSubnetIds() != null && NetDelete.class.cast(base).getSubnetIds().size() > 0) {
            boolean isSuccess = NeutronApiFactory.INSTANCE.createNeutronApi(base).getNetworkApi(base.getRegion())
                    .delete(NetDelete.class.cast(base).getNetId());
            return new CommonAdapterResult(isSuccess);
        } else {
            HashMap<String, Boolean> deleteSubnetResult = Maps.newHashMap();
            NetDelete.class.cast(base).getSubnetIds().forEach(subnetId ->
                    deleteSubnetResult.put(subnetId, NeutronApiFactory.INSTANCE.createNeutronApi(base)
                    .getSubnetApi(base.getRegion()).delete(subnetId)));

            //if there's more than one deletion failed, then result = false
            boolean hasFailed = deleteSubnetResult.values().stream().anyMatch(aBoolean -> !aBoolean);
            return new CommonAdapterResult(!hasFailed);
        }
    }
}
