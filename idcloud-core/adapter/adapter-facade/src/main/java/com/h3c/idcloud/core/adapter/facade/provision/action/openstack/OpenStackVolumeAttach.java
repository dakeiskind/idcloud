package com.h3c.idcloud.core.adapter.facade.provision.action.openstack;

import com.google.common.base.Optional;

import com.h3c.idcloud.core.adapter.facade.common.NovaApiFactory;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.ActionKvm;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskAttach;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskDetach;

import org.jclouds.openstack.nova.v2_0.domain.VolumeAttachment;
import org.jclouds.openstack.nova.v2_0.extensions.VolumeAttachmentApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by qct on 2016/4/21.
 *
 * @author qct
 */
@Service
public class OpenStackVolumeAttach extends ActionKvm {
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(OpenStackNetCreate.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws CommonAdapterException, AdapterUnavailableException {
        Optional<VolumeAttachmentApi> volumeAttachmentApiOptional =
                NovaApiFactory.INSTANCE.createNovaApi(base).getVolumeAttachmentApi(base.getRegion());
        if(volumeAttachmentApiOptional.isPresent()) {
            VolumeAttachment volumeAttachment = volumeAttachmentApiOptional.get().attachVolumeToServerAsDevice(
                    DiskAttach.class.cast(base).getVolumeId(),
                    DiskAttach.class.cast(base).getServerId(),
                    DiskAttach.class.cast(base).getDevice());
            logger.debug("volumeAttachment: {}", volumeAttachment);
            return new CommonAdapterResult(true);
        }else {
            throw new KvmException("500", "VolumeAttachmentApi is absent");
        }
    }
}
