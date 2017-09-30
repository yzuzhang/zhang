package com.feicent.zhang.util.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.feicent.zhang.util.CloseUtil;

/**
 * Java实现手机号码归属地判别
 * http://www.cnblogs.com/tyk766564616/p/7607402.html
 * @author yzuzhang
 * @date 2017年9月30日 上午10:09:55
 */
public class PhoneAddressUtil {

	private static final String TAOBAO_URL = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=";
	
	public static void getPhoneAddr(String phoneNumber) {
		BufferedReader in = null;
	    try {
	        URL taobaoURL = new URL(TAOBAO_URL + phoneNumber);
	        in = new BufferedReader(new InputStreamReader(taobaoURL.openStream()));
	        
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	            System.out.println(inputLine);
	        }
	        
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
			CloseUtil.close(in);
		}
	}
	
	public static void main(String[] args) {
		// 归属地信息
		getPhoneAddr("18951597878");
	}

}
