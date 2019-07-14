package com.zhangyan.web;

import com.zhangyan.model.BeanContext;
import com.zhangyan.model.Request;
import com.zhangyan.service.HystrixSenderService;
import com.zhangyan.service.impl.HystrixServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HystrixController {

    @Autowired
    HystrixServiceImpl hystrixService;



    private static Integer clickCount = 0;

    @RequestMapping(value = "/hystrix-test", method = RequestMethod.GET)
    public String test(@RequestParam Integer productId,
                       @RequestParam String action) {
        BeanContext<Request> beanContext = new BeanContext();
        beanContext.setProductId(productId);
        beanContext.setAction(action);

        Request request = new Request();
        request.setName("hello messi");

        beanContext.setData(request);

        clickCount++;

        System.out.println("=======请求已经点击了=" + clickCount);

        String result = hystrixService.invoke(beanContext);
        System.out.println("");

        return result;
    }

}