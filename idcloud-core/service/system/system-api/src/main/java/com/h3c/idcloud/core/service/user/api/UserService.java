package com.h3c.idcloud.core.service.user.api;

import com.h3c.idcloud.core.pojo.dto.security.UserToken;
import com.h3c.idcloud.core.pojo.dto.system.MgtObjExt;
import com.h3c.idcloud.core.pojo.dto.user.SystemMessage;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.infrastructure.common.exception.ServiceException;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

import com.h3c.idcloud.core.pojo.vo.user.UserVo;

/**
 * 用户管理Service Created by qct on 2016/1/11.
 */
public interface UserService {

    /**
     * 根据ID查找用户
     *
     * @param id 用户ID
     * @return 数据库用户信息
     */
    UserVo queryUserByIdSwq(Long id);

    User queryUserById(Long id);

    /**
     * /** 执行用户登录
     *
     * @param criteria 条件
     * @return 00：失败，01：成功 ,其他情况
     */
    String executeLogin(Criteria criteria);

    /**
     * 查询数据总行数
     *
     * @param example 查询条件
     * @return 行数
     */
    int countByParams(Criteria example);

    /**
     * 通过主键查询用户
     *
     * @param userSid 用户主键
     * @return 用户管理对象
     */
    User selectByPrimaryKey(Long userSid, Integer moduleCategory);

    User selectByPrimaryKey2(Long userSid);

    /**
     * 根据条件查询用户集合
     *
     * @param example 查询条件
     * @return 用户List
     */
    List<User> selectByParams(Criteria example);

    /**
     * 根据租户Sid查询租户用户列表
     *
     * @param example 查询条件
     * @return 用户List
     */
    List<User> selectTUserByTenantSid(Criteria example);

    /**
     * 根据产品Sid查询成员列表
     *
     * @param example 查询条件
     * @return 用户List
     */
    List<User> selectUserByProjectSid(Criteria example);

    /**
     * 根据用户主键删除用户
     *
     * @param userSid 用户主键
     * @return 执行条数
     */
    int deleteByPrimaryKey(Long userSid);

    /**
     * 根据条件更新用户
     *
     * @param record 用户管理
     * @return 执行条数
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 根据条件更新用户密码
     *
     * @param record 用户管理
     * @return 执行条数
     */
    int updatePasswordByPrimaryKey(User record);

    /**
     * 根据条件更新用户，不调用后台
     *
     * @param record 用户管理
     * @return 执行条数
     */
    int updateByPrimaryKeySelective2(User record);

    /**
     * 根据条件更新用户，不调用后台
     *
     * @return 执行条数
     */
    int updateByPrimaryKeySelective3(User user ,List<MgtObjExt> mgtObjExt);

    /**
     * 插入用户信息
     *
     * @param record 用户信息
     * @return 执行条数
     */
    int insert(User record);

    /**
     * 选择性插入用户信息
     *
     * @param record 用户信息
     * @return 执行条数
     */
    int insertSelective(User record);

    /**
     * 通过服务实例主键查询用户邮箱地址
     *
     * @param serviceInstanceSid 服务实例主键
     * @return 用户邮箱列表
     */
    List<User> selectEmailByServiceInstanceSid(Long serviceInstanceSid);

    /**
     * 获取用户状态
     *
     * @param userSid 用户主键
     * @return "0"禁用;"1"启用
     */
    String getUserStatus(Long userSid);

    /**
     * 前台删除用户
     */
    boolean deleteUser(Long userSid);

    /**
     * 前台新增用户
     */
    boolean insertUser(User user);

    /**
     * 后台新增用户
     */
    int insertPlatformUser(User user);

    /**
     * 后台更新用户
     */
    boolean updatePlatformUser(User user);

    /**
     * 注册用户
     *
     * @return 0：成功；1：失败；2：用户名重复；3：租户名重复
     */
    int addRegisterUser(User user);

    /**
     * 用户信息注册
     */
    void registerProtalUser(String params);

    /**
     * 根据租户sid和服务sid查询user实例
     */
    List<User> selectAllocatedUserByParams(Criteria example);

    /**
     * 查询出角色下的的所有用户对象
     *
     * @param roles 角色字符串 exp. 201,202
     */
    List<User> selectUserByRoles(String roles);

    List<User> selectBizByAccount(String account);

    /**
     * 根据条件查询用户集合
     *
     * @param example 查询条件
     * @return 用户List
     */
    List<User> selectIdcUser(Criteria example);

    boolean insertTenantUser(String params);

    boolean insertRegisterUser(User user);

    List<User> findUsersByMgtObjSid(Criteria example);

    List<User> findUsersByProjectMaster(Criteria example);

    List<User> findAllUsersByMgtObj(Criteria criteria);


    /**
     * @return token
     */
    UserToken login(User user);
}
