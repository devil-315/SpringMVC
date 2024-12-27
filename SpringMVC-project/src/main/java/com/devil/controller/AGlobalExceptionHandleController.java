package com.devil.controller;

import com.devil.entity.enums.ResponseCodeEnum;
import com.devil.entity.vo.ReponseVo;
import com.devil.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.BindException;

/**
 * ClassName：AGlobalExceptionHandleController
 *
 * @author: Devil
 * @Date: 2024/9/13
 * @Description:
 * @version: 1.0
 */
//拦截异常并且统一处理
@RestControllerAdvice
public class AGlobalExceptionHandleController {
    private static final String STATUC_ERROR = "error";

    @ExceptionHandler(value = Exception.class)
    Object handleException(Exception e, HttpServletRequest request){
        System.out.println("请求错误，请求地址：" + request.getRequestURI());
        System.out.println("错误信息:" + e);

        ReponseVo reponseVo = new ReponseVo();
        //404：页面不存在
        if(e instanceof NoHandlerFoundException){
            reponseVo.setCode(ResponseCodeEnum.CODE_404.getCode());
            reponseVo.setMessage(ResponseCodeEnum.CODE_404.getMsg());
            reponseVo.setStatus(STATUC_ERROR);
        } else if (e instanceof BusinessException) {
            //业务错误(自定义异常类型)
            BusinessException businessException = (BusinessException) e;
            reponseVo.setCode(ResponseCodeEnum.CODE_600.getCode());
            reponseVo.setMessage(businessException.getMessage());
            reponseVo.setStatus(STATUC_ERROR);
        } else {
            //500 服务器返回错误，请联系管理员
            reponseVo.setCode(ResponseCodeEnum.CODE_500.getCode());
            reponseVo.setMessage(ResponseCodeEnum.CODE_500.getMsg());
            reponseVo.setStatus(STATUC_ERROR);
        }
        return reponseVo;
    }
}
