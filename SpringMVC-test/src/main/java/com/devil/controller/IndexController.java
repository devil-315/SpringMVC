package com.devil.controller;

import com.devil.entity.User;
import com.devil.service.UserService;
import com.devil.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName：IndexController
 *
 * @author: Devil
 * @Date: 2024/9/11
 * @Description:
 * @version: 1.0
 */
@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/","/index","/index.html"})
    public String index(){
        return "index";
    }

    @ResponseBody
    @RequestMapping("/checkUsername")
    public ResponseVo checkUsername(@RequestParam("username") String username){
        User user= userService.checkUsername(username);
        if(user == null){
            return ResponseVo.fail(null);
        }
        return ResponseVo.ok(null);
    }
}
