package com.h3c.idcloud.core.adapter.facade.util;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.network.ExternalGateway;
import com.h3c.idcloud.core.adapter.pojo.network.NetworkStatus;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.Port;
import org.jclouds.openstack.neutron.v2.domain.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;


public class BaseUtil {

    private static ObjectMapper MAPPER;
    private static Logger logger = LoggerFactory.getLogger(BaseUtil.class);

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
        return clazz.equals(String.class) ? (T) json : MAPPER.readValue(json, clazz);
    }

    public static <T> String toJson(T src) {
        String json = null;
        try {
            json = src instanceof String ? (String) src : MAPPER.writeValueAsString(src);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static <T> T castObject(Object t, Class<T> clazz) throws IOException {
        String json = toJson(t);
        return fromJson(json, clazz);
    }

    public static KvmException changeToKvmException(Response response) {
        Map map = new HashMap();
        try {
            map = response.readEntity(Map.class);
            KvmException kvmException = new KvmException(map);
            logger.error("errorCode:" + kvmException.getErrCode() + ", errorMsg: " + kvmException.getErrMsg());
            return kvmException;
        } catch (Exception e) {
            return new KvmException(Integer.toString(response.getStatus()), e.getMessage());
        }
    }

    public static void main(String[] args) {
        Map<String, Map<String, String>> map1 = new HashMap<String, Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", "123");
        map.put("name", "llf");
        map1.put("user", map);

        try {
            Base base = castObject(map1.get("user"), Base.class);
            System.out.println(base.getUserId() + ":" + base.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static WebClient setHeaders(WebClient client, com.h3c.idcloud.core.adapter.pojo.common.Base base) {
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
        /*
         * if(!StringUtils.isEmpty(base.getTenantName())) {
		 * client.header("X_TENANT_NAME", base.getTenantName()); }
		 */
        return client;
    }

    public static <T> BaseResult setResult(com.h3c.idcloud.core.adapter.pojo.common.Base base, Class<T> result) {
        BaseResult result2 = new BaseResult();
        try {
            result2 = (BaseResult) result.newInstance();
            result2.setProviderType(base.getProviderType());
            result2.setVirtEnvType(base.getVirtEnvType());
            result2.setVirtEnvUuid(base.getVirtEnvUuid());
            result2.setOptions(base.getOptions());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result2;
    }

    public static com.h3c.idcloud.core.adapter.pojo.network.Port assemblePort(Port port) {
        com.h3c.idcloud.core.adapter.pojo.network.Port returnPort =
                com.h3c.idcloud.core.adapter.pojo.network.Port.builder().build();
        try {
            BeanUtils.copyProperties(returnPort, port);
            BeanUtils.copyProperty(returnPort, "networkStatus", NetworkStatus.fromValue(port.getStatus().name()));
            BeanUtils.copyProperty(returnPort, "ips", Sets.newHashSet(ImmutableSet.copyOf(
                    port.getFixedIps().stream().map(ip -> ip.getIpAddress()).collect(Collectors.toSet()))));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        logger.debug("plain port: {}", port);
        logger.debug("result port: {}", returnPort);
        return returnPort;
    }

    public static com.h3c.idcloud.core.adapter.facade.provision.model.network.Network assembleNetwork(Network network) {
        com.h3c.idcloud.core.adapter.facade.provision.model.network.Network resultNetwork =
                com.h3c.idcloud.core.adapter.facade.provision.model.network.Network.builder().build();
        try {
            BeanUtils.copyProperties(resultNetwork, network);
            BeanUtils.copyProperty(resultNetwork, "networkStatus", NetworkStatus.fromValue(network.getStatus().name()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        logger.debug("plain network: {}", network);
        logger.debug("result network: {}", resultNetwork);
        return resultNetwork;
    }

    public static com.h3c.idcloud.core.adapter.pojo.network.Router assembleRouter(
            ExternalGateway externalGateway, Router router) {
        com.h3c.idcloud.core.adapter.pojo.network.Router resultRouter =
                com.h3c.idcloud.core.adapter.pojo.network.Router.builder().build();
        try {
            BeanUtils.copyProperties(resultRouter, router);
            resultRouter.setExternalGateway(externalGateway);
//                ExternalGateway externalGateway = ExternalGateway.builder().build();
//                BeanUtils.copyProperties(externalGateway, router.getExternalGatewayInfo());
//                resultRouter.setExternalGateway(externalGateway);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return resultRouter;
    }
}
