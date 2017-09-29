package com.feicent.zhang.util.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feicent.zhang.util.CloseUtil;
import com.feicent.zhang.util.StringUtils;
import com.feicent.zhang.util.security.MyX509TrustManager;

public class HttpUtil {
	private static Logger log = LoggerFactory.getLogger(HttpUtil.class);
	
	public static String METHOD_GET = "GET";
	public static String METHOD_POST= "POST";
	
	public static final int timeout = 30 * 1000;
	
	/**
	 * 通过GET_URL调用接口获取内容
	 * @param GET_URL
	 */
	public static String readContentFromURL(String GET_URL) {
		String content = readContentFromURL(GET_URL, timeout, timeout);
		return content;
	}
	
	/**
	 * 通过GET_URL调用接口获取内容
	 * 拼凑get请求的URL字串，使用URLEncoder.encode方法
	 * 对特殊和不可见字符进行编码 URLEncoder.encode("str","UTF-8");
	 * @param GET_URL
	 * @param connectTimeout 连接超时
	 * @param readTimeout    读取超时
	 */
	public static String readContentFromURL(String GET_URL, int connectTimeout, int readTimeout) {
		String result = null;
		String getURL =  GET_URL;
		BufferedReader reader = null;
		HttpURLConnection connection = null;
		
		try{
			URL url = new URL(getURL);
			
			//根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(connectTimeout);
			connection.setReadTimeout(readTimeout);
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			connection.setRequestProperty("contentType", "UTF-8");
			connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			
			// 进行连接
			connection.connect();
			// 取得输入流，并使用Reader读取
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
			
			String line = null;
			StringBuffer lines = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				lines.append(line);
			}
			result = lines.toString();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			CloseUtil.close(reader);
			if(connection != null){
				connection.disconnect();
				connection = null;
			}
		}
		return result;
	}
	
	/**
	 * 发起https请求并获取结果
	 * @param requestUrl 	请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 	提交的数据
	 * @return 	
	 */
	public static String readFromHttpsRequest(String requestUrl,String requestMethod, String requestBody){
		String content = null;
		BufferedReader bufferedReader=null;
		HttpsURLConnection httpUrlConn = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if (METHOD_GET.equalsIgnoreCase(requestMethod)){
				httpUrlConn.connect();
			}
			// 当有数据需要提交时
			if ( StringUtils.isNotEmpty(requestBody) ) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(requestBody.getBytes("UTF-8"));
				outputStream.flush();
				IOUtils.closeQuietly(outputStream);
			}
			// 将返回的输入流转换成字符串
			bufferedReader = new BufferedReader(new InputStreamReader(httpUrlConn.getInputStream(), "UTF-8"));
			StringBuffer buffer = new StringBuffer();
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			
			content = buffer.toString();
		} catch (ConnectException ce) {
			log.error("ConnectException:{}", ce.getMessage());
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}finally{
			IOUtils.closeQuietly(bufferedReader);
			if(httpUrlConn != null){
				httpUrlConn.disconnect();
			}
		}
		
		return content;
	}
	
	/**
	 * 发起https请求并获取结果
	 * @param requestUrl 	请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 	提交的数据
	 * @return 	JSONObject
	 */
	public static JSONObject readJSONFromHttpsRequest(String requestUrl,String requestMethod, String requestBody){
		String content = HttpUtil.readFromHttpsRequest(requestUrl, requestMethod, requestBody);
		if ( StringUtils.isNotEmpty(content) ) {
			return JSONObject.fromObject(content);
		}
		return null;
	}
	
}
