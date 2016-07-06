package com.h3c.idcloud.core.service.user.api;


import com.h3c.idcloud.core.pojo.dto.user.RoleModule;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * 角色功能菜单Service
 * 
 * @author zharong
 */
public interface RoleModuleService {

	/**
	 * 通过条件查询有关联表内容
	 * 
	 * @param condition
	 * @return 
	 */
	List<RoleModule> selectByParams(Criteria condition);
	
	/**
	 * 通过条件删除有关联数
	 * 
	 * @param example
	 * @return 00：失败，01：成 ,其他情况
	 */
	String deleteByParams(Criteria example);
	
	/**
	 * 保存role与module关系
	 * 
	 * @param list
	 * @return false：失败，true：成 
	 */
	boolean saveRoleModule(List<RoleModule> list);
	
}