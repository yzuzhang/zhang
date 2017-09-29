package com.feicent.zhang.util.http;

import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse;  
import org.apache.http.HttpStatus;  
import org.apache.http.NameValuePair;  
import org.apache.http.client.config.RequestConfig;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.CloseableHttpResponse;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.ssl.SSLContextBuilder;  
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;  
import org.apache.http.conn.ssl.TrustStrategy;  
import org.apache.http.conn.ssl.X509HostnameVerifier;  
import org.apache.http.entity.StringEntity;  
import org.apache.http.impl.client.CloseableHttpClient;  
import org.apache.http.impl.client.HttpClients;  
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.util.EntityUtils;  

import javax.net.ssl.SSLContext;  
import javax.net.ssl.SSLException;  
import javax.net.ssl.SSLSession;  
import javax.net.ssl.SSLSocket;  
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.io.IOException;  
import java.nio.charset.Charset;  
import java.security.GeneralSecurityException;  
import java.security.KeyManagementException;  
import java.security.KeyStoreException;  
import java.security.NoSuchAlgorithmException;  
import java.security.cert.CertificateException;  
import java.security.cert.X509Certificate;  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map; 

/**
 * httpclient-4.5.2.jar
 * http://blog.csdn.net/liufang1991/article/details/51595952
 * http://bsr1983.iteye.com/blog/2211102
 * @date 2017年7月24日
 */
@SuppressWarnings("deprecation")
public class HttpPoolUtil {
	
	private static final String UTF8 = "UTF-8";
	private static final int MAX_TIMEOUT = 7000; 
    private static RequestConfig requestConfig;  
    private static PoolingHttpClientConnectionManager poolManager;
    
