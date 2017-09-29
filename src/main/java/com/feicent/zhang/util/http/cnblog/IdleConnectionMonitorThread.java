package com.feicent.zhang.util.http.cnblog;

import java.util.concurrent.TimeUnit;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * 监控http connection的空闲线程
 * 负责监控httpclient中的连接，进行清理操作。同时提供终止爬虫的功能。
 * @author yzuzhang
 * @date 2017年8月30日
 * http://blog.csdn.net/wanghao109/article/details/53121436
 */
public class IdleConnectionMonitorThread extends Thread {
	
	/**
	 * PoolingHttpClientConnectionManager is a more complex implementation that
	 * manages a pool of client connections and is able to service connection 
	 * requests from multiple execution threads.
	 */ 
	private final PoolingHttpClientConnectionManager connMgr;
	private volatile boolean shutdown;
	
	public IdleConnectionMonitorThread(PoolingHttpClientConnectionManager connMgr) {
		super("Connection Manager");
		this.connMgr = connMgr;
	}

	@Override
	public void run() {
		try {
			while (!shutdown) {
				synchronized (this) {
					wait(5000);
					// 停止过期的连接
					connMgr.closeExpiredConnections();
					// Optionally, close connections that have been idle longer
					// than 30 sec
					connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
				}
			}
		} catch (InterruptedException ex) {
			
		}
	}
	
	public void shutdown() {
		shutdown = true;
		synchronized (this) {
			notifyAll(); //让run方法不再wait
		}
	}
}
