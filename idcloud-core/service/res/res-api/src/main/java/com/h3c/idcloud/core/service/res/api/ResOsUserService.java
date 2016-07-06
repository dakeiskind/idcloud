package com.h3c.idcloud.core.service.res.api;

import com.h3c.idcloud.core.pojo.dto.res.ResOsUser;

/**
 * 接口 系统用户.
 *
 * @author Chaohong.Mao
 */
public interface ResOsUserService {

    /**
     * 插入OS用户.
     *
     * @param record OS用户记录
     *
     * @return the int
     */
    int insertSelective(ResOsUser record);
}