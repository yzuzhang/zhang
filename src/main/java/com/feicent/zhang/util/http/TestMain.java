package com.feicent.zhang.util.http;

import java.util.HashMap;  
import java.util.Map;

//对接口进行测试  
public class TestMain {  
    private String url = "https://192.168.1.101/";  
    private String charset = "UTF-8";  
    private HttpSSLClient httpClientUtil = null;  
    
    public TestMain(){  
        httpClientUtil = new HttpSSLClient();  
    }
    
    public void test(){  
        String httpOrgCreateTest = url + "httpOrg/create";  
        Map<String,String> createMap = new HashMap<String,String>();  
        createMap.put("authuser","*****");  
        createMap.put("authpass","*****");  
        createMap.put("orgkey","****");  
        createMap.put("orgname","****");  
        @SuppressWarnings("static-access")
		String httpOrgCreateTestRtn = httpClientUtil.doPost(httpOrgCreateTest, createMap, charset);  
        System.out.println("result:"+httpOrgCreateTestRtn);  
    }  
      
    public static void main(String[] args){  
        TestMain main = new TestMain();  
        main.test();  
    }  
}  
