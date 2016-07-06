package com.h3c.idcloud.core.persist.user.dao;

import java.util.List;

import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.user.RoleModule;
import org.springframework.stereotype.Repository;


/**
 * 角色功能模块Mapper
 * 
 * @author zharong
 */
@Repository
public interface RoleModuleMapper {

	/**
	 * 根据条件删除记录
	 */
	int deleteByParams(Criteria condition);

	/**
	 * 保存属不为空的记
	 */
	int insertSelective(RoleModule record);

	/**
	 * 根据条件查询记录
	 */
	List<RoleModule> selectByParams(Criteria condition);

}