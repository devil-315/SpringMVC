package com.devil.entity.enums;

/**
 * ClassName：ResponseCodeEnum
 *
 * @author: Devil
 * @Date: 2024/9/12
 * @Description:
 * @version: 1.0
 */
public enum ResponseCodeEnum {
    CODE_200(200,"请求成功"),
    CODE_404(404,"请求路径不存在"),
    CODE_600(600,"请求参数错误"),
    CODE_601(601,"信息已存在"),
    CODE_500(500,"服务器返回错误，请联系管理员");

    private Integer code;
    private String msg;



     ResponseCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
