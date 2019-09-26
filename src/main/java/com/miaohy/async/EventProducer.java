/**
 * Author : MIAOHY
 * Time :2019/8/28 20:30
 * Beauty is better than ugly!
 */
package com.miaohy.async;

import com.alibaba.fastjson.JSONObject;
import com.miaohy.utils.JedisAdapter;
import com.miaohy.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel){
        try{
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key,json);
            return true;
        } catch(Exception e){
            return false;
        }

    }
}
