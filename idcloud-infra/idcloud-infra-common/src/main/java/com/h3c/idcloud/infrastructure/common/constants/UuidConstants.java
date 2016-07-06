package com.h3c.idcloud.infrastructure.common.constants;

/**
 * UUID常量类
 *
 * @author 刘洋
 */
public interface UuidConstants {


    /** UUID前缀 */
    public interface PrefixCode {

        /** 云主机 */
        String CS = "cs-";

        /** 块存储 */
        String CBS = "cbs-";

        /** SSH密钥对 */
        String KEYPAIR = "sk-";

        /** 弹性IP */
        String EIP = "eip-";

        /** 私有网络 */
        String VPC = "vpc-";

        /** 负责均衡 */
        String LOADBALANCE = "lb-";
    }
}