    static {
        // 设置连接池  
    	poolManager = new PoolingHttpClientConnectionManager();  
        // 设置连接池大小  
        poolManager.setMaxTotal(100);  
        poolManager.setDefaultMaxPerRoute(poolManager.getMaxTotal());  
        
        RequestConfig.Builder configBuilder = RequestConfig.custom();  
        // 设置连接超时  
        configBuilder.setConnectTimeout(MAX_TIMEOUT);  
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);  
        // 设置从连接池获取连接实例的超时  
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);  
        // 在提交请求之前 测试连接是否可用  
        //configBuilder.setStaleConnectionCheckEnabled(true);  
        requestConfig = configBuilder.build();  
    }  
  
    /** 
     * 发送 GET 请求（HTTP），不带输入数据 
     * 
     * @param url 
     * @return 
     */  
    public static String doGet(String url) {  
        return doGet(url, new HashMap<String, Object>(), false);  
    }  
  
    /** 
     * 发送 GET 请求（HTTP），K-V形式 
     * 
     * @param url 
     * @param params 
     * @return 
     */  
    public static String doGet(String url, Map<String, Object> params,boolean isHttps) {  
        HttpResponse response=null;  
        String apiUrl = url;  
        StringBuffer param = new StringBuffer();  
        int i = 0;  
        for (String key : params.keySet()) {  
            if (i == 0)  
                param.append("?");  
            else  
                param.append("&");  
            param.append(key).append("=").append(params.get(key));  
            i++;  
        }  
        apiUrl += param;  
        String result = null;  
        CloseableHttpClient httpclient =null;  
        if(isHttps){  
            httpclient=createSSLClientDefault();  
        }else {  
            httpclient=HttpClients.createDefault();  
        } 
        
        HttpEntity entity = null;
        try {  
            HttpGet httpGet = new HttpGet(apiUrl);  
            response = httpclient.execute(httpGet);  
            int statusCode = response.getStatusLine().getStatusCode();  
            System.out.println("执行状态码 : " + statusCode);  
            entity = response.getEntity();  
            if (entity != null) {  
            	entity = response.getEntity();
                //result = IOUtils.toString(instream, "UTF-8");  
                result=EntityUtils.toString(entity, UTF8);  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally {  
        	EntityUtils.consumeQuietly(entity);
        }  
        
        return result;  
    }  
  
    /** 
     * 发送 POST 请求（HTTP），不带输入数据 
     * 
     * @param apiUrl 
     * @return 
     */  
    public static String doPost(String apiUrl) {  
        return doPost(apiUrl, new HashMap<String, Object>());  
    }  
  
    /** 
     * 发送 POST 请求（HTTP），K-V形式 
     * 
     * @param apiUrl API接口URL 
     * @param params 参数map 
     * @return 
     */  
    public static String doPost(String apiUrl, Map<String, Object> params) {  
        CloseableHttpClient httpClient = HttpClients.createDefault();  
        String httpStr = null;  
        HttpEntity entity = null;
        HttpPost httpPost = new HttpPost(apiUrl);  
        CloseableHttpResponse response = null;  
        
        try {  
            httpPost.setConfig(requestConfig);  
            List<NameValuePair> pairList = new ArrayList<>(params.size());  
            for (Map.Entry<String, Object> entry : params.entrySet()) {  
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry  
                        .getValue().toString());  
                pairList.add(pair);  
            }  
            
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, UTF8));  
            response = httpClient.execute(httpPost);  
            System.out.println(response.toString());  
            entity = response.getEntity();  
            httpStr = EntityUtils.toString(entity, UTF8);  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
        	EntityUtils.consumeQuietly(entity);
        }  
        
        return httpStr;  
    }  
  
    /** 
     * 发送 POST 请求（HTTP），JSON形式 
     * 
     * @param apiUrl 
     * @param json   json对象 
     * @return 
     */  
    public static String doPost(String apiUrl, Object json) {  
        CloseableHttpClient httpClient = HttpClients.createDefault();  
        String httpStr = null;  
        HttpEntity entity = null;
        HttpPost httpPost = new HttpPost(apiUrl);  
        CloseableHttpResponse response = null;  
  
        try {  
            httpPost.setConfig(requestConfig);  
            StringEntity stringEntity = new StringEntity(json.toString(), UTF8);//解决中文乱码问题  
            stringEntity.setContentEncoding(UTF8);  
            stringEntity.setContentType("application/json");  
            httpPost.setEntity(stringEntity);  
            response = httpClient.execute(httpPost);  
            entity = response.getEntity();  
            System.out.println(response.getStatusLine().getStatusCode());  
            httpStr = EntityUtils.toString(entity, UTF8);  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
        	EntityUtils.consumeQuietly(entity);
        }  
        return httpStr;  
    }  
  
    /** 
     * 发送 SSL POST 请求（HTTPS），K-V形式 
     * 
     * @param apiUrl API接口URL 
     * @param params 参数map 
     * @return 
     */  
    public static String doPostSSL(String apiUrl, Map<String, Object> params) {  
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
        		.setConnectionManager(poolManager).setDefaultRequestConfig(requestConfig).build();  
        HttpPost httpPost = new HttpPost(apiUrl);  
        CloseableHttpResponse response = null;  
        String httpStr = null;  
        HttpEntity entity =null;
        
        try {  
            httpPost.setConfig(requestConfig);  
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());  
            for (Map.Entry<String, Object> entry : params.entrySet()) {  
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry  
                        .getValue().toString());  
                pairList.add(pair);  
            }  
            
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName(UTF8)));  
            response = httpClient.execute(httpPost);  
            int statusCode = response.getStatusLine().getStatusCode();  
            if (statusCode == HttpStatus.SC_OK) {  
            	entity = response.getEntity();  
                if (entity != null) {  
                	httpStr = EntityUtils.toString(entity, UTF8);
                }   
            }   
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
        	EntityUtils.consumeQuietly(entity);
        }  
        return httpStr;  
    }  
  
    /** 
     * 发送 SSL POST 请求（HTTPS），JSON形式 
     * 
     * @param apiUrl API接口URL 
     * @param json   JSON对象 
     * @return 
     */  
    public static String doPostSSL(String apiUrl, Object json) {  
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
        		.setConnectionManager(poolManager).setDefaultRequestConfig(requestConfig).build();  
        HttpPost httpPost = new HttpPost(apiUrl);  
        CloseableHttpResponse response = null;  
        String httpStr = null;  
        HttpEntity entity =null;
        
        try {  
            httpPost.setConfig(requestConfig);  
            StringEntity stringEntity = new StringEntity(json.toString(), UTF8);//解决中文乱码问题  
            stringEntity.setContentEncoding(UTF8);  
            stringEntity.setContentType("application/json");  
            httpPost.setEntity(stringEntity);  
            
            response = httpClient.execute(httpPost);  
            int statusCode = response.getStatusLine().getStatusCode();  
            if ( statusCode == HttpStatus.SC_OK ) {  
            	entity = response.getEntity();  
                if ( entity != null ) {  
                	httpStr = EntityUtils.toString(entity, UTF8); 
                }  
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
        	EntityUtils.consumeQuietly(entity); 
        }  
        
        return httpStr;  
    }  
  
    /** 
     * 创建SSL安全连接 
     * 
     * @return 
     */  
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {  
        SSLConnectionSocketFactory sslsf = null;  
        try {  
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {  
  
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
                    return true;  
                }  
            }).build();  
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
                @Override  
                public boolean verify(String arg0, SSLSession arg1) {  
                    return true;  
                }  
  
                @Override  
                public void verify(String host, SSLSocket ssl) throws IOException {  
                }  
  
                @Override  
                public void verify(String host, X509Certificate cert) throws SSLException {  
                }  
  
                @Override  
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {  
                }  
            });  
        } catch (GeneralSecurityException e) {  
            e.printStackTrace();  
        }  
        return sslsf;  
    }  
    
    /**
     * 这种写法要求网址和https证书上的一致
     * @return
     */
    public static CloseableHttpClient createSSLClientDefault(){  
        try {  
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {  
                //信任所有  
                public boolean isTrusted(X509Certificate[] chain,  
                                         String authType) throws CertificateException {  
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
        return  HttpClients.createDefault();  
    } 
    
    /**
     * 如果需要访问IP地址的https则需要改用下面的写法
     * @return
     * @throws Exception
     */
	public static CloseableHttpClient createHttpsClient() throws Exception {
		X509TrustManager x509Tm = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] xcs, String string) {
			}
			
			@Override
			public void checkServerTrusted(X509Certificate[] xcs, String string) {
			}
			
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, new TrustManager[] { x509Tm },
				new java.security.SecureRandom());
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslContext,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		//SSLConnectionSocketFactory ssls = new SSLConnectionSocketFactory(sslContext);
		return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	}
    
    public static void main(String[] args) {
    	
		System.out.println(doGet("http://www.baidu.com/", new HashMap<String, Object>(), true));
	}
    
}
