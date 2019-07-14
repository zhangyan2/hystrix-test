package com.zhangyan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhangyan.config.HystrixConfig;
import com.zhangyan.hystrix.HystrixInvocation;
import com.zhangyan.model.BeanContext;
import com.zhangyan.service.HystrixSenderService;
import com.zhangyan.service.HystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author messi
 * @date 2019/7/13 16:49
 */
@Service
public class HystrixServiceImpl implements HystrixService {

    @Autowired
    private HystrixConfig hystrixConfig;

    @Autowired
    RestTemplate restTemplate;


    /**
     * 请求执行
     * @param beanContext
     * @return
     */
    public String invoke(BeanContext beanContext) {
        JSONObject result = new HystrixInvocation(restTemplate, hystrixConfig, beanContext)
                .execute();
        return result.toJSONString();
    }
}
