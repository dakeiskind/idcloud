package com.h3c.idcloud.core.service.product.api;


import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;

/**
 * 监控服务接口
 * 
 * @author ChengQi
 *
 */
public interface  MonitorService {
	
	String addMonitorNode(String nodeName, String ip) throws Exception;

	void addVmToMonitor(ResVm resVm) throws Exception;
	
	void addVmToMonitor(String vmId) throws Exception;
	
	void addHostToMonitor(String hostId) throws Exception;
	
	void addHostToMonitor(ResHost resHost) throws Exception;
	
	void deleteMonitorNode(String nodeId) throws Exception;
	
	void deleteVmFromMonitor(ResVm resVm) throws Exception;
	
	void deleteVmFromMonitor(String vmId) throws Exception;
	
	void deleteHostFromMonitor(ResHost resHost) throws Exception;
	
	void deleteHostFromMonitor(String hostId) throws Exception;
}
