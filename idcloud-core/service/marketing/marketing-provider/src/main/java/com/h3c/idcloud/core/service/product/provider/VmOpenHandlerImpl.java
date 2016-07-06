package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.pojo.dto.order.Order;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceChangeLog;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceChangeLogSpec;
import com.h3c.idcloud.core.pojo.dto.res.ResOsSoftware;
import com.h3c.idcloud.core.pojo.dto.res.ResVd;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.system.SysTLogRecord;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.pojo.instance.ResVmInst;
import com.h3c.idcloud.core.service.order.api.OrderService;
import com.h3c.idcloud.core.service.product.api.MonitorService;
import com.h3c.idcloud.core.service.product.api.ResourceRequestHanlder;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceChangeLogService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceSpecService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.core.service.ticket.api.TicketService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.h3c.idcloud.core.service.customer.api.TicketMgt;

/**
 * 虚拟机开通申请回调处理
 *
 * @author ChengQi
 */
//@Service("vmOpenHandlerImpl")
@Service(version = "1.0.0", group = "vmOpenHandlerImpl")
@Component("vmOpenHandlerImpl")
public class VmOpenHandlerImpl implements ResourceRequestHanlder {

    private static final Logger logger = LoggerFactory
            .getLogger(VmOpenHandlerImpl.class);

    /**
     * 资源申请接口Service
     */
    @Reference(version = "1.0.0")
    private ResVmService resVmService;

//	@Autowired
//	private MockResVmService mockResVmService;

    @Autowired
    private ServiceInstanceSpecService instanceSpecService;

    @Autowired
    private ServiceInstanceService instanceService;

    @Reference(version = "1.0.0")
    private OrderService orderService;

    @Autowired
    private ServiceInstResService serviceInstResService;

//	@Autowired
//	private SysLoggerFactory sysLogger;

    @Autowired
    private ServiceInstanceChangeLogService changeLogService;

//	@Reference(version = "1.0.0")
//	private TicketMgt ticketMgt;

    @Reference(version = "1.0.0")
    private TicketService ticketService;

    @Autowired
    private MonitorService monitorService;

    @Override
    public boolean invoke(Long processObjectId) {
        boolean result = true;
        String orderId = null;
        Order order = null;
        ServiceInstance instance = null;

        //系统日志
        SysTLogRecord record = new SysTLogRecord();
//		record.setAccount(AuthUtil.getAuthUser().getAccount());    ----------wsl
        record.setActLevel("01");
        record.setActTarget("服务订购");
        record.setActName("服务开通");
        record.setActStartDate(new Date());
        record.setActEndDate(new Date());

        try {
            Criteria criteria = new Criteria();
            instance = instanceService.selectByPrimaryKey(processObjectId);
            criteria.put("orderId", instance.getOrderId());
            List<Order> orderList = orderService.selectByParams(criteria);
            order = orderList.get(0);
            orderId = order.getOrderId();
            result = createVm(instance, order);
            record.setActDetail("订单编号:" + order.getOrderId() + "服务实例:" + processObjectId + " 服务订购:服务开通");
            record.setActResult("02");
        } catch (Exception e) {
            result = false;
            logger.error(e.getMessage(), e);
            //生成云主机开通失败工单
            operateOpenVmError(orderId, instance.getMgtObjSid(), instance.getInstanceSid(), "未知错误", order, instance, false);
            record.setActResult("01");
        } finally {
//			SysLogger log = sysLogger.getLogger(LoggerTypeEnum.DB); ----------wsl
//			log.debug(record);
        }
        return result;
    }

