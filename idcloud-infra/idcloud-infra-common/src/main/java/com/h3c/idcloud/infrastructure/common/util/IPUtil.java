package com.h3c.idcloud.infrastructure.common.util;

import com.h3c.idcloud.infrastructure.common.pojo.Nets;

/**
 * IP相关信息计算工具类
 *
 * @author 刘洋
 */
public class IPUtil {
/*	public static void main(String[] args) {
        System.out.println(getNetMask("255.255.255.0"));

		System.out.println(getPoolMax(getNetMask("255.255.255.128")));

		Nets aaa = getNets("10.229.1.1", 24);
		System.out.println(aaa.getStartIP());
		System.out.println(aaa.getEndIP());

		Nets bbb = getNets("10.229.1.1", "255.255.255.0");
		System.out.println(bbb.getStartIP());
		System.out.println(bbb.getEndIP());
	}*/

    /**
     * 根据起始IP地址和子网掩码计算终止IP
     */
    public static Nets getNets(String startIP, int netmask) {
        return getNets(startIP, getMask(netmask));
    }

    /**
     * 根据起始IP地址和子网掩码计算终止IP
     */
    public static Nets getNets(String startIP, String netmask) {
        Nets nets = new Nets();
        String[] start = Negation(startIP, netmask).split("\\.");
        nets.setStartIP(start[0] + "." + start[1] + "." + start[2] + "." + (Integer.valueOf(start[3]) + 1));
        nets.setEndIP(TaskOR(Negation(startIP, netmask), netmask));
        nets.setNetMask(netmask);
        return nets;
    }

    /**
     * 根据掩码位计算掩码
     */
    public static String getMask(int masks) {
        if (masks == 1)
            return "128.0.0.0";
        else if (masks == 2)
            return "192.0.0.0";
        else if (masks == 3)
            return "224.0.0.0";
        else if (masks == 4)
            return "240.0.0.0";
        else if (masks == 5)
            return "248.0.0.0";
        else if (masks == 6)
            return "252.0.0.0";
        else if (masks == 7)
            return "254.0.0.0";
        else if (masks == 8)
            return "255.0.0.0";
        else if (masks == 9)
            return "255.128.0.0";
        else if (masks == 10)
            return "255.192.0.0";
        else if (masks == 11)
            return "255.224.0.0";
        else if (masks == 12)
            return "255.240.0.0";
        else if (masks == 13)
            return "255.248.0.0";
        else if (masks == 14)
            return "255.252.0.0";
        else if (masks == 15)
            return "255.254.0.0";
        else if (masks == 16)
            return "255.255.0.0";
        else if (masks == 17)
            return "255.255.128.0";
        else if (masks == 18)
            return "255.255.192.0";
        else if (masks == 19)
            return "255.255.224.0";
        else if (masks == 20)
            return "255.255.240.0";
        else if (masks == 21)
            return "255.255.248.0";
        else if (masks == 22)
            return "255.255.252.0";
        else if (masks == 23)
            return "255.255.254.0";
        else if (masks == 24)
            return "255.255.255.0";
        else if (masks == 25)
            return "255.255.255.128";
        else if (masks == 26)
            return "255.255.255.192";
        else if (masks == 27)
            return "255.255.255.224";
        else if (masks == 28)
            return "255.255.255.240";
        else if (masks == 29)
            return "255.255.255.248";
        else if (masks == 30)
            return "255.255.255.252";
        else if (masks == 31)
            return "255.255.255.254";
        else if (masks == 32)
            return "255.255.255.255";
        return "";
    }

