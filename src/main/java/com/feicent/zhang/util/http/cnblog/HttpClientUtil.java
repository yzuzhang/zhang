package com.feicent.zhang.util.http.cnblog;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import com.feicent.zhang.util.http.HttpStatus;

/**
 * 使用 PoolingHttpClientConnectionManager 链接池管理类
 * @date 2017年8月30日
 * http://www.cnblogs.com/spring-Ganoder/p/httpclient.html
 */
public class HttpClientUtil {
	
	private static int MAX_CONN_TOTAL = 1000;
    private static int MAX_CONN_PERROUTE = 600;
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    
	private static IdleConnectionMonitorThread MonitorThread = null;
    private static PoolingHttpClientConnectionManager connectionManager = null;
    
    static {
    	try {
        	//设置协议http和https对应的处理socket链接工厂对象
			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy() {
			    @Override
			    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			        return true;
			    }
			}).build();
			
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, 
					new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
			        .register("http", PlainConnectionSocketFactory.INSTANCE)
			        .register("https", sslsf)
			        .build();
			  
			connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		    connectionManager.setMaxTotal(MAX_CONN_TOTAL);
		    connectionManager.setDefaultMaxPerRoute(MAX_CONN_PERROUTE);
			if (MonitorThread == null) {
				MonitorThread = new IdleConnectionMonitorThread(connectionManager);
				MonitorThread.start();//清理无效的连接
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static HttpClientResponseEntity doGet(String requestUrl, final int timeOut, 
    		String encodeName, Map<String, String> headers) throws Exception {
    	return doHttpRequest(requestUrl, METHOD_GET, null, timeOut, encodeName, headers);
    }
    
    public static HttpClientResponseEntity doPost(String requestUrl, byte[] requestData, 
    		final int timeOut, String encodeName, Map<String, String> headers) throws Exception {
    	return doHttpRequest(requestUrl, METHOD_POST, requestData, timeOut, encodeName, headers);
    }
    
    public static HttpClientResponseEntity doHttpRequest(String requestUri, String method, byte[] requestData, 
    		final int timeOut, String encodeName, Map<String, String> headers) throws Exception {
    	
        InputStream inputStream = null;
        HttpUriRequest httpUriRequest = null;
        CloseableHttpResponse response = null;
        HttpClientResponseEntity responseEntity = new HttpClientResponseEntity();
        
        try {
        	RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(timeOut)
                    .setSocketTimeout(timeOut)
                    .setConnectionRequestTimeout(timeOut)
                    .build();
            SocketConfig socketConfig = SocketConfig.custom()
                    .setSoTimeout(timeOut)
                    .build();
            
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            httpClientBuilder.setConnectionManager(connectionManager);
            httpClientBuilder.setDefaultRequestConfig(requestConfig);
            httpClientBuilder.setDefaultSocketConfig(socketConfig);
            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
            
            httpClientBuilder.setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
				@Override
				public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
					final HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                    while (it.hasNext()) {
                        final HeaderElement he = it.nextElement();
                        final String param = he.getName();
                        final String value = he.getValue();
                        if (value != null && param.equalsIgnoreCase("timeout")) {
                            try {
                                return Long.parseLong(value) * 1000;
                            } catch (final NumberFormatException ignore) {
                            }
                        }
                    }
                    return timeOut;
				}
            });
            
            CloseableHttpClient httpClient = httpClientBuilder.build();
            switch (method) {
                case METHOD_POST:
                    HttpPost httpPost = new HttpPost(requestUri);
                    if(requestData!=null && requestData.length>0){
                    	httpPost.setEntity(new ByteArrayEntity(requestData));
                    }
                    if (headers != null) {
                        for (Map.Entry<String, String> entry : headers.entrySet()) {
                            httpPost.setHeader(entry.getKey(), entry.getValue());
                        }
                    }
                    httpUriRequest = httpPost;
                    break;
                case METHOD_GET:
                    HttpGet httpGet = new HttpGet(requestUri);
                    if (headers != null) {
                        for (Map.Entry<String, String> entry : headers.entrySet()) {
                            httpGet.setHeader(entry.getKey(), entry.getValue());
                        }
                    }
                    httpUriRequest = httpGet;
                    break;
                default:
                    throw new Exception("请求方式不存在！");

            }
            
            response = httpClient.execute(httpUriRequest);
            inputStream = response.getEntity().getContent();
            responseEntity.setResponseCode(String.valueOf(response.getStatusLine().getStatusCode()));
            
            if (HttpStatus.HTTP_OK == response.getStatusLine().getStatusCode()) {
                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
                byte[] buff = new byte[1024];
                int ri = 0;
                while ((ri = inputStream.read(buff, 0, 1000)) > 0) {
                    swapStream.write(buff, 0, ri);
                }
                responseEntity.setResponseByteArray(swapStream.toByteArray());
                
                String responseBody = EntityUtils.toString(response.getEntity(), encodeName);
                responseEntity.setResponseBody(responseBody);
                responseEntity.setResponseByteArray(responseBody.getBytes());
            } else {
                responseEntity.setResponseMsg(response.getStatusLine().getReasonPhrase());
            }
            inputStream.close();
        } catch (Exception e) {
            responseEntity.setResponseCode("-1");
            responseEntity.setResponseMsg(e.getMessage());
            if (e instanceof SocketTimeoutException) {
                responseEntity.setResponseCode("timeout");
                responseEntity.setResponseMsg("接口超时（超时时间：" + timeOut + "毫秒）");
            }
        } finally {
            if (httpUriRequest != null) {
                httpUriRequest.abort();
            }
            if (response != null) {
                EntityUtils.consumeQuietly(response.getEntity());
            }
        }
        
        return responseEntity;
    }
    
}
