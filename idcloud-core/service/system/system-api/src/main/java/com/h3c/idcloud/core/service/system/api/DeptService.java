package com.h3c.idcloud.core.service.system.api;

import com.h3c.idcloud.core.pojo.dto.system.Biz;
import com.h3c.idcloud.core.pojo.dto.system.Dept;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;



public interface DeptService {
    List<Dept> selectByParams(Criteria criteria);
    List<Biz> selectByParamsBiz(Criteria criteria);
    Biz selectByPrimaryKeyBizFull(Long bizSid);
}
