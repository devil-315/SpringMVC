package com.devil.dao;

import com.devil.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * ClassName：UserDao
 *
 * @author: Devil
 * @Date: 2024/9/12
 * @Description:
 * @version: 1.0
 */
//数据操作接口
@Repository
public interface UserDao {
    //根据用户名密码查询用户
    User findByUsername(@Param("username") String username,@Param("password") String password);

    User checkUsername(@Param("username") String username);
}
