package com.h3c.idcloud.core.service.system.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.res.dao.ResHostMapper;
import com.h3c.idcloud.core.persist.system.dao.LicenseMapper;
import com.h3c.idcloud.core.pojo.dto.system.License;
import com.h3c.idcloud.core.pojo.instance.LicenseResult;
import com.h3c.idcloud.core.service.system.api.LicenseService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service(version = "1.0.0")
@Component
public class LicenseServiceImpl implements LicenseService {
    private static final Logger logger = LoggerFactory.getLogger(LicenseServiceImpl.class);
    @Autowired
    private LicenseMapper licenseMapper;
    @Autowired
    private ResHostMapper resHostMapper;

    @Override
    public String getLicenseNo() {
        String regisCode = "";
        String checkLicense = "";
        //获取license序列号
        License license = this.licenseMapper.selectByParam();
        if (null != license.getLicenseSerialno() && !"".equals(license.getLicenseSerialno())) {
            try {
                String regigtSerlNumWith = changeLocation(license.getLicenseSerialno());
                String regigtSerlNum = regigtSerlNumWith.replaceAll("-", "");
                //解密license
                checkLicense = getCpuidFromEncrypt(regigtSerlNum);
                regisCode = checkLicense;
            } catch (NullPointerException e) {
                logger.error("license序列号异常");
            }
        }
        return regisCode;
    }

    @Override
    public LicenseResult getHostMaxCount() {
        LicenseResult result = null;
        //获取license中最大host数量
        String regisCode = getLicenseNo();
        if (regisCode == "") {
            result = new LicenseResult(LicenseResult.INVALID, "请注册License序列号");
            return result;
        } else {
            String temp = regisCode.substring(24);
            String userMAXCount = temp.substring(0, temp.indexOf("F"));
            String maxCount = replaceStr(userMAXCount);
            int maxSize = Integer.parseInt(maxCount);
            //获取host中最大数量
            Criteria criteria = new Criteria();
            int hostSize = this.resHostMapper.countByParams(criteria);
            if (hostSize >= maxSize) {
                result = new LicenseResult(LicenseResult.INVALID, "所属主机数量已超过最大主机数量");
                return result;
            } else {
                result = new LicenseResult(LicenseResult.VALID, "所属主机数量未超过最大主机数量");
                return result;
            }
        }
    }

