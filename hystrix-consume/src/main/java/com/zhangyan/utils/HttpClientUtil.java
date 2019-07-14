package com.zhangyan.utils;

import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * httpClient请求工具类 封装常用请求方法
 *
 * @author wangwenchang
 */
public final class HttpClientUtil {

    private static int defaultSocketTimeout = 15 * 1000;
    private static int defaultConnectTimeout = 10000;
    private static int defaultConnectionRequestTimeout = 10000;
    private static CloseableHttpClient httpClient;
    private static final int MAX_CONN_PER_ROUTE = 5;
    private static final RequestConfig defaultRequestConfig = RequestConfig
            .custom()
            .setConnectionRequestTimeout(defaultConnectionRequestTimeout)
            .setSocketTimeout(defaultSocketTimeout)
            .setConnectTimeout(defaultConnectTimeout).build();

    static {
        httpClient = HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig)
                .setMaxConnPerRoute(MAX_CONN_PER_ROUTE)
                .setMaxConnTotal(2 * MAX_CONN_PER_ROUTE).build();
    }

    public static CloseableHttpClient getHttpClientInstance() {
        return httpClient;
    }

    public static CloseableHttpClient getHttpClientInsClient(
            RequestConfig requestConfig) {
        return HttpClients.custom().setDefaultRequestConfig(requestConfig)
                .setMaxConnPerRoute(MAX_CONN_PER_ROUTE)
                .setMaxConnTotal(2 * MAX_CONN_PER_ROUTE).build();
    }

    /**
     * httpClient 发送get请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String get(String url) throws Exception {
        CloseableHttpResponse response = null;
        String result = null;
        try {
            HttpGet get = new HttpGet(url);
            response = httpClient.execute(get);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                System.out.println("the1 response status is not ok ,the status is " + response.getStatusLine().getStatusCode() + ",the1 url is " + url);
            }
            result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != response) {
                response.close();
            }
        }
    }

    /**
     * httpClient 需要加参数的get请求 会拼接参数
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String get(String url, Map<String, String> params) throws Exception {
        if (CollectionUtils.isEmpty(params)) {
            return get(url);
        }
        // 组装请求参数
        URIBuilder uriBuilder = new URIBuilder(url);
        for (Entry<String, String> entry : params.entrySet()) {
            uriBuilder.addParameter(entry.getKey(), entry.getValue());
        }
        return get(uriBuilder.toString());
    }

    /**
     * 以json的形式发送post请求
     *
     * @param url
     * @param paramJson
     * @return
     * @throws Exception
     */
    public static String postJson(String url, String paramJson)
            throws Exception {
        return postJson(url, "UTF-8", paramJson, httpClient);
    }

    /**
     * 以json的形式发送post请求
     *
     * @param url
     * @param charset
     * @param paramJson
     * @return
     * @throws Exception
     */
    public static String postJson(String url, String charset, String paramJson, CloseableHttpClient httpClient)
            throws Exception {
        CloseableHttpResponse response = null;
        String result = null;
        HttpPost post = null;
        try {
            StringEntity stringEntity = new StringEntity(paramJson, Charset.forName("UTF-8"));
            post = new HttpPost(url);
//            post.addHeader("Content-Type", "text/plain;charset=UTF-8");
            post.addHeader("Content-Type", "application/json;charset=UTF-8");
            post.setEntity(stringEntity);
            response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                System.out.println("the2 response status is not ok ,the status is " + response.getStatusLine().getStatusCode() + ",the2 url is " + url);
            }
            result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != post) {
                post.releaseConnection();
            }
            if (null != response) {
                response.close();
            }
        }
    }

    /**
     * 以表单提交的方式发送post请求，contentType为application/x-www-form-urlencoded
     *
     * @param url
     * @param charset
     * @param paramsMap
     * @return
     * @throws Exception
     */
    public static String postHtmlForm(String url, String charset,
                                      Map<String, String> paramsMap) throws Exception {
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        if (paramsMap != null && !paramsMap.isEmpty()) {
            for (Entry<String, String> entry : paramsMap.entrySet()) {
                BasicNameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                formparams.add(nameValuePair);
            }
        }
        CloseableHttpResponse response = null;
        try {
            UrlEncodedFormEntity urEntity = new UrlEncodedFormEntity(formparams, charset);
            urEntity.setContentType("application/x-www-form-urlencoded");
            urEntity.setContentEncoding(charset);
            HttpPost post = new HttpPost(url);
            post.setEntity(urEntity);
            response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                System.out.println("the3 response status is not ok ,the status is " + response.getStatusLine().getStatusCode() + ",the3 url is " + url);
            }
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            throw e;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}