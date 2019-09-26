package com.miaohy.service;

import com.miaohy.dao.LoginTicketMapper;
import com.miaohy.dao.UserMapper;
import com.miaohy.pojo.LoginTicket;
import com.miaohy.pojo.User;
import com.miaohy.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginTicketMapper loginTicketMapper;

    public Map<String,Object> register(User user){
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isBlank(user.getName())){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(user.getPassword())){
            map.put("msg","密码不能为空");
            return map;
        }
        User user1 = new User();
        user1.setName(user.getName());
        List<User> list = userMapper.selectByUserSelective(user1);
        if(list.size()>0){
            map.put("msg","该用户名已存在");
            return map;
        }
        user.setSalt(UUID.randomUUID().toString().substring(1,6));
        user.setHeadurl("https://images.nowcoder.com/head/"+new Random().nextInt(1000)+"m.png@.png ");
        String oldPassword = user.getPassword();
        System.out.println(oldPassword);
        String newPassword = CommonUtils.MD5(oldPassword+user.getSalt());
        user.setPassword(newPassword);
        userMapper.insertSelective(user);

        // 登陆
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    public Map<String,Object> login(User user){
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isBlank(user.getName())){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(user.getPassword())){
            map.put("msg","用户密码不能为空");
            return map;
        }

        User user1 = new User();
        user1.setName(user.getName());
        List<User> list = userMapper.selectByUserSelective(user1);
        if(list.size()<0){
            map.put("msg","用户名不存在，请注册");
            return map;
        }
        //System.out.println(user1);
        user1 = list.get(0);
//        System.out.println(user1);
//        System.out.println(CommonUtils.MD5(user.getPassword()+user1.getSalt()));
//        System.out.println(user1.getPassword());
        if(!CommonUtils.MD5(user.getPassword()+user1.getSalt()).equals(user1.getPassword())){
            map.put("msg","密码错误");
            return map;
        }
        String ticket = addLoginTicket(user1.getId());
        map.put("ticket", ticket);
        return map;
    }
    private String addLoginTicket(int userId) {

        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);

        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketMapper.insertSelective(ticket);
        return ticket.getTicket();

    }

    public void logout(String ticket) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setTicket(ticket);
        List<LoginTicket> loginTickets = loginTicketMapper.selectByLogtinTicketSelective(loginTicket);
        loginTicket = loginTickets.get(0);
        loginTicket.setStatus(1);
        loginTicketMapper.updateByPrimaryKeySelective(loginTicket);
    }

    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(User record) {
        return userMapper.insertUser(record);
    }

    
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }


    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    public List<User> selectUserListByUserSelective(User user){return userMapper.selectByUserSelective(user);}
}
