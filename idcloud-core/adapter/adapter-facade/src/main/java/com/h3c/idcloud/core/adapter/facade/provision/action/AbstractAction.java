package com.h3c.idcloud.core.adapter.facade.provision.action;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;

/**
 * default implementation of Action Created by qct on 2016/3/18.
 *
 * @author qct
 */
public abstract class AbstractAction implements Action {
    @Override
    public CommonAdapterResult invoke(Base base) throws CommonAdapterException, AdapterUnavailableException {
        CommonAdapterResult result = null;
        try {
            beforeExec(base);
            result = execute(base);
            afterExec(base);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new KvmException("500", e.getMessage());
        }
        return result;
    }

    /**
     * before execute
     *
     * @param base execute subject
     * @throws CommonAdapterException CommonAdapterException
     */
    public abstract void beforeExec(Base base) throws CommonAdapterException;

    /**
     * after execute
     *
     * @param base execute subject
     * @throws CommonAdapterException CommonAdapterException
     */
    public abstract void afterExec(Base base) throws CommonAdapterException;

    /**
     * really execute method
     *
     * @param base execute subject
     * @return result of execute
     * @throws CommonAdapterException      CommonAdapterException
     * @throws AdapterUnavailableException AdapterUnavailableException
     */
    protected abstract CommonAdapterResult execute(Base base)
            throws CommonAdapterException, AdapterUnavailableException;
}