    private boolean createVm(ServiceInstance serviceInstance, Order order) throws Exception {
        Long mgtObjSid = null;
        Boolean result = false;
        //查询磁盘信息
//		Criteria criteria = new Criteria();
//		criteria.put("serviceSid", WebConstants.ServiceSid.SERVICE_STORAGE);
//		criteria.put("parentInstanceSid", serviceInstance.getInstanceSid());
//		List<ServiceInstance> diskServiceInstances = instanceService.selectByParams(criteria);

        //获取实例变更日志中的创建参数
        mgtObjSid = serviceInstance.getMgtObjSid();

        List<ServiceInstanceChangeLog> changeLogs = changeLogService.selectLastChangeLog(serviceInstance.getInstanceSid());
        if (!CollectionUtils.isEmpty(changeLogs)) {
            ServiceInstanceChangeLogSpec<ResVmInst> openSpec = changeLogService.getChangeInfo(serviceInstance.getInstanceSid(), new TypeReference<ServiceInstanceChangeLogSpec<ResVmInst>>() {
            });
            Map<String, Object> variables = openSpec.getVariables();
//			String resType  = (String)variables.get("resType");
//			String ve  = (String)variables.get("ve");
            ResVmInst resVmInst = openSpec.getParams();

//			if(WebConstants.VirtualEnv.X86.equals(ve) && WebConstants.ResType.HOST.equals(resType)) {
//				Map<String, Object> params = new HashMap<String, Object>();
//				params.put("mgtObjSid", mgtObjSid);
//				params.put("instanceSid", serviceInstance.getInstanceSid());
//				ticketMgt.createTicket(WebConstants.ticketType.HOST_OPEN_TICKET, params);
//				return true;
//			}

/*
        if(WebConstants.VirtualEnv.POWER.equals(ve)) {
			//生成AIX系统数据盘手动开通工单
			List<ResVdInst> allDisks = resVmInst.getDataDisks();
			List<ResVdInst> dataDisks = new ArrayList<ResVdInst>();
			for(ResVdInst disk : allDisks) {
				if(WebConstants.StoragePurpose.DATA_DISK.equals(disk.getStoragePurpose())) {
					dataDisks.add(disk);
				}
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("mgtObjSid", mgtObjSid);
			params.put("instanceSid", serviceInstance.getInstanceSid());
			params.put("dataDisks", dataDisks);
			ticketMgt.createTicket(WebConstants.ticketType.AIX_DISK_OPEN_TICKET, params);
			//清空数据盘参数
			//allDisks.removeAll(dataDisks);
		}
*/

            logger.info("createVm input params:" + JsonUtil.toJson(resVmInst));
//            ResInstResult resInstResult = resVmService.createVm(resVmInst);
            ResInstResult resInstResult = null;
//			ResInstResult resInstResult = mockResVmService.createVm(resVmInst);
            logger.info("createVm return result:" + JsonUtil.toJson(resInstResult));
            saveInstanceRes(order, serviceInstance,
                    resInstResult, mgtObjSid,
                    serviceInstance.getInstanceSid(), variables);
            result = resInstResult.getStatus();
        } else {
            //审核的时候选了纳管，直接调用operate
            //数据处理
            List<ServiceInstRes> serviceInstRes = serviceInstResService.selectInstanceReses(serviceInstance.getInstanceSid());
            handlerData(serviceInstance, (CollectionUtils.isEmpty(serviceInstRes)) ? "" : serviceInstRes.get(0).getResId());
        }
        return result;
    }

