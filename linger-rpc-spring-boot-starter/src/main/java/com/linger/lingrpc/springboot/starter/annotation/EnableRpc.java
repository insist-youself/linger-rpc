package com.linger.lingrpc.springboot.starter.annotation;

import com.linger.lingrpc.springboot.starter.bootstrap.RpcConsumerBootstrap;
import com.linger.lingrpc.springboot.starter.bootstrap.RpcInitBootstrap;
import com.linger.lingrpc.springboot.starter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用 RPC 注释
 *
 * @author linger
 * @date 2024/4/2 20:31
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class,
        RpcConsumerBootstrap.class})
public @interface EnableRpc {

    /**
     * 需要启动 server
     */
    boolean needServer() default true;
}
