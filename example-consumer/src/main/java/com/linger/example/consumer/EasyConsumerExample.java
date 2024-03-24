package com.linger.example.consumer;

import com.linger.example.common.model.User;
import com.linger.example.common.service.UserService;
import com.linger.linrpc.proxy.ServiceProxyFactory;

/**
 * 简易服务消费者示例
 *
 * @author linger
 * @date 2024/3/21 18:41
 */
public class EasyConsumerExample {
    public static void main(String[] args) {
        // 静态代理
        //UserService userService = new UserServiceProxy();
        //动态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("linger");
        //调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
