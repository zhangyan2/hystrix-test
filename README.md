---

---

# hystrix-test
工作中使用hystrix的示例项目, 借此可以深入研究hystrix, 也可以学习其他springclound组件

## 使用步骤

#### 1.启动顺序

eureka-server ---> hystrix-service ---> hystrix-consume ---> hystrix-dashboard

#### 2.访问

浏览器访问hystrix-consume项目:

<http://localhost:8081/hystrix-test?productId=3000&action=applyAccess>

productId : 对应机构id

action: 对应请求接口名

监控平台:

<http://localhost:8083/hystrix>

查看所要监控机器指标:

在红色框输入:

http://localhost:8081/hystrix.stream

![微信截图_20190714202929](/微信截图_20190714202929.png)

就会出现监控指标图:

![微信截图_20190714203151](/微信截图_20190714203151.png)



## 架构演化

#### 原有架构图

![原有系统架构](/原有系统架构.png)

#### 改进后的架构:

![现有系统架构](/现有系统架构.png)





















