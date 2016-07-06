package com.h3c.idcloud.core.adapter.facade.provision.action.vmware;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.VmwareException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.SnapshotInfo;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.SnapshotInfos;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmSnapshotQuery;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;

@Service
public class VmwareVmSnapshotQuery extends ActionVmware {

    private static Logger log = LoggerFactory.getLogger(VmwareVmSnapshotQuery.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws VmwareException,
            AdapterUnavailableException {

        VmSnapshotQuery vmSnapshotQuery = (VmSnapshotQuery) base;

        SnapshotInfos snapshotInfos = new SnapshotInfos();

        String json = helper.getParam(vmSnapshotQuery.getProviderType(),
                vmSnapshotQuery);

        log.info("vm snapshot query post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(),
                this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        try {
            Collection<?> c = client.path("/vm/snapshot/query")
                    .type(MediaType.APPLICATION_JSON)
                    .postAndGetCollection(json, SnapshotInfo.class);

            List<SnapshotInfo> lst = new ArrayList<SnapshotInfo>();

            for (Object object : c) {
                SnapshotInfo snapshotInfo = (SnapshotInfo) object;
                lst.add(snapshotInfo);
            }

            snapshotInfos.setSnapshotInfos(lst);

        } catch (ClientException e) {
            log.info("vmware adapter is not available when querying snapshot");
            throw new AdapterUnavailableException();

        } catch (Exception e) {
            log.error(e.getMessage());
            if (e instanceof VmwareException) {
                throw (VmwareException) e;
            } else {
                VmwareException vmwareException = new VmwareException("500", e.getMessage());
                throw vmwareException;
            }
        }

        return snapshotInfos;
    }

}