    @Override
    public void operate(ResInstResult result) {
        try {
            logger.info("createVm callback result:" + JsonUtil.toJson(result));

            Long instanceSid = null;
            //根据资源id查询出对应实例id
            ResVm resVm = (ResVm) result.getData();
            String resVmSid = resVm.getResVmSid();

            Criteria criteria = new Criteria();
            criteria.put("resId", resVmSid);
            List<ServiceInstRes> serviceInstReses = serviceInstResService.selectByParams(criteria);
            instanceSid = null;
            if (serviceInstReses.size() > 0) {
                instanceSid = serviceInstReses.get(0).getInstanceSid();
            }
            if (instanceSid == null) {
                throw new RuntimeException("The resSid no mapping in service_inst_res table resSid=" + resVmSid);
            }
            //查询出该实例所属的订单号
            ServiceInstance instance = instanceService.selectByPrimaryKey(instanceSid);
            String orderId = instance.getOrderId();
            Long mgtObjSid = instance.getMgtObjSid();

            if (ResInstResult.SUCCESS == result.getStatus()) {

                //Power的情况生成Aix数据盘开通工单
                if (resVm.getParType() != null) {
                    //生成AIX系统数据盘手动开通工单
                    List<ResVd> allDisks = resVm.getResVdList();
                    List<ResVd> dataDisks = new ArrayList<ResVd>();
                    for (ResVd disk : allDisks) {
                        if (WebConstants.StoragePurpose.DATA_DISK.equals(disk.getStoragePurpose())) {
                            dataDisks.add(disk);
                        }
                        //如果在HMC的Lpart情况下，申请的磁盘和实际创建的磁盘容量上可能不一致，
                        //所以根据返回的磁盘容量，对磁盘服务实例进行更新
                        if (WebConstants.VirtualIoServerCapable.NO.equals(resVm.getParType().toString())) {
                            Long diskInstanceSid = this.serviceInstResService.getInstanceSidByResSid(disk.getResVdSid());
                            this.instanceSpecService.updateByName(diskInstanceSid, WebConstants.InstanceSpecType.DISK_SIZE, disk.getAllocateDiskSize().toString());
                        }
                    }
//					if(dataDisks.size() > 0) {
//						Map<String, Object> params = new HashMap<String, Object>();
//						params.put("mgtObjSid", mgtObjSid);
//						params.put("instanceSid", instanceSid);
//						params.put("dataDisks", dataDisks);
//						ticketMgt.createTicket(WebConstants.ticketType.AIX_DISK_OPEN_TICKET, params);
//					}
                }

                //安装软件处理
                List<ResOsSoftware> resOsSoftwares = resVm.getSoftwares();
                List<ResOsSoftware> autoInstallSoftwares = new ArrayList<ResOsSoftware>();
                List<ResOsSoftware> manualInstallSoftwares = new ArrayList<ResOsSoftware>();
                for (ResOsSoftware resOsSoftware : resOsSoftwares) {
                    if (WebConstants.OsSoftwareStatus.WAITING.equals(resOsSoftware.getStatus())) {
                        if (resOsSoftware.getCanAutoDeploy()) {
                            autoInstallSoftwares.add(resOsSoftware);
                        } else {
                            manualInstallSoftwares.add(resOsSoftware);
                        }
                    }
                }

                //需要手动安装的软件生成软件安装工单
                if (manualInstallSoftwares.size() > 0) {
                    //生成云主机软件安装工单
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("orderId", orderId);
                    params.put("mgtObjSid", mgtObjSid);
                    params.put("instanceSid", instanceSid);
                    params.put("processTarget", JsonUtil.toJson(manualInstallSoftwares));
                    //flag= 1-无自动部署脚本，需手动安装 flag= 2-部署异常，需要手动安装
                    params.put("flag", "1");
//					ticketMgt.createTicket(WebConstants.ticketType.SOFTWARE_INSTALL_TICKET, params); ---wsl
                }

                //需要自动安装的软件调用自动部署接口
                if (autoInstallSoftwares.size() > 0) {
                    try {
                        logger.info("installSoftware input params: resVmSid=" + resVmSid + " autoInstallSoftwares=" + JsonUtil.toJson(autoInstallSoftwares));
                        resVmService.installSoftware(resVmSid, autoInstallSoftwares);
//						mockResVmService.installSoftware(resVmSid, autoInstallSoftwares);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        //生成云主机软件安装工单
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("orderId", orderId);
                        params.put("mgtObjSid", mgtObjSid);
                        params.put("instanceSid", instanceSid);
                        params.put("processTarget", JsonUtil.toJson(autoInstallSoftwares));
                        //flag= 1-无自动部署脚本，需手动安装 flag= 2-部署异常，需要手动安装
                        params.put("flag", "2");
//						ticketMgt.createTicket(WebConstants.ticketType.SOFTWARE_INSTALL_TICKET, params);----wsl
                    }
                }

                //数据处理
                handlerData(instance, resVmSid);

                //设置监控
                try {
                    monitorService.addVmToMonitor(resVm);
                } catch (Exception e) {
                    logger.error("set monitor failure.", e);
                }
            } else {
                //开通失败，删除服务实例和磁盘与资源表关联信息
                if (instanceSid != null) {
                    List<ServiceInstRes> serviceInstResList = serviceInstResService.selectInstanceReses(instanceSid);
                    for (ServiceInstRes serviceInstRes : serviceInstResList) {
                        serviceInstResService.deleteByPrimaryKey(serviceInstRes);
                    }
                }
                criteria = new Criteria();
                Order order = null;
                criteria.put("orderId", orderId);
                List<Order> orders = orderService.selectByParams(criteria);
                if (orders.size() > 0) {
                    order = orders.get(0);
                }
                operateOpenVmError(orderId, mgtObjSid, instanceSid, (String) result.getMessage(), order, instance, result.isReSend());
            }
        } catch (Exception e) {
            logger.error("operate failure.", e);
        }

    }

