package com.h3c.idcloud.core.service.user.provider;

import java.sql.SQLException;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.user.dao.RoleModuleMapper;
import com.h3c.idcloud.core.pojo.dto.user.RoleModule;
import com.h3c.idcloud.core.service.user.api.RoleModuleService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 角色功能菜单ServiceImpl
 * 
 * @author zharong
 */
@Service(version = "1.0.0")
@Component
public class RoleModuleServiceImpl implements RoleModuleService {
	
	// 日志,使用时开启注�?
	private static final Logger logger = LoggerFactory.getLogger(RoleModuleServiceImpl.class);

	/** 角色功能菜单Mapper */
	@Autowired
	private RoleModuleMapper roleModuleMapper;

	/**
	 * 通过条件查询�?有关联表内容
	 * 
	 * @param condition
	 * @return 
	 */
	@Override
	public List<RoleModule> selectByParams(Criteria condition) {
		return this.roleModuleMapper.selectByParams(condition);
	}

	/**
	 * 通过条件删除�?有关联数�?
	 * 
	 * @param example
	 * @return 00：失败，01：成�? ,其他情况
	 */
	@Override
	public String deleteByParams(Criteria example) {
		int result = 0;
		result = this.roleModuleMapper.deleteByParams(example);
		return result > 0 ? "01" : "00";
	}

	/**
	 * 保存role与module关系
	 * 
	 * @param list
	 * @return false：失败，true：成�? 
	 */
	@Override
	public boolean saveRoleModule(List<RoleModule> list) {
		Criteria criteria = new Criteria();
		// 将数据库中此角色关联的功能菜单项全部删除
		criteria.put("roleSid", list.get(0).getRoleSid());
		this.deleteByParams(criteria);
		
		if (!StringUtil.isNullOrEmpty(list.get(0).getModuleSid())) {
			// 不为空，插入数据�?
			try {
				// 遍历roleModules并将角色和功能菜单对应关联插入数据库
				for (RoleModule roleModule : list) {
					this.roleModuleMapper.insertSelective(roleModule);
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
	
}