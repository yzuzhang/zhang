package com.feicent.zhang.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;

import org.apache.commons.io.IOUtils;


/**
 * 从文件读取内容工具类
 * @author yzuzhang
 */
public class ReadFromFile {
	
	/**
	 * 以字节为单位读取文件内容
	 * @param fileName
	 */
    public static void readFileByBytes(String fileName) {
        File file = new File(fileName);
        InputStream in = null;
        try {
            System.out.println("以字节为单位读取文件内容，一次读一个字节：");
            // 一次读一个字节
            in = new FileInputStream(file);
            int tempbyte = 0;
            while ((tempbyte = in.read()) != -1) {
                System.out.println(tempbyte);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	IOUtils.closeQuietly(in);
        }
        
        System.out.println("");
        try {
            System.out.println("以字节为单位读取文件内容，一次读多个字节：");
            // 一次读多个字节1Kb
            byte[] tempbytes = new byte[1024];
            int byteread = 0;
            in = new FileInputStream(fileName);
            showAvailableBytes(in);
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while ((byteread = in.read(tempbytes)) != -1) {
                System.out.write(tempbytes, 0, byteread);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
        	IOUtils.closeQuietly(in);
        }
    }

    /**
     * 以字符为单位读取文件内容
     * @param fileName
     */
    public static void readFileByChars(String fileName) {
        File file = new File(fileName);
        Reader reader = null;
        try {
            System.out.println("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file), "utf-8");
            int tempchar = 0;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    System.out.print((char) tempchar);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	IOUtils.closeQuietly(reader);
        }
        
        try {
            System.out.println("以字符为单位读取文件内容，一次读多个字节：");
            // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName));
            // 读入多个字符到字符数组(内存)中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length) && (tempchars[tempchars.length - 1] != '\r')) {
                    System.out.print(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            System.out.print(tempchars[i]);
                        }
                    }
                }
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
        	IOUtils.closeQuietly(reader);
        }
    }

    /**
     * 逐行读取文件内容
     * @param fileName
     */
    public static void readFileByLines(String fileName) {
        BufferedReader reader = null;
        try {
        	File file = new File(fileName);
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	IOUtils.closeQuietly(reader);
        }
    }
   
    public static String readFileByRandomAccess(String fileName, long beginIndex) {
    	RandomAccessFile randomFile = null;
    	StringBuffer sb = new StringBuffer();
        
        try {
            // 打开一个随机访问文件流，按只读方式
            randomFile = new RandomAccessFile(fileName, "r");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 读文件的起始位置
            beginIndex = (beginIndex > fileLength) ? fileLength : beginIndex;
            // 将读文件的开始位置移到beginIndex位置。
            randomFile.seek(beginIndex);
            // 一次读1024个字节，如果文件内容不足1024个字节，则读剩下的字节。
            byte[] bytes = new byte[1024];
            int byteread = 0;// 将一次读取的字节数赋给byteread
            while ((byteread = randomFile.read(bytes)) != -1) {
            	sb.append(new String(bytes, 0, byteread));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	IOUtils.closeQuietly(randomFile);
        }
        return sb.toString();
    }
   
    private static void showAvailableBytes(InputStream in) {
        try {
            System.out.println("当前字节输入流中的字节数为:" + in.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        String fileName = "D:/javawed/其他/demo.txt";
        //ReadFromFile.readFileByBytes(fileName);
        //ReadFromFile.readFileByChars(fileName);
        
        ReadFromFile.readFileByLines(fileName);
        
        //System.out.println(ReadFromFile.readFileByRandomAccess(fileName, 0));
    }
}
