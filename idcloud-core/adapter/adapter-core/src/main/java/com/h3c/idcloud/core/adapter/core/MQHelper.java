package com.h3c.idcloud.core.adapter.core;

import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.other.RegisterEnv;
import com.h3c.idcloud.core.adapter.pojo.other.UnRegisterEnv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.JsonMessageConverter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * MQ Helper based on Spring AMQP
 *
 * @author yuezhu
 */
public class MQHelper {

    private static Logger log = LoggerFactory.getLogger(MQHelper.class);

    private static RabbitAdmin admin;
    private static CachingConnectionFactory cf;
    private static RabbitTemplate template;
    private static Map<String, SimpleMessageListenerContainer> containers = new HashMap<String, SimpleMessageListenerContainer>();

    static {

        Properties props = new Properties();
        URL url = MQHelper.class.getResource("/mq.properties");

        try {
            props.load(url.openStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        cf = new CachingConnectionFactory(props.getProperty("mq-server"));
        cf.setUsername(props.getProperty("mq-user"));
        cf.setPassword(props.getProperty("mq-password"));

        admin = new RabbitAdmin(cf);

        template = new RabbitTemplate(cf);
        template.setMessageConverter(new JsonMessageConverter());
        template.setReplyTimeout(600000); // 10mins waiting for sync messaging

    }

    /**
     * send async message from producer to consumers
     *
     * @param base message entity based on POJO, Message-Driven enabled on consumer end point
     * @return msgId uuid created by java API
     */
    public static String sendMessage(Base base) throws MQException {

        String ex = base.getVirtEnvType() + "-" + base.getVirtEnvUuid();

        final String key = Message2Queue.AsynMessageMapper.get(base.getClass().getSimpleName());

        try {

            TopicExchange exchange = new TopicExchange(ex);
            admin.declareExchange(exchange);

            Queue queue = new Queue(ex + "-" + key.split("\\.")[0]);
            admin.declareQueue(queue);

            admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(key.split("\\.")[0] + ".*.*"));

            template.convertAndSend(ex, key, base, new MessagePostProcessor() {
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setReplyTo("result-" + key.split("\\.")[1]);
                    try {
                        message.getMessageProperties().setCorrelationId(UUID.randomUUID().toString().getBytes("UTF-8"));

                    } catch (UnsupportedEncodingException e) {
                        throw new AmqpException(e);
                    }
                    return message;
                }
            });
        } catch (AmqpException e) {

            e.printStackTrace();
            throw new MQException("tscloud mq exception!");
        }

        return base.getMsgId();
    }

    /**
     * remote process calling for synchronous messaging process
     *
     * @param base message entity based on POJO, Message-Driven enabled on consumer end point
     * @return processing result based on certain input POJO, it is also a POJO need to downcasting
     */
    public static Object rpc(Base base) throws MQException {

        String ex = base.getVirtEnvType() + "-" + base.getVirtEnvUuid();

        String key = Message2Queue.SynMessageMapper.get(base.getClass().getSimpleName());

        Object obj = null;

        try {
            TopicExchange exchange = new TopicExchange(ex);
            admin.declareExchange(exchange);

            Queue queue = new Queue(ex + "-" + key.split("\\.")[0]);
            admin.declareQueue(queue);

            admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(key.split("\\.")[0] + ".*.*"));

            obj = template.convertSendAndReceive(ex, key, base);
        } catch (AmqpException e) {

            e.printStackTrace();
            throw new MQException("tscloud mq exception!");
        }

