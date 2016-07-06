package com.h3c.idcloud.core.service.res.api;


import com.h3c.idcloud.core.pojo.dto.res.ResOSUsageSum;
import com.h3c.idcloud.core.pojo.dto.res.ResObjectStorageUsage;

import java.util.Date;
import java.util.List;

/**
 * 接口 对象存储
 *
 * @author Chaohong.Mao
 */
public interface ResObjectStorageUsageService {

    /**
     * 扫描对象存储使用量
     *
     * @param retrieveAll 是否扫描全部租户
     */
    void scanUsage(boolean retrieveAll);

    /**
     * 获取对象存储使用量
     *
     * @param tenantId 租户id
     * @param begin    开始时间
     * @param end      结束时间
     *
     * @return usage
     */
    List<ResObjectStorageUsage> getUsage(long tenantId, Date begin, Date end);

    /**
     * 获取对象存储使用量统计信息
     *
     * @param tenantId 租户id
     * @param begin    开始时间
     * @param end      结束时间
     *
     * @return usage sum
     */
    ResOSUsageSum getUsageSum(long tenantId, Date begin, Date end);
}