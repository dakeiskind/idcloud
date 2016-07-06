package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.google.common.base.Strings;
import com.h3c.idcloud.core.persist.charge.dao.BillingAccountMapper;
import com.h3c.idcloud.core.persist.charge.dao.ServiceBillMapper;
import com.h3c.idcloud.core.persist.order.dao.OrderDetailMapper;
import com.h3c.idcloud.core.persist.product.dao.ServiceInstResMapper;
import com.h3c.idcloud.core.persist.product.dao.ServiceInstanceHistoryMapper;
import com.h3c.idcloud.core.persist.product.dao.ServiceInstanceMapper;
import com.h3c.idcloud.core.persist.user.dao.UserMapper;
import com.h3c.idcloud.core.pojo.common.ServiceBaseInput;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccount;
import com.h3c.idcloud.core.pojo.dto.charge.ServiceBill;
import com.h3c.idcloud.core.pojo.dto.order.OrderDetail;
import com.h3c.idcloud.core.pojo.dto.product.*;
import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.core.pojo.dto.res.ResStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResVd;
import com.h3c.idcloud.core.pojo.dto.system.Code;
import com.h3c.idcloud.core.pojo.dto.system.LogRecord;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.system.MgtObjExt;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.instance.*;
import com.h3c.idcloud.core.service.product.api.MgtObjResService;
import com.h3c.idcloud.core.service.product.api.ServiceDefineService;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceChangeLogService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceSpecService;
import com.h3c.idcloud.core.service.product.api.ServiceSpecService;
import com.h3c.idcloud.core.service.product.api.VmOperateHanlder;
import com.h3c.idcloud.core.service.res.api.ResFloatingIpService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.core.service.system.api.CodeService;
import com.h3c.idcloud.core.service.system.api.MgtObjExtService;
import com.h3c.idcloud.core.service.system.api.MgtObjService;
import com.h3c.idcloud.core.service.system.api.SidService;
import com.h3c.idcloud.core.service.ticket.api.TicketService;
import com.h3c.idcloud.infra.log.observable.ActionTraceObservable;
import com.h3c.idcloud.infrastructure.common.cache.JedisUtil;
import com.h3c.idcloud.infrastructure.common.constants.BusinessMessageConstants;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.exception.ServiceException;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.UuidUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(version = "1.0.0")
@Component
public class ServiceInstanceServiceImpl implements ServiceInstanceService {


    private final static String MAX_RETRY_TIMES_PRE = "MAX_RETRY_TIMES_";
    private final static int MAX_RETRY_TIMES = 3;

    @Reference(version = "1.0.0")
    ServiceInstResService serviceInstResService;
    @Reference(version = "1.0.0")
//	@Qualifier("vmOperateHandlerImpl")
            VmOperateHanlder vmOperateHanlder;

    @Autowired
    private ServiceInstanceHistoryMapper serviceInstanceHistoryMapper;

    @Autowired
    ServiceInstResMapper serviceInstResMapper;

    @Reference(version="1.0.0")
    ResFloatingIpService resFloatingIpService;

    @Autowired
    private UserMapper userMapper;

    @Reference(version = "1.0.0")
    ResVmService resVmService;

    @Reference(version = "1.0.0")
    TicketService ticketService;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private BillingAccountMapper billingAccountMapper;

    @Autowired
    private ServiceBillMapper serviceBillMapper;

    @Autowired
    private ServiceInstanceMapper serviceInstanceMapper;



    @Override
    public boolean releaseService(Long serviceInstaceSid, String serviceType) {
        return false;
    }

    @Override
    public boolean releaseService(Long serviceInstaceSid) {
        return false;
    }




//	@Override
//	public boolean createServiceInstance(OrderDetail orderDetail) throws Exception {
//		return false;
//	}

    @Override
    public boolean updateServiceInstance(OrderDetail orderDetail) throws Exception {
        return false;
    }

    @Override
    public Long createHostAndStorageServiceInstance(OrderDetail orderDetail, HashMap<String, Object> hostDiskMap) {
        return null;
    }

    @Override
    public boolean serviceInstanceOperation(Map<String, Object> params, AuthUser authUser) throws IOException {
        boolean flag = true;
        String action = null;
        String instanceName=null;
        try {
            // 标识资源实例是否全部启用成功
            // 操作时，action和serviceInstanceSid是必须的 而且需要先查询服务实例下的资源实例
            // 页面参数
            action = Strings.nullToEmpty((String) params.get("action"));
            instanceName=Strings.nullToEmpty((String) params.get("instanceName"));
            String instanceSid = Strings.nullToEmpty((String) params.get("serviceInstanceSid"));
            String rebootType = Strings.nullToEmpty((String) params.get("rebootType"));

            // 底层基础类
            ServiceBaseInput baseInput = new ServiceBaseInput();
            baseInput.setMgtObjSid(authUser.getMgtObjSid());
            baseInput.setUserId(String.valueOf(authUser.getUserSid()));
            baseInput.setUserAccount(authUser.getAccount());
            baseInput.setUserPass(authUser.getPassword());
            baseInput.setZoneId((String) params.get("zone"));

            //根据虚拟机实例id查询出关联的虚拟机资源id
            Criteria criteria = new Criteria();
            criteria.put("instanceSid", instanceSid);
            List<ServiceInstRes> serviceInstReses = serviceInstResService.selectByParams(criteria);
            String vmResId = null;
            if (serviceInstReses.size() > 0) {
                vmResId = serviceInstReses.get(0).getResId();
            }
            if (vmResId == null) {
                throw new RuntimeException("The instanceSid no mapping in service_inst_res table instanceSid=" + instanceSid);
            }
            vmOperateHanlder.invoke(vmResId, baseInput, action, rebootType);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            flag = false;
        }

        //插入log日志信息
        ActionTraceObservable logObservable = ActionTraceObservable.getActionTraceObservable();
        LogRecord logInfo=null;
        if(action.equals("start")) {
            logInfo = ActionTraceObservable.getLogRecord(authUser,
            WebConstants.ACT_TARGET.VM_MANAGEMENT,
            WebUtil.getMessage(
                   BusinessMessageConstants.VmMessage.VM_START),
            WebUtil.getMessage(
                   BusinessMessageConstants.VmMessage.VM_START)+" "+instanceName
            , new Date(), new Date());
        }else if(action.equals("stop")){
            logInfo = ActionTraceObservable.getLogRecord(authUser,
            WebConstants.ACT_TARGET.VM_MANAGEMENT,
            WebUtil.getMessage(
                 BusinessMessageConstants.VmMessage.VM_STOP),
            WebUtil.getMessage(
                 BusinessMessageConstants.VmMessage.VM_STOP)+" "+instanceName
                    , new Date(), new Date());
        }else if(action.equals("reboot")){
            logInfo = ActionTraceObservable.getLogRecord(authUser,
             WebConstants.ACT_TARGET.VM_MANAGEMENT,
             WebUtil.getMessage(
                     BusinessMessageConstants.VmMessage.VM_RESTART),
             WebUtil.getMessage(
                     BusinessMessageConstants.VmMessage.VM_RESTART)+" "+instanceName
                    , new Date(), new Date());
        }
        logObservable.addLogQueue(logInfo,logObservable);

        return flag;
    }

    @Override
    public void createServiceInstanceCancel(String serviceInstanceSid) {

    }

    @Override
    public boolean modifyResInsAndSerInsName(long instanceSid, String newName, String resDescription) throws Exception {
        return false;
    }

    @Override
    public List<ServiceInstance> countServiceByParams(Criteria example) {
        return null;
    }

    @Override
    public List<ServiceInstance> selectVolumeStorageInfo(Criteria example) {
        return this.serviceInstanceMapper.selectVolumeStorageInfo(example);
    }

    @Override
    public List<ServiceDefine> selectedTarfgetHost(Criteria example) {
        return null;
    }

    @Override
    public ServiceInstance selectedSPAddressByServiceSid(Criteria example) {
        return null;
    }

    @Override
    public List<ServiceInstance> countTenantServiceVmByParams(Criteria example) {
        return null;
    }

    @Override
    public List<ServiceInstance> countTenantServiceStByParams(Criteria example) {
        return null;
    }

    @Override
    public List<ServiceInstance> countTenantServiceVmCpuByParams(Criteria example) {
        return null;
    }

    @Override
    public List<ServiceInstance> countTenantServiceVmMemoryByParams(Criteria example) {
        return null;
    }

    @Override
    public List<ServiceInstance> countTenantServiceStDiskByParams(Criteria example) {
        return null;
    }

    @Override
    public List<ServiceInstance> countTenantServiceVmQuotaByParams(Criteria example) {
        return null;
    }

    @Override
    public List<ServiceInstance> countTenantServiceStQuotaByParams(Criteria example) {
        return null;
    }

    @Override
    public List<ServiceInstance> countTenantServiceVmCpuQuotaByParams(Criteria example) {
        return null;
    }

    @Override
    public List<ServiceInstance> countTenantServiceVmMemoryQuotaByParams(Criteria example) {
        return null;
    }

    @Override
    public List<ServiceInstance> countTenantServiceStDiskQuotaByParams(Criteria example) {
        return null;
    }

    @Override
    public Map<String, Object> getTenantUserQuotaListByParams(String params, User user) {
        return null;
    }

    @Override
    public List<ServiceInstance> removeDuplicateData(List<ServiceInstance> list) {
        return null;
    }

    @Override
    public List<ServiceInstance> selectServiceInstanceForNeedBalance(Criteria example) {
        return null;
    }

    @Override
    public List<ServiceInstance> selectServiceInstanceForApprove(Criteria example) {
        return null;
    }

    @Override
    public Map<String, Object> checkAllQuota(String orderId) {
        return null;
    }

    @Override
    public Map<String, Object> checkAllQuota(Long bizSid, List<Map<String, Object>> curOrderQuotaList) {
        return null;
    }

    @Override
    public Boolean checkOrderSuccess(String orderId, Long serviceSid) {
        return null;
    }

    @Override
    public Boolean checkVmServiceInstanceSuccess(Long instanceSid) {
        return null;
    }

    @Override
    public void modifyVm(List<Map<String, Object>> instanceList) {

    }

    @Override
    public List<ServiceInstance> selectByParamsForBar(Criteria example) {
        return null;
    }

    @Override
    public void insertApprove(Long instanceSid) {

    }

    @Override
    public void modifyAllInstanceStatus(ServiceInstance instance) {

    }

    @Override
    public Boolean checkUnsubscribeVmSuccess(String orderId) {
        return null;
    }

    @Override
    public List<ServiceInstance> selectByParamsOrderByPurpose(Criteria criteria) {
        return null;
    }

    @Override
    public List<ServiceInstance> selectCountByParams(Criteria example) {
        return null;
    }

    @Override
    public void modifyInstanceSpec(Map map) {

    }

    @Override
    public String selectVdNameByInstanceSid(Long instanceSid) {
        return null;
    }

    @Override
    public List<ServiceInstance> selectServiceCountByParams(Criteria example) {
        return null;
    }

    @Override
    public List<ServiceInstance> selectServiceInstanceByMgtObjSid(Long mgtObjSid) {
        return null;
    }

    @Override
    public ServiceInstance selectObjStorageByMgtobjSid(Criteria example) {
        return null;
    }

    @Override
    public List<ServiceInstance> selectFloatingIpInfo(Criteria example) {
        return this.serviceInstanceMapper.selectFloatingIpInfo(example);
    }

    @Override
    public void cancalCDN() {

    }

    @Override
    public void adjustServiceSpec(ServiceInstance instance, ServiceSpecChange<?> serviceInstanceChange, Long originalChangeLogSid) {

    }

    @Override
    public ServiceInstanceChangeLogSpec<ResVmInst> assembleServiceInstanceOpenParams(ServiceInstance serviceInstance, Map<String, Object> map) {
        return null;
    }

    @Override
    public Long insertVdInstanceAndSpec(ResVdInst vd, ServiceInstance serviceInstance) {
        return null;
    }

//	@Override
//	public ServiceInstanceChangeLogSpec<ResVmInst> assembleServiceInstanceOpenParams(ServiceInstance serviceInstance, Map<String, Object> map) {
//		return null;
//	}

//	@Override
//	public Long insertVdInstanceAndSpec(ResVdInst vd, ServiceInstance serviceInstance) {
//		return null;
//	}

    @Override
    public void modifyDiskServiceInstancesOfVmStatus(Long instanceSid, String status) {

    }

    @Override
    public void modifyAllChildServiceInstancesOfVmStatus(Long instanceSid, String status) {

    }

    @Override
    public void modifyOrderStatusOfVmServiceInstance(String orderId) {

    }

    @Override
    public void modifyVmServiceInstanceStatus(ServiceInstance vmServiceInstance) {

    }

    @Override
    public boolean insertResHostMgtObjRel(ResHost resHost, List<ResStorage> sysSt, List<ResStorage> dataSt, String mgtObjSid, String account) {
        return false;
    }

    @Override
    public List<ServiceInstance> selectInstVmByParams(Criteria example) {
        return this.serviceInstanceMapper.selectInstVmByParams(example);
    }

    @Override
    public List<ServiceInstance> selectInstHostByParams(Criteria example) {
        return this.serviceInstanceMapper.selectInstHostByParams(example);
    }

    @Override
    public List<ServiceInstance> countInstVmByParams(Criteria example) {
        return null;
    }

    @Override
    public List<ServiceInstance> countInstHostByParams(Criteria example) {
        return null;
    }

    @Override
    public Integer countMgtObjInstVdByParams(Criteria param) {
        return null;
    }

    @Override
    public Integer getMaxVolumeIndex(List<ResVd> resVdList) {
        return null;
    }


    @Override
    public int updateInstanceBelongMgtObj(ServiceInstance instance, String mgtObjSid) {
        return 0;
    }

    //
//	/** 用户管理Mapper */
//	@Autowired
//	private UserMapper userMapper;
//
//	/** 配额Mapper */
//	@Autowired
//	private QuotaMapper quotaMapper;
//
//	/** 业务资源关联 Service */
    @Autowired
    private MgtObjResService mgtObjResService;
    ////
////	@Autowired
////	@Qualifier("vmDeleteHandlerImpl")
////	private ResourceRequestHanlder vmDeleteHandler;
////
////	@Autowired
////	@Qualifier("vdDeleteHandlerImpl")
////	private ResourceRequestHanlder vdDeleteHandler;
////
////	@Autowired
////	@Qualifier("cdnServiceDeleteHandlerImpl")
////	private ResourceRequestHanlder cdnDeleteHandler;
////
////	@Autowired
////	@Qualifier("vmChangeHandlerImpl")
////	private ResourceRequestHanlder vmChangeHanlder;
//
    @Reference(version = "1.0.0")
    private SidService sidService;
    //
////	@Autowired
////	private ResCdnInstService resCdnInstService;
//
//	@Autowired
//	private TicketMapper ticketMapper;
//
//	@Autowired
//	private ServiceInstanceSpecMapper instanceSpecMapper;
//
//	@Autowired
//	private ServiceInstanceSpecService instanceSpecService;
//
//	@Autowired
//	private ServiceChangeLogMapper serviceChangeLogMapper;
//
////	@Autowired
////	private ApproveRecordService approveRecordService;
//
//	@Reference(version = "1.0.0")
//	private ServiceInstResService serviceInstResService;
//
//	/** 闲置资源管理Service */
////	@Autowired
////	private FreeResService freeresService;
////
////	@Autowired
////	private FreeResCheckLogService checkLogService;
//
    @Reference(version = "1.0.0")
    private MgtObjService mgtObjService;
    //
////	@Autowired
////	private MailNotificationsService mailNotificationsService;
//
    @Autowired
    private ServiceInstanceChangeLogService instanceChangeLogService;
    //
////	@Autowired
////	@Qualifier("vmOperateHandlerImpl")
////	private VmOperateHanlder vmOperateHanlder;
////
////	@Autowired
////	@Qualifier("floatingIpDeleteHandlerImpl")
////	private ResourceRequestHanlder floatingIpDeleteHandler;
////
////	@Autowired
////	@Qualifier("vdExpandHandlerImpl")
////	private ResourceRequestHanlder vdExpandHandlerImpl;
//
    @Reference(version = "1.0.0")
    private CodeService codeService;


    //	@Reference(version = "1.0.0")
//	private OrderService orderService;
//
    @Reference(version = "1.0.0")
    private MgtObjExtService mgtObjExtService;

    private static final Logger logger = LoggerFactory
            .getLogger(ServiceInstanceServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.serviceInstanceMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ServiceInstance selectByPrimaryKey(Long instanceSid) {
        return this.serviceInstanceMapper.selectByPrimaryKey(instanceSid);
    }

    public ServiceInstance selectByPrimaryKeyInstance(Long instanceSid) {
        return this.serviceInstanceMapper.selectByPrimaryKeyInstance(instanceSid);
    }

    public List<ServiceInstance> selectByParams(Criteria example) {
        return this.serviceInstanceMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long instanceSid) {
        return this.serviceInstanceMapper.deleteByPrimaryKey(instanceSid);
    }

    public int updateByPrimaryKeySelective(ServiceInstance record) {
        return this.serviceInstanceMapper.updateByPrimaryKeySelective(record);
    }

    public int insertSelective(ServiceInstance record) {
        return this.serviceInstanceMapper.insertSelective(record);
    }

    public int deleteByParams(Criteria example) {
        return this.serviceInstanceMapper.deleteByParams(example);
    }

    @Reference(version = "1.0.0")
    private ServiceDefineService serviceDefineService;

    @Reference(version = "1.0.0")
    private ServiceInstanceSpecService serviceInstanceSpecService;

    @Reference(version = "1.0.0")
    private ServiceSpecService serviceSpecService;

    /**
     * 根据订单初始化服务实例
     *
     * @param orderDetail
     * @throws Exception
     */
    public boolean createServiceInstance(OrderDetail orderDetail, String ownerId) throws Exception {
        int result = 0;

        ServiceDefine serviceDefine = serviceDefineService.selectByPrimaryKey(orderDetail
                .getServiceSid());

        // 初始化服务实例
        ServiceInstance serviceInstance = new ServiceInstance();
        serviceInstance.setServiceSid(orderDetail.getServiceSid());
        serviceInstance.setTemplateSid(orderDetail.getTemplateSid());
//		serviceInstance.setBillingType(orderDetail.getBillingType());
//		serviceInstance.setBuyLength(orderDetail.getBuyLength());
        serviceInstance.setBillingTypeId("");
        // serviceInstance.setDescription(serviceDefine.getDescription());
        // 到期时间 未来24小时
        // 期望开通时间(未来24小时)
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 1);
        serviceInstance.setExpiringDate(c.getTime());
        serviceInstance.setOwnerId(ownerId);
        // 订单ID
        serviceInstance.setOrderId(orderDetail.getOrderId());
        // 订单明细ID
        serviceInstance.setDetailSid(orderDetail.getDetailSid());

        // 目标为空
        serviceInstance.setTarget("");
        // 租户ID

//		serviceInstance.setTanentId(user.getMgtObjSid().toString());

        // 服务开通时间为空
        serviceInstance.setDredgeDate(null);
        // 服务到期时间
        serviceInstance.setExpiringDate(null);

        // 实例创建开始结束时间
        serviceInstance.setCreationDateBegin(new Date());
        serviceInstance.setCreationDateEnd(new Date());

        serviceInstance.setStatus(WebConstants.ServiceInstanceStatus.PENDING);

        //serviceInstance.setContractId(orderDetail.getContractId());
        //serviceInstance.setProjectId(orderDetail.getProjectId());
        //serviceInstance.setContractFile(orderDetail.getContractFile());
        //serviceInstance.setFwPort(orderDetail.getFwPort());

        for (int i = 0; i < orderDetail.getQuantity(); i++) {

            // TODO
            //设置服务实例名称
            String insName = UuidUtil.getShortUuid("cs-");
            // TODO
            serviceInstance.setInstanceName(insName);
            /*Object instanceName = orderDetail.getSpecificationMaps().get("instanceName");
            if(instanceName == null || instanceName.toString().split("-")[0].equals("") ){
				serviceInstance.setInstanceName(insName);
			}else{
				serviceInstance.setInstanceName(instanceName.toString());
				insName = instanceName.toString();
			}
			 */

//			serviceInstance.setInstanceName(orderDetail
//					.getServiceInstanceMaps().get(i).get("instanceName")
//					.toString());
            /*
			// 服务实例期望开通时间
			serviceInstance.setExpectedTime(StringUtil.strToDate(orderDetail
					.getServiceInstanceMaps().get(i).get("expectedTime")
					.toString()));
            */

            //当申请CDN服务的时候，不需要判断租户是否关联计算资源
//			if(!WebConstants.ServiceSid.SERVICE_CDN.equals(orderDetail.getServiceSid())){
//				Criteria bizResourceQueryParam = new Criteria();
//				bizResourceQueryParam.put("bizSid", orderDetail.getMgtObjSid());
//				bizResourceQueryParam.put("resCategory", 0);
//				List<MgtObjRes> bizResoucelist = mgtObjResService.selectBizReses(bizResourceQueryParam);
//				if(bizResoucelist.size() == 0) {
//					throw new ServiceException("您所属的项目未关联可用计算资源集，暂不能提交订单!");
//				}
//			}

//			if(orderDetail.getSpecificationMaps().get("NEED_WAN").equals("1")){
//				bizResourceQueryParam = new Criteria();
//				bizResourceQueryParam.put("bizSid", AuthUtil.getAuthUser().getBizSid());
//				bizResourceQueryParam.put("resCategory", 1);
//				bizResoucelist = bizService.selectBizReses(bizResourceQueryParam);
//				if(bizResoucelist.size() > 0) {
//					serviceInstance.setInternetVlan(bizResoucelist.get(0).getResSetSid());
//				}
//			}
//			if(orderDetail.getSpecificationMaps().get("NEED_LAN").equals("1")){
//				Criteria bizResourceQueryParam = new Criteria();
//				bizResourceQueryParam = new Criteria();
//				bizResourceQueryParam.put("bizSid", orderDetail.getMgtObjSid());
//				bizResourceQueryParam.put("resCategory", 2);
//				List<MgtObjRes> bizResoucelist = mgtObjResService.selectBizReses(bizResourceQueryParam);
//				if(bizResoucelist.size() > 0) {
//					throw new ServiceException("您所属的项目未关联可用内网资源，暂不能提交订单!");
//				}
//			}
            serviceInstance.setExpectedTime(new Date());
            serviceInstance.setParentInstanceSid(null);

            // 设置region、zone 、billingType by jipeigui
            serviceInstance.setBillingType("Month");// TODO 当前写死按月
            String region = (String) orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.REGION);
            serviceInstance.setRegionSid(region);
            String zone = (String) orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.ZONE);
            serviceInstance.setZoneSid(zone);

