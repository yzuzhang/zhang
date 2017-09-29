package com.feicent.zhang.util.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.feicent.zhang.entity.PhoneInfo;
import com.feicent.zhang.util.http.HttpUtil;
import com.feicent.zhang.util.StringUtils;

/**
* 通过手机号码,获得该号码的归属地、运营商等信息
*/
public class ShoujiHelper {
    
    public static final String SHOUJI_URL = "http://v.showji.com/Locating/showji.com2016234999234.aspx";
    public static final String REGEX_IS_MOBILE ="(?is)(^1[3|4|5|7|8][0-9]\\d{4,8}$)";// 正则表达式,可以只输入手机号码前7位
    
    /**
     * 根据手机号码获取归属地信息
     * @param mobileNumber
     * @throws Exception
     */
    public static String getMobileFrom(String mobileNumber) throws Exception {
        if (!veriyMobile(mobileNumber)) {
            throw new Exception("不是完整的11位手机号或者正确的手机号前七位");
        }
        
        String URL = SHOUJI_URL + "?m="+ mobileNumber +"&output=json&callback=querycallback&timestamp="+System.currentTimeMillis();
        String result = HttpUtil.readContentFromURL(URL);
        if( result != null ){
            result = StringUtils.substringBetween(result, "(", ")");
        }
        return result;
    }
    
    
    /**
     *验证手机号格式 是否符合
     *@param mobileNumber
     */ 
    public static boolean veriyMobile(String mobileNumber) { 
        Pattern p = Pattern.compile(REGEX_IS_MOBILE);
        Matcher m = p.matcher(mobileNumber);
        return m.matches(); 
    }
    
    //测试一下
    public static void main(String[] args) throws Exception {
        
        long[] phones={
	        13179556818L,18081685555L,18013715591L,18092163043L,18370631995L,
	        18026799125L,18056538590L,18038692012L,18083766020L,18092015835L,
	        18060923366L,18078158869L,18041153288L,18028690873L,18028796251L,
	        13585859733L,15961230678L,18937295656L,18056649455L,15640694239L,
	        18958283918L,18966551516L,15353951523L,18025114334L,15071469692L,
	        15139914717L,13738618263L,13181207626L,13516035175L,15327038012L,
	        18659138399L,15151920785L,18969515188L,13064025146L,15619969722L
        };
        
        List<PhoneInfo> list = new ArrayList<>();
        int successNum = 0, dealNum = 2;
        for(int i=0; i < dealNum; i++){
            String result = getMobileFrom(phones[i]+"");
            //System.out.println(result);
            if (result!=null && result.contains("True")) {
                successNum++;
            }
            PhoneInfo phone = JSONObject.parseObject(result, PhoneInfo.class);
            System.out.println(phone);
            list.add(phone);
        }
        
        System.out.println("=========================");
        System.out.println("|    处理的号码总数=="+dealNum);
        System.out.println("|    获取成功的个数=="+successNum);
        System.out.println("|    获取失败的个数=="+(dealNum-successNum));
        System.out.println("=========================");
    }
    
}
