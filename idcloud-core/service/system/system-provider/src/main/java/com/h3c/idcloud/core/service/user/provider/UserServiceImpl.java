package com.h3c.idcloud.core.service.user.provider;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.adapter.core.MQException;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.persist.charge.dao.BillingAccountMapper;
import com.h3c.idcloud.core.persist.charge.dao.BillingAccountMgtObjMapper;
import com.h3c.idcloud.core.persist.marketing.dao.AccountLevelMapper;
import com.h3c.idcloud.core.persist.security.dao.UserTokenMapper;
import com.h3c.idcloud.core.persist.system.dao.BizMapper;
import com.h3c.idcloud.core.persist.system.dao.MgtObjExtMapper;
import com.h3c.idcloud.core.persist.system.dao.OrgbizMapper;
import com.h3c.idcloud.core.persist.user.dao.UserMapper;
import com.h3c.idcloud.core.persist.user.dao.UserRoleMapper;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccount;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccountMgtObj;
import com.h3c.idcloud.core.pojo.dto.marketing.AccountLevel;
import com.h3c.idcloud.core.pojo.dto.product.MgtObjRes;
import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.security.UserToken;
import com.h3c.idcloud.core.pojo.dto.system.Biz;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.system.MgtObjExt;
import com.h3c.idcloud.core.pojo.dto.system.Orgbiz;
import com.h3c.idcloud.core.pojo.dto.system.SysTLogRecord;
import com.h3c.idcloud.core.pojo.dto.system.Topic;
import com.h3c.idcloud.core.pojo.dto.system.UserMgtObjKey;
import com.h3c.idcloud.core.pojo.dto.system.UserTopic;
import com.h3c.idcloud.core.pojo.dto.user.Module;
import com.h3c.idcloud.core.pojo.dto.user.Role;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.dto.user.UserRole;
import com.h3c.idcloud.core.pojo.vo.user.UserVo;
import com.h3c.idcloud.core.service.product.api.MgtObjResService;
import com.h3c.idcloud.core.service.res.api.ResTopologyService;
import com.h3c.idcloud.core.service.system.api.MailNotificationsService;
import com.h3c.idcloud.core.service.system.api.MgtObjExtService;
import com.h3c.idcloud.core.service.system.api.MgtObjService;
import com.h3c.idcloud.core.service.system.api.SysTLogRecordService;
import com.h3c.idcloud.core.service.system.api.TopicService;
import com.h3c.idcloud.core.service.system.api.UserMgtObjService;
import com.h3c.idcloud.core.service.system.api.UserTopicService;
import com.h3c.idcloud.core.service.user.api.ModuleService;
import com.h3c.idcloud.core.service.user.api.RoleService;
import com.h3c.idcloud.core.service.user.api.UserRoleService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infra.log.aspect.ActionTrace;
import com.h3c.idcloud.infra.security.AuthService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants.RES_TOPOLOGY_TYPE;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.apache.commons.lang.time.DateFormatUtils;
import org.jboss.resteasy.spi.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.wsdl.Output;

/**
 * User service implement Created by qct on 2016/1/11.
 */
@Service(version = "1.0.0")
@Component
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 用户Mapper
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 角色Service
     */
    @Autowired
    private RoleService roleService;

    /**
     * 模块Service
     */
    @Autowired
    private ModuleService moduleService;

    /**
     * 用户角色Service
     */
    @Autowired
    private UserRoleService userRoleService;

    @Reference(version = "1.0.0")
    private TopicService topicService;

    @Reference(version = "1.0.0")
    private UserTopicService userTopicService;

    /**
     * 用户角色Mapper
     */
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private BizMapper bizMapper;

    @Autowired
    private OrgbizMapper orgbizMapper;

    @Reference(version = "1.0.0")
    private SysTLogRecordService logService;
