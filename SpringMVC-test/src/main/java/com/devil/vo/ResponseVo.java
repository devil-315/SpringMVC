package com.devil.vo;

import lombok.Data;

/**
 * ClassName：ResponseVo
 *
 * @author: Devil
 * @Date: 2024/9/12
 * @Description:
 * @version: 1.0
 */
@Data
public class ResponseVo<T> {
    private String status;
    private String info;
    private Integer code;
    private T data;

    public ResponseVo() {
    }
    public static <T> ResponseVo<T> build(T data) {
        ResponseVo<T> responseVo = new ResponseVo<>();
        if(data != null){
            responseVo.setData(data);
        }
        return responseVo;
    }


    public static <T> ResponseVo<T> build(T data,ResponseCodeEnum responseCodeEnum,String status) {
        ResponseVo<T> responseVo = build(data);
        responseVo.setCode(responseCodeEnum.getCode());
        responseVo.setInfo(responseCodeEnum.getMsg());
        responseVo.setStatus(status);
        return responseVo;
    }

    //请求成功
    public static<T> ResponseVo<T> ok(T data){
            return build(data,ResponseCodeEnum.CODE_200,"success" );
    }

    //请求失败
    public static<T> ResponseVo<T> fail(T data){
        return build(data,ResponseCodeEnum.CODE_500,"error" );
    }

}
