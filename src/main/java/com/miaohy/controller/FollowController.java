/**
 * Author : MIAOHY
 * Time :2019/9/1 16:10
 * Beauty is better than ugly!
 */
package com.miaohy.controller;

import com.miaohy.async.EventModel;
import com.miaohy.async.EventProducer;
import com.miaohy.async.EventType;
import com.miaohy.pojo.EntityType;
import com.miaohy.pojo.HostHolder;
import com.miaohy.pojo.Question;
import com.miaohy.service.CommentService;
import com.miaohy.service.FollowService;
import com.miaohy.service.QuestionService;
import com.miaohy.service.UserService;
import com.miaohy.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class FollowController {
    @Autowired
    FollowService followService;
    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    EventProducer eventProducer;
    @RequestMapping("/followUser")
    @ResponseBody
    public String followUser(@RequestParam("userId") int userId){
        if(hostHolder.getUser()==null){
            return CommonUtils.getJSONString(999);
        }
        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_USER,userId);

        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
        .setActorId(hostHolder.getUser().getId()).setEntityId(userId)
                .setEntityType(EntityType.ENTITY_USER).setEntityOwnerId(userId)
        );
        return CommonUtils.getJSONString(ret? 0:1,String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId(),EntityType.ENTITY_USER)));
    }

    @RequestMapping("/unfollowUser")
    @ResponseBody
    public String unfollowUser(@RequestParam("userId") int userId){
        if(hostHolder.getUser()==null){
            return CommonUtils.getJSONString(999);
        }
        boolean ret = followService.unfollow(hostHolder.getUser().getId(),EntityType.ENTITY_USER,userId);

        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityId(userId)
                .setEntityType(EntityType.ENTITY_USER).setEntityOwnerId(userId)
        );
        return CommonUtils.getJSONString(ret ? 0:1,String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId(),EntityType.ENTITY_USER)));
    }

    @RequestMapping("/followQuestion")
    @ResponseBody
    public String followQuestion(@RequestParam("questionId") int questionId){
        if(hostHolder.getUser() == null){
            return CommonUtils.getJSONString(999);
        }
        Question q = questionService.selectByPrimaryKey(questionId);
        if(q==null){
            return CommonUtils.getJSONString(1,"问题不存在");
        }

        boolean ret = followService.follow(hostHolder.getUser().getId(),EntityType.ENTITY_QUESTION,questionId);



        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
        .setEntityOwnerId(q.getUserid()).setEntityType(EntityType.ENTITY_QUESTION)
        .setActorId(hostHolder.getUser().getId()).setEntityId(questionId));

        Map<String,Object> info = new HashMap<>();
        info.put("headUrl",hostHolder.getUser().getHeadurl());
        info.put("name",hostHolder.getUser().getName());
        info.put("id",hostHolder.getUser().getId());
        info.put("count",followService.getFolloweeCount(EntityType.ENTITY_QUESTION,questionId));

        return CommonUtils.getJSONString(ret ? 0 :1 ,info);

    }
    @RequestMapping("/unfollowQuestion")
    @ResponseBody
    public String unfollowQuestion(@RequestParam("questionId") int questionId){
        if(hostHolder.getUser()==null){
            return CommonUtils.getJSONString(999);
        }
        Question question = questionService.selectByPrimaryKey(questionId);
        if(question==null){
            return CommonUtils.getJSONString(1,"问题不存在");
        }

        boolean ret = followService.unfollow(hostHolder.getUser().getId(),EntityType.ENTITY_QUESTION,questionId);

        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW)
                .setEntityOwnerId(question.getUserid()).setEntityType(EntityType.ENTITY_QUESTION)
                .setActorId(hostHolder.getUser().getId()).setEntityId(question.getId())
        );
        Map<String,Object> info = new HashMap<>();
        info.put("id",hostHolder.getUser().getId());
        info.put("count",followService.getFolloweeCount(EntityType.ENTITY_QUESTION,questionId));
        return CommonUtils.getJSONString(ret ?0 :1,info);
    }



}
