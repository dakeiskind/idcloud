//package com.hptsic.cloud.service.service.impl;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.hptsic.cloud.alarm.pojo.AlarmData;
//import com.hptsic.cloud.alarm.service.AlarmDataService;
//import com.hptsic.cloud.common.constant.WebConstants;
//import com.hptsic.cloud.common.exception.ApplicationException;
//import com.hptsic.cloud.common.pojo.Criteria;
//import com.hptsic.cloud.common.pojo.RESTHttpResponse;
//import com.hptsic.cloud.common.utils.AuthUtil;
//import com.hptsic.cloud.common.utils.JsonUtil;
//import com.hptsic.cloud.common.utils.PropertiesUtil;
//import com.hptsic.cloud.common.utils.RSETClientUtil;
//import com.hptsic.cloud.common.utils.StringUtil;
//import com.hptsic.cloud.common.utils.WebUtil;
//import com.hptsic.cloud.resource.dao.ResourceTopologyMapper;
//import com.hptsic.cloud.resource.pojo.ResourceHost;
//import com.hptsic.cloud.resource.pojo.ResourceInstanceVm;
//import com.hptsic.cloud.resource.pojo.ResourceTopology;
//import com.hptsic.cloud.resource.pojo.ResourceTopologyOrg;
//import com.hptsic.cloud.resource.pojo.ResourceTopologyVc;
//import com.hptsic.cloud.resource.pojo.ResourceTopologyVe;
//import com.hptsic.cloud.resource.service.ResourceHostService;
//import com.hptsic.cloud.resource.service.ResourceTopologyOrgService;
//import com.hptsic.cloud.resource.service.ResourceTopologyVcService;
//import com.hptsic.cloud.resource.service.ResourceTopologyVeService;
//import com.hptsic.cloud.service.service.ServiceTopologyService;
//import com.hptsic.cloud.sys.pojo.User;
//
//@Service
//public class ServiceTopologyServiceImpl implements ServiceTopologyService {
//	
//	@Autowired
//    private ResourceTopologyMapper resourceTopologyMapper;
//	
//	/**
//	 * 主机Service
//	 */
//	@Autowired
//	private ResourceHostService resourceHostService;
//	
//	/**
//	 * 虚拟化环境管理表Service
//	 */
//	@Autowired
//	private ResourceTopologyVeService resourceTopologyVeService;
//	
//	/**
//	 * 拓扑与组织关联表Service
//	 */
//	@Autowired
//	private ResourceTopologyOrgService resourceTopologyOrgService;
//	
//	
//	/**
//	 * 集群管理表Service
//	 */
//	@Autowired
//	private ResourceTopologyVcService resourceTopologyVcService;
//	
//	/**
//	 * 报警Service
//	 */
//	@Autowired
//	private AlarmDataService alarmDataService;
//	
//	@Override
//	public List<ResourceTopology> selectByParams(Criteria example) {
//		
//		return this.resourceTopologyMapper.selectByParams(example);
//	}
//
//	@Override
//	public int updateByPrimaryKeySelective(ResourceTopology record) {
//		// 判断新增Topology的类型
//		int result = 0;
//		
//		// 判断Topology的类型
//		if("03".equals(record.getResTopologyType())){
//			// 插入虚拟化环境关联表
//			ResourceTopologyVe rte = new ResourceTopologyVe();
//			rte.setResTopologySid(record.getResTopologySid());
//			rte.setVirtualPlatformType(record.getVirtualPlatformType());
//			rte.setVirtualPlatformVersion(record.getVirtualPlatformVersion());
//			rte.setManagementAccount(record.getManagementAccount());
//			rte.setManagementUrl(record.getManagementUrl());
//			rte.setManagementPassword(record.getManagementPassword());
//			rte.setUpdateCycle(record.getUpdateCycle());
//			rte.setMqTopic(record.getMqTopic());
//			int i = this.resourceTopologyVeService.updateByPrimaryKeySelective(rte);
//			result = this.resourceTopologyMapper.updateByPrimaryKeySelective(record);
//			
//		}else if("ha".equals(record.getResTopologyType())){
//			// 打开HA功能
//			ResourceTopologyVc rtc = new ResourceTopologyVc();
//			rtc.setResTopologySid(record.getResTopologySid());
//			rtc.setOpenHa(record.getOpenHA());
//			result = this.resourceTopologyVcService.updateByPrimaryKeySelective(rtc);
//		}else{
//			result = this.resourceTopologyMapper.updateByPrimaryKeySelective(record);
//		}
//		
//		return result;
//	}
//
//	@Override
//	public ResourceTopology selectByPrimaryKey(Long resTopologySid) {
//		
//		return this.resourceTopologyMapper.selectByPrimaryKey(resTopologySid);
//	}
//
//	@Override
//	public int insertSelective(ResourceTopology record) {
//		// 判断新增Topology的类型
//		int result = 0;
//		if("03".equals(record.getResTopologyType())){
//			// 虚拟化环境
//			this.resourceTopologyMapper.insertSelective(record);
//			// 插入集群关联表
//			ResourceTopologyVe rte = new ResourceTopologyVe();
//			rte.setResTopologySid(record.getResTopologySid());
//			rte.setVirtualPlatformType(record.getVirtualPlatformType());
//			rte.setVirtualPlatformVersion(record.getVirtualPlatformVersion());
//			rte.setManagementAccount(record.getManagementAccount());
//			rte.setManagementUrl(record.getManagementUrl());
//			rte.setManagementPassword(record.getManagementPassword());
//			rte.setUpdateCycle(record.getUpdateCycle());
//			rte.setMqTopic(record.getMqTopic());
//			
//			result = this.resourceTopologyVeService.insertSelective(rte);
//			
//		}else if("04".equals(record.getResTopologyType())){
//			// 集群
//			this.resourceTopologyMapper.insertSelective(record);
//			// 插入集群关联表
//			ResourceTopologyVe rtv = this.resourceTopologyVeService.selectByPrimaryKey(record.getParentTopologySid());
//			ResourceTopologyVc rtc = new ResourceTopologyVc();
//			rtc.setVirtualPlatformType(rtv.getVirtualPlatformType());
//			rtc.setResTopologySid(record.getResTopologySid());
//			result = this.resourceTopologyVcService.insertSelective(rtc);
//		}else{
//			result = this.resourceTopologyMapper.insertSelective(record);
//		}
//		// 新增Topology_org管理表
//		ResourceTopologyOrg rto = new ResourceTopologyOrg(); 
//		User user = AuthUtil.getAuthUser();
//		rto.setOrgSid(user.getOrgSid());
//		rto.setResTopologySid(record.getResTopologySid());
//		this.resourceTopologyOrgService.insertSelective(rto);
//		
//		return result;
//	}
//
//	@Override
//	public ResourceTopology selectTopologyParentNode(Long topologyParentSid) {
//		
//		return this.resourceTopologyMapper.selectTopologyParentNode(topologyParentSid);
//	}
//
//	@Override
//	public int deleteByPrimaryKey(Long resTopologySid) {
//		// 判断删除的Topology是虚拟化环境或者集群
//		ResourceTopology topology = this.resourceTopologyMapper.selectByPrimaryKey(resTopologySid);
//		if("03".equals(topology.getResTopologyType())){
//			// 虚拟化环境
//			this.resourceTopologyVeService.deleteByPrimaryKey(resTopologySid);
//		}else if("04".equals(topology.getResTopologyType())){
//			// 集群
//			this.resourceTopologyVcService.deleteByPrimaryKey(resTopologySid);
//		}
//		// 删除Topology_org关联表
//		Criteria example = new Criteria();
//		example.put("resTopologySid", resTopologySid);
//		List<ResourceTopologyOrg> list = this.resourceTopologyOrgService.selectByParams(example);
//		// 判断该Topology节点是否与组织存在关联
//		if(list.size() > 0){
//			// 删除所有与该Topology节点相关的数据
//			this.resourceTopologyOrgService.deleteByParams(example);
//		}
//		
//		return this.resourceTopologyMapper.deleteByPrimaryKey(resTopologySid);
//	}
//
//	@Override
//	public int selectTopologyChildCount(Long resTopologySid) {
//		return this.resourceTopologyMapper.selectTopologyChildCount(resTopologySid);
//	}
//
//	/**
//	 * 获取主机报警信息
//	 */
//	@Override
//	public List<AlarmData> getAlarms(String searchType, String searchSid) throws Exception {
//		
//		List<Map<String,Object>> alarmList=new ArrayList<Map<String,Object>>();
//		if ("host".equals(searchType)) {
//			String url = PropertiesUtil.getProperty("wmware.host.url");
//			// 调南向接口获取主机下虚机
//			ResourceHost rh = this.resourceHostService.selectByPrimaryKey(Long.parseLong(searchSid));
//			url += "/"+rh.getHostName()+"/alarmlist";
//			RESTHttpResponse resourceInstanceResponse = RSETClientUtil.get(url);
//			if (RESTHttpResponse.SUCCESS.equals(resourceInstanceResponse.getStatus())) {
//				alarmList=JsonUtil.fromJson(resourceInstanceResponse.getContent(),List.class);
//			} else {
//				throw new ApplicationException();
//			}
//
//		} else {
//
//			// 如果为数据中心、集群、资源池时
//			Criteria example1 = new Criteria();
//			if (searchType.equals("pool")) {
//				example1.put("resPoolSid", searchSid);
//			} else {
//				example1.put("resTopologySid", searchSid);
//			}
//			List<ResourceHost> list = this.resourceHostService.selectByParams(example1);
//			if (!list.isEmpty()) {
//				for (ResourceHost rh : list) {
//					String url = PropertiesUtil.getProperty("wmware.host.url");
//					url += "/"+rh.getHostName()+"/alarmlist";
//					RESTHttpResponse resourceInstanceResponse = RSETClientUtil.get(url);
//					if (RESTHttpResponse.SUCCESS.equals(resourceInstanceResponse.getStatus())) {
//						 alarmList=JsonUtil.fromJson(resourceInstanceResponse.getContent(),List.class);
//					} else {
//						throw new ApplicationException();
//					}
//				}
//			}
//
//		}
//		
//		// 放置未被纳入的报警信息
//		List<Map<String, Object>> unManagedalarmDataList = new ArrayList<Map<String, Object>>();
//		
//		//查询已有报警信息
//		Criteria criteria=new Criteria();
//		List<AlarmData> alarmDataList=this.alarmDataService.selectByParams(criteria);
//		
//		//比对信息，插入数据库中没有的报警信息
//		
//		if(!alarmList.isEmpty()){
//			
//			for(Map<String,Object> map:alarmList){
//				boolean isExit = false;
//				if (!alarmDataList.isEmpty()) {
//					// 对比，过滤出未纳入管理的虚机
//					for (AlarmData ad : alarmDataList) {
//						if (ad.getTitle().equals(String.valueOf(map.get("name")))) {
//							isExit = true;
//							break;
//						}
//					}
//					if (!isExit) {
//						unManagedalarmDataList.add(map);
//					}
//				} else {
//					unManagedalarmDataList.addAll(alarmList);
//				}
//			}
//		}
//		
//		boolean insertResult=true;
//		if(!unManagedalarmDataList.isEmpty()){
//			for(Map<String,Object> unMap:unManagedalarmDataList){
//				
//				AlarmData alarmData=new AlarmData();
//				if(!StringUtil.isNullOrEmpty(unMap.get("name"))){
//					alarmData.setTitle(unMap.get("name").toString());
//				}
//				if(!StringUtil.isNullOrEmpty(unMap.get("description"))){
//					alarmData.setContent(unMap.get("description").toString());
//				}
//				if(!StringUtil.isNullOrEmpty(unMap.get("target"))){
//					alarmData.setAlarmTarget(unMap.get("target").toString());
//				}
//				if(!StringUtil.isNullOrEmpty(unMap.get("triggeredTime"))){
////					alarmData.setAlarmTime(StringUtil.strToDate(unMap.get("triggeredTime").toString()));
//					alarmData.setAlarmTime(new Date());
//				}
//				if(!StringUtil.isNullOrEmpty(unMap.get("status"))){
//					if("yellow".equals(unMap.get("status").toString())){
//						alarmData.setAlarmLevel("04");
//					}else if("red".equals(unMap.get("status").toString())){
//						alarmData.setAlarmLevel("05");
//					}
//				}
//				alarmData.setStatus(WebConstants.AlarmStatus.UNTREATED);
//				alarmData.setAlarmType("02");
//				WebUtil.prepareInsertParams(alarmData);
//				int alarmResult=this.alarmDataService.insertSelective(alarmData);
//				if(alarmResult>0){
//					insertResult=true;
//				}else{
//					insertResult=false;
//					
//				}
//			}
//		}
//		//插入成功后，重新查询报警信息
//		List<AlarmData> hostAlarmDataList =new ArrayList<AlarmData>();
//		List<AlarmData> allAlarmDataList =new ArrayList<AlarmData>();
//		if(insertResult){
//			if ("host".equals(searchType)) {
//				ResourceHost rh = this.resourceHostService.selectByPrimaryKey(Long.parseLong(searchSid));
//				Criteria criteria2=new Criteria();
//				criteria2.put("alarmTarget", rh.getHostName());
//				allAlarmDataList=this.alarmDataService.selectByParams(criteria2);
//			} else {
//
//				// 如果为数据中心、集群、资源池时
//				Criteria example1 = new Criteria();
//				if (searchType.equals("pool")) {
//					example1.put("resPoolSid", searchSid);
//				} else {
//					example1.put("resTopologySid", searchSid);
//				}
//				List<ResourceHost> list = this.resourceHostService.selectByParams(example1);
//				if (!list.isEmpty()) {
//					for (ResourceHost rh : list) {
//						Criteria criteria2=new Criteria();
//						criteria2.put("alarmTarget", rh.getHostName());
//						hostAlarmDataList=this.alarmDataService.selectByParams(criteria2);
//						allAlarmDataList.addAll(hostAlarmDataList);
//					}
//				}
//
//			}
//		}else{
//			throw new ApplicationException();
//		}
//		return allAlarmDataList;
//	}
//
//	@Override
//	public List<ResourceTopology> selectTopologyByParams(Criteria example) {
//		return this.resourceTopologyMapper.selectTopologyByParams(example);
//	}
//
//	@Override
//	public List<ResourceTopology> selectPoolTopologyByParams(Criteria example) {
//		
//		return this.resourceTopologyMapper.selectPoolTopologyByParams(example);
//	}
//}
