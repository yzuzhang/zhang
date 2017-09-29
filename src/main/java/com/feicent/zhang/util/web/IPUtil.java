package com.feicent.zhang.util.web;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

/**
 * https://github.com/dmajkic/redis/downloads
 */
public class IPUtil {
	
	public static final String UNKNOWN = "unknown";
	
	public static String getLocalServerIp() {
		String serverIp;
		try {
			InetAddress address = InetAddress.getLocalHost();
			serverIp = address.getHostAddress();
		} catch (UnknownHostException e) {
			serverIp = "127.0.0.1";
		}
		return serverIp;
	}
	
	/**
	 * 获取客户端的 IP
	 * @param request
	 * @return 客户端的 IP
	 */
	public static String getClientIp(HttpServletRequest request) {
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
	    if("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
	    	ip = getLocalServerIp();
	    }
	    
	    return ip;
	}
	
}
