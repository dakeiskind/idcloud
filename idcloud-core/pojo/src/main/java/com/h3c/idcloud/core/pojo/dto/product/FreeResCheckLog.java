package com.h3c.idcloud.core.pojo.dto.product;

import java.io.Serializable;
import java.util.Date;

public class FreeResCheckLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long fresCheckLogSid;

    private Long fresSid;

    private Long cpuCores;

    /**
     * Gb
     */
    private Double memory;

    /**
     * GB
     */
    private Double storage;

    private String pubIp;

    private String privageIp;

    private Long startNum;

    private String starter;

    private Date startTime;
    
    private Long cmSid;
    
    private String bizText;
    
    private String disk;
    
    private String network;
    



    public Long getFresCheckLogSid() {
		return fresCheckLogSid;
	}

	public void setFresCheckLogSid(Long fresCheckLogSid) {
		this.fresCheckLogSid = fresCheckLogSid;
	}

	public Long getFresSid() {
		return fresSid;
	}

	public void setFresSid(Long fresSid) {
		this.fresSid = fresSid;
	}

	public Long getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(Long cpuCores) {
        this.cpuCores = cpuCores;
    }

    /**
     * @return Gb
     */
    public Double getMemory() {
        return memory;
    }

    /**
     * @param memory 
	 *            Gb
     */
    public void setMemory(Double memory) {
        this.memory = memory;
    }

    /**
     * @return GB
     */
    public Double getStorage() {
        return storage;
    }

    /**
     * @param storage 
	 *            GB
     */
    public void setStorage(Double storage) {
        this.storage = storage;
    }

    public String getPubIp() {
        return pubIp;
    }

    public void setPubIp(String pubIp) {
        this.pubIp = pubIp;
    }

    public String getPrivageIp() {
        return privageIp;
    }

    public void setPrivageIp(String privageIp) {
        this.privageIp = privageIp;
    }

	public Long getCmSid() {
		return cmSid;
	}

	public void setCmSid(Long cmSid) {
		this.cmSid = cmSid;
	}

	public String getBizText() {
		return bizText;
	}

	public void setBizText(String bizText) {
		this.bizText = bizText;
	}

	public Long getStartNum() {
		return startNum;
	}

	public void setStartNum(Long startNum) {
		this.startNum = startNum;
	}

	public String getStarter() {
		return starter;
	}

	public void setStarter(String starter) {
		this.starter = starter;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}
	
	
}