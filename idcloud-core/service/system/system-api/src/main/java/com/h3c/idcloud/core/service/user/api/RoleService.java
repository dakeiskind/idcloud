package com.h3c.idcloud.core.service.user.api;

import com.h3c.idcloud.core.pojo.dto.user.Role;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * 角色Service
 * 
 * @author zharong
 */
public interface RoleService {
    int countByParams(Criteria example);

    Role selectByPrimaryKey(Long roleSid);

    List<Role> selectByParams(Criteria example);
    
    /**
     * 根据用户主键查询用户拥有的角�?
     * 
     * @param userSid 用户主键
     * @return List<Role> 角色列表
     */
    List<Role> selectRoleByUserSid(Long userSid);

    int deleteByPrimaryKey(Long roleSid);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Role record, Criteria example);

    int updateByParams(Role record, Criteria example);

    int insert(Role record);

    int insertSelective(Role record);
}