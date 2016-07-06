package com.hptsic.cloud.monitor.provision.action.alarm;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.MonitorException;
import com.hptsic.cloud.monitor.provision.model.CommonAdapterResult;
import com.hptsic.cloud.monitor.util.BaseUtil;
import com.hptsic.cloud.monitor.util.FreeMarkerHelper;
import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class ActionAlarm {
	@Autowired
	protected FreeMarkerHelper helper;

	protected Logger log = Logger.getLogger(this.getClass());

	private String adapterUrl = null;

	Properties adapter = new Properties();

	protected List<JacksonJaxbJsonProvider> providers = new ArrayList<JacksonJaxbJsonProvider>();

	public ActionAlarm() {
		JacksonJaxbJsonProvider jsonProvider = new JacksonJaxbJsonProvider();
		providers.add(jsonProvider);

		URL adapter_ = super.getClass().getResource("/adapter.properties");

		try {
			adapter.load(adapter_.openStream());
			setAdapterUrl(adapter.getProperty("kvm"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CommonAdapterResult invoke(Base base) throws MonitorException, AdapterUnavailableException {
		log.info(BaseUtil.toJson(base));
		this.beforeExec();
		CommonAdapterResult result = this.execute(base);
		this.afterExec();

		return result;
	}

	protected abstract CommonAdapterResult execute(Base base) throws MonitorException, AdapterUnavailableException;

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
