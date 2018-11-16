package com.feicent.zhang.util.tool;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * ip地址工具类
 * @date 2018年1月16日
 */
public class IpUtils {
	private static String LOCAL_IP_ADDRESS;
	public static final String ANYHOST = "0.0.0.0";
	public static final String LOCALHOST = "127.0.0.1";
	private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

	/**
	 * 获得本地ip地址
	 * 
	 * 注：内部吞掉异常，所以请进行判空处理
	 * 
	 * @return
	 */
	public static String getLocalIpAddress() {
		if(LOCAL_IP_ADDRESS != null) { 
			return LOCAL_IP_ADDRESS; 
		}
		
		// 获取本机的ip地址
		try {
			InetAddress addr = getInetAddress();
			if (addr != null) {
				LOCAL_IP_ADDRESS = addr.getHostAddress();// 获得本机IP
			}
		} catch (Exception e) {}
		
		return LOCAL_IP_ADDRESS;
	}
	
	public static String getLocalHostQuietly() {
	    String localAddress;
	    try {
	      localAddress = InetAddress.getLocalHost().getHostAddress();
	    } catch (Exception ex) {
	      localAddress = "localhost";
	    }
	    return localAddress;
	}

	private static InetAddress getInetAddress() throws Exception {
		try {
			InetAddress localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable e) {}
		
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        if (addresses != null) {
                            while (addresses.hasMoreElements()) {
                                try {
                                    InetAddress address = addresses.nextElement();
                                    if (isValidAddress(address)) {
                                        return address;
                                    }
                                } catch (Throwable e) {}
                            }
                        }
                    } catch (Throwable e) {}
                }
            }
		} catch (Exception ex) {
			throw ex;
		}
		
		return null;
	}
	
    private static boolean isValidAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress())
            return false;
        String name = address.getHostAddress();
        return (name != null 
                && ! ANYHOST.equals(name)
                && ! LOCALHOST.equals(name) 
                && IP_PATTERN.matcher(name).matches());
    }

	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

}