            //设置虚拟机主机名
			/*
			 * x86虚拟机名称：项目名称-项目英文-系统版本-版本位数-顺序号
			 * Power分区名称：项目英文-系统版本-顺序号
			 */
            String osVersion = (String) orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.OS);
            Criteria criteria = new Criteria();
            criteria.put("codeValue", osVersion);
            List<Code> codeList = this.codeService.getParentCodeByCodeVaule(criteria);
            Code versionCode = this.codeService.getCodeByValue(osVersion, WebConstants.CodeCategroy.OS_VERSION);
            String resInstName = "";
            if (codeList.size() > 0) {
                Code parentCode = codeList.get(0);
                String ve = parentCode.getAttribute1();
                Long mgtObjSid = Long.parseLong(orderDetail.getMgtObjSid());
                MgtObj mgtObj = this.mgtObjService.selectByPrimaryKey(mgtObjSid);
                criteria = new Criteria();
                criteria.put("mgtObjSid", mgtObj.getMgtObjSid());
                List<MgtObjExt> mgtObjExts = this.mgtObjExtService.selectByParams(criteria);
                String projectEName = "";
                for (MgtObjExt mgtObjExt : mgtObjExts) {
                    if ("projectEName".equals(mgtObjExt.getAttrKey())) {
                        projectEName = mgtObjExt.getAttrValue();
                        break;
                    }
                }
                DecimalFormat format = new DecimalFormat("00");
                if (WebConstants.VirtualEnv.POWER.equals(ve)) {
                    resInstName = projectEName.toUpperCase() + "-" + versionCode.getAttribute2().toUpperCase() + "-" + format.format(getVmHostNameSeq());
                } else if (WebConstants.VirtualEnv.X86.equals(ve)) {
                    resInstName = mgtObj.getName().toUpperCase() + "-" + projectEName.toUpperCase() + "-" + versionCode.getAttribute2().toUpperCase() + "-" + format.format(getVmHostNameSeq());
                }
            }
            serviceInstance.setResInstName(resInstName);

            // TODO
			/*
			if (!StringUtil.isNullOrEmpty(orderDetail.getServiceInstanceMaps().get(i).get("fwPort"))){
				serviceInstance.setFwPort(orderDetail
						.getServiceInstanceMaps().get(i).get("fwPort")
						.toString());
			}
			*/

            serviceInstance.setMgtObjSid(Long.parseLong(orderDetail.getMgtObjSid()));
            WebUtil.prepareInsertParams(serviceInstance);

            // insert服务实例数据到服务实例表
            result = result
                    + this.serviceInstanceMapper
                    .insertSelective(serviceInstance);
            //返回VM服务实例Sid
            Long instanceSid = serviceInstance.getInstanceSid();
            orderDetail.setVmServiceInstanceSid(instanceSid);
            // insert服务规格数据到服务规格表
            criteria = new Criteria();
            criteria.put("serviceSid", orderDetail.getServiceSid());
            List<ServiceSpec> serviceSpecList = serviceSpecService.selectByParams(criteria);
            // 生成服务规格
            if (WebConstants.ServiceSid.SERVICE_VM.equals(orderDetail.getServiceSid())) {

                List<HashMap<String, Object>> hostDisks = new ArrayList<HashMap<String, Object>>();
                hostDisks = (List<HashMap<String, Object>>) orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.DATA_DISK);
                String diskSuffix = "data-disk-";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                Integer totalDiskSize = 0;
                for (int j = 0; j < hostDisks.size(); j++) {
                    // 设置多条存储相关的服务实例和服务实例规格数据
                    Map<String, Object> hostDiskMap = hostDisks.get(j);
                    Date now = new Date();
                    String diskName = (String) hostDisks.get(j).get("diskName");
                    if (StringUtils.isBlank(diskName)) {
                        hostDisks.get(j).put("diskName", diskSuffix + sdf.format(now));
                    }
                    Integer diskSize = (Integer) hostDiskMap.get("diskSize");
                    totalDiskSize += diskSize;
                    createHostAndStorageServiceInstance(orderDetail, hostDisks.get(j));
                }

                // 生成vm服务实例规格
                for (ServiceSpec sSpec : serviceSpecList) {
                    ServiceInstanceSpec instanceSpec = new ServiceInstanceSpec();
                    instanceSpec.setInstanceSid(serviceInstance.getInstanceSid());
                    instanceSpec.setName(sSpec.getName());
                    instanceSpec.setDescription(sSpec.getDescription());
                    instanceSpec.setSequence(sSpec.getSequence());
                    instanceSpec.setTag(sSpec.getTag());
                    instanceSpec.setUnit(sSpec.getUnit());
                    // 设置服务实例规格中的值
                    if (WebConstants.InstanceSpecType.CPU.equals(instanceSpec.getName())) {
                        instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
                    } else if (WebConstants.InstanceSpecType.MEMORY.equals(instanceSpec.getName())) {
                        instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
                    } else if (WebConstants.InstanceSpecType.OS.equals(instanceSpec.getName())) {
                        instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
                    } else if (WebConstants.InstanceSpecType.SYSTEM_DISK.equals(instanceSpec.getName())) {
                        instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
                        String sysDisk = StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName()));
                        if (!StringUtil.isNullOrEmpty(sysDisk)) {
                            HashMap<String, Object> sysDiskMap = new HashMap<String, Object>();
                            sysDiskMap.put("diskName", serviceInstance.getInstanceName() + "-sysDisk");
                            sysDiskMap.put("storagePurpose", WebConstants.StoragePurpose.SYSTEM_DISK);
                            sysDiskMap.put("diskSize", sysDisk);
                            sysDiskMap.put("diskUrlName", "");
                            createHostAndStorageServiceInstance(orderDetail, sysDiskMap);
                        }
                    } else if (WebConstants.InstanceSpecType.KEY_PAIR.equals(instanceSpec.getName())) {
                        instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
                    } else if (WebConstants.InstanceSpecType.REMARK.equals(instanceSpec.getName())) {
                        instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
                    } else if (WebConstants.InstanceSpecType.RECOVERY_TYPE.equals(instanceSpec.getName())) {
                        instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
                    } else if (WebConstants.InstanceSpecType.REGION.equals(instanceSpec.getName())) {
                        instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
                    } else if (WebConstants.InstanceSpecType.ZONE.equals(instanceSpec.getName())) {
                        instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
                    } else if (WebConstants.InstanceSpecType.SECURITY_GROUP.equals(instanceSpec.getName())) {
                        instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
                    } else if (WebConstants.InstanceSpecType.INSTALL_SOFTWARE.equals(instanceSpec.getName())) {
                        //将安装的软件也改成服务实例
                        String softwares = StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName()));
                        if (!StringUtil.isNullOrEmpty(softwares)) {
                            String[] software = softwares.split(",");
                            if (software.length > 0) {
                                for (String soft : software) {
                                    ServiceInstance softInstance = new ServiceInstance();
                                    softInstance = (ServiceInstance) BeanUtils.cloneBean(serviceInstance);
                                    softInstance.setInstanceName(soft);
                                    softInstance.setResInstName(null);
                                    softInstance.setServiceSid(WebConstants.ServiceSid.SERVICE_DEPLOYMENT);
                                    softInstance.setStatus(WebConstants.ServiceInstanceStatus.PENDING);
                                    softInstance.setParentInstanceSid(instanceSid);
                                    this.serviceInstanceMapper.insertSelective(softInstance);
                                }
                            }
                        }
                        instanceSpec.setValue(softwares);
                    } else if (WebConstants.InstanceSpecType.NEED_LAN.equals(instanceSpec.getName())) {
                        instanceSpec.setValue("1");
                    } else if (WebConstants.InstanceSpecType.DATA_DISK.equals(instanceSpec.getName())) {
                        instanceSpec.setValue(totalDiskSize.toString());
                    }
                    instanceSpec.setGroupName(sSpec.getGroupName());
                    WebUtil.prepareInsertParams(instanceSpec);
                    serviceInstanceSpecService.insertSelective(instanceSpec);
                }


                ResVmInst resVmInst = new ResVmInst();

                //设置关联的计算资源
                criteria = new Criteria();
                criteria.put("bizSid", serviceInstance.getMgtObjSid());
                criteria.put("resCategory", "0");
                List<MgtObjRes> bizReses = mgtObjResService.selectByParams(criteria);
                List<String> allocateResHostIds = new ArrayList<String>();
                List<String> allocateResVcIds = new ArrayList<String>();
                for (MgtObjRes bizRes : bizReses) {
                    if (WebConstants.ResourceType.RES_HOST.equals(bizRes.getResSetType())) {
                        allocateResHostIds.add(bizRes.getResSetSid());
                    } else if (WebConstants.RES_TOPOLOGY_TYPE.VC.equals(bizRes.getResSetType())) {
                        allocateResVcIds.add(bizRes.getResSetSid());
                    }
                }
                resVmInst.setAllocateResHostIds(allocateResHostIds);
                resVmInst.setAllocateResVcIds(allocateResVcIds);
                //保存实例名称和初始密码
                resVmInst.setResVmInstName(serviceInstance.getResInstName());
                // TODO
				/*
				if (!StringUtil.isNullOrEmpty(orderDetail.getServiceInstanceMaps().get(i).get("hostPassword"))){
					resVmInst.setManagementPassword(orderDetail
							.getServiceInstanceMaps().get(i).get("hostPassword")
							.toString());
				}
				*/
                //设置CPU核数，内存， 操作系统
                resVmInst.setCpu(Integer.parseInt(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.CPU))));
                resVmInst.setMemory(Long.parseLong(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.MEMORY))) * 1024);
                resVmInst.setImageSid(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.OS)));
                if (!StringUtil.isNullOrEmpty(orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.KEY_PAIR))) {
                    resVmInst.setKeyPair(orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.KEY_PAIR).toString());
                }
                resVmInst.setRegion(!StringUtil.isNullOrEmpty(orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.REGION))
                        ? orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.REGION).toString() : "");
                resVmInst.setZoneId(!StringUtil.isNullOrEmpty(orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.ZONE))
                        ? orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.ZONE).toString() : "");
                resVmInst.setSecurityGroup(!StringUtil.isNullOrEmpty(orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.SECURITY_GROUP))
                        ? orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.SECURITY_GROUP).toString() : "");
                //设置系统盘
                List<ResVdInst> diskInstes = new ArrayList<ResVdInst>();
                ResVdInst vdInst = new ResVdInst();
                vdInst.setDiskSize(Long.parseLong(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.SYSTEM_DISK))));
                vdInst.setStoragePurpose(WebConstants.StoragePurpose.SYSTEM_DISK);
                diskInstes.add(vdInst);
                resVmInst.setDataDisks(diskInstes);

                //设置网络配置
                List<String> nets = (List<String>) orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.NETS);
                List<ResNetworkInst> netCongis = new ArrayList<ResNetworkInst>();
                for (String networkId : nets) {
                    ResNetworkInst networkInst = new ResNetworkInst();
                    networkInst.setResNetworkId(networkId);
                    netCongis.add(networkInst);
                }
                resVmInst.setNets(netCongis);
                //设置管理对象sid
                resVmInst.setMgtObjSid(serviceInstance.getMgtObjSid());
                resVmInst.setUserAccount(serviceInstance.getOwnerId());
                //页面添加的用户信息
                // TODO
                // List<VmUser> vmUsers = (List<VmUser>) orderDetail.getServiceInstanceMaps().get(i).get("instanceUser");
                // resVmInst.setVmUsers(vmUsers);

                //保存实例开通日志
                ServiceInstanceChangeLogSpec<ResVmInst> serviceInstanceChangeLogSpec = new ServiceInstanceChangeLogSpec<ResVmInst>();
                serviceInstanceChangeLogSpec.setParams(resVmInst);
                instanceChangeLogService.submitChangeServiceInstance(serviceInstance,
                        WebConstants.instanceChangeType.CREATE,
                        serviceInstanceChangeLogSpec);

            } else if (WebConstants.ServiceSid.SERVICE_OBJ_STORAGE.equals(orderDetail.getServiceSid())) {
                // 生成对象存储服务实例规格
                for (ServiceSpec sSpec : serviceSpecList) {
                    ServiceInstanceSpec instanceSpec = new ServiceInstanceSpec();
                    instanceSpec.setInstanceSid(serviceInstance.getInstanceSid());
                    instanceSpec.setName(sSpec.getName());
                    instanceSpec.setDescription(sSpec.getDescription());
                    instanceSpec.setSequence(sSpec.getSequence());
                    instanceSpec.setTag(sSpec.getTag());
                    instanceSpec.setUnit(sSpec.getUnit());

                    if (WebConstants.InstanceSpecType.OBJECT_DISK.equals(instanceSpec.getName())) {
                        instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
                    }

                    instanceSpec.setGroupName(sSpec.getGroupName());
                    WebUtil.prepareInsertParams(instanceSpec);
                    serviceInstanceSpecService.insertSelective(instanceSpec);
                }
                //保存实例变更日志
                instanceChangeLogService.submitChangeServiceInstance(serviceInstance,
                        WebConstants.instanceChangeType.CREATE,
                        null);

            } else if (WebConstants.ServiceSid.SERVICE_STORAGE.equals(orderDetail.getServiceSid())) {
                // 生成块存储服务实例规格
                for (ServiceSpec sSpec : serviceSpecList) {
                    ServiceInstanceSpec instanceSpec = new ServiceInstanceSpec();
                    instanceSpec.setInstanceSid(serviceInstance.getInstanceSid());
                    instanceSpec.setName(sSpec.getName());
                    instanceSpec.setDescription(sSpec.getDescription());
                    instanceSpec.setSequence(sSpec.getSequence());
                    instanceSpec.setTag(sSpec.getTag());
                    instanceSpec.setUnit(sSpec.getUnit());

                    if (WebConstants.InstanceSpecType.STORAGE_TYPE.equals(instanceSpec.getName())) {
                        instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
                    } else if (WebConstants.InstanceSpecType.DISK_SIZE.equals(instanceSpec.getName())) {
                        instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
                    } else if (WebConstants.InstanceSpecType.PERF_LEVEL.equals(instanceSpec.getName())) {
                        instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
                    }

                    instanceSpec.setGroupName(sSpec.getGroupName());
                    WebUtil.prepareInsertParams(instanceSpec);
                    serviceInstanceSpecService.insertSelective(instanceSpec);
                }

                ResVdInst resVdInst = new ResVdInst();
                resVdInst.setMgtObjSid(serviceInstance.getMgtObjSid());
                resVdInst.setResVdInstName(serviceInstance.getInstanceName());

                //设置存储大小
                resVdInst.setDiskSize(Long.parseLong(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.DISK_SIZE))));
                //设置存储分配目标
                criteria = new Criteria();
                criteria.put("bizSid", serviceInstance.getMgtObjSid());
                criteria.put("resCategory", "4");
//				criteria.put("resSetType", WebConstants.ResourceType.RES_HOST);
                List<MgtObjRes> mgtReses = mgtObjResService.selectByParams(criteria);
                List<String> allocateResStorageIds = new ArrayList<String>();
                for (MgtObjRes mgtRes : mgtReses) {
                    allocateResStorageIds.add(mgtRes.getResSetSid());
                }
                resVdInst.setAllocateResStorageIds(allocateResStorageIds);

                //保存实例变更日志
                ServiceInstanceChangeLogSpec<ResVdInst> serviceInstanceChangeLogSpec = new ServiceInstanceChangeLogSpec<ResVdInst>();
                serviceInstanceChangeLogSpec.setParams(resVdInst);
                instanceChangeLogService.submitChangeServiceInstance(serviceInstance,
                        WebConstants.instanceChangeType.CREATE,
                        serviceInstanceChangeLogSpec);
            } else if (WebConstants.ServiceSid.FLOATING_IP.equals(orderDetail.getServiceSid())) {
                // 浮动IP
                for (ServiceSpec sSpec : serviceSpecList) {
                    ServiceInstanceSpec instanceSpec = new ServiceInstanceSpec();
                    instanceSpec.setInstanceSid(serviceInstance.getInstanceSid());
                    instanceSpec.setName(sSpec.getName());
                    instanceSpec.setDescription(sSpec.getDescription());
                    instanceSpec.setSequence(sSpec.getSequence());
                    instanceSpec.setTag(sSpec.getTag());
                    instanceSpec.setUnit(sSpec.getUnit());

                    if (WebConstants.InstanceSpecType.TAPE_WIDTH.equals(instanceSpec.getName())) {
                        instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
                    } else if (WebConstants.InstanceSpecType.IP_COUNT.equals(instanceSpec.getName())) {
                        instanceSpec.setValue("1");
                    }

                    instanceSpec.setGroupName(sSpec.getGroupName());
                    WebUtil.prepareInsertParams(instanceSpec);
                    serviceInstanceSpecService.insertSelective(instanceSpec);

                }

                ResFloatingIpInst floatingIpInst = new ResFloatingIpInst();
                floatingIpInst.setMgtObjSid(serviceInstance.getMgtObjSid());

                //设置宽带规格，资源层接口暂时不支持宽带规格设置
//				List<ServiceInstanceSpec> instanceSpecs = instanceSpecService.selectByInstanceSpecBySid(serviceInstance.getInstanceSid());
//				String tapeWidth = ResDataUtils.getSpecValueFromSpecs("TAPE_WIDTH",instanceSpecs);

                //设置弹性IP网络
                criteria = new Criteria();
                criteria.put("bizSid", serviceInstance.getMgtObjSid());
                criteria.put("resCategory", "1");
//				criteria.put("resSetType", WebConstants.ResourceType.RES_HOST);
                List<MgtObjRes> mgtReses = mgtObjResService.selectByParams(criteria);
                List<String> resNetSids = new ArrayList<String>();
                for (MgtObjRes mgtRes : mgtReses) {
                    resNetSids.add(mgtRes.getResSetSid());
                }
                floatingIpInst.setResNetSids(resNetSids);

                //保存实例变更日志
                ServiceInstanceChangeLogSpec<ResFloatingIpInst> serviceInstanceChangeLogSpec = new ServiceInstanceChangeLogSpec<ResFloatingIpInst>();
                serviceInstanceChangeLogSpec.setParams(floatingIpInst);
                instanceChangeLogService.submitChangeServiceInstance(serviceInstance,
                        WebConstants.instanceChangeType.CREATE,
                        serviceInstanceChangeLogSpec);
            } else if (WebConstants.ServiceSid.SERVICE_CDN.equals(orderDetail.getServiceSid())) {
                // 浮动IP
                for (ServiceSpec sSpec : serviceSpecList) {
                    ServiceInstanceSpec instanceSpec = new ServiceInstanceSpec();
                    instanceSpec.setInstanceSid(serviceInstance.getInstanceSid());
                    instanceSpec.setName(sSpec.getName());
                    instanceSpec.setDescription(sSpec.getDescription());
                    instanceSpec.setSequence(sSpec.getSequence());
                    instanceSpec.setTag(sSpec.getTag());
                    instanceSpec.setUnit(sSpec.getUnit());

                    instanceSpec.setGroupName(sSpec.getGroupName());
                    WebUtil.prepareInsertParams(instanceSpec);
                    serviceInstanceSpecService.insertSelective(instanceSpec);
                }
            }


            List<HashMap<String, Object>> hostDisks = new ArrayList<HashMap<String, Object>>();
            hostDisks = (List<HashMap<String, Object>>) orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.DATA_DISK);
            String diskSuffix = "data-disk-";
            for (int j = 0; j < hostDisks.size(); j++) {
                // 设置多条存储相关的服务实例和服务实例规格数据
                String diskName = (String) hostDisks.get(j).get("diskName");
                if (StringUtils.isBlank(diskName)) {
                    hostDisks.get(j).put("diskName", diskSuffix + j);
                }
                createHostAndStorageServiceInstance(orderDetail, hostDisks.get(j));
            }

        }

        return result == orderDetail.getQuantity();
    }

    //
