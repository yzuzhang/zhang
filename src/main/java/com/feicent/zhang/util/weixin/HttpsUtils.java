package com.feicent.zhang.util.weixin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.feicent.zhang.util.StringUtils;

/**
 * https工具类
 */
@SuppressWarnings("deprecation")
public class HttpsUtils {

    /**
     * 组装请求数据字符串
     * 
     * @param rootElement 根元素名称
     * @param postData 请求数据
     * @return
     * @author SvenAugustus(蔡政滦)  e-mail: SvenAugustus@outlook.com modify by 2015年8月2日
     */
    public static String toXml(String rootElement, Map<String, String> postData) {
        Element root = DocumentHelper.createElement(rootElement);
        Document document = DocumentHelper.createDocument(root);
        Iterator<String> it = postData.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            String value = postData.get(key);
            Element element = root.addElement(key);
            element.addCDATA(StringUtils.isEmpty(value) ? "" : value);
        }
        
        return document.asXML();
    }

    /**
     * 提交http请求，获取响应数据字符串
     * 
     * @param url 请求URL
     * @headerInfo 请求头信息
     * @param xml 请求数据字符串
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @author SvenAugustus(蔡政滦)  e-mail: SvenAugustus@outlook.com modify by 2015年8月2日
     */
    public static String postXml(String url, Map<String, String> headerInfo, String xml) throws Exception {
        // httpclient 4.2.2
        //        String result = "";
        //
        //        HttpClient httpclient = new DefaultHttpClient();
        //        HttpPost pmethod = new HttpPost(url); // 设置响应头信息
        //        pmethod.addHeader("Connection", "keep-alive");
        //        pmethod.addHeader("Accept", "*/*");
        //        pmethod.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        //        pmethod.addHeader("Host", "api.mch.weixin.qq.com");
        //        pmethod.addHeader("X-Requested-With", "XMLHttpRequest");
        //        pmethod.addHeader("Cache-Control", "max-age=0");
        //        pmethod.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        //        pmethod.setEntity(new StringEntity(xml, "UTF-8"));
        //
        //        HttpResponse response = httpclient.execute(pmethod);
        //        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        //            result = EntityUtils.toString(response.getEntity(), "UTF-8");
        //        }
        //        return result;
        //return HttpsUtils.httpRequest(url, "POST", xml, "utf-8");

        // httpclient 4.3.4
        String result = null;
        HttpEntity postEntity = new StringEntity(xml, "utf-8");
        HttpPost httpPost = new HttpPost(url);
        //        httpPost.addHeader("Content-Type", "text/xml");
        //        httpPost.addHeader("Connection", "keep-alive");
        //        httpPost.addHeader("Accept", "*/*");
        //        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        //        httpPost.addHeader("Host", "api.mch.weixin.qq.com");
        //        httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
        //        httpPost.addHeader("Cache-Control", "max-age=0");
        //        httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        if (headerInfo != null) {
            Iterator<String> it = headerInfo.keySet().iterator();
            while (it.hasNext()) {
                String name = (String) it.next();
                String value = headerInfo.get(name);
                httpPost.addHeader(name, value);
            }
        }
        httpPost.setEntity(postEntity);
        
        CloseableHttpClient httpclient = HttpClients.custom().build();
        try {
            HttpResponse response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            /*if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                String location = response.getFirstHeader("Location").getValue();
                return get(location);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }*/
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity, "utf-8");
                }
            }
        } finally {
            httpclient.close();
        }
        return result;
    }

    /**
     * 提交https请求，获取响应数据字符串
     * httpclient 4.3.4 支持SSL / https
     * @param url 请求URL
     * @headerInfo 请求头信息
     * @param xml 请求数据字符串
     * @keyStorePath 证书存放路径
     * @keysecret 证书密码
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @author SvenAugustus(蔡政滦)  e-mail: SvenAugustus@outlook.com modify by 2015年8月2日
     */
    public static String postXmlSSL(String url, Map<String, String> headerInfo, String xml, String keyStorePath, String keysecret)
            throws IllegalStateException, IOException, Exception {
        String result = null;
        if (StringUtils.isNotEmpty(keyStorePath) && StringUtils.isNotEmpty(keysecret)) {
            HttpEntity postEntity = new StringEntity(xml, "utf-8");
            HttpPost httpPost = new HttpPost(url);
            if (headerInfo != null) {
                Iterator<String> it = headerInfo.keySet().iterator();
                while (it.hasNext()) {
                    String name = (String) it.next();
                    String value = headerInfo.get(name);
                    httpPost.addHeader(name, value);
                }
            }
            httpPost.setEntity(postEntity);

            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            //File keyStoreFile = new File("D:/10016225.p12");
            File keyStoreFile = new File(keyStorePath);
            FileInputStream instream = new FileInputStream(keyStoreFile);
            try {
                keyStore.load(instream, keysecret.toCharArray());
            } finally {
                instream.close();
            }
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, keysecret.toCharArray()).build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            
            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            httpClientBuilder.setSSLSocketFactory(sslsf);
            CloseableHttpClient httpclient = httpClientBuilder.build();
            try {
                HttpResponse response = httpclient.execute(httpPost);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        result = EntityUtils.toString(entity, "utf-8");
                    }
                }
                httpPost.abort();
            } finally {
                httpclient.close();
            }
        }
        
        return result;
    }
    
    public static String doGet(String url) throws Exception {
        String result = null;
        HttpGet request = new HttpGet(url);
        CloseableHttpClient httpclient = HttpClients.custom().build();
        
        HttpResponse response = httpclient.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
            String location = response.getFirstHeader("Location").getValue();
            return HttpsUtils.doGet(location);
        }
        
        HttpEntity entity = response.getEntity();
        HeaderElement[] hes = entity.getContentType().getElements();
        String encode = "utf-8";
        if (hes != null && hes.length > 0) {
            for (HeaderElement he : hes) {
                encode = he.getParameterByName("charset") == null ? "utf-8" : he.getParameterByName("charset").getValue();
            }
        }
        if (entity != null) {
            result = EntityUtils.toString(entity, encode);
        }
        httpclient.close();
        return result;
    }
    
}