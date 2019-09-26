package com.miaohy.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.miaohy.dao.LoginTicketMapper;
import com.miaohy.pojo.LoginTicket;
@Service
public class LoginTicketService{

    @Resource
    private LoginTicketMapper loginTicketMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return loginTicketMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(LoginTicket record) {
        return loginTicketMapper.insert(record);
    }

    
    public int insertSelective(LoginTicket record) {
        return loginTicketMapper.insertSelective(record);
    }

    
    public LoginTicket selectByPrimaryKey(Integer id) {
        return loginTicketMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(LoginTicket record) {
        return loginTicketMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(LoginTicket record) {
        return loginTicketMapper.updateByPrimaryKey(record);
    }

}
