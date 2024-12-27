package com.devil.service;

import com.devil.entity.User;
import org.springframework.stereotype.Service;

/**
 * ClassName：UserService
 *
 * @author: Devil
 * @Date: 2024/9/12
 * @Description:
 * @version: 1.0
 */
public interface UserService {

    User login(String username, String password);

    User checkUsername(String username);
}
