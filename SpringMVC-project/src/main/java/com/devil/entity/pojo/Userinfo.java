package com.devil.entity.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * ClassName：Userinfo
 *
 * @author: Devil
 * @Date: 2024/9/12
 * @Description:
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Userinfo {
    /*
    * 用户名id
    * */
    private Integer userId;

    /*
    * 用户名
    * */
    private String userName;

    /*
    * 手机号
    * */
    private String phone;

    /*
    * 密码
    * */
    private String password;

    /*
    * 出生年月
    * */
    //将Date 转成String 后台传给前台
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    // 将String 转成Date ，前台传给后台
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /*
    * 性别  0:女 1:男
    * */
    private Integer sex;

    /*
    * 职位 0:程序员 1：测试 2：管理员 可多选
    * */
    private Integer position;

    /*
    * 角色 0:普通用户 1:组长 2:经理 3:管理员 可多选
    * */
    private String roles;

    /*
    * 创建时间
    * */
    //将Date 转成String 后台传给前台
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    // 将String 转成Date ，前台传给后台
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
