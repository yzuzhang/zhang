package com.feicent.zhang.util.security.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

/**
 * xss攻击的预防过滤器
 * @author yzuzhang
 */
public class XssFilter implements Filter {
    
	FilterConfig filterConfig = null;
    /**
     * 需要匹配替换的正则表达式
     */
    private String regEx;
    /**
     * 过滤器排除url集合
     */
    private Set<String> excluded;
    
    /**
     * 过滤器初始化方法
     * @param filterConfig
     * @throws ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        if (filterConfig.getInitParameter("regEx") != null) {
            regEx = filterConfig.getInitParameter("regEx");
        }
        if (filterConfig.getInitParameter("excluded") != null) {
            String excludedStr = filterConfig.getInitParameter("excluded");
            if (excludedStr.contains(",")) {
                String[] arr = excludedStr.split(",");
                for (String s : arr) {
                    excluded.add(s.trim());
                }
            } else {
                excluded.add(excludedStr);
            }
        }
    }
    
    //
    public void destroy() {
        this.filterConfig = null;
    }
    
    //
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest inRequest = (HttpServletRequest) request;
        String uri = inRequest.getRequestURI();
        if (simpleMatch(excluded, uri)) {
            chain.doFilter(request, response);
            return;
        }
        chain.doFilter(new XssHttpServletRequestWrapper(inRequest,this.regEx), response);
    }

    private static boolean simpleMatch(Set<String> patterns, String str) {
        if (patterns == null || patterns.isEmpty() || str == null) {
            return false;
        }
        for (String pattern : patterns) {
            if (simpleMatch(pattern, str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * URL通配符匹配
     * @param pattern
     * @param str
     * @return
     */
    private static boolean simpleMatch(String pattern, String str) {
        if (pattern == null || str == null) {
            return false;
        }
        int firstIndex = pattern.indexOf('*');
        if (firstIndex == -1) {
            return pattern.equals(str);
        }
        if (firstIndex == 0) {
            if (pattern.length() == 1) {
                return true;
            }
            int nextIndex = pattern.indexOf('*', firstIndex + 1);
            if (nextIndex == -1) {
                return str.endsWith(pattern.substring(1));
            }
            String part = pattern.substring(1, nextIndex);
            int partIndex = str.indexOf(part);
            while (partIndex != -1) {
                if (simpleMatch(pattern.substring(nextIndex), str.substring(partIndex + part.length()))) {
                    return true;
                }
                partIndex = str.indexOf(part, partIndex + 1);
            }
            return false;
        }
        return (str.length() >= firstIndex &&
                pattern.substring(0, firstIndex).equals(str.substring(0, firstIndex)) &&
                simpleMatch(pattern.substring(firstIndex), str.substring(firstIndex)));
    }
    
}
