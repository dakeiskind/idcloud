package com.h3c.idcloud.core.adapter.facade.message;

import com.h3c.idcloud.infrastructure.common.util.JsonUtil;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by qct on 2016/2/19.
 */
public class RegisterListenerTest {

    @Test
    public void testHandleMessage() throws Exception {
        List<Map<String, String>> envs = new ObjectMapper().readValue(new FileReader("D:\\IntelliJ\\idcloud\\idcloud-core\\adapter\\adapter-facade\\src\\main\\resource\\env.json"), List.class);
        envs = envs.stream().filter(x ->
                !(x.get("env").equals("qcttt")
                        && x.get("uuid").equals("222"))
        ).collect(Collectors.toList());

        FileWriter fileWriter = new FileWriter("D:\\IntelliJ\\idcloud\\idcloud-core\\adapter\\adapter-facade\\src\\main\\resource\\env.json");
        fileWriter.write(JsonUtil.toJson(envs));
        fileWriter.flush();
        fileWriter.close();
    }
}