/**
 * Author : MIAOHY
 * Time :2019/7/6 16:41
 * Beauty is better than ugly!
 */
package com.miaohy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {

    @RequestMapping("/demo")
    public String demo(Model model){
       model.addAttribute("uid","123");
       model.addAttribute("name","demo");
        return "Hello";
    }
}
