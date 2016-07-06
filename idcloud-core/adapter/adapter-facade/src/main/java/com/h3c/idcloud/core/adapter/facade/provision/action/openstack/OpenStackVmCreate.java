package com.h3c.idcloud.core.adapter.facade.provision.action.openstack;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import com.h3c.idcloud.core.adapter.facade.common.NeutronApiFactory;
import com.h3c.idcloud.core.adapter.facade.common.NovaApiFactory;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.ActionKvm;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.network.NetworkStatus;
import com.h3c.idcloud.core.adapter.pojo.vm.VmCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNic;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.SetUtils;
import org.jclouds.collect.PagedIterable;
import org.jclouds.openstack.neutron.v2.domain.IP;
import org.jclouds.openstack.neutron.v2.domain.Port;
import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.KeyPair;
import org.jclouds.openstack.nova.v2_0.domain.Network;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.domain.ServerCreated;
import org.jclouds.openstack.nova.v2_0.extensions.KeyPairApi;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;
import org.jclouds.openstack.nova.v2_0.options.CreateServerOptions;
import org.jclouds.openstack.v2_0.options.PaginationOptions;
import org.jclouds.rest.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * Created by qct on 2016/2/24.
 *
 * @author qct-office
 */
@Service
@Scope(value = SCOPE_PROTOTYPE)
public class OpenStackVmCreate extends ActionKvm implements Runnable {
    /**
     * log
     */
    private static Logger logger = LoggerFactory.getLogger(OpenStackVmCreate.class);

    /**
     * action base
     */
    private Base base;
    /**
     * created server returned by open stack
     */
    private ServerCreated serverCreated;
    /**
     * newly created server via open stack
     */
    private Server newlyCreatedServer;

    /**
     * newly created port, for rollback
     */
    private Port port;

    /**
     * polling frequency
     */
    private static final long FREQUENCY = 5000;

    /**
     * uuid of this class
     */
    private UUID uuid = UUID.randomUUID();

    @Override
    protected CommonAdapterResult execute(Base base) throws CommonAdapterException,
            AdapterUnavailableException {
        long start = System.currentTimeMillis();
        this.base = base;
        VmCreate vmCreate = VmCreate.class.cast(base);
        logger.debug("OpenstackVmCreate({}), vmCreate: {}", uuid, vmCreate);
        ServerApi serverApi = NovaApiFactory.INSTANCE.createNovaApi(base).getServerApi(vmCreate.getRegion());

        CreateServerOptions options = CreateServerOptions.Builder
                .availabilityZone(vmCreate.getAvailabilityZone())
//                .networks(vmCreate.getNics().stream().map(nic -> nic.getNetId()).collect(Collectors.toList()))
                .adminPass(vmCreate.getAdminPass());
        if(vmCreate.getKeypair() != null) {
            prepareKeypairs(options, base);
        }
        prepareNetworks(options, base);

        try {
            ServerCreated serverCreated = serverApi.create(vmCreate.getName(),
                    vmCreate.getImage(),
//                getFlavor(vmCreate).getId(),
                    "1",
                    options);
            if (serverCreated == null) {
                throw new KvmException("500", "openStack returned serverCreated is null");
            }

            this.serverCreated = serverCreated;
            long end = System.currentTimeMillis();
            logger.debug("serverCreated: {}, time elapse: {}", serverCreated, (end - start));
            return getServerResult();
        } catch (AuthorizationException e) {
            rollback();
            throw new KvmException("500", e.getMessage());
        }
    }

