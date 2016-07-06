package com.h3c.idcloud.core.service.system.api;

import com.h3c.idcloud.core.pojo.dto.system.UserMgtObjKey;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;



public interface UserMgtObjService {
    int countByParams(Criteria example);

//    UserMgtObjKey selectByPrimaryKey(UserMgtObjKey key);

    List<UserMgtObjKey> selectByParams(Criteria example);

    int deleteByPrimaryKey(UserMgtObjKey key);

    int updateByPrimaryKeySelective(UserMgtObjKey record);
//
//    int updateByPrimaryKey(UserMgtObjKey record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(UserMgtObjKey record, Criteria example);

    int updateByParams(UserMgtObjKey record, Criteria example);

    int insert(UserMgtObjKey record);

    int insertSelective(UserMgtObjKey record);
}