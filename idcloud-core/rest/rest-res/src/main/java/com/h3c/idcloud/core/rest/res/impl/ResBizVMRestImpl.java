package com.h3c.idcloud.core.rest.res.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.res.ResBizVm;
import com.h3c.idcloud.core.pojo.dto.res.ResBizVmTO;
import com.h3c.idcloud.core.pojo.dto.res.ResStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResVd;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.rest.res.ResBizVMRest;
import com.h3c.idcloud.core.service.product.api.BusinesstypeService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.res.api.ResBizVmService;
import com.h3c.idcloud.core.service.res.api.ResStorageService;
import com.h3c.idcloud.core.service.res.api.ResVdService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.core.service.system.api.MgtObjExtService;
import com.h3c.idcloud.core.service.system.api.MgtObjService;
import com.h3c.idcloud.core.service.system.api.OrgService;
import com.h3c.idcloud.core.service.system.api.OrgbizService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import java.io.*;
import java.util.*;
@Component
public class ResBizVMRestImpl implements ResBizVMRest {
	
	/** 虚拟机Service */
	@Reference(version = "1.0.0")
	ResBizVmService resBizVmService;

	@Reference(version = "1.0.0")
	ResVmService resVmService;
	
	/** 业务的service */
	@Reference(version = "1.0.0")
	BusinesstypeService businesstypeService;

	@Reference(version = "1.0.0")
	OrgService orgService;

	@Reference(version = "1.0.0")
	OrgbizService orgbizService;

	@Reference(version = "1.0.0")
	ResStorageService resStorageService;

	@Reference(version = "1.0.0")
	ResVdService resVdService;

	@Reference(version = "1.0.0")
	ServiceInstanceService instanceService;

	@Reference(version = "1.0.0")
	MgtObjService mgtObjService;

	@Reference(version = "1.0.0")
	MgtObjExtService mgtObjExtService;

//	@Reference(version = "1.0.0")
//	UserService userService;
	
	/**
	 * 查询VM列表
	 */
	@Override
	@WebMethod
	@POST
	public Response findVM(ResBizVm vm) {
//		Criteria example = new Criteria();
//
//		if(!StringUtil.isNullOrEmpty(vm.getMgtObjSid())){
//			example.put("mgtObjSid", vm.getMgtObjSid());
//		}
//		if (!StringUtil.isNullOrEmpty(vm.getParentMgtObjSid())) {
//			example.put("parentMgtObjSid", vm.getParentMgtObjSid());
//		}
//		if (!StringUtil.isNullOrEmpty(vm.getMgtObjLevel())) {
//			example.put("mgtObjLevel", vm.getMgtObjLevel());
//		}
//		if (!StringUtil.isNullOrEmpty(vm.getVmNameLike())) {
//			example.put("vmNameLike", vm.getVmNameLike());
//		}
//		if (!StringUtil.isNullOrEmpty(vm.getStatus())) {
//			example.put("status", vm.getStatus());
//		}
//		if (!StringUtil.isNullOrEmpty(vm.getMgtObjName())) {
//			example.put("mgtObjName", StringUtil.delSpace(vm.getMgtObjName()));
//		}
//		example.setOrderByClause("A.VM_NAME");
//		List<ResBizVm> list = this.resBizVmService.selectByParams(example);
//		for (ResBizVm resBizVm : list){
//			if (StringUtil.isNullOrEmpty(resBizVm.getMonitorNodeId())){
//				resBizVm.setMonitorStatus(WebConstants.TKMonitorNode.STATUS_UNMONITORED);
//			}
//			else {
//				resBizVm.setMonitorStatus(WebConstants.TKMonitorNode.STATUS_MONITORED);
//			}
//		}
//		String json = JsonUtil.toJson(list);
		String json ="";
		return Response.status(Status.OK).entity(json).build();
	}
	
	/**
	 * 查询业务虚拟机---分页
	 */
	@Override
	@WebMethod
	@GET
	@Path("/findAll")
	public Response findBizVmByPaging(@Context HttpServletRequest request) {
		// 参数设置
//				Criteria param = new Criteria();
//				WebUtil.preparePageParams(request, param, "A.VM_NAME");
//
//				// 查询数据
//				List<ResBizVm> list = this.resBizVmService.selectByParams(param);
//				// 总数
//				int total = this.resBizVmService.countByParams(param);
//
//				//取得监控数据
//				Calendar cal=Calendar.getInstance();
//		        Date d=cal.getTime();
//		        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
//		        String period = sdf.format(d)+"000000:"+sdf.format(d)+"235959";
////		        List<GetNodeMonitorInfoResponse> nodeList = TkUtils.getAllNodeMonitorInfo(period);
//		        List<GetNodeMonitorInfoResponse> nodeList = new ArrayList<GetNodeMonitorInfoResponse>();
//		        List<ResBizVm> list2 = new ArrayList<ResBizVm>();
//		        for (ResBizVm resBizVm : list){
//		        	boolean flag = true;
//		        	if (!StringUtil.isNullOrEmpty(resBizVm.getMonitorNodeId())){
//		        		if(nodeList!=null&&nodeList.size()!=0){
//							for (GetNodeMonitorInfoResponse nodeInfo : nodeList) {
//								if(nodeInfo.getNodeId().equals(resBizVm.getMonitorNodeId())){
//									resBizVm.setCpuUsage(nodeInfo.getCpuAvgUsage()+"%");
//									resBizVm.setCpuMaxUsage(nodeInfo.getCpuMaxUsage()+"%");
//									resBizVm.setMemUsage(nodeInfo.getMemoryAvgUsage()+"%");
//									resBizVm.setMemMaxUsage(nodeInfo.getMemoryMaxUsage()+"%");
//									resBizVm.setDiskUsage(nodeInfo.getStAvgUsage()+"%");
//									resBizVm.setDiskMaxUsage(nodeInfo.getStMaxUsage()+"%");
//									resBizVm.setNetWorkAvg(nodeInfo.getNetworkAvgIo());
//									resBizVm.setNetWorkMax(nodeInfo.getNetworkMaxIo());
//									flag = false;
//									break;
//								}
//							}
//
//						}
//					}
//		        	if(flag){
//						resBizVm.setCpuUsage("0.00%");
//						resBizVm.setCpuMaxUsage("0.00%");
//						resBizVm.setMemUsage("0.00%");
//						resBizVm.setMemMaxUsage("0.00%");
//						resBizVm.setDiskUsage("0.00%");
//						resBizVm.setDiskMaxUsage("0.00%");
//						resBizVm.setNetWorkAvg(0.00);
//						resBizVm.setNetWorkMax(0.00);
//					}
//		        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		        	String presetTime = PropertiesUtil.getProperty("mgtobj.expiredate.notice");
//		        	if(resBizVm.getMgtObjSid()!=null){
//			        	Criteria criteria = new Criteria();
//			    		int time = Integer.parseInt(presetTime)*7;
//		    			criteria.put("mgtObjSid", resBizVm.getMgtObjSid());
//		    			List<MgtObjExt> mgtObjExts = this.mgtObjExtService.selectByParams(criteria);
//		    			if(!CollectionUtils.isEmpty(mgtObjExts)){
//		    				for (MgtObjExt mgtObjExt : mgtObjExts) {
//		    					if("projectEndDate".equals(mgtObjExt.getAttrKey())){
//		    						try {
//		    							String mgtObjEndDate = mgtObjExt.getAttrValue();
//		    							Date endTime = df.parse(mgtObjEndDate+" 23:59:59");
//		    							Date now = new Date();
//		    							long buffer = endTime.getTime() - now.getTime();
//		    							long days = buffer/(1000*60*60*24);
//		    							//如果在提醒范围内
//		    							if(time>=days&&0<=days){
//		    								resBizVm.setInNoticeTime(1);
//		    							}else if(days<0){
//		    								resBizVm.setInNoticeTime(-1);
//		    							}else{
//		    								resBizVm.setInNoticeTime(0);
//		    							}
//		    						} catch (ParseException e) {
//		    							e.printStackTrace();
//		    						}
//		    					}
//		    				}
//		    			}
//		        	}
//					list2.add(resBizVm);
//		        }
//				String json = JsonUtil.toJson(new BaseGridReturn(total, list2));
		String json ="";
				return Response.status(Status.OK).entity(json).build();
	}

