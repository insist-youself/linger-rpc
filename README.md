# Linger PRC框架 

## 项目介绍🎉
一款基于 Java + Etcd + Vert.x 实现的轻量级 RPC 框架。提供服务注册，发现，负载均衡，重试和容错策略，支持API调用，Spring集成和Spring Boot starter使用。是一个学习RPC工作原理的良好示例。

通过这个简易项目的学习，可以让你从零开始实现一个类似 Dubbo 服务框架 mini 版RPC，学到 RPC 的底层原理以及各种 Java 底层原理技术的编码实践。下面看一下RPC的基本调用流程：

<img src="https://webs-tlias-plus.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202024-07-26%20231407.jpg" width="600px">


## 结构 & 功能🌍
### 目录结构
````
linger-rpc框架
├─linger-rpc-core	            -- rpc 核心代码
├─linger-rpc-easy	            -- rpc 简易实现
├─example-common                    -- 示例代码公用模块
├─example-consumer	            -- 示例服务消费者
├─example-provider                  -- 示例服务提供者	 
├─example-springboot-consumer       -- 示例服务消费者（Spring Boot 框架）
├─example-springboot-provider       -- 示例服务提供者（Spring Boot 框架）
└─linger-rpc-spring-boot-starter    -- 注解驱动 (实现在 Spring Boot 项目中快速使用)
````

### 核心模块结构
````
├── bootstrap                      -> 服务提供者/消费者启动器
├── config                         -> 项目配置（注册中心、框架属性配置）
├── constant                       -> 相关常量
├── fault                          -> 故障保障措施（重试、容错）
├── loadbalancer                   -> 负载均衡策略
├── model                          -> 基本模版类 (请求、处理、元数据等类)
├── protocol                       -> 协议底层实现
├── proxy                          -> 全局异常
├── filter                         -> 责任链模式过滤请求
├── proxy                          -> 服务代理
├── registry                       -> 注册中心
├── serialize                      -> 序列化与反序列化策略
├── server                         -> 服务端协议配置
├── spi                            -> SPI自定义加载类
└── utils                          -> 项目工具包
````

### 功能
- 简单易学的代码和框架，在代码中含有大量注解 
- 基于 Vert.x 实现长连接通信，包括心跳检测、解决粘包半包等 
- 基于 Etcd、Zookeeper 实现分布式服务注册与发现 
- 实现了接口 Mock 服务方便接口的测试、开发和调试
- 实现了轮询、随机、加权随机等负载均衡算法 
- 实现了重试机制、容错机制，保证可靠性和可用性 
- 实现自定义协议（底层使用TCP）替代 HTTP，保证高效远程的网络传输
- 支持 JDK 动态代理方式 
- 支持 JSON、Hessian、Kryo、protobuf 的序列化方式
- 加入了 Spring Boot Starter, 方便快速使用

## 快速开始 🚀
### 环境准备
- Java17 及其以上版本
- Maven 3
- Etcd 单机或者集群实例

### 启动示例
#### 方式一：使用本项目中的测试用例
1. 将项目克隆到本地
````
git clone https://github.com/insist-youself/linger-rpc.git
````
2. IDEA打开项目

使用 IDEA 打开，等待项目初始化完成。

3. 运行 Etcd

如果没有安装的过需要先去下载。（本地使用可参考最后FQA）

4. 修改配置文件

修改 linger-rpc-core 中 config 目录下RegistryConfig配置文件中注册中心的地址(配置文件中 Etcd 默认的地址为localhost:2380)
![image](https://webs-tlias-plus.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202024-07-27%20003235.png)


4. 启动项目（按照图中顺序）

PS：启动项目前，必须要确保 Etcd 已启动.

<img src="https://webs-tlias-plus.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202024-07-27%20004208%281%29.png" width="500px">

- 最后，执行服务消费者的单元测试，验证能否跑通整个流程。
![img.png](https://webs-tlias-plus.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202024-07-27%20110812%281%29.png)

- 如下图，调用成功：
![img_1.png](https://webs-tlias-plus.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202024-07-27%20111142.jpg)

#### 方式二：将该rpc框架运用到自己项目中
1. 下载源码
````
git clone https://github.com/insist-youself/linger-rpc.git
````
2.  编译安装 jar 包到本地仓库（注意如果是服务器上面，需要上传到私服仓库）
````
mvn clean install
````
3. 新建Spring Boot Maven工程

在本地新建两个工程，用于模拟客户端和服务端。（这里以之前使用到的example为例）
<img src="https://webs-tlias-plus.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202024-07-27%20114910.jpg" width="450px">


4. 引入入依赖

在项目中的pom引入刚刚安装的依赖（客户端、服务端都需要引入）
``` xml
<dependency>
    <groupId>com.linger</groupId>
    <artifactId>linger-rpc-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

5. 定义服务接口
```java
public interface UserService {

    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);
    
}
```
6. 实现接口，使用自定义注解`@RpcService`暴露一个服务接口
```java
@RpcService
public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
```
7. 服务端启动类也要加上注解`@EnableRpc`
```java
@SpringBootApplication
@EnableRpc
public class ExampleSpringbootProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleSpringbootProviderApplication.class, args);
    }

}
```
8. 客户端启动类加上注解`@EnableRpc(needServer = false)`
```java
@SpringBootApplication
@EnableRpc(needServer = false)
public class ExampleSpringbootConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleSpringbootConsumerApplication.class, args);
    }

}
```

9. 使用自定义注解`@RpcReference`自动注入服务端暴露的接口服务
```java
@Service
public class ExampleServiceImpl{

    @RpcReference
    private UserService userService;

    public void test(){
        User user = new User();
        user.setName("Hello linger~~");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }

}
```
10. 启动项目 

启动服务端（服务提供者），再启动客户端（服务消费者）。

11. 测试结果

<img src="https://webs-tlias-plus.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202024-07-27%20114541%281%29.png" width="1000px">



## FAQ🔎
### Etcd的安装和可视化
#### 安装

进入Etcd官方的下载页：[https:/github.com/etcd-io/etcd/releases](https:/github.com/etcd-io/etcd/releases)

也可以在这里下载：[https:/etcd.io/docs/v3.2/install/](https:/etcd.io/docs/v3.2/install/)

找到自己操作系统的版本执行即可

安装完成后，会得到3个脚本：

- etcd: etcd服务本身

- etcdctl: 客户端，用于操作etcd,比如读写数据

- etcdutl: 备份恢复工具

执行etcd脚本后，可以启动etcd服务，服务默认占用2379和2380端口，作用分别如下：

- 2379:提供HTTP API服务，和etcdct交互

- 2380:集群中节点间通讯

window下执行成功显示如下：

<img src="https://webs-tlias-plus.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202024-07-27%20120231.jpg" width="800px">

#### Etcd可视化工具

一般情况下，我们使用数据存储中间件时，一定要有一个可视化工具，能够更直观清地管理已经存储的数据。比如
Redis Redis Desktop Manager.

同样的，Etcd也有一些可视化工具，比如：

etcdkeeper: https://github.com/evildecay/etcdkeeper/

kstone: https://github.com/kstone-io/kstone/tree/master/charts

安装后，执行命令，可以在指定端口启动可视化界面（默认是8080端口）。
```
./etcdkeeper -p 8080
```

安装后，访问本地 [http:/127.0.0.1:8080/etcdkeeper/](http:/127.0.0.1:8080/etcdkeeper/) ,就能看到可视化页面了，如图：

<img src="https://webs-tlias-plus.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202024-07-27%20120705.jpg" width="900px">