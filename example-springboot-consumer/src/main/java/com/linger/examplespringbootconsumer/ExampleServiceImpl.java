package com.linger.examplespringbootconsumer;

import com.linger.example.common.model.User;
import com.linger.example.common.service.UserService;
import com.linger.lingrpc.springboot.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

/**
 * @author linger
 * @date 2024/4/2 21:54
 */
@Service
public class ExampleServiceImpl{

    @RpcReference
    private UserService userService;

    public void test(){
        User user = new User();
        user.setName("我是 流连~~");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }

}
