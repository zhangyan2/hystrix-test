# hystrix-test
工作中使用hystrix的示例项目, 借此可以深入研究hystrix, 也可以学习其他springclound组件

## 使用步骤

#### 1.启动顺序

eureka-server ---> hystrix-service ---> hystrix-consume ---> hystrix-dashboard

#### 2.访问

浏览器访问hystrix-consume项目:

<http://localhost:8081/hystrix-test?productId=3000&action=applyAccess>

监控平台:

<http://localhost:8083/hystrix>

查看所要监控机器指标:

在红色框输入:

http://localhost:8081/hystrix.stream

![微信截图_20190714202929](D:\work\code\test\hystrix-test\微信截图_20190714202929.png)

就会出现监控指标图:

![微信截图_20190714203151](D:\work\code\test\hystrix-test\微信截图_20190714203151.png)