package com.h3c.idcloud.core.service.user.api;

import com.h3c.idcloud.core.pojo.dto.user.UserRole;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * 用户角色关联Service
 * 
 * @author zharong
 */
public interface UserRoleService {

	/**
	 * 条件查询
	 * 
	 * @param condition
	 * @return
	 */
	List<UserRole> selectByParams(Criteria condition);

	/**
	 * 删除用户和角色对应关�?
	 * 
	 * @param criteria
	 * @return 00：失败，01：成�? ,其他情况
	 */
	String deleteByParams(Criteria criteria);

	/**
	 * 删除当前页的用户角色对应关系并保存新的关�?
	 * 
	 * @param userSid
	 * @param roleSidStr
	 * @param allRoleSid
	 * @return true : 执行成功; false : 执行失败
	 */
	boolean deleteAndsaveUserRole(Long userSid, String roleSidStr, String allRoleSid);
	
	/**
	 * 新增用户
	 * 
	 * @param record
	 * @return
	 */
	int insertSelective(UserRole record);

}