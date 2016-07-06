package com.h3c.idcloud.core.adapter.facade.provision.action.openstack;

import com.h3c.idcloud.core.adapter.facade.common.CinderApiFactory;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.ActionKvm;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskCreate;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskDelete;

import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.cinder.v1.options.CreateVolumeOptions;
import org.springframework.stereotype.Service;

/**
 * Created by qct on 2016/4/21.
 *
 * @author qct
 */
@Service
public class OpenStackVolumeDelete extends ActionKvm {
    @Override
    protected CommonAdapterResult execute(Base base) throws CommonAdapterException, AdapterUnavailableException {
        return new CommonAdapterResult(CinderApiFactory.INSTANCE.createCinderApi(base).getVolumeApi(base.getRegion())
                .delete(DiskDelete.class.cast(base).getId()));
    }
}
