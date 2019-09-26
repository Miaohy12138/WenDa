package com.miaohy.dao;

import com.miaohy.pojo.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    List<Message> getConverstationList(int userId, int offset, int limit);

    int getConversationUnreadCount(int localUserId, String conversationid);

    List<Message> getConversationDetail(String conversationId, int offset, int limit);
}