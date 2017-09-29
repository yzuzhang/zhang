package com.feicent.zhang.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;

import com.feicent.zhang.util.CloseUtil;

/**
 * 从网上下载文件的几种方式
 * @author panda fang
 * @date 2017-08-26
 * @version 1.0
 * http://www.cnblogs.com/lonkiss/p/how-to-download-file-from-internet-using-java.html
 */
public class DownloadTool {
    
    /**
     * 使用传统io stream 下载文件
     * @param url
     * @param saveDir
     * @param fileName
     */
    public static void download(String url, String saveDir, String fileName) {
    	InputStream is = null;
        BufferedOutputStream bos = null;
        try {
            byte[] buff = new byte[8192];
            is = new URL(url).openStream();
            File file = new File(saveDir, fileName);
            file.getParentFile().mkdirs();
            bos = new BufferedOutputStream(new FileOutputStream(file));
            int count = 0;
            while ( (count = is.read(buff)) != -1) {
                bos.write(buff, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	CloseUtil.close(bos, is);   
        }
    }

    /**
     * 利用 commonio 库下载文件，依赖Apache Common IO,
     * 官网 https://commons.apache.org/proper/commons-io/
     * @param url
     * @param saveDir
     * @param fileName
     */
    public static void downloadByApacheCommonIO(String url, String saveDir, String fileName) {
        try {
            FileUtils.copyURLToFile(new URL(url), new File(saveDir, fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 使用NIO下载文件， 需要 jdk 1.4+
     * @param url
     * @param saveDir
     * @param fileName
     */
    public static void downloadByNIO(String url, String saveDir, String fileName) {
        ReadableByteChannel channel = null;
        FileOutputStream fos = null;
        FileChannel foutc = null;
        try {
            channel = Channels.newChannel(new URL(url).openStream());
            File file = new File(saveDir, fileName);
            file.getParentFile().mkdirs();
            fos = new FileOutputStream(file);
            foutc = fos.getChannel();
            foutc.transferFrom(channel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	CloseUtil.close(foutc, fos, channel);
        } 
    }
    
    /**
     * 使用NIO下载文件， 需要 jdk 1.7+
     * @param url
     * @param saveDir
     * @param fileName
     */
    public static void downloadByNIO2(String url, String saveDir, String fileName) {
        try (InputStream ins = new URL(url).openStream()) {
            Path target = Paths.get(saveDir, fileName);
            Files.createDirectories(target.getParent());
            Files.copy(ins, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
    // 下载一个百度Logo
    public static void main(String[] args) {
    	DownloadTool.downloadByNIO2("http://www.baidu.com/img/bd_logo1.png", "/export/tmp/", "baidu_logo.png");
        System.out.println("DownloadTool.downloadByNIO2 OK");
    }
    
}