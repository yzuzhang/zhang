package com.feicent.zhang.util.http;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https请求工具类
 * @author yzuzhang
 */
public class HttpSSLClient {
	
	public static final Logger logger = LoggerFactory.getLogger(HttpSSLClient.class);
	public static final int TIME_OUT = 5000;
	
	public static String getSSL(String url, String defaultCharset) {
		String content = null;
	    CloseableHttpClient httpclient = null;
	    CloseableHttpResponse response = null;
	    
	    try {
	    	httpclient = createSSLInsecureClient();
	        HttpGet httpGet = new HttpGet(url);
	        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIME_OUT).setConnectTimeout(TIME_OUT)
	                    .setConnectionRequestTimeout(TIME_OUT).build();
	        
	        httpGet.setConfig(requestConfig);
	        response = httpclient.execute(httpGet);
	        HttpEntity entity = response.getEntity();
	        if ( entity != null ) {
	            content = EntityUtils.toString(entity, defaultCharset);
	            EntityUtils.consume(entity);
	        }
	    } catch (IOException e) {
	        logger.error("request URL[" + url + "] error", e);
	    } finally {
	    	IOUtils.closeQuietly(response);
	    	IOUtils.closeQuietly(httpclient);
	    }
	    return content;
	}
	
	private static CloseableHttpClient createSSLInsecureClient() {
	    try {
	        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
	            @Override
	            public boolean isTrusted(X509Certificate[] arg0, String arg1) { //信任所有
	                return true;
	            }
	        }).build();
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
	        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	    } catch (KeyManagementException e) {
	        e.printStackTrace();
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (KeyStoreException e) {
	        e.printStackTrace();
	    }
	    return HttpClients.createDefault();
	}
	
	public static String doPost(String url, Map<String, String> map, String charset){  
		List<NameValuePair> list = null;
		if( map != null && map.size() > 0 ){
            list = new ArrayList<NameValuePair>(); 
            for(Map.Entry<String, String> entry : map.entrySet()){
            	list.add(new BasicNameValuePair(entry.getKey(), entry.getValue())); 
            } 
        }
		
        String result = doPost(url, list, charset);
        return result;  
    }  
	 
	@SuppressWarnings("resource")
	public static String doPost(String url, List<NameValuePair> list, String charset){  
        HttpClient httpClient = null;  
        HttpPost httpPost = null; 
        String result = null;  
        try{
            httpClient = new SSLClient();  
            httpPost = new HttpPost(url);  
            if( list != null && list.size() > 0 ){
            	UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);  
                httpPost.setEntity(entity);//设置参数 
            }
            
            //httpPost.addHeader(new BasicHeader("Cookie", ""));
            HttpResponse response = httpClient.execute(httpPost);  
            if( response != null ){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        
        return result;  
    }  
	
	public static void main(String[] args) {
	   
	    System.out.println(getSSL("https://vsp.jd.com/", "UTF-8"));
	}
}
