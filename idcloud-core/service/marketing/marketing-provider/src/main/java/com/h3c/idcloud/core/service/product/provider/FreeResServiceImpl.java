package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.product.dao.FreeResMapper;
import com.h3c.idcloud.core.pojo.dto.product.FreeRes;
import com.h3c.idcloud.core.pojo.dto.product.FreeResDisk;
import com.h3c.idcloud.core.pojo.dto.product.FreeResTotal;
import com.h3c.idcloud.core.pojo.dto.system.Biz;
import com.h3c.idcloud.core.service.product.api.BusinesstypeService;
import com.h3c.idcloud.core.service.product.api.FreeResService;
import com.h3c.idcloud.core.service.product.api.ServiceConfigService;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.core.service.system.api.MailNotificationsService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Attachment;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(version = "1.0.0")
@Component
public class FreeResServiceImpl implements FreeResService {
	@Autowired
	private FreeResMapper freeresMapper;
	@Reference(version = "1.0.0")
	private MailNotificationsService mailNotificationsService;
	@Autowired
	private BusinesstypeService businesstypeService;
//	@Autowired         ----wsl
//	private FreeVMResEmailTask freeVMResEmailTask;
//	@Autowired
//	private RecoveryVMResEmailTask recoveryVMResEmailTask;

	/** 服务实例Service */
	@Autowired
	private ServiceInstanceService serviceInstanceService;

	/** 服务实例资源关系Service */
	@Autowired
	private ServiceInstResService serviceInstResService;

	/** 服务配置Service */
	@Autowired
	private ServiceConfigService serviceConfigService;

	@Reference(version = "1.0.0")
	private ResVmService resVmService;

	private static final Logger logger = LoggerFactory
			.getLogger(FreeResServiceImpl.class);

	public int countByParams(Criteria example) {
		int count = this.freeresMapper.countByParams(example);
		logger.debug("count: {}", count);
		return count;
	}

	public FreeRes selectByPrimaryKey(Long fresSid) {
		return this.freeresMapper.selectByPrimaryKey(fresSid);
	}

	public List<FreeRes> selectByParams(Criteria example) {
		return this.freeresMapper.selectByParams(example);
	}

	public int deleteByPrimaryKey(Long fresSid) {
		return this.freeresMapper.deleteByPrimaryKey(fresSid);
	}

