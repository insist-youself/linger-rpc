package com.linger.example.consumer;

import com.linger.example.common.model.User;
import com.linger.example.common.service.UserService;
import com.linger.linrpc.config.RpcConfig;
import com.linger.linrpc.proxy.ServiceProxyFactory;
import com.linger.linrpc.utils.ConfigUtils;

/**
 * 简易服务消费者示例
 *
 * @author linger
 * @date 2024/3/23 22:20
 */
public class ConsumerExample {
    public static void main(String[] args) {
//        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
//        System.out.println(rpc);
        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("linger");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
        Integer number = userService.getNumber();
        System.out.println(number);
    }
}
