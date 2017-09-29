package com.feicent.zhang.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feicent.zhang.util.CloseUtil;

public class DownloadUtils {
	
	private static Logger logger = LoggerFactory.getLogger(DownloadUtils.class);
	
	public static void download(HttpServletRequest request, HttpServletResponse response,
			String filepath, String fileName) throws Exception {
		
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		String ctxPath = request.getSession().getServletContext().getRealPath("/") +filepath;
		try {
			File file = new File(ctxPath);
			if(!file.exists())
				throw new RuntimeException("download agent is error: "+ ctxPath +" is not exists !");
			
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-disposition","attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			
			bis = new BufferedInputStream(new FileInputStream(ctxPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			
			int bytesRead = 0;
			byte[] buff = new byte[2048];
			while (-1 != (bytesRead = bis.read(buff))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			logger.error("download agent is error ! messages --->> "+e.fillInStackTrace());
		} finally {
			CloseUtil.close(bos, bis);
		}
	}
	
	
}
