package com.zhangyan.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;

/**
 * Created by zhangyan on 2019/1/22
 */
@Data
@ToString
public class BeanContext<T> {

    /**
     * 追踪id
     */
    private Long traceId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 此次请求的行为(对应方法名)
     */
    private String action;



    /**
     * 代理层封装之后, 请求机构的业务数据
     */
    private JSONObject busiData;


    /**
     * 请求机构的参数, 包含(业务数据, appId)等
     */
    private JSONObject reqParams;

    private T data;


    public BeanContext() {
    }

    public BeanContext(Integer productId, String action) {
        this.productId = productId;
        this.action = action;
    }

}
