/**
 * 
 */
package com.h3c.idcloud.core.rest.system.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.system.Sysconfig;
import com.h3c.idcloud.core.rest.system.ConfigsRest;
import com.h3c.idcloud.core.service.system.api.SysconfigService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.InterfaceResult;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

/**
 * @author jipeigui
 * 
 */
@Component
public class ConfigsRestImpl implements ConfigsRest {

	Logger log = Logger.getLogger(this.getClass());

	/** 系统配置Service */
	@Reference(version = "1.0.0")
	private SysconfigService sysconfigService;

	/**
	 * 查询系统配置
	 * 
	 * @return
	 */
	@Override
	public Response findSysConfig(Sysconfig sysconfig) {

		Criteria criteria = new Criteria();
		if (!StringUtil.isNullOrEmpty(sysconfig.getConfigName())) {
			criteria.put("configNameLike", sysconfig.getConfigName());
		}
		if (!StringUtil.isNullOrEmpty(sysconfig.getConfigKey())) {
			criteria.put("configKeyLike", sysconfig.getConfigKey());
		}
		if (!StringUtil.isNullOrEmpty(sysconfig.getConfigType())) {
			criteria.put("configType", sysconfig.getConfigType());
		}
		criteria.setOrderByClause("CONFIG_NAME, CONFIG_SID");
		List<Sysconfig> list = sysconfigService.selectByParams(criteria);
		String json = JsonUtil.toJson(new RestResult(list));
		return Response.status(Status.OK).entity(json).build();
	}

	@Override
	public Response getSysValue(@QueryParam("configSid") Long configSid) {
		Sysconfig config = sysconfigService.selectByPrimaryKey(configSid);
		String json;
		if (config != null)
			json = JsonUtil.toJson(new RestResult(config));
		else
			json = "notexists";

		return Response.status(Status.OK).entity(json).build();
	}

	@Override
	public Response getProperty(@PathParam("name")String name) {

//		String ret = PropertiesUtil.getProperty(name);
//		if(ret ==null || "".equals(ret)) {
//			ret = "notexists";
//		}

		    Criteria criteria = new Criteria();
			criteria.put("configKey", name);
			List<Sysconfig> configs = sysconfigService.selectByParams(criteria);
		    String result = JsonUtil.toJson(new RestResult(configs));
		return Response.ok(result).build();
	}

	/**
	 * 修改系统配置
	 * 
	 * @return
	 */
	@Override
	public Response editSysConfig(Sysconfig sysconfig) {
		String json = "";
		WebUtil.prepareUpdateParams(sysconfig);
		int result = this.sysconfigService.updateByPrimaryKeySelective(sysconfig);
		if (1 == result) {
			json = InterfaceResult.setResult(WebConstants.ResultStatus.SUCCESS,
					null);
			return Response.status(Status.OK).entity(json).build();
		} else {
			json = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE,
					null);
			return Response.status(320).entity(json).build();
		}
	}

	@Override
	public Response getConfigType(Sysconfig sysconfig) {
		Criteria criteria = new Criteria();
		criteria.setOrderByClause("CONFIG_TYPE");
		List<Sysconfig> list = this.sysconfigService.selectConfigTypeByParams(criteria);
		if(list!=null&&list.size()!=0){
			for (Sysconfig config : list) {
				if(!StringUtil.isNullOrEmpty(config.getConfigType())){
					if(config.getConfigType().equals(WebConstants.ConfigType.INDEX_CHART)){
						config.setConfigTypeName("领导视图显示配置");
					}else if(config.getConfigType().equals(WebConstants.ConfigType.RES_CONFIG)){
						config.setConfigTypeName("首页资源配置");
					}else if(config.getConfigType().equals(WebConstants.ConfigType.EMAIL_ADDRESS)){
						config.setConfigTypeName("邮件地址配置");
					}else if(config.getConfigType().equals(WebConstants.ConfigType.OTHER)){
						config.setConfigTypeName("其他");
					}
				}
			}
		}
		String json = JsonUtil.toJson(new RestResult(list));
		return Response.status(Status.OK).entity(json).build();
	}

}
