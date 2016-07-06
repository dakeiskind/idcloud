package com.h3c.idcloud.core.rest.war.provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.text.SimpleDateFormat;


/**
 * com.h3c.idcloud.core.rest.war.provider
 *
 * @author Chaohong.Mao
 */
@Provider
@Produces("application/json;charset=utf-8")
public class JacksonProviderConfig implements ContextResolver<ObjectMapper> {
    private final ObjectMapper objectMapper;

    public JacksonProviderConfig() {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL)
                    .setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
//        objectMapper.getSerializationConfig()
//                    .withDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return objectMapper;
    }
}
