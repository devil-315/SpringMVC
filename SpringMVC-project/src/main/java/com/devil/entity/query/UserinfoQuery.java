package com.devil.entity.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ClassName：UserinfoQuery
 *
 * @author: Devil
 * @Date: 2024/9/16
 * @Description:
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//用户信息的参数
public class UserinfoQuery extends BaseParam{
    /*
     * 用户名id
     * */
    private Integer userId;

    /*
     * 用户名
     * */
    private String userName;
    private String userNameFuzzy;

    /*
     * 手机号
     * */
    private String phone;
    private String phoneFuzzy;

    /*
     * 密码
     * */
    private String password;

    /*
     * 出生年月
     * */
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
    private Date createTime;
}
