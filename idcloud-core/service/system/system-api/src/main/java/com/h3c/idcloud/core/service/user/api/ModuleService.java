package com.h3c.idcloud.core.service.user.api;

import com.h3c.idcloud.core.pojo.dto.user.Module;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.vo.user.UserVo;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface ModuleService {

    /**
     * 根据用户SID查询可用的功能模块
     *
     * @param userSid
     * @return
     */
    List<Module> selectByUserSid(Long userSid, Integer systemNum);

    int countByParams(Criteria example);

    Module selectByPrimaryKey(String moduleSid);

    List<Module> selectByParams(Criteria example);

    int deleteByPrimaryKey(String moduleSid);

    int updateByPrimaryKeySelective(Module record);

    int updateByPrimaryKey(Module record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Module record, Criteria example);

    int updateByParams(Module record, Criteria example);

    int insert(Module record);

    int insertSelective(Module record);
}
