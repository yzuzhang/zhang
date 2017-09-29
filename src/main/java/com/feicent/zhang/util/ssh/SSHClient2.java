package com.feicent.zhang.util.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feicent.zhang.util.CloseUtil;

import ch.ethz.ssh2.StreamGobbler;

public class SSHClient2 extends AbstractSSHClient2{
	
	public final static Logger log = LoggerFactory.getLogger(SSHClient2.class);
	protected String charset = Charset.defaultCharset().toString();
	
	/**
	 * 超时时间5分钟
	 */
	protected static final int TIME_OUT = 1000 * 60 * 5;
	
	public SSHClient2(String ip ,String user,String password) {
		this.ip = ip;
		this.user=user;
		this.password= password;
		try {
			this.authenticate();
		}catch (IOException io) {
			throw new RuntimeException(io.getMessage());
		}
	}
	
	public SSHClient2(String ip, String user, String password, int port) {
		this.ip = ip;
		this.user=user;
		this.password= password;
		this.port = port;
		try {
			this.authenticate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void openSession() throws IOException{
		this.session = conn.openSession();
		//加这句会阻塞 
		//session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT);
		//int exitStatus = session.getExitStatus();
	}
	
	public void closeSession(){
		if(this.session!=null){
			this.session.close();
		}
	}
	
	/**
	 * 执行命令不需要返回值
	 * @param cmd
	 */
	public void exeCmd(String cmd){
		try {
			this.openSession();
			this.session.execCommand(cmd, charset);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			this.closeSession();
		}
	}
	
	/**
	 * 执行命令有返回值
	 * @param cmd
	 */
	public String exeCommand(String cmd){
		StringBuffer sb = new StringBuffer();
		BufferedReader stdOut = null; //正常信息
		BufferedReader stdErr = null; //异常信息
		
		try{
			openSession();
			this.session.execCommand(cmd, charset);
			
			//正常信息
			stdOut = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStdout())));
			//异常信息
			stdErr = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStderr())));
			String tmp = null;
			
			while((tmp=stdOut.readLine())!=null){
				sb.append(tmp).append("\n");
			}
			while((tmp=stdErr.readLine())!=null){
				sb.append(tmp).append("\n");
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			CloseUtil.close(stdOut,stdErr);
			closeSession();
		}
		
		String result = sb.toString();
		if(StringUtils.isNotBlank(result)){
			result = result.substring(0, result.lastIndexOf("\n"));
		}
		return result;
	}
	
	public static void main(String[] args) {
		SSHClient2 client = new SSHClient2("192.168.172.97", "root", "jdptest");
		String content  = client.exeCommand("cat /etc/hosts");
		System.out.print(content);
	}
}
