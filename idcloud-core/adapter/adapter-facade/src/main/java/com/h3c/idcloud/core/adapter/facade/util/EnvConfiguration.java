package com.h3c.idcloud.core.adapter.facade.util;

import com.h3c.idcloud.core.adapter.facade.common.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class EnvConfiguration {

    private static Logger log = LoggerFactory.getLogger(EnvConfiguration.class);
    @Autowired
    private Conf conf;

    public List<Map<String, String>> jacksonRead() throws IOException {
        return JsonUtil.fromJson(new FileReader(conf.getEnvJsonPath()), List.class);
    }

    public void jacksonWrite(List<Map<String, String>> envList) throws IOException {
        FileWriter fileWriter = new FileWriter(conf.getEnvJsonPath());
        fileWriter.write(JsonUtil.toJson(envList));
        fileWriter.flush();
        fileWriter.close();
        log.info("Successfully Copied JSON Object to File...");
    }
}
