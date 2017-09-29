package com.feicent.zhang.util.ssh;

import java.io.IOException;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public abstract class AbstractSSHClient2 {
	
	protected Connection conn;
	protected String user;
	protected String password;
	protected String ip;
	protected int port=22;
	
	protected Session session;
	
	protected boolean authenticate() throws IOException{
		conn = new Connection(ip, port);
		conn.connect();
		boolean result = conn.authenticateWithPassword(user, password);
		if(result){
			return result;
		}else{
			throw new IOException(">>>授权失败，请检查["+ip+"]用户名和密码是否匹配！");
		}
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public Connection getConn() {
		return conn;
	}
	
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	public Session getSession() {
		return session;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}
	
}
