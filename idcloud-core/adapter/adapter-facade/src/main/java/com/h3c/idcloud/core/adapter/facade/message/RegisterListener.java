package com.h3c.idcloud.core.adapter.facade.message;

import com.google.common.collect.Maps;

import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.facade.util.Conf;
import com.h3c.idcloud.core.adapter.facade.util.EnvConfiguration;
import com.h3c.idcloud.core.adapter.pojo.other.RegisterEnv;
import com.h3c.idcloud.core.adapter.pojo.other.UnRegisterEnv;
import com.h3c.idcloud.core.adapter.pojo.other.result.RegisterEnvResult;
import com.h3c.idcloud.core.adapter.pojo.other.result.UnRegisterEnvResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RegisterListener {

    private static Logger log = LoggerFactory.getLogger(RegisterListener.class);
    @Autowired
    private HeavyListener heavyListener;
    @Autowired
    private LightListener lightListener;
    @Autowired
    private SyncListener syncListener;
    @Autowired
    private EnvConfiguration envConfiguration;
    @Autowired
    private Conf conf;

    public RegisterEnvResult handleMessage(RegisterEnv register) {
        log.info("receiving message for registering...");
        RegisterEnvResult result = new RegisterEnvResult();
        result.setVirtualType(register.getVirtEnvType());
        result.setUuid(register.getVirtEnvUuid());
        try {
            List<Map<String, String>> envList = envConfiguration.jacksonRead();

            Map<String, String> env = Maps.newHashMap();
            env.put("env", register.getVirtEnvType());
            env.put("uuid", register.getVirtEnvUuid());
            env.put("heavy", "5");
            env.put("light", "5");
            env.put("sync", "10");

            envList.add(env);
            envConfiguration.jacksonWrite(envList);
        } catch (IOException e) {
            e.printStackTrace();
            result.setSuccess(false);
            return result;
        }

        MQHelper.startListenerForConsumer(register.getVirtEnvType(),
                register.getVirtEnvUuid(), "heavy", heavyListener, 5);
        MQHelper.startListenerForConsumer(register.getVirtEnvType(),
                register.getVirtEnvUuid(), "light", lightListener, 5);
        MQHelper.startListenerForConsumer(register.getVirtEnvType(),
                register.getVirtEnvUuid(), "sync", syncListener, 5);

        result.setSuccess(true);
        return result;
    }

    public UnRegisterEnvResult handleMessage(UnRegisterEnv unRegister) {
        log.info("receiving message for unregistering...");
        UnRegisterEnvResult result = new UnRegisterEnvResult();
        result.setVirtualType(unRegister.getVirtEnvType());
        result.setUuid(unRegister.getVirtEnvUuid());

        try {
            List<Map<String, String>> envs = envConfiguration.jacksonRead().stream().filter(x ->
                    !(x.get("env").equals(unRegister.getVirtEnvType())
                            && x.get("uuid").equals(unRegister.getVirtEnvUuid())))
                    .collect(Collectors.toList());

            envConfiguration.jacksonWrite(envs);
        } catch (IOException e) {
            e.printStackTrace();
            result.setSuccess(false);
            return result;
        }

        try {
            MQHelper.stopListenerForConsumer(unRegister.getVirtEnvType(),
                    unRegister.getVirtEnvUuid(), true);
        } catch (Exception e) {
            log.error("删除虚拟化环境" + unRegister.getVirtEnvUuid() + " 失败，或者虚拟化环境不存在!");
        }

        result.setSuccess(true);
        return result;
    }

//    @SuppressWarnings("unchecked")
//    public RegisterEnvResult handleMessage(RegisterEnv register) {
//
//        log.info("receiving message for registering...");
//
//        RegisterEnvResult result = new RegisterEnvResult();
//        result.setVirtualType(register.getVirtEnvType());
//        result.setUuid(register.getVirtEnvUuid());
//
//        JSONArray envs = envConfiguration.read();
//
//        JSONObject added = new JSONObject();
//
//        added.put("env", register.getVirtEnvType());
//        added.put("uuid", register.getVirtEnvUuid());
//        added.put("heavy", "5");
//        added.put("light", "5");
//        added.put("sync", "10");
//
//        envs.add(added);
//
//        try {
//            envConfiguration.write(envs);
//        } catch (IOException e) {
//            e.printStackTrace();
//            result.setSuccess(false);
//            return result;
//        }
//
//        MQHelper.startListenerForConsumer(register.getVirtEnvType(),
//                register.getVirtEnvUuid(), "heavy", heavyListener, 5);
//        MQHelper.startListenerForConsumer(register.getVirtEnvType(),
//                register.getVirtEnvUuid(), "light", lightListener, 5);
//        MQHelper.startListenerForConsumer(register.getVirtEnvType(),
//                register.getVirtEnvUuid(), "sync", syncListener, 5);
//
//        result.setSuccess(true);
//
//        return result;
//
//    }


//    public UnRegisterEnvResult handleMessage(UnRegisterEnv unRegister) {
//
//        log.info("receiving message for unregistering...");
//
//        UnRegisterEnvResult result = new UnRegisterEnvResult();
//        result.setVirtualType(unRegister.getVirtEnvType());
//        result.setUuid(unRegister.getVirtEnvUuid());
//
//        JSONArray envs = envConfiguration.read();
//
//        for (Object obj : envs) {
//            JSONObject env = (JSONObject) obj;
//            if (env.get("env").toString().equals(unRegister.getVirtEnvType())
//                    && env.get("uuid").toString()
//                    .equals(unRegister.getVirtEnvUuid())) {
//                envs.remove(obj);
//                break;
//            }
//        }
//
//        try {
//            envConfiguration.write(envs);
//        } catch (IOException e) {
//            e.printStackTrace();
//            result.setSuccess(false);
//            return result;
//        }
//
////		boolean delete = conf.getEnvRegQueue().equals("register-slave") ? true
////				: false;
//
//        try {
//            MQHelper.stopListenerForConsumer(unRegister.getVirtEnvType(),
//                    unRegister.getVirtEnvUuid(), true);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            log.error("删除虚拟化环境" + unRegister.getVirtEnvUuid() + " 失败，或者虚拟化环境不存在!");
//        }
//
//        result.setSuccess(true);
//
//        return result;
//    }
}
