/**
 * Author : MIAOHY
 * Time :2019/8/26 17:19
 * Beauty is better than ugly!
 */
package com.miaohy.controller;

import com.miaohy.pojo.*;
import com.miaohy.service.*;
import com.miaohy.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;
    @Autowired
    SensitiveService sensitiveService;

    @Autowired
    LikeService likeService;
    @Autowired
    FollowService followService;
    @RequestMapping("/question/{qid}")
    public String questionDetail(Model model, @PathVariable("qid") int qid) {
        Question question = questionService.selectByPrimaryKey(qid);
        model.addAttribute("question", question);

        Comment comment = new Comment();
        comment.setEntityType(EntityType.ENTITY_QUESTION);
        comment.setEntityId(qid);

        List<Comment> commentList = commentService.getCommentList(comment);
        List<ViewObject> vos = new ArrayList<>();
        for (Comment com : commentList) {
            ViewObject vo = new ViewObject();
            vo.set("comment", com);
            if(hostHolder.getUser()==null){
                vo.set("liked",0);
            }else{
                vo.set("liked",likeService.getLikeStatus(hostHolder.getUser().getId(),EntityType.ENTITY_COMMENT,com.getId()));
            }
            vo.set("likeCount",likeService.getLikeCount(EntityType.ENTITY_COMMENT,com.getId()));
            vo.set("user", userService.selectByPrimaryKey(com.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("vos", vos);

        List<ViewObject> followUsers = new ArrayList<ViewObject>();
        List<Integer> users = followService.getFollowers(EntityType.ENTITY_QUESTION,qid,20);
        for(Integer userId :users){
            ViewObject vo = new ViewObject();
            User u = userService.selectByPrimaryKey(userId);
            if(u==null){
                continue;
            }
            vo.set("name",u.getName());
            vo.set("headUrl",u.getHeadurl());
            vo.set("id",u.getId());
            followUsers.add(vo);
        }
        model.addAttribute("followUsers",followUsers);
        if(hostHolder.getUser()!=null){
            model.addAttribute("followed",followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION,qid));

        }else{
            model.addAttribute("followed",false);
        }
        return "detail";
    }

    @RequestMapping("/question/add")
    @ResponseBody
    public String a(Model model,Question question)  {
        try{
            question.setCreateddate(new Date());
            question.setCommentcount(0);

            String title = question.getTitle();
            title = sensitiveService.filter(title);
            question.setTitle(title);

            String content = question.getContent();
            content = sensitiveService.filter(content);
            question.setContent(content);

            if(hostHolder.getUser()==null){
                question.setUserid(CommonUtils.ANONYMOUS_USERID);
            }else{
                question.setUserid(hostHolder.getUser().getId());
            }

            if(questionService.insertSelective(question)>0){
                return CommonUtils.getJSONString(0);
            }
        }catch (Exception e){
            logger.error("error " + e);
        }

        return CommonUtils.getJSONString(1,"失败");
    }
}
