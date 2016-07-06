package com.h3c.idcloud.core.adapter.facade.util;

import com.google.common.collect.Maps;

import com.h3c.idcloud.infrastructure.common.util.JsonUtil;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by qct on 2016/2/19.
 */
public class EnvConfigurationTest {

    @Test
    public void testJacksonRead() throws Exception {
        List<Map<String, String>> envList = new ObjectMapper().readValue(new FileReader("D:\\IntelliJ\\idcloud\\idcloud-core\\adapter\\adapter-facade\\src\\main\\resource\\env.json"), List.class);
        System.out.println(envList);
    }

    @Test
    public void testJacksonWrite() throws Exception {
        List<Map<String, String>> envList = new ObjectMapper().readValue(new FileReader("D:\\IntelliJ\\idcloud\\idcloud-core\\adapter\\adapter-facade\\src\\main\\resource\\env.json"), List.class);

        Map<String, String> env = Maps.newHashMap();
        env.put("env", "qcttt");
        env.put("uuid", "222");
        env.put("heavy", "555");
        env.put("light", "555");
        env.put("sync", "1055");

        envList.add(env);

        FileWriter fileWriter = new FileWriter("D:\\IntelliJ\\idcloud\\idcloud-core\\adapter\\adapter-facade\\src\\main\\resource\\env.json");
        fileWriter.write(JsonUtil.toJson(envList));
        fileWriter.flush();
        fileWriter.close();
    }
}