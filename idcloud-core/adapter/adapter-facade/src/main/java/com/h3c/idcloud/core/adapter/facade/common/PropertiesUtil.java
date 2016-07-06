package com.h3c.idcloud.core.adapter.facade.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created by qct on 2015/6/16.
 */
public class PropertiesUtil {

    private static Properties PROPERTIES = new Properties();

    private PropertiesUtil() {
        init();
    }

    public static PropertiesUtil getInstance() {
        return PropertiesUtilHolder.INSTANCE;
    }

    public static PropertiesUtil appendProperties(String propertiesFile) {
        InputStreamReader reader = null;
        InputStream is = ClassLoaderUtil.getResourceAsStream(propertiesFile, PropertiesUtil.class);
        if (null != is) {
            try {
                reader = new InputStreamReader(is, "UTF-8");
                PROPERTIES.load(reader);
                is.close();
                reader.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return getInstance();
    }

    /**
     * 获取参数值
     *
     * @param key 参数名称
     * @return the property
     */
    public String getProperty(String key) {
        String result = "";
        if (PROPERTIES.containsKey(key)) {
            result = PROPERTIES.getProperty(key);
        }
        return result;
    }

    /**
     * @return the properties
     */
    public Properties getProperties() {
        return PROPERTIES;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(Properties properties) {
        PropertiesUtil.PROPERTIES = properties;
    }

    private void init() {
        appendProperties("adapter.properties");
    }

    private static class PropertiesUtilHolder {
        private static final PropertiesUtil INSTANCE = new PropertiesUtil();
    }

}
