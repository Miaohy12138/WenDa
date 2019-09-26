/**
 * Author : MIAOHY
 * Time :2019/8/26 21:59
 * Beauty is better than ugly!
 */
package com.miaohy.controller;

import com.miaohy.pojo.HostHolder;
import com.miaohy.pojo.Message;
import com.miaohy.pojo.User;
import com.miaohy.pojo.ViewObject;
import com.miaohy.service.MessageService;
import com.miaohy.service.UserService;
import com.miaohy.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class MessageController {

    private static final Logger logger  = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @RequestMapping("/msg/addMessage")
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content){
        try {
            if(hostHolder.getUser()==null){
                return CommonUtils.getJSONString(999,"未登录");
            }
            User tempUser = new User();
            tempUser.setName(toName);
            User user = null;
            List<User> userList = userService.selectUserListByUserSelective(tempUser);
            if(userList.size()<0){
                return CommonUtils.getJSONString(1,"用户不存在");
            }else{
                user = userList.get(0);
            }

            int fromId = hostHolder.getUser().getId();
            int toId = user.getId();

            Message msg = new Message();
            msg.setContent(content);
            msg.setCreateddate(new Date());
            msg.setFromid(fromId);
            msg.setToid(toId);
            msg.setHasread(0);
            msg.setConversationid(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));
            messageService.insert(msg);

            return CommonUtils.getJSONString(0);
        } catch (Exception e){
            logger.error("站内信发送失败 " + e);
            return CommonUtils.getJSONString(1,"站内信发送失败");
        }
    }

    @RequestMapping("/msg/jsonAddMessage")
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content){
        try{
            Message msg = new Message();
            msg.setContent(content);
            msg.setFromid(fromId);
            msg.setToid(toId);
            msg.setCreateddate(new Date());
            msg.setConversationid(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));
            messageService.insert(msg);
            return CommonUtils.getJSONString(msg.getId());
        } catch (Exception e){
            logger.error(" 评论增加失败"+e);
            return CommonUtils.getJSONString(1,"插入评论失败");
        }

    }
    @RequestMapping("/msg/list")
    public String conversationDeatil(Model model){
        try{
            int localUserId = hostHolder.getUser().getId();
            List<ViewObject> vos = new ArrayList<>();
            List<Message> messageList = messageService.getConversationList(localUserId,0,10);
            for(Message msg:messageList){
                ViewObject vo = new ViewObject();
                vo.set("conversation",msg);
                int targetId = msg.getFromid()==localUserId ? msg.getToid() : msg.getFromid();
                User tempUser = new User();
                tempUser.setId(targetId);
                User user = userService.selectByPrimaryKey(targetId);
                vo.set("user",user);
                vo.set("unread",messageService.getConversationUnreadCount(localUserId,msg.getConversationid()));
                vos.add(vo);
            }
            model.addAttribute("conversations",vos);
        } catch (Exception e){
            logger.error("站内信获取失败 " + e);
        }
        return "letter";
    }

    @RequestMapping("/msg/detail/{conversationId}")
    public String conversationDetail(Model model, @PathVariable("conversationId") String conversationId){
        try{
            List<Message> conversationList = messageService.getConversationDetail(conversationId,0,10);
            List<ViewObject> messages = new ArrayList<>();
            for(Message msg :conversationList){
                ViewObject vo = new ViewObject();
                vo.set("message",msg);
                User user  = userService.selectByPrimaryKey(msg.getFromid());
                if(user ==null){
                    continue;
                }
                vo.set("headUrl",user.getHeadurl());
                vo.set("userId",user.getId());
                messages.add(vo);
            }
            model.addAttribute("messages",messages);
        } catch (Exception e){
            logger.error("获取消息详情失败",e.getMessage());
        }
        return "letterDetail";
    }
}
