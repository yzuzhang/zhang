package com.feicent.zhang.util.okhttp;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okio.BufferedSink;

/**
 * 官方示例:https://github.com/square/okhttp/wiki/Recipes
 * http://blog.csdn.net/iispring/article/details/51661195
 * http://www.jianshu.com/p/ca8a982a116b
 * @author yzuzhang
 * @date 2017年8月1日
 */
public class OkHttpUtils {
	
	private static OkHttpClient client = null;
	public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
	public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
	
	static {
		client = new OkHttpClient.Builder()
	        .connectTimeout(5, TimeUnit.SECONDS)
	        //.writeTimeout(10, TimeUnit.SECONDS)
	        .readTimeout(30, TimeUnit.SECONDS)
	        .build();
	}
	
	/**
	 * 处理身份验证
	 */
	public void authenticator() {
	    client = new OkHttpClient.Builder()
	        .authenticator(new Authenticator() {
	          @Override 
	          public Request authenticate(Route route, Response response) throws IOException {
	            System.out.println("Authenticating for response: " + response);
	            System.out.println("Challenges: " + response.challenges());
	            String credential = Credentials.basic("jesse", "password1");
	            return response.request().newBuilder()
	                .header("Authorization", credential)
	                .build();
	          }
	        })
	        .build();
	}
	
	/**
	 * 同步GET
	 * @throws Exception
	 */
	public static void doGet(String url) throws Exception {
		Request request = new Request.Builder().url(url).build();
		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()) {
			throw new IOException("Unexpected code " + response);
		}
		Headers responseHeaders = response.headers();
		for (int i = 0; i < responseHeaders.size(); i++) {
			System.out.println(responseHeaders.name(i) + ": "
					+ responseHeaders.value(i));
		}
		System.out.println(response.body().string());
	}
	
	/**
	 * 异步GET
	 * @param url
	 * @throws Exception
	 */
	public static void doGetAsyn(String url) throws Exception {
	    Request request = new Request.Builder().url(url).build();
	    
	    client.newCall(request).enqueue(new Callback() {
	      @Override
	      public void onFailure(Call call, IOException e) {
	    	  e.printStackTrace();
	      }
	      
	      @Override
	      public void onResponse(Call call, Response response) throws IOException {
	        if (!response.isSuccessful()) {
	        	throw new IOException("Unexpected code " + response);
	        }
	        
	        Headers responseHeaders = response.headers();
	        for (int i = 0, size = responseHeaders.size(); i < size; i++) {
	          System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
	        }
	        
	        System.out.println(response.body().string());
	      }
	    });
	}
	
	public static void requestHeader(String url) throws Exception {
	    Request request = new Request.Builder().url(url)
	        .header("User-Agent", "OkHttp Headers.java")
	        .addHeader("Accept", "application/json; q=0.5")
	        .addHeader("Accept", "application/vnd.github.v3+json")
	        .build();
	    
	    Response response = client.newCall(request).execute();
	    if (!response.isSuccessful()) {
	    	throw new IOException("Unexpected code " + response);
	    }
	    
	    System.out.println("Server: " + response.header("Server"));
	    System.out.println("Date: " + response.header("Date"));
	    System.out.println("Vary: " + response.headers("Vary"));
	}
	
	/**
	 * 用POST发送String
	 * @throws Exception
	 */
	public static void postBody(String url) throws Exception {
	    String postBody = ""
	        + "Releases\n"
	        + "--------\n"
	        + "\n"
	        + " * _1.0_ May 6, 2013\n"
	        + " * _1.1_ June 15, 2013\n"
	        + " * _1.2_ August 11, 2013\n";
	    
	    //String postBody = "{\"orderCode\": \"46666960000\", \"pin\": \"jinghailu_44\"}";
	    //RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, postBody);
	    Request request = new Request.Builder()
	    	.url(url)
	        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
	        .build();
	    
	    Response response = client.newCall(request).execute();
	    if (!response.isSuccessful()) {
	    	throw new IOException("Unexpected code " + response);
	    }
	    
	    String responseStr = response.body().string();
	    if(responseStr!=null && responseStr.contains("\"code\":200")){
	    	
	    }
	    System.out.println("response: "+responseStr);
	}
	
	/**
	 * 用POST发送Stream流
	 * @throws Exception
	 */
	public static void postStreamBody() throws Exception {
	    RequestBody requestBody = new RequestBody() {
	      @Override 
	      public MediaType contentType() {
	    	  return MEDIA_TYPE_MARKDOWN;
	      }
	      
	      @Override 
	      public void writeTo(BufferedSink sink) throws IOException {
	        sink.writeUtf8("Numbers\n");
	        sink.writeUtf8("-------\n");
	        for (int i = 2; i <= 997; i++) {
	          sink.writeUtf8(String.format(" * %s = %s\n", i, factor(i)));
	        }
	      }
	      
	      private String factor(int n) {
	        for (int i = 2; i < n; i++) {
	          int x = n / i;
	          if (x * i == n) {
	        	  return factor(x) + " × " + i;
	          }
	        }
	        return Integer.toString(n);
	      }
	    };
	    
	    Request request = new Request.Builder()
	        .url("https://api.github.com/markdown/raw")
	        .post(requestBody)
	        .build();
	    
	    Response response = client.newCall(request).execute();
	    if (!response.isSuccessful()) {
	    	throw new IOException("Unexpected code " + response);
	    }

	    System.out.println(response.body().string());
	}
	
	/**
	 * 用POST发送File
	 * @throws Exception
	 */
	public static void uploadFile() throws Exception {
	    File file = new File("D:/XssFilter.java");
	    
	    Request request = new Request.Builder()
	        .url("https://api.github.com/markdown/raw")
	        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
	        .build();
	    
	    Response response = client.newCall(request).execute();
	    if (!response.isSuccessful()) {
	    	throw new IOException("Unexpected code " + response);
	    }
	    
	    System.out.println(response.body().string());
	  }
	
	public static void main(String[] args) throws Exception {
		//doGet("http://www.jianshu.com/p/ca8a982a116b");
		//doGetAsyn("http://www.jianshu.com/p/ca8a982a116b");
		//requestHeader("http://blog.csdn.net/iispring/article/details/51661195");
		//postBody("https://api.github.com/markdown/raw");
		//postStreamBody();
		uploadFile();
	}
}
