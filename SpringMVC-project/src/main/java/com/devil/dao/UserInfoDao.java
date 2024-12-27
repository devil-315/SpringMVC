package com.devil.dao;

import com.devil.entity.pojo.Userinfo;
import com.devil.entity.query.UserinfoQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ClassName：UserDao
 *
 * @author: Devil
 * @Date: 2024/9/12
 * @Description:
 * @version: 1.0
 */
/*
* 用户信息 数据库操作接口*/
public interface UserInfoDao {
    Integer selectCount(Userinfo userinfo);

    Integer insert(Userinfo userinfo);

    Integer updateByUserId(@Param("userinfo") Userinfo userinfo);

    Userinfo selectByUserId(@Param("userId") Integer userId);


    Integer updatePassword(@Param("userinfo") Userinfo userinfo);

    Integer deleteUserinfoByUserId(@Param("userId") Integer userId);

    List<Userinfo> getAllUser(@Param("pageStart") Integer pageStart,@Param("pageSize") Integer pageSize);

    Integer selectCounts();

    List<Userinfo> selectListByParam(@Param("query") UserinfoQuery userinfoQuery);
}
