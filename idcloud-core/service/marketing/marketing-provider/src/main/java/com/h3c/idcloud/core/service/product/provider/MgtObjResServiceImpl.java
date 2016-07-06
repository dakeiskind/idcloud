package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.product.dao.MgtObjResMapper;
import com.h3c.idcloud.core.pojo.dto.product.MgtObjRes;
import com.h3c.idcloud.core.pojo.dto.product.MgtObjResKey;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceSpec;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.system.MgtObjExt;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.service.product.api.MgtObjResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceSpecService;
import com.h3c.idcloud.core.service.system.api.MailNotificationsService;
import com.h3c.idcloud.core.service.system.api.MgtObjExtService;
import com.h3c.idcloud.core.service.system.api.MgtObjService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(version = "1.0.0")
@Component
public class MgtObjResServiceImpl implements MgtObjResService {
    @Autowired
    private MgtObjResMapper mgtObjResMapper;

	@Reference(version = "1.0.0")
    private MgtObjService mgtObjService;

	@Reference(version = "1.0.0")
    private MgtObjExtService mgtObjExtService;

	@Reference(version = "1.0.0")
    private UserService userService;

	@Autowired
    private ServiceInstanceService instanceService;

	@Autowired
    private ServiceInstResService instResService;

	@Autowired
    private ServiceInstanceSpecService instanceSpecService;

	@Reference(version = "1.0.0")
	private MailNotificationsService mailNotificationsService;
	
	/*@Autowired
	@Qualifier("vmDeleteHandlerImpl")
	private ResourceRequestHanlder vmDeleteHandlerImpl;*/

	private static final Logger logger = LoggerFactory.getLogger(MgtObjResServiceImpl.class);

	public MgtObjResServiceImpl() {
	}

	public int countByParams(Criteria example) {
		int count = this.mgtObjResMapper.countByParams(example);
		logger.debug("count: {}", count);
		return count;
	}

	public MgtObjRes selectByPrimaryKey(MgtObjResKey key) {
		return this.mgtObjResMapper.selectByPrimaryKey(key);
	}

	public List<MgtObjRes> selectByParams(Criteria example) {
		return this.mgtObjResMapper.selectByParams(example);
	}



	public int deleteByPrimaryKey(MgtObjResKey key) {
		return this.mgtObjResMapper.deleteByPrimaryKey(key);
	}

