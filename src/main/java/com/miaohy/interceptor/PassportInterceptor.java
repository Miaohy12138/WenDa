/**
 * Author : MIAOHY
 * Time :2019/7/25 11:10
 * Beauty is better than ugly!
 */
package com.miaohy.interceptor;

import com.miaohy.dao.LoginTicketMapper;
import com.miaohy.dao.UserMapper;
import com.miaohy.pojo.HostHolder;
import com.miaohy.pojo.LoginTicket;
import com.miaohy.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    LoginTicketMapper loginTicketMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = null;
        if(request.getCookies()!=null){
            for(Cookie cookie :request.getCookies()){
                if(cookie.getName().equals("ticket")){
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        if(ticket!=null){
            LoginTicket loginTicket = new LoginTicket();
            loginTicket.setTicket(ticket);
            List<LoginTicket> loginTickets = loginTicketMapper.selectByLogtinTicketSelective(loginTicket);
            if(loginTickets.size()>0){
                loginTicket = loginTickets.get(0);
                if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                    return true;
                }
                User user = userMapper.selectByPrimaryKey(loginTicket.getUserId());
                hostHolder.setUser(user);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && hostHolder.getUser() != null) {
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
