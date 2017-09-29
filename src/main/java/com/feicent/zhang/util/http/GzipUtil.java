package com.feicent.zhang.util.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import com.feicent.zhang.util.CloseUtil;

/**
 * 对HTTP请求gzip压缩进行解压
 * @author yzuzhang
 * @date 2017年9月21日
 */
public class GzipUtil {
	
	private GzipUtil() {
		
    }
	
    public static String decompressGzip(byte[] bytes) {
        String decodedStr = decompressGzip(new ByteArrayInputStream(bytes), "UTF-8");
        return decodedStr;
    }
    
    public static String decompressGzip(byte[] bytes, String encoding) {
        String decodedStr = decompressGzip(new ByteArrayInputStream(bytes), encoding);
        return decodedStr;
    }
    
    public static String decompressGzip(InputStream in, String encoding) {
    	String decodedStr = null;
    	GZIPInputStream gzipInput = null;
        ByteArrayOutputStream outputStream = null;
        
        try {
        	if(null==in || in.available()==0){
        		return null;
        	}
        	
        	gzipInput = new GZIPInputStream(in);
            outputStream = new ByteArrayOutputStream();
            
            int length = 0;
            byte[] buffer = new byte[1024];
            while((length=gzipInput.read(buffer)) >= 0) {
                outputStream.write(buffer, 0, length);
            }
            
            decodedStr = new String(outputStream.toByteArray(), encoding);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	CloseUtil.close(outputStream, in);
        }

        return decodedStr;
    }
    
}
