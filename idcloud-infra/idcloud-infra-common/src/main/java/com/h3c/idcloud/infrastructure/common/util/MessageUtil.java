package com.h3c.idcloud.infrastructure.common.util;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Locale;

/**
 * com.h3c.idcloud.infrastructure.common.util
 *
 * @author Chaohong.Mao
 */
public class MessageUtil {

    private static MessageSource messageSource;

    private static final String MESSAGE_PROPERTY_PREFIX = "msg.";

    static {
        try {
            // 获取消息处理类
            messageSource = new ClassPathXmlApplicationContext(
                    "classpath:spring-message-bean.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取得系统消息
     *
     * @param msgId
     *            消息ID
     * @param arg
     *            消息设置参数
     * @return 消息内容
     */
    public static String getMessage(String msgId, Object[] arg) {
        String message = "";
        try {
            message = messageSource.getMessage(MESSAGE_PROPERTY_PREFIX + msgId, arg, Locale.CHINA);
        } catch (Exception e) {
        }

        return message;
    }

    /**
     * 取得系统消息
     *
     * @param msgId
     *            消息ID
     * @return 消息内容
     */
    public static String getMessage(String msgId) {
        return getMessage(msgId, null);
    }
}
