package com.hptsic.cloud.monitor.common;

import java.util.UUID;

public class Base {

	private String providerType;
	private String authUrl;
	private String providerUrl;//pointPort url 
	private String authUser;//��֤�û���
	private String authPass;//��֤�û�����
	private String authTenant;//��֤����Ա
	private String tenantId;
	private String tenantName;//�⻧��
	private String tenantUserName;//�⻧�û���
	private String tenantUserPass;//�⻧�û�����
	private String virtEnvType;
	private String virtEnvUuid;
	private boolean retrieveAll;
	public boolean isRetrieveAll() {
		return retrieveAll;
	}

	public void setRetrieveAll(boolean retrieveAll) {
		this.retrieveAll = retrieveAll;
	}

	private String msgId = UUID.randomUUID().toString();

	public String getTenantUserName() {
		return tenantUserName;
	}

	public void setTenantUserName(String tenantUserName) {
		this.tenantUserName = tenantUserName;
	}

	public String getTenantUserPass() {
		return tenantUserPass;
	}

	public void setTenantUserPass(String tenantUserPass) {
		this.tenantUserPass = tenantUserPass;
	}

	public String getAuthTenant() {
		return authTenant;
	}

	public void setAuthTenant(String authTenant) {
		this.authTenant = authTenant;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getVirtEnvType() {
		return virtEnvType;
	}

	public void setVirtEnvType(String virtEnvType) {
		this.virtEnvType = virtEnvType;
	}

	public String getVirtEnvUuid() {
		return virtEnvUuid;
	}

	public void setVirtEnvUuid(String virtEnvUuid) {
		this.virtEnvUuid = virtEnvUuid;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public String getProviderUrl() {
		return providerUrl;
	}

	public void setProviderUrl(String providerUrl) {
		this.providerUrl = providerUrl;
	}

	public String getAuthUrl() {
		return authUrl;
	}

	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}

	public String getAuthUser() {
		return authUser;
	}

	public void setAuthUser(String authUser) {
		this.authUser = authUser;
	}

	public String getAuthPass() {
		return authPass;
	}

	public void setAuthPass(String authPass) {
		this.authPass = authPass;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
		//this.tenantId = "81e043e3856442d89fa7813cea57c530";
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

}
