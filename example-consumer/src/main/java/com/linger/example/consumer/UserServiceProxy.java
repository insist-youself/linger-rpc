package com.linger.example.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.linger.example.common.model.User;
import com.linger.example.common.service.UserService;
import com.linger.linrpc.model.RpcRequest;
import com.linger.linrpc.model.RpcResponse;
import com.linger.linrpc.serializer.JdkSerializer;

import java.io.IOException;

/**
 * 静态代理（灵活性、扩展性差）
 *
 * @author linger
 * @date 2024/3/21 21:50
 */
public class UserServiceProxy implements UserService {
    @Override
    public User getUser(User user) {
        //指定序列器
        JdkSerializer serializer = new JdkSerializer();
        // 发请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterType(new Class[]{User.class})
                .args(new Object[]{user})
                .build();
        try {
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            byte[] result;
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                result = httpResponse.bodyBytes();
            }
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (User) rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
