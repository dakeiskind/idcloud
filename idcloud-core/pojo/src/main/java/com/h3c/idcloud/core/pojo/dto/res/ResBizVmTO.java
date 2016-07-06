package com.h3c.idcloud.core.pojo.dto.res;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResBizVmTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1871502215511738616L;

	public String getOrgSid() {
		return orgSid;
	}

	public void setOrgSid(String orgSid) {
		this.orgSid = orgSid;
	}


	public List<ResBizVm> getResBizVmList() {
		return resBizVmList;
	}

	public void setResBizVmList(List<ResBizVm> resBizVmList) {
		this.resBizVmList = resBizVmList;
	}


	private String orgSid;
	
	private String account;
	
	private List<ResBizVm> resBizVmList;
	private List<ResVm> resVmList;
	
	private Long bizSid;
	
	private String createDate;
	
	
//	public ResBizVmTO(String orgSid, String account, List<ResBizVm> resBizVmList){
//		this.orgSid = orgSid;
//		this.account = account;
//		this.resBizVmList = resBizVmList;
//	}

	public ResBizVmTO(){
		
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getBizSid() {
		return bizSid;
	}

	public void setBizSid(Long bizSid) {
		this.bizSid = bizSid;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public List<ResVm> getResVmList() {
		return resVmList;
	}

	public void setResVmList(List<ResVm> resVmList) {
		this.resVmList = resVmList;
	}

	public static void main(String args[]){

        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String str = df.format(date);
        System.out.println(str);
        
    }
}