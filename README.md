# 一、项目说明
此项目为spring cloud微服务学习示例项目。

----------
> <table>
> 	<tr>
> 		<th width="150px">模块</th>
> 		<th width="225px">项目名</th>
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
> 		<td>电商模块rpc接口</td>
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


# 三、相关说明
## （一）接口协议
```
请求数据采用json格式，通过http body传输。
1、请求对象Req由head、body、sign三部分组成。body部分为请求的实际参数；head部分为app版本号，接口版本号，亲求时间戳（默认2分钟内有效），请求的token，交易流水号；sign为请求参数的签名。
{
	"body": {
		"products": [{
				"buyCount": 1,
				"productId": 4
			}
		]
	},
	"head": {
        "appVersion": "1.0.0",
		"apiVersion": "1.0.0",
		"timestamp": 1555778393862,
		"token": "string",
		"transactionId": "eb9f81e7cee1c000"
	},
	"sign": "string"
}

2、响应对象Resp组成
{
	"head": {
		"transactionId": null,
		"code": "100200",
		"msg": "成功",
		"error": null,
		"timestamp": 0
	},
	"body": {
		"id": "2",
		"name": "手机",
		"price": "1200"
	}
}
```

## （二）服务合并
```
单个服务以jar的形式，通过maven引入合并服务中。在单体服务中，feign接口通过http请求；服务合并后，feign接口通过内部进程的方式通信。
```

## （三）接口mock数据
```
接口通过切面拦截的方式，通过反射可以获取返回对象的所有信息，然后根据对象的属性类型，可以随机生成数据；对于特定要求的数据，可以制定mock规则，生成指定格式的数据。
```

## （四）单元测试
```
通过ServletContext的类型，可以知道当前环境是单元测试，还是非单元测试；从而动态的控制eureka的开、关，控制rpc的调用方式。
通过切面的方式，如果当前环境是单元测试，则直接拦截返回mock数据（mock数据在测试用例调用之前pop进队列，后面直接poll返回）；如果是非单元测试环境，则直接跳过，触发真实的http请求。
```

# 四、笔记
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

# 五、注意事项
```
1、更改hosts文件，添加如下内容
  127.0.0.1       nodeA
  127.0.0.1       nodeB
```