	/**
	 * 查询VM列表
	 */
	@Override
	@WebMethod
	@POST
	@Path("/findVMAndMonitorInfo")
	public Response findVMAndMonitorInfo(ResBizVm vm) {
//		Criteria example = new Criteria();
//
//		if(!StringUtil.isNullOrEmpty(vm.getMgtObjSid())){
//			example.put("mgtObjSid", vm.getMgtObjSid());
//		}
//		if (!StringUtil.isNullOrEmpty(vm.getParentMgtObjSid())) {
//			example.put("parentMgtObjSid", vm.getParentMgtObjSid());
//		}
//		if (!StringUtil.isNullOrEmpty(vm.getParentBizSids())) {
//			example.put("parentBizSids", vm.getParentBizSids());
//		}
//		if (!StringUtil.isNullOrEmpty(vm.getMgtObjLevel())) {
//			example.put("mgtObjLevel", vm.getMgtObjLevel());
//		}
//		if (!StringUtil.isNullOrEmpty(vm.getVmNameLike())) {
//			example.put("vmNameLike", vm.getVmNameLike());
//		}
//		if (!StringUtil.isNullOrEmpty(vm.getStatus())) {
//			example.put("status", vm.getStatus());
//		}
//		if (!StringUtil.isNullOrEmpty(vm.getMgtObjName())) {
//			example.put("mgtObjName", StringUtil.delSpace(vm.getMgtObjName()));
//		}
//		example.setOrderByClause("A.VM_NAME");
//		List<ResBizVm> list = this.resBizVmService.selectByParams(example);
//
//		//取得监控数据
//		Calendar cal=Calendar.getInstance();
//        Date d=cal.getTime();
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
//        String period = sdf.format(d)+"000000:"+sdf.format(d)+"235959";
//        List<GetNodeMonitorInfoResponse> nodeList = TkUtils.getAllNodeMonitorInfo(period);
//        List<ResBizVm> list2 = new ArrayList<ResBizVm>();
//        if(nodeList!=null&&nodeList.size()!=0){
//	        for (ResBizVm resBizVm : list){
//				if (!StringUtil.isNullOrEmpty(resBizVm.getMonitorNodeId())){
//					for (GetNodeMonitorInfoResponse nodeInfo : nodeList) {
//						if(nodeInfo.getNodeId().equals(resBizVm.getMonitorNodeId())){
//							resBizVm.setCpuUsage(nodeInfo.getCpuAvgUsage()+"%");
//							resBizVm.setCpuMaxUsage(nodeInfo.getCpuMaxUsage()+"%");
//							resBizVm.setMemUsage(nodeInfo.getMemoryAvgUsage()+"%");
//							resBizVm.setMemMaxUsage(nodeInfo.getMemoryMaxUsage()+"%");
//							resBizVm.setDiskUsage(nodeInfo.getStAvgUsage()+"%");
//							resBizVm.setDiskMaxUsage(nodeInfo.getStMaxUsage()+"%");
//							resBizVm.setNetWorkAvg(nodeInfo.getNetworkAvgIo());
//							resBizVm.setNetWorkMax(nodeInfo.getNetworkMaxIo());
//							break;
//						}
//					}
//					list2.add(resBizVm);
//				}else{
//					list2.add(resBizVm);
//				}
//			}
//        }
//		String json = JsonUtil.toJson(list2);
		String json ="";
		return Response.status(Status.OK).entity(json).build();
	}
	
	/**
	 * 获取虚拟机信息
	 * @throws Exception 
	 */
	@Override
	@WebMethod
	@POST
	@Path("/getVmInfo")
	public Response getVmInfo(ResBizVm vm) throws Exception {
//		ResBizVm resVm=this.resBizVmService.selectByPrimaryKey(vm.getResBizVmSid());
//		ResBizVm rv=this.resBizVmService.getVmInfo(resVm);
//		String json=JsonUtil.toJson(rv);
		String json ="";
		return Response.status(Status.OK).entity(json).build();
	}
	
	/**
	 * 虚拟机纳管
	 * @throws Exception 
	 */
	@Override
	@WebMethod
	@POST
	@Path("/relateResBizVm")
	public Response relateResBizVm(ResBizVm bizVm) {
		String returnJson = null;
//
//		ResBizVmTO resBizVmTO = new ResBizVmTO();
//		String[] sids = bizVm.getResBizVmSids().split(",");
//		List<ResVm> list = new ArrayList<ResVm>();
//		if (sids != null && sids.length > 0) {
//			for (int i = 0; i < sids.length; i++) {
//				ResVm resVm = this.resVmService.selectByPrimaryKey(sids[i]);
//				list.add(resVm);
//			}
//		}
//		if(!StringUtil.isNullOrEmpty(bizVm.getUserSid())){
//			User user = userService.selectByPrimaryKey(Long.parseLong(bizVm.getUserSid()), 0);
//			resBizVmTO.setAccount(user.getAccount());
//		}
//		resBizVmTO.setResVmList(list);
//		resBizVmTO.setBizSid(bizVm.getMgtObjSid());
//
//		int result = this.resBizVmService.createRelation(resBizVmTO);
//		if (result == 1) {
//			returnJson = JsonUtil.toJson(new RestResult(RestResult.SUCCESS, WebUtil
//					.getMessage("纳管成功！"), null));
//		} else {
//			returnJson = JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil
//					.getMessage("纳管失败！"), null));
//		}

		return Response.status(Status.OK).entity(returnJson).build();
	}
	
	/**
	 * 取消虚拟机纳管
	 * @throws Exception 
	 */
	@Override
	@WebMethod
	@POST
	@Path("/unRelateResBizVm")
	public Response unRelateResBizVm(ResBizVmTO resBizVmTO) {
		String returnJson="";
//		int result = this.resBizVmService.cancelRelation(resBizVmTO);
//		if (result == 1) {
//			returnJson = JsonUtil.toJson(new RestResult(RestResult.SUCCESS, WebUtil
//					.getMessage("取消纳管成功！"), null));
//		} else {
//			returnJson = JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil
//					.getMessage("取消纳管失败！"), null));
//		}
		
		return Response.status(Status.OK).entity(returnJson).build();
	}

