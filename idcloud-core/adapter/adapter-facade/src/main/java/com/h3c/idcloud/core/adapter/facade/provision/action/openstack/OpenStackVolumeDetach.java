package com.h3c.idcloud.core.adapter.facade.provision.action.openstack;

import com.google.common.base.Optional;

import com.h3c.idcloud.core.adapter.facade.common.CinderApiFactory;
import com.h3c.idcloud.core.adapter.facade.common.NovaApiFactory;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.ActionKvm;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskDelete;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskDetach;

import org.jclouds.openstack.nova.v2_0.extensions.VolumeAttachmentApi;
import org.springframework.stereotype.Service;

/**
 * Created by qct on 2016/4/21.
 *
 * @author qct
 */
@Service
public class OpenStackVolumeDetach extends ActionKvm {
    @Override
    protected CommonAdapterResult execute(Base base) throws CommonAdapterException, AdapterUnavailableException {
        Optional<VolumeAttachmentApi> volumeAttachmentApiOptional =
                NovaApiFactory.INSTANCE.createNovaApi(base).getVolumeAttachmentApi(base.getRegion());
        if(volumeAttachmentApiOptional.isPresent()) {
            return new CommonAdapterResult(volumeAttachmentApiOptional.get().detachVolumeFromServer(
                    DiskDetach.class.cast(base).getVolumeId(),DiskDetach.class.cast(base).getServerId()));
        }else {
            throw new KvmException("500", "VolumeAttachmentApi is absent");
        }
    }
}
