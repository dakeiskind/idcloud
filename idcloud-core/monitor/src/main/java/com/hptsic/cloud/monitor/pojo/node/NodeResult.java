package com.hptsic.cloud.monitor.pojo.node;

import com.hptsic.cloud.monitor.provision.model.CommonAdapterResult;

public class NodeResult extends CommonAdapterResult {
	private CurrentInfo currentInfo;

	public CurrentInfo getCurrentInfo() {
		return currentInfo;
	}

	public void setCurrentInfo(CurrentInfo currentInfo) {
		this.currentInfo = currentInfo;
	}
	
}