    /**
     * 处理虚拟机开通成功数据
     *
     * @param instance
     * @param resVmSid
     */
    public void handlerData(ServiceInstance instance, String resVmSid) {

        String orderId = instance.getOrderId();
        Long instanceSid = instance.getInstanceSid();

        //更新云主机下的磁盘的服务实例状态为已开通
        this.instanceService.modifyDiskServiceInstancesOfVmStatus(instanceSid, WebConstants.ServiceInstanceStatus.OPENED);

        //检查云主机下的所有子服务实例是否开通完成,如果全部完成则更新云主机服务实例状态为已开通, 并发送邮件通知
        this.instanceService.modifyVmServiceInstanceStatus(instance);

        //检查当前云主机所属订单下的所有云主机服务实例是否全部开通完成，如果全部完成则更新订单的状态为已开通
        this.instanceService.modifyOrderStatusOfVmServiceInstance(orderId);

        //更新工单信息
        this.ticketService.modifyTicketStatus(instanceSid, WebConstants.ticketType.VM_AUTO_OPEN_FAILURE_TICKET);

    }

    @Transactional
    public void saveInstanceRes(Order order, ServiceInstance serviceInstance,
                                ResInstResult resInstResult, Long mgtObjSid, Long instanceSid, Map<String, Object> variables) {
        if (ResInstResult.SUCCESS == resInstResult.getStatus()) {
            //保存VM实例和资源关联关系
            ResVm resVm = (ResVm) resInstResult.getData();
            ServiceInstRes serviceInstRes = new ServiceInstRes();
            serviceInstRes.setInstanceSid(serviceInstance.getInstanceSid());
            serviceInstRes.setResId(resVm.getResVmSid());
            serviceInstRes.setResType(WebConstants.ResourceType.RES_VM);
            serviceInstResService.insert(serviceInstRes);

            //保存Disk实例和存储资源关系
            if (variables != null) {
                String diskInstanceSids = (String) variables.get("diskInstanceSids");
                String[] diskInstanceSidArray = diskInstanceSids.split(",");
                List<ResVd> resVdList = resVm.getResVdList();
                if (resVdList != null && resVdList.size() > 0) {
                    for (int i = 0; i < resVdList.size(); i++) {
                        Long diskInstanceSid = Long.parseLong(diskInstanceSidArray[i]);
                        ServiceInstRes diskServiceInstRes = new ServiceInstRes();
                        diskServiceInstRes.setInstanceSid(diskInstanceSid);
                        diskServiceInstRes.setResId(resVdList.get(i).getResVdSid());
                        diskServiceInstRes.setResType(WebConstants.ResourceType.RES_VD);
                        serviceInstResService.insert(diskServiceInstRes);
                        //更新磁盘服务状态为开通中
                        ServiceInstance diskServiceInstance = this.instanceService.selectByPrimaryKey(diskInstanceSid);
                        diskServiceInstance.setStatus(WebConstants.ServiceInstanceStatus.OPENING);
                        instanceService.updateByPrimaryKeySelective(diskServiceInstance);
                    }
                }

                //保存软件服务实例和软件资源关系
                String softwareInstanceSids = (String) variables.get("softwareInstanceSids");
                String[] softwareInstanceSidArray = softwareInstanceSids.split(",");
                List<ResOsSoftware> resOsSoftwareList = resVm.getSoftwares();
                if (resOsSoftwareList != null && resOsSoftwareList.size() > 0) {
                    for (int i = 0; i < resOsSoftwareList.size(); i++) {
                        Long softwareInstanceSid = Long.parseLong(softwareInstanceSidArray[i]);
                        ResOsSoftware resOsSoftware = resOsSoftwareList.get(i);
                        ServiceInstRes softwareServiceInstRes = new ServiceInstRes();
                        softwareServiceInstRes.setInstanceSid(softwareInstanceSid);
//					softwareServiceInstRes.setResId(resOsSoftware.getResSortwareSid());  ---------wsl
                        softwareServiceInstRes.setResType(WebConstants.ResourceType.RES_SOFTWARE);
                        serviceInstResService.insert(softwareServiceInstRes);
                        //更新软件服务状态为开通中
                        ServiceInstance diskServiceInstance = this.instanceService.selectByPrimaryKey(softwareInstanceSid);
                        if (WebConstants.OsSoftwareStatus.INSTALLED.equals(resOsSoftware.getStatus())) {
                            diskServiceInstance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
                        } else {
                            diskServiceInstance.setStatus(WebConstants.ServiceInstanceStatus.OPENING);
                        }
                        instanceService.updateByPrimaryKeySelective(diskServiceInstance);
                    }
                }
            /*
			for(Integer i = 0; i < diskServiceInstances.size(); i++) {
				ServiceInstance diskServiceInstance = diskServiceInstances.get(i);
				ResVd resVd = resVm.getResVdList().get(i);
				ServiceInstRes diskServiceInstRes = new ServiceInstRes();
				diskServiceInstRes.setInstanceSid(diskServiceInstance.getInstanceSid());
				diskServiceInstRes.setResId(resVd.getResVdSid());
				diskServiceInstRes.setResType(WebConstants.ResourceType.RES_VD);
				serviceInstResService.insert(diskServiceInstRes);
				//更新磁盘实例规格状态
				instanceSpecService.modifyInstanceSpecToChanging(diskServiceInstance.getInstanceSid());
			}
			*/

            }

            //更新实例状态
            serviceInstance.setStatus(WebConstants.ServiceInstanceStatus.OPENING);
            instanceService.updateByPrimaryKeySelective(serviceInstance);
            //更新虚拟机实例规格状态
            instanceSpecService.modifyInstanceSpecToChanging(serviceInstance.getInstanceSid());
            //更新订单状态
//            if (order != null) {
//                order.setStatus(WebConstants.ORDER_STATUS.OPENING);
//                orderService.updateByPrimaryKeySelective(order);
//            }
            //更新实例变更日志
//			this.changeLogService.beginChangeServiceInstance(instanceSid, WebConstants.instanceChangeType.CREATE); ----wsl

        } else {
            String orderId = null;
            if (order != null) {
                orderId = order.getOrderId();
            }
            operateOpenVmError(orderId, mgtObjSid, instanceSid, (String) resInstResult.getMessage(), order, serviceInstance, resInstResult.isReSend());
        }

    }

