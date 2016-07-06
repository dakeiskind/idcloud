package com.hptsic.cloud.monitor.util;

import com.hptsic.cloud.monitor.provision.exception.MonitorException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class BaseUtil {

	private static ObjectMapper MAPPER;
	private static Logger log = Logger.getLogger(BaseUtil.class);
	 static {
	        MAPPER = generateMapper(Inclusion.ALWAYS);
	    }
	 
	
	    private static ObjectMapper generateMapper(Inclusion inclusion) {

	        ObjectMapper customMapper = new ObjectMapper();

	        // 设置输出时包含属性的风格
	        customMapper.setSerializationInclusion(inclusion);

	        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
	        customMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	        // 禁止使用int代表Enum的order()來反序列化Enum,非常危險
	        customMapper.configure(Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);

	        // 所有日期格式都统一为以下样式
	        customMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

	        return customMapper;
	    }
	  @SuppressWarnings("unchecked")
	public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
		  if(!StringUtils.isEmpty(json)) {
			  return clazz.equals(String.class) ? (T) json : MAPPER.readValue(json, clazz);
		  }else {
			try {
				return clazz.newInstance();
			} catch (Exception e) {
				return null;
			}
		}
	    }
	  
	  public static <T> String toJson(T src) {
	    	String json=null;
	        try {
				json= src instanceof String ? (String) src : MAPPER.writeValueAsString(src);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return json;
	    }
	public static <T> T castObject(Object t,Class<T> clazz) throws IOException{
		
		String json = toJson(t);
		
		return fromJson(json, clazz);
	
	};
	
	@SuppressWarnings("rawtypes")
	public static MonitorException changeToMonitorException(Response response) {
		Map map = new HashMap();
		try {
			 map = response.readEntity(Map.class);
			 MonitorException kvmException = new MonitorException(map);
			 log.error("errorCode:" +kvmException.getErrCode() +", errorMsg: "+kvmException.getErrMsg());
			 return kvmException;
		} catch (Exception e) {
			return new MonitorException(Integer.toString(response.getStatus()), e.getMessage());
		}
	}
	public static void main(String[] args) {
		Map<String, Map<String, String>> map1 = new HashMap<String, Map<String,String>>();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", "123");
		map.put("name", "llf");
		
		map1.put("user", map);
		
		try {
			Base base = castObject(map1.get("user"), Base.class);
			System.out.println(base.getUserId()+":"+base.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public static WebClient setHeaders(WebClient client,Base base) {
		if (base.getTenantName() != null && !"".equals(base.getTenantName())) {
			if (!StringUtils.isEmpty(base.getTenantUserName())
					&& !StringUtils.isEmpty(base.getTenantUserPass())) {

				client.header("X_TENANT_NAME", base.getTenantName())
						.header("X_AUTH_URL", base.getProviderUrl())
						.header("X_USER_NAME", base.getTenantUserName())
						.header("X_USER_PASSWORD", base.getTenantUserPass());
			} else {
				client.header("X_TENANT_NAME", base.getTenantName())
						.header("X_AUTH_URL", base.getProviderUrl());
			}
		} else {
			client.header("X_USER_NAME", base.getAuthUser())
					.header("X_USER_PASSWORD", base.getAuthPass())
					.header("X_AUTH_URL", base.getProviderUrl())
					.header("X_TENANT_NAME", base.getAuthTenant());
		}
		
		 * if(!StringUtils.isEmpty(base.getTenantName())) {
		 * client.header("X_TENANT_NAME", base.getTenantName()); }
		 
		return client;
	}
*/
}
