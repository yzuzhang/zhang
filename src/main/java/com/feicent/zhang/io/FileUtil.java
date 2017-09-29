package com.feicent.zhang.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.feicent.zhang.util.CloseUtil;
import com.feicent.zhang.util.MyUtil;

/**
 * 文件处理工具类
 * @author yzuzhang
 * @date 2017年8月8日
 */
public class FileUtil {
	
	private FileUtil() {
		
	}
	
	public static List<String> getListFromResource(String fileName){
		return getListFromResource(fileName, "UTF-8");
	}
	
	/**
	 * 获取文件内容List
	 * @param fileName
	 * String filePath = MyUtil.getUserDir()+ "resources/" + fileName;
	 */
	public static List<String> getListFromResource(String fileName, String charsetName){
		URL resourceUrl = ClassLoader.getSystemClassLoader().getResource(fileName);
		if( null == resourceUrl ){
			resourceUrl = FileUtil.class.getResource(fileName);
		}
		if( null == resourceUrl ){
			resourceUrl = FileUtil.class.getClassLoader().getResource(fileName);
		}
		
		List<String> list;
		if( resourceUrl != null ){
			String filePath = resourceUrl.getPath();
			System.out.println(fileName+" : "+ filePath);
			list = readLines(filePath, charsetName);
		} else {
			list = readToList(fileName, charsetName);
		}
		
		return list;
	}
	
	public static List<String> getListFromPath(String fileName, String charsetName){
		String filePath = MyUtil.getUserDir() + fileName;
		List<String> list = readLines(filePath, charsetName);
		return list;
	}
	
	public static List<String> readToList(String fileName, String charsetName) {
		InputStream in = null;
		BufferedReader br = null;
		InputStreamReader isReader = null;
		List<String> lines = new ArrayList<String>();
		
		try {
			in = new FileInputStream(FileUtil.class.getClassLoader().getResource(fileName).getPath());
			
			isReader = new InputStreamReader(in, charsetName);
			br = new BufferedReader(isReader);
			while (br.ready()) {
				lines.add(br.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			CloseUtil.close(br, isReader, in);
		}
		
		return lines;
	}
	
	/**
	 * @param filePath
	 * @param charsetName
	 */
	public static List<String> readLines(String filePath, String charsetName) {
		InputStream in = null;
		BufferedReader br = null;
		InputStreamReader isReader = null;
		List<String> lines = new ArrayList<String>();
		
		try {
			in = new FileInputStream(filePath);
			isReader = new InputStreamReader(in, charsetName);
			br = new BufferedReader(isReader);
			while (br.ready()) {
				lines.add(br.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			CloseUtil.close(br, isReader, in);
		}
		
		return lines;
	}
	
	/**
	 * 向文件里写入操作
	 * @param filePath 文件路径
	 * @param fileName 文件名称
	 * @param content  写入内容
	 * @param append   true:尾部增加  false:覆盖原文件
	 */
	public static void write(String directory, String fileName, String content, boolean append){
	    try {
	    	createMkdirAndFile(directory, fileName);
	    	File file = new File(directory, fileName);
	    	writeToFile(file, content, append);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeToFile(String fileName, String content, boolean append) {
		File file = new File(fileName);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		createNewFile(file);
		// 写入操作
    	writeToFile(file, content, append);
    }
	
	public static void writeToFile(File file, String content, boolean append) {
    	FileWriter writer = null;
    	try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(file, append);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	CloseUtil.close(writer);
        }
    }
	
	public static void createMkdirAndFile(String directory, String filename) throws IOException {
		File dir = new File(directory);
		forceMkdir(dir);
		File file = new File(directory, filename);
		createNewFile(file);
	}
	
	public static void forceMkdir(File directory) throws IOException {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                String message =
                    "File "
                        + directory
                        + " exists and is "
                        + "not a directory. Unable to create directory.";
                throw new IOException(message);
            }
        } else {
            if (!directory.mkdirs()) {
                if (!directory.isDirectory())
                {
                    String message ="Unable to create directory " + directory;
                    throw new IOException(message);
                }
            }
        }
    }
	
	public static void createNewFile(File file) {
		if( file.isFile() && !file.exists() ){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}

