# 一、项目说明
此项目为spring cloud微服务学习示例项目。

----------
> <table>
> 	<tr>
> 		<th width="120px">模块</th>
> 		<th width="210px">项目名</th>
> 		<th>说明</th>
> 		<th width="70px">端口</th>
> 	</tr>
> 	<tr>
> 		<td>公共配置</td>
> 		<td>common</td>
> 		<td>日志切面、rpc测试挡板、接口mock数据、公共实体对象、工具类、公共拦截器、log4j2日志模板等</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td>代码生成</td>
> 		<td>demo-code-auto-generate</td>
> 		<td>自动生成entity、dao、biz、service、controller、公共配置等业务无关代码</td>
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
> 		<td rowspan=3>电商模块</td>
> 		<td>demo-mall-module</td>
> 		<td>电商模块父项目</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td>demo-mall-order-service</td>
> 		<td>订单服务</td>
> 		<td>20011</td>
> 	</tr>
> 	<tr>
> 		<td>demo-mall-product-service</td>
> 		<td>商品服务</td>
> 		<td>20021</td>
> 	</tr>
> 	<tr>
> 		<td>电商模块的rpc接口</td>
> 		<td>demo-mall-rpc</td>
> 		<td>包括请求、响应对象、rpc接口</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td>ORM</td>
> 		<td>demo-mapper-common</td>
> 		<td>mybatis、mapper封装。业务无关mapper动态生成，sql日志打印等</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td rowspan=2>服务合并模块</td>
> 		<td>demo-merge-module</td>
> 		<td>合并模块父项目</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td>demo-merge-mall</td>
> 		<td>电商合并服务</td>
> 		<td>30001</td>
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
| [spring boot](https://spring.io/projects/spring-boot/) | 手脚架 |
| [spring cloud gateway](https://spring.io/projects/spring-cloud-gateway) | 服务网关 |
| [eureka](https://spring.io/projects/spring-cloud-netflix) | 服务注册 |
| [spring boot admin](https://github.com/codecentric/spring-boot-admin) | 服务监控 |
| [openfeign](https://spring.io/projects/spring-cloud-openfeign) | 声明式服务调用 |
| [sleuth](https://spring.io/projects/spring-cloud-sleuth)、[log4j2](https://logging.apache.org/log4j/2.x/) | 链路追踪、日志 |
| [mybatis](http://www.mybatis.org/mybatis-3/zh/index.html) 、[mapper](https://github.com/abel533/Mapper)| ORM |
| [swagger](https://swagger.io/)、[gitbook](https://www.gitbook.com/) | 接口文档 |
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

## （三）sleuth
### 3.1、log4j2集成sleuth
```
日志打印pattern中加入“[%X{X-B3-TraceId},%X{X-B3-SpanId},%X{X-B3-ParentSpanId},%X{X-Span-Export}]”。

各字段解释：
TraceId为此次调用链共享id；
SpanId本应用唯一id；
ParentSpanId为上级应用唯一id；
X-Span-Export是否是发送给Zipkin。
```
### 3.2、sleuth的原理
```
Spring Cloud Sleuth可以追踪10种类型的组件：async、Hystrix、messaging、websocket、rxjava、scheduling、web（Spring MVC Controller，Servlet）、webclient（Spring RestTemplate）、Feign、Zuul。

例如scheduling
原理是AOP（TraceSchedulingAspect、TraceSchedulingAutoConfiguration）处理Scheduled注解，只要是在IOC容器中的Bean带有@Scheduled注解的方法的调用都会被sleuth处理。

其他组件实现见包org.springframework.cloud.sleuth.instrument。
```

# 四、注意事项
```
1、更改hosts文件，添加如下内容
  127.0.0.1       nodeA
  127.0.0.1       nodeB
```