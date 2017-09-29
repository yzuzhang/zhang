package com.feicent.zhang.util.web.xss;

import java.io.IOException;  
  
import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServletRequest;  

/**
 * 
 * @author yzuzhang
 * @date 2017年9月19日
 */
public class XssFilter implements Filter {  
    
    @Override  
    public void init(FilterConfig filterConfig) throws ServletException {  
    }  
  
    @Override  
    public void doFilter(ServletRequest request, ServletResponse response,  
            FilterChain chain) throws IOException, ServletException {  
        chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);  
    }  
  
    @Override  
    public void destroy() {  
    }  
    
    /*<filter>  
	    <filter-name>XssEscape</filter-name>  
	    <filter-class>com.jd.ptest.utils.web.xss.XssFilter</filter-class>  
	</filter>  
	<filter-mapping>  
	    <filter-name>XssEscape</filter-name>  
	    <url-pattern>/*</url-pattern>  
	    <dispatcher>REQUEST</dispatcher>  
	</filter-mapping> */
}
