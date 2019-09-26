/**
 * Author : MIAOHY
 * Time :2019/9/1 18:02
 * Beauty is better than ugly!
 */
package com.miaohy.async.handler;

import com.miaohy.async.EventHandler;
import com.miaohy.async.EventModel;
import com.miaohy.async.EventType;
import com.miaohy.pojo.EntityType;
import com.miaohy.pojo.Message;
import com.miaohy.pojo.User;
import com.miaohy.service.MessageService;
import com.miaohy.service.UserService;
import com.miaohy.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class FollowHandler implements EventHandler {
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {

        Message message = new Message();
        message.setFromid(CommonUtils.SYSTEM_USERID);
        message.setToid(model.getEntityOwnerId());
        message.setCreateddate(new Date());
        User user = userService.selectByPrimaryKey(model.getActorId());

        if(model.getEntityType() == EntityType.ENTITY_QUESTION){
            message.setContent("用户" + user.getName()
                    + "关注了你的问题,http://127.0.0.1:8080/question/" + model.getEntityId());
        } else if (model.getEntityType() == EntityType.ENTITY_USER) {
            message.setContent("用户" + user.getName()
                    + "关注了你,http://127.0.0.1:8080/user/" + model.getActorId());
        }
        messageService.insert(message);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