    private void prepareKeypairs(CreateServerOptions options, Base base) throws KvmException {
        Optional<KeyPairApi> keyPairApiOptional = NovaApiFactory.INSTANCE.createNovaApi(base).getKeyPairApi(
                base.getRegion());
        if(keyPairApiOptional.isPresent()) {
            KeyPair keyPair = keyPairApiOptional.get().get(VmCreate.class.cast(base).getKeypair().getName());
            if(keyPair != null) {
                options.keyPairName(keyPair.getName());
            }else {
                KeyPair newlyCreatedKeyPair = keyPairApiOptional.get().createWithPublicKey(
                        VmCreate.class.cast(base).getKeypair().getName(),
                        VmCreate.class.cast(base).getKeypair().getPublicKey());
                if(newlyCreatedKeyPair != null) {
                    options.keyPairName(newlyCreatedKeyPair.getName());
                }else {
                    throw new KvmException("500", "KeyPairApi create failed.");
                }
            }
        }else {
            throw new KvmException("500", "KeyPairApi is absent");
        }
    }

    private void rollback() {
        rollbackPort();
    }

    private void rollbackPort() {
        if(port != null) {
            NeutronApiFactory.INSTANCE.createNeutronApi(base).getPortApi(base.getRegion()).delete(port.getId());
        }
    }

    private void prepareNetworks(CreateServerOptions options, Base base) {
        ArrayList<Network> networks = Lists.newArrayList();
        VmCreate.class.cast(base).getNics().forEach(vmNic -> {
            port = NeutronApiFactory.INSTANCE.createNeutronApi(base).getPortApi(base.getRegion()).create(
                    Port.CreatePort.createBuilder(vmNic.getNetId())
                            .fixedIps(ImmutableSet.of(IP.builder().subnetId(vmNic.getSubnetId()).build()))
                            .build());
            logger.debug("--> create port: {}", port);
            networks.add(Network.builder().networkUuid(vmNic.getNetId()).portUuid(port.getId()).build());
            logger.debug("--> bind port({}) to network({}).", port.getId(), vmNic.getNetId());
        });
        options.novaNetworks(networks);
    }

    /**
     * combine result
     *
     * @return common adapter result
     * @throws KvmException kvm exception
     */
    private CommonAdapterResult getServerResult() throws KvmException {
        com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server resultServer =
                new com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server();
        // start thread to query vm
        ScheduledExecutorService singleThreadScheduledPool = Executors.newSingleThreadScheduledExecutor();
        singleThreadScheduledPool.scheduleWithFixedDelay(this, 2000, 2000, TimeUnit.MILLISECONDS);

        int timeout = 60000;
        try {
            while (timeout > 0) {
                Thread.sleep(FREQUENCY);
                timeout -= FREQUENCY;
                if (newlyCreatedServer == null) {
                    continue;
                }
                switch (newlyCreatedServer.getStatus()) {
                    case ACTIVE: {
                        assembleResult(resultServer, true);
                        shutDownThreadPool(singleThreadScheduledPool);
                        logger.debug("server active, create successfully.{}", newlyCreatedServer);
                        return resultServer;
                    }
                    case ERROR: {
                        assembleResult(resultServer, false);
                        shutDownThreadPool(singleThreadScheduledPool);
                        logger.debug("server error, create successfully but status error.{}", newlyCreatedServer);
                        return resultServer;
                    }
                    default: {
                        logger.debug("server: {}", newlyCreatedServer);
                    }
                }
            }
            shutDownThreadPool(singleThreadScheduledPool);
            assembleResult(resultServer, false);
            logger.debug("query server timeout.{}", newlyCreatedServer);
            return resultServer;
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new KvmException("500", "InterruptedException occurred");
        }
    }

    /**
     * assemble result
     *
     * @param resultServer result
     * @param isSuccess    whether success
     */
    private void assembleResult(com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server resultServer,
                                boolean isSuccess) {
        resultServer.setName(newlyCreatedServer.getName());
        resultServer.setUuid(newlyCreatedServer.getId());
        resultServer.setStatus(newlyCreatedServer.getStatus().value());

        resultServer.setPorts(Sets.newHashSet(BaseUtil.assemblePort(port)));
//        resultServer.setHost(newlyCreatedServer.getHostId());

        if (newlyCreatedServer.getExtendedAttributes().isPresent()) {
            resultServer.setHostName(newlyCreatedServer.getExtendedAttributes().get().getHostName());
            resultServer.setHost(resultServer.getHostName());
        }
        resultServer.setVmNics(assembleNetworks());
        resultServer.setSuccess(isSuccess);
    }

