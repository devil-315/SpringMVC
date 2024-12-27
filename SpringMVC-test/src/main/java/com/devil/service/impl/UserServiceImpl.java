package com.devil.service.impl;

import com.devil.dao.UserDao;
import com.devil.entity.User;
import com.devil.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName：UserServiceImpl
 *
 * @author: Devil
 * @Date: 2024/9/12
 * @Description:
 * @version: 1.0
 */
//业务逻辑接口
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User login(String username, String password) {
       User user =  userDao.findByUsername(username,password);
        System.out.println(user);
       //用户名或者密码错误
       if(user == null){
           return null;
       }
       return user;
    }

    @Override
    public User checkUsername(String username) {
        return userDao.checkUsername(username);
    }
}

