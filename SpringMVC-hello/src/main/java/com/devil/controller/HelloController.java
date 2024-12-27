package com.devil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName：HelloController
 *
 * @author: Devil
 * @Date: 2024/9/11
 * @Description:
 * @version: 1.0
 */
//此时 @Controller 返回的是视图
@Controller
public class HelloController {

    //请求路径的映射
    //真实的访问路径： 127.0.0:8080/
    @RequestMapping("/")
    public String index(){

        //返回视图名称为 index 的视图
        return "index";
    }

    @RequestMapping("/hello")
    public String hello(){

        //返回视图名称为 index 的视图
        return "hello";
    }
}
