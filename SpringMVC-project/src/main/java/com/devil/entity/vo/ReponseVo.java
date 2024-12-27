package com.devil.entity.vo;

import com.devil.entity.enums.ResponseCodeEnum;
import lombok.Data;

/**
 * ClassName：ReponseVo
 *
 * @author: Devil
 * @Date: 2024/9/12
 * @Description:
 * @version: 1.0
 */
@Data
public class ReponseVo<T> {
    private String status;
    private Integer code;
    private String message;
    private T data;

    public ReponseVo() {
    }

    protected static<T> ReponseVo<T> build(T data){
        ReponseVo<T> reponseVo = new ReponseVo<>();
        if(data != null){
            reponseVo.setData(data);
        }
        return reponseVo;
    }

    public static<T> ReponseVo<T> build(Integer code,String message, String status){
        ReponseVo<T> reponseVo = build(null);
        reponseVo.setCode(code);
        reponseVo.setMessage(message);
        reponseVo.setStatus(status);
        return reponseVo;
    }
    public static<T> ReponseVo<T> build(T data, ResponseCodeEnum responseCodeEnum,String status){
        ReponseVo<T> reponseVo = build(data);
        reponseVo.setCode(responseCodeEnum.getCode());
        reponseVo.setMessage(responseCodeEnum.getMsg());
        reponseVo.setStatus(status);
        return reponseVo;
    }

    //操作成功
    public static<T> ReponseVo<T> ok(T data){
        return build(data,ResponseCodeEnum.CODE_200,"success");
    }

    public static<T> ReponseVo<T> ok(){
        return ReponseVo.ok(null);
    }

    //操作失败
    public static<T> ReponseVo<T>fail(T data){
        return build(data,ResponseCodeEnum.CODE_600,"error");
    }

    public static<T> ReponseVo<T> fail(){
        return ReponseVo.fail(null);
    }
}
