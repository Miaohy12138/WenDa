/**
 * Author : MIAOHY
 * Time :2019/9/1 9:24
 * Beauty is better than ugly!
 */
package com.miaohy.controller;

import com.miaohy.async.EventModel;
import com.miaohy.async.EventProducer;
import com.miaohy.async.EventType;
import com.miaohy.pojo.Comment;
import com.miaohy.pojo.EntityType;
import com.miaohy.pojo.HostHolder;
import com.miaohy.service.CommentService;
import com.miaohy.service.LikeService;
import com.miaohy.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {

    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping("/like")
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId){
        if(hostHolder.getUser()==null){
            return CommonUtils.getJSONString(999);
        }
        Comment comment = commentService.selectByPrimaryKey(commentId);

        eventProducer.fireEvent(new EventModel(EventType.LIKE)
        .setActorId(hostHolder.getUser().getId()).setEntityId(commentId)
        .setEntityType(EntityType.ENTITY_COMMENT)
        .setEntityOwnerId(comment.getUserId())
        .setExt("questionId",String.valueOf(comment.getEntityId())));

        long likeCount = likeService.like(hostHolder.getUser().getId(),EntityType.ENTITY_COMMENT,commentId);
        return CommonUtils.getJSONString(0,String.valueOf(likeCount));
    }

    @RequestMapping("/dislike")
    @ResponseBody
    public String disLike(@RequestParam("commentId") int commentId){
        if (hostHolder.getUser() == null) {
            return CommonUtils.getJSONString(999);
        }
        long likeCount = likeService.disLike(hostHolder.getUser().getId(),EntityType.ENTITY_COMMENT,commentId);
        return CommonUtils.getJSONString(0,String.valueOf(likeCount));
    }
}