//    @Autowired
//    private SysLoggerFactory sysLogger;

    /**
     * 业务资源关系Service
     */
    @Reference(version = "1.0.0")
    private MgtObjResService mgtObjResService;

    /**
     * 资源拓扑Service
     */
    @Reference(version = "1.0.0")
    private ResTopologyService resTopologyService;

    @Reference(version = "1.0.0")
    private MgtObjService mgtObjService;

    @Reference(version = "1.0.0")
    private MgtObjExtService mgtObjExtService;

    @Reference(version = "1.0.0")
    private MailNotificationsService mailNotificationsService;

    @Reference(version = "1.0.0")
    private UserMgtObjService userMgtObjService;

    @Autowired
    AuthService authService;

    @Autowired
    UserTokenMapper userTokenMapper;

    @Autowired
    AccountLevelMapper accountLevelMapper;

    @Autowired
    BillingAccountMapper billingAccountMapper;

    @Autowired
    BillingAccountMgtObjMapper billingAccountMgtObjMapper;

    @Autowired
    private MgtObjExtMapper mgtObjExtMapper;

    /**
     * 执行用户登录
     *
     * @return 00：失败，01：成功 ,其他情况，02：失败，不是后台用户, 03:失败,用户被禁用
     */
    @Override
    public String executeLogin(Criteria criteria) {

        // 条件查询
        List<User> list = this.userMapper.selectByParams(criteria);
        if (null == list || list.size() != 1) {
            // 没有此用户名
            return "00";
        }
        User dataBaseUser = list.get(0);

        if (dataBaseUser.getUserType()
                .equals(WebConstants.USER_TYPE.FOREGROUND)) {

            // 非后台用户
            return "02";
        }

        if (!WebConstants.UserStatus.AVAILABILITY.equals(dataBaseUser
                                                                 .getStatus())) {
            return "03";
        }
        // 传入的password已经md5过一次了,并且为小写，加入salt值
        String passwordIn = WebUtil.encrypt(
                (String) criteria.get("passwordIn"),
                (String) criteria.get("account"));
        if (!passwordIn.equals(dataBaseUser.getPassword())) {
            // 密码不正确
            return "00";
        }
        // 更新最后登录时间和登录ip
        User updateUser = new User();
        updateUser.setUserSid(dataBaseUser.getUserSid());
        // updateUser.setErrorCount((short) 0);
        updateUser.setLastLoginTime(new Date());
        updateUser.setLastLoginIp((String) criteria.get("loginip"));
        this.userMapper.updateByPrimaryKeySelective(updateUser);
        // 取出用户对应的角色放入其中
        List<Role> roles = this.roleService.selectRoleByUserSid(dataBaseUser
                                                                        .getUserSid());
        List<Module> modules = this.moduleService.selectByUserSid(
                dataBaseUser.getUserSid(),
                WebConstants.ModuleCategory.DASHBOARD);
        dataBaseUser.setRoles(roles);
        dataBaseUser.setModules(modules);
        // controller中取出放到session中
        criteria.put("baseUser", dataBaseUser);

        return "01";
    }

    public int countByParams(Criteria example) {
        int count = this.userMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public User selectByPrimaryKey(Long userSid, Integer moduleCategory) {
        User user = this.userMapper.selectByPrimaryKey(userSid);
        // 查询用户权限放入用户信息中
        List<Module> modules = this.moduleService.selectByUserSid(userSid,
                                                                  moduleCategory);
        List<Role> roles = this.roleService.selectRoleByUserSid(user
                                                                        .getUserSid());
        List<MgtObjExt> exts = this.mgtObjExtService.selectByMgtObjSid(user.getMgtObjSid());
        user.setModules(modules);
        user.setRoles(roles);
        user.setExts(exts);
        return user;
    }

    public User selectByPrimaryKey2(Long userSid) {
        User user = this.userMapper.selectByPrimaryKey(userSid);
        return user;
    }

    public List<User> selectByParams(Criteria example) {
        List<User> users = this.userMapper.selectByParams(example);
        for (User user : users) {
            List<Role> roles = this.roleService.selectRoleByUserSid(user
                                                                            .getUserSid());
            user.setRoles(roles);
        }
        return users;
    }

    /**
     * 通过服务实例主键查询用户邮箱地址
     *
     * @param serviceInstanceSid 服务实例主键
     * @return 用户邮箱列表
     */
    public List<User> selectEmailByServiceInstanceSid(Long serviceInstanceSid) {
        return this.userMapper
                .selectEmailByServiceInstanceSid(serviceInstanceSid);
    }

    public int deleteByPrimaryKey(Long userSid) {
        return this.userMapper.deleteByPrimaryKey(userSid);
    }

    public int updateByPrimaryKeySelective(User record) {
        // 申请用户
//		User user = this.userMapper.selectByPrimaryKey(record.getUserSid());
//		MgtObj mgtObj = mgtObjService.selectByPrimaryKey(user.getMgtObjSid());
//
//		// String resVeSid = PropertiesUtil.getProperty("kvm.resve");
//		List<ResVe> resVes = this.resVeService
//				.selectMgtObjVe(user.getMgtObjSid(),
//						WebConstants.VirtualPlatformType.OPENSTACK);
//		if (!CollectionUtils.isEmpty(resVes)) {
//			ResVe resVe = resVes.get(0);
//			UserCreate userCreate = new UserCreate();
//
//			userCreate.setProviderType(resVe.getVirtualPlatformType());
//			userCreate.setProviderUrl(resVe.getManagementUrl());
//			userCreate.setAuthUser(resVe.getManagementAccount());
//			userCreate.setAuthPass(resVe.getManagementPassword());
//			userCreate.setVirtEnvType(resVe.getVirtualPlatformType());
//			userCreate.setVirtEnvUuid(resVe.getResTopologySid().toString());
//
//			userCreate.setTenantId(mgtObj.getUuid());
//			userCreate.setPassword(user.getPassword());
//			userCreate.setName(user.getUserSid() + "");
//			userCreate.setRoleId(PropertiesUtil.getProperty("mgtobj.manager"));
//			Object rpc = new Object();
//			try {
//				rpc = MQHelper.rpc(userCreate);
//			} catch (MQException e) {
//				e.printStackTrace();
//			}
//			UserCreateResult userResult = (UserCreateResult) rpc;
//			if (userResult.isSuccess()) {
//				record.setUuid(userResult.getId());
        return this.userMapper.updateByPrimaryKeySelective(record);
//			} else {
//				return 0;
//			}
//		} else {
//			return -1;
//		}
    }

    public int updateByPrimaryKeySelective2(User record) {
        return this.userMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeySelective3(User user ,List<MgtObjExt> mgtObjExt) {
        int flag = 0;
        flag = userMapper.updateByPrimaryKeySelective(user);
        flag += this.mgtObjExtMapper.updateBatchByPks(mgtObjExt);
        return flag;
    }

    public int insert(User record) {
        return this.userMapper.insert(record);
    }

    public int insertSelective(User record) {
        return this.userMapper.insertSelective(record);
    }

    @Override
    public String getUserStatus(Long userSid) {
        User user = this.userMapper.selectByPrimaryKey(userSid);
        return user.getStatus();
    }

    /**
     * 删除用户
     */
    @Override
    public boolean deleteUser(Long userSid) {
        boolean result = false;
        try {
//            User user = this.userMapper.selectByPrimaryKey(userSid);
            Criteria condition = new Criteria();
            condition.put("userSid", userSid);
            System.err.println("############" + userSid);
            String userRoleResult = this.userRoleService
                    .deleteByParams(condition);
            int userResult = this.userMapper.deleteByPrimaryKey(userSid);
//            result = !(userRoleResult.equals("00") || userResult == 0);
            if (userRoleResult.equals("00") || userResult == 0) {
                result = false;
            } else {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 新增用户
     */
    @Override
    public boolean insertUser(User user) {
        boolean result = false;
        try {
            // 插入用户
            WebUtil.prepareInsertParams(user);
            user.setUserType(WebConstants.USER_TYPE.FOREGROUND);
            user.setPassword64(WebUtil.encryptBase64(user.getPassword()));
            user.setPassword(WebUtil.encrypt(user.getPassword(),
                                             user.getAccount()));
            String limitNumber = PropertiesUtil
                    .getProperty("service.limit.user.quantity");
            user.setServiceLimitQuantity(Integer.parseInt(limitNumber));
            int isSuccess = this.userMapper.insertSelective(user);
            if (isSuccess == 1) {
                UserRole userRole = new UserRole();
                userRole.setRoleSid(WebConstants.RoleSid.T_USER);
                userRole.setUserSid(user.getUserSid());
                int userRoleResult = this.userRoleService
                        .insertSelective(userRole);
                if (userRoleResult == 1) {
                    result = true;
                } else {
                    result = false;
                }
            } else {
                this.userMapper.deleteByPrimaryKey(user.getUserSid());
                result = false;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * 注册用户
     */
    @Override
    public int addRegisterUser(User user) {
        int result = 0;

        // 系统日志
        SysTLogRecord record = new SysTLogRecord();
        record.setAccount("");
        record.setActLevel("01");
        record.setActTarget("用户管理");
        record.setActName("用户注册");
        record.setActStartDate(new Date());

        String bizText = user.getBizText();
        Long fbizSid = user.getFbizSid();
        // 得到新的父业务
        Biz fBiz = bizMapper.selectByPrimaryKey(fbizSid);
        // 如果二级业务改变
        Long bizSid = null;
        // 进行添加
        Biz biz = new Biz();
        biz.setName(bizText);
        biz.setParentBizSid(fbizSid);
        biz.setOwner(fBiz.getOwner());
        biz.setOwnerTel(fBiz.getOwnerTel());
        biz.setLevel(2L);
        biz.setNetworktype(0L);
        biz.setFwport(0L);
        biz.setIsbizcont(0L);
        biz.setIsprono(0L);
        biz.setIsproattach(0L);
        biz.setPm(user.getPmName());
        biz.setPmEmail(user.getPmEmail());
        biz.setPmTel(user.getPmTel());
        bizMapper.insertSelective(biz);

		/* 添加业务默认关联的资源--- begin */

        // 查询父业务所属的资源分区
        Criteria criteria = new Criteria();
        criteria.put("bizSid", biz.getParentBizSid());
        criteria.put("resSetType", WebConstants.RES_TOPOLOGY_TYPE.RZ);
        List<MgtObjRes> bizResList = mgtObjResService.selectByParams(criteria);

        // 组装资源分区参数
        List<String> resourceZones = new ArrayList<String>();
        for (MgtObjRes bizRes : bizResList) {
            resourceZones.add("'" + bizRes.getResSetSid() + "'");
        }
        // 查询父业务的资源分区下所属的计算资源
        Criteria resZoneParam = new Criteria();
        resZoneParam.put("resZoneSids", StringUtils.join(resourceZones, ","));
        resZoneParam.put("resTopologyType", RES_TOPOLOGY_TYPE.PCX);
        List<ResTopology> resTopologies = resTopologyService
                .getResZoneChildTopology(resZoneParam);

        for (ResTopology resTopology : resTopologies) {
            MgtObjRes res = new MgtObjRes();
            res.setBizSid(biz.getBizSid());
            res.setResCategory("0");
            res.setResSetType(resTopology.getResTopologyType());
            res.setResSid(resTopology.getResTopologySid());
            this.mgtObjResService.insertSelective(res);

        }

        // 查询父业务的资源分区下所属的网络资源
        resZoneParam = new Criteria();
        resZoneParam.put("resZoneSids", StringUtils.join(resourceZones, ","));
        resZoneParam.put("resTopologyType", WebConstants.RES_TOPOLOGY_TYPE.PN);
        resTopologies = resTopologyService
                .getResZoneChildTopology(resZoneParam);

        for (ResTopology resTopology : resTopologies) {
            MgtObjRes res = new MgtObjRes();
            res.setBizSid(biz.getBizSid());
            res.setResCategory("1");
            res.setResSetType(resTopology.getResTopologyType());
            String resTopologySid = resTopology.getResTopologySid();

            // 判断是否有重复的resid被写入
            if (resourceZones.contains("'" + resTopologySid + "'")) {
                continue;
            }

            res.setResSid(resTopology.getResTopologySid());
            this.mgtObjResService.insertSelective(res);
        }

		/* 添加业务默认关联的资源--- end */

        // 同时添加业务和部门的关联
        // 判断父业务是否已经关联
        Criteria example = new Criteria();
        example.put("orgSid", user.getOrgSid());
        example.put("bizSid", fbizSid);
        List<Orgbiz> fbizorg = orgbizMapper.selectByParams(example);
        if (fbizorg == null || fbizorg.size() == 0 || fbizorg.get(0) == null) {
            Orgbiz orgBiz = new Orgbiz();
            orgBiz.setBizSid(fbizSid);
            orgBiz.setOrgSid(user.getOrgSid());
            orgbizMapper.insert(orgBiz);
        }

        // 保存新的业务和部门关联
        example = new Criteria();
        example.put("name", bizText);
        example.put("parentBizSid", fbizSid);
        List<Biz> newBiz = bizMapper.selectByParams2(example);
        bizSid = newBiz.get(0).getBizSid();
        Orgbiz orgBiz = new Orgbiz();
        orgBiz.setBizSid(bizSid);
        orgBiz.setOrgSid(user.getOrgSid());
        orgbizMapper.insert(orgBiz);

//		user.setMgtObjSid(bizSid);

        // 插入用户,当租户类型为个人时，添加默认租户信息
        user.setTenantSid(Long.parseLong(PropertiesUtil
                                                 .getProperty("personal.tenant.sid")));
        user.setUserType(WebConstants.USER_TYPE.FOREGROUND);
        user.setStatus(WebConstants.UserStatus.NOTAPPROVE);
        user.setPassword64(WebUtil.encryptBase64(user.getPassword()));
        user.setPassword(WebUtil.encrypt(user.getPassword(), user.getAccount()));
        String userLimitNumber = PropertiesUtil
                .getProperty("service.limit.user.quantity");
        user.setServiceLimitQuantity(Integer.parseInt(userLimitNumber));
        user.setOrgSid(user.getOrgSid());
        user.setDepName(user.getDepName());
        // user.setBizSid(user.getBizSid());
        WebUtil.prepareInsertParams(user);
        int userResult = this.userMapper.insertSelective(user);

        // 配置用户角色为：租户管理员
        UserRole userRole = new UserRole();
        userRole.setRoleSid(WebConstants.RoleSid.T_USER);
        userRole.setUserSid(user.getUserSid());
        int userRoleResult = this.userRoleMapper.insertSelective(userRole);
        if (userResult + userRoleResult == 2) {
            result = 0;
            record.setActResult("02");
        } else {
            result = 1;
            record.setActResult("01");
        }

        record.setActDetail("注册帐号:" + user.getAccount());
        record.setActEndDate(new Date());
//        SysLogger log = sysLogger.getLogger(LoggerTypeEnum.DB);
//        log.debug(record);

        return result;
    }

    /**
     * 根据租户Sid查询租户用户列表
     */
    @Override
    public List<User> selectTUserByTenantSid(Criteria example) {
        return this.userMapper.selectTUserByTenantSid(example);
    }

    /**
     * 根据产品经理Sid查询成员列表
     */
    @Override
    public List<User> selectUserByProjectSid(Criteria example) {
        return this.userMapper.selectUserByProjectSid(example);
    }

    /**
     * 根据租户sid和服务sid查询user实例
     */
    public List<User> selectAllocatedUserByParams(Criteria example) {
        return this.userMapper.selectAllocatedUserByParams(example);
    }

    @Override
    public int insertPlatformUser(User user) {
        int result = 1;
        try {
            // 补全用户信息
            WebUtil.prepareInsertParams(user);

            // 加密密码
            user.setPassword64(WebUtil.encryptBase64(user.getPassword()));
            user.setPassword(WebUtil.encrypt(user.getPassword(), user.getAccount()));
            int userResult = this.userMapper.insertSelective(user);
            if (WebConstants.USER_TYPE.FOREGROUND.equals(user.getUserType())
                && !WebConstants.UserStatus.NOTAPPROVE.equals(user.getStatus())) {
                for (int i = 0; i < user.getRoleArr().size(); i++) {
                    if (!user.getRoleArr().get(i).equals("NaN")) {
                        String roleId = user.getRoleArr().get(i);
                        UserRole userRole = new UserRole();
                        userRole.setRoleSid(Long.parseLong(roleId));
                        userRole.setUserSid(user.getUserSid());
                        int userRoleResult = this.userRoleService.insertSelective(userRole);
//						UserMgtObjKey umok = new UserMgtObjKey();
//						umok.setUserSid(user.getUserSid());
//						umok.setMgtObjSid(Long.valueOf(user.getUserMgtObj()));
//						this.userMgtObjService.insertSelective(umok);
                    }
                }
//				UserMgtObjKey umok = new UserMgtObjKey();
//				umok.setUserSid(user.getUserSid());
//				umok.setMgtObjSid(Long.valueOf(user.getUserMgtObj()));
//				this.userMgtObjService.insertSelective(umok);
            }
            if (WebConstants.USER_TYPE.BACKGROUND.equals(user.getUserType())
                || WebConstants.UserStatus.NOTAPPROVE.equals(user.getStatus())) {

                boolean roleResult = true;
                // 插入用户的角色
                for (int i = 0; i < user.getRoleArr().size(); i++) {
                    if (!user.getRoleArr().get(i).equals("NaN")) {
                        UserRole userRole = new UserRole();
                        userRole.setRoleSid(Long.parseLong(user.getRoleArr()
                                                                   .get(i)));
                        userRole.setUserSid(user.getUserSid());
                        int userRoleResult = this.userRoleService
                                .insertSelective(userRole);
                        if (userRoleResult == 1) {
                            roleResult = true;
                        } else {
                            roleResult = false;
                            break;
                        }
                    }
                }
                // 配置topic
                Criteria param = new Criteria();
                List<Topic> topics = topicService.selectByParams(param);
                int sortRank = 0;
                if (!CollectionUtils.isEmpty(topics)) {
                    for (Topic topic : topics) {
                        UserTopic ut = new UserTopic();
                        ut.setUserSid(user.getUserSid());
                        ut.setTopicSid(topic.getTopicSid());
                        ut.setSortRank(sortRank);
                        userTopicService.insertSelective(ut);
                        sortRank++;
                    }
                }

                if (userResult == 1 && roleResult) {
                    result = 1;
                } else {
                    result = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }
        return result;

    }

    @Override
    public boolean updatePlatformUser(User user) {
        boolean result = false;
        boolean roleResult = true;
        try {
            // 清空用户角色关系表中的该用户所有角色
            if ("01".equals(user.getUserType())) {
                Criteria criteria = new Criteria();
                criteria.put("userSid", user.getUserSid());
                String delresult = this.userRoleService.deleteByParams(criteria);
                if ("01".equals(delresult)) {
                    // 插入用户的角色
                    for (int i = 0; i < user.getRoleArr().size(); i++) {
                        if (!user.getRoleArr().get(i).equals("NaN")) {
                            UserRole userRole = new UserRole();
                            userRole.setRoleSid(Long.parseLong(user.getRoleArr()
                                                                       .get(i)));
                            userRole.setUserSid(user.getUserSid());
                            int userRoleResult = this.userRoleService
                                    .insertSelective(userRole);
                            if (userRoleResult == 1) {
                                roleResult = true;
                            } else {
                                roleResult = false;
                                break;
                            }
                        }
                    }

                } else {
                    result = false;
                }
            }
            // 更新用户表
            int userResult = this.userMapper
                    .updateByPrimaryKeySelective(user);

            User newUser = this.userMapper.selectByPrimaryKey(user.getUserSid());
            // 判断是不是后台用户，是的话将业务和部门清空
            if (!StringUtil.isNullOrEmpty(newUser.getUserType())
                && WebConstants.USER_TYPE.BACKGROUND.equals(newUser
                                                                    .getUserType())) {
                newUser.setOrgSid(null);
                //newUser.setMgtObjSid(null);
                this.userMapper.updateByPrimaryKey(newUser);
            }

            result = userResult == 1 && roleResult;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public List<User> selectUserByRoles(String roles) {
        return userMapper.selectUserByRoles(roles);
    }

    @Override
    public List<User> selectBizByAccount(String account) {
        // TODO Auto-generated method stub
        return userMapper.selectBizByAccount(account);
    }

    @Override
    public List<User> selectIdcUser(Criteria example) {
        return userMapper.selectIdcUser(example);
    }

    @Override
    public boolean insertTenantUser(String params) {
        boolean flag = false;
        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            // 插入管理对象
            Map<String, String> tenantMap = (Map<String, String>) map
                    .get("tenant");
            Map<String, String> userMap = (Map<String, String>) map
                    .get("user");

            User user = JsonUtil.fromJson(JsonUtil.toJson(userMap), User.class);
            Criteria criteria = new Criteria();
            //获取随机不重复帐号
//            int isTrue = 0;
//            List<User> userList = null;
//            String account = "";
//            while(isTrue==0){
//                account = WebUtil.randomAccount(6);
//                criteria.clear();
//                criteria.put("account", account);
//                userList = this.userMapper.selectByParams(criteria);
//                if (userList.size() == 0) {
//                    isTrue = 1;
//                    user.setAccount(account);
//                }
//            }

            MgtObj mgtObj = new MgtObj();
            if (tenantMap.get("tenantName") == null || tenantMap.get("tenantName") == "") {
                mgtObj.setName(userMap.get("realName"));
            } else {
                mgtObj.setName(tenantMap.get("tenantName"));
            }
            mgtObj.setParentId(-999L);
            mgtObj.setLevel(1L);
            mgtObj.setStatus("01");
            mgtObj.setCreatedBy(user.getAccount());
            mgtObj.setCreatedDt(new Date());
//            WebUtil.prepareUpdateParams(mgtObj);
            int tenant = mgtObjService.insertSelective(mgtObj);
            criteria.clear();
            criteria.put("name", mgtObj.getName());
            List<MgtObj> mgtObjs = mgtObjService.selectByParams(criteria);
            if (tenant == 1) {
                // 出入ext表
                Object[] keys = tenantMap.keySet().toArray();
                for (Object string : keys) {
                    MgtObjExt ext = new MgtObjExt();
                    ext.setAttrKey((String) string);
                    ext.setAttrValue(tenantMap.get(string));
                    ext.setMgtObjSid(mgtObjs.get(0).getMgtObjSid());
                    mgtObjExtService.insertSelective(ext);
                }
                // 插入用户信息
//                Map<String, String> userMap = (Map<String, String>) map
//                        .get("user");

                user.setMgtObjSid(mgtObjs.get(0).getMgtObjSid());
                user.setUserType(WebConstants.USER_TYPE.FOREGROUND);
                user.setStatus(WebConstants.UserStatus.NOTACTIVATE);
                user.setPassword64(WebUtil.encryptBase64(user.getPassword()));
                user.setPassword(WebUtil.encrypt(user.getPassword(), user.getAccount()));
                WebUtil.prepareInsertParams(user);
                userMapper.insertSelective(user);
                // 查询企业管理员的id
//                String mgtObjRole = PropertiesUtil
//                        .getProperty("mgtobj.manager.sid");
                String mgtObjRole = "104";
                UserRole userRole = new UserRole();
                userRole.setRoleSid(Long.parseLong(mgtObjRole));
                userRole.setUserSid(user.getUserSid());
                this.userRoleMapper.insertSelective(userRole);

                //生成账户
                BillingAccount billingAccount = new BillingAccount();
                billingAccount.setStatus(WebConstants.ACCOUNT_STATUS.INACTIVE);
                billingAccount.setAccountType(WebConstants.ACCOUNT_TYPE.PERSONAL);
                billingAccount.setAccountName(user.getAccount());
//                account.setDefaultSpace(Long.parseLong(PropertiesUtil.getProperty("register.default.space")));
                //设置账户等级
                List<AccountLevel> alList = this.accountLevelMapper.selectByParams(null);
                if (alList != null && alList.size() > 0) {
                    AccountLevel accountLevel0 = alList.get(0);
                    billingAccount.setAccountLevelSid(accountLevel0.getLevelSid());
                    billingAccount.setAccountLevelName(accountLevel0.getLevelName());
                }
                WebUtil.prepareInsertParams(billingAccount, user.getAccount());
                this.billingAccountMapper.insertSelective(billingAccount);

                BillingAccountMgtObj billingAccountMgtObj = new BillingAccountMgtObj();
                billingAccountMgtObj.setAccountSid(billingAccount.getAccountSid());
                billingAccountMgtObj.setMgtObjSid(mgtObjs.get(0).getMgtObjSid());
                this.billingAccountMgtObjMapper.insertSelective(billingAccountMgtObj);

                flag = true;
                if (flag) {
                    this.mailNotificationsService.registerAccountEmail(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public int updatePasswordByPrimaryKey(User record) {
        // 修改用户密码
        return this.userMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 项目经理注册
     */
    @Override
    public boolean insertRegisterUser(User user) {
        boolean flag = false;
        user.setUserType(WebConstants.USER_TYPE.FOREGROUND);
        user.setStatus(WebConstants.UserStatus.NOTAPPROVE);
        user.setPassword64(WebUtil.encryptBase64(user.getPassword()));
        user.setPassword(WebUtil.encrypt(user.getPassword(),
                                         user.getAccount()));
        WebUtil.prepareInsertParams(user);
        userMapper.insertSelective(user);
        String mgtObjRole = PropertiesUtil.getProperty("mgtobj.manager.sid");
        UserRole userRole = new UserRole();
        userRole.setRoleSid(Long.parseLong(mgtObjRole));
        userRole.setUserSid(user.getUserSid());
        this.userRoleMapper.insertSelective(userRole);
        flag = true;
        if (flag) {
            this.mailNotificationsService.registerAccountEmail(user);
        }
        return flag;
    }

    @Override
    public List<User> findUsersByMgtObjSid(Criteria example) {
        return this.userMapper.findUsersByMgtObjSid(example);
    }

    @Override
    public List<User> findUsersByProjectMaster(Criteria example) {
        return this.userMapper.findUsersByProjectMaster(example);
    }

    @Override
    public List<User> findAllUsersByMgtObj(Criteria criteria) {
        return this.userMapper.findAllUsersByMgtObj(criteria);
    }

    @Transactional
    @Override
    public UserToken login(User user) {
        UserToken userToken = null;
        // 验证用户
        Criteria critera = new Criteria();
        critera.put("loginAccount", user.getAccount());
        critera.put("userType", user.getUserType());
        List<User> list = userMapper.selectByParams(critera);

        if (list != null && list.size() > 0) {
            User loginUser = list.get(0);
            //判断用户状态
            if (WebConstants.UserStatus.AVAILABILITY.equals(loginUser.getStatus())) {
                // 有效用户
                String md5Passwd = WebUtil.encrypt(user.getPassword(), loginUser.getAccount());
                if (md5Passwd.equals(loginUser.getPassword())) {
                    //TODO
                    // 1. generate token
                    userToken = authService.createJWT(list.get(0));
                    logger.info("generated user token({}): {}", list.get(0).getAccount(),
                                userToken);
                    // 2. store token in redis
                    // 3. store token in db
                    userTokenMapper.insertSelective(userToken);
                } else {
                    // 用户名或密码错误
                    userToken = new UserToken();
                    userToken.setAccessToken("02");
                }
            } else {
                // 用户未激活或无效
                userToken = new UserToken();
                userToken.setAccessToken("03");
            }
        } else {
            // 用户不存在
            userToken = new UserToken();
            userToken.setAccessToken("04");
        }

        return userToken;
    }

    @ActionTrace(actionTarget = "用户管理", actionName = "用户查询", actionLevel = "1", actionDetail = "查询用户", isUsedDefaultDetail = false)
    @Override
    public UserVo queryUserByIdSwq(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        UserVo userVo = new UserVo();
        userVo.setAccount(user.getAccount());
        userVo.setPassword(user.getPassword());
//        userVo.setAccountType(user.getUserType());
        userVo.setUserSid(user.getUserSid());
        //覆盖操作详细
        userVo.setActionDetail("查询用户，用户SID：" + userVo.getUserSid());
        return userVo;
    }

    @Override
    public User queryUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    private void assembleMgtObjExtToList(String attrKey, String attrValue,
                                         List<MgtObjExt> mgtObjExtList) {
        MgtObjExt mgtObjExt = new MgtObjExt();
        mgtObjExt.setAttrKey(attrKey);
        mgtObjExt.setAttrValue(attrValue);
        mgtObjExtList.add(mgtObjExt);
    }

    private Long savePersonOrEnterpriseInfo(Map<String, String> baseMap,
                                            Map<String, String> detailMap) {

        String type = baseMap.get("type");
        List<MgtObjExt> mgtObjExtList = new ArrayList<MgtObjExt>();
        MgtObj mgtObj = new MgtObj();

        if ("person".equals(type)) {
            mgtObj.setName(baseMap.get("realName"));
            mgtObj.setLevel(1L);
            mgtObj.setParentId(WebConstants.MgtObjGroupId.PERSON);
//            mgtObj.setType(WebConstants.MgtObjType.PERSON);
            WebUtil.prepareInsertParams(mgtObj);

            assembleMgtObjExtToList("province", detailMap.get("province"), mgtObjExtList);
            assembleMgtObjExtToList("city", detailMap.get("province"), mgtObjExtList);
            assembleMgtObjExtToList("street", detailMap.get("street"), mgtObjExtList);
            assembleMgtObjExtToList("zipCode", detailMap.get("zipCode"), mgtObjExtList);
            assembleMgtObjExtToList("certificateId", detailMap.get("certificateId"), mgtObjExtList);
            assembleMgtObjExtToList("certificateType", detailMap.get("certificateType"),
                                    mgtObjExtList);
        } else if ("enterprise".equals(type)) {
            mgtObj.setName(detailMap.get("enterpriseName"));
            mgtObj.setLevel(1L);
            mgtObj.setParentId(WebConstants.MgtObjGroupId.ENTERPRISE);
//            mgtObj.setType(WebConstants.MgtObjType.ENTERPRISE);
            WebUtil.prepareInsertParams(mgtObj);

            //行业类型
            assembleMgtObjExtToList("industryType", detailMap.get("industryType"), mgtObjExtList);
            //业务类型
            assembleMgtObjExtToList("businessType", detailMap.get("businessType"), mgtObjExtList);
            assembleMgtObjExtToList("officer", detailMap.get("officer"), mgtObjExtList);
            assembleMgtObjExtToList("certificateId", detailMap.get("certificateId"), mgtObjExtList);
            assembleMgtObjExtToList("zipCode", detailMap.get("zipCode"), mgtObjExtList);

            assembleMgtObjExtToList("certificateType", "LICENSE", mgtObjExtList);
            assembleMgtObjExtToList("registerTime", detailMap.get(""), mgtObjExtList);
        }

        mgtObjService.insertSelective(mgtObj);
        for (MgtObjExt mgtObjExt : mgtObjExtList) {
            mgtObjExt.setMgtObjSid(mgtObj.getMgtObjSid());
            this.mgtObjExtService.insertSelective(mgtObjExt);
        }
        return mgtObj.getMgtObjSid();
    }

    @Override
    public void registerProtalUser(String params) {
        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
//            Map<String, String> map =JsonUtil.fromJson(params,Map.class);
            Map<String, String> baseMap = (Map<String, String>) map
                    .get("base");
            Map<String, String> detailMap = (Map<String, String>) map
                    .get("detail");
            String type = baseMap.get("type");
            String account = baseMap.get("account");
            String realName = baseMap.get("realName");
            String mail = baseMap.get("email");
            String mobile = baseMap.get("mobile");
            String password = baseMap.get("password");

            User user = new User();
            Date registerTime = new Date();
            Long mgtObjSid = null;
            if (detailMap != null) {
                detailMap.put("registerTime",
                              DateFormatUtils.format(registerTime, "yyyy-MM-dd HH:mm:ss"));
                mgtObjSid = savePersonOrEnterpriseInfo(baseMap, detailMap);
                user.setMgtObjSid(mgtObjSid);
            }
            user.setAccount(account);
            user.setRealName(realName);
            user.setEmail(mail);
            user.setUserType(WebConstants.USER_TYPE.FOREGROUND);
            user.setMobile(mobile);
            user.setPassword(WebUtil.encrypt(password, account));
            user.setPassword64(WebUtil.encryptBase64(password));
            user.setStatus(WebConstants.UserStatus.NOTAPPROVE);

            userMapper.insertSelective(user);

            UserRole userRole = new UserRole();
            userRole.setUserSid(user.getUserSid());
            if ("person".equals(type)) {
                //如果是个人账号
                userRole.setRoleSid(103L);
            } else if ("enterprise".equals(type)) {
                //如果是企业用户
                userRole.setRoleSid(205L);
            }
            userRoleService.insertSelective(userRole);

//            //组装BSS接口用户信息，调用用户信息注册接口
//            UserVo userVo = new UserVo();
//            userVo.setMobile(mobile);
//            userVo.setUsername(account);
//            userVo.setRealName(realName);
//            userVo.setPassword(password);
//            userVo.setLocked(true);
//            String certificateId = detailMap.get("certificateId");
//            String certificateType = detailMap.get("certificateType");
//            String province = detailMap.get("province");
//            String city = detailMap.get("city");
//            String street = detailMap.get("street");
//            String zipCode = detailMap.get("zipCode");
//            String enterpriseName = detailMap.get("enterpriseName");
//            String industryType = detailMap.get("industryType");
//            String businessType = detailMap.get("businessType");
//            String officer = detailMap.get("officer");
//            UserInfoVo  userInfoVo = null;
//            if("person".equals(type)) {
//                PersonInfoVo personInfoVo = new PersonInfoVo();
//                personInfoVo.setEmail(mail);
//                personInfoVO.setCertificateId(certificateId);
//                personInfoVO.setCertificateType(SystemConstant.CertificateType.valueOf(certificateType));
//                personInfoVO.setName(realName);
//                personInfoVO.setTelephone(mobile);
//                personInfoVO.setMobile(mobile);
//
//                List<AddressVO> addressList = new ArrayList<AddressVO>();
//                AddressVO addressVO = new AddressVO();
//                addressVO.setProvince(province);
//                addressVO.setCity(city);
//                addressVO.setStreet(street);
//                addressVO.setEmail(mail);
//                addressVO.setContact(realName);
//                addressVO.setTele(mobile);
//                addressVO.setZipCode(zipCode);
//                addressList.add(addressVO);
//                personInfoVO.setAddress(addressList);
//                userInfoVO = personInfoVO;
//            } else if("enterprise".equals(type)) {
//                EnterpriseInfoVO enterpriseInfoVO = new EnterpriseInfoVO();
//                enterpriseInfoVO.setEmail(mail);
//                enterpriseInfoVO.setCertificateId(certificateId);
//                enterpriseInfoVO.setCertificateType(SystemConstant.CertificateType.LICENSE);
//                enterpriseInfoVO.setName(enterpriseName);
//                enterpriseInfoVO.setZipCode(zipCode);
//                enterpriseInfoVO.setOfficer(officer);
//                enterpriseInfoVO.setRegisterTime(new Date());
//
//                EnterpriseBizTypeVO entIndustryTypeType = new EnterpriseBizTypeVO();
//                entIndustryTypeType.setCode(industryType);
//                enterpriseInfoVO.setEnterpriseType(entIndustryTypeType);
//
//                EnterpriseBizTypeVO entBusinessType = new EnterpriseBizTypeVO();
//                entBusinessType.setCode(businessType);
//                enterpriseInfoVO.setEnterpriseBiz(entBusinessType);
//
//                List<AddressVO> addressList = new ArrayList<AddressVO>();
//                AddressVO addressVO = new AddressVO();
//                addressVO.setEmail(mail);
//                addressVO.setContact(realName);
//                addressVO.setTele(mobile);
//                addressVO.setZipCode(zipCode);
//                addressList.add(addressVO);
//                enterpriseInfoVO.setAddress(addressList);
//                userInfoVO = enterpriseInfoVO;
//            }
//            userVO.setUserInfo(userInfoVO);
//
//            List<ProviderResourceVO> providerResources = new ArrayList<ProviderResourceVO>();
//            ProviderResourceVO providerResourceVO = new ProviderResourceVO();
//            providerResourceVO.setProviderResourceName("XCLOUD");
//            providerResources.add(providerResourceVO);
//            userVO.setProviderResources(providerResources);
//
//            UserVO resUserVO = BssUtils.registerUser(userVO);
//            if(resUserVO.getUserInfo() != null && resUserVO.getUserInfo().getId() != null) {
//                MgtObjExt bssEnterpriseIdExt = new MgtObjExt();
//                bssEnterpriseIdExt.setMgtObjSid(mgtObjSid);
//                bssEnterpriseIdExt.setAttrKey("enterpriseBssId");
//                bssEnterpriseIdExt.setAttrValue(resUserVO.getUserInfo().getId().toString());
//                this.mgtObjExtService.insertSelective(bssEnterpriseIdExt);
//            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }
}
