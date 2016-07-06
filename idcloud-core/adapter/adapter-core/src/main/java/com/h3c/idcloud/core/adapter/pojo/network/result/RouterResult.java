package com.h3c.idcloud.core.adapter.pojo.network.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.network.Port;
import com.h3c.idcloud.core.adapter.pojo.network.Router;

import java.util.Set;

/**
 * Created by qct on 2016/4/15.
 *
 * @author qct
 */
public class RouterResult extends BaseResult {
    private Router router;

    private Set<Port> ports;

    public Set<Port> getPorts() {
        return ports;
    }

    public void setPorts(Set<Port> ports) {
        this.ports = ports;
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }
}
