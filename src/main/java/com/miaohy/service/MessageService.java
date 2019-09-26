package com.miaohy.service;

import com.miaohy.dao.MessageMapper;
import com.miaohy.pojo.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageService{

    @Resource
    private MessageMapper messageMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return messageMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Message record) {
        return messageMapper.insert(record);
    }

    
    public int insertSelective(Message record) {
        return messageMapper.insertSelective(record);
    }

    
    public Message selectByPrimaryKey(Integer id) {
        return messageMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Message record) {
        return messageMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Message record) {
        return messageMapper.updateByPrimaryKey(record);
    }

    public List<Message> getConversationList(int userId,int offset,int limit){return messageMapper.getConverstationList(userId,offset,limit);}

    public int getConversationUnreadCount(int localUserId, String conversationid) {
        return messageMapper.getConversationUnreadCount(localUserId,conversationid);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return messageMapper.getConversationDetail(conversationId,offset,limit);
    }
}
