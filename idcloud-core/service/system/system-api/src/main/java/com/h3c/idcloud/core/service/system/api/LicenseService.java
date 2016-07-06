package com.h3c.idcloud.core.service.system.api;


import com.h3c.idcloud.core.pojo.instance.LicenseResult;

public interface LicenseService {
	
    /**
     * 判断host数量是否超过license指定数量
     * 超过：false + 返回message
     * 没超过：true + 返回message
     * 接口用
     */
    public LicenseResult getHostMaxCount();
    
    /**
     * 判断license是否有效，通过到期日期判断
     * 接口和后台公用
     */
    public LicenseResult getAndCheckLicense();
    
    /**
     * 解析license序列号
     * 
     */
    public String getLicenseNo();
    
    /**
     * 检测license格式是否正确，防止用户注册时候license序列号不全，后台无法解析
     * 此方法注册时用
     */
    public boolean checkLicense(String license);
    
    /**
     * 获取初始注册码
     */
    public String getInitSerialno(String serialno);
    
}