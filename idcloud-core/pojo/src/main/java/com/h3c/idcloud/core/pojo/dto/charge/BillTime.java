package com.h3c.idcloud.core.pojo.dto.charge;

import java.io.Serializable;

public class BillTime implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 账单开始时间
     */
    private String billTimeName;
    
    /**
     * 时间长度(按天)
     */
    private int timeDaysCount;
    
    /**
     * 计费开始时间
     */
    private String billStartTime;
    
    /**
     * 计费结束时间 
     */
    private String billEndTime;

	public String getBillTimeName() {
		return billTimeName;
	}

	public void setBillTimeName(String billTimeName) {
		this.billTimeName = billTimeName;
	}

	public int getTimeDaysCount() {
		return timeDaysCount;
	}

	public void setTimeDaysCount(int timeDaysCount) {
		this.timeDaysCount = timeDaysCount;
	}

	public String getBillStartTime() {
		return billStartTime;
	}

	public void setBillStartTime(String billStartTime) {
		this.billStartTime = billStartTime;
	}

	public String getBillEndTime() {
		return billEndTime;
	}

	public void setBillEndTime(String billEndTime) {
		this.billEndTime = billEndTime;
	}
    
    
    
}