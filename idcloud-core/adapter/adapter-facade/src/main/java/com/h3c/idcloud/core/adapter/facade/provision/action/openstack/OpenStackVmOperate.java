package com.h3c.idcloud.core.adapter.facade.provision.action.openstack;

import com.google.common.base.Optional;

import com.h3c.idcloud.core.adapter.facade.common.NovaApiFactory;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.ActionKvm;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmOperate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmResize;

import org.apache.http.HttpStatus;
import org.jclouds.http.HttpResponseException;
import org.jclouds.openstack.nova.v2_0.domain.RebootType;
import org.jclouds.openstack.nova.v2_0.extensions.ServerAdminApi;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;
import org.springframework.stereotype.Service;

/**
 * Created by qct on 2016/2/24.
 *
 * @author qct-office
 */
@Service
public class OpenStackVmOperate extends ActionKvm {
    static final String ACTION_STOP = "stop";
    static final String ACTION_START = "start";
    static final String ACTION_REBOOT = "reboot";
    static final String ACTION_SUSPEND = "suspend";
    static final String ACTION_RESUME = "resume";
    static final String ACTION_RESIZE = "resize";

    @Override
    protected CommonAdapterResult execute(Base base) throws CommonAdapterException, AdapterUnavailableException {
        VmOperate vmOperate = (VmOperate) base;
        ServerApi serverApi = NovaApiFactory.INSTANCE.createNovaApi(base).getServerApi(base.getRegion());
        Optional<ServerAdminApi> serverAdminApiOptional = NovaApiFactory.INSTANCE.createNovaApi(base)
                .getServerAdminApi(base.getRegion());
        CommonAdapterResult result = new CommonAdapterResult();
        try {
            switch (vmOperate.getAction()) {
                case ACTION_STOP:
                    serverApi.stop(vmOperate.getUuid());
                    result.setSuccess(true);
                    break;
                case ACTION_START:
                    serverApi.start(vmOperate.getUuid());
                    result.setSuccess(true);
                    break;
                case ACTION_REBOOT:
                    serverApi.reboot(vmOperate.getUuid(), RebootType.fromValue(vmOperate.getRebootType().toUpperCase()));
                    result.setSuccess(true);
                    break;
                case ACTION_RESIZE:
                    serverApi.resize(vmOperate.getUuid(), buildFlavor(vmOperate.getVmResize()));
                    result.setSuccess(true);
                    break;
                case ACTION_SUSPEND:
                    if (serverAdminApiOptional.isPresent()) {
                        serverAdminApiOptional.get().suspend(vmOperate.getUuid());
                        result.setSuccess(true);
                    } else {
                        result.setSuccess(false);
                    }
                    break;
                case ACTION_RESUME:
                    if (serverAdminApiOptional.isPresent()) {
                        serverAdminApiOptional.get().resume(vmOperate.getUuid());
                        result.setSuccess(true);
                    }else {
                        result.setSuccess(false);
                    }
                    break;
                default:
                    result.setSuccess(false);
            }
        } catch (IllegalStateException e) {
                throw new KvmException("409", e.getMessage());
        }
        return result;
    }

    /**
     * Find flavorId by cpu, mem, disk. <p>If there is no flavor found in openstack cluster, create a flavor specified
     * by the {@link VmResize} parameter.
     *
     * @param vmResize new vm size
     * @return flavorId
     */
    private String buildFlavor(VmResize vmResize) {
        //TODO
        return "1";
    }
}
