package com.h3c.idcloud.core.service.user.provider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.user.dao.UserRoleMapper;
import com.h3c.idcloud.core.pojo.dto.user.UserRole;
import com.h3c.idcloud.core.service.user.api.UserRoleService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户角色关联ServiceImpl
 * 
 * @author zharong
 */
@Service(version = "1.0.0")
@Component
public class UserRoleServiceImpl implements UserRoleService {
	
	/** 用户角色Mapper */
	@Autowired
	private UserRoleMapper userRoleMapper;

	// 日志, 使用时开启注�?
	private static final Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);

	/**
	 * 条件查询
	 * 
	 * @param condition
	 * @return 
	 */
	@Override
	public List<UserRole> selectByParams(Criteria condition) {
		return this.userRoleMapper.selectByParams(condition);
	}

	/**
	 * 删除用户和角色对应关�?
	 * 
	 * @param example
	 * @return 00：失败，01：成�? ,其他情况
	 */
	@Override
	public String deleteByParams(Criteria example) {
		int result = 0;
		result = this.userRoleMapper.deleteByParams(example);
		return result > 0 ? "01" : "00";
	}
	
	/**
	 * 删除当前页的用户角色对应关系并保存新的关�?
	 * 
	 * @param userSid
	 * @param roleSidStr
	 * @param allRoleSid
	 * @return true : 执行成功; false : 执行失败
	 */
	@Override
	public boolean deleteAndsaveUserRole(Long userSid, String roleSidStr, String allRoleSid) {
		
		Criteria criteria = new Criteria();
		
		// 将当前页�?有角色主键拆分为数组
		String[] ids = allRoleSid.split(",");
		int count = ids.length;
		// 遍历每条并删除其和用户的对应关系
		for (int i = 0; i < count; i++) {
			// 根据用户主键和当前条角色主键删除 用户与角色关�?
			criteria.put("userSid", userSid);
			criteria.put("roleSid", ids[i]);
			this.deleteByParams(criteria);
			criteria.clear();
		}

		if (roleSidStr != null && "".equals(roleSidStr) == false) {

			// 将roleSidStr字符串转为Integer插入对象列表�?
			List<UserRole> userRoles = new ArrayList<UserRole>();
			ids = roleSidStr.split(",");
			count = ids.length;
			for (int i = 0; i < count; i++) {
				UserRole userRole = new UserRole();
				userRole.setUserSid(Long.valueOf(userSid));
				userRole.setRoleSid(Long.valueOf(ids[i]));
				userRoles.add(userRole);
			}

			try {
				// 遍历userRoles并新的用户与角色对应数据插入数据库内
				for (UserRole userRole : userRoles) {
					this.userRoleMapper.insertSelective(userRole);
				}
			} catch (Exception e) {
				logger.error("Exception: ", e);
				if (e instanceof SQLException) {
					SQLException sqlEx = (SQLException) e;
					if (sqlEx.getErrorCode() == 1) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 新增用户
	 */
	@Override
	public int insertSelective(UserRole record) {
		return this.userRoleMapper.insertSelective(record);
	}

}