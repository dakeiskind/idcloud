package com.h3c.idcloud.core.service.system.provider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.AlarmDataMapper;
import com.h3c.idcloud.core.pojo.dto.system.AlarmData;
import com.h3c.idcloud.core.service.system.api.AlarmDataService;
import com.h3c.idcloud.core.service.system.api.MailNotificationsService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import org.springframework.stereotype.Component;

/**
 * @author gujie
 */
@Service(version = "1.0.0")
@Component
public class AlarmDataServiceImpl implements AlarmDataService {

    /** 注入Mapper */
    @Autowired
    private AlarmDataMapper alarmDataMapper;

    /**  */
//	@Reference(version = "1.0.0")
//    private AlarmHandler alarmHandler;

    /** 邮件发送Service */
    @Reference(version = "1.0.0")
    private MailNotificationsService mailNotificationsService;

    /** 日志Log */
    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmDataServiceImpl.class);

    @Override
    public int countByParams(Criteria example) {
        int count = this.alarmDataMapper.countByParams(example);
        LOGGER.debug("count: {}", count);
        return count;
    }

    @Override
    public AlarmData selectByPrimaryKey(Long alarmDataSid) {
        return this.alarmDataMapper.selectByPrimaryKey(alarmDataSid);
    }

    @Override
    public List<AlarmData> selectByParams(Criteria example) {
        return this.alarmDataMapper.selectByParams(example);
    }

    @Override
    public int deleteByPrimaryKey(Long alarmDataSid) {
        return this.alarmDataMapper.deleteByPrimaryKey(alarmDataSid);
    }

    @Override
    public int updateByPrimaryKeySelective(AlarmData record) {
        return this.alarmDataMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AlarmData record) {
        return this.alarmDataMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteByParams(Criteria example) {
        return this.alarmDataMapper.deleteByParams(example);
    }

    @Override
    public int updateByParamsSelective(AlarmData record, Criteria example) {
        return this.alarmDataMapper.updateByParamsSelective(record, example.getCondition());
    }

    @Override
    public int updateByParams(AlarmData record, Criteria example) {
        return this.alarmDataMapper.updateByParams(record, example.getCondition());
    }

    @Override
    public int insert(AlarmData record) {
        return this.alarmDataMapper.insert(record);
    }

    @Override
    public int insertSelective(AlarmData record) {
        return this.alarmDataMapper.insertSelective(record);
    }

//	@Override
//	public void syncAlarmInfo(Criteria t) {
//
//		logger.info("开始同步告警信息！");
//		EventListGet eventListGet = new EventListGet();
//	/*	eventListGet.setEventidFrom("15305");
//		eventListGet.setEventidTill("15305");*/
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		try {
//			eventListGet.setTimeFrom(getBeforeSevenDayTime());
//		} catch (ParseException e1) {
//			e1.printStackTrace();
//		}
//		eventListGet.setTimeTill(sdf.format(new Date()));
//
//		eventListGet.setRetrieveAll(true);
//		eventListGet.setProviderType("opennms");
//		try {
//			List<Alarm> eventList = alarmHandler.getAlarmList(eventListGet);
//
//			// HMC和IVM
////    		String resVeSid = PropertiesUtil.getProperty("kvm.resve");
////    		ResVe resVe = this.resVeMapper.selectByPrimaryKey(resVeSid);
////        	TenantCreate tenantCreate = new TenantCreate();
////        	tenantCreate.setName(Long.toString(record.getMgtObjSid()));
////        	tenantCreate.setDescription(record.getDescription());
////
////        	tenantCreate.setTenantName(StringUtil.nullToEmpty(record.getMgtObjSid()));
////        	tenantCreate.setProviderType(resVe.getVirtualPlatformType());
////
////        	tenantCreate.setVirtEnvType(resVe.getVirtualPlatformType());
////        	tenantCreate.setVirtEnvUuid(resVe.getResTopologySid());
//
//
//			Criteria example = new Criteria();
//			// 查询出数据库中所有的
//			List<AlarmData> alaList = this.alarmDataMapper.selectByParams(example);
//			List<AlarmData> alarmData_list = new ArrayList<AlarmData>();
//			if(eventList != null && eventList.size() > 0){
//				if(alaList !=null && alaList.size() > 0){
//					for (Alarm ala : eventList) {
//						// 新增
//						boolean isok = true;
//						for(AlarmData alarmdata:alaList){
//							// 判断是否和以前的告警相同
//							if(ala.getId().equals(alarmdata.getAlarmId())){
//								isok = false;
//								// 编辑
//								try {
//									alarmdata.setAlarmTime(sdf.parse(ala.getTime()));
//								} catch (java.text.ParseException e) {
//									e.printStackTrace();
//								}
//								alarmdata.setAlarmLevel(ala.getPriority());
//								alarmdata.setAlarmType(ala.getType());
//								alarmdata.setTitle(ala.getTitle());
//								alarmdata.setContent(ala.getContent());
//								alarmdata.setAlarmTarget(ala.getNodeId());
//								WebUtil.prepareUpdateParams(alarmdata);
//								this.alarmDataMapper.updateByPrimaryKeySelective(alarmdata);
//								alarmData_list.add(alarmdata);
//							}
//						}
//
//						if(isok){
//							// 新增
//							AlarmData alaData = new AlarmData();
//							alaData.setAlarmId(ala.getId());
//							try {
//								alaData.setAlarmTime(sdf.parse(ala.getTime()));
//							} catch (java.text.ParseException e) {
//								e.printStackTrace();
//							}
//							alaData.setAlarmLevel(ala.getPriority());
//							alaData.setAlarmType(ala.getType());
//							alaData.setTitle(ala.getTitle());
//							alaData.setAlarmSource("监控平台");
//							alaData.setContent(ala.getContent());
//							alaData.setAlarmTarget(ala.getNodeId());
//							alaData.setStatus(WebConstants.AlarmStatus.UNTREATED);
//							WebUtil.prepareInsertParams(alaData);
//
//							this.alarmDataMapper.insertSelective(alaData);
//							alarmData_list.add(alaData);
//						}
//					}
//				}else{
//					// 数据库中告警信息为空，全部插入数据库
//					for (Alarm ala : eventList) {
//						AlarmData alaData = new AlarmData();
//						alaData.setAlarmId(ala.getId());
//						try {
//							alaData.setAlarmTime(sdf.parse(ala.getTime()));
//						} catch (java.text.ParseException e) {
//							e.printStackTrace();
//						}
//						alaData.setAlarmLevel(ala.getPriority());
//						alaData.setAlarmType(ala.getType());
//						alaData.setTitle(ala.getTitle());
//						alaData.setAlarmSource("监控平台");
//						alaData.setContent(ala.getContent());
//						alaData.setAlarmTarget(ala.getNodeId());
//						alaData.setStatus(WebConstants.AlarmStatus.UNTREATED);
//						WebUtil.prepareInsertParams(alaData);
//
//						this.alarmDataMapper.insertSelective(alaData);
//						//告警信息邮件
//						alarmData_list.add(alaData);
//
//					}
//				}
//				this.mailNotificationsService.alarmsInfoNotice(alarmData_list);
//			}else{
//				logger.info("没有同步告警信息！");
//			}
//		} catch (CommonAdapterException e) {
//			logger.debug("同步告警信息失败！");
//			e.printStackTrace();
//		} catch (AdapterUnavailableException e) {
//			logger.debug("同步告警信息失败！");
//			e.printStackTrace();
//		}
//		logger.info("完成告警信息同步！");
//	}

    @Override
    public List<AlarmData> countAlarmByLevel(Criteria example) {
        return this.alarmDataMapper.countAlarmByLevel(example);
    }

    /**
     * 获取前7天的时间
     * @return 返回前7天的时间
     * @throws ParseException 异常
     */
    public String getBeforeSevenDayTime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -7);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }

}