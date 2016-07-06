package com.h3c.idcloud.infra.log.listener;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Set;

/**
 * com.h3c.idcloud.log.listener
 * Created by Tono on 2016/1/21.
 */
public class LoggerConfigListener extends ContextAwareBase implements LoggerContextListener, LifeCycle {

    private boolean started = false;

    private static Properties properties = new Properties();

    private static final String LOG_CONFIG_FILE = "log.properties";

    static {
        InputStreamReader reader = null;
        InputStream is = ClassLoader.getSystemResourceAsStream(LOG_CONFIG_FILE);
        if (null != is) {
            try {
                reader = new InputStreamReader(is, "UTF-8");
                properties.load(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start() {
        if (started) {
            return;
        }

        Context context = getContext();
        Set<String> properSet = properties.stringPropertyNames();
        for (String key : properSet) {
            context.putProperty(key, properties.getProperty(key));
        }

        started = true;
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isStarted() {
        return false;
    }

    @Override
    public boolean isResetResistant() {
        return false;
    }

    @Override
    public void onStart(LoggerContext loggerContext) {

    }

    @Override
    public void onReset(LoggerContext loggerContext) {

    }

    @Override
    public void onStop(LoggerContext loggerContext) {

    }

    @Override
    public void onLevelChange(Logger logger, Level level) {

    }
}
