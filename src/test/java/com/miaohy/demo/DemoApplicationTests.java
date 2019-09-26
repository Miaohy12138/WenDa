package com.miaohy.demo;

import com.miaohy.dao.UserMapper;
import com.miaohy.pojo.Question;
import com.miaohy.pojo.User;
import com.miaohy.service.QuestionService;
import com.miaohy.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    @Autowired
    UserMapper userMapper;

    @Test
    public void contextLoads() {
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setHeadurl("https://images.nowcoder.com/head/" + random.nextInt(1000) + "m.png@.png ");
            user.setPassword("123456");
            user.setSalt("");
            user.setId(i);
            userService.updateByPrimaryKeySelective(user);
            Question question = new Question();
            question.setUserid(i);
            question.setCommentcount(random.nextInt(12));
            question.setTitle(String.format("测试标题{%d}", i));
            question.setContent(String.format("测试内容{%d}", i));
            question.setCreateddate(new Date());
            questionService.insertSelective(question);
        }
        User user = new User();
        user.setName("USER1");
        List<User> list = userMapper.selectByUserSelective(user);
        System.out.println(Arrays.toString(list.toArray()));
    }

}
