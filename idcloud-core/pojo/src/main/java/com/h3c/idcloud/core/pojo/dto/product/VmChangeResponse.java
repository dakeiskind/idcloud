package com.h3c.idcloud.core.pojo.dto.product;



import com.h3c.idcloud.core.pojo.instance.ResNetworkInst;
import com.h3c.idcloud.core.pojo.instance.ResVdInst;

import java.util.List;

/**
 * 
 * @author ChengQi
 *
 */
public class VmChangeResponse {

	private String resVmInstId;
	private String resVmInstName;
	private String resVmInstNamePrefix;
	private int cpu;
	private long memory;
	private String imageSid;
	private String managementAccount;
	private String managementPassword;
	private String allocateResHostId;
	private String allocateResVcId;
	private List<ResVdInst> dataDisks;
	private List<ResNetworkInst> nets;
	
	

}
