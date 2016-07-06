package com.h3c.idcloud.core.service.res.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.res.dao.ResBizVmMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResBizVm;
import com.h3c.idcloud.core.service.res.api.ResBizVmService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service(version = "1.0.0")
@Component
public class ResBizVmServiceImpl implements ResBizVmService {

	@Autowired
	private ResBizVmMapper resbizvmMapper;
//
//	@Autowired
//	private UserMapper userMapper;
//
//	@Autowired
//	private ServiceInstanceMapper serviceInstanceMapper;
//
//	@Autowired
//	private ServiceInstanceSpecMapper serviceInstanceSpecMapper;
//
//	@Autowired
//	private ServiceOperationMapper serviceOperationMapper;
//
//	@Autowired
//	private ServiceInstResMapper serviceInstResMapper;
//
//	@Autowired
//	private ResVdMapper resVdMapper;
//
//	@Autowired
//	private ResStorageMapper resStorageMapper;
//
//	@Autowired
//	private BusinesstypeService bizService;
//
//	@Autowired
//	private CodeService codeService;
//
//	/** 资源申请接口Service */
//	@Autowired
//	private ResVmService resVmService;
//
//	private static final Logger logger = LoggerFactory
//			.getLogger(ResBizVmServiceImpl.class);
//
//	public int countByParams(Criteria example) {
//		int count = 0;
//		try {
//			count = this.resbizvmMapper.countByParams(example);
//		} catch (Exception e) {
//			logger.info("---- 调用存储过程产生死锁！");
//		}
//		logger.debug("count: {}", count);
//		return count;
//	}
//
//	public ResBizVm selectByPrimaryKey(String ResBizVmSid) {
//		return this.resbizvmMapper.selectByPrimaryKey(ResBizVmSid);
//	}
//
//	public List<ResBizVm> selectByParams(Criteria example) {
//		List<ResBizVm> list = new ArrayList<ResBizVm>();
//		try {
//			 list = this.resbizvmMapper.selectByParams(example);
//		} catch (Exception e) {
//			logger.info("------ 调用存储过程产生死锁！",e);
//		}
//		return list;
//
//	}
//
//	public int deleteByPrimaryKey(String ResBizVmSid) {
//		return this.resbizvmMapper.deleteByPrimaryKey(ResBizVmSid);
//	}
//
//	public int updateByPrimaryKeySelective(ResBizVm record) {
//		return this.resbizvmMapper.updateByPrimaryKeySelective(record);
//	}
//
//	public int deleteByParams(Criteria example) {
//		return this.resbizvmMapper.deleteByParams(example);
//	}
//
//	public int updateByParamsSelective(ResBizVm record, Criteria example) {
//		return this.resbizvmMapper.updateByParamsSelective(record,
//				example.getCondition());
//	}
//
//	public int updateByParams(ResBizVm record, Criteria example) {
//		return this.resbizvmMapper.updateByParams(record, example.getCondition());
//	}
//
//	public int insert(ResBizVm record) {
//		return this.resbizvmMapper.insert(record);
//	}
//
//	public int insertSelective(ResBizVm record) {
//		return this.resbizvmMapper.insertSelective(record);
//	}
//
//	public ResBizVm getVmInfo(ResBizVm ResBizVm) throws Exception {
//		return null;
//	}
//
//	@Override
//	public int updateByPrimaryKey(ResBizVm paramResBizVm) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Transactional
//	public int createRelation(ResBizVmTO resBizVmTO) {
//		Long bizSid = resBizVmTO.getBizSid();
////		User user = findUser(bizSid);
//		String tenantSid = null;
//		String account = resBizVmTO.getAccount();
////		if (user != null ){
////			tenantSid = user.getTenantSid().toString();
////			account = user.getAccount();
////		}
//
//		int result = 0;
//
//		try {
//			for (ResVm resBizVm : resBizVmTO.getResVmList()) {
//				String resVmSid = resBizVm.getResVmSid();
//				ResVm vmInfo = resVmService.getVmInfo(resVmSid);
//				List<ResIp> resIpList = vmInfo.getResIpList();
//				String internetIp = ResDataUtils.getInternetIp(resIpList);
//				String intranetIp = ResDataUtils.getIntranetIp(resIpList);
//				//服务实例
//				ServiceInstance serviceInstance = new ServiceInstance();
//				WebUtil.prepareInsertParams(serviceInstance);
//				serviceInstance.setServiceSid(WebConstants.ServiceSid.SERVICE_VM);
//				serviceInstance.setTemplateSid(Long.parseLong(PropertiesUtil.getProperty("idc.service.template.sid")));
//				serviceInstance.setInstanceName(resBizVm.getVmName());
//				serviceInstance.setDescription("外部导入");
//				serviceInstance.setOwnerId(account);
//				serviceInstance.setStatus(WebConstants.SERVICE_INSTANCE_CD.OPENED);
//				serviceInstance.setTanentId(tenantSid);
////				serviceInstance.setBizSid(bizSid);
//				serviceInstance.setMgtObjSid(bizSid);
//				serviceInstance.setDredgeDate(new Date(new Date().getTime()+300000));
//				serviceInstance.setCreatedBy(PropertiesUtil.getProperty("idc.created.by"));
//				String internetNetworkSid = ResDataUtils.getInternetNetworkSid(resIpList);
//				if(internetNetworkSid != null) {
//					serviceInstance.setInternetVlan(internetNetworkSid);
//				}
//				String intranetNetworkSid = ResDataUtils.getIntranetNetworkSid(resIpList);
//				if(intranetNetworkSid != null) {
//					serviceInstance.setIntranetVlan(intranetNetworkSid);
//				}
//
//				int i = this.serviceInstanceMapper
//						.insertSelective(serviceInstance);
//
//				Long instanceSid = serviceInstance.getInstanceSid();
//
//				//服务实例资源
//				ServiceInstRes serviceInstRes = new ServiceInstRes();
//				serviceInstRes.setInstanceSid(instanceSid);
//				serviceInstRes.setResId(resBizVm.getResVmSid());
//				serviceInstRes.setResType(WebConstants.ResourceType.RES_VM);
//				int j = this.serviceInstResMapper
//						.insertSelective(serviceInstRes);
//
//				//服务变更操作
//				ServiceOperation serviceOperation = new ServiceOperation();
//				WebUtil.prepareInsertParams(serviceOperation);
//				serviceOperation.setServiceSid(WebConstants.ServiceSid.SERVICE_VM);
//				serviceOperation.setName("import");
//				serviceOperation.setDescription("导入");
//				serviceOperation.setWsDefinition("/resbiz/vm/relateResBizVm");
//				serviceOperation.setCreatedBy(PropertiesUtil.getProperty("idc.created.by"));
//				int k = this.serviceOperationMapper
//						.insertSelective(serviceOperation);
//
//				//服务实例规格
//				ServiceInstanceSpec serviceInstanceSpec = new ServiceInstanceSpec();
//				WebUtil.prepareInsertParams(serviceInstanceSpec);
//				serviceInstanceSpec.setInstanceSid(instanceSid);
//				serviceInstanceSpec.setStatus(WebConstants.SpecStatus.valid);
//
//				serviceInstanceSpec.setName(WebConstants.InstanceSpecType.CPU);
//				serviceInstanceSpec.setDescription("CPU核数");
//				serviceInstanceSpec.setSequence("1");
//				serviceInstanceSpec.setUnit("核");
//				if(!StringUtil.isNullOrEmpty(resBizVm.getCpuCores())){
//					serviceInstanceSpec.setValue(resBizVm.getCpuCores().toString());
//				}
//
//				int l = this.serviceInstanceSpecMapper
//						.insertSelective(serviceInstanceSpec);
//
//				serviceInstanceSpec.setName(WebConstants.InstanceSpecType.MEMORY);
//				serviceInstanceSpec.setDescription("内存大小");
//				serviceInstanceSpec.setSequence("2");
//				serviceInstanceSpec.setUnit("GB");
//				if(!StringUtil.isNullOrEmpty(resBizVm.getMemorySize())){
//					int mem = BigDecimal.valueOf(resBizVm.getMemorySize()).divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_UP).intValue();
//					serviceInstanceSpec.setValue(mem+"");
//				}
//				int m = this.serviceInstanceSpecMapper
//						.insertSelective(serviceInstanceSpec);
//
//				serviceInstanceSpec.setName(WebConstants.InstanceSpecType.OS);
//				serviceInstanceSpec.setDescription("操作系统");
//				serviceInstanceSpec.setSequence("4");
//				serviceInstanceSpec.setUnit("无");
//				if(!StringUtil.isNullOrEmpty(resBizVm.getOsVersion())){
//					serviceInstanceSpec.setValue(resBizVm.getOsVersion());
//				}
//
//				int r = this.serviceInstanceSpecMapper
//						.insertSelective(serviceInstanceSpec);
//
//				serviceInstanceSpec.setName(WebConstants.InstanceSpecType.SYSTEM_DISK);
//				serviceInstanceSpec.setDescription("系统盘");
//				serviceInstanceSpec.setSequence("3");
//				serviceInstanceSpec.setUnit("GB");
//				serviceInstanceSpec.setValue(PropertiesUtil.getProperty("sys.disk.size"));
//
//				int s = this.serviceInstanceSpecMapper
//						.insertSelective(serviceInstanceSpec);
//
//				serviceInstanceSpec.setName(WebConstants.InstanceSpecType.NEED_LAN);
//				serviceInstanceSpec.setDescription("是否需要内网IP");
//				serviceInstanceSpec.setSequence("9");
//				serviceInstanceSpec.setUnit("无");
//				serviceInstanceSpec.setValue(StringUtil.isNullOrEmpty(intranetIp)?"0":"1");
//
//				int x = this.serviceInstanceSpecMapper
//						.insertSelective(serviceInstanceSpec);
//
//				serviceInstanceSpec.setName(WebConstants.InstanceSpecType.NEED_WAN);
//				serviceInstanceSpec.setDescription("是否需要外网IP");
//				serviceInstanceSpec.setSequence("9");
//				serviceInstanceSpec.setUnit("无");
//				serviceInstanceSpec.setValue(StringUtil.isNullOrEmpty(internetIp)?"0":"1");
//
//				int y = this.serviceInstanceSpecMapper
//						.insertSelective(serviceInstanceSpec);
//
//				Long totalSize = 0L;
//
//				List<ResVd> resVdList = vmInfo.getResVdList();
//
//				for (ResVd resVd : resVdList) {
//					//创建系统盘/数据盘服务实例
//						serviceInstance = new ServiceInstance();
//						WebUtil.prepareInsertParams(serviceInstance);
//						serviceInstance.setParentInstanceSid(instanceSid);
//						serviceInstance.setServiceSid(WebConstants.ServiceSid.SERVICE_STORAGE);
//						serviceInstance.setTemplateSid(Long.parseLong(PropertiesUtil.getProperty("idc.service.template.sid")));
//						serviceInstance.setInstanceName(resVd.getVdName());
//						serviceInstance.setDescription("外部导入");
//						serviceInstance.setOwnerId(account);
//						serviceInstance.setOrderId("Imported"
//								+ resBizVm.getResVmSid());
//						serviceInstance.setStatus(WebConstants.SERVICE_INSTANCE_CD.OPENED);
//						serviceInstance.setTanentId(tenantSid);
////						serviceInstance.setBizSid(bizSid);
//						serviceInstance.setMgtObjSid(bizSid);
//						serviceInstance.setDredgeDate(new Date(new Date().getTime()+300000));
//						serviceInstance.setCreatedBy(PropertiesUtil.getProperty("idc.created.by"));
//						this.serviceInstanceMapper
//								.insertSelective(serviceInstance);
//
//						//创建系统盘/数据盘服务实例资源
//						serviceInstRes = new ServiceInstRes();
//						serviceInstRes.setInstanceSid(serviceInstance.getInstanceSid());
//						serviceInstRes.setResId(resVd.getResVdSid());
//						serviceInstRes.setResType(WebConstants.ResourceType.RES_VD);
//						this.serviceInstResMapper
//								.insertSelective(serviceInstRes);
//
//						//创建系统盘/数据盘服务实例规格
//						serviceInstanceSpec = new ServiceInstanceSpec();
//						WebUtil.prepareInsertParams(serviceInstanceSpec);
//						serviceInstanceSpec.setStatus(WebConstants.SpecStatus.valid);
//
//						serviceInstanceSpec.setInstanceSid(serviceInstance.getInstanceSid());
//
//						serviceInstanceSpec.setName(WebConstants.InstanceSpecType.STORAGE_PURPOSE);
//						serviceInstanceSpec.setDescription("块存储用途");
//						serviceInstanceSpec.setSequence("1");
//						serviceInstanceSpec.setUnit("无");
//						serviceInstanceSpec.setValue(resVd.getStoragePurpose());
//
//						this.serviceInstanceSpecMapper.insertSelective(serviceInstanceSpec);
//
//						serviceInstanceSpec.setName(WebConstants.InstanceSpecType.STORAGE_TYPE);
//						serviceInstanceSpec.setDescription("块存储类型");
//						serviceInstanceSpec.setSequence("1");
//						serviceInstanceSpec.setUnit("无");
//						ResStorage resStorage = findResStorage(resVd.getAllocateResStorageSid());
//						if (resStorage != null) {
//							serviceInstanceSpec.setValue(resStorage.getStorageType());
//						}
//						else {
//							serviceInstanceSpec.setValue(WebConstants.VolumeType.DATASTORE);
//						}
//
//						this.serviceInstanceSpecMapper.insertSelective(serviceInstanceSpec);
//
//
//						serviceInstanceSpec.setName(WebConstants.InstanceSpecType.DISK_SIZE);
//						serviceInstanceSpec.setDescription("磁盘容量");
//						serviceInstanceSpec.setSequence("1");
//						serviceInstanceSpec.setUnit("GB");
//
//						if (WebConstants.StoragePurpose.SYSTEM_DISK.equals(resVd.getStoragePurpose())) {
//							serviceInstanceSpec.setValue(PropertiesUtil.getProperty("sys.disk.size"));
//						}
//						else if (WebConstants.StoragePurpose.DATA_DISK.equals(resVd.getStoragePurpose())) {
//							Long diskSize = resVd.getAllocateDiskSize();
//							serviceInstanceSpec.setValue(diskSize.toString());
//							totalSize += diskSize;
//						}
//
//
//						this.serviceInstanceSpecMapper.insertSelective(serviceInstanceSpec);
//
//						serviceInstanceSpec.setName(WebConstants.InstanceSpecType.PERF_LEVEL);
//						serviceInstanceSpec.setDescription("块存储性能保障等级");
//						serviceInstanceSpec.setSequence("1");
//						serviceInstanceSpec.setUnit("无");
//						serviceInstanceSpec.setValue("");
//
//						this.serviceInstanceSpecMapper.insertSelective(serviceInstanceSpec);
//				}
//
//				serviceInstanceSpec.setInstanceSid(instanceSid);
//				serviceInstanceSpec.setName(WebConstants.InstanceSpecType.DATA_DISK);
//				serviceInstanceSpec.setDescription("外置存储盘");
//				serviceInstanceSpec.setSequence("3");
//				serviceInstanceSpec.setUnit("GB");
//				serviceInstanceSpec.setValue(totalSize.toString());
//
//				int t = this.serviceInstanceSpecMapper
//						.insertSelective(serviceInstanceSpec);
//
//				if (1 == i && 1 == j && 1 == k && 1 == l && 1 == m && 1 == r && 1 == s && 1 == t && 1 == x && 1 == y) {
//					result = 1;
//				} else {
//					result = 0;
//				}
//
////				TkUtils.updateMonitorNode(resVmSid, bizSid);
//
//			}
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//			result = 0;
//		}
//		return result;
//
//	}
//
//	private User findUser(Long bizSid){
//		if (StringUtil.isNullOrEmpty(bizSid) ){
//			return null;
//		}
//		else {
//			Criteria example = new Criteria();
//			example.put("bizSid", bizSid);
//			List<User> userList = this.userMapper.selectByParams(example);
//			return userList.get(0);
//		}
//	}
//
//	private ServiceInstance findServiceInstance(String orderId){
//		Criteria example = new Criteria();
//		example.put("orderId", orderId);
//		List<ServiceInstance> serviceInstanceList = this.serviceInstanceMapper.selectByParams(example);
//		return serviceInstanceList.get(0);
//	}
//
//	private ResStorage findResStorage(String resStorageSid){
//		Criteria example = new Criteria();
//		example.put("resStorageSid", resStorageSid);
//		return this.resStorageMapper.selectByPrimaryKey(resStorageSid);
//	}
//
//	@Transactional
//	public int cancelRelation(ResBizVmTO resBizVmTO) {
//		int result = 0;
//		try {
//			for (ResBizVm resBizVm : resBizVmTO.getResBizVmList()) {
//				String resId = resBizVm.getResBizVmSid();
//				deleteServiceInstanceRelation(resId);
//				result = 1;
//			}
//		} catch (Exception e) {
//			result = 0;
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//	public void deleteServiceInstanceRelation(String resVmId) {
//		Criteria example = new Criteria();
//		example.put("resId", resVmId);
//		List<ServiceInstRes> serviceInstResList = this.serviceInstResMapper.selectByParams(example);
//		if (serviceInstResList.size() < 1){
//			logger.warn("resId no mapping service instance. resVmId=" + resVmId);
//			return;
//		}
//		ServiceInstRes serviceInstRes = serviceInstResList.get(0);
//
//		//删除虚机服务规格实例
//		Long instanceSid = serviceInstRes.getInstanceSid();
//		example = new Criteria();
//		example.put("instanceSid", instanceSid.toString());
//		this.serviceInstanceSpecMapper.deleteByParams(example);
//
//		//获得数据盘服务实例
//		example = new Criteria();
//		example.put("parentInstanceSid", instanceSid.toString());
//		List<ServiceInstance> serviceInstanceList = this.serviceInstanceMapper
//				.selectByParams(example);
//
//		//删除数据盘服务规格实例，服务实例资源和服务实例
//		for (ServiceInstance serviceInstance : serviceInstanceList) {
//			example = new Criteria();
//			example.put("instanceSid", serviceInstance.getInstanceSid()
//					.toString());
//			this.serviceInstanceSpecMapper.deleteByParams(example);
//			this.serviceInstResMapper.deleteByParams(example);
//			this.serviceInstanceMapper
//					.deleteByPrimaryKey(serviceInstance
//							.getInstanceSid());
//		}
//
//		//删除虚机服务实例资源
//		ServiceInstResKey key = new ServiceInstResKey();
//		key.setInstanceSid(instanceSid);
//		key.setResId(resVmId);
//		this.serviceInstResMapper.deleteByPrimaryKey(key);
//
//		//删除虚机服务实例
//		this.serviceInstanceMapper.deleteByPrimaryKey(instanceSid);
//	}
//
//	@Override
//	public List<ResBizVm> selectByParams2(Criteria example) {
//		return this.resbizvmMapper.selectByParams2(example);
//	}
//
//	@Override
//	public int setMonitorNode(ResBizVmTO resBizVmTO) {
//		System.out.println("setMonitorNode");
//		int result = 0;
//		try {
//			for (ResBizVm resBizVm : resBizVmTO.getResBizVmList()) {
//				String resVmSid = resBizVm.getResBizVmSid();
//				Long bizSid = resBizVm.getMgtObjSid();
//				TkUtils.updateMonitorNode(resVmSid, bizSid);
//			}
//			result = 1;
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			result = 0;
//		}
//		return result;
//	}
//
//	public int modifyVm(ResBizVmTO resBizVmTO) {
//		int result = 0;
//		try {
//			for (ResBizVm resBizVm : resBizVmTO.getResBizVmList()) {
//				Long instanceSid = resBizVm.getInstanceSid();
//				ServiceInstance instance = new ServiceInstance();
//				instance.setInstanceSid(instanceSid);
//				instance.setDredgeDate(DateUtils.parseDate(resBizVmTO.getCreateDate(), new String [] {"yyyy-MM-dd HH:mm:ss"}));
//				serviceInstanceMapper.updateByPrimaryKeySelective(instance);
//			}
//			result = 1;
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			result = 0;
//		}
//		return result;
//	}
//
//	@Override
//	public List<ResBizVm> selectByParamsForReport(Criteria example) {
//		return this.resbizvmMapper.selectByParamsForReport(example);
//	}
//
//	@Override
//	public List<ResBizVm> selectSumByParams(Criteria example) {
//		return this.resbizvmMapper.selectSumByParams(example);
//	}

	@Override
	public ResBizVm statisticalBizOfVM(Long resBizSid) {
		return this.resbizvmMapper.statisticalBizOfVM(resBizSid);
	}

	@Override
	public List<ResBizVm> selectNanotubeableVmInBiz(Criteria paramCriteria) {
		return this.resbizvmMapper.selectNanotubeableVmInBiz(paramCriteria);
	}
//
//	@Override
//	public List<ResBizVm> selectByParamsForList(Criteria params) {
//		return this.resbizvmMapper.selectByParamsForList(params);
//	}
//
//	@Override
//	public List<ResBizVm> selectByParamsForPortal(Criteria example) {
//		return this.resbizvmMapper.selectByParamsForPortal(example);
//	}
//
}