        return obj;

    }

    /**
     * start listeners for messaging process on consumer end point
     *
     * @param virtualType virtual platform type, for example, vcenter or openstack
     * @param uuid        virtual platform unique identifier
     * @param taskType    different listener for different task
     * @param listener    listener instance on consumer end point, here is entry for Message-Driven processing
     * @param concurrent  paralleled messaging process threads for certain message container based on different
     *                    messaging queue
     */
    public static void startListenerForConsumer(String virtualType, String uuid, String taskType, Object listener,
                                                int concurrent) {

        String ex = virtualType + "-" + uuid;

        Queue queue = new Queue(ex + "-" + taskType);

        admin.declareQueue(queue);

        TopicExchange exchange = new TopicExchange(ex);
        admin.declareExchange(exchange);

        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(taskType + ".*.*"));

        // set up the listener and container
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cf);

        MessageListenerAdapter adapter = new MessageListenerAdapter(listener, new JsonMessageConverter());
        container.setMessageListener(adapter);

        container.setQueueNames(ex + "-" + taskType);
        container.setConcurrentConsumers(concurrent);

        log.info(taskType + " container start......");

        container.start();

        containers.put(ex + "-" + taskType, container);

    }

    /**
     * start listeners for messaging process on producer end point
     *
     * @param listener listener instance on producer end point, here is entry for Message-Driven processing
     */
    public static void startListenerForProducer(Object listener, String type) {

        Queue queue = new Queue("result-" + type);
        admin.declareQueue(queue);

        // set up the listener and container
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cf);

        MessageListenerAdapter adapter = new MessageListenerAdapter(listener, new JsonMessageConverter());
        container.setMessageListener(adapter);
        container.setQueueNames("result-" + type);
        container.setConcurrentConsumers(2);
        container.start();

    }

    public static void startListernerForRegister(Object listener, String queueName) {

        Queue queue = new Queue(queueName);
        admin.declareQueue(queue);

        // set up the listener and container
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cf);

        MessageListenerAdapter adapter = new MessageListenerAdapter(listener, new JsonMessageConverter());
        container.setMessageListener(adapter);
        container.setQueueNames(queueName);
        container.setConcurrentConsumers(1);

        container.start();

    }

    public static String register(RegisterEnv register) throws MQException {

        try {
            DirectExchange exchange = new DirectExchange("register");
            admin.declareExchange(exchange);

            Queue queue = new Queue("register");
            admin.declareQueue(queue);

            admin.declareBinding(BindingBuilder.bind(queue).to(exchange).withQueueName());

            template.convertAndSend("register", "register", register, new MessagePostProcessor() {
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setReplyTo("result-register");
                    try {
                        message.getMessageProperties().setCorrelationId(UUID.randomUUID().toString().getBytes("UTF-8"));

                    } catch (UnsupportedEncodingException e) {
                        throw new AmqpException(e);
                    }
                    return message;
                }
            });

        } catch (AmqpException e) {

            e.printStackTrace();
            throw new MQException("tscloud mq exception!");
        }

        return "";
    }

    public static boolean registerRpc(RegisterEnv register) throws MQException {

        try {

            DirectExchange exchange = new DirectExchange("register");
            admin.declareExchange(exchange);

            Queue queue = new Queue("register");
            admin.declareQueue(queue);

            admin.declareBinding(BindingBuilder.bind(queue).to(exchange).withQueueName());

            Object obj = template.convertSendAndReceive("register", register);

            // if (((RegisterEnvResult) obj).isSuccess()) {
            //
            // Queue queue_ = new Queue("register-slave");
            // admin.declareQueue(queue_);
            //
            // admin.declareBinding(BindingBuilder.bind(queue_).to(exchange)
            // .withQueueName());
            //
            // Object obj_ = template.convertSendAndReceive("register-slave",
            // register);
            //
            // if (((RegisterEnvResult) obj_).isSuccess()) {
            // return true;
            // }
            // }

        } catch (AmqpException e) {

            e.printStackTrace();
            throw new MQException("tscloud mq exception!");

        }
        return true;

    }

    public static boolean unRegisterRpc(UnRegisterEnv unRegister) throws MQException {

        try {

            DirectExchange exchange = new DirectExchange("register");
            admin.declareExchange(exchange);

            Queue queue = new Queue("register");
            admin.declareQueue(queue);

            admin.declareBinding(BindingBuilder.bind(queue).to(exchange).withQueueName());

            Object obj = template.convertSendAndReceive("register", unRegister);

//			if (((UnRegisterEnvResult) obj).isSuccess()) {
//
//				Queue queue_ = new Queue("register-slave");
//				admin.declareQueue(queue_);
//
//				admin.declareBinding(BindingBuilder.bind(queue_).to(exchange).withQueueName());
//
//				Object obj_ = template.convertSendAndReceive("register-slave", unRegister);
//
//				if (((UnRegisterEnvResult) obj_).isSuccess()) {
//					return true;
//				}
//
//			}
        } catch (AmqpException e) {

            e.printStackTrace();
            throw new MQException("tscloud mq exception!");
        }

        return true;
    }

    public static String unRegister(UnRegisterEnv unRegister) throws MQException {
        try {
            DirectExchange exchange = new DirectExchange("register");
            admin.declareExchange(exchange);

            Queue queue = new Queue("register");
            admin.declareQueue(queue);

            admin.declareBinding(BindingBuilder.bind(queue).to(exchange).withQueueName());

            template.convertAndSend("register", "register", unRegister, new MessagePostProcessor() {
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setReplyTo("result-register");
                    try {
                        message.getMessageProperties().setCorrelationId(UUID.randomUUID().toString().getBytes("UTF-8"));

                    } catch (UnsupportedEncodingException e) {
                        throw new AmqpException(e);
                    }
                    return message;
                }
            });

        } catch (AmqpException e) {

            e.printStackTrace();
            throw new MQException("tscloud mq exception!");
        }

        return "";
    }

    public static void stopListenerForConsumer(String virtualType, String uuid, boolean delete) {

        String qHeavy = virtualType + "-" + uuid + "-" + "heavy";
        String qLight = virtualType + "-" + uuid + "-" + "light";
        String qSync = virtualType + "-" + uuid + "-" + "sync";

        containers.get(qHeavy).stop();
        containers.remove(qHeavy);
        containers.get(qLight).stop();
        containers.remove(qLight);
        containers.get(qSync).stop();
        containers.remove(qSync);

        if (delete) {
            admin.deleteQueue(qHeavy, true, true);
            admin.deleteQueue(qLight, true, true);
            admin.deleteQueue(qSync, true, true);

            admin.deleteExchange(virtualType + "-" + uuid);
        }
    }
}
