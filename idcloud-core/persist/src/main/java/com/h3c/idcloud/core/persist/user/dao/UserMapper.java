package com.h3c.idcloud.core.persist.user.dao;

import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByParams(Criteria example);

    /**
     * 根据条件删除记录
     */
    int deleteByParams(Criteria example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long userSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(User record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(User record);

    /**
     * 根据条件查询记录集
     */
    List<User> selectByParams(Criteria example);
    /**
     * 根据租户Sid查询租户用户列表
     */
    List<User> selectTUserByTenantSid(Criteria example);

    /**
     * 根据产品经理Sid查询成员列表
     */
    List<User> selectUserByProjectSid(Criteria example);

    /**
     * 根据主键查询记录
     */
    User selectByPrimaryKey(Long userSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") User record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") User record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(User record);

    /**
     * 根据tenantSid查询所有租户管理员
     */
    List<User> selectEmailByServiceInstanceSid(Long serviceInstanceSid);

    /**
     * 根据租户sid和服务sid查询user实例
     */
    List<User> selectAllocatedUserByParams(Criteria example);

    /**
     * 查询出角色下的所有用户
     * @param roles
     * @return
     */
    List<User> selectUserByRoles(String roles);

    List<User> selectBizByAccount(String account);

    List<User> selectIdcUser(Criteria example);

    List<User> findUsersByMgtObjSid(Criteria example);

    List<User> findUsersByProjectMaster(Criteria example);

    List<User> findAllUsersByMgtObj(Criteria criteria);
}