    /**
     * 根据掩码位计算掩码
     */
    public static int getStandardMask(String masks) {
        if ("128.0.0.0".equals(masks))
            return 1;
        else if ("192.0.0.0".equals(masks))
            return 2;
        else if ("224.0.0.0".equals(masks))
            return 3;
        else if ("240.0.0.0".equals(masks))
            return 4;
        else if ("248.0.0.0".equals(masks))
            return 5;
        else if ("252.0.0.0".equals(masks))
            return 6;
        else if ("254.0.0.0".equals(masks))
            return 7;
        else if ("255.0.0.0".equals(masks))
            return 8;
        else if ("255.128.0.0".equals(masks))
            return 9;
        else if ("255.192.0.0".equals(masks))
            return 10;
        else if ("255.224.0.0".equals(masks))
            return 11;
        else if ("255.240.0.0".equals(masks))
            return 12;
        else if ("255.248.0.0".equals(masks))
            return 13;
        else if ("255.252.0.0".equals(masks))
            return 14;
        else if ("255.254.0.0".equals(masks))
            return 15;
        else if ("255.255.0.0".equals(masks))
            return 16;
        else if ("255.255.128.0".equals(masks))
            return 17;
        else if ("255.255.192.0".equals(masks))
            return 18;
        else if ("255.255.224.0".equals(masks))
            return 19;
        else if ("255.255.240.0".equals(masks))
            return 20;
        else if ("255.255.248.0".equals(masks))
            return 21;
        else if ("255.255.252.0".equals(masks))
            return 22;
        else if ("255.255.254.0".equals(masks))
            return 23;
        else if ("255.255.255.0".equals(masks))
            return 24;
        else if ("255.255.255.128".equals(masks))
            return 25;
        else if ("255.255.255.192".equals(masks))
            return 26;
        else if ("255.255.255.224".equals(masks))
            return 27;
        else if ("255.255.255.240".equals(masks))
            return 28;
        else if ("255.255.255.248".equals(masks))
            return 29;
        else if ("255.255.255.252".equals(masks))
            return 30;
        else if ("255.255.255.254".equals(masks))
            return 31;
        else if ("255.255.255.255".equals(masks))
            return 32;
        return 0;
    }

    /**
     * temp1根据temp2取反
     */
    private static String Negation(String StartIP, String netmask) {
        String[] temp1 = StartIP.trim().split("\\.");
        String[] temp2 = netmask.trim().split("\\.");
        int[] rets = new int[4];
        for (int i = 0; i < 4; i++) {
            rets[i] = Integer.parseInt(temp1[i]) & Integer.parseInt(temp2[i]);
        }
        return rets[0] + "." + rets[1] + "." + rets[2] + "." + rets[3];
    }

    /**
     * temp1根据temp2取或
     */
    private static String TaskOR(String StartIP, String netmask) {
        String[] temp1 = StartIP.trim().split("\\.");
        String[] temp2 = netmask.trim().split("\\.");
        int[] rets = new int[4];
        for (int i = 0; i < 4; i++) {
            rets[i] = 255 - (Integer.parseInt(temp1[i]) ^ Integer.parseInt(temp2[i]));
        }
        return rets[0] + "." + rets[1] + "." + rets[2] + "." + (rets[3] - 1);
    }

    /**
     * 计算子网大小
     */
    public static int getPoolMax(int netmask) {
        if (netmask <= 0 || netmask >= 32) {
            return 0;
        }
        int bits = 32 - netmask;
        return (int) Math.pow(2, bits) - 2;
    }

    /**
     * 转换为掩码位数
     */
    public static int getNetMask(String netmarks) {
        StringBuffer sbf;
        String str;
        int inetmask = 0, count = 0;
        String[] ipList = netmarks.split("\\.");
        for (int n = 0; n < ipList.length; n++) {
            sbf = toBin(Integer.parseInt(ipList[n]));
            str = sbf.reverse().toString();
            count = 0;
            for (int i = 0; i < str.length(); i++) {
                i = str.indexOf('1', i);
                if (i == -1) {
                    break;
                }
                count++;
            }
            inetmask += count;
        }
        return inetmask;
    }

    private static StringBuffer toBin(int x) {
        StringBuffer result = new StringBuffer();
        result.append(x % 2);
        x /= 2;
        while (x > 0) {
            result.append(x % 2);
            x /= 2;
        }
        return result;
    }
}
