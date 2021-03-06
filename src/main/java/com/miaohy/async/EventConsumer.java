/**
 * Author : MIAOHY
 * Time :2019/8/30 17:34
 * Beauty is better than ugly!
 */
package com.miaohy.async;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.miaohy.utils.JedisAdapter;
import com.miaohy.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    @Autowired
    JedisAdapter jedisAdapter;
    private Map<EventType, List<EventHandler>> config = new HashMap<EventType, List<EventHandler>>();
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {

        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);

        if (beans != null) {
            for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
                List<EventType> eventTypes = entry.getValue().getSupportEventType();

                for (EventType type : eventTypes) {
                    if (!config.containsKey(type)) {
                        config.put(type, new ArrayList<EventHandler>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }

        ThreadFactory namedThread = new ThreadFactoryBuilder().setNameFormat("event-pool-%d").build();
        ExecutorService pool2 = new ThreadPoolExecutor(1, 1, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), namedThread);
        pool2.submit(() -> {

        });

        Thread thread = new Thread(()->{
            while (true) {
                String key = RedisKeyUtil.getEventQueueKey();
                List<String> events = jedisAdapter.brpop(0, key);
                for (String message : events) {
                    if (message.equals(key)) {
                        continue;
                    }
                    EventModel eventModel = JSON.parseObject(message, EventModel.class);
                    if (!config.containsKey(eventModel.getType())) {
                        logger.error("不能识别的事件");
                        continue;
                    }
                    for (EventHandler handler : config.get(eventModel.getType())) {
                        handler.doHandle(eventModel);
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
