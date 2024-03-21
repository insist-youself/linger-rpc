package com.linger.example.consumer;

import com.linger.example.common.model.User;
import com.linger.example.common.service.UserService;

/**
 * 简易服务消费者示例
 *
 * @author linger
 * @date 2024/3/21 18:41
 */
public class EasyConsumerExample {
    public static void main(String[] args) {
        // todo 需要获取 UserService 的实现类对象
        UserService userService = null;
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
