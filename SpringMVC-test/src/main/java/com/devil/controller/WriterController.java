package com.devil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName：WriterController
 *
 * @author: Devil
 * @Date: 2024/9/12
 * @Description:
 * @version: 1.0
 */
@Controller
public class WriterController {

    //如果使用的时@Controller,想要返回数据，不进行视图跳转，可以添加 @ResponseBody
    @ResponseBody
    @RequestMapping("/writer")
    public String writer(){
        return "hello world";
    }
}
