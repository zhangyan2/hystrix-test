package com.zhangyan.service;

import com.zhangyan.model.BeanContext;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author messi
 * @date 2019/7/13 23:32
 */

public interface HystrixService  {


    String invoke(BeanContext beanContext);
}
