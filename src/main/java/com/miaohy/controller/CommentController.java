/**
 * Author : MIAOHY
 * Time :2019/8/26 16:43
 * Beauty is better than ugly!
 */
package com.miaohy.controller;

import com.miaohy.async.EventModel;
import com.miaohy.async.EventProducer;
import com.miaohy.async.EventType;
import com.miaohy.pojo.Comment;
import com.miaohy.pojo.EntityType;
import com.miaohy.pojo.HostHolder;
import com.miaohy.pojo.Question;
import com.miaohy.service.CommentService;
import com.miaohy.service.QuestionService;
import com.miaohy.service.SensitiveService;
import com.miaohy.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    SensitiveService sensitiveservice;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    CommentService commentService;
    @Autowired
    QuestionService questionService;
    @Autowired
    EventProducer eventProducer;
    @RequestMapping("/addComment")
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content){
        try {
            content = HtmlUtils.htmlEscape(content);
            content = sensitiveservice.filter(content);

            Comment comment = new Comment();
            if(hostHolder.getUser()!=null){
                comment.setUserId(hostHolder.getUser().getId());
            } else{
                comment.setUserId(CommonUtils.ANONYMOUS_USERID);
            }

            comment.setContent(content);
            comment.setCreatedDate(new Date());
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setStatus(0);
            commentService.insertSelective(comment);
            //添加评论后更新question表中该question的comment count字段
            int count = commentService.getCommentCount(comment.getEntityId(),comment.getEntityType());
            Question question = questionService.selectByPrimaryKey(questionId);
            question.setCommentcount(count);
            questionService.updateByPrimaryKeySelective(question);
            eventProducer.fireEvent(new EventModel(EventType.COMMENT).setActorId(comment.getUserId()).setEntityId(questionId));

        }catch (Exception e){
            logger.error("comment error"+e);
        }
        return "redirect:/question/"+String.valueOf(questionId);
    }
}
