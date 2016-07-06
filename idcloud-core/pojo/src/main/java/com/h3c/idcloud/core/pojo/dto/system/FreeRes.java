package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.h3c.idcloud.infrastructure.common.util.JSONPercentToDoubleDeserializer;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

public class FreeRes implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 闲置资源SID
     */
    private Long fresSid;

    /**
     * 闲置资源名称
     */
    private String resName;

    /**
     * 资源实例SID
     */
    @JsonProperty("RES_SID")
    private String resSid;

    /**
     * 一级业务
     */
    private Long fbizSid;

    /**
     * 二级业务
     */
    private Long sbizSid;

    /**
     * CPU核数
     */
    @JsonProperty("CPU_CORES")
    private Long cpuCores;

    /**
     * CPU频率 GHz
     */
    @JsonProperty("CPU_FERQUENCY")
    private Double cpuFerquency;

    /**
     * 用于报表的组合
     */
    private String cpuGhz;
    
    /**
     * CPU使用率
     */
    @JsonDeserialize(using=JSONPercentToDoubleDeserializer.class)
    @JsonProperty("CPU_USAGE")
    private Double cpuUsage;

    /**
     * 内存配置 Gb
     */
    @JsonProperty("MEMORY")
    private Long memory;

    /**
     * 内存使用率
     */
    @JsonDeserialize(using=JSONPercentToDoubleDeserializer.class)
    @JsonProperty("MEM_USAGE")
    private Double memUsage;

    /**
     * 存储配置GB
     */
    @JsonProperty("STORAGE")
    private Long storage;
    
    /**
     * 存储配置GB转换为TB
     */
    private double storageFormat;

    /**
     * 存储使用量 GB
     */
    @JsonProperty("ST_USED")
    private Double stUsed;

    /**
     * 存储使用率
     */
    @JsonDeserialize(using=JSONPercentToDoubleDeserializer.class)
    @JsonProperty("ST_USAGE")
    private Double stUsage;

    /**
     * 峰值流量 Mbps
     */
    @JsonProperty("MAX_IO")
    private Double maxIo;

    /**
     * 均值流量 Mbps
     */
    @JsonProperty("AVG_IO")
    private Double avgIo;

    /**
     * 公网IP
     */
    @JsonProperty("PUB_IP")
    private String pubIp;

    /**
     * 内网IP
     */
    @JsonProperty("PRIVAGE_IP")
    private String privageIp;

    /**
     * 预留1
     */
    private String re1;

    /**
     * 预留2
     */
    private String re2;

    /**
     * 预留3
     */
    private String re3;

    /**
     * 预留4
     */
    private String re4;

    /**
     * 统计时间
     */
    private Date statTime;

    /**
     * 操作人
     */
    private Long creator;
    
    private String createName;

    /**
     * 操作时间
     */
    private Date opTime;
    
    /**
     * 实例名称
     */
    private String instanceName;
    
    /**
     * 一级业务名称
     */
    private String fbizText;
    
    /**
     * 二级业务名称
     */
    private String sbizText;
    
    /**
     * 所有者账号
     */
    private String ownerId;

    /**
     * 资源空闲类型
     */
    @JsonProperty("RES_FREE_TYPE")
    private String resFreeType;

    /**
     * 网络空闲
     */
    @JsonProperty("NET_FREE_TYPE")
    private String netFreeType;
    
    /**
     * 状态
     */
    @JsonProperty("STATUS")
    private Long status;
    
    private String statusName;

    private String nameDate;
    
    /**
     * 归属部门
     */
    private String orgName;
    
    /**
     * 开通时间
     */
    private Date openDate;
    
    /**
     * 回收时间
     */
    private Date finishDate;
    
    /**
     * 回收的cpu
     */
    private Long cpuRecovery;
    
    /**
     * 回收的内存
     */
    private Long memRecovery;
    
    /**
     * 回收的虚拟存储
     */
    private Long stRecovery;
    
    private Long stTBRecovery;
    
    /**
     * 回收配置建议
     */
    private String content;
    
    /**
     * 发起人
     */
    private String starter;
    
    /**
     * 发起次数
     */
    private Long startNum;
    
    private Date createdDt;
    
    /**
     * @return 闲置资源SID
     */
    public Long getFresSid() {
        return fresSid;
    }

    /**
     * @param fresSid 
	 *            闲置资源SID
     */
    public void setFresSid(Long fresSid) {
        this.fresSid = fresSid;
    }

    /**
     * @return 闲置资源名称
     */
    public String getResName() {
        return resName;
    }

    /**
     * @param resName 
	 *            闲置资源名称
     */
    public void setResName(String resName) {
        this.resName = resName;
    }

    /**
     * @return 资源实例SID
     */
    public String getResSid() {
        return resSid;
    }

    /**
     * @param resSid 
	 *            资源实例SID
     */
    public void setResSid(String resSid) {
        this.resSid = resSid;
    }

    /**
     * @return 一级业务
     */
    public Long getFbizSid() {
        return fbizSid;
    }

    /**
     * @param fbizSid 
	 *            一级业务
     */
    public void setFbizSid(Long fbizSid) {
        this.fbizSid = fbizSid;
    }

    /**
     * @return 二级业务
     */
    public Long getSbizSid() {
        return sbizSid;
    }

    /**
     * @param sbizSid 
	 *            二级业务
     */
    public void setSbizSid(Long sbizSid) {
        this.sbizSid = sbizSid;
    }

    /**
     * @return CPU核数
     */
    public Long getCpuCores() {
        return cpuCores;
    }

    /**
     * @param cpuCores 
	 *            CPU核数
     */
    public void setCpuCores(Long cpuCores) {
        this.cpuCores = cpuCores;
    }

    /**
     * @return CPU频率 GHz
     */
    public Double getCpuFerquency() {
        return cpuFerquency;
    }

    /**
     * @param cpuFerquency 
	 *            CPU频率 GHz
     */
    public void setCpuFerquency(Double cpuFerquency) {
        this.cpuFerquency = cpuFerquency;
    }

    /**
     * @return CPU使用率
     */
    public Double getCpuUsage() {
        return cpuUsage;
    }

    /**
     * @param cpuUsage 
	 *            CPU使用率
     */
    public void setCpuUsage(Double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    /**
     * @return 内存配置 Gb
     */
    public Long getMemory() {
        return memory;
    }

    /**
     * @param memory 
	 *            内存配置 Gb
     */
    public void setMemory(Long memory) {
        this.memory = memory;
    }

    /**
     * @return 内存使用率
     */
    public Double getMemUsage() {
        return memUsage;
    }

    /**
     * @param memUsage 
	 *            内存使用率
     */
    public void setMemUsage(Double memUsage) {
        this.memUsage = memUsage;
    }

    /**
     * @return 存储配置GB
     */
    public Long getStorage() {
        return storage;
    }

    /**
     * @param storage 
	 *            存储配置GB
     */
    public void setStorage(Long storage) {
        this.storage = storage;
    }

    /**
     * @return 存储使用量 GB
     */
    public Double getStUsed() {
        return stUsed;
    }

    /**
     * @param stUsed 
	 *            存储使用量 GB
     */
    public void setStUsed(Double stUsed) {
        this.stUsed = stUsed;
    }

    /**
     * @return 存储使用率
     */
    public Double getStUsage() {
        return stUsage;
    }

    /**
     * @param stUsage 
	 *            存储使用率
     */
    public void setStUsage(Double stUsage) {
        this.stUsage = stUsage;
    }

    /**
     * @return 峰值流量 Mbps
     */
    public Double getMaxIo() {
        return maxIo;
    }

    /**
     * @param maxIo 
	 *            峰值流量 Mbps
     */
    public void setMaxIo(Double maxIo) {
        this.maxIo = maxIo;
    }

    /**
     * @return 均值流量 Mbps
     */
    public Double getAvgIo() {
        return avgIo;
    }

    /**
     * @param avgIo 
	 *            均值流量 Mbps
     */
    public void setAvgIo(Double avgIo) {
        this.avgIo = avgIo;
    }

    /**
     * @return 公网IP
     */
    public String getPubIp() {
        return pubIp;
    }

    /**
     * @param pubIp 
	 *            公网IP
     */
    public void setPubIp(String pubIp) {
        this.pubIp = pubIp;
    }

    /**
     * @return 内网IP
     */
    public String getPrivageIp() {
        return privageIp;
    }

    /**
     * @param privageIp 
	 *            内网IP
     */
    public void setPrivageIp(String privageIp) {
        this.privageIp = privageIp;
    }

    /**
     * @return 预留1
     */
    public String getRe1() {
        return re1;
    }

    /**
     * @param re1 
	 *            预留1
     */
    public void setRe1(String re1) {
        this.re1 = re1;
    }

    /**
     * @return 预留2
     */
    public String getRe2() {
        return re2;
    }

    /**
     * @param re2 
	 *            预留2
     */
    public void setRe2(String re2) {
        this.re2 = re2;
    }

    /**
     * @return 预留3
     */
    public String getRe3() {
        return re3;
    }

    /**
     * @param re3 
	 *            预留3
     */
    public void setRe3(String re3) {
        this.re3 = re3;
    }

    /**
     * @return 预留4
     */
    public String getRe4() {
        return re4;
    }

    /**
     * @param re4 
	 *            预留4
     */
    public void setRe4(String re4) {
        this.re4 = re4;
    }

    /**
     * @return 统计时间
     */
    public Date getStatTime() {
        return statTime;
    }

    /**
     * @param statTime 
	 *            统计时间
     */
    public void setStatTime(Date statTime) {
        this.statTime = statTime;
    }

    /**
     * @return 操作人
     */
    public Long getCreator() {
        return creator;
    }

    /**
     * @param creator 
	 *            操作人
     */
    public void setCreator(Long creator) {
        this.creator = creator;
    }

    /**
     * @return 操作时间
     */
    public Date getOpTime() {
        return opTime;
    }

    /**
     * @param opTime 
	 *            操作时间
     */
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    /**
     * @return 资源空闲类型
     */
    public String getResFreeType() {
        return resFreeType;
    }

    /**
     * @param resFreeType 
	 *            资源空闲类型
     */
    public void setResFreeType(String resFreeType) {
        this.resFreeType = resFreeType;
    }

    /**
     * @return 网络空闲
     */
    public String getNetFreeType() {
        return netFreeType;
    }

    /**
     * @param netFreeType 
	 *            网络空闲
     */
    public void setNetFreeType(String netFreeType) {
        this.netFreeType = netFreeType;
    }

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getFbizText() {
		return fbizText;
	}

	public void setFbizText(String fbizText) {
		this.fbizText = fbizText;
	}

	public String getSbizText() {
		return sbizText;
	}

	public void setSbizText(String sbizText) {
		this.sbizText = sbizText;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public double getStorageFormat() {
		BigDecimal a = new BigDecimal(0);
		if(this.storage!=null){
			a = new BigDecimal(this.storage).divide(new BigDecimal(1024), 2, BigDecimal.ROUND_HALF_UP);
		}
		return a.doubleValue();
	}

	public void setStorageFormat(double storageFormat) {
		this.storageFormat = storageFormat;
	}

	public String getCpuGhz() {
		String cpu = this.cpuCores+"*"+this.cpuFerquency;
		return cpu;
	}

	public void setCpuGhz(String cpuGhz) {
		this.cpuGhz = cpuGhz;
	}

	public String getNameDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = this.instanceName+"("+sdf.format(this.opTime)+")";
		return str;
	}

	public void setNameDate(String nameDate) {
		this.nameDate = nameDate;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Long getCpuRecovery() {
		return cpuRecovery;
	}

	public void setCpuRecovery(Long cpuRecovery) {
		this.cpuRecovery = cpuRecovery;
	}

	public Long getMemRecovery() {
		return memRecovery;
	}

	public void setMemRecovery(Long memRecovery) {
		this.memRecovery = memRecovery;
	}

	public Long getStRecovery() {
		return stRecovery;
	}

	public void setStRecovery(Long stRecovery) {
		this.stRecovery = stRecovery;
	}

	public void setStTBRecovery(Long stTBRecovery) {
		this.stTBRecovery = stTBRecovery;
	}

	public Long getStTBRecovery() {
		BigDecimal divide = new BigDecimal(0);
		if(this.stRecovery!=null){
			divide = BigDecimal.valueOf(this.stRecovery).divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_UP);
		}
		return divide.longValue();
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStarter() {
		return starter;
	}

	public void setStarter(String starter) {
		this.starter = starter;
	}

	public Long getStartNum() {
		return startNum;
	}

	public void setStartNum(Long startNum) {
		this.startNum = startNum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}
    
}