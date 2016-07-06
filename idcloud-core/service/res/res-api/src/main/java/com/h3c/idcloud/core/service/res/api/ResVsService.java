package com.h3c.idcloud.core.service.res.api;

/**
 * 交换机接口.
 */
public interface ResVsService {

    /**
     * 单个交换机同步
     *
     * @param resVsSid 交换机Sid
     *
     * @return boolean
     */
    boolean findAllByVs(String resVsSid);

}