package com.miaohy.dao;

import com.miaohy.pojo.LoginTicket;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginTicketMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoginTicket record);

    int insertSelective(LoginTicket record);

    LoginTicket selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoginTicket record);

    int updateByPrimaryKey(LoginTicket record);

    List<LoginTicket> selectByLogtinTicketSelective(LoginTicket record);
}