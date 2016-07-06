package com.h3c.idcloud.core.adapter.facade.provision.action.monitor.external;

import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
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

public abstract class ActionExternal {

    protected static Logger log = LoggerFactory.getLogger(ActionExternal.class);
    @Autowired
    protected FreeMarkerHelper helper;
    protected List<JacksonJaxbJsonProvider> providers = new ArrayList<JacksonJaxbJsonProvider>();
    Properties adapter = new Properties();
    private String adapterUrl = null;

    public ActionExternal() {
        JacksonJaxbJsonProvider jsonProvider = new JacksonJaxbJsonProvider();
        providers.add(jsonProvider);

        URL adapter_ = super.getClass().getResource("/adapter.monitor.properties");

        try {
            adapter.load(adapter_.openStream());
            setAdapterUrl(adapter.getProperty("external"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public CommonAdapterResult invoke(Base base) throws CommonAdapterException {

        this.beforeExec();
        CommonAdapterResult result = this.execute(base);
        this.afterExec();

        return result;
    }

    protected abstract CommonAdapterResult execute(Base base)
            throws CommonAdapterException;

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
