# 一、说明
此项目为spring cloud微服务学习示例项目。

----------
>| 项目名 | 说明 | 端口 |
>| - | - | - |
>| common | 公共配置等 | - |
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

# 三、注意事项
```
1、更改hosts文件，添加如下内容
  127.0.0.1       nodeA
  127.0.0.1       nodeB
```