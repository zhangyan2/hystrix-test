package com.zhangyan.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.alibaba.fastjson.JSONObject;
import com.zhangyan.config.HystrixConfig;
import com.zhangyan.model.BeanContext;
import com.zhangyan.model.Request;
import com.zhangyan.service.HystrixSenderService;
import com.zhangyan.utils.HttpClientUtil;
import org.apache.http.client.HttpClient;
import org.springframework.web.client.RestTemplate;

/**
 * @author messi
 * @date 2019/7/13 15:55
 */
//@Component
public class HystrixInvocation extends HystrixCommand<JSONObject> {

    private BeanContext beanContext;

    private RestTemplate restTemplate;


    public HystrixInvocation(RestTemplate restTemplate, HystrixConfig hystrixConfig,
                             BeanContext beanContext) {

        super(hystrixConfig.buildHystrixInvocationSetter(beanContext.getProductId(), beanContext.getAction()));
        this.beanContext = beanContext;
        this.restTemplate = restTemplate;
    }

    @Override
    protected JSONObject run() throws Exception {
        // 模拟直接调用机构, 进行数据返回
        String postResult = restTemplate.postForObject("http://hystrix-service/hystrix",
                    beanContext.getData(), String.class);
        return JSONObject.parseObject(postResult);
    }



    @Override
    protected JSONObject getFallback() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "i am fallback");
        return jsonObject;
    }
}
