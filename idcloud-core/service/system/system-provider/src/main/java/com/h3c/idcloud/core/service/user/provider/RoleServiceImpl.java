package com.h3c.idcloud.core.service.user.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.user.dao.RoleMapper;
import com.h3c.idcloud.core.persist.user.dao.RoleModuleMapper;
import com.h3c.idcloud.core.pojo.dto.user.Role;
import com.h3c.idcloud.core.pojo.dto.user.RoleModule;
import com.h3c.idcloud.core.service.user.api.RoleService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service(version = "1.0.0")
@Component
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleModuleMapper roleModuleMapper;

    public int countByParams(Criteria example) {
        int count = this.roleMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Role selectByPrimaryKey(Long roleSid) {
        return this.roleMapper.selectByPrimaryKey(roleSid);
    }

    /**
     * 根据用户主键查询用户拥有的角�?
     *
     * @param userSid 用户主键
     * @return List<Role> 角色列表
     */
    public List<Role> selectRoleByUserSid(Long userSid) {
        Criteria criteria = new Criteria();
        criteria.put("userSid", userSid);
        return this.roleMapper.selectByParams(criteria);
    }

    public List<Role> selectByParams(Criteria example) {
        return this.roleMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long roleSid) {
        int result = 0;
        try {
            //删除角色
            this.roleMapper.deleteByPrimaryKey(roleSid);
            //删除角色对应的模块
            Criteria criteria = new Criteria();
            criteria.put("roleSid", roleSid);
            this.roleModuleMapper.deleteByParams(criteria);
            result = 1;
        }catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }
        return result;
//        return this.roleMapper.deleteByPrimaryKey(roleSid);
    }

    public int updateByPrimaryKeySelective(Role record) {
        int result = 0;
        try {
            //修改角色信息
            WebUtil.prepareUpdateParams(record);
            this.roleMapper.updateByPrimaryKeySelective(record);
            //删除角色对应的模块
            Criteria criteria = new Criteria();
            criteria.put("roleSid", record.getRoleSid());
            int delcount = this.roleModuleMapper.deleteByParams(criteria);
            //添加角色对应的模块
            if(!StringUtil.isNullOrEmpty(record.getModuleIds())){
                String moduleIds = record.getModuleIds();
                String modAry[] = moduleIds.split(",");
                RoleModule roleModule = new RoleModule();
                roleModule.setRoleSid(record.getRoleSid());
                for(int i=0;i<modAry.length;i++){
                    if(!StringUtil.isNullOrEmpty(modAry[i])){
                        roleModule.setModuleSid(modAry[i]);
                        this.roleModuleMapper.insertSelective(roleModule);
                    }
                }
            }
            result = 1;
        }catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }
        return result;
//        return this.roleMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Role record) {
        return this.roleMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.roleMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Role record, Criteria example) {
        return this.roleMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Role record, Criteria example) {
        return this.roleMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Role record) {return this.roleMapper.insert(record);}

    public int insertSelective(Role record) {
        int result = 0;
        try {
            //添加角色信息
            record.setStatus("0");
            WebUtil.prepareInsertParams(record);
            this.roleMapper.insertSelective(record);
            //添加角色模版信息
            if(!StringUtil.isNullOrEmpty(record.getModuleIds())){
                String moduleIds = record.getModuleIds();
                String modAry[] = moduleIds.split(",");
                RoleModule roleModule = new RoleModule();
                roleModule.setRoleSid(record.getRoleSid());
                for(int i=0;i<modAry.length;i++){
                    if(!StringUtil.isNullOrEmpty(modAry[i])){
                        roleModule.setModuleSid(modAry[i]);
                        this.roleModuleMapper.insertSelective(roleModule);
                    }
                }
            }
            result = 1;
        }catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }
        return result;
    }
}