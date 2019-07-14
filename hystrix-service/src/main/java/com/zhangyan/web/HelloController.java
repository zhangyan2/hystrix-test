package com.zhangyan.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangyan.model.Request;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

@RestController
public class HelloController {

	private final Logger logger = Logger.getLogger(getClass());

	private static Long timeHolder = null;

	private Integer clickCount = 0;

	@RequestMapping(value = "/hystrix", method = RequestMethod.POST)
	public String hello(@RequestBody Request request) throws Exception {

		clickCount++;

		System.out.println("当前请求了=" + clickCount);
		System.out.println("请求数据=" + JSON.toJSONString(request));

		if (!Objects.isNull(timeHolder)) {
			Long currTime = System.currentTimeMillis();
			if ((currTime - timeHolder) > 30000) {
				logger.info("当前服务器恢复正常 time=" + (currTime - timeHolder)/1000 + "=======");
				timeHolder = null;
			} else {
				logger.info("当前服务器处于宕机中====");
				// 模拟宕机
				Thread.sleep(10000);
			}
		} else {
			int flag = new Random().nextInt(2);
			logger.info("当前服务器" + (flag == 0 ? "============正常========" : "======挂了======"));
			if (flag == 1) {
				timeHolder = System.currentTimeMillis();
				// 模拟宕机
				Thread.sleep(10000);
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", "messi");
		return jsonObject.toJSONString();
	}

	@RequestMapping(value = "/hystrix1")
	public String hello1() throws Exception {
		return "123";
	}


}