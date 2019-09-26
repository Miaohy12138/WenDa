/**
 * Author : MIAOHY
 * Time :2019/7/22 16:42
 * Beauty is better than ugly!
 */
package com.miaohy.controller;

import com.miaohy.pojo.User;
import com.miaohy.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;
    @RequestMapping("/reg")
    public String reg(Model model, User user, @RequestParam(value = "next",required = false) String next,
                      @RequestParam(value = "rememberme",defaultValue = "false") boolean rememberme,
                      HttpServletResponse response){
        try {
            Map<String ,Object> map = userService.register(user);
            if(map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme) {
                    cookie.setMaxAge(3600*24*7);

                }
                response.addCookie(cookie);
                if(StringUtils.isNotBlank(next)){
                    return "redirect:"+next;
                }
                return "redirect:/";
            }else{
                model.addAttribute("msg",map.get("msg"));
                return "login";
            }

        }catch (Exception e){
            model.addAttribute("msg","服务器拥挤");
            return "login";
        }
    }
    @RequestMapping("/reglogin")
    public String relogin(Model model,@RequestParam(value = "next",required = false ) String next){
        model.addAttribute("next",next);
        return "login";
    }

    @RequestMapping("/login")
    public String login(Model model, User user,
                        @RequestParam(value="next", required = false) String next,
                        @RequestParam(value="rememberme", defaultValue = "false") boolean rememberme,
                        HttpServletResponse response){
        System.out.println(next);
        try {
            Map<String ,Object> map = userService.login(user);
            if(map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme) {
                    cookie.setMaxAge(3600*24*7);
                }
                response.addCookie(cookie);
                if(StringUtils.isNotBlank(next)){
                    return "redirect:"+next;
                }
                return "redirect:/";
            }else{
                model.addAttribute("msg",map.get("mag"));
                return "login";
            }

        }catch (Exception e){
            model.addAttribute("msg","服务器拥挤");
            return "login";
        }
    }
    @RequestMapping("/logout")
    public String logout(Model model,@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }

}
