package com.h3c.idcloud.core.adapter.facade.common;

/**
 * Created by qct on 2016/3/15.
 *
 * @author qct
 */
public interface Constants {
    interface Provider {
        String OPEN_STACK = "OpenStack";
        String VMWARE = "Vmware";
        String HYPERV = "HyperV";
        String HMC = "HMC";
    }

    interface AdapterUnvailableException {
        String CODE = "9999";
        String MSG = "adapter unavailable";
    }
}
