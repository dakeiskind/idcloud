package com.h3c.idcloud.core.service.system.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.res.dao.ResVeMapper;
import com.h3c.idcloud.core.persist.system.dao.MgtObjMapper;
import com.h3c.idcloud.core.persist.system.dao.UserMgtObjMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.system.UserMgtObjKey;
import com.h3c.idcloud.core.service.system.api.MgtObjService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Service(version = "1.0.0")
@Component
public class MgtObjServiceImpl implements MgtObjService {
    @Autowired
    private MgtObjMapper mgtObjMapper;
    @Autowired
    private ResVeMapper resVeMapper;

	@Autowired
    private UserMgtObjMapper userMgtObjMapper;
    
//    @Reference(version = "1.0.0")
//    private UserMgtObjService userMgtObjService;

//	@Reference(version = "1.0.0")
//    private ServiceInstanceChangeLogService instanceChangeLogService;
//
//	@Reference(version = "1.0.0")
//    private ApproveRecordService approveRecordService;

    private static final Logger logger = LoggerFactory.getLogger(MgtObjServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.mgtObjMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public MgtObj selectByPrimaryKey(Long mgtObjSid) {
        return this.mgtObjMapper.selectByPrimaryKey(mgtObjSid);
    }

    public List<MgtObj> selectByParams(Criteria example) {
    	return this.mgtObjMapper.selectByParams(example);
    }
    
    public List<MgtObj> selectMgtObjTreeByParams(Criteria example) {
    	return this.mgtObjMapper.selectMgtObjTreeByParams(example);
    }

    public List<MgtObj> findParentMgtObj(Criteria example) {
        return this.mgtObjMapper.findParentMgtObj(example);
    }

    public int deleteByPrimaryKey(Long mgtObjSid) {
    	// 调用MQ
//    	MgtObj record = this.mgtObjMapper.selectByPrimaryKey(mgtObjSid);
//    	String resVeSid = PropertiesUtil.getProperty("kvm.resve");
//    	ResVe resVe = this.resVeMapper.selectByPrimaryKey(resVeSid);
//    	TenantDelete tenantDelete = new TenantDelete();
//
//    	tenantDelete.setTenantId(record.getUuid());
////    	tenantDelete.setTenantName("demo");
//    	tenantDelete.setTenantName(StringUtil.nullToEmpty(record.getMgtObjSid()));
//    	tenantDelete.setProviderType(resVe.getVirtualPlatformType());
//    	tenantDelete.setVirtEnvType(resVe.getVirtualPlatformType());
//    	tenantDelete.setVirtEnvUuid(resVe.getResTopologySid());
//
//    	Object obj = null;
//    	try {
//			obj = MQHelper.rpc(tenantDelete);
//		} catch (MQException e) {
//			e.printStackTrace();
//		}
//    	TenantDeleteResult result = (TenantDeleteResult)obj;
//    	if(result.isSuccess()||"404".equals(result.getErrCode())){
//    		 return this.mgtObjMapper.deleteByPrimaryKey(mgtObjSid);
//    	}else{
//    		 return 0;
//    	}
		return 1;
    }
    
    public int deleteTenantResByPrimaryKey(Long mgtObjSid) {
    	// 调用MQ
//    	MgtObj record = this.mgtObjMapper.selectByPrimaryKey(mgtObjSid);
//    	String resVeSid = PropertiesUtil.getProperty("kvm.resve");
//    	ResVe resVe = this.resVeMapper.selectByPrimaryKey(resVeSid);
//    	TenantResourcesDelete tenantResDelete = new TenantResourcesDelete();
//
//    	tenantResDelete.setTenantId(record.getUuid());
////    	tenantDelete.setTenantName("demo");
//    	tenantResDelete.setTenantName(StringUtil.nullToEmpty(record.getMgtObjSid()));
//    	tenantResDelete.setProviderType(resVe.getVirtualPlatformType());
//    	tenantResDelete.setVirtEnvType(resVe.getVirtualPlatformType());
//    	tenantResDelete.setVirtEnvUuid(resVe.getResTopologySid());
//
//    	Object obj = null;
//    	try {
//    		obj = MQHelper.rpc(tenantResDelete);
//    	} catch (MQException e) {
//    		e.printStackTrace();
//    	}
//    	TenantResourcesDeleteResult result = (TenantResourcesDeleteResult)obj;
//    	if(result.isSuccess()||"404".equals(result.getErrCode())){
//    		return this.mgtObjMapper.deleteByPrimaryKey(mgtObjSid);
//    	}else{
//    		return 0;
//    	}
		return 0;
    }

    public int updateByPrimaryKeySelective(MgtObj record) {
        return this.mgtObjMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(MgtObj record) {
        return this.mgtObjMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.mgtObjMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(MgtObj record, Criteria example) {
        return this.mgtObjMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(MgtObj record, Criteria example) {
        return this.mgtObjMapper.updateByParams(record, example.getCondition());
    }

    public int insert(MgtObj record) {
        return this.mgtObjMapper.insert(record);
    }

    public int insertSelective(MgtObj record) {
		return this.mgtObjMapper.insertSelective(record);
//    	if(1 == recordresult){
//    		// 调用MQ接口
//    		String resVeSid = PropertiesUtil.getProperty("kvm.resve");
//    		ResVe resVe = this.resVeMapper.selectByPrimaryKey(resVeSid);
//        	TenantCreate tenantCreate = new TenantCreate();
//        	tenantCreate.setName(Long.toString(record.getMgtObjSid()));
//        	tenantCreate.setDescription(record.getDescription());
////        	tenantCreate.setTenantName("demo");
//        	tenantCreate.setTenantName(StringUtil.nullToEmpty(record.getMgtObjSid()));
////        	tenantCreate.setProviderType(resVe.getVirtualPlatformType());
//
////        	tenantCreate.setVirtEnvType(resVe.getVirtualPlatformType());
////        	tenantCreate.setVirtEnvUuid(resVe.getResTopologySid());
//        	Object obj = new Object();
//        	try {
//    			obj = MQHelper.rpc(tenantCreate);
//    		} catch (MQException e) {
//    			e.printStackTrace();
//    		}
//        	TenantCreateResult result = (TenantCreateResult)obj;
//        	if(result.isSuccess()){
//        		record.setUuid(result.getId());
//        		return this.mgtObjMapper.updateByPrimaryKeySelective(record);
//        	}else{
//        		 // 删除掉mgtObj
//        		this.mgtObjMapper.deleteByPrimaryKey(record.getMgtObjSid());
//        	}
//    	}
//    	return recordresult;
    }

    public List<ResTopology> selectResZoneTopologyByParams(Criteria example) {
    	return this.mgtObjMapper.selectResZoneTopologyByParams(example);
    }
    
    public List<ResTopology> selectVeListByHost(Criteria example) {
        return this.mgtObjMapper.selectVeListByHost(example);
    }

	@Override
	public List<MgtObj> selectByBillingAccount(Criteria example) {
		return this.mgtObjMapper.selectByBillingAccount(example);
	}

	@Override
	public List<MgtObj> selectAllProject(Criteria example) {
		return this.mgtObjMapper.selectAllProject(example);
	}

	@Override
	public List<ResTopology> selectMgtObjComByParams(Criteria example) {
		return this.mgtObjMapper.selectMgtObjComByParams(example);
	}

	@Override
	public int deleteLocalDataByPrimaryKey(Long mgtObjSid) {
		return this.mgtObjMapper.deleteByPrimaryKey(mgtObjSid);
	}
	
	public MgtObj selectFirstUserMgtObj(Long userSid) {
		MgtObj mgtObj = new MgtObj();
		Criteria example = new Criteria();
		example.put("userSid", userSid);
		List<UserMgtObjKey> userMgtObjs = userMgtObjMapper.selectByParams(example);
		if(!CollectionUtils.isEmpty(userMgtObjs)){
			mgtObj = mgtObjMapper.selectByPrimaryKey(userMgtObjs.get(0).getMgtObjSid());
		}
		return mgtObj;
	}

	@Override
	public List<MgtObj> selectMgtObjTreeByParams2(Criteria criteria) {
		return this.mgtObjMapper.selectMgtObjTreeByParams2(criteria);
	}

	@Override
	public List<ResTopology> selectMgtObjResZoneTopologyByParams(
			Criteria example) {
		return this.mgtObjMapper.selectMgtObjResZoneTopologyByParams(example);
	}

	@Override
	public List<MgtObj> selectBaseFileByParams(Criteria condition) {
		return this.mgtObjMapper.selectBaseFileByParams(condition);
	}

	@Override
	public int changeManager(String mgtObjSid, String newManagerSid, String oldManagerSid) {
//		MgtObj mgtObj = mgtObjMapper.selectByPrimaryKey(Long.parseLong(mgtObjSid));
//		ServiceInstanceChangeLog changeLog = new ServiceInstanceChangeLog();
//		changeLog.setInstanceSid(mgtObj.getMgtObjSid());
//		changeLog.setChangeSponsor(AuthUtil.getAuthUser().getAccount());
//		changeLog.setChangeType(WebConstants.instanceChangeType.CHANGE_MANAGER);
//		changeLog.setChangeBeginTime(new Date());
//		Map map= new HashMap();
//		map.put("mgtObjSid", mgtObjSid);
//		map.put("oldUserSid", oldManagerSid);
//		map.put("newUserSid", newManagerSid);
//		changeLog.setChangeSpec(JsonUtil.toJson(map));
//		changeLog.setStatus(WebConstants.instanceChangeStatus.UNCHANGE);
//		WebUtil.prepareInsertParams(changeLog);
//		int result = this.instanceChangeLogService.insertSelective(changeLog);
//		approveRecordService.initChangeApprove(mgtObjSid, changeLog.getChangeLogSid(), WebConstants.ProcessType.MANAGER_CHANGE);
//		return result;
		return 1;
	}
}