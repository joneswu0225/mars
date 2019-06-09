package com.jones.mars;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class PostRequest {

    public static void main(String[] args) throws InterruptedException {
        //用于分线程执行完 开始执行主线程 我试的没用
        // CountDownLatch count = new CountDownLatch(500);
        int threadcount = 1000;
        //如果要实现真正的并发同时执行，可通过CyclicBarrier来控制;不想同时则可以不用
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadcount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadcount);
        //http连接池
        CloseableHttpClient httpClient;
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(500);
        cm.setDefaultMaxPerRoute(20);
        cm.setDefaultMaxPerRoute(1000);
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
        //开启线程
        long start = System.currentTimeMillis();
        for (int i = 0; i < threadcount; i++) {
            executorService.execute(new myThread1(i,httpClient, cyclicBarrier));
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);

    }
}

//定义内部变量线程
class myThread1 implements Runnable{

    //传入的参数可以自定义
    private int i;
    private CyclicBarrier cyclicBarrier;
    private CloseableHttpClient httpClient;
    public myThread1() {
    }

    public myThread1(int i,CloseableHttpClient httpClient) {
        super();
        this.i = i;
        this.httpClient = httpClient;
    }
    public myThread1(int i,CloseableHttpClient httpClient, CyclicBarrier cyclicBarrier) {
        this(i, httpClient);
        this.cyclicBarrier = cyclicBarrier;
    }


    //线程要执行的方法
    @Override
    public void run() {
        // 等待所有任务准备就绪
        try {
            cyclicBarrier.await();

        // 测试内容
        //传入的url
        String url = "https://flume.wmcloud-stg.com";
        HttpPost get = new HttpPost(url);
        String test = "[{\n" +
                "\t\"common\": {\n" +
                "\t\t\"domain\": \"mof-tpzg.wmcloud-qa.com\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\",\n" +
                "\t\t\"title\": \"通联魔方-一站式资管服务平台-私募基金公司排名\",\n" +
                "\t\t\"referrer\": \"\",\n" +
                "\t\t\"userAgent\": \"Chrome\",\n" +
                "\t\t\"agentVersion\": \"72.0.3626.119\",\n" +
                "\t\t\"deviceId\": \"f8d9f3fc-d7f4-44b2-a852-97244b464f90\",\n" +
                "\t\t\"appEnv\": \"stg\",\n" +
                "\t\t\"userId\": \"anonymous\",\n" +
                "\t\t\"appId\": 20,\n" +
                "\t\t\"appVersion\": \"3.31.3\",\n" +
                "\t\t\"appName\": \"mof-web-fe\",\n" +
                "\t\t\"ext\": {\n" +
                "\t\t\t\"sh\": 1080,\n" +
                "\t\t\t\"sw\": 1920,\n" +
                "\t\t\t\"cd\": 24,\n" +
                "\t\t\t\"lang\": \"zh-CN\",\n" +
                "\t\t\t\"ua\": \"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36\",\n" +
                "\t\t\t\"browser\": {\n" +
                "\t\t\t\t\"name\": \"Chrome\",\n" +
                "\t\t\t\t\"version\": \"72.0.3626.119\",\n" +
                "\t\t\t\t\"major\": \"72\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"engine\": {\n" +
                "\t\t\t\t\"name\": \"WebKit\",\n" +
                "\t\t\t\t\"version\": \"537.36\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"os\": {\n" +
                "\t\t\t\t\"name\": \"Windows\",\n" +
                "\t\t\t\t\"version\": \"7\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"device\": {\n" +
                "\t\t\t\t\n" +
                "\t\t\t},\n" +
                "\t\t\t\"cpu\": {\n" +
                "\t\t\t\t\"architecture\": \"amd64\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"events\": [{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t},{\n" +
                "\t\t\"eventName\": \"魔方首页-未登录状态【点击】\",\n" +
                "\t\t\"timestamp\": 1551760345774,\n" +
                "\t\t\"page\": \"1_1\",\n" +
                "\t\t\"eventId\": 1,\n" +
                "\t\t\"eventType\": \"click\",\n" +
                "\t\t\"url\": \"http://mof-tpzg.wmcloud-qa.com/\"\n" +
                "\t}]\n" +
                "}] ";
        //设置表单数据 我们是post请求  get的直接url后加参数
//        JSONObject json = json.par
        List<NameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("name", "" + i));
        params.add(new BasicNameValuePair("unit", "" + i));
        params.add(new BasicNameValuePair("phone", "" + i));
        params.add(new BasicNameValuePair("jobTitle", "" + i));
        params.add(new BasicNameValuePair("email", "" + i));
        // 转换参数并设置编码格式
        get.setEntity(new StringEntity(test, Consts.UTF_8));
            try {
                //发送请求
                CloseableHttpResponse response=null;
                for(int i=0;i<5;i++) {
                    response = httpClient.execute(get);
                    response.getEntity().getContent().close();
                }
                //System.out.println(i); //无法测试什么时候分线程执行完 所以每个分线程执行完打印  但打印不全
            } catch (ClientProtocolException e) {
                System.out.println("fail");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("fail1");
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }


}
