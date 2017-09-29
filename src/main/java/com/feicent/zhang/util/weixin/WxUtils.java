package com.feicent.zhang.util.weixin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;

/**
 * 微信支付工具类
 * https://my.oschina.net/langxSpirit/blog/488435
 * @version 2015年8月2日
 */
public class WxUtils {

    /**
     * 随机字符串，不长于32 位
     * 
     * @return
     * @author 蔡政滦 modify by 2015年8月2日
     */
    public static String randomStr() {
        String template = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        while (buffer.length() < 32) {
            int index = random.nextInt(36);
            char c = template.charAt(index);
            buffer.append(c);
        }
        return buffer.toString();
    }
    
    /**
     * 签名
     * 
     * @param map 数据
     * @param password 密钥
     * @return
     * @author 蔡政滦 modify by 2015年8月2日
     * @throws Exception 
     */
    public static String getSign(Map<String, Object> map, String password) throws Exception {
        return getSign(map, password, "");
    }
    
    /**
     * 签名
     * 
     * @param map 数据
     * @param password 密钥
     * @param ignore_keys 忽略的key
     * @return
     * @author 蔡政滦 modify by 2015年8月2日
     * @throws Exception 
     */
    public static String getSign(Map<String, Object> map, String password, String... ignoreKeys) throws Exception {
        String result = "";
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (ignoreKeys!=null && ignoreKeys.length>0) {
                for (int i = 0; i < ignoreKeys.length; i++) {
                    if (ignoreKeys[i].equals(entry.getKey())) {
                        continue;
                    }
                }
            }
            if (entry.getValue() != null && !"".equals(entry.getValue())) {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        if (size > 0) {
            String[] arrayToSort = list.toArray(new String[size]);
            Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append(arrayToSort[i]);
            }
            result = sb.toString();
            result += "key=" + password;
            result = PayUtils.md5Digest(result);
            result = result.toUpperCase();
        }
        return result;
    }

    /**
     * 组装请求数据字符串
     * 
     * @param postData 请求数据
     * @return
     * @author 蔡政滦 modify by 2015年8月2日
     */
    public static String toXml(Map<String, String> postData) {
        return HttpsUtils.toXml("xml", postData);
    }

    /**
     * 提交http请求，获取响应数据字符串
     * 
     * @param url 请求URL
     * @param xml 请求数据字符串
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @author 蔡政滦 modify by 2015年8月2日
     */
    public static String postXml(String url, String xml) throws Exception {
        Map<String, String> headerInfo = new HashMap<String, String>();
        headerInfo.put("Content-Type", "text/xml");
        headerInfo.put("Connection", "keep-alive");
        headerInfo.put("Accept", "*/*");
        headerInfo.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headerInfo.put("Host", "api.mch.weixin.qq.com");
        headerInfo.put("X-Requested-With", "XMLHttpRequest");
        headerInfo.put("Cache-Control", "max-age=0");
        headerInfo.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        return HttpsUtils.postXml(url, headerInfo, xml);
    }

    /**
     * 提交https请求，获取响应数据字符串
     * 
     * @param url 请求URL
     * @param xml 请求数据字符串
     * @keyStorePath 证书存放路径
     * @keysecret 证书密码
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @author 蔡政滦 modify by 2015年8月2日
     */
    public static String postXmlSSL(String url, String xml, String keyStorePath, String keysecret) throws IllegalStateException, IOException, Exception {
        Map<String, String> headerInfo = new HashMap<String, String>();
        headerInfo.put("Content-Type", "text/xml");
        headerInfo.put("Connection", "keep-alive");
        headerInfo.put("Accept", "*/*");
        headerInfo.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headerInfo.put("Host", "api.mch.weixin.qq.com");
        headerInfo.put("X-Requested-With", "XMLHttpRequest");
        headerInfo.put("Cache-Control", "max-age=0");
        headerInfo.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        return HttpsUtils.postXmlSSL(url, headerInfo, xml, keyStorePath, keysecret);
    }
}