	public int updateByPrimaryKeySelective(FreeRes record) {
		return this.freeresMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(FreeRes record) {
		return this.freeresMapper.updateByPrimaryKey(record);
	}

	public int deleteByParams(Criteria example) {
		return this.freeresMapper.deleteByParams(example);
	}

	public int updateByParamsSelective(FreeRes record, Criteria example) {
		return this.freeresMapper.updateByParamsSelective(record,
				example.getCondition());
	}

	public int updateByParams(FreeRes record, Criteria example) {
		return this.freeresMapper
				.updateByParams(record, example.getCondition());
	}

	public int insert(FreeRes record) {
		return this.freeresMapper.insert(record);
	}

	public int insertSelective(FreeRes record) {
		return this.freeresMapper.insertSelective(record);
	}

	public List<FreeRes> selectByFreeInstance(Criteria example) {
		return this.freeresMapper.selectByFreeInstance(example);
	}

	@Override
	public List<FreeRes> selectByParamsForFresReport(Criteria example) {
		//得到当前时间所处的月，只查询该月上一个月的
//		if(example.get("startDate")==null||example.get("startDate")==""){
//			if(example.get("finishDate")==null||example.get("finishDate")==""){
//				Date date = new Date();
//				Date startDate = new Date(date.getYear(), date.getMonth()-1, 1);
//				Date endDate = new Date(date.getYear(), date.getMonth(), 1);
//				example.put("openDate", startDate);
//				example.put("finishDate", endDate);
//			}
//		}
		return this.freeresMapper.selectByParamsForFresReport(example);
	}
	
	@Override
	public List<FreeResTotal> selectByParamsForRecoveryReport(Criteria example) {
		//得到当前时间所处的月，只查询该月上一个月的
//		if(example.get("startDate")==null||example.get("startDate")==""){
//			if(example.get("finishDate")==null||example.get("finishDate")==""){
//				Date date = new Date();
//				Date startDate = new Date(date.getYear(), date.getMonth()-1, 1);
//				Date endDate = new Date(date.getYear(), date.getMonth(), 1);
//				example.put("openDate", startDate);
//				example.put("finishDate", endDate);
//			}
//		}
		List<FreeResTotal> list = freeresMapper.selectByParamsForRecoveryReport(example);
		
		return list;
	}

	@Override
	public void createFresExcelForEMail(Criteria example) {
		Map<String,List<FreeRes>> map = new HashMap<String, List<FreeRes>>();
		List<Biz> bizList = businesstypeService.selectFbizByParams(example);
		if(bizList!=null&&bizList.size()!=0){
			Date date = new Date();
			Date startDate = new Date(date.getYear(), date.getMonth()-1, 1);
			Date endDate = new Date(date.getYear(), date.getMonth(), 1);
			example.put("openDate", startDate);
			example.put("finishDate", endDate);
			for (Biz biz : bizList) {
				example.put("fbizSid", biz.getBizSid());
				example.put("status", 1);
				List<FreeRes> list = this.freeresMapper.selectByParamsForFresReport(example);
				map.put(biz.getName()+"业务", list);
			}
		}
		String tempPath = PropertiesUtil.getProperty("upload.tmpfile.path");
		
		String date = StringUtil.dateFormat(new Date(), StringUtil.DF_YMD);
		String systemName = PropertiesUtil.getProperty("system.name");
		String fileName = ""+systemName+"-闲置资源管理-闲置虚拟机资源报表-"+date+".xls";
		
		String path = tempPath+File.separator+fileName;
//		ExcelUtil eu = new ExcelUtil();    ----wsl
//		try {
//			HSSFWorkbook workbook = eu.fresExcelForEMail(path, map);
//			//新建一输出文件流
//			FileOutputStream fOut = new FileOutputStream(path);
//			//把相应的Excel 工作簿存盘
//			workbook.write(fOut);
//			fOut.flush();
//			//操作结束，关闭文件
//			fOut.close();
//
//			System.out.println("--------------  文件已生成  -------------");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
			
		// 邮件发送
		List<Attachment> attachtList = new ArrayList<Attachment>();
		Attachment attachment = new Attachment();
		attachment.setAttachmentLocation(path);
		attachment.setOriginalName(fileName);
		attachtList.add(attachment);
		boolean result = mailNotificationsService.sendSysMgtReportNoticeEmail(WebConstants.MailTemplateId.FRES_EMAIL_TO_EXCEL, attachtList);
		if(result){
			System.out.println("--------------  邮件已发送  -------------");
			//发送完就将本地的文件删掉
			File file = new File(path);
			file.delete();
		}
	}
	
	@Override
	public void createRecoveryExcelForEMail(Criteria example) {
		Date date = new Date();
		Date startDate = new Date(date.getYear(), date.getMonth()-1, 1);
		Date endDate = new Date(date.getYear(), date.getMonth(), 1);
		example.put("openDate", startDate);
		example.put("finishDate", endDate);
		List<FreeRes> list = this.freeresMapper.selectByParamsForRecovery(example);
		List<Biz> bizList = businesstypeService.selectFbizByParams(example);
		
        String tempPath = PropertiesUtil.getProperty("upload.tmpfile.path");
		
		String dateChart = StringUtil.dateFormat(new Date(), StringUtil.DF_YMD);
		String systemName = PropertiesUtil.getProperty("system.name");
		String fileName = ""+systemName+"-闲置资源管理-业务云资源回收报表-"+dateChart+".xls";
		
		String path = tempPath+File.separator+fileName;
		
//		ExcelUtil eu = new ExcelUtil();       -----wsl
//		try {
//			HSSFWorkbook workbook = eu.recoveryExcelForEMail(path, list,bizList);
//			//新建一输出文件流
//			FileOutputStream fOut = new FileOutputStream(path);
//			//把相应的Excel 工作簿存盘
//			workbook.write(fOut);
//			fOut.flush();
//			//操作结束，关闭文件
//			fOut.close();
//			System.out.println("--------------  文件已生成  -------------");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// 邮件发送
		List<Attachment> attachtList = new ArrayList<Attachment>();
		Attachment attachment = new Attachment();
		attachment.setAttachmentLocation(path);
		attachment.setOriginalName(fileName);
		attachtList.add(attachment);
		boolean result = mailNotificationsService.sendSysMgtReportNoticeEmail(WebConstants.MailTemplateId.RECOVERY_EMAIL_TO_EXCEL, attachtList);
		if(result){
			System.out.println("--------------  邮件已发送  -------------");
			//发送完就将本地的文件删掉
			File file = new File(path);
			file.delete();
		}
	}

	@Override
	public void createFresExcelForEMailTest(Criteria criteria) {
//		freeVMResEmailTask.start(criteria);
		createFresExcelForEMail(criteria);
//		iostatService.createOprationWeekExcelForEMail(criteria);
	}

	@Override
	public void createRecoveryExcelForEMailTest(Criteria criteria) {
//		recoveryVMResEmailTask.start(criteria);
		createRecoveryExcelForEMail(criteria);
	}

	@Override
	public void insertFresAndRel(Criteria criteria) {
//		Calendar calendar = Calendar.getInstance();        ---------wsl
//		Date curDate = calendar.getTime();
//		//变更成上一个月的时间
//		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
////		calendar.set(Calendar.WEEK_OF_YEAR, (calendar.get(Calendar.WEEK_OF_YEAR) - 1));
//		String startTime = DateFormatUtils.format(calendar.getTime(), "yyyyMM");
//		Date startDate = new Date();
//		logger.info("call TK interface getFreeResource start");
//		List<GetFreeResourceResponse> freeResources =TkUtils.getFreeResource(startTime);
//		logger.info("call TK interface getFreeResource end");
//		if(freeResources != null) {
//			logger.info("Tk interface return freeResources.size=" + freeResources.size());
//			for(GetFreeResourceResponse freeResResponse : freeResources) {
//				try {
//					String resId = freeResResponse.getResSid();
//
//					if(resId == null) throw new ServiceException("resId is null");
//					//从服务实例资源关系表取得服务实例id
//					Criteria cri = new Criteria();
//					cri.put("resId", resId);
//					List<ServiceInstRes> list = serviceInstResService.selectByParams(cri);
//					Long instanceSid = null;
//					//根据服务实例查询出二级业务id
//					if(!list.isEmpty()) {
//						instanceSid = ((ServiceInstRes)list.get(0)).getInstanceSid();
//					}
//					ServiceInstance serviceInstance = serviceInstanceService.selectByPrimaryKey(instanceSid);
//					Long secordBizSid = serviceInstance.getMgtObjSid();
//					String serviceName = serviceInstance.getInstanceName();
//					if(secordBizSid == null) throw new ServiceException("secordBizSid is null");
//
//					Long fristBizSid = null;
//					criteria = new Criteria();
//					criteria.put("bizSid", secordBizSid);
//					Biz biz = businesstypeService.selectByPrimaryKey(secordBizSid);
//					if(!list.isEmpty()) {
//						fristBizSid = biz.getParentBizSid();
//					}
//					if(fristBizSid == null) throw new ServiceException("fristBizSid is null");
//
//					//查询当前虚拟机的信息
//					ResVm vmInfo = resVmService.getVmInfo(resId);
//					FreeRes freeres = new FreeRes();
//					if(!StringUtil.isNullOrEmpty(freeResResponse.getAvgIo())) {
//						freeres.setAvgIo(Double.parseDouble(freeResResponse.getAvgIo()));
//					}
//					if(vmInfo.getCpuCores()!=null){
//						freeres.setCpuCores(vmInfo.getCpuCores().longValue());
//					}
////					if(freeResResponse.getCpuCores() != null) {
////						freeres.setCpuCores(freeResResponse.getCpuCores());
////					}
//					if(!StringUtil.isNullOrEmpty(freeResResponse.getCpuFerquency())) {
//						freeres.setCpuFerquency(Double.parseDouble(freeResResponse.getCpuFerquency()));
//					}
//					if(!StringUtil.isNullOrEmpty(freeResResponse.getCpuUsage())) {
//						freeres.setCpuUsage(Double.parseDouble(freeResResponse.getCpuUsage()));
//					}
//					if(!StringUtil.isNullOrEmpty(freeResResponse.getMaxIo())) {
//						freeres.setMaxIo(Double.parseDouble(freeResResponse.getMaxIo()));
//					}
//					if(vmInfo.getMemorySize()!=null){
//						freeres.setMemory(vmInfo.getMemorySize());
//					}
////					if(freeResResponse.getMemory() != null) {
////						freeres.setMemory(freeResResponse.getMemory().longValue());
////					}
//					if(!StringUtil.isNullOrEmpty(freeResResponse.getMemUsage())) {
//						freeres.setMemUsage(Double.parseDouble(freeResResponse.getMemUsage()));
//					}
//					String priIps = "";
//					String pubIps = "";
//					List<ResIp> resIpList = vmInfo.getResIpList();
//					if(!CollectionUtils.isEmpty(resIpList)){
//						for (ResIp resIp : resIpList) {
//							if(WebConstants.ResNetworkType.PRIVATE.equals(resIp.getNetworkType())){
//								priIps = priIps + resIp.getIpAddress() +",";
//							}else if(WebConstants.ResNetworkType.PUBLIC.equals(resIp.getNetworkType())){
//								pubIps = pubIps + resIp.getIpAddress() +",";
//							}
//						}
//					}
//					if(priIps.length()>0){
//						priIps = priIps.substring(0, priIps.length()-1);
//					}
//					if(pubIps.length()>0){
//						pubIps = pubIps.substring(0, pubIps.length()-1);
//					}
//					freeres.setPrivageIp(priIps);
//					freeres.setPubIp(pubIps);
//
////					if(freeResResponse.getPrivageIp() != null) {
////						freeres.setPrivageIp(freeResResponse.getPrivageIp());
////					}
////					if(freeResResponse.getPubIp() != null) {
////						freeres.setPubIp(freeResResponse.getPubIp());
////					}
//					if(!StringUtil.isNullOrEmpty(freeResResponse.getResSid())) {
//						freeres.setResSid(freeResResponse.getResSid());
//					}
//
//					//获取对应的vd
//					Long vd = 0L;
//					List<ResVd> resVdList = vmInfo.getResVdList();
//					if(!CollectionUtils.isEmpty(resVdList)){
//						for (ResVd resVd : resVdList) {
//							vd = vd + resVd.getAllocateDiskSize();
//						}
//					}
//					freeres.setStorage(vd);
//
////					if(freeResResponse.getStorage() != null) {
////						freeres.setStorage(freeResResponse.getStorage().longValue());
////					}
//					if(!StringUtil.isNullOrEmpty(freeResResponse.getStUsage())) {
//						freeres.setStUsage(Double.parseDouble(freeResResponse.getStUsage()));
//					}
//					if(!StringUtil.isNullOrEmpty(freeResResponse.getStUsed())) {
//						freeres.setStUsed(Double.parseDouble(freeResResponse.getStUsed()));
//					}
//					if(!StringUtil.isNullOrEmpty(freeResResponse.getResFreeType())) {
//						freeres.setResFreeType(freeResResponse.getResFreeType());
//					}
//					if(!StringUtil.isNullOrEmpty(freeResResponse.getNetFreeType())) {
//						//天馈接口返回数据有问题，暂时处理一下保证程序运行
//						freeres.setNetFreeType(freeResResponse.getNetFreeType().substring(0, 2));
//					}
//
//
//
//					freeres.setFbizSid(fristBizSid);
//					freeres.setSbizSid(secordBizSid);
//					freeres.setResName(serviceName);
//					freeres.setCreator(0L);
//					freeres.setOpTime(curDate);
//					freeres.setStatTime(startDate);
//
//					if(serviceInstance.getIsFree()==null||serviceInstance.getIsFree().equals(2L)){
//						freeres.setStatus(1L);
//						insertSelective(freeres);
////						String activitiFlowKey = serviceConfigService.selectActivitiFlowByServiceSid(WebConstants.ServiceSid.SERVICE_VM,
////								WebConstants.ServiceConfigActiviti.FREE_RESOURCE_REDUCE_PROCESS);
////						ActivitiWorkflowUtil activitiWorkflowUtil = new ActivitiWorkflowUtil();
////						Map<String, Object> variables = new HashMap<String, Object>();
////						variables.put("processType", WebConstants.ProcessType.FREE_RESOURCE_REDUCE);
////						variables.put("instanceSid", serviceInstance.getInstanceSid());
////						variables.put("processObjectId", serviceInstance.getInstanceSid());
////						// 启动流程实例
////						ProcessInstance processInstance = activitiWorkflowUtil.startWorkflow(variables, activitiFlowKey);
//						//更新实例表变更流程Id
////						serviceInstance.setProcessInstanceChangeId(processInstance.getProcessInstanceId());
//						//设置实例为闲置
//						serviceInstance.setIsFree(0L);
//						serviceInstanceService.updateByPrimaryKeySelective(serviceInstance);
//					}else{
//						//该实例已经是闲置的状态，不需要进入审批流程
////						freeres.setStatus(5L);
////						insertSelective(freeres);
//						//更新记录
//						Criteria example = new Criteria();
//						example.put("resSid", freeres.getResSid());
//						example.put("status", "1,2,3");
//						List<FreeRes> fres = selectByParams(example);
//						if(fres!=null&&fres.size()!=0){
//							freeres.setFresSid(fres.get(0).getFresSid());
//							updateByPrimaryKeySelective(freeres);
//						}
//					}
//				} catch(Exception e) {
//					logger.error(e.getMessage(), e);
//				}
//			}
//		} else {
//			logger.error("access getFreeResource interface failure.");
//		}
	}

	@Override
	public List<FreeRes> selectByParamsForRecovery(Criteria criteria) {

		List<FreeRes> list = freeresMapper.selectByParamsForRecovery(criteria);
		return list;
	}

	@Override
	public List<FreeResDisk> selectByParamsForFreeDisk(Criteria criteria) {

		List<FreeResDisk> list = freeresMapper.selectByParamsForFreeDisk(criteria);
		return list;
	}
	
	@Override
	public List<FreeRes> selectByInstSid(Criteria criteria){
		List<FreeRes> list = freeresMapper.selectByInstSid(criteria);
		return list;
	}

	@Override
	public List<FreeRes> selectByParamsForSum(Criteria example) {
		List<FreeRes> list = freeresMapper.selectByParamsForSum(example);
		return list;
	}
}