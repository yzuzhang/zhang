package com.feicent.zhang.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.feicent.zhang.util.CloseUtil;

/**
 * 压缩、解压 工具类
 * @author yzuzhang
 * @date 2017年9月28日 下午7:05:45
 */
public class ZipUtils {
	
	private ZipUtils(){
	}
	
	public static void doCompress(String srcFile, String zipFile) throws IOException {
		doCompress(new File(srcFile), new File(zipFile));
	}
	
	/**
	 * 文件压缩
	 * @param srcFile 目录或者单个文件
	 * @param zipFile 压缩后的ZIP文件
	 */
	public static void doCompress(File srcFile, File zipFile) throws IOException {
		ZipOutputStream out = null;
        try {
        	out = new ZipOutputStream(new FileOutputStream(zipFile));
        	doCompress(srcFile, out);
        } catch (Exception e) {
        	throw e;
		} finally {
			out.close();//记得关闭资源
        }
	}
	
	public static void doCompress(String filelName, ZipOutputStream out) throws IOException{
		doCompress(new File(filelName), out);
	}
	
	public static void doCompress(File file, ZipOutputStream out) throws IOException{
		doCompress(file, out, "");
	}
	
	public static void doCompress(File inFile, ZipOutputStream out, String dir) throws IOException {
        if ( inFile.isDirectory() ) {
            File[] files = inFile.listFiles();
            if (files!=null && files.length>0) {
                for (File file : files) {
                    String name = inFile.getName();
                    if (!"".equals(dir)) {
                        name = dir + "/" + name;
                    }
                    ZipUtils.doCompress(file, out, name);
                }
            }
        } else {
        	 ZipUtils.doZip(inFile, out, dir);
        }
    }
	
	public static void doZip(File inFile, ZipOutputStream out, String dir) throws IOException {
		String entryName = null;
		if (!"".equals(dir)) {
		    entryName = dir + "/" + inFile.getName();
		} else {
		    entryName = inFile.getName();
		}
		ZipEntry entry = new ZipEntry(entryName);
		out.putNextEntry(entry);
		
		int len = 0 ;
		byte[] buffer = new byte[1024];
		FileInputStream fis = new FileInputStream(inFile);
		while ((len = fis.read(buffer)) > 0) {
		    out.write(buffer, 0, len);
		    out.flush();
		}
		out.closeEntry();
		fis.close();
	}
	
	/**
     * 文档解压
     * @param source 需要解压缩的文档名称
     * @param path   需要解压缩的路径
     */
    public static void unCompress(String source, String path) throws IOException {
        ZipEntry zipEntry = null;
        createPaths(path);
        //实例化ZipFile，每一个zip压缩文件都可以表示为一个ZipFile
        //实例化一个Zip压缩文件的ZipInputStream对象，可以利用该类的getNextEntry()方法依次拿到每一个ZipEntry对象
        ZipFile zipFile = new ZipFile(source);
        ZipInputStream zipInputStream = null;
        try {
        	zipInputStream = new ZipInputStream(new FileInputStream(source));
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String fileName = zipEntry.getName();
                File temp = new File(path + "/" + fileName);
                if (!temp.getParentFile().exists()) {
                    temp.getParentFile().mkdirs();
                }
                
                OutputStream out = new FileOutputStream(temp);
                //通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
                InputStream fis = zipFile.getInputStream(zipEntry);
                int len = 0 ;
        		byte[] buffer = new byte[1024];
        		while ((len = fis.read(buffer)) > 0) {
        		    out.write(buffer, 0, len);
        		    out.flush();
        		}
        		out.close();
        		fis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	CloseUtil.close(zipInputStream);
        }
    }
	
    public static void createPaths(String path) {
		File dir = new File(path);
		if ( !dir.exists()) {
			dir.mkdirs();
		}
	}
    
	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		doCompress("D:/yzuzhang/", "D:/yzuzhang.zip");
		System.out.println("耗时==="+(System.currentTimeMillis()-start)+"ms");
		
		unCompress("D:/yzuzhang.zip", "D:/yzuzhang/");
		System.out.println("耗时==="+(System.currentTimeMillis()-start)+"ms");
	}
	
}