//
//	/**
//	 * 根据订单更新服务实例
//	 *
//	 * @param order
//	 * @throws ServiceException
//	 */
//	public boolean updateServiceInstance(OrderDetail orderDetail) throws Exception {
//		int result = 0;
//
//		// 取得服务定义信息
////		ServiceDefineService service = SpringContextHolder
////				.getBean("serviceDefineServiceImpl");
////		ServiceInstanceSpecService spec = SpringContextHolder
////				.getBean("serviceInstanceSpecServiceImpl");
////		ServiceSpecService serviceSpec = SpringContextHolder
////				.getBean("serviceSpecServiceImpl");
//////		User user = AuthUtil.getAuthUser();
////
////		ServiceDefine serviceDefine = service.selectByPrimaryKey(orderDetail
////				.getServiceSid());
////
////		// 初始化服务实例
////		Criteria params = new Criteria();
////		params.put("serviceSid", orderDetail.getServiceSid());
////		params.put("ownerId", user.getAccount());
////		params.put("orderId", orderDetail.getOrderId());
////		params.put("detailSid", orderDetail.getDetailSid());
////
////		List<ServiceInstance> instances = serviceInstanceMapper.selectByParams(params);
////
////		for (int i = 0; i < orderDetail.getQuantity(); i++) {
////			ServiceInstance serviceInstance = new ServiceInstance();
////			if(i<instances.size()){
////				serviceInstance = instances.get(i);
////				serviceInstance.setStatus(WebConstants.ServiceInstanceStatus.PENDING);
////				serviceInstance.setInstanceName(orderDetail
////						.getServiceInstanceMaps().get(i).get("instanceName")
////						.toString());
////				result = result
////						+ this.serviceInstanceMapper
////						.updateByPrimaryKeySelective(serviceInstance);
////			}else{
////				serviceInstance.setServiceSid(orderDetail.getServiceSid());
////				serviceInstance.setTemplateSid(orderDetail.getTemplateSid());
////				serviceInstance.setBillingTypeId("");
////				// 期望开通时间(未来24小时)
////				Calendar c = Calendar.getInstance();
////				c.setTime(new Date());
////				c.add(Calendar.DAY_OF_MONTH, 1);
////				serviceInstance.setExpiringDate(c.getTime());
////				serviceInstance.setOwnerId(user.getAccount());
////				// 订单ID
////				serviceInstance.setOrderId(orderDetail.getOrderId());
////				// 订单明细ID
////				serviceInstance.setDetailSid(orderDetail.getDetailSid());
////				// 目标为空
////				serviceInstance.setTarget("");
////				// 服务开通时间为空
////				serviceInstance.setDredgeDate(null);
////				// 服务到期时间
////				serviceInstance.setExpiringDate(null);
////				// 实例创建开始结束时间
////				serviceInstance.setCreationDateBegin(new Date());
////				serviceInstance.setCreationDateEnd(new Date());
////				serviceInstance.setStatus(WebConstants.ServiceInstanceStatus.PENDING);
////				// 服务实例名称
////				serviceInstance.setInstanceName(orderDetail
////						.getServiceInstanceMaps().get(i).get("instanceName")
////						.toString());
////
////				//设置虚拟机主机名
////				/*
////				 * x86虚拟机名称：项目名称-项目英文-系统版本-版本位数-顺序号
////				 * Power分区名称：项目英文-系统版本-顺序号
////				 */
////				String osVersion = (String)orderDetail.getSpecificationMaps().get(WebConstants.InstanceSpecType.OS);
////				Criteria criteria = new Criteria();
////				criteria.put("codeValue", osVersion);
////				List<Code> codeList = this.codeService.getParentCodeByCodeVaule(criteria);
////				Code versionCode = this.codeService.getCodeByValue(osVersion, WebConstants.CodeCategroy.OS_VERSION);
////				String resInstName = "";
////				if(codeList.size() > 0) {
////					Code parentCode = codeList.get(0);
////					String ve = parentCode.getAttribute1();
////					Long mgtObjSid = Long.parseLong(orderDetail.getMgtObjSid());
////					MgtObj mgtObj = this.mgtObjService.selectByPrimaryKey(mgtObjSid);
////					criteria = new Criteria();
////					criteria.put("mgtObjSid", mgtObj.getMgtObjSid());
////					List<MgtObjExt> mgtObjExts = this.mgtObjExtService.selectByParams(criteria);
////					String projectEName = "";
////					for(MgtObjExt mgtObjExt : mgtObjExts) {
////						if("projectEName".equals(mgtObjExt.getAttrKey())) {
////							projectEName = mgtObjExt.getAttrValue();
////							break;
////						}
////					}
////					DecimalFormat format = new DecimalFormat("00");
////					if(WebConstants.VirtualEnv.POWER.equals(ve)) {
////						resInstName = projectEName.toUpperCase() + "-" + versionCode.getAttribute2().toUpperCase() + "-" + format.format(getVmHostNameSeq());
////					} else if(WebConstants.VirtualEnv.X86.equals(ve)) {
////						resInstName = mgtObj.getName().toUpperCase() + "-" + projectEName.toUpperCase() + "-" + versionCode.getAttribute2().toUpperCase() + "-" + format.format(getVmHostNameSeq());
////					}
////				}
////				serviceInstance.setResInstName(resInstName);
////				serviceInstance.setMgtObjSid(Long.parseLong(orderDetail.getMgtObjSid()));
////				WebUtil.prepareInsertParams(serviceInstance);
////				// insert服务实例数据到服务实例表
////				result = result
////						+ this.serviceInstanceMapper
////						.insertSelective(serviceInstance);
////			}
////
////			//返回VM服务实例Sid
////			Long instanceSid = serviceInstance.getInstanceSid();
////			//删掉该实例下的其他实例和规格
////			params = new Criteria();
////			params.put("parentInstanceSid", instanceSid);
////			List<ServiceInstance> childInstances = serviceInstanceMapper.selectByParams(params);
////			if(!CollectionUtils.isEmpty(childInstances)){
////				for (ServiceInstance si : childInstances) {
////					params = new Criteria();
////					params.put("instanceSid", si.getInstanceSid());
////					spec.deleteByParams(params);
////
////					serviceInstanceMapper.deleteByPrimaryKey(si.getInstanceSid());
////				}
////			}
////			params = new Criteria();
////			params.put("instanceSid", instanceSid);
////			spec.deleteByParams(params);
////
////			orderDetail.setVmServiceInstanceSid(instanceSid);
////			// insert服务规格数据到服务规格表
////			Criteria criteria = new Criteria();
////			criteria.put("serviceSid", orderDetail.getServiceSid());
////			List<ServiceSpec> serviceSpecList = serviceSpec.selectByParams(criteria);
////			// 生成服务规格
////			if(WebConstants.ServiceSid.SERVICE_VM.equals(orderDetail.getServiceSid())){
////				// 生成vm服务实例规格
////				for (ServiceSpec sSpec : serviceSpecList) {
////					ServiceInstanceSpec instanceSpec = new ServiceInstanceSpec();
////					instanceSpec.setInstanceSid(serviceInstance.getInstanceSid());
////					instanceSpec.setName(sSpec.getName());
////					instanceSpec.setDescription(sSpec.getDescription());
////					instanceSpec.setSequence(sSpec.getSequence());
////					instanceSpec.setTag(sSpec.getTag());
////					instanceSpec.setUnit(sSpec.getUnit());
////					// 设置服务实例规格中的值
////					if (WebConstants.InstanceSpecType.CPU.equals(instanceSpec.getName())) {
////						instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
////					} else if (WebConstants.InstanceSpecType.MEMORY.equals(instanceSpec.getName())) {
////						instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
////					}else if (WebConstants.InstanceSpecType.OS.equals(instanceSpec.getName())) {
////						instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
////					}else if (WebConstants.InstanceSpecType.SYSTEM_DISK.equals(instanceSpec.getName())) {
////						instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
////						String sysDisk = StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName()));
////						if(!StringUtil.isNullOrEmpty(sysDisk)){
////							HashMap<String, Object> sysDiskMap = new HashMap<String, Object>();
////							sysDiskMap.put("diskName", serviceInstance.getInstanceName()+"-sysDisk");
////							sysDiskMap.put("storagePurpose", WebConstants.StoragePurpose.SYSTEM_DISK);
////							sysDiskMap.put("diskSize", sysDisk);
////							createHostAndStorageServiceInstance(orderDetail, sysDiskMap);
////						}
////					}else if (WebConstants.InstanceSpecType.KEY_PAIR.equals(instanceSpec.getName())) {
////						instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
////					}else if (WebConstants.InstanceSpecType.REMARK.equals(instanceSpec.getName())) {
////						instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
////					}else if (WebConstants.InstanceSpecType.RECOVERY_TYPE.equals(instanceSpec.getName())) {
////						instanceSpec.setValue(StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName())));
////					}else if (WebConstants.InstanceSpecType.INSTALL_SOFTWARE.equals(instanceSpec.getName())) {
////						//将安装的软件也改成服务实例
////						String softwares = StringUtil.nullToEmpty(orderDetail.getSpecificationMaps().get(instanceSpec.getName()));
////						if(!StringUtil.isNullOrEmpty(softwares)){
////							String[] software = softwares.split(",");
////							if(software.length>0){
////								for (String soft : software) {
////									ServiceInstance softInstance = new ServiceInstance();
////									softInstance = (ServiceInstance)BeanUtils.cloneBean(serviceInstance);
////									softInstance.setInstanceName(soft);
////									softInstance.setServiceSid(WebConstants.ServiceSid.SERVICE_DEPLOYMENT);
////									softInstance.setStatus(WebConstants.ServiceInstanceStatus.PENDING);
////									softInstance.setParentInstanceSid(instanceSid);
////									this.serviceInstanceMapper.insertSelective(softInstance);
////								}
////							}
////						}
////						instanceSpec.setValue(softwares);
////					}else if (WebConstants.InstanceSpecType.NEED_LAN.equals(instanceSpec.getName())) {
////						instanceSpec.setValue("1");
////					}
////					instanceSpec.setGroupName(sSpec.getGroupName());
////					WebUtil.prepareInsertParams(instanceSpec);
////					spec.insertSelective(instanceSpec);
////				}
////
////				List<HashMap<String, Object>> hostDisks = new ArrayList<HashMap<String, Object>>();
////				hostDisks = (List<HashMap<String, Object>>) orderDetail.getSpecificationMaps().get("DATA_DISK");
////				String diskSuffix = "data-disk-";
////				for (int j = 0; j < hostDisks.size(); j++) {
////					// 设置多条存储相关的服务实例和服务实例规格数据
////					String diskName = (String)hostDisks.get(j).get("diskName");
////					if(StringUtils.isBlank(diskName)) {
////						hostDisks.get(j).put("diskName", diskSuffix + j);
////					}
////					createHostAndStorageServiceInstance(orderDetail, hostDisks.get(j));
////				}
////
////				ResVmInst resVmInst = new ResVmInst();
////				//页面添加的用户信息
////				List<VmUser> vmUsers = (List<VmUser>) orderDetail.getServiceInstanceMaps().get(i).get("instanceUser");
////				resVmInst.setVmUsers(vmUsers);
////
////				//保存实例开通日志
////				ServiceInstanceChangeLogSpec<ResVmInst> serviceInstanceChangeLogSpec = new ServiceInstanceChangeLogSpec<ResVmInst>();
////				serviceInstanceChangeLogSpec.setParams(resVmInst);
////				ServiceInstanceChangeLog instanceChangeLog = this.instanceChangeLogService.getLastChangeLog(instanceSid);
////				if(instanceChangeLog ==  null){
////					instanceChangeLogService.submitChangeServiceInstance(serviceInstance,
////							WebConstants.instanceChangeType.CREATE,
////							serviceInstanceChangeLogSpec);
////				} else {
////					instanceChangeLogService.updateChangeServiceInstance(instanceSid,
////							WebConstants.instanceChangeType.CREATE,
////							serviceInstanceChangeLogSpec,
////							new TypeReference<ServiceInstanceChangeLogSpec<ResVmInst>>() {});
////				}
////			}
////		}
//
//		if (result == orderDetail.getQuantity()) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	/**
//	 * 根据订单初始化服务实例，主机的存储服务实例单独生成多条
//	 *
//	 * @param orderDetail
//	 */
//	public Long createHostAndStorageServiceInstance(OrderDetail orderDetail,
//													HashMap<String, Object> hostDiskMap) {
//		int result = 0;
//
////		// 取得服务定义信息
////		ServiceDefineService service = SpringContextHolder
////				.getBean("serviceDefineServiceImpl");
////		ServiceInstanceSpecService spec = SpringContextHolder
////				.getBean("serviceInstanceSpecServiceImpl");
////		ServiceSpecService serviceSpec = SpringContextHolder
////				.getBean("serviceSpecServiceImpl");
////		User user = AuthUtil.getAuthUser();
////
////		ServiceDefine serviceDefine = service.selectByPrimaryKey(orderDetail
////				.getServiceSid());
////
////		// 初始化服务实例
////		ServiceInstance serviceInstance = new ServiceInstance();
////		serviceInstance.setServiceSid(WebConstants.ServiceSid.SERVICE_STORAGE);
////		serviceInstance.setTemplateSid(orderDetail.getTemplateSid());
//////		serviceInstance.setBillingType(orderDetail.getBillingType());
//////		serviceInstance.setBuyLength(orderDetail.getBuyLength());
////		serviceInstance.setBillingTypeId("");
//////		serviceInstance.setDescription(serviceDefine.getDescription());
////		// 到期时间 未来24小时
////		// 期望开通时间(未来24小时)
////		Calendar c = Calendar.getInstance();
////		c.setTime(new Date());
////		c.add(Calendar.DAY_OF_MONTH, 1);
////		serviceInstance.setExpiringDate(c.getTime());
////		serviceInstance.setOwnerId(user.getAccount());
////		// 订单ID
////		serviceInstance.setOrderId(orderDetail.getOrderId());
////		// 订单明细ID
////		serviceInstance.setDetailSid(orderDetail.getDetailSid());
////
////		// 目标为空
////		serviceInstance.setTarget("");
////		// 租户ID
//////		serviceInstance.setTanentId(user.getTenantSid().toString());
////		// 服务开通时间为空
////		serviceInstance.setDredgeDate(null);
////		// 服务到期时间
////		serviceInstance.setExpiringDate(null);
////
////		// 实例创建开始结束时间
////		serviceInstance.setCreationDateBegin(new Date());
////		serviceInstance.setCreationDateEnd(new Date());
////
////		// 服务实例名称
////		serviceInstance.setInstanceName(hostDiskMap.get("diskName").toString());
////		serviceInstance.setMgtObjSid(Long.parseLong(orderDetail.getMgtObjSid()));
////		if(StringUtil.isNullOrEmpty(hostDiskMap.get("instStatus"))){
////			serviceInstance.setStatus(WebConstants.ServiceInstanceStatus.PENDING);
////		}else{
////			serviceInstance.setStatus(hostDiskMap.get("instStatus").toString());
////		}
////        /*
////		// 服务实例期望开通时间
////		serviceInstance.setExpectedTime(StringUtil
////				.strToDate(orderDetail.getServiceInstanceMaps().get(0)
////						.get("expectedTime").toString()));
////        */
////		WebUtil.prepareInsertParams(serviceInstance);
////		//设置数据盘和系统盘所属的VmInstanceSid
////		serviceInstance.setParentInstanceSid(orderDetail.getVmServiceInstanceSid());
////		// insert服务实例数据到服务实例表
////		result = result
////				+ this.serviceInstanceMapper.insertSelective(serviceInstance);
////
////		// insert服务规格数据到服务规格表
////		Criteria criteria = new Criteria();
////		criteria.put("serviceSid", WebConstants.ServiceSid.SERVICE_STORAGE);
////		List<ServiceSpec> serviceSpecList = serviceSpec
////				.selectByParams(criteria);
////
////		for (ServiceSpec sSpec : serviceSpecList) {
////			ServiceInstanceSpec instanceSpec = new ServiceInstanceSpec();
////			instanceSpec.setInstanceSid(serviceInstance.getInstanceSid());
////			instanceSpec.setName(sSpec.getName());
////			instanceSpec.setDescription(sSpec.getDescription());
////			instanceSpec.setSequence(sSpec.getSequence());
////			instanceSpec.setTag(sSpec.getTag());
////			instanceSpec.setUnit(sSpec.getUnit());
////			// 设置服务实例规格中的值
////			if (instanceSpec.getName().equals(
////					WebConstants.InstanceSpecType.STORAGE_TYPE)) {
////				instanceSpec.setValue("");
////			} else if (instanceSpec.getName().equals(
////					WebConstants.InstanceSpecType.STORAGE_PURPOSE)) {
////				instanceSpec.setValue(hostDiskMap.get("storagePurpose")
////						.toString());
////			} else if (instanceSpec.getName().equals(
////					WebConstants.InstanceSpecType.DISK_SIZE)) {
//////				if(WebConstants.StoragePurpose.SYSTEM_DISK.equals(hostDiskMap.get("storagePurpose").toString())) {
//////					instanceSpec.setValue(PropertiesUtil.getProperty("sys.disk.size"));
//////				} else if(WebConstants.StoragePurpose.DATA_DISK.equals(hostDiskMap.get("storagePurpose").toString())) {
////				instanceSpec.setValue(hostDiskMap.get("diskSize").toString());
//////				}
////			} else if(instanceSpec.getName().equals(WebConstants.InstanceSpecType.MOUNT_POINT)) {
////				if(hostDiskMap.get("diskUrlName") != null) {
////					instanceSpec.setValue(hostDiskMap.get("diskUrlName").toString());
////				}
////			} else if(instanceSpec.getName().equals(WebConstants.InstanceSpecType.FILE_SYSTEM)) {
////				if(hostDiskMap.get("fileSystemType") != null) {
////					instanceSpec.setValue(hostDiskMap.get("fileSystemType").toString());
////				}
////			}
////			instanceSpec.setGroupName(sSpec.getGroupName());
////			WebUtil.prepareInsertParams(instanceSpec);
////			spec.insertSelective(instanceSpec);
////		}
//
////		if (result == 1) {
//////			return serviceInstance.getInstanceSid();
////		} else {
////			return null;
////		}
//	}
//
//	/**
//	 * 添加vd的服务实例和规格
//	 * @param vd
//	 * @param serviceInstance
//	 * @return
//	 */
//	public Long insertVdInstanceAndSpec(ResVdInst vd, ServiceInstance serviceInstance) {
//		int result = 0;
//
//		// 取得服务定义信息
//		ServiceDefineService service = SpringContextHolder
//				.getBean("serviceDefineServiceImpl");
//		ServiceInstanceSpecService spec = SpringContextHolder
//				.getBean("serviceInstanceSpecServiceImpl");
//		ServiceSpecService serviceSpec = SpringContextHolder
//				.getBean("serviceSpecServiceImpl");
////		User user = AuthUtil.getAuthUser();
//		User user = null;
//		// 初始化服务实例
//		ServiceInstance vdInstance = new ServiceInstance();
//		vdInstance.setServiceSid(WebConstants.ServiceSid.SERVICE_STORAGE);
////		vdInstance.setTemplateSid(orderDetail.getTemplateSid());
////		vdInstance.setBillingType(orderDetail.getBillingType());
////		vdInstance.setBuyLength(orderDetail.getBuyLength());
//		vdInstance.setBillingTypeId("");
////		vdInstance.setDescription(serviceDefine.getDescription());
//		// 到期时间 未来24小时
//		// 期望开通时间(未来24小时)
//		Calendar c = Calendar.getInstance();
//		c.setTime(new Date());
//		c.add(Calendar.DAY_OF_MONTH, 1);
//		vdInstance.setExpiringDate(c.getTime());
//		vdInstance.setOwnerId(user.getAccount());
//		// 订单ID
////		vdInstance.setOrderId(orderDetail.getOrderId());
//		// 订单明细ID
////		vdInstance.setDetailSid(orderDetail.getDetailSid());
//
//		// 目标为空
//		vdInstance.setTarget("");
//		// 租户ID
////		vdInstance.setTanentId(user.getTenantSid().toString());
//		// 服务开通时间为空
//		vdInstance.setDredgeDate(null);
//		// 服务到期时间
//		vdInstance.setExpiringDate(null);
//
//		// 实例创建开始结束时间
//		vdInstance.setCreationDateBegin(new Date());
//		vdInstance.setCreationDateEnd(new Date());
//
//		// 服务实例名称
////		vdInstance.setInstanceName(vd.getResVdInstName());
//		vdInstance.setMgtObjSid(serviceInstance.getMgtObjSid());
//
//		vdInstance.setStatus(WebConstants.ServiceInstanceStatus.PENDING);
//        /*
//		// 服务实例期望开通时间
//		vdInstance.setExpectedTime(StringUtil
//				.strToDate(orderDetail.getServiceInstanceMaps().get(0)
//						.get("expectedTime").toString()));
//        */
//		WebUtil.prepareInsertParams(vdInstance);
//		//设置数据盘和系统盘所属的VmInstanceSid
//		vdInstance.setParentInstanceSid(serviceInstance.getInstanceSid());
//		// insert服务实例数据到服务实例表
//		result = result
//				+ this.serviceInstanceMapper.insertSelective(vdInstance);
//
//		// insert服务规格数据到服务规格表
//		Criteria criteria = new Criteria();
//		criteria.put("serviceSid", WebConstants.ServiceSid.SERVICE_STORAGE);
//		List<ServiceSpec> serviceSpecList = serviceSpec
//				.selectByParams(criteria);
//
//		for (ServiceSpec sSpec : serviceSpecList) {
//			ServiceInstanceSpec instanceSpec = new ServiceInstanceSpec();
//			instanceSpec.setInstanceSid(vdInstance.getInstanceSid());
//			instanceSpec.setName(sSpec.getName());
//			instanceSpec.setDescription(sSpec.getDescription());
//			instanceSpec.setSequence(sSpec.getSequence());
//			instanceSpec.setTag(sSpec.getTag());
//			instanceSpec.setUnit(sSpec.getUnit());
//			// 设置服务实例规格中的值
////			if (instanceSpec.getName().equals(
////					WebConstants.InstanceSpecType.STORAGE_TYPE)) {
////				instanceSpec.setValue("");
////			} else if (instanceSpec.getName().equals(
////					WebConstants.InstanceSpecType.STORAGE_PURPOSE)) {
//////				instanceSpec.setValue(vd.getStoragePurpose());
////			} else if (instanceSpec.getName().equals(
//////					WebConstants.InstanceSpecType.DISK_SIZE)) {
//////				if(WebConstants.StoragePurpose.SYSTEM_DISK.equals(vd.getStoragePurpose())) {
//////					instanceSpec.setValue(PropertiesUtil.getProperty("sys.disk.size"));
//////				} else if(WebConstants.StoragePurpose.DATA_DISK.equals(vd.getStoragePurpose())) {
//////					instanceSpec.setValue(vd.getDiskSize()+"");
//////				}
////			}
////			instanceSpec.setGroupName(sSpec.getGroupName());
////			WebUtil.prepareInsertParams(instanceSpec);
////			spec.insertSelective(instanceSpec);
//		}
//
////		return vdInstance.getInstanceSid();
//		return 1L;
//	}
//
////	/**
////	 * 服务退订
////	 */
////	@Override
////	public boolean releaseService(Long serviceInstaceSid, String serviceType) {
////
////		resourceInstanceExchangeService = SpringContextHolder
////				.getBean("resourceInstanceExchangeServiceImpl");
////
////		// 表示资源实例是否释放成功
////		boolean releaseResult = false;
////		// 标识资源实例是否全部释放成功
////		boolean flag = true;
////		// VM服务
////		if (serviceType.equals(WebConstants.ServiceType.VM)) {
////			// 更新主机资源实例
////			Criteria criteria = new Criteria();
////			criteria.put("serviceInstanceSid", serviceInstaceSid);
////			List<ResourceInstance> resInsList = this.resourceInstanceMapper
////					.selectByParams(criteria);
////			for (ResourceInstance ri : resInsList) {
////				// 释放资源
////				releaseResult = this.resourceInstanceService.release(ri);
////				if (releaseResult) {
////
////					// 资源实例状态
////					WebUtil.prepareUpdateParams(ri);
////					if (ri.getResInstanceType().equals(
////							WebConstants.ResourceInstanceType.RES_INST_IP)) {
////						ri.setStatus(WebConstants.ResIpInstanceStatus.DELETED);
////					} else if (ri.getResInstanceType().equals(
////							WebConstants.ResourceInstanceType.RES_INST_VM)) {
////						ri.setStatus(WebConstants.ResHostInstanceStatus.DESTROY);
////					}
////
////					int resInsResult = this.resourceInstanceMapper
////							.updateByPrimaryKeySelective(ri);
////					if (resInsResult == 0) {
////						flag = false;
////					}
////				} else {
////					flag = false;
////					break;
////				}
////			}
////			//
////			// // 更新主机带的存储实例,查出下面全部的detail订单
////			// criteria = new Criteria();
////			// criteria.put("serviceInstanceSid", serviceInstaceSid);
////			// ServiceInstance serviceInstance =
////			// this.serviceInstanceMapper.selectByPrimaryKey(serviceInstaceSid);
////			// criteria = new Criteria();
////			// criteria.put("detailSid", serviceInstance.getDetailSid());
////			// List<ServiceInstance> serviceInstances =
////			// this.serviceInstanceMapper.selectByParams(criteria);
////			//
////			// for (ServiceInstance si : serviceInstances) {
////			// // 更新主机资源实例
////			// criteria = new Criteria();
////			// criteria.put("serviceInstanceSid", si.getInstanceSid());
////			// resInsList =
////			// this.resourceInstanceMapper.selectByParams(criteria);
////			// for (ResourceInstance ri : resInsList) {
////			// // 已经更新了的主机和ip不需要更新了
////			// if (!ri.getServiceInstanceSid().equals(serviceInstaceSid)) {
////			// // 调用南向接口释放资源
////			// releaseResult = this.resourceInstanceService.release(ri);
////			// if (releaseResult) {
////			// // 资源实例状态
////			// WebUtil.prepareUpdateParams(ri);
////			// if
////			// (ri.getResInstanceType().equals(WebConstants.RESOURCE_INSTANCE_TYPE.RES_INST_IP))
////			// {
////			// ri.setStatus(WebConstants.RES_IP_INSTANCE_STATUS.DELETED);
////			// } else if
////			// (ri.getResInstanceType().equals(WebConstants.RESOURCE_INSTANCE_TYPE.RES_INST_VM))
////			// {
////			// ri.setStatus(WebConstants.RES_HOST_INSTANCE_STATUS.DESTROY);
////			// } else if (ri.getResInstanceType().equals(
////			// WebConstants.RESOURCE_INSTANCE_TYPE.RES_INST_STORAGE)) {
////			// ri.setStatus(WebConstants.RES_STORAGE_INSTANCE_STATUS.DELETED);
////			// }
////			//
////			// int resInsResult =
////			// this.resourceInstanceMapper.updateByPrimaryKeySelective(ri);
////			// if (resInsResult == 0) {
////			// flag = false;
////			// }
////			// } else {
////			// flag = false;
////			// break;
////			// }
////			// }
////			//
////			// }
////			// }
////
////		} else if (serviceType.equals(WebConstants.ServiceType.STORAGE)) {
////			// 更新storage资源实例
////			Criteria criteria = new Criteria();
////			criteria.put("serviceInstanceSid", serviceInstaceSid);
////			List<ResourceInstance> resInsList = this.resourceInstanceMapper
////					.selectByParams(criteria);
////			for (ResourceInstance ri : resInsList) {
////
////				// 调用南向接口释放资源
////				releaseResult = this.resourceInstanceService.release(ri);
////				if (releaseResult) {
////
////					// 资源实例状态
////					WebUtil.prepareUpdateParams(ri);
////					if (ri.getResInstanceType().equals(
////							WebConstants.ResourceInstanceType.RES_INST_STORAGE)) {
////						ri.setStatus(WebConstants.ResStorageInstanceStatus.DELETED);
////					}
////
////					int resInsResult = this.resourceInstanceMapper
////							.updateByPrimaryKeySelective(ri);
////					if (resInsResult == 0) {
////						flag = false;
////					}
////				} else {
////					flag = false;
////					break;
////				}
////			}
////		}
////
////		// 更新服务实例状态
////		if (flag) {
////			ServiceInstance serviceInstance = new ServiceInstance();
////			serviceInstance.setInstanceSid(serviceInstaceSid);
////			WebUtil.prepareUpdateParams(serviceInstance);
////			serviceInstance
////					.setStatus(WebConstants.ServiceInstanceStatus.CANCELED);
////			this.serviceInstanceMapper
////					.updateByPrimaryKeySelective(serviceInstance);
////		} else {
////			ServiceInstance serviceInstance = new ServiceInstance();
////			serviceInstance.setInstanceSid(serviceInstaceSid);
////			WebUtil.prepareUpdateParams(serviceInstance);
////			serviceInstance
////					.setStatus(WebConstants.ServiceInstanceStatus.EXCEPTION);
////			this.serviceInstanceMapper
////					.updateByPrimaryKeySelective(serviceInstance);
////		}
////		return flag;
////	}
//
////	/**
////	 * 服务退订
////	 */
////	@Override
////	public boolean releaseService(Long serviceInstaceSid) {
////
////		resourceInstanceExchangeService = SpringContextHolder
////				.getBean("resourceInstanceExchangeServiceImpl");
////
////		// 表示资源实例是否释放成功
////		boolean releaseResult = false;
////		// 标识资源实例是否全部释放成功
////		boolean flag = true;
////
////		// VM服务
////		Criteria criteria = new Criteria();
////		criteria.put("serviceInstanceSid", serviceInstaceSid);
////		List<ResourceInstance> resInsList = this.resourceInstanceMapper
////				.selectByParams(criteria);
////		for (ResourceInstance ri : resInsList) {
////
////			// 调用南向接口释放资源
////			releaseResult = this.resourceInstanceService.release(ri);
////			if (releaseResult) {
////
////				// 资源实例状态
////				WebUtil.prepareUpdateParams(ri);
////				if (ri.getResInstanceType().equals(
////						WebConstants.ResourceInstanceType.RES_INST_IP)) {
////					ri.setStatus(WebConstants.ResIpInstanceStatus.DELETED);
////				} else if (ri.getResInstanceType().equals(
////						WebConstants.ResourceInstanceType.RES_INST_VM)) {
////					ri.setStatus(WebConstants.ResHostInstanceStatus.DESTROY);
////				} else if (ri.getResInstanceType().equals(
////						WebConstants.ResourceInstanceType.RES_INST_STORAGE)) {
////					ri.setStatus(WebConstants.ResStorageInstanceStatus.DELETED);
////				}
////
////				int resInsResult = this.resourceInstanceMapper
////						.updateByPrimaryKeySelective(ri);
////				if (resInsResult == 0) {
////					flag = false;
////				}
////			} else {
////				flag = false;
////				break;
////			}
////		}
////
////		// 更新服务实例状态
////		if (flag) {
////			ServiceInstance serviceInstance = new ServiceInstance();
////			serviceInstance.setInstanceSid(serviceInstaceSid);
////			WebUtil.prepareUpdateParams(serviceInstance);
////			serviceInstance
////					.setStatus(WebConstants.ServiceInstanceStatus.CANCELED);
////			this.serviceInstanceMapper
////					.updateByPrimaryKeySelective(serviceInstance);
////		} else {
////			ServiceInstance serviceInstance = new ServiceInstance();
////			serviceInstance.setInstanceSid(serviceInstaceSid);
////			WebUtil.prepareUpdateParams(serviceInstance);
////			serviceInstance
////					.setStatus(WebConstants.ServiceInstanceStatus.EXCEPTION);
////			this.serviceInstanceMapper
////					.updateByPrimaryKeySelective(serviceInstance);
////		}
////		return flag;
////	}
//
//	/**
//	 * 操作服务实例
//	 *
//	 * @throws IOException
//	 * @throws JSONException
//	 */
//	@Override
//	public boolean serviceInstanceOperation(String params) throws IOException,
//			JSONException {
//		boolean flag = true;
//		try {
//			// 标识资源实例是否全部启用成功
//			Map<String, Object> map;
//			map = JsonUtil.fromJson(params, Map.class);
//			// 操作时，action和serviceInstanceSid是必须的 而且需要先查询服务实例下的资源实例
//			String action = String.valueOf(map.get("action"));
//			String instanceSid = String.valueOf(map.get("serviceInstanceSid"));
//			long mgtObjSid = Long.parseLong(String.valueOf(map.get("mgtObjSid")));
//			String rebootType = String.valueOf(map.get("rebootType"));
//			//根据虚拟机实例id查询出关联的虚拟机资源id
//			Criteria criteria = new Criteria();
//			criteria.put("instanceSid", instanceSid);
//			List<ServiceInstRes> serviceInstReses = serviceInstResService.selectByParams(criteria);
//			String vmResId = null;
//			if(serviceInstReses.size() > 0) {
//				vmResId = serviceInstReses.get(0).getResId();
//			}
//			if(vmResId == null) {
//				throw new RuntimeException("The instanceSid no mapping in service_inst_res table instanceSid=" + instanceSid);
//			}
////			vmOperateHanlder.invoke(vmResId,mgtObjSid, action,rebootType);
//		} catch(Exception e) {
//			logger.error(e.getMessage(), e);
//			flag = false;
//		}
//		return flag;
////		Long serviceInstanceSid = Long.parseLong(String.valueOf(map
////				.get("serviceInstanceSid")));
////		Criteria criteria = new Criteria();
////		criteria.put("serviceInstanceSid", serviceInstanceSid);
////		List<ResourceInstance> resInsList = this.resourceInstanceMapper
////				.selectByParams(criteria);
////		for (ResourceInstance ri : resInsList) {
////
////			// 主机资源实例操作
////			if (WebConstants.ResourceInstanceType.RES_INST_VM.equals(ri
////					.getResInstanceType())
////					|| WebConstants.ResourceInstanceType.RES_INST_IP.equals(ri
////							.getResInstanceType())) {
////				ri.setStatus(WebConstants.ResHostInstanceStatus.WAITING);
////				WebUtil.prepareUpdateParams(ri);
////				this.resourceInstanceService.updateByPrimaryKeySelective(ri);
////				flag = this.resourceInstanceVmService.operateResInstanceVm(
////						action, ri);
////				if (!flag) {
////					flag = false;
////					break;
////				}
////			} else if (WebConstants.ResourceInstanceType.RES_INST_STORAGE
////					.equals(ri.getResInstanceType())) {
////				// ResourceInstanceStorage
////				// ris=this.resourceInstanceStorageService.selectByPrimaryKey(ri.getResInstanceSid());
////				// 存储资源实例操作
////				long resVmInsSid = Long.parseLong(map.get("resVmSid")
////						.toString());
////				String deviceName = null;
////				if ("attach".equals(action)) {
////					deviceName = map.get("deviceName").toString();
////				}
////				flag = this.resourceInstanceStorageService.operateVDisk(action,
////						resVmInsSid, ri, deviceName);
////				if (!flag) {
////					flag = false;
////					break;
////				}
////			}
////
////		}
////		// 更新服务实例状态
////		if (flag) {
////			ServiceInstance serviceInstance = new ServiceInstance();
////			serviceInstance.setInstanceSid(serviceInstanceSid);
////			WebUtil.prepareUpdateParams(serviceInstance);
////			// serviceInstance.setStatus(WebConstants.SERVICE_INSTANCE_CD.DISABLED);
////			this.serviceInstanceMapper
////					.updateByPrimaryKeySelective(serviceInstance);
////		} else {
////			ServiceInstance serviceInstance = new ServiceInstance();
////			serviceInstance.setInstanceSid(serviceInstanceSid);
////			WebUtil.prepareUpdateParams(serviceInstance);
//////			serviceInstance
//////					.setStatus(WebConstants.ServiceInstanceStatus.EXCEPTION);
////			this.serviceInstanceMapper
////					.updateByPrimaryKeySelective(serviceInstance);
////		}
//	}
//
//	/**
//	 * 服务实例退订（前端系统调用）
//	 *
//	 * @param serviceInstaceSid
//	 * @return true 退订成功 false 退订失败
//	 */
//	public void createServiceInstanceCancel(String serviceInstaceSid) {
//
////		ServiceInstance serviceInstance = this.serviceInstanceMapper.selectByPrimaryKey(Long.parseLong(serviceInstaceSid));
////		Long changeLogSid = instanceChangeLogService.submitChangeServiceInstance(serviceInstance, WebConstants.instanceChangeType.UNSUBSCRIBE, null);
////		if (serviceInstance.getServiceSid().equals( WebConstants.ServiceSid.SERVICE_VM)) {
////			this.approveRecordService.initCancelApproveRecord(serviceInstaceSid, changeLogSid);
////		} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.FLOATING_IP)) {
////			floatingIpDeleteHandler.invoke(Long.parseLong(serviceInstaceSid));
////		} else if (serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_STORAGE)) {
////			vdDeleteHandler.invoke(Long.parseLong(serviceInstaceSid));
////		}
//
//	}
//
//	/**
//	 * 测试退订CDN
//	 *
//	 * @param serviceInstaceSid
//	 * @return true 退订成功 false 退订失败
//	 */
//	@Override
//	public void cancalCDN() {
//
//
//
//		//TODO 取得用户对应租户CDN服务实例，方便测试退订
////		User user=AuthUtil.getAuthUser();
//		User user= null;
//		Criteria criteria=new Criteria();
////			criteria.put("mgtObjSid", user.getMgtObjSid());
//		criteria.put("status", WebConstants.CdnInstStatus.NORMAL);
////			List<ResCdnInst> resCdnInstList=this.resCdnInstService.selectByParams(criteria);
////			Criteria criteria1=new Criteria();
////			criteria1.put("resId", resCdnInstList.get(0).getCdnInstSid());
////			List<ServiceInstRes> serInsRes=this.serviceInstResService.selectByParams(criteria1);
//
////			cdnDeleteHandler.invoke(serInsRes.get(0).getInstanceSid());
////		// 取得流程key
////		String activitiFlowKey = getActivitiFlow(serviceInstance
////				.getServiceSid());
////
////		variables.put("serviceInstaceSid", serviceInstaceSid);
////
////		// 启动流程实例
////		ProcessInstance processInstance = activitiWorkflowUtil.startWorkflow(
////				variables, activitiFlowKey);
//
//	}
//
//	/**
//	 * 取得流程启动key
//	 *
//	 * @param e
//	 */
//	private String getActivitiFlow(Long serviceSid) {
//		String activitiFlow = "";
//		// 取得流程启动key
//		ServiceConfigService serviceConfigService = SpringContextHolder
//				.getBean("serviceConfigServiceImpl");
//		activitiFlow = serviceConfigService
//				.selectActivitiFlowByServiceSid(
//						serviceSid,
//						WebConstants.ServiceConfigActiviti.CLOUD_SERVICE_CANCEL_PROCESS);
//		return activitiFlow;
//	}
//
//	/**
//	 * 修改服务实例及其下的资源实例名称
//	 */
//	@Override
//	public boolean modifyResInsAndSerInsName(long instanceSid, String newName,
//											 String resDescription) throws ApplicationException, Exception {
//		boolean result = false;
////		try {
////			ServiceInstance serviceInstance = this.serviceInstanceMapper
////					.selectByPrimaryKey(instanceSid);
////			serviceInstance.setInstanceName(newName);
////			int serInsUpdateResult = this.serviceInstanceMapper
////					.updateByPrimaryKeySelective(serviceInstance);
////			if (serInsUpdateResult == 0) {
////				throw new ApplicationException();
////			}
////			result = true;
////		} catch (ApplicationException appE) {
////			// TODO Auto-generated catch block
////			appE.printStackTrace();
////			result = false;
////			throw appE;
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////			result = false;
////			throw e;
////		}
//		return result;
//	}
//
//	/**
//	 * 根据条件查询服务总数与有效服务
//	 */
//	@Override
//	public List<ServiceInstance> countServiceByParams(Criteria example) {
//		return this.serviceInstanceMapper.countServiceByParams(example);
//	}
//
//	/**
//	 * 根据条件查询租户服务总数与用户服务数
//	 */
//	@Override
//	public List<ServiceInstance> countTenantServiceVmByParams(Criteria example) {
//		return this.serviceInstanceMapper.countTenantServiceVmByParams(example);
//	}
//
//	/**
//	 * 根据条件查询租户服务总数与用户服务数
//	 */
//	@Override
//	public List<ServiceInstance> countTenantServiceStByParams(Criteria example) {
//		return this.serviceInstanceMapper.countTenantServiceStByParams(example);
//	}
//
//	/**
//	 * 根据条件查询租户服务总数与用户服务数
//	 */
//	@Override
//	public List<ServiceInstance> countTenantServiceVmCpuByParams(
//			Criteria example) {
//		return this.serviceInstanceMapper
//				.countTenantServiceVmCpuByParams(example);
//	}
//
//	/**
//	 * 根据条件查询租户服务总数与用户服务数
//	 */
//	@Override
//	public List<ServiceInstance> countTenantServiceVmMemoryByParams(
//			Criteria example) {
//		return this.serviceInstanceMapper
//				.countTenantServiceVmMemoryByParams(example);
//	}
//
//	@Override
//	public List<ServiceInstance> selectVolumeStorageInfo(Criteria example) {
//
//		return this.serviceInstanceMapper.selectVolumeStorageInfo(example);
//	}
//
//	/**
//	 * 根据条件查询租户服务总数与用户服务数
//	 */
//	@Override
//	public List<ServiceInstance> countTenantServiceStDiskByParams(
//			Criteria example) {
//		return this.serviceInstanceMapper
//				.countTenantServiceStDiskByParams(example);
//	}
//
//	@Override
//	public List<ServiceDefine> selectedTarfgetHost(Criteria example) {
//		return this.serviceInstanceMapper.selectedTarfgetHost(example);
//	}
//
//	@Override
//	public ServiceInstance selectedSPAddressByServiceSid(Criteria example) {
//		return this.serviceInstanceMapper
//				.selectedSPAddressByServiceSid(example);
//	}
//
//	/**
//	 * 根据条件查询租户服务总数与用户服务数
//	 */
//	@Override
//	public List<ServiceInstance> countTenantServiceVmQuotaByParams(
//			Criteria example) {
//		return this.serviceInstanceMapper
//				.countTenantServiceVmQuotaByParams(example);
//	}
//
//	/**
//	 * 根据条件查询租户服务总数与用户服务数
//	 */
//	@Override
//	public List<ServiceInstance> countTenantServiceStQuotaByParams(
//			Criteria example) {
//		return this.serviceInstanceMapper
//				.countTenantServiceStQuotaByParams(example);
//	}
//
//	/**
//	 * 根据条件查询租户服务总数与用户服务数
//	 */
//	@Override
//	public List<ServiceInstance> countTenantServiceVmCpuQuotaByParams(
//			Criteria example) {
//		return this.serviceInstanceMapper
//				.countTenantServiceVmCpuQuotaByParams(example);
//	}
//
//	/**
//	 * 根据条件查询租户服务总数与用户服务数
//	 */
//	@Override
//	public List<ServiceInstance> countTenantServiceVmMemoryQuotaByParams(
//			Criteria example) {
//		return this.serviceInstanceMapper
//				.countTenantServiceVmMemoryQuotaByParams(example);
//	}
//
//	/**
//	 * 根据条件查询租户服务总数与用户服务数
//	 */
//	@Override
//	public List<ServiceInstance> countTenantServiceStDiskQuotaByParams(
//			Criteria example) {
//		return this.serviceInstanceMapper
//				.countTenantServiceStDiskQuotaByParams(example);
//	}
//
//	/**
//	 * 根据条件查询租户服务总数与用户服务数
//	 */
//	@Override
//	public Map<String, Object> getTenantUserQuotaListByParams(String params,
//															  User user) {
//		//开通中，已开通，退订中 这些数据算用户还在用的。
//		String statusParams="'" + WebConstants.ServiceInstanceStatus.OPENING + "','" +
//				WebConstants.ServiceInstanceStatus.OPENED + "','"
//				+ WebConstants.ServiceInstanceStatus.CANCELING + "'".replace(" ","");
//
//		// 查询已开通
//		List<ServiceInstance> resultServiceInstanceList = new ArrayList<ServiceInstance>();
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		try {
//			Criteria example = new Criteria();
//			if (!StringUtil.isNullOrEmpty(params) && !params.equals("null")) {
//				Map<String, Object> map;
//
//				map = JsonUtil.fromJson(params, Map.class);
//
//				if (StringUtil.isNullOrEmpty(map.get("ownerId"))) {
//					map.put("tanentId", user.getTenantSid());
//				}
//				map.put("statusParams", statusParams);
//				example.setCondition(map);
//			} else {
//				// 初始化查询出所有租户下已开通的服务
//				example.put("tanentId", user.getTenantSid());
//				example.put("statusParams", statusParams);
//			}
//
//			example.setOrderByClause("A.OWNER_ID");
//			List<ServiceInstance> serviceInstanceVmList = this.serviceInstanceMapper
//					.selectByParams(example);
//			for (ServiceInstance serviceInstance : serviceInstanceVmList) {
//				// 块存储服务要单独筛选
//				if (serviceInstance.getServiceSid().equals(
//						WebConstants.ServiceSid.SERVICE_STORAGE)) {
//					// 块存储 选出数据盘,去掉系统盘
//					example = new Criteria();
//					example.put("volumeStorageOrderTenantId",
//							user.getTenantSid());
//					example.put("statusParams", statusParams);
//					example.put("storagePurpose",
//							WebConstants.StoragePurpose.DATA_DISK);
//					List<ServiceInstance> serviceInstanceStList = this.serviceInstanceMapper
//							.selectVolumeStorageInfo(example);
//					for (ServiceInstance serviceInstanceSt : serviceInstanceStList) {
//						if (serviceInstance.getInstanceSid().equals(
//								serviceInstanceSt.getInstanceSid())) {
//							resultServiceInstanceList.add(serviceInstance);
//						}
//					}
//				} else {
//					resultServiceInstanceList.add(serviceInstance);
//				}
//			}
//
//			resultMap.put("serviceInstanceList", resultServiceInstanceList);
//			// 统计云主机数
//			example = new Criteria();
//			example.put("tanentId", user.getTenantSid());
//			example.put("statusParams", statusParams);
//			example.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
//			List<ServiceInstance> userServiceVmList = this.serviceInstanceMapper
//					.countTenantServiceVmByParams(example);
//			if (userServiceVmList == null || userServiceVmList.size() == 0) {
//				ServiceInstance blankServiceInstance = new ServiceInstance();
//				blankServiceInstance.setOwnerName("无");
//				blankServiceInstance.setUserServiceNum(0);
//				userServiceVmList.add(blankServiceInstance);
//			}
//			resultMap.put("userServiceVmList", userServiceVmList);
//
//			// 统计云主机cpu数
//			example = new Criteria();
//			example.put("tanentId", user.getTenantSid());
//			example.put("statusParams", statusParams);
//			example.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
//			List<ServiceInstance> userServiceVmCpuList = this.serviceInstanceMapper
//					.countTenantServiceVmCpuByParams(example);
//			if (userServiceVmCpuList == null
//					|| userServiceVmCpuList.size() == 0) {
//				ServiceInstance blankServiceInstance = new ServiceInstance();
//				blankServiceInstance.setOwnerName("无");
//				blankServiceInstance.setUserServiceNum(0);
//				userServiceVmCpuList.add(blankServiceInstance);
//			}
//			resultMap.put("userServiceVmCpuList", userServiceVmCpuList);
//
//			// 统计云主机内存数
//			example = new Criteria();
//			example.put("tanentId", user.getTenantSid());
//			example.put("statusParams", statusParams);
//			example.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
//			List<ServiceInstance> userServiceVmMemoryList = this.serviceInstanceMapper
//					.countTenantServiceVmMemoryByParams(example);
//			if (userServiceVmMemoryList == null
//					|| userServiceVmMemoryList.size() == 0) {
//				ServiceInstance blankServiceInstance = new ServiceInstance();
//				blankServiceInstance.setOwnerName("无");
//				blankServiceInstance.setUserServiceNum(0);
//				userServiceVmMemoryList.add(blankServiceInstance);
//			}
//			resultMap.put("userServiceVmMemoryList", userServiceVmMemoryList);
//
//			// 统计块存储数
//			example = new Criteria();
//			example.put("volumeStorageOrderTenantId", user.getTenantSid());
//			example.put("statusParams", statusParams);
//			example.put("storagePurpose", WebConstants.StoragePurpose.DATA_DISK);
//			List<ServiceInstance> userServiceStList = this.serviceInstanceMapper
//					.countTenantServiceStByParams(example);
//			if (userServiceStList == null || userServiceStList.size() == 0) {
//				ServiceInstance blankServiceInstance = new ServiceInstance();
//				blankServiceInstance.setOwnerName("无");
//				blankServiceInstance.setUserServiceNum(0);
//				userServiceStList.add(blankServiceInstance);
//			}
//			resultMap.put("userServiceStList", userServiceStList);
//
//			// 统计块存储数磁盘大小
//			example = new Criteria();
//			example.put("volumeStorageOrderTenantId", user.getTenantSid());
//			example.put("statusParams", statusParams);
//			//磁盘大小不分系统盘和数据盘
//			//example.put("storagePurpose", WebConstants.StoragePurpose.DATA_DISK);
//			List<ServiceInstance> userServiceStDiskList = this.serviceInstanceMapper
//					.countTenantServiceStDiskByParams(example);
//			if (userServiceStDiskList == null
//					|| userServiceStDiskList.size() == 0) {
//				ServiceInstance blankServiceInstance = new ServiceInstance();
//				blankServiceInstance.setOwnerName("无");
//				blankServiceInstance.setUserServiceNum(0);
//				userServiceStDiskList.add(blankServiceInstance);
//			}
//			resultMap.put("userServiceStDiskList", userServiceStDiskList);
//
//			return resultMap;
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return resultMap;
//
//		}
//	}
//
//	/**
//	 * 根据条件查询租户服务总数与用户服务数
//	 */
//	@Override
//	public List<ServiceInstance> removeDuplicateData(List<ServiceInstance> list) {
//
//		for (int i = 0; i < list.size() - 1; i++) {
//			for (int j = list.size() - 1; j > i; j--) {
//				if (list.get(j).getOwnerId().equals(list.get(i).getOwnerId())) {
//					list.remove(j);
//				}
//			}
//		}
//		return list;
//	}
//
//	@Override
//	public List<ServiceInstance> selectServiceInstanceForNeedBalance(
//			Criteria example) {
//
//		return this.serviceInstanceMapper.selectServiceInstanceForNeedBalance(example);
//	}
//
//	@Override
//	public List<ServiceInstance> selectServiceInstanceForApprove(Criteria example) {
//		return this.serviceInstanceMapper.selectServiceInstanceForApprove(example);
//	}
//
//	public Map<String, Object> checkAllQuota(String orderId) throws ServiceException {
//		Map<String, Object> result = null;
//		Criteria criteria = new Criteria();
//		criteria.put("orderId", orderId);
//		List<ServiceInstance> instances = this.serviceInstanceMapper.selectByParams(criteria);
//		String ownerId = null;
//		if(!instances.isEmpty()) {
//			ownerId = instances.get(0).getOwnerId();
//		}
//
//		if(ownerId == null) throw new ServiceException("order's ownerId is null");
//
//		criteria = new Criteria();
//		criteria.put("account", ownerId);
//		List<User> users = this.userMapper.selectByParams(criteria);
//		Long mgtObjSid = null;
//		if(!users.isEmpty()) {
////			mgtObjSid = users.get(0).getMgtObjSid();
//		}
//		if(mgtObjSid == null) throw new ServiceException("user's mgtObjSid is null");
//
//		//查询当前业务配额
//		criteria = new Criteria();
//		criteria.put("quotaObjSid", mgtObjSid);
//		criteria.put("quotaObj", WebConstants.QuotaObjectType.BIZ);
//		List<Quota> quotaList = this.quotaMapper.selectByParams(criteria);
//		if(quotaList.size() > 0) {
//			criteria = new Criteria();
//			criteria.put("orderId", orderId);
//			List<Map<String, Object>> curOrderQuotaList = this.serviceInstanceMapper.getOrderQuotaByParams(criteria);
//			criteria = new Criteria();
//			criteria.put("mgtObjSid", mgtObjSid);
//			List<Map<String, Object>> allQuotaList = this.serviceInstanceMapper.countAllUsageQuotaByParams(criteria);
//			result = getCheckItemQuotaLimit(quotaList, curOrderQuotaList, allQuotaList);
//		} else {
//
//			//如果子业务配额未设置，查询父业务配额设置
//			MgtObj mgtobj =  mgtObjService.selectByPrimaryKey(mgtObjSid);
//			criteria = new Criteria();
//			criteria.put("quotaObjSid", mgtobj.getParentId());
//			criteria.put("quotaObj", WebConstants.QuotaObjectType.BIZ);
//			List<Quota> parentQuotaList = this.quotaMapper.selectParentBizQuota(criteria);
//			if(parentQuotaList.size() > 0) {
//				criteria = new Criteria();
//				criteria.put("orderId", orderId);
//				List<Map<String, Object>> curOrderQuotaList = this.serviceInstanceMapper.getOrderQuotaByParams(criteria);
//				criteria = new Criteria();
//				criteria.put("mgtObjSid", mgtObjSid);
//				List<Map<String, Object>> allQuotaList = this.serviceInstanceMapper.countAllUsageQuotaByParams(criteria);
//				result = getCheckItemQuotaLimit(parentQuotaList, curOrderQuotaList, allQuotaList);
//			}
//		}
//		return result;
//	}
//
//	public Map<String, Object> checkAllQuota(Long mgtObjSid, List<Map<String, Object>> curOrderQuotaList) throws ServiceException {
//		Map<String, Object> result = null;
//
//		//查询当前业务配额
////		Criteria criteria = new Criteria();
////		criteria.put("quotaObjSid", mgtObjSid);
////		criteria.put("quotaObj", WebConstants.QuotaObjectType.BIZ);
////		List<Quota> quotaList = this.quotaMapper.selectByParams(criteria);
////		if(quotaList.size() > 0) {
////			criteria = new Criteria();
////			criteria.put("mgtObjSid", mgtObjSid);
////			List<Map<String, Object>> allQuotaList = this.serviceInstanceMapper.countAllUsageQuotaByParams(criteria);
////			result = getCheckItemQuotaLimit(quotaList, curOrderQuotaList, allQuotaList);
////		} else {
////
////			//如果子业务配额未设置，查询父业务配额设置
////			MgtObj mgtobj =  mgtObjService.selectByPrimaryKey(mgtObjSid);
////			criteria = new Criteria();
////			criteria.put("quotaObjSid", mgtobj.getParentId());
////			criteria.put("quotaObj", WebConstants.QuotaObjectType.BIZ);
////			List<Quota> parentQuotaList = this.quotaMapper.selectParentBizQuota(criteria);
////			if(parentQuotaList.size() > 0) {
////				criteria = new Criteria();
////				criteria.put("mgtObjSid", mgtObjSid);
////				List<Map<String, Object>> allQuotaList = this.serviceInstanceMapper.countAllUsageQuotaByParams(criteria);
////				result = getCheckItemQuotaLimit(parentQuotaList, curOrderQuotaList, allQuotaList);
////			}
////		}
//		//测试用
//		result = new HashMap<String, Object>();
//		result.put("checkResult", true);
//		return result;
//	}
//
//	private Map<String, Object> getCheckItemQuotaLimit(List<Quota> quotaList, List<Map<String, Object>> curOrderQuotaList, List<Map<String, Object>> allQuotaList) {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		List<Object []> checkList = new ArrayList<Object[]>();
//		for(Quota quota : quotaList) {
//			String key = quota.getQuotaKey();
//			Object [] checkItem = new Object [6];
//			if(key.equals("cores")) {
//				checkItem[1] = "CPU";
//			} else if(key.equals("ram")) {
//				checkItem[1] = "MEMORY";
//			} else if(key.equals("vLanIdoLimit")) {
//				checkItem[1] = "NEED_WAN";
//			} else if(key.equals("vLanIdiLimit")) {
//				checkItem[1] = "NEED_LAN";
//			}
//			else {
//				continue;
//			}
//			checkItem[0] = key;
//
//			checkItem[2]   = quota.getQuotaName();
//			checkItem[3]   = quota.getQuotaValue() != null ? Integer.parseInt(quota.getQuotaValue()) : 0;
//			checkItem[4] = 0.0;
//			for(Map<String, Object> orderQuota : curOrderQuotaList) {
//				if(orderQuota.get("NAME").equals(checkItem[1])) {
//					if(orderQuota.get("VALUE") instanceof Double) {
//						checkItem[4] = (Double)orderQuota.get("VALUE");
//					} else if(orderQuota.get("VALUE") instanceof Integer) {
//						checkItem[4] = Double.parseDouble(((Integer)orderQuota.get("VALUE")).toString());
//					}
//				}
//			}
//			checkItem[5] = 0.0;
//			for(Map<String, Object> allQuota : allQuotaList) {
//				if(allQuota.get("NAME").equals(checkItem[1])) {
//					checkItem[5] = allQuota.get("VALUE") != null ? (Double)allQuota.get("VALUE") : 0.0;
//				}
//			}
//			checkList.add(checkItem);
//		}
//		boolean result = true;
//		List<String> checkNames = new ArrayList<String>();
//		for(Object [] checkItem : checkList) {
//			boolean quotaCheckResult = (Integer)checkItem[3] - (Double)checkItem[4] - (Double)checkItem[5] >= 0;
//			if(!quotaCheckResult) {
//				checkNames.add((String)checkItem[2]);
//			}
//			result = result && quotaCheckResult;
//		}
//		resultMap.put("checkResult", result);
//		resultMap.put("checkMessage", StringUtils.join(checkNames, ","));
//		return resultMap;
//	}
//
//	private Map<String, Object> getCheckParentItemQuotaLimit(List<Quota> quotaList, List<Map<String, Object>> curOrderQuotaList, List<Map<String, Object>> allQuotaList) {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		List<Object []> checkList = new ArrayList<Object[]>();
//		for(Quota quota : quotaList) {
//			String key = quota.getQuotaKey();
//			Object [] checkItem = new Object [6];
//			if(key.equals("cores")) {
//				checkItem[1] = "CPU";
//			} else if(key.equals("ram")) {
//				checkItem[1] = "MEMORY";
//			} else if(key.equals("vLanIdoLimit")) {
//				checkItem[1] = "NEED_WAN";
//			} else if(key.equals("vLanIdiLimit")) {
//				checkItem[1] = "NEED_LAN";
//			}
//			else {
//				continue;
//			}
//			checkItem[0] = key;
//
//			checkItem[2]   = quota.getQuotaName();
//			checkItem[3]   = Integer.parseInt(quota.getQuotaValue());
//			for(Map<String, Object> orderQuota : curOrderQuotaList) {
//				if(orderQuota.get("NAME").equals(checkItem[1])) {
//					checkItem[4] = (Double)orderQuota.get("VALUE");
//				}
//			}
//			for(Map<String, Object> allQuota : allQuotaList) {
//				if(allQuota.get("NAME").equals(checkItem[1])) {
//					checkItem[5] = (Double)allQuota.get("VALUE");
//				}
//			}
//			checkList.add(checkItem);
//		}
//		boolean result = true;
//		List<String> checkNames = new ArrayList<String>();
//		for(Object [] checkItem : checkList) {
//			boolean quotaCheckResult = (Integer)checkItem[3] - (Double)checkItem[4] - (Double)checkItem[5] > 0;
//			if(!quotaCheckResult) {
//				checkNames.add((String)checkItem[2]);
//			}
//			result = result && quotaCheckResult;
//		}
//		resultMap.put("checkResult", result);
//		resultMap.put("checkMessage", StringUtils.join(checkNames, ","));
//		return resultMap;
//	}
//
//	@Override
//	public Boolean checkOrderSuccess(String orderId, Long serviceSid) {
//		Criteria criteria = new Criteria();
//		criteria.put("orderId", orderId);
//		criteria.put("serviceSid", serviceSid);
//		return serviceInstanceMapper.checkOrderSuccess(criteria);
//	}
//
//	@Override
//	public Boolean checkVmServiceInstanceSuccess(Long instanceSid) {
//		Criteria criteria = new Criteria();
//		criteria.put("instanceSid", instanceSid);
//		return serviceInstanceMapper.checkVmServiceInstanceSuccess(criteria);
//	}
//
//	@Override
//	public Boolean checkUnsubscribeVmSuccess(String orderId) {
//		return serviceInstanceMapper.checkUnsubscribeVmSuccess(orderId);
//	}
//
//	@Override
//	public void adjustServiceSpec(ServiceInstance instance, ServiceSpecChange<?> serviceInstanceChange, Long orignalChangeLogSid) {
//		Object changeData = serviceInstanceChange.getChangeData();
//		ServiceInstanceChangeLogSpec<Object> instanceChangeLogSpec = new ServiceInstanceChangeLogSpec<Object>();
//		instanceChangeLogSpec.setVariables(serviceInstanceChange.getVariables());
//		instanceChangeLogSpec.setParams(changeData);
//		if(orignalChangeLogSid == null) {
//			orignalChangeLogSid = this.instanceChangeLogService.submitChangeServiceInstance(instance, WebConstants.instanceChangeType.CHANGE, instanceChangeLogSpec);
//		} else {
//			this.instanceChangeLogService.updateChangeServiceInstance(instance.getInstanceSid(), WebConstants.instanceChangeType.CHANGE, instanceChangeLogSpec, new TypeReference<ServiceInstanceChangeLogSpec<ResVmInst>>() {});
//		}
//		approveRecordService.initApprove(instance.getInstanceSid().toString(), orignalChangeLogSid);
//
//	}
//
//	@Override
//	public void modifyVm(List<Map<String, Object>> instanceList) {
////		String changeTime = null;
//		Long vmInstanceSid = null;
//		String vmName = null;
//		Boolean cpuIncrease = null;
//		Boolean memoryIncrease = null;
//		Boolean needLanCreate = null;
//		Boolean needWanCreate = null;
//		Long specVersion = null;
//		ResVmInst resVmInst = new ResVmInst();
//		List<List<ServiceInstanceSpec>> diskChangeSpecs = new ArrayList<List<ServiceInstanceSpec>>();
//		//用来记录变更前磁盘，处理磁盘删除情况
//		List<ServiceInstance> diskList = new ArrayList<ServiceInstance>();
//		List<Boolean> diskExpend = new ArrayList<Boolean>();
//		boolean diskOpe = false;
//		for(Map<String, Object> instance : instanceList) {
//			if(instance.get("instanceSid")!=null){
//				Long instanceSid = Long.parseLong(instance.get("instanceSid").toString());
//				Long serviceSid = Long.parseLong(instance.get("serviceSid").toString());
//				List<Map<String, Object>> specs = (List<Map<String, Object>>)instance.get("specs");
////				if(WebConstants.ServiceSid.SERVICE_VM.equals(Long.parseLong(instance.get("serviceSid").toString()))) {
////					changeTime = ResDataUtils.getSpecValue("CHANGE_TIME", specs);
////				}
//				if(!diskOpe){
//					diskOpe = Boolean.parseBoolean(ResDataUtils.getSpecValue("DISK_OPE", specs));
//				}
//				//获取实例规格的最大版本号
//				Long maxVersion = instanceSpecMapper.selectByInstanceSpecMaxVersion(instanceSid);
//				Long validInstaceSpec = instanceSpecService.selectValidInstanceSpecByVersion(instanceSid);
//				//获取当前版本中有效规格
//				List<ServiceInstanceSpec> instanceSpecs = instanceSpecMapper.selectByInstanceSpecByVersion(instanceSid, WebConstants.SpecStatus.valid, validInstaceSpec);
//				specVersion = maxVersion + 1;
//				ServiceInstance serviceInstance = serviceInstanceMapper.selectByPrimaryKey(instanceSid);
//				if(WebConstants.ServiceSid.SERVICE_VM.equals(serviceSid)) {
//					//得到该实例的所有已开通的disk实例
//					Criteria example = new Criteria();
//					example.put("parentInstanceSid", instanceSid);
//					diskList = serviceInstanceMapper.selectByParams(example);
//
//					vmInstanceSid = instanceSid;
//					vmName = serviceInstance.getInstanceName();
//
//					for(ServiceInstanceSpec instanceSpec : instanceSpecs) {
//						instanceSpec.setSpecSid(null);
//						String newValue = ResDataUtils.getSpecValue(instanceSpec.getName(), specs);
//						String oldValue = instanceSpec.getValue();
//						if(newValue != null) {
//							if("CPU".equals(instanceSpec.getName())) {
//								if(Long.parseLong(newValue) > Long.parseLong(oldValue)) {
//									cpuIncrease = true;
//								}  else if(Long.parseLong(newValue) < Long.parseLong(oldValue)) {
//									cpuIncrease = false;
//								}
//							} else if("MEMORY".equals(instanceSpec.getName())) {
//								if(Double.parseDouble(newValue) > Double.parseDouble(oldValue)) {
//									memoryIncrease = true;
//								} else if(Double.parseDouble(newValue) < Double.parseDouble(oldValue)) {
//									memoryIncrease = false;
//								}
//							}
////							else if("NEED_LAN".equals(instanceSpec.getName())) {
////								if("0".equals(oldValue) && "1".equals(newValue)) {
////									needLanCreate = true;
////								} else if(Long.parseLong(newValue) < Long.parseLong(oldValue)) {
////									needLanCreate = false;
////								}
////							} else if("NEED_WAN".equals(instanceSpec.getName())) {
////								if("0".equals(oldValue) && "1".equals(newValue)) {
////									needWanCreate = true;
////								} else if(Long.parseLong(newValue) < Long.parseLong(oldValue)) {
////									needWanCreate = false;
////								}
////							}
//							instanceSpec.setValue(newValue);
//							instanceSpec.setStatus(WebConstants.SpecStatus.invalid);
//						} else {
//							instanceSpec.setStatus(WebConstants.SpecStatus.valid);
//						}
//						instanceSpec.setVersion(specVersion);
//						instanceSpecMapper.insertSelective(instanceSpec);
//					}
//
//					//新的网卡变更
//					for(Map<String, Object> spec : specs) {
//						String name = (String)spec.get("name");
//						if("NET_CHANGE_FLAG".equals(name)) {
//							needWanCreate = (Boolean) spec.get("value");
//							break;
//						}
//					}
//					for(Map<String, Object> spec : specs) {
//						String name = (String)spec.get("name");
//						if("NET_WORK_CHANGE".equals(name)) {
//							try {
//								resVmInst =  JsonUtil.fromJson((String)spec.get("value"), ResVmInst.class);
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//							break;
//						}
//					}
//				} else if(WebConstants.ServiceSid.SERVICE_STORAGE.equals(serviceSid)) {
//					//将没删除的去掉,页面传来的都是没删除或者增加的，将没删除的从全部里面剔除
//					for (ServiceInstance si : diskList) {
//						if(si!=null&&si.getInstanceSid().equals(instanceSid)){
//							diskList.remove(si);
//							break;
//						}
//					}
//					for(ServiceInstanceSpec instanceSpec : instanceSpecs) {
//						instanceSpec.setSpecSid(null);
//						if("DISK_SIZE".equals(instanceSpec.getName())) {
//							String newValue = ResDataUtils.getSpecValue(instanceSpec.getName(), specs);
//							String oldValue = instanceSpec.getValue();
//							if(newValue != null) {
//								instanceSpec.setValue(newValue);
//								if(!newValue.equals(oldValue)){
//									instanceSpec.setStatus(WebConstants.SpecStatus.invalid);
//									if(Long.parseLong(newValue) > Long.parseLong(oldValue)) {
//										diskExpend.add(true);
//									} else if(Long.parseLong(newValue) < Long.parseLong(oldValue)) {
//										diskExpend.add(false);
//										//diskChangeSpecs里面只保存进行了缩容的磁盘实例
//										diskChangeSpecs.add(instanceSpecs);
//									}
//									String stPurpose = ResDataUtils.getSpecValueFromSpecs("STORAGE_PURPOSE", instanceSpecs);
//									Long parentInstanceSid = serviceInstance.getParentInstanceSid();
//									//获取实例规格的最大版本号
//									Long simaxVersion = instanceSpecMapper.selectByInstanceSpecMaxVersion(parentInstanceSid);
//									List<ServiceInstanceSpec> newInstaceSpec = instanceSpecService.selectByInstanceSpecByVersion(parentInstanceSid, null, simaxVersion);
//									if(stPurpose.equals(WebConstants.StoragePurpose.SYSTEM_DISK)){
//										for (ServiceInstanceSpec siSpec : newInstaceSpec) {
//											if(siSpec.getName().equals("SYSTEM_DISK")){
//												siSpec.setStatus(WebConstants.SpecStatus.invalid);
//												siSpec.setValue(newValue);
//												this.instanceSpecMapper.updateByPrimaryKeySelective(siSpec);
//											}
//										}
//									}else{
//										for (ServiceInstanceSpec siSpec : newInstaceSpec) {
//											if(siSpec.getName().equals("DATA_DISK")){
//												Long old = Long.parseLong(siSpec.getValue());
//												siSpec.setStatus(WebConstants.SpecStatus.invalid);
//												siSpec.setValue((old- Long.parseLong(oldValue)+Long.parseLong(newValue))+"");
//												this.instanceSpecMapper.updateByPrimaryKeySelective(siSpec);
//											}
//										}
//									}
//								}else{
//									instanceSpec.setStatus(WebConstants.SpecStatus.valid);
//								}
//							}
//						}else{
//							instanceSpec.setStatus(WebConstants.SpecStatus.valid);
//						}
//						instanceSpec.setVersion(specVersion);
//						instanceSpecMapper.insertSelective(instanceSpec);
//					}
//				}
//			}else{
//				//instanceSid为空，说明是新增的disk
//				ServiceDefineService service = SpringContextHolder
//						.getBean("serviceDefineServiceImpl");
//				ServiceSpecService serviceSpec = SpringContextHolder
//						.getBean("serviceSpecServiceImpl");
//				Long serviceSid = Long.parseLong(instance.get("serviceSid").toString());
//				Long parentInstanceSid = Long.parseLong(instance.get("parentInstanceSid").toString());
//				List<Map<String, Object>> specs = (List<Map<String, Object>>)instance.get("specs");
//				ServiceDefine serviceDefine = service.selectByPrimaryKey(serviceSid);
//				if(WebConstants.ServiceSid.SERVICE_STORAGE.equals(serviceSid)) {
//					//添加serviceInstance
//					Long instanceSid = parentInstanceSid;
//					Long maxVersion = instanceSpecMapper.selectByInstanceSpecMaxVersion(instanceSid);
//					ServiceInstance si = new ServiceInstance();
//					si.setParentInstanceSid(parentInstanceSid);
//					si.setInstanceName(instance.get("diskName").toString());
//					si.setDescription(serviceDefine.getDescription());
//					si.setServiceSid(serviceSid);
//					si.setOwnerId(AuthUtil.getAuthUser().getAccount());
////					si.setMgtObjSid(AuthUtil.getAuthUser().getMgtObjSid());
//					si.setTanentId(AuthUtil.getAuthUser().getTenantSid().toString());
//					// 实例创建开始结束时间
//					si.setCreationDateBegin(new Date());
//					si.setCreationDateEnd(new Date());
//					WebUtil.prepareInsertParams(si);
//					this.serviceInstanceMapper.insertSelective(si);
//					//添加instance_spec
//					Criteria example = new Criteria();
//					example.put("instanceName", instance.get("diskName").toString());
//					example.put("serviceSid", serviceSid);
//					example.put("parentInstanceSid", parentInstanceSid);
//					List<ServiceInstance> ins = this.serviceInstanceMapper.selectByParams(example);
//					if(ins!=null&&ins.size()!=0){
//						Criteria criteria = new Criteria();
//						criteria.put("serviceSid", WebConstants.ServiceSid.SERVICE_STORAGE);
//						List<ServiceSpec> serviceSpecList = serviceSpec
//								.selectByParams(criteria);
//						for (ServiceSpec sSpec : serviceSpecList) {
//							ServiceInstanceSpec instanceSpec = new ServiceInstanceSpec();
//							instanceSpec.setInstanceSid(ins.get(0).getInstanceSid());
//							instanceSpec.setName(sSpec.getName());
//							instanceSpec.setDescription(sSpec.getDescription());
//							instanceSpec.setSequence(sSpec.getSequence());
//							instanceSpec.setTag(sSpec.getTag());
//							instanceSpec.setUnit(sSpec.getUnit());
//							//保证版本的一致，新增的不再从1开始
//							instanceSpec.setVersion(maxVersion);
//							// 设置服务实例规格中的值
//							if (instanceSpec.getName().equals(
//									WebConstants.InstanceSpecType.STORAGE_TYPE)) {
//								instanceSpec.setValue("");
//							} else if (instanceSpec.getName().equals( WebConstants.InstanceSpecType.STORAGE_PURPOSE)) {
//								instanceSpec.setValue(WebConstants.StoragePurpose.DATA_DISK);
//							} else if (instanceSpec.getName().equals( WebConstants.InstanceSpecType.DISK_SIZE)) {
//								instanceSpec.setValue(ResDataUtils.getSpecValue("DISK_SIZE", specs));
//							}
//							instanceSpec.setGroupName(sSpec.getGroupName());
//							instanceSpec.setCreatedBy(AuthUtil.getAuthUser().getAccount());
//							instanceSpec.setUpdatedBy(AuthUtil.getAuthUser().getAccount());
//							instanceSpec.setCreatedDt(new Date());
//							instanceSpec.setUpdatedDt(new Date());
////							WebUtil.prepareInsertParams(instanceSpec);
//							this.instanceSpecService.insertSelective(instanceSpec);
//						}
//					}
//					diskExpend.add(true);
//				}
//				//获取实例规格的最大版本号
//				Long maxVersion = instanceSpecMapper.selectByInstanceSpecMaxVersion(parentInstanceSid);
//				//获取当前版本中未生效Vm规格
//				List<ServiceInstanceSpec> newInstaceSpec = instanceSpecService.selectByInstanceSpecByVersion(parentInstanceSid, null, maxVersion);
//				boolean f = false;
//				for (ServiceInstanceSpec siSpec : newInstaceSpec) {
//					if(siSpec.getName().equals("DATA_DISK")){
//						f = true;
//						Long old = Long.parseLong(siSpec.getValue()==null?"0":siSpec.getValue());
//						siSpec.setStatus(WebConstants.SpecStatus.invalid);
//						siSpec.setValue((old+Long.parseLong(ResDataUtils.getSpecValue("DISK_SIZE", specs)))+"");
//						this.instanceSpecMapper.updateByPrimaryKeySelective(siSpec);
//						break;
//					}
//				}
//				if(!f){
//					//没有数据盘规格，则添加这个规格
//					ServiceInstanceSpec instanceSpec = new ServiceInstanceSpec();
//					instanceSpec.setInstanceSid(parentInstanceSid);
//					instanceSpec.setName("DATA_DISK");
//					instanceSpec.setDescription("外置存储盘");
//					instanceSpec.setSequence("3");
//					instanceSpec.setUnit("GB");
//					//保证版本的一致，新增的不再从1开始
//					instanceSpec.setVersion(maxVersion);
//					instanceSpec.setValue(Long.parseLong(ResDataUtils.getSpecValue("DISK_SIZE", specs))+"");
//					instanceSpec.setCreatedBy(AuthUtil.getAuthUser().getAccount());
//					instanceSpec.setUpdatedBy(AuthUtil.getAuthUser().getAccount());
//					instanceSpec.setCreatedDt(new Date());
//					instanceSpec.setUpdatedDt(new Date());
//					this.instanceSpecService.insertSelective(instanceSpec);
//				}
//			}
//		}
//		//判断删除的磁盘
//		if(diskList.size()>0&&diskOpe){
//			Integer diskSize = 0;
//			for (ServiceInstance disk : diskList) {
//				//判断这个磁盘是不是已经删除的
//				if(instanceSpecService.checkInstanceValid(disk.getInstanceSid())){
//					//获取实例规格的最大版本号
//					Long maxVersion = instanceSpecMapper.selectByInstanceSpecMaxVersion(disk.getParentInstanceSid());
//					Long validInstaceSpec = instanceSpecService.selectValidInstanceSpecByVersion(disk.getInstanceSid());
//					//获取当前版本中有效规格
//					List<ServiceInstanceSpec> instanceSpecs = instanceSpecMapper.selectByInstanceSpecByVersion(disk.getInstanceSid(), WebConstants.SpecStatus.valid, validInstaceSpec);
////					specVersion = maxVersion + 1;
//					for(ServiceInstanceSpec instanceSpec : instanceSpecs) {
//						instanceSpec.setSpecSid(null);
//						if("DISK_SIZE".equals(instanceSpec.getName())) {
//							diskSize = diskSize + Integer.parseInt(instanceSpec.getValue());
//							String newValue = "0";
//							instanceSpec.setValue(newValue);
//						}
//						instanceSpec.setStatus(WebConstants.SpecStatus.invalid);
//						instanceSpec.setVersion(maxVersion);
//						instanceSpecMapper.insertSelective(instanceSpec);
//					}
//					//删除的磁盘不走工单，直接删除
////					diskChangeSpecs.add(instanceSpecs);
//				}
//			}
//			//获取实例规格的最大版本号
//			Long maxVersion = instanceSpecMapper.selectByInstanceSpecMaxVersion(vmInstanceSid);
//			//获取当前版本中未生效Vm规格
//			List<ServiceInstanceSpec> newInstaceSpec = instanceSpecService.selectByInstanceSpecByVersion(vmInstanceSid, null, maxVersion);
//			boolean f = false;
//			for (ServiceInstanceSpec siSpec : newInstaceSpec) {
//				if(siSpec.getName().equals("DATA_DISK")){
//					f = true;
//					Long old = Long.parseLong(siSpec.getValue()==null?"0":siSpec.getValue());
//					siSpec.setStatus(WebConstants.SpecStatus.invalid);
//					siSpec.setValue((old-diskSize.longValue())+"");
//					this.instanceSpecMapper.updateByPrimaryKeySelective(siSpec);
//					break;
//				}
//			}
//		}
//
//		//创建变更记录信息
//		if(vmInstanceSid == null || specVersion == null) {
//			throw new RuntimeException("Not enough data to insert service_change_log table. vmInstanceSid=" + vmInstanceSid +
//					" specVersion=" + specVersion);
//		}
//		ServiceChangeLog changeLog = new ServiceChangeLog();
//		changeLog.setInstanceSid(vmInstanceSid);
//		if(specVersion != null) {
//			changeLog.setSpecVersion(specVersion);
//		}
////		changeLog.setCmSponsor(AuthUtil.getAuthUser().getAccount());
//		changeLog.setCmBeginTime(new Date());
//		changeLog.setCmType(0L);
//		changeLog.setCmComments("云主机变更");
//		changeLog.setStatus(WebConstants.ServiceChangeStatus.NOT_CHANGE);
//
//		//添加具体变更内容----暂时只有网卡
//		String changecontent = new String();
////		changecontent = JsonUtil.toJson(resVmInst);
//		changeLog.setChangeContent(changecontent);
//
////		try {
////			changeLog.setSugTime(DateUtils.parseDate(changeTime, new String[] {"yyyy-MM-dd HH:mm:ss"}));
////		} catch (ParseException e) {
////			new RuntimeException(e);
////		}
//		serviceChangeLogMapper.insert(changeLog);
//
//		//如果是申请追加资源，则进行审批
//		if(((cpuIncrease != null && cpuIncrease)
//				|| (memoryIncrease != null && memoryIncrease)
//				|| (needLanCreate != null && needLanCreate)
//				|| (needWanCreate != null && needWanCreate))
//				|| (diskExpend.size() > 0 && checkAllDiskExpend(diskExpend))) {
////			approveRecordService.initApprove(vmInstanceSid.toString());
//		}
//
//		//如果是CPU减小, MEMROY减小, 内网删除，外网删除，磁盘无变化，直接调用接口
//		if(((cpuIncrease == null || (cpuIncrease != null && !cpuIncrease))
//				&& (memoryIncrease == null || (memoryIncrease != null && !memoryIncrease))
//				&& (needLanCreate == null || (needLanCreate != null && !needLanCreate))
//				&& (needWanCreate == null || (needWanCreate != null && !needWanCreate))) && (diskExpend.size() == 0)){
////			vmChangeHanlder.invoke(vmInstanceSid);
//		}
//
//		//如果是磁盘缩容，生成工单
//		if(diskExpend.size() > 0 &&  !checkAllDiskExpend(diskExpend)) {
//			String type = "云主机磁盘缩容";
//			ServiceInstance instance = serviceInstanceMapper.selectByPrimaryKey(vmInstanceSid);
//			StringBuilder content = new StringBuilder();
//			content.append("实例名称: ").append(StringUtil.nullToEmpty(vmName)).append("\r\n");
//			content.append("请求变更磁盘: ").append("\r\n");
//			for(List<ServiceInstanceSpec> diskSpecs : diskChangeSpecs) {
//				if(diskSpecs.size() > 0) {
//					ServiceInstance diskInstance = serviceInstanceMapper.selectByPrimaryKey(diskSpecs.get(0).getInstanceSid());
////					String value = ResDataUtils.getSpecValueFromSpecs("DISK_SIZE", diskSpecs);
////					if(StringUtil.isNullOrEmpty(value)){
////						content.append("磁盘名称: ").append(diskInstance.getInstanceName()).append("删除").append("\r\n");
////					}else{
////						content.append("磁盘名称: ").append(diskInstance.getInstanceName()).append("缩容为")
////								.append(value).append("\r\n");
////					}
//				}
//			}
//			Ticket ticket = new Ticket();
//			ticket.setQuestionType("99");
//			ticket.setQuestionLevel(WebConstants.QuestionLevel.BEST_HIGH);
//			ticket.setTitle(type + "-" + vmInstanceSid);
//			ticket.setServiceSid(instance.getServiceSid());
//			ticket.setTicketNo(sidService.getMaxSid(WebConstants.SidCategory.TICKET));
//			ticket.setVersion(1L);
//			ticket.setTenantSid(Long.parseLong(instance.getTanentId()));
//			ticket.setContent(content.toString());
//			ticket.setAutoHandlerFlag(0);
////			ticket.setTicketUser(AuthUtil.getAuthUser().getAccount());
////			ticket.setCreatedBy(AuthUtil.getAuthUser().getAccount());
//			ticket.setCreatedDt(new Date());
//			ticket.setStatus(WebConstants.TicketStatus.CREATED);
//			//新加的字段
//			ticket.setProcessType(WebConstants.TicketProcessType.DISK_REDUCE);
//			ticket.setProcessObjectId(vmInstanceSid);
//			ticketMapper.insertSelective(ticket);
////			mailNotificationsService.ticketNoticeEmail(ticket,WebConstants.RoleSid.OM_MANAGERKLB);
//		}
//	}
//
//
//	private Boolean checkAllDiskExpend(List<Boolean> diskExpend) {
//		Boolean diskExpendFlag = false;
//		for(Boolean diskFlag : diskExpend) {
//			if(diskFlag != null) {
//				diskExpendFlag = diskExpendFlag || diskFlag;
//			}
//		}
//		return diskExpendFlag;
//	}
//
//	@Override
//	public List<ServiceInstance> selectByParamsForBar(Criteria example) {
//		return serviceInstanceMapper.selectByParamsForBar(example);
//	}
//
//	@Override
//	public void insertApprove(Long instanceSid) {
//		//得到闲置资源的原值
////		Criteria example1 = new Criteria();
////		example1.put("instanceSid", instanceSid);
////		example1.put("status", 3L);
////		List<FreeRes> fres = freeresService.selectByFreeInstance(example1);
////		//得到变更后的值
////		Criteria example2 = new Criteria();
////		example2.put("fresSid", fres.get(0).getFresSid());
////		List<FreeResCheckLog> frc = checkLogService.selectByParams(example2);
////		//查询当前虚拟机实例规格
////		List<ServiceInstanceSpec> instanceSpecs = instanceSpecMapper.selectByInstanceSpecBySid(instanceSid);
////		Criteria example3 = new Criteria();
////		example3.put("parentInstanceSid", instanceSid);
////		List<ServiceInstance> instances = serviceInstanceMapper.selectByParams(example3);
////		for (ServiceInstance serviceInstance : instances) {
////			if(serviceInstance.getInstanceName().equals("系统盘")){
////				instanceSpecs.addAll(instanceSpecMapper.selectByInstanceSpecBySid(serviceInstance.getInstanceSid()));
////			}
////		}
////		//设置闲置资源回收实例规格
////		List<ServiceInstanceSpec> diskChangeSpecs = new ArrayList<ServiceInstanceSpec>();
////		Long version = null;
////		List<Boolean> diskExpend = new ArrayList<Boolean>();
////		for(ServiceInstanceSpec instanceSpec : instanceSpecs) {
////			instanceSpec.setSpecSid(null);
////			if("CPU".equals(instanceSpec.getName())) {
////				String value = frc.get(0).getCpuCores().toString();
////				if(value != null) {
////					instanceSpec.setValue(value);
////					instanceSpec.setStatus(WebConstants.SpecStatus.invalid);
////				}
////			} else if("MEMORY".equals(instanceSpec.getName())) {
////				String value = frc.get(0).getMemory().toString();
////				if(value != null) {
////					instanceSpec.setValue(value);
////					instanceSpec.setStatus(WebConstants.SpecStatus.invalid);
////				}
////			} else if("DISK_SIZE".equals(instanceSpec.getName())) {
////				String value = frc.get(0).getStorage().toString();
////				if(value != null) {
////					if(Integer.parseInt(instanceSpec.getValue())<Double.parseDouble(value)){
////						diskExpend.add(true);
////					}else{
////						//磁盘容量变小，则生成工单
////						diskExpend.add(false);
////					}
////					instanceSpec.setValue(value);
////					instanceSpec.setStatus(WebConstants.SpecStatus.invalid);
////					diskChangeSpecs.add(instanceSpec);
////				}
////			}else if("NEED_WAN".equals(instanceSpec.getName())){
//////				String value = frc.get(0).getPubIp();
//////				if("-".equals(value)) {
//////					instanceSpec.setValue("0");
//////					instanceSpec.setStatus(WebConstants.SpecStatus.invalid);
//////				}
////			} else if("NEED_LAN".equals(instanceSpec.getName())) {
//////				String value = frc.get(0).getPrivageIp();
//////				if("-".equals(value)) {
//////					instanceSpec.setValue("0");
//////					instanceSpec.setStatus(WebConstants.SpecStatus.invalid);
//////				}
////			} else {
////				instanceSpec.setStatus(WebConstants.SpecStatus.valid);
////			}
////			version = instanceSpec.getVersion() + 1;
////			instanceSpec.setVersion(version);
////			instanceSpecMapper.insertSelective(instanceSpec);
////		}
//
//		//创建变更记录信息
//		ServiceChangeLog changeLog = new ServiceChangeLog();
//		changeLog.setInstanceSid(instanceSid);
//		if(version != null) {
//			changeLog.setSpecVersion(version);
//		}
////		changeLog.setCmSponsor(AuthUtil.getAuthUser().getAccount());
//		changeLog.setCmBeginTime(new Date());
//		changeLog.setCmType(1L);
//		changeLog.setCmComments("闲置资源回收");
//		changeLog.setStatus(WebConstants.ServiceChangeStatus.NOT_CHANGE);
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 12);
//		changeLog.setSugTime(calendar.getTime());
//		serviceChangeLogMapper.insertSelective(changeLog);
//
//		Criteria criteria = new Criteria();
//		criteria.put("instanceSid", instanceSid);
//		criteria.put("specVersion", changeLog.getSpecVersion());
//		criteria.put("cmSponsor", changeLog.getCmSponsor());
//		criteria.put("cmType", 1L);
//		criteria.put("cmComments", "闲置资源回收");
//		criteria.put("sugTime", changeLog.getSugTime());
//		List<ServiceChangeLog> changeLogs = serviceChangeLogMapper.selectByParams(criteria);
//
////		if(changeLogs!=null&&changeLogs.size()!=0){
////			frc.get(0).setCmSid(changeLogs.get(0).getCmSid());
////			checkLogService.updateByPrimaryKeySelective(frc.get(0));
////		}
////
////		vmChangeHanlder.invoke(instanceSid);
//
//		//如果是磁盘缩容，生成工单
//		if(diskExpend.size() > 0 &&  !checkAllDiskExpend(diskExpend)) {
//			String type = "云主机磁盘缩容";
//			ServiceInstance instance = serviceInstanceMapper.selectByPrimaryKey(instanceSid);
//			StringBuilder content = new StringBuilder();
//			content.append("实例名称: ").append(StringUtil.nullToEmpty(instance.getInstanceName())).append("\r\n");
//			content.append("请求变更磁盘: ").append("\r\n");
//			for(ServiceInstanceSpec diskSpecs : diskChangeSpecs) {
//				ServiceInstance diskInstance = serviceInstanceMapper.selectByPrimaryKey(diskSpecs.getInstanceSid());
//				content.append("磁盘名称: ").append(diskInstance.getInstanceName()).append("缩容为")
//						.append(diskSpecs.getValue()).append("\r\n");
//			}
//			Ticket ticket = new Ticket();
//			ticket.setQuestionType("99");
//			ticket.setQuestionLevel(WebConstants.QuestionLevel.BEST_HIGH);
//			ticket.setTitle(type + "-" + instanceSid);
//			ticket.setServiceSid(instance.getServiceSid());
//			ticket.setTicketNo(sidService.getMaxSid(WebConstants.SidCategory.TICKET));
//			ticket.setVersion(1L);
//			ticket.setTenantSid(Long.parseLong(instance.getTanentId()));
//			ticket.setContent(content.toString());
////			ticket.setTicketUser(AuthUtil.getAuthUser().getAccount());
////			ticket.setCreatedBy(AuthUtil.getAuthUser().getAccount());
//			ticket.setCreatedDt(new Date());
//			ticket.setStatus(WebConstants.TicketStatus.CREATED);
//			ticket.setProcessType(WebConstants.TicketProcessType.DISK_REDUCE);
//			ticket.setProcessObjectId(instanceSid);
//			ticketMapper.insertSelective(ticket);
//		}
//	}
//
//	public void modifyAllInstanceStatus(ServiceInstance instance) {
//
//		//查询虚拟机实例的磁盘实例
//		Criteria criteria = new Criteria();
//		criteria.put("parentInstanceSid", instance.getInstanceSid());
//		criteria.put("serviceSid", WebConstants.ServiceSid.SERVICE_STORAGE);
//		List<ServiceInstance> diskInstances = selectByParams(criteria);
//		//将云主机所属磁盘的服务实例状态更新为已开通
//		for(ServiceInstance diskInstance : diskInstances) {
//			diskInstance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
//			updateByPrimaryKeySelective(instance);
//		}
//
//		//更新实例状态为开通
//		instance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
//		//更新开通时间
//		instance.setDredgeDate(new Date());
//		updateByPrimaryKeySelective(instance);
//
//	}
//
//	public void modifyDiskServiceInstancesOfVmStatus(Long instanceSid, String status) {
//		modifyChildServiceInstancesOfVmStatus(instanceSid, status, WebConstants.ServiceSid.SERVICE_STORAGE);
//	}
//
//	public void modifyAllChildServiceInstancesOfVmStatus(Long instanceSid, String status) {
//		modifyChildServiceInstancesOfVmStatus(instanceSid, status, null);
//	}
//
//	public void modifyChildServiceInstancesOfVmStatus(Long instanceSid, String status, Long serviceSid) {
//		//查询虚拟机实例的磁盘实例
//		Criteria criteria = new Criteria();
//		criteria.put("parentInstanceSid", instanceSid);
//		if(serviceSid != null) {
//			criteria.put("serviceSid", serviceSid);
//		}
//		List<ServiceInstance> diskInstances = selectByParams(criteria);
//		//将云主机所属磁盘的服务实例状态更新
//		for(ServiceInstance diskInstance : diskInstances) {
//			diskInstance.setStatus(status);
//			this.updateByPrimaryKeySelective(diskInstance);
//		}
//	}
//
//	public void modifyVmServiceInstanceStatus(ServiceInstance vmServiceInstance) {
//		if(checkVmServiceInstanceSuccess(vmServiceInstance.getInstanceSid())) {
//
//			//更新实例状态为开通
//			vmServiceInstance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
//
//			//更新开通时间
//			vmServiceInstance.setDredgeDate(new Date());
//			this.updateByPrimaryKeySelective(vmServiceInstance);
//
//			//结束服务实例开通日志
//			changeLogService.endChangeServiceInstance(vmServiceInstance, WebConstants.instanceChangeType.CREATE);
//
//			//发送邮件通知
//			//mailNotificationsService.launchServiceEmail(vmServiceInstance.getInstanceSid());
//		}
//	}
//
//	public void modifyOrderStatusOfVmServiceInstance(String orderId) {
////		if(checkOrderSuccess(orderId, WebConstants.ServiceSid.SERVICE_VM)) {
////			//更新订单状态为已完成
////			Criteria criteria = new Criteria();
////			criteria.put("orderId", orderId);
////			List<Order> orders = orderService.selectByParams(criteria);
////			Order order = null;
////			if(orders.size() > 0){
////				order = orders.get(0);
////				order.setStatus(WebConstants.ORDER_STATUS.OPENED);
////				order.setDredgeDate(new Date());
////				orderService.updateByPrimaryKeySelective(order);
////
////				//订单下所有的实例创建成功，发送订单成功邮件
////				mailNotificationsService.launchServiceEmail(orderId);
////			}
////		}
//	}
//
//	@Override
//	public List<ServiceInstance> selectByParamsOrderByPurpose(Criteria criteria) {
//		return this.serviceInstanceMapper.selectByParamsOrderByPurpose(criteria);
//	}
//
//	@Override
//	public List<ServiceInstance> selectCountByParams(Criteria example) {
//		return this.serviceInstanceMapper.selectCountByParams(example);
//	}
//
//	@Override
//	public String selectVdNameByInstanceSid(Long instanceSid) {
//		return this.serviceInstanceMapper.selectVdNameByInstanceSid(instanceSid);
//	}
//
//	public void modifyInstanceSpec(Map map) {
////		Long instanceSid = ((Integer)map.get("instanceSid")).longValue();
////		String orderId = (String)map.get("orderId");
////		String instanceName = (String)map.get("instanceName");
////		String resInstanceName  = (String)map.get("resInstanceName");
////		String updateAllFlag = (String)map.get("updateAllFlag");
////		if("1".equals(updateAllFlag)) {
////			Criteria criteria = new Criteria();
////			criteria.put("orderId", orderId);
////			List<ServiceInstance> instancelist = this.serviceInstanceMapper.selectByParams(criteria);
////			for(ServiceInstance instance : instancelist) {
////				updateInstanceSpec(instance.getInstanceSid(), map);
////			}
////		} else if("0".equals(updateAllFlag)) {
////			ServiceInstance serviceInstance = this.serviceInstanceMapper.selectByPrimaryKey(instanceSid);
////			serviceInstance.setInstanceName(instanceName);
////			serviceInstance.setResInstName(resInstanceName);
////			this.serviceInstanceMapper.updateByPrimaryKeySelective(serviceInstance);
////			updateInstanceSpec(instanceSid, map);
////			//更新service_instance_change_log表中的开通配置参数
////			ServiceInstanceChangeLogSpec<ResVmInst> instanceChangeLogSpec = new ServiceInstanceChangeLogSpec<ResVmInst>();
////			ResVmInst resVmInst = new ResVmInst();
////			resVmInst.setResVmInstName(resInstanceName);
////			resVmInst.setVmSystemName(instanceName);
////			instanceChangeLogSpec.setParams(resVmInst);
////			this.instanceChangeLogService.updateChangeServiceInstance(instanceSid, WebConstants.instanceChangeType.CREATE, instanceChangeLogSpec, new TypeReference<ServiceInstanceChangeLogSpec<ResVmInst>>() {
////			});
////		}
//	}
//
//	private void updateInstanceSpec(Long instanceSid, Map specMap) {
//		List<ServiceInstanceSpec> specList = this.instanceSpecMapper.selectByInstanceSid(instanceSid);
//		for(ServiceInstanceSpec spec : specList) {
//			if("CPU".equals(spec.getName())) {
//				spec.setValue(((Integer)specMap.get("CPU")).toString());
//				this.instanceSpecMapper.updateByPrimaryKey(spec);
//			} else if("MEMORY".equals(spec.getName())) {
//				spec.setValue(((Integer)specMap.get("MEMORY")).toString());
//				this.instanceSpecMapper.updateByPrimaryKey(spec);
//			} else if("OS".equals(spec.getName())) {
//				//spec.setValue((String)specMap.get("OS"));
//				//this.instanceSpecMapper.updateByPrimaryKey(spec);
//			} else if("NEED_WAN".equals(spec.getName())) {
////				spec.setValue((String)specMap.get("NEED_WAN"));
////				this.instanceSpecMapper.updateByPrimaryKey(spec);
//			} else if("NEED_LAN".equals(spec.getName())) {
////				spec.setValue((String)specMap.get("NEED_LAN"));
////				this.instanceSpecMapper.updateByPrimaryKey(spec);
//			}
//		}
//	}
//
//	public ServiceInstanceChangeLogSpec<ResVmInst> assembleServiceInstanceOpenParams(ServiceInstance serviceInstance, Map<String, Object> map) {
//
////		Map resSetMap = approveRecordService.getInstanceResSet((List<Map<String, Object>>)map.get("resSet"), serviceInstance.getInstanceSid());
////		String resType = (String)resSetMap.get("resType");
////		String partitionType = (String)resSetMap.get("partitionType");
////
////		ResVmInst resVmInst = new ResVmInst();
////		//设置开通虚拟机磁盘信息
////		List<ResVdInst> diskInstes = new ArrayList<ResVdInst>();
////		//网络配置
////		List<ResNetworkInst> nets = new ArrayList<ResNetworkInst>();
////		Long instanceSid = serviceInstance.getInstanceSid();
////		resVmInst.setVmSystemName(serviceInstance.getInstanceName());
////		resVmInst.setResVmInstName(serviceInstance.getResInstName());
//		//设置分区类型
////		if(StringUtils.isNotBlank(partitionType)) {
////			logger.debug("partitionType=" + partitionType);
////			resVmInst.setPartitionType(partitionType);
////		}
//		//设置CPU资源池
////		String cpuResPool = (String)resSetMap.get("cpuResPool");
////		if(StringUtils.isNotBlank(cpuResPool)) {
////			resVmInst.setCpuPoolSid(cpuResPool);
////		}
//		//设置虚拟机交换机
////		String virtualSwitch = (String)resSetMap.get("virtualSwitch");
////		if(StringUtils.isNotBlank(virtualSwitch)) {
////			resVmInst.setVirtualSwitchSid(virtualSwitch);
////		}
////		List<ServiceInstanceSpec> instanceSpecs = instanceSpecService.selectByInstanceSpecBySid(instanceSid);
////		String resPoolType = (String)resSetMap.get("resPoolType");
////		String ve = (String)resSetMap.get("ve");
//
//		//如果是申请虚拟机
////		if("1".equals(resType)) {
//			//内部网络
////			String needLan = ResDataUtils.getSpecValueFromSpecs(WebConstants.InstanceSpecType.NEED_LAN, instanceSpecs);
////			if("1".equals(needLan)) {
////				String intranetVlanSid = (String)resSetMap.get("vLanIDI");
////				if(!StringUtil.isNullOrEmpty(intranetVlanSid)) {
////					ResNetworkInst intranetVlan = new ResNetworkInst();
////					intranetVlan.setResNetworkId(intranetVlanSid);
////					intranetVlan.setResNetworkType(WebConstants.ResNetworkType.PRIVATE);
////					intranetVlan.setIpAddress((String)resSetMap.get("lanIp"));
////					String networkHabCard = (String)resSetMap.get("networkHbaCard");
////					if(StringUtils.isNotBlank(networkHabCard)) {
////						intranetVlan.setNetHBASid(networkHabCard);
////					}
////					nets.add(intranetVlan);
////				}
////			}
//			//外部网络
////			String needWan = ResDataUtils.getSpecValueFromSpecs(WebConstants.InstanceSpecType.NEED_WAN, instanceSpecs);
////			if("1".equals(needWan)) {
////				String internetVlanSid = (String)resSetMap.get("vLanIDO");
////				if(!StringUtil.isNullOrEmpty(internetVlanSid)) {
////					ResNetworkInst internetVlan = new ResNetworkInst();
////					internetVlan.setResNetworkId(internetVlanSid);
////					internetVlan.setResNetworkType(WebConstants.ResNetworkType.PUBLIC);
////					internetVlan.setIpAddress((String)resSetMap.get("wanIp"));
////					//				internetVlan.setNetPrimary(WebConstants.NetPrimary.P);
////					nets.add(internetVlan);
////				}
////			}
////			resVmInst.setNets(nets);
//		}
//
//		//分配目标计算资源信息
////		Long mgtObjSid = serviceInstance.getMgtObjSid();
//////		resVmInst.setMgtObjSid(mgtObjSid);
////		String computeResSetSid = (String)resSetMap.get("rescomuteid");
////		String computeResType = (String)resSetMap.get("rescomuteType");
////		List<String> resHostIds = this.getAllocHostIds(computeResSetSid, computeResType, mgtObjSid, resPoolType, partitionType);
//		//设置可选的计算资源主机信息
////		resVmInst.setAllocateResHostIds(resHostIds);
////
////		for(ServiceInstanceSpec instanceSpec : instanceSpecs) {
////			String value = instanceSpec.getValue();
////			String name = instanceSpec.getName();
////			if (StringUtil.isNullOrEmpty(value)) {
////				continue;
////			}
////			else {
////				if(WebConstants.InstanceSpecType.CPU.equals(name) ) {
////					resVmInst.setCpu(Integer.parseInt(value));
////				} else if(WebConstants.InstanceSpecType.MEMORY.equals(name)) {
////					Double memory = Double.parseDouble(value) * 1024;
////					resVmInst.setMemory(memory.longValue());
////				} else if(WebConstants.InstanceSpecType.OS.equals(name)) {
////					resVmInst.setOsVersion(value);
////				}
////			}
////		};
//
//		//设置软件操作系统版本
////		String osVersion = resVmInst.getOsVersion();
////		if(StringUtils.isNotBlank(osVersion)) {
////			String osType = null;
////			Criteria criteria = new Criteria();
////			criteria.put("codeCategory", WebConstants.CodeCategroy.OS_VERSION);
////			criteria.put("codeValue", osVersion);
////			List<Code> codes = codeService.selectByParams(criteria);
////			if(codes.size() > 0) {
////				osType = codes.get(0).getParentCodeValue();
////			}
//////			if(osType != null) {
//////				resVmInst.setOsType(osType);
//////			}
////		}
//
//
//		//查询虚拟机实例的磁盘实例
////		Criteria criteria = new Criteria();
////		criteria.put("parentInstanceSid", instanceSid);
////		criteria.put("serviceSid", WebConstants.ServiceSid.SERVICE_STORAGE);
////		List<ServiceInstance> diskInstances = this.selectByParams(criteria);
////		List<String> diskInstanceSids = new ArrayList<String>();
////		Integer diskIndex = 1;
////		DecimalFormat format = new DecimalFormat("00");
////		for(ServiceInstance diskInstance : diskInstances) {
////			List<ServiceInstanceSpec> diskInstanceSpecs = instanceSpecService.selectByInstanceSpecBySid(diskInstance.getInstanceSid());
////
////			String valueDiskSize = ResDataUtils.getSpecValueFromSpecs(WebConstants.InstanceSpecType.DISK_SIZE, diskInstanceSpecs);
////			String valueStorePurpose = ResDataUtils.getSpecValueFromSpecs(WebConstants.InstanceSpecType.STORAGE_PURPOSE, diskInstanceSpecs);
////			String mountPoint = ResDataUtils.getSpecValueFromSpecs(WebConstants.InstanceSpecType.MOUNT_POINT, diskInstanceSpecs);
////			String fileSystem = ResDataUtils.getSpecValueFromSpecs(WebConstants.InstanceSpecType.FILE_SYSTEM, diskInstanceSpecs);
////
////			ResVdInst resVdInst = new ResVdInst();
////			resVdInst.setStoragePurpose(valueStorePurpose);
////			if(WebConstants.StoragePurpose.DATA_DISK.equals(valueStorePurpose)) {
////				//设置挂载点
////				resVdInst.setMountPoint(mountPoint);
////				//设置存储卷名
////				String volumeNamePrefix = PropertiesUtil.getProperty("data.storage.volume.name.prefix");
////				resVdInst.setVolumeName(volumeNamePrefix + format.format(diskIndex));
////				//设置文件系统
////				resVdInst.setFileSystem(fileSystem);
////			}
////
////			if (!StringUtil.isNullOrEmpty(valueDiskSize)){
////				resVdInst.setDiskSize(Long.parseLong(valueDiskSize));
////			}
////			if(WebConstants.StoragePurpose.SYSTEM_DISK.equals(valueStorePurpose)) {
////				String rootHbaCard = (String)resSetMap.get("rootHbaCard");
////				if(StringUtils.isNotBlank(rootHbaCard)) {
////					resVdInst.setSysDiskHBASid(rootHbaCard);
////				}
////			}
////			diskInstes.add(resVdInst);
////			diskInstanceSids.add(diskInstance.getInstanceSid().toString());
////			diskIndex++;
////		}
//
////		resVmInst.setDataDisks(diskInstes);
//
//		//查询虚拟机实例的软件实例
////		List<ResOsSoftware> osSoftwares = new ArrayList<ResOsSoftware>();
////		criteria = new Criteria();
////		criteria.put("parentInstanceSid", instanceSid);
////		criteria.put("serviceSid", WebConstants.ServiceSid.SERVICE_DEPLOYMENT);
////		List<ServiceInstance> softwareInstances = this.selectByParams(criteria);
////		List<String> softwareInstanceSids = new ArrayList<String>();
////		for(ServiceInstance softwareInstance : softwareInstances) {
////			ResOsSoftware software = new ResOsSoftware();
////			software.setSoftwareVersion(softwareInstance.getInstanceName());
////			osSoftwares.add(software);
////			softwareInstanceSids.add(softwareInstance.getInstanceSid().toString());
////		}
////		resVmInst.setSoftwares(osSoftwares);
//
//		//设置存储HBA卡的数据
////		String stHbaCard = (String)resSetMap.get("stHbaCard");
////		if(!StringUtil.isNullOrEmpty(stHbaCard)){
////			resVmInst.setStHbaCardSids(stHbaCard);
////		}
//
//		//组织开通参数
////		ServiceInstanceChangeLogSpec<ResVmInst> changeLogSpec = new ServiceInstanceChangeLogSpec<ResVmInst>();
////		Map<String, Object> variables = new HashMap<String, Object>();
////		variables.put("resType", resType);
////		variables.put("ve", ve);
////		variables.put("diskInstanceSids", StringUtils.join(diskInstanceSids, ","));
////		variables.put("softwareInstanceSids", StringUtils.join(softwareInstanceSids, ","));
////		if(WebConstants.ResType.HOST.equals(resType)) {
////			//如果是开通物理机则无需设定开通参数
////			changeLogSpec.setParams(null);
////			if(resVmInst.getAllocateResHostIds().size() > 0) {
////				variables.put("hostId", resVmInst.getAllocateResHostIds().get(0));
////			}
////		} else {
////			changeLogSpec.setParams(resVmInst);
////		}
////		changeLogSpec.setVariables(variables);
////		return changeLogSpec;
//	}
//
//	@Override
//	public List<ServiceInstance> selectServiceCountByParams(Criteria example) {
//		return this.serviceInstanceMapper.selectServiceCountByParams(example);
//	}
//
//	@Override
//	public List<ServiceInstance> selectServiceInstanceByMgtObjSid(Long mgtObjSid) {
//		return this.serviceInstanceMapper.selectServiceInstanceByMgtObjSid(mgtObjSid);
//	}
//
//	@Override
//	public ServiceInstance selectObjStorageByMgtobjSid(Criteria example) {
//		return this.serviceInstanceMapper.selectObjStorageByMgtobjSid(example);
//	}
//
//	@Override
//	public List<ServiceInstance> selectFloatingIpInfo(Criteria example) {
//		return this.serviceInstanceMapper.selectFloatingIpInfo(example);
//	}
//
//	@Override
//	public boolean insertResHostMgtObjRel(ResHost resHost, List<ResStorage> sysSt, List<ResStorage> dataSt, String mgtObjSid, String account) {
//		ServiceInstanceSpecService spec = SpringContextHolder
//				.getBean("serviceInstanceSpecServiceImpl");
//		ServiceSpecService serviceSpec = SpringContextHolder
//				.getBean("serviceSpecServiceImpl");
//
////		User user = AuthUtil.getAuthUser();
//		User user = null;
//
////		MgtObjRes mgtObjRes = new MgtObjRes();
////		mgtObjRes.setBizSid(Long.parseLong(mgtObjSid));
////		mgtObjRes.setResSid(resHost.getResHostSid());
////		mgtObjRes.setResCategory("0");
////		mgtObjRes.setResSetType("RES-HOST");
////
////		mgtObjResService.insertSelective(mgtObjRes);
//
//		//生成主机的服务实例
//		// 初始化服务实例
//		ServiceInstance serviceInstance = new ServiceInstance();
//		serviceInstance.setServiceSid(WebConstants.ServiceSid.SERVICE_PM);
//		serviceInstance.setBillingTypeId("");
//		// 到期时间 未来24小时
//		// 期望开通时间(未来24小时)
//		Calendar c = Calendar.getInstance();
//		c.setTime(new Date());
//		c.add(Calendar.DAY_OF_MONTH, 1);
//		serviceInstance.setExpiringDate(c.getTime());
//		serviceInstance.setOwnerId(user.getAccount());
//		// 目标为空
//		serviceInstance.setTarget("");
//		// 服务开通时间为空
//		serviceInstance.setDredgeDate(new Date());
//		// 服务到期时间
//		serviceInstance.setExpiringDate(null);
//		// 实例创建开始结束时间
//		serviceInstance.setCreationDateBegin(new Date());
//		serviceInstance.setCreationDateEnd(new Date());
//
//		// 服务实例名称
////		serviceInstance.setInstanceName(resHost.getHostName());
//		serviceInstance.setMgtObjSid(Long.parseLong(mgtObjSid));
//
//		serviceInstance.setOwnerId(account);
//
//		serviceInstance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
//		WebUtil.prepareInsertParams(serviceInstance);
//		// insert服务实例数据到服务实例表
//		serviceInstanceMapper.insertSelective(serviceInstance);
//
//		//服务实例关联主机
//		ServiceInstRes instRes = new ServiceInstRes();
//		instRes.setInstanceSid(serviceInstance.getInstanceSid());
////		instRes.setResId(resHost.getResHostSid());
//		instRes.setResType("RES-HOST");
//		serviceInstResService.insertSelective(instRes);
//
//		OrderDetail orderDetail = new OrderDetail();
//		orderDetail.setMgtObjSid(mgtObjSid);
//		orderDetail.setVmServiceInstanceSid(serviceInstance.getInstanceSid());
//		orderDetail.setServiceSid(WebConstants.ServiceSid.SERVICE_VM);
//
//		// insert服务规格数据到服务规格表
//		Criteria criteria = new Criteria();
//		criteria.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
//		List<ServiceSpec> serviceSpecList = serviceSpec.selectByParams(criteria);
//		// 生成vm服务实例规格
//		for (ServiceSpec sSpec : serviceSpecList) {
//			ServiceInstanceSpec instanceSpec = new ServiceInstanceSpec();
//			instanceSpec.setInstanceSid(serviceInstance.getInstanceSid());
//			instanceSpec.setName(sSpec.getName());
//			instanceSpec.setDescription(sSpec.getDescription());
//			instanceSpec.setSequence(sSpec.getSequence());
//			instanceSpec.setTag(sSpec.getTag());
//			instanceSpec.setUnit(sSpec.getUnit());
//
//			// 设置服务实例规格中的值
////			if (WebConstants.InstanceSpecType.CPU.equals(instanceSpec.getName())) {
////				instanceSpec.setValue(StringUtil.nullToEmpty(resHost.getCpuCores()));
////			} else if (WebConstants.InstanceSpecType.MEMORY.equals(instanceSpec.getName())) {
////				instanceSpec.setValue(StringUtil.nullToEmpty(BigDecimal.valueOf(resHost.getMemorySize()).
////						divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_UP)));
////			}else if (WebConstants.InstanceSpecType.OS.equals(instanceSpec.getName())) {
////				instanceSpec.setValue(StringUtil.nullToEmpty(resHost.getHostOsType()));
////			}else if (WebConstants.InstanceSpecType.SYSTEM_DISK.equals(instanceSpec.getName())) {
////				if(!CollectionUtils.isEmpty(sysSt)){
////					instanceSpec.setValue(StringUtil.nullToEmpty(sysSt.get(0).getTotalCapacity()));
////					String sysDisk = StringUtil.nullToEmpty(sysSt.get(0).getTotalCapacity());
////					if(!StringUtil.isNullOrEmpty(sysDisk)){
////						HashMap<String, Object> sysDiskMap = new HashMap<String, Object>();
////						sysDiskMap.put("diskName", serviceInstance.getInstanceName()+"-sysDisk");
////						sysDiskMap.put("storagePurpose", WebConstants.StoragePurpose.SYSTEM_DISK);
////						sysDiskMap.put("diskSize", sysDisk);
////						sysDiskMap.put("instStatus", WebConstants.ServiceInstanceStatus.OPENED);
////						Long diskInstanceSid = createHostAndStorageServiceInstance(orderDetail, sysDiskMap);
////						if(diskInstanceSid!=null){
////							ServiceInstRes diskInstRes = new ServiceInstRes();
////							diskInstRes.setInstanceSid(diskInstanceSid);
////							diskInstRes.setResId(sysSt.get(0).getResStorageSid());
////							diskInstRes.setResType("RES-STORAGE");
////							serviceInstResService.insertSelective(diskInstRes);
////						}
////					}
////				}
////			}else if (WebConstants.InstanceSpecType.RECOVERY_TYPE.equals(instanceSpec.getName())) {
////				instanceSpec.setValue("1");
////			}else if (WebConstants.InstanceSpecType.NEED_LAN.equals(instanceSpec.getName())) {
////				instanceSpec.setValue("1");
////			}
//			instanceSpec.setGroupName(sSpec.getGroupName());
//			WebUtil.prepareInsertParams(instanceSpec);
//			spec.insertSelective(instanceSpec);
//		}
//
//		if(!CollectionUtils.isEmpty(dataSt)){
////			for (ResStorage st : dataSt) {
////				HashMap<String, Object> hostDiskMap = new HashMap<String, Object>();
////				hostDiskMap.put("diskName", st.getStorageName());
////				hostDiskMap.put("storagePurpose", st.getStoragePurpose());
////				hostDiskMap.put("diskSize", st.getTotalCapacity());
////				hostDiskMap.put("instStatus", WebConstants.ServiceInstanceStatus.OPENED);
////				Long diskInstanceSid = createHostAndStorageServiceInstance(orderDetail, hostDiskMap);
////
////				if(diskInstanceSid!=null){
////					ServiceInstRes diskInstRes = new ServiceInstRes();
////					diskInstRes.setInstanceSid(diskInstanceSid);
////					diskInstRes.setResId(st.getResStorageSid());
////					diskInstRes.setResType("RES-STORAGE");
////					serviceInstResService.insertSelective(diskInstRes);
////				}
////			}
//
//		}
//
//		return true;
//	}
//
//	@Override
//	public List<ServiceInstance> selectInstVmByParams(Criteria example) {
//		return this.serviceInstanceMapper.selectInstVmByParams(example);
//	}
//
//	@Override
//	public List<ServiceInstance> selectInstHostByParams(Criteria example) {
//		return this.serviceInstanceMapper.selectInstHostByParams(example);
//	}
//
//	@Override
//	public List<ServiceInstance> countInstVmByParams(Criteria example) {
//		return this.serviceInstanceMapper.countInstVmByParams(example);
//	}
//
//	@Override
//	public List<ServiceInstance> countInstHostByParams(Criteria example) {
//		return this.serviceInstanceMapper.countInstHostByParams(example);
//	}
//
//	@Override
//	public Integer countMgtObjInstVdByParams(Criteria example) {
//		return this.serviceInstanceMapper.countMgtObjInstVdByParams(example);
//	}
//
//	public List<String> getAllocHostIds(String computeResSetSid, String computeResType,
//										Long mgtObjSid, String resPoolType, String partitionType) {
//		List<String> resHostIds = new ArrayList<String>();
//		if(WebConstants.ResourceType.RES_HOST.equals(computeResType)) {
//			resHostIds.add(computeResSetSid);
//		} else {
//			Criteria criteria = new Criteria();
//			criteria.put("mgtObjSid", mgtObjSid);
////			if(WebUtil.isNotBlank(resPoolType)) {
////				criteria.put("resPoolType", resPoolType);
////			}
////			if(WebUtil.isNotBlank(partitionType)) {
////				criteria.put("partitionType", partitionType);
////			}
//			List<ResTopology> resTopologyes = mgtObjService.selectMgtObjComByParams(criteria);
//			this.getChildHostsFromTopology(computeResSetSid, resTopologyes, resHostIds);
//		}
//		return resHostIds;
//	}
//
//	public void getChildHostsFromTopology(String resTopologySid, List<ResTopology> allTopologyes, List<String> childHosts) {
//		List<ResTopology> childTopologys = getChildTopologyNode(resTopologySid, allTopologyes);
//		if(childTopologys.size() == 0) {
//			return;
//		}
//		for(ResTopology childTopology : childTopologys) {
//			if(WebConstants.ResourceType.RES_HOST.equals(childTopology.getResTopologyType())) {
//				childHosts.add(childTopology.getResTopologySid());
//			}
//			getChildHostsFromTopology(childTopology.getResTopologySid(), allTopologyes, childHosts);
//		}
//	}
//
//	private List<ResTopology> getChildTopologyNode(String resTopologySid, List<ResTopology> allTopologyes) {
//		List<ResTopology> childTopology = new ArrayList<ResTopology>();
//		for(ResTopology resTopology : allTopologyes) {
//			if((StringUtils.isBlank(resTopology.getParentTopologySid()) && StringUtils.isBlank(resTopologySid)) ||
//					(StringUtils.isNotBlank(resTopology.getParentTopologySid()) && resTopology.getParentTopologySid().equals(resTopologySid))) {
//				childTopology.add(resTopology);
//			}
//		}
//		return childTopology;
//	}
//
    public Long getVmHostNameSeq() {
        return this.serviceInstanceMapper.selectVmHostNameSeq();
    }
