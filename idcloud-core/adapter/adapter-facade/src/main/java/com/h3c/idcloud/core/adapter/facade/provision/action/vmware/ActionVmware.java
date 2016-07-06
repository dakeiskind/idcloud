package com.h3c.idcloud.core.adapter.facade.provision.action.vmware;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.VmwareException;
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

import javax.ws.rs.core.Response;

public abstract class ActionVmware {

    protected static Logger log = LoggerFactory.getLogger(ActionVmware.class);
    @Autowired
    protected FreeMarkerHelper helper;
    protected List<JacksonJaxbJsonProvider> providers = new ArrayList<JacksonJaxbJsonProvider>();
    Properties adapter = new Properties();
    private String adapterUrl = null;

    public ActionVmware() {
        JacksonJaxbJsonProvider jsonProvider = new JacksonJaxbJsonProvider();
        providers.add(jsonProvider);

        URL adapter_ = super.getClass().getResource("/adapter.properties");

        try {
            adapter.load(adapter_.openStream());
            setAdapterUrl(adapter.getProperty("vmware"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public CommonAdapterResult invoke(Base base) throws VmwareException, AdapterUnavailableException {

        this.beforeExec();
        CommonAdapterResult result = this.execute(base);
        this.afterExec();

        return result;
    }

    protected abstract CommonAdapterResult execute(Base base) throws VmwareException, AdapterUnavailableException;

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

    protected void checkResponseStatus(Response resp) throws VmwareException {
        if (resp.getStatus() == 400) {
            throw new VmwareException("9998", "vcenter exception");
        } else if (resp.getStatus() == 401) {
            throw new VmwareException("9997", "vcenter authentication error");
        }
    }

}
