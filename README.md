# 一、项目说明
此项目为spring cloud微服务学习示例项目。

----------
> <table>
> 	<tr>
> 		<th>模块</th>
> 		<th>项目名</th>
> 		<th>说明</th>
> 		<th>端口</th>
> 	</tr>
> 	<tr>
> 		<td>公共配置</td>
> 		<td>common</td>
> 		<td>-</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td rowspan=3>服务注册中心</td>
> 		<td>demo-eureka-module</td>
> 		<td>服务注册中心模块父项目</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td>eureka-nodeA</td>
> 		<td>eureka节点A</td>
> 		<td>10001</td>
> 	</tr>
> 	<tr>
> 		<td>eureka-nodeB</td>
> 		<td>eureka节点B</td>
> 		<td>10002</td>
> 	</tr>
> 	<tr>
> 		<td>ORM</td>
> 		<td>demo-mapper-common</td>
> 		<td>mybatis、mapper封装</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td rowspan=4>订单模块</td>
> 		<td>demo-order-module</td>
> 		<td>订单模块父项目</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td>demo-order-service</td>
> 		<td>订单服务</td>
> 		<td>20011</td>
> 	</tr>
> 	<tr>
> 		<td>demo-order-base-domain</td>
> 		<td>订单服务自动生成的DAO代码</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td>demo-order-rpc</td>
> 		<td>订单服务rpc接口封装</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td rowspan=4>商品模块</td>
> 		<td>demo-product-module</td>
> 		<td>商品模块父项目</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td>demo-product-service</td>
> 		<td>商品服务</td>
> 		<td>20021</td>
> 	</tr>
> 	<tr>
> 		<td>demo-product-base-domain</td>
> 		<td>商品服务自动生成的DAO代码</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td>demo-product-rpc</td>
> 		<td>商品服务rpc接口封装</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td>服务监控</td>
> 		<td>spring-boot-admin</td>
> 		<td>-</td>
> 		<td>10011</td>
> 	</tr>
> 	<tr>
> 		<td>服务网关</td>
> 		<td>spring-cloud-gateway</td>
> 		<td>-</td>
> 		<td>80</td>
> 	</tr>
> 	<tr>
> 		<td>相关文档</td>
> 		<td>sql</td>
> 		<td>服务相关sql</td>
> 		<td>-</td>
> 	</tr>
> </table>

----------

# 二、技术栈
| 名称 | 说明 |
| - | - |
| spring boot | 手脚架 |
| spring cloud gateway | 服务网关 |
| eureka | 服务注册 |
| [spring boot admin](https://github.com/codecentric/spring-boot-admin) | 服务监控 |
| feign | 声明式服务调用 |
| sleuth、log4j2 | 链路追踪、日志 |
| [mybatis](http://www.mybatis.org/mybatis-3/zh/index.html) 、[mapper](https://github.com/abel533/Mapper)| ORM |
| [swagger](https://swagger.io/) | 接口文档 |
| [Lombok](https://www.projectlombok.org/) | 简化代码 |


# 三、笔记
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

# 四、注意事项
```
1、更改hosts文件，添加如下内容
  127.0.0.1       nodeA
  127.0.0.1       nodeB
```