    @Override
    public LicenseResult getAndCheckLicense() {
        LicenseResult result = null;
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取license到期日期
        String regisCode = getLicenseNo();
        if ("".equals(regisCode)) {
            result = new LicenseResult(LicenseResult.INVALID, "请注册License序列号");
        } else {
            String serialNo = regisCode.substring(0, 16);
            String initRegisCode = getInitRegisCode(serialNo);
            String licenseSerialno = PropertiesUtil.getProperty("product.code");
            if (!licenseSerialno.trim().equals(initRegisCode.trim())) {
                result = new LicenseResult(LicenseResult.INVALID, "License序列号无效");
            } else {
                String delDateStr = regisCode.substring(16);
                String overDateTime = getOverDateLicense(delDateStr);
                String overDate = replaceStr(overDateTime);
                //获取系统当前日期
                String nowDate = sdf.format(new java.util.Date());
                try {
                    java.util.Date dt1 = sdf.parse(overDate);
                    java.util.Date dt2 = sdf.parse(nowDate);
                    if (dt1.getTime() > dt2.getTime()) {
                        result = new LicenseResult(LicenseResult.VALID, "License序列号未过期");
                    } else if (dt1.getTime() < dt2.getTime()) {
                        result = new LicenseResult(LicenseResult.INVALID, "License序列号已过期");
                    } else {
                        result = new LicenseResult(LicenseResult.VALID, "License序列号已到期");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public boolean checkLicense(String license) {
        //获取license序列号,解析license
        try {
            String regigtSerlNumWith = changeLocation(license);
            String regigtSerlNum = regigtSerlNumWith.replaceAll("-", "");
            //解密license
            String regisCode = getCpuidFromEncrypt(regigtSerlNum);
            return true;
        } catch (NullPointerException e) {
            logger.error("license序列号异常");
            return false;
        }
    }

    @Override
    public String getInitSerialno(String serialno) {
        String regisCode = "";
        String checkLicense = "";
        //获取license序列号
        try {
            String regigtSerlNumWith = changeLocation(serialno);
            String regigtSerlNum = regigtSerlNumWith.replaceAll("-", "");
            //解密license
            checkLicense = getCpuidFromEncrypt(regigtSerlNum);
            String hostlicense = checkLicense.substring(0, 16);
            String licenseno = this.getInitRegisCode(hostlicense);
            regisCode = licenseno;
        } catch (NullPointerException e) {
            logger.error("license序列号异常");
        }
        return regisCode;
    }

    /**
     * 解析license
     */
    public String changeLocation(String str) {
        String result = null;
        String rlt = null;
        try {
            String aa = str.replaceAll("-", "").substring(0, 36);
            int v = new Integer(str.substring(44)).intValue();
            char[] cary = aa.toCharArray();
            List<Character> list = new ArrayList<Character>();
            List<Character> listTo = new ArrayList<Character>();
            char[] caryTo = new char[36];
            for (int i = 0; i < cary.length; i++) {
                list.add(cary[i]);
            }
            for (int i = list.size() - 1; i >= 0; i--) {
                int to = 0;
                to = i ^ v;
                caryTo[to] = list.get(i);
            }
            for (int i = 0; i < caryTo.length; i++) {
                listTo.add(caryTo[i]);
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < listTo.size(); i++) {
                sb.append(listTo.get(i));
            }
            String after = sb.toString();
            String cut1 = after.substring(0, 14);
            String cut2 = after.substring(14, 24);
            String cut3 = after.substring(24);
            result = cut2 + cut1 + cut3;
            rlt = result.substring(0, 4) + "-" + result.substring(4, 8) + "-"
                    + result.substring(8, 12) + "-" + result.substring(12, 16)
                    + "-" + result.substring(16, 20) + "-"
                    + result.substring(20, 24) + "-" + result.substring(24, 28)
                    + "-" + result.substring(28, 32) + "-"
                    + result.substring(32, 36);
        } catch (Throwable e) {
            logger.error("license序列号异常");
        }
        return rlt;
    }

    /**
     * 获取初始化注册码
     *
     * @param encryptCpuid
     * @return
     */
    public String getInitRegisCode(String encryptCpuid) {

        char[] sAr = encryptCpuid.toCharArray();
        char[] sb = new char[24];
        StringBuffer sbfer = new StringBuffer();

        for (int i = sAr.length; i > 0; i--) {
            long serL = charToLong(sAr[i - 1]);
            long serDcd = serL;
            serDcd ^= 3 % 9;
            if (serDcd < 48L || (57L < serDcd & serDcd < 65L) || serDcd > 90) {
                serDcd = serL;
            }
            sb[i - 1] = longTochar(serDcd);
        }

        for (int j = 0; j < sb.length; j++) {
            sbfer.append(sb[j]);
        }
        return sbfer.toString();
    }

    public String getCpuidFromEncrypt(String encryptCpuid) {

        char[] sAr = encryptCpuid.toCharArray();
        char[] sb = new char[36];
        StringBuffer sbfer = new StringBuffer();

        for (int i = sAr.length; i > 0; i--) {
            long serL = charToLong(sAr[i - 1]);
            long serDcd = serL;
            serDcd ^= 5 % 9;
            if (serDcd < 48L || (57L < serDcd & serDcd < 65L) || serDcd > 90) {
                serDcd = serL;
            }
            sb[i - 1] = longTochar(serDcd);
        }

        for (int j = 0; j < sb.length; j++) {
            sbfer.append(sb[j]);
        }
        return sbfer.toString();
    }

    public long charToLong(char serChar) {
        int i = serChar;
        return new Integer(i).longValue();
    }

    public char longTochar(long serLong) {
        int i = (int) serLong;
        char car = (char) i;
        return car;

    }

    public String getOverDateLicense(String delDateStr) {
        String overData = delDateStr.substring(0, 4) + "-"
                + delDateStr.substring(4, 6) + "-" + delDateStr.substring(6, 8);
        return overData;
    }

    private String replaceStr(String result) {
        result = result.replace("H", "0");
        result = result.replace("Y", "1");
        result = result.replace("M", "2");
        result = result.replace("I", "3");
        result = result.replace("Q", "4");
        result = result.replace("#", "5");
        result = result.replace("P", "6");
        result = result.replace("!", "7");
        result = result.replace("&", "8");
        result = result.replace("B", "9");
        return result;
    }


}