//
////	public Integer getMaxVolumeIndex(List<ResVd> resVdList) {
////		Integer maxVolumeIndex = 0;
////		for(ResVd resVd : resVdList) {
////			Integer curVolumeIndex = getVolumeIndex(resVd.getLogicVolume());
////			if(curVolumeIndex > maxVolumeIndex) {
////				maxVolumeIndex = curVolumeIndex;
////			}
////		}
////		return maxVolumeIndex;
////	}
//
//	private Integer getVolumeIndex(String logicVolumeName) {
////		if(logicVolumeName == null) {
////			return 0;
////		}
////		Pattern pattern = Pattern.compile("[a-zA-Z]+(\\d+)");
////		Matcher matcher = pattern.matcher(logicVolumeName);
////		Integer result = null;
////		if(matcher.find()) {
////			result = Integer.parseInt(matcher.group(1));
////		}
////		return result;
//		return  1;
//	}
//
//	@Override
//	public int updateInstanceBelongMgtObj(ServiceInstance instance, String mgtObjSid) {
//		ServiceInstanceChangeLog changeLog = new ServiceInstanceChangeLog();
//		changeLog.setInstanceSid(instance.getInstanceSid());
////		changeLog.setChangeSponsor(AuthUtil.getAuthUser().getAccount());
//		changeLog.setChangeType(WebConstants.instanceChangeType.CHANGE_MGT_OBJ);
//		changeLog.setChangeBeginTime(new Date());
//		Map map= new HashMap();
//		map.put("newMgtObjSid", mgtObjSid);
//		map.put("oldMgtObjSid", instance.getMgtObjSid()+"");
//		map.put("instanceSid", instance.getInstanceSid()+"");
//		changeLog.setChangeSpec(JsonUtil.toJson(map));
//		changeLog.setStatus(WebConstants.instanceChangeStatus.UNCHANGE);
//		WebUtil.prepareInsertParams(changeLog);
//		int result = this.instanceChangeLogService.insertSelective(changeLog);
////		approveRecordService.initChangeApprove(instance.getInstanceSid().toString(), changeLog.getChangeLogSid(), WebConstants.ProcessType.MGT_OBJ_CHANGE);
//		return result;
//	}


    @Override
    @Transactional
    public boolean releaseServiceInstance(Long serviceInstanceSid,String serviceType,AuthUser authUser) {
        ServiceInstance instance = this.serviceInstanceMapper.selectByPrimaryKey(serviceInstanceSid);

        if(StringUtil.isNullOrEmpty(instance))
            throw new RpcException(RpcException.BIZ_EXCEPTION,WebUtil.getMessage(
                    BusinessMessageConstants.ServiceInstance.CAN_NOT_FIND_SERVICE_INSTANCE,new String[]{serviceInstanceSid.toString()}));
        Criteria criteria = new Criteria();
        criteria.put("instanceSid",serviceInstanceSid);
        criteria.put("resType",serviceType);
        List<ServiceInstRes> serviceInstRes = this.serviceInstResMapper.selectByParams(criteria);
        if(StringUtil.isNullOrEmpty(serviceInstRes) || serviceInstRes.size() == 0)
            throw new RpcException(RpcException.BIZ_EXCEPTION,WebUtil.getMessage(
                    BusinessMessageConstants.ServiceInstance.CAN_NOT_FIND_SERVICE_INSTANCE_RES,new String[]{serviceInstanceSid.toString()}));
        ServiceInstanceHistory serviceInstanceHistory = new ServiceInstanceHistory();
        try {
            BeanUtils.copyProperties(serviceInstanceHistory,instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RpcException(RpcException.BIZ_EXCEPTION,e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RpcException(RpcException.BIZ_EXCEPTION,e.getMessage());
        }
        WebUtil.prepareInsertParams(serviceInstanceHistory,authUser);
        serviceInstanceHistory.setInstanceDelTime(new Date());
        // TODO 退订流程

        ResCommonInst resVmInst = new ResCommonInst();
        resVmInst.setMgtObjSid(authUser.getMgtObjSid());
        resVmInst.setUserAccount(authUser.getAccount());
        resVmInst.setUserPass(authUser.getPassword());
        resVmInst.setZoneId(instance.getZoneSid());
        resVmInst.setInstId(instance.getInstanceId());
        Map<String,Object> paramJsonMap = new HashMap<>();
        //设置主机
        paramJsonMap.put("resVmSid",serviceInstRes.get(0).getResId());
        paramJsonMap.put("isDueToCreateVm",true);
        //设置浮动IP
        paramJsonMap.put("resSid",serviceInstRes.get(0).getResId());
        paramJsonMap.put("zone",instance.getZoneSid());
        resVmInst.setResSpecParam(JsonUtil.toJson(paramJsonMap));
        //调用资源层进行释放操作
        ResInstResult result = releaseResourceInvoke(resVmInst,serviceType);
        if(result.getStatus()){
            //移动服务实例到历史表
            this.serviceInstanceHistoryMapper.insertSelective(serviceInstanceHistory);
            this.serviceInstanceMapper.deleteByPrimaryKey(instance.getInstanceSid());
        }else{
            logger.error("ServiceInstanceServiceImpl---releaseServiceInstance error message:"+result.getMessage());
            return false;
        }
       //插入log日志信息
        ActionTraceObservable logObservable = ActionTraceObservable.getActionTraceObservable();
        LogRecord logInfo=null;
        if(serviceType.equals(WebConstants.ResourceType.RES_VM)) {
            logInfo = ActionTraceObservable.getLogRecord(authUser,
            WebConstants.ACT_TARGET.VM_MANAGEMENT,
            WebUtil.getMessage(
                   BusinessMessageConstants.VmMessage.VM_DELETE),
            WebUtil.getMessage(
                   BusinessMessageConstants.VmMessage.VM_DELETE)
           , new Date(), new Date());
        }else if(serviceType.equals(WebConstants.ResourceType.RES_FLOATING_IP)){
            logInfo = ActionTraceObservable.getLogRecord(authUser,
             WebConstants.ACT_TARGET.EIP_MANAGEMENT,
             WebUtil.getMessage(
                     BusinessMessageConstants.EipMessage.EIP_DELETE),
             WebUtil.getMessage(
                     BusinessMessageConstants.EipMessage.EIP_DELETE)
                    , new Date(), new Date());
        }
        logObservable.addLogQueue(logInfo,logObservable);

        return true;
    }

    /**
     * 根据资源类型调用相应的资源服务进行释放
     * TODO 后续修改为自动泛型反射调用
     * @param resVmInst 资源公共参数类
     * @param resourceType 资源类型
     * @return
     */
    private ResInstResult releaseResourceInvoke(ResCommonInst resVmInst,String resourceType){
        //TODO 其他资源的释放
        ResInstResult resInstResult;
        switch (resourceType){
            case WebConstants.ResourceType.RES_VM:
                resInstResult = resVmService.deleteVm(resVmInst);
                break;
            case WebConstants.ResourceType.RES_FLOATING_IP:
                resInstResult = resFloatingIpService.deleteFloatingIP(resVmInst);
                break;
            default:
                resInstResult = new ResInstResult();
                resInstResult.setStatus(ResInstResult.FAILURE);
                resInstResult.setMessage("can't invoke resource service by resourceType: "+resourceType+" !");
                break;
        }
        return resInstResult;
    }

    @Override
    public void resInvokeErrorCallback(String resId,String instanceID,String resType,String errorMsg) {
        Assert.notNull(resId);
        Assert.notNull(instanceID);
        Assert.notNull(resType);

        if(false){
//        if(!isMaxRetryTimes(resId)){
            try {
                //休眠3s
                Thread.sleep(3000);
                //TODO 后续修改为泛用通用调用
                switch (resType){
                    case WebConstants.ResourceType.RES_VM:
                        retryInvokeCreateVm(resId,instanceID);
                        break;
                    default:
                        throw new RpcException(RpcException.BIZ_EXCEPTION,"can't find resType:"+resType+" !");
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
                sendWorkOrder(resId,instanceID,resType,e.getMessage());
            }catch (ServiceException se){
                se.printStackTrace();
                sendWorkOrder(resId,instanceID,resType,se.getMessage());
            }
        }else{
            logger.info("ServiceInstanceServiceImpl------------resInvokeErrorCallback  resId="+resId+" instanceID="+instanceID+" had retry max times! send workorder request!");
            sendWorkOrder(resId,instanceID,resType,errorMsg);
        }

    }

    @Override
    @Transactional
    public void openServiceSuccessHandle(Map<String,String> resMap,ServiceInstance serviceInstance,User user,boolean isRetry) {
        logger.info("ServiceInstanceServiceImpl------------openServiceSuccessHandle---instanceSid="+serviceInstance.getInstanceSid()+" resMap="+resMap.toString()+" start");

        if(!isRetry)
            serviceInstanceMapper.insertSelective(serviceInstance);

        resMap.forEach((key,value) ->{
            ServiceInstRes serviceInstRes = new ServiceInstRes();
            serviceInstRes.setInstanceSid(serviceInstance.getInstanceSid());
            serviceInstRes.setResId(key);
            serviceInstRes.setResType(value);
            serviceInstResMapper.insert(serviceInstRes);
            logger.info("ServiceInstanceServiceImpl------------openServiceSuccessHandle---instanceSid="+serviceInstance.getInstanceSid()+" ResID="+serviceInstRes.getResId()+" ResType="+value+" instanceSid="+serviceInstRes.getInstanceSid());
        });

        Calendar now = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        serviceInstance.setDredgeDate(now.getTime());
        //包年包月会有结束时间，按量计费则没有结束时间
        if(serviceInstance.getBillingType().equals(WebConstants.BILLING_TYPE.YEAR))
            endTime.add(Calendar.YEAR,serviceInstance.getBuyLength().intValue());
        else if(serviceInstance.getBillingType().equals(WebConstants.BILLING_TYPE.MONTH))
            endTime.add(Calendar.MONTH,serviceInstance.getBuyLength().intValue());
        else
            endTime = null;
        serviceInstance.setBillingEndTime(StringUtil.isNullOrEmpty(endTime)?null:endTime.getTime());
        serviceInstance.setExpiringDate(StringUtil.isNullOrEmpty(endTime)?null:endTime.getTime());
        //更新开通与计费时间
        serviceInstanceMapper.updateByPrimaryKey(serviceInstance);

        OrderDetail orderDetail = orderDetailMapper.selectByPrimaryKey(serviceInstance.getDetailSid());

        BigDecimal amount = orderDetail.getAmount().divide(new BigDecimal(orderDetail.getQuantity())).setScale(2,BigDecimal.ROUND_HALF_UP);

        BillingAccount billingAccount = billingAccountMapper.selectByUserId(user.getUserSid());
        // 资源开通成功插入账单信息，每个服务实例都对应一条账单信息
        ServiceBill serviceBill = new ServiceBill();
        serviceBill.setBillId(StringUtil.dateFormat(new Date(),"yyyyMMddHHmmss")+String.valueOf(Math.random()*1000000000).substring(0,7));
        serviceBill.setServiceSid(serviceInstance.getServiceSid());
        serviceBill.setServiceInstanceSid(serviceInstance.getInstanceSid());
        serviceBill.setServiceName(orderDetail.getServiceName());
        serviceBill.setBillTime(StringUtil.dateFormat(new Date(),StringUtil.DF_YM));

        serviceBill.setAmount(amount);
        serviceBill.setRealAmount(amount);
        serviceBill.setDuration(serviceInstance.getBuyLength().intValue());
        serviceBill.setStatus(Integer.parseInt(WebConstants.RECHARGE_STATUS.PAYED));
        serviceBill.setUserSid(user.getUserSid());
        serviceBill.setAccountSid(billingAccount.getAccountSid());
        serviceBill.setStartTime(now.getTime());
        serviceBill.setEndTime(StringUtil.isNullOrEmpty(endTime)?null:endTime.getTime());
        serviceBill.setBillingType(serviceInstance.getBillingType());
        WebUtil.prepareInsertParams(serviceBill,user.getAccount());
        serviceBillMapper.insertSelective(serviceBill);
        logger.info("ServiceInstanceServiceImpl------------openServiceSuccessHandle---instanceSid="+serviceInstance.getInstanceSid()+" resMap="+resMap.toString()+" end");
    }

    /**
     * 重试调用资源进行开通
     * @param resId 资源id
     * @param resId 服务实例ID
     */
    private void retryInvokeCreateVm(String resId,String instanceID) throws ServiceException{
        logger.error("ServiceInstanceServiceImpl------------retryInvokeCreateVm resId="+resId+" into retry create invoke");

        ServiceInstance serviceInstance = this.serviceInstanceMapper.selectByInstanceId(instanceID);
        if(StringUtil.isNullOrEmpty(serviceInstance))
            throw new RpcException(RpcException.BIZ_EXCEPTION,"can't service instance info by instanceID:"+instanceID);
        Map<String,Object> specJson = JsonUtil.fromJson(serviceInstance.getSpecification(),HashMap.class);
        specJson.put("resVmSid",resId);
        //组建原始调用公共参数
        Criteria criteria = new Criteria();
        criteria.put("account",serviceInstance.getOwnerId());
        List<User> users = userMapper.selectByParams(criteria);
        if(StringUtil.isNullOrEmpty(users) || users.size() == 0)
            throw new RpcException(RpcException.BIZ_EXCEPTION,"can't find user info by account:"+serviceInstance.getOwnerId());
        User user = users.get(0);
        ResCommonInst resCommonInst = new ResCommonInst();
        resCommonInst.setMgtObjSid(user.getMgtObjSid());
        resCommonInst.setUserAccount(user.getAccount());
        resCommonInst.setUserPass(user.getPassword());
        resCommonInst.setZoneId(serviceInstance.getZoneSid());
        resCommonInst.setInstId(serviceInstance.getInstanceId());
        resCommonInst.setResSpecParam(JsonUtil.toJson(specJson));
        ResInstResult resInstResult = resVmService.createVm(resCommonInst);

        if (ResInstResult.SUCCESS == resInstResult.getStatus()) {
            Map<String,String> resMap = (Map<String,String>)resInstResult.getData();
            this.openServiceSuccessHandle(resMap,serviceInstance,user,true);
        }else{
            logger.error("ServiceInstanceServiceImpl------------retryInvokeCreateVm  resId="+resId+" RESException instanceName="+serviceInstance.getInstanceId()+" message="+ StringUtil.nullToEmpty(resInstResult.getMessage()));
            throw new ServiceException(StringUtil.nullToEmpty(resInstResult.getMessage()));
        }
        //重试次数计数
        JedisUtil.instance().incr(MAX_RETRY_TIMES_PRE+resId);
    }

    /**
     * 是否已经超过最大尝试次数
     * @param resId
     * @return
     */
    private boolean isMaxRetryTimes(String resId){
        if(!JedisUtil.instance().isConnected())
            return true;
        String retryTimes = JedisUtil.instance().get(MAX_RETRY_TIMES_PRE+resId);
        logger.info("ServiceInstanceServiceImpl------------isMaxRetryTimes---resId="+resId+" retryTimes="+retryTimes);
        if(!StringUtil.isNullOrEmpty(retryTimes) && Long.parseLong(retryTimes) >= MAX_RETRY_TIMES)
            return true;
        return false;
    }

    /**
     * 超过最大尝试次数之后发送开通失败工单
     * @param resId 资源ID
     * @param resType 资源类型，需要转换成相应资源工单类型
     * @param errorMsg 错误消息
     * @return
     */
    private void sendWorkOrder(String resId,String instanceID,String resType,String errorMsg){
        // TODO  WebConstants.ticketType.VM_AUTO_OPEN_FAILURE_TICKET
        String ticketType = WebConstants.ticketType.VM_AUTO_OPEN_FAILURE_TICKET;
        switch (resType){
            case WebConstants.ResourceType.RES_VM:
                ticketType = WebConstants.ticketType.VM_AUTO_OPEN_FAILURE_TICKET;
                break;
            default:
                logger.error("ServiceInstanceServiceImpl------------sendWorkOrder---resId="+resId+" instanceID="+instanceID+" can't find resolve restype:"+resType);
        }
        Map<String,Object> params = new HashMap<>();
        params.put("resId",resId);
        params.put("instanceID",instanceID);
        params.put("errorMsg",errorMsg);
        this.ticketService.createTicket(ticketType,params);
    }
}