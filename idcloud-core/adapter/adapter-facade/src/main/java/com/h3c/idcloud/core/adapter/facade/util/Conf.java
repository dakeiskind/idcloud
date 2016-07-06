package com.h3c.idcloud.core.adapter.facade.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Conf {

    @Value("#{interfaceProps['env.json.path']}")
    private String envJsonPath;

    @Value("#{interfaceProps['env.reg.queue']}")
    private String envRegQueue;

    public String getEnvJsonPath() {
        return envJsonPath;
    }

    public void setEnvJsonPath(String envJsonPath) {
        this.envJsonPath = envJsonPath;
    }

    public String getEnvRegQueue() {
        return envRegQueue;
    }

    public void setEnvRegQueue(String envRegQueue) {
        this.envRegQueue = envRegQueue;
    }

}
