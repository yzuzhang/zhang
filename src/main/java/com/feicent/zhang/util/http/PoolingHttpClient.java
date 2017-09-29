package com.feicent.zhang.util.http;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

/**
 * http://bsr1983.iteye.com/blog/2211102
 * @author yzuzhang
 * @date 2017年7月25日
 */
public class PoolingHttpClient {
	
	private int maxTotalPool = 200;
	private int maxConPerRoute = 20;
	private int socketTimeout = 5000;
	private int connectTimeout = 2000;
	private int connectionRequestTimeout = 1000;
	private PoolingHttpClientConnectionManager poolConnManager = null;
	
    public void init()  
    {  
         try {  
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null,new TrustSelfSignedStrategy()).build();  
            @SuppressWarnings("deprecation")
			HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;  
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(  
                    sslcontext,hostnameVerifier);  
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())  
                    .register("https", sslsf)  
                    .build();  
            poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
            // Increase max total connection to 200  
            poolConnManager.setMaxTotal(maxTotalPool);  
            // Increase default max connection per route to 20  
            poolConnManager.setDefaultMaxPerRoute(maxConPerRoute);  
            SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(socketTimeout).build();  
            poolConnManager.setDefaultSocketConfig(socketConfig);  
        } catch (Exception e) {  
            System.out.println("InterfacePhpUtilManager init Exception"+e.toString());  
        }  
    }  
    
    public CloseableHttpClient getHttpClient() {  
        RequestConfig requestConfig = RequestConfig.custom()
        		.setConnectionRequestTimeout(connectionRequestTimeout)  
                .setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();  
        CloseableHttpClient httpClient = HttpClients.custom()  
                .setConnectionManager(poolConnManager)
                .setDefaultRequestConfig(requestConfig).build();  
        
        if(poolConnManager!=null && poolConnManager.getTotalStats()!=null)  
        {  
        	System.out.println("now client pool getMaxTotal--->"+ poolConnManager.getMaxTotal());
        	System.out.println("now client pool "+ poolConnManager.getTotalStats().toString());
        }  
        return httpClient;  
    }  
    
    public String doPost(String url, String jsonStr)  
    {  
        String returnStr = null;  
        //参数检测  
        if(url==null||"".equals(url))  
        {  
            return returnStr;  
        }  
        
        HttpPost httpPost = new HttpPost(url);  
        try {  
        	CloseableHttpClient client = getHttpClient();
            
        	long currentTime=System.currentTimeMillis();  
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
            nvps.add(new BasicNameValuePair("jsonstr", jsonStr));  
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));  
            System.out.println(currentTime+" 开始发送 请求：url"+url);  
            
            CloseableHttpResponse response = client.execute(httpPost);  
            int status = response.getStatusLine().getStatusCode();  
            if (status >= 200 && status < 300) {  
                HttpEntity entity = response.getEntity();  
                String resopnse="";  
                if(entity != null) {  
                    resopnse=EntityUtils.toString(entity,"utf-8");  
                }  
                System.out.println(currentTime+" 接收响应：url"+url+" status="+status);  
                return entity != null ? resopnse : null;  
            } else {  
                HttpEntity entity = response.getEntity();  
                httpPost.abort();  
                System.out.println(currentTime+" 接收响应：url"+url+" status="+status+" resopnse="+EntityUtils.toString(entity,"utf-8"));  
                throw new ClientProtocolException("Unexpected response status: " + status);  
            }  
        } catch (Exception e) {  
            httpPost.abort();  
            System.out.println(" Exception"+e.toString()+" url="+url+" jsonstr="+jsonStr);  
        }   
        
        return returnStr;  
    }  
    
    public static void main(String[] args) {
    	PoolingHttpClient pool = new PoolingHttpClient();
    	pool.init();
    	pool.getHttpClient();
	}
}