    public void operateOpenVmError(String orderId, Long mgtObjSid, Long instanceSid, String errorMessage, Order order, ServiceInstance serviceInstance, Boolean isResSend) {
        if (order == null || serviceInstance == null) {
            logger.error("Can't modify order or serviceInstance status, because they is null. order=" + order + " serviceInstance=" + serviceInstance);
            return;
        }

        //更新实例状态
        serviceInstance.setStatus(WebConstants.ServiceInstanceStatus.OPENING);
        instanceService.updateByPrimaryKeySelective(serviceInstance);
        //更新订单状态  订单支付完成后无需再更新状态
//		if(order!=null){
//			order.setStatus(WebConstants.OrderStatusCd.OPENING);
//			orderService.updateByPrimaryKeySelective(order);
//		}

        //生成云主机开通失败工单
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderId", orderId);
        params.put("mgtObjSid", mgtObjSid);
        params.put("instanceSid", instanceSid);
        params.put("errorMessage", errorMessage);
        params.put("isResSend", isResSend);
//		ticketMgt.createTicket(WebConstants.ticketType.VM_AUTO_OPEN_FAILURE_TICKET, params); --------wsl
    }


    /**
     * 调用申请资源接口
     *
     * @param params 动态参数
     * @return
     */
    public boolean invoke(Map<String, Object> params) {
        return false;
    }

    /**
     * 调用申请资源接口
     */
    public boolean invoke(List<Long> instanceSids) {
        return false;
    }

}
