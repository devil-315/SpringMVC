package com.devil.controller;

import com.devil.entity.User;
import com.devil.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ClassName：UserController
 *
 * @author: Devil
 * @Date: 2024/9/12
 * @Description:
 * @version: 1.0
 */
@Controller
//这个方法中的所有请求路径前缀
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //此时的真实访问路径为： 127.0.0.1:8080/user/show
    @RequestMapping("show")
    public String toShow(){
        return "show";
    }

    //@RequestMapping(value="/login",method = RequestMethod.POST)
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session){
        User user = userService.login(username,password);

        if(user != null){
            //设置 session
            session.setAttribute("session_key",username);
            return "show";
        }
        return "loginUserError";
    }
}

