package com.linger.examplespringbootprovider;

import com.linger.example.common.model.User;
import com.linger.example.common.service.UserService;
import com.linger.lingrpc.springboot.starter.annotation.RpcService;
import org.springframework.stereotype.Service;

/**
 * @author linger
 * @date 2024/4/2 21:54
 */
@Service
@RpcService
public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
