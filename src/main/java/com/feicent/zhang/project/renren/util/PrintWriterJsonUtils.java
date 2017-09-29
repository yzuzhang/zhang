package com.feicent.zhang.project.renren.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class PrintWriterJsonUtils {
	
	public static void printWriter(HttpServletResponse response, String json) throws IOException {
		PrintWriter pWriter = response.getWriter();
		pWriter.write(json);
		pWriter.close();
	}

}
