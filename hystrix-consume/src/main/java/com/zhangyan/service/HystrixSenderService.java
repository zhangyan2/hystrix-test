package com.zhangyan.service;

import com.zhangyan.model.BeanContext;
import com.zhangyan.model.Request;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author messi
 * @date 2019/7/13 23:32
 */
@FeignClient(name= "hystrix-service")
public interface HystrixSenderService {

    @RequestMapping(value = "/hystrix")
    String send();

}
