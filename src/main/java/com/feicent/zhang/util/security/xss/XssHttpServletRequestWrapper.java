package com.feicent.zhang.util.security.xss;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 用户请求包装类
 * Created on 2016/7/14
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    /**
     * 需要匹配替换的正则表达式
     */
    private String regEx;

    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest,String regEx) {
        super(servletRequest);
        this.regEx = regEx;
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXSS(values[i]);
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);
    }

    //
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null)
            return null;
        return cleanXSS(value);
    }

    /**
     * 清除替换XSS代码
     * @param value
     * @return
     */
    private String cleanXSS(String value) {
//        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
//        value = value.replaceAll("'", "&#39;");
//        value = value.replaceAll("eval\\((.*)\\)", "");
//        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
//        value = value.replaceAll("script", "").trim();
    	
        String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\"]";
        if(this.regEx != null){
            regEx = this.regEx;
        }
        Pattern p   =   Pattern.compile(regEx);
        Matcher dataMatcher   =   p.matcher(value);
        value = dataMatcher.replaceAll("").trim() ;
        return value;
    }
    
}
