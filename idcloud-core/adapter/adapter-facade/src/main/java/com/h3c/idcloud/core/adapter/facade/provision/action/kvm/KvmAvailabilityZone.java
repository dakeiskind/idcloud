package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Cluster;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Clusters;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Host;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmAvailabilityZone extends ActionKvm {

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
//		AvailabilityZone result = new AvailabilityZone();
        Clusters result = new Clusters();
        log.info("query availabilityzone");

        WebClient client = WebClient.create(this.getAdapterUrl(), providers);
        HTTPConduit conduit = client.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);
        client = BaseUtil.setHeaders(client, base);
        Map respMap = null;

        try {
            Response resp = client.path("/availability_zones").type(MediaType.APPLICATION_JSON).get();

            if (resp.getStatus() == 200) {
                respMap = resp.readEntity(Map.class);
                if (respMap.containsKey("availability_zones")) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, String>> listMap = (List<Map<String, String>>) respMap.get("availability_zones");
                    if (listMap != null) {
                        List<Cluster> listVos = new ArrayList<Cluster>();
                        for (Map<String, String> map : listMap) {
                            Cluster vo = new Cluster();
                            vo.setResTopologyName(map.get("zone_name"));
                            @SuppressWarnings("unchecked")
                            List<Map<String, Object>> hostList = BaseUtil.castObject(map.get("hosts"), List.class);
                            List<Host> hostVOs = new ArrayList<Host>();
                            if (!CollectionUtils.isEmpty(hostList)) {
                                for (Map<String, Object> map2 : hostList) {
                                    Host hostVO = new Host();
                                    hostVO = BaseUtil.castObject(map2, Host.class);
                                    hostVOs.add(hostVO);
                                }
                                vo.setHosts(hostVOs);
                            }
                            listVos.add(vo);
                        }
                        result.setListdata(listVos);
                        result.setSuccess(true);
                    }
                }
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when query availabilityzone");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof KvmException) {
                throw (KvmException) e;
            } else {
                throw new KvmException("500", e.getMessage());
            }
        }
        return result;
    }
}