	public int updateByPrimaryKeySelective(MgtObjRes record) {
		return this.mgtObjResMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(MgtObjRes record) {
		return this.mgtObjResMapper.updateByPrimaryKey(record);
	}

	public int deleteByParams(Criteria example) {
		return this.mgtObjResMapper.deleteByParams(example);
	}

	public int updateByParamsSelective(MgtObjRes record, Criteria example) {
		return this.mgtObjResMapper.updateByParamsSelective(record, example.getCondition());
	}

	public int updateByParams(MgtObjRes record, Criteria example) {
		return this.mgtObjResMapper.updateByParams(record, example.getCondition());
	}

	public int insert(MgtObjRes record) {
		return this.mgtObjResMapper.insert(record);
	}

	public int insertSelective(MgtObjRes record) {
		return this.mgtObjResMapper.insertSelective(record);
	}

	public List<MgtObjRes> selectBizReses(Criteria example) {
		return this.mgtObjResMapper.selectBizReses(example);
	}

	@Override
	public List<MgtObjRes> selectByParams2(Criteria example) {
		return this.mgtObjResMapper.selectByParams2(example);
	}

	@Override
	public List<MgtObjRes> selectMgtRes(Criteria example) {
		return this.mgtObjResMapper.selectMgtObjResByParams(example);
	}

	@Override
	public void selectMgtObjResForRecovery(Criteria example) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//查询将要到期的项目
		Criteria params = new Criteria();
		params.put("level", "1");
		params.put("status", WebConstants.MGT_OBJ_STATUS.NORMAL);
		List<MgtObj> mgtObjs = mgtObjService.selectMgtObjTreeByParams(params);
		//得到提前多久提醒
		String presetTime = PropertiesUtil.getProperty("mgtobj.expiredate.notice");
		int time = Integer.parseInt(presetTime)*7;
		if(!CollectionUtils.isEmpty(mgtObjs)){
			for (MgtObj mgtObj : mgtObjs) {
				//查询该项目的经理，得到邮箱
				params = new Criteria();
				params.put("userMgtObjSid", mgtObj.getMgtObjSid());
				params.put("roleSid", WebConstants.RoleSid.MGT_OBJ_MANAGER);
				List<User> manager = userService.selectByParams(params);
				String managerEmail = "";
				if(!CollectionUtils.isEmpty(manager)){
					managerEmail = manager.get(0).getEmail();
				}

				Map<String,String> mgtObjData = new HashMap<String, String>();

				mgtObjData.put("mgtObjName", mgtObj.getName());

				//查询项目的到期时间
				params = new Criteria();
				params.put("mgtObjSid", mgtObj.getMgtObjSid());
				params.put("attrKey", "projectEndDate");
				List<MgtObjExt> exts = mgtObjExtService.selectByParams(params);
				if(!CollectionUtils.isEmpty(exts)){
					String mgtObjEndDate = exts.get(0).getAttrValue();

					mgtObjData.put("mgtObjEndTime", mgtObjEndDate);

					try {
						Date endTime = df.parse(mgtObjEndDate+" 23:59:59");
						Date now = new Date();
						long buffer = endTime.getTime() - now.getTime();
						long days = buffer/(1000*60*60*24);
						//如果在提醒范围内，进行邮件提醒
						if(time>=days){
							String statusParams = "'"
									+ WebConstants.ServiceInstanceStatus.CANCELING + "','"
									+ WebConstants.ServiceInstanceStatus.CHANGEING + "','"
									+ WebConstants.ServiceInstanceStatus.OPENED + "','"
									+ WebConstants.ServiceInstanceStatus.REFUSED
									+ "'".replace(" ", "");

							Criteria condition = new Criteria();
							condition.put("mgtObjSid", mgtObj.getMgtObjSid());
							condition.put("statusParams", statusParams);
							condition.put("serviceSidList", "'"+WebConstants.ServiceSid.SERVICE_PM+ "','"+WebConstants.ServiceSid.SERVICE_VM+ "'".replace(" ", ""));
							List<ServiceInstance> mgtObjInstances = instanceService.selectByParams(condition);

							if(!CollectionUtils.isEmpty(mgtObjInstances)){
								String mName="";
								for (ServiceInstance instance : mgtObjInstances) {
									condition = new Criteria();
									condition.put("instanceSid", instance.getInstanceSid());
									List<ServiceInstRes> instRess = instResService.selectByParams(condition);
									if(!CollectionUtils.isEmpty(instRess)){
//										Long maxVersion = instanceSpecService.selectValidInstanceSpecByVersion(
//												instRess.get(0).getInstanceSid());

										condition = new Criteria();
										condition.put("instanceSid", instance.getInstanceSid());
										condition.put("name", WebConstants.InstanceSpecType.RECOVERY_TYPE);
//										condition.put("version", maxVersion);
										condition.setOrderByClause("VERSION DESC ");
										List<ServiceInstanceSpec> specs = instanceSpecService.selectByParams(condition);
										if(!CollectionUtils.isEmpty(specs)){
											String recoveryType = specs.get(0).getValue();
											if(days>0){
												//只是提醒
												mName = mName+instance.getInstanceName()+",";
											}else if(days<=0){
												//如果到了项目的结束时间，该提醒的提醒，回收的回收
												if(WebConstants.RECOVERY_REMARK_TYPE.NOTICE.equals(recoveryType)){
													mName = mName+instance.getInstanceName()+",";
												}else if(WebConstants.RECOVERY_REMARK_TYPE.RECOVER.equals(recoveryType)){
													if(WebConstants.ServiceSid.SERVICE_PM.equals(instance.getServiceSid())){
														instance.setStatus(WebConstants.ServiceInstanceStatus.CANCELED);
														instanceService.updateByPrimaryKeySelective(instance);

														condition = new Criteria();
														condition.put("instanceSid", instance.getInstanceSid());
														instResService.deleteByParams(condition);
													}else{
//														vmDeleteHandlerImpl.invoke(instance.getInstanceSid());
													}
												}
											}
										}
									}
								}
								if(mName.length()>0){
									mName = mName.substring(0, mName.length()-1);
								}
								mgtObjData.put("mgtObjResName", mName);

							}
							//该项目下没有虚机资源，发送单纯的提醒邮件
							boolean result = mailNotificationsService.sendMgtObjExpireDateNoticeEmail(
									WebConstants.MailTemplateId.MGTOBJ_EXPIREDATE_TO_EMAIL, managerEmail,mgtObjData);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}