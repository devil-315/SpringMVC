package com.devil.controller;

import com.alibaba.fastjson.JSON;
import com.devil.entity.User;
import com.devil.vo.ResponseVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * ClassName：TestController
 *
 * @author: Devil
 * @Date: 2024/9/12
 * @Description:
 * @version: 1.0
 */
//如果确保整个 Conroller类中都不需要跳转页面，二是返回json或者字符串就直接使用@RestController
@RestController
public class TestController {

    @RequestMapping("hello")
    public String index(){
        return "index";
    }


    @RequestMapping("/test")
    public ResponseVo test(){
        return ResponseVo.ok("index");
    }

    @RequestMapping("/test02")
    public String  test02(){
        //fastJson
        User user = new User(1, "HAHA", "123", "123", new Date(new Date().getTime()));
        return JSON.toJSONString(user);
    }
}
