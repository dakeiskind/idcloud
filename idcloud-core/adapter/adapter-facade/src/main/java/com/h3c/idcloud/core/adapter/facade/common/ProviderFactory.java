package com.h3c.idcloud.core.adapter.facade.common;

import com.h3c.idcloud.core.adapter.facade.provision.Monitor;
import com.h3c.idcloud.core.adapter.facade.provision.ProvisionAdmin;
import com.h3c.idcloud.core.adapter.facade.provision.ProvisionNetwork;
import com.h3c.idcloud.core.adapter.facade.provision.ProvisionScan;
import com.h3c.idcloud.core.adapter.facade.provision.ProvisionStorage;
import com.h3c.idcloud.core.adapter.facade.provision.ProvisionVm;
import com.h3c.idcloud.core.adapter.facade.provision.impl.MonitorCeilometerImpl;
import com.h3c.idcloud.core.adapter.facade.provision.impl.MonitorExternalImpl;
import com.h3c.idcloud.core.adapter.facade.provision.impl.MonitorVMwareImpl;
import com.h3c.idcloud.core.adapter.facade.provision.impl.ProvisionAdminKvmImpl;
import com.h3c.idcloud.core.adapter.facade.provision.impl.ProvisionDiskKvmImpl;
import com.h3c.idcloud.core.adapter.facade.provision.impl.ProvisionDiskVmwareImpl;
import com.h3c.idcloud.core.adapter.facade.provision.impl.ProvisionNetworkImpl;
import com.h3c.idcloud.core.adapter.facade.provision.impl.ProvisionScanKvmImpl;
import com.h3c.idcloud.core.adapter.facade.provision.impl.ProvisionScanPowerImpl;
import com.h3c.idcloud.core.adapter.facade.provision.impl.ProvisionScanVmwareImpl;
import com.h3c.idcloud.core.adapter.facade.provision.impl.ProvisionVmKvmImpl;
import com.h3c.idcloud.core.adapter.facade.provision.impl.ProvisionVmPowerImpl;
import com.h3c.idcloud.core.adapter.facade.provision.impl.ProvisionVmVmwareImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * provider factory
 *
 * @author qct
 */
@Service
public class ProviderFactory {

    @Autowired
    private ProvisionScanKvmImpl provisionScanKvm;

    @Autowired
    private ProvisionScanVmwareImpl provisionScanVmware;

    @Autowired
    private ProvisionVmVmwareImpl provisionVmVmware;

    @Autowired
    private ProvisionVmKvmImpl provisionVmKvm;

    @Autowired
    private ProvisionAdminKvmImpl provisionAdminKvm;

    @Autowired
    private ProvisionDiskKvmImpl provisionDiskKvm;

    @Autowired
    private ProvisionDiskVmwareImpl provisionDiskVmware;

    @Autowired
    private MonitorCeilometerImpl monitorCeilometer;

    @Autowired
    private MonitorExternalImpl monitorExternal;

    @Autowired
    private ProvisionNetworkImpl provisionNetwork;

    @Autowired
    private ProvisionVmPowerImpl provisionVmPower;

    @Autowired
    private ProvisionScanPowerImpl provisionScanPower;

    @Autowired
    private MonitorVMwareImpl monitorVMware;

    public Monitor getMonitor(String providerType) {

        if (providerType.equalsIgnoreCase(Constants.Provider.OPEN_STACK)) {
            return monitorCeilometer;

        } else if (providerType.equalsIgnoreCase(Constants.Provider.VMWARE)) {
            return monitorVMware;

        } else if (providerType.equalsIgnoreCase(Constants.Provider.HMC)) {
            return monitorExternal;

        } else {
            return null;
        }

    }

    public ProvisionVm getProvisionVm(String providerType) {

        if (providerType.equalsIgnoreCase(Constants.Provider.OPEN_STACK)) {
            return provisionVmKvm;

        } else if (providerType.equalsIgnoreCase(Constants.Provider.VMWARE)) {
            return provisionVmVmware;

        } else if (providerType.equalsIgnoreCase(Constants.Provider.HYPERV)) {
            return null;

        } else if (providerType.equalsIgnoreCase(Constants.Provider.HMC)) {
            return provisionVmPower;

        } else {
            return null;
        }

    }

    public ProvisionNetwork getProvisionNetwork(String providerType) {

        if (providerType.equalsIgnoreCase(Constants.Provider.OPEN_STACK)) {
            return provisionNetwork;

        } else if (providerType.equalsIgnoreCase(Constants.Provider.VMWARE)) {
            return null;

        } else if (providerType.equalsIgnoreCase(Constants.Provider.HYPERV)) {
            return null;

        } else {
            return null;
        }

    }

    public ProvisionAdmin getProvisionAdmin(String providerType) {

        if (providerType.equalsIgnoreCase(Constants.Provider.OPEN_STACK)) {
            return provisionAdminKvm;

        } else if (providerType.equalsIgnoreCase(Constants.Provider.VMWARE)) {
            return null;

        } else if (providerType.equalsIgnoreCase(Constants.Provider.HYPERV)) {
            return null;

        } else {
            return null;
        }

    }

    public ProvisionStorage getProvisionStorage(String providerType) {

        if (providerType.equalsIgnoreCase(Constants.Provider.OPEN_STACK)) {
            return provisionDiskKvm;

        } else if (providerType.equalsIgnoreCase(Constants.Provider.VMWARE)) {
            return provisionDiskVmware;

        } else if (providerType.equalsIgnoreCase(Constants.Provider.HYPERV)) {
            return null;

        } else {
            return null;
        }

    }

    public ProvisionScan getProvisionScan(String providerType) {

        if (providerType.equalsIgnoreCase(Constants.Provider.OPEN_STACK)) {
            return provisionScanKvm;

        } else if (providerType.equalsIgnoreCase(Constants.Provider.VMWARE)) {
            return provisionScanVmware;

        } else if (providerType.equalsIgnoreCase(Constants.Provider.HYPERV)) {
            return null;

        } else if (providerType.equalsIgnoreCase(Constants.Provider.HMC)) {
            return provisionScanPower;
        } else {
            return null;
        }

    }
}
