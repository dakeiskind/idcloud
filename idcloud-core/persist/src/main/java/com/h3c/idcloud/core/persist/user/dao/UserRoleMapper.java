package com.h3c.idcloud.core.persist.user.dao;

import java.util.List;

import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.user.UserRole;
import org.springframework.stereotype.Repository;

/**
 * 用户角色映射
 * 
 * @author zharong
 */
@Repository
public interface UserRoleMapper {

	/**
	 * 根据条件删除记录
	 */
	int deleteByParams(Criteria condition);
	
	/**
	 * 保存属不为空的记
	 */
	int insertSelective(UserRole record);

	/**
	 * 根据条件查询记录
	 */
	List<UserRole> selectByParams(Criteria condition);
}