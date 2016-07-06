package com.h3c.idcloud.core.adapter.facade.provision.action.powervm;

import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.ActionKvm;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.PowerException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.util.FreeMarkerHelper;
import com.h3c.idcloud.core.adapter.pojo.common.Base;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class ActionPowervm {

    protected static Logger log = LoggerFactory.getLogger(ActionKvm.class);
    @Autowired
    protected FreeMarkerHelper helper;
    protected List<JacksonJaxbJsonProvider> providers = new ArrayList<JacksonJaxbJsonProvider>();
    Properties adapter = new Properties();
    private String adapterUrl = null;

    public ActionPowervm() {
        JacksonJaxbJsonProvider jsonProvider = new JacksonJaxbJsonProvider();
        providers.add(jsonProvider);

        URL adapter_ = super.getClass().getResource("/adapter.properties");

        try {
            adapter.load(adapter_.openStream());
            setAdapterUrl(adapter.getProperty("powervm"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public CommonAdapterResult invoke(Base base) throws AdapterUnavailableException, PowerException {
        log.info(base.toString());
        this.beforeExec();
        CommonAdapterResult result = this.execute(base);
        this.afterExec();

        return result;
    }

    protected abstract CommonAdapterResult execute(Base base) throws AdapterUnavailableException, PowerException;

    protected void beforeExec() {

    }

    protected void afterExec() {

    }

    public String getAdapterUrl() {
        return adapterUrl;
    }

    private void setAdapterUrl(String adapterUrl) {
        this.adapterUrl = adapterUrl;
    }

}
