package com.h3c.idcloud.core.adapter.facade.provision.action;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;

/**
 * define invocation of actions.
 *
 * Created by qct on 2016/3/18.
 *
 * @author qct
 */
public interface Action {
    /**
     * invoke action
     *
     * @param base base object
     * @return result of invocation
     * @throws CommonAdapterException      CommonAdapterException
     * @throws AdapterUnavailableException AdapterUnavailableException
     */
    CommonAdapterResult invoke(Base base) throws CommonAdapterException, AdapterUnavailableException;
}
