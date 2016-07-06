package com.h3c.idcloud.core.pojo.dto.product;

import java.io.Serializable;
import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonProperty;

public class FreeResTotal implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 一级业务
     */
    private Long fbizSid;
    
    /**
     * 公网IP
     */
    private Long pubIp;
    
    /**
     * 内网IP
     */
    private Long privateIp;

    /**
     * 一级业务名称
     */
    private String fbizText;

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
     * @return 公网IP
     */
    public Long getPubIp() {
        return pubIp;
    }

    /**
     * @param pubIp 
	 *            公网IP
     */
    public void setPubIp(Long pubIp) {
        this.pubIp = pubIp;
    }

	public String getFbizText() {
		return fbizText;
	}

	public void setFbizText(String fbizText) {
		this.fbizText = fbizText;
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

	public Long getPrivateIp() {
		return privateIp;
	}

	public void setPrivateIp(Long privateIp) {
		this.privateIp = privateIp;
	}


    
}