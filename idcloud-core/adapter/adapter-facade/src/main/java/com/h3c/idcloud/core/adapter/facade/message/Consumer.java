package com.h3c.idcloud.core.adapter.facade.message;


import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.facade.util.Conf;
import com.h3c.idcloud.core.adapter.facade.util.EnvConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class Consumer {

    private static Logger log = LoggerFactory.getLogger(HeavyListener.class);
    @Autowired
    private HeavyListener heavyListener;
    @Autowired
    private LightListener lightListener;
    @Autowired
    private SyncListener syncListener;
    @Autowired
    private RegisterListener registerListener;
    @Autowired
    private Conf conf;
    @Autowired
    private EnvConfiguration envConfiguration;

    public void start() {

        List<Map<String, String>> envs = null;
        try {
            envs = envConfiguration.jacksonRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map<String, String> env : envs) {
            log.info("start listenr for virtual environment uuid is " + env.get("uuid").toString());
            MQHelper.startListenerForConsumer(env.get("env").toString(),
                    env.get("uuid").toString(),
                    "heavy",
                    heavyListener,
                    new Integer(env.get("heavy").toString()).intValue());
            MQHelper.startListenerForConsumer(env.get("env").toString(),
                    env.get("uuid").toString(),
                    "light",
                    lightListener,
                    new Integer(env.get("light").toString()).intValue());
            MQHelper.startListenerForConsumer(env.get("env").toString(),
                    env.get("uuid").toString(),
                    "sync",
                    syncListener,
                    new Integer(env.get("sync").toString()).intValue());
        }
        MQHelper.startListernerForRegister(registerListener, conf.getEnvRegQueue());
    }
}
