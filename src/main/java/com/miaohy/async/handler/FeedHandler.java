/**
 * Author : MIAOHY
 * Time :2019/10/20 14:43
 * Beauty is better than ugly!
 */
package com.miaohy.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.miaohy.async.EventHandler;
import com.miaohy.async.EventModel;
import com.miaohy.async.EventType;
import com.miaohy.pojo.EntityType;
import com.miaohy.pojo.Feed;
import com.miaohy.pojo.Question;
import com.miaohy.pojo.User;
import com.miaohy.service.FeedService;
import com.miaohy.service.FollowService;
import com.miaohy.service.QuestionService;
import com.miaohy.service.UserService;
import com.miaohy.utils.JedisAdapter;
import com.miaohy.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FeedHandler implements EventHandler {

    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;
    @Autowired
    FeedService feedService;
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    QuestionService questionService;
    private String buildFeedData(EventModel model){
        Map<String,String> map = new HashMap<>();
        User actor = userService.selectByPrimaryKey(model.getActorId());

        if(actor == null){
            return null;
        }
        map.put("userId",String.valueOf(actor.getId()));
        map.put("userHead", actor.getHeadurl());
        map.put("userName", actor.getName());
        if(model.getType() == EventType.COMMENT||(model.getType() == EventType.FOLLOW&&model.getEntityType()== EntityType.ENTITY_QUESTION)){
            Question question = questionService.selectByPrimaryKey(model.getEntityId());
            if(question == null){
                return null;
            }
            map.put("questionId",String.valueOf(question.getId()));
            map.put("questionTitle",question.getTitle());
            return JSONObject.toJSONString(map);

        }
        return null;
    }
    @Override
    public void doHandle(EventModel model) {
        model.setActorId(1);

        Feed feed =new Feed();
        feed.setCreatedDate(new Date());
        feed.setType(model.getType().getValue());
        feed.setUserId(model.getActorId());
        feed.setData(buildFeedData(model));
        if(feed.getData() == null){
            return ;
        }
        feedService.insertSelective(feed);

        List<Integer> followers = followService.getFollowers(EntityType.ENTITY_USER,model.getActorId(),Integer.MAX_VALUE);

        followers.add(0);
        for(int follower : followers){
            String timeLineKey = RedisKeyUtil.getTimelineKey(follower);
            jedisAdapter.lpush(timeLineKey,String.valueOf(feed.getId()));
        }

    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(new EventType[]{EventType.COMMENT,EventType.FOLLOW});
    }
}
