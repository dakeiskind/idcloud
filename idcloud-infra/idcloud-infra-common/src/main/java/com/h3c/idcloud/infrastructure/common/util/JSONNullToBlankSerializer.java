package com.h3c.idcloud.infrastructure.common.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class JSONNullToBlankSerializer extends JsonSerializer<Object> {
    @Override
    public void serialize(Object object, JsonGenerator paramJsonGenerator,
                          SerializerProvider paramSerializerProvider) throws IOException,
            JsonProcessingException {
        paramJsonGenerator.writeString("");
    }
}
