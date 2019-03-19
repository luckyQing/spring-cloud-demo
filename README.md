# 一、说明
此项目为spring cloud微服务学习示例项目。

----------
>| 项目名 | 说明 | 端口 |
>| - | - | - |
>| common | 公共配置等 | - |
>| demo-order-service | 订单服务 | 20011 |
>| demo-product-service | 商品服务 | 20021 |
>| eureka-nodeA | eureka节点A | 10001 |
>| eureka-nodeB | eureka节点B | 10002 |
>| spring-boot-admin | 服务监控 | 10011 |
>| spring-cloud-gateway | 服务网关 | 80 |

----------

# 二、笔记
## （一）@EnableDiscoveryClient与@EnableEurekaClient区别
```
如果选用的注册中心是eureka，那么就推荐@EnableEurekaClient，如果是其他的注册中心，那么推荐使用@EnableDiscoveryClient。
```
## （二）spring boot引入其他的yaml文件
```
比如src/main/resources下有application-email.yml、application-mq.yml等文件，在yaml中添加
spring:
  profiles:
    include: email,mq

或
spring:
  profiles:
    include:
      - 子项1
      - 子项2
      - 子项3  
```

# 三、注意事项
```
1、更改hosts文件，添加如下内容
  127.0.0.1       nodeA
  127.0.0.1       nodeB
```