    /**
     *
     * @return
     */
    private List<VmNic> assembleNetworks() {
        List<VmNic> vmNics = VmCreate.class.cast(base).getNics();
        for (VmNic vmNic : vmNics) {
            ArrayListMultimap<String, String> multimap = ArrayListMultimap.create();
            multimap.put("network_id", vmNic.getNetId());
            multimap.put("device_id", newlyCreatedServer.getId());
            NeutronApiFactory.INSTANCE.createNeutronApi(base).getPortApi(base.getRegion()).list(
                    PaginationOptions.Builder.queryParameters(multimap)
            ).forEach(port -> {
                ImmutableSet<IP> fixedIps = port.getFixedIps();
                if (!fixedIps.isEmpty() && fixedIps.stream().findFirst().isPresent()) {
                    vmNic.setPrivateIp(fixedIps.stream().findFirst().get().getIpAddress());
                    vmNic.setSubnetId(fixedIps.stream().findFirst().get().getSubnetId());
                }
                vmNic.setMac(port.getMacAddress());
                vmNic.setPort(port.getId());
            });
        }
        return vmNics;
    }

    /**
     * find flavor by cpu mem disk, if there is no flavor found by the criteria, create a new flavor
     *
     * @param vmCreate query criteria, include cpu, memory, sysDiskSize
     * @return flavor found in open stack or newly created flavor
     */
    private Flavor getFlavor(VmCreate vmCreate) {
        PagedIterable<Flavor> flavorPagedIterable = NovaApiFactory.INSTANCE.createNovaApi(vmCreate)
                .getFlavorApi(vmCreate.getRegion()).listInDetail();
        Optional<Flavor> flavorOptional = flavorPagedIterable.concat().firstMatch(flavor -> {
            return flavor.getVcpus() == Integer.parseInt(vmCreate.getCpu())
                    && flavor.getRam() == Integer.parseInt(vmCreate.getMemory())
                    && flavor.getDisk() == Integer.parseInt(vmCreate.getSysDiskSize());
        });

        if (flavorOptional.isPresent()) {
            return flavorOptional.get();
        } else {
            logger.debug("create flavor, cpu:{}, ram:{}, sysDisk:{}",
                    vmCreate.getCpu(),
                    vmCreate.getMemory(),
                    vmCreate.getSysDiskSize());
            UUID uuid = UUID.randomUUID();
            Flavor flavor = NovaApiFactory.INSTANCE.createNovaApi(vmCreate).getFlavorApi(vmCreate
                    .getRegion()).create(Flavor.builder()
                    .name("qct-flavor-" + uuid)
                    .vcpus(Integer.parseInt(vmCreate.getCpu()))
                    .ram(Integer.parseInt(vmCreate.getMemory()))
                    .disk(Integer.parseInt(vmCreate.getSysDiskSize()))
//                    .id(uuid.toString())
                    .build());
            logger.debug("newly created flavor: {}", flavor);
            return flavor;
        }
    }

    /**
     * close thread pool
     *
     * @param singleThreadScheduledPool the thread pool to be close
     */
    private void shutDownThreadPool(ScheduledExecutorService singleThreadScheduledPool) {
        if (!singleThreadScheduledPool.isShutdown()) {
            logger.debug("schedule finished, will shutdown thread pool.");
            singleThreadScheduledPool.shutdownNow();
        }
    }

    @Override
    public void run() {
        Server newlyCreatedServer = NovaApiFactory.INSTANCE.createNovaApi(base).getServerApi(base
                .getRegion())
                .get(serverCreated.getId());
        this.newlyCreatedServer = newlyCreatedServer;
    }
}
