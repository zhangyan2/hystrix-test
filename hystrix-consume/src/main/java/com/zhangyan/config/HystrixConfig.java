package com.zhangyan.config;

import com.netflix.hystrix.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author messi
 * @date 2019/7/13 16:11
 */
@Component
public class HystrixConfig {

    /**
     * 代理层hystrix线程池个数
     */
    private final static Integer ORGPROXY_POOL_COUNT = 5;

    /**
     * 代理层hystrix线程池前缀
     */
    private final static String ORGPROXY_POOL_PREFIX = "orgproxy_pool_";

    /**
     * 超时时间200, 定义为超时
     */
    private final static Integer TIME_OUT = 200;

    /**
     * 核心线程数
     */
    private final static Integer CORE_SIZE = 100;

    /**
     * 队列长度
     */
    private final static Integer MAX_QUEUE_SIZE = 10000;

    public HystrixCommand.Setter buildHystrixInvocationSetter(Integer productId, String action) {
        if (Objects.isNull(productId)) {
            throw new NullPointerException("prodoctId 为空");
        }
        if (StringUtils.isBlank(action)) {
            throw new NullPointerException("action 为空!");
        }
        String groupKey = productId + "_" + action;
        String commandKey = groupKey;
        return HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(ORGPROXY_POOL_PREFIX + (productId % ORGPROXY_POOL_COUNT)))
                .andThreadPoolPropertiesDefaults(
                        // 接口级 隔离环境线程池大小(允许的最大并发调用)
                        HystrixThreadPoolProperties.Setter()
                                .withCoreSize(CORE_SIZE) // CORE_SIZE:100
                                .withMaxQueueSize(MAX_QUEUE_SIZE) // MAX_QUEUE_SIZE:1000
                                // 这个值必须 > 客户端并发数 否则小于部分的请求将无法进入队列
                                .withQueueSizeRejectionThreshold(100))
                .andCommandPropertiesDefaults(
                        // 方法级 超时
                        HystrixCommandProperties.Setter()
                                .withExecutionTimeoutInMilliseconds(TIME_OUT) // TIME_OUT:200
                                // 方法级 手动熔断开关
                                .withCircuitBreakerForceOpen(false));
    }
}
