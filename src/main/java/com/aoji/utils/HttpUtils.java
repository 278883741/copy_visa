package com.aoji.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Map;

/**
 * author: chenhaibo
 * description: Http请求工具类
 * date: 2018/1/27
 */
public class HttpUtils {

    /**
     * 发送get请求
     * @param url
     * @return
     */
    public static String doGet(String url){
        //创建client实例
        CloseableHttpClient client = HttpClients.createDefault();
        //创建指定方法实例，并指定URL
        url= url.replaceAll(" ", "%20");
        HttpGet httpGet = new HttpGet(url);
        //get请求不需要设置参数
        //设置头信息
        httpGet.addHeader("Content-Type", "application/json");
        httpGet.setHeader("Accept-Charset", "UTF-8");
        //执行请求
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);
            //检验状态码
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == 200){
                //获得响应实体
                return EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            if(response != null){
                try {
                    client.close();
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String sendPost(String url, String data) {
        String response = null;
        try {
            CloseableHttpClient httpclient = null;
            CloseableHttpResponse httpresponse = null;
           try {
                httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost(url);
                StringEntity stringentity = new StringEntity(data,ContentType.create("text/json", "UTF-8"));
                httppost.setEntity(stringentity);
                httpresponse = httpclient.execute(httppost);
                response = EntityUtils.toString(httpresponse.getEntity());
            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (httpresponse != null) {
                    httpresponse.close();
                }
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
     }
    public static String sendPost2(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //1.获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //2.中文有乱码的需要将PrintWriter改为如下
            //out=new OutputStreamWriter(conn.getOutputStream(),"UTF-8")
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        System.out.println("post推送结果："+result);
        return result;
    }


    public static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 初始化RestTemplate
     */
    private static RestTemplate restTemplate;
    static {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    /**
     * 发送GET请求
     * @param url
     * @param resType
     * @return
     */
    public static <T> T doGet(String url, Class<T> resType){
        long timeStamp = Calendar.getInstance().getTimeInMillis();
        logger.info("{} - GET request for {}", timeStamp, url);
        T response = restTemplate.getForObject(url, resType);
        logger.info("{} - GET response for {}", timeStamp, JSONObject.toJSONString(response));
        return response;
    }

    /**
     * 发送POST请求
     * @param url
     * @param param
     * @param resType
     * @return
     */
    public static <T> T doPost(String url, MultiValueMap<String, String> param, Class<T> resType){
        long timeStamp = Calendar.getInstance().getTimeInMillis();
        logger.info("{} - POST request for {} param {}", timeStamp, url, JSONObject.toJSONString(param));
        // 头信息
        HttpHeaders headers = new HttpHeaders();
        // 请求实体
        HttpEntity<MultiValueMap<String, String>> requestEntity  = new HttpEntity<>(param, headers);
        // 发送post请求
        T response = restTemplate.postForObject(url, requestEntity, resType);
        logger.info("{} - POST response for {}", timeStamp, JSONObject.toJSONString(response));
        return response;
    }

    /**
     * 发送POST请求(调用资源系统)
     * @param url
     * @param param
     * @param resType
     * @return
     */
    public static <T> T doPostForXxRes(String url, Map<String,String> param, Class<T> resType){
        long timeStamp = Calendar.getInstance().getTimeInMillis();
        logger.info("{} - POST request for {} param {}", timeStamp, url, JSONObject.toJSONString(param));
        // 头信息
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        // 请求实体
        HttpEntity<Map<String,String>> requestEntity  = new HttpEntity<>(param, headers);
        // 发送post请求
        T response = restTemplate.postForObject(url, requestEntity, resType);
        logger.info("{} - POST response for {}", timeStamp, JSONObject.toJSONString(response));
        return response;
    }

    public static void main(String[] args) {
//        HttpUtils.doGet(
//                "http://api.aojiedu.net/sign/api/StudentContractInfo?studentNo=15201", String.class);

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("sid", "15201");
        param.add("type", "10");
        HttpUtils.doPost("http://api.aojiedu.net/Sign/api/Revisit", param, String.class);

//        MultiValueMap<String, String> param1 = new LinkedMultiValueMap<>();
//        param1.add("sid", "500059");
//        HttpUtils.doPost("http://192.168.11.80:5004/api/GetPhoneNumBySid", param1, String.class);
    }

}
