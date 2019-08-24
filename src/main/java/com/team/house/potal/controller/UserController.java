package com.team.house.potal.controller;

import com.team.house.entity.Users;
import com.team.house.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/page")//这里注意要在springmvc中增添扫描注解controller控制器路径
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/reg")
    public String reg(Users users){
        System.out.println("========================================="+users.toString());
        int temp=userService.regUser(users);
        if(temp>0)
            return "login";
        else
            return "regs";
    }
    //检查用户名是否存在
    @RequestMapping("/checkUserName")//同步返回页面、异步返回数据
    @ResponseBody
    public Map<String,Object> checkUserName(String username){
        int temp=userService.isUserNameExists(username);
        //返回数据
        Map<String,Object> map=new HashMap<>();
        map.put("result",temp);
        return map;
    }
    //实现登录
    @RequestMapping("/login")//同步返回页面、异步返回数据
    public String checkUserName(String username, String password, Model model, HttpSession session){
        Users user = userService.login(username, password);
        if(user==null) {
            model.addAttribute("info","用户名和密码不正确");
            return "login";
        }
        else {
            //注意：只要登入，必须使用session（分布式规划，分布式session,会用到redis）保存登入人的信息或者cookie（将用户信息保存在客户段的一种手段，即本地保存，常用于皮肤以及个性化设置的时候）保存
            session.setAttribute("userInfo",user);
            //设置保存时间
            session.setMaxInactiveInterval(600);//秒，即设置了十分钟
            return "redirect:getHouse";
        }
    }
}
