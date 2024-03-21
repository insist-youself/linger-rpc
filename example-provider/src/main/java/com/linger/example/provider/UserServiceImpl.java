package com.linger.example.provider;

import com.linger.example.common.model.User;
import com.linger.example.common.service.UserService;

/**
 * 用户服务实现类
 *
 * @author linger
 * @date 2024/3/21 18:33
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
