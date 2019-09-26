/**
 * Author : MIAOHY
 * Time :2019/7/17 14:01
 * Beauty is better than ugly!
 */
package com.miaohy.controller;

import com.miaohy.pojo.Question;
import com.miaohy.pojo.ViewObject;
import com.miaohy.service.QuestionService;
import com.miaohy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    private  static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    private List<ViewObject> getQuestion(int userid,int offset,int limit){
        List<Question> questionList = questionService.selectLatestQuestions(userid,offset,limit);
        List<ViewObject> vos = new ArrayList<>();
        for(Question question :questionList){
            ViewObject vo = new ViewObject();
            vo.set("question",question);
            vo.set("user",userService.selectByPrimaryKey(question.getUserid()));
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(value = {"/","/index"})
    public String index(Model model){
        model.addAttribute("vos",getQuestion(0,0,10));
        return "index";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        System.out.println(userId);
        model.addAttribute("vos", getQuestion(userId, 0, 10));
        return "index";
    }

}
