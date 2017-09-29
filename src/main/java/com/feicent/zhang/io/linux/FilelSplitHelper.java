package com.feicent.zhang.io.linux;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Java操作Linux命令分割合并文本文件及其他
 * http://www.toutiao.com/i6438497324594364930/
 * @author yzuzhang
 * @date 2017年8月22日
 */
public class FilelSplitHelper {
	
	public static final String UTF_8 = "UTF-8";
	
	/**
	 * 磁盘空间使用率获取
	 * 在处理文本之前，一定要备份一下，在备份之前要判断一下磁盘空间是否足够，用到的Linux命令是 df -hl -P
	 * @param filePath 要看的目录
	 * @return
	 * @throws Exception
	 */
	public static int getDiskUsage(String filePath) throws Exception {
		int diskUsage = 0;
		Process ps = null;
		ProcessBuilder builder = null;
		
		try {
			String[] cmd = { "/bin/sh", "-c", "df -hl -P" };
			builder = new ProcessBuilder(cmd);
			builder.redirectErrorStream(true);
			ps = builder.start();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					ps.getInputStream(), UTF_8));
			
			String result = null;
			while ((result = br.readLine()) != null) {
				String[] entitys = result.split("");
				for (String entity : entitys) {
					if (entity!=null && entity.startsWith("/")) {
						String[] t = entity.split(" ");
						for (int j = 0; j < t.length; j++) {
							if (t[j].endsWith(filePath)) {
								if (t[j - 1].contains("%")) {
									diskUsage = Integer.parseInt(t[j - 1].replace("%", ""));
								}
							}
						}
					}
				}
			}
			ps.waitFor();
			return diskUsage;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			destroy(ps);
		}
	}
	
	/**
	 * 获取文件行数
	 * 获取总行数主要是为了能确认按行分割文件的话，能分割几个文件。
	 * 使用的Linux命令是：find /home/leo -name "java.txt"|xargs cat|wc -l
	 * @param filePath
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static long getLineNum(String filePath, String fileName) throws Exception {
		Process ps = null;
		long lineNums = 0L;
		ProcessBuilder builder = null;
		
		try {
			String[] cmd = {
					"/bin/sh",
					"-c",
					"find " + filePath + " -name \"" + fileName + "\"|xargs cat|wc -l" };
			
			builder = new ProcessBuilder(cmd);
			builder.redirectErrorStream(true);
			ps = builder.start();
			
			BufferedReader stdoutReader = new BufferedReader(
					new InputStreamReader(ps.getInputStream(), UTF_8));// linux终端的编码为utf-8
			
			String outLine = null;
			while ((outLine = stdoutReader.readLine()) != null) {
				if (outLine.contains("No such file or directory")) {
					throw new Exception("查询文件行数失败,文件不存在!");
				} else {
					lineNums = Long.parseLong(outLine);
				}
			}
			
			ps.waitFor();
			return lineNums;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {	
			destroy(ps);
		}
	}
	
	/**
	 * 分割文件
	 * 这里使用了分割、改名两条命令，所以写成了一个sh
	 * 注意不要忘了给sh赋权
	 * @param filePath
	 * @param prefix
	 * @return
	 * @throws Exception
	 */
	public static String splitFile(String filePath, String prefix) throws Exception {
		Process ps = null;
		String restr = "fail";
		ProcessBuilder builder = null;
		
		try {
			String[] cmd = { "/bin/sh", "-c", "/home/admin/splitfile.sh "+ filePath +" " + prefix };
			
			builder = new ProcessBuilder(cmd);
			builder.redirectErrorStream(true);
			ps = builder.start();
			
			BufferedReader stdoutReader = new BufferedReader(
					new InputStreamReader(ps.getInputStream(), UTF_8));// linux终端的编码为utf-8
			
			String outLine = null;
			while ((outLine = stdoutReader.readLine()) != null) {
				if (outLine.contains("No such file or directory")) {
					throw new Exception("查询文件行数失败,文件不存在!");
				} 
				restr = outLine;
			}
			
			ps.waitFor();
			return restr;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			destroy(ps);
		}
	}
	
	/**
	 * 合并文件
	 * 使用的Linux命令是：
	 * cat /home/leo/sd_000.txt /home/leo/sd_001.txt /home/leo/sd_002.txt /home/leo/sd_003.txt /home/leo/sd_004.txt > /home/leo/java2.txt
	 * @param fileList
	 * @param destFileName
	 * @return
	 * @throws Exception
	 */
	public static String catFiles(String fileList, String destFileName) throws Exception {
		Process ps = null;
		String restr = "done";
		ProcessBuilder builder = null;
		
		try {
			// cat split00.txt split01.txt > split.txt
			String[] cmd = { "/bin/sh", "-c", "cat " + fileList + " > " + destFileName };
			
			builder = new ProcessBuilder(cmd);
			builder.redirectErrorStream(true);
			ps = builder.start();
			
			BufferedReader stdoutReader = new BufferedReader(
					new InputStreamReader(ps.getInputStream(), UTF_8));// linux终端的编码为utf-8
			
			String outLine = null;
			while ((outLine = stdoutReader.readLine()) != null) {
				if (outLine.contains("No such file or directory")) {
					throw new Exception("查询文件行数失败,文件不存在!");
				}
				restr = outLine;
			}
			
			ps.waitFor();
			return restr;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			destroy(ps);
		}
	}
	
	protected static void destroy(Process ps) {
		if (ps != null) {
			ps.destroy();
		}
	}
    
}
