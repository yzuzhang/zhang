package com.feicent.zhang.util.http;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

@SuppressWarnings("deprecation")
public class HttpComponentsUtils {
	
	public static final String UTF8 = "UTF-8";
	
	public static boolean executeHttpRuquest(DefaultHttpClient httpClient, String url){
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode == 200){
				httpGet.releaseConnection();
				return true;
			}
		} catch (Exception e) {
			
		} 
		return false;
	}
	
	public static HttpResponse getHttpResponse(DefaultHttpClient httpClient, String url){
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode == 200){
				return httpResponse;
			}else{
				return null;
			}
		} catch (Exception e) {
			throw new RuntimeException("获取httpResponse失败");
		} 
	}
	
	public static boolean  executeHttpRuquest(DefaultHttpClient httpClient,String url,List<NameValuePair> paramPairList){
		HttpPost httpPost = new HttpPost(url);
		try {
			UrlEncodedFormEntity urlEncodeFormEntity = new UrlEncodedFormEntity(paramPairList,"utf-8");
			httpPost.setEntity(urlEncodeFormEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode == 200){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			httpPost.releaseConnection();
		}
	}
	
	
	public static HttpResponse getHttpResponse(DefaultHttpClient httpClient, String url, List<NameValuePair> paramPairList){
		try {
			UrlEncodedFormEntity urlEncodeFormEntity = new UrlEncodedFormEntity(paramPairList,UTF8);
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(urlEncodeFormEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode == 200){
				return httpResponse;
			}else{
				return null;
			}
		} catch (Exception e) {
			throw new RuntimeException("获取httpResponse失败,url:"+url);
		} 
	}
	
	public static HttpResponse getHttpResponse(DefaultHttpClient httpClient,String url,String cookies,List<NameValuePair> paramPairList){
		try {
			UrlEncodedFormEntity urlEncodeFormEntity = new UrlEncodedFormEntity(paramPairList, UTF8);
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(urlEncodeFormEntity);
			httpPost.addHeader(new BasicHeader("Cookie", cookies));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode == 200){
				return httpResponse;
			}else{
				return null;
			}
		} catch (Exception e) {
			throw new RuntimeException("获取httpResponse失败,url:"+url);
		} 
	}
	
	public static boolean executeHttpRuquest(DefaultHttpClient httpClient,String url,String cookies,List<NameValuePair> paramPairList){
		try {
			UrlEncodedFormEntity urlEncodeFormEntity = new UrlEncodedFormEntity(paramPairList, UTF8);
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(urlEncodeFormEntity);
			httpPost.setHeader(new BasicHeader("Cookie", cookies));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode == 200){
				httpPost.releaseConnection();
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		} 
	}
	
}