	@Override
	@WebMethod
	@POST
	@Path("/getBizVmCount")
	public Response getBizVmCount(ResBizVm vm) {
//		Criteria criteria = new Criteria();
//		List<Biz> bizs = businesstypeService.selectFbizByParams(criteria);
//		Map<String,Integer> map = new HashMap<String, Integer>();
//		int sum = 0;
//		for (Biz biz : bizs) {
//			Criteria example = new Criteria();
//			example.put("parentBizSid", biz.getBizSid());
//			List<ResBizVm> list = this.resBizVmService.selectByParams2(example);
//			map.put(biz.getName(), list.size());
//			sum = sum + list.size();
//		}
//		StringBuffer json = new StringBuffer();
//		for (int i=0;i<bizs.size();i++) {
//			Integer integer = map.get(bizs.get(i).getName());
//			if(i==bizs.size()-1){
//				json.append("['"+bizs.get(i).getName()+"',"+integer+"]");
//			}else{
//				json.append("['"+bizs.get(i).getName()+"',"+integer+"],");
//			}
//		}
//		json.insert(0, '[');
//		json.append(']');
//		Map<String,String> strMap = new HashMap<String, String>();
//		strMap.put("sumNum", sum+"");
//		strMap.put("data", json.toString());
//		String result = "";
//		result = JsonUtil.toJson(strMap);
		String result ="";
		return Response.status(Status.OK).entity(result).build();
	}
	@Override
	@WebMethod
	@POST
	@Path("/setMonitorNode")
	public Response setMonitorNode(ResBizVmTO resBizVmTO) {
		String returnJson="";
//		int result = this.resBizVmService.setMonitorNode(resBizVmTO);
//		if (result == 1) {
//			returnJson = JsonUtil.toJson(new RestResult(RestResult.SUCCESS, WebUtil
//					.getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS), null));
//		} else {
//			returnJson = JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil
//					.getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE), null));
//		}
		
		return Response.status(Status.OK).entity(returnJson).build();
	
	}

	@Override
	@WebMethod
	@GET
	@Path("/getBizVmForReport/{params}")
	@Produces("application/vnd.ms-excel")
	public Response getBizVmForReport(@PathParam("params") String params ,@Context HttpServletResponse servletResponse) {
//		ByteArrayOutputStream text = new ByteArrayOutputStream();
//		try {
//			Criteria example = new Criteria();
//			Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
//			Criteria criteria = new Criteria();
//			if (!StringUtil.isNullOrEmpty(map.get("fbizSid"))) {
//				criteria.put("fbizSid", map.get("fbizSid"));
//			}
//			if (!StringUtil.isNullOrEmpty(map.get("sbizSid"))) {
//				criteria.put("sbizSid", map.get("sbizSid"));
//			}
//			if (!StringUtil.isNullOrEmpty(map.get("startDate"))) {
//				criteria.put("startDate", map.get("startDate"));
//			}
//			if (!StringUtil.isNullOrEmpty(map.get("finishDate"))) {
//				criteria.put("finishDate", map.get("finishDate"));
//			}
//			List<ResBizVm> bizVMList = new ArrayList<ResBizVm>();
//			List<ResBizVm> list = this.resBizVmService.selectByParamsForReport(example);
//			for (ResBizVm resBizVm : list) {
//				Long mem = BigDecimal.valueOf((resBizVm.getMemorySize()==null?0:resBizVm.getMemorySize())).divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_UP).longValue();
//				Long st = BigDecimal.valueOf((resBizVm.getStorage()==null?0:resBizVm.getStorage())).longValue();
//				String ip = (resBizVm.getVmIp()==null?"":resBizVm.getVmIp());
//				String[] split = ip.split(",");
//				String ipFormat = "";
//				for (String string : split) {
//					ipFormat = ipFormat + string +"/";
//				}
//				if(ipFormat.length()>0){
//					ipFormat = ipFormat.substring(0, ipFormat.length()-1);
//				}
//				resBizVm.setMemorySize(mem);
//				resBizVm.setStorage(st);
//				resBizVm.setVmIp(ipFormat);
//				bizVMList.add(resBizVm);
//			}
//			text = JasperReportUtil.getExcel("/reporttemplate/bizvm-report-tmp.jrxml", null, bizVMList);
//			OutputStream outputStream = new FileOutputStream ("bizVMRel.xls");
//			text.writeTo(outputStream);
//			File file = new File("bizVMRel.xls");
//			ResponseBuilder response = Response.ok((Object) file);
//			String fileName = "业务虚拟机关系表";
//			response.header("Content-Disposition", "attachment; filename="+new String( fileName .getBytes("gb2312"), "ISO8859-1" ) +".xls");
//			return response.build();
//		}  catch (IOException e) {
//			e.printStackTrace();
//			String str = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
//			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(str).build();
//		}
		String str="";
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(str).build();
	}

	/**
	 * 修改納管以后的虚拟机信息
	 */
	@WebMethod
	@POST
	@Path("/modify")
	public Response modifyInfo(ResBizVmTO resBizVmTO) {
		String returnJson="";
//		int result = this.resBizVmService.modifyVm(resBizVmTO);
//		if (result == 1) {
//			returnJson = JsonUtil.toJson(new RestResult(RestResult.SUCCESS, WebUtil
//					.getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS), null));
//		} else {
//			returnJson = JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil
//					.getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE), null));
//		}
		
		return Response.status(Status.OK).entity(returnJson).build();
	}
	

	/**
	 * POI - 导出vm列表
	 */
	@Override
	@WebMethod
	@GET
	@Path("/exportBizVm/{params}")
	@Produces("application/vnd.ms-excel")
	public Response exportBizVmDatagrid(@PathParam("params") String params) {
//		Criteria example = new Criteria();
//		Map<String,Object> conditionMap = new HashMap<String,Object>();
//		try {
//			conditionMap = JsonUtil.fromJson(params, Map.class);
//
//			if (!StringUtil.isNullOrEmpty(conditionMap.get("vmNameLike"))) {
//				example.put("vmNameLike", conditionMap.get("vmNameLike"));
//			}
//			if (!StringUtil.isNullOrEmpty(conditionMap.get("status"))) {
//				example.put("status", conditionMap.get("status"));
//			}
//			if (!StringUtil.isNullOrEmpty(conditionMap.get("bizSid"))) {
//				example.put("bizSid", conditionMap.get("bizSid"));
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		List<ResBizVm> list = this.resBizVmService.selectByParams(example);
//		ExcelUtil excel = new ExcelUtil();
//		HSSFWorkbook wb = excel.exportBizVmToExcel(list);
//		OutputStream outputStream;
//		try {
//			outputStream = new FileOutputStream ("bizVmReports.xls");
//			try {
//				wb.write(outputStream);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
		File file = new File("bizVmReports.xls");
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=StorageReports.xls");
		return response.build();
	}

