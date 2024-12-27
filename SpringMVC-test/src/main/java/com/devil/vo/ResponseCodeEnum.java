package com.devil.vo;

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
    CODE_500(500,"请求失败");
    private Integer code;
    private String msg;

    ResponseCodeEnum() {
    }

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
