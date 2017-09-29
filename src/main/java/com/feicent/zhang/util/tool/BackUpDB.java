package com.feicent.zhang.util.tool;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.feicent.zhang.util.CloseUtil;

/**
 * 备份数据库
 * @author yzuzhang
 *
 */
public class BackUpDB {
	  
	  private String userName = "root";
	  private String userPwd = "123456";
	  private String databaseName = "zhang";//数据库名
	  private String copyDbCmd = "mysqldump";//数据库备份命令
	  private String copyprocCmd = "-R";//存储工程备份命令
	  private String copyedFilePath = "D:"; //备份后的文件存放位置
	  private String restoreCmd = "mysql";//数据库还原命令
	  private String databaseDirectory = "D:/Program Files/MySQL/MySQL Server 5.5/bin"; //数据库bin路径
	  
	  public BackUpDB(){
		  
	  }
	  
	  public BackUpDB(String userName, String userPwd){
		  this.userPwd = userPwd;
		  this.userName = userName;
	  }
	  
	  /**
	   * 备份数据库
	   * @throws Exception
	   */
	  public void backupDataBase() throws Exception {
		Runtime rt = Runtime.getRuntime();
		
		// 调用 mysql 的 cmd:
		System.out.println("数据库名: "+databaseName);
		System.out.println("用户名: "+userName); 
		System.out.println("密码: "+userPwd);
		System.out.println("数据库备份命令: "+copyDbCmd);
		System.out.println("存储工程备份命令: "+copyprocCmd);
		System.out.println("数据库bin路径: "+databaseDirectory);
		
		String str = databaseDirectory + "/" + copyDbCmd + " --no-defaults "
				+ "-u " + userName + " " + "-p " + userPwd + " " + copyprocCmd
				+ " " + "--set-charset=utf-8" + " " + databaseName;
		
		//str = "D:/SOFT/MySQL/MySQL Server 5.5/bin/mysqldump -uroot -p123456 -R --set-charset=utf-8 zhang";
		System.out.println(str);
		
		// 设置导出编码为utf8。这里必须是utf8
		Process child = rt.exec(str);// 设置导出编码为utf8。这里必须是utf8
		
		// 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
		InputStream in = child.getInputStream();// 控制台的输出信息作为输入流

		InputStreamReader xx = new InputStreamReader(in, "utf8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码

		String inStr;
		StringBuffer sb = new StringBuffer("");
		String outStr;
		// 组合控制台输出信息字符串
		BufferedReader br = new BufferedReader(xx);

		// System.out.println("------------"+br.readLine());

		while ((inStr = br.readLine()) != null) {
			sb.append(inStr + "\r\n");
		}
		outStr = sb.toString();

		//System.out.println("执行结果：\n"+outStr);
		// 创建文件名称
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String today = format.format(new Date());

		// String targetDirectory = scontext.getRealPath("/WEB-INF/DBbackup");
		// System.out.println(targetDirectory);
		// 要用来做导入用的sql目标文件：D:/ibtts.sql
		
		FileOutputStream fout = new FileOutputStream(copyedFilePath + "/"
				+ databaseName + "_" + today + ".sql");
		OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");
		writer.write(outStr);
		// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
		writer.flush();
		
		// 别忘记关闭输入输出流
		CloseUtil.close(writer, fout, in, xx, br);
		System.out.println("成功导出数据库["+databaseName+"]");
	  }
	  
	  /**
	   * 数据的恢复
	   * @throws Exception
	   */
	  public void load() throws Exception {
		// String targetDirectory = scontext.getRealPath("/WEB-INF/DBbackup");
		// 获取id后进行查询 D:/ibtts.sql
		String fPath = copyedFilePath + "/";
		System.out.println(fPath);
		
		Runtime rt = Runtime.getRuntime();
		//rt.exec("D:/db/mysql/mysql-5.0.45-win32/bin/mysql -uroot -pmysql ibtts");
		String cmd = databaseDirectory + "/" + restoreCmd+" -u" + userName + " -p"+ userPwd + " " +databaseName;
		Process child = rt.exec(cmd);
		//System.out.println(cmd);
		
		OutputStream out = child.getOutputStream();// 控制台的输入信息作为输出流
		String inStr;
		StringBuffer sb = new StringBuffer("");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(fPath), "utf8"));
		while ((inStr = br.readLine()) != null) {
			sb.append(inStr + "\r\n");
		}
		
		OutputStreamWriter writer = new OutputStreamWriter(out, "utf8");
		writer.write(sb.toString());
		// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
		writer.flush();
		
		// 别忘记关闭输入输出流
		CloseUtil.close(writer, out, br);
	}
	  
	/**
	 * 对特定表的处理
	 * @throws Exception
	 */
	public void copytable() throws Exception {
		Runtime rt = Runtime.getRuntime();
		
		// 调用mysql的cmd:备份某个表,设置导出编码为utf8。这里必须是utf8
		// Process child = rt.exec("D:/db/mysql/mysql-5.0.45-win32/bin/mysqldump -uroot -pmysql  --set-charset=utf8 ibtts t_a_dbbak");//
		Process child = rt.exec(databaseDirectory + "/" + copyDbCmd
				+ " --no-defaults " + "-u" + userName + " " + "-p" + userPwd
				+ " " + "--set-charset=utf8" //设置导出编码为utf8。这里必须是utf8
				+ " " + databaseName + " " + "t_a_dbbak");
		// 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
		InputStream in = child.getInputStream();// 控制台的输出信息作为输入流

		// System.out.println(databaseDirectory+"/"+copyDbCmd+" --no-defaults "+"-u"+userName+" "+"-p"+userPwd+" "+"--set-charset=utf8"+" "+databaseName+" "+"t_a_dbbak");
		InputStreamReader xx = new InputStreamReader(in, "utf8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码

		String inStr;
		StringBuffer sb = new StringBuffer("");
		String outStr;
		// 组合控制台输出信息字符串
		BufferedReader br = new BufferedReader(xx);
		while ((inStr = br.readLine()) != null) {
			sb.append(inStr + "\r\n");
		}
		outStr = sb.toString();
		// String targetDirectory = scontext.getRealPath("/WEB-INF/DBbackup");

		// 要用来做导入用的sql目标文件：D:/ibtts.sql
		FileOutputStream fout = new FileOutputStream(copyedFilePath + "/"
				+ "ibtts_talbe.sql");
		OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");
		writer.write(outStr);
		// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
		writer.flush();
		
		// 别忘记关闭输入输出流
		CloseUtil.close(writer,fout,in,xx,br);
	}
	
	public static void main(String[] args) throws Exception {
		BackUpDB backUpDB = new BackUpDB();
		backUpDB.backupDataBase();
	}
}
