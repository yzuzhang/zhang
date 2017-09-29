package com.feicent.zhang.util.ssh;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SCPInputStream;
import ch.ethz.ssh2.SCPOutputStream;

public class SCPClient2 {
	
	private SSHClient2 sshClient2;
	public SCPClient2(SSHClient2 sshClient2) {
		this.sshClient2 = sshClient2;
	}
	/**
	 * copy远程服务器上文件到本地
	 * @param remoteFile 远程文件
	 * @param localFile 本地文件
	 * @throws IOException
	 */
	public void copyRemoteToLocal(String remoteFile,String localFile) throws IOException{
		copyRemoteToLocal(remoteFile, localFile,null);
	}
	
	/**
	 * copy远程服务器上文件到本地
	 * @param remoteFile 远程文件
	 * @param localFile 本地文件
	 * @param localFilename 自定义保存到本地文件名
	 * @throws IOException
	 */
	public void copyRemoteToLocal(String remoteFile,String localFile,String localFilename) throws IOException{
		SCPClient client = this.sshClient2.conn.createSCPClient();
		// 将文件下载到本地
		SCPInputStream scpInputStream = client.get(remoteFile);
		BufferedInputStream bis = null;
		BufferedOutputStream bufferedOutputStream=null;
		try {
			bis = new BufferedInputStream(scpInputStream);
			String baseName=null;
			if(StringUtils.isNotBlank(localFilename)){
				baseName = localFilename;
			}else{
				baseName=FilenameUtils.getName(remoteFile);
			}
			File f = new File(localFile,baseName);
			if(!f.exists()){
				f.createNewFile();
			}
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(f));
			IOUtils.copy(bis, bufferedOutputStream);
		}catch(IOException ioe){
			throw new RuntimeException("IP:"+sshClient2.getIp()+",原因:"+ioe.getMessage());
		}finally{
			IOUtils.closeQuietly(bufferedOutputStream);
			IOUtils.closeQuietly(bis);
		}
	}
	
	
	/**
	 * copy本地文件到远程目录
	 * @param localFile 本地文件 
	 * @param remoteTargetDirectory 远程目录 
	 * @throws IOException
	 */
	public void copyLocalToRemote(String localFile,String remoteTargetDirectory) throws IOException {
		File file = new File(localFile);
		InputStream is = null;
		SCPOutputStream os = null;
		try {
			SCPClient client = this.sshClient2.conn.createSCPClient();
			is =  new BufferedInputStream(new FileInputStream(file));
			String name = FilenameUtils.getName(localFile);
			os = client.put(name,file.length() , remoteTargetDirectory, null);
			IOUtils.copy(is, os);
		}catch(IOException ioe){
			throw new RuntimeException("IP:"+sshClient2.getIp()+",原因:"+ioe.getMessage());
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
	}
	
	public void copyLocalToRemote(InputStream is,String remoteTargetDirectory,String filename) throws IOException {
		SCPOutputStream os = null;
		BufferedInputStream bis = null;
		try {
			SCPClient client = this.sshClient2.conn.createSCPClient();
			bis =  new BufferedInputStream(is);
			os = client.put(filename,is.available() , remoteTargetDirectory, null);
			IOUtils.copy(bis, os);
		}catch(IOException ioe){
			throw new RuntimeException("IP:"+sshClient2.getIp()+",原因:"+ioe.getMessage());
		} finally {
			IOUtils.closeQuietly(bis);
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
	}
	
	/**
	 * copy本地文件到远程目录
	 * @param localFile 本地文件 
	 * @param remoteTargetDirectory 远程目录 
	 * @param remoteFileName 远程文件名
	 * @throws IOException
	 */
	public void copyLocalToRemote(String localFile,String remoteTargetDirectory, String remoteFileName) throws IOException {
		File file = new File(localFile);
		InputStream is = null;
		SCPOutputStream os = null;
		try {
			SCPClient client = this.sshClient2.conn.createSCPClient();
			is =  new BufferedInputStream(new FileInputStream(file));
			os = client.put(remoteFileName,file.length() , remoteTargetDirectory, null);
			IOUtils.copy(is, os);
		} catch(IOException ioe){
			throw new RuntimeException("IP:"+sshClient2.getIp()+",原因:"+ioe.getMessage());
		}finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
	}
	
	public SSHClient2 getSshClient2() {
		return sshClient2;
	}
	
	public void setSshClient2(SSHClient2 sshClient2) {
		this.sshClient2 = sshClient2;
	}
	
}
