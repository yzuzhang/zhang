package com.feicent.zhang.util.tool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.feicent.zhang.base.Constants;
import com.feicent.zhang.entity.User;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64; 

/**
 * 自动登录Cookie工具类
 * @author yzuzhang
 * @date 2017年2月2日
 */
@SuppressWarnings("restriction")
public class CookieUtil {
	
	public static final String UNKNOWN = "unknown";
	public static Log logger=LogFactory.getLog(CookieUtil.class);
	public final static String cookieDomainName = "WIFIID";//保存cookie时的cookieName
    public final static String COOKIE_KEY = "akazamCookies";//加密cookie时的网站自定码
    public final static int cookieMaxAge = 60 * 60 * 24 * 7 * 2;//设置cookie有效期是两个星期，根据需要自定义
    
    /**
     * 将cookie信息保存在客户端
     * @param response
     */
    public static void saveCookie(String phone, String password, HttpServletResponse response) {
        //cookie的有效期
        long validTime = System.currentTimeMillis() + (cookieMaxAge * 1000L);
        //MD5加密用户详细信息
        String cookieValueWithMd5 = getMD5(phone + "," + password + "," + validTime + "," + Constants.SYSTEM_KEY);
        
        //将要被保存的完整的Cookie值
        String cookieValue = phone + "," + validTime + "," + cookieValueWithMd5;
        //再一次对Cookie的值进行BASE64编码
        String cookieValueBase64 = new String(Base64.encode(cookieValue.getBytes()));
        //开始保存Cookie
        Cookie cookie = new Cookie(Constants.CookieDomainName, cookieValueBase64);
        cookie.setMaxAge(cookieMaxAge); 
        //cookie有效路径是网站根目录
        cookie.setPath("/");
        
        //向客户端写入
        response.addCookie(cookie);
    }
    
    public static void readCookieAndLogon(HttpServletRequest request, HttpServletResponse response, 
    		FilterChain chain) throws IOException, ServletException,UnsupportedEncodingException{
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	//根据cookieName取cookieValue
    	Cookie cookies[] = request.getCookies();
        String cookieValue = null;
        
        if( cookies != null ){
        	for(Cookie cookie : cookies){
        		if(Constants.CookieDomainName.equals(cookie.getName())){
        			cookieValue = cookie.getValue();
                    break;
        		}
        	}
         }
         //如果cookieValue为空,返回
         if( cookieValue == null ){
    	    chain.doFilter(request,response);
            return;
         }
        
        byte[] tempStr = null;
        String cookieValueAfterDecode = "";
		try {
			//对客户端取得的Cookie解码
			tempStr = BASE64Decoder.class.newInstance().decodeBuffer(cookieValue);
		} catch (InstantiationException e) {
			logger.debug(e);
			chain.doFilter(request,response);
			return;
		} catch (IllegalAccessException e) {
			logger.debug(e);
			chain.doFilter(request,response);
			return;
		}
		if (tempStr == null) {
			chain.doFilter(request,response);
      	    return;
		}
          
		  cookieValueAfterDecode = new String(tempStr, "UTF-8"); 
	      String cookieValues[] = cookieValueAfterDecode.split(",");
	      if(cookieValues.length != 3){	
    	  	 chain.doFilter(request,response);
             return;
	      }
    	  
          //判断是否在有效期内,过期就删除Cookie
          long validTimeInCookie = Long.parseLong(cookieValues[1]);
          if(validTimeInCookie < System.currentTimeMillis()){
        	 clearCookie(response);//删除Cookie
        	 chain.doFilter(request,response);
             return;
          }
          
          String phone = cookieValues[0];//取出cookie中的phone
          String md5ValueInCookie = cookieValues[2];//取出cookie中的cookieValueWithMd5
          logger.info("手机====="+ phone);
          logger.info("客户端MD5Cookie====="+ md5ValueInCookie);
          
          //根据用户名到数据库中检查用户是否存在
          User user = new User();//systemApi.login(phone);
          /*if (user == null) {
        	  chain.doFilter(request,response);
              return;
          } */             
         
         String md5ValueFromUser = getMD5(user.getPhone() + "," + user.getPassword() + "," + validTimeInCookie + "," + Constants.SYSTEM_KEY);
         logger.info("服务器端MD5Cookie====="+md5ValueFromUser);
         //将结果与Cookie中的MD5码相比较,如果相同,写入Session,自动登陆成功,并继续用户请求
         if(md5ValueFromUser.equals(md5ValueInCookie)){
                //处理
        	 	//...
                chain.doFilter(request, response);
         }else{
        	 chain.doFilter(request,response);
        	 return;
         }  	
	}
    
    private static void clearCookie(HttpServletResponse response) {
    	clearCookie(Constants.CookieDomainName, response);
    }
    
    //用户注销时,清除Cookie,在需要时可随时调用
	public static void clearCookie(String cookieName, HttpServletResponse response) {
		Cookie cookie = new Cookie(cookieName, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
    
    //获取Cookie组合字符串的MD5码的字符串
	private static String getMD5(String value) {
		String result = null;
		try {
			byte[] valueByte = value.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(valueByte);
			result = toHex(md.digest());
		} catch (NoSuchAlgorithmException e2) {
			logger.debug(e2);
		}
		return result;
	}
    
    //将传递进来的字节数组转换成十六进制的字符串形式并返回
	private static String toHex(byte[] buffer) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
		}
		return sb.toString();
	}
    
    public static String getstrBase64(String str){
    	String result = "";
    	result = new String(Base64.decode(str));
    	return result;
    }
    
	/**
	 * 从Request中获取客户端的IP
	 * @param request
	 */
	public static String getRemoteAddr(HttpServletRequest request) { 
		// 需要注意有多个 Proxy 的情况: X-Forwarded-For: client, proxy1, proxy2
	    // 没有最近的 Proxy 的 IP, 只有 1 个 Proxy 时是客户端的 IP
	    String ip = request.getHeader("X-Forwarded-For");
	    if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
	        ip = request.getHeader("X-Real-IP");
	    }
	    if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr(); // 没有使用 Proxy 时是客户端的 IP, 使用 Proxy 时是最近的 Proxy 的 IP
	    }
	    return ip;
	}
    
    public static void main(String[] args) throws InstantiationException, IllegalAccessException{
    	String cookieValueBase64 = new String(Base64.encode("ADFQEWPISFKSDJFERK".getBytes()));
    	System.out.println(cookieValueBase64);
    	
    	cookieValueBase64="MTUzNzEzNDY5MTUsMTM3NjkwNjI5NjM1OSxBNjQ0MEQxRjg1MjlBODZFMzI5NUMwMUZFQTgyRjkxNg==";
    	//cookieValueBase64="MTUzNzEzNDY5MTUsMTM3NjkwNDMzNTExNiw0NzlCRTU4RTY4M0U4ODAwNDM2NTU2QTVFMzZGRjIxNA==";
    	String temp=new String(Base64.decode(cookieValueBase64));
    	String[] ss= temp.split(",");
    	System.out.println(temp);
    	System.out.println("phone: "+ss[0]);
    }
    
}