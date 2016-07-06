package com.h3c.idcloud.infrastructure.common.util;

import java.io.IOException;
import java.text.NumberFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class JSONPercentToDoubleDeserializer extends JsonDeserializer<Double> {

	private static Log logger = LogFactory.getLog(JSONPercentToDoubleDeserializer.class);

	@Override
	public Double deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		Double value = null;
		try {
			String text = jsonParser.getText();
			NumberFormat percentFormat = NumberFormat.getPercentInstance();
			value = percentFormat.parse(text).doubleValue();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return value;
	}

}	