	@Override
	@WebMethod
	@GET
	@Path("/getBizVmMonitorForReport/{params}")
	@Produces("application/vnd.ms-excel")
	public Response getBizVmMonitorForReport(@PathParam("params") String params,@Context HttpServletResponse servletResponse) {
		//导出的是昨天的数据
//		Calendar cal=Calendar.getInstance();
//        cal.add(Calendar.DATE,-1);
//        Date d=cal.getTime();
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
//        String period = sdf.format(d)+"000000:"+sdf.format(d)+"235959";
//        List<GetNodeMonitorInfoResponse> nodeList = TkUtils.getAllNodeMonitorInfo(period);
//        Map<String,List> map = new HashMap<String,List>();
//        //分大业务的sheet
//        List<List<Map>> exceldata = new ArrayList<List<Map>>();
//        List<Map> dataList = null;
//		Map<String, Object> dataMap = null;
//
//		List<Map> totaldataList = new ArrayList<Map>();
//		Map<String, Object> totaldataMap = null;
//		String[] totalhead = new String[] { "业务类型", "业务总数","虚拟机总数","CPU(核)","CPU使用率均值(%)","内存(G)","内存使用率均值(%)","存储(G)","存储使用率均值(%)","流量总计(Mbps)"};
//		String[] totalheaderField = new String[] {"parentName", "bizCount", "vmCount","cpuCore","cpuTotalUsage", "memSize","memTotalUsage","storage","stTotalUsage", "ioTotal"};
//		short[] totalwidths = new short[] { 15 * 256, 10 * 256, 10 * 256, 10 * 256,10 * 256,10 * 256,10 * 256, 10 * 256,10 * 256,10 * 256, 15 * 256};
//		Integer[] totaltype = new Integer[] { 1, 0, 0, 0,0, 0,0,0, 0,0};
//        int bizTotal = 0;
//        int vmTotal = 0;
//        Integer cpuTotal = 0;
//        Long memTotal = 0L;
//        int diskTotal = 0;
//        double cpuTotalUsed = 0;
//		double memTotalUsed = 0;
//		double stTotalUsed = 0;
//        double io = 0;
//
//		String bizNames = "";
//        Criteria criteria = new Criteria();
//        List<Biz> bizs = businesstypeService.selectFbizByParams(criteria);
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    	for (Biz biz : bizs) {
//    		dataList = new ArrayList<Map>();
//    		criteria.put("parentBizSid", biz.getBizSid());
//
//    		List<Biz> sbiz = businesstypeService.selectByParams2(criteria);
//    		ResBizVm list = this.resBizVmService.statisticalBizOfVM(biz.getBizSid());
//    		totaldataMap = new HashMap<String, Object>();
//			totaldataMap.put("parentName", biz.getName());
//			totaldataMap.put("bizCount", sbiz.size());
//			bizTotal = bizTotal + sbiz.size();
//			totaldataMap.put("vmCount", list.getStaTotalVm());
//			vmTotal = vmTotal + list.getStaTotalVm();
//			totaldataMap.put("cpuCore", list.getCpuCores()==null?0:list.getCpuCores());
//			cpuTotal = cpuTotal + (list.getCpuCores()==null?0:list.getCpuCores());
//			totaldataMap.put("memSize", list.getMemorySize()==null?0:list.getMemorySize());
//			memTotal = memTotal + (list.getMemorySize()==null?0:list.getMemorySize());
//			totaldataMap.put("storage", new Integer(list.getStaVmDisk())==null?0:list.getStaVmDisk());
//			diskTotal = diskTotal + (new Integer(list.getStaVmDisk())==null?0:list.getStaVmDisk());
//
//    		List<ResBizVm> bizVms = resBizVmService.selectByParams2(criteria);
//    		double cpuUsedTotal = 0;
//    		double memUsedTotal = 0;
//    		double stUsedTotal = 0;
//    		double totalIo = 0;
//    		if(bizVms!=null&&bizVms.size()!=0){
//    			bizNames = bizNames + biz.getName()+",";
//    			for (int i = 0; i < bizVms.size(); i++) {
//    				dataMap = new HashMap<String, Object>();
//					ResBizVm rbv = bizVms.get(i);
//					GetNodeMonitorInfoResponse gmir = new GetNodeMonitorInfoResponse();
//					if(nodeList!=null&&nodeList.size()!=0){
//    					for (GetNodeMonitorInfoResponse monitor : nodeList) {
//    						if(monitor.getNodeId().equals(rbv.getMonitorNodeId())){
//    							gmir = monitor;
//    							break;
//    						}
//    					}
//					}
//					dataMap.put("num",i+1);
//					dataMap.put("bizName",rbv.getName());
//					dataMap.put("orgName",rbv.getOrgName());
//					dataMap.put("opDate",rbv.getDredgeDate()==null?"":sdf2.format(rbv.getDredgeDate()));
//					dataMap.put("vmName",rbv.getVmName());
//					dataMap.put("statusName",rbv.getStatusName());
//					dataMap.put("snmpStatus",(gmir.getSnmpStatusName()==null?"":gmir.getSnmpStatusName()));
//					dataMap.put("cpuCore",rbv.getCpuCores());
//					dataMap.put("cpuUsage",(gmir.getCpuAvgUsage()==null?"":gmir.getCpuAvgUsage()));
//					dataMap.put("cpuUsageMax",(gmir.getCpuMaxUsage()==null?"":gmir.getCpuMaxUsage()));
//					dataMap.put("memSize",BigDecimal.valueOf(rbv.getMemorySize()).divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
//					dataMap.put("memUsage",(gmir.getMemoryAvgUsage()==null?"":gmir.getMemoryAvgUsage()));
//					dataMap.put("memUsageMax",(gmir.getMemoryMaxUsage()==null?"":gmir.getMemoryMaxUsage()));
//					dataMap.put("storage",rbv.getStorage());
//					dataMap.put("stUsage",(gmir.getStAvgUsage()==null?"":gmir.getStAvgUsage()));
//					dataMap.put("stUsageMax",(gmir.getStMaxUsage()==null?"":gmir.getStMaxUsage()));
//					dataMap.put("ioAvg",(gmir.getNetworkAvgIo()==null?"":gmir.getNetworkAvgIo()));
//					dataMap.put("ioMax",(gmir.getNetworkMaxIo()==null?"":gmir.getNetworkMaxIo()));
//					dataMap.put("ipAddress",rbv.getVmIp());
//					dataList.add(dataMap);
//
//					cpuUsedTotal = cpuUsedTotal + (rbv.getCpuCores()*(gmir.getCpuAvgUsage()==null?0:gmir.getCpuAvgUsage()));
//					memUsedTotal = memUsedTotal + (BigDecimal.valueOf(rbv.getMemorySize()).divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_UP).doubleValue()
//							*(gmir.getMemoryAvgUsage()==null?0:gmir.getMemoryAvgUsage()));
//					stUsedTotal = stUsedTotal + (rbv.getStorage()*(gmir.getStAvgUsage()==null?0:gmir.getStAvgUsage()));
//					totalIo = totalIo + (gmir.getNetworkAvgIo()==null?0:gmir.getNetworkAvgIo());
//        		}
//    			exceldata.add(dataList);
//			}
//    		if(list.getCpuCores()==null||list.getCpuCores()==0){
//    			totaldataMap.put("cpuTotalUsage", 0);
//    		}else{
//    			totaldataMap.put("cpuTotalUsage", BigDecimal.valueOf(cpuUsedTotal).divide(BigDecimal.valueOf(list.getCpuCores()), 2, BigDecimal.ROUND_HALF_UP));
//    		}
//    		if(list.getMemorySize()==null||list.getMemorySize()==0){
//    			totaldataMap.put("memTotalUsage", 0);
//    		}else{
//    			totaldataMap.put("memTotalUsage", BigDecimal.valueOf(memUsedTotal).divide(BigDecimal.valueOf(list.getMemorySize()), 2, BigDecimal.ROUND_HALF_UP));
//    		}
//    		if(new Integer(list.getStaVmDisk())==null||list.getStaVmDisk()==0){
//    			totaldataMap.put("stTotalUsage", 0);
//    		}else{
//    			totaldataMap.put("stTotalUsage", BigDecimal.valueOf(stUsedTotal).divide(BigDecimal.valueOf(list.getStaVmDisk()), 2, BigDecimal.ROUND_HALF_UP));
//    		}
//    		totaldataMap.put("ioTotal", totalIo);
//    		totaldataList.add(totaldataMap);
//
//    		cpuTotalUsed = cpuTotalUsed + cpuUsedTotal;
//    		memTotalUsed = memTotalUsed + memUsedTotal;
//    		stTotalUsed = stTotalUsed + stUsedTotal;
//    		io = io + totalIo;
//        }
//    	totaldataMap = new HashMap<String, Object>();
//		totaldataMap.put("parentName", "总计");
//		totaldataMap.put("bizCount", bizTotal);
//		totaldataMap.put("vmCount", vmTotal);
//		totaldataMap.put("cpuCore", cpuTotal);
//		totaldataMap.put("memSize", memTotal);
//		totaldataMap.put("storage", diskTotal);
//		if(cpuTotal==null||cpuTotal==0){
//			totaldataMap.put("cpuTotalUsage", 0);
//		}else{
//			totaldataMap.put("cpuTotalUsage", BigDecimal.valueOf(cpuTotalUsed).divide(BigDecimal.valueOf(cpuTotal), 2, BigDecimal.ROUND_HALF_UP));
//		}
//		if(memTotal==null||memTotal==0){
//			totaldataMap.put("memTotalUsage", 0);
//		}else{
//			totaldataMap.put("memTotalUsage", BigDecimal.valueOf(memTotalUsed).divide(BigDecimal.valueOf(memTotal), 2, BigDecimal.ROUND_HALF_UP));
//		}
//		if(new Integer(diskTotal)==null||diskTotal==0){
//			totaldataMap.put("stTotalUsage", 0);
//		}else{
//			totaldataMap.put("stTotalUsage", BigDecimal.valueOf(stTotalUsed).divide(BigDecimal.valueOf(diskTotal), 2, BigDecimal.ROUND_HALF_UP));
//		}
//		totaldataMap.put("ioTotal", io);
//		totaldataList.add(totaldataMap);
//    	exceldata.add(0, totaldataList);
//    	bizNames = "汇总,"+bizNames;
//
//        if(bizNames.length()>0){
//    		bizNames = bizNames.substring(0, bizNames.length()-1);
//    	}
//    	String[] sheetName = bizNames.split(",");
//		//判断那个字段不需要显示
//		String[] cons = params.split(",");
//		// 表的头部
//		int[] type = new int[] { 0, 1,1,1,1,1, 0, 0,0, 0,0,0,0,0,0,0,0,1,1};
//		List<String> headList = new ArrayList<String>();
//		List<String> headFileList = new ArrayList<String>();
//		List<Integer> typeList = new ArrayList<Integer>();
//
//		String[] head = new String[] { "编号", "业务名称","归属部门","开通时间","虚拟机名称","状态(开机/关机)","CPU(核)","CPU使用率(%)","CPU使用率峰值(%)",
//									"内存(GB)","内存使用率(%)","内存使用率峰值(%)","存储(GB)","存储使用率(%)","存储使用率峰值(%)",
//									"均值流量(Mbps)","峰值流量(Mbps)","IP地址（多IP用逗号分开）","SNMP监控(是/否)"};
//		String[] headerField = new String[] {"num", "bizName", "orgName", "opDate","vmName","statusName", "cpuCore","cpuUsage","cpuUsageMax","memSize","memUsage"
//				,"memUsageMax","storage","stUsage","stUsageMax","ioAvg","ioMax","ipAddress","snmpStatus"};
//		// 每列的宽度
//		short[] widths = new short[] { 8 * 256, 20 * 256, 20 * 256, 20 * 256,20 * 256, 10 * 256,10 * 256,10 * 256,10 * 256,10 * 256,10 * 256,10 * 256,10 * 256,10 * 256,10 * 256,10 * 256,10 * 256,50 * 256,20 * 256};
//		for (short i = 0; i < head.length; i++) {
//			if(Boolean.parseBoolean((cons[i]))){
//				headList.add(head[i]);
//				headFileList.add(headerField[i]);
//				typeList.add(type[i]);
//			}
//		}
//		String[] headTitleFiled = new String[headList.size()];
//		String[] headFiled = new String[headFileList.size()];
//		Integer[] types = new Integer[typeList.size()];
//		headList.toArray(headTitleFiled);
//		headFileList.toArray(headFiled);
//		typeList.toArray(types);
//		List<String[]> headers = new ArrayList<String[]>();
//		List<String[]> headerFiles = new ArrayList<String[]>();
//		List<short[]> cellwidths = new ArrayList<short[]>();
//		List<Integer[]> datatypes = new ArrayList<Integer[]>();
//		for(int i=1;i<sheetName.length;i++){
//			headers.add(headTitleFiled);
//			headerFiles.add(headFiled);
//			cellwidths.add(widths);
//			datatypes.add(types);
//		}
//		headers.add(0,totalhead);
//		headerFiles.add(0, totalheaderField);
//		cellwidths.add(0, totalwidths);
//		datatypes.add(0, totaltype);
//		String date = StringUtil.dateFormat(new Date(), StringUtil.DF_YMD);
//		String systemName = com.hptsic.cloud.common.utils.PropertiesUtil.getProperty("system.name");
//		String fileName = ""+systemName+"-业务管理-虚拟机监控报表-"+date+".xls";
//
//		POIUtil excelUtil = new POIUtil();
//		excelUtil.doFromDataListToExecl(servletResponse, headers, headerFiles, cellwidths, exceldata, sheetName, fileName,datatypes);
		return null;
	}

	@Override
	public Response statisticalBizOfVM(@PathParam("resBizSid") Long resBizSid) {
		ResBizVm list = this.resBizVmService.statisticalBizOfVM(resBizSid);
		String json = JsonUtil.toJson(new RestResult(list));
		return Response.status(Status.OK).entity(json).build();
	}
	
	/**
	 * 查询可纳管的虚拟机
	 */
	@Override
	@WebMethod
	@POST
	@Path("/findNanotubeVm")
	public Response findNanotubeableVM(ResBizVm vm) {
        Criteria example = new Criteria();
		if (!StringUtil.isNullOrEmpty(vm.getVmNameLike())) {
			example.put("vmNameLike", vm.getVmNameLike());
		}
		if (!StringUtil.isNullOrEmpty(vm.getVmIp())) {
			example.put("resBizVmIpLike", vm.getVmIp());
		}
		if (!StringUtil.isNullOrEmpty(vm.getStatus())) {
			example.put("status", vm.getStatus());
		}
		example.put("nanotubes", "yes");
		example.setOrderByClause("A.VM_NAME");
		List<ResBizVm> list = this.resBizVmService.selectNanotubeableVmInBiz(example);

		String json = JsonUtil.toJson(new RestResult(list));
		return Response.status(Status.OK).entity(json).build();
	}
	

	@Override
	@WebMethod
	@GET
	@Path("/getVmMonitorForReport")
	@Produces("application/vnd.ms-excel")
	public Response getVmMonitorForReport(@Context HttpServletResponse servletResponse) {
		//汇总的sheet的相关参数
//		String[] totalhead = new String[] { "业务类型", "业务总数","虚拟机总数","CPU(核)","CPU使用率(%)","内存(G)","内存使用率(%)","存储(G)","存储使用率(%)","流量总计(Mbps)"};
//		String[] totalheaderField = new String[] {"parentName", "bizCount", "vmCount","cpuCore","cpuUsage", "memSize","memUsage","storage","stUsage", "ioTotal"};
//		short[] totalwidths = new short[] { 15 * 256, 10 * 256, 10 * 256, 10 * 256,10 * 256,10 * 256,10 * 256,10 * 256,10 * 256, 15 * 256};
//		Integer[] totaltype = new Integer[] { 1, 0, 0, 0,0, 0,0,0,0, 0};
//
//		String[] head = new String[] { "编号", "业务名称","CPU(核)","CPU使用率(%)","内存(G)","内存使用率(%)","存储(G)","存储使用率(%)","流量总计(Mbps)"};
//		String[] headerField = new String[] {"num", "bizName", "cpuCore","cpuUsage", "memSize","memUsage","storage","stUsage", "ioTotal"};
//		// 每列的宽度
//		short[] widths = new short[] { 8 * 256, 20 * 256, 10 * 256, 10 * 256,10 * 256,10 * 256, 10 * 256,10 * 256, 15 * 256};
//		Integer[] type = new Integer[] { 0, 1, 0, 0,0, 0,0, 0,0};
//		List<String[]> headers = new ArrayList<String[]>();
//		List<String[]> fileds = new ArrayList<String[]>();
//		List<short[]> cellwidth = new ArrayList<short[]>();
//		List<Integer[]> types = new ArrayList<Integer[]>();
//		//导出的是昨天的数据
//		Calendar cal=Calendar.getInstance();
//        cal.add(Calendar.DATE,-1);
//        Date d=cal.getTime();
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
//        String period = sdf.format(d)+"000000:"+sdf.format(d)+"235959";
//        List<GetNodeMonitorInfoResponse> nodeList = TkUtils.getAllNodeMonitorInfo(period);
//        List<GetNodeMonitorInfoResponse> tempList = new ArrayList<GetNodeMonitorInfoResponse>(nodeList);
//
//        //得到所以的业务虚机
//        Criteria params = new Criteria();
//        params.setOrderByClause("A.PARENT_BIZ_SID,A.BIZ_SID");
//        List<ResBizVm> bizVms = resBizVmService.selectByParamsForList(params);
//        if(!CollectionUtils.isEmpty(bizVms)){
//        	for (ResBizVm rbv : bizVms) {
//        		for (GetNodeMonitorInfoResponse monitor : nodeList) {
//        			if(monitor.getNodeId().equals(rbv.getMonitorNodeId())){
//						rbv.setCpuUsage((monitor.getCpuAvgUsage()==null?0:monitor.getCpuAvgUsage())+"");
//						rbv.setMemUsage((monitor.getMemoryAvgUsage()==null?0:monitor.getMemoryAvgUsage())+"");
//						rbv.setDiskUsage((monitor.getStAvgUsage()==null?0:monitor.getStAvgUsage())+"");
//						rbv.setNetWorkAvg(monitor.getNetworkAvgIo()==null?0:monitor.getNetworkAvgIo());
//						tempList.remove(monitor);
//						break;
//					}
//				}
//        		nodeList = new ArrayList<GetNodeMonitorInfoResponse>(tempList);
//        	}
//        }
//        List<ResBizVm> bizVmTemp = new ArrayList<ResBizVm>(bizVms);
//        //分大业务的sheet
//        List<List<Map>> exceldata = new ArrayList<List<Map>>();
//        List<Map> dataList = null;
//		Map<String, Object> dataMap = null;
//		List<Map> totaldataList = new ArrayList<Map>();
//		Map<String, Object> totaldataMap = null;
//        Criteria criteria = new Criteria();
//        criteria.setOrderByClause("A.BIZ_SID");
//        List<Biz> bizs = businesstypeService.selectFbizByParams(criteria);
//        String bizNames = "";
//        int bizTotal = 0;
//        int vmTotal = 0;
//        Integer cpuTotal = 0;
//        Long memTotal = 0L;
//        int diskTotal = 0;
//        int num = 0;
//        double io = 0;
//        double cpuUsed2 = 0;
//		double memUsed2 = 0;
//		double stUsed2 = 0;
//
//		for (Biz biz : bizs) {
//			dataList = new ArrayList<Map>();
//			criteria = new Criteria();
//			criteria.put("parentBizSid", biz.getBizSid());
//			criteria.setOrderByClause("A.BIZ_SID");
//			List<Biz> sbiz = businesstypeService.selectByParams2(criteria);
//			ResBizVm list = this.resBizVmService.statisticalBizOfVM(biz.getBizSid());
//
//			double cpuUsedTotal = 0;
//    		double memUsedTotal = 0;
//    		double stUsedTotal = 0;
//			double totalIo = 0;
//
//			totaldataMap = new HashMap<String, Object>();
//			totaldataMap.put("parentName", biz.getName());
//			totaldataMap.put("bizCount", sbiz.size());
//			bizTotal = bizTotal + sbiz.size();
//			totaldataMap.put("vmCount", list.getStaTotalVm());
//			vmTotal = vmTotal + list.getStaTotalVm();
//			totaldataMap.put("cpuCore", list.getCpuCores()==null?0:list.getCpuCores());
//			cpuTotal = cpuTotal + (list.getCpuCores()==null?0:list.getCpuCores());
//			totaldataMap.put("memSize", list.getMemorySize()==null?0:list.getMemorySize());
//			memTotal = memTotal + (list.getMemorySize()==null?0:list.getMemorySize());
//			totaldataMap.put("storage", new Integer(list.getStaVmDisk())==null?0:list.getStaVmDisk());
//			diskTotal = diskTotal + (new Integer(list.getStaVmDisk())==null?0:list.getStaVmDisk());
//			//得到记录
//			for (ResBizVm vm : bizVms) {
//				//首先属于这个大业务
//				if(vm.getParentMgtObjSid().equals(biz.getBizSid())){
//					//其次按照小业务统计
//					boolean flag = false;
//					if(!CollectionUtils.isEmpty(dataList)){
//						for (Map<String,Object> data : dataList) {
//							if(data.get("bizSid").equals(vm.getMgtObjSid())){
//								flag = true;
//								data.put("cpuCore", (Integer.parseInt(data.get("cpuCore").toString())+vm.getCpuCores())+"");
//								Double cpuUsed = Double.parseDouble(data.get("cpuUsedTotal").toString())+(vm.getCpuCores()*Double.parseDouble(vm.getCpuUsage()==null?"0":vm.getCpuUsage()));
//								data.put("cpuUsage", (BigDecimal.valueOf(cpuUsed).divide(
//										BigDecimal.valueOf(Integer.parseInt(data.get("cpuCore").toString())+vm.getCpuCores()),
//										2, BigDecimal.ROUND_HALF_UP))+"");
//								data.put("cpuUsedTotal", cpuUsed+"");
//
//								Double mem = BigDecimal.valueOf(vm.getMemorySize()).divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
//								data.put("memSize", (Double.parseDouble(data.get("memSize").toString())+mem)+"");
//								Double memUsed = (Double.parseDouble(data.get("memUsedTotal").toString())+(mem*Double.parseDouble(vm.getMemUsage()==null?"0":vm.getMemUsage())));
//								data.put("memUsage", (BigDecimal.valueOf(memUsed).divide(
//										BigDecimal.valueOf(Double.parseDouble(data.get("memSize").toString())+mem),
//										2, BigDecimal.ROUND_HALF_UP))+"");
//								data.put("memUsedTotal", memUsed+"");
//
//								data.put("storage", (Double.parseDouble(data.get("storage").toString())+vm.getStorage())+"");
//								Double stUsed = (Double.parseDouble(data.get("stUsedTotal").toString())+(vm.getStorage()*Double.parseDouble(vm.getDiskUsage()==null?"0":vm.getDiskUsage())));
//								data.put("storage", (BigDecimal.valueOf(stUsed).divide(
//										BigDecimal.valueOf(Double.parseDouble(data.get("storage").toString())+vm.getStorage()),
//										2, BigDecimal.ROUND_HALF_UP))+"");
//								data.put("stUsedTotal", stUsed+"");
//								data.put("ioTotal",(Double.parseDouble(data.get("ioTotal").toString())+vm.getNetWorkAvg())+"");
//							}
//						}
//					}
//					if(!flag){
//						dataMap = new HashMap<String, Object>();
//						dataMap.put("cpuCore", vm.getCpuCores()+"");
//						dataMap.put("cpuUsage", vm.getCpuUsage()==null?"0":vm.getCpuUsage());
//						dataMap.put("cpuUsedTotal", (vm.getCpuCores()*Double.parseDouble(vm.getCpuUsage()==null?"0":vm.getCpuUsage()))+"");
//						Double mem = BigDecimal.valueOf(vm.getMemorySize()).divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
//						dataMap.put("memSize", mem+"");
//						dataMap.put("memUsage", vm.getMemUsage()==null?"0":vm.getMemUsage());
//						dataMap.put("memUsedTotal", (mem*Double.parseDouble(vm.getMemUsage()==null?"0":vm.getMemUsage()))+"");
//						dataMap.put("storage", vm.getStorage()+"");
//						dataMap.put("stUsage", vm.getDiskUsage()==null?"0":vm.getDiskUsage());
//						dataMap.put("stUsedTotal", (vm.getStorage()*Double.parseDouble(vm.getDiskUsage()==null?"0":vm.getDiskUsage()))+"");
//						dataMap.put("ioTotal",vm.getNetWorkAvg()+"");
//						dataMap.put("bizName", vm.getName());
//						dataMap.put("bizSid",vm.getMgtObjSid());
//
//						dataMap.put("num",num+1);
//						dataList.add(dataMap);
//						num++;
//					}
//					//最后将已处理的移除
//					cpuUsedTotal = cpuUsedTotal + (vm.getCpuCores()*Double.parseDouble(vm.getCpuUsage()==null?"0":vm.getCpuUsage()));
//					Double mem = BigDecimal.valueOf(vm.getMemorySize()).divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
//					memUsedTotal = memUsedTotal + (mem*Double.parseDouble(vm.getMemUsage()==null?"0":vm.getMemUsage()));
//					stUsedTotal = stUsedTotal + (vm.getStorage()*Double.parseDouble(vm.getDiskUsage()==null?"0":vm.getDiskUsage()));
//					totalIo = totalIo + ((Double)vm.getNetWorkAvg()==null?0:vm.getNetWorkAvg());
//					bizVmTemp.remove(vm);
//				}
//			}
//			bizVms = new ArrayList<ResBizVm>(bizVmTemp);
//			totaldataMap.put("ioTotal", totalIo);
//			if(cpuUsedTotal==0.0){
//				totaldataMap.put("cpuUsage",0.0);
//			}else{
//				totaldataMap.put("cpuUsage", BigDecimal.valueOf(cpuUsedTotal).divide(
//						BigDecimal.valueOf(list.getCpuCores()),2, BigDecimal.ROUND_HALF_UP));
//			}
//			if(memUsedTotal==0.0){
//				totaldataMap.put("memUsage",0.0);
//			}else{
//				totaldataMap.put("memUsage", BigDecimal.valueOf(memUsedTotal).divide(
//						BigDecimal.valueOf(list.getMemorySize()),2, BigDecimal.ROUND_HALF_UP));
//			}
//			if(stUsedTotal==0.0){
//				totaldataMap.put("stUsage",0.0);
//			}else{
//				totaldataMap.put("stUsage", BigDecimal.valueOf(stUsedTotal).divide(
//						BigDecimal.valueOf(list.getStaVmDisk()),2, BigDecimal.ROUND_HALF_UP));
//			}
//			if(dataList.size()>0){
//				bizNames = bizNames + biz.getName()+",";
//				headers.add(head);
//				fileds.add(headerField);
//				cellwidth.add(widths);
//				types.add(type);
//				exceldata.add(dataList);
//			}
//			totaldataList.add(totaldataMap);
//			cpuUsed2 = cpuUsed2 + cpuUsedTotal;
//			memUsed2 = memUsed2 + memUsedTotal;
//			stUsed2 = stUsed2 + stUsedTotal;
//			io = io + totalIo;
//		}
//    	totaldataMap = new HashMap<String, Object>();
//		totaldataMap.put("parentName", "总计");
//		totaldataMap.put("bizCount", bizTotal);
//		totaldataMap.put("vmCount", vmTotal);
//		totaldataMap.put("cpuCore", cpuTotal);
//		totaldataMap.put("cpuUsage", BigDecimal.valueOf(cpuUsed2).divide(
//				BigDecimal.valueOf(cpuTotal),2, BigDecimal.ROUND_HALF_UP));
//		totaldataMap.put("memSize", memTotal);
//		totaldataMap.put("memUsage",  BigDecimal.valueOf(memUsed2).divide(
//				BigDecimal.valueOf(memTotal),2, BigDecimal.ROUND_HALF_UP));
//		totaldataMap.put("storage", diskTotal);
//		totaldataMap.put("stUsage",  BigDecimal.valueOf(stUsed2).divide(
//				BigDecimal.valueOf(diskTotal),2, BigDecimal.ROUND_HALF_UP));
//		totaldataMap.put("ioTotal", io);
//		totaldataList.add(totaldataMap);
//    	exceldata.add(0, totaldataList);
//    	headers.add(0,totalhead);
//    	fileds.add(0, totalheaderField);
//    	cellwidth.add(0, totalwidths);
//    	types.add(0, totaltype);
//    	bizNames = "汇总,"+bizNames;
//    	if(bizNames.length()>0){
//    		bizNames = bizNames.substring(0, bizNames.length()-1);
//    	}
//    	String[] sheetName = bizNames.split(",");
//		// 表的头部
//
//		String date = StringUtil.dateFormat(new Date(), StringUtil.DF_YMD);
//		String systemName = com.hptsic.cloud.common.utils.PropertiesUtil.getProperty("system.name");
//		String fileName = ""+systemName+"-业务管理-业务云监控报表-"+date+".xls";
//		//7.调用通用方法，生成excel文件
//		POIUtil excelUtil = new POIUtil();
//		excelUtil.doFromDataListToExecl(servletResponse, headers, fileds, cellwidth, exceldata, sheetName, fileName,types);
		return null;
	}

	@Override
	@WebMethod
	@POST
	@Path("/findMgtObjResCount")
	public Response findMgtObjResCount(String params) {
		Map<String,Map<String,String>> resultMap = new HashMap<String, Map<String,String>>();
		try {
			// 查询退订中、已开通
			String statusParams = "'"
					+ WebConstants.ServiceInstanceStatus.CANCELING + "','"
					+ WebConstants.ServiceInstanceStatus.CHANGEING + "','"
					+ WebConstants.ServiceInstanceStatus.REFUSED + "','"
					+ WebConstants.ServiceInstanceStatus.OPENED
					+ "'".replace(" ", "");
			
			Criteria param = new Criteria();
			Criteria param2 = new Criteria();
			
			int sum = 0;
			StringBuffer resCountJson = new StringBuffer();
			StringBuffer aixCountJson = new StringBuffer();
			StringBuffer winCountJson = new StringBuffer();
			StringBuffer stCountJson = new StringBuffer();
			
			//项目存储使用数据
			Long stResSum = 0L;
			
			Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
			Criteria condition = new Criteria();
			if(map.get("mgtObjSid")!=null){
				//判断选择的是不是最上层目录，及昆仑银行
				MgtObj mgtObj = mgtObjService.selectByPrimaryKey(Long.parseLong(map.get("mgtObjSid").toString()));
				if(mgtObj!=null&&mgtObj.getLevel().equals(0L)){
					condition.put("parentIds", map.get("mgtObjSid"));
				}else if(mgtObj!=null&&mgtObj.getLevel().equals(-1L)){
					//查询所有的项目
					condition.put("level", 1L);
					condition.put("status", "02");
				}else{
					condition.put("mgtObjSid", map.get("mgtObjSid"));
				}
			}
			List<MgtObj> mgtObjs = mgtObjService.selectBaseFileByParams(condition);
			if(!CollectionUtils.isEmpty(mgtObjs)){
				//获得总的项目虚机数据
				for (int i=0;i<mgtObjs.size();i++) {
					param.put("mgtObjSid", mgtObjs.get(i).getMgtObjSid());
					param.put("statusParams", statusParams);
					param.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
					List<ServiceInstance> instanceVmList = this.instanceService.selectInstVmByParams(param);
					
					param2.put("mgtObjSid", mgtObjs.get(i).getMgtObjSid());
					param2.put("statusParams", statusParams);
					param2.put("serviceSid", WebConstants.ServiceSid.SERVICE_PM);
					List<ServiceInstance> instancceHostList = this.instanceService.selectInstHostByParams(param2);
					
					int resCount = (instanceVmList==null?0:instanceVmList.size())+(instancceHostList==null?0:instancceHostList.size());
					sum = sum + resCount;
					if(resCount!=0){
						if(i==mgtObjs.size()-1){
							resCountJson.append("['"+mgtObjs.get(i).getName()+"',"+resCount+"]");
						}else{
							resCountJson.append("['"+mgtObjs.get(i).getName()+"',"+resCount+"],");
						}
					}

					//得到对应的存储
					Long  resVd = 0L;
					if(!CollectionUtils.isEmpty(instancceHostList)){
						for (ServiceInstance si : instancceHostList) {
							ResStorage st = resStorageService.statisticalHostOfStorageVolume(si.getResSid());
							if(st!=null){
								resVd = resVd + (st.getTotalCapacity()==null?0L:st.getTotalCapacity());
							}
						}
					}
					if(!CollectionUtils.isEmpty(instanceVmList)){
						for (ServiceInstance si : instanceVmList) {
							Criteria cond = new Criteria();
							cond.put("resVmSid", si.getResSid());
							List<ResVd> vds = resVdService.selectVdResSum(cond);
							if(!CollectionUtils.isEmpty(vds)){
								resVd = resVd + (vds.get(0).getAllocateDiskSize()==null?0L:vds.get(0).getAllocateDiskSize());
							}
						}
					}
					stResSum = stResSum + resVd;
					if(i==mgtObjs.size()-1){
						if((resVd!=null&&resVd!=0)){
							stCountJson.append("['"+mgtObjs.get(i).getName()+"',"+resVd+"]");
						}
					}else{
						if((resVd!=null&&resVd!=0)){
							stCountJson.append("['"+mgtObjs.get(i).getName()+"',"+resVd+"],");
						}
					}
					
				}
				resCountJson.insert(0, '[');
				resCountJson.append(']');
				Map<String,String> totalMap = new HashMap<String, String>();
				totalMap.put("sumNum", sum+"");
				totalMap.put("chartData", resCountJson.toString());
				resultMap.put("total", totalMap);
				
				//获取AIX和X86的各自项目资源数据
				int aixResSum = 0;
				int winResSum = 0;
				
				for (int i=0;i<mgtObjs.size();i++) {
					int aixCount=0;
					int winCount=0;
					param.put("mgtObjSid", mgtObjs.get(i).getMgtObjSid());
					param.put("statusParams", statusParams);
					param.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
					List<ServiceInstance> instanceVmList = this.instanceService.countInstVmByParams(param);
					if(!CollectionUtils.isEmpty(instanceVmList)){
						for (ServiceInstance si : instanceVmList) {
							if(WebConstants.OS_TYPE.LINUX.equals(si.getOsTypeName())||WebConstants.OS_TYPE.WINDOWS.equals(si.getOsTypeName())){
								winCount = winCount + si.getInstanceCount();
							}else if(WebConstants.OS_TYPE.AIX.equals(si.getOsTypeName())){
								aixCount = aixCount + si.getInstanceCount();
							}
						}
					}
					
					param2.put("mgtObjSid", mgtObjs.get(i).getMgtObjSid());
					param2.put("statusParams", statusParams);
					param2.put("serviceSid", WebConstants.ServiceSid.SERVICE_PM);
					List<ServiceInstance> instancceHostList = this.instanceService.countInstHostByParams(param2);
					if(!CollectionUtils.isEmpty(instancceHostList)){
						for (ServiceInstance si : instancceHostList) {
							if(WebConstants.OS_TYPE.LINUX.equals(si.getOsTypeName())||WebConstants.OS_TYPE.WINDOWS.equals(si.getOsTypeName())){
								winCount = winCount + si.getInstanceCount();
							}else if(WebConstants.OS_TYPE.AIX.equals(si.getOsTypeName())){
								winCount = winCount + si.getInstanceCount();
							}
						}
					}
					aixResSum = aixResSum + aixCount;
					winResSum = winResSum + winCount;
					
					if(i==mgtObjs.size()-1){
						if(aixCount!=0){
							aixCountJson.append("['"+mgtObjs.get(i).getName()+"',"+0+"]");
						}
						if(winCount!=0){
							winCountJson.append("['"+mgtObjs.get(i).getName()+"',"+0+"]");
						}
					}else{
						if(aixCount!=0){
							aixCountJson.append("['"+mgtObjs.get(i).getName()+"',"+0+"],");
						}
						if(winCount!=0){
							winCountJson.append("['"+mgtObjs.get(i).getName()+"',"+0+"],");
						}
					}
				}
				aixCountJson.insert(0, '[');
				aixCountJson.append(']');
				winCountJson.insert(0, '[');
				winCountJson.append(']');
				Map<String,String> aixMap = new HashMap<String, String>();
				aixMap.put("sumNum", aixResSum+"");
				aixMap.put("chartData", aixCountJson.toString());
				resultMap.put("aix", aixMap);
				Map<String,String> winMap = new HashMap<String, String>();
				winMap.put("sumNum", winResSum+"");
				winMap.put("chartData", winCountJson.toString());
				resultMap.put("win", winMap);
				
				
//				for (int i=0;i<mgtObjs.size();i++) {
//					param.put("mgtObjSid", mgtObjs.get(i).getMgtObjSid());
//					param.put("statusParams", statusParams);
//					param.put("resType", "RES-VD");
//					Integer vdCount = this.instanceService.countMgtObjInstVdByParams(param);
//					
//					param2.put("mgtObjSid", mgtObjs.get(i).getMgtObjSid());
//					param2.put("statusParams", statusParams);
//					param2.put("resType", "RES-STORSGE");
//					Integer stCount = this.instanceService.countMgtObjInstVdByParams(param);
//					
//					stResSum = stResSum + (vdCount==null?0:vdCount) + (stCount==null?0:stCount);
//					if((vdCount==null?0:vdCount) + (stCount==null?0:stCount)!=0){
//						if(i==mgtObjs.size()-1){
//							stCountJson.append("['"+mgtObjs.get(i).getName()+"',"+((vdCount==null?0:vdCount) + (stCount==null?0:stCount))+"]");
//						}else{
//							stCountJson.append("['"+mgtObjs.get(i).getName()+"',"+((vdCount==null?0:vdCount) + (stCount==null?0:stCount))+"],");
//						}
//					}
//				}
				stCountJson.insert(0, '[');
				stCountJson.append(']');
				Map<String,String> stMap = new HashMap<String, String>();
				stMap.put("sumNum", stResSum+"");
				stMap.put("chartData", stCountJson.toString());
				resultMap.put("st", stMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = "";
		result = JsonUtil.toJson(new RestResult(resultMap));
		return Response.status(Status.OK).entity(result).build();
	}

}
